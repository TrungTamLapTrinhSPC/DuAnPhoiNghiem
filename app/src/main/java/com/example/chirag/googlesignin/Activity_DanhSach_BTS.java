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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;

public class Activity_DanhSach_BTS extends AppCompatActivity {
    ImageButton btnBack,btnThemBTS;
    ListView listview;
    LinearLayout btnThietKe;
    List<DoiTuong_BTS> list_BTS = new ArrayList<>();
    Adapter_DoiTuong_BTS adapter_doiTuong_bts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_bts);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        SuKien();
        NhanBien();
        SettupListView();
    }

    private void SettupListView()
    {
        list_BTS.clear();
        list_BTS.add(new DoiTuong_BTS("","","","",""));
        //Thay đổi thử dòng 30
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
                Intent intent= new Intent(Activity_DanhSach_BTS.this,Activity_DanhSach_Anten.class);
                startActivity(intent);
            }
        });
        btnThemBTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemBTS(Gravity.CENTER);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnThietKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThietKeCot(Gravity.CENTER,"Thiết kế cột","Lưu thiết kế");
            }
        });
    }
    private void AnhXa() {
        listview = findViewById(R.id.listview_bts);
        btnThemBTS = findViewById(R.id.btnThemBTS);
        btnBack = findViewById(R.id.btnBack);
        btnThietKe = findViewById(R.id.btnThietKe);
    }
    //region Dialog
    private void DialogThemBTS(int gravity){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_BTS.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_bts);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = gravity;
        window.setAttributes(windowArr);
        dialogthongso.show();

    };
    private void DialogThietKeCot(int gravity,String title,String titleButton){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_BTS.this,R.style.PauseDialog);
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
        Button btnLuuThongSo = dialogthongso.findViewById(R.id.btnLuu);
        btnLuuThongSo.setText(titleButton);
    };
    //endregion
}
