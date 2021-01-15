package com.example.chirag.googlesignin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Activity_DanhSach_Cot extends AppCompatActivity
{
    ListView listview;
    TextView title,tvToaDo,tvViTri,tvMaTram,tvViTriDat,tvNgaySua,tvSoAnh;

    ImageButton btnBack,btnMenu,btnThemCot;
    LinearLayout btnAnhChup,btnThietKe,btnNhaDatTram;
    List<DoiTuong_Cot> list_Cot = new ArrayList<>();
    Adapter_DoiTuong_Cot adapter_doiTuongCot;
    File pathDanhSachCot ;
    String MaTram,DiaDiem,ToaDo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_cot);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        NhanBien();
        pathDanhSachCot = new File(SPC.pathDataApp_PNDT,MaTram);
        pathDanhSachCot = new File(pathDanhSachCot,"DuLieu");
        SuKien();
        SettupListView();
    }
    private void AnhXa() {
        listview = findViewById(R.id.listview_cot);
        btnBack = findViewById(R.id.btnBack);
        btnAnhChup = findViewById(R.id.btnAnhChup);
        btnThietKe = findViewById(R.id.btnThietKe);
        btnMenu = findViewById(R.id.btnMenu);
        btnThemCot = findViewById(R.id.btnThemCot);
        btnNhaDatTram = findViewById(R.id.btnNhaDatTram);
        title = findViewById(R.id.title);
        tvToaDo = findViewById(R.id.tvToaDo);
        tvViTri = findViewById(R.id.tvViTri);
        tvMaTram = findViewById(R.id.tvMaTram);
        tvViTriDat = findViewById(R.id.tvViTriDat);
        tvNgaySua = findViewById(R.id.tvNgaySua);
        tvSoAnh = findViewById(R.id.tvSoAnh);
    }
    //region Settup ListView
    private void SettupListView() {
        /*list_Cot.clear();
        list_Cot.add(new DoiTuong_Cot("14","Cột A","3","14.9","12.3"));
        adapter_doiTuongCot = new Adapter_DoiTuong_Cot(list_Cot, Activity_DanhSach_Cot.this,R.layout.item_cot);
        listview.setAdapter(adapter_doiTuongCot);*/
        list_Cot = new ArrayList<>();
        File[] files=pathDanhSachCot.listFiles();
        try{
            for (File file:files)
            {
                if(file.isDirectory())
                {
                    File fileThietKe = new File(pathDanhSachCot,file.getName().trim()+ "/ThietKeCot.txt");
                    if (fileThietKe.exists())
                    {
                        String thietke = SPC.readText(fileThietKe);
                        JSONObject jsonObject = new JSONObject(thietke);
                        if (!thietke.equals(""))
                        {
//                            String[] listThietke = thietke.split("&");
//                            String TenCot = listThietke[SPC.TimViTri("TenCot",SPC.ThietKeCot)];
//                            String ChieuCaoCot = listThietke[SPC.TimViTri("ChieuCaoCot",SPC.ThietKeCot)];
//                            String SoChan = listThietke[SPC.TimViTri("SoChan",SPC.ThietKeCot)];
//                            String ViTriX = listThietke[SPC.TimViTri("ViTriX",SPC.ThietKeCot)];
//                            String ViTriY = listThietke[SPC.TimViTri("ViTriY",SPC.ThietKeCot)];
                            String TenCot = jsonObject.getString("TenCot");
                            String ChieuCaoCot = jsonObject.getString("ChieuCaoCot");
                            String SoChan = jsonObject.getString("SoChan");
                            String ViTriX = jsonObject.getString("ViTriX");
                            String ViTriY = jsonObject.getString("ViTriY");

                            list_Cot.add(new DoiTuong_Cot(TenCot,ChieuCaoCot,SoChan,ViTriX,ViTriY));
                        }
                    }
                }
            }
            adapter_doiTuongCot = new Adapter_DoiTuong_Cot(list_Cot, Activity_DanhSach_Cot.this,R.layout.item_cot);
            listview.setAdapter(adapter_doiTuongCot);
            setupThietKeTram();
            setupHinhAnhTongThe();
        }
        catch (Exception e){}
    }
    private void NhanBien() {
        Intent intent = getIntent();
        MaTram =intent.getStringExtra("MaTram");
        title.setText(MaTram);
        DiaDiem=intent.getStringExtra("DiaDiem");
        tvViTri.setText(DiaDiem);
        ToaDo = intent.getStringExtra("ToaDo");
        tvToaDo.setText(ToaDo);
        for(String img:SPC.TenHinhAnhTongThe)
        {
            File imgPath = new File(SPC.pathDataApp_PNDT,MaTram+ SPC.DuongDanThuMucHinhAnh);
             imgPath = new File(imgPath,img);
            SPC.TaoThuMuc(imgPath);
        }
    }
    public void setupThietKeTram() throws JSONException {
        File fileThietKe = new File(SPC.pathDataApp_PNDT,MaTram+ SPC.DuongDanFileThietKeTram);
        if (fileThietKe.exists())
        {
            String thietke = SPC.readText(fileThietKe);
            JSONObject jsonObject = new JSONObject(thietke);
            if (!thietke.equals(""))
            {
//                String[] listThietke = thietke.split("&");
//                String MaTram = listThietke[SPC.TimViTri("MaTram",SPC.ThietKeTram)];
//                String ViTriDat = listThietke[SPC.TimViTri("ViTriDat",SPC.ThietKeTram)];
                String MaTram = jsonObject.getString("MaTram");
                String ViTriDat= jsonObject.getString("ViTriDat");
                tvMaTram.setText(MaTram);
                tvViTriDat.setText(ViTriDat);
            }
        }
    }
    public void setupHinhAnhTongThe(){
        //
        File ThuMucHinhAnh = new File(SPC.pathDataApp_PNDT,MaTram+ SPC.DuongDanThuMucHinhAnh);
        String NgaySua = SPC.getLastModified(ThuMucHinhAnh);
        if (ThuMucHinhAnh.exists())
        {
            File[] listHinhAnh = ThuMucHinhAnh.listFiles();
            int soAnh =0 ;
            for(File file:listHinhAnh)
            {
                File[] listAnh = file.listFiles();
                int cou = listAnh.length-1;
                if(cou>=0) soAnh+=1;
            }
            tvSoAnh.setText(String.valueOf(soAnh));
            tvNgaySua.setText(NgaySua);
        }
    }
    //endregion
    //region Sự kiện
    private void SuKien() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(Activity_DanhSach_Cot.this,Activity_DanhSach_BTS.class);
                intent.putExtra("MaTram",MaTram);
                intent.putExtra("TenCot",list_Cot.get(position).getTenCot());
                intent.putExtra("DiaDiem",tvViTri.getText().toString());
                intent.putExtra("ToaDo",tvToaDo.getText().toString());
                startActivity(intent);
            }
        });
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
        btnAnhChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent= new Intent(Activity_DanhSach_Cot.this,Activity_DanhSach_AnhChup.class);
                intent.putExtra("MaTram",MaTram);
                intent.putExtra("DiaDiem",tvViTri.getText().toString());
                intent.putExtra("ToaDo",tvToaDo.getText().toString());
                startActivity(intent);
            }
        });
        btnNhaDatTram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DialogThietKeNhaTram();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        btnThietKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    try {
                        DialogThietKe(Gravity.CENTER,"Thiết kế","Lưu thiết kế");
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

            }
        });
        btnThemCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogThemCot(Gravity.CENTER,"Thêm cột anten");
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });
    }
    //endregion
    //region Dialog
    private void DialogMenu(int vt){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this,R.style.PauseDialog);
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
        Button btnSua = (Button) dialogthongso.findViewById(R.id.btnmenudoiten);
        Button btnLoad = (Button) dialogthongso.findViewById(R.id.btnupload);btnLoad.setVisibility(View.GONE);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_DanhSach_Cot.this);
                builder.setTitle("Bạn muốn xóa trạm này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File file = new File(pathDanhSachCot,list_Cot.get(vt).getTenCot());
                        try {
                            FileUtils.deleteDirectory(file);
                            Toast.makeText(getApplicationContext(),"Đã xóa cột!", Toast.LENGTH_SHORT).show();
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
            public void onClick(View v) {
                DialogDoiten(vt);
                dialogthongso.dismiss();

            }
        });
    }
    private void DialogDoiten(int vt){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this,R.style.PauseDialog);
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
        AutoCompleteTextView edtMaTram = dialogthongso.findViewById(R.id.edtMaTram);
        edtMaTram.setText(list_Cot.get(vt).getTenCot());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtMaTram.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(Activity_DanhSach_Cot.this);
                    builder.setTitle("Bạn muốn đổi tên trạm này không?");
                    // add the buttons
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            File fileOld = new File(pathDanhSachCot,list_Cot.get(vt).getTenCot());
                            File fileNew = new File(pathDanhSachCot,edtMaTram.getText().toString());

                            if(!fileNew.exists()){
                                boolean result= fileOld.renameTo(fileNew);
                                if (result) Toast.makeText(Activity_DanhSach_Cot.this, "Đã đổi tên!", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(Activity_DanhSach_Cot.this, "Đã có trạm này!", Toast.LENGTH_SHORT).show();
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
    private void DialogThietKe(int gravity,String title2,String titleButton) throws JSONException {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_themtram);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = gravity;
        window.setAttributes(windowArr);
        dialogthongso.show();
        /**
         * Ánh xạ
         */
        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle);
        tvTitle.setText(title2);
        Button btnLuuThongSo = dialogthongso.findViewById(R.id.btnLuuThongSo);
        btnLuuThongSo.setText(titleButton);
        AutoCompleteTextView edtMaTram = dialogthongso.findViewById(R.id.edtMaTram);edtMaTram.setText(title.getText());
        AutoCompleteTextView edtDiaDiem = dialogthongso.findViewById(R.id.edtDiaDiem);
        AutoCompleteTextView edtToaDo = dialogthongso.findViewById(R.id.edtToaDo);
        AutoCompleteTextView edtViTriDat = dialogthongso.findViewById(R.id.edtViTriDat);
        AutoCompleteTextView edtNgayDo = dialogthongso.findViewById(R.id.edtNgayDo);
        RadioButton checkbox_trenmai = dialogthongso.findViewById(R.id.checkbox_trenmai);
        RadioButton checkbox_duoidat = dialogthongso.findViewById(R.id.checkbox_duoidat);
        RadioGroup radioGroup = (RadioGroup) dialogthongso.findViewById(R.id.radioGroup);
        ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtMaTram,edtDiaDiem,edtToaDo,edtNgayDo,edtViTriDat));
        /**
         * Nhận dữ liệu
         */
        File pathTramMoi = new File(SPC.pathDataApp_PNDT, edtMaTram.getText().toString());
        File pathDuLieu = new File(pathTramMoi, "DuLieu");
        //SPC.ReadListAutoCompleteTextView("ThietKeTram.txt",pathDuLieu,listAutoCompleteTextView);
        SPC.ReadListAutoCompleteTextView_Json("ThietKeTram.txt",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeTram);
        if(edtViTriDat.equals("Dưới đất")) checkbox_duoidat.setChecked(true);
        else checkbox_trenmai.setChecked(true);

        /**
         * Sự kiện
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkbox_trenmai.isChecked())  edtViTriDat.setText("Trên mái");
                else edtViTriDat.setText("Dưới đất");
            }
        });
        btnLuuThongSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for( AutoCompleteTextView edt:listAutoCompleteTextView){
                    if(edt.getText().toString().trim().equals("")){
                        Toast.makeText(Activity_DanhSach_Cot.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        File pathTramMoi = new File(SPC.pathDataApp_PNDT, edtMaTram.getText().toString());
                        File pathHinhAnh = new File(pathTramMoi, "HinhAnh");
                        File pathDuLieu = new File(pathTramMoi, "DuLieu");
                        SPC.TaoThuMuc(pathTramMoi);
                        SPC.TaoThuMuc(pathDuLieu);
                        SPC.TaoThuMuc(pathHinhAnh);
                        if(pathDuLieu.exists())
                        {
                            try {
                                SPC.SaveListAutoCompleteTextView_json("ThietKeTram",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeTram);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(Activity_DanhSach_Cot.this, "Đã tạo trạm " + pathTramMoi.getName(), Toast.LENGTH_SHORT).show();
                            dialogthongso.dismiss();
                        }
                    }

                }
            }
        });

    };
    private void DialogThietKeNhaTram() throws  JSONException {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_nha_dat_tram);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();
        /**
         * Ánh xạ
         */
        AutoCompleteTextView edtTenCongTrinh = dialogthongso.findViewById(R.id.edtTenCongTrinh);
        AutoCompleteTextView edtSoTang = dialogthongso.findViewById(R.id.edtSoTang);
        AutoCompleteTextView edtChieuCaoCongTrinh = dialogthongso.findViewById(R.id.edtChieuCaoCongTrinh);
        AutoCompleteTextView edtDoDay = dialogthongso.findViewById(R.id.edtDoDay);
        AutoCompleteTextView edtDoRong = dialogthongso.findViewById(R.id.edtDoRong);

        ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenCongTrinh,edtSoTang,edtChieuCaoCongTrinh,edtDoDay,edtDoRong));
        File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
        File pathDuLieu = new File(pathTramMoi, "DuLieu");
        SPC.ReadListAutoCompleteTextView_Json("ThietKeNhaTram.txt",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeNhaDatTram);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for( AutoCompleteTextView edt:listAutoCompleteTextView){
                    if(edt.getText().toString().trim().equals("")){
                        Toast.makeText(Activity_DanhSach_Cot.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
                        File pathDuLieu = new File(pathTramMoi, "DuLieu");
                        if(pathDuLieu.exists())
                        {
                            try {
                                SPC.SaveListAutoCompleteTextView_json("ThietKeNhaTram",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeNhaDatTram);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dialogthongso.dismiss();
                        }
                    }

                }
            }
        });

    };
    private void DialogThemCot(int gravity,String title){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_themcot);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = gravity;
        window.setAttributes(windowArr);
        dialogthongso.show();
        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        AutoCompleteTextView edtTenCot = dialogthongso.findViewById(R.id.edtTenThanhPhan);
        AutoCompleteTextView edtChieucaoCot = dialogthongso.findViewById(R.id.edtChieuCaoCongTrinh);
        AutoCompleteTextView edtSoChan = dialogthongso.findViewById(R.id.edtSoChan);
        AutoCompleteTextView edtKichThuocThanCot = dialogthongso.findViewById(R.id.edtKichThuocThanCot);
        AutoCompleteTextView edtChieuX = dialogthongso.findViewById(R.id.edtChieuX);
        AutoCompleteTextView edtChieuY = dialogthongso.findViewById(R.id.edtChieuY);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);
        RadioButton checkbox_trenmai = dialogthongso.findViewById(R.id.checkbox_trenmai);
        RadioButton checkbox_duoidat = dialogthongso.findViewById(R.id.checkbox_duoidat);
        RadioGroup radioGroup = (RadioGroup) dialogthongso.findViewById(R.id.radioGroup);
        /**
         * Sự kiện
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if(checkbox_trenmai.isChecked())  edtSoChan.setText("3");
                else edtSoChan.setText("4");
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenCot,edtChieucaoCot,edtSoChan,edtKichThuocThanCot,edtChieuX,edtChieuY));
                for( AutoCompleteTextView edt:listAutoCompleteTextView){
                    if(edt.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(Activity_DanhSach_Cot.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        File pathDuLieu = new File(pathDanhSachCot, edtTenCot.getText().toString());
                        SPC.TaoThuMuc(pathDuLieu);
                        if(pathDuLieu.exists())
                        {
                            try {
                                SPC.SaveListAutoCompleteTextView_json("ThietKeCot",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeCot);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SettupListView();
                            Toast.makeText(Activity_DanhSach_Cot.this, "Đã tạo cột " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();
                            dialogthongso.dismiss();
                        }
                    }

                }
            }
        });
    };

    private void showMenu() {
        Intent intent= new Intent(Activity_DanhSach_Cot.this,ActivityMenu.class);
        intent.putExtra("MaTram",MaTram);
        intent.putExtra("DiaDiem",tvViTri.getText().toString());
        intent.putExtra("ToaDo",tvToaDo.getText().toString());
        startActivity(intent);
        overridePendingTransition(R.anim.zoom, R.anim.zoomin);
    }
    //endregion
}
