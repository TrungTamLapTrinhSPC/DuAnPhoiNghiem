package com.example.chirag.googlesignin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import java.util.List;

public class Multip_list_Adapter extends ArrayAdapter<Multip_list> {
    public Multip_list_Adapter(@NonNull Context context, int resource, @NonNull List<Multip_list> objects) {
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View V = convertView;
        if (null == V) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = inflater.inflate(R.layout.mul_list_item, null);
        }
        Multip_list multip_list = getItem(position);

        ImageView img = (ImageView) V.findViewById(R.id.mul_imageView);
        img.setImageURI(multip_list.getImageID());
        //Picasso.get().load(multip_list.getImageID()).into(img);
    return V;
    }
}
