
/***/
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

public class Listview_HM_Adapter extends BaseAdapter {
    private ArrayList<HangMuc> arraylist;
    private List<HangMuc> myobjects = null;
    private ArrayList<String> myList = new ArrayList<String>();;
    Context mycontext;
    int myreaource;

    public Listview_HM_Adapter(Context context, int resource, @NonNull List<HangMuc> objects) {
        mycontext = context;
        myreaource = resource;
        myobjects = objects;
        this.arraylist = new ArrayList<HangMuc>();
        this.arraylist.addAll(objects);
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View V = convertView;
        if (null == V)
        {
            LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = inflater.inflate(R.layout.list_item_hangmuc, null);
        }

        ImageView img = (ImageView) V.findViewById(R.id.imageView2);
        TextView txtTen = (TextView) V.findViewById(R.id.txtTenHM);

        img.setImageResource(myobjects.get(position).getImageID());
        txtTen.setText(myobjects.get(position).getTitle());
        return V;

    }
    // Filter Class
    public ArrayList<String> filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myobjects.clear();
        myList.clear();
        if (charText.length() == 0)
        {
            myobjects.addAll(arraylist);
        }
        else {
            for (HangMuc wp : arraylist) {
                if (wp.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText) ) {
                    myobjects.add(wp);
                    myList.add(wp.getTitle().toString());
                }
            }
        }
        notifyDataSetChanged();
        return myList;
    }
}

