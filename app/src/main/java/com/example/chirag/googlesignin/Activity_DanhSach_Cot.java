package com.example.chirag.googlesignin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
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
import java.util.Collections;
import java.util.List;

public class Activity_DanhSach_Cot extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener
{
    ListView listview,listview_bts;
    TextView title,tvToaDo,tvViTri,tvMaTram,tvViTriDat,tvNgaySua,tvSoAnh,tvTramGoc;

    ImageButton btnBack,btnMenu,btnThemCot;
    LinearLayout btnAnhChup,btnThietKe,btnNhaDatTram,btnDanhSachBTS,btnCTCN;
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
            btnCTCN = findViewById(R.id.btnCTCN);
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
            tvTramGoc = findViewById(R.id.tvTramGoc);
        }
    //region Settup ListView
        private String DemSoBTS(){
            File fileTram = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/DanhSachBTS");
            int sotram = 0;
            File[] listCot = fileTram.listFiles();
            sotram = listCot.length;
            return String.valueOf(sotram);
        }
        private void SettupListView() {
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
                tvTramGoc.setText(DemSoBTS());
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
                Intent intent= new Intent(Activity_DanhSach_Cot.this,Activity_ChiTiet_Anten.class);
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
        btnDanhSachBTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent= new Intent(Activity_DanhSach_Cot.this,Activity_DanhSach_BTS.class);
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
        btnCTCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemCongtrinh();
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
            Button btnSua = (Button) dialogthongso.findViewById(R.id.btnmenudoiten);btnSua.setText("Chỉnh sửa");
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
                    try {
                        DialogChinhSuaCot(vt);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialogthongso.dismiss();

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
        private void DialogThemCongtrinh() {
            final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this,R.style.PauseDialog);
            dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogthongso.setContentView(R.layout.dialog_them_ctcn);
            Window window= dialogthongso.getWindow();
            if (window==null){return;}
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

            File pathTramMoi = new File(SPC.pathDataApp_PNDT, MaTram);
            File filecongtrinh = new File(pathTramMoi, "DuLieu");
            ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenCongTrinh,edtChieuCaoCongTrinh,edtKhoangCach,edtSoTang,edtGocPhuongVi,edtDoDay,edtDoRong));
            try {
                SPC.ReadListAutoCompleteTextView_Json("ThietKeCTCN.txt",filecongtrinh,listAutoCompleteTextView,SPC.ThietKeCongTrinh);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            btnLuu.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {

                    for( AutoCompleteTextView edt:listAutoCompleteTextView){
                        if(edt.getText().toString().trim().equals("")){
                            Toast.makeText(Activity_DanhSach_Cot.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else
                        {

                            if(filecongtrinh.exists())
                            {
                                try {
                                    SPC.SaveListAutoCompleteTextView_json("ThietKeCTCN",filecongtrinh,listAutoCompleteTextView,SPC.ThietKeCongTrinh);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                dialogthongso.dismiss();
                            }
                        }

                    }
                }
            });

        }

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
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    if(checkbox_trenmai.isChecked())  edtSoChan.setText("3");
                    else edtSoChan.setText("4");
                }
            });
            btnLuu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenCot,edtChieucaoCot,edtSoChan,edtKichThuocThanCot,edtChieuX,edtChieuY,edtDanhSachTramGoc));
                    for( AutoCompleteTextView edt:listAutoCompleteTextView){
                        if(edt.getText().toString().trim().equals(""))
                        {
                            Toast.makeText(Activity_DanhSach_Cot.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else
                        {
                            File pathDuLieu = new File(pathDanhSachCot, edtTenCot.getText().toString());

                            if (!editText.getText().toString().equals("")){
                                String[] lst_TramGoc = editText.getText().toString().split("; ");
                                for (String bts :lst_TramGoc){
                                    File pathDuLieuDTS = new File(pathDuLieu, bts);
                                    SPC.TaoThuMuc(pathDuLieuDTS);
                                }
                            }

                            SPC.TaoThuMuc(pathDuLieu);
                            if(pathDuLieu.exists())
                            {
                                try {
                                    SPC.SaveListAutoCompleteTextView_json("ThietKeCot",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeCot);
                                } catch (JSONException e)
                                {
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
        private void DialogChinhSuaCot(int vt) throws  JSONException {
            final Dialog dialogthongso = new Dialog(Activity_DanhSach_Cot.this,R.style.PauseDialog);
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
                public void onClick(View v)
                {
                    DialogBTS_CheckBox(edtDanhSachTramGoc);
                }
            });
            /**
             * NHẬN BIẾN
             */
            File pathDuLieu = new File(pathDanhSachCot, list_Cot.get(vt).getTenCot());
            ArrayList<AutoCompleteTextView> listEditText = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtTenCot,edtChieucaoCot,edtSoChan,edtKichThuocThanCot,edtChieuX,edtChieuY,edtDanhSachTramGoc));
            SPC.ReadListAutoCompleteTextView_Json("ThietKeCot.txt",pathDuLieu,listEditText,SPC.ThietKeCot);
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
                            Toast.makeText(Activity_DanhSach_Cot.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else
                        {
                            if(pathDuLieu.exists())
                            {
                                try {

                                    SPC.SaveListAutoCompleteTextView_json("ThietKeCot",pathDuLieu,listEditText,SPC.ThietKeCot);
                                    if (!edtDanhSachTramGoc.getText().toString().equals(""))
                                    {
                                        String[] lst_TramGoc = edtDanhSachTramGoc.getText().toString().split("; ");
                                        //KIỂM TRA CÁC BTS KHÔNG TRÙNG VỚI DÁNH SÁCH BTS VỪA CHỈNH SỬA
                                            File[] lst_BTS = pathDuLieu.listFiles();
                                            ArrayList<String> ar_BTS = new ArrayList<String>();
                                            ar_BTS.addAll(Arrays.asList(lst_TramGoc));
                                            for (File f_BTS:lst_BTS)
                                            {
                                                if(f_BTS.isDirectory())
                                                {
                                                    if (!ar_BTS.contains(f_BTS.getName())) FileUtils.deleteDirectory(f_BTS);
                                                }
                                            }
                                        //TẠO LẠI CÁC BTS, NẾU CÓ RỒI SẼ KHÔNG THÊM
                                            for (String bts :lst_TramGoc)
                                            {
                                                File pathDuLieuDTS = new File(pathDuLieu, bts);
                                                SPC.TaoThuMuc(pathDuLieuDTS);
                                            }
                                    }
                                } catch (JSONException | IOException e)
                                {
                                    e.printStackTrace();
                                }
                                SettupListView();
                                Toast.makeText(Activity_DanhSach_Cot.this, "Đã Lưu thiết kế " + pathDuLieu.getName(), Toast.LENGTH_SHORT).show();
                                dialogthongso.dismiss();
                            }
                        }

                    }
                }
            });
        };
    /**TẠO MENU CHECK BOX CHO DANH SACH TRAM GOC*/
        List<DoiTuong_BTS_CheckList> list_BTS = new ArrayList<>();
        Adapter_DoiTuong_BTS_CheckList adapter_doiTuong_bts;
        EditText editText;
        public Boolean Kiemtra(String s,String[] list)
        {
            Boolean b= false;
            for (String string :list)
            {
                if(string.replace("\n","").equalsIgnoreCase(s)) {b=true;break;}
            }
            return b;
        }
        private void SettupListView_dialog(EditText editText){
            String[] strings = editText.getText().toString().split("; ");
            File fileTram = new File(SPC.pathDataApp_PNDT,MaTram + "/DuLieu/DanhSachBTS");
            list_BTS.clear();
            list_BTS = new ArrayList<>();
            File[] files=fileTram.listFiles();
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
                            list_BTS.add(new DoiTuong_BTS_CheckList(TenTramGoc,ChungLoaiThietBi,BangTanHoatDong,MangSuDung,Kiemtra(TenTramGoc,strings)));
                        }
                    }
                }
            }
            catch (Exception ignored){}
            //Thay đổi thử dòng 30
            /**HIỂN THỊ RA MÀN HÌNH*/
            adapter_doiTuong_bts = new Adapter_DoiTuong_BTS_CheckList(list_BTS, Activity_DanhSach_Cot.this,R.layout.item_checked_bts);
            listview_bts.setAdapter(adapter_doiTuong_bts);
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int pos = listview_bts.getPositionForView(buttonView);
            if (pos!=ListView.INVALID_POSITION) {
                DoiTuong_BTS_CheckList hienTrang = list_BTS.get(pos);
                hienTrang.setActive(isChecked);
                editText.setText(printSelectedItems());
            }
        }

        public StringBuilder printSelectedItems()  {
            StringBuilder sb= new StringBuilder();
            ArrayList<DoiTuong_BTS_CheckList> countryList = adapter_doiTuong_bts.arraylist;
            for (int i=0 ; i<countryList.size();i++)
            {
                DoiTuong_BTS_CheckList country = countryList.get(i);
                if (country.isActive())
                {
                    String s= country.getTenTramGoc();
                    if (sb.length()==0) sb = sb.append(""+s);
                    else sb = sb.append("; "+s);
                }
            }
            return sb;
        }
        public void DialogBTS_CheckBox(final AutoCompleteTextView tv) {
            try
            {
                final Dialog dialog = new Dialog(Activity_DanhSach_Cot.this,R.style.PauseDialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_select_bts);
                Window window= dialog.getWindow();
                if (window==null){return;}
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
            }
            catch (ArithmeticException e)
            {Toast.makeText(Activity_DanhSach_Cot.this, "Đề xuất của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
        }

    //endregion
}
