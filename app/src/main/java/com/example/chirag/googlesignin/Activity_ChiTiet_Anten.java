package com.example.chirag.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Activity_ChiTiet_Anten extends AppCompatActivity {
    LinearLayout btnDanhSachCongTrinh;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_anten);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void AnhXa() {
        btnBack = findViewById(R.id.btnBack);
        btnDanhSachCongTrinh = findViewById(R.id.btnDanhSachCongTrinh);
    }
}
