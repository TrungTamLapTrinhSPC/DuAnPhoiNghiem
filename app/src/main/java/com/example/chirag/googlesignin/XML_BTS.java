package com.example.chirag.googlesignin;

import java.util.List;

public class XML_BTS {
    private String tenBTS;
    private List<XML_Anten> listAnten_inBTS;

    public XML_BTS() {
    }

    public XML_BTS(String tenBTS, List<XML_Anten> listAnten_inBTS) {
        this.tenBTS = tenBTS;
        this.listAnten_inBTS = listAnten_inBTS;
    }

    public String getTenBTS() {
        return tenBTS;
    }

    public void setTenBTS(String tenBTS) {
        this.tenBTS = tenBTS;
    }

    public List<XML_Anten> getListAnten_inBTS() {
        return listAnten_inBTS;
    }

    public void setListAnten_inBTS(List<XML_Anten> listAnten_inBTS) {
        this.listAnten_inBTS = listAnten_inBTS;
    }

    @Override
    public String toString() {
        return "XML_BTS{" +
                "tenBTS='" + tenBTS + '\'' +
                ", listAnten_inBTS=" + listAnten_inBTS +
                '}';
    }
}
