package com.yourapp.arabiclearning;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.yourapp.arabiclearning.database.DatabaseHelper;
import com.yourapp.arabiclearning.models.Phrase;
import com.yourapp.arabiclearning.utils.TTSManager;

public class EmergencyActivity extends AppCompatActivity {

    private EditText edtInput;
    private TextView txtResult;
    private Button btnTranslate, btnSave;
    private Translator translator;
    private DatabaseHelper dbHelper;
    private TTSManager ttsManager;
    private String lastTranslatedText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        dbHelper = new DatabaseHelper(this);
        ttsManager = new TTSManager(this);

        edtInput = findViewById(R.id.edtEmergencyInput);
        txtResult = findViewById(R.id.txtEmergencyResult);
        btnTranslate = findViewById(R.id.btnTranslateNow);
        btnSave = findViewById(R.id.btnSaveEmergency);

        btnTranslate.setOnClickListener(v -> translateText());
        btnSave.setOnClickListener(v -> saveTranslation());
    }

    private void translateText() {
        String input = edtInput.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(this, "لطفاً جمله را وارد کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        // ساخت Translator برای عربی به فارسی
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ARABIC)
                .setTargetLanguage(TranslateLanguage.PERSIAN)
                .build();

        translator = Translation.getClient(options);

        // دانلود مدل (فقط یک بار)
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()  // برای جلوگیری از مصرف اینترنت
                .build();

        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(unused -> {
                    // ترجمه
                    translator.translate(input)
                            .addOnSuccessListener(translated -> {
                                lastTranslatedText = translated;
                                txtResult.setText("📝 " + translated);
                                txtResult.setVisibility(TextView.VISIBLE);
                                btnSave.setVisibility(Button.VISIBLE);
                                // تلفظ خودکار
                                ttsManager.speak(translated, "lebanese");
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(EmergencyActivity.this, "خطا در ترجمه: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            );
                })
                .addOnFailureListener(e ->
                        Toast.makeText(EmergencyActivity.this, "خطا در دانلود مدل: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    // ذخیره جمله ترجمه شده در دیتابیس
    private void saveTranslation() {
        if (lastTranslatedText.isEmpty()) {
            Toast.makeText(this, "ابتدا جمله را ترجمه کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        Phrase phrase = new Phrase();
        phrase.arabicText = edtInput.getText().toString().trim();
        phrase.persianText = lastTranslatedText;
        phrase.phonetic = "";
        phrase.dialect = "lebanese"; // یا هر لهجه دیگه
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
