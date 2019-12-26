package com.example.nullsing_kenta;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddList2Activity extends Activity {
    String URL = "https://mighty-ravine-64959.herokuapp.com/";
    RequestQueue requestQueue;
    ArrayList<JSONsong> json_songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlist2);

        json_songs = new ArrayList<>();

        Intent intent = this.getIntent();
        String str_title = intent.getStringExtra("title");
        String str_artist = intent.getStringExtra("artist");
        String str_genre = intent.getStringExtra("genre");

        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Method.GET,
                URL,
                null,
                new Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Jsonをパースする
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                String id = obj.getString("id");
                                String title = obj.getString("title");
                                String artist = obj.getString("artist");
                                String genre = obj.getString("genre");

                                JSONsong song = new JSONsong();
                                song.set_id(Integer.parseInt(id));
                                song.set_title(title);
                                song.set_artist(artist);
                                song.set_genre(genre);
                                json_songs.add(song);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        LinearLayout songsList = (LinearLayout) findViewById(R.id.songs);
                        for(int i = 0; i < json_songs.size(); i++) {
                            CheckBox chkbox = new CheckBox(getApplicationContext());
                            chkbox.setText(json_songs.get(i).get_title() + "/" + json_songs.get(i).get_artist());
                            chkbox.setId(i);
                            songsList.addView(chkbox, new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);

        requestQueue.start();

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

                Intent intent = new Intent(AddList2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        menu_mylist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();

                Intent intent = new Intent(AddList2Activity.this, MyListActivity.class);
                startActivity(intent);
            }
        });

        menu_mathcing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();

                Intent intent = new Intent(AddList2Activity.this, MatchingTypeSelectActivity.class);
                startActivity(intent);
            }
        });

        final Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox chkbox;

                for(int i = 0; i < json_songs.size(); i++) {
                    chkbox = (CheckBox)findViewById(i);

                    if(chkbox.isChecked()) {
                        int id = json_songs.get(i).get_id();
                        String title = json_songs.get(i).get_title();
                        String artist = json_songs.get(i).get_artist();
                        // String genre = json_songs.get(i).get_genre();

                        try {
                            db.execSQL("INSERT INTO MySing VALUES(" + id + ", '" + title + "', '" + artist + "');");
                        } catch (Exception e) {
                        }
                    }
                }
                db.close();

                Intent intent = new Intent(AddList2Activity.this, MyListActivity.class);
                startActivity(intent);
            }
        });
    }
}
