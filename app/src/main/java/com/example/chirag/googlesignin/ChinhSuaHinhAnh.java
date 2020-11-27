package com.example.chirag.googlesignin;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class ChinhSuaHinhAnh extends AppCompatActivity {
    TextView tvCongTac,tvHangMuc;
    ImageButton btnHome,btnBack;
    ImageButton btnSuaHinh, btnToaDo,btnLuu,btnThoat;
    OutputStream outputStream;
    ImageView imgHinh;
    int posi;
    private String vitrichup,longi,latgi;
    Uri image_uri ;
    private String tenHM,tenTram,tenCT,tenanh,duongdananh;
    String[] ArrayHM = new String[1000];
    String[] MangCT= new String[1000];
    int biendem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_hinh_anh);
        NhanBien();
        AnhXa();
        Click();
    }
    public void Click()
    {
        btnSuaHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCrop(image_uri);
            }
        });
        btnToaDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChinhSuaHinhAnh.this);
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
                //readData();
                AlertDialog.Builder builder = new AlertDialog.Builder(ChinhSuaHinhAnh.this);
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

                            File file2 = new File(duongdananh, tenanh);
                            file2.delete();
                            try {
                                outputStream = new FileOutputStream(file2);
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
                            NextTo_AnhChupActivity();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Bạn chưa chụp ảnh!",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NextTo_AnhChupActivity();
                    }
                });
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();

            }

        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_AnhChupActivity();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHome.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent = new Intent(ChinhSuaHinhAnh.this, MenuTramActivity.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent = new Intent(ChinhSuaHinhAnh.this,anhchupActivity.class);
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
    }
    private void NextTo_AnhChupActivity()
    {
        Intent intent = new Intent(ChinhSuaHinhAnh.this,anhchupActivity.class);
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

    }
    public void NhanBien(){
        Intent intent = getIntent();
        tenHM = intent.getStringExtra("TenHM");
        tenanh = intent.getStringExtra("Tenanhsua");
        duongdananh = intent.getStringExtra("DuongDanAnh");
        tenTram = intent.getStringExtra("TenTram");
        MangCT = intent.getStringArrayExtra("MangCT");
        tenCT = intent.getStringExtra("TenCongTac");
        String ss = intent.getStringExtra("position");
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
        imgHinh = findViewById(R.id.imgHinh);
        //Bitmap myBitmap = BitmapFactory.decodeFile(fileAnh.getPath());
        //imgHinh.setImageBitmap(myBitmap);
        image_uri = Uri.fromFile(new File(fileAnh.getPath()));
        imgHinh.setImageURI(image_uri);
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
