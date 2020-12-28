package com.example.chirag.googlesignin;

public class DoiTuong_Cot {
    private String ChieuCaoCot;
    private String TenCot;
    private String SoChan;
    private String ViTriX;
    private String ViTriY;

    public DoiTuong_Cot(String tenCot,String chieuCaoCot,  String soChan, String viTriX, String viTriY) {
        ChieuCaoCot = chieuCaoCot;
        TenCot = tenCot;
        SoChan = soChan;
        ViTriX = viTriX;
        ViTriY = viTriY;
    }

    public String getChieuCaoCot() {
        return ChieuCaoCot;
    }

    public void setChieuCaoCot(String chieuCaoCot) {
        ChieuCaoCot = chieuCaoCot;
    }

    public String getTenCot() {
        return TenCot;
    }

    public void setTenCot(String tenCot) {
        TenCot = tenCot;
    }

    public String getSoChan() {
        return SoChan;
    }

    public void setSoChan(String soChan) {
        SoChan = soChan;
    }

    public String getViTriX() {
        return ViTriX;
    }

    public void setViTriX(String viTriX) {
        ViTriX = viTriX;
    }

    public String getViTriY() {
        return ViTriY;
    }

    public void setViTriY(String viTriY) {
        ViTriY = viTriY;
    }
}
