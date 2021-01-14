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

import java.util.ArrayList;
import java.util.List;

public class Adapter_DoiTuong_CongTrinh extends BaseAdapter {
    private ArrayList<DoiTuong_CongTrinh> arraylist;
    private List<DoiTuong_CongTrinh> myobjects = null;
    private ArrayList<String> myList = new ArrayList<String>();
    Context mycontext;
    int myreaource;

    public Adapter_DoiTuong_CongTrinh(List<DoiTuong_CongTrinh> myobjects, Context mycontext, int myreaource) {
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
            V = inflater.inflate(R.layout.item_congtrinh, null);
            AutoCompleteTextView edtTenCongTrinh = V.findViewById(R.id.edtTenCongTrinh);
            AutoCompleteTextView edtChieuCaoCongTrinh = V.findViewById(R.id.edtChieuCaoCongTrinh);
            AutoCompleteTextView edtKhoangCach = V.findViewById(R.id.edtKhoangCach);
            AutoCompleteTextView edtSoTang = V.findViewById(R.id.edtSoTang);
            AutoCompleteTextView edtGocPhuongVi = V.findViewById(R.id.edtGocPhuongVi);
            AutoCompleteTextView edtDoDay = V.findViewById(R.id.edtDoDay);
            AutoCompleteTextView edtDoRong = V.findViewById(R.id.edtDoRong);

            edtTenCongTrinh.setText(myobjects.get(position).getTenCongTrinh());
            edtChieuCaoCongTrinh.setText(myobjects.get(position).getChieuCao());
            edtKhoangCach.setText(myobjects.get(position).getKhoangCach());
            edtSoTang.setText(myobjects.get(position).getSoTang());
            edtGocPhuongVi.setText(myobjects.get(position).getGocPhuongVi());
            edtDoDay.setText(myobjects.get(position).getDoDay());
            edtDoRong.setText(myobjects.get(position).getDoRong());
        }

        return V;
    }

}
