package com.example.chirag.googlesignin;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter_DoiTuong_Cot extends BaseAdapter {
    private ArrayList<DoiTuong_Cot> arraylist;
    private List<DoiTuong_Cot> myobjects = null;
    private ArrayList<String> myList = new ArrayList<String>();
    Context mycontext;
    int myreaource;

    public Adapter_DoiTuong_Cot(List<DoiTuong_Cot> myobjects, Context mycontext, int myreaource) {
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
            V = inflater.inflate(R.layout.item_cot, null);
        }
        TextView tvTenCot = V.findViewById(R.id.tvTenCot);
        TextView tvToaDoX = V.findViewById(R.id.tvToaDoX);
        TextView tvToaDoY = V.findViewById(R.id.tvToaDoY);
        TextView tvChieuCaoCot = V.findViewById(R.id.tvChieuCaoCot);

        tvTenCot.setText(myobjects.get(position).getTenCot());
        tvToaDoX.setText(myobjects.get(position).getViTriX()+" m");
        tvToaDoY.setText(myobjects.get(position).getViTriY()+" m");
        tvChieuCaoCot.setText(myobjects.get(position).getChieuCaoCot()+" m");
        return V;
    }

}
