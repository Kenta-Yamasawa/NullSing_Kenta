package com.example.nullsing_kenta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    Boolean chkbox1_flag = false;
    Boolean chkbox2_flag = false;
    Boolean chkbox3_flag = false;
    Boolean chkbox4_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlist);

        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM MySing;");
        } catch (Exception e) {
        }

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
                if (chkbox0_flag) {
                    try {
                        db.execSQL("INSERT INTO MySing VALUES(0, 'パプリカ', 'Foorin');");
                    } catch (Exception e) {
                    }
                }
                if(chkbox1_flag) {
                    try {
                        db.execSQL("INSERT INTO MySing VALUES(1, 'Lemon', '米津玄師');");
                    } catch (Exception e ) {
                    }
                }
                if(chkbox2_flag) {
                    try {
                        db.execSQL("INSERT INTO MySing VALUES(2, 'Pretender', 'Official髭男dism');");
                    } catch (Exception e) {
                    }
                }
                if(chkbox3_flag) {
                    try {
                        db.execSQL("INSERT INTO MySing VALUES(3, 'マリーゴールド', 'あいみょん');");
                    } catch (Exception e) {
                    }
                }
                if(chkbox4_flag) {
                    try {
                        db.execSQL("INSERT INTO MySing VALUES(4, 'シャルル', 'バルーン');");
                    } catch (Exception e) {
                    }
                }
                db.close();

                Intent intent = new Intent(AddListActivity.this, MyListActivity.class);
                startActivity(intent);
            }
        });

        final CheckBox chkbox0 = (CheckBox)findViewById(R.id.checkbox0);
        chkbox0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkbox0.isChecked())
                    chkbox0_flag = true;
                else
                    chkbox0_flag = false;
            }
        });

        final CheckBox chkbox1 = (CheckBox)findViewById(R.id.checkbox1);
        chkbox1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkbox1.isChecked())
                    chkbox1_flag = true;
                else
                    chkbox1_flag = false;
            }
        });

        final CheckBox chkbox2 = (CheckBox)findViewById(R.id.checkbox2);
        chkbox2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkbox2.isChecked())
                    chkbox2_flag = true;
                else
                    chkbox2_flag = false;
            }
        });

        final CheckBox chkbox3 = (CheckBox)findViewById(R.id.checkbox3);
        chkbox3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkbox3.isChecked())
                    chkbox3_flag = true;
                else
                    chkbox3_flag = false;
            }
        });

        final CheckBox chkbox4 = (CheckBox)findViewById(R.id.checkbox4);
        chkbox4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkbox4.isChecked())
                    chkbox4_flag = true;
                else
                    chkbox4_flag = false;
            }
        });
    }
}
