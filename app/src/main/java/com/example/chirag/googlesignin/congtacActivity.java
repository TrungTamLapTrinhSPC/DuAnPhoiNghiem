package com.example.chirag.googlesignin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;

public class congtacActivity extends AppCompatActivity {
    private ViewStub stubList;
    ImageButton btnHome,btnBack,popup,btnSearch;
    LinearLayout layoutSearch;
    TextView tvCongTac,tvHangMuc;
    String phulucluu,LoaiCot;
    private String vitrichup= null, longi = null, latgi = null;
    ImageButton btnbackHM,btnnextHM;
    Dialog dialog,dialogmenu;
    private ListView listView;
    ArrayList<String> listTheThoiGian = new ArrayList<String>();
    SearchView.SearchAutoComplete searchAutoCompleteCT;
    private Listview_HinhAnh_CongTac listview_hm_adapter;
    private List<HinhAnhCongTac> productList;
    ArrayList<String> listCongTac = new ArrayList<String>();
    ArrayList<String> listItem2 = new ArrayList<String>();
    ArrayList<String> ArraylistHT = new ArrayList<String>();
        EditText searchEditTextCK;
    ImageView imgViTiThaoTac,imgCauKienThaotac;
    private int curnntView = 0;
    private String tenHM,tenTram,biendau,TenChiTiet="";
    int vtCT,hien=1,posi,position,biendem,lengthduongdan=0,lengthArray;
    String[] ArrayHM = new String[1000];
    Intent intenttudong;
    File[] files2;
    private String tentramcu,tentrammoi,DanhGiaHienTrang,LoaiChup = "Chup";
    List<String> listFile = new ArrayList<String>();
    private File mediaStorageDir,hangmucStorageDir,congtacStorageDir,hinhanhStorageDir,fileoldDoup,filenewDoup;
    String[] chuoiHinhAnh = new String[1000];
    String[] duongdananh= new String[1000];
    static final  int VIEW_MODE_GRIDVIEW = 1;
    /** Tạo Horizon list view*/
    private HorizontalListView horizontalListView;
    private HorizontalAdapter customeAdapter;
    private ArrayList<Model> imageModelArrayList;
    ByteArrayOutputStream bytearrayoutputstream;
    ArrayList<String> listDoPhanGiaiAnh = new ArrayList<String>();
    byte[] BYTE;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congtac);
        stubList = (ViewStub) findViewById(R.id.stub_list_HM);
        stubList.inflate();
        listView = (ListView) findViewById(R.id.mylistView_HM);
        tvCongTac = (TextView) findViewById(R.id.tvCongTac);
        tvHangMuc = (TextView) findViewById(R.id.tvHangMuc);
        btnbackHM = (ImageButton) findViewById(R.id.btnbackHM);
        btnnextHM = (ImageButton) findViewById(R.id.btnnextHM);
        /**truyền biến**/
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_PAN);
        try
        {
            Intent intent = getIntent();
            tenHM = intent.getStringExtra("TenHM");
            ArrayHM = intent.getStringArrayExtra("MangString");
            latgi = intent.getStringExtra("Lat");
            longi = intent.getStringExtra("Long");
            TenChiTiet = intent.getStringExtra("TenChiTiet");
            vitrichup = intent.getStringExtra("ViTri");
            //Log.d("Lccation CT",longi);
            //Log.d("Lccation CT",latgi);
            //Log.d("Lccation CT",vitrichup);
            String s = intent.getStringExtra("position");
            String soluongHM = intent.getStringExtra("SoLuong");
            posi = Integer.parseInt(s);
            //Log.d("Vị Tri nhận", String.valueOf(posi));
            biendem = Integer.parseInt(soluongHM);
           final String[] HM = tenHM.split("_");
            tenTram = intent.getStringExtra("TenTram");
            Kiemtraloaicot();
            try {
                Log.d("lentgh text",biendau);
            }
            catch (Exception e)
            {
                hien = 0;
            }
            final  String[] output = tenTram.split("_");
            /***/
            tvHangMuc.setText(output[0]);
            tvCongTac.setText(HM[0]);
        }
        catch (ArithmeticException e)
        {
            Toast.makeText(congtacActivity.this, "Error #40: Lỗi nhận biến", Toast.LENGTH_LONG).show();

        }
        try {
            horizontalListView = (HorizontalListView) findViewById(R.id.HorizontalListView);
            imageModelArrayList = populateData();
            //Log.d("hjhjh",imageModelArrayList.size()+"");
            customeAdapter = new HorizontalAdapter(congtacActivity.this, imageModelArrayList);
            horizontalListView.setAdapter(customeAdapter);
            horizontalListView.setOnItemClickListener(HonItemClick);
            horizontalListView.setOnItemLongClickListener(HonItemLongClickListener);
            String[] position = tenHM.split("\\.");
            int sc = Integer.parseInt(position[0]);
            horizontalListView.scrollTo(posi * 130);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error#40!", Toast.LENGTH_SHORT).show();

        }

        btnbackHM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try
                {
                    if (posi != 0)
                        NextTo_CongTacActivity(posi - 1);
                    else
                        NextTo_CongTacActivity(biendem);
                }
                catch (ArithmeticException e)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(congtacActivity.this);
                    builder.setTitle("Error: " + e.getMessage());
                    // add the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            onBackPressed();
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog1 = builder.create();
                    dialog1.show();
                }
            }
        });
        btnnextHM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                if (posi!=biendem)
                    NextTo_CongTacActivity(posi+1);
                else
                {
                    TenChiTiet = nameplus(TenChiTiet);
                    NextTo_CongTacActivity(0);}
                }
                catch (ArithmeticException e)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(congtacActivity.this);
                    builder.setTitle("Error: " + e.getMessage());
                    // add the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            onBackPressed();
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog1 = builder.create();
                    dialog1.show();
                }
            }
        });
        /***/
        /**set text**/
        tvHangMuc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(congtacActivity.this, hangmucActivity.class);
                intent.putExtra("TenHM",tenHM);  // Truyền một String
                intent.putExtra("TenTram", tenTram);  // Truyền một String
                intent.putExtra("Long", longi);  // Truyền một String
                intent.putExtra("Lat", latgi);  // Truyền một String
                intent.putExtra("ViTri", vitrichup);  // Truyền một String
                startActivity(intent);
            }
        });
        /** CHỤP ẢNH TỰ ĐỘNG*/
            ImageButton fab = (ImageButton) findViewById(R.id.fabChupTuDong);
            fab.setVisibility(View.GONE);
            if (tenHM.contains("dị tật bất thường"))
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if(tenHM.contains("dị tật bất thường"))
                        NextTo_CameraAPI_DiTat();

                }
            });
        /****/
        mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,tenTram);
        mediaStorageDir = new File(mediaStorageDir,tenHM);
        getProductList(mediaStorageDir);
        SharedPreferences sharedPreferences = getSharedPreferences("ViewsMode", MODE_PRIVATE);
        curnntView = sharedPreferences.getInt("currentView", VIEW_MODE_GRIDVIEW);
        /**Register item lick**/
        //listview_hm_adapter = new Listview_HinhAnh_CongTac(this, R.layout.list_item_hangmuc, productList);
        //listView.setAdapter(listview_hm_adapter);
        listView.setOnItemClickListener(onItemClick);
        listView.setOnItemLongClickListener(onItemLongClickListener);
        /**SEARCH VIEW 1*/
        layoutSearch = (LinearLayout) findViewById(R.id.LayoutSearch);
        layoutSearch.setVisibility(View.GONE);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutSearch.getVisibility() == View.GONE)
                {
                    layoutSearch.setVisibility(View.VISIBLE);
                }
                else if (layoutSearch.getVisibility() == View.VISIBLE)
                {
                    layoutSearch.setVisibility(View.GONE);
                }
            }
        });
        SearchView searchViewCT1 = (SearchView) findViewById(R.id.SeachView);
        searchViewCT1.setQueryHint("Tìm nhanh công việc");
        searchViewCT1.setIconifiedByDefault(false);

        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoCompleteCT1 = (SearchView.SearchAutoComplete)searchViewCT1.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT1.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        searchAutoCompleteCT1.setThreshold(1);
        searchAutoCompleteCT1.setDropDownHeight(500);
        final EditText searchEditTextCK1 = (EditText)searchViewCT1.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditTextCK1.setTextColor(getResources().getColor(R.color.colorWhite));
        searchEditTextCK1.setHintTextColor(getResources().getColor(R.color.colorWhite));

        ImageView searchMagIcon1 = (ImageView)searchViewCT1.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchMagIcon1.setImageResource(R.drawable.ic_search_black_24dp);

        ArrayAdapter<String> newsAdapterCT1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listCongTac);
        searchAutoCompleteCT1.setAdapter(newsAdapterCT1);
        searchAutoCompleteCT1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoCompleteCT1.setText(queryString);

                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
        searchViewCT1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                for (int i=0;i<=100;i++)
                    duongdananh[i] = null;
                listItem2=listview_hm_adapter.filter(query);
                int i = 0;
                for (String s:listItem2)
                {
                    duongdananh[i] = s;
                    //Log.d("Item dd", duongdananh[i]);
                    i++;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                for (int i=0;i<=100;i++)
                    duongdananh[i] = null;
                listItem2=listview_hm_adapter.filter(newText);
                int i = 0;
                for (String s:listItem2)
                {
                    duongdananh[i] = s;
                    //Log.d("Item dd", duongdananh[i]);
                    i++;
                }
                return false;
            }
        });
        /**SEARCH VIEW 2**/
        ArraylistHT.clear();
        DanhGiaHienTrang = UT.HienTrang;
        String[] phuluccon = DanhGiaHienTrang.split("--");
        //Log.d("length:", String.valueOf(phuluccon.length));
        //Log.d("read 1:",phuluccon[1]);
        for (int j = 1; j < phuluccon.length; j = j + 1) {

                if (j==4||j==5||j==6)
                {
                    String[] phuluccongtac = phuluccon[j].split("_");
                    //Log.d("Tên hạng mục:", phuluccongtac[0]);
                    for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                        //Log.d("Tên Hiện trạng:",phuluccongtac[i]);
                        ArraylistHT.add(phuluccongtac[i]);
                    }
                }
        }
        SearchView searchViewCT = (SearchView) findViewById(R.id.SearchChiTiet);
        // Get SearchView autocomplete object.
        searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        searchViewCT.setQueryHint("Nhập chi tiết cần chụp");
        searchViewCT.setIconifiedByDefault(false);
        searchAutoCompleteCT.setThreshold(1);
        searchAutoCompleteCT.setDropDownHeight(500);
        searchEditTextCK = (EditText)  searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditTextCK.setText(TenChiTiet);
        searchEditTextCK.setTextColor(getResources().getColor(R.color.colorPrimary));
        searchEditTextCK.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        ImageView searchMagIcon = (ImageView)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchMagIcon.setImageResource(R.drawable.ic_speaker_notes_black_24dp);
        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ArraylistHT);
        searchAutoCompleteCT.setAdapter(newsAdapterCT);
        searchAutoCompleteCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoCompleteCT.setText(queryString);
                TenChiTiet = searchAutoCompleteCT.getText().toString();
                hideSoftKeyboard(congtacActivity.this);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
        /**POPUP MENU*/
        popup = (ImageButton) findViewById(R.id.btnpopupmenu);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(congtacActivity.this,popup);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu3,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.itemCheDoChup:
                                DialogCheDoChup();
                                break;
                            case R.id.itemThem:
                                //DialogAdd();
                                break;
                            case R.id.itemrestartHT:
                               final AlertDialog.Builder builder4 = new AlertDialog.Builder(congtacActivity.this);
                                builder4.setTitle("Cập nhật lại cây thư mục đang dùng?");
                                // add the buttons
                                builder4.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        /**THÊM FOLDER GIÁM SÁT*/
                                        File Templat = new File(Environment.getExternalStorageDirectory(),"Template");
                                        File txtHienTrang = new File(Templat,"DANHGIAHIENTRANG.txt");
                                        try{
                                            txtHienTrang.delete();
                                            Templat.delete();
                                            File Template = new File(Environment.getExternalStorageDirectory(),"Template");
                                            if (!Template.exists()) {
                                                if (!Template.mkdirs()) {
                                                    Log.d("App", "failed to create directory");
                                                }
                                                else {Toast.makeText(getApplicationContext(), "Đã làm lại hiện trạng như ban đầu vào bộ nhớ" , Toast.LENGTH_SHORT).show();
                                                    saveDataOnCacher(UT.HienTrang,"DANHGIAHIENTRANG");
                                                }
                                            }

                                            AlertDialog.Builder builder = new AlertDialog.Builder(congtacActivity.this);
                                            builder.setTitle("Đã làm lại hiện trạng.");
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                }
                                            });
                                            // create and show the alert dialog
                                            AlertDialog dialog2 = builder4.create();
                                            dialog2.show();
                                        }
                                        catch (Exception e){}
                                        onBackPressed();
                                    }
                                });
                                builder4.setNegativeButton("không", null);
                                // create and show the alert dialog
                                AlertDialog dialog111 = builder4.create();
                                dialog111.show();
                                break;
                            case R.id.itemrestartphuluc:
                                AlertDialog.Builder builder = new AlertDialog.Builder(congtacActivity.this);
                                builder.setTitle("Cập nhật lại cây thư mục đang dùng?");
                                // add the buttons
                                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        /**THÊM FOLDER GIÁM SÁT*/
                                        File Templat = new File(Environment.getExternalStorageDirectory(),"Template");
                                        File txtCOTDAYCO = new File(Templat,"COTDAYCO.txt");
                                        File txtCOTTUDUNG = new File(Templat,"COTTUDUNG.txt");
                                        File txtHienTrang = new File(Templat,"DANHGIAHIENTRANG.txt");
                                        try{
                                            txtCOTDAYCO.delete();
                                            txtCOTTUDUNG.delete();
                                            txtHienTrang.delete();
                                            Templat.delete();
                                            File Template = new File(Environment.getExternalStorageDirectory(),"Template");
                                            if (!Template.exists()) {
                                                if (!Template.mkdirs()) {
                                                    Log.d("App", "failed to create directory");
                                                }
                                                else {Toast.makeText(getApplicationContext(), "Đã thêm folder Template vào bộ nhớ" , Toast.LENGTH_SHORT).show();
                                                    saveDataOnCacher(UT.TramDayCo,"COTDAYCO");
                                                    /***Tạo phụ lục Tự ĐỨNG*/
                                                    saveDataOnCacher(UT.TramTuDung,"COTTUDUNG");
                                                }
                                            }
                                            saveDataOnCacher(UT.TramDayCo,"COTDAYCO");
                                            /***Tạo phụ lục Tự ĐỨNG*/
                                            saveDataOnCacher(UT.TramTuDung,"COTTUDUNG");
                                            AlertDialog.Builder builder = new AlertDialog.Builder(congtacActivity.this);
                                            builder.setTitle("Xóa bỏ thư mục trạm bị lỗi và tạo lại thư mục tương tự.");
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    onBackPressed();
                                                }
                                            });
                                            // create and show the alert dialog
                                            AlertDialog dialog1 = builder.create();
                                            dialog1.show();
                                        }
                                        catch (Exception e){}
                                    }
                                });
                                builder.setNegativeButton("không", null);
                                // create and show the alert dialog
                                AlertDialog dialog1 = builder.create();
                                dialog1.show();
                                break;

                            case R.id.itemrestartHienTrang:
                                onBackPressed();
                                break;
                            case R.id.itemBaoCao:
                                Intent intent = new Intent(congtacActivity.this, Main4Activity.class);
                                intent.putExtra("TenTram",tenTram);  // Truyền một String
                                startActivity(intent);

                                break;
                            case R.id.itemluuphuluc:
                                luuphuluc();
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        ////
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                onBackPressed();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHome.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent = new Intent(congtacActivity.this, MenuTramActivity.class);
                startActivity(intent);
            }
        });
    }

    public Uri GetUriImagtoPath(String MaTram,String TenHangMuc,String TenCongTac)
    {
        Uri uriImage = null;
        try
        {
            File BitmapStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
            BitmapStorageDir = new File(BitmapStorageDir,MaTram);
            BitmapStorageDir = new File(BitmapStorageDir,TenHangMuc);
            BitmapStorageDir = new File(BitmapStorageDir,TenCongTac);
            if (BitmapStorageDir.exists())
            {
                File[] files=BitmapStorageDir.listFiles();
                //Log.d("Lenght file", String.valueOf(files.length));
                if(files.length>0)
                {
                    for (File file:files)
                    {
                        if (file.getPath().contains(".jpg"))
                        {
                            String[] Check = file.getName().split("--");
                            if(Check[1].equals("1"))
                            {
                                /*//LẤY ẢNH BỊ NẶNG
                                Bitmap myBitmap = BitmapFactory.decodeFile(file.getPath());
                                bytearrayoutputstream = new ByteArrayOutputStream();
                                myBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytearrayoutputstream);
                                BYTE = bytearrayoutputstream.toByteArray();
                                Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);
                                bitmap =bitmap2;*/
                                //CÁCH 2
                                uriImage =Uri.parse(file.getPath());
                                //img.setImageURI(imgUri);
                            }

                        }
                        else
                        {
                           //Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ico_camera);
                            //uriImage= getImageUri(this,bitmap);
                            uriImage= Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ico_camera);

                            file.delete();
                        }
                    }
                }
                else if (files.length==0)
                {
                   //Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ico_camera);
                    uriImage= Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ico_camera);

                    //uriImage= getImageUri(this,bitmap);
                    //uriImage = Uri.fromFile(bitmap)
                }
            }

        }
        catch (Exception e)
        {

        }

        return uriImage;
    }
    private Uri getImageUri(Context context, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);

        return Uri.parse(path);
    }
    public Bitmap GetBitMaptoPath(String MaTram,String TenHangMuc,String TenCongTac)
    {
        Bitmap bitmap = null;
        try
        {
            File BitmapStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
            BitmapStorageDir = new File(BitmapStorageDir,MaTram);
            BitmapStorageDir = new File(BitmapStorageDir,TenHangMuc);
            BitmapStorageDir = new File(BitmapStorageDir,TenCongTac);
            if (BitmapStorageDir.exists())
            {
                File[] files=BitmapStorageDir.listFiles();
                //Log.d("Lenght file", String.valueOf(files.length));
                if(files.length>0)
                {
                    for (File file:files)
                    {
                        if (file.getPath().contains(".jpg"))
                        {
                            String[] Check = file.getName().split("--");
                            if(Check[1].equals("1"))
                            {
                                //LẤY ẢNH BỊ NẶNG
                                Bitmap myBitmap = BitmapFactory.decodeFile(file.getPath());
                                bytearrayoutputstream = new ByteArrayOutputStream();
                                myBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytearrayoutputstream);
                                BYTE = bytearrayoutputstream.toByteArray();
                                Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);
                                bitmap =bitmap2;
                                //CÁCH 2
                                //Uri imgUri=Uri.parse(DuongDan);
                                //img.setImageURI(imgUri);
                            }

                        }
                        else
                        {
                            bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ico_camera);
                            file.delete();
                        }
                    }
                }
                else if (files.length==0)
                {
                    bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ico_camera);
                }
            }

        }
        catch (Exception e)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(congtacActivity.this);
            builder.setTitle("Lỗi tải ảnh");
            // add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    onBackPressed();
                }
            });
            // create and show the alert dialog
            AlertDialog dialog1 = builder.create();
            dialog1.show();
        }

        return bitmap;
    }
    public List<HinhAnhCongTac> getProductList(File f){
        try
        {
            Bitmap bitmap = null;
            productList = new ArrayList<>();
            File[] files=f.listFiles();
            listFile.clear();
            lengthduongdan = 0;
            for (File file:files)
            {
                listCongTac.add(file.getName().toString());
                //Log.d("list:",file.getName().toString());
                //productList.add(new HangMuc(R.drawable.ic_folder_open_black_24dp,file.getName()));
            }
            Collections.sort(listCongTac);
            for (String file:listCongTac)
            {
                if (!file.contains("Hình ảnh khác"))
                {
                    duongdananh[lengthduongdan] = file;
                    lengthduongdan++;
                    Uri uri = GetUriImagtoPath(tenTram,tenHM,file);
                    productList.add(new HinhAnhCongTac(uri, file));
                    /*bitmap= GetBitMaptoPath(tenTram,tenHM,file);
                    if (bitmap==null)  bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.camera);
                    final int THUMBNAIL_SIZE = 120;
                    Float width = new Float(bitmap.getWidth());
                    Float height = new Float(bitmap.getHeight());
                    Float ratio = width/height;
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int)(THUMBNAIL_SIZE * ratio), THUMBNAIL_SIZE, false);
                    productList.add(new HinhAnhCongTac(bitmap,file));*/
                }
            }
            for (String file:listCongTac)
            {
                if (file.contains("Hình ảnh khác"))
                {
                    duongdananh[lengthduongdan] = file;
                    lengthduongdan++;
                   Uri uri = GetUriImagtoPath(tenTram,tenHM,file);
                   productList.add(new HinhAnhCongTac(uri, file));
                    /*bitmap= GetBitMaptoPath(tenTram,tenHM,file);
                    if (bitmap==null)  bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.camera);
                        final int THUMBNAIL_SIZE = 120;
                        Float width = new Float(bitmap.getWidth());
                        Float height = new Float(bitmap.getHeight());
                        Float ratio = width / height;
                        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (THUMBNAIL_SIZE * ratio), THUMBNAIL_SIZE, false);
                        productList.add(new HinhAnhCongTac(bitmap, file));*/
                }
            }
            lengthduongdan--;
            //Log.d("Length", String.valueOf(lengthduongdan));
            /**AUTO FILTER LẦN 1*/
            listview_hm_adapter = new Listview_HinhAnh_CongTac(this, R.layout.list_item_hangmuc_ct, productList);
            listView.setAdapter(listview_hm_adapter);
        }
        catch (Exception e)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(congtacActivity.this);
            builder.setTitle("Error: Lỗi tải danh sách ảnh!");
            // add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    //onBackPressed();
                }
            });
            // create and show the alert dialog
            AlertDialog dialog1 = builder.create();
            dialog1.show();
        }


        return productList;
    }

    /**CLICK ITEM**/
    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NextTo_CameraAPI2(position);
        }
    };
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            vtCT = position;
            DialogMenu();
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(100);
            return true;
        }
    };
    AdapterView.OnItemClickListener HonItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), ArrayHM[position].toString(), Toast.LENGTH_SHORT).show();

            NextTo_CongTacActivity(position);
        }
    };
    AdapterView.OnItemLongClickListener HonItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            Toast.makeText(getApplicationContext(), ArrayHM[position].toString(), Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    private void NextTo_ChupAnhActivity(int position,String tenCT)
    {
        Intent intent2 = new Intent(congtacActivity.this, ChenAnhTuThuVien.class);
        intent2.putExtra("TenHM", tenHM);  // Truyền một String
        intent2.putExtra("TenTram", tenTram);  // Truyền một String
        intent2.putExtra("MangCT", duongdananh);  // Truyền một String
        intent2.putExtra("TenCongTac", tenCT);  // Truyền một String
        intent2.putExtra("position", String.valueOf(position));  // Truyền một String
        intent2.putExtra("MangString", ArrayHM);  // Truyền một String
        intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
        intent2.putExtra("Long", longi);  // Truyền một String
        intent2.putExtra("Lat", latgi);  // Truyền một String
        intent2.putExtra("ViTri", vitrichup);  // Truyền một String
        intent2.putExtra("LoaiChup", LoaiChup);  // Truyền một String
        intent2.putExtra("TenChiTiet", searchEditTextCK .getText().toString());  // Truyền một String
        finish();
        startActivity(intent2);
    }
    private void NextTo_AnhChupActivity(int position)
    {
        Intent intent = new Intent(congtacActivity.this,anhchupActivity.class);
        intent.putExtra("TenTram", tenTram);  // Truyền một String
        intent.putExtra("TenHangMuc",tenHM);  // Truyền một String
        intent.putExtra("TenCongTac",  productList.get(position).getTitle());  //Truyền một String
        intent.putExtra("position",  String.valueOf(position));  // Truyền một String
        intent.putExtra("MangCT",  duongdananh);  // Truyền một String
        intent.putExtra("Long", longi);  // Truyền một String
        intent.putExtra("Lat", latgi);  // Truyền một String
        intent.putExtra("ViTri", vitrichup);  // Truyền một String
        intent.putExtra("MangString", ArrayHM);  // Truyền một String
        intent.putExtra("TenChiTiet",searchEditTextCK .getText().toString());  // Truyền một String
        intent.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
        startActivity(intent);
    }
    private void NextTo_CameraAPI2(int position)
    {
        String string= DataforPath("CHEDOCHUP");
        String[] listCheDo = string.split("@");
        Intent intent2;
        if (listCheDo.length<18)
        {
            string = UT.CheDoChup;
            listCheDo = string.split("@");
            saveDataOnCacher(string,"CHEDOCHUP");
        }
        final String[] tDoPhanGiai = {listCheDo[17].toString()};
        if (tDoPhanGiai[0].contains("On"))
        {intent2 = new Intent(congtacActivity.this, Camera2BasicFragment.class);}
        else {intent2 = new Intent(congtacActivity.this,Camera2BasicFragment.class);}
        intent2.putExtra("TenHM", tenHM);  // Truyền một String
        intent2.putExtra("TenTram", tenTram);  // Truyền một String
        intent2.putExtra("MangCT", duongdananh);  // Truyền một String
        intent2.putExtra("TenCongTac", duongdananh[position]);  // Truyền một String
        intent2.putExtra("position", String.valueOf(position));  // Truyền một String
        intent2.putExtra("MangString", ArrayHM);  // Truyền một String
        intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
        intent2.putExtra("Long", longi);  // Truyền một String
        intent2.putExtra("Lat", latgi);  // Truyền một String
        intent2.putExtra("ViTri", vitrichup);  // Truyền một String
        intent2.putExtra("TenChiTiet", searchEditTextCK .getText().toString());  // Truyền một String

        startActivity(intent2);

    }
    private void NextTo_CameraAPI_DiTat()
    {
        String string= DataforPath("CHEDOCHUP");
        String[] listCheDo = string.split("@");
        Intent intent2;
        if (listCheDo.length<18)
        {
            string = UT.CheDoChup;
            listCheDo = string.split("@");
            saveDataOnCacher(string,"CHEDOCHUP");
        }
        final String[] tDoPhanGiai = {listCheDo[17].toString()};
        if (tDoPhanGiai[0].contains("On"))
        {intent2 = new Intent(congtacActivity.this, Camera2BasicFragment.class);}
        else {intent2 = new Intent(congtacActivity.this, Camera2BasicFragment.class);}

        intent2.putExtra("TenHM", tenHM);  // Truyền một String
        intent2.putExtra("TenTram", tenTram);  // Truyền một String
        intent2.putExtra("MangCT", duongdananh);  // Truyền một String
        intent2.putExtra("TenCongTac", "Dị tật bất thường" );  // Truyền một String
        intent2.putExtra("position",String.valueOf(posi));  // Truyền một String
        intent2.putExtra("MangString", ArrayHM);  // Truyền một String
        intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
        intent2.putExtra("Long", longi);  // Truyền một String
        intent2.putExtra("Lat", latgi);  // Truyền một String
        intent2.putExtra("ViTri", vitrichup);  // Truyền một String
        intent2.putExtra("TenChiTiet", searchEditTextCK .getText().toString());  // Truyền một String
        startActivity(intent2);

    }

    private void NextTo_CongTacActivity(int position)
    {
        try {
            Intent intent = new Intent(congtacActivity.this, congtacActivity.class);
            intent.putExtra("TenHM", ArrayHM[position].toString());  // Truyền một String
            intent.putExtra("TenTram", tenTram);  // Truyền một String
            intent.putExtra("Long", longi);  // Truyền một String
            intent.putExtra("Lat", latgi);  // Truyền một String
            intent.putExtra("ViTri", vitrichup);  // Truyền một String
            intent.putExtra("MangString", ArrayHM);  // Truyền một String
            intent.putExtra("position", String.valueOf(position));  // Truyền một String
            intent.putExtra("TenChiTiet", TenChiTiet);  // Truyền một String
            intent.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
            finish();
            startActivity(intent);
        }
        catch (Exception e){}
    }
    private void DialogMenu(){
        dialogmenu = new Dialog(this,R.style.PauseDialog);
        dialogmenu.setContentView(R.layout.dialog_menu);
        dialogmenu.show();
        Button btnmenudoiten = (Button) dialogmenu.findViewById(R.id.btnmenudoiten);
        Button btnmenuxoa= (Button) dialogmenu.findViewById(R.id.btnmenuxoa);
        Button btnDupl = (Button) dialogmenu.findViewById(R.id.btnduplicate);
        Button btnXemAnh = (Button) dialogmenu.findViewById(R.id.btnXemAnh);
        Button btnPhongLon = (Button) dialogmenu.findViewById(R.id.btnZoom);
        Button btnThuVien = (Button) dialogmenu.findViewById(R.id.btnmenuthemanh);
        btnThuVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LoaiChup="ThuVien";
                NextTo_ChupAnhActivity(vtCT,productList.get(vtCT).getTitle());
            }
        });
        btnPhongLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogZoom(GetBitMaptoPath(tenTram,tenHM,productList.get(vtCT).getTitle()));
            }
        });
        btnXemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogmenu.dismiss();
                NextTo_AnhChupActivity(vtCT);
            }
        });
        btnDupl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDuplicate();
            }
        });
        btnmenuxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(congtacActivity.this);
                builder.setTitle("Bạn muốn xóa thư mục này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        xoaFolder(tenTram,tenHM,productList.get(vtCT).getTitle());

                        dialogmenu.dismiss();
                        NextTo_CongTacActivity(posi);
                    }
                });
                builder.setNegativeButton("không", null);
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();
            }
        });
        btnmenudoiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogRename();
            }
        });
    };
    private void DialogRename(){
        final Dialog dialogD = new Dialog(this,R.style.PauseDialog);
        dialogD.setContentView(R.layout.dialog_rename2);
        dialogD.show();
        dialogD.setCanceledOnTouchOutside(false);
        tentramcu =  productList.get(vtCT).getTitle();
        final EditText txtTentram = (EditText) dialogD.findViewById(R.id.txtMaTram);
        Button btnOK = (Button) dialogD.findViewById(R.id.btnRename);
        txtTentram.setText(productList.get(vtCT).getTitle());
        TextView tvTen = (TextView) dialogD.findViewById(R.id.tvTen);
        tvTen.setText("Đổi tên");
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tentrammoi = txtTentram.getText().toString();
                File fileold = new File(mediaStorageDir,tentramcu);
                File filenew = new File(mediaStorageDir,tentrammoi);
                fileold.renameTo(filenew);
                // int vt=vtCT;
                //productList.remove(vt);
                //productList.set(vt,new HangMuc(R.drawable.ic_folder_open_black_24dp,tentrammoi.toString()));
                dialogD.dismiss();
                dialogmenu.dismiss();
                //restartActivity(congtacActivity.this);
                NextTo_CongTacActivity(posi);
                Toast.makeText(getApplicationContext(), "Đã đổi tên thành công", Toast.LENGTH_SHORT).show();
            }
        });
        Button btnThoat = (Button) dialogD.findViewById(R.id.btnthoat);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogD.dismiss();

            }
        });
    }
    private void DialogDuplicate(){
        dialog = new Dialog(this,R.style.PauseDialog);
        dialog.setContentView(R.layout.dialog_rename2);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        tentramcu =  productList.get(vtCT).getTitle();
        final EditText txtTentram = (EditText) dialog.findViewById(R.id.txtMaTram);
        Button btnOK = (Button) dialog.findViewById(R.id.btnRename);
        TextView tvTen = (TextView) dialog.findViewById(R.id.tvTen);
        tvTen.setText("Thêm thư mục");
        /**gắn tên thu mục*/
        String string = productList.get(vtCT).getTitle();
        char chr = string.charAt(string.length()-1);
        char[] name= new char[string.length()-1];
        string.getChars(0,string.length()-1,name,0);
        //Log.d("New char", String.valueOf(name));
        final boolean boo = isNumeric(String.valueOf(chr));
        if (boo)
        {
            int number = Integer.parseInt(String.valueOf(chr));
            //Log.d("New number", String.valueOf(number+1));
            txtTentram.setText(String.valueOf(name)+String.valueOf(number+1));
        }
        else
        {
            tentramcu =  productList.get(vtCT).getTitle();
            tentrammoi = productList.get(vtCT).getTitle()+" 1";
            fileoldDoup = new File(mediaStorageDir,tentramcu);
            filenewDoup = new File(mediaStorageDir,tentrammoi);
            String string2 = tentramcu + " 2";
            txtTentram.setText(string2);
        }
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (boo)
                {
                    tentrammoi = txtTentram.getText().toString();
                    File filenew = new File(mediaStorageDir,tentrammoi);
                    /**Tạo thu muc mới**/
                    if (!filenew.exists()) {
                        if (!filenew.mkdirs()) {
                            Log.d("App", "failed to create directory");
                        } else {
                        }
                    }
                }
                else
                {
                    tentrammoi = txtTentram.getText().toString();
                    fileoldDoup.renameTo(filenewDoup);
                    File filenew = new File(mediaStorageDir,tentrammoi);
                    /**Tạo thu muc mới**/
                    if (!filenew.exists()) {
                        if (!filenew.mkdirs()) {
                            Log.d("App", "failed to create directory");
                        } else {
                        }
                    }
                }
                //productList.remove(vt);
                //productList.set(vt,new HangMuc(R.drawable.ic_folder_open_black_24dp,tentrammoi.toString()));
                dialog.dismiss();
                dialogmenu.dismiss();
                //restartActivity(congtacActivity.this);
                NextTo_CongTacActivity(posi);
                Toast.makeText(getApplicationContext(), "Đã thêm thư mục tương tự!", Toast.LENGTH_SHORT).show();
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
    /** TEST STRING IS NUMBER*/
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    private void DialogAdd(){
        final Dialog dialog = new Dialog(this,R.style.PauseDialog);
        dialog.setContentView(R.layout.dialog_add2);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        // Ánh xạ
        final EditText txtTenFolder = (EditText) dialog.findViewById(R.id.txtTenFolder);

        Button btnThoat = (Button) dialog.findViewById(R.id.btnthoat);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maTram = txtTenFolder.getText().toString();
                File HANGMUC = new File(mediaStorageDir,maTram);
                if (!HANGMUC.exists()) {
                    if (!HANGMUC.mkdirs()) {
                        Log.d("App", "failed to create directory");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Đã thêm " + maTram, Toast.LENGTH_SHORT).show();
                        NextTo_CongTacActivity(posi);
                    }
                }
                dialog.dismiss();

            }
        });
    }
    public void xoaphuluc(){
        File file;
        File file2;
        file = new File(Environment.getExternalStorageDirectory(), "Template");
        file = new File(file, "COTDAYCO.txt");
        file2 = new File(file, "COTTUDUNG.txt");
        file.delete();
        file2.delete();
    }
    public void xoaFileHienTrang(){
        File file;
        file = new File(Environment.getExternalStorageDirectory(), "Template");
        file = new File(file, "DANHGIAHIENTRANG.txt");
        file.delete();

    }
    private  void luuphuluc(){
        File PHULUCStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        PHULUCStorageDir = new File(PHULUCStorageDir,tenTram);
        File[] files=PHULUCStorageDir.listFiles();
        listFile.clear();
        phulucluu="";
        int biendem=0;
        for (File file:files){
            if (file.getName()!= "\n") {
                //{Log.d("folder hạng muc",file.getName());
                phulucluu =phulucluu + "--" + file.getName();
                biendem++;
                File PHULUCStorageDir2 = new File(PHULUCStorageDir, file.getName());
                files2 = PHULUCStorageDir2.listFiles();
                listFile.clear();
            }
            for (File fileCT:files2){
                if (fileCT.getName()!= "\n") {

                    //Log.d("folder cong tac",fileCT.getName());
                    phulucluu = phulucluu + "_" + fileCT.getName()+ "_";

                }
            }

        }
        //Log.d("folder cong tac",phulucluu);
        //Log.d("folder cong tac", String.valueOf(biendem));
        if (biendem==10)
            saveDataOnCacher(phulucluu,"COTDAYCO");
        else if (biendem==8)
            saveDataOnCacher(phulucluu,"COTTUDUNG");
        Toast.makeText(getApplicationContext(), "Đã lưu phụ lục bạn vừa thay đổi", Toast.LENGTH_SHORT).show();

    };
    private  void Kiemtraloaicot(){
        File PHULUCStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        PHULUCStorageDir = new File(PHULUCStorageDir,tenTram);
        File[] files=PHULUCStorageDir.listFiles();
        listFile.clear();
        int biendem=0;
        for (File file:files){
                biendem++;
        }
        //Log.d("folder cong tac", String.valueOf(biendem));
        if (biendem==10)
            LoaiCot = "DayCo";
        else if (biendem==8)
            LoaiCot = "TuDung";

    };
    public void saveDataOnCacher(String text,String Name){
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

    public void xoaFolder(String tentram,String tenhangmuc,String tencongtac)
    {
        try
        {
            mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
            mediaStorageDir = new File(mediaStorageDir,tentram);/// lấy link vào hang mục
            hangmucStorageDir = new File(mediaStorageDir,tenhangmuc);/// lấy link vào hang mục
            congtacStorageDir = new File(hangmucStorageDir,tencongtac);/// lấy link vào hang mục
            try {
                FileUtils.deleteDirectory(congtacStorageDir);
                Toast.makeText(getApplicationContext(),"Đã xóa thư mục dữ liệu!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*int i=0,j=0,z=0;
            listFile.clear();
            File[] tenanh = congtacStorageDir.listFiles();
            for (File TIM:tenanh)
            {
                //Log.d("Ten hình ảnh", TIM.getName());
                hinhanhStorageDir = new File(congtacStorageDir,TIM.getName());
                chuoiHinhAnh[z] = hinhanhStorageDir.getPath();
                //Log.d("Ten hình ảnh",chuoiHinhAnh[z]);
                z++;
            }
            for (int ii=0;ii<z;ii++)
            {
                File file = new File(chuoiHinhAnh[ii]);
                file.delete();
                //Log.d("Ten hình ảnh", String.valueOf(z));
            }
            Toast.makeText(getApplicationContext(),"Đã xóa "+congtacStorageDir.getName(), Toast.LENGTH_SHORT).show();
            congtacStorageDir.delete();*/
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"không thể xóa thư mục dữ liệu!", Toast.LENGTH_SHORT).show();

        }


    }
    /***/
    private ArrayList<Model> populateData(){
        ArrayList<Model> list = new ArrayList<>();
        try{
            //Log.d("Tên hạng mục",tenHM);
            String[] vt = tenHM.split("\\.");
            final int posi = Integer.parseInt(vt[0]);
            for(int i = 0; i <=biendem; i++){
                Model imageModel = new Model();
                String[] position = ArrayHM[i].split("\\.");
                //Log.d("ListHM",  ArrayHM[i]);
                imageModel.setName(position[0]);
                if (Integer.parseInt(position[0])==posi)
                    imageModel.setImage_drawable(R.drawable.duongbotron5);
                else
                    imageModel.setImage_drawable(R.drawable.duongbotron2);
                list.add(imageModel);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error #51", Toast.LENGTH_SHORT).show();
        }
        return list;
    }


    public String nameplus(final String string){
        String nameaddOne = string;
        try{
        String[] NB = string.split("-");
        /**gắn tên thu mục*/
        char chr = string.charAt(string.length()-1);
        char[] name= new char[string.length()-1];
        string.getChars(0,string.length()-1,name,0);
        //Log.d("New char", String.valueOf(name));
        final boolean boo = isNumeric(String.valueOf(chr));
        if (boo)
        {
            if (NB.length==1) {
                int number = Integer.parseInt(String.valueOf(chr));
                //Log.d("New number", String.valueOf(number+1));
                nameaddOne = String.valueOf(name) + String.valueOf(number + 1);
            }
            else if (NB.length>1)
            {
                nameaddOne = nameplus(NB[0])+"-"+nameplus(NB[1]);
            }
        }
        }
        catch (Exception e){

        }

        return nameaddOne;
    }
    public Bitmap BITMAP_RESIZER(Bitmap bitmap,int newWidth,int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;

    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void DialogCheDoChup() {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_chodocup);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            /***/

            String string = DataforPath("CHEDOCHUP");
            String[] listCheDo = string.split("@");
            if (listCheDo.length<18)
            {
                string = UT.CheDoChup;
                listCheDo = string.split("@");
                saveDataOnCacher(string,"CHEDOCHUP");
            }
            /***/
            final String[] tChitiet = {listCheDo[1].toString()};
            final String[] tThuong = {listCheDo[3].toString()};
            final String[] tKhuyetTat = {listCheDo[5].toString()};
            final String[] tTheChung = {listCheDo[7].toString()};
            final String[] tTheThoiGian = {listCheDo[9].toString()};
            final String[] tToaDo = {listCheDo[11].toString()};
            final String[] tViTri = {listCheDo[13].toString()};
            final String[] tDoPhanGiai = {listCheDo[15].toString()};
            final String[] tLiveCam = {listCheDo[17].toString()};


            final Switch switchchitiet = (Switch) dialog.findViewById(R.id.switchChiTiet);
            final Switch switchthuong = (Switch) dialog.findViewById(R.id.switchThuong);
            final Switch switchkhuyettat = (Switch) dialog.findViewById(R.id.switchDiTat);
            final Switch switchthechung = (Switch) dialog.findViewById(R.id.switchTheChung);
            final Switch switchtoado = (Switch) dialog.findViewById(R.id.switchToaDo);
            final Switch switchvitri = (Switch) dialog.findViewById(R.id.switchDiaDiem);
            final Switch switchlivecam = (Switch) dialog.findViewById(R.id.switchLive);

            if (tChitiet[0].equals("On")) switchchitiet.setChecked(true);
            else switchchitiet.setChecked(false);
            if (tThuong[0].equals("On")) switchthuong.setChecked(true);
            else switchthuong.setChecked(false);
            if (tKhuyetTat[0].contains("On")) switchkhuyettat.setChecked(true);
            else switchkhuyettat.setChecked(false);
            if (tTheChung[0].equals("On")) switchthechung.setChecked(true);
            else switchthechung.setChecked(false);
            if (tToaDo[0].equals("On")) switchtoado.setChecked(true);
            else switchtoado.setChecked(false);
            if (tViTri[0].contains("On")) switchvitri.setChecked(true);
            else switchvitri.setChecked(false);
            if (tLiveCam[0].contains("On")) switchlivecam.setChecked(true);
            else switchlivecam.setChecked(false);

            final TextView txt1 = (TextView) dialog.findViewById(R.id.txt1);
            txt1.setText(tChitiet[0]);
            final TextView txt2 = (TextView) dialog.findViewById(R.id.txt2);
            txt2.setText(tThuong[0]);
            final TextView txt3 = (TextView) dialog.findViewById(R.id.txt3);
            txt3.setText(tKhuyetTat[0]);
            final TextView txt4 = (TextView) dialog.findViewById(R.id.txt4);
            txt4.setText(tTheChung[0]);
            final TextView txt5 = (TextView) dialog.findViewById(R.id.txt5);
            txt5.setText(tToaDo[0]);
            final TextView txt6 = (TextView) dialog.findViewById(R.id.txt6);
            txt6.setText(tViTri[0]);
            final TextView txt7 = (TextView) dialog.findViewById(R.id.txt7);
            txt7.setText(tLiveCam[0]);
            switchchitiet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tChitiet[0] = (switchchitiet.isChecked() ? "On" : "Off");
                    txt1.setText(tChitiet[0]);
                }
            });
            switchthuong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tThuong[0] = (switchthuong.isChecked() ? "On" : "Off");
                    txt2.setText(tThuong[0]);

                }
            });
            switchkhuyettat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tKhuyetTat[0] = (switchkhuyettat.isChecked() ? "On" : "Off");
                    txt3.setText(tKhuyetTat[0]);
                }
            });
            /**
             * Thẻ
             */
            switchthechung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tTheChung[0] = (switchthechung.isChecked() ? "On" : "Off");
                    txt4.setText(tTheChung[0]);
                }
            });
            switchtoado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tToaDo[0] = (switchtoado.isChecked() ? "On" : "Off");
                    txt5.setText(tToaDo[0]);

                }
            });
            switchvitri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tViTri[0] = (switchvitri.isChecked() ? "On" : "Off");
                    txt6.setText(tViTri[0]);
                }
            });

            switchlivecam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tLiveCam[0] = (switchlivecam.isChecked() ? "On" : "Off");
                    txt7.setText(tLiveCam[0]);
                }
            });

            /***
             * Thẻ thời gian
             */
            listTheThoiGian.clear();
            listTheThoiGian.add("None");
            listTheThoiGian.add("dd/mm/yyyy hh:mm");
            listTheThoiGian.add("hh:mm");
            listTheThoiGian.add("dd/mm/yyyy");

            final TextView edtVitri =dialog.findViewById(R.id.edtThoiGian);
            edtVitri.setText(tTheThoiGian[0]);
            /**TẠO POPUP EDIT TEXT*/
            edtVitri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(congtacActivity.this, edtVitri);
                    for (String s : listTheThoiGian) {
                        popupMenu.getMenu().add(s);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem menuItem) {
                            edtVitri.setText(menuItem.getTitle().toString());
                            tTheThoiGian[0]= (String) edtVitri.getText();
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            /***
             * Thẻ độ phân giải ảnh
             */
            listDoPhanGiaiAnh.clear();
            listDoPhanGiaiAnh.add("QHD (2560x1440)");
            listDoPhanGiaiAnh.add("FHD (1920x1080)");
            listDoPhanGiaiAnh.add("HD (1280x720)");
            listDoPhanGiaiAnh.add("VGA (640x480)");

            final TextView edtDoPhanGiai =dialog.findViewById(R.id.edtChatLuongAnh);
            edtDoPhanGiai.setText(tDoPhanGiai[0]);
            /**TẠO POPUP EDIT TEXT*/
            edtDoPhanGiai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(congtacActivity.this, edtDoPhanGiai);
                    for (String s : listDoPhanGiaiAnh) {
                        popupMenu.getMenu().add(s);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem menuItem) {
                            edtDoPhanGiai.setText(menuItem.getTitle().toString());
                            tDoPhanGiai[0]= (String) edtDoPhanGiai.getText();
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            /**
             * Buton
             */
            Button btnThoat = dialog.findViewById(R.id.btnthoat);
            btnThoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            Button btnOK = dialog.findViewById(R.id.btnOK);
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveDataOnCacher("1@" + tChitiet[0] + "@2@" + tThuong[0] + "@3@" + tKhuyetTat[0] + "@4@" + tTheChung[0]+ "@5@" + tTheThoiGian[0] + "@6@" + tToaDo[0] + "@7@" + tViTri[0]+ "@8@" + tDoPhanGiai[0]+ "@9@" + tLiveCam[0], "CHEDOCHUP");
                    dialog.dismiss();
                }
            });
        }
        catch (Exception e)
        {
            File Templat = new File(Environment.getExternalStorageDirectory(),"Template");
            File txtCheDoChup = new File(Templat,"CHEDOCHUP.txt");
            if (txtCheDoChup.exists()){txtCheDoChup.delete();}
            if(!txtCheDoChup.exists())
                saveDataOnCacher(UT.CheDoChup,"CHEDOCHUP");
        }
    }
    public String DataforPath(String name){
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
    private void DialogZoom(Bitmap bitmap){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_zoom);
        dialog.show();
        ImageView imageView =(ImageView) dialog.findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
        FloatingActionButton fabBack = (FloatingActionButton) dialog.findViewById(R.id.btnThoat);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialogmenu.dismiss();
    }
}

