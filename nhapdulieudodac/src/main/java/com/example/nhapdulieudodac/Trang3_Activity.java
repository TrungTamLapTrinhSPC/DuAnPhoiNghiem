package com.example.nhapdulieudodac;

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
import java.util.ArrayList;
import java.util.Arrays;

public class Trang3_Activity extends AppCompatActivity {
    /**KHIA BÁO BIẾN**/
    TableLayout Table7,Table8_Duoi,Table8_Tren,Table9;
    Button btnTrang1,btnTrang2,btnTrang3,btnTrang4,btnTable7,btnTable8_Duoi,btnTable8_Tren,btnTable9;
    ImageButton btnNext,btnBack,btnHome;
    String MaTram,ViTriDat,KichThuocMong,KichThuocCot,DanhGiaHienTrang,GiaChongXoay,ChieuCaoDot;
    int SoMong,SoDot,SoTangDay,SoCot;
    ArrayList<String> listHT = new ArrayList<String>();
    ArrayList<String> listGCX = new ArrayList<String>();
    ArrayList<String> ArraylistHT = new ArrayList<String>();
    int SoGaChongXoay=0;
    ArrayList<String> listTangDayCo = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayco_trang3);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        /**Ánh xạ**/
        AnhXa();
        /**NHẬN CÁC BIẾN*/
        NhanBienTruyen();
        setDataTable7();
        setDataTable8_Duoi();
        setDataTable8_Tren();
        setDataTable9();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(Trang3_Activity.this, Trang2_Activity.class);
                TruyenBien(intent3);
                intent3.putExtra("TenTram", MaTram);  // Truyền một String
                intent3.putExtra("SoMong",String.valueOf(SoMong) );  // Truyền một String
                intent3.putExtra("SoDot", String.valueOf(SoDot));  // Truyền một String
                intent3.putExtra("ViTriDat", String.valueOf(ViTriDat));  // Truyền một String
                intent3.putExtra("SoTangDay", String.valueOf(SoTangDay));  // Truyền một String
                intent3.putExtra("SoChanCot", String.valueOf(SoCot));  // Truyền một String
                intent3.putExtra("KichThuocMong",String.valueOf(KichThuocMong));  // Truyền một String
                intent3.putExtra("KichThuocCot", String.valueOf(KichThuocCot));  // Truyền một String
                intent3.putExtra("GiaChongXoay",String.valueOf(GiaChongXoay));  // Truyền một String
                startActivity(intent3);            }
        });
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent3 = new Intent(Trang3_Activity.this, Trang4_Activity.class);
                TruyenBien(intent3);
                intent3.putExtra("TenTram", MaTram);  // Truyền một String
                intent3.putExtra("SoMong",String.valueOf(SoMong) );  // Truyền một String
                intent3.putExtra("SoDot", String.valueOf(SoDot));  // Truyền một String
                intent3.putExtra("ViTriDat", String.valueOf(ViTriDat));  // Truyền một String
                intent3.putExtra("SoTangDay", String.valueOf(SoTangDay));  // Truyền một String
                intent3.putExtra("SoChanCot", String.valueOf(SoCot));  // Truyền một String
                intent3.putExtra("KichThuocMong",String.valueOf(KichThuocMong));  // Truyền một String
                intent3.putExtra("KichThuocCot", String.valueOf(KichThuocCot));  // Truyền một String
                intent3.putExtra("GiaChongXoay",String.valueOf(GiaChongXoay));  // Truyền một String
                startActivity(intent3);
            }
        });
        btnTrang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Trang3_Activity.this, Trang1_Activity.class);
                TruyenBien(intent3);
                intent3.putExtra("TenTram", MaTram);  // Truyền một String
                intent3.putExtra("SoMong",String.valueOf(SoMong) );  // Truyền một String
                intent3.putExtra("SoDot", String.valueOf(SoDot));  // Truyền một String
                intent3.putExtra("ViTriDat", String.valueOf(ViTriDat));  // Truyền một String
                intent3.putExtra("SoTangDay", String.valueOf(SoTangDay));  // Truyền một String
                intent3.putExtra("SoChanCot", String.valueOf(SoCot));  // Truyền một String
                intent3.putExtra("KichThuocMong",String.valueOf(KichThuocMong));  // Truyền một String
                intent3.putExtra("KichThuocCot", String.valueOf(KichThuocCot));  // Truyền một String
                intent3.putExtra("GiaChongXoay",String.valueOf(GiaChongXoay));  // Truyền một String

                startActivity(intent3);
            }
        });
        btnTrang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(Trang3_Activity.this, Trang2_Activity.class);
                TruyenBien(intent3);
                intent3.putExtra("TenTram", MaTram);  // Truyền một String
                intent3.putExtra("SoMong",String.valueOf(SoMong) );  // Truyền một String
                intent3.putExtra("SoDot", String.valueOf(SoDot));  // Truyền một String
                intent3.putExtra("ViTriDat", String.valueOf(ViTriDat));  // Truyền một String
                intent3.putExtra("SoTangDay", String.valueOf(SoTangDay));  // Truyền một String
                intent3.putExtra("SoChanCot", String.valueOf(SoCot));  // Truyền một String
                intent3.putExtra("KichThuocMong",String.valueOf(KichThuocMong));  // Truyền một String
                intent3.putExtra("KichThuocCot", String.valueOf(KichThuocCot));  // Truyền một String
                intent3.putExtra("GiaChongXoay",String.valueOf(GiaChongXoay));  // Truyền một String

                startActivity(intent3);
            }
        });

        btnTrang4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Trang3_Activity.this, Trang4_Activity.class);
                TruyenBien(intent3);
                intent3.putExtra("TenTram", MaTram);  // Truyền một String
                intent3.putExtra("SoMong",String.valueOf(SoMong) );  // Truyền một String
                intent3.putExtra("SoDot", String.valueOf(SoDot));  // Truyền một String
                intent3.putExtra("ViTriDat", String.valueOf(ViTriDat));  // Truyền một String
                intent3.putExtra("SoTangDay", String.valueOf(SoTangDay));  // Truyền một String
                intent3.putExtra("SoChanCot", String.valueOf(SoCot));  // Truyền một String
                intent3.putExtra("KichThuocMong",String.valueOf(KichThuocMong));  // Truyền một String
                intent3.putExtra("KichThuocCot", String.valueOf(KichThuocCot));  // Truyền một String
                intent3.putExtra("GiaChongXoay",String.valueOf(GiaChongXoay));  // Truyền một String
                startActivity(intent3);
            }
        });
        btnTable7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String dataTable3 = GetTableData(Table7,4);
                    saveDataOnCacher(dataTable3,"TABLE7_Duoi");
                    saveDataOnCacher(dataTable3,"TABLE7_Tren");
                    Toast.makeText(Trang3_Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnTable8_Duoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String dataTable3 = GetTableData(Table8_Duoi,(SoMong+1)*2+1);
                    saveDataOnCacher(dataTable3,"TABLE8_Duoi");
                    Toast.makeText(Trang3_Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnTable8_Tren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String dataTable3 = GetTableData(Table8_Tren,(SoMong+1)*2+1);
                    saveDataOnCacher(dataTable3,"TABLE8_Tren");
                    Toast.makeText(Trang3_Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnTable9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataTable3 = GetTableData(Table9,(SoMong+2)*2+1);
                saveDataOnCacher(dataTable3,"TABLE9");
                Toast.makeText(Trang3_Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Trang3_Activity.this, Trang1_Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });
        /**TABLE**/
        setDataTable7();
        setDataTable8_Duoi();
        setDataTable8_Tren();
        setDataTable9();
    }
    /**TABLE 7**/
    public void setDataTable7() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        File file_duoi = new File(file,"TABLE7_Duoi.txt");
        File file_tren = new File(file,"TABLE7_Tren.txt");
        String[][] data = new String[0][0];
        int ColumnCount =4;
        int Rowcount = Table7.getChildCount();
        if (file_duoi.exists() && file_tren.exists() )
        {
            String[][] data_duoi=DataforPath(file_duoi);
            String[][] data_tren=DataforPath(file_tren);
            //ArrayList<String> listPhuKienCot = new ArrayList<>();
            //listPhuKienCot.addAll(Arrays.asList("Bản định vị chân cột","Tăng đơ","Ma ní đầu dưới","Ma ní đầu trên","Khoá cáp đầu dưới","Khoá cáp đầu trên","Vòng ốp móc dây co và bu lông vòng ốp dây co","Dây co","Gá chống xoay","Gá treo Anten (Viba)","Kim thu sét","Mặt bích bịt đầu cột"));

                //Bảng trên
                for (int i= 1; i<Rowcount;i++)
                {
                    for (int j= 1; j<Rowcount;j++)
                    {
                        String s =data_tren[i][0];
                        if (!s.contains("đầu trên"))
                        if (!s.contains("xoay"))
                        if (!s.contains("(Viba)"))
                        if (!s.contains("đầu cột"))
                        {
                            if (s.equals(data_duoi[j][0]))
                            {
                                data_tren[i] = data_duoi[j];
                            }
                        }
                    }
                }

            data = data_tren;
        }
        else if (file_duoi.exists() && !file_tren.exists())
        {
            data =DataforPath(file_duoi);
        }
        else if (file_tren.exists() && !file_duoi.exists())
        {
            data =DataforPath(file_tren);
        }
        /**SET TEXT ON ROW**/

        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table7)).getChildAt(i);
            setDataTable(row,data,i,ColumnCount);
        }
    }
    /**TABLE 8**/
       public void setDataTable8_Duoi() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        file = new File(file,"Data"+ MaTram);
        File file_duoi = new File(file,"TABLE8_Duoi.txt");
        String[][] data = new String[0][0];
        final int ColumnCount = SoMong*2+1;
            data = DataforPath(file_duoi);
            /**SET TEXT ON ROW**/
            int Rowcount = Table8_Duoi.getChildCount();
            /**CHO ẨN HẾT CÁC DÒNG*/
            for (int i = 1; i < Rowcount; i++) {
                TableRow row = (TableRow) (((TableLayout) Table8_Duoi)).getChildAt(i);
                row.setVisibility(View.GONE);
            }
            /**CHO DÒNG ĐẦU HIỂN THỊ VÀ ẨN CÁC CỘT THỪA**/
            TableRow row0 = (TableRow) (((TableLayout) Table8_Duoi)).getChildAt(0);
            for (int j = SoMong + 1; j < row0.getChildCount(); j++) {
                TextView column = (TextView) (((TableRow) row0)).getChildAt(j);
                column.setVisibility(View.GONE);
            }
            /**CHO CÁC DÒNG CÒN LẠI HIỂN TRỊ VÀ ẨN CÁC CỘT THỪA**/
            for (int i = 1; i < listTangDayCo.size() + 2; i++) {
                TableRow row = (TableRow) (((TableLayout) Table8_Duoi)).getChildAt(i);
                row.setVisibility(View.VISIBLE);
                for (int j = SoMong * 2 + 1; j < row.getChildCount(); j++) {
                    TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                    column.setVisibility(View.GONE);
                }
            }

            for (int i = 2; i < listTangDayCo.size() + 2; i++) {
                TableRow row = (TableRow) (((TableLayout) Table8_Duoi)).getChildAt(i);
                TextView tv = (TextView) (((TableRow) row)).getChildAt(0);
                tv.setText(listTangDayCo.get(i - 2).replace("Tầng dây ", "T"));
                //CheckDataTable(row,data,i-1,ColumnCount);
            }

            for (int i = 2; i < listTangDayCo.size() + 2; i++) {
                TableRow row = (TableRow) (((TableLayout) Table8_Duoi)).getChildAt(i);
                setDataTable2(row, data, i - 1, ColumnCount);
                //CheckDataTable(row,data,i-1,ColumnCount);
            }

    }
    public void setDataTable8_Tren() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        file = new File(file,"Data"+ MaTram);
        File file_tren = new File(file,"TABLE8_Tren.txt");
        String[][] data = new String[0][0];
        final int ColumnCount = SoMong*2+1;
            data = DataforPath(file_tren);
            /**SET TEXT ON ROW**/
            int Rowcount = Table8_Tren.getChildCount();
            /**CHO ẨN HẾT CÁC DÒNG*/
            for (int i = 1; i < Rowcount; i++) {
                TableRow row = (TableRow) (((TableLayout) Table8_Tren)).getChildAt(i);
                row.setVisibility(View.GONE);
            }
            /**CHO DÒNG ĐẦU HIỂN THỊ VÀ ẨN CÁC CỘT THỪA**/
            TableRow row0 = (TableRow) (((TableLayout) Table8_Tren)).getChildAt(0);
            for (int j = SoMong + 1; j < row0.getChildCount(); j++) {
                TextView column = (TextView) (((TableRow) row0)).getChildAt(j);
                column.setVisibility(View.GONE);
            }
            /**CHO CÁC DÒNG CÒN LẠI HIỂN TRỊ VÀ ẨN CÁC CỘT THỪA**/
            for (int i = 1; i < listTangDayCo.size() + 2; i++) {
                TableRow row = (TableRow) (((TableLayout) Table8_Tren)).getChildAt(i);
                row.setVisibility(View.VISIBLE);
                for (int j = SoMong * 2 + 1; j < row.getChildCount(); j++) {
                    TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                    column.setVisibility(View.GONE);
                }
            }

            for (int i = 2; i < listTangDayCo.size() + 2; i++) {
                TableRow row = (TableRow) (((TableLayout) Table8_Tren)).getChildAt(i);
                TextView tv = (TextView) (((TableRow) row)).getChildAt(0);
                tv.setText(listTangDayCo.get(i - 2).replace("Tầng dây ", "T"));
                //CheckDataTable(row,data,i-1,ColumnCount);
            }

            for (int i = 2; i < listTangDayCo.size() + 2; i++) {
                TableRow row = (TableRow) (((TableLayout) Table8_Tren)).getChildAt(i);
                setDataTable2(row, data, i - 1, ColumnCount);
                //CheckDataTable(row,data,i-1,ColumnCount);
            }

    }
    /**TABLE 9**/
    public void setDataTable9() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE9.txt");
        String[][] data_Table9=DataforPath(file);

        /**SET TEXT ON ROW**/
        final int ColumnCount = (1+SoMong)*2+1;
        int Rowcount = Table9.getChildCount();

        /**CHO ẨN HẾT CÁC DÒNG*/
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        /**CHO DÒNG ĐẦU HIỂN THỊ VÀ ẨN CÁC CỘT THỪA**/
        TableRow row0 = (TableRow) (((TableLayout) Table9)).getChildAt(0);
        for(int j=SoMong+2;j<row0.getChildCount();j++)
        {
            TextView column = (TextView) (((TableRow) row0)).getChildAt(j);
            column.setVisibility(View.GONE);
        }

        /**CHO CÁC DÒNG CÒN LẠI HIỂN TRỊ VÀ ẨN CÁC CỘT THỪA**/
        for(int i=1;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            for(int j=(SoMong+1)*2+1;j<row.getChildCount();j++)
            {
                TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                column.setVisibility(View.GONE);
            }
        }
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
            tv.setText(listTangDayCo.get(i-2).replace("Tầng dây ","T"));
            //CheckDataTable(row,data,i-1,ColumnCount);
        }

        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            setDataTable(row,data_Table9,i-1,ColumnCount);
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
    }

    /****/
    public void AnhXa() {
        btnBack  = (ImageButton) findViewById(R.id.btnback);
        btnNext = (ImageButton) findViewById(R.id.btnnext);
        btnHome= (ImageButton) findViewById(R.id.btnHome);
        btnTrang1 = (Button) findViewById(R.id.btnTrang1);
        btnTrang2 = (Button) findViewById(R.id.btnTrang2);
        btnTrang3 = (Button) findViewById(R.id.btnTrang3);
        btnTrang4 = (Button) findViewById(R.id.btnTrang4);
        btnTable7 = (Button) findViewById(R.id.btnTable7);
        btnTable8_Duoi = (Button) findViewById(R.id.btnTable8_Duoi);
        btnTable8_Tren = (Button) findViewById(R.id.btnTable8_Tren);
        btnTable9 = (Button) findViewById(R.id.btnTable9);
        Table7 = (TableLayout) findViewById(R.id.Table7);
        Table8_Tren = (TableLayout) findViewById(R.id.Table8_Tren);
        Table8_Duoi= (TableLayout) findViewById(R.id.Table8_Duoi);
        Table9 = (TableLayout) findViewById(R.id.Table9);
    }

    public void NhanBienTruyen() {
        /**Tạo **/
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
        //region Tách gá chống xoay
        try {
            String[] GaChongXay = GiaChongXoay.split(",");
            for (int i=0;i<GaChongXay.length;i++)
            {
                final boolean boo = isNumeric(String.valueOf(GaChongXay[i].trim()));
                if (boo)
                    listGCX.add(GaChongXay[i].trim()) ;
            }
            for (int i=0;i<=(SoTangDay)-1;i++)
            {
                if (listGCX.contains(String.valueOf(i+1)))
                {
                    listTangDayCo.add("Tầng dây " + String.valueOf(i+1) + "a");
                    listTangDayCo.add("Tầng dây " + String.valueOf(i+1) + "b");
                }
                else
                {
                    listTangDayCo.add("Tầng dây " +String.valueOf(i+1));
                }
            }
        }
        catch (Exception e)
        {

        }
        SoGaChongXoay= listGCX.size();
        //endregion
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
                final PopupMenu popupMenu = new PopupMenu(Trang3_Activity.this,textView);
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
    public void setDataTable2(TableRow row,String[][] data,int position,int count){
        try {
            for (int i = 1; i < count; i++) {
                TextView tv = (TextView) (((TableRow) row)).getChildAt(i);
                tv.setText(data[position][i+1].toString()); // set selected text data into the
                tv.setHeight(65);
                tv.setTextSize(15);
            }
        }
        catch (Exception e) {}
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
    public String GetTableData(TableLayout table,int ColumnCount) {
        String string="";
        if (table==Table7)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);
            }
        }
        else if(table==Table8_Tren || table==Table8_Duoi )
        {
            for(int i=2;i<listTangDayCo.size()+2;i++)
            {
                String rowi="["+ String.valueOf(i-1);
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                for(int j=0;j<SoMong*2+1;j++)
                {
                    TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                    rowi=rowi+"&"+column.getText();
                }
                rowi=rowi+"]";
                string = string+rowi;
            }
        }

        else if(table==Table9)
        {
            for(int i=2;i<listTangDayCo.size()+2;i++)
            {
                String rowi="[";
                TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
                for(int j=0;j<(SoMong+1)*2+1;j++)
                {
                    TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                    if (j==0)
                        rowi=rowi+column.getText();
                    else
                        rowi=rowi+"&"+column.getText();
                }
                rowi=rowi+"]";
                string = string+rowi;
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
                //if(!phuluccongtac[i].trim().equals(""))
                mangString[j][i] = phuluccongtac[i];

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
        Intent intent3 = new Intent(Trang3_Activity.this, Trang2_Activity.class);
        TruyenBien(intent3);
        startActivity(intent3);
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
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
        if (table==Table7)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowDataCHECK(row,ColumnCount);
            }
        }
        else if(table==Table8_Tren || table==Table8_Duoi)
        {
            for(int i=2;i<listTangDayCo.size()+2;i++)
            {
                String rowi="["+ String.valueOf(i-1);
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                for(int j=0;j<SoMong*2+1;j++)
                {
                    TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                    rowi=rowi+"&"+column.getText();
                }
                rowi=rowi+"]";
                string = string+rowi;
            }
        }

        else if(table==Table9)
        {
            for(int i=2;i<listTangDayCo.size()+2;i++)
            {
                String rowi="[";
                TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
                for(int j=0;j<(SoMong+1)*2+1;j++)
                {
                    TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                    if (j==0)
                        rowi=rowi+column.getText();
                    else
                        rowi=rowi+"&"+column.getText();
                }
                rowi=rowi+"]";
                string = string+rowi;
            }
        }
        return string;
    }
    public void saveDataOnCacher(String text,String Name){
        String content = text;
        content = content.replace("[","@");
        content = content.replace("]","");
        content = content.replace("&","_");
        content = content.replace("  ","");
        content = content.replace("   ","");
        File file;
        FileOutputStream outputStream;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "DataViettel");

            file = new File(file, "Data"+ MaTram);
            if (!file.exists())
                if (!file.mkdirs()) {Log.d("App", "failed to create directory");}
            file = new File(file, Name+".txt");
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
            //Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
