package com.example.chirag.googlesignin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_DanhSach_CongTrinh extends AppCompatActivity {
    HorizontalListView listview;
    ImageButton btnBack;
    List<DoiTuong_CongTrinh> list_CongTrinh = new ArrayList<>();
    Adapter_DoiTuong_CongTrinh adapter_doiTuong_CongTrinh;
    FloatingActionButton fab;
    String MaTram,TenCot,TenTramGoc,TenAnten,DiaDiem,ToaDo;
    TextView title,tvToaDo,tvViTri;
    File pathThietKeAnten;
    ArrayList<EditText> listEditText;
    int [] listID;
    EditText[] listedt;
    Button btnLuu;
    //EDITTEXT
    EditText edtTenCongTrinh,edtChieuCao,edtKhoangCach,edtSoTang,edtGocPhuongVi,edtDoDay,edtDoRong;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_congtrinh);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        SuKien();
        NhanBien();
        try {
            SettupCongTrinhCaoTang();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            SettupListView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void SettupCongTrinhCaoTang() throws JSONException {
        File filecongtrinh = new File (pathThietKeAnten,"CongTrinhCaoTang");
        if (filecongtrinh.exists())
        {
            if (filecongtrinh.isDirectory()){
                File[] listcongtrinh = filecongtrinh.listFiles();
                for(File congtrinh:listcongtrinh)
                {
                    if(congtrinh.getName().contains(".txt"))
                    {
                        SPC.ReadListEditText_Json(congtrinh.getName(),filecongtrinh,listEditText,SPC.ThietKeCongTrinh);
                    }
                }
            }
        }
        else SPC.TaoThuMuc(filecongtrinh);


    }
    private void SettupListView() throws JSONException {
        list_CongTrinh.clear();
        File filecongtrinh = new File (pathThietKeAnten,"CongTrinhThapTang");
        if (filecongtrinh.exists()){
            if (filecongtrinh.isDirectory()){
                File[] listcongtrinh = filecongtrinh.listFiles();
                if (listcongtrinh.length ==0){
                    listview.setVisibility(View.GONE);
                } else
                {
                    listview.setVisibility(View.VISIBLE);
                }
                for(File congtrinh:listcongtrinh)
                {
                    if(congtrinh.getName().contains(".txt"))
                    {
                        String thietkecongtrinh = SPC.readText(congtrinh);
                        JSONObject jsonObject = new JSONObject(thietkecongtrinh);
                        String TenCongTrinh = jsonObject.getString("TenCongTrinh");
                        String ChieuCao = jsonObject.getString("ChieuCao");
                        String KhoangCach = jsonObject.getString("KhoangCach");
                        String SoTang = jsonObject.getString("SoTang");
                        String GocPhuongVi = jsonObject.getString("GocPhuongVi");
                        String DoDay = jsonObject.getString("DoDay");
                        String DoRong = jsonObject.getString("DoRong");
//                        String[] mangcongtrinh = thietkecongtrinh.split("&");
//                        String TenCongTrinh = mangcongtrinh[SPC.TimViTri("TenCongTrinh",SPC.ThietKeCongTrinh)];
//                        String ChieuCao = mangcongtrinh[SPC.TimViTri("ChieuCao",SPC.ThietKeCongTrinh)];
//                        String KhoangCach = mangcongtrinh[SPC.TimViTri("KhoangCach",SPC.ThietKeCongTrinh)];
//                        String SoTang = mangcongtrinh[SPC.TimViTri("SoTang",SPC.ThietKeCongTrinh)];
//                        String GocPhuongVi = mangcongtrinh[SPC.TimViTri("GocPhuongVi",SPC.ThietKeCongTrinh)];
//                        String DoDay = mangcongtrinh[SPC.TimViTri("DoDay",SPC.ThietKeCongTrinh)];
//                        String DoRong = mangcongtrinh[SPC.TimViTri("DoRong",SPC.ThietKeCongTrinh)];
                        list_CongTrinh.add(new DoiTuong_CongTrinh(TenCongTrinh,ChieuCao,KhoangCach,SoTang,GocPhuongVi,DoDay,DoRong));
                    }
                }
            }
            else {
                SPC.TaoThuMuc(filecongtrinh);
                Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã tạo " + filecongtrinh.getName(), Toast.LENGTH_SHORT).show();

            }
        }

        /**HIỂN THỊ RA MÀN HÌNH*/
        adapter_doiTuong_CongTrinh = new Adapter_DoiTuong_CongTrinh(list_CongTrinh, Activity_DanhSach_CongTrinh.this,R.layout.item_congtrinh);
        listview.setAdapter(adapter_doiTuong_CongTrinh);
    }

    private void NhanBien() {
        Intent intent =getIntent();//Nhận biến truyền từ trang danh sách cột
        MaTram =intent.getStringExtra("MaTram");
        TenCot =intent.getStringExtra("TenCot");
        TenAnten =intent.getStringExtra("TenAnten");
        TenTramGoc =intent.getStringExtra("TenTramGoc");
        title.setText(MaTram+" - "+TenAnten);
        DiaDiem=intent.getStringExtra("DiaDiem");
        tvViTri.setText(DiaDiem);
        ToaDo = intent.getStringExtra("ToaDo");
        tvToaDo.setText(ToaDo);
        pathThietKeAnten = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/" + TenCot + "/" + TenTramGoc+ "/" + TenAnten);
    }

    private void SuKien() {

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogMenu(position);
                return true;
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemCongtrinh();
            }


        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtTenCongTrinh = findViewById(R.id.edtTenCongTrinh);
                EditText edtChieuCaoCongTrinh =findViewById(R.id.edtChieuCaoCongTrinh);
                EditText edtKhoangCach = findViewById(R.id.edtKhoangCach);
                EditText edtSoTang = findViewById(R.id.edtSoTang);
                EditText edtGocPhuongVi = findViewById(R.id.edtGocPhuongVi);
                EditText edtDoDay = findViewById(R.id.edtDoDay);
                EditText edtDoRong = findViewById(R.id.edtDoRong);
                Button btnLuu = findViewById(R.id.btnLuu);
                btnLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<EditText> listEditText = new ArrayList<EditText>(Arrays.asList(edtTenCongTrinh,edtChieuCaoCongTrinh,edtKhoangCach,edtSoTang,edtGocPhuongVi,edtDoDay,edtDoRong));
                        for( EditText edt:listEditText){
                            if(edt.getText().toString().trim().equals(""))
                            {
                                Toast.makeText(Activity_DanhSach_CongTrinh.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            else
                            {
                                File pathDuLieu = new File(pathThietKeAnten, "CongTrinhCaoTang");
                                if(pathDuLieu.exists())
                                {
                                    try {
                                        SPC.SaveListEditText_json(edtTenCongTrinh.getText().toString(),pathDuLieu,listEditText,SPC.ThietKeCongTrinh);
                                        Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã lưu dữ liệu!", Toast.LENGTH_SHORT).show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        SettupListView();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                else
                                {
                                    SPC.TaoThuMuc(pathDuLieu);
                                    Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã tạo " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();

                                }
                            }

                        }
                    }
                });
            }
        });
    }
    private void AnhXa() {
        listview = findViewById(R.id.listview_congtrinh);
        btnBack = findViewById(R.id.btnBack);
        fab = findViewById(R.id.fab);
        title = findViewById(R.id.title);
        tvToaDo = findViewById(R.id.tvToaDo);
        tvViTri = findViewById(R.id.tvViTri);
        btnLuu = findViewById(R.id.btnLuu);
        listID = new int[]{R.id.edtTenCongTrinh,R.id.edtChieuCaoCongTrinh,R.id.edtKhoangCach,R.id.edtSoTang,R.id.edtGocPhuongVi,R.id.edtDoDay,R.id.edtDoRong};
        listedt = new EditText[]{edtTenCongTrinh,edtChieuCao,edtKhoangCach,edtSoTang,edtGocPhuongVi,edtDoDay,edtDoRong};
        listEditText = new ArrayList<EditText>();
        for(int i= 0;i<listedt.length;i++)
        {
            listedt[i] =  (EditText) findViewById(listID[i]);
            listEditText.add(listedt[i]);
        }
    }
    private void DialogThemCongtrinh() {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_CongTrinh.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_congtrinh);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();

        EditText edtTenCongTrinh = dialogthongso.findViewById(R.id.edtTenCongTrinh);
        EditText edtChieuCaoCongTrinh = dialogthongso.findViewById(R.id.edtChieuCaoCongTrinh);
        EditText edtKhoangCach = dialogthongso.findViewById(R.id.edtKhoangCach);
        EditText edtSoTang = dialogthongso.findViewById(R.id.edtSoTang);
        EditText edtGocPhuongVi = dialogthongso.findViewById(R.id.edtGocPhuongVi);
        EditText edtDoDay = dialogthongso.findViewById(R.id.edtDoDay);
        EditText edtDoRong = dialogthongso.findViewById(R.id.edtDoRong);

        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<EditText> listEditText = new ArrayList<EditText>(Arrays.asList(edtTenCongTrinh,edtChieuCaoCongTrinh,edtKhoangCach,edtSoTang,edtGocPhuongVi,edtDoDay,edtDoRong));
                for( EditText edt:listEditText){
                    if(edt.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(Activity_DanhSach_CongTrinh.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        File pathDuLieu = new File(pathThietKeAnten, "CongTrinhThapTang");
                        if(pathDuLieu.exists())
                        {
                            try {
                                SPC.SaveListEditText_json(edtTenCongTrinh.getText().toString(),pathDuLieu,listEditText,SPC.ThietKeCongTrinh);
                                Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã lưu dữ liệu!", Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                SettupListView();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dialogthongso.dismiss();
                        }
                        else {
                            SPC.TaoThuMuc(pathDuLieu);
                            Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã tạo " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();

                        }
                    }

                }
            }
        });

    }
    private void DialogMenu(int vt){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_CongTrinh.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_menu_main);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();
        Button btnXoa = (Button) dialogthongso.findViewById(R.id.btnXoa);
        Button btnSua = (Button) dialogthongso.findViewById(R.id.btnmenudoiten);btnSua.setText("Chỉnh sửa");
        Button btnLoad = (Button) dialogthongso.findViewById(R.id.btnupload);btnLoad.setVisibility(View.GONE);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_DanhSach_CongTrinh.this);
                builder.setTitle("Bạn muốn xóa trạm này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File pathDuLieu = new File(pathThietKeAnten, "CongTrinhThapTang");
                        if(pathDuLieu.exists())
                        {
                            File file = new File(pathDuLieu,list_CongTrinh.get(vt).getTenCongTrinh()+".txt");
                            try {
                                file.delete();
                                Toast.makeText(getApplicationContext(),"Đã xóa công trình!", Toast.LENGTH_SHORT).show();
                                SettupListView();
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            dialogthongso.dismiss();
                        }

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
            public void onClick(View v) {
                DialogDoiten(vt);
                dialogthongso.dismiss();

            }
        });
    }
    private void DialogDoiten(int vt){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_CongTrinh.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_congtrinh);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();
        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle);tvTitle.setText("Chỉnh sửa");
        EditText edtTenCongTrinh = dialogthongso.findViewById(R.id.edtTenCongTrinh);
        EditText edtChieuCaoCongTrinh = dialogthongso.findViewById(R.id.edtChieuCaoCongTrinh);
        EditText edtKhoangCach = dialogthongso.findViewById(R.id.edtKhoangCach);
        EditText edtSoTang = dialogthongso.findViewById(R.id.edtSoTang);
        EditText edtGocPhuongVi = dialogthongso.findViewById(R.id.edtGocPhuongVi);
        EditText edtDoDay = dialogthongso.findViewById(R.id.edtDoDay);
        EditText edtDoRong = dialogthongso.findViewById(R.id.edtDoRong);
        ArrayList<EditText> listEditText = new ArrayList<EditText>(Arrays.asList(edtTenCongTrinh,edtChieuCaoCongTrinh,edtKhoangCach,edtSoTang,edtGocPhuongVi,edtDoDay,edtDoRong));
        File filecongtrinh = new File(pathThietKeAnten, "CongTrinhThapTang");
        try {
            SPC.ReadListEditText_Json(list_CongTrinh.get(vt).getTenCongTrinh()+".txt",filecongtrinh,listEditText,SPC.ThietKeCongTrinh);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);btnLuu.setText("Lưu chỉnh sửa");
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for( EditText edt:listEditText){
                    if(edt.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(Activity_DanhSach_CongTrinh.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        File pathDuLieu = new File(pathThietKeAnten, "CongTrinhThapTang");
                        if(pathDuLieu.exists())
                        {
                            try {
                                SPC.SaveListEditText_json(edtTenCongTrinh.getText().toString(),pathDuLieu,listEditText,SPC.ThietKeCongTrinh);
                                Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã lưu dữ liệu!", Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                SettupListView();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dialogthongso.dismiss();
                        }
                        else {
                            SPC.TaoThuMuc(pathDuLieu);
                            Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã tạo " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();

                        }
                    }

                }
            }
        });

    }
}
