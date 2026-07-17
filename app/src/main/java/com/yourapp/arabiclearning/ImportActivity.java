package com.yourapp.arabiclearning;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.opencsv.CSVReader;
import com.yourapp.arabiclearning.database.DatabaseHelper;
import com.yourapp.arabiclearning.models.Phrase;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImportActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1001;
    private DatabaseHelper dbHelper;
    private TextView txtStatus;
    private Button btnSelectFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        dbHelper = new DatabaseHelper(this);
        txtStatus = findViewById(R.id.txtImportStatus);
        btnSelectFile = findViewById(R.id.btnSelectCSV);

        btnSelectFile.setOnClickListener(v -> selectCSVFile());
    }

    // انتخاب فایل از حافظه
    private void selectCSVFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/csv");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            if (fileUri != null) {
                importCSV(fileUri);
            }
        }
    }

    // خواندن و ذخیره CSV
    private void importCSV(Uri fileUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            List<String[]> rows = reader.readAll();
            reader.close();

            int successCount = 0;
            for (String[] row : rows) {
                if (row.length >= 6) {
                    Phrase phrase = new Phrase();
                    phrase.arabicText = row[0].trim();
                    phrase.persianText = row[1].trim();
                    phrase.phonetic = row[2].trim();
                    phrase.dialect = row[3].trim().toLowerCase();
                    phrase.gender = row[4].trim().toLowerCase();
                    phrase.category = row[5].trim();
                    phrase.difficulty = (row.length > 6) ? row[6].trim() : "beginner";
                    phrase.isFavorite = false;

                    long id = dbHelper.insertPhrase(phrase);
                    if (id != -1) successCount++;
                }
            }

            txtStatus.setText("✅ " + successCount + " جمله با موفقیت وارد شد!");
            Toast.makeText(this, "✅ " + successCount + " جمله اضافه شد", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            txtStatus.setText("❌ خطا: " + e.getMessage());
            Toast.makeText(this, "خطا در واردات: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
