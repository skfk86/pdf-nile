# PDF Nile — بي دي إف نيل

أدوات PDF والمستندات لأندرويد، بواجهة عربية أولاً، وقراءة نصوص عربية (OCR) تعمل على الجهاز.

Arabic-first PDF and document tools for Android, with on-device Arabic OCR.

## الميزات / Features
- قراءة PDF وتحريره: تظليل، ملاحظات، توقيع، تعبئة نماذج.
- دمج وتقسيم وضغط وتشفير وفك تشفير (AES-256).
- OCR عربي وإنجليزي على الجهاز، وتحويل المستند الممسوح إلى PDF قابل للبحث.
- مسح المستندات ضوئياً (عبر خدمات Google Play) وأدوات الصور.
- يعمل بدون حساب، ولا يرفع ملفاتك إلى خادم.

## البناء / Build
بناء النكهة `play` عبر GitHub Actions (`.github/workflows/android.yml`): JDK 17، وSDK 36.

```
./gradlew :app:assemblePlayRelease   # APKs
./gradlew :app:bundlePlayRelease     # AAB
```

## الأصل والترخيص / Origin and license
مبني على [ClearPDF](https://github.com/Chethan616/ClearPDF) من Chethan616 بترخيص MIT. نص الترخيص الأصلي في `LICENSE`، وإشعارات المكتبات والنموذج العربي والخط في `THIRD_PARTY_NOTICES.md`. README الأصلي محفوظ في `docs/UPSTREAM_README.md`.

Based on ClearPDF by Chethan616 (MIT). See `LICENSE` and `THIRD_PARTY_NOTICES.md`.
