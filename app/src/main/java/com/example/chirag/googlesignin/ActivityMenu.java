package com.example.chirag.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ActivityMenu extends AppCompatActivity {
    LinearLayout btn_TongQuanBaoCao;
    ImageButton btn_BackMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        AnhXa();
        NhanBien();
        SuKien();
    }

    private void SuKien() {
        btn_TongQuanBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ActivityMenu.this,ActivityXemTruocBaoCao.class);
                startActivity(intent);
            }
        });
        btn_BackMenu .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void NhanBien() {

    }

    private void AnhXa() {
        btn_TongQuanBaoCao = findViewById(R.id.btn_TongQuanBaoCao);
        btn_BackMenu = findViewById(R.id.btnBackMenu);
    }
}