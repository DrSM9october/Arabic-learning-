package com.yourapp.arabiclearning.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.HashMap;
import java.util.Locale;

public class TTSManager {
    private TextToSpeech tts;
    private boolean isReady = false;

    public TTSManager(Context context) {
        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(new Locale("ar"));
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts.setLanguage(Locale.US);
                }
                tts.setSpeechRate(0.9f);
                tts.setPitch(1.0f);
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

        if ("american".equals(dialect)) {
            tts.setLanguage(Locale.US);
        } else {
            tts.setLanguage(new Locale("ar"));
        }

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params);
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
}
