package com.example.chirag.googlesignin;

import android.net.Uri;

public class DoiTuong_AnhChup {
    private String TenAnh;
    private Uri imageUri;
    private String NgayChup;

    public DoiTuong_AnhChup(String tenAnh, Uri imageUri, String ngayChup) {
        TenAnh = tenAnh;
        this.imageUri = imageUri;
        NgayChup = ngayChup;
    }

    public String getTenAnh() {
        return TenAnh;
    }

    public void setTenAnh(String tenAnh) {
        TenAnh = tenAnh;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getNgayChup() {
        return NgayChup;
    }

    public void setNgayChup(String ngayChup) {
        NgayChup = ngayChup;
    }
}
