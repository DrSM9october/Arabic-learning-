package com.yourapp.arabiclearning;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.yourapp.arabiclearning.database.DatabaseHelper;
import com.yourapp.arabiclearning.models.Phrase;
import com.yourapp.arabiclearning.utils.TTSManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

public class EmergencyActivity extends AppCompatActivity {

    private EditText edtInput;
    private TextView txtResult, txtStatus;
    private Button btnTranslate, btnSave, btnSpeakResult;
    private ProgressBar progressBar;
    private Translator translator;
    private DatabaseHelper dbHelper;
    private TTSManager ttsManager;
    private String lastTranslatedText = "";
    private String lastOriginalText = "";
    private boolean isModelDownloaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        dbHelper = new DatabaseHelper(this);
        ttsManager = new TTSManager(this);

        initViews();
        setupButtons();
        checkInternetAndModel();
    }

    private void initViews() {
        edtInput = findViewById(R.id.edtEmergencyInput);
        txtResult = findViewById(R.id.txtEmergencyResult);
        txtStatus = findViewById(R.id.txtEmergencyStatus);
        btnTranslate = findViewById(R.id.btnTranslateNow);
        btnSave = findViewById(R.id.btnSaveEmergency);
        btnSpeakResult = findViewById(R.id.btnSpeakResult);
        progressBar = findViewById(R.id.progressBarEmergency);
    }

    private void setupButtons() {
        btnTranslate.setOnClickListener(v -> {
            if (hasInternet()) {
                translateText();
            } else {
                Toast.makeText(this, "⚠️ برای ترجمه به اینترنت نیاز است!", Toast.LENGTH_LONG).show();
            }
        });

        btnSave.setOnClickListener(v -> saveTranslation());
        
        btnSpeakResult.setOnClickListener(v -> {
            if (!lastTranslatedText.isEmpty()) {
                ttsManager.speak(lastTranslatedText, "lebanese");
            }
        });
    }

    private void checkInternetAndModel() {
        if (!hasInternet()) {
            txtStatus.setText("⚠️ برای ترجمه به اینترنت نیاز است!");
            txtStatus.setVisibility(TextView.VISIBLE);
            btnTranslate.setEnabled(false);
        } else {
            txtStatus.setText("✅ آماده ترجمه (نیاز به اینترنت یکبار)");
            txtStatus.setVisibility(TextView.VISIBLE);
            btnTranslate.setEnabled(true);
        }
    }

    private boolean hasInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void translateText() {
        String input = edtInput.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(this, "لطفاً جمله را وارد کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        lastOriginalText = input;
        btnTranslate.setEnabled(false);
        btnTranslate.setText("⏳ در حال ترجمه...");
        progressBar.setVisibility(ProgressBar.VISIBLE);
        txtResult.setVisibility(TextView.GONE);
        btnSave.setVisibility(Button.GONE);
        btnSpeakResult.setVisibility(Button.GONE);
        txtStatus.setText("📥 در حال دانلود مدل ترجمه...");

        // ساخت Translator (عربی به فارسی)
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ARABIC)
                .setTargetLanguage(TranslateLanguage.PERSIAN)
                .build();

        translator = Translation.getClient(options);

        // دانلود مدل (فقط یک بار)
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()  // فقط با وای‌فای دانلود کن
                .build();

        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(unused -> {
                    txtStatus.setText("✅ مدل دانلود شد! در حال ترجمه...");
                    isModelDownloaded = true;
                    performTranslation(input);
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(ProgressBar.GONE);
                    btnTranslate.setEnabled(true);
                    btnTranslate.setText("🔍 ترجمه کن");
                    txtStatus.setText("❌ خطا: " + e.getMessage());
                    Toast.makeText(EmergencyActivity.this, 
                        "❌ خطا در دانلود مدل: " + e.getMessage(), 
                        Toast.LENGTH_LONG).show();
                });
    }

    private void performTranslation(String text) {
        translator.translate(text)
                .addOnSuccessListener(translated -> {
                    lastTranslatedText = translated;
                    txtResult.setText("📝 " + translated);
                    txtResult.setVisibility(TextView.VISIBLE);
                    btnSave.setVisibility(Button.VISIBLE);
                    btnSpeakResult.setVisibility(Button.VISIBLE);
                    progressBar.setVisibility(ProgressBar.GONE);
                    btnTranslate.setEnabled(true);
                    btnTranslate.setText("🔍 ترجمه کن");
                    txtStatus.setText("✅ ترجمه انجام شد!");
                    
                    // تلفظ خودکار
                    ttsManager.speak(translated, "lebanese");
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(ProgressBar.GONE);
                    btnTranslate.setEnabled(true);
                    btnTranslate.setText("🔍 ترجمه کن");
                    txtStatus.setText("❌ خطا: " + e.getMessage());
                    Toast.makeText(EmergencyActivity.this, 
                        "❌ خطا در ترجمه: " + e.getMessage(), 
                        Toast.LENGTH_LONG).show();
                });
    }

    private void saveTranslation() {
        if (lastTranslatedText.isEmpty() || lastOriginalText.isEmpty()) {
            Toast.makeText(this, "ابتدا جمله را ترجمه کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        Phrase phrase = new Phrase();
        phrase.arabicText = lastOriginalText;
        phrase.persianText = lastTranslatedText;
        phrase.phonetic = "";
        phrase.dialect = "lebanese";
        phrase.gender = "neutral";
        phrase.category = "emergency";
        phrase.difficulty = "beginner";
        phrase.isFavorite = true;

        long id = dbHelper.insertPhrase(phrase);
        if (id != -1) {
            Toast.makeText(this, "✅ جمله ذخیره شد!", Toast.LENGTH_SHORT).show();
            btnSave.setVisibility(Button.GONE);
            txtResult.setText("");
            edtInput.setText("");
            lastTranslatedText = "";
            lastOriginalText = "";
            txtStatus.setText("✅ ذخیره شد! می‌توانید جمله جدیدی وارد کنید.");
        } else {
            Toast.makeText(this, "❌ خطا در ذخیره سازی", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ttsManager != null) ttsManager.destroy();
        if (translator != null) translator.close();
    }
}
