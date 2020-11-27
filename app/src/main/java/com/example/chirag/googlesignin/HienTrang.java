package com.example.chirag.googlesignin;

public class HienTrang {

    private String userName;
    private boolean active= false;

    public HienTrang(String userName)  {
        this.userName= userName;
        this.active= true;
    }

    public HienTrang(String userName, boolean active)  {
        this.userName= userName;
        this.active= active;
    }


    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    @Override
    public String toString() {
        return this.userName;
    }

}