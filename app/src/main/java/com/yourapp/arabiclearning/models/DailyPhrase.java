package com.yourapp.arabiclearning.models;

public class DailyPhrase {
    private Phrase phrase;
    private boolean isLearned;
    private boolean needsReview;
    private int reviewCount;

    public DailyPhrase(Phrase phrase) {
        this.phrase = phrase;
        this.isLearned = false;
        this.needsReview = false;
        this.reviewCount = 0;
    }

    // Getters and Setters
    public Phrase getPhrase() {
        return phrase;
    }

    public void setPhrase(Phrase phrase) {
        this.phrase = phrase;
    }

    public boolean isLearned() {
        return isLearned;
    }

    public void setLearned(boolean learned) {
        isLearned = learned;
    }

    public boolean isNeedsReview() {
        return needsReview;
    }

    public void setNeedsReview(boolean needsReview) {
        this.needsReview = needsReview;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void incrementReviewCount() {
        this.reviewCount++;
    }

    public void resetReviewCount() {
        this.reviewCount = 0;
    }
}
