package com.example.chirag.googlesignin;

public class DoiTuong_CongTrinh {
    private String TenCongTrinh;
    private String ChieuCao;
    private String KhoangCach;
    private String SoTang;
    private String GocPhuongVi;
    private String DoDay;
    private String DoRong;
    private int width;

    public DoiTuong_CongTrinh(String tenCongTrinh, String chieuCao, String khoangCach, String soTang, String gocPhuongVi, String doDay, String doRong, int width) {
        TenCongTrinh = tenCongTrinh;
        ChieuCao = chieuCao;
        KhoangCach = khoangCach;
        SoTang = soTang;
        GocPhuongVi = gocPhuongVi;
        DoDay = doDay;
        DoRong = doRong;
        this.width = width;
    }

    public String getTenCongTrinh() {
        return TenCongTrinh;
    }

    public void setTenCongTrinh(String tenCongTrinh) {
        TenCongTrinh = tenCongTrinh;
    }

    public String getChieuCao() {
        return ChieuCao;
    }

    public void setChieuCao(String chieuCao) {
        ChieuCao = chieuCao;
    }

    public String getKhoangCach() {
        return KhoangCach;
    }

    public void setKhoangCach(String khoangCach) {
        KhoangCach = khoangCach;
    }

    public String getSoTang() {
        return SoTang;
    }

    public void setSoTang(String soTang) {
        SoTang = soTang;
    }

    public String getGocPhuongVi() {
        return GocPhuongVi;
    }

    public void setGocPhuongVi(String gocPhuongVi) {
        GocPhuongVi = gocPhuongVi;
    }

    public String getDoDay() {
        return DoDay;
    }

    public void setDoDay(String doDay) {
        DoDay = doDay;
    }

    public String getDoRong() {
        return DoRong;
    }

    public void setDoRong(String doRong) {
        DoRong = doRong;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
