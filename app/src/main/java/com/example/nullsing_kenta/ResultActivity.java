package com.example.nullsing_kenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import android.util.Log;

public class ResultActivity extends Activity {

    String yourDbDataString;
    String [] yourDbData;

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

        LinearLayout menu_addlist = (LinearLayout) findViewById(R.id.menu_addList_l);
        menu_addlist.setClickable(true);
        menu_addlist.setOnClickListener(new ResultActivity.MenuAddListOnClickListener());

        LinearLayout menu_mathcing = (LinearLayout) findViewById(R.id.menu_matching_l);
        menu_mathcing.setClickable(true);
        menu_mathcing.setOnClickListener(new ResultActivity.MenuMatchingOnClickListener());

        //--

        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        yourDbDataString = this.getIntent().getStringExtra("yourDbDataString");
        Log.d("yourDbData", "string : " + yourDbDataString);

        if(yourDbDataString.length() != 0) {
            yourDbData = yourDbDataString.split("\\|");
            Log.d("yourDbData", "yourDbData.length : " + yourDbData.length);
            for (int i = 0; i < yourDbData.length; i++) {
                Log.d("yourDbData", "yourDbData[" + i + "] :" + yourDbDataString);
            }
        }

        Cursor c = db.query("MySing", new String[]{"title", "singer", "genre"}, null,
                null, null, null, null);

        ArrayList<String> myTitleList = new ArrayList<String>();
        ArrayList<String> myArtistList = new ArrayList<String>();
        ArrayList<String> myGenreList = new ArrayList<String>();

        boolean mov = c.moveToFirst();
        while (mov) {
            myTitleList.add(c.getString(0) + "/" + c.getString(1));
            myArtistList.add(c.getString(1));
            myGenreList.add(c.getString(2));

            mov = c.moveToNext();
        }
        c.close();
        db.close();

        List<String> myHashedTitleList = new ArrayList<String>(new LinkedHashSet<>(myTitleList));
        List<String> myHashedArtistList = new ArrayList<String>(new LinkedHashSet<>(myArtistList));
        List<String> myHashedGenreList = new ArrayList<String>(new LinkedHashSet<>(myGenreList));

        ArrayList<String> yourTitleList = new ArrayList<String>();
        ArrayList<String> yourArtistList = new ArrayList<String>();
        ArrayList<String> yourGenreList = new ArrayList<String>();

        // TODO -ここから- yourTitleList, yourArtistList, yourGenreListにそれぞれaddしてください（重複可)
        //                  ただし、titleは"曲名/アーティスト名"のフォーマットでお願いします。
        //                  上でyourTitleList, yourArtistList, yourGenreLIstにaddしてるので参考にしてください。

        // 下はテスト用にデータを手動でリストに追加しています。消してください。

        String [] yourDbDataElements;
        if (yourDbDataString.length() != 0) {
            for (int i = 0; i < yourDbData.length; i++) {
                Log.d("ResultActivity", "yourDbData[" + i + "] : " + yourDbData[i]);
                yourDbDataElements = yourDbData[i].split(",");
                yourTitleList.add(yourDbDataElements[0] + "/" + yourDbDataElements[1]);
                yourArtistList.add(yourDbDataElements[1]);
                yourGenreList.add(yourDbDataElements[2]);
            }
        }

        /*
        yourTitleList.add("title1/artist1");
        yourArtistList.add("artist1");
        yourGenreList.add("雅楽");

        yourTitleList.add("マリーゴールド/あいみょん");
        yourArtistList.add("あいみょん");
        yourGenreList.add("J-POP");

        yourTitleList.add("title3/artist3");
        yourArtistList.add("artist3");
        yourGenreList.add("洋楽");

        yourTitleList.add("title1/artist1");
        yourArtistList.add("米津玄師");
        yourGenreList.add("J-POP");

        yourTitleList.add("title1/artist1");
        yourArtistList.add("back number");
        yourGenreList.add("ジャンル例");*/

        // -ここまで-

        ArrayList<String> yourHashedTitleList = new ArrayList<String>(new LinkedHashSet<>(yourTitleList));
        ArrayList<String> yourHashedArtistList = new ArrayList<String>(new LinkedHashSet<>(yourArtistList));
        ArrayList<String> yourHashedGenreList = new ArrayList<String>(new LinkedHashSet<>(yourGenreList));

        String[] result = {};

        Intent intent = this.getIntent();
        String matchingType = intent.getStringExtra("matchingType");
        TextView text_result_title = (TextView) findViewById(R.id.text_result_title);

        if (matchingType.equals("title")) {
            String[] myTitleArray = myHashedTitleList.toArray(new String[myHashedTitleList.size()]);
            String[] yourTitleArray = yourHashedTitleList.toArray(new String[yourHashedTitleList.size()]);

            result = set_function.intersect_set(myTitleArray, yourTitleArray);

            if(result.length != 0) {
                text_result_title.setText("二人が知っている曲はこちらです");
            } else {
                text_result_title.setText("二人が知っている曲はありませんでした");
            }

        } else if (matchingType.equals("artist")) {
            String[] myArtistArray = myHashedArtistList.toArray(new String[myHashedArtistList.size()]);
            String[] yourArtistArray = yourHashedArtistList.toArray(new String[yourHashedArtistList.size()]);

            result = set_function.intersect_set(myArtistArray, yourArtistArray);

            if(result.length != 0) {
                text_result_title.setText("二人が知っているアーティストはこちらです");
            } else {
                text_result_title.setText("二人が知っているアーティストはありませんでした");
            }

        } else if (matchingType.equals("genre")) {
            String[] myGenreArray = myHashedGenreList.toArray(new String[myHashedGenreList.size()]);
            String[] yourGenreArray = yourHashedGenreList.toArray(new String[yourHashedGenreList.size()]);

            result = set_function.intersect_set(myGenreArray, yourGenreArray);

            if(result.length != 0) {
                text_result_title.setText("二人が知っているジャンルはこちらです");
            } else {
                text_result_title.setText("二人が知っているジャンルはありませんでした");
            }

        }

        LinearLayout trigger = (LinearLayout) findViewById(R.id.trigger);
        for(int i = 0; i < result.length; i++) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setPadding(0, 0, 0, 5);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(Color.argb(0,127,127,127));
            textView.setTextSize(20);
            textView.setText(result[i]);
            trigger.addView(textView);
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
