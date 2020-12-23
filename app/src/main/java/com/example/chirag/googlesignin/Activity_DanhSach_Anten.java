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

public class Activity_DanhSach_Anten extends AppCompatActivity {
    ListView listview;
    ImageButton btnBack;
    List<DoiTuong_Anten> list_Anten = new ArrayList<>();
    Adapter_DoiTuong_Anten adapter_doiTuongAnten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_anten);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        NhanBien();
        SuKien();
        SettupListView();
    }

    private void SettupListView() {
        list_Anten.clear();
        list_Anten.add(new DoiTuong_Anten("14","Cột A","3","14.9","12.3"));
        list_Anten.add(new DoiTuong_Anten("14","Cột A","3","14.9","12.3"));
        list_Anten.add(new DoiTuong_Anten("14","Cột A","3","14.9","12.3"));

        /**HIỂN THỊ RA MÀN HÌNH*/
        adapter_doiTuongAnten = new Adapter_DoiTuong_Anten(list_Anten, Activity_DanhSach_Anten.this,R.layout.item_anten);
        listview.setAdapter(adapter_doiTuongAnten);

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

    }
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent= new Intent(Activity_DanhSach_Anten.this,Activity_ChiTiet_Anten.class);
            startActivity(intent);
        }
    };
    //endregion
    //region Dialog
    private void DialogThemTram(int gravity,String title){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Anten.this,R.style.PauseDialog);
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
    };
    //endregion
    private void AnhXa() {
        listview = findViewById(R.id.listview_anten);
        btnBack = findViewById(R.id.btnBack);

    }

}
