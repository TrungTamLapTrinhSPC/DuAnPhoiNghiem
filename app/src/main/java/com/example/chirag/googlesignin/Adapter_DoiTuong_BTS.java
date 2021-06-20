package com.example.chirag.googlesignin;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Adapter_DoiTuong_BTS extends BaseAdapter {
    private ArrayList<DoiTuong_BTS> arraylist;
    private List<DoiTuong_BTS> myobjects = null;
    private ArrayList<String> myList = new ArrayList<String>();
    Context mycontext;
    int myreaource;

    public Adapter_DoiTuong_BTS(List<DoiTuong_BTS> myobjects, Context mycontext, int myreaource) {
        this.myobjects = myobjects;
        this.mycontext = mycontext;
        this.myreaource = myreaource;
    }

    @Override
    public int getCount() {
        return myobjects.size();
    }

    @Override
    public String getItem(int position)
    {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View V = convertView;
        if (null == V)
        {
            LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = inflater.inflate(R.layout.item_bts, null);
        }
        TextView tvTentramGoc = V.findViewById(R.id.tvTenTramgoc);
        TextView tvChungLoaiThietBi = V.findViewById(R.id.tvChungLoaiThietBi);
        TextView tvBangTanHoatDong = V.findViewById(R.id.tvBangTanHoatDong);
        TextView tvSoAnten = V.findViewById(R.id.tvSoAnTen);
        tvTentramGoc.setText(myobjects.get(position).getTenTramGoc());
        tvChungLoaiThietBi.setText(myobjects.get(position).getChungLoaiThietBi());
        tvBangTanHoatDong.setText(myobjects.get(position).getBangTanHoatDong() + " MHz");
        tvSoAnten.setText(myobjects.get(position).getMangSuDung());
        return V;
    }

}
