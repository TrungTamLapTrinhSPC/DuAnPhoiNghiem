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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import android.os.Bundle;
import android.text.format.Time;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Switch;
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
import java.util.Arrays;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Activity_DanhSach_AnhChup extends AppCompatActivity {
    ListView listview;
    ImageButton btnBack,btnGrid,btnSetting;
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
    ArrayList<String> listTheThoiGian = new ArrayList<String>();
    ArrayList<String> listDoPhanGiaiAnh = new ArrayList<String>();
    ArrayList<String> listDoSang = new ArrayList<String>();
    static final  int VIEW_MODE_LISTVIEW = 0;
    static int chatLuongAnh=2;
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
        if (pathHinhAnh.isDirectory()) {
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
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCheDoChup();
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
        btnSetting = findViewById(R.id.btnSetting);
        fab = findViewById(R.id.fab);

        stubGrid = (ViewStub) findViewById(R.id.stub_grid1);
        stubList = (ViewStub) findViewById(R.id.stub_list);
        stubList.inflate();
        stubGrid.inflate();
        girdView = (GridView) findViewById(R.id.myGrid);
        listview = (ListView) findViewById(R.id.mylistView);

    }
    public Bitmap GanToaDo(Bitmap bitmap2){
        Bitmap AnhDauRa = null;
        Bitmap newbitmap = null;
        /***/
        String string= SPC.DataforPath("CHEDOCHUP");
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
        String text ="Mã trạm:"+ MaTram;
        String text2 =ToaDo;
        String text3 =DiaDiem;
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int month = today.month +1;
        painttext.getTextBounds(text,0,text.length(),rectText);
        newcanvas.drawText(text,0,rectText.height(),painttext);
        /**
         * Toạ độ
         */
        if (listCheDo[11].contains("On"))
            if (!ToaDo.equals("null"))
                newcanvas.drawText(text2,0,2*rectText.height(),painttext);
        /**
         * Vị Trí
         */
        int hViTri = 3;
        if (listCheDo[11].contains("Off")) hViTri= 2;
        if (listCheDo[13].contains("On"))
            if (!ToaDo.equals("null"))
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
        /*int hChiTiet = 5;
        String text4 = tvChiTiet.getText().toString();
        newcanvas.drawText(text4,0,hChiTiet*rectText.height(),painttext);*/
        AnhDauRa = newbitmap;


        return AnhDauRa;
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
            FileOutputStream output = null;
            Bitmap bitmap2 = SPC.BITMAP_RESIZER(bitmap, bitmap.getWidth() / chatLuongAnh, bitmap.getHeight() / chatLuongAnh);
            /**XOAY ẢNH**/
            if (bitmap2.getWidth()> bitmap2.getHeight())
            {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(),matrix, true);
            }
            bitmap2 = GanToaDo(bitmap2);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG,80,stream);
            @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            byte[] byteArray = stream.toByteArray();
            try
            {
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
                if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //Toast.makeText(Activity_DanhSach_AnhChup.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                }
                else {
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
    public void DialogCheDoChup() {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_setting_camera);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            /***/

            String string = SPC.DataforPath("CHEDOCHUP");
            String[] listCheDo = string.split("@");
            if (listCheDo.length<18)
            {
                string = UT.CheDoChup;
                listCheDo = string.split("@");
                SPC.saveDataOnCacher(string,"CHEDOCHUP");
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
            final String[] tDoSang = {listCheDo[19].toString()};


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
                    final PopupMenu popupMenu = new PopupMenu(Activity_DanhSach_AnhChup.this, edtVitri);
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
             * Thẻ độ sáng
             */
            listDoSang.clear();
            listDoSang.add("Cao");
            listDoSang.add("Trung bình");
            listDoSang.add("Thấp");

            final TextView edtDoSang =dialog.findViewById(R.id.edtDoSang);
            edtDoSang.setText(tDoSang[0]);
            /**TẠO POPUP EDIT TEXT*/
            edtDoSang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(Activity_DanhSach_AnhChup.this, edtDoSang);
                    for (String s : listDoSang) {
                        popupMenu.getMenu().add(s);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem menuItem) {
                            edtDoSang.setText(menuItem.getTitle().toString());
                            tDoSang[0]= (String) edtDoSang.getText();
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
                    final PopupMenu popupMenu = new PopupMenu(Activity_DanhSach_AnhChup.this, edtDoPhanGiai);
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
                    SPC.saveDataOnCacher("1@" + tChitiet[0] + "@2@" + tThuong[0] + "@3@" + tKhuyetTat[0] + "@4@" + tTheChung[0]+ "@5@" + tTheThoiGian[0] + "@6@" + tToaDo[0] + "@7@" + tViTri[0]+ "@8@" + tDoPhanGiai[0]+ "@9@" + tLiveCam[0]+ "@10@" + tDoSang[0], "CHEDOCHUP");
                    switch (edtDoPhanGiai.getText().toString())
                    {
                        case "QHD (2560x1440)":
                            chatLuongAnh = 1;
                            break;
                        case "FHD (1920x1080)":
                            chatLuongAnh = 2;
                            break;
                        case "HD (1280x720)":
                            chatLuongAnh = 3;
                            break;
                        case "VGA (640x480)":
                            chatLuongAnh = 4;
                            break;
                    }

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
                SPC.saveDataOnCacher(UT.CheDoChup,"CHEDOCHUP");
        }
    }

}
