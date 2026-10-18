package com.kyant.ocrcore

import android.content.Context
import java.util.Locale

/**
 * Decides whether OCR should run in Arabic mode (Tesseract `ara+eng`). Follows the app language
 * (Arabic UI => Arabic OCR) unless the user's explicit choice is stored under [PREF_KEY].
 */
object ArabicOcrMode {
    private const val PREFS = "clearpdf_settings"
    const val PREF_KEY = "ocr_arabic_mode"

    fun isEnabled(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        if (prefs.contains(PREF_KEY)) return prefs.getBoolean(PREF_KEY, false)
        return Locale.getDefault().language.equals("ar", ignoreCase = true)
    }

    fun set(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putBoolean(PREF_KEY, enabled).apply()
    }
}
