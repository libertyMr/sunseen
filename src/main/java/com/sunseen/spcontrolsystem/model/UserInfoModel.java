package com.sunseen.spcontrolsystem.model;

import java.io.Serializable;

public class UserInfoModel implements Serializable{
    private String user_name;
    private int user_power;
    private String last_login;
    private String user_id;
    private String pwd;

    public UserInfoModel(String user_name, String pwd,int user_power, String last_login, String user_id)
    {
        this.user_name = user_name;
        this.user_power = user_power;
        this.last_login = last_login;
        this.user_id = user_id;
        this.pwd = pwd;
    }

    public String getUser_name() {
        return user_name;
    }

    public int getUser_power() {
        return user_power;
    }

    public String getLast_login() {
        return last_login;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPwd() {
        return pwd;
    }
}
