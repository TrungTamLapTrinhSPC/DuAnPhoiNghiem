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

public class ListviewAdapter extends BaseAdapter {
    private ArrayList<Product> arraylist;
    private List<Product> myobjects = null;
    Context mycontext;
    int myreaource;

    public ListviewAdapter(Context context, int resource, @NonNull List<Product> objects) {
        mycontext = context;
        myreaource = resource;
        myobjects = objects;
        this.arraylist = new ArrayList<Product>();
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

        ImageView img = (ImageView) V.findViewById(R.id.imageView2);
        TextView txtTen = (TextView) V.findViewById(R.id.txtTen);
        TextView txtToado = (TextView) V.findViewById(R.id.txtToaDo);

        img.setImageResource(myobjects.get(position).getImageID());
        txtTen.setText(myobjects.get(position).getTitle());
        txtToado.setText(myobjects.get(position).getDescription());
    return V;

    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myobjects.clear();
        if (charText.length() == 0) {
            myobjects.addAll(arraylist);
        } else {
            for (Product wp : arraylist) {
                if (wp.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getDescription().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    myobjects.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
