package com.kyant.ocrcore

import android.content.Context
import android.graphics.Bitmap
import com.googlecode.tesseract.android.TessBaseAPI
import com.googlecode.tesseract.android.TessBaseAPI.PageIteratorLevel
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Fully open-source (Apache-2.0) offline OCR fallback used only when the bundled
 * ML Kit engine fails to initialize or recognize (e.g. an OEM image stripped of the
 * TFLite runtime, or a non-Latin script ML Kit's default model doesn't cover).
 * Bundles `eng.traineddata` as a module asset so the fallback never needs a download.
 */
internal object TesseractOcrEngine {
    private const val LANG_LATIN = "eng"
    private const val LANG_ARABIC = "ara+eng"

    /** True when the Arabic model was bundled into the app (ara.traineddata asset). */
    fun hasArabicModel(context: Context): Boolean =
        runCatching { context.assets.list("tessdata")?.contains("ara.traineddata") == true }.getOrDefault(false)

    /** Copies every bundled trained-data asset into app-private storage on first use.
     *  Returns the data-path directory to pass to [TessBaseAPI.init] (the parent of "tessdata/"). */
    private fun ensureTrainedData(context: Context): File {
        val tessdataDir = File(context.filesDir, "tesseract/tessdata").apply { mkdirs() }
        val names = context.assets.list("tessdata").orEmpty().filter { it.endsWith(".traineddata") }
        for (name in names) {
            val dest = File(tessdataDir, name)
            if (!dest.exists() || dest.length() == 0L) {
                context.assets.open("tessdata/$name").use { input ->
                    dest.outputStream().use { output -> input.copyTo(output) }
                }
            }
        }
        return tessdataDir.parentFile!!
    }

    suspend fun recognize(context: Context, bitmap: Bitmap, arabic: Boolean = false): OcrPageResult = withContext(Dispatchers.Default) {
        val dataDir = ensureTrainedData(context)
        val lang = if (arabic && hasArabicModel(context)) LANG_ARABIC else LANG_LATIN
        val api = TessBaseAPI()
        try {
            check(api.init(dataDir.absolutePath, lang)) { "Tesseract init failed for $lang" }
            api.setImage(bitmap)
            api.getUTF8Text() // triggers recognition; result consumed via the iterator below

            val w = bitmap.width.toFloat().coerceAtLeast(1f)
            val h = bitmap.height.toFloat().coerceAtLeast(1f)
            val words = buildList {
                val it = api.resultIterator
                try {
                    it.begin()
                    while (!it.isAtBeginningOf(PageIteratorLevel.RIL_WORD)) {
                        if (!it.next(PageIteratorLevel.RIL_WORD)) return@buildList
                    }
                    do {
                        if (it.isAtBeginningOf(PageIteratorLevel.RIL_WORD)) {
                            val word = it.getUTF8Text(PageIteratorLevel.RIL_WORD)?.trim().orEmpty()
                            val box = it.getBoundingRect(PageIteratorLevel.RIL_WORD)
                            if (word.isNotEmpty() && box != null) {
                                add(
                                    OcrWord(
                                        text = word,
                                        left = (box.left / w).coerceIn(0f, 1f),
                                        top = (box.top / h).coerceIn(0f, 1f),
                                        right = (box.right / w).coerceIn(0f, 1f),
                                        bottom = (box.bottom / h).coerceIn(0f, 1f)
                                    )
                                )
                            }
                        }
                    } while (it.next(PageIteratorLevel.RIL_WORD))
                } finally {
                    it.delete()
                }
            }
            OcrPageResult(words, engineUsed = if (lang == LANG_ARABIC) "tesseract-ara" else "tesseract")
        } finally {
            api.recycle()
        }
    }
}
