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
                Log.d("TTSManager", "TTS initialized successfully");
            } else {
                Log.e("TTSManager", "TTS initialization failed");
            }
        });
    }

    // ===== تلفظ یک بار =====
    public void speak(String text, String dialect) {
        if (!isReady) {
            Log.w("TTSManager", "TTS not ready");
            return;
        }

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

    // ===== تلفظ با تکرار خودکار =====
    public void speakWithRepeat(String text, String dialect, int repeatCount) {
        if (!isReady) return;

        for (int i = 0; i < repeatCount; i++) {
            speak(text, dialect);
            try {
                Thread.sleep(500); // نیم ثانیه بین تکرارها
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // ===== تلفظ با سرعت دلخواه =====
    public void speakWithSpeed(String text, String dialect, float speed) {
        if (!isReady) return;

        if ("american".equals(dialect)) {
            tts.setLanguage(Locale.US);
        } else {
            tts.setLanguage(new Locale("ar"));
        }

        tts.setSpeechRate(speed);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        tts.setSpeechRate(speechRate); // برگشت به سرعت پیش‌فرض
    }

    public void stop() {
        if (tts != null) {
            tts.stop();
        }
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

    public void setSpeechRate(float rate) {
        this.speechRate = rate;
        if (isReady) {
            tts.setSpeechRate(rate);
        }
    }
}
