package com.example.chirag.googlesignin;

public class DoiTuong_BTS {
    private String TenTramGoc;
    private String ChungLoaiThietBi;
    private String BangTanHoatDong;
    private String SoAnten;

    public DoiTuong_BTS(String tenTramGoc, String chungLoaiThietBi, String bangTanHoatDong, String soAnten) {
        TenTramGoc = tenTramGoc;
        ChungLoaiThietBi = chungLoaiThietBi;
        BangTanHoatDong = bangTanHoatDong;
        SoAnten = soAnten;
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

    public String getSoAnten() {
        return SoAnten;
    }

    public void setSoAnten(String soAnten) {
        SoAnten = soAnten;
    }
}
