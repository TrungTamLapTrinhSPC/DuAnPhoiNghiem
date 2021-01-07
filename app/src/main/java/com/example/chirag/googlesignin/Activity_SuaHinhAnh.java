package com.example.chirag.googlesignin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Activity_SuaHinhAnh extends AppCompatActivity {
    TextView tvTenhinhAnh;
    ImageButton btnSuaHinh, btnToaDo,btnLuu,btnThoat;
    OutputStream outputStream;
    ImageView imgHinh;
    Uri image_uri ;
    String DiaDiem,ToaDo,MaTram,TenHinhAnh;
    File pathHinhAnh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_hinh_anh);
        AnhXa();
        NhanBien();
        Click();
    }
    private void NhanBien() {
        Intent intent =getIntent();//Nhận biến truyền từ trang danh sách cột
        MaTram= intent.getStringExtra("MaTram");
        DiaDiem=intent.getStringExtra("DiaDiem");
        TenHinhAnh=intent.getStringExtra("TenHinhAnh");
        tvTenhinhAnh.setText(TenHinhAnh);
        ToaDo = intent.getStringExtra("ToaDo");
        pathHinhAnh= new File(SPC.pathDataApp_PNDT,MaTram+ SPC.DuongDanThuMucHinhAnh);
        pathHinhAnh= new File(pathHinhAnh,TenHinhAnh);
        pathHinhAnh= new File(pathHinhAnh,"image.jpg");
        imgHinh = findViewById(R.id.imgHinh);
        //Bitmap myBitmap = BitmapFactory.decodeFile(fileAnh.getPath());
        //imgHinh.setImageBitmap(myBitmap);
        image_uri = Uri.fromFile(pathHinhAnh);
        imgHinh.setImageURI(image_uri);
    }
    private void AnhXa() {
        btnSuaHinh = findViewById(R.id.btnChinhSua);
        btnToaDo = findViewById(R.id.btnGanLaiToDo);
        btnLuu = findViewById(R.id.btnLuu);
        btnThoat = findViewById(R.id.btnThoat);
        tvTenhinhAnh = findViewById(R.id.tvTenHinhAnh);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_SuaHinhAnh.this);
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
                        onBackPressed();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_SuaHinhAnh.this);
                builder.setTitle("Bạn có chắc muốn sửa ảnh này không?");
                //builder.setMessage("Bạn muốn xóa folder này không?");
                // add the buttons
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ii) {
                        try {
                            BitmapDrawable drawable = (BitmapDrawable) imgHinh.getDrawable();
                            Bitmap bitmap = drawable.getBitmap();


                            File file2 = pathHinhAnh;
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

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Bạn chưa chụp ảnh!",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
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
                onBackPressed();
            }
        });

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
    public Bitmap GanToaDo(Bitmap bitmap){
        Bitmap AnhDauRa = null;
        Bitmap newbitmap = null;
        Bitmap bitmap2 = null;
        /**XOAY ẢNH**/
        if (bitmap.getWidth()> bitmap.getHeight())
        {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),matrix, true);
        }
        else bitmap2 = bitmap;


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
        String text ="Mã trạm:"+ MaTram;
        String text2 =ToaDo;
        String text3 =DiaDiem;

        painttext.getTextBounds(text,0,text.length(),rectText);
        newcanvas.drawText(text,0,rectText.height(),painttext);
        newcanvas.drawText(text2,0,2*rectText.height(),painttext);
        newcanvas.drawText(text3,0,3*rectText.height(),painttext);

        AnhDauRa = newbitmap;




        return AnhDauRa;
    }

}
