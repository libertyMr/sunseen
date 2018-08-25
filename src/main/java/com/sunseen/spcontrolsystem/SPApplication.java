package com.sunseen.spcontrolsystem;

import android.app.Application;

import com.sunseen.spcontrolsystem.model.SystemRunModel;

public class SPApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SystemRunModel.SystemConfigInit();
    }
}
