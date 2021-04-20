package com.example.chirag.googlesignin;

public class DoiTuong_BTS {
    private String TenTramGoc;
    private String ChungLoaiThietBi;
    private String BangTanHoatDong;
    private String MangSuDung;
    private String SoMayThuPhatSong;

    public DoiTuong_BTS(String tenTramGoc, String chungLoaiThietBi, String bangTanHoatDong, String mangSuDung, String soMayThuPhatSong) {
        TenTramGoc = tenTramGoc;
        ChungLoaiThietBi = chungLoaiThietBi;
        BangTanHoatDong = bangTanHoatDong;
        MangSuDung = mangSuDung;
        SoMayThuPhatSong = soMayThuPhatSong;
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

    public void setMangSuDung(String mangSuDung) {
        MangSuDung = mangSuDung;
    }

    public String getSoMayThuPhatSong() {
        return SoMayThuPhatSong;
    }

    public void setSoMayThuPhatSong(String soMayThuPhatSong) {
        SoMayThuPhatSong = soMayThuPhatSong;
    }
}
