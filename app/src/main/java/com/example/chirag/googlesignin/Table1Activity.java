package com.example.chirag.googlesignin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.Arrays;

public class Table1Activity extends AppCompatActivity {
    /**KHIA BÁO BIẾN**/
    TableLayout Table3,TableBia,Table2,TableKichThuocMong,Table4,Table5,Table6,TableBiaTTC,TableMstower;
    TextView tvViTriDat,tvChieuCaoDot,tvDoCao;
    EditText tvMaTram,tvDiaDiem,tvKichThuocMong,tvKichThuocChanCot,tvKichThuocDinhCot,tvSoDot;
    ImageButton btnNext,btnBack;
    Button btnLuuTTC,btnTrang1,btnTrang2,btnTruyen,btnTable2,btnTable3,btnTable4,btnTable5,btnTable6,btnLuuSoLieu;
    String MaTram,ViTriDat,KichThuocMong,KichThuocChanCot,KichThuocDinhCot,DanhGiaHienTrang;
    int SoMong=4,SoDot,SoCot=4;
    ArrayList<String> ArraylistHT = new ArrayList<String>();
    ArrayList<String> ArraylistAT = new ArrayList<String>();
    ArrayList<String> listHT = new ArrayList<String>();
    /***https://stackoverflow.com/questions/10514097/how-do-i-get-values-from-a-dynamically-created-android-tablerow**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table1);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        /**Ánh xạ**/
        AnhXa();
        TableBia.setVisibility(View.GONE);
        /**NHẬN CÁC BIẾN*/
        NhanBienTruyen();
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        /**THÊM FOLDER GIÁM SÁT*/
        File GIAMSAT = new File(file,"Data"+ MaTram);
        if (!GIAMSAT.exists())
        {
            if (!GIAMSAT.mkdirs())
            {
                Log.d("App", "failed to create directory");
            }
            else
            {Toast.makeText(getApplicationContext(), "Đã thêm thư mục dữ liệu vào bộ nhớ" , Toast.LENGTH_SHORT).show();
                //restartActivity(MainActivity.this);
            }
        }
        File file2 = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        file2 = new File(file2,"Data"+ MaTram);
        file2 = new File(file2,"TABLEBia.txt");/**TABLE**/
        if (!file2.exists()) DialogThongTinChung();
        tvKichThuocMong.setText(KichThuocMong);
        setDataTableBia();
        setDataTable2();
        setDataTable3();
        setDataTable4();
        setDataTable5();
        setDataTable6();
        setDataTableMstower();
        //setDataTableKichThuocMong();
        /**Button*/
        btnLuuTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThongTinChung();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SaveAllTable();
                Intent intent3 = new Intent(Table1Activity.this, Table2Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });
        btnTrang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAllTable();
                Intent intent3 = new Intent(Table1Activity.this, Table1Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });
        btnTrang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAllTable();
                Intent intent3 = new Intent(Table1Activity.this, Table2Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });


        btnTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=1;i<SoMong+1;i++)
                {
                    TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
                    setColumnData(row,tvKichThuocMong.getText().toString(),1);
                    hideSoftKeyboard(Table1Activity.this);
                }
            }
        });
        btnTable2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dataTable2 = GetTableData(Table2,4);
                    saveDataOnCacher(dataTable2,"TABLE2");        Toast.makeText(Table1Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();


            }
        });
        btnTable3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String dataTable2 = GetTableData(Table3,19);
                    saveDataOnCacher(dataTable2,"TABLE3");        Toast.makeText(Table1Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();


            }
        });
        btnTable4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String dataTable3 = GetTableData(Table4,4);
                    saveDataOnCacher(dataTable3,"TABLE4");        Toast.makeText(Table1Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();


            }
        });
        btnTable5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String dataTable3 = GetTableData(Table5,4);
                    saveDataOnCacher(dataTable3,"TABLE5");        Toast.makeText(Table1Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();


            }
        });
        btnTable6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String dataTable6 = GetTableData(Table6,3);
                    saveDataOnCacher(dataTable6,"TABLE6");        Toast.makeText(Table1Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();


            }
        });
        btnLuuSoLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataTableMS = GetTableData(TableMstower,9);
                saveDataOnCacher(dataTableMS,"TABLEMstower");        Toast.makeText(Table1Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

            }
        });
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
        final  ArrayList<String> listViTriDat = new ArrayList<String>();
        final  ArrayList<String> listcapdobenbt = new ArrayList<String>();
        final  ArrayList<String> listkichthuocchancot = new ArrayList<String>();
        final  ArrayList<String> listkichthuocdinhcot = new ArrayList<String>();
        listLoaiCot.addAll(Arrays.asList("Tự đứng","Cột cóc","Cột chống cứng","Cột monopole","Cột bê tông li tâm"));
        listViTriDat.addAll(Arrays.asList("Trên mái","Dưới đất"));
        listcapdobenbt.addAll(Arrays.asList("B15","B20","B25"));
        listkichthuocchancot.addAll(Arrays.asList("4.0x4.0","5.0x5.0","6.0x6.0","7.0x7.0"));
        listkichthuocdinhcot.addAll(Arrays.asList("0.4x0.4","0.5x0.5","0.6x0.6","0.7x0.7"));
        /**ÁNH XẠ VỚI DÒNG */
        int ColumnCount =2;
        int Rowcount = TableBia.getChildCount();
        for(int i=0;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableBia)).getChildAt(i);
            setDataTable(row,data,i,ColumnCount);
        }
        SetPopup(listLoaiCot,TableBia,3,1);
        SetPopup(listViTriDat,TableBia,7,1);
        SetPopup(listcapdobenbt ,TableBia,8,1);
        SetPopup(listkichthuocchancot,TableBia,5,1);
        SetPopup(listkichthuocdinhcot,TableBia,6,1);
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
        for(int i=1;i<SoMong+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            ////CheckDataTable(row,data,i,ColumnCount);
            //setColumnData(row,KichThuocMong,1);
        }
        /**HIỂN THỊ DẦM D1,D2**/
        Log.d("Vị tri",ViTriDat);
        TableRow BuLong = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-3);
        BuLong.setVisibility(View.VISIBLE);
        setDataTable(BuLong,data,SoMong+1,ColumnCount);
        ////CheckDataTable(BuLong,data,SoMong+2,ColumnCount);

        ////CheckDataTable(MocCo,data,SoMong+3,ColumnCount);
        if (ViTriDat.contains("Trênmái"))
        {
            TableRow Dam1 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-1);
            Dam1.setVisibility(View.VISIBLE);
            setDataTable(Dam1,data,SoMong+2,ColumnCount);
            ////CheckDataTable(Dam1,data,SoMong+4,ColumnCount);
            TableRow Dam2 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-2);
            Dam2.setVisibility(View.VISIBLE);
            setDataTable(Dam2,data,SoMong+3,ColumnCount);
            ////CheckDataTable(Dam2,data,SoMong+5,ColumnCount);
        }
        /**SET POUP HIỆN TRẠNG**/

        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (Table2,i,j);
            }
        }

        for(int i=1;i<Rowcount;i++)
        {
            SetPopupHienTrang(Table2,i,2);
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

        for(int i=1;i<SoMong+1;i++)
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
            setDataTable(Dam1,data,SoMong+1,ColumnCount);
            //CheckDataTable(Dam1,data,SoMong+2,ColumnCount);
            TableRow Dam2 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-2);
            Dam2.setVisibility(View.VISIBLE);
            setDataTable(Dam2,data,SoMong+2,ColumnCount);
            ////CheckDataTable(Dam2,data,SoMong+3,ColumnCount);
        }
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (Table3,i,j);

            }
        }
    }
    /**BẢNG 4**/
    public void setDataTable4() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE4.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table4.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table4)).getChildAt(i);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick(Table4, i, j);
            }
        }
        for(int i=1;i<Rowcount;i++)
        {
            SetPopupHienTrang(Table4,i,2);
            SetPopupAnten(Table4,i,1);

        }


    }
    /**BẢNG 5**/
    public void setDataTable5() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE5.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table5.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table5)).getChildAt(i);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (Table5,i,j);

            }
        }
    }
    /**BẢNG 6.2**/
    public void setDataTable6() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE6.txt");
        String[][] data=DataforPath(file);
        /**DÒNG ĐỐT 1*/
        int ColumnCount =3;
        int Rowcount = Table6.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table6)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }

        for(int i=1;i<SoDot +1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table6)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);

        }
        /****/
        for(int i=1;i<Rowcount;i++)
        {

            for(int j=1;j<=ColumnCount;j++)
            {
                setClick(Table6,i,j);

            }
        }
        for(int i=1;i<Rowcount;i++)
        {
            SetPopupHienTrang(Table6,i,1);
        }

    }
    /**BẢNG MsTower**/
    public void setDataTableMstower() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLEMstower.txt");
        String[][] data=DataforPath(file);
        /**DÒNG ĐỐT 1*/
        int ColumnCount =9;
        int Rowcount = TableMstower.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        /****/
        for(int i=1;i<SoDot +1;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                if(j!=5 || j!=4) setClick (TableMstower,i,j);
            }
        }
        for(int i=1;i<SoDot +1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            TextView tv = (TextView) (((TableRow) row)).getChildAt(0);
            tv.setText("Đốt " + String.valueOf(SoDot+1-i));
            SetButtonFACE(TableMstower,i,4);
            SetButtonPLAN(TableMstower,i,5);
            SetButtonHIP(TableMstower,i,6);
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
        Intent intent = getIntent();
        MaTram = intent.getStringExtra("TenTram");
        SoMong= Integer.parseInt(intent.getStringExtra("SoMong"));
        SoDot= Integer.parseInt(intent.getStringExtra("SoDot"));
        ViTriDat = intent.getStringExtra("ViTriDat");
        KichThuocMong = intent.getStringExtra("KichThuocMong");
        KichThuocChanCot = intent.getStringExtra("KichThuocChanCot");
        KichThuocDinhCot = intent.getStringExtra("KichThuocDinhCot");
        SoCot= Integer.parseInt(intent.getStringExtra("SoChanCot"));
    }
    public void TruyenBien(Intent intent3){
        intent3.putExtra("TenTram", MaTram);  // Truyền một String
        intent3.putExtra("SoMong", String.valueOf(SoMong));  // Truyền một String
        intent3.putExtra("SoDot", String.valueOf(SoDot));  // Truyền một String
        intent3.putExtra("ViTriDat", ViTriDat);  // Truyền một String
        intent3.putExtra("KichThuocMong", KichThuocMong);  // Truyền một String
        intent3.putExtra("KichThuocChanCot", KichThuocChanCot);  // Truyền một String
        intent3.putExtra("KichThuocDinhCot", KichThuocDinhCot);  // Truyền một String
        intent3.putExtra("SoChanCot", String.valueOf(SoCot));  // Truyền một String
        finish();
    }
    public void SaveAllTable() {
        String dataTable3 = GetTableData(Table3,19);
        saveDataOnCacher(dataTable3,"TABLE3");

        String dataTable2 = GetTableData(Table2,4);
        saveDataOnCacher(dataTable2,"TABLE2");

        String dataTableBia = GetTableData(TableBia,2);
        saveDataOnCacher(dataTableBia,"TABLEBia");

        String dataTable4 = GetTableData(Table4,4);
        saveDataOnCacher(dataTable4,"TABLE4");

        String dataTable5 = GetTableData(Table5,4);
        saveDataOnCacher(dataTable5,"TABLE5");

        String dataTable6 = GetTableData(Table6,3);
        saveDataOnCacher(dataTable6,"TABLE6");

        String dataTableMS = GetTableData(TableMstower,9);
        saveDataOnCacher(dataTableMS,"TABLEMstower");
        Toast.makeText(Table1Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
    }
    public void GanBien() {
        if (!tvViTriDat.getText().equals(""))
        {
            KichThuocMong = tvKichThuocMong.getText().toString().replace(" ", "").trim();
            KichThuocDinhCot = tvKichThuocDinhCot.getText().toString().replace(" ", "").trim();
            KichThuocChanCot = tvKichThuocChanCot.getText().toString().replace(" ", "").trim();
            SoDot = Integer.parseInt(tvSoDot.getText().toString().replace(" ", "").trim());
            ViTriDat = tvViTriDat.getText().toString().replace(" ", "").trim();
        }
    }
    public void AnhXa() {
        btnBack  = (ImageButton) findViewById(R.id.btnback);
        btnNext = (ImageButton) findViewById(R.id.btnnext);
        btnTruyen = (Button) findViewById(R.id.btnTruyenVaoBang);
        btnTrang1 = (Button) findViewById(R.id.btnTrang1);
        btnTrang2 = (Button) findViewById(R.id.btnTrang2);
        btnTable2 = (Button) findViewById(R.id.btnTable2);
        btnTable3 = (Button) findViewById(R.id.btnTable3);
        btnTable4 = (Button) findViewById(R.id.btnTable4);
        btnTable5 = (Button) findViewById(R.id.btnTable5);
        btnTable6 = (Button) findViewById(R.id.btnTable6);
        btnLuuSoLieu = (Button) findViewById(R.id.btnLuuSoLieu);
        btnLuuTTC = (Button) findViewById(R.id.btnLuuThongTinChung);
        Table3 = (TableLayout) findViewById(R.id.Table3);
        Table2 = (TableLayout) findViewById(R.id.Table2);
        Table4 = (TableLayout) findViewById(R.id.Table4);
        Table5 = (TableLayout) findViewById(R.id.Table5);
        Table6 = (TableLayout) findViewById(R.id.Table6);
        TableBia = (TableLayout) findViewById(R.id.TableBia);
        TableMstower = (TableLayout) findViewById(R.id.TableMstower);
        TableKichThuocMong= (TableLayout) findViewById(R.id.tableKichThuocMong);
        tvViTriDat = (TextView) findViewById(R.id.tbBiaViTri);
        tvChieuCaoDot = (EditText) findViewById(R.id.tbBiaChieuCaoDot);
        tvDoCao = (EditText) findViewById(R.id.tbBiaDoCao);
        tvMaTram = (EditText) findViewById(R.id.tbBiaMaTram);
        tvDiaDiem = (EditText) findViewById(R.id.tbBiaDiaDiem);
        tvKichThuocMong = (EditText) findViewById(R.id.tbBiaKichThuocMong);
        tvSoDot = (EditText) findViewById(R.id.tbBiaSoDot);
        tvKichThuocChanCot = (EditText) findViewById(R.id.tbBiaKichThuocChanCot);
        tvKichThuocDinhCot = (EditText) findViewById(R.id.tbBiaKichThuocDinhCot);
    }
    @SuppressLint("RestrictedApi")
    private void DialogHienTrang(final TextView textView,final TextView textViewHienTrangTren,final TextView textViewDeXuatTren,final TextView textViewDeXuat)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_copy);
        dialog.show();
        Button btnOK = (Button) dialog.findViewById(R.id.btnRename);
        /**SEARCH VIEW**/
        ArraylistHT.clear();
        DanhGiaHienTrang = StringforPath("DANHGIAHIENTRANG");
        String[] phuluccon = DanhGiaHienTrang.split("--");
        //Log.d("length:", String.valueOf(phuluccon.length));
         for (int j = 1; j < phuluccon.length; j = j + 1) {
            if (j==1||j==2||j==3||j==8)
            {
                String[] phuluccongtac = phuluccon[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                    Log.d("Tên Hiện trạng:",phuluccongtac[i]);
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
    public void SetButtonFACE(TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFACE(textView);
            }
        });

    }
    public void SetButtonPLAN(TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogPLAN(textView);
            }
        });

    }
    public void SetButtonHIP(TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogHIP(textView);
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
    public void saveDataOnCacher(String text,String Name){

    }
    public void SetPopup(final ArrayList<String> listHT,TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(Table1Activity.this,textView);
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
                final PopupMenu popupMenu = new PopupMenu(Table1Activity.this,textView);
                for (String s:listHT)
                    popupMenu.getMenu().add(s);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (textView.getText().toString().equals("")){
                            textView.setText(item.getTitle());
                        } else {
                            textView.setText(textView.getText()+","+ item.getTitle());
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
                ;
                TextView tv = (TextView) (((TableRow) row)).getChildAt(i);
                tv.setText(data[position][i].toString()); // set selected text data into the
                tv.setHeight(65);
                tv.setTextSize(15);
            }
        }catch (Exception e) {}

    }
    public void CheckDataTable(TableRow row,String[][] data,int position,int count){
        try {
            for (int i = 0; i < count; i++) {
                TextView tv = (TextView) (((TableRow) row)).getChildAt(i);
                if (tv.getText().toString().equals("")){
                    tv.setHeight(65);
                    tv.setTextSize(15);
                } // set selected text data into the
                else {
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e) {}
    }
    public void setColumnData(TableRow row,String data,int position){
        try {

            TextView tv = (TextView) (((TableRow) row)).getChildAt(position);
            tv.setText(data.toString()); // set selected text data into the
            tv.setHeight(65);
            tv.setTextSize(15);

        }catch (Exception e) {}

    }
    public String GetTableData(TableLayout table,int ColumnCount) {
        String string="";
        if(table==Table2)
        {
            int Rowcount = Table2.getChildCount();
            for(int i=1;i<SoMong+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
            TableRow BuLong = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-3);
            string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(BuLong,ColumnCount).trim();


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
            for(int i=1;i<SoMong+1;i++)
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
        else if (table==TableBia||table==TableBiaTTC)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
            }
        }
        else if (table==Table4)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);
            }
        }
        else if (table == Table5)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount).replace(",",".");
            }
        }
        else if (table==Table6 )
        {
            for(int i=1;i<SoDot +1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table6)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);
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
        Log.d("item",string);
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
                Log.d("item:",phuluccongtac[i]);
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
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
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
            if (i==0) string = string + tv.getText().toString().trim();
            if (i!=0) string = string +"&"+ tv.getText().toString().trim();

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
        else if (table==Table4 || table == Table5)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowDataCHECK(row,ColumnCount);
            }
        }
        else if (table==Table6)
        {
            for(int i=1;i<SoDot +1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table6)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowDataCHECK(row,ColumnCount);
            }
        }
        return string;
    }
    private void DialogThongTinChung() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_thongtinchung_td);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        /**ÁNH XẠ**/
        final TextView tvViTriDatTTC = (TextView) dialog.findViewById(R.id.tbBiaViTri);
        final EditText tvChieuCaoDotTTC = (EditText) dialog.findViewById(R.id.tbBiaChieuCaoDot);
        final EditText tvDoCaoTTC = (EditText) dialog.findViewById(R.id.tbBiaDoCao);
        final EditText tvMaTramTTC = (EditText) dialog.findViewById(R.id.tbBiaMaTram);
        final EditText tvDiaDiemTTC = (EditText) dialog.findViewById(R.id.tbBiaDiaDiem);
        final TextView tvLoaiCotTTC = (TextView) dialog.findViewById(R.id.tbBiaLoaiCot);
        final EditText tvKichThuocMongTTC = (EditText) dialog.findViewById(R.id.tbBiaKichThuocMong);
        final EditText tvSoDotTTC = (EditText) dialog.findViewById(R.id.tbBiaSoDot);
        final EditText tvKichThuocChanCotTTC = (EditText) dialog.findViewById(R.id.tbBiaKichThuocChanCot);
        final EditText tvKichThuocDinhCotTTC = (EditText) dialog.findViewById(R.id.tbBiaKichThuocDinhCot);
        final Button btnLuu = (Button) dialog.findViewById(R.id.btnLuuThongTinChung);
        TableBiaTTC = (TableLayout) dialog.findViewById(R.id.TableBia);
        final ImageButton btnCopyTTX = (ImageButton) dialog.findViewById(R.id.btnCopy);

        /***/
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLEBia.txt");
        String[][] data=DataforPath(file);

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,MaTram);
        if(mediaStorageDir.exists())
        {
            File[] files=mediaStorageDir.listFiles();
            if(files.length==8) tvLoaiCotTTC.setText("Tự đứng");
            if(files.length==10) tvLoaiCotTTC.setText("Dây co");
        }
        tvMaTramTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] tenTram = MaTram.split("_");
                tvDiaDiemTTC.setText(tenTram[1]);
                tvMaTramTTC.setText(tenTram[0]);
            }
        });
        /***/
        final  ArrayList<String> listLoaiCot = new ArrayList<String>();
        final  ArrayList<String> listViTriDat = new ArrayList<String>();
        final  ArrayList<String> listcapdobenbt = new ArrayList<String>();
        final  ArrayList<String> listkichthuocchancot = new ArrayList<String>();
        final  ArrayList<String> listkichthuocdinhcot = new ArrayList<String>();
        listLoaiCot.addAll(Arrays.asList("Tự đứng","Cột cóc","Cột chống cứng","Cột monopole","Cột bê tông li tâm"));
        listViTriDat.addAll(Arrays.asList("Trên mái","Dưới đất"));
        listcapdobenbt.addAll(Arrays.asList("B15","B20","B25"));
        listkichthuocchancot.addAll(Arrays.asList("4.0x4.0","5.0x5.0","6.0x6.0","7.0x7.0"));
        listkichthuocdinhcot.addAll(Arrays.asList("0.4x0.4","0.5x0.5","0.6x0.6","0.7x0.7"));
        /**ÁNH XẠ VỚI DÒNG */
        int ColumnCount =2;
        int Rowcount = TableBiaTTC.getChildCount();
        for(int i=0;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableBiaTTC)).getChildAt(i);
            setDataTable(row,data,i,ColumnCount);
        }
        String[] tenTram = MaTram.split("_");
        tvDiaDiemTTC.setText(tenTram[1]);
        tvMaTramTTC.setText(tenTram[0]);
        SetPopup(listLoaiCot,TableBiaTTC,3,1);
        SetPopup(listViTriDat,TableBiaTTC,7,1);
        SetPopup(listcapdobenbt ,TableBiaTTC,8,1);
        SetPopup(listkichthuocchancot,TableBiaTTC,5,1);
        SetPopup(listkichthuocdinhcot,TableBiaTTC,6,1);
        /**GÁN BIẾN**/
        btnCopyTTX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrayListTR = new ArrayList<String>();

                File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
                File[] files1=mediaStorageDir.listFiles();
                for (File f:files1)
                {
                    if(f.exists())
                    {
                        File[] files=f.listFiles();
                        if(files.length==8) arrayListTR.add(f.getName());
                    }
                }

                final PopupMenu popupMenu = new PopupMenu(Table1Activity.this, btnCopyTTX);
                for (int j=0 ;j<arrayListTR.size();j++) {
                    popupMenu.getMenu().add(arrayListTR.get(j));
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        File file1 = new File(Environment.getExternalStorageDirectory(),"DataViettel/Data"+item.getTitle());Log.d("1",file1.getPath());
                        File file2 = new File(Environment.getExternalStorageDirectory(),"DataViettel/Data"+MaTram);Log.d("2",file2.getPath());
                        UT.CopyFile(file1,file2,"TABLEBia.txt");
                        dialog.dismiss();
                        DialogThongTinChung();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvViTriDatTTC.getText().equals("")&&!tvSoDotTTC.getText().equals("")&&!tvKichThuocChanCotTTC.getText().equals("")&&!tvKichThuocDinhCotTTC.getText().equals(""))
                {
                    if(CheckDataTable(TableBiaTTC,2,1,100)&& isNumeric(tvSoDotTTC.getText().toString().trim()))
                    {
                        String dataTableBia = GetTableData(TableBiaTTC, 2);
                        saveDataOnCacher(dataTableBia, "TABLEBia");
                        Intent intent3 = new Intent(Table1Activity.this, Table1Activity.class);
                        intent3.putExtra("TenTram", MaTram);  // Truyền một String
                        intent3.putExtra("SoMong","4"); // Truyền một String
                        intent3.putExtra("SoDot", tvSoDotTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("ViTriDat", tvViTriDatTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("KichThuocMong", "0.5x0.5x0.58");  // Truyền một String
                        intent3.putExtra("KichThuocChanCot", tvKichThuocChanCotTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("KichThuocDinhCot", tvKichThuocDinhCotTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("SoChanCot", String.valueOf(4));  // Truyền một String
                        finish();
                        startActivity(intent3);
                    }
                    else Toast.makeText(getApplicationContext(),"Bạn chưa nhập đủ dữ liệu bảng", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void DialogFACE(TextView tv) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_menu_face);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        /**ÁNH XẠ**/
        final ImageView xma = dialog.findViewById(R.id.dhm);
        final ImageView xma_m = dialog.findViewById(R.id.hx2);
        final ImageView dlm = dialog.findViewById(R.id.dlm);
        final ImageView k1_m1 = dialog.findViewById(R.id.drm);
        final ImageView k2 = dialog.findViewById(R.id.k2);
        final ImageView none = dialog.findViewById(R.id.none);

        /**Button**/
        xma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("XMA");
                dialog.dismiss();
            }
        });
        xma_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("XMA,M");
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
        k1_m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("K1,M1");
                dialog.dismiss();
            }
        });
        k2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("K2");
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


    private void DialogPLAN(TextView tv) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_menu_plan);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        /**ÁNH XẠ**/
        final ImageView pl2a = dialog.findViewById(R.id.pl2a);
        final ImageView pl3s = dialog.findViewById(R.id.pl3s);
        final ImageView pld = dialog.findViewById(R.id.pld);
        final ImageView plx = dialog.findViewById(R.id.plx);
        final ImageView none = dialog.findViewById(R.id.none);

        /**Button**/
        pl2a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("PL2A");
                dialog.dismiss();
            }
        });
        pl3s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("PL3S");
                dialog.dismiss();
            }
        });
        pld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("PLD");
                dialog.dismiss();
            }
        });
        plx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("PLX");
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
    private void DialogHIP(TextView tv) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_menu_hip);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        /**ÁNH XẠ**/
        final ImageView hd = dialog.findViewById(R.id.dm);
        final ImageView hk = dialog.findViewById(R.id.dhm);
        final ImageView hka = dialog.findViewById(R.id.dlm);
        final ImageView hs = dialog.findViewById(R.id.drm);
        final ImageView ht = dialog.findViewById(R.id.dmleft);
        final ImageView hx2 = dialog.findViewById(R.id.hx2);
        final ImageView none = dialog.findViewById(R.id.none);

        /**Button**/
        hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HD");
                dialog.dismiss();
            }
        });
        hk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HK");
                dialog.dismiss();
            }
        });
        hka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HKA");
                dialog.dismiss();
            }
        });
        hs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HS");
                dialog.dismiss();
            }
        });
        ht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HT");
                dialog.dismiss();
            }
        });
        hx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HX2");
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

    @SuppressLint("RestrictedApi")
    private void DialogAnten(final TextView textView,final TextView KichThuoc,final TextView KhoiLuong) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_anten_search);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Button btnOK = (Button) dialog.findViewById(R.id.btnRename);
        /**SEARCH VIEW**/
        ArraylistAT.clear();
        ArraylistAT.addAll(Arrays.asList("Cáp đồng trần C50","Dây đồng bọc M35","Cáp thép mạ kẽm D8","Cáp thép mạ kẽm D10","Cáp thép mạ kẽm D12","Cáp thép mạ kẽm D18"));

        SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchHientrang);
        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        searchViewCT.setQueryHint("Tìm nhanh loại dây");
        searchAutoCompleteCT.setThreshold(1);
        searchAutoCompleteCT.setDropDownHeight(500);
        final EditText searchEditTextCK = (EditText)  searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditTextCK.setTextColor(getResources().getColor(R.color.colorPrimary));
        searchEditTextCK.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        ImageView searchMagIcon = (ImageView)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchMagIcon.setImageResource(R.drawable.ic_search_xam_24dp);
        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ArraylistAT);
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
            public void onClick(View view) {
                if(!textView.getText().toString().equals(""))
                    textView.setText(textView.getText()+", "+searchEditTextCK.getText().toString());
                else
                    textView.setText(textView.getText()+searchEditTextCK.getText().toString());

                //Log.d("Anten:",searchEditTextCK.getText().toString());
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
    public void SetPopupAnten(TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView Anten = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        final TextView KichThuoc = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem+1);
        final TextView KhoiLuong = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem+2);
        Anten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAnten(Anten,KichThuoc,KhoiLuong);

            }
        });
        Anten.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogViewHienTrang(Anten.getText().toString(),Anten);
                return false;
            }
        });
    }
    /** TEST STRING IS NUMBER*/
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
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
}
