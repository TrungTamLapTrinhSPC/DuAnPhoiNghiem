package com.example.chirag.googlesignin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {
    private File mediaStorageDir,hangmucStorageDir,fileluuanh;
    List<String> listFile = new ArrayList<String>();
    ImageButton btnOpenImg, btnCamera;
    ImageView imgHinh;
    Button btnxoay,btnxoaytrai;
    Button btntiep,btnremoveHT,btnremoveHT2;
    EditText txtTentram;
    Time today;
    ImageButton btnHome,btnBack;
    ArrayList<String> listHT = new ArrayList<String>();
    ArrayList<String> listCT = new ArrayList<String>();
    Bitmap AnhGoc;
    ArrayList<String> ArraylistHT = new ArrayList<String>();
    TextView tvCongTac,tvHangMuc,txtCongtac,tvHienTrang,tvChiTiet;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    OutputStream outputStream;
    int REQUEST_CODE_FOLDER = 456,posi,lengthArray;
    private String Bien,vitrichup= null, longi = null, latgi = null;
    Integer[] tt = new Integer[100];
    String[] duongdananh= new String[100];
    String[] ArrayHM = new String[1000];
    int biendem;
    Uri image_uri ;
    String DanhGiaHienTrang,TenChiTiet="";
    ByteArrayOutputStream bytearrayoutputstream;
    byte[] BYTE;
    String[] ArrayString = new String[100];
    private String tenHM,tenTram,tenCT,bien,biendau,tenhm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            // Write you code here if permission already given.
        }
        tvCongTac = (TextView) findViewById(R.id.tvCongTac);
        tvHangMuc = (TextView) findViewById(R.id.tvHangMuc);
        txtCongtac = (TextView) findViewById(R.id.txtCongtac);
        btntiep = (Button) findViewById(R.id.btnTiep);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHome.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent = new Intent(CameraActivity.this, MenuTramActivity.class);
                startActivity(intent);
            }
        });
        try{
            btnBack = (ImageButton) findViewById(R.id.btnBack);
            Intent intent = getIntent();
            tenHM = intent.getStringExtra("TenHM");
            tenTram = intent.getStringExtra("TenTram");
            duongdananh = intent.getStringArrayExtra("MangCT");
            tenCT = intent.getStringExtra("TenCongTac");
            TenChiTiet = intent.getStringExtra("TenChiTiet");
            ArrayHM = intent.getStringArrayExtra("MangString");
            String soluongHM = intent.getStringExtra("SoLuong");
            biendem = Integer.parseInt(soluongHM);
            String[] output = tenTram.split("_");
            latgi = intent.getStringExtra("Lat");
            longi = intent.getStringExtra("Long");
            vitrichup = intent.getStringExtra("ViTri");
        }
        catch (Exception e) {
            Toast.makeText(CameraActivity.this, "Error #50: Lỗi nhận biến", Toast.LENGTH_LONG).show();
        }

        readFileDanhGia();
        try{
            String[] test = DanhGiaHienTrang.split("--");}
        catch (Exception e){
            String hientrang = UT.HienTrang;
            saveDataOnCacher(hientrang,"DANHGIAHIENTRANG");

        }
        /***/
        //set text
        tvHangMuc.setText(tenHM);
        txtCongtac.setText(tenCT);
        tvCongTac.setText(tenCT);
        /**Tên công tác lưu*/
        tvCongTac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(CameraActivity.this,tvCongTac);
                popupMenu.getMenuInflater().inflate(R.menu.menu_ditat,popupMenu.getMenu());
                popupMenu.getMenu().add(tenCT);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        tvCongTac.setText(menuItem.getTitle());
                        switch (menuItem.getItemId()) {
                            case R.id.action_ditat:
                                Toast.makeText(getApplicationContext(),ArrayString[lengthArray].toString() , Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        /***/
        /** drop down menu*/
        tvChiTiet = (TextView) findViewById(R.id.tvChiTiet) ;
        tvChiTiet.setText(TenChiTiet);
        tvChiTiet.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogHienTrang(tvChiTiet,listCT,"1");
                //DialogHienTrang();
                return false;
            }
        });
        tvChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(CameraActivity.this,tvChiTiet);
                //popupMenu.getMenuInflater().inflate(R.menu.popup_menu_hientrang,popupMenu.getMenu());
                String[] phuluccon = DanhGiaHienTrang.split("--");
                //Log.d("length:", String.valueOf(phuluccon.length));
                //Log.d("read 1:",phuluccon[1]);
                for (int j = 1; j < phuluccon.length; j = j + 1)
                {
                    if (j==4||j==5||j==6)
                    {
                        String[] phuluccongtac = phuluccon[j].split("_");
                        Log.d("Tên hạng mục:", phuluccongtac[0]);
                        SubMenu sub3= popupMenu.getMenu().addSubMenu(phuluccongtac[0].toString());
                        for (int i = 1; i < phuluccongtac.length; i = i + 2)
                        {
                            Log.d("Tên công tác:",phuluccongtac[i]);
                            sub3.add(0,i-1,i-1,phuluccongtac[i].toString());
                        }
                    }
                }
                popupMenu.getMenu().add("Khác");

                /**ĐỌC ĐÁNH GIÁ HIỆN TRẠNG*/
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_SHORT).show();
                        if (item.getTitle().equals("Bê tông") || item.getTitle().equals("Thép") || item.getTitle().equals("Khác")|| item.getTitle().equals("Nền đất")|| item.getTitle().equals("Cấu kiện")|| item.getTitle().equals("Đốt")|| item.getTitle().equals("Móng")) {
                            if ( item.getTitle().equals("Khác"))
                            {
                                DialogHienTrang(tvChiTiet,listCT,"1");
                            }
                        }
                        else
                        {
                            listCT.add(item.getTitle().toString());
                        }
                        String HT = "";
                        tvChiTiet.clearComposingText();
                        for (int i = 0; i < listCT.size(); i++) {
                            if (i == 0)
                                HT=HT + listCT.get(i);
                            else
                                HT=HT + ", " + listCT.get(i);
                        }
                        tvChiTiet.setText(HT);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        tvHienTrang = (TextView) findViewById(R.id.tvHienTrang) ;
        tvHienTrang.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogHienTrang(tvHienTrang,listHT,"2");
                //DialogHienTrang();
                return false;
            }
        });
        tvHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listCT.clear();
                final PopupMenu popupMenu = new PopupMenu(CameraActivity.this,tvHienTrang);
                //popupMenu.getMenuInflater().inflate(R.menu.popup_menu_hientrang,popupMenu.getMenu());
                String[] phuluccon = DanhGiaHienTrang.split("--");
                //Log.d("length:", String.valueOf(phuluccon.length));
                //Log.d("read 1:",phuluccon[1]);
                for (int j = 1; j < phuluccon.length; j = j + 1) {
                    if (j==1||j==2||j==3)
                    {
                        String[] phuluccongtac = phuluccon[j].split("_");
                        Log.d("Tên hạng mục:", phuluccongtac[0]);
                        SubMenu sub3 = popupMenu.getMenu().addSubMenu(phuluccongtac[0].toString());
                        for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                            Log.d("Tên công tác:", phuluccongtac[i]);
                            sub3.add(0, i - 1, i - 1, phuluccongtac[i].toString());
                        }
                    }
                }
                popupMenu.getMenu().add("Khác");

                /**ĐỌC ĐÁNH GIÁ HIỆN TRẠNG*/
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_SHORT).show();
                        if (item.getTitle().equals("Bê tông") || item.getTitle().equals("Thép") || item.getTitle().equals("Khác")|| item.getTitle().equals("Nền đất")|| item.getTitle().equals("Cấu kiện")) {
                            if ( item.getTitle().equals("Khác")) {
                                DialogHienTrang(tvHienTrang,listHT,"2");
                            }
                        }

                        else
                        {
                            listHT.add(item.getTitle().toString());
                        }
                        String HT = "";
                        tvHienTrang.clearComposingText();
                        for (int i = 0; i < listHT.size(); i++) {
                            if (i == 0)
                                HT=HT + listHT.get(i);
                            else
                                HT=HT + ", " + listHT.get(i);
                        }
                        tvHienTrang.setText(HT);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        /** xóa hiện trạng đã tạo*/
        btnremoveHT = (Button) findViewById(R.id.btnremoveHT);
        btnremoveHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(CameraActivity.this,btnremoveHT);
                for(String s : listHT)
                {
                    popupMenu.getMenu().add(s);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
                        builder.setTitle("Bạn muốn xóa bỏ hiện trạng này không?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int iI) {
                                listHT.remove(menuItem.getTitle());
                                tvHienTrang.setText("");
                                for (int i=0;i<listHT.size();i++)
                                {
                                    if (i==0)
                                        tvHienTrang.setText(tvHienTrang.getText() + listHT.get(i));
                                    else
                                        tvHienTrang.setText(tvHienTrang.getText() + ", " + listHT.get(i));
                                }
                            }
                        });
                        builder.setNegativeButton("Không", null);
                        // create and show the alert dialog
                        AlertDialog dialog2 = builder.create();
                        dialog2.show();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        /** xóa hiện trạng đã tạo*/
        btnremoveHT2 = (Button) findViewById(R.id.btnremoveHT2);
        btnremoveHT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(CameraActivity.this,btnremoveHT);
                for(String s : listCT)
                {
                    popupMenu.getMenu().add(s);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        listCT.remove(menuItem.getTitle());
                        tvChiTiet.setText("");
                        for (int i=0;i<listCT.size();i++)
                        {
                            if (i==0)
                                tvChiTiet.setText(tvChiTiet.getText() + listCT.get(i));
                            else
                                tvChiTiet.setText(tvChiTiet.getText() + ", " + listCT.get(i));
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        /////lấy danh sách mục
        mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,tenTram);/// lấy link vào hang mục
        /**lấy Array Hang mục*/
        File[] tenhangmuc2 =mediaStorageDir.listFiles();
        int ii = 0;
        for (File file : tenhangmuc2) {
            //Log.d("folder hạng muc",file.getName());
            ArrayString[ii] = file.getName();
            //Log.d("chuoi: ",ArrayString[i].toString());
            ii++;
        }
        lengthArray = ii - 1;
        sapxep(ArrayString);

        /***nút Thêm*/
        btntiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //readData();
                try {
                    btntiep.setBackgroundResource(R.drawable.edittext);
                    if (!tvChiTiet.getText().toString().equals("")) {
                        BitmapDrawable drawable = (BitmapDrawable) imgHinh.getDrawable();
                        /***/
                        Bitmap bitmap2 = drawable.getBitmap();
                        LuuAnh(bitmap2);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Bạn chưa chọn chi tiết!",Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Bạn chưa chụp ảnh!",Toast.LENGTH_SHORT).show();

                }
            }

        });
        /***/
        String string= DataforPath("CHEDOCHUP");
        final String[] listCheDo = string.split("@");
        /***/
        /**nút xoay*/
        btnxoay = (Button) findViewById(R.id.btnxoay);
        btnxoay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bitmap newbitmapC = null;
                    imgHinh.setImageBitmap(AnhGoc);
                    BitmapDrawable drawable = (BitmapDrawable) imgHinh.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                            matrix, true);
                    AnhGoc = rotated;
                    /////
                    if (!latgi.equals("null")) {
                        Bitmap bitmap1;
                        if (listCheDo[7].contains("On"))
                            bitmap1 = GanToaDo(AnhGoc);
                        else
                            bitmap1 = AnhGoc;
                        imgHinh.setImageBitmap(bitmap1);}
                }
                catch (Exception e){}
            }
        });
        ////nút xoay
        btnxoaytrai = (Button) findViewById(R.id.btnxoaytrai);
        btnxoaytrai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bitmap newbitmapC = null;
                    imgHinh.setImageBitmap(AnhGoc);
                    BitmapDrawable drawable = (BitmapDrawable) imgHinh.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    Matrix matrix = new Matrix();
                    matrix.postRotate(-90);
                    Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                            matrix, true);
                    AnhGoc = rotated;
                    /////
                    if (!latgi.equals("null")) {
                        Bitmap bitmap1;
                        if (listCheDo[7].contains("On"))
                            bitmap1 = GanToaDo(AnhGoc);
                        else
                            bitmap1 = AnhGoc;
                        imgHinh.setImageBitmap(bitmap1);}
                    //imgHinh.setImageBitmap(rotated);
                } catch (Exception e) {
                }
            }
        });
        ////nút back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                onBackPressed();
            }
        });

        /////
        Anhxa();
        ////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED ) {
                String[] permission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission,PERMISSION_CODE);
            }
            else  { openCamera();}
        }
        else {openCamera();}
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btnCamera.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED ) {
                        String[] permission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else  { openCamera();}
                }
                else {openCamera();}
            }
        });
        btnOpenImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // btnOpenImg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });
    }
    // Chụp ảnh và mở ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap2 = null;
        /***/
        String string= DataforPath("CHEDOCHUP");
        String[] listCheDo = string.split("@");
        /***/
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap2 = BitmapFactory.decodeStream(inputStream);
                if (bitmap2.getWidth()>bitmap2.getHeight()) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    bitmap2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(),
                            matrix, true);
                }
                Bitmap bitmap;
                //Log.d("the",listCheDo[9].toString());
                if (listCheDo[7].contains("On"))
                    bitmap = GanToaDo(bitmap2);
                else
                    bitmap = bitmap2;
                if (listCheDo[3].contains("On"))if(listCheDo[1].contains("On"))
                {
                    if (!tvChiTiet.getText().equals(""))LuuAnh(bitmap);
                    else imgHinh.setImageBitmap(bitmap);
                }
                else if(listCheDo[3].contains("Off"))LuuAnh(bitmap);
            } catch (Exception e) {
            }
        }
        else{
            try {
                AnhGoc = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
                if (AnhGoc.getWidth()>AnhGoc.getHeight()) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    AnhGoc = Bitmap.createBitmap(AnhGoc, 0, 0, AnhGoc.getWidth(), AnhGoc.getHeight(),
                            matrix, true);
                }
                Bitmap bitmap;
                //Log.d("the",listCheDo[9].toString());
                if (listCheDo[7].contains("On"))
                    bitmap = GanToaDo(AnhGoc);
                else
                    bitmap = AnhGoc;

                //Log.d("chế độ chụp",listCheDo[3]);
                if (listCheDo[3].contains("On"))
                {
                    if(listCheDo[1].contains("On"))
                    {
                        if (!tvChiTiet.getText().equals(""))
                            LuuAnh(bitmap);
                        else
                            imgHinh.setImageBitmap(bitmap);
                    }
                    else if(listCheDo[1].contains("Off"))
                        imgHinh.setImageBitmap(bitmap);

                }
                else if(listCheDo[3].contains("Off")) LuuAnh(bitmap);
            } catch (Exception e) {
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    openCamera();
                }
                else
                {Toast.makeText(this,"Permission denid..", Toast.LENGTH_SHORT).show();}
            }

        }
    }
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT , image_uri);
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);
    }
    private void Nextto_CongTacActivity(int position)
    {
        Intent intent2 = new Intent(CameraActivity.this,congtacActivity.class);
        intent2.setClass(CameraActivity.this, congtacActivity.class);
        intent2.putExtra("TenTram", tenTram);  // Truyền một String
        intent2.putExtra("TenHM", tenHM);  // Truyền một String
        intent2.putExtra("Long", longi);  // Truyền một String
        intent2.putExtra("Lat", latgi);  // Truyền một String
        intent2.putExtra("ViTri", vitrichup);  // Truyền một String
        intent2.putExtra("MangString", ArrayHM);  // Truyền một String
        intent2.putExtra("position", String.valueOf(position));  // Truyền một String
        intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
        finish();
        startActivity(intent2);
    }
    private void Anhxa() {
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        btnOpenImg = (ImageButton) findViewById(R.id.btnOpenImg);
        imgHinh = (ImageView) findViewById(R.id.imgHinh);
    }
    /*---------- Listener class to get coordinates ------------- */
    private  String herelocation(double lat,double lom){
        String cityName="";
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat,lom,10);
            if(addresses.size()>0){
                for(Address adr:addresses){
                    if(adr.getLocality() != null && adr.getLocality().length() >0)
                    {
                        cityName = adr.getLocality();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }
    private void DialogViewHienTrang(String string,final TextView textView,final ArrayList<String> arrayList) {
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
                arrayList.clear();
                String[] HT = textView.getText().toString().split(",");
                for (String s:HT)
                {
                    arrayList.add(s);
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
    @SuppressLint("RestrictedApi")
    private void DialogHienTrang(final TextView textView, final ArrayList<String> arrayList,final String loai) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_search);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Button btnOK = (Button) dialog.findViewById(R.id.btnRename);
        /**SEARCH VIEW**/
        ArraylistHT.clear();
        DanhGiaHienTrang = DataforPath("DANHGIAHIENTRANG");
        String[] phuluccon = DanhGiaHienTrang.split("--");
        //Log.d("length:", String.valueOf(phuluccon.length));
        //Log.d("read 1:",phuluccon[1]);
        for (int j = 1; j < phuluccon.length; j = j + 1) {
            if (loai.contains("1"))
            {
                if (j==4||j==5||j==6||j==7||j==8)
                {
                    String[] phuluccongtac = phuluccon[j].split("_");
                    //Log.d("Tên hạng mục:", phuluccongtac[0]);
                    for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                        //Log.d("Tên Hiện trạng:",phuluccongtac[i]);
                        ArraylistHT.add(phuluccongtac[i]);
                    }
                }
            }
            else if (loai.contains("2"))
            {
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
        }
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
        if (loai.contains("2"))
            searchEditTextCK.setText(textView.getText());
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
            public void onClick(View view) {
                if (loai.contains("1"))
                {
                    if (textView.getText().equals(""))
                        textView.setText(textView.getText() + searchEditTextCK.getText().toString());
                    else
                        textView.setText(textView.getText() + ", " + searchEditTextCK.getText().toString());
                    arrayList.add(searchEditTextCK.getText().toString());
                    if (!DanhGiaHienTrang.contains(searchEditTextCK.getText().toString()))
                    {
                        DanhGiaHienTrang = DanhGiaHienTrang +"_"+searchEditTextCK.getText().toString()+"_";
                        saveDataOnCacher(DanhGiaHienTrang,"DANHGIAHIENTRANG");
                    }
                    dialog.dismiss();

                }
                else if (loai.contains("2"))
                {
                    if (textView.getText().equals(""))
                        textView.setText(textView.getText() + searchEditTextCK.getText().toString());
                    else
                        textView.setText(textView.getText() + ", " + searchEditTextCK.getText().toString());
                    arrayList.add(searchEditTextCK.getText().toString());
                    if (!DanhGiaHienTrang.contains(searchEditTextCK.getText().toString()))
                    {
                        DanhGiaHienTrang = DanhGiaHienTrang +"_"+searchEditTextCK.getText().toString()+"_";
                        saveDataOnCacher(DanhGiaHienTrang,"DANHGIAHIENTRANG");
                    }
                    dialog.dismiss();
                }
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

    public void sapxep(String[] chuoi){

        ArrayList<Student> ar = new ArrayList<Student>();
        for(int i=0;i<=lengthArray;i++)
        {
            String[] tt1 = chuoi[i].split("\\.",2);
            try
            {
                tt[i] = Integer.parseInt(tt1[0]);
                ar.add(new CameraActivity.Student(tt[i], tt1[1]));
            }
            catch (Exception e){Toast.makeText(getApplicationContext(), "Hãy thêm số thứ tự đằng trước!!!", Toast.LENGTH_SHORT).show();}

        }

        Collections.sort(ar, new Sortbyroll());
        //System.out.println("\nSorted by rollno");
        for (int y=0; y<ar.size(); y++)
            //System.out.println(ar.get(y));
            ArrayString[y] = ar.get(y).toString();
    }
    class Student
    {
        int rollno;
        String name;

        // Constructor
        public Student(int rollno, String name)
        {
            this.rollno = rollno;
            this.name = name;

        }

        // Used to print student details in main()
        public String toString()
        {
            return this.rollno + "." + this.name;
        }
    }

    class Sortbyroll implements Comparator<Student>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(CameraActivity.Student a, CameraActivity.Student b)
        {
            return a.rollno - b.rollno;
        }
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
    public Bitmap GanToaDo(Bitmap bitmap2)
    {
        Bitmap AnhDauRa = null;
        Bitmap newbitmap = null;
        /***/
        String string= DataforPath("CHEDOCHUP");
        String[] listCheDo = string.split("@");
        /***/
        if (!latgi.equals("null"))
        {
            Bitmap.Config config = bitmap2.getConfig();
            config = Bitmap.Config.ARGB_8888;
            newbitmap=Bitmap.createBitmap(bitmap2.getWidth(),bitmap2.getHeight(),config);
            Canvas newcanvas = new Canvas(newbitmap);
            newcanvas.drawBitmap(bitmap2,0,0,null);
            Paint painttext = new Paint(Paint.ANTI_ALIAS_FLAG);
            painttext.setColor(Color.WHITE);
            painttext.setTextSize(bitmap2.getWidth()/35);
            //painttext.setShadowLayer(10f,10f,10f,Color.BLACK);
            Rect rectText = new Rect();
            String[] maTram = tenTram.split("_");
            String text ="Mã trạm:"+maTram[0];
            String text2 =longi+"'N"+" - "+latgi+"'E";
            String text3 =vitrichup;
            today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            int month = today.month +1;
            painttext.getTextBounds(text,0,text.length(),rectText);
            newcanvas.drawText(text,0,rectText.height(),painttext);
            /**
             * Toạ độ
             */
            if (listCheDo[11].contains("On"))
                newcanvas.drawText(text2,0,2*rectText.height(),painttext);
            /**
             * Vị Trí
             */
            int hViTri = 3;
            if (listCheDo[11].contains("Off")) hViTri= 2;
            if (listCheDo[13].contains("On"))
                newcanvas.drawText(text3,0,hViTri*rectText.height(),painttext);
            /**
             * Thẻ thời gian
             */
            if (!listCheDo[9].contains("None")){
                int hThoiGian = 4;
                if (listCheDo[11].contains("Off")&& listCheDo[13].contains("On")) hThoiGian= 3;
                if (listCheDo[11].contains("Off")&& listCheDo[13].contains("Off")) hThoiGian= 2;
                if (listCheDo[11].contains("Off")&& listCheDo[13].contains("On")) hThoiGian= 3;
                //String text4 =today.monthDay + "/"+month+ "/" + today.year+ " " + today.format("%k:%M");
                String text4 = listCheDo[9].replace("hh:mm",String.valueOf(today.format("%k:%M"))).replace("dd",String.valueOf(today.monthDay)).replace("mm",String.valueOf(month)).replace("yyyy",String.valueOf(today.year));
                newcanvas.drawText(text4,0,hThoiGian*rectText.height(),painttext);}
            AnhDauRa = newbitmap;
        }
        else
            //imgHinh.setImageBitmap(bitmap2);
            AnhDauRa = newbitmap;

        return AnhDauRa;
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
    public void LuuAnh(Bitmap bitmap2)
    {
        //readData();
        try {
            btntiep.setBackgroundResource(R.drawable.edittext);
            if (!tvChiTiet.getText().toString().equals("")) {
                bytearrayoutputstream = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, bytearrayoutputstream);
                BYTE = bytearrayoutputstream.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(BYTE, 0, BYTE.length);
                bitmap = BITMAP_RESIZER(bitmap2, bitmap2.getWidth() /2, bitmap2.getHeight() / 2);
                /***/
                today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                String tenhangmucluu;
                tenhangmucluu = tvHangMuc.getText().toString();
                String tencongtacluu;
                tencongtacluu = tvCongTac.getText().toString();
                File fileluuanh = new File(mediaStorageDir, tenhangmucluu);
                if (tencongtacluu.equals("Dị tật bất thường")) {
                    File fileLuuDiTat = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
                    fileLuuDiTat = new File(fileLuuDiTat, tenTram);
                    fileLuuDiTat = new File(fileLuuDiTat, ArrayString[lengthArray].toString());
                    if(!tvHienTrang.getText().toString().equals(""))
                    fileluuanh = new File(fileLuuDiTat,tvChiTiet.getText().toString()+"-"+tvHienTrang.getText().toString().replace("_",""));
                    else
                    fileluuanh = new File(fileLuuDiTat,tvChiTiet.getText().toString()+" dị tật"+today.format("%k_%M_%S"));

                    if (!fileluuanh.exists()) {
                        if (!fileluuanh.mkdirs()) {
                            Log.d("App", "failed to create directory");
                        } else {
                            Toast.makeText(getApplicationContext(), "Đã thêm " + fileluuanh.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
                    mediaStorageDir = new File(mediaStorageDir, tenTram);
                    fileluuanh = new File(mediaStorageDir, tenhangmucluu);
                    fileluuanh = new File(fileluuanh, tencongtacluu);
                }
                /// ngày giờ
                String time = new String();
                int month = today.month + 1;
                File[] lengthFD = fileluuanh.listFiles();
                if (lengthFD.length == 0)
                    Bien = "--1--";
                else if (lengthFD.length > 0) {
                    for (File file : lengthFD) {
                        String[] Check = file.getPath().split("--");

                        if (Check[1].equals("1")) {
                            File anhCu = new File(file.getPath());
                            File anhMoi = new File(Check[0] + "--0--" + Check[2]);
                            anhCu.renameTo(anhMoi);
                            Log.d(" Đã chuyển", anhMoi.getName());
                        }
                    }
                    Bien = "--1--";
                }
                String gio = today.format("%k:%M:%S").replace(":","@").replace("_","@");
                String Chitiet = tvChiTiet.getText().toString().replace("\\.",",").replace("_","").replace("@","");
                String HienTrang = tvHienTrang.getText().toString().replace("\\.",",").replace("_","").replace("@","");
                time ="@HIENTRANG"+"@"+"@GIO"+"@"+Bien;
                time = time.replace("@HIENTRANG","image");
                time = time.replace("@GIO",gio);                          ///
                File file = new File(fileluuanh, time.toString() + ".jpg");
                Log.d("time:", file.getPath());

                try {
                    outputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                Toast.makeText(getApplicationContext(), "Đã lưu ảnh vào thư mục:", Toast.LENGTH_SHORT).show();
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
                AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
                builder.setTitle("Bạn muốn thêm ảnh cho công tác này không?");
                //builder.setMessage("Bạn muốn xóa folder này không?");
                // add the buttons
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = getIntent();
                        String s = intent.getStringExtra("position");
                        posi = Integer.parseInt(s);
                        Intent intent2 = new Intent(CameraActivity.this, CameraActivity.class);
                        intent2.putExtra("TenHM", tenHM);  // Truyền một String
                        intent2.putExtra("TenTram", tenTram);  // Truyền một String
                        intent2.putExtra("TenCongTac", duongdananh);  // Truyền một String
                        intent2.putExtra("TenCongTac", "Dị tật bất thường");  // Truyền một String
                        intent2.putExtra("position", String.valueOf(posi));  // Truyền một String
                        intent2.putExtra("Long", longi);  // Truyền một String
                        intent2.putExtra("Lat", latgi);  // Truyền một String
                        intent2.putExtra("TenChiTiet", TenChiTiet);  // Truyền một String
                        intent2.putExtra("ViTri", vitrichup);  // Truyền một String
                        intent2.putExtra("MangString", ArrayHM);  // Truyền một String
                        intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
                        finish();
                        startActivity(intent2);

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String[] Stt = tenHM.split("\\.");
                        Intent intent=new Intent();
                        intent.setClass(CameraActivity.this, congtacActivity.class);
                        intent.putExtra("TenTram", tenTram);  // Truyền một String
                        intent.putExtra("TenHM", tenHM);  // Truyền một String
                        intent.putExtra("Long", longi);  // Truyền một String
                        intent.putExtra("Lat", latgi);  // Truyền một String
                        intent.putExtra("ViTri", vitrichup);  // Truyền một String
                        intent.putExtra("TenChiTiet", TenChiTiet);  // Truyền một String
                        intent.putExtra("position", Stt[0]);  // Truyền một String
                        intent.putExtra("MangString", ArrayHM);  // Truyền một String
                        intent.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
                        finish();
                        startActivity(intent);
                    }
                });
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();
                */
                String[] Stt = tenHM.split("\\.");
                Intent intent=new Intent();
                intent.setClass(CameraActivity.this, congtacActivity.class);
                intent.putExtra("TenTram", tenTram);  // Truyền một String
                intent.putExtra("TenHM", tenHM);  // Truyền một String
                intent.putExtra("Long", longi);  // Truyền một String
                intent.putExtra("Lat", latgi);  // Truyền một String
                intent.putExtra("ViTri", vitrichup);  // Truyền một String
                intent.putExtra("TenChiTiet", TenChiTiet);  // Truyền một String
                intent.putExtra("position", Stt[0]);  // Truyền một String
                intent.putExtra("MangString", ArrayHM);  // Truyền một String
                intent.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
                finish();
                startActivity(intent);

            }
            else
                Toast.makeText(getApplicationContext(),"Bạn chưa chọn hiện trạng!",Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Bạn chưa chụp ảnh!",Toast.LENGTH_SHORT).show();

        }
    }

}

