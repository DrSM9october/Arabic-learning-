package com.yourapp.arabiclearning.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yourapp.arabiclearning.DailyMemorizationActivity;
import com.yourapp.arabiclearning.R;
import com.yourapp.arabiclearning.models.DailyPhrase;
import com.yourapp.arabiclearning.models.Phrase;  // ← این خط رو اضافه کن
import com.yourapp.arabiclearning.utils.TTSManager;
import java.util.ArrayList;
import java.util.List;

public class DailyPhraseAdapter extends RecyclerView.Adapter<DailyPhraseAdapter.ViewHolder> {

    private List<DailyPhrase> phraseList;
    private TTSManager ttsManager;
    private DailyMemorizationActivity activity;

    public DailyPhraseAdapter(List<DailyPhrase> phrases, TTSManager tts, DailyMemorizationActivity activity) {
        this.phraseList = phrases != null ? phrases : new ArrayList<>();
        this.ttsManager = tts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_phrase, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyPhrase dailyPhrase = phraseList.get(position);
        Phrase phrase = dailyPhrase.getPhrase();

        // نمایش جمله
        holder.txtArabic.setText(phrase.arabicText);
        holder.txtPersian.setText(phrase.persianText);
        holder.txtPhonetic.setText(phrase.phonetic);
        holder.txtCategory.setText(phrase.category);

        // وضعیت یادگیری
        if (dailyPhrase.isLearned()) {
            holder.btnStatus.setBackgroundColor(holder.itemView.getContext()
                    .getResources().getColor(android.R.color.holo_green_light));
            holder.btnStatus.setText("✅ یاد گرفتم");
        } else {
            holder.btnStatus.setBackgroundColor(holder.itemView.getContext()
                    .getResources().getColor(android.R.color.holo_orange_light));
            holder.btnStatus.setText("📖 در حال یادگیری");
        }

        // دکمه تلفظ
        holder.btnSpeak.setOnClickListener(v -> {
            if (ttsManager != null) {
                ttsManager.speak(phrase.arabicText, phrase.dialect);
            }
        });

        // دکمه وضعیت یادگیری
        holder.btnStatus.setOnClickListener(v -> {
            if (dailyPhrase.isLearned()) {
                dailyPhrase.setLearned(false);
                dailyPhrase.setNeedsReview(true);
                Toast.makeText(v.getContext(), "🔄 نیاز به تکرار دارید!", Toast.LENGTH_SHORT).show();
            } else {
                dailyPhrase.setLearned(true);
                dailyPhrase.setNeedsReview(false);
                dailyPhrase.incrementReviewCount();
                Toast.makeText(v.getContext(), "🎉 عالی! جمله را یاد گرفتی!", Toast.LENGTH_SHORT).show();
            }
            notifyItemChanged(position);
            activity.onPhraseStatusChanged();
        });

        // دکمه نیاز به تکرار
        holder.btnReview.setOnClickListener(v -> {
            dailyPhrase.setNeedsReview(!dailyPhrase.isNeedsReview());
            if (dailyPhrase.isNeedsReview()) {
                Toast.makeText(v.getContext(), "🔄 این جمله را دوباره مرور کن!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "✅ نیاز به تکرار لغو شد!", Toast.LENGTH_SHORT).show();
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return phraseList.size();
    }

    public void updateData(List<DailyPhrase> newList) {
        this.phraseList = newList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtArabic, txtPersian, txtPhonetic, txtCategory;
        Button btnSpeak, btnStatus, btnReview;

        ViewHolder(View itemView) {
            super(itemView);
            txtArabic = itemView.findViewById(R.id.txtDailyArabic);
            txtPersian = itemView.findViewById(R.id.txtDailyPersian);
            txtPhonetic = itemView.findViewById(R.id.txtDailyPhonetic);
            txtCategory = itemView.findViewById(R.id.txtDailyCategory);
            btnSpeak = itemView.findViewById(R.id.btnDailySpeak);
            btnStatus = itemView.findViewById(R.id.btnDailyStatus);
            btnReview = itemView.findViewById(R.id.btnDailyReview);
        }
    }
}
