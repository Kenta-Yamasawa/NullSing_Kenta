package com.example.nullsing_kenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MatchingTypeSelectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_type_select);

        TextView button_matching_type_title = (TextView) findViewById(R.id.button_matching_type_title);
        button_matching_type_title.setOnClickListener(new MatchingTypeSelectActivity.ButtonMatchingTypeTitleOnClickListener());

        TextView button_matching_type_artist = (TextView) findViewById(R.id.button_matching_type_artist);
        button_matching_type_artist.setOnClickListener(new MatchingTypeSelectActivity.ButtonMatchingTypeArtistOnClickListener());

        TextView button_matching_type_genre = (TextView) findViewById(R.id.button_matching_type_genre);
        button_matching_type_genre.setOnClickListener(new MatchingTypeSelectActivity.ButtonMatchingTypeGenreOnClickListener());

    }

    private class ButtonMatchingTypeTitleOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            // Intent intent = new Intent(MatchingTypeSelectActivity.this, MathcingActivity.class);
            Intent intent = new Intent(MatchingTypeSelectActivity.this, ResultActivity.class);
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
            Intent intent = new Intent(MatchingTypeSelectActivity.this, ResultActivity.class);
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
            Intent intent = new Intent(MatchingTypeSelectActivity.this, ResultActivity.class);
            intent.putExtra("matchingType","genre");
            // Activityの移動
            startActivity(intent);
        }
    }

}
