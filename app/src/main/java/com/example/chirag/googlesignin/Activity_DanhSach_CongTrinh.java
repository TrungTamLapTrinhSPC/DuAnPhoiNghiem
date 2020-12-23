package com.example.chirag.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Activity_DanhSach_CongTrinh extends AppCompatActivity {
    ListView listview;

    List<DoiTuong_CongTrinh> list_CongTrinh = new ArrayList<>();
    Adapter_DoiTuong_CongTrinh adapter_doiTuong_CongTrinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_congtrinh);
        AnhXa();
        SuKien();
        NhanBien();
        SettupListView();
    }

    private void SettupListView()
    {
        list_CongTrinh.clear();
        list_CongTrinh.add(new DoiTuong_CongTrinh("","","","",""));
        //Thay đổi thử dòng 30
        /**HIỂN THỊ RA MÀN HÌNH*/
        adapter_doiTuong_CongTrinh = new Adapter_DoiTuong_CongTrinh(list_CongTrinh, Activity_DanhSach_CongTrinh.this,R.layout.item_congtrinh);
        listview.setAdapter(adapter_doiTuong_CongTrinh);
    }

    private void NhanBien() {
        Intent intent =getIntent();//Nhận biến truyền từ trang danh sách cột

    }

    private void SuKien() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(Activity_DanhSach_CongTrinh.this,Activity_DanhSach_Anten.class);
                startActivity(intent);
            }
        });
    }
    private void AnhXa() {
        listview = findViewById(R.id.listview_congtrinh);

    }

}
