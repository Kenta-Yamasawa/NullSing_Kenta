package com.example.nullsing_kenta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.BaseAdapter;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView menu_mylist = (TextView)findViewById(R.id.menu_myList);
        menu_mylist.setOnClickListener(new MenuMyListOnClickListener());

        TextView menu_addlist = (TextView)findViewById(R.id.menu_addList);
        menu_addlist.setOnClickListener(new MenuAddListOnClickListener());

        TextView menu_mathcing = (TextView)findViewById(R.id.menu_matching);
        menu_mathcing.setOnClickListener(new MenuMathcingOnClickListener());

        TextView menu_addlist2 = (TextView)findViewById(R.id.menu_addList2);
        menu_addlist2.setOnClickListener(new MenuAddListOnClickListener());

        TextView menu_mathcing2 = (TextView)findViewById(R.id.menu_matching2);
        menu_mathcing2.setOnClickListener(new MenuMathcingOnClickListener());
    }

    private class MenuMyListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MainActivity.this, MyListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuAddListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MainActivity.this, AddListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuMathcingOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MainActivity.this, MathcingActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }
}