package com.example.chirag.googlesignin;

import android.graphics.Bitmap;
import android.net.Uri;

public class HinhAnhCongTac {
    private Uri imageID;
    private String title;

    public HinhAnhCongTac(Uri imageID, String title) {
        this.imageID = imageID;

        this.title = title;
    }

    public Uri getImageID() {
        return imageID;
    }

    public void setImageID(Uri imageID) {
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
