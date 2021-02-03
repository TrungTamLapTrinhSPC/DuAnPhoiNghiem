package com.example.chirag.googlesignin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter_DoiTuong_BTS_CheckList extends BaseAdapter {
    public ArrayList<DoiTuong_BTS_CheckList> arraylist;
    private List<DoiTuong_BTS_CheckList> myobjects = null;
    private ArrayList<String> myList = new ArrayList<String>();
    Context mycontext;
    int myreaource;

    public Adapter_DoiTuong_BTS_CheckList(List<DoiTuong_BTS_CheckList> myobjects, Context mycontext, int myreaource) {
        this.myobjects = myobjects;
        this.mycontext = mycontext;
        this.myreaource = myreaource;
        this.arraylist = new ArrayList<DoiTuong_BTS_CheckList>();
        this.arraylist.addAll(myobjects);
    }
    private class ViewHolder {
        TextView tvTentramGoc ;
        TextView tvChungLoaiThietBi;
        TextView tvBangTanHoatDong ;
        TextView tvSoAnten ;
        CheckBox name;
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

    /*@NonNull
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
    }*/

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.item_checked_bts, null);

            holder = new ViewHolder();
            holder.tvTentramGoc = (TextView) convertView.findViewById(R.id.tvTenTramgoc);
            holder.tvChungLoaiThietBi = (TextView) convertView.findViewById(R.id.tvChungLoaiThietBi);
            holder.tvBangTanHoatDong = (TextView) convertView.findViewById(R.id.tvBangTanHoatDong);
            holder.tvSoAnten = (TextView) convertView.findViewById(R.id.tvSoAnTen);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkbox_BTS);
            convertView.setTag(holder);
            holder.name.setOnCheckedChangeListener((Activity_DanhSach_Cot) mycontext);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        DoiTuong_BTS_CheckList country = arraylist.get(position);
        holder.name.setChecked(country.isActive());
        holder.name.setTag(country);
        holder.tvTentramGoc.setText(myobjects.get(position).getTenTramGoc());
        holder.tvChungLoaiThietBi.setText(myobjects.get(position).getChungLoaiThietBi());
        holder.tvChungLoaiThietBi.setText(myobjects.get(position).getChungLoaiThietBi());
        holder.tvBangTanHoatDong.setText(myobjects.get(position).getBangTanHoatDong() + " MHz");
        holder.tvSoAnten.setText(myobjects.get(position).getMangSuDung());
        return convertView;
    }


    // Filter Class
    public ArrayList<String> filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myobjects.clear();
        myList.clear();
        if (charText.length() == 0)
        {
            myobjects.addAll(arraylist);
        }
        else {
            for (DoiTuong_BTS_CheckList wp : arraylist) {
                if (wp.getTenTramGoc().toLowerCase(Locale.getDefault())
                        .contains(charText) ) {
                    myobjects.add(wp);
                    myList.add(wp.getTenTramGoc().toString());
                }
            }
        }
        notifyDataSetChanged();
        return myList;
    }
}
