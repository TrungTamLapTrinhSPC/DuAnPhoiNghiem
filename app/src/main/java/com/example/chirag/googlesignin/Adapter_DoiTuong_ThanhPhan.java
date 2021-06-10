package com.example.chirag.googlesignin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_DoiTuong_ThanhPhan extends BaseAdapter {
    private ArrayList<DoiTuong_ThanhPhan> arraylist;
    private List<DoiTuong_ThanhPhan> myobjects = null;
    private ArrayList<String> myList = new ArrayList<String>();
    Context mycontext;
    int myreaource;

    public Adapter_DoiTuong_ThanhPhan(List<DoiTuong_ThanhPhan> myobjects, Context mycontext, int myreaource) {
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
            V = inflater.inflate(R.layout.item_thanhphan, null);
        }
        TextView edtTenThanhPhan = V.findViewById(R.id.tvTenThanhPhan);
        AutoCompleteTextView edtSuyHao = V.findViewById(R.id.edtSuyHao);

        edtTenThanhPhan.setText(myobjects.get(position).getTenThanhPhan());
        edtSuyHao.setText(myobjects.get(position).getSuyHao());
        return V;
    }

}
