package com.example.chirag.googlesignin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_DanhSach_BTS extends AppCompatActivity {
    ImageButton btnBack,btnThemBTS,btnMenu;
    TextView title,tvToaDo,tvViTri;
    ListView listview;
    List<DoiTuong_BTS> list_BTS = new ArrayList<>();
    Adapter_DoiTuong_BTS adapter_doiTuong_bts;
    String MaTram,TenCot,DiaDiem,ToaDo;
    File pathTramGoc;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_bts);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        SuKien();
        NhanBien();
        SettupListView();
    }

    private void SettupListView(){
        list_BTS.clear();
        list_BTS = new ArrayList<>();
        File[] files=pathTramGoc.listFiles();
        try{
            for (File fileThietKe:files)
            {
                if(fileThietKe.exists())
                {
                        String thietke = SPC.readText(fileThietKe);
                        JSONObject jsonObject = new JSONObject(thietke);
                        if (!thietke.equals(""))
                        {
                            String TenTramGoc = jsonObject.getString("TenTramGoc");
                            String BangTanHoatDong = jsonObject.getString("BangTanHoatDong");
                            String ChungLoaiThietBi = jsonObject.getString("ChungLoaiThietBi");
                            String MangSuDung = jsonObject.getString("MangSuDung");
                            list_BTS.add(new DoiTuong_BTS(TenTramGoc,ChungLoaiThietBi,BangTanHoatDong,MangSuDung));
                        }
                }
            }

        }
        catch (Exception ignored){}

        //Thay đổi thử dòng 30
        /**HIỂN THỊ RA MÀN HÌNH*/
        adapter_doiTuong_bts = new Adapter_DoiTuong_BTS(list_BTS, Activity_DanhSach_BTS.this,R.layout.item_bts);
        listview.setAdapter(adapter_doiTuong_bts);
    }

    private String DemSoAnTen(File fileTram){
        int sotram = 0;
        File[] listCot = fileTram.listFiles();
        sotram = listCot.length-1;
        return String.valueOf(sotram);
    }
    private void NhanBien() {
        Intent intent =getIntent();//Nhận biến truyền từ trang danh sách cột
        //TenCot =intent.getStringExtra("TenCot");
        MaTram =intent.getStringExtra("MaTram");
        title.setText(MaTram);
        DiaDiem=intent.getStringExtra("DiaDiem");
        tvViTri.setText(DiaDiem);
        ToaDo = intent.getStringExtra("ToaDo");
        tvToaDo.setText(ToaDo);
        pathTramGoc = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/DanhSachBTS");
        if (!pathTramGoc.exists()) SPC.TaoThuMuc(pathTramGoc);
    }
    private void SuKien() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent= new Intent(Activity_DanhSach_BTS.this,Activity_DanhSach_Anten.class);
//                intent.putExtra("MaTram",MaTram);
//                intent.putExtra("TenCot",TenCot);
//                intent.putExtra("TenTramGoc",list_BTS.get(position).getTenTramGoc());
//                intent.putExtra("DiaDiem",tvViTri.getText().toString());
//                intent.putExtra("ToaDo",tvToaDo.getText().toString());
//                startActivity(intent);
                DialogMenu(position);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });
        btnThemBTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemBTS(Gravity.CENTER);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       /* btnThietKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DialogThietKeCot("Thiết kế cột","Lưu thiết kế");
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });
    }
    private void AnhXa() {
        listview = findViewById(R.id.listview_bts);
        btnThemBTS = findViewById(R.id.btnThemBTS);
        btnBack = findViewById(R.id.btnBack);
        //btnThietKe = findViewById(R.id.btnThietKe);
        title = findViewById(R.id.title);
        tvToaDo = findViewById(R.id.tvToaDo);
        tvViTri = findViewById(R.id.tvViTri);
        btnMenu = findViewById(R.id.btnMenu);
    }
    //region Dialog
    private void DialogThemBTS(int gravity){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_BTS.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_bts);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = gravity;
        window.setAttributes(windowArr);
        dialogthongso.show();
        ImageButton tvChonBangTanHoatDong = dialogthongso.findViewById(R.id.tvChonBangTanHoatDong);
        ImageButton btnChonChungLoai = dialogthongso.findViewById(R.id.btnChonChungLoai);
        AutoCompleteTextView edtTenTramGoc = dialogthongso.findViewById(R.id.edtTenTramGoc);
        AutoCompleteTextView edtChungLoaiThietBi = dialogthongso.findViewById(R.id.edtTenCongTrinh);
        SPC.setPopUp(this,edtChungLoaiThietBi,SPC.LayDanhSachThietBi(),btnChonChungLoai);
        AutoCompleteTextView edtBangTanHoatDong = dialogthongso.findViewById(R.id.edtBangTanHoatDong);
        AutoCompleteTextView edtMangSuDung = dialogthongso.findViewById(R.id.edtMangSuDung);
        SPC.setPopUp(this,edtBangTanHoatDong,SPC.listBangTan,tvChonBangTanHoatDong);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuuThongSo);
        edtBangTanHoatDong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edtBangTanHoatDong.getText().toString().trim().equals("")){
                    edtMangSuDung.setText(SPC.layMangSuDung(edtChungLoaiThietBi.getText().toString(),edtBangTanHoatDong.getText().toString()));
                    edtTenTramGoc.setText("BTS " + edtMangSuDung.getText().toString() + " " + edtBangTanHoatDong.getText().toString());
                }
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenTramGoc,edtChungLoaiThietBi,edtBangTanHoatDong,edtMangSuDung));
                for( AutoCompleteTextView edt:listAutoCompleteTextView){
                    if(edt.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(Activity_DanhSach_BTS.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        //File pathDuLieu = new File(pathTramGoc, edtTenTramGoc.getText().toString());
                        //SPC.TaoThuMuc(pathDuLieu);
                        if(pathTramGoc.exists())
                        {
                            try
                            {
                                SPC.SaveListAutoCompleteTextView_json(edtTenTramGoc.getText().toString(),pathTramGoc,listAutoCompleteTextView,SPC.ThietKeBTS);
                                File pathHinhAnh= new File(SPC.pathDataApp_PNDT,MaTram+ SPC.DuongDanThuMucHinhAnh);
                                SPC.TaoThuMuc(new File( pathHinhAnh,"Thiết bị " + edtTenTramGoc.getText().toString().replace("BTS ","")));
                                SPC.TaoThuMuc(new File( pathHinhAnh,"Hình ảnh anten " + edtTenTramGoc.getText().toString().replace("BTS ","")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SettupListView();
                            Toast.makeText(Activity_DanhSach_BTS.this, "Đã tạo tram " + pathTramGoc.getName(), Toast.LENGTH_SHORT).show();
                            dialogthongso.dismiss();
                        }
                    }

                }
            }
        });
    };
    private void DialogMenu(int vt){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_BTS.this,R.style.PauseDialog);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_DanhSach_BTS.this);
                builder.setTitle("Bạn muốn xóa trạm này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File file = new File(pathTramGoc,list_BTS.get(vt).getTenTramGoc());

                            //FileUtils.deleteDirectory(file);
                            file.delete();
                            Toast.makeText(getApplicationContext(),"Đã xóa trạm gốc!", Toast.LENGTH_SHORT).show();
                            SettupListView();

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
            public void onClick(View v) {
                DialogChinhSua(vt);
                dialogthongso.dismiss();
            }
        });
    }
    private void DialogChinhSua(int vt){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_BTS.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_bts);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();

        ImageButton tvChonBangTanHoatDong = dialogthongso.findViewById(R.id.tvChonBangTanHoatDong);
        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle); tvTitle.setText("Chỉnh sửa");
        ImageButton btnChonChungLoai = dialogthongso.findViewById(R.id.btnChonChungLoai);
        AutoCompleteTextView edtTenTramGoc = dialogthongso.findViewById(R.id.edtTenTramGoc);edtTenTramGoc.setText(list_BTS.get(vt).getTenTramGoc());
        AutoCompleteTextView edtChungLoaiThietBi = dialogthongso.findViewById(R.id.edtTenCongTrinh);edtChungLoaiThietBi.setText(list_BTS.get(vt).getChungLoaiThietBi());
        SPC.setPopUp(this,edtChungLoaiThietBi,SPC.LayDanhSachThietBi(),btnChonChungLoai);
        AutoCompleteTextView edtBangTanHoatDong = dialogthongso.findViewById(R.id.edtBangTanHoatDong);edtBangTanHoatDong.setText(list_BTS.get(vt).getBangTanHoatDong());
        AutoCompleteTextView edtMangSuDung = dialogthongso.findViewById(R.id.edtMangSuDung);edtBangTanHoatDong.setText(list_BTS.get(vt).getMangSuDung());
        SPC.setPopUp(this,edtBangTanHoatDong,SPC.listBangTan,tvChonBangTanHoatDong);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuuThongSo);btnLuu.setText("Lưu thông số");
        edtBangTanHoatDong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edtBangTanHoatDong.getText().toString().trim().equals("")){
                    edtMangSuDung.setText(SPC.layMangSuDung(edtChungLoaiThietBi.getText().toString(),edtBangTanHoatDong.getText().toString()));
                    edtTenTramGoc.setText("BTS " + edtMangSuDung.getText().toString() + " " + edtBangTanHoatDong.getText().toString());
                }
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenTramGoc,edtChungLoaiThietBi,edtBangTanHoatDong,edtMangSuDung));
                for( AutoCompleteTextView edt:listAutoCompleteTextView){
                    if(edt.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(Activity_DanhSach_BTS.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        File pathDuLieu = new File(pathTramGoc, list_BTS.get(vt).getTenTramGoc());
                        try
                        {
                            SPC.SaveListAutoCompleteTextView_json("ThietKeBTS",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeBTS);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SettupListView();
                        Toast.makeText(Activity_DanhSach_BTS.this, "Đã tạo tram " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();
                        dialogthongso.dismiss();
                    }

                }
            }
        });
    }
    private void showMenu() {
        Intent intent= new Intent(Activity_DanhSach_BTS.this,ActivityMenu.class);
        intent.putExtra("MaTram",MaTram);
        intent.putExtra("DiaDiem",tvViTri.getText().toString());
        intent.putExtra("ToaDo",tvToaDo.getText().toString());
        startActivity(intent);
        overridePendingTransition(R.anim.zoom, R.anim.zoomin);
    }
    //endregion
}
