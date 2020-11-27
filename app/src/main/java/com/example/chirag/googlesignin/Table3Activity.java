package com.example.chirag.googlesignin;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Table3Activity extends AppCompatActivity {
    /**KHIA BÁO BIẾN**/
    TableLayout Table3,TableBia,Table2,TableKichThuocMong,TableToaDoMong,TableCaoDoDayCo,TableMstower,TableCanhCanhCanh,TableCanhGocCanh;
    TextView tvViTriDat,tvSoMong,tvSoTangDay,tvDoCao,tvSoChanCot,tvGiaChongXoay,tvKichThuocCot,tvDiaDiem,tvSoDot;
    EditText tvMaTram,edtChieuCaoDot,edtGocXoay;
    ImageButton btnNext,btnBack,btnHome;
    ImageView imgMatBang;
    LinearLayout layoutMong1,layoutMong2,layoutMong3,layoutMong4,layoutBangCanh,layoutBangGoc,layoutBangToaDoMongM1;
    Button btnLuuTTC,btnTrang1,btnTrang2,btnTrang3,btnTrang4,btnTruyen,btnTable2,btnTable3,btnLuuChiTietCot,btnChonLoaiMong,btnLuuLoaiMong,btnMoBangCanh,btnMoBangGoc;
    Button btnLuuBangCanh,btnLuuBangGoc;
    String MaTram,ViTriDat,KichThuocMong,KichThuocCot,DanhGiaHienTrang,GiaChongXoay,ChieuCaoDot,LoaiMatBang = "L1",LoaiMong1 ="L1",LoaiMong2 ="L1",LoaiMong3 ="L1",LoaiMong4 ="L1",LoaiDot="L1";
    int SoMong,SoDot,SoTangDay,SoCot;
    ArrayList<String> ArraylistHT = new ArrayList<String>();
    ArrayList<String> listHT = new ArrayList<String>();
    ArrayList<String> listGCX = new ArrayList<String>();
    private TableLayout TableBiaTTC;

    public void AnhXa() {
        btnBack  = (ImageButton) findViewById(R.id.btnback);
        btnNext = (ImageButton) findViewById(R.id.btnnext);

        btnTrang1 = (Button) findViewById(R.id.btnTrang1);
        btnTrang2 = (Button) findViewById(R.id.btnTrang2);
        btnTrang3 = (Button) findViewById(R.id.btnTrang3);
        btnTrang4 = (Button) findViewById(R.id.btnTrang4);
        btnTable2 = (Button) findViewById(R.id.btnTable2);
        btnTable3 = (Button) findViewById(R.id.btnTable3);

        btnLuuTTC = (Button) findViewById(R.id.btnLuuThongTinChung);
        btnLuuChiTietCot = (Button) findViewById(R.id.btnLuuChiTietCot);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnChonLoaiMong = (Button) findViewById(R.id.btnChonLoaiMong);
        btnLuuLoaiMong = (Button) findViewById(R.id.btnLuuLoaiMong);
        btnMoBangCanh = (Button) findViewById(R.id.btnMoBangCanh);
        btnMoBangGoc= (Button) findViewById(R.id.btnMoBangGoc);
        btnLuuBangCanh = (Button) findViewById(R.id.btnLuuBangCanh);
        btnLuuBangGoc = (Button) findViewById(R.id.btnLuuBangGoc);

        Table3 = (TableLayout) findViewById(R.id.Table3);
        Table2 = (TableLayout) findViewById(R.id.Table2);
        TableBia = (TableLayout) findViewById(R.id.TableBia);
        TableCaoDoDayCo = (TableLayout) findViewById(R.id.TableCaoDoDayCo);
        TableKichThuocMong= (TableLayout) findViewById(R.id.tableKichThuocMong);
        TableMstower= (TableLayout) findViewById(R.id.TableMstower);
        TableCanhCanhCanh = (TableLayout) findViewById(R.id.TableCanhCanhCanh);
        TableCanhGocCanh = (TableLayout) findViewById(R.id.TableCanhGocCanh);
        TableToaDoMong = (TableLayout) findViewById(R.id.TableToaDoMong);

        tvViTriDat = findViewById(R.id.tbBiaViTri);
        tvSoMong = findViewById(R.id.tbBiaSoMong);
        tvSoTangDay = findViewById(R.id.tbBiaSoTangDay);
        edtChieuCaoDot = findViewById(R.id.tbBiaChieuCaoDot);
        tvDoCao = findViewById(R.id.tbBiaDoCao);
        tvSoChanCot = findViewById(R.id.tbBiaSoCot);
        tvMaTram = findViewById(R.id.tbBiaMaTram);
        tvDiaDiem = findViewById(R.id.tbBiaDiaDiem);
        tvKichThuocCot = findViewById(R.id.tbBiaKichThuocCot);
        tvSoDot = findViewById(R.id.tbBiaSoDot);
        tvGiaChongXoay = findViewById(R.id.tbBiaGiaChongXoay);
        edtGocXoay = findViewById(R.id.txtGocXoay);

        layoutMong1 = (LinearLayout) findViewById(R.id.LayoutMong1);
        layoutMong2 = (LinearLayout) findViewById(R.id.LayoutMong2);
        layoutMong3 = (LinearLayout) findViewById(R.id.LayoutMong3);
        layoutMong4 = (LinearLayout) findViewById(R.id.LayoutMong4);

        layoutBangCanh = (LinearLayout) findViewById(R.id.LayoutBangcanh);
        layoutBangGoc = (LinearLayout) findViewById(R.id.LayoutBanggoc);
        layoutBangToaDoMongM1= (LinearLayout) findViewById(R.id.LayoutBangToaDoM1);



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table3);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        /**Ánh xạ**/
        AnhXa();
        layoutMong1.setVisibility(View.GONE);layoutMong2.setVisibility(View.GONE);
        layoutMong3.setVisibility(View.GONE);layoutMong4.setVisibility(View.GONE);
        layoutBangGoc.setVisibility(View.GONE);
        //layoutBangGoc.setVisibility(View.GONE);
        //layoutBangToaDoMongM1.setVisibility(View.GONE);
        /**NHẬN CÁC BIẾN*/
        NhanBienTruyen();

        final File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        /**THÊM FOLDER GIÁM SÁT*/
        File GIAMSAT = new File(file,"Data"+ MaTram);
        if (!GIAMSAT.exists())
        {
            if (!GIAMSAT.mkdirs())
            {
                Log.d("App", "failed to create directory");
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Đã thêm thư mục dữ liệu vào bộ nhớ" , Toast.LENGTH_SHORT).show();
                //restartActivity(MainActivity.this);
            }
        }
        File file2 = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        file2 = new File(file2,"Data"+ MaTram);
        File fileBia = new File(file2,"TABLEBia.txt");
        File fileSoAnten = new File(file2,"SoAnten.txt");
        /**TABLE**/
        if (!fileSoAnten.exists()) saveDataOnCacher("5","SoAnten");
        setDataTableBia();
        setDataTableCaoDoDayCo();
        setDataTable2();
        setDataTable3();
        setDataTableToaDoMong();
        setDataTableMstower();
        setDataTableCanhCanhCanh();
        setDataTableCanhGocCanh();
        //setDataTableKichThuocMong();

        SetButtonLoaiMong1();
        SetButtonLoaiMong2();
        SetButtonLoaiMong3();
        SetButtonLoaiMong4();
        SetButtonLuu();
    }

    public void SetButtonLuu()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAllTable();onBackPressed();
                Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SaveAllTable();
                Intent intent3 = new Intent(Table3Activity.this, Table4Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });
        btnTrang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAllTable();
                Intent intent3 = new Intent(Table3Activity.this, Table3Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });
        btnTrang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAllTable();
                Intent intent3 = new Intent(Table3Activity.this, Table4Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });
        btnTrang3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAllTable();
                Intent intent3 = new Intent(Table3Activity.this, Table5Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });
        btnTrang4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAllTable();
                Intent intent3 = new Intent(Table3Activity.this, Table6Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });

        btnTable2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckDataTable(Table2,4,1,70))
                {String dataTable2 = GetTableData(Table2,4);
                    saveDataOnCacher(dataTable2,"TABLE2");}
                Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

            }
        });
        btnTable3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDataTable(Table3,19,1,70)){
                    String dataTable2 = GetTableData(Table3,19);
                    saveDataOnCacher(dataTable2,"TABLE3");
                }        Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAllTable();
                onBackPressed();
            }
        });
        /**Button*/
        btnChonLoaiMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SoMong==3)
                {layoutMong1.setVisibility(View.VISIBLE);
                    layoutMong2.setVisibility(View.VISIBLE);
                    layoutMong3.setVisibility(View.VISIBLE);}
                else if (SoMong==4)
                {layoutMong1.setVisibility(View.VISIBLE);layoutMong2.setVisibility(View.VISIBLE);
                    layoutMong3.setVisibility(View.VISIBLE);layoutMong4.setVisibility(View.VISIBLE);}

            }
        });
        btnLuuLoaiMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                layoutMong1.setVisibility(View.GONE);layoutMong2.setVisibility(View.GONE);
                layoutMong3.setVisibility(View.GONE);layoutMong4.setVisibility(View.GONE);
                String ChiTietMong = "@Loai mặt bằng_" + LoaiMatBang + "@Loai đốt_" + LoaiDot + "@Loai móng_" + LoaiMong1+ "@Loai móng_" + LoaiMong2+ "@Loai móng_" + LoaiMong3+ "@Loai móng_" + LoaiMong4;
                saveDataOnCacher(ChiTietMong,"TABLEChiTietCot");
            }
        });



        tvGiaChongXoay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    final ArrayList<String> listgiachongxoay = new ArrayList<String>();
                    SoTangDay = Integer.parseInt(tvSoTangDay.getText().toString().replace(" ", "").trim());
                    for (int i = 1; i <= SoTangDay; i++) {
                        listgiachongxoay.add(String.valueOf(i));
                    }
                    SetPopup2(listgiachongxoay, TableBia, 13, 1);
                }
                catch (Exception e)
                {
                }
            }

        });
        btnLuuChiTietCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String dataTableBangCanhCanhCanh = GetTableData(TableCaoDoDayCo,3);
                saveDataOnCacher(dataTableBangCanhCanhCanh,"TABLECaoDoDayCo");
                getDataTableMstower();
                Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

            }
        });
        btnMoBangCanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutBangCanh.setVisibility(View.VISIBLE);
                layoutBangToaDoMongM1.setVisibility(View.VISIBLE);
            }
        });
        btnMoBangGoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutBangGoc.setVisibility(View.VISIBLE);
                layoutBangToaDoMongM1.setVisibility(View.VISIBLE);
            }
        });
        btnLuuBangCanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataTableBangCanhCanhCanh = GetTableData(TableCanhCanhCanh,3);
                saveDataOnCacher(dataTableBangCanhCanhCanh,"TABLECanhCanhCanh");
                String dataTableBangCanhGocCanh = GetTableData(TableCanhGocCanh,3);
                saveDataOnCacher(dataTableBangCanhGocCanh,"TABLECanhGocCanh");
                //layoutBangCanh.setVisibility(View.GONE);
                //layoutBangToaDoMongM1.setVisibility(View.GONE);
                Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

            }
        });
        btnLuuBangGoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataTableBangCanhGocCanh = GetTableData(TableCanhGocCanh,3);
                saveDataOnCacher(dataTableBangCanhGocCanh,"TABLECanhGocCanh");
                layoutBangGoc.setVisibility(View.GONE);
                Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void SetButtonLoaiMong1()
    {
        final Button btnMong1 = (Button) findViewById(R.id.btnMong1_Loai1);
        final Button btnMong2 = (Button) findViewById(R.id.btnMong1_Loai2);
        final Button btnMong3 = (Button) findViewById(R.id.btnMong1_Loai3);
        final Button btnMong4 = (Button) findViewById(R.id.btnMong1_Loai4);
        final ImageView imgMong = (ImageView) findViewById(R.id.ImgMong1);

        btnMong1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong1 = "L1";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong1));

            }
        });
        btnMong2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong1 = "L2";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong2));

            }
        });
        btnMong3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong1 = "L3";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong3));

            }
        });
        btnMong4.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong1 = "L4";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong4));

            }
        });
    }
    public void SetButtonLoaiMong2()
    {
        final Button btnMong1 = (Button) findViewById(R.id.btnMong2_Loai1);
        final Button btnMong2 = (Button) findViewById(R.id.btnMong2_Loai2);
        final Button btnMong3 = (Button) findViewById(R.id.btnMong2_Loai3);
        final Button btnMong4 = (Button) findViewById(R.id.btnMong2_Loai4);
        final ImageView imgMong = (ImageView) findViewById(R.id.ImgMong2);

        btnMong1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong2 = "L1";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong1));

            }
        });
        btnMong2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong2 = "L2";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong2));

            }
        });
        btnMong3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong2 = "L3";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong3));

            }
        });
        btnMong4.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong2 = "L4";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong4));

            }
        });
    }
    public void SetButtonLoaiMong3()
    {
        final Button btnMong1 = (Button) findViewById(R.id.btnMong3_Loai1);
        final Button btnMong2 = (Button) findViewById(R.id.btnMong3_Loai2);
        final Button btnMong3 = (Button) findViewById(R.id.btnMong3_Loai3);
        final Button btnMong4 = (Button) findViewById(R.id.btnMong3_Loai4);
        final ImageView imgMong = (ImageView) findViewById(R.id.ImgMong3);

        btnMong1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong3 = "L1";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong1));

            }
        });
        btnMong2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong3 = "L2";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong2));

            }
        });
        btnMong3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong3 = "L3";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong3));

            }
        });
        btnMong4.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong3 = "L4";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong4));

            }
        });
    }
    public void SetButtonLoaiMong4()
    {
        final Button btnMong1 = (Button) findViewById(R.id.btnMong4_Loai1);
        final Button btnMong2 = (Button) findViewById(R.id.btnMong4_Loai2);
        final Button btnMong3 = (Button) findViewById(R.id.btnMong4_Loai3);
        final Button btnMong4 = (Button) findViewById(R.id.btnMong4_Loai4);
        final ImageView imgMong = (ImageView) findViewById(R.id.ImgMong4);

        btnMong1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong4 = "L1";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong1));

            }
        });
        btnMong2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong4 = "L2";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong2));

            }
        });
        btnMong3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong4 = "L3";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong3));

            }
        });
        btnMong4.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong4 = "L4";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.duongbotron5));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.duongbotron2));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong4));

            }
        });
    }
    /**BẢNG Tọa độ móng**/
    public void setDataTableMstower() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLEMstower.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =8;
        int Rowcount = TableMstower.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }
        /****/
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (TableMstower,i,j);
            }
        }
        for(int i=1;i<SoDot+1;i++) {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            SetButtonFACE(TableMstower,i,3);
            setDataTable(row,data,i,ColumnCount);

        }

    }
    public void getDataTableMstower() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLEMstower.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = TableMstower.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }
        /****/
        for(int i=1;i<SoDot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.VISIBLE);

            //STT ĐỐT
            TextView STT = (TextView) (((TableRow) row)).getChildAt(0);
            STT.setText(String.valueOf(i));
            //CHIỀU CAO ĐỐT
            TextView Chieucaodot = (TextView) (((TableRow) row)).getChildAt(1);
            Chieucaodot.setText(String.valueOf(ChieuCaoDot));
            //Kich thước ĐỐT
            TextView rongdot = (TextView) (((TableRow) row)).getChildAt(2);
            String[] KichThuocthan = KichThuocCot.split("x");
            rongdot.setText(String.valueOf(KichThuocthan[0]));

            final  ArrayList<String> listBulong = new ArrayList<String>();
            listBulong.addAll(Arrays.asList("0.3x0.3@M12-4","0.4x0.4@M18-3","0.6x0.6@M22-3"));
            TextView LoaiBulong = (TextView) (((TableRow) row)).getChildAt(4);
            LoaiBulong.setText(TRABANG(KichThuocCot,listBulong));

        }
        TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(1);
        if(!tvGiaChongXoay.getText().equals(""))
        {
            TextView Cross = (TextView) (((TableRow) row)).getChildAt(5);
            Cross.setText("CT");
        }
        /****/
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (TableMstower,i,j);

            }
        }
    }
    /**BẢNG Tọa kích thước cạnh**/
    public void setDataTableCanhCanhCanh() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLECanhCanhCanh.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =3;
        int Rowcount = TableCanhCanhCanh.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableCanhCanhCanh)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoMong+1;i++) {
            TableRow row = (TableRow) (((TableLayout) TableCanhCanhCanh)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
        }
        /****/
/****/
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (TableCanhCanhCanh,i,j);

            }
        }
    }
    /**BẢNG Tọa kích thước góc**/
    public void setDataTableCanhGocCanh() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLECanhGocCanh.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =3;
        int Rowcount = TableCanhGocCanh.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableCanhGocCanh)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoMong+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableCanhGocCanh)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
        }
        /****/
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (TableCanhGocCanh,i,j);
            }
        }
    }
    /**BẢNG Tọa độ móng**/
    public void setDataTableToaDoMong() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLECot.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = TableToaDoMong.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableToaDoMong)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }
        /****/
        for(int i=1;i<SoMong+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableToaDoMong)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (TableToaDoMong,i,j);

            }
        }
    }
    /**BẢNG Cao độ dây co**/
    public void setDataTableCaoDoDayCo() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLECaoDoDayCo.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =3;
        int Rowcount = TableCaoDoDayCo.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableCaoDoDayCo)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        /****/
        for(int i=1;i<SoTangDay+1;i++)
        {

            TableRow row = (TableRow) (((TableLayout) TableCaoDoDayCo)).getChildAt(i);
            setDataTable(row,data,i,ColumnCount);
            row.setVisibility(View.VISIBLE);
            for (int j= 0; j<listGCX.size();j++)
            {
                if (String.valueOf(i).contains(listGCX.get(j).trim()))
                {
                    //Log.d("GCX",listGCX.get(j));
                    setDataTable(row,data,i,ColumnCount);
                    TextView tv = (TextView) (((TableRow) row)).getChildAt(2);
                    tv.setText("CT");
                }
                else
                {
                    setDataTable(row,data,i,ColumnCount);
                }
            }

        }
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (TableCaoDoDayCo,i,j);

            }
        }

    }
    /**BẢNG BÌA**/
    public void setDataTableBia() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLEBia.txt");
        String[][] data=DataforPath(file);
        String[] tenTram = MaTram.split("_");
        tvDiaDiem.setText(tenTram[1]);
        tvMaTram.setText(tenTram[0]);
        final  ArrayList<String> listLoaiCot = new ArrayList<String>();
        final  ArrayList<String> listSoMong = new ArrayList<String>();
        final  ArrayList<String> listViTriDat = new ArrayList<String>();
        final  ArrayList<String> listSoChanCot = new ArrayList<String>();
        final  ArrayList<String> listkichthuoccot = new ArrayList<String>();
        final  ArrayList<String> listchieucaodot = new ArrayList<String>();
        final  ArrayList<String> listcapdobenbt = new ArrayList<String>();
        final  ArrayList<String> listgiachongxoay = new ArrayList<String>();
        listLoaiCot.addAll(Arrays.asList("Tự đứng","Dây co","Cột cóc","Cột chống cứng","Cột monopole","Cột bê tông li tâm"));
        listSoMong.addAll(Arrays.asList("2","3","4","6","8","12"));
        listSoChanCot.addAll(Arrays.asList("3","4"));
        listViTriDat.addAll(Arrays.asList("Trên mái","Dưới đất"));
        listkichthuoccot.addAll(Arrays.asList("0.3x0.3","0.4x0.4","0.6x0.6"));
        listchieucaodot.addAll(Arrays.asList("3","6"));
        listcapdobenbt.addAll(Arrays.asList("B15","B20","B25"));
        //listgiachongxoay.addAll(Arrays.asList("1","2","3","4","5","6","7","8","9","10"));
        /**ÁNH XẠ VỚI DÒNG */
        int ColumnCount =2;
        int Rowcount = TableBia.getChildCount();
        for(int i=0;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableBia)).getChildAt(i);
            setDataTable(row,data,i,ColumnCount);
        }
        SetPopup(listLoaiCot,TableBia,3,1);
        SetPopup(listSoMong,TableBia,8,1);
        SetPopup(listViTriDat,TableBia,6,1);
        SetPopup(listSoChanCot,TableBia,9,1);
        SetPopup(listkichthuoccot,TableBia,5,1);
        SetPopup(listchieucaodot,TableBia,10,1);
        SetPopup(listcapdobenbt ,TableBia,7,1);
        for (int i =1 ;i<= SoDot;i++)
        {
            listgiachongxoay.add(String.valueOf(i));
        }
        SetPopup2(listgiachongxoay ,TableBia,13,1);
        GanBien();

    }
    /**BẢNG 2**/
    public void setDataTable2() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE2.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table2.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }
        /****/
        for(int i=1;i<SoMong+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            ////CheckDataTable(row,data,i,ColumnCount);
            //setColumnData(row,KichThuocMong,1);
        }
        /**HIỂN THỊ DẦM D1,D2**/
        //Log.d("Vị tri",ViTriDat);
        TableRow BuLong = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-3);
        BuLong.setVisibility(View.VISIBLE);
        setDataTable(BuLong,data,SoMong+2,ColumnCount);
        ////CheckDataTable(BuLong,data,SoMong+2,ColumnCount);
        TableRow MocCo = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-4);
        MocCo.setVisibility(View.VISIBLE);
        setDataTable(MocCo,data,SoMong+3,ColumnCount);
        ////CheckDataTable(MocCo,data,SoMong+3,ColumnCount);
        if (ViTriDat.contains("Trênmái"))
        {
            TableRow Dam1 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-1);
            Dam1.setVisibility(View.VISIBLE);
            setDataTable(Dam1,data,SoMong+4,ColumnCount);
            ////CheckDataTable(Dam1,data,SoMong+4,ColumnCount);
            TableRow Dam2 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-2);
            Dam2.setVisibility(View.VISIBLE);
            setDataTable(Dam2,data,SoMong+5,ColumnCount);
            ////CheckDataTable(Dam2,data,SoMong+5,ColumnCount);
        }

    }
    /**BẢNG 3*/
    public void setDataTable3() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE3.txt");
        String[][] data=DataforPath(file);
        /**DÒNG MÓNG M0*/
        int ColumnCount =19;
        int Rowcount = Table3.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }

        for(int i=1;i<SoMong+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            ////CheckDataTable(row,data,i,ColumnCount);
        }
        /**HIỂN THỊ DẦM D1,D2**/
        if (ViTriDat.contains("Trênmái"))
        {
            TableRow Dam1 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-1);
            Dam1.setVisibility(View.VISIBLE);
            setDataTable(Dam1,data,SoMong+2,ColumnCount);
            //CheckDataTable(Dam1,data,SoMong+2,ColumnCount);
            TableRow Dam2 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-2);
            Dam2.setVisibility(View.VISIBLE);
            setDataTable(Dam2,data,SoMong+3,ColumnCount);
            ////CheckDataTable(Dam2,data,SoMong+3,ColumnCount);
        }

    }
    /****/
    public void NhanBienTruyen() {
        /**Tạo **/
        File Template = new File(Environment.getExternalStorageDirectory(),"DataViettel");
        if (!Template.exists()) {
            if (!Template.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
            else {Toast.makeText(getApplicationContext(), "Dữ liệu được lưu trong DataViettel trên máy bạn" , Toast.LENGTH_SHORT).show();
            }
        }
        readFileDanhGia();
        try{
            String[] test = DanhGiaHienTrang.split("--");}
        catch (Exception e){
            DanhGiaHienTrang = UT.HienTrang;
            saveDataOnCacher(DanhGiaHienTrang,"DANHGIAHIENTRANG");
        }
        Intent intent = getIntent();
        MaTram = intent.getStringExtra("TenTram");
        SoMong= Integer.parseInt(intent.getStringExtra("SoMong"));
        SoTangDay= Integer.parseInt(intent.getStringExtra("SoTangDay"));
        SoDot= Integer.parseInt(intent.getStringExtra("SoDot"));
        ViTriDat = intent.getStringExtra("ViTriDat");
        KichThuocMong = intent.getStringExtra("KichThuocMong");
        KichThuocCot = intent.getStringExtra("KichThuocCot");
        SoCot= Integer.parseInt(intent.getStringExtra("SoChanCot"));
        GiaChongXoay = intent.getStringExtra("GiaChongXoay");
        ChieuCaoDot = intent.getStringExtra("ChieuCaoDot");
        listGCX.clear();
        try {
            String[] GaChongXay = GiaChongXoay.split(",");
            for (int i=0;i<GaChongXay.length;i++)
            {
                final boolean boo = isNumeric(String.valueOf(GaChongXay[i].trim()));
                if (boo)
                    listGCX.add(GaChongXay[i].trim()) ;
                Log.d("GCX:",GaChongXay[i]);
            }
        }
        catch (Exception e)
        {

        }
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    public void TruyenBien(Intent intent3){
        intent3.putExtra("TenTram", MaTram);  // Truyền một String
        intent3.putExtra("SoMong", String.valueOf(SoMong));  // Truyền một String
        intent3.putExtra("SoDot", String.valueOf(SoDot));  // Truyền một String
        intent3.putExtra("ViTriDat", ViTriDat);  // Truyền một String
        intent3.putExtra("KichThuocMong", KichThuocMong);  // Truyền một String
        intent3.putExtra("SoTangDay", String.valueOf(SoTangDay));  // Truyền một String
        intent3.putExtra("KichThuocCot", KichThuocCot);  // Truyền một String
        intent3.putExtra("SoChanCot", String.valueOf(SoCot));  // Truyền một String
        intent3.putExtra("GiaChongXoay", GiaChongXoay);  // Truyền một String
        intent3.putExtra("ChieuCaoDot", ChieuCaoDot);  // Truyền một String
        finish();
    }
    public void setClick(TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAllTable();
            }
        });
    }
    public void SaveAllTable(){
        if(CheckDataTable(Table3,19,1,70))
        {String dataTable3 = GetTableData(Table3,19);
            saveDataOnCacher(dataTable3,"TABLE3");}
        if(CheckDataTable(Table2,4,1,60))
        {String dataTable2 = GetTableData(Table2,4);
            saveDataOnCacher(dataTable2,"TABLE2");}
        if(CheckDataTable(TableBia,2,1,70))
        { String dataTableBia = GetTableData(TableBia,2);
            saveDataOnCacher(dataTableBia,"TABLEBia");}
        if(CheckDataTable(TableToaDoMong,4,1,80))
        { String dataTableBia = GetTableData(TableToaDoMong,4);
            saveDataOnCacher(dataTableBia,"TABLECot");}
        String dataTableBangCanhCanhCanh2 = GetTableData(TableCaoDoDayCo,3);
        saveDataOnCacher(dataTableBangCanhCanhCanh2,"TABLECaoDoDayCo");
        String ChiTietMong = "@Loai mặt bằng_" + LoaiMatBang + "@Loai đốt_" + LoaiDot + "@Loai móng_" + LoaiMong1+ "@Loai móng_" + LoaiMong2+ "@Loai móng_" + LoaiMong3+ "@Loai móng_" + LoaiMong4;
        saveDataOnCacher(ChiTietMong,"TABLEChiTietCot");
        String dataTableBia = GetTableData(TableMstower,9);
        saveDataOnCacher(dataTableBia,"TABLEMsTower");
        String dataTableBangCanhCanhCanh = GetTableData(TableCanhCanhCanh,3);
        saveDataOnCacher(dataTableBangCanhCanhCanh,"TABLECanhCanhCanh");
        String dataTableBangCanhGocCanh = GetTableData(TableCanhGocCanh,3);
        saveDataOnCacher(dataTableBangCanhGocCanh,"TABLECanhGocCanh");
        Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
    }
    public void GanBien() {
        if (!tvSoMong.getText().equals(""))
        {
            SoMong = Integer.parseInt(tvSoMong.getText().toString().replace(" ", "").trim());
            SoTangDay = Integer.parseInt(tvSoTangDay.getText().toString().replace(" ", "").trim());
            SoCot = Integer.parseInt(tvSoChanCot.getText().toString().replace(" ", "").trim());
            //int ChieucaoDot = Integer.parseInt(tvChieuCaoDot.getText().toString().replace(" ", "").trim());
            //int DoCaoCot = Integer.parseInt(tvDoCao.getText().toString().replace(" ", "").trim());

            SoDot = Integer.parseInt(tvSoDot.getText().toString().replace(" ", "").trim());
            ViTriDat = tvViTriDat.getText().toString().replace(" ", "").trim();
            KichThuocCot = tvKichThuocCot.getText().toString().replace(" ", "").trim();
            GiaChongXoay = tvGiaChongXoay.getText().toString().replace(" ", "").trim();
            ChieuCaoDot = edtChieuCaoDot.getText().toString().replace(" ", "").trim();
        }

    }
    @SuppressLint("RestrictedApi")
    private void DialogHienTrang(final TextView textView,final TextView textViewHienTrangTren,final TextView textViewDeXuatTren,final TextView textViewDeXuat) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_copy);
        dialog.show();
        Button btnOK = (Button) dialog.findViewById(R.id.btnRename);
        /**SEARCH VIEW**/
        ArraylistHT.clear();
        DanhGiaHienTrang = StringforPath("DANHGIAHIENTRANG");
        String[] phuluccon = DanhGiaHienTrang.split("--");
        //Log.d("length:", String.valueOf(phuluccon.length));
        //Log.d("read 1:",phuluccon[1]);
        for (int j = 1; j < phuluccon.length; j = j + 1) {
            if (j==1||j==2||j==3||j==8)
            {
                String[] phuluccongtac = phuluccon[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                    //Log.d("Tên Hiện trạng:",phuluccongtac[i]);
                    ArraylistHT.add(phuluccongtac[i]);
                }
            }
        }
        Button btnCopy = (Button) dialog.findViewById(R.id.btnNhuTren);
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(textViewHienTrangTren.getText());
                textViewDeXuat.setText(textViewDeXuatTren.getText());
                dialog.dismiss();
            }
        });

        SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchHientrang);
        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        searchViewCT.setQueryHint("Nhập ghi chú");
        searchAutoCompleteCT.setThreshold(1);
        searchAutoCompleteCT.setDropDownHeight(500);


        final EditText searchEditTextCK = (EditText)  searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditTextCK.setTextColor(getResources().getColor(R.color.colorPrimary));
        searchEditTextCK.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        ImageView searchMagIcon = (ImageView)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchMagIcon.setImageResource(R.drawable.ic_search_xam_24dp);


        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ArraylistHT);


        searchAutoCompleteCT.setAdapter(newsAdapterCT);
        searchAutoCompleteCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoCompleteCT.setText(queryString);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                textView.setText(textView.getText() + "\n- " + searchEditTextCK.getText().toString());
                if (!DanhGiaHienTrang.contains(searchEditTextCK.getText().toString()))
                    if (!DanhGiaHienTrang.equals(searchEditTextCK.getText().toString()))
                    {
                        DanhGiaHienTrang = DanhGiaHienTrang +"_"+searchEditTextCK.getText().toString()+"_";
                        saveDataOnTemplate(DanhGiaHienTrang,"DANHGIAHIENTRANG");
                    }
                dialog.dismiss();
            }
        });
        Button btnThoat = (Button) dialog.findViewById(R.id.btnthoat);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    public void SetPopupHienTrang(TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        TableRow rowHienTrangTren = (TableRow) (((TableLayout) table)).getChildAt(rowItem-1);
        final TextView textViewHienTrangTren = (TextView) (((TableRow) rowHienTrangTren)).getChildAt(columnItem);
        final TextView textViewDeXuatTren = (TextView) (((TableRow) rowHienTrangTren)).getChildAt(columnItem+1);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        final TextView textViewDeXuat = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem+1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHienTrang(textView,textViewHienTrangTren,textViewDeXuatTren,textViewDeXuat);
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogViewHienTrang(textView.getText().toString(),textView);
                return false;
            }
        });
    }
    public String StringforPath(String name){
        String s=null;
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "Template");
            file = new File(file, name+".txt");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            s = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    public void saveDataOnTemplate(String text,String Name){
        String content = text;
        File file;
        FileOutputStream outputStream;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "Template");
            file = new File(file, Name+".txt");
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SetPopup(final ArrayList<String> listHT,TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(Table3Activity.this,textView);
                for (String s:listHT)
                    popupMenu.getMenu().add(s);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        textView.setText(item.getTitle());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }
    public void SetPopup2(final ArrayList<String> listHT,TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(Table3Activity.this,textView);
                for (String s:listHT)
                    popupMenu.getMenu().add(s);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (textView.getText().toString().equals("")){
                            textView.setText(item.getTitle());
                        } else {
                            textView.setText(textView.getText().toString().trim()+","+ item.getTitle());
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogViewHienTrang(textView.getText().toString(),textView);
                return false;
            }
        });
    }
    public void setDataTable(TableRow row,String[][] data,int position,int count){
        try {
            for (int i = 0; i < count; i++) {
                TextView tv = (TextView) (((TableRow) row)).getChildAt(i);
                tv.setText(data[position][i].toString()); // set selected text data into the
                tv.setHeight(65);
                tv.setTextSize(15);
            }
        }catch (Exception e) {}

    }
    public String GetTableData(TableLayout table,int ColumnCount) {
        String string="";
        if(table==Table2)
        {
            int Rowcount = Table2.getChildCount();
            for(int i=1;i<SoMong+2;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
            TableRow BuLong = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-3);
            string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(BuLong,ColumnCount).trim();

            TableRow MocCo = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-4);
            string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(MocCo,ColumnCount).trim();

            if (ViTriDat.contains("Trênmái"))
            {
                TableRow Dam1 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-1);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(Dam1,ColumnCount).trim();
                TableRow Dam2 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-2);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(Dam2,ColumnCount).trim();

            }
        }
        else if(table==Table3)
        {
            int Rowcount = Table3.getChildCount();
            for(int i=1;i<SoMong+2;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
            if (ViTriDat.contains("Trênmái"))
            {
                TableRow Dam1 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-1);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(Dam1,ColumnCount).trim();
                TableRow Dam2 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-2);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(Dam2,ColumnCount).trim();

            }
        }
        else if(table==TableToaDoMong|| table == TableCanhCanhCanh|| table == TableCanhGocCanh)
        {
            for(int i=1;i<SoMong+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
        }
        else if(table==TableMstower)
        {
            for(int i=1;i<SoDot+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
        }
        else if(table==TableCaoDoDayCo)
        {
            for(int i=1;i<SoTangDay+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) TableCaoDoDayCo)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
        }
        else if (table==TableBia||table==TableBiaTTC)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
            }
        }
        return string;
    }
    public String[][] DataforPath(File file) {
        /**ĐỌC ĐƯỜNG DẪN*/
        String string="";
        int rowCount;
        int columnCount;
        BufferedReader input = null;
        try {

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null)
            {
                buffer.append(line).append("\n");
            }
            string = buffer.toString();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        /**Gán vào mảng**/
        //Log.d("item",string);
        String[] phuluccon = string.split("@");
        rowCount = 0;
        columnCount = 0;
        for (int j = 0; j < phuluccon.length; j++) {
            String[] phuluccongtac = phuluccon[j].split("_");
            rowCount ++;
            for (int i = 0; i < phuluccongtac.length; i++) {
                //Log.d("item:",phuluccongtac[i]);
                columnCount++;
                //mangString[j][i] = phuluccongtac[i].toString();
            }
        }
        String mangString[][] = new String[rowCount][columnCount];
        for (int j = 0; j < phuluccon.length; j++) {
            String[] phuluccongtac = phuluccon[j].split("_");
            for (int i = 0; i < phuluccongtac.length; i++) {
                //Log.d("item:",phuluccongtac[i]);
                mangString[j][i] = phuluccongtac[i].toString();
            }
        }
        return mangString;
    }
    private void DialogViewHienTrang(String string,final TextView textView) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_view);
        dialog.show();
        final EditText txtTentram = (EditText) dialog.findViewById(R.id.txtHienTrang);
        txtTentram.setText(string);
        Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(txtTentram.getText().toString());
                listHT.clear();
                String[] HT = textView.getText().toString().split(",");
                for (String s:HT)
                {
                    listHT.add(s);
                }
                dialog.dismiss();
            }
        });
        Button btnThoat = (Button) dialog.findViewById(R.id.btnthoat);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
    }
    public void readFileDanhGia(){

        BufferedReader input = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "Template");
            file = new File(file, "DANHGIAHIENTRANG.txt");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            DanhGiaHienTrang = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Boolean CheckDataTable(TableLayout table,int ColumnCount,int Soheader,int PhanTram){
        /*
        String string = GetTableDataCHECK(table,ColumnCount);
        string = string.replace("[","@");
        string = string.replace("]","");
        string = string.replace("#","_");
        string = string.replace("  ","");
        string = string.replace("   ","");
        //Log.d("data:", String.valueOf(string));
        String[] phuluccon = string.split("@");
        int rowCount=phuluccon.length;
        for (int j = 0; j < phuluccon.length; j++) {
            String[] phuluccongtac = phuluccon[j].split("_");
            for (int i = 0; i < phuluccongtac.length; i++) {
                //Log.d("item:",phuluccongtac[i]);
                //mangString[j][i] = phuluccongtac[i].toString();
            }
        }
        Boolean Ktra = false;
        int tongSoO = 0;
        tongSoO = (rowCount-Soheader)*(ColumnCount-1);
        int soORong = 0;
        //Log.d("Tổng số ô lưu:",String.valueOf(rowCount)+"*"+String.valueOf(ColumnCount)+"="+ String.valueOf(tongSoO));
        String mangString[][] = new String[rowCount][ColumnCount];
        for (int j = 0; j < phuluccon.length; j++) {
            String[] phuluccongtac = phuluccon[j].split("_");
            for (int i = 0; i < phuluccongtac.length; i++) {
                //Log.d("item kiem tra:",phuluccongtac[i]);
                mangString[j][i] = phuluccongtac[i].toString();
                if (phuluccongtac[i].toString().equals(" ")) soORong ++;
            }
        }
        int phantram = (((soORong*100)/(tongSoO)));
        if (  phantram  > PhanTram){
            Ktra=true;
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập đủ dữ liệu bảng"+"\nSố ô chưa có dữ liệu: "+soORong, Toast.LENGTH_SHORT).show();
        }
        else if(phantram  < PhanTram)
         */
        Boolean Ktra=true;
        return Ktra;
    }
    public String GetRowData(TableRow row,int count) {
        String string="[";
        /**DÒNG MÓNG M0*/
        /**GET TEXT ON ROW**/
        //final String[] str = new String[count];
        for (int i = 0; i < count; i++)
        {
            TextView tv = (TextView) (((TableRow) row)).getChildAt(i);
            if (i==0) string = string + tv.getText().toString().trim().replace("&","");
            if (i!=0) string = string +"&"+ tv.getText().toString().trim().replace("&","");

            //str[i] = tv.getText().toString(); // set selected text data into the
        }
        string = string+"]";
        //string = Arrays.toString(str);
        return string;
    }
    public String GetRowDataCHECK(TableRow row,int count) {
        String string="[";
        /**DÒNG MÓNG M0*/
        /**GET TEXT ON ROW**/
        //final String[] str = new String[count];
        for (int i = 0; i < count; i++)
        {
            TextView tv = (TextView) (((TableRow) row)).getChildAt(i);
            if (i==0) string = string + tv.getText().toString().trim().replace("#","");
            if (i!=0) string = string +"# "+ tv.getText().toString().trim().replace("#","");;

            //str[i] = tv.getText().toString(); // set selected text data into the
        }
        string = string+"]";

        //string = Arrays.toString(str);
        return string;

    }
    public String GetTableDataCHECK(TableLayout table,int ColumnCount) {
        String string="";
        if(table==Table2)
        {
            int Rowcount = Table2.getChildCount();
            for(int i=1;i<SoMong+2;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowDataCHECK(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
            TableRow BuLong = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-3);
            string=string.replace("_ ","_").replace("  "," ").trim() + GetRowDataCHECK(BuLong,ColumnCount).trim();

            TableRow MocCo = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-4);
            string=string.replace("_ ","_").replace("  "," ").trim() + GetRowDataCHECK(MocCo,ColumnCount).trim();

            if (ViTriDat.contains("Trênmái"))
            {
                TableRow Dam1 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-1);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowDataCHECK(Dam1,ColumnCount).trim();
                TableRow Dam2 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-2);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowDataCHECK(Dam2,ColumnCount).trim();

            }

        }
        else if(table==TableToaDoMong)
        {
            for(int i=1;i<SoMong+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) TableToaDoMong)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowDataCHECK(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
        }
        else if(table==TableCaoDoDayCo)
        {
            for(int i=1;i<SoTangDay+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) TableCaoDoDayCo)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowDataCHECK(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
        }
        else if(table==Table3)
        {
            int Rowcount = Table3.getChildCount();
            for(int i=1;i<SoMong+2;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowDataCHECK(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
            if (ViTriDat.contains("Trênmái"))
            {
                TableRow Dam1 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-1);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowDataCHECK(Dam1,ColumnCount).trim();
                TableRow Dam2 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-2);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowDataCHECK(Dam2,ColumnCount).trim();

            }
        }
        else if (table==TableBia||table==TableBiaTTC)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowDataCHECK(row,ColumnCount).trim();
            }
        }
        return string;
    }
    public void saveDataOnCacher(String text,String Name){

    }
    public String TRABANG (String dauvao,ArrayList<String> chuoivao) {
        String daura=null;
        for (int i=0;i< chuoivao.size();i++){
            if (chuoivao.get(i).contains(dauvao)){
                daura = chuoivao.get(i).split("@")[1];
                break;
            }
        }
        return daura;
    }

    public void SetButtonFACE(TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFACEdayco(textView);
            }
        });

    }
    private void DialogFACEdayco(TextView tv) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_menu_face_dayco);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        /**ÁNH XẠ**/
        final ImageView dhm = dialog.findViewById(R.id.dhm);
        final ImageView dlm = dialog.findViewById(R.id.dlm);
        final ImageView dm = dialog.findViewById(R.id.dm);
        final ImageView drm = dialog.findViewById(R.id.drm);
        final ImageView dmleft = dialog.findViewById(R.id.dmleft);
        final ImageView none = dialog.findViewById(R.id.none);

        /**Button**/
        dhm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                tv.setText("DHM");
                dialog.dismiss();
            }
        });
        dlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("DLM");
                dialog.dismiss();
            }
        });

        dm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("DM");
                dialog.dismiss();
            }
        });
        drm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("DRM");
                dialog.dismiss();
            }
        });

        dmleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("DM");
                dialog.dismiss();
            }
        });
        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
                dialog.dismiss();
            }
        });

    }
}
