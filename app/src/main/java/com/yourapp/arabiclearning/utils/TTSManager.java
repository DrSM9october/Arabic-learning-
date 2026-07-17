package com.yourapp.arabiclearning.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import java.util.HashMap;
import java.util.Locale;

public class TTSManager {
    private TextToSpeech tts;
    private boolean isReady = false;
    private float speechRate = 0.85f;  // سرعت کمتر = روان‌تر
    private float pitch = 1.0f;

    public TTSManager(Context context) {
        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                // تنظیم زبان عربی
                int result = tts.setLanguage(new Locale("ar"));
                if (result == TextToSpeech.LANG_MISSING_DATA || 
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts.setLanguage(Locale.US);
                }
                // تنظیمات برای تلفظ روان‌تر
                tts.setSpeechRate(speechRate);  // کاهش سرعت برای روانی بیشتر
                tts.setPitch(pitch);
                isReady = true;
                Log.d("TTSManager", "TTS initialized successfully");
            } else {
                Log.e("TTSManager", "TTS initialization failed");
            }
        });
    }

    public void speak(String text, String dialect) {
        if (!isReady) {
            Log.w("TTSManager", "TTS not ready");
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "unique_id");

        // تنظیم زبان بر اساس لهجه
        if ("american".equals(dialect)) {
            tts.setLanguage(Locale.US);
            tts.setSpeechRate(0.9f);  // انگلیسی با سرعت کمی بیشتر
        } else {
            tts.setLanguage(new Locale("ar"));
            tts.setSpeechRate(0.85f);  // عربی با سرعت کم‌تر برای روانی
        }
        
        tts.setPitch(1.0f);

        // اضافه کردن کمی تاخیر قبل از تلفظ برای جلوگیری از قطع شدن
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params);
    }

    // متد جدید برای پخش با سرعت قابل تنظیم
    public void speakWithSpeed(String text, String dialect, float speed) {
        if (!isReady) return;
        
        if ("american".equals(dialect)) {
            tts.setLanguage(Locale.US);
        } else {
            tts.setLanguage(new Locale("ar"));
        }
        
        tts.setSpeechRate(speed);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        // بازگشت به سرعت پیش‌فرض
        tts.setSpeechRate(speechRate);
    }

    // متد جدید برای تکرار خودکار
    public void speakWithRepeat(String text, String dialect, int repeatCount) {
        if (!isReady) return;
        
        for (int i = 0; i < repeatCount; i++) {
            speak(text, dialect);
            try {
                Thread.sleep(500);  // نیم ثانیه بین تکرارها
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    // تنظیم سرعت
    public void setSpeechRate(float rate) {
        this.speechRate = rate;
        if (isReady) {
            tts.setSpeechRate(rate);
        }
    }
}
