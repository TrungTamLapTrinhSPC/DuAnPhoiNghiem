package com.example.chirag.googlesignin;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ChenAnhTuThuVien extends AppCompatActivity {
    private File mediaStorageDir;
    TextView tvCongTac,tvHangMuc;
    ImageButton btnHome,btnBack;
    ImageButton btnSuaHinh, btnToaDo,btnLuu,btnThoat;
    OutputStream outputStream;
    ImageView imgHinh;
    int posi;
    AutoCompleteTextView tvTenChiTiet;
    private String vitrichup,longi,latgi,TenChiTiet="";
    Uri image_uri,uri ;
    private String tenHM,tenTram,tenCT;
    String[] ArrayHM = new String[1000];
    String[] MangCT= new String[1000];
    ArrayList<String> Arraylist = new ArrayList<String>();
    int biendem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chen_anh_tu_thu_vien);
        AnhXa();

        NhanBien();
        Click();

        Arraylist.clear();
        Arraylist.addAll(Arrays.asList(UT.listCauKienTiepDia));
        Arraylist.addAll(Arrays.asList(UT.listMong));
        Arraylist.addAll(Arrays.asList(UT.listPhuKienCot));
        Arraylist.addAll(Arrays.asList(UT.listThanCot));
        Arraylist.addAll(Arrays.asList(UT.listTKheHo));

        ArrayAdapter<String> adapterKT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, Arraylist);
        tvTenChiTiet.setAdapter(adapterKT);
        tvTenChiTiet.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        tvTenChiTiet.setThreshold(0);
        tvTenChiTiet.setDropDownHeight(300);
    }
    public void Click(){
        btnSuaHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCrop(image_uri);
            }
        });
        btnToaDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChenAnhTuThuVien.this);
                builder.setTitle("Bạn có chắc muốn gắn lại toạ độ ảnh này không?");
                //builder.setMessage("Bạn muốn xóa folder này không?");
                // add the buttons
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ii) {
                        try
                        {
                            Bitmap image=((BitmapDrawable)imgHinh.getDrawable()).getBitmap();
                            imgHinh.setImageBitmap(GanToaDo(image));
                            Toast.makeText(getApplicationContext(),"Đã gắn lại toạ độ cho hình ảnh",Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {

                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BitmapDrawable drawable = (BitmapDrawable) imgHinh.getDrawable();
                    /***/
                    Bitmap bitmap2 = drawable.getBitmap();
                    Bitmap bitmap = GanToaDo(bitmap2);
                    /***/
                    String tenhangmucluu;
                    tenhangmucluu=tenHM;
                    String tencongtacluu;
                    tencongtacluu=tenCT;
                        mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
                        mediaStorageDir = new File(mediaStorageDir, tenTram);
                       File fileluuanh = new File(mediaStorageDir,tenhangmucluu);
                        fileluuanh = new File(fileluuanh, tencongtacluu);
                    /// ngày giờ
                    Time today = new Time(Time.getCurrentTimezone());
                    String Bien = "--1--";
                    today.setToNow();
                    String time = new String();
                    int month = today.month +1;
                    File[] lengthFD = fileluuanh.listFiles();
                    if(lengthFD.length==0)
                        Bien="--1--";
                    else if(lengthFD.length>0)
                    {
                        for(File file:lengthFD)
                        {
                            String[] Check = file.getPath().split("--");

                            if(Check[1].equals("1"))
                            {
                                File anhCu = new File(file.getPath());
                                File anhMoi = new File(Check[0]+"--0--"+Check[2]);
                                anhCu.renameTo(anhMoi);
                                //Log.d(" Đã chuyển",anhMoi.getName());
                            }
                        }
                        Bien="--1--";
                    }
                    String gio = today.format("%k:%M:%S").replace(":","@").replace("_","@");
                    String Chitiet = tvTenChiTiet.getText().toString().replace("\\.",",").replace("_","").replace("@","");
                    time ="@HIENTRANG"+"@"+"@GIO"+"@"+Bien;
                    time = time.replace("@HIENTRANG",Chitiet);
                    time = time.replace("@GIO",gio);
                    File file = new File(fileluuanh, time.toString() + ".jpg");
                    //Log.d("time:",file.getPath());
                    try {
                        outputStream = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    Toast.makeText(getApplicationContext(),"Đã lưu ảnh vào thư mục:",Toast.LENGTH_SHORT).show();
                    try {
                        outputStream.flush();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        outputStream.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    Nextto_CongTacActivity(posi);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Bạn chưa chụp ảnh!",Toast.LENGTH_SHORT).show();
                }
            }

        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nextto_CongTacActivity(posi);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHome.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent = new Intent(ChenAnhTuThuVien.this, MenuTramActivity.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent = new Intent(ChenAnhTuThuVien.this,anhchupActivity.class);
                intent.putExtra("TenTram", tenTram);  // Truyền một String
                intent.putExtra("TenHangMuc",tenHM);  // Truyền một String
                intent.putExtra("TenCongTac",tenCT);  //Truyền một String
                intent.putExtra("position",  String.valueOf(posi));  // Truyền một String
                intent.putExtra("MangCT",  MangCT);  // Truyền một String
                intent.putExtra("Long", longi);  // Truyền một String
                intent.putExtra("Lat", latgi);  // Truyền một String
                intent.putExtra("ViTri", vitrichup);  // Truyền một String
                intent.putExtra("MangString", ArrayHM);  // Truyền một String
                intent.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
                finish();
                startActivity(intent);
            }
        });
    }
    private void Nextto_CongTacActivity(int position)
    {
        Intent intent2 = new Intent(ChenAnhTuThuVien.this,congtacActivity.class);
        intent2.setClass(ChenAnhTuThuVien.this, congtacActivity.class);
        intent2.putExtra("TenTram", tenTram);  // Truyền một String
        intent2.putExtra("TenHM", tenHM);  // Truyền một String
        intent2.putExtra("Long", longi);  // Truyền một String
        intent2.putExtra("Lat", latgi);  // Truyền một String
        intent2.putExtra("ViTri", vitrichup);  // Truyền một String
        intent2.putExtra("MangString", ArrayHM);  // Truyền một String
        intent2.putExtra("position", String.valueOf(position));  // Truyền một String
        intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
        intent2.putExtra("TenChiTiet",TenChiTiet);  // Truyền một String
        finish();
        startActivity(intent2);
    }
    private void NextTo_AnhChupActivity(){
        Intent intent = new Intent(ChenAnhTuThuVien.this,anhchupActivity.class);
        intent.putExtra("TenTram", tenTram);  // Truyền một String
        intent.putExtra("TenHangMuc",tenHM);  // Truyền một String
        intent.putExtra("TenCongTac",tenCT);  //Truyền một String
        intent.putExtra("position", String.valueOf(posi));  // Truyền một String
        intent.putExtra("MangCT",  MangCT);  // Truyền một String
        intent.putExtra("Long", longi);  // Truyền một String
        intent.putExtra("Lat", latgi);  // Truyền một String
        intent.putExtra("ViTri", vitrichup);  // Truyền một String
        intent.putExtra("MangString", ArrayHM);  // Truyền một String
        intent.putExtra("TenChiTiet","");  // Truyền một String
        intent.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
        startActivity(intent);
    }
    public void AnhXa(){
        btnSuaHinh = findViewById(R.id.btnChinhSua);
        btnToaDo = findViewById(R.id.btnGanLaiToDo);
        btnLuu = findViewById(R.id.btnLuu);
        btnThoat = findViewById(R.id.btnThoat);
        tvCongTac = (TextView) findViewById(R.id.tvCongtac);
        tvHangMuc = (TextView) findViewById(R.id.tvHangMuc);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        tvTenChiTiet = findViewById(R.id.tvChiTiet);
    }
    public void NhanBien(){
        Intent intent = getIntent();
        tenHM = intent.getStringExtra("TenHM");
        tenTram = intent.getStringExtra("TenTram");
        tenCT = intent.getStringExtra("TenCongTac");
        String ss = intent.getStringExtra("position");
        TenChiTiet = intent.getStringExtra("TenChiTiet");
        posi = Integer.parseInt(ss);
        ArrayHM = intent.getStringArrayExtra("MangString");
        String soluongHM = intent.getStringExtra("SoLuong");
        biendem = Integer.parseInt(soluongHM);
        latgi = intent.getStringExtra("Lat");
        MangCT = intent.getStringArrayExtra("MangCT");
        longi = intent.getStringExtra("Long");
        vitrichup = intent.getStringExtra("ViTri");
        imgHinh = findViewById(R.id.imgHinh);
        CropImage.startPickImageActivity(ChenAnhTuThuVien.this);
        tvHangMuc.setText(tenHM);
        tvCongTac.setText(tenCT);

    }
    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                uri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                startCrop(imageuri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imgHinh.setImageURI(result.getUri());
                Toast.makeText(this, "Đã sửa hình ảnh!",Toast.LENGTH_LONG).show();
            }

        }
    }
    public Bitmap GanToaDo(Bitmap bitmap2){
        Bitmap AnhDauRa = null;
        Bitmap newbitmap = null;
        /***/
        String string= DataforPath("CHEDOCHUP");
        String[] listCheDo = string.split("@");
        /***/

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
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int month = today.month +1;
        painttext.getTextBounds(text,0,text.length(),rectText);
        newcanvas.drawText(text,0,rectText.height(),painttext);
        /**
         * Toạ độ
         */
        if (listCheDo[11].contains("On"))
            if (!latgi.equals("null"))
                newcanvas.drawText(text2,0,2*rectText.height(),painttext);
        /**
         * Vị Trí
         */
        int hViTri = 3;
        if (listCheDo[11].contains("Off")) hViTri= 2;
        if (listCheDo[13].contains("On"))
            if (!latgi.equals("null"))
                newcanvas.drawText(text3,0,hViTri*rectText.height(),painttext);
        /**
         * Thẻ thời gian
         */
        if (!listCheDo[9].contains("None"))
        {
            int hThoiGian = 4;
            if (listCheDo[11].contains("Off")&& listCheDo[13].contains("On")) hThoiGian= 3;
            if (listCheDo[11].contains("Off")&& listCheDo[13].contains("Off")) hThoiGian= 2;
            //String text4 =today.monthDay + "/"+month+ "/" + today.year+ " " + today.format("%k:%M");
            String text4 = listCheDo[9].replace("hh:mm",String.valueOf(today.format("%k:%M"))).replace("dd",String.valueOf(today.monthDay)).replace("mm",String.valueOf(month)).replace("yyyy",String.valueOf(today.year));
            newcanvas.drawText(text4,0,hThoiGian*rectText.height(),painttext);
        }
        int hChiTiet = 5;
        String text4 = tvTenChiTiet.getText().toString();
        newcanvas.drawText(text4,0,hChiTiet*rectText.height(),painttext);
        AnhDauRa = newbitmap;


        return AnhDauRa;
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
}
