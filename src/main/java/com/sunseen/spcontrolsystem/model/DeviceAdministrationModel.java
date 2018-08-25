package com.sunseen.spcontrolsystem.model;

public class DeviceAdministrationModel {
    private int logo;
    private String title;
    private int onLine;
    private int outLine;
    private int right;

    public DeviceAdministrationModel(int logo, String title, int onLine, int outLine, int right) {
        this.logo = logo;
        this.title = title;
        this.onLine = onLine;
        this.outLine = outLine;
        this.right = right;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOnLine() {
        return onLine;
    }

    public void setOnLine(int onLine) {
        this.onLine = onLine;
    }

    public int getOutLine() {
        return outLine;
    }

    public void setOutLine(int outLine) {
        this.outLine = outLine;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
