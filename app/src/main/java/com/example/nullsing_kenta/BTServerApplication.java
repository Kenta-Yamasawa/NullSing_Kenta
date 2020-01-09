package com.example.nullsing_kenta;

import android.app.Application;

public class BTServerApplication extends Application {

    private String tempValue = "no Database Data";

    public synchronized String getTempValue(){
        return tempValue;
    }

    public synchronized void setTempValue(String s){
        tempValue =s;
    }
}
