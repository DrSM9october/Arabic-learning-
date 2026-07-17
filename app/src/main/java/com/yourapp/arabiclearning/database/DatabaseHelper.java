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
    private static final int DATABASE_VERSION = 2;
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
        insertAllPhrases(db);
    }

    private void insertAllPhrases(SQLiteDatabase db) {
        // جملات نمونه (همون ۴۶۰ جمله‌ای که قبلاً دادی)
        String[][] iraqi = {
            {"هَلا", "سلام", "hala", "iraqi", "neutral", "greeting", "beginner"},
            {"شَلونَكَ؟", "چطوری؟ (مذکر)", "shlonak", "iraqi", "male", "greeting", "beginner"},
            // ... بقیه جملات رو اینجا بذار ...
        };
        // و بقیه لهجه‌ها...
        insertPhrases(db, iraqi);
        // insertPhrases(db, lebanese);
        // insertPhrases(db, american);
    }

    private void insertPhrases(SQLiteDatabase db, String[][] phrases) {
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

    // ===== متدهای اصلی =====
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

    public void toggleFavorite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_PHRASES +
                " SET is_favorite = CASE WHEN is_favorite = 0 THEN 1 ELSE 0 END" +
                " WHERE id = ?";
        db.execSQL(sql, new Object[]{id});
        db.close();
    }

    // ===== متد جدید برای ذخیره جمله =====
    public long insertPhrase(Phrase phrase) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("arabic_text", phrase.arabicText);
        values.put("persian_text", phrase.persianText);
        values.put("phonetic", phrase.phonetic);
        values.put("dialect", phrase.dialect);
        values.put("gender", phrase.gender);
        values.put("category", phrase.category);
        values.put("difficulty", phrase.difficulty);
        values.put("is_favorite", phrase.isFavorite ? 1 : 0);
        
        long id = db.insert(TABLE_PHRASES, null, values);
        db.close();
        return id;
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
