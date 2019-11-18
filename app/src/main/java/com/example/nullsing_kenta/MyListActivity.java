package com.example.nullsing_kenta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.BaseAdapter;

public class MyListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        TextView menu_home = (TextView)findViewById(R.id.menu_home);
        menu_home.setOnClickListener(new MenuHomeOnClickListener());

        TextView menu_addlist = (TextView)findViewById(R.id.menu_addList);
        menu_addlist.setOnClickListener(new MyListActivity.MenuAddListOnClickListener());

        TextView menu_mathcing = (TextView)findViewById(R.id.menu_matching);
        menu_mathcing.setOnClickListener(new MyListActivity.MenuMathcingOnClickListener());

        Cursor c = db.query("MySing", new String[] { "title", "singer" }, null,
                null, null, null, null);

        LinearLayout trigger = (LinearLayout) findViewById(R.id.trigger);

        boolean mov = c.moveToFirst();
        while (mov) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setPadding(0, 0, 0, 5);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(15);
            textView.setText(String.format("%s / %s", c.getString(0), c.getString(1)));
            mov = c.moveToNext();
            trigger.addView(textView);
        }
        c.close();
        db.close();
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
