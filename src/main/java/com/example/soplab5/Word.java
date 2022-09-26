package com.example.soplab5;

import java.util.ArrayList;

public class Word {
    public ArrayList<String> badWords, goodWords;

    public Word() {
        this.badWords = new ArrayList<>();
        this.badWords.add("fuck");
        this.badWords.add("olo");
        this.goodWords = new ArrayList<>();
        this.goodWords.add("happy");
        this.goodWords.add("enjoy");
        this.goodWords.add("life");
    }
}
