package com.example.nullsing_kenta;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MatchingHostActivity extends AppCompatActivity {

    static final String TAG = "BTTest1S";

    BluetoothAdapter bluetoothAdapter = null;

    EditText tempEditText;
    BTServerThread btServerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_host); // Find Views
        tempEditText = (EditText) findViewById(R.id.tempEditText);
        tempEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                ((BTServerApplication) MatchingHostActivity.this.getApplication()).setTempValue(s);
            }
        });
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
        outState.putString(Constants.STATE_TEMP, tempEditText.getText().toString());
    }

    public class BTServerThread extends Thread {

        BluetoothServerSocket bluetoothServerSocket;
        BluetoothSocket bluetoothSocket;
        InputStream inputStream;
        OutputStream outputStream;

        public void run() {

            byte[] incomingBuff = new byte[64];

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

                if (cmd.equals("GET:TEMP")) {
                    String s = ((BTServerApplication) MatchingHostActivity.this.getApplication()).getTempValue();
                    resp = (s == null) ? "n/a" : s;
                } else {
                    Log.d(TAG, "Unknown Command");
                }

            } catch (Exception e) {
                Log.d(TAG, "Exception - processCommand " + e.getMessage());
            }

            return resp;
        }
    }
}