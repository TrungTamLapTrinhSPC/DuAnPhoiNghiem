package com.example.chirag.googlesignin;

import android.annotation.SuppressLint;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Table2Activity extends AppCompatActivity {
    /**KHIA BÁO BIẾN**/
    TableLayout Table10,Table11,Table12_V0,Table12_V1,Table12_V2,Table12_V3,Table101;
    Button btnTrang1,btnTrang2,btnTrang3,btnTrang4,btnTruyen,btnTable10,btnTable11,btnTable12,btnTinhDoNghiengCot,btnThemDong;
    EditText edtBuLong,edtKheHo;
    TextView X1,X2,X3,Y1,Y2,Y3,H1,H2,H3,Dx1,Dx2,Dx3,Dy1,Dy2,Dy3,e1,e2,e3,Dl1,Dl2,Dl3,KL1,Kl2,KL3;

    ImageButton btnNext,btnBack;
    String MaTram,ViTriDat,KichThuocMong,KichThuocCot,DanhGiaHienTrang,KichThuocChanCot,KichThuocDinhCot;
    int SoMong=4,SoDot,SoTangDay,SoCot=4,SoAnten;
    ArrayList<String> listHT = new ArrayList<String>();
    ArrayList<String> ArraylistHT = new ArrayList<String>();
    ArrayList<String> ArraylistAT = new ArrayList<String>();
    ArrayList<String> ArraylistKG = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table2);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        /**Ánh xạ**/
        AnhXa();
        /**NHẬN CÁC BIẾN*/

        NhanBienTruyen();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAllTable();
                Intent intent3 = new Intent(Table2Activity.this, Table1Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);            }
        });
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SaveAllTable();
                Intent intent3 = new Intent(Table2Activity.this, Table1Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });
        btnTrang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAllTable();
                Intent intent3 = new Intent(Table2Activity.this, Table1Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });
        btnTrang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAllTable();
                Intent intent3 = new Intent(Table2Activity.this, Table2Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });
        btnTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=1;i<SoDot+1;i++)
                {
                    TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
                    setColumnData(row,edtBuLong.getText().toString(),1);
                    setColumnData(row,edtKheHo.getText().toString(),2);
                }
            }
        });
        btnTable10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckDataTable(Table10,5,1,70))
                {
                    String dataTable3 = GetTableData(Table10,5);
                    saveDataOnCacher(dataTable3,"TABLE10");
                }
            }
        });
        btnTable11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckDataTable(Table11,5,1,70))
                {
                    String dataTable3 = GetTableData(Table11,5);
                    saveDataOnCacher(dataTable3,"TABLE11");
                }
            }
        });
        btnTable12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDataTable(Table12_V0,4,1,70)&&CheckDataTable(Table12_V1,4,1,70)
                        &&CheckDataTable(Table12_V2,4,1,70)&&CheckDataTable(Table12_V3,4,1,70))
                {
                    String dataTable12_V0 = GetTableData(Table12_V0,4);
                    saveDataOnCacher(dataTable12_V0,"TABLE12_V0");
                    String dataTable12_V1 = GetTableData(Table12_V1,4);
                    saveDataOnCacher(dataTable12_V1,"TABLE12_V1");
                    String dataTable12_V2 = GetTableData(Table12_V2,4);
                    saveDataOnCacher(dataTable12_V2,"TABLE12_V2");
                    String dataTable12_V3 = GetTableData(Table12_V3,4);
                    saveDataOnCacher(dataTable12_V3,"TABLE12_V3");
                    String dataTable12 = dataTable12_V0+dataTable12_V1+dataTable12_V2+dataTable12_V3;
                    saveDataOnCacher(dataTable12,"TABLE12");
                }

            }
        });
        btnTinhDoNghiengCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TinhDoNghiengCot();
            }
        });
        btnThemDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoAnten ++;
                saveDataOnCacher(String.valueOf(SoAnten),"SoAnten");
                File file2 = new File(Environment.getExternalStorageDirectory(), "DataViettel");
                file2 = new File(file2,"Data"+ MaTram);
                File fileSoAnten = new File(file2,"SoAnten.txt");
                //Log.d("Text", fileSoAnten.getPath());
                if (fileSoAnten.exists())
                {
                    String text = readText(fileSoAnten);

                    SoAnten = Integer.parseInt(text.trim());
                    //Log.d("SoAnten", String.valueOf(SoAnten));

                }
                for(int i=1;i<SoAnten+1;i++)
                {
                    TableRow row = (TableRow) (((TableLayout) Table11)).getChildAt(i);
                    row.setVisibility(View.VISIBLE);
                    //setDataTable(row,data,i,ColumnCount);
                    //CheckDataTable(row,data,i,ColumnCount);
                }
            }
        });
        /**TABLE**/
        setDataTable10();
        setDataTable11();
        setDataTable12_V0();
        setDataTable12_V1();
        setDataTable12_V2();
        setDataTable12_V3();
    }

    /**BẢNG 10**/
    public void setDataTable10() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE10.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =5;
        int Rowcount = Table10.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoDot;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<Rowcount;i++)
        {

            for(int j=1;j<=ColumnCount;j++)
            {
                setClick(Table10,i,j);
            }
        }
        for(int i=1;i<Rowcount;i++)
        {
            SetPopupHienTrang(Table10,i,3);
        }

    }
    /**BẢNG 11**/
    public void setDataTable11() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE11.txt");
        File file2 = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        file2 = new File(file2,"Data"+ MaTram);
        File fileSoAnten = new File(file2,"SoAnten.txt");
        Log.d("Text", fileSoAnten.getPath());
        if (fileSoAnten.exists())
        {
            String text = readText(fileSoAnten);

            SoAnten = Integer.parseInt(text.trim());
            Log.d("SoAnten", String.valueOf(SoAnten));

        }
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =6;
        int Rowcount = Table11.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table11)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoAnten+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table11)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }

        for(int i=1;i<SoAnten+1;i++)
        {
            SetPopupAnten(Table11,i,1);
        }


    }
    /**BẢNG 12**/
    public void setDataTable12_V0() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE12_V0.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V0.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V0)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (Table12_V0,i,j);
            }
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V0)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
            if (i>1)
            {
                KiemTraKhongCach(Table12_V0,i,4);
            }
        }


    }
    public void setDataTable12_V1() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE12_V1.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V1.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V1)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (Table12_V1,i,j);

            }
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V1)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
            if (i>1)
            {
                KiemTraKhongCach(Table12_V1,i,4);
            }
        }


    }
    public void setDataTable12_V2() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE12_V2.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V2.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V2)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (Table12_V2,i,j);

            }
        }
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (Table12_V2,i,j);

            }
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V2)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
            if (i>1)
            {
                KiemTraKhongCach(Table12_V2,i,4);
            }
        }


    }
    public void setDataTable12_V3() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE12_V3.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V3.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V3)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<Rowcount;i++)
        {
            for(int j=1;j<=ColumnCount;j++)
            {
                setClick (Table12_V3,i,j);

            }
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V3)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
            if (i>1)
            {
                KiemTraKhongCach(Table12_V3,i,4);
            }
        }


    }
    /****/
    public void setColumnData(TableRow row,String data,int position){
        try {

            TextView tv = (TextView) (((TableRow) row)).getChildAt(position);
            tv.setText(data.toString()); // set selected text data into the
            tv.setHeight(65);
            tv.setTextSize(15);

        }catch (Exception e) {}

    }
    public void AnhXa() {
        btnBack  = (ImageButton) findViewById(R.id.btnback);
        btnNext = (ImageButton) findViewById(R.id.btnnext);
        btnTrang1 = (Button) findViewById(R.id.btnTrang1);
        btnTrang2 = (Button) findViewById(R.id.btnTrang2);
        btnTrang3 = (Button) findViewById(R.id.btnTrang3);
        btnTrang4 = (Button) findViewById(R.id.btnTrang4);
        btnTable10 = (Button) findViewById(R.id.btnTable10);
        btnTable11 = (Button) findViewById(R.id.btnTable11);
        btnTable12 = (Button) findViewById(R.id.btnTable12);
        Table10 = (TableLayout) findViewById(R.id.Table10);
        Table11 = (TableLayout) findViewById(R.id.Table11);
        Table12_V0 = (TableLayout) findViewById(R.id.Table12_V0);
        Table12_V1 = (TableLayout) findViewById(R.id.Table12_V1);
        Table12_V2 = (TableLayout) findViewById(R.id.Table12_V2);
        Table12_V3 = (TableLayout) findViewById(R.id.Table12_V3);
        edtBuLong = (EditText) findViewById(R.id.edtBuLong);
        edtKheHo = (EditText) findViewById(R.id.edtKheHo);
        btnTruyen = (Button) findViewById(R.id.btnTruyenVaoBang);
        Table101 = (TableLayout) findViewById(R.id.tbCacKichThuocDienHinh);
        btnTinhDoNghiengCot = (Button) findViewById(R.id.btnTinhNghieng);
        btnThemDong = (Button) findViewById(R.id.btnThemDong);

    }
    public void SaveAllTable()
    {

            String dataTable3 = GetTableData(Table10,5);
            saveDataOnCacher(dataTable3,"TABLE10");

            String dataTable4 = GetTableData(Table11,5);
            saveDataOnCacher(dataTable4,"TABLE11");

            String dataTable12_V0 = GetTableData(Table12_V0,4);
            saveDataOnCacher(dataTable12_V0,"TABLE12_V0");
            String dataTable12_V1 = GetTableData(Table12_V1,4);
            saveDataOnCacher(dataTable12_V1,"TABLE12_V1");
            String dataTable12_V2 = GetTableData(Table12_V2,4);
            saveDataOnCacher(dataTable12_V2,"TABLE12_V2");
            String dataTable12_V3 = GetTableData(Table12_V3,4);
            saveDataOnCacher(dataTable12_V3,"TABLE12_V3");
            String dataTable12 = dataTable12_V0+dataTable12_V1+dataTable12_V2+dataTable12_V3;
            saveDataOnCacher(dataTable12,"TABLE12");
        Toast.makeText(Table2Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

    }
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
    @SuppressLint("RestrictedApi")
    private void DialogAnten(final TextView textView,final TextView KichThuoc,final TextView KhoiLuong) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_anten_search);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Button btnOK = (Button) dialog.findViewById(R.id.btnRename);
        /**SEARCH VIEW**/
        ArraylistAT.clear();
        ArraylistAT.addAll(Arrays.asList("4G","3G","3G 2100","Triple band gain thấp","Dual band gain cao","Dual band gain thấp","Twinbeam","RRU","FEEDER 7/8","RF","2G 900","2G 1800","Diplexer"));

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
                textView.setText(searchEditTextCK.getText().toString());
                //Log.d("Anten:",searchEditTextCK.getText().toString());
                String[] string = TRABANGANTEN(searchEditTextCK.getText().toString(),ArraylistKG);
                KichThuoc.setText(string[0]);
                KhoiLuong.setText(string[1]);
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

    public void SetPopup(final ArrayList<String> listHT, TableLayout table, int rowItem, int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(Table2Activity.this,textView);
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
        if(table==Table10)
        {
            for(int i=1;i<SoDot+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);
            }
        }
        else if (table==Table11)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);
            }
        }
        else if (table==Table12_V0 || table==Table12_V1 || table==Table12_V2|| table==Table12_V3)
        {
            for(int i=1;i<SoCot+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);

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
    @Override
    public void onBackPressed() {
        SaveAllTable();
        Intent intent3 = new Intent(Table2Activity.this, Table1Activity.class);
        TruyenBien(intent3);
        startActivity(intent3);
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
            if (i!=0) string = string +"&"+ tv.getText().toString().trim().replace("&","");;

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
        if(table==Table10)
        {
            for(int i=1;i<SoDot+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowDataCHECK(row,ColumnCount);
            }
        }
        else if (table==Table11)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowDataCHECK(row,ColumnCount);
            }
        }
        else if (table==Table12_V0 || table==Table12_V1 || table==Table12_V2|| table==Table12_V3)
        {
            for(int i=1;i<SoCot+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowDataCHECK(row,ColumnCount);

            }
        }
        return string;
    }
    public void saveDataOnCacher(String text,String Name){

    }
    public String[] TRABANGANTEN (String dauvao,ArrayList<String> chuoivao) {
        String[] daura=new String[3];
        ArraylistKG.addAll(Arrays.asList("4G@147x275x86@16.8","3G@1334x261x146@9.8","3G 2100@1214x155x70@9.8","Triple band gain thấp@1384x261x146@23.3","Dual band gain cao@2533x261x146@31.2","Dual band gain thấp@1334x261x146@20.3","Twinbeam@1390x301x181@21.5","RRU@425x300x190@15","FEEDER 7/8@0@0","RF@0@0","2G 900@2580x262x116@25.3","2G 1800@1314x155x70@9.8","Diplexer@425x300x190@15"));

        for (int i=0;i< ArraylistKG.size();i++){
            if (chuoivao.get(i).toString().contains(dauvao)){

                daura[0] = ArraylistKG.get(i).toString().split("@")[1];
                daura[1] = ArraylistKG.get(i).toString().split("@")[2];
                break;
            }

        }

        return daura;
    }
    public void TinhDoNghiengCot()
    {
        /*H1 = (EditText) findViewById(id.H1);
        H2 = (EditText) findViewById(id.H2);
        H3 = (EditText) findViewById(id.H3);
        EditText Hv1 = (EditText) findViewById(id.Hv1);
        EditText Hv2 = (EditText) findViewById(id.Hv2);
        EditText Hv3 = (EditText) findViewById(id.Hv3);

        if (!Hv1.equals("") && !Hv2.equals("") && !Hv3.equals("")) {
            H1.setText(Hv1.getText().toString().replace(" ", "").replace(",", "."));
            H2.setText(Hv2.getText().toString().replace(" ", "").replace(",", "."));
            H3.setText(Hv3.getText().toString().replace(" ", "").replace(",", "."));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Nhập đủ độ cao đo (H) của 3 vòng!" , Toast.LENGTH_SHORT).show();
        }
        */
        DecimalFormat format = new DecimalFormat("0.00");
        DecimalFormat format0000 = new DecimalFormat("0.0000");
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        File fileV1 = new File(file,"TABLE12_V1.txt");
        File fileV2 = new File(file,"TABLE12_V2.txt");
        File fileV3 = new File(file,"TABLE12_V3.txt");
        ArrayList<TextView> Vong1 = new ArrayList<TextView>();
        ArrayList<TextView> Vong2 = new ArrayList<TextView>();
        ArrayList<TextView> Vong3 = new ArrayList<TextView>();
        Vong1.add(X1 = (TextView) findViewById(R.id.X1));
        Vong1.add(Y1 = (TextView) findViewById(R.id.Y1));
        Vong1.add(H1 = (TextView) findViewById(R.id.H1));
        Vong1.add(Dx1 = (TextView) findViewById(R.id.Dx1));
        Vong1.add(Dy1 = (TextView) findViewById(R.id.Dy1));
        Vong1.add(e1 = (TextView) findViewById(R.id.e1));
        Vong1.add(Dl1 = (TextView) findViewById(R.id.DL1));
        Vong1.add(KL1 = (TextView) findViewById(R.id.KL1));
        /***Vòng 2**/
        Vong2.add(X2 = (TextView) findViewById(R.id.X2));
        Vong2.add(Y2 = (TextView) findViewById(R.id.Y2));
        Vong2.add(H2 = (TextView) findViewById(R.id.H2));
        Vong2.add(Dx2 = (TextView) findViewById(R.id.Dx2));
        Vong2.add(Dy2 = (TextView) findViewById(R.id.Dy2));
        Vong2.add(e2 = (TextView) findViewById(R.id.e2));
        Vong2.add(Dl2 = (TextView) findViewById(R.id.DL2));
        Vong2.add(Kl2 = (TextView) findViewById(R.id.KL2));
        /***Vòng 3**/
        Vong3.add(X3 = (TextView) findViewById(R.id.X3));
        Vong3.add(Y3 = (TextView) findViewById(R.id.Y3));
        Vong3.add(H3 = (TextView) findViewById(R.id.H3));
        Vong3.add(Dx3 = (TextView) findViewById(R.id.Dx3));
        Vong3.add(Dy3 = (TextView) findViewById(R.id.Dy3));
        Vong3.add(e3 = (TextView) findViewById(R.id.e3));
        Vong3.add(Dl3 = (TextView) findViewById(R.id.DL3));
        Vong3.add(KL3 = (TextView) findViewById(R.id.KL3));
        int ColumnCount =4;
        String[][] dataKQ = new String[3][8];
        if (fileV1.exists())
        {
            String[][] dataV1=DataforPath(fileV1);
            //Log.d("Data:",dataV1[1][1].toString())  ;
            for (int j=1;j<ColumnCount;j++)
            {
                double Tong = 0;
                for(int i=1;i<=SoCot;i++)
                {
                    Tong = (double) (Tong + Double.parseDouble(dataV1[i][j].toString()));
                }
                Vong1.get(j-1).setText(String.valueOf(format.format(Tong/SoCot)).replace(",","."));
                dataKQ[0][j-1] = String.valueOf(format.format(Tong/SoCot).replace(",","."));
                //Log.d("Data:", String.valueOf(dataKQ[0][j-1]));
            }
        }
        if (fileV2.exists())
        {String[][] dataV2=DataforPath(fileV2);
            for (int j=1;j<ColumnCount;j++)
            {
                double Tong = 0;
                for(int i=1;i<=SoCot;i++)
                {
                    Tong = (double) (Tong + Double.parseDouble(dataV2[i][j].toString()));
                }
                Vong2.get(j-1).setText(String.valueOf(format.format(Tong/SoCot)).replace(",","."));
                dataKQ[1][j-1] = String.valueOf(format.format(Tong/SoCot)).replace(",",".");
            }

        }
        if (fileV3.exists())
        {String[][] dataV3=DataforPath(fileV3);
            for (int j=1;j<ColumnCount;j++)
            {
                double Tong = 0;
                for(int i=1;i<=SoCot;i++)
                {
                    Tong = (double) (Tong + Double.parseDouble(dataV3[i][j].toString()));
                }
                Vong3.get(j-1).setText(String.valueOf(format.format(Tong/SoCot)).replace(",","."));
                dataKQ[2][j-1] = String.valueOf(format.format(Tong/SoCot)).replace(",",".");
            }
        }
        /**Tính Dx, DY*/
        for(int i=0;i<2;i++)
        {
            Double Dx = Double.parseDouble(dataKQ[1][i]) - Double.parseDouble(dataKQ[0][i]);
            dataKQ[1][i + 4] = String.valueOf(format0000.format(Dx)).replace(",",".");
            Vong2.get(i + 3).setText(dataKQ[1][i + 4]);

            //Log.d("Data:",dataKQ[1][i]+ "-" +dataKQ[0][i])  ;
        }
        for(int i=0;i<2;i++)
        {
            Double Dy = Double.parseDouble(dataKQ[2][i]) - Double.parseDouble(dataKQ[0][i]);
            dataKQ[2][i + 4] = String.valueOf(format0000.format(Dy)).replace(",",".");
            Vong3.get(i + 3).setText(dataKQ[2][i + 4]);
        }
        /***Tính độ lệch**/

        Double e2 =Math.sqrt( Math.pow(Double.parseDouble(dataKQ[1][4]),2)+ Math.pow(Double.parseDouble(dataKQ[1][5]),2));
        dataKQ[1][6] = String.valueOf(format0000.format(e2)).replace(",",".");
        Vong2.get(5).setText(String.valueOf(format0000.format(e2)).replace(",","."));
        Double e3 =Math.sqrt( Math.pow(Double.parseDouble(dataKQ[2][4]),2)+ Math.pow(Double.parseDouble(dataKQ[2][5]),2));
        dataKQ[2][6] = String.valueOf(format0000.format(e3)).replace(",",".");
        Vong3.get(5).setText(String.valueOf(format0000.format(e3)).replace(",","."));
        /**Tính Độ lệch cho phép**/
        try {
            Double dl1 = Double.parseDouble(Vong1.get(2).getText().toString().replace(",", ".")) / 400;
            Vong1.get(6).setText(String.valueOf(format0000.format(dl1)).replace(",", "."));
            dataKQ[0][7] = String.valueOf(format0000.format(dl1)).replace(",", ".");
            Double dl2 = Double.parseDouble(Vong2.get(2).getText().toString().replace(",", ".")) / 400;
            Vong2.get(6).setText(String.valueOf(format0000.format(dl2)).replace(",", "."));
            dataKQ[1][7] = String.valueOf(format0000.format(dl2)).replace(",", ".");
            Double dl3 = Double.parseDouble(Vong3.get(2).getText().toString().replace(",", ".")) / 400;
            Vong3.get(6).setText(String.valueOf(format0000.format(dl3)).replace(",", "."));
            dataKQ[2][7] = String.valueOf(format0000.format(dl3)).replace(",", ".");
            /**Kiểm tra**/
            if (Double.parseDouble(dataKQ[1][7])>=Double.parseDouble(dataKQ[1][6]))
            {
                Vong2.get(7).setText("Đạt");
            }
            else Vong2.get(7).setText("Không đạt");
            if (Double.parseDouble(dataKQ[2][7])>=Double.parseDouble(dataKQ[2][6]))
            {
                Vong3.get(7).setText("Đạt");
            }
            else Vong3.get(7).setText("Không đạt");
        }
        catch (Exception e){Toast.makeText(getApplicationContext(), "Nhập đủ độ cao đo (H) của 3 vòng!" , Toast.LENGTH_SHORT).show();}

    }
    public String readText(File file){
        String text = "";
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            text = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    public void KiemTraKhongCach( TableLayout table, int rowItem, int columnItem) {
        final DecimalFormat format = new DecimalFormat("0.00");

        TableRow rowCotTren = (TableRow) (((TableLayout) table)).getChildAt(rowItem-1);
        final EditText tvX1 = (EditText) (((TableRow) rowCotTren)).getChildAt(columnItem-3);
        final EditText tvY1 = (EditText) (((TableRow) rowCotTren)).getChildAt(columnItem-2);
        TableRow rowCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final EditText tvX2 = (EditText) (((TableRow) rowCot)).getChildAt(columnItem-3);
        final EditText tvY2 = (EditText) (((TableRow) rowCot)).getChildAt(columnItem-2);
        final TextView tvKQ = (TextView) (((TableRow) rowCot)).getChildAt(columnItem);
        tvKQ.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                try {
                    Double X1,X2,Y1,Y2,KQ;
                    //Log.d("x1:",KichThuocCot);
                    X1 = Double.parseDouble(tvX1.getText().toString());
                    X2 = Double.parseDouble(tvX2.getText().toString());
                    Y1 = Double.parseDouble(tvY1.getText().toString());
                    Y2 = Double.parseDouble(tvY2.getText().toString());
                    KQ = Math.sqrt((X1-X2)* (X1-X2) + (Y1-Y2)*(Y1-Y2));

                        tvKQ.setText(String.valueOf(format.format(KQ)).replace(",","."));
                        tvKQ.setTextColor(R.color.colorGreen);


                }
                catch (Exception e)
                {}
            }
        });

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
