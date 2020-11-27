package com.example.chirag.googlesignin;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class Listview_CT_Adapter extends ArrayAdapter<CongTac> {
    public Listview_CT_Adapter(@NonNull Context context, int resource, @NonNull List<CongTac> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View V = convertView;
        if (null == V) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = inflater.inflate(R.layout.list_item_congtac, null);
        }
        CongTac product = getItem(position);
        ImageView img = (ImageView) V.findViewById(R.id.imageView2);
        TextView txtHienTrang = (TextView) V.findViewById(R.id.txtHienTrang);
        TextView txtNgayGio = (TextView) V.findViewById(R.id.txtNgayGio);
        ImageView ivCheckBox = (ImageView) V.findViewById(R.id.iv_check_box);
        String DuongDan = product.getDuongDan();
        //img.setImageBitmap(product.getImageID());
        Uri imgUri=Uri.parse(DuongDan);
        img.setImageURI(imgUri);
        txtHienTrang.setText(product.getHientrang());
        txtNgayGio.setText(product.getNgaychup());
        String[] Check = DuongDan.split("--");
        //Log.d("folder ảnh",Check[1]);
        if (product.isSelected())
        {
            ivCheckBox.setBackgroundResource(R.drawable.checked);
            if(Check[1].equals("0"))
            {
                File anhCu = new File(DuongDan);
                File anhMoi = new File(Check[0]+"--1--"+Check[2]);
                anhCu.renameTo(anhMoi);
                //Log.d(" Đã chuyển thành Có lấy",anhMoi.getName());
            }
        }
        else
        {
            ivCheckBox.setBackgroundResource(R.drawable.check);
            if(Check[1].equals("1"))
            {
                File anhCu = new File(DuongDan);
                File anhMoi = new File(Check[0]+"--0--"+Check[2]);
                anhCu.renameTo(anhMoi);
                //Log.d("Không lấy:",anhMoi.getName());
            }
        }


    return V;
    }
    public void updateRecords(List<CongTac>  users){
        //this.users = users;

        notifyDataSetChanged();
    }
}
