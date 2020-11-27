package com.example.chirag.googlesignin;

import android.graphics.Bitmap;

public class HangMucCT {
    private Bitmap imageID;
    private String title;

    public HangMucCT(Bitmap imageID, String title) {
        this.imageID = imageID;

        this.title = title;
    }

    public Bitmap getImageID() {
        return imageID;
    }

    public void setImageID(Bitmap imageID) {
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
