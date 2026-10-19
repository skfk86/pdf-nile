# PDF Nile privacy notes / سياسة الخصوصية

*Draft for the current build, without ads. It must be updated when AdMob is added.*

PDF Nile is an Android document app. It has no account system. Document operations, including OCR, run on your device. PDF Nile does not run a server for uploading your files or extracted text.

## ما يعالجه التطبيق (بالعربية)
- ملفاتك تُفتح وتُحفظ عبر منتقي الملفات في أندرويد، ولا تُرفع إلى خادم تابع لنا.
- التعرّف على النصوص (OCR) يتم على جهازك باستخدام نماذج مضمّنة في التطبيق.
- الكاميرا تُستخدم لمسح المستندات فقط، عبر مكوّن المسح في خدمات Google Play.
- قائمة الملفات الأخيرة تُحفظ محلياً ويمكنك حذفها.

## English
- **Files:** opened and saved through Android's document picker. Recent-file entries stay on the device.
- **OCR:** Arabic and English recognition use Tesseract models bundled in the app; Latin text may use bundled ML Kit. Nothing is uploaded.
- **Camera:** used only for document scanning, provided by Google Play services (ML Kit Document Scanner). Google manages those components; they may download models through Google Play services.
- **Analytics and crash reporting:** the app code contains none.
- **Permissions:** camera (scanning) and legacy storage access on old Android versions. Network permissions may be added by bundled libraries.

## Open source
PDF Nile is based on ClearPDF (MIT License), https://github.com/Chethan616/ClearPDF. See `LICENSE` and `THIRD_PARTY_NOTICES.md`.

## Contact
Developer: علوش (Alloush). Contact details to be added before publishing.
