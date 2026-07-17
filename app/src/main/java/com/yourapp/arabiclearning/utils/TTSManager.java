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

    // تنظیمات تکرار
    private int repeatCount = 2;      // تعداد تکرار
    private int repeatDelay = 800;    // میلی‌ثانیه (۰.۸ ثانیه)

    public TTSManager(Context context) {
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

    // ===== تلفظ یک بار =====
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

    // ===== تلفظ با تکرار (با مکث) =====
    public void speakWithRepeat(String text, String dialect) {
        if (!isReady) return;

        for (int i = 0; i < repeatCount; i++) {
            speak(text, dialect);
            if (i < repeatCount - 1) { // بعد از آخرین تکرار مکث نکن
                try {
                    Thread.sleep(repeatDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ===== تلفظ با تکرار و تعداد دلخواه =====
    public void speakWithRepeat(String text, String dialect, int count) {
        this.repeatCount = count;
        speakWithRepeat(text, dialect);
    }

    // ===== تنظیم تعداد و فاصله تکرار =====
    public void setRepeatSettings(int count, int delayMs) {
        this.repeatCount = count;
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
