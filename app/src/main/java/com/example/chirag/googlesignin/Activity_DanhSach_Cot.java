package com.example.chirag.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Activity_DanhSach_Cot extends AppCompatActivity {
    ListView listview;
    List<DoiTuong_Cot> list_Cot = new ArrayList<>();
    Adapter_DoiTuong_Cot adapter_doiTuongCot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_cot);
        AnhXa();
        NhanBien();
        SettupListView();
        SuKien();

    }

    private void SettupListView() {
        list_Cot.clear();
        list_Cot.add(new DoiTuong_Cot("14","Cột A","3","14.9","12.3"));

        /**HIỂN THỊ RA MÀN HÌNH*/
        adapter_doiTuongCot = new Adapter_DoiTuong_Cot(list_Cot, Activity_DanhSach_Cot.this,R.layout.item_cot);
        listview.setAdapter(adapter_doiTuongCot);

    }

    private void NhanBien() {

    }

    private void SuKien()
    {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(Activity_DanhSach_Cot.this,Activity_DanhSach_BTS.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        listview = findViewById(R.id.listview_cot);
    }

}
