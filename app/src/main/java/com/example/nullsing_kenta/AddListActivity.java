package com.example.nullsing_kenta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.util.Log;

public class AddListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlist);

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
                Intent intent = new Intent(AddListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        menu_mylist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddListActivity.this, MyListActivity.class);
                startActivity(intent);
            }
        });

        menu_mathcing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddListActivity.this, MatchingTypeSelectActivity.class);
                startActivity(intent);
            }
        });

        final Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SpannableStringBuilder sb = (SpannableStringBuilder)titleText.getText();
                String str_title = sb.toString();
                sb = (SpannableStringBuilder)singerText.getText();
                String str_singer = sb.toString();
                sb = (SpannableStringBuilder)genreText.getText();
                String str_genre = sb.toString();
                if (str_title.equals("") && str_singer.equals("") && str_genre.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddListActivity.this);
                    builder.setMessage("どれか1つは検索条件を入力してください。")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // ボタンをクリックしたときの動作
                                }
                            });
                    builder.show();

                } else {
                    Intent intent = new Intent(AddListActivity.this, AddList2Activity.class);
                    intent.putExtra("title", str_title);
                    intent.putExtra("artist", str_singer);
                    intent.putExtra("genre", str_genre);
                    startActivity(intent);
                }


            }
        });
    }
}
