package com.example.chirag.googlesignin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.client.auth.oauth.AbstractOAuthGetToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class Activity_ChiTiet_Anten extends AppCompatActivity {
    LinearLayout btnDanhSachCongTrinh;

    ImageButton btnBack, btnMolayoutThietBi, btnMolayoutAnten, btnMolayoutSuyHao, btnMenu, btnChonCot, btnChonBTS, btnChonAnten,
            img_ChungLoaiThietBi, img_CongXuatPhat1, img_ChungLoaiAnten, img_ChungLoaiFeeder, img_ChungLoaiJumper, img_BangTan, img_LoaiAnten,img_Connector;
    Button btnLuuThietBi, btnLuuAnten, btnLuuSuyHao;
    LinearLayout layoutThietBi, layoutAnten, layoutSuyHao;
    FloatingActionButton fab;
    String MaTram, TenCot, TenTramGoc, TenAnten, DiaDiem, ToaDo, ThuTuAnten, BangTanHoatDong, ChungLoaiThietBi,SoMayThuPhatSong;
    String indexCot, indexBts, indexAntens;
    TextView title, tvTenCot, tvTenBTS;
    File pathThietKeAnten, pathDanhSachCot, pathDanhSachBTS, pathDanhSachAnten;

    ArrayList<AutoCompleteTextView> listAutoCompleteTextView;
    ViewGroup viewgroup;
    EditText edtTITLDien, edtTITLCo;
    int[] listID;
    AutoCompleteTextView[] list;
    ListView listview_thanhphan;
    List<DoiTuong_ThanhPhan> list_ThanhPhan = new ArrayList<>();
    Adapter_DoiTuong_ThanhPhan adapter_doiTuong_thanhphan;
    File pathTramGlobal;
    File pathDuLieuGlobal;
    File pathDataXmlGlobal;
    XML_Tram xml_tramGlobal;

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
        } catch (JSONException | IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

//        try {
//            setUpView();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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
    private void setUpListView() throws JSONException {
        list_ThanhPhan.clear();

//        File fileThanhPhan = new File(pathThietKeAnten, "ThanhPhan");
//        if (fileThanhPhan.exists()) {
//            if (fileThanhPhan.isDirectory()) {
//                File[] listThanhPhan = fileThanhPhan.listFiles();
//                if (listThanhPhan.length == 0) {
//                    listview_thanhphan.setVisibility(View.GONE);
//                } else {
//                    listview_thanhphan.setVisibility(View.VISIBLE);
//                    for (File thanhphan : listThanhPhan) {
//                        if (thanhphan.getName().contains(".txt")) {
//                            String thietkethanhphan = SPC.readText(thanhphan);
//                            JSONObject jsonObject = new JSONObject(thietkethanhphan);
//                            String TenThanhPhan = jsonObject.getString("TenThanhPhan");
//                            String ChieuDai = jsonObject.getString("ChieuDai");
//                            String ChungLoai = jsonObject.getString("ChungLoai");
//                            String SuyHaodB = jsonObject.getString("SuyHaodB");
//                            String SuyHao = jsonObject.getString("SuyHao");
//                            list_ThanhPhan.add(new DoiTuong_ThanhPhan(TenThanhPhan, ChieuDai, ChungLoai, SuyHaodB, SuyHao));
//                        }
//                    }
//                    /**HIỂN THỊ RA MÀN HÌNH*/
//                    adapter_doiTuong_thanhphan = new Adapter_DoiTuong_ThanhPhan(list_ThanhPhan, Activity_ChiTiet_Anten.this, R.layout.item_thanhphan);
//                    listview_thanhphan.setAdapter(adapter_doiTuong_thanhphan);
//
//                }
//            }
//        } else {
//            SPC.TaoThuMuc(fileThanhPhan);
//            Toast.makeText(Activity_ChiTiet_Anten.this, "Đã tạo " + fileThanhPhan.getName(), Toast.LENGTH_SHORT).show();
//        }
        try {
            XML_Tram xml_tram = SPC.readXml(pathDataXmlGlobal);
            if (xml_tram != null) {
                if (indexBts != null && indexAntens != null) {
                    if (Integer.parseInt(indexBts) >= 0 && Integer.parseInt(indexAntens) >= 0) {
                        List<String> listThanhPhan = xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).getListAnten_inBTS().get(Integer.parseInt(indexAntens)).getListThanhPhan();
                        if (listThanhPhan != null) {
                            if (listThanhPhan.size() == 0) {
                                listview_thanhphan.setVisibility(View.GONE);
                            } else if (listThanhPhan.size() > 0) {
                                listview_thanhphan.setVisibility(View.VISIBLE);
                                for (String tp : listThanhPhan) {
                                    String thietke = tp;
                                    JSONObject jsonObject = new JSONObject(thietke);
                                    if (!thietke.equals("")) {
                                        String TenThanhPhan = jsonObject.getString("TenThanhPhan");
                                        String ChieuDai = jsonObject.getString("ChieuDai");
                                        String ChungLoai = jsonObject.getString("ChungLoai");
                                        String SuyHaodB = jsonObject.getString("SuyHaodB");
                                        String SuyHao = jsonObject.getString("SuyHao");
                                        list_ThanhPhan.add(new DoiTuong_ThanhPhan(TenThanhPhan, ChieuDai, ChungLoai, SuyHaodB, SuyHao));
                                    }
                                }
                                System.out.println(listThanhPhan.toString());
                                //                    /**HIỂN THỊ RA MÀN HÌNH*/
                                adapter_doiTuong_thanhphan = new Adapter_DoiTuong_ThanhPhan(list_ThanhPhan, Activity_ChiTiet_Anten.this, R.layout.item_thanhphan);
                                listview_thanhphan.setAdapter(adapter_doiTuong_thanhphan);
                            }
                        }else listview_thanhphan.setVisibility(View.GONE);

                    }
                }
            } else listview_thanhphan.setVisibility(View.GONE);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
// =======
//         double _TongSuyHao = 0.0;
//         File fileThanhPhan = new File (pathThietKeAnten,"ThanhPhan");
//         if (fileThanhPhan.exists()){
//             if (fileThanhPhan.isDirectory()){
//                 File[] listThanhPhan = fileThanhPhan.listFiles();
//                 if (listThanhPhan.length ==0){
//                     listview_thanhphan.setVisibility(View.GONE);
//                 } else
//                 {
//                     listview_thanhphan.setVisibility(View.VISIBLE);
//                     for(File thanhphan:listThanhPhan)
//                     {
//                         if(thanhphan.getName().contains(".txt"))
//                         {
//                             String thietkethanhphan = SPC.readText(thanhphan);
//                             JSONObject jsonObject = new JSONObject(thietkethanhphan);
//                             String TenThanhPhan = jsonObject.getString("TenThanhPhan");
//                             String SuyHao = jsonObject.getString("SuyHao");
//                             if(SPC.isNumeric(SuyHao)) _TongSuyHao += Double.valueOf(SuyHao);
//                             list_ThanhPhan.add(new DoiTuong_ThanhPhan(TenThanhPhan,SuyHao));
//                         }
//                     }
//                     /**HIỂN THỊ RA MÀN HÌNH*/
//                     adapter_doiTuong_thanhphan = new Adapter_DoiTuong_ThanhPhan(list_ThanhPhan, Activity_ChiTiet_Anten.this,R.layout.item_thanhphan);
//                     listview_thanhphan.setAdapter(adapter_doiTuong_thanhphan);
//                 }
//             }
//         }
//         else
//         {
//             SPC.TaoThuMuc(fileThanhPhan);
//             Toast.makeText(Activity_ChiTiet_Anten.this, "Đã tạo " + fileThanhPhan.getName(), Toast.LENGTH_SHORT).show();
// >>>>>>> main
//         }
//         return _TongSuyHao;
    }

    private void setPopupCot() throws IOException, SAXException, ParserConfigurationException, JSONException {
//        pathDanhSachCot = new File(SPC.pathDataApp_PNDT, MaTram);
//        pathDanhSachCot = new File(pathDanhSachCot, "DuLieu");
        ArrayList<String> arr_Cot = new ArrayList<>();

//        if (pathDanhSachCot.exists()) {
//            File[] files = pathDanhSachCot.listFiles();
//
//            for (File file : files) {
//                if (file.isDirectory()) {
//                    if (!file.getName().equals("DanhSachBTS"))
//                        arr_Cot.add(file.getName());
//                }
//            }
//            SPC.setPopUp_img(Activity_ChiTiet_Anten.this, tvTenCot, arr_Cot, btnChonCot);
//            tvTenCot.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    tvTenBTS.setText("");
//                    setPopupBTS(tvTenCot.getText().toString());
//                }
//            });
//            tvTenCot.setText(TenCot);
//        }
//        /**KIỂM TRA XEM ĐÃ CHỌN CỘT VÀ BTS CHƯA*/
//        edtTenAnten.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (tvTenBTS.getText().toString().equals("") || tvTenCot.getText().toString().equals("")) {
//                    Toast.makeText(Activity_ChiTiet_Anten.this, "Bạn chưa chọn thiết bị!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        XML_Tram xml_tramTG = SPC.readXml(pathDataXmlGlobal);
        if (xml_tramTG != null) {
            if (xml_tramTG.getListCot() != null) {
                if (xml_tramTG.getListCot().size() > 0) {
                    for (XML_Cot cot : xml_tramTG.getListCot()) {
                        String thietke = cot.getThietKeCot();
                        JSONObject jsonObject = new JSONObject(thietke);
                        if (!thietke.equals("")) {
                            String TenCot = jsonObject.getString("TenCot");
                            arr_Cot.add(TenCot);
                        }
                    }
                    SPC.setPopUp_img(Activity_ChiTiet_Anten.this, tvTenCot, arr_Cot, btnChonCot);
                    tvTenCot.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
// =======
//         if (pathDanhSachCot.exists()){
//             File[] files=pathDanhSachCot.listFiles();
//             for (File file:files)
//             {
//                 if(file.isDirectory())
//                 {
//                     if (!file.getName().equals("DanhSachBTS"))
//                     arr_Cot.add(file.getName());
//                 }
//             }
//             SPC.setPopUp_img(Activity_ChiTiet_Anten.this,tvTenCot,arr_Cot,btnChonCot);
//             tvTenCot.addTextChangedListener(new TextWatcher() {
//                 @Override
//                 public void beforeTextChanged(CharSequence s, int start, int count, int after) {
// >>>>>>> main

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            tvTenBTS.setText("");
                            try {
                                for (int i = 0; i < arr_Cot.size(); i++) {
                                    if (arr_Cot.get(i).trim().equals(tvTenCot.getText().toString())) {
                                        indexCot = String.valueOf(i);
                                        break;
                                    }
                                }
                                setPopupBTS(tvTenCot.getText().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
//                    tvTenCot.setText(TenCot);
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
                        if (tvTenBTS.getText().toString().equals("") || tvTenCot.getText().toString().equals("")) {
                            Toast.makeText(Activity_ChiTiet_Anten.this, "Bạn chưa chọn thiết bị!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }


    private void setPopupBTS(String tenCot) throws JSONException {
//        pathDanhSachBTS = new File(pathDanhSachCot, tenCot);
        ArrayList<String> arr_BTS = new ArrayList<>();
        File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
        File pathDuLieu = new File(pathTramMoi, "DuLieu");
        File pathData = new File(pathDuLieu, "Data.xml");
        try {
            XML_Tram xml_tram = SPC.readXml(pathData);
            if (xml_tram != null) {
                XML_Cot xml_cot = xml_tram.getListCot().get(Integer.parseInt(indexCot));
                for (XML_BTS itemBts : xml_cot.getListBTS_inCot()) {
                    arr_BTS.add(itemBts.getTenBTS());
                }
                SPC.setPopUp_img(Activity_ChiTiet_Anten.this, tvTenBTS, arr_BTS, btnChonBTS);
//                String thietke = xml_cot.getThietKeCot();
//                JSONObject jsonObject = new JSONObject(thietke);
//                if (!thietke.equals("")) {
//                    String TenCot = jsonObject.getString("TenCot");
//                }
                tvTenBTS.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String btsGoc = "";
                        int i = 0;
                        for (String stringBts : xml_tram.getListBTS()) {
                            String thietke = stringBts;
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(thietke);
                                if (!thietke.equals("")) {
                                    String tenBts = jsonObject.getString("TenTramGoc");
                                    if (tenBts.equals(tvTenBTS.getText().toString().trim())) {
                                        btsGoc = stringBts;
                                        indexBts = String.valueOf(i);
// =======
//                 @Override
//                 public void afterTextChanged(Editable s) {
//                     File fileTram = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/DanhSachBTS");
//                     File[] files=fileTram.listFiles();
//                     try{
//                         for (File fileThietKe:files)
//                         {
//                             if(fileThietKe.getName().contains(tvTenBTS.getText().toString()))
//                             {
//                                 if(fileThietKe.exists())
//                                 {
//                                     String thietke = SPC.readText(fileThietKe);
//                                     JSONObject jsonObject = new JSONObject(thietke);
//                                     if (!thietke.equals(""))
//                                     {
//                                         for(AutoCompleteTextView edt : listAutoCompleteTextView)
//                                         {
//                                             edt.setText("");
//                                         }
//                                         BangTanHoatDong = jsonObject.getString("BangTanHoatDong");
//                                         ChungLoaiThietBi = jsonObject.getString("ChungLoaiThietBi");
//                                         SoMayThuPhatSong = jsonObject.getString("SoMayThuPhatSong");
//                                         edtChungLoaiThietBi.setText(ChungLoaiThietBi);
//                                         edtSoMayThu.setText(SoMayThuPhatSong);
//                                         edtBangTan.setText(BangTanHoatDong);
//                                         ArrayList<String> lstCongSuat=  SPC.layCongSuatPhat1(ChungLoaiThietBi,BangTanHoatDong);
//                                         if(lstCongSuat.size()>0)
//                                         {
//                                             edtCongXuatPhat1.setText(lstCongSuat.get(0));
//                                             edtCongXuatPhat2.setText(SPC.TinhCongSuatPhat2(edtCongXuatPhat1.getText().toString()));
//                                             SPC.setPopUp(Activity_ChiTiet_Anten.this,edtCongXuatPhat1,lstCongSuat,img_CongXuatPhat1);
//                                         }
// >>>>>>> main
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            i++;
                        }

                        String thietke2 = btsGoc;
                        JSONObject jsonObject2 = null;
                        try {
                            jsonObject2 = new JSONObject(thietke2);
                            if (!thietke2.equals("")) {
                                for (AutoCompleteTextView edt : listAutoCompleteTextView) {
                                    edt.setText("");
                                }
                                BangTanHoatDong = jsonObject2.getString("BangTanHoatDong");
                                ChungLoaiThietBi = jsonObject2.getString("ChungLoaiThietBi");
                                edtChungLoaiThietBi.setText(ChungLoaiThietBi);
                                edtBangTan.setText(BangTanHoatDong);
                                ArrayList<String> lstCongSuat = SPC.layCongSuatPhat1(ChungLoaiThietBi, BangTanHoatDong);
                                if (lstCongSuat.size() > 0) {
                                    edtCongXuatPhat1.setText(lstCongSuat.get(0));
                                    edtCongXuatPhat2.setText(SPC.TinhCongSuatPhat2(edtCongXuatPhat1.getText().toString()));
                                    SPC.setPopUp(Activity_ChiTiet_Anten.this, edtCongXuatPhat1, lstCongSuat, img_CongXuatPhat1);
                                }
                            }
                            setPopupAnten(tvTenBTS.getText().toString());
                            // remove toàn bộ list view
                            adapter_doiTuong_thanhphan = new Adapter_DoiTuong_ThanhPhan(new ArrayList<>(), Activity_ChiTiet_Anten.this, R.layout.item_thanhphan);
                            listview_thanhphan.setAdapter(adapter_doiTuong_thanhphan);
                        } catch (JSONException | IOException | SAXException | ParserConfigurationException e) {
                            e.printStackTrace();
                        }

                    }

                });
            }
        } catch (
                ParserConfigurationException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                SAXException e) {
            e.printStackTrace();
        }

//        if (pathDanhSachBTS.exists()){
//            File[] files=pathDanhSachBTS.listFiles();
//            for (File file:files)
//            {
//                if(file.isDirectory()) arr_BTS.add(file.getName());
//            }
//            SPC.setPopUp_img(Activity_ChiTiet_Anten.this,tvTenBTS,arr_BTS,btnChonBTS);
//            tvTenBTS.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    File fileTram = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/DanhSachBTS");
//                    File[] files=fileTram.listFiles();
//                    try{
//                        for (File fileThietKe:files)
//                        {
//                            if(fileThietKe.getName().contains(tvTenBTS.getText().toString()))
//                            {
//                                if(fileThietKe.exists())
//                                {
//                                    String thietke = SPC.readText(fileThietKe);
//                                    JSONObject jsonObject = new JSONObject(thietke);
//                                    if (!thietke.equals(""))
//                                    {
//                                        for(AutoCompleteTextView edt : listAutoCompleteTextView)
//                                        {
//                                            edt.setText("");
//                                        }
//                                        BangTanHoatDong = jsonObject.getString("BangTanHoatDong");
//                                        ChungLoaiThietBi = jsonObject.getString("ChungLoaiThietBi");
//                                        edtChungLoaiThietBi.setText(ChungLoaiThietBi);
//                                        edtBangTan.setText(BangTanHoatDong);
//                                        ArrayList<String> lstCongSuat=  SPC.layCongSuatPhat1(ChungLoaiThietBi,BangTanHoatDong);
//                                        if(lstCongSuat.size()>0)
//                                        {
//                                            edtCongXuatPhat1.setText(lstCongSuat.get(0));
//                                            edtCongXuatPhat2.setText(SPC.TinhCongSuatPhat2(edtCongXuatPhat1.getText().toString()));
//                                            SPC.setPopUp(Activity_ChiTiet_Anten.this,edtCongXuatPhat1,lstCongSuat,img_CongXuatPhat1);
//                                        }
//                                    }
//                                }
//                            }
//
//                        }
//                    }
//                    catch (Exception ignored){}
//                    setPopupAnten(tvTenBTS.getText().toString());
//
//                }
//            });
//        }
    }

    private void setPopupAnten(String tenBTS) throws IOException, SAXException, ParserConfigurationException, JSONException {
//        pathDanhSachAnten = new File(pathDanhSachBTS, tenBTS);
        ArrayList<String> arr_Anten = new ArrayList<>();
//        if (pathDanhSachAnten.exists()) {
//            File[] files = pathDanhSachAnten.listFiles();
//            for (File file : files) {
//                if (file.isDirectory()) arr_Anten.add(file.getName());
//            }
//            ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(Activity_ChiTiet_Anten.this, R.layout.custom_list_item, R.id.text_view_list_item, arr_Anten);
//            edtTenAnten.setAdapter(adapterHT);
//            edtTenAnten.setThreshold(1);
//            edtTenAnten.setDropDownHeight(400);
//            if (btnChonAnten != null) {
//                btnChonAnten.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        final PopupMenu popupMenu = new PopupMenu(Activity_ChiTiet_Anten.this, btnChonAnten);
//
//                        for (String s : arr_Anten) {
//                            popupMenu.getMenu().add(s);
//                        }
//                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(final MenuItem menuItem) {
//                                edtTenAnten.setText(menuItem.getTitle());
//                                pathThietKeAnten = new File(pathDanhSachAnten, edtTenAnten.getText().toString());
//
//                                try {
//                                    setUpView();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                return false;
//                            }
//                        });
//                        popupMenu.show();
//                    }
//                });
//            }

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
//        }

        XML_Tram xml_tram = SPC.readXml(pathDataXmlGlobal);
        if (xml_tram != null) {
            if (indexBts != null) {
                List<XML_Anten> listAnten = xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).getListAnten_inBTS();
                for (XML_Anten item : listAnten) {
                    String thietke = item.getThietKeAnTen();
                    JSONObject jsonObject = new JSONObject(thietke);
                    if (!thietke.equals("")) {
                        String tenAnten = jsonObject.getString("TenAnten");
                        arr_Anten.add(tenAnten);
                    }
                }
                ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(Activity_ChiTiet_Anten.this, R.layout.custom_list_item, R.id.text_view_list_item, arr_Anten);
                edtTenAnten.setAdapter(adapterHT);
                edtTenAnten.setThreshold(1);
                edtTenAnten.setDropDownHeight(400);
                if (btnChonAnten != null) {
                    btnChonAnten.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final PopupMenu popupMenu = new PopupMenu(Activity_ChiTiet_Anten.this, btnChonAnten);

                            for (String s : arr_Anten) {
                                popupMenu.getMenu().add(s);
                            }
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(final MenuItem menuItem) {
                                    edtTenAnten.setText(menuItem.getTitle());
                                    try {
                                        setUpView(listAnten, String.valueOf(menuItem.getTitle()));
                                        listview_thanhphan.setVisibility(View.GONE);
                                        setUpListView();
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
            }

        }
    }

    private void setUpView(List<XML_Anten> listAnten, String nameAnten) throws JSONException {
        String thietKeAnten = "";
        if (listAnten != null) {
            if (listAnten.size() > 0) {
                for (int i = 0; i < listAnten.size(); i++) {
                    String thietke = listAnten.get(i).getThietKeAnTen();
                    JSONObject jsonObject = new JSONObject(thietke);
                    if (!thietke.equals("")) {
                        String tenAnten = jsonObject.getString("TenAnten");
                        if (tenAnten.equals(nameAnten)) {
                            thietKeAnten = listAnten.get(i).getThietKeAnTen();
                            indexAntens = String.valueOf(i);
                        }
                    }
                }
            }
        }

        if (thietKeAnten != "") {
            SPC.ReadList_Json(thietKeAnten, listAutoCompleteTextView, SPC.ThietKeAnten);
            edtChungLoaiThietBi.setText(ChungLoaiThietBi);
            edtBangTan.setText(BangTanHoatDong);
            SPC.setPopUp(Activity_ChiTiet_Anten.this, edtChungLoaiThietBi, SPC.LayDanhSachThietBi(), img_ChungLoaiThietBi);
            edtChungLoaiThietBi.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    ArrayList<String> lstCongSuat = SPC.layCongSuatPhat1(edtChungLoaiThietBi.getText().toString(), edtBangTan.getText().toString());
                    if (lstCongSuat.size() > 0) {
                        edtCongXuatPhat1.setText(lstCongSuat.get(0));
                        SPC.setPopUp(Activity_ChiTiet_Anten.this, edtCongXuatPhat1, lstCongSuat, img_CongXuatPhat1);
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
                    ArrayList<String> lstCongSuat = SPC.layCongSuatPhat1(edtChungLoaiThietBi.getText().toString(), edtBangTan.getText().toString());
                    if (lstCongSuat.size() > 0) {
                        edtCongXuatPhat1.setText(lstCongSuat.get(0));
                        SPC.setPopUp(Activity_ChiTiet_Anten.this, edtCongXuatPhat1, lstCongSuat, img_CongXuatPhat1);
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
            edtTITLCo.setText(SPC.layGocNgang(edtChungLoaiAnten.getText().toString(), edtBangTan.getText().toString()));
            if (!edtTITLCo.getText().toString().trim().equals("")) {
                if (SPC.isNumeric(edtTITLCo.getText().toString().trim())) {
                    if (!edtGocNgang.getText().toString().trim().equals("")) {
                        if (SPC.isNumeric(edtGocNgang.getText().toString().trim())) {
                            Double TillTong = Double.valueOf(edtGocNgang.getText().toString().trim());
                            Double TillCo = Double.valueOf(edtTITLCo.getText().toString().trim());
                            edtTITLDien.setText(String.valueOf(TillTong - TillCo));
                        }
                    }
                }
            }
            //setUpListView();
            TinhTongSuyHaodB();
        }

//        File filethietKe = new File(pathThietKeAnten, "ThietKeAnten.txt");
//        if (filethietKe.exists()) {
//            SPC.ReadListAutoCompleteTextView_Json("ThietKeAnten.txt", pathThietKeAnten, listAutoCompleteTextView, SPC.ThietKeAnten);
//            edtChungLoaiThietBi.setText(ChungLoaiThietBi);
//            edtBangTan.setText(BangTanHoatDong);
//            SPC.setPopUp(Activity_ChiTiet_Anten.this, edtChungLoaiThietBi, SPC.LayDanhSachThietBi(), img_ChungLoaiThietBi);
//            edtChungLoaiThietBi.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    ArrayList<String> lstCongSuat = SPC.layCongSuatPhat1(edtChungLoaiThietBi.getText().toString(), edtBangTan.getText().toString());
//                    if (lstCongSuat.size() > 0) {
//                        edtCongXuatPhat1.setText(lstCongSuat.get(0));
//                        SPC.setPopUp(Activity_ChiTiet_Anten.this, edtCongXuatPhat1, lstCongSuat, img_CongXuatPhat1);
//                    }
//                }
//            });
//            edtSoMayThu.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    ArrayList<String> lstCongSuat = SPC.layCongSuatPhat1(edtChungLoaiThietBi.getText().toString(), edtBangTan.getText().toString());
//                    if (lstCongSuat.size() > 0) {
//                        edtCongXuatPhat1.setText(lstCongSuat.get(0));
//                        SPC.setPopUp(Activity_ChiTiet_Anten.this, edtCongXuatPhat1, lstCongSuat, img_CongXuatPhat1);
//                    }
//                }
//            });
//            edtCongXuatPhat1.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    edtCongXuatPhat2.setText(SPC.TinhCongSuatPhat2(edtCongXuatPhat1.getText().toString()));
//                }
//            });
//            //TINH GOC NGANG
//            edtTITLCo.setText(SPC.layGocNgang(edtChungLoaiAnten.getText().toString(), edtBangTan.getText().toString()));
//            if (!edtTITLCo.getText().toString().trim().equals("")) {
//                if (SPC.isNumeric(edtTITLCo.getText().toString().trim())) {
//                    if (!edtGocNgang.getText().toString().trim().equals("")) {
//                        if (SPC.isNumeric(edtGocNgang.getText().toString().trim())) {
//                            Double TillTong = Double.valueOf(edtGocNgang.getText().toString().trim());
//                            Double TillCo = Double.valueOf(edtTITLCo.getText().toString().trim());
//                            edtTITLDien.setText(String.valueOf(TillTong - TillCo));
//                        }
//                    }
//                }
//            }
//        } else {
//            for (AutoCompleteTextView edt : listAutoCompleteTextView) {
//                edt.setText("");
//            }
//        }
    }

    private void NhanBien() throws JSONException, IOException, SAXException, ParserConfigurationException {
        Intent intent = getIntent();//Nhận biến truyền từ trang danh sách cột
        MaTram = intent.getStringExtra("MaTram");
        TenCot = intent.getStringExtra("TenCot");
        TenAnten = intent.getStringExtra("TenAnten");
        indexCot = intent.getStringExtra("indexCot");
        //TenTramGoc =intent.getStringExtra("TenTramGoc");
        ThuTuAnten = intent.getStringExtra("ThuTuAnten");
        title.setText(MaTram);
        DiaDiem = intent.getStringExtra("DiaDiem");
        ToaDo = intent.getStringExtra("ToaDo");
        pathTramGlobal = new File(SPC.pathDataApp_PNDT, MaTram);
        pathDuLieuGlobal = new File(pathTramGlobal, "DuLieu");
        pathDataXmlGlobal = new File(pathDuLieuGlobal, "Data.xml");
        xml_tramGlobal = SPC.readXml(pathDataXmlGlobal);
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

    @SuppressLint("RestrictedApi")
    private void SuKien() {
        btnDanhSachCongTrinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvTenBTS.getText().toString().equals("") && !tvTenCot.getText().toString().equals("") && !edtTenAnten.getText().toString().equals("")) {
                    /**LẤY VỊ TRÍ ANTEN TRONG DANH SÁCH*/
                    ArrayList<String> arr_Anten = new ArrayList<>();
                    try {
                        File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
                        File pathDuLieu = new File(pathTramMoi, "DuLieu");
                        File pathData = new File(pathDuLieu, "Data.xml");
                        XML_Tram xml_tram = SPC.readXml(pathData);
                        if (indexBts != null) {
                            XML_BTS xml_bts = xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts));
                            if (xml_bts.getListAnten_inBTS() != null) {
                                if (xml_bts.getListAnten_inBTS().size() > 0) {
                                    XML_Anten xml_anten = null;
                                    for (XML_Anten item : xml_bts.getListAnten_inBTS()) {
                                        String thietke = item.getThietKeAnTen();
                                        JSONObject jsonObject = new JSONObject(thietke);
                                        if (!thietke.equals("")) {
                                            String tenAnten = jsonObject.getString("TenAnten");
                                            arr_Anten.add(tenAnten);
//                                            if (tenAnten.equals(edtTenAnten.getText().toString().trim())){
//                                                xml_anten = item;
//                                                break;
//                                            }
                                        }
                                    }
                                    int sttAnten = arr_Anten.indexOf(
                                            edtTenAnten.getText().toString());
                                    if (sttAnten >= 0) {
                                        ThuTuAnten = String.valueOf(sttAnten);
                                        /**TRUYỀN CÁC BIẾN DANG TRANG CÔNG TRÌNH*/
                                        Intent intent = new Intent(Activity_ChiTiet_Anten.this, Activity_DanhSach_CongTrinh.class);
                                        intent.putExtra("MaTram", MaTram);
                                        intent.putExtra("TenCot", tvTenCot.getText().toString());
                                        intent.putExtra("TenTramGoc", tvTenBTS.getText().toString());
                                        intent.putExtra("ThuTuAnten", ThuTuAnten);
                                        intent.putExtra("TenAnten", edtTenAnten.getText().toString());
                                        intent.putExtra("DiaDiem", DiaDiem);
                                        intent.putExtra("ToaDo", ToaDo);
                                        intent.putExtra("indexCot", indexCot);
                                        intent.putExtra("indexBTS", indexBts);
                                        intent.putExtra("indexAnten", indexAntens);
                                        startActivity(intent);
                                    } else
                                        Toast.makeText(Activity_ChiTiet_Anten.this, "Bạn chưa có anten!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    if (pathDanhSachAnten.exists()) {
//                        File[] files = pathDanhSachAnten.listFiles();
//                        for (File file : files) {
//                            if (file.isDirectory()) arr_Anten.add(file.getName());
//                        }
//                        int sttAnten = arr_Anten.indexOf(edtTenAnten.getText().toString());
//                        if (sttAnten >= 0) {
//                            ThuTuAnten = String.valueOf(sttAnten);
//                            /**TRUYỀN CÁC BIẾN DANG TRANG CÔNG TRÌNH*/
//                            Intent intent = new Intent(Activity_ChiTiet_Anten.this, Activity_DanhSach_CongTrinh.class);
//                            intent.putExtra("MaTram", MaTram);
//                            intent.putExtra("TenCot", tvTenCot.getText().toString());
//                            intent.putExtra("TenTramGoc", tvTenBTS.getText().toString());
//                            intent.putExtra("ThuTuAnten", ThuTuAnten);
//                            intent.putExtra("TenAnten", edtTenAnten.getText().toString());
//                            intent.putExtra("DiaDiem", DiaDiem);
//                            intent.putExtra("ToaDo", ToaDo);
//                            startActivity(intent);
//                        } else
//                            Toast.makeText(Activity_ChiTiet_Anten.this, "Bạn chưa có anten!", Toast.LENGTH_SHORT).show();
//
//                    }
                } else
                    Toast.makeText(Activity_ChiTiet_Anten.this, "Bạn chưa chọn thiết bị!", Toast.LENGTH_SHORT).show();
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_ChiTiet_Anten.this, ActivityMenu.class);
                intent.putExtra("MaTram", MaTram);
                intent.putExtra("DiaDiem", DiaDiem);
                intent.putExtra("ToaDo", ToaDo);
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
                if (layoutThietBi.getVisibility() == View.GONE) {
                    layoutThietBi.setVisibility(View.VISIBLE);
                    layoutAnten.setVisibility(View.GONE);
                    layoutSuyHao.setVisibility(View.GONE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
                } else {
                    layoutThietBi.setVisibility(View.GONE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
                }
                fab.setVisibility(layoutSuyHao.getVisibility());
                btnLuuSuyHao.setVisibility(layoutSuyHao.getVisibility());

            }
        });
        btnMolayoutAnten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutAnten.getVisibility() == View.GONE) {
                    layoutThietBi.setVisibility(View.GONE);
                    layoutAnten.setVisibility(View.VISIBLE);
                    layoutSuyHao.setVisibility(View.GONE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
                } else {
                    layoutAnten.setVisibility(View.GONE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
                }
                fab.setVisibility(layoutSuyHao.getVisibility());
                btnLuuSuyHao.setVisibility(layoutSuyHao.getVisibility());
            }
        });
        btnMolayoutSuyHao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutSuyHao.getVisibility() == View.GONE) {
                    layoutThietBi.setVisibility(View.GONE);
                    layoutAnten.setVisibility(View.GONE);
                    layoutSuyHao.setVisibility(View.VISIBLE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
                } else {
                    layoutSuyHao.setVisibility(View.GONE);
                    AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(150);
                    TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
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

        SPC.setPopUp(this, edtChungLoaiAnten, SPC.LayDanhSachAnten(), img_ChungLoaiAnten);
        SPC.setPopUp(this, edtBangTan, SPC.listBangTan, img_BangTan);
        SPC.setPopUp(this, edtLoaiAnten, SPC.listLoaiAnten, img_LoaiAnten);
// =======

//         SPC.setPopUp(this,edtChungLoaiAnten,SPC.LayDanhSachAnten(),img_ChungLoaiAnten);
//         SPC.setPopUp(this,edtBangTan,SPC.listBangTan,img_BangTan);
//         SPC.setPopUp(this,edtLoaiAnten,SPC.listLoaiAnten,img_LoaiAnten);
//         SPC.setPopUp(this,edtConnector,SPC.listSoDauConnecter,img_Connector);
// >>>>>>> main

        edtChungLoaiAnten.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                edtDoTangIch.setText(SPC.layDoTangIchAnten(edtChungLoaiAnten.getText().toString(), edtBangTan.getText().toString()));
                edtDoDaiBucXa.setText(SPC.layDoDaiBucXa(edtChungLoaiAnten.getText().toString(), edtBangTan.getText().toString()));
                edtTITLCo.setText(SPC.layGocNgang(edtChungLoaiAnten.getText().toString(), edtBangTan.getText().toString()));
// =======
//                 edtDoTangIch.setText(SPC.layDoTangIchAnten(edtChungLoaiAnten.getText().toString(),edtBangTan.getText().toString()));
//                 edtDoDaiBucXa.setText(SPC.layDoDaiBucXa(edtChungLoaiAnten.getText().toString(),edtBangTan.getText().toString()));
//                 edtTITLCo.setText(SPC.layGocNgang(edtChungLoaiAnten.getText().toString(),edtBangTan.getText().toString()));
//                 edtLoaiAnten.setText("Định hướng");
//                 edtConnector.setText("4");
// >>>>>>> main

            }
        });
        SPC.setPopUp(this, edtChungLoaiFeeder, SPC.LayDanhSachSuyHao(), img_ChungLoaiFeeder);
        SPC.setPopUp(this, edtChungLoaiJumper, SPC.LayDanhSachSuyHao(), img_ChungLoaiJumper);
        edtChungLoaiFeeder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                edtSuyHaodBFeeder.setText(SPC.laySuyHaodB(edtChungLoaiFeeder.getText().toString(), edtBangTan.getText().toString()));
// =======
//                 edtSuyHaodBFeeder.setText(SPC.laySuyHaodB("Feeder",edtChungLoaiFeeder.getText().toString(),edtBangTan.getText().toString()));
// >>>>>>> main
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

                edtSuyHaodBFeeder.setText(SPC.laySuyHaodB(edtChungLoaiFeeder.getText().toString(), edtBangTan.getText().toString()));
// =======
//                 edtSuyHaodBFeeder.setText(SPC.laySuyHaodB("Feeder",edtChungLoaiFeeder.getText().toString(),edtBangTan.getText().toString()));
// >>>>>>> main
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

                edtSuyHaodBJumper.setText(SPC.laySuyHaodB(edtChungLoaiJumper.getText().toString(), edtBangTan.getText().toString()));
// =======
//                 edtSuyHaodBJumper.setText(SPC.laySuyHaodB("Jumper",edtChungLoaiJumper.getText().toString(),edtBangTan.getText().toString()));
// >>>>>>> main
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

                edtSuyHaodBJumper.setText(SPC.laySuyHaodB(edtChungLoaiJumper.getText().toString(), edtBangTan.getText().toString()));
// =======
//                 edtSuyHaodBJumper.setText(SPC.laySuyHaodB("Jumper",edtChungLoaiJumper.getText().toString(),edtBangTan.getText().toString()));
// >>>>>>> main
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
                edtSuyHaoFeeder.setText(SPC.TinhSuyHao(edtChieuDaiFeeder.getText().toString(), edtSuyHaodBFeeder.getText().toString()));
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
                edtSuyHaoJumper.setText(SPC.TinhSuyHao(edtChieuDaiJumper.getText().toString(), edtSuyHaodBJumper.getText().toString()));
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
                if (!edtTITLCo.getText().toString().trim().equals("")) {
                    if (SPC.isNumeric(edtTITLCo.getText().toString().trim())) {
                        if (!edtTITLDien.getText().toString().trim().equals("")) {
                            if (SPC.isNumeric(edtTITLDien.getText().toString().trim())) {
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
                if (!edtTITLCo.getText().toString().trim().equals("")) {
                    if (SPC.isNumeric(edtTITLCo.getText().toString().trim())) {
                        if (!edtTITLDien.getText().toString().trim().equals("")) {
                            if (SPC.isNumeric(edtTITLDien.getText().toString().trim())) {
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
        File pathTram = new File(SPC.pathDataApp_PNDT, MaTram);
        File pathDuLieu = new File(pathTram, "DuLieu");
        File pathDataXml = new File(pathDuLieu, "Data.xml");
        try {
            XML_Tram xml_tram = SPC.readXml(pathDataXml);
            List<XML_Anten> listAnten = new ArrayList<>();
            XML_Anten xml_anten = new XML_Anten();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < listAutoCompleteTextView.size(); i++) {
                obj.put(SPC.ThietKeAnten.get(i), listAutoCompleteTextView.get(i).getText().toString());
            }
            xml_anten.setThietKeAnTen(obj.toString());
            if (xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).getListAnten_inBTS() != null) {
                if (xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).getListAnten_inBTS().size() > 0) {
                    listAnten = xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).getListAnten_inBTS();
                }
            }
            listAnten.add(xml_anten);
            xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).setListAnten_inBTS(listAnten);
            SPC.saveToXml(xml_tram, "Data", pathDuLieu);
            Toast.makeText(getApplicationContext(), "Đã lưu!", Toast.LENGTH_SHORT).show();
            setUpListView();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

//        pathThietKeAnten = new File(pathDanhSachAnten, edtTenAnten.getText().toString());
//        if (!pathThietKeAnten.exists()) SPC.TaoThuMuc(pathThietKeAnten);
//        if (pathThietKeAnten.isDirectory()) {
//            SPC.SaveListAutoCompleteTextView_json("ThietKeAnten", pathThietKeAnten, listAutoCompleteTextView, SPC.ThietKeAnten);
//            Toast.makeText(getApplicationContext(), "Đã lưu!", Toast.LENGTH_SHORT).show();
//        }

    }

    @SuppressLint("RestrictedApi")
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
        layoutAnten = findViewById(R.id.layoutAnten);
        layoutAnten.setVisibility(View.GONE);
        layoutSuyHao = findViewById(R.id.layoutSuyHao);
        layoutSuyHao.setVisibility(View.GONE);
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

        listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenAnten, edtChungLoaiThietBi, edtSoMayThu,
                edtCongXuatPhat1, edtCongXuatPhat2, edtChungLoaiAnten, edtLoaiAnten, edtDoTangIch, edtBangTan, edtDoDaiBucXa, edtGocNgang,
                edtGocPhuongVi, edtDoCao_vs_ChanCot, edtDoCao_vs_MatDat, edtChungLoaiFeeder, edtChieuDaiFeeder, edtSuyHaodBFeeder,
                edtSuyHaoFeeder, edtChungLoaiJumper, edtChieuDaiJumper, edtSuyHaodBJumper, edtSuyHaoJumper, edtTongSuyHao));
// =======
//         listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenAnten,edtChungLoaiThietBi,edtSoMayThu,
//                 edtCongXuatPhat1,edtCongXuatPhat2,edtChungLoaiAnten,edtLoaiAnten,edtDoTangIch,edtBangTan,edtDoDaiBucXa,edtGocNgang,
//                 edtGocPhuongVi,edtDoCao_vs_ChanCot,edtDoCao_vs_MatDat,edtChungLoaiFeeder,edtChieuDaiFeeder,edtSuyHaodBFeeder,
//                 edtSuyHaoFeeder,edtChungLoaiJumper,edtChieuDaiJumper,edtSuyHaodBJumper,edtSuyHaoJumper,edtConnector,edtTongSuyHao_Connector,edtTongSuyHao));
// >>>>>>> main
    }

    private void DialogThemThanhPhan() {
        final Dialog dialogthongso = new Dialog(Activity_ChiTiet_Anten.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_thietbi);
        Window window = dialogthongso.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();

        AutoCompleteTextView edtTenThanhPhan = dialogthongso.findViewById(R.id.edtTenThanhPhan);
        AutoCompleteTextView edtSuyHao = dialogthongso.findViewById(R.id.edtSuyHao);
// <<<<<<< DoXuanHieu
        ImageButton img_ChungLoai = dialogthongso.findViewById(R.id.img_ChungLoai);

        SPC.setPopUp(this, edtChungLoai, SPC.LayDanhSachSuyHao(), img_ChungLoai);
        edtChungLoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSuyHaodB.setText(SPC.laySuyHaodB(edtChungLoai.getText().toString(), edtBangTan.getText().toString()));
            }
        });
        edtSuyHaodB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSuyHao.setText(SPC.TinhSuyHao(edtChieuDai.getText().toString(), edtSuyHaodB.getText().toString()));
            }
        });
// =======
//         ImageButton img_LoaiThietBi = dialogthongso.findViewById(R.id.img_LoaiThietBi);

//         SPC.setPopUp(this,edtTenThanhPhan,SPC.LayDanhSachTenThanhPhan(),img_LoaiThietBi);
// >>>>>>> main

        TextView Title = dialogthongso.findViewById(R.id.title);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// <<<<<<< DoXuanHieu
                boolean check = true;
                ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenThanhPhan, edtChungLoai, edtChieuDai, edtSuyHaodB, edtSuyHao));
                for (AutoCompleteTextView edt : listAutoCompleteTextView) {
                    if (edt.getText().toString().trim().equals("")) {
                        check = false;
                        Toast.makeText(Activity_ChiTiet_Anten.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
//                    else {
//                        File pathDuLieu = new File(pathThietKeAnten, "ThanhPhan");
//                        if (pathDuLieu.exists()) {
//                            try {
//                                SPC.SaveListAutoCompleteTextView_json(edtTenThanhPhan.getText().toString().trim(), pathDuLieu, listAutoCompleteTextView, SPC.ThietKeThanhPhan);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                                setUpListView();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            Toast.makeText(Activity_ChiTiet_Anten.this, "Đã tạo thành phần " + edtTenThanhPhan.getText().toString().trim(), Toast.LENGTH_SHORT).show();
//                            dialogthongso.dismiss();
//                        }
//                    }
                }
                if (check) {
                    File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
                    File pathDuLieu = new File(pathTramMoi, "DuLieu");
                    File pathData = new File(pathDuLieu, "Data.xml");
                    try {
                        XML_Tram xml_tram = SPC.readXml(pathData);
                        System.out.println(xml_tram.toString());
                        if (xml_tram != null) {
                            if (indexBts != null) {
                                if (xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).getListAnten_inBTS().size() > 0) {
                                    List<XML_Anten> listAnten = xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).getListAnten_inBTS();
                                    List<String> listThanhPhan = new ArrayList<>();
                                    for (int i = 0; i < listAnten.size(); i++) {
                                        String thietke = listAnten.get(i).getThietKeAnTen();
                                        JSONObject jsonObject = new JSONObject(thietke);
                                        if (!thietke.equals("")) {
                                            String tenAnten = jsonObject.getString("TenAnten");
                                            if (tenAnten.equals(edtTenAnten.getText().toString())) {
                                                indexAntens = String.valueOf(i);
                                            }
                                        }
                                    }

                                    if (indexAntens != null) {
                                        if (listAnten.get(Integer.parseInt(indexAntens)).getListThanhPhan() != null) {
                                            listThanhPhan = listAnten.get(Integer.parseInt(indexAntens)).getListThanhPhan();
                                        }
                                        JSONObject obj = new JSONObject();
                                        for (int i = 0; i < listAutoCompleteTextView.size(); i++) {
                                            obj.put(SPC.ThietKeThanhPhan.get(i), listAutoCompleteTextView.get(i).getText().toString());
                                        }
                                        listThanhPhan.add(obj.toString());
                                        listAnten.get(Integer.parseInt(indexAntens)).setListThanhPhan(listThanhPhan);
                                        xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).setListAnten_inBTS(listAnten);
                                        SPC.writeXml(pathData, xml_tram);
                                        setUpListView();
                                        Toast.makeText(Activity_ChiTiet_Anten.this, "Đã thêm thành phần.", Toast.LENGTH_SHORT).show();
                                        dialogthongso.dismiss();
                                    } else {
                                        Toast.makeText(Activity_ChiTiet_Anten.this, "Chưa chọn Anten, vui lòng chọn Anten trước.", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(Activity_ChiTiet_Anten.this, "Vui lòng thêm Anten trước.", Toast.LENGTH_SHORT).show();
                                }
                            } else
                                Toast.makeText(Activity_ChiTiet_Anten.this, "Chọn bts trước.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    }
// =======
//                 ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenThanhPhan,edtSuyHao));

//                 File pathDuLieu = new File(pathThietKeAnten,"ThanhPhan");
//                 SPC.TaoThuMuc(pathDuLieu);
//                 try {
//                     SPC.SaveListAutoCompleteTextView_json(edtTenThanhPhan.getText().toString().trim(),pathDuLieu,listAutoCompleteTextView,SPC.ThietKeThanhPhan);
//                 } catch (JSONException e) {
//                     e.printStackTrace();
// >>>>>>> main
                }

                    //setUpListView();
                    TinhTongSuyHaodB();

                Toast.makeText(Activity_ChiTiet_Anten.this, "Đã tạo thành phần " + edtTenThanhPhan.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                dialogthongso.dismiss();

            }
        });


    }

    private void DialogMenu(int vt) {
        final Dialog dialogthongso = new Dialog(Activity_ChiTiet_Anten.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_menu_main);
        Window window = dialogthongso.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();
        Button btnXoa = (Button) dialogthongso.findViewById(R.id.btnXoa);
        Button btnSua = (Button) dialogthongso.findViewById(R.id.btnmenudoiten);
        btnSua.setText("Chỉnh sửa");
        Button btnLoad = (Button) dialogthongso.findViewById(R.id.btnupload);
        btnLoad.setVisibility(View.GONE);

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
                        try {
// <<<<<<< DoXuanHieu
                            XML_Tram xml_tram = SPC.readXml(pathDataXmlGlobal);
                            List<String> listThanhPhan = xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).getListAnten_inBTS().get(Integer.parseInt(indexAntens)).getListThanhPhan();
                            if (listThanhPhan != null) {
                                if (listThanhPhan.size() > 0) {
                                    listThanhPhan.remove(vt);
                                    xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).getListAnten_inBTS().get(Integer.parseInt(indexAntens)).setListThanhPhan(listThanhPhan);
                                    SPC.writeXml(pathDataXmlGlobal, xml_tram);
                                    setUpListView();
                                    Toast.makeText(getApplicationContext(), "Đã xóa thư mục!", Toast.LENGTH_SHORT).show();
                                    dialogthongso.dismiss();
                                }
                            }
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (TransformerException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
// =======
//                             file.delete();
//                             Toast.makeText(getApplicationContext(),"Đã xóa thư mục ảnh!", Toast.LENGTH_SHORT).show();
//                             //setUpListView();
//                             TinhTongSuyHaodB();
//                         } catch (Exception e)
//                         {
// >>>>>>> main
                            e.printStackTrace();
                        }
//                        File file = new File(pathThietKeAnten, "ThanhPhan/" + list_ThanhPhan.get(vt).getTenThanhPhan() + ".txt");
//                        try {
//                            file.delete();
//                            Toast.makeText(getApplicationContext(), "Đã xóa thư mục!", Toast.LENGTH_SHORT).show();
//                            setUpListView();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        dialogthongso.dismiss();
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

    private void DialogChinhSua(int i) {
        final Dialog dialogthongso = new Dialog(Activity_ChiTiet_Anten.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_thietbi);
        Window window = dialogthongso.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();
// <<<<<<< DoXuanHieu
        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle);
        tvTitle.setText("Chỉnh sửa");
        AutoCompleteTextView edtTenThanhPhan = dialogthongso.findViewById(R.id.edtTenThanhPhan);
        edtTenThanhPhan.setText(list_ThanhPhan.get(i).getTenThanhPhan());
        AutoCompleteTextView edtChungLoai = dialogthongso.findViewById(R.id.edtChungLoai);
        edtChungLoai.setText(list_ThanhPhan.get(i).getChungLoai());
        AutoCompleteTextView edtChieuDai = dialogthongso.findViewById(R.id.edtChieuDai);
        edtChieuDai.setText(list_ThanhPhan.get(i).getChieuDai());
        AutoCompleteTextView edtSuyHaodB = dialogthongso.findViewById(R.id.edtSuyHaodB);
        edtSuyHaodB.setText(list_ThanhPhan.get(i).getSuyHaodB());
        AutoCompleteTextView edtSuyHao = dialogthongso.findViewById(R.id.edtSuyHao);
        edtSuyHao.setText(list_ThanhPhan.get(i).getSuyHao());
        ImageButton img_ChungLoai = dialogthongso.findViewById(R.id.img_ChungLoai);

        SPC.setPopUp(this, edtChungLoai, SPC.LayDanhSachSuyHao(), img_ChungLoai);
        edtChungLoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSuyHaodB.setText(SPC.laySuyHaodB(edtChungLoai.getText().toString(), edtBangTan.getText().toString()));
            }
        });
        edtSuyHaodB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSuyHao.setText(SPC.TinhSuyHao(edtChieuDai.getText().toString(), edtSuyHaodB.getText().toString()));
            }
        });

        TextView Title = dialogthongso.findViewById(R.id.title);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);
        btnLuu.setText("Lưu thông số");
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
                boolean check = true;
                ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenThanhPhan, edtChungLoai, edtChieuDai, edtSuyHaodB, edtSuyHao));
                for (AutoCompleteTextView edt : listAutoCompleteTextView) {
                    if (edt.getText().toString().trim().equals("")) {
                        check = false;
                        Toast.makeText(Activity_ChiTiet_Anten.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
//                    else {
//                        File pathDuLieu = new File(pathThietKeAnten, "ThanhPhan");
//                        if (pathDuLieu.exists()) {
//                            try {
//                                File old = new File(pathDuLieu, list_ThanhPhan.get(i).getTenThanhPhan() + ".txt");
//                                old.delete();
//                                SPC.SaveListAutoCompleteTextView_json(edtTenThanhPhan.getText().toString().trim(), pathDuLieu, listAutoCompleteTextView, SPC.ThietKeThanhPhan);
//                                Toast.makeText(Activity_ChiTiet_Anten.this, "Đã lưu thành phần " + edtTenThanhPhan.getText().toString().trim(), Toast.LENGTH_SHORT).show();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                                setUpListView();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            dialogthongso.dismiss();
//                        }
//                    }
                }
                if (check) {
                    try {
                        XML_Tram xml_tram = SPC.readXml(pathDataXmlGlobal);
                        if (xml_tram != null) {
                            if (indexBts != null) {
                                if (xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).getListAnten_inBTS().size() > 0) {
                                    List<XML_Anten> listAnten = xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).getListAnten_inBTS();
                                    List<String> listThanhPhan = new ArrayList<>();
                                    for (int i = 0; i < listAnten.size(); i++) {
                                        String thietke = listAnten.get(i).getThietKeAnTen();
                                        JSONObject jsonObject = new JSONObject(thietke);
                                        if (!thietke.equals("")) {
                                            String tenAnten = jsonObject.getString("TenAnten");
                                            if (tenAnten.equals(edtTenAnten.getText().toString())) {
                                                indexAntens = String.valueOf(i);
                                            }
                                        }
                                    }
                                    if (indexAntens != null) {
                                        if (listAnten.get(Integer.parseInt(indexAntens)).getListThanhPhan() != null) {
                                            listThanhPhan = listAnten.get(Integer.parseInt(indexAntens)).getListThanhPhan();
                                        }
                                        listThanhPhan.remove(i);
                                        JSONObject obj = new JSONObject();
                                        for (int i = 0; i < listAutoCompleteTextView.size(); i++) {
                                            obj.put(SPC.ThietKeThanhPhan.get(i), listAutoCompleteTextView.get(i).getText().toString());
                                        }
                                        listThanhPhan.add(i, obj.toString());
                                        listAnten.get(Integer.parseInt(indexAntens)).setListThanhPhan(listThanhPhan);
                                        xml_tram.getListCot().get(Integer.parseInt(indexCot)).getListBTS_inCot().get(Integer.parseInt(indexBts)).setListAnten_inBTS(listAnten);
                                        SPC.writeXml(pathDataXmlGlobal, xml_tram);
                                        Toast.makeText(Activity_ChiTiet_Anten.this, "Đã lưu thành phần.", Toast.LENGTH_SHORT).show();
                                        setUpListView();
                                        dialogthongso.dismiss();
                                    }

                                } else {
                                    Toast.makeText(Activity_ChiTiet_Anten.this, "Vui lòng thêm Anten trước.", Toast.LENGTH_SHORT).show();
                                }
                            } else
                                Toast.makeText(Activity_ChiTiet_Anten.this, "Chọn bts trước.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    }
                }
// =======
//         TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle); tvTitle.setText("Chỉnh sửa");
//         AutoCompleteTextView edtTenThanhPhan = dialogthongso.findViewById(R.id.edtTenThanhPhan);edtTenThanhPhan.setText(list_ThanhPhan.get(i).getTenThanhPhan());
//         AutoCompleteTextView edtSuyHao = dialogthongso.findViewById(R.id.edtSuyHao);edtSuyHao.setText(list_ThanhPhan.get(i).getSuyHao());

//         TextView Title = dialogthongso.findViewById(R.id.title);
//         Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);btnLuu.setText("Lưu thông số");
//         btnLuu.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenThanhPhan,edtSuyHao));

//                         File pathDuLieu = new File(pathThietKeAnten,"ThanhPhan");
//                         if(pathDuLieu.exists())
//                         {
//                             try
//                             {
//                                 File old = new File( pathDuLieu,list_ThanhPhan.get(i).getTenThanhPhan() + ".txt");
//                                 old.delete();
//                                 SPC.SaveListAutoCompleteTextView_json(edtTenThanhPhan.getText().toString().trim(),pathDuLieu,listAutoCompleteTextView,SPC.ThietKeThanhPhan);
//                                 Toast.makeText(Activity_ChiTiet_Anten.this, "Đã lưu thành phần " + edtTenThanhPhan.getText().toString().trim(), Toast.LENGTH_SHORT).show();

//                             } catch (JSONException e) {
//                                 e.printStackTrace();
//                             }
//                             /*try {
//                                 setUpListView();
//                             } catch (JSONException e) {
//                                 e.printStackTrace();
//                             }*/
//                             TinhTongSuyHaodB();
//                             dialogthongso.dismiss();
//                         }

// >>>>>>> main
            }
        });

    }
}
