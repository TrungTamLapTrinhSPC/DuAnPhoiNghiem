package com.example.chirag.googlesignin;

import android.widget.ListAdapter;

/*
 * List item containing a vertically scrollable ListView
 */
public class VerticalScrollableItem {
    private String title;
    private ListAdapter adapter;

    // Initialize it with the ListAdapter necessary to populate and drive the vertical ListView
    // contained by this item
    public VerticalScrollableItem(ListAdapter adapter,String title) {
        this.adapter = adapter;
        this.title = title;
    }

    public ListAdapter getAdapter() {
        return adapter;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}