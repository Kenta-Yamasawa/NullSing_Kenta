package com.example.nullsing_kenta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class MatchingClientActivity extends Activity {

    String matchingType;
    String myDbData;

    static final String TAG = "BTTEST1";
    BluetoothAdapter bluetoothAdapter;

//    TextView btStatusTextView;
//    TextView tempTextView;

    BTClientThread btClientThread;

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            String s;

            switch(msg.what){
                case Constants.MESSAGE_BT:
                    s = (String) msg.obj;
                    if(s != null){
//                        btStatusTextView.setText(s);
                    }
                    break;
                case Constants.MESSAGE_TEMP:
                    s = (String) msg.obj;
                    if(s != null){
//                        tempTextView.setText(s);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_client);

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
        myDbData = "";
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query("MySing", new String[]{"title", "singer", "genre"}, null,
                null, null, null, null);

        String tmpMyDbData = "";
        boolean mov = c.moveToFirst();
        while (mov) {
            tmpMyDbData = tmpMyDbData + c.getString(0) + ",";
            tmpMyDbData = tmpMyDbData + c.getString(1) + ",";
            tmpMyDbData = tmpMyDbData + c.getString(2) + "|";
            Log.d("MCA:" ,"tmpMyDbData : " + tmpMyDbData.getBytes().length + " byte");
            if (tmpMyDbData.getBytes().length < 900) {
                myDbData = tmpMyDbData;
            }
            mov = c.moveToNext();
        }

        c.close();
        db.close();

        Log.d("dbData", myDbData);

        LinearLayout menu_home = (LinearLayout) findViewById(R.id.menu_home_l);
        menu_home.setClickable(true);
        menu_home.setOnClickListener(new MatchingClientActivity.MenuHomeOnClickListener());

        LinearLayout menu_mylist = (LinearLayout) findViewById(R.id.menu_myList_l);
        menu_mylist.setClickable(true);
        menu_mylist.setOnClickListener(new MatchingClientActivity.MenuMyListOnClickListener());

        LinearLayout menu_addlist = (LinearLayout)findViewById(R.id.menu_addList_l);
        menu_addlist.setClickable(true);
        menu_addlist.setOnClickListener(new MatchingClientActivity.MenuAddListOnClickListener());

        LinearLayout menu_matching = (LinearLayout) findViewById(R.id.menu_matching_l);
        menu_matching.setClickable(true);
        menu_matching.setOnClickListener(new MatchingClientActivity.MenuMathcingOnClickListener());

        // Find Views
//        btStatusTextView = (TextView) findViewById(R.id.btStatusTextView);
//        tempTextView = (TextView) findViewById(R.id.tempTextView);
//        tempTextView.setOnClickListener(new MatchingClientActivity.MatchingPasswordOnClickListener());

        if(savedInstanceState != null){
//            String temp = savedInstanceState.getString(Constants.STATE_TEMP);
//            tempTextView.setText(temp);
        }

        // Initialize Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if( bluetoothAdapter == null ){
            Log.d(TAG, "This device doesn't support Bluetooth.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        btClientThread = new BTClientThread();
        btClientThread.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(btClientThread != null){
            btClientThread.interrupt();
            btClientThread = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString(Constants.STATE_TEMP, tempTextView.getText().toString());
    }

    public class BTClientThread extends Thread {

        InputStream inputStream;
        OutputStream outputStrem;
        BluetoothSocket bluetoothSocket;

        public void run() {

            byte[] incomingBuff = new byte[6400000];

            BluetoothDevice bluetoothDevice = null;
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
            for(BluetoothDevice device : devices){
                if(device.getName().equals(Constants.BT_DEVICE)) {
                    bluetoothDevice = device;
                    break;
                }
            }

            if(bluetoothDevice == null){
                Log.d(TAG, "No device found.");
                return;
            }

            try {

                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(
                        Constants.BT_UUID);

                while(true) {

                    if(Thread.interrupted()){
                        break;
                    }

                    try {
                        bluetoothSocket.connect();

                        handler.obtainMessage(
                                Constants.MESSAGE_BT,
                                "CONNECTED " + bluetoothDevice.getName())
                                .sendToTarget();

                        inputStream = bluetoothSocket.getInputStream();
                        outputStrem = bluetoothSocket.getOutputStream();

                        while (true) {

                            if (Thread.interrupted()) {
                                break;
                            }

                            // Send Command
                            String command = "DB:SEND" + myDbData;
                            outputStrem.write(command.getBytes());
                            // Read Response
                            int incomingBytes = inputStream.read(incomingBuff);
                            Log.d("MCA", "incomingBytes : " + incomingBytes);
                            byte[] buff = new byte[incomingBytes];
                            System.arraycopy(incomingBuff, 0, buff, 0, incomingBytes);
                            String s = new String(buff, StandardCharsets.UTF_8);

                            //activity遷移
                            String successTag = "success";
                            if(s.startsWith(successTag)) {
                                String yourDbDataString = s.substring(successTag.length());
                                Log.d("MCA yourDbData:", yourDbDataString);
                                // 引数1：自身のActivity、引数2:移動先のActivity名
                                Intent intent = new Intent(MatchingClientActivity.this, ResultActivity.class);
                                intent.putExtra("matchingType", matchingType);
                                intent.putExtra("yourDbDataString", yourDbDataString);
                                // Activityの移動
                                startActivity(intent);
                            }

                            // Show Result to UI
                            handler.obtainMessage(
                                    Constants.MESSAGE_TEMP,
                                    s)
                                    .sendToTarget();

                            // Update again in a few seconds
                            Thread.sleep(3000);
                        }

                    } catch (IOException e) {
                        // connect will throw IOException immediately
                        // when it's disconnected.
                        Log.d(TAG, e.getMessage());
                    }

                    handler.obtainMessage(
                            Constants.MESSAGE_BT,
                            "DISCONNECTED")
                            .sendToTarget();

                    // Re-try after 3 sec
                    Thread.sleep(3 * 1000);
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if(bluetoothSocket != null){
                try {
                    bluetoothSocket.close();
                } catch (IOException e) {}
                bluetoothSocket = null;
            }

            handler.obtainMessage(
                    Constants.MESSAGE_BT,
                    "DISCONNECTED - Exit BTClientThread")
                    .sendToTarget();
        }
    }

    private class MenuHomeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingClientActivity.this, MainActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuMyListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingClientActivity.this, MyListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuAddListOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingClientActivity.this, AddListActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MenuMathcingOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingClientActivity.this, MatchingTypeSelectActivity.class);
            // Activityの移動
            startActivity(intent);
        }
    }

    private class MatchingPasswordOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 引数1：自身のActivity、引数2:移動先のActivity名
            Intent intent = new Intent(MatchingClientActivity.this, ResultActivity.class);
            intent.putExtra("matchingType", matchingType);
            // Activityの移動
            startActivity(intent);
        }
    }

}
