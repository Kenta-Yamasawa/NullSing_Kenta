package com.example.nullsing_kenta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();
        db.close();

        LinearLayout menu_mylist = (LinearLayout) findViewById(R.id.menu_myList_l);
        menu_mylist.setClickable(true);
        menu_mylist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();

                // 引数1：自身のActivity、引数2:移動先のActivity名
                Intent intent = new Intent(MainActivity.this, MyListActivity.class);
                // Activityの移動
                startActivity(intent);
            }
        });

        LinearLayout menu_addlist = (LinearLayout) findViewById(R.id.menu_addList_l);
        menu_mylist.setClickable(true);
        menu_addlist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();

                // 引数1：自身のActivity、引数2:移動先のActivity名
                Intent intent = new Intent(MainActivity.this, AddListActivity.class);
                // Activityの移動
                startActivity(intent);
            }
        });

        LinearLayout menu_mathcing = (LinearLayout) findViewById(R.id.menu_matching_l);
        menu_mylist.setClickable(true);
        menu_mathcing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();

                // 引数1：自身のActivity、引数2:移動先のActivity名
                Intent intent = new Intent(MainActivity.this, MatchingTypeSelectActivity.class);
                // Activityの移動
                startActivity(intent);
            }
        });

        TextView menu_addlist2 = (TextView) findViewById(R.id.menu_addList2);
        menu_addlist2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.execSQL("DELETE FROM MySing;");
                } catch (Exception e) {
                }
                db.close();

                // 引数1：自身のActivity、引数2:移動先のActivity名
                Intent intent = new Intent(MainActivity.this, AddListActivity.class);
                // Activityの移動
                startActivity(intent);
            }
        });

        TextView menu_mathcing2 = (TextView) findViewById(R.id.menu_matching2);
        menu_mathcing2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();

                // 引数1：自身のActivity、引数2:移動先のActivity名
                Intent intent = new Intent(MainActivity.this, MatchingTypeSelectActivity.class);
                // Activityの移動
                startActivity(intent);
            }
        });
    }
}