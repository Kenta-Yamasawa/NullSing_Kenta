package com.example.nullsing_kenta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.BaseAdapter;

public class MyListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        TextView menu_home = (TextView)findViewById(R.id.menu_home);
        menu_home.setOnClickListener(new MyListActivity.MenuHomeOnClickListener());

        TextView menu_addlist = (TextView)findViewById(R.id.menu_addList);
        menu_addlist.setOnClickListener(new MyListActivity.MenuAddListOnClickListener());

        TextView menu_mathcing = (TextView)findViewById(R.id.menu_matching);
        menu_mathcing.setOnClickListener(new MyListActivity.MenuMathcingOnClickListener());
    }

    private class MenuHomeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MyListActivity.this, MainActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuAddListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MyListActivity.this, AddListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuMathcingOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MyListActivity.this, MathcingActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }
}
