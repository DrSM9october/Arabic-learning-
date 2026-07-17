package com.yourapp.arabiclearning.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.HashMap;
import java.util.Locale;

public class TTSManager {
    private TextToSpeech tts;
    private boolean isReady = false;
    private float speechRate = 0.85f;
    private Context context;

    // تعداد و فاصله تکرار پیش‌فرض
    private int defaultRepeatCount = 2;
    private int repeatDelay = 800; // میلی‌ثانیه (۰.۸ ثانیه)

    public TTSManager(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(new Locale("ar"));
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts.setLanguage(Locale.US);
                }
                tts.setSpeechRate(speechRate);
                tts.setPitch(1.0f);
                isReady = true;
                Log.d("TTSManager", "TTS ready");
            }
        });
    }

    // ===== تلفظ یک بار (همون دکمه 🔊) =====
    public void speak(String text, String dialect) {
        if (!isReady) return;

        HashMap<String, String> params = new HashMap<>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "unique_id");

        if ("american".equals(dialect)) {
            tts.setLanguage(Locale.US);
            tts.setSpeechRate(0.9f);
        } else {
            tts.setLanguage(new Locale("ar"));
            tts.setSpeechRate(0.85f);
        }
        tts.setPitch(1.0f);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params);
    }

    // ===== تلفظ با تکرار (دکمه 🔁) =====
    public void speakWithRepeat(String text, String dialect) {
        if (!isReady) return;

        // اجرای تکرار با مکث
        for (int i = 0; i < defaultRepeatCount; i++) {
            speak(text, dialect);
            if (i < defaultRepeatCount - 1) { // بعد از آخرین تکرار مکث نکن
                try {
                    Thread.sleep(repeatDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ===== تنظیم تعداد و فاصله تکرار (برای استفاده در آینده) =====
    public void setRepeatSettings(int count, int delayMs) {
        this.defaultRepeatCount = count;
        this.repeatDelay = delayMs;
    }

    public void stop() {
        if (tts != null) tts.stop();
    }

    public void destroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }

    public boolean isReady() {
        return isReady;
    }
}
