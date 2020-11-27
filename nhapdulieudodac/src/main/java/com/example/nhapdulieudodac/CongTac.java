package com.example.nhapdulieudodac;

import android.graphics.Bitmap;

public class CongTac {
    private Bitmap imageID;
    private String hientrang;
    private String ngaychup;
    private String toado;
    private String diadiem;
    private String DuongDan;
    boolean isSelected;

    public CongTac(boolean isSelected, Bitmap imageID, String hientrang, String ngaychup, String DuongDan) {
        this.isSelected = isSelected;
        this.imageID = imageID;
        this.hientrang = hientrang;
        this.ngaychup = ngaychup;
        this.DuongDan = DuongDan;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public Bitmap getImageID() {
        return imageID;
    }
    public void setImageID(Bitmap imageID) {
        this.imageID = imageID;
    }

    public String getHientrang() {
        return hientrang;
    }

    public void setHientrang(String hientrang) {
        this.hientrang = hientrang;
    }

    public String getNgaychup() {
        return ngaychup;
    }
    public void setNgaychup(String ngaychup) {
        this.ngaychup = ngaychup;
    }
    public String getDuongDan() {
        return DuongDan;
    }

    public void setDuongDan(String DuongDan) {
        this.DuongDan = DuongDan;
    }

}
