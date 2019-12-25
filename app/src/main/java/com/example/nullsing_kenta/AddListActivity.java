package com.example.nullsing_kenta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.BaseAdapter;

public class AddListActivity extends Activity {
    Boolean chkbox0_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlist);

        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        final EditText titleText = (EditText) findViewById(R.id.titleText);
        final EditText singerText = (EditText) findViewById(R.id.singerText);
        final EditText genreText = (EditText) findViewById(R.id.genreText);

        LinearLayout menu_home = (LinearLayout) findViewById(R.id.menu_home_l);
        menu_home.setClickable(true);
        LinearLayout menu_mylist = (LinearLayout) findViewById(R.id.menu_myList_l);
        menu_mylist.setClickable(true);
        LinearLayout menu_addlist = (LinearLayout)findViewById(R.id.menu_addList_l);
        menu_addlist.setClickable(true);
        LinearLayout menu_mathcing = (LinearLayout)findViewById(R.id.menu_matching_l);
        menu_mathcing.setClickable(true);

        menu_home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();

                Intent intent = new Intent(AddListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        menu_mylist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();

                Intent intent = new Intent(AddListActivity.this, MyListActivity.class);
                startActivity(intent);
            }
        });

        menu_mathcing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();

                Intent intent = new Intent(AddListActivity.this, MatchingTypeSelectActivity.class);
                startActivity(intent);
            }
        });

        final Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();

                SpannableStringBuilder sb = (SpannableStringBuilder)titleText.getText();
                String str_title = sb.toString();
                sb = (SpannableStringBuilder)singerText.getText();
                String singer_title = sb.toString();
                sb = (SpannableStringBuilder)genreText.getText();
                String genre_title = sb.toString();

                Intent intent = new Intent(AddListActivity.this, MyListActivity.class);
                startActivity(intent);
            }
        });
    }
}
