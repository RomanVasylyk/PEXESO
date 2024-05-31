package com.example.pexo;

import android.view.View;

public class Card {

    private String word;
    private boolean isMatched;
    private boolean isShowing;
    private View view;

    public Card(String word) {
        this.word = word;
        this.isMatched = false;
        this.isShowing = false;
    }

    public String getWord() {
        return word;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
