package com.example.chirag.googlesignin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Activity_DanhSach_AnhChup extends AppCompatActivity {
    ListView listview;
    ImageButton btnBack,btnGrid;
    private ViewStub stubGrid;
    private ViewStub stubList;
    List<DoiTuong_AnhChup> list_AnhChup = new ArrayList<>();
    Adapter_List_DoiTuong_AnhChup adapter_List_doiTuong_AnhChup;
    Adapter_Grid_DoiTuong_AnhChup adapter_grid_doiTuong_anhChup;
    File pathHinhAnh;
    String MaTram;
    File mFile;
    private GridView girdView;
    FloatingActionButton fab;
    TextView title,tvToaDo,tvViTri;
    String DiaDiem,ToaDo;
    Uri imageUri;
    static final  int VIEW_MODE_LISTVIEW = 0;
    static final  int VIEW_MODE_GRIDVIEW = 1;
    private int curnntView = VIEW_MODE_GRIDVIEW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_anhchup);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        SuKien();
        NhanBien();
        SettupListView();
        EnableRuntimePermission();
    }
    private void SettupListView(){
        list_AnhChup.clear();//Hoan thay đổi
        if (pathHinhAnh.isDirectory())
        {
            File[] listImageName = pathHinhAnh.listFiles();
            for (File ImgName : listImageName){
                if(ImgName.isDirectory())
                {
                    File[] Img  = ImgName.listFiles();
                    if(Img.length!=0)
                    {
                        String NgaySua = SPC.getLastModified(Img[0]);
                        Uri uriImage = Uri.parse(Img[0].getPath());
                        list_AnhChup.add(new DoiTuong_AnhChup(ImgName.getName(),uriImage,NgaySua));
                    }
                    else
                    {
                        list_AnhChup.add(new DoiTuong_AnhChup(ImgName.getName(),null,"Chưa chụp"));
                    }
                }
            }
        }

        /**HIỂN THỊ RA MÀN HÌNH*/
        adapter_List_doiTuong_AnhChup = new Adapter_List_DoiTuong_AnhChup(list_AnhChup, Activity_DanhSach_AnhChup.this,R.layout.item_anh_chup);
        listview.setAdapter(adapter_List_doiTuong_AnhChup);
        SharedPreferences sharedPreferences = getSharedPreferences("ViewsMode", MODE_PRIVATE);
        curnntView = sharedPreferences.getInt("currentView", VIEW_MODE_GRIDVIEW);
        switchView();

    }
    @SuppressLint("SetTextI18n")
    private void NhanBien() {
        Intent intent =getIntent();//Nhận biến truyền từ trang danh sách cột
        MaTram= intent.getStringExtra("MaTram");
        title.setText("Hình ảnh trạm "+MaTram);
        DiaDiem=intent.getStringExtra("DiaDiem");
        tvViTri.setText(DiaDiem);
        ToaDo = intent.getStringExtra("ToaDo");
        tvToaDo.setText(ToaDo);
        pathHinhAnh= new File(SPC.pathDataApp_PNDT,MaTram+ SPC.DuongDanThuMucHinhAnh);
    }
    private void SuKien(){
        listview.setOnItemClickListener(onItemClickListener);
        listview.setOnItemLongClickListener(onItemLongClickListener);
        girdView.setOnItemLongClickListener(onItemLongClickListener);
        girdView.setOnItemClickListener(onItemClickListener);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(VIEW_MODE_LISTVIEW == curnntView) {
                    curnntView = VIEW_MODE_GRIDVIEW;
                    btnGrid.setImageResource(R.drawable.ic_view_module_black_24dp);
                } else {
                    curnntView = VIEW_MODE_LISTVIEW;
                    btnGrid.setImageResource(R.drawable.ic_view_list_black_24dp);
                }
                switchView();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogThemAnh();
            }
        });
    }
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mFile = new File(pathHinhAnh,list_AnhChup.get(position).getTenAnh());
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, 7);
        }
    };
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            DialogMenu(position);
            return true;
        }
    };
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
            adapter_List_doiTuong_AnhChup = new Adapter_List_DoiTuong_AnhChup(list_AnhChup, Activity_DanhSach_AnhChup.this,R.layout.item_anh_chup);
            listview.setAdapter(adapter_List_doiTuong_AnhChup);
        } else
        {
            adapter_grid_doiTuong_anhChup = new Adapter_Grid_DoiTuong_AnhChup(list_AnhChup, Activity_DanhSach_AnhChup.this,R.layout.grid_item_anh_chup);
            girdView.setAdapter(adapter_grid_doiTuong_anhChup);
        }
    }
    private void AnhXa() {
        //listview = findViewById(R.id.listview_AnhChup);
        btnBack = findViewById(R.id.btnBack);
        title = findViewById(R.id.title);
        tvToaDo = findViewById(R.id.tvToaDo);
        tvViTri = findViewById(R.id.tvViTri);
        btnGrid = findViewById(R.id.btnGrid);
        fab = findViewById(R.id.fab);

        stubGrid = (ViewStub) findViewById(R.id.stub_grid1);
        stubList = (ViewStub) findViewById(R.id.stub_list);
        stubList.inflate();
        stubGrid.inflate();
        girdView = (GridView) findViewById(R.id.myGrid);
        listview = (ListView) findViewById(R.id.mylistView);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert bitmap != null;
            Bitmap bitmap2 = SPC.GanToaDo(bitmap,MaTram,ToaDo,DiaDiem);
            FileOutputStream output = null;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG,100,stream);
            @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            byte[] byteArray = stream.toByteArray();
            try {
                output = new FileOutputStream(new File(mFile,"image"+".jpg"));
                output.write(byteArray);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != output)
                {
                    try
                    {
                        output.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            SettupListView();
        }
    }
    public void EnableRuntimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_DanhSach_AnhChup.this,
                Manifest.permission.CAMERA)) {
            Toast.makeText(Activity_DanhSach_AnhChup.this,"CAMERA permission allows us to Access CAMERA app",     Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Activity_DanhSach_AnhChup.this,new String[]{
                    Manifest.permission.CAMERA}, 7);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] result) {
        switch (requestCode) {
            case 7:
                if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(Activity_DanhSach_AnhChup.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Activity_DanhSach_AnhChup.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    private void DialogMenu(int vt){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_AnhChup.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_menu_anh_chup);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();
        Button btnXoa = (Button) dialogthongso.findViewById(R.id.btnXoa);
        Button btnSua = (Button) dialogthongso.findViewById(R.id.btnSua);
        Button btnDanhSachAnh = (Button) dialogthongso.findViewById(R.id.btnDanhSachAnh);
        Button btnXemAnh = (Button) dialogthongso.findViewById(R.id.btnXemAnh);
        btnXemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent= new Intent(Activity_DanhSach_AnhChup.this,Activity_SuaHinhAnh.class);
                intent.putExtra("MaTram",MaTram);
                intent.putExtra("DiaDiem",tvViTri.getText().toString());
                intent.putExtra("ToaDo",tvToaDo.getText().toString());
                intent.putExtra("TenHinhAnh",list_AnhChup.get(vt).getTenAnh());
                startActivity(intent);
                dialogthongso.dismiss();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_DanhSach_AnhChup.this);
                builder.setTitle("Bạn muốn xóa trạm này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File file = new File(pathHinhAnh,list_AnhChup.get(vt).getTenAnh());
                        try {
                            FileUtils.deleteDirectory(file);
                            Toast.makeText(getApplicationContext(),"Đã xóa!", Toast.LENGTH_SHORT).show();
                            SettupListView();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        dialogthongso.dismiss();
                    }
                });
                builder.setNegativeButton("không", null);
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogDoiten(vt);
                dialogthongso.dismiss();
            }
        });
    }
    private void DialogDoiten(int vt){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_AnhChup.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_edit);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();
        Button btnSua = (Button) dialogthongso.findViewById(R.id.btnLuuThongSo);
        EditText edtMaTram = dialogthongso.findViewById(R.id.edtMaTram);
        edtMaTram.setText(list_AnhChup.get(vt).getTenAnh());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtMaTram.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(Activity_DanhSach_AnhChup.this);
                    builder.setTitle("Bạn muốn đổi tên trạm này không?");
                    // add the buttons
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            File fileOld = new File(pathHinhAnh,list_AnhChup.get(vt).getTenAnh());
                            File fileNew = new File(pathHinhAnh,edtMaTram.getText().toString());

                            if(!fileNew.exists())
                            {
                                boolean result= fileOld.renameTo(fileNew);
                                if (result) Toast.makeText(Activity_DanhSach_AnhChup.this, "Đã đổi tên!", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(Activity_DanhSach_AnhChup.this, "Đã có trạm này!", Toast.LENGTH_SHORT).show();
                            SettupListView();
                            dialogthongso.dismiss();
                        }
                    });
                    builder.setNegativeButton("không", null);
                    // create and show the alert dialog
                    AlertDialog dialog2 = builder.create();
                    dialog2.show();
                }
            }
        });

    }
    private void DialogThemAnh(){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_AnhChup.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_anten);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();

        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle);tvTitle.setText("Thêm hình ảnh");
        TextView tvTen = dialogthongso.findViewById(R.id.tvTen);tvTen.setText("Tên hình ảnh");
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuuThongSo);btnLuu.setText("Thêm");
        EditText edtTenAnten = dialogthongso.findViewById(R.id.edtTenAnten);edtTenAnten.setHint("Nhập tên hình ảnh");
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTenAnten.getText().toString().trim().equals(""))
                {
                    Toast.makeText(Activity_DanhSach_AnhChup.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    File pathDuLieu = new File(pathHinhAnh, edtTenAnten.getText().toString());
                    SPC.TaoThuMuc(pathDuLieu);
                    if(pathDuLieu.exists())
                    {
                        SettupListView();
                        dialogthongso.dismiss();
                    }
                }
            }
        });
    };
}
