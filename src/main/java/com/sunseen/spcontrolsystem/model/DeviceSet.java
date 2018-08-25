package com.sunseen.spcontrolsystem.model;

/**
 * 设备名称及ip地址
 */
public class DeviceSet {

    private String device_name;
    private String device_ip;

    public DeviceSet(String device_name, String device_ip)
    {
        this.device_name = device_name;
        this.device_ip = device_ip;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_ip() {
        return device_ip;
    }

    public void setDevice_ip(String device_ip) {
        this.device_ip = device_ip;
    }
}
