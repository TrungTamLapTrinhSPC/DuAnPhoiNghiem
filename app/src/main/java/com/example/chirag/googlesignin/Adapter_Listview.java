package com.example.chirag.googlesignin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter_Listview extends BaseAdapter {
    private ArrayList<DoiTuong_Tram> arraylist;
    private List<DoiTuong_Tram> myobjects = null;
    Context mycontext;
    int myreaource;

    public Adapter_Listview(Context context, int resource, @NonNull List<DoiTuong_Tram> objects) {
        mycontext = context;
        myreaource = resource;
        myobjects = objects;
        this.arraylist = new ArrayList<DoiTuong_Tram>();
        this.arraylist.addAll(objects);
    }


    @Override
    public int getCount() {
        return myobjects.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View V = convertView;
        if (null == V) {
            LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = inflater.inflate(R.layout.list_item, null);
        }

        TextView txtMaTram = (TextView) V.findViewById(R.id.txtMaTram);
        TextView txtDiaDiem = (TextView) V.findViewById(R.id.txtDiaDiem);
        TextView txtNgay = (TextView) V.findViewById(R.id.txtNgay);
        TextView txtTramGoc = (TextView) V.findViewById(R.id.txtTramGoc);

        txtMaTram.setText(myobjects.get(position).getMaTram());
        txtDiaDiem.setText(myobjects.get(position).getDiaDiem());
        txtNgay.setText(myobjects.get(position).getNgaySua());
        txtTramGoc.setText(myobjects.get(position).getSoTramGoc());
    return V;

    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myobjects.clear();
        if (charText.length() == 0) {
            myobjects.addAll(arraylist);
        } else {
            for (DoiTuong_Tram wp : arraylist) {
                if (wp.getMaTram().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getMaTram().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    myobjects.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
