package com.sunseen.spcontrolsystem.model;

public class SystemSettingsItemModel {
    private String title;
    private int logo;
    private int right;

    public SystemSettingsItemModel(String title, int logo, int right)
    {
        this.title = title;
        this.logo = logo;
        this.right = right;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
