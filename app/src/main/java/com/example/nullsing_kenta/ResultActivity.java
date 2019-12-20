package com.example.nullsing_kenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        LinearLayout menu_home = (LinearLayout) findViewById(R.id.menu_home_l);
        menu_home.setClickable(true);
        menu_home.setOnClickListener(new ResultActivity.MenuHomeOnClickListener());

        LinearLayout menu_mylist = (LinearLayout) findViewById(R.id.menu_myList_l);
        menu_mylist.setClickable(true);
        menu_mylist.setOnClickListener(new ResultActivity.MenuMyListOnClickListener());

        LinearLayout menu_addlist = (LinearLayout)findViewById(R.id.menu_addList_l);
        menu_addlist.setClickable(true);
        menu_addlist.setOnClickListener(new ResultActivity.MenuAddListOnClickListener());

        LinearLayout menu_mathcing = (LinearLayout)findViewById(R.id.menu_matching_l);
        menu_mathcing.setClickable(true);
        menu_mathcing.setOnClickListener(new ResultActivity.MenuMatchingOnClickListener());

        TextView text_result_title = (TextView)findViewById(R.id.text_result_title);
        Intent intent = this.getIntent();
        String matchingType = intent.getStringExtra("matchingType");

        TextView text_matched_item_0 = (TextView)findViewById(R.id.text_matched_item_0);
        TextView text_matched_item_1 = (TextView)findViewById(R.id.text_matched_item_1);

        if(matchingType.equals("title")) {
            text_result_title.setText("二人が知っている曲はこちらです");
        } else if (matchingType.equals("artist")){
            text_result_title.setText("二人が知っているアーティストはこちらです");
            text_matched_item_0.setText("米津玄師");
            text_matched_item_1.setText("あいみょん");
        } else if (matchingType.equals("genre")){
            text_result_title.setText("二人が知っているジャンルはこちらです");
        }

    }

    private class MenuHomeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuMyListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(ResultActivity.this, MyListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuAddListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(ResultActivity.this, AddListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuMatchingOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(ResultActivity.this, MatchingTypeSelectActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }
}
