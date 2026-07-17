package com.yourapp.arabiclearning.models;

public class Phrase {
    public int id;
    public String arabicText;
    public String persianText;
    public String phonetic;
    public String dialect;
    public String gender;
    public String category;
    public String difficulty;
    public boolean isFavorite;

    public Phrase() {}

    public Phrase(String arabicText, String persianText, String phonetic,
                  String dialect, String gender, String category,
                  String difficulty, boolean isFavorite) {
        this.arabicText = arabicText;
        this.persianText = persianText;
        this.phonetic = phonetic;
        this.dialect = dialect;
        this.gender = gender;
        this.category = category;
        this.difficulty = difficulty;
        this.isFavorite = isFavorite;
    }
}
