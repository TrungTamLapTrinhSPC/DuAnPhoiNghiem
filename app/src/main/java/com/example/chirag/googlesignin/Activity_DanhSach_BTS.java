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
import android.widget.EditText;
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
    //THIẾT KẾ CỘT
    TextView tvViTriX,tvViTriY,tvChieuCaoCot;
    ListView listview;
    LinearLayout btnThietKe;
    List<DoiTuong_BTS> list_BTS = new ArrayList<>();
    Adapter_DoiTuong_BTS adapter_doiTuong_bts;
    String MaTram,TenCot,DiaDiem,ToaDo;
    File pathTramGoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            for (File file:files)
            {
                if(file.isDirectory())
                {
                    File fileThietKe = new File(pathTramGoc,file.getName().trim()+ "/ThietKeBTS.txt");
                    if (fileThietKe.exists())
                    {
                        String thietke = SPC.readText(fileThietKe);
                        JSONObject jsonObject = new JSONObject(thietke);
                        if (!thietke.equals(""))
                        {
//                            String[] listThietke = thietke.split("&");
//                            String TenTramGoc = listThietke[SPC.TimViTri("TenTramGoc",SPC.ThietKeBTS)];
//                            String BangTanHoatDong = listThietke[SPC.TimViTri("BangTanHoatDong",SPC.ThietKeBTS)];
//                            String ChungLoaiThietBi = listThietke[SPC.TimViTri("ChungLoaiThietBi",SPC.ThietKeBTS)];
                            String TenTramGoc = jsonObject.getString("TenTramGoc");
                            String BangTanHoatDong = jsonObject.getString("BangTanHoatDong");
                            String ChungLoaiThietBi = jsonObject.getString("ChungLoaiThietBi");

                            String SoAnten ="0"+ DemSoAnTen(file);
                            list_BTS.add(new DoiTuong_BTS(TenTramGoc,ChungLoaiThietBi,BangTanHoatDong,SoAnten));
                        }
                    }
                }
            }
            getThietKeCot();
        }
        catch (Exception e){}

        //Thay đổi thử dòng 30
        /**HIỂN THỊ RA MÀN HÌNH*/
        adapter_doiTuong_bts = new Adapter_DoiTuong_BTS(list_BTS, Activity_DanhSach_BTS.this,R.layout.item_bts);
        listview.setAdapter(adapter_doiTuong_bts);
    }
    private void getThietKeCot() throws JSONException {
        File fileThietKe = new File(pathTramGoc, "ThietKeCot.txt");
        if (fileThietKe.exists())
        {
            String thietke = SPC.readText(fileThietKe);
            JSONObject jsonObject = new JSONObject(thietke);
            if (!thietke.equals(""))
            {
//                String[] listThietke = thietke.split("&");
//                String ChieuCaoCot = listThietke[SPC.TimViTri("ChieuCaoCot",SPC.ThietKeCot)]+ " m";
//                String ViTriX = listThietke[SPC.TimViTri("ViTriX",SPC.ThietKeCot)] + " m";
//                String ViTriY = listThietke[SPC.TimViTri("ViTriY",SPC.ThietKeCot)]+ " m";
                String ChieuCaoCot = jsonObject.getString("ChieuCaoCot")+ " m";
                String ViTriX = jsonObject.getString("ViTriX")+ " m";
                String ViTriY = jsonObject.getString("ViTriY")+ " m";
                tvChieuCaoCot.setText(ChieuCaoCot);
                tvViTriX.setText(ViTriX);
                tvViTriY.setText(ViTriY);
            }
        }
    }
    private String DemSoAnTen(File fileTram){
        int sotram = 0;
        File[] listCot = fileTram.listFiles();
        sotram = listCot.length-1;
        return String.valueOf(sotram);
    }
    private void NhanBien() {
        Intent intent =getIntent();//Nhận biến truyền từ trang danh sách cột
        TenCot =intent.getStringExtra("TenCot");
        MaTram =intent.getStringExtra("MaTram");
        title.setText(MaTram+" - "+TenCot);
        DiaDiem=intent.getStringExtra("DiaDiem");
        tvViTri.setText(DiaDiem);
        ToaDo = intent.getStringExtra("ToaDo");
        tvToaDo.setText(ToaDo);
        pathTramGoc = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/" + TenCot);

    }
    private void SuKien() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(Activity_DanhSach_BTS.this,Activity_DanhSach_Anten.class);
                intent.putExtra("MaTram",MaTram);
                intent.putExtra("TenCot",TenCot);
                intent.putExtra("TenTramGoc",list_BTS.get(position).getTenTramGoc());
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
        btnThietKe.setOnClickListener(new View.OnClickListener() {
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
        });
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
        btnThietKe = findViewById(R.id.btnThietKe);
        title = findViewById(R.id.title);
        tvToaDo = findViewById(R.id.tvToaDo);
        tvViTri = findViewById(R.id.tvViTri);
        tvViTriX = findViewById(R.id.tvViTriX);
        tvViTriY = findViewById(R.id.tvViTriY);
        tvChieuCaoCot = findViewById(R.id.tvChieuCaoCot);
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

        EditText edtTenTramGoc = dialogthongso.findViewById(R.id.edtTenTramGoc);
        EditText edtChungLoaiThietBi = dialogthongso.findViewById(R.id.edtTenCongTrinh);
        EditText edtBangTanHoatDong = dialogthongso.findViewById(R.id.edtBangTanHoatDong);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuuThongSo);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<EditText> listEditText = new ArrayList<EditText>(Arrays.asList(edtTenTramGoc,edtChungLoaiThietBi,edtBangTanHoatDong));
                for( EditText edt:listEditText){
                    if(edt.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(Activity_DanhSach_BTS.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        File pathDuLieu = new File(pathTramGoc, edtTenTramGoc.getText().toString());
                        SPC.TaoThuMuc(pathDuLieu);
                        if(pathDuLieu.exists())
                        {
                            try {
                                SPC.SaveListEditText_json("ThietKeBTS",pathDuLieu,listEditText,SPC.ThietKeBTS);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SettupListView();
                            Toast.makeText(Activity_DanhSach_BTS.this, "Đã tạo tram " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();
                            dialogthongso.dismiss();
                        }
                    }

                }
            }
        });



    };
    private void DialogThietKeCot(String title2,String title3) throws ParseException, JSONException {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_BTS.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_themcot);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();
        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle);
        tvTitle.setText(title2);

        EditText edtTenCot = dialogthongso.findViewById(R.id.edtTenThanhPhan);
        EditText edtChieucaoCot = dialogthongso.findViewById(R.id.edtChieuCaoCongTrinh);
        EditText edtSoChan = dialogthongso.findViewById(R.id.edtSoChan);
        EditText edtKichThuocThanCot = dialogthongso.findViewById(R.id.edtKichThuocThanCot);
        EditText edtChieuX = dialogthongso.findViewById(R.id.edtChieuX);
        EditText edtChieuY = dialogthongso.findViewById(R.id.edtChieuY);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);
        btnLuu.setText(title3);
        RadioButton checkbox_trenmai = dialogthongso.findViewById(R.id.checkbox_trenmai);
        RadioButton checkbox_duoidat = dialogthongso.findViewById(R.id.checkbox_duoidat);
        RadioGroup radioGroup = (RadioGroup) dialogthongso.findViewById(R.id.radioGroup);
        /**
         * NHẬN BIẾN
         */
        ArrayList<EditText> listEditText = new ArrayList<EditText>(Arrays.asList(edtTenCot,edtChieucaoCot,edtSoChan,edtKichThuocThanCot,edtChieuX,edtChieuY));
        SPC.ReadListEditText_Json("ThietKeCot.txt",pathTramGoc,listEditText,SPC.ThietKeCot);
        if(edtSoChan.equals("3")) checkbox_duoidat.setChecked(true);
        else checkbox_trenmai.setChecked(true);
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
                for( EditText edt:listEditText){
                    if(edt.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(Activity_DanhSach_BTS.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        File pathDuLieu = pathTramGoc;
                        if(pathDuLieu.exists())
                        {
                            try {
                                SPC.SaveListEditText_json("ThietKeCot",pathDuLieu,listEditText,SPC.ThietKeCot);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SettupListView();
                            Toast.makeText(Activity_DanhSach_BTS.this, "Đã Lưu thiết kế " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();
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
        Button btnSua = (Button) dialogthongso.findViewById(R.id.btnmenudoiten);
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
                        try {
                            FileUtils.deleteDirectory(file);
                            Toast.makeText(getApplicationContext(),"Đã xóa trạm gốc!", Toast.LENGTH_SHORT).show();
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
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_BTS.this,R.style.PauseDialog);
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
        edtMaTram.setText(list_BTS.get(vt).getTenTramGoc());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtMaTram.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(Activity_DanhSach_BTS.this);
                    builder.setTitle("Bạn muốn đổi tên trạm này không?");
                    // add the buttons
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            File fileOld = new File(pathTramGoc,list_BTS.get(vt).getTenTramGoc());
                            File fileNew = new File(pathTramGoc,edtMaTram.getText().toString());

                            if(!fileNew.exists()){
                                boolean result= fileOld.renameTo(fileNew);
                                if (result) Toast.makeText(Activity_DanhSach_BTS.this, "Đã đổi tên!", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(Activity_DanhSach_BTS.this, "Đã có trạm này!", Toast.LENGTH_SHORT).show();
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

    private void showMenu() {
        Intent intent= new Intent(Activity_DanhSach_BTS.this,ActivityMenu.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom, R.anim.zoomin);
    }
    //endregion
}
