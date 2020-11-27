package com.example.chirag.googlesignin;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// This is used by top container ListView whose items each contain a horizontal ListView
public class HorizontalScrollableItemAdapter extends ArrayAdapter<HorizontalScrollableItem> {

    private int resourceId;

    public HorizontalScrollableItemAdapter(Context context, int resourceId, HorizontalScrollableItem[] items) {
        super(context, -1, items);

        this.resourceId = resourceId;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {




        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);

            //convertView = inflater.inflate(R.layout.layout_containing_horizontal_listview, null);
            //HorizontalScrollableItem product = getItem(position);


            ViewHolder viewHolder = new ViewHolder();
            viewHolder.recyclerView = (RecyclerView)convertView
                    .findViewById(R.id.recyclerView);
            viewHolder.txtTen = (TextView) convertView.findViewById(R.id.txtBaoCaoHM);

            // Give it a horizontal LinearLayoutManager to make it a horizontal ListView
            if (viewHolder.recyclerView != null) {
                viewHolder.recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
                viewHolder.recyclerView.setLayoutManager(layoutManager);
            }

            convertView.setTag(viewHolder);
        }

        final HorizontalScrollableItem item = getItem(position);
        VerticalScrollableItemAdapter verticalScrollableItemAdapter = item.getAdapter();
        ViewHolder viewHolder = (ViewHolder)convertView.getTag();

        if (viewHolder.recyclerView != null && verticalScrollableItemAdapter != null) {
            viewHolder.recyclerView.setAdapter(verticalScrollableItemAdapter);
            viewHolder.txtTen.setText(item.getTitle());

        }

        return convertView;
    }

    private static class ViewHolder {
        RecyclerView recyclerView;
        TextView txtTen;
    }
}
