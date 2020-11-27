
/***/
package com.example.nhapdulieudodac;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Listview_HT_Adapter_TuDung extends BaseAdapter {
    public ArrayList<HienTrang> arraylist;
    private List<HienTrang> myobjects = null;
    private ArrayList<String> myList = new ArrayList<String>();;
    Context mycontext;
    int myreaource;

    public Listview_HT_Adapter_TuDung(Context context, int resource, @NonNull List<HienTrang> objects) {
        //super(context,resource,objects);
        mycontext = context;
        myreaource = resource;
        myobjects = objects;
        this.arraylist = new ArrayList<HienTrang>();
        this.arraylist.addAll(objects);
    }
    private class ViewHolder {
        TextView code;
        CheckBox name;
    }

    @Override
    public int getCount() {
        return myobjects.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylist;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_item_checkbox, null);

            holder = new ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.title_HT);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkbox_HT);
            convertView.setTag(holder);
            holder.name.setOnCheckedChangeListener((FormMain_CotTuDung) mycontext);


        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        HienTrang country = arraylist.get(position);
        holder.code.setText(country.getUserName());
        holder.name.setChecked(country.isActive());
        holder.name.setTag(country);
        holder.code.setText(myobjects.get(position).getUserName());
        holder.name.setChecked(myobjects.get(position).isActive());
        return convertView;
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
            for (HienTrang wp : arraylist) {
                if (wp.getUserName().toLowerCase(Locale.getDefault())
                        .contains(charText) ) {
                    myobjects.add(wp);
                    myList.add(wp.getUserName().toString());
                }
            }
        }
        notifyDataSetChanged();
        return myList;
    }
    public void updateRecords(List<HienTrang>  users){
        //this.users = users;

        notifyDataSetChanged();
    }
}

