package com.example.nullsing_kenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView menu_home = (TextView)findViewById(R.id.menu_home);
        menu_home.setOnClickListener(new ResultActivity.MenuHomeOnClickListener());

        TextView menu_mylist = (TextView)findViewById(R.id.menu_myList);
        menu_mylist.setOnClickListener(new ResultActivity.MenuMyListOnClickListener());

        TextView menu_addlist = (TextView)findViewById(R.id.menu_addList);
        menu_addlist.setOnClickListener(new ResultActivity.MenuAddListOnClickListener());


        TextView menu_mathcing = (TextView)findViewById(R.id.menu_matching);
        menu_mathcing.setOnClickListener(new MenuMatchingOnClickListener());

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
            Intent intent = new Intent(ResultActivity.this, MathcingActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }
}
