package com.example.chirag.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Activity_ChiTiet_Anten extends AppCompatActivity {
    LinearLayout btnDanhSachCongTrinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_anten);
        AnhXa();
        SuKien();
    }

    private void SuKien() {
        btnDanhSachCongTrinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Activity_ChiTiet_Anten.this,Activity_DanhSach_CongTrinh.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        btnDanhSachCongTrinh = findViewById(R.id.btnDanhSachCongTrinh);
    }
}
