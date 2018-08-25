package com.sunseen.spcontrolsystem.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

public class WebService extends Service{
    private WebSocketServer webSocketServer;
    private WebConfig webConfig;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        webConfig = new WebConfig();
        webConfig.setMaxParallels(10);
        webConfig.setPort(8000);
        webSocketServer = new WebSocketServer(webConfig);
        webSocketServer.startServerAsync();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        try {
            webSocketServer.stopServerAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        return Service.START_NOT_STICKY;
    }
}
