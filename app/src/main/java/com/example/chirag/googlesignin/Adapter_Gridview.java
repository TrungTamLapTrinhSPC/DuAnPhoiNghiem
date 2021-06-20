package com.example.chirag.googlesignin;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter_Gridview extends ArrayAdapter<DoiTuong_Tram> {
    public Adapter_Gridview(@NonNull Context context, int resource, @NonNull List<DoiTuong_Tram> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View V = convertView;
        if (null == V) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = inflater.inflate(R.layout.grid_item, null);
        }
        DoiTuong_Tram doiTuongTram = getItem(position);
        TextView txtMaTram = (TextView) V.findViewById(R.id.txtMaTram);
        TextView txtNgay = (TextView) V.findViewById(R.id.txtNgay);
        TextView txtTramGoc = (TextView) V.findViewById(R.id.txtTramGoc);

        txtMaTram.setText(doiTuongTram.getMaTram());
        txtNgay.setText(doiTuongTram.getNgaySua());
        txtTramGoc.setText(doiTuongTram.getSoTramGoc());
        return V;
    }
}