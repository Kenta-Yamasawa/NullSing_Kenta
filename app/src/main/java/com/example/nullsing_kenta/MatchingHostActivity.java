package com.example.nullsing_kenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MatchingHostActivity extends Activity {

    static String matchingType;

    static final String TAG = "BTTest1S";

    BluetoothAdapter bluetoothAdapter = null;

    EditText tempEditText;
    BTServerThread btServerThread;

    static int communicationState;
    String yourDbDataString = "no DB Data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        communicationState = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_host); // Find Views

        Intent intent = this.getIntent();
        matchingType = intent.getStringExtra("matchingType");
        TextView textView = (TextView)this.findViewById(R.id.text_matching_type);
        if(matchingType.equals("title")) {
            textView.setText("曲名でマッチングします");
        } else if (matchingType.equals("artist")){
            textView.setText("アーティスト名でマッチングします");
        } else if (matchingType.equals("genre")){
            textView.setText("ジャンル名でマッチングします");
        }

        //DBのStringデータを作成
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String myDbData = "";

        Cursor c = db.query("MySing", new String[]{"title", "singer", "genre"}, null,
                null, null, null, null);

        boolean mov = c.moveToFirst();
        while (mov) {
            myDbData = myDbData + c.getString(0) + "/" + c.getString(1) + ",";
            myDbData = myDbData + c.getString(1) + ",";
            myDbData = myDbData + c.getString(2) + "|";

            mov = c.moveToNext();
        }
        c.close();
        db.close();

        Log.d("dbData", myDbData);

        ((BTServerApplication) MatchingHostActivity.this.getApplication()).setTempValue(myDbData);


        LinearLayout menu_home = (LinearLayout) findViewById(R.id.menu_home_l);
        menu_home.setClickable(true);
        menu_home.setOnClickListener(new MatchingHostActivity.MenuHomeOnClickListener());

        LinearLayout menu_mylist = (LinearLayout) findViewById(R.id.menu_myList_l);
        menu_mylist.setClickable(true);
        menu_mylist.setOnClickListener(new MatchingHostActivity.MenuMyListOnClickListener());

        LinearLayout menu_addlist = (LinearLayout)findViewById(R.id.menu_addList_l);
        menu_addlist.setClickable(true);
        menu_addlist.setOnClickListener(new MatchingHostActivity.MenuAddListOnClickListener());

        LinearLayout menu_matching = (LinearLayout) findViewById(R.id.menu_matching_l);
        menu_matching.setClickable(true);
        menu_matching.setOnClickListener(new MatchingHostActivity.MenuMathcingOnClickListener());

//        tempEditText = (EditText) findViewById(R.id.tempEditText);
//        tempEditText.setText(myDbData);
//        tempEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String s = editable.toString();
//                ((BTServerApplication) MatchingHostActivity.this.getApplication()).setTempValue(s);
//            }
//        });
        if (savedInstanceState != null) {
            tempEditText.setText(savedInstanceState.getString(Constants.STATE_TEMP));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (bluetoothAdapter == null) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                Log.d(TAG, "This device doesn't support Bluetooth.");
            }
        }

        btServerThread = new BTServerThread();
        btServerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (btServerThread != null) {
            btServerThread.cancel();
            btServerThread = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString(Constants.STATE_TEMP, tempEditText.getText().toString());
    }

    public class BTServerThread extends Thread {

        BluetoothServerSocket bluetoothServerSocket;
        BluetoothSocket bluetoothSocket;
        InputStream inputStream;
        OutputStream outputStream;

        public void run() {

            byte[] incomingBuff = new byte[64000];

            try {
                while (true) {

                    if (Thread.interrupted()) {
                        break;
                    }

                    try {

                        bluetoothServerSocket
                                = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(
                                Constants.BT_NAME,
                                Constants.BT_UUID);

                        bluetoothSocket = bluetoothServerSocket.accept();
                        bluetoothServerSocket.close();
                        bluetoothServerSocket = null;

                        inputStream = bluetoothSocket.getInputStream();
                        outputStream = bluetoothSocket.getOutputStream();

                        while (true) {

                            if (Thread.interrupted()) {
                                break;
                            }

                            int incomingBytes = inputStream.read(incomingBuff);
                            byte[] buff = new byte[incomingBytes];
                            System.arraycopy(incomingBuff, 0, buff, 0, incomingBytes);
                            String cmd = new String(buff, StandardCharsets.UTF_8);

                            String resp = processCommand(cmd);
                            outputStream.write(resp.getBytes());


                            if (!resp.equals("OK")) {
                                if(communicationState == 0) communicationState = 1;
                                else if (communicationState == 1) communicationState = 2;
                                if(communicationState == 1) {
                                    // 引数1：自身のActivity、引数2:移動先のActivity名
                                    Intent intent = new Intent(MatchingHostActivity.this, ResultActivity.class);
                                    intent.putExtra("matchingType", matchingType);
                                    intent.putExtra("yourDbDataString", yourDbDataString);
                                    // Activityの移動
                                    startActivity(intent);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (bluetoothSocket != null) {
                        try {
                            bluetoothSocket.close();
                            bluetoothSocket = null;
                        } catch (IOException e) {
                        }
                    }

                    // Bluetooth connection broke. Start Over in a few seconds.
                    Thread.sleep(3 * 1000);
                }
            } catch (InterruptedException e) {
                Log.d(TAG, "Cancelled ServerThread");
            }

            Log.d(TAG, "ServerThread exit");
        }

        public void cancel() {
            if (bluetoothServerSocket != null) {
                try {
                    bluetoothServerSocket.close();
                    bluetoothServerSocket = null;
                    super.interrupt();
                } catch (IOException e) {
                }
            }
        }

        protected String processCommand(String cmd) {

            Log.d(TAG, "processCommand " + cmd);
            String resp = "OK";

            try {

                if (cmd.startsWith("DB:SEND")) {
                    yourDbDataString = cmd.substring(7);
                    String s = ((BTServerApplication) MatchingHostActivity.this.getApplication()).getTempValue();
                    //resp = (s == null) ? "n/a" : s;
                    resp = "success" + s;
                } else {
                    Log.d(TAG, "Unknown Command");
                }

            } catch (Exception e) {
                Log.d(TAG, "Exception - processCommand " + e.getMessage());
            }

            return resp;
        }
    }

    private class MenuHomeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingHostActivity.this, MainActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuMyListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingHostActivity.this, MyListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuAddListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingHostActivity.this, AddListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuMathcingOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingHostActivity.this, MatchingTypeSelectActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

}