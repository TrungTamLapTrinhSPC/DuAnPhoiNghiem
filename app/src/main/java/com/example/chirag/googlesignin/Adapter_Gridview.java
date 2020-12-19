package com.example.chirag.googlesignin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        ImageView img = (ImageView) V.findViewById(R.id.imageView2);
        TextView txtTen = (TextView) V.findViewById(R.id.txtTen);
        TextView txtToado = (TextView) V.findViewById(R.id.txtToaDo);
        img.setImageResource(doiTuongTram.getImageID());
        txtTen.setText(doiTuongTram.getTitle());
        //txtToado.setText(product.getDescription());
        return V;
    }
}