package com.example.chirag.googlesignin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Activity_DanhSach_Anten extends AppCompatActivity {
    ListView listview;
    ImageButton btnBack,btnThemAnten,btnMenu;
    TextView title,tvToaDo,tvViTri;

    List<DoiTuong_Anten> list_Anten = new ArrayList<>();
    Adapter_DoiTuong_Anten adapter_doiTuongAnten;
    String MaTram,TenCot,TenTramGoc,DiaDiem,ToaDo;

    File pathDanhSachAnten,pathHinhAnh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_anten);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        NhanBien();
        SuKien();
        try {
            SettupListView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SettupListView() throws JSONException {
        list_Anten.clear();
        list_Anten = new ArrayList<>();
        File[] files=pathDanhSachAnten.listFiles();
//        try{
            for (File file:files)
            {
                if(file.isDirectory())
                {
                    File fileThietKe = new File(pathDanhSachAnten,file.getName().trim()+ "/ThietKeAnTen.txt");

                    if (fileThietKe.exists())
                    {
                        String thietke = SPC.readText(fileThietKe);
                        JSONObject jsonObject = new JSONObject(thietke);

                        if (!thietke.equals(""))
                        {
//                            String[] listThietke = thietke.split("&");
//                            String TenAnten = listThietke[SPC.TimViTri("TenAnten",SPC.ThietKeAnten)];
//                            String ChungLoaiAnten = listThietke[SPC.TimViTri("ChungLoaiAnten",SPC.ThietKeAnten)];
//                            String GocPhuongVi = listThietke[SPC.TimViTri("GocPhuongVi",SPC.ThietKeAnten)];
//                            String CaoDoAnten1 = listThietke[SPC.TimViTri("DoCaoAnten1",SPC.ThietKeAnten)] + "m";
                            String TenAnten = jsonObject.getString("TenAnten") ;
                            String ChungLoaiAnten = jsonObject.getString("ChungLoaiAnten") ;
                            String GocPhuongVi = jsonObject.getString("GocPhuongVi") ;
                            String CaoDoAnten1 = jsonObject.get("DoCaoAnten1") + "m";
                            String NgayThem = SPC.getLastModified(fileThietKe);
                            list_Anten.add(new DoiTuong_Anten(TenAnten,ChungLoaiAnten,NgayThem,GocPhuongVi,CaoDoAnten1));
                        }
                    }
                }
            }
//        }
//        catch (Exception e){}

        /**HIỂN THỊ RA MÀN HÌNH*/
        adapter_doiTuongAnten = new Adapter_DoiTuong_Anten(list_Anten, Activity_DanhSach_Anten.this,R.layout.item_anten);
        listview.setAdapter(adapter_doiTuongAnten);
    }

    private void NhanBien() {
        Intent intent =getIntent();//Nhận biến truyền từ trang danh sách cột
        MaTram =intent.getStringExtra("MaTram");
        TenCot =intent.getStringExtra("TenCot");
        TenTramGoc =intent.getStringExtra("TenTramGoc");
        title.setText(MaTram+" - "+TenTramGoc);
        DiaDiem=intent.getStringExtra("DiaDiem");
        tvViTri.setText(DiaDiem);
        ToaDo = intent.getStringExtra("ToaDo");
        tvToaDo.setText(ToaDo);
        pathDanhSachAnten = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/" + TenCot + "/" + TenTramGoc);
    }
    //region Sự kiện
    private void SuKien()
    {
        listview.setOnItemClickListener(onItemClickListener);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogMenu(position);
                return true;
            }
        });
        listview.setOnItemClickListener(onItemClickListener);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnThemAnten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemAnten(Gravity.CENTER);
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Activity_DanhSach_Anten.this,ActivityMenu.class);
                intent.putExtra("MaTram",MaTram);
                intent.putExtra("DiaDiem",tvViTri.getText().toString());
                intent.putExtra("ToaDo",tvToaDo.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.zoom, R.anim.zoomin);
            }
        });
    }
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent= new Intent(Activity_DanhSach_Anten.this,Activity_ChiTiet_Anten.class);
            intent.putExtra("MaTram",MaTram);
            intent.putExtra("TenCot",TenCot);
            intent.putExtra("TenTramGoc",TenTramGoc);
            intent.putExtra("TenAnten",list_Anten.get(position).getTenAnten());
            intent.putExtra("ThuTuAnten",String.valueOf(position));
            intent.putExtra("DiaDiem",tvViTri.getText().toString());
            intent.putExtra("ToaDo",tvToaDo.getText().toString());
            startActivity(intent);
        }
    };
    //endregion
    //region Dialog
    private void DialogThemAnten(int gravity){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Anten.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_anten);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = gravity;
        window.setAttributes(windowArr);
        dialogthongso.show();

        Button btnLuu = dialogthongso.findViewById(R.id.btnLuuThongSo);
        EditText edtTenAnten = dialogthongso.findViewById(R.id.edtTenAnten);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTenAnten.getText().toString().trim().equals(""))
                {
                    Toast.makeText(Activity_DanhSach_Anten.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    File pathDuLieu = new File(pathDanhSachAnten, edtTenAnten.getText().toString());
                    SPC.TaoThuMuc(pathDuLieu);
                    if(pathDuLieu.exists())
                    {
                        String ThietKe = null;
                        try {
                            ThietKe = SPC.TaoThietKeAnten(edtTenAnten.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SPC.saveTextFile("ThietKeAnTen",ThietKe,pathDuLieu);
                        try {
                            SettupListView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(Activity_DanhSach_Anten.this, "Đã tạo anten " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();
                        dialogthongso.dismiss();
                    }
                }
            }
        });
    };
    //endregion
    private void AnhXa() {
        listview = findViewById(R.id.listview_anten);
        btnBack = findViewById(R.id.btnBack);
        btnThemAnten = findViewById(R.id.btnThemAnten);
        title = findViewById(R.id.title);
        tvToaDo = findViewById(R.id.tvToaDo);
        tvViTri = findViewById(R.id.tvViTri);
        btnMenu = findViewById(R.id.btnMenu);
    }
    private void DialogMenu(int vt){
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Anten.this,R.style.PauseDialog);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_DanhSach_Anten.this);
                builder.setTitle("Bạn muốn xóa trạm này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File file = new File(pathDanhSachAnten,list_Anten.get(vt).getTenAnten());
                        try {
                            FileUtils.deleteDirectory(file);
                            Toast.makeText(getApplicationContext(),"Đã xóa anten!", Toast.LENGTH_SHORT).show();
                            SettupListView();
                        } catch (IOException | JSONException e)
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
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Anten.this,R.style.PauseDialog);
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
        edtMaTram.setText(list_Anten.get(vt).getTenAnten());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtMaTram.getText().toString().trim().equals(""))
                {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(Activity_DanhSach_Anten.this);
                    builder.setTitle("Bạn muốn đổi tên trạm này không?");
                    // add the buttons
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            File fileOld = new File(pathDanhSachAnten,list_Anten.get(vt).getTenAnten());
                            File fileNew = new File(pathDanhSachAnten,edtMaTram.getText().toString());

                            if(!fileNew.exists()){
                                boolean result= fileOld.renameTo(fileNew);
                                if (result) Toast.makeText(Activity_DanhSach_Anten.this, "Đã đổi tên!", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(Activity_DanhSach_Anten.this, "Đã có trạm này!", Toast.LENGTH_SHORT).show();
                            try {
                                SettupListView();
                            } catch (JSONException e) {
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

            }
        });

    }
}
