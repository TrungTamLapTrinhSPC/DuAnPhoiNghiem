package com.example.chirag.googlesignin;

public class DoiTuong_ThanhPhan {

    private String TenThanhPhan;
    private String ChieuDai;
    private String ChungLoai;
    private String SuyHaodB;
    private String SuyHao;

    public DoiTuong_ThanhPhan(String tenThanhPhan, String chieuDai, String chungLoai, String suyHaodB, String suyHao) {
        TenThanhPhan = tenThanhPhan;
        ChieuDai = chieuDai;
        ChungLoai = chungLoai;
        SuyHaodB = suyHaodB;
        SuyHao = suyHao;
    }

    public String getTenThanhPhan() {
        return TenThanhPhan;
    }

    public void setTenThanhPhan(String tenThanhPhan) {
        TenThanhPhan = tenThanhPhan;
    }

    public String getChieuDai() {
        return ChieuDai;
    }

    public void setChieuDai(String chieuDai) {
        ChieuDai = chieuDai;
    }

    public String getChungLoai() {
        return ChungLoai;
    }

    public void setChungLoai(String chungLoai) {
        ChungLoai = chungLoai;
    }

    public String getSuyHaodB() {
        return SuyHaodB;
    }

    public void setSuyHaodB(String suyHaodB) {
        SuyHaodB = suyHaodB;
    }

    public String getSuyHao() {
        return SuyHao;
    }

    public void setSuyHao(String suyHao) {
        SuyHao = suyHao;
    }
}
