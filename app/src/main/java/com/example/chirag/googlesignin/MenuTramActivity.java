package com.example.chirag.googlesignin;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.apache.commons.io.FileUtils;
import android.widget.AutoCompleteTextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MenuTramActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    private ViewStub stubGrid;
    private ViewStub stubList;
    Dialog dialog,dialog2;
    TextView tvToaDo,tvViTri;
    ListView listView;
    ImageView imgThanhPho;
    private String NGAYCAI=null,KEYACTTIVE=null;
    private String vitrichup= "null",thanhpho ="null", longi = "null", latgi = "null";
    private GridView girdView;
    private ListviewAdapter listViewAdapter;
    private GridviewAdapter gridviewAdapter;
    private List<Product> productList;
    ArrayList<String> listThanhPho = new ArrayList<String>();
    private File mediaStorageDir;
    List<String> listFile = new ArrayList<String>();
    ArrayList<String> listTram = new ArrayList<String>();
    private int curnntView = 0;
    String tentramcu,tendatatramcu,tentrammoi,phuluc=null,maTram,viTri;
    int vt;
    private static final String CHAR_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
    private static final int RANDOM_STRING_LENGTH = 8;
    static final  int VIEW_MODE_LISTVIEW = 0;
    static final  int VIEW_MODE_GRIDVIEW = 1;
    boolean checkkey=false;
    /***/
    public static final int RequestPermissionCode = 1;
    GoogleApiClient googleApiClient;
    FusedLocationProviderClient fusedLocationProviderClient;
    int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_tram_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        /**XIN CẤP QUYỀN BỘ NHỚ**/
        /***/
        Time today;
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        initPermission();
        initPermission_W();
        File Template = new File(Environment.getExternalStorageDirectory(),"Template");
        /**THÊM FOLDER GIÁM SÁT*/
        if (!Template.exists()) {
            if (!Template.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuTramActivity.this);
                builder.setTitle("Đã thêm thư viện cây thư mục");
                builder.setNegativeButton("OK", null);
                // create and show the alert dialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();
                /***Tạo phụ lục dây co*/
                phuluc = UT.TramDayCo;
                saveDataOnCacher(phuluc,"COTDAYCO");
                /***Tạo phụ lục Tự ĐỨNG*/
                phuluc = UT.TramTuDung;
                saveDataOnCacher(phuluc,"COTTUDUNG");
                saveDataOnCacher(UT.HienTrang,"DANHGIAHIENTRANG");
            }
        }
        File PHULUC  = new File(Template,"COTDAYCO.txt");
        if (!PHULUC.exists())
        {
            /***Tạo phụ lục dây co*/
            phuluc = UT.TramDayCo;
            saveDataOnCacher(phuluc,"COTDAYCO");
            /***Tạo phụ lục Tự ĐỨNG*/
            phuluc = UT.TramTuDung;
            saveDataOnCacher(phuluc,"COTTUDUNG");
        }
        File HIENTRANG   = new File(Template,"DANHGIAHIENTRANG.txt");
        if (!HIENTRANG.exists())
        {
            /***Tạo phụ lục dây co*/
            saveDataOnCacher(UT.HienTrang,"DANHGIAHIENTRANG");
        }
        File HienTrangBeTong   = new File(Template,"HienTrangBeTong.txt");
        if (!HienTrangBeTong.exists())
        {
            /***Tạo phụ lục dây co*/
            saveDataOnCacher(UT.HienTrangBeTong,"HienTrangBeTong");
        }
        File HienTrangThep   = new File(Template,"HienTrangThep.txt");
        if (!HienTrangThep.exists())
        {
            /***Tạo phụ lục dây co*/
            saveDataOnCacher(UT.HienTrangThep,"HienTrangThep");
        }
        File DeXuatBeTong   = new File(Template,"DeXuatBeTong.txt");
        if (!DeXuatBeTong.exists())
        {
            /***Tạo phụ lục dây co*/
            saveDataOnCacher(UT.DeXuatBeTong,"DeXuatBeTong");
        }
        File DeXuatThep   = new File(Template,"DeXuatThep.txt");
        if (!DeXuatThep.exists())
        {
            /***Tạo phụ lục dây co*/
            saveDataOnCacher(UT.DeXuatThep,"DeXuatThep");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Viettel Camera");
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);
        stubList = (ViewStub) findViewById(R.id.stub_list);
        tvToaDo = findViewById(R.id.tvToaDo);
        tvViTri = findViewById(R.id.tvViTri);
        stubList.inflate();
        stubGrid.inflate();
        girdView = (GridView) findViewById(R.id.myGrid);
        listView = (ListView) findViewById(R.id.mylistView);
        //
        mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        getProductList(mediaStorageDir);
        SharedPreferences sharedPreferences = getSharedPreferences("ViewsMode", MODE_PRIVATE);
        curnntView = sharedPreferences.getInt("currentView", VIEW_MODE_GRIDVIEW);

        //Register item lick
        listView.setOnItemLongClickListener(onItemLongClickListener);
        listView.setOnItemClickListener(onItemClick);
        girdView.setOnItemLongClickListener(onItemLongClickListener);
        girdView.setOnItemClickListener(onItemClick);
        switchView();
        /// ghi bien
        ///
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSelect();
            }
        });
    }
    private void switchView(){
        if(VIEW_MODE_LISTVIEW == curnntView) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.GONE);
            //Display gridview
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }
    private void setAdapters(){
        if(VIEW_MODE_LISTVIEW == curnntView) {
            listViewAdapter = new ListviewAdapter(this, R.layout.list_item, productList);
            listView.setAdapter(listViewAdapter);
        } else
         {
            gridviewAdapter = new GridviewAdapter(this, R.layout.grid_item, productList);
            girdView.setAdapter(gridviewAdapter);
        }
    }
    public List<Product> getProductList(File f){
        productList = new ArrayList<>();
        File[] files=f.listFiles();
        listFile.clear();
        try{
            for (File file:files)
            {
                String[] output = file.getName().split("_");
                listTram.add(output[0]);
                listTram.add(output[1]);
                productList.add(new Product(R.drawable.ic_folder_open_black_24dp, output[0], output[1]));
            }
        }
        catch (Exception e){
            //e.printStackTrace();
            mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath());
            /**THÊM FOLDER GIÁM SÁT*/
            File GIAMSAT = new File(mediaStorageDir,"GIAMSAT");
            if (!GIAMSAT.exists()) {
                if (!GIAMSAT.mkdirs()) {
                    Log.d("App", "failed to create directory");
                }
                else {AlertDialog.Builder builder = new AlertDialog.Builder(MenuTramActivity.this);
                    builder.setTitle("Đã thêm thư mục chứa hình ảnh");
                    builder.setNegativeButton("OK", null);
                    // create and show the alert dialog
                    AlertDialog dialog1 = builder.create();
                    dialog1.show();
                }
            }
            /**THÊM FOLDER GIÁM SÁT*/
            File Template = new File(Environment.getExternalStorageDirectory(),"Template");
            if (!Template.exists()) {
                if (!Template.mkdirs()) {
                    Log.d("App", "failed to create directory");
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MenuTramActivity.this);
                    builder.setTitle("Đã thêm thư viện cây thư mục");
                    builder.setNegativeButton("OK", null);
                    // create and show the alert dialog
                    AlertDialog dialog1 = builder.create();
                    dialog1.show();
                    /***Tạo phụ lục dây co*/
                    phuluc = UT.TramDayCo;
                    saveDataOnCacher(phuluc,"COTDAYCO");
                    /***Tạo phụ lục Tự ĐỨNG*/
                    phuluc = UT.TramTuDung;
                    saveDataOnCacher(phuluc,"COTTUDUNG");
                }
            }
            mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        }
        listViewAdapter = new ListviewAdapter(this, R.layout.list_item, productList);
        listView.setAdapter(listViewAdapter);
        return productList;
    }
    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
            initPermission();
            initPermission_W();
            checkKEYactive();
            //checkCauTrucAnh(productList.get(position).getTitle() + "_" + productList.get(position).getDescription());
            if(checkkey)
            {
                File folderTram = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
                folderTram = new File(folderTram,productList.get(position).getTitle() + "_" + productList.get(position).getDescription());
                if (folderTram.exists())
                {
                    File[] sohangmuc = folderTram.listFiles();
                    if(sohangmuc.length > 8)
                        KiemTraCauTrucThuMuc(productList.get(position).getTitle() + "_" + productList.get(position).getDescription());
                }

                Toast.makeText(getApplicationContext(), productList.get(position).getTitle() + " - " + productList.get(position).getDescription(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MenuTramActivity.this, hangmucActivity.class);
                intent.putExtra("TenTram", productList.get(position).getTitle() + "_" + productList.get(position).getDescription());  // Truyền một String
                if (longi!=null && latgi!=null)
                {
                    intent.putExtra("Long", longi);  // Truyền một String
                    intent.putExtra("Lat", latgi);  // Truyền một String
                    intent.putExtra("ViTri", vitrichup);  // Truyền một String
                }
                else
                {
                    intent.putExtra("Long", "null");  // Truyền một String
                    intent.putExtra("Lat", "null");  // Truyền một String
                    intent.putExtra("ViTri", "null");  // Truyền một String
                }
                startActivity(intent);
            }
            else
            {
            }
            /*
            Intent  intent3= new Intent(MenuTramActivity.this, MainActivityHienTrang.class);
            startActivity(intent3);*/
        }
    };
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

            DialogMenu(position);
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(100);
            return true;
        }
    };
    private void DialogSelect(){
        dialog2 = new Dialog(MenuTramActivity.this,R.style.PauseDialog);
        dialog2.setContentView(R.layout.dialog_select_typle);
        dialog2.show();
        Button btnTudung = (Button) dialog2.findViewById(R.id.btntudung);
        Button btnDayco = (Button) dialog2.findViewById(R.id.btndayco);
        btnTudung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd("COTTUDUNG");
                //Log.d("dayCo",dayCo);
            }
        });
        btnDayco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd("COTDAYCO");
                dialog2.dismiss();
            }
        });
    }
    private void DialogMenu(int vt2){
        dialog = new Dialog(MenuTramActivity.this,R.style.PauseDialog);
        dialog.setContentView(R.layout.dialog_menu_main);
        dialog.show();
        vt = vt2;
        Button btnmenudoiten = (Button) dialog.findViewById(R.id.btnmenudoiten);
        Button btnmenuxoa= (Button) dialog.findViewById(R.id.btnmenuxoa);
        btnmenudoiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogRename(vt);
            }
        });
        btnmenuxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuTramActivity.this);
                builder.setTitle("Bạn muốn xóa folder này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tentramcu = productList.get(vt).getTitle()+"_"+productList.get(vt).getDescription();
                        xoaFolder(tentramcu);

                        dialog.dismiss();
                        restartActivity(MenuTramActivity.this);
                    }
                });
                builder.setNegativeButton("không", null);
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();

            }
        });
        Button btnupload = (Button) dialog.findViewById(R.id.btnupload);
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MenuTramActivity.this);
                if (account==null)
                signIn();
                else if(account != null)
                {
                    Intent intent = new Intent(MenuTramActivity.this, UserActivity.class);
                    intent.putExtra("TenTram", productList.get(vt).getTitle()+"_"+productList.get(vt).getDescription());  // Truyền một String
                    startActivity(intent);
                    dialog.dismiss();
                }
                dialog.dismiss();
            }
        });
    }
    private void DialogRename(int position){
        final Dialog dialog = new Dialog(MenuTramActivity.this,R.style.PauseDialog);
        dialog.setContentView(R.layout.dialog_rename);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        ArrayList<String> list = new ArrayList<String>();
        listThanhPho.clear();
        list.addAll(Arrays.asList(UT.tenThanhpho));
        SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.txtThanhPho);
        searchViewCT.setQueryHint("Nhập tên thành phố");
        Collections.sort(list);
        listThanhPho.add("Khác...");
        listThanhPho.addAll(list);

        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);

        final EditText searchEditTextCK = (EditText)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditTextCK.setTextColor(getResources().getColor(R.color.colorPrimary));
        searchEditTextCK.setTextSize(18);
        searchEditTextCK.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        searchEditTextCK.setText(productList.get(position).getDescription());

        ImageView searchMagIcon = (ImageView)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchMagIcon.setImageResource(R.drawable.ic_search_xam_24dp);

        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listThanhPho);
        searchAutoCompleteCT.setAdapter(newsAdapterCT);
        searchAutoCompleteCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoCompleteCT.setText(queryString);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
        tentramcu = productList.get(position).getTitle()+"_"+productList.get(position).getDescription();
        tendatatramcu ="Data" + productList.get(vt).getTitle()+"_"+productList.get(vt).getDescription();

        imgThanhPho = (ImageView) dialog.findViewById(R.id.imgThanhPho);
        imgThanhPho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(MenuTramActivity.this, imgThanhPho);
                for (String s : listThanhPho)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        if (!menuItem.getTitle().toString().equals("Khác..."))
                            searchAutoCompleteCT.setText(menuItem.getTitle().toString());
                        else
                        {
                            //(searchAutoCompleteCT);
                        }
                        //searchAutoCompleteCT.setText(menuItem.getTitle().toString());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        final EditText txtTentram = (EditText) dialog.findViewById(R.id.txtMaTram);
        Button btnOK = (Button) dialog.findViewById(R.id.btnRename);
        txtTentram.setText(productList.get(position).getTitle());
        vt=position;
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ĐỔI TÊN THƯ MỤC ẢNH
                tentrammoi = txtTentram.getText() + "_" + searchAutoCompleteCT.getText().toString();
                File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
                File fileold = new File(mediaStorageDir,tentramcu);
                File filenew = new File(mediaStorageDir,tentrammoi);
                fileold.renameTo(filenew);

                //ĐỔI TÊN THƯ MỤC ẢNH
                String tendatatrammoi ="Data" +  txtTentram.getText() + "_" + searchAutoCompleteCT.getText().toString();
                File fileData = new File(Environment.getExternalStorageDirectory(),"DataViettel");
                File fileDataold = new File(fileData,tendatatramcu);
                File fileDatanew = new File(fileData,tendatatrammoi);
                fileDataold.renameTo(fileDatanew);

                productList.remove(vt);
                productList.add(vt,new Product(R.drawable.ic_folder_open_black_24dp, txtTentram.getText().toString(), searchAutoCompleteCT.getText().toString()));
                dialog.dismiss();
                restartActivity(MenuTramActivity.this);
                Toast.makeText(getApplicationContext(), "Đã đổi tên thành công", Toast.LENGTH_SHORT).show();
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
    private void DialogAdd(final String loaicot) {
        dialog = new Dialog(MenuTramActivity.this,R.style.PauseDialog);
        dialog.setContentView(R.layout.dialog_add);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        // Ánh xạ
        readData2(loaicot);
        ArrayList<String> list = new ArrayList<String>();
        listThanhPho.clear();
        list.addAll(Arrays.asList(UT.tenThanhpho));
        AutoCompleteTextView searchViewCT = (AutoCompleteTextView) dialog.findViewById(R.id.txtThanhPho);
        Collections.sort(list);
        listThanhPho.add("Khác...");
        listThanhPho.addAll(list);
        // Get SearchView autocomplete object.
        searchViewCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        searchViewCT.setText(thanhpho);
        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listThanhPho);
        searchViewCT.setAdapter(newsAdapterCT);
        searchViewCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchViewCT.setText(queryString);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
        imgThanhPho = (ImageView) dialog.findViewById(R.id.imgThanhPho) ;
        imgThanhPho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(MenuTramActivity.this, imgThanhPho);
                for (String s : listThanhPho)
                {
                    popupMenu.getMenu().add(s);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        if (!menuItem.getTitle().toString().equals("Khác..."))
                            searchViewCT.setText(menuItem.getTitle().toString());
                        else
                        {
                            //DialogThemLuaChon(searchAutoCompleteCT);
                        }
                        //searchAutoCompleteCT.setText(menuItem.getTitle().toString());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        final EditText txtMaTram = (EditText) dialog.findViewById(R.id.txtMaTram);
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
                /**Kiểm tra trùng thư mục*/
                maTram = txtMaTram.getText().toString().toUpperCase();
                viTri = searchViewCT.getText().toString().trim();

                if (!maTram.equals("") && !viTri.equals(""))
                {
                    mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
                    File HANGMUC = new File(mediaStorageDir,maTram+"_"+viTri);
                    if (!HANGMUC.exists())
                    {
                        if (!HANGMUC.mkdirs())
                        {
                            Log.d("App", "failed to create directory");
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Đã thêm " + maTram, Toast.LENGTH_SHORT).show();
                            restartActivity(MenuTramActivity.this);
                        }
                        /**TẠO FOLDER HẠNG MỤC*/
                        try {
                            //Log.d("lengh phu luc", String.valueOf(phuluc.length()));
                            String[] phuluccon = phuluc.split("--");
                            for (int j = 1; j < phuluccon.length; j = j + 1) {
                                String[] phuluccongtac = phuluccon[j].split("_");
                                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                                String[] loctap =  phuluccongtac[0].split("\t");
                                String[] locdoupble =  loctap[0].split("  ");
                                File CONGTAC = new File(HANGMUC, locdoupble[0].trim());
                                //L/og.d("loc tap", locdoupble[0]);
                                if (!CONGTAC.exists())
                                {
                                    if (!CONGTAC.mkdirs()) {
                                        Log.d("App", "failed to create directory");
                                    } else
                                    {
                                        //restartActivity(MenuTramActivity.this);
                                    }
                                }
                                /**TẠO FOLDER CÔNG TÁC*/
                                for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                                    Log.d("Tên công tác:", phuluccongtac[i]);
                                    File ANHCHUP = new File(CONGTAC, phuluccongtac[i]);
                                    if (!ANHCHUP.exists()) {
                                        if (!ANHCHUP.mkdirs())
                                        {
                                            Log.d("App", "failed to create directory");
                                        }
                                        else
                                        {
                                            //restartActivity(MenuTramActivity.this);
                                        }
                                    }
                                }
                            }
                        }
                        catch (Exception e)
                        {
                            /***Tạo phụ lục dây co*/
                            phuluc = UT.TramDayCo;
                            saveDataOnCacher(phuluc,"COTDAYCO");
                            /***Tạo phụ lục Tự ĐỨNG*/
                            phuluc = UT.TramTuDung;
                            saveDataOnCacher(phuluc,"COTTUDUNG");
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Thư mục đã tồn tại!", Toast.LENGTH_SHORT).show();
                        try {
                            //Log.d("lengh phu luc", String.valueOf(phuluc.length()));
                            String[] phuluccon = phuluc.split("--");
                            for (int j = 1; j < phuluccon.length; j = j + 1) {
                                String[] phuluccongtac = phuluccon[j].split("_");
                                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                                String[] loctap =  phuluccongtac[0].split("\t");
                                String[] locdoupble =  loctap[0].split("  ");
                                File CONGTAC = new File(HANGMUC, locdoupble[0]);
                                //L/og.d("loc tap", locdoupble[0]);
                                if (!CONGTAC.exists())
                                {
                                    if (!CONGTAC.mkdirs()) {
                                        Log.d("App", "failed to create directory");
                                    } else
                                    {
                                        //restartActivity(MenuTramActivity.this);
                                    }
                                }
                                /**TẠO FOLDER CÔNG TÁC*/
                                for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                                    Log.d("Tên công tác:", phuluccongtac[i]);
                                    File ANHCHUP = new File(CONGTAC, phuluccongtac[i]);
                                    if (!ANHCHUP.exists()) {
                                        if (!ANHCHUP.mkdirs())
                                        {
                                            Log.d("App", "failed to create directory");
                                        }
                                        else
                                        {
                                            //restartActivity(MenuTramActivity.this);
                                        }
                                    }
                                }
                            }
                        }
                        catch (Exception e)
                        {

                        }
                    }
                    /**KIỂM TRA CÓ CÂY THU MỤC CHƯA*/
                    try
                    {
                        restartActivity(MenuTramActivity.this);
                    }

                    /**TẠO CÂY THƯ MỤC**/
                    catch (Exception e)
                    {
                        /***Tạo phụ lục dây co*/
                        phuluc = UT.TramDayCo;
                        saveDataOnCacher(phuluc,"COTDAYCO");
                        /***Tạo phụ lục Tự ĐỨNG*/
                        phuluc = UT.TramTuDung;
                        saveDataOnCacher(phuluc,"COTTUDUNG");
                    }
                }

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.holo_blue_light);

        // Create a new ArrayAdapter and add data to search auto complete object.
        //String dataArr[] = {"Apple" , "Amazon" , "Amd", "Microsoft", "Microwave", "MicroNews", "Intel", "Intelligence"};

        ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listTram);
        searchAutoComplete.setAdapter(newsAdapter);
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText(queryString);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listViewAdapter.filter(query);

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                listViewAdapter.filter(newText);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                if(VIEW_MODE_LISTVIEW == curnntView) {
                    curnntView = VIEW_MODE_GRIDVIEW;
                    item.setIcon(R.drawable.ic_view_module_black_24dp);
                } else {
                    curnntView = VIEW_MODE_LISTVIEW;
                    item.setIcon(R.drawable.ic_view_list_black_24dp);
                }
                //Switch view
                switchView();
                //Save view mode in share reference
                SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentViewMode", curnntView);
                editor.commit();
                break;
            case R.id.itemrestart:
                restartActivity(MenuTramActivity.this);
                break;


        }
        return true;
    }
    public static void restartActivity(Activity act){

        Intent intent=new Intent();
        intent.setClass(act, act.getClass());
        act.startActivity(intent);
        act.finish();

    }
    public void readData2(String name){

        BufferedReader input = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "Template");
            file = new File(file, name+".txt"); // Pass getFilesDir() and "MyFile" to read file
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null)
            {
                buffer.append(line).append("\n");
            }
            phuluc = buffer.toString();
                /*
                //Log.d("read-data:",buffer.toString());
                String[] phuluccon = phuluc.split("--");
                //Log.d("length:", String.valueOf(phuluccon.length));
                //Log.d("read 1:",phuluccon[1]);
                for (int j = 1; j < phuluccon.length; j = j + 2) {
                    String[] phuluccongtac = phuluccon[j].split("_");
                    //Log.d("Tên hạng mục:", phuluccongtac[0]);
                    for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                        //Log.d("Tên công tác:",phuluccongtac[i]);
                    }
                }*/
        } catch (IOException e)
        {

            /**THÊM FOLDER GIÁM SÁT*/
            File Template = new File(Environment.getExternalStorageDirectory(),"Template");
            if (!Template.exists()) {
                if (!Template.mkdirs()) {
                    Log.d("App", "failed to create directory");
                }
                else {Toast.makeText(getApplicationContext(), "Đã thêm folder Template vào bộ nhớ" , Toast.LENGTH_SHORT).show();
                    /***Tạo phụ lục dây co*/
                    phuluc = UT.TramDayCo;
                    saveDataOnCacher(phuluc,"COTDAYCO");
                    /***Tạo phụ lục Tự ĐỨNG*/
                    phuluc = UT.TramTuDung;
                    saveDataOnCacher(phuluc,"COTTUDUNG");
                }
            }
        }

    }
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
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                String city = herelocation(location.getLatitude(), location.getLongitude());
                                //Toast.makeText(getApplicationContext(), "Vị trí hiện tại :" + city, Toast.LENGTH_SHORT).show();
                                vitrichup = city;
                                longi = Double.toString(location.getLongitude());
                                latgi = Double.toString(location.getLatitude());
//                                Log.d("Lccation",longi);
//                                Log.d("Lccation",latgi);
//                                Log.d("Lccation",vitrichup);
                                tvToaDo.setText(longi + "'N" +"  "+ latgi+ "'E");
                                tvViTri.setText(vitrichup);
                            }
                        }
                    });
        }
    }
    private  String herelocation(double lat,double lom){
        String cityName="";
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat,lom,10);
            if(addresses.size()>0){
                for(Address adr:addresses){
                    if(adr.getLocality() != null && adr.getLocality().length() >0)
                    {String[] TP = adr.getAddressLine(0).split(",");
                        cityName = adr.getAddressLine(0);
                        thanhpho =TP[TP.length-3].trim() + ","+ TP[TP.length-2].trim();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }
    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        super.onStart();
    }
    @Override
    protected void onStop(){
        if (googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
        super.onStop();
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(MenuTramActivity.this, new
                String[]{ACCESS_FINE_LOCATION}, RequestPermissionCode);
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.e("MenuTramActivity", "Connection suspendedd");
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("MenuTramActivity", "Connection failed: " + connectionResult.getErrorCode());
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    public void initPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(MenuTramActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MenuTramActivity.this, "Đang đợi cấp quyền, vui lòng đợi trong giây lát!", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        }
    }
    public void initPermission_W(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(MenuTramActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MenuTramActivity.this, "Đang đợi cấp quyền, vui lòng đợi trong giây lát!", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        }
    }
    private void KiemTraCauTrucThuMuc(String tentram) {
        try {
            int sothumuctrung = 0;
            readData2("COTDAYCO");
            File HANGMUC = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
            HANGMUC = new File(HANGMUC, tentram);/// lấy link vào hang mục
            if (HANGMUC.exists())
            {
                //KIỂM TRA THU MỤC BỊ TRÙNG NHAU
                File[] danhsachthumuc = HANGMUC.listFiles();
                for (File file : danhsachthumuc)
                {
                    if (file.getName().contains("10."))
                    {
                        sothumuctrung++;
                    }
                }
                String[] phuluccon = phuluc.split("--");
                for (int j = 1; j < phuluccon.length; j = j + 1) {
                    String[] phuluccongtac = phuluccon[j].split("_");
                    String[] loctap =  phuluccongtac[0].split("\t");
                    String[] locdoupble =  loctap[0].split("  ");
                    File CONGTAC = new File(HANGMUC, locdoupble[0].trim());
                    /*if(CONGTAC.getName().contains("10.Hình ảnh dị tật"))
                        CONGTAC = new File(HANGMUC, locdoupble[0].trim());*/
                    if (sothumuctrung==0){
                        if (!CONGTAC.exists())
                        {
                            if (!CONGTAC.mkdirs())
                            {
                                Log.d("App", "failed to create directory");
                            }
                            else
                            {
                                //restartActivity(MenuTramActivity.this);
                            }
                        }
                    }
                    else
                    {
                        if (!CONGTAC.exists() && !locdoupble[0].trim().contains("Hình ảnh dị tật"))
                        {
                            if (!CONGTAC.mkdirs())
                            {
                                Log.d("App", "failed to create directory");
                            }
                            else
                            {
                                //restartActivity(MenuTramActivity.this);
                            }
                        }
                    }
                    /**TẠO FOLDER CÔNG TÁC*/
                    for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                        Log.d("Tên công tác:", phuluccongtac[i]);
                        File ANHCHUP = new File(CONGTAC, phuluccongtac[i]);
                        if (!ANHCHUP.exists()) {
                            if (!ANHCHUP.mkdirs())
                            {
                                Log.d("App", "failed to create directory");
                            }
                            else
                            {
                                //restartActivity(MenuTramActivity.this);
                            }
                        }
                    }
                }
                if (sothumuctrung>1)
                {
                    for (File file : danhsachthumuc){
                        if (file.listFiles().length==0) {
                            file.delete();
                            onBackPressed();
                        }
                    }

                }
            }
        }
        catch (Exception e)
        {

        }
    }
    public void xoaFolder(String tentramcu){
        mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,tentramcu);/// lấy link vào hang mục
        try {
            FileUtils.deleteDirectory(mediaStorageDir);
            Toast.makeText(getApplicationContext(),"Đã xóa thư mục ảnh!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"DataViettel");
        mediaStorageDir = new File(mediaStorageDir,"Data"+tentramcu);/// lấy link vào hang mục
        try {
            FileUtils.deleteDirectory(mediaStorageDir);
            Toast.makeText(getApplicationContext(),"Đã xóa thư mục dữ liệu!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try{
        mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,tentramcu);/// lấy link vào hang mục
        //Log.d("Path",mediaStorageDir.getPath());

        File[] tenhangmuc =mediaStorageDir.listFiles();
        listFile.clear();
        int i=0,j=0,z=0;
        for (File THM:tenhangmuc)
        {
            hangmucStorageDir = new File(mediaStorageDir,THM.getName());
            //Log.d("Ten hạng mục", hangmucStorageDir.getPath());
            chuoiHangMuc[i]=hangmucStorageDir.getPath();

            File[] tencongtac =hangmucStorageDir.listFiles();
            for (File TCT:tencongtac)
            {
                //Log.d("Ten công tác", TCT.getName());
                congtacStorageDir=  new File(hangmucStorageDir,TCT.getName());
                chuoiCongTac[j]=congtacStorageDir.getPath();
                File[] tenanh = congtacStorageDir.listFiles();
                for (File TIM:tenanh)
                {
                    //Log.d("Ten hình ảnh", TIM.getName());
                    hinhanhStorageDir = new File(congtacStorageDir,TIM.getName());
                    chuoiHinhAnh[z] = hinhanhStorageDir.getPath();
                    //Log.d("Ten hình ảnh",chuoiHinhAnh[z]);
                    z++;
                }
                j++;
            }
            i++;
            kichthuocchuoianh =z;
        }
        for (int ii=0;ii<z;ii++)
        {
            File file = new File(chuoiHinhAnh[ii]);
            file.delete();
            //Log.d("Ten hình ảnh", String.valueOf(z));
        }
        for (int ii=0;ii<j;ii++)
        {
            File file = new File(chuoiCongTac[ii]);
            file.delete();
        }
        for (int ii=0;ii<i;ii++)
        {
            File file = new File(chuoiHangMuc[ii]);
            file.delete();
        }
        mediaStorageDir.delete();
        Toast.makeText(getApplicationContext(),"Đã xóa "+hangmucStorageDir.getName(), Toast.LENGTH_SHORT).show();}
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error #3", Toast.LENGTH_SHORT).show();}
            */

    }
    private void checkKEYactive(){
        Time today;
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int month = today.month +1;
        BufferedReader input = null;
        File file = null;
        try {
            //Log.d("ten",tenTram+".txt");

            file = new File(Environment.getExternalStorageDirectory(), "sdhs2dkjsdf2wg34245y4hwese23534falsfh8sd3ek2jfh1gfdj.txt"); // Pass getFilesDir() and "MyFile" to read file
            String key = StringforPath(file);


            String keyday[] = key.split("t");
            String[] chuoi=new String[RANDOM_STRING_LENGTH];
            double tong;
            for (int i = 0; i < RANDOM_STRING_LENGTH; i++)
            {
                char ch = keyday[4].charAt(i);
                chuoi[i] = String.valueOf(ch);
            }
            /**GIAI MÃ KEY**/
            tong=TinhTong(chuoi);
            Log.d("Ten hình ảnh", String.valueOf(tong));
            if (keyday.length==5)
            {
                DialogKeyActive(keyday[4],tong);
            }
            else if (keyday.length>5) {
                checkkey = true;
                int ngayHetHan = Integer.parseInt(keyday[2].toString()) + 4;
                if (ngayHetHan>12)
                    ngayHetHan = ngayHetHan-12;
                /**KIỂM TRA NGÀY HẾT HẠN**/
                if (month == ngayHetHan)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MenuTramActivity.this);
                    builder.setTitle("Bạn đã hết hạn đăng nhập");
                    // add the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            File file1 = new File(Environment.getExternalStorageDirectory(), "sdhs2dkjsdf2wg34245y4hwese23534falsfh8sd3ek2jfh1gfdj.txt");
                            file1.delete();
                            finish();
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog2 = builder.create();
                    dialog2.show();
                }

            }

        } catch (Exception e) {
            saveKey();
            Toast.makeText(getApplicationContext(), "đã tạo Key", Toast.LENGTH_SHORT).show();
            checkKEYactive();
        }
    }
    private void saveMaKichHoat(String d,String name){

        File file;
        FileOutputStream outputStream;
        try
        {
            file = new File(Environment.getExternalStorageDirectory(), name+".txt");
            outputStream = new FileOutputStream(file);
            outputStream.write(d.getBytes());
            outputStream.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public String StringforPath(File file) {
        /**ĐỌC ĐƯỜNG DẪN*/
        String string="";
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

        return string;
    }
    private void saveKey(){
        Time today;
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int month = today.month +1;
        NGAYCAI = "t"+today.monthDay+"t"+month+"t"+today.year+"t";
        KEYACTTIVE = generateRandomString();
        //saveMaKichHoat(KEYACTTIVE,"MaThietBi");
        String content = NGAYCAI+KEYACTTIVE;
        saveMaKichHoat(content,"sdhs2dkjsdf2wg34245y4hwese23534falsfh8sd3ek2jfh1gfdj");
    }
    public String generateRandomString(){

        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<RANDOM_STRING_LENGTH; i++)
        {
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }
    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }
    private double TinhTong(String[] chuoi){
        double tong=1;
        for (int j=0;j<RANDOM_STRING_LENGTH;j++)
        {
            Log.d("tach chuoi",chuoi[j]);
            if( chuoi[j].equals("A"))
                tong = tong * 1;
            else if( chuoi[j].equals("B"))
                tong = tong * 2;
            else if( chuoi[j].equals("C"))
                tong = tong * 3;
            else if( chuoi[j].equals("D"))
                tong = tong * 4;
            else if( chuoi[j].equals("E"))
                tong = tong * 5;
            else if( chuoi[j].equals("F"))
                tong = tong * 6;
            else if( chuoi[j].equals("G"))
                tong = tong * 7;
            else if( chuoi[j].equals("H"))
                tong = tong * 8;
            else if( chuoi[j].equals("I"))
                tong = tong * 9;
            else if( chuoi[j].equals("K"))
                tong = tong * 10;
            else if( chuoi[j].equals("L"))
                tong = tong * 11;
            else if( chuoi[j].equals("M"))
                tong = tong * 12;
            else if( chuoi[j].equals("N"))
                tong = tong * 13;
            else if( chuoi[j].equals("O"))
                tong = tong * 14;
            else if( chuoi[j].equals("P"))
                tong = tong * 15;
            else if( chuoi[j].equals("Q"))
                tong = tong * 16;
            else if( chuoi[j].equals("R"))
                tong = tong * 17;
            else if( chuoi[j].equals("S"))
                tong = tong * 18;
            else if( chuoi[j].equals("T"))
                tong = tong * 19;
            else if( chuoi[j].equals("U"))
                tong = tong * 20;
            else if( chuoi[j].equals("V"))
                tong = tong * 21;
            else if( chuoi[j].equals("W"))
                tong = tong * 22;
            else if( chuoi[j].equals("X"))
                tong = tong * 23;
            else if( chuoi[j].equals("Y"))
                tong = tong * 24;
            else if( chuoi[j].equals("Z"))
                tong = tong * 25;
            else if( chuoi[j].equals("1"))
                tong = tong * 1;
            else if( chuoi[j].equals("2"))
                tong = tong * 2;
            else if( chuoi[j].equals("3"))
                tong = tong * 3;
            else if( chuoi[j].equals("4"))
                tong = tong * 4;
            else if( chuoi[j].equals("5"))
                tong = tong * 5;
            else if( chuoi[j].equals("6"))
                tong = tong * 6;
            else if( chuoi[j].equals("7"))
                tong = tong * 7;
            else if( chuoi[j].equals("8"))
                tong = tong * 8;
            else if( chuoi[j].equals("9"))
                tong = tong * 9;
        }
        return tong;
    }
    private void DialogKeyActive(String MaThietBi,final double tong) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_keyactive);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
        Button btnthoat = (Button) dialog.findViewById(R.id.btnthoat);
        final TextView tvMaTB = (TextView) dialog.findViewById(R.id.tvMaThietBi);
        final EditText tvKeyActive = (EditText) dialog.findViewById(R.id.tvKeyActive);
        tvMaTB.setText(MaThietBi);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double manhap = Double.parseDouble(tvKeyActive.getText().toString());
                if (manhap == tong)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MenuTramActivity.this);
                    builder.setTitle("Đã kích hoạt thành công mã thiết bị, bạn có 4 tháng để sử dụng");
                    // add the buttons
                    builder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            checkkey = true;
                            File file = new File(Environment.getExternalStorageDirectory(), "sdhs2dkjsdf2wg34245y4hwese23534falsfh8sd3ek2jfh1gfdj.txt"); // Pass getFilesDir() and "MyFile" to read file
                            String key = StringforPath(file);
                            file.delete();
                            Time today;
                            today = new Time(Time.getCurrentTimezone());
                            today.setToNow();
                            int month = today.month +1;
                            NGAYCAI = "t"+today.monthDay+"t"+month+"t"+today.year+"t";
                            //saveMaKichHoat(KEYACTTIVE,"MaThietBi");
                            String content = NGAYCAI+tvMaTB.getText().toString();
                            String keyLuu = content+"t"+ tvKeyActive.getText().toString()+"tjhvasv69tHJVSCIHOI7AdHQHaikvnxwn8h3nhajc8wba5";
                            saveMaKichHoat(keyLuu,"sdhs2dkjsdf2wg34245y4hwese23534falsfh8sd3ek2jfh1gfdj");
                            dialog.dismiss();
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog2 = builder.create();
                    dialog2.show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MenuTramActivity.this);
                    builder.setTitle("Mã kích hoạt sai!");
                    builder.setNegativeButton("OK", null);
                    // create and show the alert dialog
                    AlertDialog dialog1 = builder.create();
                    dialog1.show();
                }
            }
        });
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(MenuTramActivity.this, UserActivity.class);
            intent.putExtra("TenTram", productList.get(vt).getTitle()+"_"+productList.get(vt).getDescription());  // Truyền một String
            startActivity(intent);        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(MenuTramActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }


}