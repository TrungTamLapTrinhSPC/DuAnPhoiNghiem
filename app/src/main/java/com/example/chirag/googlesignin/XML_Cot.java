package com.example.chirag.googlesignin;

import java.io.Serializable;
import java.util.List;

public class XML_Cot implements Serializable {
    private String thietKeCot;
    private List<XML_BTS> listBTS_inCot;

    public XML_Cot() {
    }

    public XML_Cot(String thietKeCot, List<XML_BTS> listBTS_inCot) {
        this.thietKeCot = thietKeCot;
        this.listBTS_inCot = listBTS_inCot;
    }

    public String getThietKeCot() {
        return thietKeCot;
    }

    public void setThietKeCot(String thietKeCot) {
        this.thietKeCot = thietKeCot;
    }

    public List<XML_BTS> getListBTS_inCot() {
        return listBTS_inCot;
    }

    public void setListBTS_inCot(List<XML_BTS> listBTS_inCot) {
        this.listBTS_inCot = listBTS_inCot;
    }

    @Override
    public String toString() {
        return "XML_Cot{" +
                "thietKeCot='" + thietKeCot + '\'' +
                ", listBTS_inCot=" + listBTS_inCot +
                '}';
    }
}
