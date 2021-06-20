package com.example.chirag.googlesignin;

public class DoiTuong_ThanhPhan {

    private String TenThanhPhan;
    private String SuyHao;

    public DoiTuong_ThanhPhan(String tenThanhPhan, String suyHao) {
        TenThanhPhan = tenThanhPhan;
        SuyHao = suyHao;
    }

    public String getTenThanhPhan() {
        return TenThanhPhan;
    }

    public void setTenThanhPhan(String tenThanhPhan) {
        TenThanhPhan = tenThanhPhan;
    }

    public String getSuyHao() {
        return SuyHao;
    }

    public void setSuyHao(String suyHao) {
        SuyHao = suyHao;
    }
}
