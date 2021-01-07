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

public class Adapter_Grid_DoiTuong_AnhChup extends BaseAdapter {
    private ArrayList<DoiTuong_AnhChup> arraylist;
    private List<DoiTuong_AnhChup> myobjects = null;
    private ArrayList<String> myList = new ArrayList<String>();
    Context mycontext;
    int myreaource;

    public Adapter_Grid_DoiTuong_AnhChup(List<DoiTuong_AnhChup> myobjects, Context mycontext, int myreaource) {
        this.myobjects = myobjects;
        this.mycontext = mycontext;
        this.myreaource = myreaource;
    }
    public interface iClickCamera{

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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View V = convertView;
        if (null == V)
        {
            LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = inflater.inflate(R.layout.grid_item_anh_chup, null);
        }
        TextView tvTenHinhAnh = V.findViewById(R.id.tvTenHinhAnh);
        TextView tvNgayChup = V.findViewById(R.id.tvNgayChup);
        ImageView imageview = V.findViewById(R.id.imageview);

        tvTenHinhAnh.setText(myobjects.get(position).getTenAnh());
        tvNgayChup.setText(myobjects.get(position).getNgayChup());

        if (myobjects.get(position).getImageUri()!=null)
        {
            imageview.setVisibility(View.VISIBLE);
            imageview.setImageURI(myobjects.get(position).getImageUri());
        }
        else
        {
            imageview.setImageURI(null);
        }

        return V;
    }
}
