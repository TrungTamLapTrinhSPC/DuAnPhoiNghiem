package com.example.chirag.googlesignin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;

import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.SerializablePermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Activity_DanhSach_Cot extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener {
    ListView listview, listview_bts;
    TextView title, tvToaDo, tvViTri, tvMaTram, tvViTriDat, tvNgaySua, tvSoAnh, tvTramGoc;
    TextView tvWidth, tvHeight;

    ImageButton btnBack, btnMenu, btnThemCot;
    LinearLayout btnAnhChup, btnThietKe, btnNhaDatTram, btnDanhSachBTS;
    List<DoiTuong_Cot> list_Cot = new ArrayList<>();
    Adapter_DoiTuong_Cot adapter_doiTuongCot;
    File pathDanhSachCot;
    File pathDataXml;
    String MaTram, DiaDiem, ToaDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_cot);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        NhanBien();
        pathDanhSachCot = new File(SPC.pathDataApp_PNDT, MaTram);
        pathDanhSachCot = new File(pathDanhSachCot, "DuLieu");
        pathDataXml = new File(pathDanhSachCot, "Data.xml");
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
        btnDanhSachBTS = findViewById(R.id.btnDanhSachBTS);
        title = findViewById(R.id.title);
        tvToaDo = findViewById(R.id.tvToaDo);
        tvViTri = findViewById(R.id.tvViTri);
        tvMaTram = findViewById(R.id.tvMaTram);
        tvViTriDat = findViewById(R.id.tvViTriDat);
        tvNgaySua = findViewById(R.id.tvNgaySua);
        tvSoAnh = findViewById(R.id.tvSoAnh);
        tvTramGoc = findViewById(R.id.tvTramGoc);
        tvWidth = (TextView) findViewById(R.id.tvWidth);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
    }

    //region Settup ListView
    private String DemSoBTS() {
        File fileTram = new File(SPC.pathDataApp_PNDT, MaTram + "/DuLieu/DanhSachBTS");
        int sotram = 0;
        File[] listCot = fileTram.listFiles();
        sotram = listCot.length;
        return String.valueOf(sotram);
    }

    private void SettupListView() {
        list_Cot = new ArrayList<>();
//        File[] files = pathDanhSachCot.listFiles();
//        try {
//            for (File file : files) {
//                if (file.isDirectory()) {
//                    File fileThietKe = new File(pathDanhSachCot, file.getName().trim() + "/ThietKeCot.txt");
//                    if (fileThietKe.exists()) {
//                        String thietke = SPC.readText(fileThietKe);
//                        JSONObject jsonObject = new JSONObject(thietke);
//                        if (!thietke.equals("")) {
//                            String TenCot = jsonObject.getString("TenCot");
//                            String ChieuCaoCot = jsonObject.getString("ChieuCaoCot");
//                            String SoChan = jsonObject.getString("SoChan");
//                            String ViTriX = jsonObject.getString("ViTriX");
//                            String ViTriY = jsonObject.getString("ViTriY");
//                            list_Cot.add(new DoiTuong_Cot(TenCot, ChieuCaoCot, SoChan, ViTriX, ViTriY));
//                        }
//                    }
//                }
//            }
//            adapter_doiTuongCot = new Adapter_DoiTuong_Cot(list_Cot, Activity_DanhSach_Cot.this, R.layout.item_cot);
//            listview.setAdapter(adapter_doiTuongCot);
//            setupThietKeTram();
//            setupHinhAnhTongThe();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            XML_Tram xml_tram = SPC.readXml(pathDataXml);
            if (xml_tram != null) {
                if (xml_tram.getListBTS() != null) {
                    tvTramGoc.setText(String.valueOf(xml_tram.getListBTS().size()));
                }
                if (xml_tram.getListCot() != null) {
                    if (xml_tram.getListCot().size() > 0) {
                        for (XML_Cot tkCot : xml_tram.getListCot()) {
                            String thietke = tkCot.getThietKeCot();
                            JSONObject jsonObject = new JSONObject(thietke);
                            if (!thietke.equals("")) {
                                String TenCot = jsonObject.getString("TenCot");
                                String ChieuCaoCot = jsonObject.getString("ChieuCaoCot");
                                String SoChan = jsonObject.getString("SoChan");
                                String ViTriX = jsonObject.getString("ViTriX");
                                String ViTriY = jsonObject.getString("ViTriY");
                                list_Cot.add(new DoiTuong_Cot(TenCot, ChieuCaoCot, SoChan, ViTriX, ViTriY));
                            }
                        }
                    }
                }
                adapter_doiTuongCot = new Adapter_DoiTuong_Cot(list_Cot, Activity_DanhSach_Cot.this, R.layout.item_cot);
                listview.setAdapter(adapter_doiTuongCot);
                setupThietKeTram();
                setupHinhAnhTongThe();
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

    }

    private void NhanBien() {
        Intent intent = getIntent();
        MaTram = intent.getStringExtra("MaTram");
        title.setText(MaTram);
        DiaDiem = intent.getStringExtra("DiaDiem");
        tvViTri.setText(DiaDiem);
        ToaDo = intent.getStringExtra("ToaDo");
        tvToaDo.setText(ToaDo);
        for (String img : SPC.TenHinhAnhTongThe) {
            File imgPath = new File(SPC.pathDataApp_PNDT, MaTram + SPC.DuongDanThuMucHinhAnh);
            imgPath = new File(imgPath, img);
            SPC.TaoThuMuc(imgPath);
        }
    }

    public void setupThietKeTram() throws JSONException, IOException, SAXException, ParserConfigurationException {
        File fileThietKe = new File(SPC.pathDataApp_PNDT, MaTram + SPC.DuongDanFileThietKeTram);
        if (fileThietKe.exists()) {
            XML_Tram xml_tram = SPC.readXml(fileThietKe);
            if (xml_tram != null) {
                String thietke = xml_tram.getThietKeTram();
                JSONObject jsonObject = new JSONObject(thietke);
                if (!thietke.equals("")) {
                    String MaTram = jsonObject.getString("MaTram");
                    String ViTriDat = jsonObject.getString("ViTriDat");
                    tvMaTram.setText(MaTram);
                    tvViTriDat.setText(ViTriDat);
                }
                setUpTextViewNhaDatTram(xml_tram, tvWidth, tvHeight);
            }
        }
    }

    public void setupHinhAnhTongThe() {
        //
        File ThuMucHinhAnh = new File(SPC.pathDataApp_PNDT, MaTram + SPC.DuongDanThuMucHinhAnh);
        String NgaySua = SPC.getLastModified(ThuMucHinhAnh);
        if (ThuMucHinhAnh.exists()) {
            File[] listHinhAnh = ThuMucHinhAnh.listFiles();
            int soAnh = 0;
            for (File file : listHinhAnh) {
                File[] listAnh = file.listFiles();
                int cou = listAnh.length - 1;
                if (cou >= 0) soAnh += 1;
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
                Intent intent = new Intent(Activity_DanhSach_Cot.this, Activity_ChiTiet_Anten.class);
                intent.putExtra("MaTram", MaTram);
                intent.putExtra("TenCot", list_Cot.get(position).getTenCot());
                intent.putExtra("DiaDiem", tvViTri.getText().toString());
                intent.putExtra("ToaDo", tvToaDo.getText().toString());
                intent.putExtra("indexCot", String.valueOf(position));
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
            public void onClick(View v) {
                Intent intent = new Intent(Activity_DanhSach_Cot.this, Activity_DanhSach_AnhChup.class);
                intent.putExtra("MaTram", MaTram);
                intent.putExtra("DiaDiem", tvViTri.getText().toString());
                intent.putExtra("ToaDo", tvToaDo.getText().toString());
                startActivity(intent);
            }
        });
        btnDanhSachBTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_DanhSach_Cot.this, Activity_DanhSach_BTS.class);
                intent.putExtra("MaTram", MaTram);
                intent.putExtra("DiaDiem", tvViTri.getText().toString());
                intent.putExtra("ToaDo", tvToaDo.getText().toString());
                startActivity(intent);
            }
        });
        btnNhaDatTram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DialogThietKeNhaTram();
                } catch (JSONException | IOException | SAXException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        });
        btnThietKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    DialogThietKe(Gravity.CENTER, "Thiết kế", "Lưu thiết kế");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        btnThemCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemCot(Gravity.CENTER, "Thêm cột anten");
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
    private void DialogMenu(int vt) {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this, R.style.PauseDialog);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_DanhSach_Cot.this);
                builder.setTitle("Bạn muốn xóa trạm này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
                        File pathDuLieu = new File(pathTramMoi, "DuLieu");
                        //SPC.ReadListAutoCompleteTextView("ThietKeTram.txt",pathDuLieu,listAutoCompleteTextView);
//        SPC.ReadListAutoCompleteTextView_Json("Data.xml",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeTram);
                        File pathFileData = new File(pathDuLieu, "Data.xml");
//                        File file = new File(pathDanhSachCot, list_Cot.get(vt).getTenCot());
//                        try {
//
//                            FileUtils.deleteDirectory(file);
//                            Toast.makeText(getApplicationContext(), "Đã xóa cột!", Toast.LENGTH_SHORT).show();
//                            SettupListView();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                        try {
                            XML_Tram xml_tram = SPC.readXml(pathFileData);
                            if (xml_tram != null) {
                                if (xml_tram.getListCot().size() > 0) {
                                    xml_tram.getListCot().remove(vt);
                                    SPC.writeXml(pathFileData,xml_tram);
                                    dialogthongso.dismiss();
                                    Toast.makeText(getApplicationContext(), "Đã xóa cột!", Toast.LENGTH_SHORT).show();
                                    SettupListView();
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
                    DialogChinhSuaCot(vt);
                } catch (JSONException | IOException | SAXException | ParserConfigurationException e) {
                    e.printStackTrace();
                }
                dialogthongso.dismiss();

            }
        });
    }

    private void DialogThietKe(int gravity, String title2, String titleButton) throws JSONException, IOException, SAXException, ParserConfigurationException {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_themtram);
        Window window = dialogthongso.getWindow();
        if (window == null) {
            return;
        }
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
        AutoCompleteTextView edtMaTram = dialogthongso.findViewById(R.id.edtMaTram);
        edtMaTram.setText(title.getText());
        edtMaTram.setEnabled(false);
        AutoCompleteTextView edtDiaDiem = dialogthongso.findViewById(R.id.edtDiaDiem);
        AutoCompleteTextView edtToaDo = dialogthongso.findViewById(R.id.edtToaDo);
        AutoCompleteTextView edtViTriDat = dialogthongso.findViewById(R.id.edtViTriDat);
        AutoCompleteTextView edtNgayDo = dialogthongso.findViewById(R.id.edtNgayDo);
        RadioButton checkbox_trenmai = dialogthongso.findViewById(R.id.checkbox_trenmai);
        RadioButton checkbox_duoidat = dialogthongso.findViewById(R.id.checkbox_duoidat);
        RadioGroup radioGroup = (RadioGroup) dialogthongso.findViewById(R.id.radioGroup);
        ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtMaTram, edtDiaDiem, edtToaDo, edtNgayDo, edtViTriDat));
        /**
         * Nhận dữ liệu
         */
        File pathTramMoi = new File(SPC.pathDataApp_PNDT, edtMaTram.getText().toString());
        File pathDuLieu = new File(pathTramMoi, "DuLieu");
        //SPC.ReadListAutoCompleteTextView("ThietKeTram.txt",pathDuLieu,listAutoCompleteTextView);
//        SPC.ReadListAutoCompleteTextView_Json("Data.xml",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeTram);
        File pathFileData = new File(pathDuLieu, "Data.xml");
        XML_Tram xml_tram1 = SPC.readXml(pathFileData);
        SPC.ReadList_Json(xml_tram1.getThietKeTram(), listAutoCompleteTextView, SPC.ThietKeTram);
        if (edtViTriDat.getText().toString().trim().equals("Dưới đất"))
            checkbox_duoidat.setChecked(true);
        else checkbox_trenmai.setChecked(true);

        /**
         * Sự kiện
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkbox_trenmai.isChecked()) edtViTriDat.setText("Trên mái");
                else edtViTriDat.setText("Dưới đất");
            }
        });
        btnLuuThongSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                for (AutoCompleteTextView edt : listAutoCompleteTextView) {
                    if (edt.getText().toString().trim().equals("")) {
                        check = false;
                        Toast.makeText(Activity_DanhSach_Cot.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }
                if (check) {
                    File pathTramMoi = new File(SPC.pathDataApp_PNDT, edtMaTram.getText().toString());
                    File pathDuLieu = new File(pathTramMoi, "DuLieu");
                    if (pathDuLieu.exists()) {
//                            try {
//                                SPC.SaveListAutoCompleteTextView_json("ThietKeTram",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeTram);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                        try {
                            XML_Tram xml_tram = new XML_Tram();
                            JSONObject obj = new JSONObject();
                            for (int i = 0; i < listAutoCompleteTextView.size(); i++) {
                                obj.put(SPC.ThietKeTram.get(i), listAutoCompleteTextView.get(i).getText().toString());
                            }
                            xml_tram.setThietKeTram(obj.toString());
                            SPC.saveToXml(xml_tram, "Data", pathDuLieu);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (TransformerException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(Activity_DanhSach_Cot.this, "Đã tạo trạm " + pathTramMoi.getName(), Toast.LENGTH_SHORT).show();
                        dialogthongso.dismiss();
                    }
                }
            }
        });

    }


    private void DialogThietKeNhaTram() throws JSONException, IOException, SAXException, ParserConfigurationException {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_nha_dat_tram);
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
        /**
         * Ánh xạ
         */
        AutoCompleteTextView edtTenCongTrinh = dialogthongso.findViewById(R.id.edtTenCongTrinh);
        AutoCompleteTextView edtSoTang = dialogthongso.findViewById(R.id.edtSoTang);
        AutoCompleteTextView edtChieuCaoCongTrinh = dialogthongso.findViewById(R.id.edtChieuCaoCongTrinh);
        AutoCompleteTextView edtDoDay = dialogthongso.findViewById(R.id.edtDoDay);
        AutoCompleteTextView edtDoRong = dialogthongso.findViewById(R.id.edtDoRong);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);


        ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenCongTrinh, edtSoTang, edtChieuCaoCongTrinh, edtDoDay, edtDoRong));
        File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
        File pathDuLieu = new File(pathTramMoi, "DuLieu");
//        SPC.ReadListAutoCompleteTextView_Json("ThietKeNhaTram.txt",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeNhaDatTram);
        File pathFileData = new File(pathDuLieu, "Data.xml");
        XML_Tram xml_tram1 = SPC.readXml(pathFileData);
        System.out.println(xml_tram1.getThietKeNhaTram());
        if (xml_tram1.getThietKeNhaTram() != "" && xml_tram1.getThietKeNhaTram() != null) {
            SPC.ReadList_Json(xml_tram1.getThietKeNhaTram(), listAutoCompleteTextView, SPC.ThietKeNhaDatTram);
            setUpTextViewNhaDatTram(xml_tram1, tvWidth, tvHeight);
        }

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AutoCompleteTextView edt : listAutoCompleteTextView) {
                    if (edt.getText().toString().trim().equals("")) {
                        Toast.makeText(Activity_DanhSach_Cot.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
//                        File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
//                        File pathDuLieu = new File(pathTramMoi, "DuLieu");
//                        if(pathDuLieu.exists())
//                        {
//                            try {
//                                SPC.SaveListAutoCompleteTextView_json("ThietKeNhaTram",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeNhaDatTram);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            dialogthongso.dismiss();
//                        }
                        File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
                        File pathDuLieu = new File(pathTramMoi, "DuLieu");
                        if (pathDuLieu.exists()) {
                            try {
                                JSONObject obj = new JSONObject();
                                for (int i = 0; i < listAutoCompleteTextView.size(); i++) {
                                    obj.put(SPC.ThietKeNhaDatTram.get(i), listAutoCompleteTextView.get(i).getText().toString());
                                }
                                xml_tram1.setThietKeNhaTram(obj.toString());
                                SPC.saveToXml(xml_tram1, "Data", pathDuLieu);
                                setUpTextViewNhaDatTram(xml_tram1, tvWidth, tvHeight);
                                dialogthongso.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParserConfigurationException e) {
                                e.printStackTrace();
                            } catch (SAXException e) {
                                e.printStackTrace();
                            } catch (TransformerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
        });

    }

    ;

    private void setUpTextViewNhaDatTram(XML_Tram xml_tram, TextView tvWidth, TextView tvHeight) throws JSONException {
        String nhaDatTram = xml_tram.getThietKeNhaTram();
        if (!xml_tram.getThietKeNhaTram().equals("")) {
            JSONObject jsonObject = new JSONObject(nhaDatTram);
            if (!nhaDatTram.equals("")) {
                String width = jsonObject.getString("ChieuDai");
                String height = jsonObject.getString("ChieuRong");
                tvWidth.setText(width);
                tvHeight.setText(height);
            }
        }

    }


    private void DialogThemCot(int gravity, String title) {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_themcot);
        Window window = dialogthongso.getWindow();
        if (window == null) {
            return;
        }
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
        AutoCompleteTextView edtDanhSachTramGoc = dialogthongso.findViewById(R.id.edtDanhSachTramGoc);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);
        Button btnChonTramGoc = dialogthongso.findViewById(R.id.btnChonTramGoc);
        RadioButton checkbox_trenmai = dialogthongso.findViewById(R.id.checkbox_trenmai);
        RadioButton checkbox_duoidat = dialogthongso.findViewById(R.id.checkbox_duoidat);
        RadioGroup radioGroup = (RadioGroup) dialogthongso.findViewById(R.id.radioGroup);

        /**
         * Sự kiện
         */
        btnChonTramGoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBTS_CheckBox(edtDanhSachTramGoc);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkbox_trenmai.isChecked()) edtSoChan.setText("3");
                else edtSoChan.setText("4");
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkEmpty = true;
                ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenCot, edtChieucaoCot, edtSoChan, edtKichThuocThanCot, edtChieuX, edtChieuY, edtDanhSachTramGoc));
                for (AutoCompleteTextView edt : listAutoCompleteTextView) {
                    if (edt.getText().toString().trim().equals("")) {
                        checkEmpty = false;
                        Toast.makeText(Activity_DanhSach_Cot.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
//                    else {
//                        File pathDuLieu = new File(pathDanhSachCot, edtTenCot.getText().toString());
//
//                        if (!editText.getText().toString().equals("")) {
//                            String[] lst_TramGoc = editText.getText().toString().split("; ");
//                            for (String bts : lst_TramGoc) {
//                                File pathDuLieuDTS = new File(pathDuLieu, bts);
//                                SPC.TaoThuMuc(pathDuLieuDTS);
//                            }
//                        }
//
//                        SPC.TaoThuMuc(pathDuLieu);
//                        if (pathDuLieu.exists()) {
//                            try {
//                                SPC.SaveListAutoCompleteTextView_json("ThietKeCot", pathDuLieu, listAutoCompleteTextView, SPC.ThietKeCot);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            SettupListView();
//                            Toast.makeText(Activity_DanhSach_Cot.this, "Đã tạo cột " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();
//                            dialogthongso.dismiss();
//                        }
//                    }
                }
                File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
                File pathDuLieu = new File(pathTramMoi, "DuLieu");
                File pathData = new File(pathDuLieu, "Data.xml");
                if (checkEmpty) {
                    try {
                        XML_Tram xml_tram = SPC.readXml(pathData);
                        List<XML_Cot> listCot = new ArrayList<>();
                        if (xml_tram.getListCot() != null) {
                            listCot = xml_tram.getListCot();
                        }
                        XML_Cot xml_cot = new XML_Cot();
                        JSONObject obj = new JSONObject();
                        for (int i = 0; i < listAutoCompleteTextView.size(); i++) {
                            obj.put(SPC.ThietKeCot.get(i), listAutoCompleteTextView.get(i).getText().toString());
                        }

                        xml_cot.setThietKeCot(obj.toString());
                        String[] arrListBTS = edtDanhSachTramGoc.getText().toString().trim().split(";");
                        List<XML_BTS> listXMLBts = new ArrayList<>();
                        if (arrListBTS.length > 0) {
                            for (String item : arrListBTS) {
                                XML_BTS xml_bts = new XML_BTS();
                                xml_bts.setTenBTS(item);
                                listXMLBts.add(xml_bts);
                            }
                        }
                        xml_cot.setListBTS_inCot(listXMLBts);
                        listCot.add(xml_cot);
                        xml_tram.setListCot(listCot);
                        SPC.writeXml(pathData, xml_tram);
                        System.out.println(xml_tram.toString());
                        Toast.makeText(Activity_DanhSach_Cot.this, "Thêm cột thành công!", Toast.LENGTH_SHORT).show();
                        SettupListView();
                        dialogthongso.dismiss();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    ;

    private void showMenu() {
        Intent intent = new Intent(Activity_DanhSach_Cot.this, ActivityMenu.class);
        intent.putExtra("MaTram", MaTram);
        intent.putExtra("DiaDiem", tvViTri.getText().toString());
        intent.putExtra("ToaDo", tvToaDo.getText().toString());
        startActivity(intent);
        overridePendingTransition(R.anim.zoom, R.anim.zoomin);
    }

    private void DialogChinhSuaCot(int vt) throws JSONException, IOException, SAXException, ParserConfigurationException {
        final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_themcot);
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
        tvTitle.setText("Chỉnh sửa cột");

        AutoCompleteTextView edtTenCot = dialogthongso.findViewById(R.id.edtTenThanhPhan);
        AutoCompleteTextView edtChieucaoCot = dialogthongso.findViewById(R.id.edtChieuCaoCongTrinh);
        AutoCompleteTextView edtSoChan = dialogthongso.findViewById(R.id.edtSoChan);
        AutoCompleteTextView edtKichThuocThanCot = dialogthongso.findViewById(R.id.edtKichThuocThanCot);
        AutoCompleteTextView edtChieuX = dialogthongso.findViewById(R.id.edtChieuX);
        AutoCompleteTextView edtChieuY = dialogthongso.findViewById(R.id.edtChieuY);
        AutoCompleteTextView edtDanhSachTramGoc = dialogthongso.findViewById(R.id.edtDanhSachTramGoc);
        Button btnLuu = dialogthongso.findViewById(R.id.btnLuu);
        btnLuu.setText("Lưu thiết kế");
        RadioButton checkbox_trenmai = dialogthongso.findViewById(R.id.checkbox_trenmai);
        RadioButton checkbox_duoidat = dialogthongso.findViewById(R.id.checkbox_duoidat);
        RadioGroup radioGroup = (RadioGroup) dialogthongso.findViewById(R.id.radioGroup);
        Button btnChonTramGoc = dialogthongso.findViewById(R.id.btnChonTramGoc);

        /**
         * Sự kiện
         */
        btnChonTramGoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBTS_CheckBox(edtDanhSachTramGoc);
            }
        });
        /**
         * NHẬN BIẾN
         */
        File pathTram = new File(SPC.pathDataApp_PNDT, MaTram);
        File pathDuLieu = new File(pathTram, "DuLieu");
        File pathDataXml = new File(pathDuLieu, "Data.xml");
        XML_Tram xml_tram = SPC.readXml(pathDataXml);
        ArrayList<AutoCompleteTextView> listEditText = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenCot, edtChieucaoCot, edtSoChan, edtKichThuocThanCot, edtChieuX, edtChieuY, edtDanhSachTramGoc));
        SPC.ReadList_Json(xml_tram.getListCot().get(vt).getThietKeCot(), listEditText, SPC.ThietKeCot);
        if (edtSoChan.equals("3")) checkbox_duoidat.setChecked(true);
        else checkbox_trenmai.setChecked(true);
        /**
         * Sự kiện
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkbox_trenmai.isChecked()) edtSoChan.setText("3");
                else edtSoChan.setText("4");
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = true;
                for (EditText edt : listEditText) {
                    if (edt.getText().toString().trim().equals("")) {
                        check = false;
                        Toast.makeText(Activity_DanhSach_Cot.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    }
//                    else {
//                        if (pathDuLieu.exists()) {
//                            try {
//
//                                SPC.SaveListAutoCompleteTextView_json("ThietKeCot", pathDuLieu, listEditText, SPC.ThietKeCot);
//                                if (!edtDanhSachTramGoc.getText().toString().equals("")) {
//                                    String[] lst_TramGoc = edtDanhSachTramGoc.getText().toString().split("; ");
//                                    //KIỂM TRA CÁC BTS KHÔNG TRÙNG VỚI DÁNH SÁCH BTS VỪA CHỈNH SỬA
//                                    File[] lst_BTS = pathDuLieu.listFiles();
//                                    ArrayList<String> ar_BTS = new ArrayList<String>();
//                                    ar_BTS.addAll(Arrays.asList(lst_TramGoc));
//                                    for (File f_BTS : lst_BTS) {
//                                        if (f_BTS.isDirectory()) {
//                                            if (!ar_BTS.contains(f_BTS.getName()))
//                                                FileUtils.deleteDirectory(f_BTS);
//                                        }
//                                    }
//                                    //TẠO LẠI CÁC BTS, NẾU CÓ RỒI SẼ KHÔNG THÊM
//                                    for (String bts : lst_TramGoc) {
//                                        File pathDuLieuDTS = new File(pathDuLieu, bts);
//                                        SPC.TaoThuMuc(pathDuLieuDTS);
//                                    }
//                                }
//                            } catch (JSONException | IOException e) {
//                                e.printStackTrace();
//                            }
//                            SettupListView();
//                            Toast.makeText(Activity_DanhSach_Cot.this, "Đã Lưu thiết kế " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();
//                            dialogthongso.dismiss();
//                        }
//                    }

                }
                if (check) {
                    List<XML_Cot> listCot = new ArrayList<>();
                    if (xml_tram.getListCot() != null) {
                        listCot = xml_tram.getListCot();
                        listCot.remove(vt);
                    }
                    XML_Cot xml_cot = new XML_Cot();
                    JSONObject obj = new JSONObject();
                    for (int i = 0; i < listEditText.size(); i++) {
                        try {
                            obj.put(SPC.ThietKeCot.get(i), listEditText.get(i).getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            break;
                        }

                    }
                    xml_cot.setThietKeCot(obj.toString());
                    String[] arrListBTS = edtDanhSachTramGoc.getText().toString().trim().split(";");
                    List<XML_BTS> listXMLBts = new ArrayList<>();
                    if (arrListBTS.length > 0) {
                        for (String item : arrListBTS) {
                            XML_BTS xml_bts = new XML_BTS();
                            xml_bts.setTenBTS(item);
                            listXMLBts.add(xml_bts);
                        }
                    }
                    xml_cot.setListBTS_inCot(listXMLBts);
                    listCot.add(vt, xml_cot);
                    xml_tram.setListCot(listCot);
                    try {
                        SPC.writeXml(pathDataXml, xml_tram);
                        System.out.println(xml_tram.toString());
                        Toast.makeText(Activity_DanhSach_Cot.this, "Chỉnh sửa cột thành công!", Toast.LENGTH_SHORT).show();
                        SettupListView();
                        dialogthongso.dismiss();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    ;
    /**
     * TẠO MENU CHECK BOX CHO DANH SACH TRAM GOC
     */
    List<DoiTuong_BTS_CheckList> list_BTS = new ArrayList<>();
    Adapter_DoiTuong_BTS_CheckList adapter_doiTuong_bts;
    EditText editText;

    public Boolean Kiemtra(String s, String[] list) {
        Boolean b = false;
        for (String string : list) {
            if (string.replace("\n", "").equalsIgnoreCase(s)) {
                b = true;
                break;
            }
        }
        return b;
    }

    private void SettupListView_dialog(EditText editText) {
        String[] strings = editText.getText().toString().split("; ");
//        File fileTram = new File(SPC.pathDataApp_PNDT, MaTram + "/DuLieu/DanhSachBTS");
        list_BTS.clear();
        list_BTS = new ArrayList<>();
//        File[] files = fileTram.listFiles();
//        try {
//            for (File fileThietKe : files) {
//                if (fileThietKe.exists()) {
//                    String thietke = SPC.readText(fileThietKe);
//                    JSONObject jsonObject = new JSONObject(thietke);
//                    if (!thietke.equals("")) {
//                        String TenTramGoc = jsonObject.getString("TenTramGoc");
//                        String BangTanHoatDong = jsonObject.getString("BangTanHoatDong");
//                        String ChungLoaiThietBi = jsonObject.getString("ChungLoaiThietBi");
//                        String MangSuDung = jsonObject.getString("MangSuDung");
//                        list_BTS.add(new DoiTuong_BTS_CheckList(TenTramGoc, ChungLoaiThietBi, BangTanHoatDong, MangSuDung, Kiemtra(TenTramGoc, strings)));
//                    }
//                }
//            }
//        } catch (Exception ignored) {
//        }
        File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
        File pathDuLieu = new File(pathTramMoi, "DuLieu");
        File pathData = new File(pathDuLieu, "Data.xml");
        try {
            XML_Tram xml_tram = SPC.readXml(pathData);
            if (xml_tram.getListBTS() != null) {
                if (xml_tram.getListBTS().size() > 0) {
                    for (String itemBts : xml_tram.getListBTS()) {
                        String thietke = itemBts;
                        JSONObject jsonObject = new JSONObject(thietke);
                        if (!thietke.equals("")) {
                            String TenTramGoc = jsonObject.getString("TenTramGoc");
                            String BangTanHoatDong = jsonObject.getString("BangTanHoatDong");
                            String ChungLoaiThietBi = jsonObject.getString("ChungLoaiThietBi");
                            String MangSuDung = jsonObject.getString("MangSuDung");
                            list_BTS.add(new DoiTuong_BTS_CheckList(TenTramGoc, ChungLoaiThietBi, BangTanHoatDong, MangSuDung, Kiemtra(TenTramGoc, strings)));
                        }
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
        //Thay đổi thử dòng 30
        /**HIỂN THỊ RA MÀN HÌNH*/
        adapter_doiTuong_bts = new Adapter_DoiTuong_BTS_CheckList(list_BTS, Activity_DanhSach_Cot.this, R.layout.item_checked_bts);
        listview_bts.setAdapter(adapter_doiTuong_bts);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = listview_bts.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            DoiTuong_BTS_CheckList hienTrang = list_BTS.get(pos);
            hienTrang.setActive(isChecked);
            editText.setText(printSelectedItems());
        }
    }

    public StringBuilder printSelectedItems() {
        StringBuilder sb = new StringBuilder();
        ArrayList<DoiTuong_BTS_CheckList> countryList = adapter_doiTuong_bts.arraylist;
        for (int i = 0; i < countryList.size(); i++) {
            DoiTuong_BTS_CheckList country = countryList.get(i);
            if (country.isActive()) {
                String s = country.getTenTramGoc();
                if (sb.length() == 0) sb = sb.append("" + s);
                else sb = sb.append("; " + s);
            }
        }
        return sb;
    }

    public void DialogBTS_CheckBox(final AutoCompleteTextView tv) {
        try {
            final Dialog dialog = new Dialog(Activity_DanhSach_Cot.this, R.style.PauseDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_select_bts);
            Window window = dialog.getWindow();
            if (window == null) {
                return;
            }
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowArr = window.getAttributes();
            windowArr.gravity = Gravity.CENTER;
            window.setAttributes(windowArr);
            dialog.show();

            listview_bts = (ListView) dialog.findViewById(R.id.listHienTrang);
            Button button = (Button) dialog.findViewById(R.id.button);
            editText = (EditText) dialog.findViewById(R.id.edittext);
            editText.setText(tv.getText());
            SettupListView_dialog(editText);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* TuHocDeXuat(editText,LoaiCauKien,listDeXuat);
                    vitriDeXuat =1;

                    tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriDeXuat) + "/" + String.valueOf(listDeXuat.size()));
                    for(int i= 0 ;i<=10;i++)
                    {
                        listDeXuat.add("");
                    }*/
                    tv.setText(editText.getText());
                    dialog.dismiss();

                }
            });

            SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
            searchViewCT.setIconifiedByDefault(false);
            searchViewCT.setQueryHint("Tìm kiếm");
            searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    adapter_doiTuong_bts.filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter_doiTuong_bts.filter(newText);
                    return false;
                }
            });
        } catch (ArithmeticException e) {
            Toast.makeText(Activity_DanhSach_Cot.this, "Đề xuất của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    //endregion
}
