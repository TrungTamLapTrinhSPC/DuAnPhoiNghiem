package com.example.chirag.googlesignin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

public class Activity_DanhSach_Cot extends AppCompatActivity {
    ListView listview;
    TextView title;
    ImageButton btnBack,btnMenu,btnThemCot;
    LinearLayout btnAnhChup,btnThietKe;
    List<DoiTuong_Cot> list_Cot = new ArrayList<>();
    Adapter_DoiTuong_Cot adapter_doiTuongCot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_cot);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        NhanBien();
        SuKien();
        SettupListView();
    }
    private void AnhXa() {
        listview = findViewById(R.id.listview_cot);
        btnBack = findViewById(R.id.btnBack);
        btnAnhChup = findViewById(R.id.btnAnhChup);
        btnThietKe = findViewById(R.id.btnThietKe);
        btnMenu = findViewById(R.id.btnMenu);
        btnThemCot = findViewById(R.id.btnThemCot);
        title = findViewById(R.id.title);
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
    //region Sự kiện
    private void SuKien()
    {
        listview.setOnItemClickListener(onItemClickListener);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnAnhChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent= new Intent(Activity_DanhSach_Cot.this,Activity_DanhSach_AnhChup.class);
                startActivity(intent);
            }
        });
        btnThietKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemTram(Gravity.CENTER,"Thiết kế","Lưu thiết kế");

            }
        });
        btnThemCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemCot(Gravity.CENTER,"Thêm cột anten");
            }
        });
    }
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent= new Intent(Activity_DanhSach_Cot.this,Activity_DanhSach_BTS.class);
            startActivity(intent);
        }
    };
    //endregion
    //region Dialog
    private void DialogThemTram(int gravity,String title,String titleButton){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_themtram);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = gravity;
        window.setAttributes(windowArr);
        dialogthongso.show();

        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        Button btnLuuThongSo = dialogthongso.findViewById(R.id.btnLuuThongSo);
        btnLuuThongSo.setText(titleButton);
    };
    private void DialogThemCot(int gravity,String title){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_themcot);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = gravity;
        window.setAttributes(windowArr);
        dialogthongso.show();
        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
    };
    //endregion


}
