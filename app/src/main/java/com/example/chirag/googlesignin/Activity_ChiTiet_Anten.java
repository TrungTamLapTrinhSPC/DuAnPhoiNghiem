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
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Activity_ChiTiet_Anten extends AppCompatActivity {
    LinearLayout btnDanhSachCongTrinh;
    ImageButton btnBack,btnMolayoutThietBi,btnMolayoutAnten,btnMolayoutSuyHao,btnMenu;
    Button btnLuuThietBi,btnLuuAnten,btnLuuSuyHao;
    LinearLayout layoutThietBi,layoutAnten,layoutSuyHao;
    FloatingActionButton fab;
    String MaTram,TenCot,TenTramGoc,TenAnten,DiaDiem,ToaDo,ThuTuAnten;
    TextView title,tvToaDo,tvViTri;
    File pathThietKeAnten;
    ArrayList<AutoCompleteTextView> listAutoCompleteTextView;
    ViewGroup viewgroup;
    int [] listID;
    AutoCompleteTextView[] list;
    HorizontalListView listview_thanhphan;
    List<DoiTuong_ThanhPhan> list_ThanhPhan = new ArrayList<>();
    Adapter_DoiTuong_ThanhPhan adapter_doiTuong_thanhphan;
    //AutoCompleteTextView THIẾT KẾ ANTEN
    AutoCompleteTextView edtTenAnten,edtChungLoaiThietBi,edtSoMayThu,edtCongXuatPhat1,edtCongXuatPhat2,edtChungLoaiAnten,edtLoaiAnten,edtDoTangIch,edtBangTan,edtDoDaiBucXa,edtGocNgang,edtGocPhuongVi,edtDoCao_vs_ChanCot,edtDoCao_vs_MatDat,edtChungLoaiFeeder,edtChieuDaiFeeder,edtSuyHaodBFeeder,edtSuyHaoFeeder,edtChungLoaiJumper,edtChieuDaiJumper,edtSuyHaodBJumper,edtSuyHaoJumper,edtTongSuyHao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_anten);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        SuKien();
        NhanBien();
        try {
            setUpView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            setUpListView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUpListView() throws JSONException {
        list_ThanhPhan.clear();
        File fileThanhPhan = new File (pathThietKeAnten,"ThanhPhan");
        if (fileThanhPhan.exists()){
            if (fileThanhPhan.isDirectory()){
                File[] listThanhPhan = fileThanhPhan.listFiles();
                if (listThanhPhan.length ==0){
                    listview_thanhphan.setVisibility(View.GONE);
                } else
                {
                    listview_thanhphan.setVisibility(View.VISIBLE);
                }
                for(File thanhphan:listThanhPhan)
                {
                    if(thanhphan.getName().contains(".txt"))
                    {
                        String thietkethanhphan = SPC.readText(thanhphan);
                        JSONObject jsonObject = new JSONObject(thietkethanhphan);
//                        String[] mangThanhPhan = thietkethanhphan.split("&");
//                        String TenThanhPhan = mangThanhPhan[SPC.TimViTri("TenThanhPhan",SPC.ThietKeThanhPhan)];
//                        String ChieuDai = mangThanhPhan[SPC.TimViTri("ChieuDai",SPC.ThietKeThanhPhan)];
//                        String ChungLoai = mangThanhPhan[SPC.TimViTri("ChungLoai",SPC.ThietKeThanhPhan)];
//                        String SuyHaodB = mangThanhPhan[SPC.TimViTri("SuyHaodB",SPC.ThietKeThanhPhan)];
//                        String SuyHao = mangThanhPhan[SPC.TimViTri("SuyHao",SPC.ThietKeThanhPhan)];
                        String TenThanhPhan = jsonObject.getString("TenThanhPhan");
                        String ChieuDai = jsonObject.getString("ChieuDai");
                        String ChungLoai = jsonObject.getString("ChungLoai");
                        String SuyHaodB = jsonObject.getString("SuyHaodB");
                        String SuyHao = jsonObject.getString("SuyHao");
                        list_ThanhPhan.add(new DoiTuong_ThanhPhan(TenThanhPhan,ChieuDai,ChungLoai,SuyHaodB,SuyHao));
                    }
                }
            }
            /**HIỂN THỊ RA MÀN HÌNH*/
            adapter_doiTuong_thanhphan = new Adapter_DoiTuong_ThanhPhan(list_ThanhPhan, Activity_ChiTiet_Anten.this,R.layout.item_thanhphan);
            listview_thanhphan.setAdapter(adapter_doiTuong_thanhphan);
            listview_thanhphan.setVisibility(View.GONE);

        }
        else {
            SPC.TaoThuMuc(fileThanhPhan);
            Toast.makeText(Activity_ChiTiet_Anten.this, "Đã tạo " + fileThanhPhan.getName(), Toast.LENGTH_SHORT).show();

        }

    }

    private void setUpView() throws JSONException {
        File filethietKe = new File(pathThietKeAnten,"ThietKeAnten.txt");
        if(filethietKe.exists()){
            SPC.ReadListAutoCompleteTextView_Json("ThietKeAnten.txt",pathThietKeAnten,listAutoCompleteTextView,SPC.ThietKeAnten);
        }
    }

    private void NhanBien()
    {
        Intent intent =getIntent();//Nhận biến truyền từ trang danh sách cột
        MaTram =intent.getStringExtra("MaTram");
        TenCot =intent.getStringExtra("TenCot");
        TenAnten =intent.getStringExtra("TenAnten");
        TenTramGoc =intent.getStringExtra("TenTramGoc");
        ThuTuAnten =intent.getStringExtra("ThuTuAnten");
        title.setText(MaTram+" - "+TenAnten);
        DiaDiem=intent.getStringExtra("DiaDiem");
        tvViTri.setText(DiaDiem);
        ToaDo = intent.getStringExtra("ToaDo");
        tvToaDo.setText(ToaDo);
        pathThietKeAnten = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/" + TenCot + "/" + TenTramGoc+ "/" + TenAnten);
    }

    private void SuKien() {
        btnDanhSachCongTrinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Activity_ChiTiet_Anten.this,Activity_DanhSach_CongTrinh.class);
                intent.putExtra("MaTram",MaTram);
                intent.putExtra("TenCot",TenCot);
                intent.putExtra("TenTramGoc",TenTramGoc);
                intent.putExtra("ThuTuAnten",ThuTuAnten);
                intent.putExtra("TenAnten",TenAnten);
                intent.putExtra("DiaDiem",tvViTri.getText().toString());
                intent.putExtra("ToaDo",tvToaDo.getText().toString());
                startActivity(intent);
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Activity_ChiTiet_Anten.this,ActivityMenu.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom, R.anim.zoomin);
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
                DialogThemThanhPhan();
            }
        });
        btnMolayoutThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutThietBi.getVisibility() == View.GONE){
                    layoutThietBi.setVisibility(View.VISIBLE);
                    layoutAnten.setVisibility(View.GONE);
                    layoutSuyHao.setVisibility(View.GONE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup,autoTransition);
                } else {
                    layoutThietBi.setVisibility(View.GONE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup,autoTransition);
                }
                fab.setVisibility(layoutSuyHao.getVisibility());
                btnLuuSuyHao.setVisibility(layoutSuyHao.getVisibility());

            }
        });
        btnMolayoutAnten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutAnten.getVisibility() == View.GONE){
                    layoutThietBi.setVisibility(View.GONE);
                    layoutAnten.setVisibility(View.VISIBLE);
                    layoutSuyHao.setVisibility(View.GONE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup,autoTransition);
                } else {
                    layoutAnten.setVisibility(View.GONE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup,autoTransition);
                }
                fab.setVisibility(layoutSuyHao.getVisibility());
                btnLuuSuyHao.setVisibility(layoutSuyHao.getVisibility());
            }
        });
        btnMolayoutSuyHao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutSuyHao.getVisibility() == View.GONE){
                    layoutThietBi.setVisibility(View.GONE);
                    layoutAnten.setVisibility(View.GONE);
                    layoutSuyHao.setVisibility(View.VISIBLE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup,autoTransition);
                } else {
                    layoutSuyHao.setVisibility(View.GONE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup,autoTransition);
                }
                fab.setVisibility(layoutSuyHao.getVisibility());
                btnLuuSuyHao.setVisibility(layoutSuyHao.getVisibility());

            }
        });
        listview_thanhphan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogMenu(position);
                return true;
            }
        });
        btnLuuAnten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LuuThietKeAnten();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        btnLuuThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LuuThietKeAnten();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        btnLuuSuyHao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LuuThietKeAnten();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void LuuThietKeAnten() throws JSONException {
        if (pathThietKeAnten.isDirectory())
        {
            SPC.SaveListAutoCompleteTextView_json("ThietKeAnten",pathThietKeAnten,listAutoCompleteTextView,SPC.ThietKeAnten);
            Toast.makeText(getApplicationContext(),"Đã lưu!", Toast.LENGTH_SHORT).show();
        }
    }

    private void AnhXa() {
        btnMenu = findViewById(R.id.btnMenu);
        btnBack = findViewById(R.id.btnBack);
        fab = findViewById(R.id.fab);
        title = findViewById(R.id.title);
        tvToaDo = findViewById(R.id.tvToaDo);
        tvViTri = findViewById(R.id.tvViTri);
        viewgroup = findViewById(R.id.viewgroup);
        listview_thanhphan = findViewById(R.id.listview_thanhphan);
        btnDanhSachCongTrinh = findViewById(R.id.btnDanhSachCongTrinh);
        btnMolayoutThietBi = findViewById(R.id.btnMolayoutThietBi);
        btnMolayoutAnten = findViewById(R.id.btnMolayoutAnten);
        btnMolayoutSuyHao = findViewById(R.id.btnMolayoutSuyHao);
        //BUTTON LƯU
        btnLuuThietBi = findViewById(R.id.btnLuuThietBi);
        btnLuuAnten = findViewById(R.id.btnLuuAnten);
        btnLuuSuyHao = findViewById(R.id.btnLuuSuyHao);
        //Layout
        layoutThietBi = findViewById(R.id.layoutThietBi);//layoutThietBi.setVisibility(View.GONE);
        layoutAnten = findViewById(R.id.layoutAnten);layoutAnten.setVisibility(View.GONE);
        layoutSuyHao  = findViewById(R.id.layoutSuyHao);layoutSuyHao.setVisibility(View.GONE);
        fab.setVisibility(layoutSuyHao.getVisibility());
        btnLuuSuyHao.setVisibility(layoutSuyHao.getVisibility());

        listID = new int[]{R.id.edtTenAnten, R.id.edtChungLoaiThietBi, R.id.edtChieuCaoCongTrinh, R.id.edtCongXuatPhat1, R.id.edtCongXuatPhat2, R.id.edtChungLoaiAnten, R.id.edtLoaiAnten, R.id.edtDoTangIch, R.id.edtBangTan, R.id.edtDoDaiBucXa, R.id.edtGocNgang, R.id.edtGocPhuongVi, R.id.edtDoCao_vs_ChanCot, R.id.edtDoCao_vs_MatDat, R.id.edtChungLoaiFeeder, R.id.edtChieuDaiFeeder, R.id.edtSuyHaodBFeeder, R.id.edtSuyHaoFeeder, R.id.edtChungLoaiJumper, R.id.edtChieuDaiJumper, R.id.edtSuyHaodBJumper, R.id.edtSuyHaoJumper, R.id.edtTongSuyHao};
        list = new AutoCompleteTextView[]{edtTenAnten,edtChungLoaiThietBi,edtSoMayThu,edtCongXuatPhat1,edtCongXuatPhat2,edtChungLoaiAnten,edtLoaiAnten,edtDoTangIch,edtBangTan,edtDoDaiBucXa,edtGocNgang,edtGocPhuongVi,edtDoCao_vs_ChanCot,edtDoCao_vs_MatDat,edtChungLoaiFeeder,edtChieuDaiFeeder,edtSuyHaodBFeeder,edtSuyHaoFeeder,edtChungLoaiJumper,edtChieuDaiJumper,edtSuyHaodBJumper,edtSuyHaoJumper,edtTongSuyHao};
        listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>();
        for(int i= 0;i<list.length;i++)
        {
            list[i] =  (AutoCompleteTextView) findViewById(listID[i]);
            listAutoCompleteTextView.add(list[i]);
        }
        //listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenAnten,edtChungLoaiThietBi,edtSoMayThu,edtCongXuatPhat1,edtCongXuatPhat2,edtChungLoaiAnten,edtLoaiAnten,edtDoTangIch,edtBangTan,edtDoDaiBucXa,edtGocNgang,edtGocPhuongVi,edtDoCao_vs_ChanCot,edtDoCao_vs_MatDat,edtChungLoaiFeeder,edtChieuDaiFeeder,edtSuyHaodBFeeder,edtSuyHaoFeeder,edtChungLoaiJumper,edtChieuDaiJumper,edtSuyHaodBJumper,edtSuyHaoJumper,edtTongSuyHao));
    }

    private void DialogThemThanhPhan(){
        final Dialog dialogthongso = new Dialog(Activity_ChiTiet_Anten.this,R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_thietbi);
        Window window= dialogthongso.getWindow();
        if (window==null){return;}
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();

        AutoCompleteTextView edtTenThanhPhan = dialogthongso.findViewById(R.id.edtTenThanhPhan);
        AutoCompleteTextView edtChungLoai = dialogthongso.findViewById(R.id.edtTenCongTrinh);
        AutoCompleteTextView edtChieuDai = dialogthongso.findViewById(R.id.edtChieuDai);
        AutoCompleteTextView edtSuyHaodB = dialogthongso.findViewById(R.id.edtSuyHaodB);
        AutoCompleteTextView edtSuyHao = dialogthongso.findViewById(R.id.edtSuyHao);
        TextView Title = dialogthongso.findViewById(R.id.title);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);
        Button btnOK = dialogthongso.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title.setText(edtTenThanhPhan.getText());
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenThanhPhan,edtChungLoai,edtChieuDai,edtSuyHaodB,edtSuyHao));
                for( AutoCompleteTextView edt:listAutoCompleteTextView){
                    if(edt.getText().toString().trim().equals(""))
                    {
                        Toast.makeText(Activity_ChiTiet_Anten.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        File pathDuLieu = new File(pathThietKeAnten,"ThanhPhan");
                        if(pathDuLieu.exists())
                        {
                            try {
                                SPC.SaveListAutoCompleteTextView_json(edtTenThanhPhan.getText().toString().trim(),pathDuLieu,listAutoCompleteTextView,SPC.ThietKeThanhPhan);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                setUpListView();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(Activity_ChiTiet_Anten.this, "Đã tạo thành phần " + edtTenThanhPhan.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                            dialogthongso.dismiss();
                        }
                    }
                }
            }
        });



    }

    private void DialogMenu(int vt){
        final Dialog dialogthongso = new Dialog(Activity_ChiTiet_Anten.this,R.style.PauseDialog);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ChiTiet_Anten.this);
                builder.setTitle("Bạn muốn xóa trạm này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File file = new File(pathThietKeAnten,"ThanhPhan/"+list_ThanhPhan.get(vt).getTenThanhPhan()+".txt");
                        try {
                            file.delete();
                            Toast.makeText(getApplicationContext(),"Đã xóa thư mục ảnh!", Toast.LENGTH_SHORT).show();
                            setUpListView();
                        } catch (Exception e)
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
                dialogthongso.dismiss();

            }
        });
    }

}
