package com.example.chirag.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Activity_DanhSach_BTS extends AppCompatActivity {
    ListView listview;
    List<DoiTuong_BTS> list_BTS = new ArrayList<>();
    Adapter_DoiTuong_BTS adapter_doiTuong_bts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_bts);
        AnhXa();
        SuKien();
        NhanBien();
        SettupListView();
    }

    private void SettupListView() {
        list_BTS.clear();
        list_BTS.add(new DoiTuong_BTS("","","","",""));

        /**HIỂN THỊ RA MÀN HÌNH*/
        adapter_doiTuong_bts = new Adapter_DoiTuong_BTS(list_BTS, Activity_DanhSach_BTS.this,R.layout.item_bts);
        listview.setAdapter(adapter_doiTuong_bts);
    }

    private void NhanBien() {
        Intent intent =getIntent();//Nhận biến truyền từ trang danh sách cột

    }

    private void SuKien() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void AnhXa() {
        listview = findViewById(R.id.listview_bts);

    }

}
