package com.example.chirag.googlesignin;

public class DoiTuong_BTS_CheckList {
    private String TenTramGoc;
    private String ChungLoaiThietBi;
    private String BangTanHoatDong;
    private String MangSuDung;
    private boolean active= false;

    public DoiTuong_BTS_CheckList(String tenTramGoc, String chungLoaiThietBi, String bangTanHoatDong, String mangSuDung, boolean active) {
        TenTramGoc = tenTramGoc;
        ChungLoaiThietBi = chungLoaiThietBi;
        BangTanHoatDong = bangTanHoatDong;
        MangSuDung = mangSuDung;
        this.active = active;
    }

    public String getTenTramGoc() {
        return TenTramGoc;
    }

    public void setTenTramGoc(String tenTramGoc) {
        TenTramGoc = tenTramGoc;
    }

    public String getChungLoaiThietBi() {
        return ChungLoaiThietBi;
    }

    public void setChungLoaiThietBi(String chungLoaiThietBi) {
        ChungLoaiThietBi = chungLoaiThietBi;
    }

    public String getBangTanHoatDong() {
        return BangTanHoatDong;
    }

    public void setBangTanHoatDong(String bangTanHoatDong) {
        BangTanHoatDong = bangTanHoatDong;
    }

    public String getMangSuDung() {
        return MangSuDung;
    }

    public void setMangSuDung(String MangSuDung) {
        MangSuDung = MangSuDung;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
