package com.tsdreamdeveloper.readtext.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Timur Seisembayev
 * @since 02.10.2019
 */
public class Book {

    private String text;
    private List<String> textList;

    public Book(String text) {
        this.text = text;
        if (!TextUtils.isEmpty(text)) {
            textList = splitToNChar(text, 500);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

    /**
     * Split text into n number of characters.
     *
     * @param text the text to be split.
     * @param size the split size.
     * @return an array of the split text.
     */
    private static List<String> splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts;
    }
}
