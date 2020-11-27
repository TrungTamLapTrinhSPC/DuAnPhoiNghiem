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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class suaAnh extends AppCompatActivity {
    Spinner spinner_d;
    private File mediaStorageDir,hangmucStorageDir;
    List<String> listFile = new ArrayList<String>();
    Button btntiep,btnxoay,btnremoveHT,btnremoveHT2;
    ImageButton btnOpenImg, btnCamera;
    ArrayList<String> listHT = new ArrayList<String>();
    ArrayList<String> listCT = new ArrayList<String>();
    ArrayList<String> ArraylistHT = new ArrayList<String>();
    ImageView imgHinh;
    String DanhGiaHienTrang;
    ImageButton btnHome,btnBack;
    TextView tvCongTac,tvHangMuc,txtCongtac,tvHienTrang,tvChiTiet;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    OutputStream outputStream;
    int REQUEST_CODE_FOLDER = 456,posi;
    private String vitrichup,longi,latgi;
    Time today;
    Uri image_uri ;
    EditText txtTentram;
    private String tenHM,tenTram,tenCT,tenanh,duongdananh;
    String[] ArrayHM = new String[1000];
    int biendem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_anh);

        tvCongTac = (TextView) findViewById(R.id.txtCongtac);
        tvHangMuc = (TextView) findViewById(R.id.tvHangMuc);
        txtCongtac = (TextView) findViewById(R.id.tvCongTac);
        btntiep = (Button) findViewById(R.id.btnLuuAnh);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHome.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent = new Intent(suaAnh.this, MenuTramActivity.class);
                startActivity(intent);
            }
        });
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        readFileDanhGia();
        try{
            String[] test = DanhGiaHienTrang.split("--");}
        catch (Exception e){
            String hientrang = UT.HienTrang;
            saveDataOnCacher(hientrang,"DANHGIAHIENTRANG");
        }
        ////// drop down menu
        /** drop down menu*/
        tvChiTiet = (TextView) findViewById(R.id.tvChiTiet) ;
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

                final PopupMenu popupMenu = new PopupMenu(suaAnh.this,tvChiTiet);
                //popupMenu.getMenuInflater().inflate(R.menu.popup_menu_hientrang,popupMenu.getMenu());
                String[] phuluccon = DanhGiaHienTrang.split("--");
                //Log.d("length:", String.valueOf(phuluccon.length));
                //Log.d("read 1:",phuluccon[1]);
                for (int j = 1; j < phuluccon.length; j = j + 1)
                {
                    if (j==4||j==5||j==6)
                    {
                        String[] phuluccongtac = phuluccon[j].split("_");
                        //Log.d("Tên hạng mục:", phuluccongtac[0]);
                        SubMenu sub3= popupMenu.getMenu().addSubMenu(phuluccongtac[0].toString());
                        for (int i = 1; i < phuluccongtac.length; i = i + 2)
                        {
                            //Log.d("Tên công tác:",phuluccongtac[i]);
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
                            if (tvChiTiet.getText().equals(""))
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
                final PopupMenu popupMenu = new PopupMenu(suaAnh.this,tvHienTrang);
                //popupMenu.getMenuInflater().inflate(R.menu.popup_menu_hientrang,popupMenu.getMenu());
                String[] phuluccon = DanhGiaHienTrang.split("--");
                //Log.d("length:", String.valueOf(phuluccon.length));
                //Log.d("read 1:",phuluccon[1]);
                for (int j = 1; j < phuluccon.length; j = j + 1) {
                    if (j==1||j==2||j==3)
                    {
                        String[] phuluccongtac = phuluccon[j].split("_");
                        //Log.d("Tên hạng mục:", phuluccongtac[0]);
                        SubMenu sub3 = popupMenu.getMenu().addSubMenu(phuluccongtac[0].toString());
                        for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                            //Log.d("Tên công tác:", phuluccongtac[i]);
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
                            if (tvHienTrang.getText().equals(""))
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
                final PopupMenu popupMenu = new PopupMenu(suaAnh.this,btnremoveHT);
                for(String s : listHT)
                {
                    popupMenu.getMenu().add(s);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(suaAnh.this);
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
                final PopupMenu popupMenu = new PopupMenu(suaAnh.this,btnremoveHT);
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
        Intent intent = getIntent();
        tenHM = intent.getStringExtra("TenHM");
        tvHienTrang.setText(intent.getStringExtra("HienTrang"));
        String[] HT = tvHienTrang.getText().toString().split(",");
        for (String s:HT)
        {
            listHT.add(s);
        }
        tenanh = intent.getStringExtra("Tenanhsua");
        duongdananh = intent.getStringExtra("DuongDanAnh");
        tenTram = intent.getStringExtra("TenTram");
        tenCT = intent.getStringExtra("TenCongTac");
        String ss = intent.getStringExtra("position");
        Log.d("position",ss);
        ArrayHM = intent.getStringArrayExtra("MangString");
        String soluongHM = intent.getStringExtra("SoLuong");
        biendem = Integer.parseInt(soluongHM);
        posi = Integer.parseInt(ss);
        latgi = intent.getStringExtra("Lat");
        longi = intent.getStringExtra("Long");
        vitrichup = intent.getStringExtra("ViTri");
        //bien = intent.getStringExtra("bienchay");
        String[] output = tenTram.split("_");
        File fileAnh = new File(duongdananh,tenanh);
        Log.d("đương dẫn ảnh",fileAnh.getPath());
        imgHinh = (ImageView) findViewById(R.id.imgHinh);
        Bitmap myBitmap = BitmapFactory.decodeFile(fileAnh.getPath());
        imgHinh.setImageBitmap(myBitmap);

        //set text
        tvHangMuc.setText(tenHM);
        txtCongtac.setText(tenCT);
        tvCongTac.setText(tenCT);
        ////// drop down menu
        /////lấy danh sách mục
        mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,tenTram);/// lấy link vào hang mục
        ////nút Tiếp
        btntiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //readData();
                AlertDialog.Builder builder = new AlertDialog.Builder(suaAnh.this);
                builder.setTitle("Bạn có chắc muốn sửa ảnh này không?");
                //builder.setMessage("Bạn muốn xóa folder này không?");
                // add the buttons
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ii) {
                        try {
                            BitmapDrawable drawable = (BitmapDrawable) imgHinh.getDrawable();
                            Bitmap bitmap = drawable.getBitmap();
                            String[] nameImg = tenanh.split("@");
                            Log.d("file ảnh mới:", String.valueOf(nameImg.length));
                            nameImg[0]=tvChiTiet.getText().toString()+ tvHienTrang.getText().toString();
                            String name=nameImg[0];
                            for (int i=1;i<nameImg.length;i++)
                            {

                                name=name+"@"+nameImg[i];
                            }

                            File file = new File(duongdananh, name);
                            File file2 = new File(duongdananh, tenanh);
                            file2.delete();
                            try {
                                outputStream = new FileOutputStream(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                            Toast.makeText(getApplicationContext(),"Đã lưu ảnh vào thư mục:",Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(suaAnh.this,anhchupActivity.class);
                            intent.putExtra("TenTram", tenTram);  // Truyền một String
                            intent.putExtra("TenHangMuc",tenHM);  // Truyền một String
                            intent.putExtra("TenCongTac",tenCT);  //Truyền một String
                            intent.putExtra("position",  String.valueOf(posi));  // Truyền một String
                            intent.putExtra("MangCT",  duongdananh);  // Truyền một String
                            intent.putExtra("Long", longi);  // Truyền một String
                            intent.putExtra("Lat", latgi);  // Truyền một String
                            intent.putExtra("ViTri", vitrichup);  // Truyền một String
                            intent.putExtra("MangString", ArrayHM);  // Truyền một String
                            intent.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
                            finish();
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Bạn chưa chụp ảnh!",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(suaAnh.this,anhchupActivity.class);
                        intent.putExtra("TenTram", tenTram);  // Truyền một String
                        intent.putExtra("TenHangMuc",tenHM);  // Truyền một String
                        intent.putExtra("TenCongTac",tenCT);  //Truyền một String
                        intent.putExtra("position",  String.valueOf(posi));  // Truyền một String
                        intent.putExtra("MangCT",  duongdananh);  // Truyền một String
                        intent.putExtra("Long", longi);  // Truyền một String
                        intent.putExtra("Lat", latgi);  // Truyền một String
                        intent.putExtra("ViTri", vitrichup);  // Truyền một String
                        intent.putExtra("MangString", ArrayHM);  // Truyền một String
                        intent.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
                        finish();
                        startActivity(intent);
                    }
                });
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();

            }

        });
        ////nút xoay
        btnxoay = (Button) findViewById(R.id.btnxoay);
        btnxoay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BitmapDrawable drawable = (BitmapDrawable) imgHinh.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                            matrix, true);
                    imgHinh.setImageBitmap(rotated);
                }
                catch (Exception e){}
            }
        });
        ////nút back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent = new Intent(suaAnh.this,anhchupActivity.class);
                intent.putExtra("TenTram", tenTram);  // Truyền một String
                intent.putExtra("TenHangMuc",tenHM);  // Truyền một String
                intent.putExtra("TenCongTac",tenCT);  //Truyền một String
                intent.putExtra("position",  String.valueOf(posi));  // Truyền một String
                intent.putExtra("MangCT",  duongdananh);  // Truyền một String
                intent.putExtra("Long", longi);  // Truyền một String
                intent.putExtra("Lat", latgi);  // Truyền một String
                intent.putExtra("ViTri", vitrichup);  // Truyền một String
                intent.putExtra("MangString", ArrayHM);  // Truyền một String
                intent.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
                finish();
                startActivity(intent);
            }
        });

        /////
        Anhxa();
        ////
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
        Bitmap newbitmap = null;
        Bitmap newbitmapC = null;
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap2 = BitmapFactory.decodeStream(inputStream);
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
                String text2 =longi+"N"+" - "+latgi+"E";
                String text3 =vitrichup;
                today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                int month = today.month +1;
                String text4 =today.monthDay + "/"+month+ "/" + today.year+ " " + today.format("%k:%M");
                painttext.getTextBounds(text,0,text.length(),rectText);
                newcanvas.drawText(text,0,rectText.height(),painttext);
                newcanvas.drawText(text2,0,2*rectText.height(),painttext);
                newcanvas.drawText(text3,0,3*rectText.height(),painttext);
                newcanvas.drawText(text4,0,4*rectText.height(),painttext);
                imgHinh.setImageBitmap(newbitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                imgHinh.setImageURI(image_uri);
                BitmapDrawable drawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                        matrix, true);
                /////
                Bitmap.Config configC = rotated.getConfig();
                configC = Bitmap.Config.ARGB_8888;
                newbitmapC =Bitmap.createBitmap(rotated.getWidth(),rotated.getHeight(),configC);
                Canvas newcanvasC = new Canvas(newbitmapC);
                newcanvasC.drawBitmap(rotated,0,0,null);
                Paint painttextC = new Paint(Paint.ANTI_ALIAS_FLAG);
                painttextC.setColor(Color.WHITE);
                painttextC.setTextSize(rotated.getWidth()/35);
                Rect rectText = new Rect();
                String[] maTram = tenTram.split("_");
                String text ="Mã trạm:"+maTram[0];
                String text2 =longi+"N"+" - "+latgi+"E";
                String text3 =vitrichup;
                today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                int month = today.month +1;
                String text4 =today.monthDay + "/"+month+ "/" + today.year+ " " + today.format("%k:%M");
                painttextC.getTextBounds(text,0,text.length(),rectText);
                newcanvasC.drawText(text,0,rectText.height(),painttextC);
                newcanvasC.drawText(text2,0,2*rectText.height(),painttextC);
                newcanvasC.drawText(text3,0,3*rectText.height(),painttextC);
                newcanvasC.drawText(text4,0,4*rectText.height(),painttextC);
                imgHinh.setImageBitmap(newbitmapC);
            } catch (Exception e) {
                e.printStackTrace();
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
    }}

