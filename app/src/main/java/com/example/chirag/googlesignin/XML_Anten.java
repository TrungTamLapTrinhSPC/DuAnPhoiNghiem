package com.example.chirag.googlesignin;

import java.io.Serializable;
import java.util.List;

public class XML_Anten implements Serializable {
    private String thietKeAnTen;
    private List<String> listThanhPhan;
    private List<String> listCongTrinhThapTang;

    public XML_Anten(String thietKeAnTen, List<String> listThanhPhan, List<String> listCongTrinhThapTang) {
        this.thietKeAnTen = thietKeAnTen;
        this.listThanhPhan = listThanhPhan;
        this.listCongTrinhThapTang = listCongTrinhThapTang;
    }

    public XML_Anten() {
    }

    public String getThietKeAnTen() {
        return thietKeAnTen;
    }

    public void setThietKeAnTen(String thietKeAnTen) {
        this.thietKeAnTen = thietKeAnTen;
    }

    public List<String> getListThanhPhan() {
        return listThanhPhan;
    }

    public void setListThanhPhan(List<String> listThanhPhan) {
        this.listThanhPhan = listThanhPhan;
    }

    public List<String> getListCongTrinhThapTang() {
        return listCongTrinhThapTang;
    }

    public void setListCongTrinhThapTang(List<String> listCongTrinhThapTang) {
        this.listCongTrinhThapTang = listCongTrinhThapTang;
    }

    @Override
    public String toString() {
        return "XML_Anten{" +
                "thietKeAnTen='" + thietKeAnTen + '\'' +
                ", listThanhPhan=" + listThanhPhan +
                ", listCongTrinhThapTang=" + listCongTrinhThapTang +
                '}';
    }
}
