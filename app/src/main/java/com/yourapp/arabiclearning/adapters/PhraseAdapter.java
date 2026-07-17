package com.yourapp.arabiclearning.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yourapp.arabiclearning.R;
import com.yourapp.arabiclearning.database.DatabaseHelper;
import com.yourapp.arabiclearning.models.Phrase;
import com.yourapp.arabiclearning.utils.TTSManager;
import java.util.ArrayList;
import java.util.List;

public class PhraseAdapter extends RecyclerView.Adapter<PhraseAdapter.ViewHolder> {
    private List<Phrase> originalList;
    private List<Phrase> filteredList;
    private TTSManager ttsManager;
    private DatabaseHelper dbHelper;

    public PhraseAdapter(List<Phrase> phrases, TTSManager tts, DatabaseHelper db) {
        this.originalList = phrases != null ? phrases : new ArrayList<>();
        this.filteredList = new ArrayList<>(this.originalList);
        this.ttsManager = tts;
        this.dbHelper = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phrase, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position >= filteredList.size()) return;

        Phrase phrase = filteredList.get(position);

        holder.txtArabic.setText(phrase.arabicText);
        holder.txtPersian.setText(phrase.persianText);
        holder.txtPhonetic.setText(phrase.phonetic);
        holder.txtCategory.setText(phrase.category);
        holder.txtDifficulty.setText(phrase.difficulty);

        String genderDisplay = "⚪";
        if ("male".equals(phrase.gender)) genderDisplay = "♂";
        else if ("female".equals(phrase.gender)) genderDisplay = "♀";
        holder.txtGender.setText(genderDisplay);

        holder.btnFavorite.setImageResource(phrase.isFavorite ?
                android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

        // ===== دکمه تلفظ یک بار =====
        holder.btnSpeak.setOnClickListener(v -> {
            if (ttsManager != null) {
                ttsManager.speak(phrase.arabicText, phrase.dialect);
            }
        });

        // ===== دکمه تکرار خودکار (با مکث) =====
        holder.btnRepeat.setOnClickListener(v -> {
            if (ttsManager != null) {
                ttsManager.speakWithRepeat(phrase.arabicText, phrase.dialect);
                Toast.makeText(v.getContext(), "🔁 تکرار ۲ بار", Toast.LENGTH_SHORT).show();
            }
        });

        // ===== دکمه ذخیره =====
        holder.btnFavorite.setOnClickListener(v -> {
            if (dbHelper != null) {
                dbHelper.toggleFavorite(phrase.id);
                phrase.isFavorite = !phrase.isFavorite;
                notifyItemChanged(position);
                Toast.makeText(v.getContext(),
                        phrase.isFavorite ? "⭐ ذخیره شد!" : "از ذخیره خارج شد",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList != null ? filteredList.size() : 0;
    }

    public void filter(String query) {
        if (filteredList == null) return;
        filteredList.clear();

        if (query == null || query.trim().isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            String lower = query.toLowerCase().trim();
            for (Phrase p : originalList) {
                if (p.arabicText.toLowerCase().contains(lower) ||
                        p.persianText.toLowerCase().contains(lower) ||
                        (p.phonetic != null && p.phonetic.toLowerCase().contains(lower))) {
                    filteredList.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateData(List<Phrase> newList) {
        this.originalList = newList != null ? newList : new ArrayList<>();
        this.filteredList = new ArrayList<>(this.originalList);
        notifyDataSetChanged();
    }

    public List<Phrase> getFilteredList() {
        return filteredList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtArabic, txtPersian, txtPhonetic, txtCategory, txtDifficulty, txtGender;
        Button btnSpeak, btnRepeat;
        ImageView btnFavorite;

        ViewHolder(View itemView) {
            super(itemView);
            txtArabic = itemView.findViewById(R.id.txtArabic);
            txtPersian = itemView.findViewById(R.id.txtPersian);
            txtPhonetic = itemView.findViewById(R.id.txtPhonetic);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtDifficulty = itemView.findViewById(R.id.txtDifficulty);
            txtGender = itemView.findViewById(R.id.txtGender);
            btnSpeak = itemView.findViewById(R.id.btnSpeak);
            btnRepeat = itemView.findViewById(R.id.btnRepeat);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }
}
