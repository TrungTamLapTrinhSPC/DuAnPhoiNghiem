package com.example.chirag.googlesignin;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.chirag.googlesignin.SPC.ThietKeAnten;

public class ActivityXemTruocBaoCao extends AppCompatActivity {
    TableLayout TableXemBaoCao;
    String MaTram;
    ImageButton btnChonBTS;
    TextView tvTenBTS;
    File pathThietKeAnten, pathDanhSachCot, pathDanhSachBTS, pathDanhSachAnten;
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_truoc_bao_cao);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        NhanBien();
        setPopupBTS();
        //LayDuLieuJson();
    }

    private void NhanBien() {
        Intent intent = getIntent();
        MaTram = intent.getStringExtra("MaTram");

    }

    private void AnhXa() {

        tvTenBTS = findViewById(R.id.tvTenBTS);

        TableXemBaoCao = (TableLayout) findViewById(R.id.TableXemTruocBaoCao);
        btnChonBTS = findViewById(R.id.btnChonBTS);

    }

    private void LayDuLieuJson(String text, int size) {
       /* String text = "[" +
                "   {\"TenAnten\":\"Anten1\",\"ChungLoaiThietBi\":\"EricssonRBS\",\"SoMayPhat\":\"2\",\"TongCongSuatPhat1\":\"35.48\",\"TongCongSuatPhat2\":\"45.5\",\"ChungLoaiAnten\":\"PFS\",\"LoaiAnten\":\"Định hướng\",\"DoTangIch\":\"17.5\",\"BangTanHoatDong\":\"1800\",\"DoDaiBucXa\":\"1.35\",\"GocNgang\":\"8\",\"GocPhuongVi\":\"65\",\"DoCaoAnten1\":\"31.65\",\"DoCaoAnten2\":\"33\",\"ChungLoaiJumper\":\"1/2\",\"ChieuDaiJumper\":\"6\",\"SuyHaodBJumper\":\"0\",\"SuyHaoJumper\":\"0\",\"ChungLoaiFeeder\":\"7/8\",\"ChieuDaiFeeder\":\"10\",\"SuyHaodBFeeder\":\"0\",\"SuyHaoFeeder\":\"0\",\"TongSuyHao\":\"2\"},\n" +
                "    {\"TenAnten\":\"Anten2\",\"ChungLoaiThietBi\":\"EricssonRBS\",\"SoMayPhat\":\"2\",\"TongCongSuatPhat1\":\"35.48\",\"TongCongSuatPhat2\":\"45.5\",\"ChungLoaiAnten\":\"PFS\",\"LoaiAnten\":\"Định hướng\",\"DoTangIch\":\"17.5\",\"BangTanHoatDong\":\"1800\",\"DoDaiBucXa\":\"1.35\",\"GocNgang\":\"8\",\"GocPhuongVi\":\"65\",\"DoCaoAnten1\":\"31.65\",\"DoCaoAnten2\":\"33\",\"ChungLoaiJumper\":\"1/2\",\"ChieuDaiJumper\":\"6\",\"SuyHaodBJumper\":\"0\",\"SuyHaoJumper\":\"0\",\"ChungLoaiFeeder\":\"7/8\",\"ChieuDaiFeeder\":\"10\",\"SuyHaodBFeeder\":\"0\",\"SuyHaoFeeder\":\"0\",\"TongSuyHao\":\"2\"},\n" +
                "    {\"TenAnten\":\"Anten3\",\"ChungLoaiThietBi\":\"EricssonRBS\",\"SoMayPhat\":\"2\",\"TongCongSuatPhat1\":\"35.48\",\"TongCongSuatPhat2\":\"45.5\",\"ChungLoaiAnten\":\"PFS\",\"LoaiAnten\":\"Định hướng\",\"DoTangIch\":\"17.5\",\"BangTanHoatDong\":\"1800\",\"DoDaiBucXa\":\"1.35\",\"GocNgang\":\"8\",\"GocPhuongVi\":\"65\",\"DoCaoAnten1\":\"31.65\",\"DoCaoAnten2\":\"33\",\"ChungLoaiJumper\":\"1/2\",\"ChieuDaiJumper\":\"6\",\"SuyHaodBJumper\":\"0\",\"SuyHaoJumper\":\"0\",\"ChungLoaiFeeder\":\"7/8\",\"ChieuDaiFeeder\":\"10\",\"SuyHaodBFeeder\":\"0\",\"SuyHaoFeeder\":\"0\",\"TongSuyHao\":\"2\"},\n" +
                "]";*/
        try {
            jsonArray = new JSONArray(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int SoAnten = 2 + size;
        int SoHang = ThietKeAnten.size();

        for (int j = 3; j <= 9; j++) {
            for (int i = 0; i <= SoHang - 1; i++) {
                TableRow row = (TableRow) (((TableLayout) TableXemBaoCao)).getChildAt(i);
                TextView tv = (TextView) (((TableRow) row)).getChildAt(j);
                tv.setVisibility(View.GONE);
            }
        }

        for (int j = 3; j <= SoAnten; j++) {
            try {
                jsonObject = jsonArray.getJSONObject(j - 3);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i <= SoHang - 1; i++) {

                TableRow row = (TableRow) (((TableLayout) TableXemBaoCao)).getChildAt(i);
                TextView tv = (TextView) (((TableRow) row)).getChildAt(j);
                try {
                    tv.setText(jsonObject.getString(ThietKeAnten.get(i)));
                    tv.setVisibility(View.VISIBLE);
                    tv.setTextSize(15);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void setPopupBTS() {
        File pathDanhSachBTS = new File(SPC.pathDataApp_PNDT, MaTram + "/DuLieu/DanhSachBTS");
        ArrayList<String> arr_BTS = new ArrayList<>();
        if (pathDanhSachBTS.exists()) {
            File[] files = pathDanhSachBTS.listFiles();
            for (File file : files) {
                if (file.isFile()) arr_BTS.add(file.getName().replace(".txt", ""));
            }
            SPC.setPopUp_img(ActivityXemTruocBaoCao.this, tvTenBTS, arr_BTS, btnChonBTS);
            tvTenBTS.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    JSONArray arr = new JSONArray();

                    pathDanhSachCot = new File(SPC.pathDataApp_PNDT, MaTram);
                    pathDanhSachCot = new File(pathDanhSachCot, "DuLieu");
                    if (pathDanhSachCot.exists()) {
                        File[] files = pathDanhSachCot.listFiles();
                        for (File file : files) {
                            if (file.isDirectory()) {
                                if (!file.getName().equals("DanhSachBTS")) {
                                    File path_BTS = new File(pathDanhSachCot, file.getName());
                                    if (path_BTS.exists()) {
                                        File[] file_BTS = path_BTS.listFiles();
                                        for (File f_BTS : file_BTS) {
                                            if (f_BTS.isDirectory()) {
                                                if (tvTenBTS.getText().toString().contains(f_BTS.getName())) {
                                                    pathDanhSachAnten = new File(path_BTS, tvTenBTS.getText().toString());
                                                    if (pathDanhSachAnten.exists()) {
                                                        File[] files_anten = pathDanhSachAnten.listFiles();
                                                        for (File file_anten : files_anten) {
                                                            if (file_anten.isDirectory()) {
                                                                pathThietKeAnten = new File(pathDanhSachAnten, file_anten.getName());
                                                                pathThietKeAnten = new File(pathThietKeAnten, "ThietKeAnten.txt");
                                                                if (pathDanhSachAnten.exists()) {
                                                                    JSONObject obj = null;
                                                                    try {
                                                                        obj = new JSONObject(SPC.readText(pathThietKeAnten));
                                                                        arr.put(obj);
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }


                    if (!arr.toString().equals("[]")) {
                        TableXemBaoCao.setVisibility(View.VISIBLE);

                        LayDuLieuJson(arr.toString(), arr.length());
                    } else {
                        TableXemBaoCao.setVisibility(View.GONE);
                    }
                }
            });
        }
    }


}