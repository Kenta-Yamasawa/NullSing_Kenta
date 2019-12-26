package com.example.nullsing_kenta;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class JSONsong {
    private int id;
    private String title;
    private String artist;
    private String genre;

    public void set_id(int val) {
        id = val;
    }

    public void set_title(String val) {
        title = val;
    }

    public void set_artist(String val) {
        artist = val;
    }

    public void set_genre(String val) {
        genre = genre;
    }

    public int get_id() {
        return id;
    }

    public String get_title() {
        return title;
    }

    public String get_artist() {
        return artist;
    }

    public String get_genre() {
        return genre;
    }

    public String getJSON() {
        String json = "{\"id\":" + id + ",\"title\":\"" + title + "\",\"artist\":\"" + artist + "\",\"genre\":\"" + genre + "\"}";
        Log.d("テスト", json);

        return json;
    }
}