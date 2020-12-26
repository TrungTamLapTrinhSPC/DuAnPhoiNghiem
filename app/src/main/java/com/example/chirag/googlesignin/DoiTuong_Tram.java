package com.example.chirag.googlesignin;

public class DoiTuong_Tram {
    private String MaTram;
    private String NgaySua;
    private String DiaDiem;
    private String SoTramGoc;

    public DoiTuong_Tram(String maTram, String ngaySua, String diaDiem, String soTramGoc) {
        MaTram = maTram;
        NgaySua = ngaySua;
        DiaDiem = diaDiem;
        SoTramGoc = soTramGoc;
    }

    public String getMaTram() {
        return MaTram;
    }

    public void setMaTram(String maTram) {
        MaTram = maTram;
    }

    public String getNgaySua() {
        return NgaySua;
    }

    public void setNgaySua(String ngaySua) {
        NgaySua = ngaySua;
    }

    public String getDiaDiem() {
        return DiaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        DiaDiem = diaDiem;
    }

    public String getSoTramGoc() {
        return SoTramGoc;
    }

    public void setSoTramGoc(String soTramGoc) {
        SoTramGoc = soTramGoc;
    }
}
