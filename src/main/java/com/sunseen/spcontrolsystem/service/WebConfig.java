package com.sunseen.spcontrolsystem.service;

public class WebConfig {

    private int port = 8000;//端口
    private int maxParallels = 15; //最大接入客户端

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxParallels() {
        return maxParallels;
    }

    public void setMaxParallels(int maxParallels) {
        this.maxParallels = maxParallels;
    }
}
