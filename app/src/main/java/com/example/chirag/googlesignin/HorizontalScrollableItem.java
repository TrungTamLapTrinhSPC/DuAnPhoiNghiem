package com.example.chirag.googlesignin;

/*
 * List item containing a horizontal scrollable ListView
 */
public class HorizontalScrollableItem {
    private String title;

    private VerticalScrollableItemAdapter adapter;

    public HorizontalScrollableItem(VerticalScrollableItemAdapter adapter,String title) {
        this.adapter = adapter;
        this.title = title;

    }

    public VerticalScrollableItemAdapter getAdapter() {
        return adapter;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
