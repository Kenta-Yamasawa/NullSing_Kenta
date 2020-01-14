package com.example.nullsing_kenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MatchingTypeSelectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_type_select);

        LinearLayout button_matching_type_title = (LinearLayout) findViewById(R.id.button_matching_type_title);
        button_matching_type_title.setOnClickListener(new MatchingTypeSelectActivity.ButtonMatchingTypeTitleOnClickListener());

        LinearLayout button_matching_type_artist = (LinearLayout) findViewById(R.id.button_matching_type_artist);
        button_matching_type_artist.setOnClickListener(new MatchingTypeSelectActivity.ButtonMatchingTypeArtistOnClickListener());

        LinearLayout button_matching_type_genre = (LinearLayout) findViewById(R.id.button_matching_type_genre);
        button_matching_type_genre.setOnClickListener(new MatchingTypeSelectActivity.ButtonMatchingTypeGenreOnClickListener());

        LinearLayout menu_home = (LinearLayout) findViewById(R.id.menu_home_l);
        menu_home.setClickable(true);
        menu_home.setOnClickListener(new MatchingTypeSelectActivity.MenuHomeOnClickListener());

        LinearLayout menu_mylist = (LinearLayout) findViewById(R.id.menu_myList_l);
        menu_mylist.setClickable(true);
        menu_mylist.setOnClickListener(new MatchingTypeSelectActivity.MenuMyListOnClickListener());

        LinearLayout menu_addlist = (LinearLayout)findViewById(R.id.menu_addList_l);
        menu_addlist.setClickable(true);
        menu_addlist.setOnClickListener(new MatchingTypeSelectActivity.MenuAddListOnClickListener());

        LinearLayout menu_matching = (LinearLayout) findViewById(R.id.menu_matching_l);
        menu_matching.setClickable(true);
        menu_matching.setOnClickListener(new MatchingTypeSelectActivity.MenuMathcingOnClickListener());

    }

    private class ButtonMatchingTypeTitleOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            // Intent intent = new Intent(MatchingTypeSelectActivity.this, MathcingActivity.class);
            Intent intent = new Intent(MatchingTypeSelectActivity.this, MathcingActivity.class);
            intent.putExtra("matchingType","title");
            // Activityの移動
            startActivity(intent);
        }
    }

    private class ButtonMatchingTypeArtistOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            // Intent intent = new Intent(MatchingTypeSelectActivity.this, MathcingActivity.class);
            Intent intent = new Intent(MatchingTypeSelectActivity.this, MathcingActivity.class);
            intent.putExtra("matchingType","artist");
            // Activityの移動
            startActivity(intent);
        }
    }

    private class ButtonMatchingTypeGenreOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            // Intent intent = new Intent(MatchingTypeSelectActivity.this, MathcingActivity.class);
            Intent intent = new Intent(MatchingTypeSelectActivity.this, MathcingActivity.class);
            intent.putExtra("matchingType","genre");
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuHomeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingTypeSelectActivity.this, MainActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuMyListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingTypeSelectActivity.this, MyListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuAddListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingTypeSelectActivity.this, AddListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuMathcingOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingTypeSelectActivity.this, MathcingActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

}
