package com.yourapp.arabiclearning;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yourapp.arabiclearning.adapters.PhraseAdapter;
import com.yourapp.arabiclearning.database.DatabaseHelper;
import com.yourapp.arabiclearning.models.Phrase;
import com.yourapp.arabiclearning.utils.TTSManager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerDialect, spinnerGender;
    private RecyclerView recyclerView;
    private PhraseAdapter adapter;
    private List<Phrase> phraseList = new ArrayList<>();
    private DatabaseHelper dbHelper;
    private TTSManager ttsManager;
    private EditText searchBox;
    private TextView txtCount;
    private String currentDialect = "iraqi";
    private String currentGender = "neutral";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        ttsManager = new TTSManager(this);

        initViews();
        setupSpinners();
        setupSearch();
        setupButtons();
        loadPhrases();
    }

    private void initViews() {
        spinnerDialect = findViewById(R.id.spinnerDialect);
        spinnerGender = findViewById(R.id.spinnerGender);
        recyclerView = findViewById(R.id.recyclerView);
        searchBox = findViewById(R.id.searchBox);
        txtCount = findViewById(R.id.txtCount);

        adapter = new PhraseAdapter(phraseList, ttsManager, dbHelper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> dialectAdapter = ArrayAdapter.createFromResource(this,
                R.array.dialects_array, android.R.layout.simple_spinner_item);
        dialectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDialect.setAdapter(dialectAdapter);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        spinnerDialect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] dialects = getResources().getStringArray(R.array.dialects_values);
                currentDialect = dialects[position];
                loadPhrases();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] genders = getResources().getStringArray(R.array.gender_values);
                currentGender = genders[position];
                loadPhrases();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSearch() {
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    adapter.filter(s.toString());
                    updateCount();
                } else {
                    loadPhrases();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupButtons() {
        // پخش همه
        findViewById(R.id.btnSpeakAll).setOnClickListener(v -> {
            List<Phrase> phrases = adapter.getFilteredList();
            for (Phrase p : phrases) {
                ttsManager.speak(p.arabicText, p.dialect);
                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            }
        });

        // تصادفی
        findViewById(R.id.btnRandom).setOnClickListener(v -> {
            List<Phrase> phrases = adapter.getFilteredList();
            if (!phrases.isEmpty()) {
                int index = (int) (Math.random() * phrases.size());
                Phrase p = phrases.get(index);
                ttsManager.speak(p.arabicText, p.dialect);
                Toast.makeText(this, p.persianText + " : " + p.arabicText, Toast.LENGTH_SHORT).show();
            }
        });

        // ترجمه اضطراری
        findViewById(R.id.btnEmergency).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, EmergencyActivity.class));
        });

        // واردات
        findViewById(R.id.btnImport).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ImportActivity.class));
        });

        // ===== دکمه جدید: حفظ روزانه =====
        findViewById(R.id.btnDailyMemorization).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DailyMemorizationActivity.class));
        });
    }

    private void loadPhrases() {
        phraseList.clear();
        phraseList.addAll(dbHelper.getPhrases(currentDialect, currentGender));
        adapter.updateData(phraseList);
        updateCount();
    }

    private void updateCount() {
        List<Phrase> list = adapter.getFilteredList();
        txtCount.setText((list != null ? list.size() : 0) + " جمله");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ttsManager != null) {
            ttsManager.destroy();
        }
    }
}
