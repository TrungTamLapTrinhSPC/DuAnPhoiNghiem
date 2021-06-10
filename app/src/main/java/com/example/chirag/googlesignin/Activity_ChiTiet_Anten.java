package com.example.chirag.googlesignin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Activity_ChiTiet_Anten extends AppCompatActivity {
    LinearLayout btnDanhSachCongTrinh;
    ImageButton btnBack,btnMolayoutThietBi,btnMolayoutAnten,btnMolayoutSuyHao,btnMenu,btnChonCot,btnChonBTS,btnChonAnten,
            img_ChungLoaiThietBi,img_CongXuatPhat1,img_ChungLoaiAnten,img_ChungLoaiFeeder,img_ChungLoaiJumper,img_BangTan,img_LoaiAnten,img_Connector;
    Button btnLuuThietBi,btnLuuAnten,btnLuuSuyHao;
    LinearLayout layoutThietBi,layoutAnten,layoutSuyHao;
    FloatingActionButton fab;
    String MaTram,TenCot,TenTramGoc,TenAnten,DiaDiem,ToaDo,ThuTuAnten,BangTanHoatDong,ChungLoaiThietBi,SoMayThuPhatSong;
    TextView title,tvTenCot,tvTenBTS;
    File pathThietKeAnten,pathDanhSachCot,pathDanhSachBTS,pathDanhSachAnten;
    ArrayList<AutoCompleteTextView> listAutoCompleteTextView;
    ViewGroup viewgroup;
    EditText edtTITLDien,edtTITLCo;
    int [] listID;
    AutoCompleteTextView[] list;
    ListView listview_thanhphan;
    List<DoiTuong_ThanhPhan> list_ThanhPhan = new ArrayList<>();
    Adapter_DoiTuong_ThanhPhan adapter_doiTuong_thanhphan;
    //AutoCompleteTextView THIẾT KẾ ANTEN
    AutoCompleteTextView edtTenAnten,edtChungLoaiThietBi,edtSoMayThu,edtCongXuatPhat1,edtCongXuatPhat2,edtChungLoaiAnten,edtLoaiAnten,edtDoTangIch,edtBangTan,edtDoDaiBucXa,edtGocNgang,edtGocPhuongVi,edtDoCao_vs_ChanCot,edtDoCao_vs_MatDat,edtChungLoaiFeeder,edtChieuDaiFeeder,edtSuyHaodBFeeder,edtSuyHaoFeeder,edtChungLoaiJumper,edtChieuDaiJumper,edtSuyHaodBJumper,edtSuyHaoJumper,edtConnector,edtTongSuyHao_Connector,edtTongSuyHao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_anten);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        SuKien();
        try {
            NhanBien();
            setPopupCot();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*try {
            setUpView();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        /*try {
            listview_thanhphan.setVisibility(View.GONE);
            setUpListView();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
    private void TinhTongSuyHaodB()
    {
        try {
            double _TongThanhPhan = setUpListView();
            if(SPC.isNumeric(edtSuyHaoFeeder.getText().toString())) _TongThanhPhan += Double.valueOf(edtSuyHaoFeeder.getText().toString());
            if(SPC.isNumeric(edtSuyHaoJumper.getText().toString())) _TongThanhPhan += Double.valueOf(edtSuyHaoJumper.getText().toString());
            if(SPC.isNumeric(edtTongSuyHao_Connector.getText().toString())) _TongThanhPhan += Double.valueOf(edtTongSuyHao_Connector.getText().toString());
            edtTongSuyHao.setText(String.valueOf(SPC.round(_TongThanhPhan,4)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private double setUpListView() throws JSONException {
        list_ThanhPhan.clear();
        double _TongSuyHao = 0.0;
        File fileThanhPhan = new File (pathThietKeAnten,"ThanhPhan");
        if (fileThanhPhan.exists()){
            if (fileThanhPhan.isDirectory()){
                File[] listThanhPhan = fileThanhPhan.listFiles();
                if (listThanhPhan.length ==0){
                    listview_thanhphan.setVisibility(View.GONE);
                } else
                {
                    listview_thanhphan.setVisibility(View.VISIBLE);
                    for(File thanhphan:listThanhPhan)
                    {
                        if(thanhphan.getName().contains(".txt"))
                        {
                            String thietkethanhphan = SPC.readText(thanhphan);
                            JSONObject jsonObject = new JSONObject(thietkethanhphan);
                            String TenThanhPhan = jsonObject.getString("TenThanhPhan");
                            String SuyHao = jsonObject.getString("SuyHao");
                            if(SPC.isNumeric(SuyHao)) _TongSuyHao += Double.valueOf(SuyHao);
                            list_ThanhPhan.add(new DoiTuong_ThanhPhan(TenThanhPhan,SuyHao));
                        }
                    }
                    /**HIỂN THỊ RA MÀN HÌNH*/
                    adapter_doiTuong_thanhphan = new Adapter_DoiTuong_ThanhPhan(list_ThanhPhan, Activity_ChiTiet_Anten.this,R.layout.item_thanhphan);
                    listview_thanhphan.setAdapter(adapter_doiTuong_thanhphan);
                }
            }
        }
        else
        {
            SPC.TaoThuMuc(fileThanhPhan);
            Toast.makeText(Activity_ChiTiet_Anten.this, "Đã tạo " + fileThanhPhan.getName(), Toast.LENGTH_SHORT).show();
        }
        return _TongSuyHao;
    }
    private void setPopupCot(){
        pathDanhSachCot = new File(SPC.pathDataApp_PNDT,MaTram);
        pathDanhSachCot = new File(pathDanhSachCot,"DuLieu");
        ArrayList<String> arr_Cot = new ArrayList<>();
        if (pathDanhSachCot.exists()){
            File[] files=pathDanhSachCot.listFiles();
            for (File file:files)
            {
                if(file.isDirectory())
                {
                    if (!file.getName().equals("DanhSachBTS"))
                    arr_Cot.add(file.getName());
                }
            }
            SPC.setPopUp_img(Activity_ChiTiet_Anten.this,tvTenCot,arr_Cot,btnChonCot);
            tvTenCot.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    tvTenBTS.setText("");
                    setPopupBTS(tvTenCot.getText().toString());
                }
            });
            tvTenCot.setText(TenCot);
        }
        /**KIỂM TRA XEM ĐÃ CHỌN CỘT VÀ BTS CHƯA*/
        edtTenAnten.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable s) {
                if (tvTenBTS.getText().toString().equals("") || tvTenCot.getText().toString().equals("")){
                    Toast.makeText(Activity_ChiTiet_Anten.this,"Bạn chưa chọn thiết bị!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void setPopupBTS(String tenCot){
        pathDanhSachBTS = new File(pathDanhSachCot,tenCot);
        ArrayList<String> arr_BTS = new ArrayList<>();
        if (pathDanhSachBTS.exists()){
            File[] files=pathDanhSachBTS.listFiles();
            for (File file:files)
            {
                if(file.isDirectory()) arr_BTS.add(file.getName());
            }
            SPC.setPopUp_img(Activity_ChiTiet_Anten.this,tvTenBTS,arr_BTS,btnChonBTS);
            tvTenBTS.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    File fileTram = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/DanhSachBTS");
                    File[] files=fileTram.listFiles();
                    try{
                        for (File fileThietKe:files)
                        {
                            if(fileThietKe.getName().contains(tvTenBTS.getText().toString()))
                            {
                                if(fileThietKe.exists())
                                {
                                    String thietke = SPC.readText(fileThietKe);
                                    JSONObject jsonObject = new JSONObject(thietke);
                                    if (!thietke.equals(""))
                                    {
                                        for(AutoCompleteTextView edt : listAutoCompleteTextView)
                                        {
                                            edt.setText("");
                                        }
                                        BangTanHoatDong = jsonObject.getString("BangTanHoatDong");
                                        ChungLoaiThietBi = jsonObject.getString("ChungLoaiThietBi");
                                        SoMayThuPhatSong = jsonObject.getString("SoMayThuPhatSong");
                                        edtChungLoaiThietBi.setText(ChungLoaiThietBi);
                                        edtSoMayThu.setText(SoMayThuPhatSong);
                                        edtBangTan.setText(BangTanHoatDong);
                                        ArrayList<String> lstCongSuat=  SPC.layCongSuatPhat1(ChungLoaiThietBi,BangTanHoatDong);
                                        if(lstCongSuat.size()>0)
                                        {
                                            edtCongXuatPhat1.setText(lstCongSuat.get(0));
                                            edtCongXuatPhat2.setText(SPC.TinhCongSuatPhat2(edtCongXuatPhat1.getText().toString()));
                                            SPC.setPopUp(Activity_ChiTiet_Anten.this,edtCongXuatPhat1,lstCongSuat,img_CongXuatPhat1);
                                        }
                                    }
                                }
                            }

                        }
                    }
                    catch (Exception ignored){}
                    setPopupAnten(tvTenBTS.getText().toString());

                }
            });
        }
    }
    private void setPopupAnten(String tenBTS){
        pathDanhSachAnten = new File(pathDanhSachBTS,tenBTS);
        ArrayList<String> arr_Anten = new ArrayList<>();
        if (pathDanhSachAnten.exists()){
            File[] files=pathDanhSachAnten.listFiles();
            for (File file:files)
            {
                if(file.isDirectory()) arr_Anten.add(file.getName());
            }
            ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(Activity_ChiTiet_Anten.this, R.layout.custom_list_item, R.id.text_view_list_item, arr_Anten);
            edtTenAnten.setAdapter(adapterHT);
            edtTenAnten.setThreshold(1);
            edtTenAnten.setDropDownHeight(400);
            if (btnChonAnten != null)
            {
                btnChonAnten.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final PopupMenu popupMenu = new PopupMenu(Activity_ChiTiet_Anten.this, btnChonAnten);

                        for (String s : arr_Anten)
                        { popupMenu.getMenu().add(s); }
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(final MenuItem menuItem) {
                                edtTenAnten.setText(menuItem.getTitle());
                                pathThietKeAnten = new File(pathDanhSachAnten,edtTenAnten.getText().toString());

                                try {
                                    setUpView();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
            }

            /*SPC.setPopUp(Activity_ChiTiet_Anten.this,edtTenAnten,arr_Anten,btnChonAnten);
            edtTenAnten.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    pathThietKeAnten = new File(pathDanhSachAnten,edtTenAnten.getText().toString());
                    if (pathThietKeAnten.exists()){
                        try {
                            setUpView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    try {
                            setUpView();
                            listview_thanhphan.setVisibility(View.GONE);
                            setUpListView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });*/
        }
    }
    private void setUpView() throws JSONException {
        File filethietKe = new File(pathThietKeAnten,"ThietKeAnten.txt");
        if(filethietKe.exists())
        {
            SPC.ReadListAutoCompleteTextView_Json("ThietKeAnten.txt",pathThietKeAnten,listAutoCompleteTextView,SPC.ThietKeAnten);
            edtChungLoaiThietBi.setText(ChungLoaiThietBi);
            edtBangTan.setText(BangTanHoatDong);
            SPC.setPopUp(Activity_ChiTiet_Anten.this,edtChungLoaiThietBi,SPC.LayDanhSachThietBi(),img_ChungLoaiThietBi);
            edtChungLoaiThietBi.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    ArrayList<String> lstCongSuat=  SPC.layCongSuatPhat1(edtChungLoaiThietBi.getText().toString(),edtBangTan.getText().toString());
                    if(lstCongSuat.size()>0)
                    {
                        edtCongXuatPhat1.setText(lstCongSuat.get(0));
                        SPC.setPopUp(Activity_ChiTiet_Anten.this,edtCongXuatPhat1,lstCongSuat,img_CongXuatPhat1);
                    }
                }
            });
            edtSoMayThu.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    ArrayList<String> lstCongSuat=  SPC.layCongSuatPhat1(edtChungLoaiThietBi.getText().toString(),edtBangTan.getText().toString());
                    if(lstCongSuat.size()>0)
                    {
                        edtCongXuatPhat1.setText(lstCongSuat.get(0));
                        SPC.setPopUp(Activity_ChiTiet_Anten.this,edtCongXuatPhat1,lstCongSuat,img_CongXuatPhat1);
                    }
                }
            });
            edtCongXuatPhat1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    edtCongXuatPhat2.setText(SPC.TinhCongSuatPhat2(edtCongXuatPhat1.getText().toString()));
                }
            });
            //TINH GOC NGANG
            edtTITLCo.setText(SPC.layGocNgang(edtChungLoaiAnten.getText().toString(),edtBangTan.getText().toString()));
            if(!edtTITLCo.getText().toString().trim().equals(""))
            {
                if(SPC.isNumeric(edtTITLCo.getText().toString().trim()))
                {
                    if(!edtGocNgang.getText().toString().trim().equals(""))
                    {
                        if(SPC.isNumeric(edtGocNgang.getText().toString().trim()))
                        {
                            Double TillTong = Double.valueOf(edtGocNgang.getText().toString().trim());
                            Double TillCo = Double.valueOf(edtTITLCo.getText().toString().trim());
                            edtTITLDien.setText(String.valueOf(TillTong-TillCo));
                        }
                    }
                }
            }
            //setUpListView();
            TinhTongSuyHaodB();
        }
        else
        {
            for(AutoCompleteTextView edt : listAutoCompleteTextView)
            {
                edt.setText("");
            }
        }
    }

    private void NhanBien() throws JSONException {
        Intent intent =getIntent();//Nhận biến truyền từ trang danh sách cột
        MaTram =intent.getStringExtra("MaTram");
        TenCot =intent.getStringExtra("TenCot");
        TenAnten =intent.getStringExtra("TenAnten");
        //TenTramGoc =intent.getStringExtra("TenTramGoc");
        ThuTuAnten =intent.getStringExtra("ThuTuAnten");
        title.setText(MaTram);
        DiaDiem=intent.getStringExtra("DiaDiem");
        ToaDo = intent.getStringExtra("ToaDo");

        /*pathThietKeAnten = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/" + TenCot + "/" + TenTramGoc+ "/" + TenAnten);
        File fileThietKe = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/" + TenCot + "/" + TenTramGoc + "/ThietKeBTS.txt");
        String thietke = SPC.readText(fileThietKe);
        JSONObject jsonObject = new JSONObject(thietke);
        if (!thietke.equals(""))
        {
             BangTanHoatDong = jsonObject.getString("BangTanHoatDong");
            ChungLoaiThietBi = jsonObject.getString("ChungLoaiThietBi");

        }*/
    }

    private void SuKien() {
        btnDanhSachCongTrinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvTenBTS.getText().toString().equals("") && !tvTenCot.getText().toString().equals("") && !edtTenAnten.getText().toString().equals("")){
                    /**LẤY VỊ TRÍ ANTEN TRONG DANH SÁCH*/
                            ArrayList<String> arr_Anten = new ArrayList<>();
                            if (pathDanhSachAnten.exists())
                            {
                                File[] files = pathDanhSachAnten.listFiles();
                                for (File file : files) {
                                    if (file.isDirectory()) arr_Anten.add(file.getName());
                                }
                               int sttAnten = arr_Anten.indexOf(edtTenAnten.getText().toString());
                               if (sttAnten >=0 ) {
                                   ThuTuAnten = String.valueOf(sttAnten);
                                   /**TRUYỀN CÁC BIẾN DANG TRANG CÔNG TRÌNH*/
                                   Intent intent= new Intent(Activity_ChiTiet_Anten.this,Activity_DanhSach_CongTrinh.class);
                                   intent.putExtra("MaTram",MaTram);
                                   intent.putExtra("TenCot",tvTenCot.getText().toString());
                                   intent.putExtra("TenTramGoc",tvTenBTS.getText().toString());
                                   intent.putExtra("ThuTuAnten",ThuTuAnten);
                                   intent.putExtra("TenAnten",edtTenAnten.getText().toString());
                                   intent.putExtra("DiaDiem",DiaDiem);
                                   intent.putExtra("ToaDo",ToaDo);
                                   startActivity(intent);
                               }
                               else Toast.makeText(Activity_ChiTiet_Anten.this,"Bạn chưa có anten!",Toast.LENGTH_SHORT).show();

                            }
                }
                else Toast.makeText(Activity_ChiTiet_Anten.this,"Bạn chưa chọn thiết bị!",Toast.LENGTH_SHORT).show();
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Activity_ChiTiet_Anten.this,ActivityMenu.class);
                intent.putExtra("MaTram",MaTram);
                intent.putExtra("DiaDiem",DiaDiem);
                intent.putExtra("ToaDo",ToaDo);
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
                    if (!edtTenAnten.getText().toString().isEmpty())
                        LuuThietKeAnten();
                    else
                        Toast.makeText(getApplicationContext(),"Chưa có tên anten để lưu!", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        btnLuuThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!edtTenAnten.getText().toString().isEmpty())
                        LuuThietKeAnten();
                    else
                        Toast.makeText(getApplicationContext(),"Chưa có tên anten để lưu!", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        btnLuuSuyHao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!edtTenAnten.getText().toString().isEmpty())
                        LuuThietKeAnten();
                    else
                        Toast.makeText(getApplicationContext(),"Chưa có tên anten để lưu!", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        SPC.setPopUp(this,edtChungLoaiAnten,SPC.LayDanhSachAnten(),img_ChungLoaiAnten);
        SPC.setPopUp(this,edtBangTan,SPC.listBangTan,img_BangTan);
        SPC.setPopUp(this,edtLoaiAnten,SPC.listLoaiAnten,img_LoaiAnten);
        SPC.setPopUp(this,edtConnector,SPC.listSoDauConnecter,img_Connector);

        edtChungLoaiAnten.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                edtDoTangIch.setText(SPC.layDoTangIchAnten(edtChungLoaiAnten.getText().toString(),edtBangTan.getText().toString()));
                edtDoDaiBucXa.setText(SPC.layDoDaiBucXa(edtChungLoaiAnten.getText().toString(),edtBangTan.getText().toString()));
                edtTITLCo.setText(SPC.layGocNgang(edtChungLoaiAnten.getText().toString(),edtBangTan.getText().toString()));
                edtLoaiAnten.setText("Định hướng");
                edtConnector.setText("4");

            }
        });
        SPC.setPopUp(this,edtChungLoaiFeeder,SPC.LayDanhSachSuyHao(),img_ChungLoaiFeeder);
        SPC.setPopUp(this,edtChungLoaiJumper,SPC.LayDanhSachSuyHao(),img_ChungLoaiJumper);
        edtChungLoaiFeeder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSuyHaodBFeeder.setText(SPC.laySuyHaodB("Feeder",edtChungLoaiFeeder.getText().toString(),edtBangTan.getText().toString()));
            }
        });
        edtChieuDaiFeeder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSuyHaodBFeeder.setText(SPC.laySuyHaodB("Feeder",edtChungLoaiFeeder.getText().toString(),edtBangTan.getText().toString()));
            }
        });
        edtChungLoaiJumper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSuyHaodBJumper.setText(SPC.laySuyHaodB("Jumper",edtChungLoaiJumper.getText().toString(),edtBangTan.getText().toString()));
            }
        });
        edtChieuDaiJumper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSuyHaodBJumper.setText(SPC.laySuyHaodB("Jumper",edtChungLoaiJumper.getText().toString(),edtBangTan.getText().toString()));
            }
        });
        edtSuyHaodBFeeder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSuyHaoFeeder.setText(SPC.TinhSuyHao(edtChieuDaiFeeder.getText().toString(),edtSuyHaodBFeeder.getText().toString()));
            }
        });
        edtSuyHaodBJumper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSuyHaoJumper.setText(SPC.TinhSuyHao(edtChieuDaiJumper.getText().toString(),edtSuyHaodBJumper.getText().toString()));
            }
        });
        edtTITLDien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!edtTITLCo.getText().toString().trim().equals(""))
                {
                    if(SPC.isNumeric(edtTITLCo.getText().toString().trim()))
                    {
                        if(!edtTITLDien.getText().toString().trim().equals(""))
                        {
                            if(SPC.isNumeric(edtTITLDien.getText().toString().trim()))
                            {
                                Double TillDien = Double.valueOf(edtTITLDien.getText().toString().trim());
                                Double TillCo = Double.valueOf(edtTITLCo.getText().toString().trim());
                                edtGocNgang.setText(String.valueOf(TillCo + TillDien));
                            }
                        }
                    }
                }
            }
        });
        edtTITLCo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!edtTITLCo.getText().toString().trim().equals(""))
                {
                    if(SPC.isNumeric(edtTITLCo.getText().toString().trim()))
                    {
                        if(!edtTITLDien.getText().toString().trim().equals(""))
                        {
                            if(SPC.isNumeric(edtTITLDien.getText().toString().trim()))
                            {
                                Double TillDien = Double.valueOf(edtTITLDien.getText().toString().trim());
                                Double TillCo = Double.valueOf(edtTITLCo.getText().toString().trim());
                                edtGocNgang.setText(String.valueOf(TillCo + TillDien));
                            }
                        }
                    }
                }
            }
        });
        edtConnector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String _str_Connecter = SPC.laySuyHaodB("Connector","Connector",edtBangTan.getText().toString());
                if(!_str_Connecter.isEmpty())
                {
                    double _dou_Connecter = Double.valueOf(_str_Connecter);
                    if(SPC.isNumeric(edtConnector.getText().toString().trim()))
                    {
                        int _int_SoDau = Integer.parseInt(edtConnector.getText().toString().trim());
                        edtTongSuyHao_Connector.setText(String.valueOf(_dou_Connecter * _int_SoDau));
                    }
                }


            }
        });
        //Tinh tong suy hao
        edtSuyHaoJumper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TinhTongSuyHaodB();
            }
        });
        edtSuyHaoFeeder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TinhTongSuyHaodB();
            }
        });
        edtTongSuyHao_Connector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TinhTongSuyHaodB();
            }
        });
    }

    private void LuuThietKeAnten() throws JSONException {
        pathThietKeAnten = new File(pathDanhSachAnten,edtTenAnten.getText().toString());
        if (!pathThietKeAnten.exists()) SPC.TaoThuMuc(pathThietKeAnten);
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
        tvTenCot = findViewById(R.id.tvTenCot);
        tvTenBTS = findViewById(R.id.tvTenBTS);

        viewgroup = findViewById(R.id.viewgroup);
        edtTITLCo = findViewById(R.id.edtTITLCo);
        edtTITLDien = findViewById(R.id.edtTITLDien);

        listview_thanhphan = findViewById(R.id.listview_thanhphan);
        btnDanhSachCongTrinh = findViewById(R.id.btnDanhSachCongTrinh);
        btnMolayoutThietBi = findViewById(R.id.btnMolayoutThietBi);
        btnMolayoutAnten = findViewById(R.id.btnMolayoutAnten);
        btnMolayoutSuyHao = findViewById(R.id.btnMolayoutSuyHao);
        img_ChungLoaiThietBi = findViewById(R.id.img_ChungLoaiThietBi);
        img_CongXuatPhat1 = findViewById(R.id.img_CongXuatPhat1);
        img_ChungLoaiAnten = findViewById(R.id.img_ChungLoaiAnten);
        img_ChungLoaiFeeder = findViewById(R.id.img_ChungLoaiFeeder);
        img_ChungLoaiJumper = findViewById(R.id.img_ChungLoaiJumper);

        img_BangTan = findViewById(R.id.img_BangTan);
        img_LoaiAnten = findViewById(R.id.img_LoaiAnten);
        img_Connector = findViewById(R.id.img_Connector);

        btnChonCot = findViewById(R.id.btnChonCot);
        btnChonBTS = findViewById(R.id.btnChonBTS);
        btnChonAnten = findViewById(R.id.btnChonAnten);
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
        /*listID = new int[]{R.id.edtTenAnten, R.id.edtChungLoaiThietBi, R.id.edtChieuCaoCongTrinh, R.id.edtCongXuatPhat1, R.id.edtCongXuatPhat2, R.id.edtChungLoaiAnten, R.id.edtLoaiAnten, R.id.edtDoTangIch, R.id.edtBangTan, R.id.edtDoDaiBucXa, R.id.edtGocNgang, R.id.edtGocPhuongVi, R.id.edtDoCao_vs_ChanCot, R.id.edtDoCao_vs_MatDat, R.id.edtChungLoaiFeeder, R.id.edtChieuDaiFeeder, R.id.edtSuyHaodBFeeder, R.id.edtSuyHaoFeeder, R.id.edtChungLoaiJumper, R.id.edtChieuDaiJumper, R.id.edtSuyHaodBJumper, R.id.edtSuyHaoJumper, R.id.edtTongSuyHao};
        list = new AutoCompleteTextView[]{edtTenAnten,edtChungLoaiThietBi,edtSoMayThu,edtCongXuatPhat1,edtCongXuatPhat2,edtChungLoaiAnten,edtLoaiAnten,edtDoTangIch,edtBangTan,edtDoDaiBucXa,edtGocNgang,edtGocPhuongVi,
                edtDoCao_vs_ChanCot,edtDoCao_vs_MatDat,edtChungLoaiFeeder,edtChieuDaiFeeder,edtSuyHaodBFeeder,edtSuyHaoFeeder,edtChungLoaiJumper,edtChieuDaiJumper,edtSuyHaodBJumper,edtSuyHaoJumper,edtTongSuyHao};
        listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>();
        for(int i= 0;i<list.length;i++)
        {
            list[i] =  (AutoCompleteTextView) findViewById(listID[i]);
            listAutoCompleteTextView.add(list[i]);
        }*/
        edtTenAnten = findViewById(R.id.edtTenAnten);
        edtChungLoaiThietBi = findViewById(R.id.edtChungLoaiThietBi);
        edtSoMayThu = findViewById(R.id.edtChieuCaoCongTrinh);
        edtCongXuatPhat1 = findViewById(R.id.edtCongXuatPhat1);
        edtCongXuatPhat2 = findViewById(R.id.edtCongXuatPhat2);
        edtChungLoaiAnten = findViewById(R.id.edtChungLoaiAnten);
        edtLoaiAnten = findViewById(R.id.edtLoaiAnten);
        edtDoTangIch = findViewById(R.id.edtDoTangIch);
        edtBangTan = findViewById(R.id.edtBangTan);
        edtDoDaiBucXa = findViewById(R.id.edtDoDaiBucXa);
        edtGocNgang = findViewById(R.id.edtGocNgang);
        edtGocPhuongVi = findViewById(R.id.edtGocPhuongVi);
        edtDoCao_vs_ChanCot = findViewById(R.id.edtDoCao_vs_ChanCot);
        edtDoCao_vs_MatDat = findViewById(R.id.edtDoCao_vs_MatDat);
        edtChungLoaiFeeder = findViewById(R.id.edtChungLoaiFeeder);
        edtChieuDaiFeeder = findViewById(R.id.edtChieuDaiFeeder);
        edtSuyHaodBFeeder = findViewById(R.id.edtSuyHaodBFeeder);
        edtSuyHaoFeeder = findViewById(R.id.edtSuyHaoFeeder);
        edtChungLoaiJumper = findViewById(R.id.edtChungLoaiJumper);
        edtChieuDaiJumper = findViewById(R.id.edtChieuDaiJumper);
        edtSuyHaodBJumper = findViewById(R.id.edtSuyHaodBJumper);
        edtSuyHaoJumper = findViewById(R.id.edtSuyHaoJumper);
        edtConnector = findViewById(R.id.edtConnector);
        edtTongSuyHao_Connector = findViewById(R.id.edtTongSuyHao_Connector);
        edtTongSuyHao = findViewById(R.id.edtTongSuyHao);
        listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenAnten,edtChungLoaiThietBi,edtSoMayThu,
                edtCongXuatPhat1,edtCongXuatPhat2,edtChungLoaiAnten,edtLoaiAnten,edtDoTangIch,edtBangTan,edtDoDaiBucXa,edtGocNgang,
                edtGocPhuongVi,edtDoCao_vs_ChanCot,edtDoCao_vs_MatDat,edtChungLoaiFeeder,edtChieuDaiFeeder,edtSuyHaodBFeeder,
                edtSuyHaoFeeder,edtChungLoaiJumper,edtChieuDaiJumper,edtSuyHaodBJumper,edtSuyHaoJumper,edtConnector,edtTongSuyHao_Connector,edtTongSuyHao));
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
        AutoCompleteTextView edtSuyHao = dialogthongso.findViewById(R.id.edtSuyHao);
        ImageButton img_LoaiThietBi = dialogthongso.findViewById(R.id.img_LoaiThietBi);

        SPC.setPopUp(this,edtTenThanhPhan,SPC.LayDanhSachTenThanhPhan(),img_LoaiThietBi);

        TextView Title = dialogthongso.findViewById(R.id.title);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenThanhPhan,edtSuyHao));

                File pathDuLieu = new File(pathThietKeAnten,"ThanhPhan");
                SPC.TaoThuMuc(pathDuLieu);
                try {
                    SPC.SaveListAutoCompleteTextView_json(edtTenThanhPhan.getText().toString().trim(),pathDuLieu,listAutoCompleteTextView,SPC.ThietKeThanhPhan);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    //setUpListView();
                    TinhTongSuyHaodB();

                Toast.makeText(Activity_ChiTiet_Anten.this, "Đã tạo thành phần " + edtTenThanhPhan.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                dialogthongso.dismiss();

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
        Button btnSua = (Button) dialogthongso.findViewById(R.id.btnmenudoiten);btnSua.setText("Chỉnh sửa");
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
                            //setUpListView();
                            TinhTongSuyHaodB();
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
                DialogChinhSua(vt);
                dialogthongso.dismiss();

            }
        });
    }

    private void DialogChinhSua(int i){
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
        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle); tvTitle.setText("Chỉnh sửa");
        AutoCompleteTextView edtTenThanhPhan = dialogthongso.findViewById(R.id.edtTenThanhPhan);edtTenThanhPhan.setText(list_ThanhPhan.get(i).getTenThanhPhan());
        AutoCompleteTextView edtSuyHao = dialogthongso.findViewById(R.id.edtSuyHao);edtSuyHao.setText(list_ThanhPhan.get(i).getSuyHao());

        TextView Title = dialogthongso.findViewById(R.id.title);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);btnLuu.setText("Lưu thông số");
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenThanhPhan,edtSuyHao));

                        File pathDuLieu = new File(pathThietKeAnten,"ThanhPhan");
                        if(pathDuLieu.exists())
                        {
                            try
                            {
                                File old = new File( pathDuLieu,list_ThanhPhan.get(i).getTenThanhPhan() + ".txt");
                                old.delete();
                                SPC.SaveListAutoCompleteTextView_json(edtTenThanhPhan.getText().toString().trim(),pathDuLieu,listAutoCompleteTextView,SPC.ThietKeThanhPhan);
                                Toast.makeText(Activity_ChiTiet_Anten.this, "Đã lưu thành phần " + edtTenThanhPhan.getText().toString().trim(), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            /*try {
                                setUpListView();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                            TinhTongSuyHaodB();
                            dialogthongso.dismiss();
                        }

            }
        });

    }
}
