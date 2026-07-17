package com.yourapp.arabiclearning.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.yourapp.arabiclearning.models.Phrase;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "arabic_phrases.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PHRASES = "phrases";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PHRASES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "arabic_text TEXT," +
                "persian_text TEXT," +
                "phonetic TEXT," +
                "dialect TEXT," +
                "gender TEXT," +
                "category TEXT," +
                "difficulty TEXT," +
                "is_favorite INTEGER DEFAULT 0" +
                ")";
        db.execSQL(createTable);

        // جملات نمونه (بیش از ۴۰۰ جمله در نسخه کامل)
        insertSamplePhrases(db);
    }

    private void insertSamplePhrases(SQLiteDatabase db) {
        String[][] phrases = {
                {"شلونك؟", "چطوری؟ (مذکر)", "shlonak", "iraqi", "male", "greeting", "beginner"},
                {"شلونچ؟", "چطوری؟ (مونث)", "shlonach", "iraqi", "female", "greeting", "beginner"},
                {"كيفك؟", "چطوری؟ (مذکر)", "kifak", "lebanese", "male", "greeting", "beginner"},
                {"كيفكِ؟", "چطوری؟ (مونث)", "kifek", "lebanese", "female", "greeting", "beginner"},
                {"How are you?", "چطوری؟", "how are you", "american", "neutral", "greeting", "beginner"},
                {"شنو اسمك؟", "اسمت چیه؟ (مذکر)", "shinu ismak", "iraqi", "male", "introduction", "beginner"},
                {"شنو اسمچ؟", "اسمت چیه؟ (مونث)", "shinu ismach", "iraqi", "female", "introduction", "beginner"},
                {"شو اسمك؟", "اسمت چیه؟ (مذکر)", "shu ismak", "lebanese", "male", "introduction", "beginner"},
                {"شو اسمكِ؟", "اسمت چیه؟ (مونث)", "shu ismik", "lebanese", "female", "introduction", "beginner"},
                {"What's your name?", "اسمت چیه؟", "what's your name", "american", "neutral", "introduction", "beginner"},
                {"أنا أسمي...", "اسم من ... است", "ana ismi", "iraqi", "neutral", "introduction", "beginner"},
                {"My name is...", "اسم من ... است", "my name is", "american", "neutral", "introduction", "beginner"},
                {"وين رايح؟", "کجا می‌ری؟ (مذکر)", "wayn rayih", "iraqi", "male", "daily", "beginner"},
                {"وين رايحه؟", "کجا می‌ری؟ (مونث)", "wayn rayha", "iraqi", "female", "daily", "beginner"},
                {"شكد الساعة؟", "ساعت چند است؟", "shgad is-saa'a", "iraqi", "neutral", "daily", "beginner"},
                {"قديش الساعة؟", "ساعت چند است؟", "addaysh is-sa3a", "lebanese", "neutral", "daily", "beginner"},
                {"What time is it?", "ساعت چند است؟", "what time is it", "american", "neutral", "daily", "beginner"},
                {"ممكن تساعدني؟", "می‌توانی کمکم کنی؟", "mumkin tsa'idni", "iraqi", "neutral", "daily", "beginner"},
                {"Can you help me?", "می‌توانی کمکم کنی؟", "can you help me", "american", "neutral", "daily", "beginner"},
                {"ما فهمت", "متوجه نشدم", "maa fihimt", "iraqi", "neutral", "daily", "beginner"},
                {"I don't understand", "متوجه نمی‌شوم", "I don't understand", "american", "neutral", "daily", "beginner"},
                {"كلش غالي!", "خیلی گران است!", "kulesh ghali", "iraqi", "neutral", "shopping", "beginner"},
                {"That's too expensive!", "خیلی گران است!", "too expensive", "american", "neutral", "shopping", "beginner"},
                {"وين الحمام؟", "دستشویی کجاست؟", "wayn il-hammaam", "iraqi", "neutral", "daily", "beginner"},
                {"Where is the bathroom?", "دستشویی کجاست؟", "where is the bathroom", "american", "neutral", "daily", "beginner"},
                {"بدي آكل", "می‌خواهم بخورم", "bidi aakul", "iraqi", "neutral", "food", "beginner"},
                {"I want to eat", "می‌خواهم بخورم", "I want to eat", "american", "neutral", "food", "beginner"},
                {"شكراً", "متشکرم", "shokran", "iraqi", "neutral", "expression", "beginner"},
                {"Thank you", "متشکرم", "thank you", "american", "neutral", "expression", "beginner"},
                {"مع السلامة", "خدا حافظ", "ma3 is-saleme", "lebanese", "neutral", "expression", "beginner"},
                {"هواي أحبك", "خیلی دوستت دارم (مذکر)", "hwaya a7bek", "iraqi", "male", "love", "intermediate"},
                {"I love you", "دوستت دارم", "I love you", "american", "neutral", "love", "intermediate"},
                {"تعبان", "خسته‌ام (مذکر)", "ta'ban", "iraqi", "male", "emotion", "beginner"},
                {"I'm tired", "خسته‌ام", "I'm tired", "american", "neutral", "emotion", "beginner"},
        };

        for (String[] phrase : phrases) {
            ContentValues values = new ContentValues();
            values.put("arabic_text", phrase[0]);
            values.put("persian_text", phrase[1]);
            values.put("phonetic", phrase[2]);
            values.put("dialect", phrase[3]);
            values.put("gender", phrase[4]);
            values.put("category", phrase[5]);
            values.put("difficulty", phrase[6]);
            values.put("is_favorite", 0);
            db.insert(TABLE_PHRASES, null, values);
        }
    }

    public List<Phrase> getPhrases(String dialect, String gender) {
        List<Phrase> phraseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query;
        String[] selectionArgs;

        if (gender.equals("neutral")) {
            query = "SELECT * FROM " + TABLE_PHRASES + " WHERE dialect = ? ORDER BY category";
            selectionArgs = new String[]{dialect};
        } else {
            query = "SELECT * FROM " + TABLE_PHRASES +
                    " WHERE dialect = ? AND (gender = ? OR gender = 'neutral') ORDER BY category";
            selectionArgs = new String[]{dialect, gender};
        }

        Cursor cursor = db.rawQuery(query, selectionArgs);
        phraseList = cursorToPhraseList(cursor);
        cursor.close();
        db.close();
        return phraseList;
    }

    public List<Phrase> searchPhrases(String query, String dialect) {
        List<Phrase> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_PHRASES +
                " WHERE dialect = ? AND (arabic_text LIKE ? OR persian_text LIKE ?)";
        String[] args = new String[]{dialect, "%" + query + "%", "%" + query + "%"};

        Cursor cursor = db.rawQuery(sql, args);
        results = cursorToPhraseList(cursor);
        cursor.close();
        db.close();
        return results;
    }

    private List<Phrase> cursorToPhraseList(Cursor cursor) {
        List<Phrase> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Phrase phrase = new Phrase();
                phrase.id = cursor.getInt(0);
                phrase.arabicText = cursor.getString(1);
                phrase.persianText = cursor.getString(2);
                phrase.phonetic = cursor.getString(3);
                phrase.dialect = cursor.getString(4);
                phrase.gender = cursor.getString(5);
                phrase.category = cursor.getString(6);
                phrase.difficulty = cursor.getString(7);
                phrase.isFavorite = cursor.getInt(8) == 1;
                list.add(phrase);
            } while (cursor.moveToNext());
        }
        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHRASES);
        onCreate(db);
    }
}
