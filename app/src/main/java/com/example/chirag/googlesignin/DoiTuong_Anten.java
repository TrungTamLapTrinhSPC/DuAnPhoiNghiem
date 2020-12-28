package com.example.chirag.googlesignin;

public class DoiTuong_Anten {
    private String TenAnten;
    private String ChungLoaiAnTen;
    private String NgaySua;
    private String GocPhuongVi;
    private String CaoDoAnTen;

    public DoiTuong_Anten(String tenAnten, String chungLoaiAnTen, String ngaySua, String gocPhuongVi, String caoDoAnTen) {
        TenAnten = tenAnten;
        ChungLoaiAnTen = chungLoaiAnTen;
        NgaySua = ngaySua;
        GocPhuongVi = gocPhuongVi;
        CaoDoAnTen = caoDoAnTen;
    }

    public String getTenAnten() {
        return TenAnten;
    }

    public void setTenAnten(String tenAnten) {
        TenAnten = tenAnten;
    }

    public String getChungLoaiAnTen() {
        return ChungLoaiAnTen;
    }

    public void setChungLoaiAnTen(String chungLoaiAnTen) {
        ChungLoaiAnTen = chungLoaiAnTen;
    }

    public String getNgaySua() {
        return NgaySua;
    }

    public void setNgaySua(String ngaySua) {
        NgaySua = ngaySua;
    }

    public String getGocPhuongVi() {
        return GocPhuongVi;
    }

    public void setGocPhuongVi(String gocPhuongVi) {
        GocPhuongVi = gocPhuongVi;
    }

    public String getCaoDoAnTen() {
        return CaoDoAnTen;
    }

    public void setCaoDoAnTen(String caoDoAnTen) {
        CaoDoAnTen = caoDoAnTen;
    }
}
