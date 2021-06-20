package com.example.chirag.googlesignin;

import java.io.Serializable;
import java.util.List;

public class XML_Tram implements Serializable {
   private String thietKeTram;
   private String thietKeNhaTram;
   private String thietKeCTCN;
   private List<String> listBTS;
   private List<XML_Cot> listCot;

    public XML_Tram() {
    }

    public XML_Tram(String thietKeTram, String thietKeNhaTram, String thietKeCTCN, List<String> listBTS, List<XML_Cot> listCot) {
        this.thietKeTram = thietKeTram;
        this.thietKeNhaTram = thietKeNhaTram;
        this.thietKeCTCN = thietKeCTCN;
        this.listBTS = listBTS;
        this.listCot = listCot;
    }

    public String getThietKeTram() {
        return thietKeTram;
    }

    public void setThietKeTram(String thietKeTram) {
        this.thietKeTram = thietKeTram;
    }

    public String getThietKeNhaTram() {
        return thietKeNhaTram;
    }

    public void setThietKeNhaTram(String thietKeNhaTram) {
        this.thietKeNhaTram = thietKeNhaTram;
    }

    public String getThietKeCTCN() {
        return thietKeCTCN;
    }

    public void setThietKeCTCN(String thietKeCTCN) {
        this.thietKeCTCN = thietKeCTCN;
    }

    public List<String> getListBTS() {
        return listBTS;
    }

    public void setListBTS(List<String> listBTS) {
        this.listBTS = listBTS;
    }

    public List<XML_Cot> getListCot() {
        return listCot;
    }

    public void setListCot(List<XML_Cot> listCot) {
        this.listCot = listCot;
    }

    @Override
    public String toString() {
        return "XML_Tram{" +
                "thietKeTram='" + thietKeTram + '\'' +
                ", thietKeNhaTram='" + thietKeNhaTram + '\'' +
                ", thietKeCTCN='" + thietKeCTCN + '\'' +
                ", listBTS=" + listBTS +
                ", listCot=" + listCot +
                '}';
    }
}
