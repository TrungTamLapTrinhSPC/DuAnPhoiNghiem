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

public class Trang2_Activity extends AppCompatActivity {
    /**KHIA BÁO BIẾN**/
    TableLayout Table4,Table5,Table6,Table61;
    Button btnTrang1,btnTrang2,btnTrang3,btnTrang4,btnTruyen,btnTable4,btnTable5,btnTable6;
    ImageButton btnNext,btnBack,btnHome;
    String MaTram,ViTriDat,KichThuocMong,KichThuocCot,DanhGiaHienTrang,GiaChongXoay,ChieuCaoDot,KichThuocDotChieuCaoDot;
    int SoMong,SoDot,SoTangDay,SoCot;
    ArrayList<String> ArraylistHT = new ArrayList<String>();
    ArrayList<String> ArraylistAT = new ArrayList<String>();
    ArrayList<String> listHT = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayco_trang2);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        /**Ánh xạ**/
        AnhXa();

        /**NHẬN CÁC BIẾN*/
        NhanBienTruyen();
        setDataTable61();
        setDataTable4();
        setDataTable5();
        setDataTable6();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(Trang2_Activity.this, Trang1_Activity.class);
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

                Intent intent3 = new Intent(Trang2_Activity.this, Trang3_Activity.class);
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

                Intent intent3 = new Intent(Trang2_Activity.this, Trang1_Activity.class);
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

        btnTrang3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(Trang2_Activity.this, Trang3_Activity.class);
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

                Intent intent3 = new Intent(Trang2_Activity.this, Trang4_Activity.class);
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

        btnTable4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String dataTable3 = GetTableData(Table4,4);
                    saveDataOnCacher(dataTable3,"TABLE4");
                 Toast.makeText(Trang2_Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

            }
        });
        btnTable5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String dataTable3 = GetTableData(Table5,4);
                    saveDataOnCacher(dataTable3,"TABLE5");
                Toast.makeText(Trang2_Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

            }
        });
        btnTable6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String dataTable6 = GetTableData(Table6,6);
                    saveDataOnCacher(dataTable6,"TABLE6");
                     Toast.makeText(Trang2_Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(Trang2_Activity.this, Trang1_Activity.class);
                TruyenBien(intent3);
                startActivity(intent3);
            }
        });
        /**TABLE**/
        setDataTable4();
        setDataTable5();
        setDataTable6();
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

    }
    /**BẢNG 6.1**/
    public void setDataTable61() {
        final  ArrayList<String> listthanhcanh = new ArrayList<String>();
        final  ArrayList<String> listthanhgiang = new ArrayList<String>();
        listthanhcanh.addAll(Arrays.asList("0.3x0.3@D31.8x4","0.3x0.3@D50.5x4","0.4x0.4@D50.8x4","0.6x0.6@D60.5x4","0.6x0.6@D70.3x4.5"));
        listthanhgiang.addAll(Arrays.asList("0.3x0.3@D12","0.3x0.3@D14","0.4x0.4@D16","0.6x0.6@D18"));

        KichThuocDotChieuCaoDot = KichThuocCot + "x" +ChieuCaoDot;
        /****/

    }
    /**BẢNG 6.2**/
    public void setDataTable6() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE6.txt");
        String[][] data=DataforPath(file);
        //Mstower

        /**DÒNG ĐỐT 1*/
        int ColumnCount =6;
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
            //setColumnData(row,KichThuocCot,1);
            //CheckDataTable(row,data,i,ColumnCount);

        }

    }
    /****/
    public void SaveAllTable()
    {
        if(CheckDataTable(Table4,4,1,70))
        {
         String dataTable3 = GetTableData(Table4,4);
         saveDataOnCacher(dataTable3,"TABLE4");
        }
        if(CheckDataTable(Table5,4,0,33))
        {
            String dataTable3 = GetTableData(Table5,4);
            saveDataOnCacher(dataTable3,"TABLE5");
        }
        if(CheckDataTable(Table6,6,1,70))
        {
            String dataTable6 = GetTableData(Table6,6);
            saveDataOnCacher(dataTable6,"TABLE6");
        }
        Toast.makeText(Trang2_Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();

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
        catch (Exception e)
        {
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
        ChieuCaoDot = intent.getStringExtra("ChieuCaoDot");
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
    public void AnhXa() {
        btnBack  = (ImageButton) findViewById(R.id.btnback);
        btnNext = (ImageButton) findViewById(R.id.btnnext);
        btnHome= (ImageButton) findViewById(R.id.btnHome);

        btnTrang1 = (Button) findViewById(R.id.btnTrang1);
        btnTrang2 = (Button) findViewById(R.id.btnTrang2);
        btnTrang3 = (Button) findViewById(R.id.btnTrang3);
        btnTrang4 = (Button) findViewById(R.id.btnTrang4);
        btnTable4 = (Button) findViewById(R.id.btnTable4);
        btnTable5 = (Button) findViewById(R.id.btnTable5);
        btnTable6 = (Button) findViewById(R.id.btnTable6);
        Table4 = (TableLayout) findViewById(R.id.Table4);
        Table5 = (TableLayout) findViewById(R.id.Table5);
        Table6 = (TableLayout) findViewById(R.id.Table6);
    }
    public void setColumnData(TableRow row,String data,int position){
        try {
            TextView tv = (TextView) (((TableRow) row)).getChildAt(position);
            tv.setText(data.toString()); // set selected text data into the
            tv.setHeight(65);
            tv.setTextSize(15);
        }catch (Exception e) {}

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
                final PopupMenu popupMenu = new PopupMenu(Trang2_Activity.this,textView);
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
        if (table==Table4 || table == Table5)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);
            }
        }
        else if (table==Table6)
        {
            for(int i=1;i<SoDot +1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table6)).getChildAt(i);
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

        Intent intent3 = new Intent(Trang2_Activity.this, Trang1_Activity.class);
        TruyenBien(intent3);
        startActivity(intent3);
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
        if (table==Table4 || table == Table5)
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

    public void setClick(TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
