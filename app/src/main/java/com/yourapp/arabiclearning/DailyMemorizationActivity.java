package com.yourapp.arabiclearning;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yourapp.arabiclearning.adapters.DailyPhraseAdapter;
import com.yourapp.arabiclearning.database.DatabaseHelper;
import com.yourapp.arabiclearning.models.Phrase;
import com.yourapp.arabiclearning.models.DailyPhrase;
import com.yourapp.arabiclearning.utils.TTSManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DailyMemorizationActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TTSManager ttsManager;
    private RecyclerView recyclerView;
    private DailyPhraseAdapter adapter;
    private TextView txtDate, txtProgress, txtStreak;
    private Button btnRefresh;
    private List<DailyPhrase> dailyPhrases = new ArrayList<>();
    private int learnedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_memorization);

        dbHelper = new DatabaseHelper(this);
        ttsManager = new TTSManager(this);

        initViews();
        loadDailyPhrases();
        updateStats();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewDaily);
        txtDate = findViewById(R.id.txtDate);
        txtProgress = findViewById(R.id.txtProgress);
        txtStreak = findViewById(R.id.txtStreak);
        btnRefresh = findViewById(R.id.btnRefreshDaily);

        // تنظیم تاریخ امروز
        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.YEAR) + "/" +
                     (calendar.get(Calendar.MONTH) + 1) + "/" +
                     calendar.get(Calendar.DAY_OF_MONTH);
        txtDate.setText("📅 " + date);

        btnRefresh.setOnClickListener(v -> {
            loadDailyPhrases();
            Toast.makeText(this, "🔄 جملات جدید بارگذاری شد!", Toast.LENGTH_SHORT).show();
        });

        adapter = new DailyPhraseAdapter(dailyPhrases, ttsManager, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadDailyPhrases() {
        dailyPhrases.clear();
        learnedCount = 0;

        // دریافت جملات از دیتابیس (ترجیحاً از لهجه‌ای که کاربر انتخاب کرده)
        List<Phrase> allPhrases = dbHelper.getPhrases("iraqi", "neutral");
        
        // انتخاب ۵ جمله تصادفی
        Random random = new Random();
        int total = allPhrases.size();
        int count = Math.min(5, total);
        
        List<Integer> selectedIndices = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index;
            do {
                index = random.nextInt(total);
            } while (selectedIndices.contains(index));
            selectedIndices.add(index);
            
            Phrase phrase = allPhrases.get(index);
            DailyPhrase dailyPhrase = new DailyPhrase(phrase);
            dailyPhrases.add(dailyPhrase);
        }

        adapter.updateData(dailyPhrases);
        updateStats();
    }

    private void updateStats() {
        int total = dailyPhrases.size();
        int learned = 0;
        for (DailyPhrase dp : dailyPhrases) {
            if (dp.isLearned()) learned++;
        }
        txtProgress.setText("✅ " + learned + "/" + total + " یاد گرفته شد");
        txtStreak.setText("🔥 " + dbHelper.getStreak() + " روز پیاپی");
    }

    // متد برای به‌روزرسانی آمار بعد از هر تغییر
    public void onPhraseStatusChanged() {
        updateStats();
        // ذخیره پیشرفت در دیتابیس
        dbHelper.saveDailyProgress(dailyPhrases);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ttsManager != null) {
            ttsManager.destroy();
        }
    }
}
