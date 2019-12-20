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

public class MathcingActivity extends Activity {

    String matchingType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        TextView text_matching_type = (TextView)findViewById(R.id.text_matching_type);

        LinearLayout menu_home = (LinearLayout) findViewById(R.id.menu_home_l);
        menu_home.setClickable(true);
        menu_home.setOnClickListener(new MathcingActivity.MenuHomeOnClickListener());

        LinearLayout menu_mylist = (LinearLayout) findViewById(R.id.menu_myList_l);
        menu_mylist.setClickable(true);
        menu_mylist.setOnClickListener(new MathcingActivity.MenuMyListOnClickListener());

        LinearLayout menu_addlist = (LinearLayout)findViewById(R.id.menu_addList_l);
        menu_addlist.setClickable(true);
        menu_addlist.setOnClickListener(new MathcingActivity.MenuAddListOnClickListener());

        TextView button_matching_host = (TextView)findViewById(R.id.button_matching_host);
        button_matching_host.setOnClickListener(new MathcingActivity.ButtonMatchingHostOnClickListener());

        TextView button_matching_client = (TextView)findViewById(R.id.button_matching_client);
        button_matching_client.setOnClickListener(new MathcingActivity.ButtonMatchingClientOnClickListener());

        Intent intent = this.getIntent();
        matchingType = intent.getStringExtra("matchingType");
        TextView textView = (TextView)this.findViewById(R.id.text_matching_type);
        textView.setText(matchingType);

    }

    private class MenuHomeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MathcingActivity.this, MainActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuMyListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MathcingActivity.this, MyListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuAddListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MathcingActivity.this, AddListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class ButtonMatchingHostOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MathcingActivity.this, MatchingHostActivity.class);
            intent.putExtra("matchingType", matchingType);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class ButtonMatchingClientOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MathcingActivity.this, MatchingClientActivity.class);
            intent.putExtra("matchingType", matchingType);
            // Activityの移動
            startActivity(intent);
        }
    }

}
