package com.example.chirag.googlesignin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Activity_DanhSach_CongTrinh extends AppCompatActivity {
    ListView listview;
    ImageButton btnBack, btnSua, btnChup, btnMenu;
    ImageView imgHinh;
    List<DoiTuong_CongTrinh> list_CongTrinh = new ArrayList<>();
    Adapter_DoiTuong_CongTrinh adapter_doiTuong_CongTrinh;
    FloatingActionButton fab;
    String MaTram, TenCot, TenTramGoc, TenAnten, DiaDiem, ToaDo, ThuTuAnten;
    TextView title, tvToaDo, tvViTri, tvTenHinhAnh;
    File pathThietKeAnten, pathHinhAnh;
    ArrayList<AutoCompleteTextView> listAutoCompleteTextView;
    Integer indexCot, indexAnten, indexBts;
    int[] listID;
    AutoCompleteTextView[] listedt;
    Button btnLuu;
    File mFile;
    Uri imageUri;
    int height ;
    int width ;
    //AutoCompleteTextView
    AutoCompleteTextView edtTenCongTrinh, edtChieuCao, edtKhoangCach, edtSoTang, edtGocPhuongVi, edtDoDay, edtDoRong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_congtrinh);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        SuKien();
        NhanBien();
// <<<<<<< DoXuanHieu
        try {
            SettupCongTrinhCaoTang();
// =======
//         DisplayMetrics displayMetrics = new DisplayMetrics();
//         getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//         height = displayMetrics.heightPixels;
//         width = displayMetrics.widthPixels;
// //        try {
// //            SettupCongTrinhCaoTang();
// //        } catch (JSONException e) {
// //            e.printStackTrace();
// //        }
//         try {
// >>>>>>> main
            listview.setVisibility(View.GONE);
            SettupListView();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
// <<<<<<< DoXuanHieu

    private void SettupCongTrinhCaoTang() throws JSONException, IOException, SAXException, ParserConfigurationException {
//        File filecongtrinh = new File(pathThietKeAnten, "CongTrinhCaoTang");
//        if (filecongtrinh.exists()) {
//            if (filecongtrinh.isDirectory()) {
//                File[] listcongtrinh = filecongtrinh.listFiles();
//                for (File congtrinh : listcongtrinh) {
//                    if (congtrinh.getName().contains(".txt")) {
//                        SPC.ReadListAutoCompleteTextView_Json(congtrinh.getName(), filecongtrinh, listAutoCompleteTextView, SPC.ThietKeCongTrinh);
//                    }
//                }
//            }
//        } else SPC.TaoThuMuc(filecongtrinh);
        XML_Tram xml_tram = SPC.readXml(pathThietKeAnten);
        List<String> listCongTrinh = xml_tram.getListCot().get(indexCot).getListBTS_inCot().get(indexBts).getListAnten_inBTS().get(indexAnten).getListCongTrinhThapTang();
        if (listCongTrinh != null) {
            if (listCongTrinh.size() > 0) {
                for (String item : listCongTrinh) {
                    SPC.ReadList_Json(item, listAutoCompleteTextView, SPC.ThietKeCongTrinh);
                }
            }
        }
    }

    private void SettupListView() throws JSONException, IOException, SAXException, ParserConfigurationException {
        list_CongTrinh.clear();
//        File filecongtrinh = new File(pathThietKeAnten, "CongTrinhThapTang");
//        if (filecongtrinh.exists()) {
//            if (filecongtrinh.isDirectory()) {
//                File[] listcongtrinh = filecongtrinh.listFiles();
//                if (listcongtrinh.length == 0) {
//                    listview.setVisibility(View.GONE);
//                } else {
//                    listview.setVisibility(View.VISIBLE);
//                }
//                for (File congtrinh : listcongtrinh) {
//                    if (congtrinh.getName().contains(".txt")) {
//                        String thietkecongtrinh = SPC.readText(congtrinh);
//                        JSONObject jsonObject = new JSONObject(thietkecongtrinh);
//                        String TenCongTrinh = jsonObject.getString("TenCongTrinh");
//                        String ChieuCao = jsonObject.getString("ChieuCao");
//                        String KhoangCach = jsonObject.getString("KhoangCach");
//                        String SoTang = jsonObject.getString("SoTang");
//                        String GocPhuongVi = jsonObject.getString("GocPhuongVi");
//                        String DoDay = jsonObject.getString("DoDay");
//                        String DoRong = jsonObject.getString("DoRong");
////                        String[] mangcongtrinh = thietkecongtrinh.split("&");
////                        String TenCongTrinh = mangcongtrinh[SPC.TimViTri("TenCongTrinh",SPC.ThietKeCongTrinh)];
////                        String ChieuCao = mangcongtrinh[SPC.TimViTri("ChieuCao",SPC.ThietKeCongTrinh)];
////                        String KhoangCach = mangcongtrinh[SPC.TimViTri("KhoangCach",SPC.ThietKeCongTrinh)];
////                        String SoTang = mangcongtrinh[SPC.TimViTri("SoTang",SPC.ThietKeCongTrinh)];
////                        String GocPhuongVi = mangcongtrinh[SPC.TimViTri("GocPhuongVi",SPC.ThietKeCongTrinh)];
////                        String DoDay = mangcongtrinh[SPC.TimViTri("DoDay",SPC.ThietKeCongTrinh)];
////                        String DoRong = mangcongtrinh[SPC.TimViTri("DoRong",SPC.ThietKeCongTrinh)];
//                        list_CongTrinh.add(new DoiTuong_CongTrinh(TenCongTrinh, ChieuCao, KhoangCach, SoTang, GocPhuongVi, DoDay, DoRong));
//                    }
//                }
//            } else {
//                SPC.TaoThuMuc(filecongtrinh);
//                Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã tạo " + filecongtrinh.getName(), Toast.LENGTH_SHORT).show();
//
//            }
//        }
        XML_Tram xml_tram = SPC.readXml(pathThietKeAnten);
        List<String> listCongTrinh = xml_tram.getListCot().get(indexCot).getListBTS_inCot().get(indexBts).getListAnten_inBTS().get(indexAnten).getListCongTrinhThapTang();
        if (listCongTrinh != null) {
            if (listCongTrinh.size() > 0) {
                listview.setVisibility(View.VISIBLE);
                for (String item : listCongTrinh) {
                    String thietkecongtrinh = item;
                    JSONObject jsonObject = new JSONObject(thietkecongtrinh);
                    String TenCongTrinh = jsonObject.getString("TenCongTrinh");
                    String ChieuCao = jsonObject.getString("ChieuCao");
                    String KhoangCach = jsonObject.getString("KhoangCach");
                    String SoTang = jsonObject.getString("SoTang");
                    String GocPhuongVi = jsonObject.getString("GocPhuongVi");
                    String DoDay = jsonObject.getString("DoDay");
                    String DoRong = jsonObject.getString("DoRong");
                    list_CongTrinh.add(new DoiTuong_CongTrinh(TenCongTrinh, ChieuCao, KhoangCach, SoTang, GocPhuongVi, DoDay, DoRong));
                    System.out.println("ittem"+ item);
// =======
// //    private void SettupCongTrinhCaoTang() throws JSONException {
// //        File filecongtrinh = new File (pathThietKeAnten,"CongTrinhCaoTang");
// //        if (filecongtrinh.exists())
// //        {
// //            if (filecongtrinh.isDirectory()){
// //                File[] listcongtrinh = filecongtrinh.listFiles();
// //                for(File congtrinh:listcongtrinh)
// //                {
// //                    if(congtrinh.getName().contains(".txt"))
// //                    {
// //                        SPC.ReadListAutoCompleteTextView_Json(congtrinh.getName(),filecongtrinh,listAutoCompleteTextView,SPC.ThietKeCongTrinh);
// //                    }
// //                }
// //            }
// //        }
// //        else SPC.TaoThuMuc(filecongtrinh);
// //    }
//     private void SettupListView() throws JSONException {
//         list_CongTrinh.clear();
//         File filecongtrinh = new File (pathThietKeAnten,"CongTrinhThapTang");
//         if (filecongtrinh.exists()){
//             if (filecongtrinh.isDirectory()){
//                 File[] listcongtrinh = filecongtrinh.listFiles();
//                 if (listcongtrinh.length ==0)
//                 {
//                     listview.setVisibility(View.GONE);
//                 } else
//                 {
//                     listview.setVisibility(View.VISIBLE);
//                 }
//                 for(File congtrinh:listcongtrinh)
//                 {
//                     if(congtrinh.getName().contains(".txt"))
//                     {
//                         String thietkecongtrinh = SPC.readText(congtrinh);
//                         JSONObject jsonObject = new JSONObject(thietkecongtrinh);
//                         String TenCongTrinh = jsonObject.getString("TenCongTrinh");
//                         String ChieuCao = jsonObject.getString("ChieuCao");
//                         String KhoangCach = jsonObject.getString("KhoangCach");
//                         String SoTang = jsonObject.getString("SoTang");
//                         String GocPhuongVi = jsonObject.getString("GocPhuongVi");
//                         String DoDay = jsonObject.getString("DoDay");
//                         String DoRong = jsonObject.getString("DoRong");
// //                        String[] mangcongtrinh = thietkecongtrinh.split("&");
// //                        String TenCongTrinh = mangcongtrinh[SPC.TimViTri("TenCongTrinh",SPC.ThietKeCongTrinh)];
// //                        String ChieuCao = mangcongtrinh[SPC.TimViTri("ChieuCao",SPC.ThietKeCongTrinh)];
// //                        String KhoangCach = mangcongtrinh[SPC.TimViTri("KhoangCach",SPC.ThietKeCongTrinh)];
// //                        String SoTang = mangcongtrinh[SPC.TimViTri("SoTang",SPC.ThietKeCongTrinh)];
// //                        String GocPhuongVi = mangcongtrinh[SPC.TimViTri("GocPhuongVi",SPC.ThietKeCongTrinh)];
// //                        String DoDay = mangcongtrinh[SPC.TimViTri("DoDay",SPC.ThietKeCongTrinh)];
// //                        String DoRong = mangcongtrinh[SPC.TimViTri("DoRong",SPC.ThietKeCongTrinh)];
//                         list_CongTrinh.add(new DoiTuong_CongTrinh(TenCongTrinh,ChieuCao,KhoangCach,SoTang,GocPhuongVi,DoDay,DoRong,width));
//                     }
// >>>>>>> main
                }
                /**HIỂN THỊ RA MÀN HÌNH*/
                adapter_doiTuong_CongTrinh = new Adapter_DoiTuong_CongTrinh(list_CongTrinh, Activity_DanhSach_CongTrinh.this, R.layout.item_congtrinh);
                listview.setAdapter(adapter_doiTuong_CongTrinh);
            }
        } else {
            listview.setVisibility(View.GONE);

        }
    }

    private void NhanBien() {
        Intent intent = getIntent();//Nhận biến truyền từ trang danh sách cột
        MaTram = intent.getStringExtra("MaTram");
        TenCot = intent.getStringExtra("TenCot");
        TenAnten = intent.getStringExtra("TenAnten");
        TenTramGoc = intent.getStringExtra("TenTramGoc");
        ThuTuAnten = intent.getStringExtra("ThuTuAnten");
        indexCot = Integer.parseInt(intent.getStringExtra("indexCot"));
        indexBts = Integer.parseInt(intent.getStringExtra("indexBTS"));
        indexAnten = Integer.parseInt(intent.getStringExtra("indexAnten"));
        title.setText(MaTram + " - " + TenAnten);
        DiaDiem = intent.getStringExtra("DiaDiem");
        tvViTri.setText(DiaDiem);
        ToaDo = intent.getStringExtra("ToaDo");
        tvToaDo.setText(ToaDo);
        pathThietKeAnten = new File(SPC.pathDataApp_PNDT, MaTram + "/DuLieu/" + "Data.xml");
        pathHinhAnh = new File(SPC.pathDataApp_PNDT, MaTram + "/HinhAnh");
//        pathThietKeAnten = new File(SPC.pathDataApp_PNDT, MaTram + "/DuLieu/" + TenCot + "/" + TenTramGoc + "/" + TenAnten);
        pathHinhAnh = new File(SPC.pathDataApp_PNDT, MaTram + SPC.DuongDanThuMucHinhAnh);
        mFile = new File(pathHinhAnh, SPC.TenHinhAnh.get(Integer.parseInt(ThuTuAnten)));
        if (mFile.exists()) {
            tvTenHinhAnh.setText(mFile.getName());
            mFile = new File(mFile, "image" + ".jpg");
            if (mFile.exists()) {
                Uri uriImage = Uri.parse(mFile.getPath());
                imgHinh.setImageURI(uriImage);
            }
        } else {
            SPC.TaoThuMuc(mFile);
            mFile = new File(mFile, "image" + ".jpg");
        }
    }

    private void SuKien() {
        btnChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 7);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Activity_DanhSach_CongTrinh.this,"lomng ",Toast.LENGTH_SHORT).show();
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
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_DanhSach_CongTrinh.this, ActivityMenu.class);
                intent.putExtra("MaTram", MaTram);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom, R.anim.zoomin);
            }
        });
       /* btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView edtTenCongTrinh = findViewById(R.id.edtTenCongTrinh);
                AutoCompleteTextView edtChieuCaoCongTrinh = findViewById(R.id.edtChieuCaoCongTrinh);
                @SuppressLint("WrongViewCast") AutoCompleteTextView edtKhoangCach = findViewById(R.id.edtKhoangCach);
                @SuppressLint("WrongViewCast") AutoCompleteTextView edtSoTang = findViewById(R.id.edtSoTang);
                @SuppressLint("WrongViewCast") AutoCompleteTextView edtGocPhuongVi = findViewById(R.id.edtGocPhuongVi);
                @SuppressLint("WrongViewCast") AutoCompleteTextView edtDoDay = findViewById(R.id.edtDoDay);
                @SuppressLint("WrongViewCast") AutoCompleteTextView edtDoRong = findViewById(R.id.edtDoRong);
                Button btnLuu = findViewById(R.id.btnLuu);
                btnLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean check = true;
                        ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenCongTrinh, edtChieuCaoCongTrinh, edtKhoangCach, edtSoTang, edtGocPhuongVi, edtDoDay, edtDoRong));
                        for (AutoCompleteTextView edt : listAutoCompleteTextView) {
                            if (edt.getText().toString().trim().equals("")) {
                                check = false;
                                Toast.makeText(Activity_DanhSach_CongTrinh.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                                break;
                            }
//                            else {
//                                File pathDuLieu = new File(pathThietKeAnten, "CongTrinhCaoTang");
//                                if (pathDuLieu.exists()) {
//                                    try {
//                                        SPC.SaveListAutoCompleteTextView_json(edtTenCongTrinh.getText().toString(), pathDuLieu, listAutoCompleteTextView, SPC.ThietKeCongTrinh);
//                                        Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã lưu dữ liệu!", Toast.LENGTH_SHORT).show();
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    try {
//                                        SettupListView();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                } else {
//                                    SPC.TaoThuMuc(pathDuLieu);
//                                    Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã tạo " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
                            if (check) {
                                try {
                                    XML_Tram xml_tram = SPC.readXml(pathThietKeAnten);
                                    List<String> listCongTrinhOld = xml_tram.getListCot().get(indexCot).getListBTS_inCot().get(indexBts).getListAnten_inBTS().get(indexAnten).getListCongTrinhThapTang();
                                    List<String> listCongTrinhNew = new ArrayList<>();
                                    if (listCongTrinhOld != null) {
                                        if (listCongTrinhOld.size() > 0) {
                                            listCongTrinhNew = listCongTrinhOld;
                                        }
                                    }
                                    JSONObject obj = new JSONObject();
                                    for (int i = 0; i < listAutoCompleteTextView.size(); i++) {
                                        obj.put(SPC.ThietKeCongTrinh.get(i), listAutoCompleteTextView.get(i).getText().toString());
                                    }
                                    listCongTrinhNew.add(obj.toString());
                                    xml_tram.getListCot().get(indexCot).getListBTS_inCot().get(indexBts).getListAnten_inBTS().get(indexAnten).setListCongTrinhThapTang(listCongTrinhNew);
                                    SPC.writeXml(pathThietKeAnten, xml_tram);
                                    SettupListView();
                                    Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã lưu dữ liệu!", Toast.LENGTH_SHORT).show();
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
                        }
                    }
                });
            }
        });*/
    }

    private void AnhXa() {
        listview = findViewById(R.id.listview_congtrinh);
        btnBack = findViewById(R.id.btnBack);
        fab = findViewById(R.id.fab);
        title = findViewById(R.id.title);
        tvToaDo = findViewById(R.id.tvToaDo);
        btnMenu = findViewById(R.id.btnMenu);
        tvViTri = findViewById(R.id.tvViTri);
//        btnLuu = findViewById(R.id.btnLuu);
        btnChup = findViewById(R.id.btnChup);
        btnSua = findViewById(R.id.btnSua);
        imgHinh = findViewById(R.id.imgHinh);
        tvTenHinhAnh = findViewById(R.id.tvTenHinhAnh);
        listID = new int[]{R.id.edtTenCongTrinh, R.id.edtChieuCaoCongTrinh, R.id.edtKhoangCach, R.id.edtSoTang, R.id.edtGocPhuongVi, R.id.edtDoDay, R.id.edtDoRong};
        listedt = new AutoCompleteTextView[]{edtTenCongTrinh, edtChieuCao, edtKhoangCach, edtSoTang, edtGocPhuongVi, edtDoDay, edtDoRong};
        listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>();
        for (int i = 0; i < listedt.length; i++) {
            listedt[i] = (AutoCompleteTextView) findViewById(listID[i]);
            listAutoCompleteTextView.add(listedt[i]);
        }
    }

    private void DialogThemCongtrinh() {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_CongTrinh.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_congtrinh);
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

        AutoCompleteTextView edtTenCongTrinh = dialogthongso.findViewById(R.id.edtTenCongTrinh);
        AutoCompleteTextView edtChieuCaoCongTrinh = dialogthongso.findViewById(R.id.edtChieuCaoCongTrinh);
        AutoCompleteTextView edtKhoangCach = dialogthongso.findViewById(R.id.edtKhoangCach);
        AutoCompleteTextView edtSoTang = dialogthongso.findViewById(R.id.edtSoTang);
        AutoCompleteTextView edtGocPhuongVi = dialogthongso.findViewById(R.id.edtGocPhuongVi);
        AutoCompleteTextView edtDoDay = dialogthongso.findViewById(R.id.edtDoDay);
        AutoCompleteTextView edtDoRong = dialogthongso.findViewById(R.id.edtDoRong);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);
        ImageButton btnChonCongTrinh = dialogthongso.findViewById(R.id.btnChonCongTrinh);
        SPC.setPopUp(Activity_DanhSach_CongTrinh.this, edtTenCongTrinh, SPC.listLoaiCongTrinh, btnChonCongTrinh);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenCongTrinh, edtChieuCaoCongTrinh, edtKhoangCach, edtSoTang, edtGocPhuongVi, edtDoDay, edtDoRong));
                for (AutoCompleteTextView edt : listAutoCompleteTextView) {
                    if (edt.getText().toString().trim().equals("")) {
                        check = false;
                        Toast.makeText(Activity_DanhSach_CongTrinh.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
//                    else {
//                        File pathDuLieu = new File(pathThietKeAnten, "CongTrinhThapTang");
//                        if (pathDuLieu.exists()) {
//                            try {
//                                SPC.SaveListAutoCompleteTextView_json(edtTenCongTrinh.getText().toString(), pathDuLieu, listAutoCompleteTextView, SPC.ThietKeCongTrinh);
//                                Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã lưu dữ liệu!", Toast.LENGTH_SHORT).show();
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                                SettupListView();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            dialogthongso.dismiss();
//                        } else {
//                            SPC.TaoThuMuc(pathDuLieu);
//                            Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã tạo " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
                }
                if (check) {
                    try {
                        XML_Tram xml_tram = SPC.readXml(pathThietKeAnten);
                        List<String> listCongTrinhOld = xml_tram.getListCot().get(indexCot).getListBTS_inCot().get(indexBts).getListAnten_inBTS().get(indexAnten).getListCongTrinhThapTang();
                        List<String> listCongTrinhNew = new ArrayList<>();
                        if (listCongTrinhOld != null) {
                            if (listCongTrinhOld.size() > 0) {
                                listCongTrinhNew = listCongTrinhOld;
                            }
                        }
                        JSONObject obj = new JSONObject();
                        for (int i = 0; i < listAutoCompleteTextView.size(); i++) {
                            obj.put(SPC.ThietKeCongTrinh.get(i), listAutoCompleteTextView.get(i).getText().toString());
                        }
                        listCongTrinhNew.add(obj.toString());
                        xml_tram.getListCot().get(indexCot).getListBTS_inCot().get(indexBts).getListAnten_inBTS().get(indexAnten).setListCongTrinhThapTang(listCongTrinhNew);
                        SPC.writeXml(pathThietKeAnten, xml_tram);
                        SettupListView();
                        Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã lưu dữ liệu!", Toast.LENGTH_SHORT).show();
                        dialogthongso.dismiss();
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
            }
        });

    }

    private void DialogMenu(int vt) {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_CongTrinh.this, R.style.PauseDialog);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_DanhSach_CongTrinh.this);
                builder.setTitle("Bạn muốn xóa trạm này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        File pathDuLieu = new File(pathThietKeAnten, "CongTrinhThapTang");
//                        if (pathDuLieu.exists()) {
//                            File file = new File(pathDuLieu, list_CongTrinh.get(vt).getTenCongTrinh() + ".txt");
//                            try {
//                                file.delete();
//                                Toast.makeText(getApplicationContext(), "Đã xóa công trình!", Toast.LENGTH_SHORT).show();
//                                SettupListView();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            dialogthongso.dismiss();
//                        }

                        try {
                            XML_Tram xml_tram = SPC.readXml(pathThietKeAnten);
                            xml_tram.getListCot().get(indexCot).getListBTS_inCot().get(indexBts).getListAnten_inBTS().get(indexAnten).getListCongTrinhThapTang().remove(vt);
                            SettupListView();
                            Toast.makeText(getApplicationContext(), "Đã xóa công trình!", Toast.LENGTH_SHORT).show();
                            dialogthongso.dismiss();
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                try {
                    DialogDoiten(vt);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                dialogthongso.dismiss();

            }
        });
    }

    private void DialogDoiten(int vt) throws IOException, SAXException, ParserConfigurationException {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_CongTrinh.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_them_congtrinh);
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
        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle);
        tvTitle.setText("Chỉnh sửa");
        AutoCompleteTextView edtTenCongTrinh = dialogthongso.findViewById(R.id.edtTenCongTrinh);
        AutoCompleteTextView edtChieuCaoCongTrinh = dialogthongso.findViewById(R.id.edtChieuCaoCongTrinh);
        AutoCompleteTextView edtKhoangCach = dialogthongso.findViewById(R.id.edtKhoangCach);
        AutoCompleteTextView edtSoTang = dialogthongso.findViewById(R.id.edtSoTang);
        AutoCompleteTextView edtGocPhuongVi = dialogthongso.findViewById(R.id.edtGocPhuongVi);
        AutoCompleteTextView edtDoDay = dialogthongso.findViewById(R.id.edtDoDay);
        AutoCompleteTextView edtDoRong = dialogthongso.findViewById(R.id.edtDoRong);
        ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenCongTrinh, edtChieuCaoCongTrinh, edtKhoangCach, edtSoTang, edtGocPhuongVi, edtDoDay, edtDoRong));
        File filecongtrinh = new File(pathThietKeAnten, "CongTrinhThapTang");
        XML_Tram xml_tram = SPC.readXml(pathThietKeAnten);
        try {
            SPC.ReadList_Json(xml_tram.getListCot().get(indexCot).getListBTS_inCot().get(indexBts).getListAnten_inBTS().get(indexAnten).getListCongTrinhThapTang().get(vt), listAutoCompleteTextView, SPC.ThietKeCongTrinh);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);
        btnLuu.setText("Lưu chỉnh sửa");
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                for (AutoCompleteTextView edt : listAutoCompleteTextView) {
                    if (edt.getText().toString().trim().equals("")) {
                        check = false;
                        Toast.makeText(Activity_DanhSach_CongTrinh.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
//                    else {
//                        File pathDuLieu = new File(pathThietKeAnten, "CongTrinhThapTang");
//                        if (pathDuLieu.exists()) {
//                            try {
//                                SPC.SaveListAutoCompleteTextView_json(edtTenCongTrinh.getText().toString(), pathDuLieu, listAutoCompleteTextView, SPC.ThietKeCongTrinh);
//                                Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã lưu dữ liệu!", Toast.LENGTH_SHORT).show();
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                                SettupListView();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            dialogthongso.dismiss();
//                        } else {
//                            SPC.TaoThuMuc(pathDuLieu);
//                            Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã tạo " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    }

                }
                if (check) {
                    try {
                        XML_Tram xml_tram = SPC.readXml(pathThietKeAnten);
                        List<String> listCongTrinhOld = xml_tram.getListCot().get(indexCot).getListBTS_inCot().get(indexBts).getListAnten_inBTS().get(indexAnten).getListCongTrinhThapTang();
                        List<String> listCongTrinhNew = new ArrayList<>();
                        if (listCongTrinhOld != null) {
                            if (listCongTrinhOld.size() > 0) {
                                listCongTrinhNew = listCongTrinhOld;
                                listCongTrinhNew.remove(vt);
                            }
                        }
                        JSONObject obj = new JSONObject();
                        for (int i = 0; i < listAutoCompleteTextView.size(); i++) {
                            obj.put(SPC.ThietKeCongTrinh.get(i), listAutoCompleteTextView.get(i).getText().toString());
                        }
                        listCongTrinhNew.add(vt, obj.toString());
                        xml_tram.getListCot().get(indexCot).getListBTS_inCot().get(indexBts).getListAnten_inBTS().get(indexAnten).setListCongTrinhThapTang(listCongTrinhNew);
                        SPC.writeXml(pathThietKeAnten, xml_tram);
                        SettupListView();
                        Toast.makeText(Activity_DanhSach_CongTrinh.this, "Đã lưu dữ liệu!", Toast.LENGTH_SHORT).show();
                        dialogthongso.dismiss();
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
            }
        });

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
            Bitmap bitmap2 = SPC.GanToaDo(bitmap, MaTram, ToaDo, DiaDiem);
            imgHinh.setImageBitmap(bitmap2);
            FileOutputStream output = null;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            try {
                output = new FileOutputStream(mFile);
                output.write(byteArray);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != output) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
