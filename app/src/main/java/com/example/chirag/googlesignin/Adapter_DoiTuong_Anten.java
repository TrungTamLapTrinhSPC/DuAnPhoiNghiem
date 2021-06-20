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

public class Adapter_DoiTuong_Anten extends BaseAdapter {
    private ArrayList<DoiTuong_Anten> arraylist;
    private List<DoiTuong_Anten> myobjects = null;
    private ArrayList<String> myList = new ArrayList<String>();
    Context mycontext;
    int myreaource;

    public Adapter_DoiTuong_Anten(List<DoiTuong_Anten> myobjects, Context mycontext, int myreaource) {
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
            V = inflater.inflate(R.layout.item_anten, null);
        }
        TextView tvTenCot = V.findViewById(R.id.tvTenAnten);
        TextView tvChungLoaiAnten = V.findViewById(R.id.tvChungLoaiAnten);
        TextView tvGocPhuongVi = V.findViewById(R.id.tvGocPhuongVi);
        TextView tvCaoDoAnTen = V.findViewById(R.id.tvCaoDo);
        TextView tvNgaySua = V.findViewById(R.id.tvNgaySua);

        tvTenCot.setText(myobjects.get(position).getTenAnten());
        tvChungLoaiAnten.setText(myobjects.get(position).getChungLoaiAnTen());
        tvGocPhuongVi.setText(myobjects.get(position).getGocPhuongVi());
        tvCaoDoAnTen.setText(myobjects.get(position).getCaoDoAnTen());
        tvNgaySua.setText(myobjects.get(position).getNgaySua());
        return V;
    }

}
