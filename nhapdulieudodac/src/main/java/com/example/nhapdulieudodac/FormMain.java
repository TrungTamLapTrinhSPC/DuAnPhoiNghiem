package com.example.nhapdulieudodac;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FormMain extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener{
    //region KHỞI TẠO BIẾN
    Boolean kiemtrabanggoc;
    Dialog dialog,dialogPhuKien,dialogCauKien,dialogBulong;
    ViewGroup viewGroup,viewGroupBulong,viewGroupPhuKien,viewGroupCauKien,viewGroupMain;
    /**
     * CHỤP ẢNH
     */
    ImageButton btnChupHienTrang,btnChupHienTrangBulong,btnChupHienTrangPhuKien,btnChupHienTrangCauKien,btnChupHienTrangMocCo,btnChupHienTrangKheHo,
                btnChupCuongDo,btnChupLucCang,btnChupLucSiet,btnChupThanhGiang,btnChupThanhCanh,
                btnLocHienTrang,btnLocHienTrangBulong,btnLocHienTrangMocCo,btnLocHienTrangKheHo,btnLocHienTrangPhuKien,btnLocHienTrangCauKien;

    /**
     * IMAGEBUTTON
     */
    ImageButton btnThoat,btnThoatPhuKien,btnBackMong,
                btnSearchPhuKien,btnSearch,btnSearchBulong,btnSearchCauKien,btnSearchMocCo,btnSearchKheHo,
                btnSearchDX,btnSearchDXBulong,btnSearchDXMocCo,btnSearchDXKheHo,btnSearchDXPhuKien,btnSearchDXCauKien;
    ImageView imgMatBang,imgGocXoay;
    //Dialog_TTC
    ImageButton btnBack,btnBackBulong,btnBackCauKien,
            btnNext,btnNextBulong,
            btnBackHienTrang,btnBackHienTrangPhuKien,btnBackHienTrangBulong,btnBackHienTrangCauKien,btnBackHienTrangMocCo,btnBackHienTrangKheHo,
            btnNextHienTrang,btnNextHienTrangPhuKien,btnNextHienTrangCauKien,btnNextHienTrangKheHo,btnNextHienTrangBulong,btnNextHienTrangMocCo,
            btnBackDX,btnBackDXBulong,btnBackDXCauKien,btnBackDXMocCo,btnBackDXKheHo,btnBackDXPhuKien,
            btnNextDX,btnNextDXBulong,btnNextDXPhuKien,btnNextDXCauKien,btnNextDXMocCo,btnNextDXKheHo;
    /**
     * BUTTON
     */
    Button btnChonMong,btnThongTinChung,btnCopy,btnCopyKheHo,btnCopyPhuKien,btnPaste,btnPasteMocCo,btnPasteKheHo,btnPastePhuKien,btnHuongBac;
    //Dialog_Main
    Button btnDuLieuDoMong,btnDulieuDam,btnMstower,btnDoDienTro,btnChonLoaiMong,btnToaDoMong,btnCaoDoDayCo,btnLuuGocMoc;
    Button btnCauTrucCot,btnPhuKienCot,btnDoLucSietKhoaCap,btnDoLucCang,btnThietBiTrenCot;
    Button btnChanCot,btnDinhCot,btnVong0,btnVong1,btnVong2,btnVong3,btnKetQuaDoNghieng;
    ScrollView layoutTable2,layoutTable3,layoutTable8,layoutTable9,layoutTable6,layoutTable10;
    //Dialog
    Button btnLuuPhuKien,btnLuu,btnLuuCauKien,btnLuuMocCo,btnLuuKheHo,btnLuuMong,btnLuuCuongDo,btnLuuLucSiet;
    //Dialog_TTC
    Button btnLuuTTC;
    //Dialog_Cấu kiện tiếp địa
    Button btnLuuBangCanh,btnLuuBangGoc,btnLuuLucCang,btnLuuBulong;

    /**
     * TEXTVIEW
     */
    //Dialog
    TextView tvTenPhuKien,tvTenMong,edtDoDayThanhCanh,tvTenCauKien,tvGiaTriTrungBinh,tvDiaDiemTTC,
             tvViTriHienTrang,tvViTriHienTrangBulong,tvViTriHienTrangPhuKien,tvViTriHienTrangCauKien,tvViTriHienTrangMocCo,tvViTriHienTrangKheHo,
             tvViTriDeXuat,tvViTriDeXuatBulong,tvViTriDeXuatPhuKien,tvViTriDeXuatCauKien,tvViTriDeXuatMocCo,tvViTriDeXuatKheHo;
    //Dialog_TTC
    TextView tvSoChanCotTTC,tvViTriDatTTC,tvSoMongTTC,tvSoTangDayTTC,tvChieuCaoDotTTC,tvDoCaoTTC,tbBiaLoaiCot,tvGiaChongXoayTTC,edtGocBan,edtLoaiMay,edtDanhGia,edtDanhGiaDoNghieng;
    //Dialog_Đo nghiêng
    TextView X1,X2,X3,Y1,Y2,Y3,H1,H2,H3,Dx1,Dx2,Dx3,Dy1,Dy2,Dy3,e1,e2,e3,Dl1,Dl2,Dl3,KL1,Kl2,KL3,tvKichThuocCotTTC,edtLoaiCotTinhNghieng,tbBiaMacBeTong;
    TextView TV_M0M1,TV_M0M2,TV_M0M3,TV_M0M4,TV_M0M5,TV_M0M6,TV_M0M7;
    TextView TV_M0M8,TV_M0M9,TV_M0M10,TV_M0M11,TV_M0M12,TV_M0M13,TV_M0M14;
    /**
     * EDITTEXXT
     */
    //Dialog
    AutoCompleteTextView edtKichThuoc,edtKichThuocBulong,edtKichThuocCauKien,edtKichThuocPhuKien,edtKichThuocKheHo,edtKichThuocMocCo,
            edtHienTrang,edtHienTrangBulong,edtHienTrangCauKien,edtHienTrangMocCo,edtHienTrangKheHo,edtHienTrangPhuKien,
            edtDeXuat,edtDeXuatBulong,edtDeXuatKheHo,edtDeXuatCauKien,edtDeXuatMocCo,edtDeXuatPhuKien,
            edtThanhGiang,edtThanhCanh,edtThanhCanhKheHo;
    EditText edtGiaTriDo,edtLanDo;
    //Dialog_TTC
    EditText tvMaTramTTC,tvSoDotTTC;
    //Dialog_Lực Siết
    EditText GT_M0M1_Tren,GT_M0M2_Tren,GT_M0M3_Tren,GT_M0M4_Tren,GT_M0M5_Tren,GT_M0M6_Tren,GT_M0M7_Tren;
    EditText GT_M0M1_Duoi,GT_M0M2_Duoi,GT_M0M3_Duoi,GT_M0M4_Duoi,GT_M0M5_Duoi,GT_M0M6_Duoi,GT_M0M7_Duoi;
    EditText GT_M0M8_Duoi,GT_M0M9_Duoi,GT_M0M10_Duoi,GT_M0M11_Duoi,GT_M0M12_Duoi,GT_M0M13_Duoi,GT_M0M14_Duoi;

    EditText GT_M0M1,GT_M0M2,GT_M0M3,GT_M0M4,GT_M0M5,GT_M0M6,GT_M0M7,GT_M0M8,GT_M0M9,GT_M0M10,GT_M0M11,GT_M0M12,GT_M0M13,GT_M0M14;
    //Dialog_Anten
    AutoCompleteTextView edtCaoTrinh,edtSoLuong,edtLoaiAnten,edtKichThuocAnten,edtTrongLuong;
    //Dialog_đo nghiêng
    EditText GT_X,GT_Y,GT_Z;
    EditText edtGocXoay,edtCaoDoChanCot;
    /**
     * STRING
     */
    String MaTram, LocalCity, Logiest, Latitude, ViTriDat,KichThuocMong,DeXuatMong,KichThuocCot,DanhGiaHienTrang,GiaChongXoay,ChieuCaoDot,LoaiMatBang = "L1",LoaiMong1 ="L1",LoaiMong2 ="L1",LoaiMong3 ="L1",LoaiMong4 ="L1",LoaiDot="L1";
    String textCopy="",textCopyMocCo="",textCopyKheHo="",textPaste="",MacBeTong,
            textDX,textHT,textDXPhuKien,textHTPhuKien,textDXBulong,textHTBulong,textDXCauKien,textHTCauKien,textDXMocCo,textHTMocCo,textDXKheHo,textHTKheHo;
    String DeXuatBeTong,DeXuatThep,HienTrangBeTong,HienTrangThep;
    String[][] data_Table2;
    String[][] data_Table3;
    String[][] data_Table4;
    String[][] data_Table5;
    String[][] data_Table6;
    String[][] data_Table7;
    String[][] data_Table8;
    String[][] data_Table9;
    String[][] data_Table10;
    String[][] data_Table11;
    String[][] data_Table12_ChanCot;
    String[][] data_Table12_HuongBac;
    String[][] data_Table12_DinhCot;
    String[][] data_Table12_V0;
    String[][] data_Table12_V1;
    String[][] data_Table12_V2;
    String[][] data_Table12_V3;
    /**
     * LinearLayout
     */
    //Main
    LinearLayout btnMoBaoCao,btnKichThuocPhuKien,btnThietBiMatDat,btnDuLieuBulong,btnHienCaoDoChanCot,btnHienGocXoay,btnHienLucSiet,btnHienLucCang,btnHienBangMong,btnHienBangKheHo,btnHienBangThanCot,btnHienBangCuongDo,btnHienBangLucSiet,btnHienBangLucCang,btnMoThongTinChung,btnMoDoMstower,btnMoMatBang,btnMoDoMong,btnMoTiepDia,btnMoThanCot,btnMoDoNghieng,btnHienBangKetQua_Mstower,btnMoNoiNghiep,btnMoNgoaiNghiep;
    LinearLayout layoutGocXoay,layoutLucSiet,layoutLucCang,layoutMatBang,layoutDoMong,layoutTiepDia,layoutThanCot,layoutDoNghieng,layoutBangToaDoMong_Mstower;
    //Dialog
    LinearLayout layoutCaoDoChanCot,
                    lauoutKichThuoc,lauoutKichThuocBulong,lauoutKichThuocMocCo,lauoutKichThuocKheHo,lauoutCuongDo,lauoutBang,lauoutKichThuocPhuKien,lauoutKichThuocCauKien,lauoutBangCauKien,lauoutBangCauKienBulong,
                    btnlauoutKichThuoc,btnlauoutKichThuocPhuKien,btnlauoutKichThuocCauKien,btnlauoutKichThuocBulong,btnlauoutKichThuocMocCo,btnlauoutKichThuocKheHo,btnlauoutCuongDo,lauoutBangPhuKien,
                    btnlauoutBang,btnlauoutBangBulong,btnlauoutBangPhuKien,btnlauoutBangCauKien,btnlauoutBangKheHo,btnHienBangCanh,btnHienBangGoc,btnHienBangKetQua,layoutNoiNghiep,layoutNgoaiNghiep;
    //Dialog_Lực siết
    LinearLayout MongM1,MongM2,MongM3,MongM4,MongM5,MongM6,MongM7;
    LinearLayout MongM8,MongM9,MongM10,MongM11,MongM12,MongM13,MongM14;
    //Dialog_Loại mong
    LinearLayout layoutMong1,layoutMong2,layoutMong3,layoutMong4,layoutBangCanh,layoutBangGoc,layoutBangToaDoMong;
    /**
     * INTEGER
     */
    int SoMong,SoDot,SoTangDay,SoCot,lando=1,vitriMong,vitriDam,vitriHienTrang=1,vitriDeXuat=1,vitriHienTrangBulong=1,vitriDeXuatBulong=1,
            vitriHienTrangCauKien=1,vitriDeXuatCauKien=1,vitriHienTrangMocCo=1,vitriDeXuatMocCo=1,vitriHienTrangKheHo=1,vitriDeXuatKheHo=1,
            vitriHienTrangPhuKien=1,vitriDeXuatPhuKien=1,SoGaChongXoay,SoAnten,lengthArray;
    double CaoDoChanCot = 0;
    /**
     * TABLELAYOUT
     */
    TableLayout Table12_V3,Table12_V2,Table12_V1,Table12_V0,Table12_ChanCot,Table12_DinhCot,Table12_HuongBac,Table11,Table10,Table9,Table8,Table7,Table6,Table5,Table4,Table3,Table2,TableToaDoMong,TableToaDoMong_Mstower,TableCaoDoDayCo,TableMstower,TableCanhCanhCanh,TableCanhGocCanh,TableBiaTTC,TableCaoDoChanCot;
    /**
     * ARRAYLIST
     */
    String[] ArrayString = new String[1000];
    Integer[] tt = new Integer[100];
    String[] duongdananh= new String[1000];
    //HIENTRANG
    ArrayList<String> listHienTrangBeTong = new ArrayList<String>();
    ArrayList<String> listHienTrangThep = new ArrayList<String>();
    ArrayList<String> listDeXuatBeTong = new ArrayList<String>();
    ArrayList<String> listDeXuatThep = new ArrayList<String>();
    //Mong
    ArrayList<String> listMong = new ArrayList<String>();
    ArrayList<String> listHienTrang = new ArrayList<String>();
    ArrayList<String> listDeXuat = new ArrayList<String>();
    ArrayList<String> listHienTrang_copy = new ArrayList<String>();
    ArrayList<String> listDeXuat_copy = new ArrayList<String>();
    //CAUKIEN
    ArrayList<String> listCauKien = new ArrayList<String>();
    ArrayList<String> listHienTrangCauKien = new ArrayList<String>();
    ArrayList<String> listDeXuatCauKien = new ArrayList<String>();
    //CAUKIEN
    ArrayList<String> listBulong = new ArrayList<String>();
    ArrayList<String> listHienTrangBulong= new ArrayList<String>();
    ArrayList<String> listDeXuatBulong = new ArrayList<String>();

    //MocCo
    ArrayList<String> listMongMocCo = new ArrayList<String>();
    ArrayList<String> listHienTrangMocCo = new ArrayList<String>();
    ArrayList<String> listDeXuatMocCo = new ArrayList<String>();
    ArrayList<String> listHienTrang_copyMocCo = new ArrayList<String>();
    ArrayList<String> listDeXuat_copyMocCo = new ArrayList<String>();
    //KheHo
    ArrayList<String> listHienTrangKheHo = new ArrayList<String>();
    ArrayList<String> listDeXuatKheHo = new ArrayList<String>();
    ArrayList<String> listHienTrang_copyKheHo = new ArrayList<String>();
    ArrayList<String> listDeXuat_copyKheHo = new ArrayList<String>();
    //PhuKien
    ArrayList<String> listHienTrangPhuKien = new ArrayList<String>();
    ArrayList<String> listDeXuatPhuKien = new ArrayList<String>();
    ArrayList<String> listHienTrang_copyPhuKien = new ArrayList<String>();
    ArrayList<String> listDeXuat_copyPhuKien = new ArrayList<String>();

    //Dam
    ArrayList<String> listDam = new ArrayList<String>();
    //Bulong
    ArrayList<String> listbulong = new ArrayList<String>();
    //TiepDia
    ArrayList<String> listCauKienTiepDia = new ArrayList<String>();
    ArrayList<String> listCauKienTiepDiaTable = new ArrayList<String>();
    ArrayList<String> listLanDoDienTro= new ArrayList<String>();
    //ThanCot
    ArrayList<String> listThanCot = new ArrayList<String>();
    //PhuKienCot
    ArrayList<String> listPhuKienCot = new ArrayList<String>();
    ArrayList<String> listPhuKienCot2 = new ArrayList<String>();
    ArrayList<String> listPhuKienCotDauDuoi = new ArrayList<String>();
    ArrayList<String> listPhuKienCotDauTren = new ArrayList<String>();
    //LienKetCot
    ArrayList<String> listLienKetCot = new ArrayList<String>();
    //listTangDayco
    ArrayList<String> listTangDayCo = new ArrayList<String>();
    //Dialog_Cấu kiện tiếp địa
    ArrayList<String> ArraylistKT = new ArrayList<String>();
    //Lực siết khoá cáp
    ArrayList<EditText> listEdtLucSiet = new ArrayList<EditText>();
    ArrayList<EditText> listEdtLucCang = new ArrayList<EditText>();
    ArrayList<EditText> listEdtAnten = new ArrayList<EditText>();
    //DoNghieng
    ArrayList<String> listDoNghieng_V0 = new ArrayList<String>();
    ArrayList<String> listDoNghieng_V1 = new ArrayList<String>();
    ArrayList<String> listDoNghieng_V2 = new ArrayList<String>();
    ArrayList<String> listDoNghieng_V3 = new ArrayList<String>();

    ArrayList<String> listHT = new ArrayList<String>();
    ArrayList<String> listLoaiMayDo = new ArrayList<String>();
    ArrayList<String> listGCX = new ArrayList<String>();
    //Dialog_TTC
    ArrayList<String> listLoaiCot = new ArrayList<String>();
    ArrayList<String> listSoMong = new ArrayList<String>();
    ArrayList<String> listViTriDat = new ArrayList<String>();
    ArrayList<String> listSoChanCot = new ArrayList<String>();
    ArrayList<String> listkichthuoccot = new ArrayList<String>();
    ArrayList<String> listchieucaodot = new ArrayList<String>();
    ArrayList<String> listcapdobenbt = new ArrayList<String>();
    ArrayList<String> listsotangdayco = new ArrayList<String>();
    /**
     * HIỆN TRẠNG
     */
    private Listview_HT_Adapter listview_ht_adapter;
    private List<HienTrang> productList;
    private ListView listView;
    private EditText editText;
    private Button button;
    private ArrayList<String> myList = new ArrayList<String>();

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_main);

        AnhXa_Main();
        NhanBienTruyen();
        click_Main();
        if (SoMong==0) DialogThongTinChung();
        //region MÓNG
        for (int i=0 ;i<=SoMong;i++)
        {
            listMong.add("Móng M" + String.valueOf(i));
        }
        listDam.add("Dầm D1");
        listDam.add("Dầm D2");

        //endregion
        //region THÂN CỘT
        listThanCot.add("Chân cột");
        for (int i=0;i<SoDot;i++)
        {
            listThanCot.add("Đốt D" + String.valueOf(i+1));
        }
        //for (int i=0 ;i<SoTangDay;i++)
        //{
        //    listTangDayCo.add("Tầng dây " + String.valueOf(i+1));
        //}
        listTangDayCo.clear();
        /*for(int i=2;i<SoTangDay+2;i++){
                listTangDayCo.add("Tầng dây " +String.valueOf(i-1));
        }
        if (listGCX.size()>0)
        {
            listTangDayCo.remove(listTangDayCo.size()-1);
            for (int i = 0; i < listGCX.size(); i++)
            {
                listTangDayCo.add("Tầng dây " + listGCX.get(i) + "a");
                listTangDayCo.add("Tầng dây " + listGCX.get(i) + "b");
            }
        }

        */
        for (int i=0;i<=(SoTangDay)-1;i++)
        {
            if (listGCX.contains(String.valueOf(i+1))){
                listTangDayCo.add("Tầng dây " + String.valueOf(i+1) + "a");
                listTangDayCo.add("Tầng dây " + String.valueOf(i+1) + "b");
            }else{
                listTangDayCo.add("Tầng dây " +String.valueOf(i+1));
            }
        }
        //endregion
        //region Tiếp Địa
        listCauKienTiepDiaTable.addAll(Arrays.asList("Thoát sét cho kim thu sét","Thoát sét cho thiết bị treo trên cột","Thoát sét cho chân cột","Thoát sét cho cáp thép dây co","Thoát sét cho phòng máy"));
        listLanDoDienTro.addAll(Arrays.asList("Lần đo 1","Lần đo 2","Lần đo 3"));
        listLoaiMayDo.addAll(Arrays.asList("C380","HT-75","HT-225"));
        //endregion
        //region LIÊN KẾT THÂN CỘT
        listLienKetCot.add("Chân cột");
        for (int i=0;i<SoDot-1;i++)
        {
            listLienKetCot.add("Đốt D" + String.valueOf(i+1) + "- " + String.valueOf(i+2));
        }
        //endregion
        //region CẤU Phụ kiện CỘT
        listPhuKienCot.addAll(Arrays.asList("Bản định vị chân cột","Tăng đơ","Ma ní đầu dưới","Ma ní đầu trên","Khoá cáp đầu dưới","Khoá cáp đầu trên","Vòng ốp móc dây co và bu lông vòng ốp dây co","Dây co","Gá chống xoay","Gá treo Anten (Viba)","Kim thu sét","Mặt bích bịt đầu cột"));
        listPhuKienCot2.addAll(Arrays.asList("Gá chống xoay","Gá treo Anten (Viba)","Kim thu sét","Mặt bích bịt đầu cột"));
        //endregion
        for (int i=0;i<SoCot;i++)
        {
            listDoNghieng_V0.add("Toạ độ vòng 0-" +String.valueOf(i+1));
            listDoNghieng_V1.add("Toạ độ vòng 1-" +String.valueOf(i+1));
            listDoNghieng_V2.add("Toạ độ vòng 2-" +String.valueOf(i+1));
            listDoNghieng_V3.add("Toạ độ vòng 3-" +String.valueOf(i+1));
        }
    }
    /*** ÁNH XẠ*/
    //region ÁNH XẠ
    public void AnhXa_Main(){
        viewGroupMain = findViewById(R.id.viewgroupmain);
        btnChonMong = findViewById(R.id.btnDuLieuDoMong);
        btnThongTinChung = findViewById(R.id.btnLuuThongTinChung);btnThongTinChung.setVisibility(View.GONE);
        btnDulieuDam = findViewById(R.id.btnDuLieuDam);
        btnDuLieuDoMong = findViewById(R.id.btnDuLieuDoMong);
        btnMstower = findViewById(R.id.btnMstower);btnMstower.setVisibility(View.GONE);
        btnDoDienTro = findViewById(R.id.btnDoDienTro);
        btnCauTrucCot = findViewById(R.id.btnCauTrucCot);
        btnPhuKienCot = findViewById(R.id.btnPhuKienCot);
        btnDoLucCang = findViewById(R.id.btnDoLucCang);
        btnThietBiTrenCot = findViewById(R.id.btnThietBiTrenCot);
        btnChanCot = findViewById(R.id.btnChanCot);
        btnDinhCot = findViewById(R.id.btnDinhCot);
        btnHuongBac = findViewById(R.id.btnHuongBac);
        btnVong0 = findViewById(R.id.btnVong0);
        btnVong1 = findViewById(R.id.btnVong1);
        btnVong2 = findViewById(R.id.btnVong2);
        btnVong3 = findViewById(R.id.btnVong3);
        btnKetQuaDoNghieng= findViewById(R.id.btnKetQuaDoNghieng);
        btnChonLoaiMong= findViewById(R.id.btnChonLoaiMong);
        btnToaDoMong = findViewById(R.id.btnToaDoMong);
        btnCaoDoDayCo = findViewById(R.id.btnCaoDoDayCo);

        btnMoThongTinChung = findViewById(R.id.btnMoThongTinChung);
        btnMoDoMstower = findViewById(R.id.btnMoMstower);
        btnMoMatBang = findViewById(R.id.btnMoMatBang);
        btnMoDoMong = findViewById(R.id.btnMoDoMong);
        btnMoTiepDia = findViewById(R.id.btnMoTiepDia);
        btnMoThanCot = findViewById(R.id.btnMoThanCot);
        btnMoDoNghieng = findViewById(R.id.btnMoDoNghieng);
        btnMoNoiNghiep = findViewById(R.id.btnMoNoiNghiep);
        btnMoNgoaiNghiep = findViewById(R.id.btnMoNgoaiNghiep);
        btnMoBaoCao = findViewById(R.id.btnMoBaoCao);

        layoutDoMong = findViewById(R.id.layoutDoMong);layoutDoMong.setVisibility(View.GONE);
        layoutMatBang= findViewById(R.id.layoutMatBang);layoutMatBang.setVisibility(View.GONE);
        layoutTiepDia = findViewById(R.id.layoutTiepDia);layoutTiepDia.setVisibility(View.GONE);
        layoutThanCot = findViewById(R.id.layoutThanCot);layoutThanCot.setVisibility(View.GONE);
        layoutDoNghieng = findViewById(R.id.layoutDoNghieng);layoutDoNghieng.setVisibility(View.GONE);
        layoutNoiNghiep = findViewById(R.id.layoutNoiNghiep);layoutNoiNghiep.setVisibility(View.GONE);
        layoutNgoaiNghiep = findViewById(R.id.layoutNgoaiNghiep);layoutNgoaiNghiep.setVisibility(View.GONE);
    }
    public void AnhXa_DialogTTC(Dialog dialog){
        btnLuuTTC = (Button) dialog.findViewById(R.id.btnLuuThongTinChung);
        btnThoat = (ImageButton) dialog.findViewById(R.id.btnThoat);
        TableBiaTTC = (TableLayout) dialog.findViewById(R.id.TableBia);
        tvViTriDatTTC = (TextView) dialog.findViewById(R.id.tbBiaViTri);
        tvSoMongTTC = (TextView) dialog.findViewById(R.id.tbBiaSoMong);
        tvSoTangDayTTC = (TextView) dialog.findViewById(R.id.tbBiaSoTangDay);
        tvChieuCaoDotTTC = (TextView) dialog.findViewById(R.id.tbBiaChieuCaoDot);
        tvDoCaoTTC = (TextView) dialog.findViewById(R.id.tbBiaDoCao);
        tbBiaLoaiCot = (TextView) dialog.findViewById(R.id.tbBiaLoaiCot);
        tbBiaMacBeTong = dialog.findViewById(R.id.tbBiaMacBeTong);
        tvSoChanCotTTC = dialog.findViewById(R.id.tbBiaSoCot);
        tvMaTramTTC = (EditText) dialog.findViewById(R.id.tbBiaMaTram);
        tvDiaDiemTTC = (TextView) dialog.findViewById(R.id.tbBiaDiaDiem);
        tvKichThuocCotTTC = (TextView) dialog.findViewById(R.id.tbBiaKichThuocCot);
        tvSoDotTTC = (EditText) dialog.findViewById(R.id.tbBiaSoDot);
        tvGiaChongXoayTTC = (TextView) dialog.findViewById(R.id.tbBiaGiaChongXoay);
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);

    }
    public void AnhXa_Dialogmong(Dialog dialog){

        layoutTable3 = dialog.findViewById(R.id.layoutTable3);layoutTable3.setVisibility(View.GONE);
        layoutTable2 = dialog.findViewById(R.id.layoutTable2);layoutTable2.setVisibility(View.GONE);
        layoutTable8 = dialog.findViewById(R.id.layoutTable8);layoutTable8.setVisibility(View.GONE);
        layoutTable9 = dialog.findViewById(R.id.layoutTable9);layoutTable9.setVisibility(View.GONE);

        Table3 = dialog.findViewById(R.id.Table3);
        Table2 = dialog.findViewById(R.id.Table2);
        Table8 = dialog.findViewById(R.id.Table8);
        Table9 = dialog.findViewById(R.id.Table9);

        viewGroup =  dialog.findViewById(R.id.viewgroup);

        tvTenMong = dialog.findViewById(R.id.tvTenMong);
        tvGiaTriTrungBinh= dialog.findViewById(R.id.tvGiaTriTrungBinh);

        edtKichThuoc = dialog.findViewById(R.id.edtKichThuocMong);
        edtHienTrang = dialog.findViewById(R.id.edtDanhGiaMong);
        edtDeXuat= dialog.findViewById(R.id.edtDeXuatMong);
        edtGiaTriDo = dialog.findViewById(R.id.edtGiaTriDo);
        edtGocBan = dialog.findViewById(R.id.edtGocBanMong);
        edtLanDo = dialog.findViewById(R.id.edtLanDo);
        edtDanhGia = dialog.findViewById(R.id.edtDanhGia);
        edtLoaiMay = dialog.findViewById(R.id.edtLoaiMay);

        btnLuuMong = dialog.findViewById(R.id.btnDuLieuDoMong);
        btnLuuCuongDo = dialog.findViewById(R.id.btnDuLieuDoCuongDo);
        btnLuuLucSiet = dialog.findViewById(R.id.btnDuLieuDoLucSiet);
        btnBack = dialog.findViewById(R.id.btnBack);
        btnNext = dialog.findViewById(R.id.btnNext);
        btnBackHienTrang = dialog.findViewById(R.id.btnBackHienTrang);
        btnNextHienTrang = dialog.findViewById(R.id.btnNextHienTrang);
        btnBackMong  = dialog.findViewById(R.id.btnBackMong);
        btnCopy = dialog.findViewById(R.id.btnCopy);
        btnLuuLucCang= dialog.findViewById(R.id.btnDuLieuLucCang);
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);
        btnChupCuongDo = dialog.findViewById(R.id.btnChupCuongDo);
        btnChupLucCang = dialog.findViewById(R.id.btnChupLucCang);
        btnChupLucSiet = dialog.findViewById(R.id.btnChupLucSiet);
        btnKichThuocPhuKien = dialog.findViewById(R.id.btnKichThuocPhuKien);
        btnThietBiMatDat = dialog.findViewById(R.id.btnThietBiMatDat);

        btnPaste = dialog.findViewById(R.id.btnPaste);btnPaste.setVisibility(View.GONE);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocMong);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutCuongDo = dialog.findViewById(R.id.layoutDoCuongDo);lauoutCuongDo.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);
        layoutLucSiet= dialog.findViewById(R.id.layoutLucSiet);layoutLucSiet.setVisibility(View.GONE);
        layoutLucCang= dialog.findViewById(R.id.layoutLucCang);layoutLucCang.setVisibility(View.GONE);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocMong);
        btnlauoutCuongDo = dialog.findViewById(R.id.btnHienDoCuongDo);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
        btnHienBangMong = dialog.findViewById(R.id.btnHienBangMong);
        btnHienBangCuongDo = dialog.findViewById(R.id.btnHienBangCuongDo);
        btnHienBangLucSiet = dialog.findViewById(R.id.btnHienBangLucSiet);
        btnHienBangLucCang = dialog.findViewById(R.id.btnHienBangLucCang);
        btnLocHienTrang = dialog.findViewById(R.id.btnLocHienTrang);

        btnHienLucSiet= dialog.findViewById(R.id.btnHienLucSiet);
        btnHienLucCang= dialog.findViewById(R.id.btnHienLucCang);

        tvViTriHienTrang = dialog.findViewById((R.id.tvViTriHienTrang));
        tvViTriDeXuat= dialog.findViewById((R.id.tvViTriDeXuat));
        btnSearch = dialog.findViewById((R.id.btnSearch));
        btnSearchDX = dialog.findViewById((R.id.btnSearchDX));

        btnBackDX = dialog.findViewById(R.id.btnBackDX);
        btnNextDX = dialog.findViewById(R.id.btnNextDX);
        //region Lực siết trên
        GT_M0M1_Duoi= dialog.findViewById(R.id.GT_M0M1_Siet);
        GT_M0M2_Duoi= dialog.findViewById(R.id.GT_M0M2_Siet);
        GT_M0M3_Duoi= dialog.findViewById(R.id.GT_M0M3_Siet);
        GT_M0M4_Duoi= dialog.findViewById(R.id.GT_M0M4_Siet);
        GT_M0M5_Duoi= dialog.findViewById(R.id.GT_M0M5_Siet);
        GT_M0M6_Duoi= dialog.findViewById(R.id.GT_M0M6_Siet);
        GT_M0M7_Duoi= dialog.findViewById(R.id.GT_M0M7_Siet);

        GT_M0M8_Duoi= dialog.findViewById(R.id.GT_M0M8_Siet);
        GT_M0M9_Duoi= dialog.findViewById(R.id.GT_M0M9_Siet);
        GT_M0M10_Duoi= dialog.findViewById(R.id.GT_M0M10_Siet);
        GT_M0M11_Duoi= dialog.findViewById(R.id.GT_M0M11_Siet);
        GT_M0M12_Duoi= dialog.findViewById(R.id.GT_M0M12_Siet);
        GT_M0M13_Duoi= dialog.findViewById(R.id.GT_M0M13_Siet);
        GT_M0M14_Duoi= dialog.findViewById(R.id.GT_M0M14_Siet);


        TV_M0M1= dialog.findViewById(R.id.TV_M0M1_Siet);
        TV_M0M2= dialog.findViewById(R.id.TV_M0M2_Siet);
        TV_M0M3= dialog.findViewById(R.id.TV_M0M3_Siet);
        TV_M0M4= dialog.findViewById(R.id.TV_M0M4_Siet);
        TV_M0M5= dialog.findViewById(R.id.TV_M0M5_Siet);
        TV_M0M6= dialog.findViewById(R.id.TV_M0M6_Siet);
        TV_M0M7= dialog.findViewById(R.id.TV_M0M7_Siet);

        TV_M0M8= dialog.findViewById(R.id.TV_M0M8_Siet);
        TV_M0M9= dialog.findViewById(R.id.TV_M0M9_Siet);
        TV_M0M10= dialog.findViewById(R.id.TV_M0M10_Siet);
        TV_M0M11= dialog.findViewById(R.id.TV_M0M11_Siet);
        TV_M0M12= dialog.findViewById(R.id.TV_M0M12_Siet);
        TV_M0M13= dialog.findViewById(R.id.TV_M0M13_Siet);
        TV_M0M14= dialog.findViewById(R.id.TV_M0M14_Siet);
        //region Layout móng
        MongM1 = dialog.findViewById(R.id.MongM1_Siet);
        MongM2 = dialog.findViewById(R.id.MongM2_Siet);
        MongM3 = dialog.findViewById(R.id.MongM3_Siet);
        MongM4 = dialog.findViewById(R.id.MongM4_Siet);
        MongM5 = dialog.findViewById(R.id.MongM5_Siet);
        MongM6 = dialog.findViewById(R.id.MongM6_Siet);
        MongM7 = dialog.findViewById(R.id.MongM7_Siet);

        MongM8 = dialog.findViewById(R.id.MongM8_Siet);
        MongM9 = dialog.findViewById(R.id.MongM9_Siet);
        MongM10 = dialog.findViewById(R.id.MongM10_Siet);
        MongM11 = dialog.findViewById(R.id.MongM11_Siet);
        MongM12 = dialog.findViewById(R.id.MongM12_Siet);
        MongM13 = dialog.findViewById(R.id.MongM13_Siet);
        MongM14 = dialog.findViewById(R.id.MongM14_Siet);
        ArrayList<LinearLayout> ArraylistLayout = new ArrayList<LinearLayout>();
        ArraylistLayout.addAll(Arrays.asList(MongM1,MongM2,MongM3,MongM4,MongM5,MongM6,MongM7,MongM8,MongM9,MongM10,MongM11,MongM12,MongM13,MongM14));

        ArrayList<TextView> ArraylistLayoutTV = new ArrayList<TextView>();
        ArraylistLayoutTV.addAll(Arrays.asList(TV_M0M1,TV_M0M2,TV_M0M3,TV_M0M4,TV_M0M5,TV_M0M6,TV_M0M7));
        ArraylistLayoutTV.addAll(Arrays.asList(TV_M0M8,TV_M0M9,TV_M0M10,TV_M0M11,TV_M0M12,TV_M0M13,TV_M0M14));
        for (int i=0;i<14;i++)
        {
            ArraylistLayout.get(i).setVisibility(View.GONE);
        }
        for (int i=0;i<SoTangDay+SoGaChongXoay;i++)
        {
            ArraylistLayout.get(i).setVisibility(View.VISIBLE);
            ArraylistLayoutTV.get(i).setText(listTangDayCo.get(i) + " (N.m)");
        }
        listEdtLucSiet.clear();
        listEdtLucSiet.addAll(Arrays.asList(GT_M0M1_Duoi,GT_M0M2_Duoi,GT_M0M3_Duoi,GT_M0M4_Duoi,GT_M0M5_Duoi,GT_M0M6_Duoi,GT_M0M7_Duoi));
        listEdtLucSiet.addAll(Arrays.asList(GT_M0M8_Duoi,GT_M0M9_Duoi,GT_M0M10_Duoi,GT_M0M11_Duoi,GT_M0M12_Duoi,GT_M0M13_Duoi,GT_M0M14_Duoi));
        //endregion
        //endregion
        //region Lực Căng dây cơ
        TV_M0M1= dialog.findViewById(R.id.TV_M0M1_Cang);
        TV_M0M2= dialog.findViewById(R.id.TV_M0M2_Cang);
        TV_M0M3= dialog.findViewById(R.id.TV_M0M3_Cang);
        TV_M0M4= dialog.findViewById(R.id.TV_M0M4_Cang);
        TV_M0M5= dialog.findViewById(R.id.TV_M0M5_Cang);
        TV_M0M6= dialog.findViewById(R.id.TV_M0M6_Cang);
        TV_M0M7= dialog.findViewById(R.id.TV_M0M7_Cang);

        TV_M0M8= dialog.findViewById(R.id.TV_M0M8_Cang);
        TV_M0M9= dialog.findViewById(R.id.TV_M0M9_Cang);
        TV_M0M10= dialog.findViewById(R.id.TV_M0M10_Cang);
        TV_M0M11= dialog.findViewById(R.id.TV_M0M11_Cang);
        TV_M0M12= dialog.findViewById(R.id.TV_M0M12_Cang);
        TV_M0M13= dialog.findViewById(R.id.TV_M0M13_Cang);
        TV_M0M14= dialog.findViewById(R.id.TV_M0M14_Cang);

        GT_M0M1= dialog.findViewById(R.id.GT_M0M1_Cang);
        GT_M0M2= dialog.findViewById(R.id.GT_M0M2_Cang);
        GT_M0M3= dialog.findViewById(R.id.GT_M0M3_Cang);
        GT_M0M4= dialog.findViewById(R.id.GT_M0M4_Cang);
        GT_M0M5= dialog.findViewById(R.id.GT_M0M5_Cang);
        GT_M0M6= dialog.findViewById(R.id.GT_M0M6_Cang);
        GT_M0M7= dialog.findViewById(R.id.GT_M0M7_Cang);
        GT_M0M8= dialog.findViewById(R.id.GT_M0M8_Cang);
        GT_M0M9= dialog.findViewById(R.id.GT_M0M9_Cang);
        GT_M0M10= dialog.findViewById(R.id.GT_M0M10_Cang);
        GT_M0M11= dialog.findViewById(R.id.GT_M0M11_Cang);
        GT_M0M12= dialog.findViewById(R.id.GT_M0M12_Cang);
        GT_M0M13= dialog.findViewById(R.id.GT_M0M13_Cang);
        GT_M0M14= dialog.findViewById(R.id.GT_M0M14_Cang);

        //region Layout móng
        MongM1 = dialog.findViewById(R.id.MongM1_Cang);
        MongM2 = dialog.findViewById(R.id.MongM2_Cang);
        MongM3 = dialog.findViewById(R.id.MongM3_Cang);
        MongM4 = dialog.findViewById(R.id.MongM4_Cang);
        MongM5 = dialog.findViewById(R.id.MongM5_Cang);
        MongM6 = dialog.findViewById(R.id.MongM6_Cang);
        MongM7 = dialog.findViewById(R.id.MongM7_Cang);

        MongM8 = dialog.findViewById(R.id.MongM8_Cang);
        MongM9 = dialog.findViewById(R.id.MongM9_Cang);
        MongM10 = dialog.findViewById(R.id.MongM10_Cang);
        MongM11 = dialog.findViewById(R.id.MongM11_Cang);
        MongM12 = dialog.findViewById(R.id.MongM12_Cang);
        MongM13 = dialog.findViewById(R.id.MongM13_Cang);
        MongM14 = dialog.findViewById(R.id.MongM14_Cang);
        ArrayList<LinearLayout> ArraylistLayout2 = new ArrayList<LinearLayout>();
        ArraylistLayout2.addAll(Arrays.asList(MongM1,MongM2,MongM3,MongM4,MongM5,MongM6,MongM7,MongM8,MongM9,MongM10,MongM11,MongM12,MongM13,MongM14));
        for (int i=0;i<14;i++)
        {
            ArraylistLayout2.get(i).setVisibility(View.GONE);
        }
        ArrayList<TextView> ArraylistLayoutTV2 = new ArrayList<TextView>();
        ArraylistLayoutTV2.addAll(Arrays.asList(TV_M0M1,TV_M0M2,TV_M0M3,TV_M0M4,TV_M0M5,TV_M0M6,TV_M0M7));
        ArraylistLayoutTV2.addAll(Arrays.asList(TV_M0M8,TV_M0M9,TV_M0M10,TV_M0M11,TV_M0M12,TV_M0M13,TV_M0M14));
        for (int i=0;i<SoTangDay+SoGaChongXoay;i++)
        {
            ArraylistLayout2.get(i).setVisibility(View.VISIBLE);
            ArraylistLayoutTV2.get(i).setText(listTangDayCo.get(i) + " (Kgf)");
        }

        listEdtLucCang.clear();
        listEdtLucCang.addAll(Arrays.asList(GT_M0M1,GT_M0M2,GT_M0M3,GT_M0M4,GT_M0M5,GT_M0M6,GT_M0M7));
        listEdtLucCang.addAll(Arrays.asList(GT_M0M8,GT_M0M9,GT_M0M10,GT_M0M11,GT_M0M12,GT_M0M13,GT_M0M14));
        //endregion
        //endregion
        AnhXa_DialogMocCo(dialog);
        AnhXa_DialogBulong(dialog);
    }
    public void AnhXa_DialogMocCo(Dialog dialog){
        btnLocHienTrangMocCo = dialog.findViewById(R.id.btnLocHienTrangMocCo);
        edtKichThuocMocCo = dialog.findViewById(R.id.edtKichThuocMocCo);
        edtHienTrangMocCo = dialog.findViewById(R.id.edtDanhGiaMocCo);
        edtDeXuatMocCo= dialog.findViewById(R.id.edtDeXuatMocCo);
        btnLuuMocCo = dialog.findViewById(R.id.btnDuLieuDoMocCo);

        btnBackHienTrangMocCo = dialog.findViewById(R.id.btnBackHienTrangMocCo);
        btnNextHienTrangMocCo = dialog.findViewById(R.id.btnNextHienTrangMocCo);
        lauoutKichThuocMocCo = dialog.findViewById(R.id.layoutKichThuocMocCo);lauoutKichThuocMocCo.setVisibility(View.GONE);
        btnChupHienTrangMocCo = dialog.findViewById(R.id.btnChupHienTrangMocCo);
        btnlauoutKichThuocMocCo = dialog.findViewById(R.id.btnHienKichThuocMocCo);
        tvViTriHienTrangMocCo = dialog.findViewById((R.id.tvViTriHienTrangMocCo));
        tvViTriDeXuatMocCo= dialog.findViewById((R.id.tvViTriDeXuatMocCo));
        btnSearchMocCo = dialog.findViewById((R.id.btnSearchMocCo));
        btnSearchDXMocCo = dialog.findViewById((R.id.btnSearchDXMocCo));
        btnBackDXMocCo = dialog.findViewById(R.id.btnBackDXMocCo);
        btnNextDXMocCo = dialog.findViewById(R.id.btnNextDXMocCo);
    }
    public void AnhXa_Dialogdam(Dialog dialog){
        btnLocHienTrang = dialog.findViewById(R.id.btnLocHienTrang);

        viewGroup =  dialog.findViewById(R.id.viewgroup);

        tvTenMong = dialog.findViewById(R.id.tvTenMong);
        tvGiaTriTrungBinh= dialog.findViewById(R.id.tvGiaTriTrungBinh);

        edtKichThuoc = dialog.findViewById(R.id.edtKichThuocMong);
        edtHienTrang = dialog.findViewById(R.id.edtDanhGiaMong);
        edtDeXuat= dialog.findViewById(R.id.edtDeXuatMong);
        edtGiaTriDo = dialog.findViewById(R.id.edtGiaTriDo);
        edtGocBan = dialog.findViewById(R.id.edtGocBanMong);
        edtLanDo = dialog.findViewById(R.id.edtLanDo);

        btnLuuMong = dialog.findViewById(R.id.btnDuLieuDoMong);
        btnLuuCuongDo = dialog.findViewById(R.id.btnDuLieuDoCuongDo);
        btnBack = dialog.findViewById(R.id.btnBack);
        btnNext = dialog.findViewById(R.id.btnNext);
        btnBackMong  = dialog.findViewById(R.id.btnBackMong );
        btnBackHienTrang = dialog.findViewById(R.id.btnBackHienTrang);
        btnNextHienTrang = dialog.findViewById(R.id.btnNextHienTrang);
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);
        btnChupCuongDo = dialog.findViewById(R.id.btnChupCuongDo);


        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocMong);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutCuongDo = dialog.findViewById(R.id.layoutDoCuongDo);lauoutCuongDo.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocMong);
        btnlauoutCuongDo = dialog.findViewById(R.id.btnHienDoCuongDo);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);

        tvViTriHienTrang = dialog.findViewById((R.id.tvViTriHienTrang));
        tvViTriDeXuat= dialog.findViewById((R.id.tvViTriDeXuat));
        btnSearch = dialog.findViewById((R.id.btnSearch));
        btnSearchDX = dialog.findViewById((R.id.btnSearchDX));
        btnBackDX = dialog.findViewById(R.id.btnBackDX);
        btnNextDX = dialog.findViewById(R.id.btnNextDX);

    }
    public void AnhXa_DialogTiepDia(Dialog dialog){
        btnLocHienTrangCauKien = dialog.findViewById(R.id.btnLocHienTrang);
        tvTenCauKien = dialog.findViewById(R.id.tvTenCauKien);
        viewGroupCauKien =  dialog.findViewById(R.id.viewgroup);
        edtKichThuocCauKien = dialog.findViewById(R.id.edtKichThuocCauKien);
        edtHienTrangCauKien = dialog.findViewById(R.id.edtDanhGiaCauKien);
        edtDeXuatCauKien= dialog.findViewById(R.id.edtDeXuatCauKien);
        btnLuuCauKien = dialog.findViewById(R.id.btnDuLieuDoCauKien);
        btnBackCauKien = dialog.findViewById(R.id.btnBackCauKien);
        lauoutKichThuocCauKien = dialog.findViewById(R.id.layoutKichThuocCauKien);lauoutKichThuocCauKien.setVisibility(View.GONE);
        lauoutBangCauKien = dialog.findViewById(R.id.layoutXemDangBang);lauoutBangCauKien.setVisibility(View.GONE);
        btnBackHienTrangCauKien = dialog.findViewById(R.id.btnBackHienTrang);
        btnNextHienTrangCauKien = dialog.findViewById(R.id.btnNextHienTrang);
        btnlauoutKichThuocCauKien = dialog.findViewById(R.id.btnHienKichThuocCauKien);
        btnlauoutBangCauKien = dialog.findViewById(R.id.btnHienBang);
        btnChupHienTrangCauKien = dialog.findViewById(R.id.btnChupHienTrang);
        tvViTriHienTrangCauKien = dialog.findViewById((R.id.tvViTriHienTrang));
        tvViTriDeXuatCauKien= dialog.findViewById((R.id.tvViTriDeXuat));
        btnSearchCauKien = dialog.findViewById((R.id.btnSearch));
        btnSearchDXCauKien = dialog.findViewById((R.id.btnSearchDX));
        btnBackDXCauKien = dialog.findViewById(R.id.btnBackDX);
        btnNextDXCauKien = dialog.findViewById(R.id.btnNextDX);
    }
    public void AnhXa_DialogPhuKien(Dialog dialog){
        btnLocHienTrangPhuKien = dialog.findViewById(R.id.btnLocHienTrangPhuKien);
        tvTenPhuKien = dialog.findViewById(R.id.tvTenPhuKien);
        viewGroupPhuKien =  dialog.findViewById(R.id.viewgroupPhuKien);

        edtKichThuocPhuKien = dialog.findViewById(R.id.edtKichThuocPhuKien);
        edtHienTrangPhuKien = dialog.findViewById(R.id.edtDanhGiaPhuKien);
        edtDeXuatPhuKien= dialog.findViewById(R.id.edtDeXuatPhuKien);
        btnCopyPhuKien = dialog.findViewById(R.id.btnCopyPhuKien);
        btnPastePhuKien = dialog.findViewById(R.id.btnPastePhuKien);btnPastePhuKien.setVisibility(View.GONE);
        btnLuuPhuKien = dialog.findViewById(R.id.btnDuLieuDoPhuKien);
        btnThoatPhuKien = dialog.findViewById(R.id.btnBackPhuKien);
        lauoutKichThuocPhuKien = dialog.findViewById(R.id.layoutKichThuocPhuKien);lauoutKichThuocPhuKien.setVisibility(View.GONE);
        lauoutBangPhuKien = dialog.findViewById(R.id.layoutXemDangBangPhuKien);lauoutBangPhuKien.setVisibility(View.GONE);
        btnBackHienTrangPhuKien = dialog.findViewById(R.id.btnBackHienTrangPhuKien);
        btnNextHienTrangPhuKien = dialog.findViewById(R.id.btnNextHienTrangPhuKien);
        btnlauoutKichThuocPhuKien = dialog.findViewById(R.id.btnHienKichThuocPhuKien);
        btnlauoutBangPhuKien = dialog.findViewById(R.id.btnHienBangPhuKien);
        btnChupHienTrangPhuKien = dialog.findViewById(R.id.btnChupHienTrangPhuKien);

        tvViTriHienTrangPhuKien = dialog.findViewById((R.id.tvViTriHienTrangPhuKien));
        tvViTriDeXuatPhuKien= dialog.findViewById((R.id.tvViTriDeXuatPhuKien));
        btnSearchPhuKien = dialog.findViewById((R.id.btnSearchPhuKien));
        btnSearchDXPhuKien = dialog.findViewById((R.id.btnSearchDXPhuKien));
        btnBackDXPhuKien = dialog.findViewById(R.id.btnBackDXPhuKien);
        btnNextDXPhuKien = dialog.findViewById(R.id.btnNextDXPhuKien);
    }
    public void AnhXa_DialogThanCot(Dialog dialog){
        layoutTable6 = dialog.findViewById(R.id.layoutTable6);layoutTable6.setVisibility(View.GONE);
        layoutTable10 = dialog.findViewById(R.id.layoutTable10);layoutTable10.setVisibility(View.GONE);

        btnLocHienTrang = dialog.findViewById(R.id.btnLocHienTrang);

        tvTenMong = dialog.findViewById(R.id.tvTenThanCot);
        viewGroup =  dialog.findViewById(R.id.viewgroup);

        edtKichThuoc = dialog.findViewById(R.id.edtKichThuocThanCot);
        edtHienTrang = dialog.findViewById(R.id.edtDanhGiaThanCot);
        edtDeXuat= dialog.findViewById(R.id.edtDeXuatThanCot);
        edtThanhCanh= dialog.findViewById(R.id.edtThanhCanhThanCot);
        edtDoDayThanhCanh= dialog.findViewById(R.id.edtDoDayThanhCanh);
        edtThanhGiang= dialog.findViewById(R.id.edtThanhGiangThanCot);
        btnCopy = dialog.findViewById(R.id.btnCopy);
        btnPaste = dialog.findViewById(R.id.btnPaste);btnPaste.setVisibility(View.GONE);
        btnBackHienTrang = dialog.findViewById(R.id.btnBackHienTrang);
        btnNextHienTrang = dialog.findViewById(R.id.btnNextHienTrang);
        btnLuu = dialog.findViewById(R.id.btnDuLieuDoThanCot);
        btnBackMong = dialog.findViewById(R.id.btnBackThanCot);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocThanCot);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);
        btnChupThanhGiang = dialog.findViewById(R.id.btnChupThanhGiang);
        btnChupThanhCanh = dialog.findViewById(R.id.btnChupThanhCanh);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocThanCot);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
        btnHienBangThanCot = dialog.findViewById(R.id.btnHienBangThanCot);
        btnHienBangKheHo = dialog.findViewById(R.id.btnHienBangKheHo);

        tvViTriHienTrang = dialog.findViewById((R.id.tvViTriHienTrang));
        tvViTriDeXuat= dialog.findViewById((R.id.tvViTriDeXuat));
        btnSearch = dialog.findViewById((R.id.btnSearch));
        btnSearchDX = dialog.findViewById((R.id.btnSearchDX));
        btnBackDX = dialog.findViewById(R.id.btnBackDX);
        btnNextDX = dialog.findViewById(R.id.btnNextDX);

        AnhXa_DialogKheHo(dialog);

    }
    public void AnhXa_DialogKheHo(Dialog dialog){
        btnLocHienTrangKheHo = dialog.findViewById(R.id.btnLocHienTrangKheHo);
        edtKichThuocKheHo = dialog.findViewById(R.id.edtDuongKinhBulongKheHo);
        edtThanhCanhKheHo = dialog.findViewById(R.id.edtKhoangCachKheHo);
        edtHienTrangKheHo = dialog.findViewById(R.id.edtDanhGiaKheHo);
        edtDeXuatKheHo= dialog.findViewById(R.id.edtDeXuatKheHo);
        btnCopyKheHo = dialog.findViewById(R.id.btnCopyKheHo);
        btnPasteKheHo = dialog.findViewById(R.id.btnPasteKheHo);btnPasteKheHo.setVisibility(View.GONE);
        btnLuuKheHo = dialog.findViewById(R.id.btnDuLieuDoKheHo);
        lauoutKichThuocKheHo = dialog.findViewById(R.id.layoutKichThuocKheHo);lauoutKichThuocKheHo.setVisibility(View.GONE);
        btnBackHienTrangKheHo = dialog.findViewById(R.id.btnBackHienTrangKheHo);
        btnNextHienTrangKheHo = dialog.findViewById(R.id.btnNextHienTrangKheHo);
        btnlauoutKichThuocKheHo = dialog.findViewById(R.id.btnHienKichThuocKheHo);
        btnlauoutBangKheHo = dialog.findViewById(R.id.btnHienBangKheHo);
        btnChupHienTrangKheHo = dialog.findViewById(R.id.btnChupHienTrangKheHo);

        tvViTriHienTrangKheHo = dialog.findViewById((R.id.tvViTriHienTrangKheHo));
        tvViTriDeXuatKheHo= dialog.findViewById((R.id.tvViTriDeXuatKheHo));
        btnSearchKheHo = dialog.findViewById((R.id.btnSearchKheHo));
        btnSearchDXKheHo = dialog.findViewById((R.id.btnSearchDXKheHo));
        btnBackDXKheHo = dialog.findViewById(R.id.btnBackDXKheHo);
        btnNextDXKheHo = dialog.findViewById(R.id.btnNextDXKheHo);
    }
    public void AnhXa_DialogDienTro(Dialog dialog){
        tvTenMong = dialog.findViewById(R.id.tvLanDo);
        viewGroup =  dialog.findViewById(R.id.viewgroup);

        edtKichThuoc = dialog.findViewById(R.id.edtDienTro);
        edtHienTrang = dialog.findViewById(R.id.tvDanhGia);
        edtDeXuat= dialog.findViewById(R.id.edtGhiChu);
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);


        btnLuu = dialog.findViewById(R.id.btnDuLieuDoThanCot);
        btnBackMong = dialog.findViewById(R.id.btnBackThanCot);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocThanCot);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocThanCot);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
    }
    public void AnhXa_DialogBulong(Dialog dialog){
        btnLocHienTrangBulong = dialog.findViewById(R.id.btnLocHienTrangBulong);
        edtKichThuocBulong = dialog.findViewById(R.id.edtKichThuocBulong);
        edtHienTrangBulong = dialog.findViewById(R.id.edtDanhGiaBulong);
        edtDeXuatBulong= dialog.findViewById(R.id.edtDeXuatBulong);

        btnLuuBulong = dialog.findViewById(R.id.btnDuLieuDoBulong);
        btnBackHienTrangBulong = dialog.findViewById(R.id.btnBackHienTrangBulong);
        btnNextHienTrangBulong = dialog.findViewById(R.id.btnNextHienTrangBulong);

        lauoutKichThuocBulong = dialog.findViewById(R.id.layoutKichThuocBulong);lauoutKichThuocBulong.setVisibility(View.GONE);
        btnChupHienTrangBulong = dialog.findViewById(R.id.btnChupHienTrangBulong);
        btnlauoutKichThuocBulong = dialog.findViewById(R.id.btnHienKichThuocBulong);
        btnlauoutKichThuocBulong.setVisibility(View.GONE);

        tvViTriHienTrangBulong = dialog.findViewById((R.id.tvViTriHienTrangBulong));
        tvViTriDeXuatBulong= dialog.findViewById((R.id.tvViTriDeXuatBulong));
        btnSearchBulong = dialog.findViewById((R.id.btnSearchBulong));
        btnSearchDXBulong = dialog.findViewById((R.id.btnSearchDXBulong));
        btnBackDXBulong = dialog.findViewById(R.id.btnBackDXBulong);
        btnNextDXBulong = dialog.findViewById(R.id.btnNextDXBulong);
    }
    public void AnhXa_DialogLucCang(Dialog dialog){
        tvTenMong = dialog.findViewById(R.id.tvTenTangCo);
        viewGroup =  dialog.findViewById(R.id.viewgroup);
        layoutTable8 = dialog.findViewById(R.id.layoutTable8);layoutTable8.setVisibility(View.GONE);
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);
        btnKichThuocPhuKien = dialog.findViewById(R.id.btnKichThuocPhuKien);

        Table8 = dialog.findViewById(R.id.Table8);

        //region Lực siết trên
        GT_M0M1_Tren= dialog.findViewById(R.id.GT_M0M1_Siet);
        GT_M0M2_Tren= dialog.findViewById(R.id.GT_M0M2_Siet);
        GT_M0M3_Tren= dialog.findViewById(R.id.GT_M0M3_Siet);
        GT_M0M4_Tren= dialog.findViewById(R.id.GT_M0M4_Siet);
        GT_M0M5_Tren= dialog.findViewById(R.id.GT_M0M5_Siet);
        GT_M0M6_Tren= dialog.findViewById(R.id.GT_M0M6_Siet);

        MongM1 = dialog.findViewById(R.id.MongM1_Siet);
        MongM2 = dialog.findViewById(R.id.MongM2_Siet);
        MongM3 = dialog.findViewById(R.id.MongM3_Siet);
        MongM4 = dialog.findViewById(R.id.MongM4_Siet);
        MongM5 = dialog.findViewById(R.id.MongM5_Siet);
        MongM6 = dialog.findViewById(R.id.MongM6_Siet);
        ArrayList<LinearLayout> ArraylistLayout_Siet = new ArrayList<LinearLayout>();
        ArraylistLayout_Siet.addAll(Arrays.asList(MongM1,MongM2,MongM3,MongM4,MongM5,MongM6));
        for (int i=0;i<6;i++)
        {
            ArraylistLayout_Siet.get(i).setVisibility(View.GONE);
        }
        for (int i=0;i<SoMong;i++)
        {
            ArraylistLayout_Siet.get(i).setVisibility(View.VISIBLE);
        }
        listEdtLucSiet.clear();
        listEdtLucSiet.addAll(Arrays.asList(null,null,GT_M0M1_Tren,null,GT_M0M2_Tren,null,GT_M0M3_Tren,null,GT_M0M4_Tren,null,GT_M0M5_Tren,null,GT_M0M6_Tren));
        //endregion

        btnBackMong = dialog.findViewById(R.id.btnBackMong);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);
        layoutLucSiet= dialog.findViewById(R.id.layoutLucSiet);layoutLucSiet.setVisibility(View.GONE);
        btnHienBangLucSiet = dialog.findViewById(R.id.btnHienBangLucSiet);
        btnLuuLucSiet = dialog.findViewById(R.id.btnDuLieuLucSiet);

        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
        btnHienLucSiet= dialog.findViewById(R.id.btnHienLucSiet);

    }
    public void AnhXa_DialogAnten(Dialog dialog){
        viewGroup =  dialog.findViewById(R.id.viewgroup);
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);


        edtCaoTrinh= dialog.findViewById(R.id.edtCaoTrinh);
        edtLoaiAnten= dialog.findViewById(R.id.edtLoaiAnten);
        edtKichThuocAnten= dialog.findViewById(R.id.edtKichThuocAnten);
        edtTrongLuong= dialog.findViewById(R.id.edtTrongLuong);
        edtSoLuong= dialog.findViewById(R.id.edtSoLuong);

        listEdtAnten.clear();
        listEdtAnten.addAll(Arrays.asList(edtCaoTrinh,edtLoaiAnten,edtKichThuocAnten,edtTrongLuong,edtSoLuong));

        btnLuu = dialog.findViewById(R.id.btnDuLieuDoMong);
        btnBackMong = dialog.findViewById(R.id.btnBackMong);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocMong);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocMong);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
    }
    public void AnhXa_DialogDoNghieng(Dialog dialog){
        tvTenMong = dialog.findViewById(R.id.tvTenTangCo);
        viewGroup =  dialog.findViewById(R.id.viewgroup);
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);

        GT_X= dialog.findViewById(R.id.GT_X);
        GT_Y= dialog.findViewById(R.id.GT_Y);
        GT_Z= dialog.findViewById(R.id.GT_Z);

        btnLuu = dialog.findViewById(R.id.btnDuLieuDoMong);
        btnBackMong = dialog.findViewById(R.id.btnBackMong);
        edtDanhGiaDoNghieng = dialog.findViewById(R.id.edtDanhGiaDoNghieng);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocMong);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocMong);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
    }
    public void AnhXa_DialogKetQuaDoNghieng(Dialog dialog){

        btnLuu = dialog.findViewById(R.id.btnTinhNghieng);
        btnBackMong = dialog.findViewById(R.id.btnBackMong);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocMong);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);
        viewGroup =  dialog.findViewById(R.id.viewgroup);
        edtLoaiCotTinhNghieng =  dialog.findViewById(R.id.edtLoaiCotTinhNghieng);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocMong);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
    }
    public void AnhXa_Dialog_LoaiMong(Dialog dialog){

        btnLuu = dialog.findViewById(R.id.btnLuu);
        btnBackMong = dialog.findViewById(R.id.btnBack);

        layoutMong1 = (LinearLayout) dialog.findViewById(R.id.LayoutMong1);
        layoutMong2 = (LinearLayout) dialog.findViewById(R.id.LayoutMong2);
        layoutMong3 = (LinearLayout) dialog.findViewById(R.id.LayoutMong3);
        layoutMong4 = (LinearLayout) dialog.findViewById(R.id.LayoutMong4);
    }
    public void AnhXa_Dialog_ToaDoMong(Dialog dialog){
        btnLuu = dialog.findViewById(R.id.btnLuu);
        viewGroup =  dialog.findViewById(R.id.viewgroup);
        btnLuuGocMoc = dialog.findViewById(R.id.btnLuuGocMoc);
        btnBackMong = dialog.findViewById(R.id.btnBack);
        btnHienBangCanh = dialog.findViewById(R.id.btnHienBangCanh);
        btnHienCaoDoChanCot = dialog.findViewById(R.id.btnHienCaoDoChanCot);
        btnHienBangGoc = dialog.findViewById(R.id.btnHienBangGoc);
        btnHienBangKetQua = dialog.findViewById(R.id.btnHienBangKetQua);
        btnHienBangKetQua_Mstower = dialog.findViewById(R.id.btnHienBangKetQua_Mstower);
        btnHienGocXoay = dialog.findViewById(R.id.btnHienGocXoay);
        btnLuuBangCanh = (Button) dialog.findViewById(R.id.btnLuuBangCanh);
        btnLuuBangGoc = (Button) dialog.findViewById(R.id.btnLuuBangGoc);

        layoutBangCanh = (LinearLayout) dialog.findViewById(R.id.LayoutBangcanh);layoutBangCanh.setVisibility(View.GONE);
        layoutCaoDoChanCot = (LinearLayout) dialog.findViewById(R.id.layoutCaoDoChanMong);layoutCaoDoChanCot.setVisibility(View.GONE);
        layoutGocXoay= (LinearLayout) dialog.findViewById(R.id.layoutGocXoay);//layoutGocXoay.setVisibility(View.GONE);
        layoutBangGoc = (LinearLayout) dialog.findViewById(R.id.LayoutBanggoc);layoutBangGoc.setVisibility(View.GONE);
        layoutBangToaDoMong= (LinearLayout) dialog.findViewById(R.id.LayoutBangToaDoMong);layoutBangToaDoMong.setVisibility(View.GONE);
        layoutBangToaDoMong_Mstower= (LinearLayout) dialog.findViewById(R.id.LayoutBangToaDoMong_Mstower);layoutBangToaDoMong_Mstower.setVisibility(View.GONE);
        edtGocXoay = dialog.findViewById(R.id.txtGocXoay);
        edtCaoDoChanCot = dialog.findViewById(R.id.txtCaoDoChanCot);

        TableCanhCanhCanh = (TableLayout) dialog.findViewById(R.id.TableCanhCanhCanh);
        TableCanhGocCanh = (TableLayout) dialog.findViewById(R.id.TableCanhGocCanh);
        TableToaDoMong = (TableLayout) dialog.findViewById(R.id.TableToaDoMong);
        TableToaDoMong_Mstower = (TableLayout) dialog.findViewById(R.id.TableToaDoMong_Mstower);
        TableCaoDoChanCot = (TableLayout) dialog.findViewById(R.id.TableCaoDoMong);
        imgMatBang = dialog.findViewById(R.id.imgMatBang);
        imgGocXoay = dialog.findViewById(R.id.imgGocXoay);
    }
    public void AnhXa_Dialog_CaoDoDayCo(Dialog dialog){
        btnLuu = dialog.findViewById(R.id.btnLuu);
        TableCaoDoDayCo = dialog.findViewById(R.id.TableCaoDoDayCo);
        btnBackMong = dialog.findViewById(R.id.btnBack);

    }
    public void AnhXa_Dialog_Mstower(Dialog dialog){
        btnLuu = dialog.findViewById(R.id.btnLuu);
        TableMstower = dialog.findViewById(R.id.TableMstower);
        btnBackMong = dialog.findViewById(R.id.btnBack);

    }
    //endregion
    /**
     * SỰ KIỆN CLICK
     */
    //region SỰ KIỆN CLICK
    public void click_Main(){
        btnMoNoiNghiep.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (SoMong<=1){Toast.makeText(getApplicationContext(),"Bạn chưa lưu dữ liệu thông tin chung", Toast.LENGTH_SHORT).show();}else{
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (layoutNoiNghiep.getVisibility() == View.GONE)
                {layoutNoiNghiep.setVisibility(View.VISIBLE);layoutNgoaiNghiep.setVisibility(View.GONE);}
                else layoutNoiNghiep.setVisibility(View.GONE);}
            }
        });
        btnMoNgoaiNghiep.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (SoMong<=1){Toast.makeText(getApplicationContext(),"Bạn chưa lưu dữ liệu thông tin chung", Toast.LENGTH_SHORT).show();}else{

                    AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (layoutNgoaiNghiep.getVisibility() == View.GONE)
                {layoutNgoaiNghiep.setVisibility(View.VISIBLE);layoutNoiNghiep.setVisibility(View.GONE);}
                else layoutNgoaiNghiep.setVisibility(View.GONE);}
            }
        });
        btnMoBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent3 = new Intent(FormMain.this, Trang1_Activity.class);
                intent3.putExtra("TenTram", MaTram);  // Truyền một String
                intent3.putExtra("SoMong",String.valueOf(SoMong) );  // Truyền một String
                intent3.putExtra("SoDot", String.valueOf(SoDot));  // Truyền một String
                intent3.putExtra("ViTriDat", String.valueOf(ViTriDat));  // Truyền một String
                intent3.putExtra("SoTangDay", String.valueOf(SoTangDay));  // Truyền một String
                intent3.putExtra("SoChanCot", String.valueOf(SoCot));  // Truyền một String
                intent3.putExtra("KichThuocMong",String.valueOf(KichThuocMong));  // Truyền một String
                intent3.putExtra("GiaChongXoay",String.valueOf(GiaChongXoay));  // Truyền một String
                intent3.putExtra("KichThuocCot", String.valueOf(KichThuocCot));  // Truyền một String
                startActivity(intent3);
            }
        });
        //region THÔNG TIN CHUNG
        btnThongTinChung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThongTinChung();
            }
        });

        btnMoThongTinChung.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (btnThongTinChung.getVisibility() == View.GONE)
                {btnThongTinChung.setVisibility(View.VISIBLE);layoutDoMong.setVisibility(View.GONE);layoutMatBang.setVisibility(View.GONE);btnMstower.setVisibility(View.GONE);
                    layoutTiepDia.setVisibility(View.GONE);layoutThanCot.setVisibility(View.GONE);layoutDoNghieng.setVisibility(View.GONE);}
                else btnThongTinChung.setVisibility(View.GONE);
            }
        });
        //endregion
        //region ĐO DẦM, MONG, BULONG
        btnDuLieuDoMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnChonMong);
                for (String s : listMong)
                { popupMenu.getMenu().add(s);}
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        vitriMong = listMong.indexOf(menuItem.getTitle().toString());
                        DialogMong(menuItem.getTitle().toString(),vitriMong+1);

                        //searchAutoCompleteCT.setText(menuItem.getTitle().toString());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnDulieuDam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnDulieuDam);
                for (String s : listDam)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {

                        int Rowcount = SoMong+6;
                        vitriDam = listDam.indexOf(menuItem.getTitle().toString());
                        DialogDam(menuItem.getTitle().toString(),Rowcount-2+vitriDam);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        btnMoDoMong.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                //final ChangeBounds transition = new Ch
                // angeBounds();
                //transition.setDuration(150L); // Sets a duration of 600 milliseconds
                //new TransitionManager().beginDelayedTransition(viewGroup, transition);
                if (layoutDoMong.getVisibility() == View.GONE)
                {
                    layoutDoMong.setVisibility(View.VISIBLE);layoutMatBang.setVisibility(View.GONE);btnThongTinChung.setVisibility(View.GONE);btnMstower.setVisibility(View.GONE);
                    layoutTiepDia.setVisibility(View.GONE);layoutThanCot.setVisibility(View.GONE);layoutDoNghieng.setVisibility(View.GONE);
                }
                else layoutDoMong.setVisibility(View.GONE);
            }
        });
        //endregion
        //region MẶT BẰNG
        btnMoMatBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (layoutMatBang.getVisibility() == View.GONE)
                {layoutMatBang.setVisibility(View.VISIBLE);layoutDoMong.setVisibility(View.GONE);btnThongTinChung.setVisibility(View.GONE);btnMstower.setVisibility(View.GONE);
                    layoutTiepDia.setVisibility(View.GONE);layoutThanCot.setVisibility(View.GONE);layoutDoNghieng.setVisibility(View.GONE);}

                else layoutMatBang.setVisibility(View.GONE);
            }
        });
        btnChonLoaiMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLoaiMong();
            }
        });
        btnToaDoMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogToaDoMong();
            }
        });
        btnCaoDoDayCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCaoDoDayCo();
            }
        });
        //endregion
        //region MSTOWER
        btnMoDoMstower.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (btnMstower.getVisibility() == View.GONE)
                {
                    btnMstower.setVisibility(View.VISIBLE);layoutMatBang.setVisibility(View.GONE);btnThongTinChung.setVisibility(View.GONE);layoutDoMong.setVisibility(View.GONE);
                    layoutTiepDia.setVisibility(View.GONE);layoutThanCot.setVisibility(View.GONE);layoutDoNghieng.setVisibility(View.GONE);
                }

                else btnMstower.setVisibility(View.GONE);
            }
        });
        btnMstower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogMstower();
            }
        });
        //endregion
        //region HÊ THÔNG TIẾP ĐỊA
        btnMoTiepDia.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (layoutTiepDia.getVisibility() == View.GONE)
                {
                    layoutTiepDia.setVisibility(View.VISIBLE);layoutDoMong.setVisibility(View.GONE);btnThongTinChung.setVisibility(View.GONE);btnMstower.setVisibility(View.GONE);
                    layoutMatBang.setVisibility(View.GONE);layoutThanCot.setVisibility(View.GONE);layoutDoNghieng.setVisibility(View.GONE);
                }
                else layoutTiepDia.setVisibility(View.GONE);
            }
        });

        btnDoDienTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnDoDienTro);
                for (String s : listLanDoDienTro)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listLanDoDienTro.indexOf(menuItem.getTitle().toString());
                        //Toast.makeText(getApplicationContext(),String.valueOf(index), Toast.LENGTH_SHORT).show();
                        DialogDienTro(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        //endregion
        //region KIỂM TRA THÂN CỘT
        btnMoThanCot.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (layoutThanCot.getVisibility() == View.GONE)
                {
                    layoutThanCot.setVisibility(View.VISIBLE);layoutMatBang.setVisibility(View.GONE);btnThongTinChung.setVisibility(View.GONE);btnMstower.setVisibility(View.GONE);
                    layoutTiepDia.setVisibility(View.GONE);layoutDoMong.setVisibility(View.GONE);layoutDoNghieng.setVisibility(View.GONE);
                }
                else layoutThanCot.setVisibility(View.GONE);
            }
        });
        btnCauTrucCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnCauTrucCot);
                for (String s : listThanCot)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listThanCot.indexOf(menuItem.getTitle().toString());
                        //Toast.makeText(getApplicationContext(),String.valueOf(index), Toast.LENGTH_SHORT).show();
                        DialogThanCot(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnPhuKienCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnPhuKienCot);
                for (String s : listPhuKienCot2)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listPhuKienCot.indexOf(menuItem.getTitle().toString());
                        //Toast.makeText(getApplicationContext(),String.valueOf(index), Toast.LENGTH_SHORT).show();
                        DialogPhuKien(menuItem.getTitle().toString(),index+1,listPhuKienCot2);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        btnDoLucCang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnDoLucCang);
                for (String s : listTangDayCo)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listTangDayCo.indexOf(menuItem.getTitle().toString());
                        DialogLucCang(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        btnThietBiTrenCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAnten();
            }
        });
        //endregion
        //region ĐO NGHIÊNG CỘT
        btnMoDoNghieng.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (layoutDoNghieng.getVisibility() == View.GONE)
                {layoutDoNghieng.setVisibility(View.VISIBLE);layoutMatBang.setVisibility(View.GONE);btnThongTinChung.setVisibility(View.GONE);layoutDoMong.setVisibility(View.GONE);
                    layoutTiepDia.setVisibility(View.GONE);layoutThanCot.setVisibility(View.GONE);btnMstower.setVisibility(View.GONE);}
                else layoutDoNghieng.setVisibility(View.GONE);
            }
        });
        btnChanCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDoNghieng_ChanCot();
            }
        });
        btnDinhCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDoNghieng_DinhCot();
            }
        });
        btnHuongBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDoNghieng_HuongBac();
            }
        });
        btnVong0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnVong0);
                for (String s : listDoNghieng_V0)
                {popupMenu.getMenu().add(s);}
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listDoNghieng_V0.indexOf(menuItem.getTitle().toString());
                        DialogDoNghieng_V0(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnVong1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnVong1);
                for (String s : listDoNghieng_V1)
                {popupMenu.getMenu().add(s);}
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listDoNghieng_V1.indexOf(menuItem.getTitle().toString());
                        DialogDoNghieng_V1(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnVong2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnVong2);
                for (String s : listDoNghieng_V2)
                {popupMenu.getMenu().add(s);}
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listDoNghieng_V2.indexOf(menuItem.getTitle().toString());
                        DialogDoNghieng_V2(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnVong3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnVong3);
                for (String s : listDoNghieng_V3)
                {popupMenu.getMenu().add(s);}
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listDoNghieng_V3.indexOf(menuItem.getTitle().toString());
                        DialogDoNghieng_V3(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnKetQuaDoNghieng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogKetQuaDoNghieng();
            }
        });
        //endregion
    }
    public void click_DialogTTC(final Dialog dialog){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(0,"","");
            }
        });

        tvGiaChongXoayTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {   try {
                final ArrayList<String> listgiachongxoay = new ArrayList<String>();
                int SoTangDay = Integer.parseInt(tvSoTangDayTTC.getText().toString().replace(" ", "").trim());
                for (int i = 1; i <= SoTangDay; i++) {
                    listgiachongxoay.add(String.valueOf(i));
                }
                SetPopup2(listgiachongxoay, TableBiaTTC, 13, 1);
            }catch (Exception e)

            {

            }
            }

        });
        tvSoDotTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNumeric(tvChieuCaoDotTTC.getText().toString()) && isNumeric(tvDoCaoTTC.getText().toString()))
                {
                    int ChieucaoDot = Integer.parseInt(tvChieuCaoDotTTC.getText().toString().replace(" ", "").trim());
                    int DoCaoCot = Integer.parseInt(tvDoCaoTTC.getText().toString().replace(" ", "").trim());
                    SoDot = (DoCaoCot / ChieucaoDot);
                    tvSoDotTTC.setText(String.valueOf(SoDot));
                }
                else Toast.makeText(getApplicationContext(),"Dữ liệu phải là số!", Toast.LENGTH_SHORT).show();

            }
        });

        btnLuuTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!tvSoMongTTC.getText().toString().trim().equals("") || !tvSoTangDayTTC.getText().toString().trim().equals("") || !tvSoChanCotTTC.getText().toString().trim().equals("")
                        || !tvChieuCaoDotTTC.getText().toString().trim().equals("")  || !tvDoCaoTTC.getText().toString().trim().equals("") ||!tvSoDotTTC.getText().toString().trim().equals("") ||!tvKichThuocCotTTC.getText().toString().trim().equals("") )
                {
                    if (isNumeric(tvSoMongTTC.getText().toString()) && isNumeric(tvSoTangDayTTC.getText().toString()) && isNumeric(tvSoChanCotTTC.getText().toString())
                            && isNumeric(tvChieuCaoDotTTC.getText().toString())  && isNumeric(tvDoCaoTTC.getText().toString()) && isNumeric(tvSoDotTTC.getText().toString()))
                    {
                        String dataTableBia = GetTableData(TableBiaTTC, 2);
                        saveDataOnCacher(dataTableBia, "TABLEBia");
                        Intent intent3 = new Intent(FormMain.this, FormMain.class);
                        intent3.putExtra("TenTram", MaTram);  // Truyền một String
                        intent3.putExtra("SoMong", tvSoMongTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("SoDot", tvSoDotTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("ViTriDat", tvViTriDatTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("SoTangDay",tvSoTangDayTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("KichThuocCot", tvKichThuocCotTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("SoChanCot",tvSoChanCotTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("GiaChongXoay",tvGiaChongXoayTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("ChieuCaoDot",tvChieuCaoDotTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("MacBeTong",tbBiaMacBeTong.getText().toString().replace(" ", "").trim());  // Truyền một String
                        intent3.putExtra("KichThuocMong", "0.5x0.5x0.58");  // Truyền một String

                        intent3.putExtra("Lat", Latitude);  // Truyền một String
                        intent3.putExtra("Long", Logiest);  // Truyền một String
                        intent3.putExtra("ViTri", LocalCity);  // Truyền một String

                        finish();
                        startActivity(intent3);
                        dialog.dismiss();
                    }
                    else Toast.makeText(getApplicationContext(),"Dữ liệu phải là số!", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(),"Bạn chưa nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();


            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public void click_DialogMong(final int vitri){
        //region KÍCH THƯỚC MONG + BÊ TÔNG

        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(8,tvTenMong.getText().toString(),edtHienTrang.getText().toString());
            }
        });
        btnChupCuongDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(4,tvTenMong.getText().toString(),"");
            }
        });
        btnChupLucCang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(2,tvTenMong.getText().toString(),"");
            }
        });
        btnChupLucSiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(3,tvTenMong.getText().toString(),"");
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE)
                {lauoutKichThuoc.setVisibility(View.VISIBLE);lauoutKichThuocBulong.setVisibility(View.GONE);lauoutCuongDo.setVisibility(View.GONE);layoutLucSiet.setVisibility(View.GONE);lauoutKichThuocMocCo.setVisibility(View.GONE);layoutLucCang.setVisibility(View.GONE);}
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });
        btnlauoutKichThuocBulong.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuocBulong.getVisibility() == View.GONE)
                {lauoutKichThuocBulong.setVisibility(View.VISIBLE);lauoutKichThuoc.setVisibility(View.GONE);lauoutCuongDo.setVisibility(View.GONE);layoutLucSiet.setVisibility(View.GONE);lauoutKichThuocMocCo.setVisibility(View.GONE);layoutLucCang.setVisibility(View.GONE);}
                else lauoutKichThuocBulong.setVisibility(View.GONE);
            }
        });

        btnlauoutCuongDo.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutCuongDo.getVisibility() == View.GONE)
                {lauoutCuongDo.setVisibility(View.VISIBLE);lauoutKichThuocBulong.setVisibility(View.GONE);lauoutKichThuoc.setVisibility(View.GONE);layoutLucSiet.setVisibility(View.GONE);lauoutKichThuocMocCo.setVisibility(View.GONE);layoutLucCang.setVisibility(View.GONE);}
                else lauoutCuongDo.setVisibility(View.GONE);
            }
        });

        btnHienLucSiet.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (layoutLucSiet.getVisibility() == View.GONE)
                {lauoutCuongDo.setVisibility(View.GONE);lauoutKichThuocBulong.setVisibility(View.GONE);lauoutKichThuoc.setVisibility(View.GONE);layoutLucSiet.setVisibility(View.VISIBLE);lauoutKichThuocMocCo.setVisibility(View.GONE);layoutLucCang.setVisibility(View.GONE);}
                else layoutLucSiet.setVisibility(View.GONE);
            }
        });

        btnlauoutKichThuocMocCo.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuocMocCo.getVisibility() == View.GONE)
                {lauoutKichThuocMocCo.setVisibility(View.VISIBLE);lauoutKichThuocBulong.setVisibility(View.GONE);lauoutKichThuoc.setVisibility(View.GONE);lauoutCuongDo.setVisibility(View.GONE);layoutLucCang.setVisibility(View.GONE);layoutLucSiet.setVisibility(View.GONE);}
                else lauoutKichThuocMocCo.setVisibility(View.GONE);
            }
        });
        btnHienLucCang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (layoutLucCang.getVisibility() == View.GONE)
                {lauoutCuongDo.setVisibility(View.GONE);lauoutKichThuocBulong.setVisibility(View.GONE);lauoutKichThuoc.setVisibility(View.GONE);layoutLucCang.setVisibility(View.VISIBLE);lauoutKichThuocMocCo.setVisibility(View.GONE);layoutLucSiet.setVisibility(View.GONE);}
                else layoutLucCang.setVisibility(View.GONE);
            }
        });

        edtLanDo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().trim().equals(""))
                    if(isNumeric(s.toString()))
                    {
                        lando = Integer.parseInt(s.toString());
                        if(lando>0 && lando<17)
                            edtGiaTriDo.setText( data_Table3[vitri][lando]);
                    }
            }
        });
        edtLoaiMay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, edtLoaiMay);
                for (String s : listLoaiMayDo)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        edtLoaiMay.setText(menuItem.getTitle());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lando<16) {
                    if(!edtGiaTriDo.getText().toString().trim().equals("")) {
                        lando = Integer.parseInt(edtLanDo.getText().toString());
                        boolean boo = isNumeric(edtGiaTriDo.getText().toString().replace(",", ".").trim());
                        if(boo && Double.valueOf(edtGiaTriDo.getText().toString().replace(",", ".").trim()) <=100)
                        {
                            data_Table3[vitri][lando] = edtGiaTriDo.getText().toString().replace(",", ".").trim();
                            tvGiaTriTrungBinh.setText("0");
                            data_Table3[vitri][17] = tvGiaTriTrungBinh.getText().toString();
                            data_Table3[vitri][18] = edtGocBan.getText().toString();
                            Double GTTB = 0.0;
                            for (int i=1;i<=lando;i++)
                            {
                                GTTB = GTTB + Double.valueOf(data_Table3[vitri][i]) ;
                            }
                            DecimalFormat format = new DecimalFormat("0.00");
                            double giatritrungbinh = GTTB/lando;
                            double macbetong = Double.valueOf(MacBeTong.replace("B",""));
                            tvGiaTriTrungBinh.setText(format.format(giatritrungbinh));

                            if(giatritrungbinh < macbetong)
                            {
                                edtDanhGia.setText("Không đạt");
                                edtDanhGia.setTextColor(getResources().getColor(R.color.colorRed));
                            }
                            else
                            {
                                edtDanhGia.setText("Đạt");
                                edtDanhGia.setTextColor(getResources().getColor(R.color.colorGreen));
                            }
                            lando++;
                            edtLanDo.setText(String.valueOf(lando));
                            edtGiaTriDo.setText(data_Table3[vitri][lando]);
                            set_DataTable3(vitri);
                            saveDataOnCacher(edtLoaiMay.getText().toString(),"LoaiMayDo");
                        } else Toast.makeText(FormMain.this, "Kiểm tra lại giá trị đo!", Toast.LENGTH_LONG).show();

                    }else Toast.makeText(FormMain.this, "Không được để trống dữ liệu!", Toast.LENGTH_LONG).show();
                }
                else if (lando==16)
                {
                    if(!edtGiaTriDo.getText().toString().trim().equals("")) {
                        lando = Integer.parseInt(edtLanDo.getText().toString());
                        boolean boo = isNumeric(String.valueOf(edtGiaTriDo.getText().toString().replace(",", ".").trim()));
                        if(boo)
                        {
                            data_Table3[vitri][lando] = edtGiaTriDo.getText().toString().replace(",", ".").trim();
                            Double GTTB = 0.0;
                            for (int i=1;i<=lando;i++)
                            {
                                GTTB = GTTB + Double.valueOf(data_Table3[vitri][i]) ;
                            }
                            DecimalFormat format = new DecimalFormat("0.00");
                            double giatritrungbinh = GTTB/lando;
                            double macbetong = Double.valueOf(MacBeTong.replace("B",""));
                            tvGiaTriTrungBinh.setText(format.format(giatritrungbinh));

                            if(giatritrungbinh < macbetong)
                            {
                                edtDanhGia.setText("Không đạt");
                                edtDanhGia.setTextColor(getResources().getColor(R.color.colorRed));
                            }
                            else
                            {
                                edtDanhGia.setText("Đạt");
                                edtDanhGia.setTextColor(getResources().getColor(R.color.colorGreen));
                            }
                            data_Table3[vitri][17] = tvGiaTriTrungBinh.getText().toString();
                            data_Table3[vitri][18] = edtGocBan.getText().toString();
                            edtLanDo.setText(String.valueOf(lando));
                            edtGiaTriDo.setText(data_Table3[vitri][lando]);
                            set_DataTable3(vitri);
                            Toast.makeText(FormMain.this, "Đã lưu!", Toast.LENGTH_LONG).show();
                        } else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();

                    }else Toast.makeText(FormMain.this, "Không được để trống dữ liệu!", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lando>=2)
                {
                    lando = Integer.parseInt(edtLanDo.getText().toString());

                        data_Table3[vitri][lando] = edtGiaTriDo.getText().toString().replace(",", ".").trim();
                        lando--;
                        edtLanDo.setText(String.valueOf(lando));
                        edtGiaTriDo.setText(data_Table3[vitri][lando]);
                        set_DataTable3(vitri);
                        Toast.makeText(FormMain.this, "Đã lưu!", Toast.LENGTH_LONG).show();

                }
            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, tvTenMong);
                for (String s : listMong)
                { popupMenu.getMenu().add(s);}
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        if (!menuItem.getTitle().toString().contains("Dầm"))
                        {
                            char chr = menuItem.getTitle().toString().charAt(menuItem.getTitle().toString().length()-1);
                            final boolean boo = isNumeric(String.valueOf(chr));
                            if (boo) {
                                vitriMong = Integer.parseInt(String.valueOf(chr));
                                dialog.dismiss();
                                DialogMong(menuItem.getTitle().toString(),vitriMong+1);
                                //Toast.makeText(FormMain.this, String.valueOf(chr), Toast.LENGTH_LONG).show();
                            }
                        }
                        //searchAutoCompleteCT.setText(menuItem.getTitle().toString());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        //endregion
        //region DO LỰC SIẾT
        btnHienBangMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutTable2.getVisibility() == View.GONE) {layoutTable2.setVisibility(View.VISIBLE);layoutTable3.setVisibility(View.GONE);layoutTable8.setVisibility(View.GONE);layoutTable9.setVisibility(View.GONE);}
                else layoutTable2.setVisibility(View.GONE);
            }
        });
        btnHienBangCuongDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutTable3.getVisibility() == View.GONE) {layoutTable3.setVisibility(View.VISIBLE);layoutTable2.setVisibility(View.GONE);layoutTable8.setVisibility(View.GONE);layoutTable9.setVisibility(View.GONE);}
                else layoutTable3.setVisibility(View.GONE);
            }
        });
        btnHienBangLucSiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutTable8.getVisibility() == View.GONE) {layoutTable8.setVisibility(View.VISIBLE);layoutTable2.setVisibility(View.GONE);layoutTable3.setVisibility(View.GONE);layoutTable9.setVisibility(View.GONE);}
                else layoutTable8.setVisibility(View.GONE);
            }
        });
        btnHienBangLucCang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutTable9.getVisibility() == View.GONE) {layoutTable9.setVisibility(View.VISIBLE);layoutTable2.setVisibility(View.GONE);layoutTable3.setVisibility(View.GONE);layoutTable8.setVisibility(View.GONE);}
                else layoutTable9.setVisibility(View.GONE);
            }
        });
        //endregion
        //region BUTTON LƯU
        edtGocBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] string = {"-90","0","90"};
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, edtGocBan);
                for (String s : string)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        edtGocBan.setText(menuItem.getTitle());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        btnLuuCuongDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lauoutKichThuoc.setVisibility(View.GONE);
                //lauoutCuongDo.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);
                //layoutLucSiet.setVisibility(View.GONE);

                lando = Integer.parseInt(edtLanDo.getText().toString());
                data_Table3[vitri][lando] = edtGiaTriDo.getText().toString().replace(",", ".").trim();
                data_Table3[vitri][17] = tvGiaTriTrungBinh.getText().toString();
                data_Table3[vitri][18] = edtGocBan.getText().toString();
                saveDataOnCacher(edtLoaiMay.getText().toString(),"LoaiMayDo");
                set_DataTable3(vitri);
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();
            }
        });
        btnLuuLucCang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lauoutKichThuoc.setVisibility(View.GONE);
                //lauoutCuongDo.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);
                //layoutLucSiet.setVisibility(View.GONE);
                //layoutLucCang.setVisibility(View.GONE);
                if(vitri!=1) {
                    for (int j=0;j<SoTangDay+SoGaChongXoay;j++)
                    {
                        if (String.valueOf(data_Table9[j+1][1+(vitri-1)*2-1]).contains("ull"))
                            data_Table9[j+1][(vitri-1)*2-1]="0";
                        if (isNumeric(String.valueOf(listEdtLucCang.get(j).getText().toString().replace(",", ".").trim())))
                        data_Table9[j+1][1+(vitri-1)*2] =listEdtLucCang.get(j).getText().toString();
                        else Toast.makeText(FormMain.this, listEdtLucCang.get(j).getText().toString()+" :Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    }
                }
                set_DataTable9(vitri);
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });
        btnLuuLucSiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lauoutKichThuoc.setVisibility(View.GONE);
                //lauoutCuongDo.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);
                //layoutLucSiet.setVisibility(View.GONE);
                if(vitri!=1) {
                    for (int j=0;j<SoTangDay+SoGaChongXoay;j++)
                    {

                        for (int i=1;i<=(vitri-1)*2;i++)
                        {
                            if (String.valueOf(data_Table8[j+1][i]).contains("ull"))
                                data_Table8[j+1][i]="";
                        }
                        if (isNumeric(String.valueOf(listEdtLucSiet.get(j).getText().toString().replace(",", ".").trim())))
                        data_Table8[j+1][(vitri-1)*2] =listEdtLucSiet.get(j).getText().toString();
                        else Toast.makeText(FormMain.this, listEdtLucSiet.get(j).getText().toString()+" :Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    }
                }
                set_DataTable8_Duoi(vitri);
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });
        btnLuuMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lauoutKichThuoc.setVisibility(View.GONE);
                //lauoutCuongDo.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);
                //layoutLucSiet.setVisibility(View.GONE);
               LuuHienTrangBeTong();
                LuuDeXuatBeTong();
                data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                data_Table2[vitri][2]= textHT;
                data_Table2[vitri][3]= textDX;
                set_DataTable2(vitri);
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

                if (!KichThuocMong.contains(edtKichThuoc.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuoc.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUp();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }

            }
        });
        //endregion
        //region Điều Hướng hiện trạng
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listHienTrang_copy.clear();
                listDeXuat_copy.clear();
                listHienTrang_copy.addAll(listHienTrang);
                listDeXuat_copy.addAll(listDeXuat) ;
                textCopy = edtKichThuoc.getText().toString() + "_" ;
                if (!textCopy.equals("")) btnPaste.setVisibility(View.VISIBLE);
                Toast.makeText(FormMain.this, "Đã sao chép!", Toast.LENGTH_LONG).show();
            }
        });
        btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormMain.this);
                builder.setTitle("Bạn có muốn dán dữ liệu từ cấu kiện trước?");
                // add the buttons
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String[] text = textCopy.split("_");
                        edtKichThuoc.setText(text[0]);
                        //edtHienTrang.setText(text[1]);
                        listHienTrang.clear();
                        listDeXuat.clear();
                        listHienTrang.addAll(listHienTrang_copy);
                        listDeXuat.addAll(listDeXuat_copy);
                        edtHienTrang.setText(listHienTrang.get(1).replace("\n",""));
                        edtDeXuat.setText(listDeXuat.get(1).replace("\n",""));
                        //Lưu số liệu
                        LuuHienTrangBeTong();
                        LuuDeXuatBeTong();

                        data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                        data_Table2[vitri][2]= textHT;
                        data_Table2[vitri][3]= textDX;

                        set_DataTable2(vitri);
                        Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();
                        //
                    }
                });
                builder.setNegativeButton("Không", null);
                // create and show the alert dialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LuuHienTrangBeTong();
                DialogHientrangCheckBox(edtHienTrang,"HienTrangBeTong","");
            }
        });
        btnSearchDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuDeXuatBeTong();
                DialogHientrangCheckBoxDX(edtDeXuat,"DeXuatBeTong");
            }
        });
        btnNextDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listDeXuat.set(vitriDeXuat,edtDeXuat.getText().toString().replace("\n","").replace(" -",""));
                    vitriDeXuat++;
                    if (vitriDeXuat<listDeXuat.size())
                    {
                        edtDeXuat.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
                        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriDeXuat) + "/" + String.valueOf(listDeXuat.size()));
                    }
                    LuuHienTrangBeTong();
                    LuuDeXuatBeTong();

                    data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                    data_Table2[vitri][2]= textHT;
                    data_Table2[vitri][3]= textDX;
                    set_DataTable2(vitri);}catch (Exception e){}
            }
        });
        btnBackDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriDeXuat>=2){
                        listDeXuat.set(vitriDeXuat,edtDeXuat.getText().toString().replace("\n","").replace(" -",""));
                        vitriDeXuat--;
                        if (vitriHienTrang>0)
                        {
                            edtDeXuat.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
                            tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriDeXuat) + "/" + String.valueOf(listDeXuat.size()));
                        }
                        LuuHienTrangBeTong();
                        LuuDeXuatBeTong();

                        data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                        data_Table2[vitri][2]= textHT;
                        data_Table2[vitri][3]= textDX;
                        set_DataTable2(vitri);}
                }
                catch (Exception e){}
            }
        });
        btnNextHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listHienTrang.set(vitriHienTrang,edtHienTrang.getText().toString().trim().replace("\n","").replace(" -",""));
                    vitriHienTrang++;
                    if (vitriHienTrang<listHienTrang.size())
                    {
                        edtHienTrang.setText(listHienTrang.get(vitriHienTrang).trim().replace("\n",""));
                        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
                        LuuHienTrangBeTong();
                        LuuDeXuatBeTong();

                        data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                        data_Table2[vitri][2]= textHT;
                        data_Table2[vitri][3]= textDX;
                        set_DataTable2(vitri);
                    }
                }catch (Exception e){}

            }
        });
        btnBackHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriHienTrang>=2){
                        listHienTrang.set(vitriHienTrang,edtHienTrang.getText().toString().trim().replace("\n","").replace(" -",""));
                        vitriHienTrang--;
                        edtHienTrang.setText(listHienTrang.get(vitriHienTrang).replace("\n","").trim());
                        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));

                        LuuHienTrangBeTong();
                        LuuDeXuatBeTong();

                        data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                        data_Table2[vitri][2]= textHT;
                        data_Table2[vitri][3]= textDX;
                        set_DataTable2(vitri);}
                }
                catch (Exception e){}
            }
        });

        //endregion
        //region PHỤ KIỆN CỘT
        btnKichThuocPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutCuongDo.setVisibility(View.GONE);
                lauoutKichThuocBulong.setVisibility(View.GONE);
                lauoutKichThuocMocCo.setVisibility(View.GONE);
                layoutLucSiet.setVisibility(View.GONE);
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnKichThuocPhuKien);
                for (String s : listPhuKienCotDauDuoi)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int vitri =0;
                        for (int i= 0;i<listPhuKienCot.size();i++)
                        {
                            if (menuItem.getTitle().toString().contains(listPhuKienCot.get(i)))
                            {
                                vitri = i;
                                break;
                            }
                        }
                        DialogPhuKien(menuItem.getTitle().toString(),vitri+1,listPhuKienCotDauDuoi);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        //endregion
        click_DialogMocCo(vitri);
        click_DialogBulong(SoMong+2);
        btnThietBiMatDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutCuongDo.setVisibility(View.GONE);
                lauoutKichThuocBulong.setVisibility(View.GONE);
                lauoutKichThuocMocCo.setVisibility(View.GONE);
                layoutLucSiet.setVisibility(View.GONE);
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnThietBiMatDat);
                for (String s : listCauKienTiepDia)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
//                        int index = listCauKienTiepDiaTable.indexOf(menuItem.getTitle().toString());
//                        //Toast.makeText(getApplicationContext(),String.valueOf(index), Toast.LENGTH_SHORT).show();
//                        DialogTiepDia(menuItem.getTitle().toString(),index+1);
                        int vitri =0;
                        for (int i= 0;i<listCauKienTiepDiaTable.size();i++)
                        {
                            if (menuItem.getTitle().toString().contains(listCauKienTiepDiaTable.get(i)))
                            {
                                vitri = i;
                                break;
                            }
                        }
                        DialogTiepDia(menuItem.getTitle().toString(),vitri+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    };
    public void click_DialogDam(final int vitri){
    //region ĐO BÊ TÔNG

        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(8,tvTenMong.getText().toString(),edtHienTrang.getText().toString());
            }
        });
        btnChupCuongDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(4,tvTenMong.getText().toString(),edtHienTrang.getText().toString());
            }
        });
        edtLanDo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().trim().equals(""))
                    if(isNumeric(s.toString()))
                    {
                        lando = Integer.parseInt(s.toString());
                        if(lando>0 && lando<17)
                            edtGiaTriDo.setText( data_Table3[vitri-2][lando]);
                    }
            }
        });
        edtGocBan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                String[] string = {"-90","0","90"};
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, edtGocBan);
                for (String s : string)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        edtGocBan.setText(menuItem.getTitle());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE)
                {lauoutKichThuoc.setVisibility(View.VISIBLE);lauoutCuongDo.setVisibility(View.GONE);}
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });
        btnlauoutCuongDo.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutCuongDo.getVisibility() == View.GONE) {lauoutCuongDo.setVisibility(View.VISIBLE);lauoutKichThuoc.setVisibility(View.GONE);}
                else lauoutCuongDo.setVisibility(View.GONE);
            }
        });
        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lando<16) {
                    if(!edtGiaTriDo.getText().toString().trim().equals("")) {
                        lando = Integer.parseInt(edtLanDo.getText().toString());
                        boolean boo = isNumeric(String.valueOf(edtGiaTriDo.getText().toString().replace(",", ".").trim()));
                        if(boo)
                        {
                            data_Table3[vitri-2][lando] = edtGiaTriDo.getText().toString().replace(",", ".").trim();
                            data_Table3[vitri-2][18] = edtGocBan.getText().toString();
                            lando++;
                            edtLanDo.setText(String.valueOf(lando));
                            edtGiaTriDo.setText(data_Table3[vitri-2][lando]);
                            set_DataTable3(vitri-2);
                            Toast.makeText(FormMain.this, "Đã lưu!", Toast.LENGTH_LONG).show();

                        } else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    }else Toast.makeText(FormMain.this, "Không được để trống dữ liệu!", Toast.LENGTH_LONG).show();

                }
                else if (lando==16)
                {
                    if(!edtGiaTriDo.getText().toString().trim().equals("")) {
                        lando = Integer.parseInt(edtLanDo.getText().toString());
                        boolean boo = isNumeric(String.valueOf(edtGiaTriDo.getText().toString().replace(",", ".").trim()));
                        if(boo)
                        {
                            data_Table3[vitri-2][lando] = edtGiaTriDo.getText().toString().replace(",", ".").trim();
                            data_Table3[vitri-2][18] = edtGocBan.getText().toString();
                            edtLanDo.setText(String.valueOf(lando));
                            edtGiaTriDo.setText(data_Table3[vitri-2][lando]);
                            set_DataTable3(vitri-2);
                            Toast.makeText(FormMain.this, "Đã lưu!", Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    }else Toast.makeText(FormMain.this, "Không được để trống dữ liệu!", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lando>=2) {

                    lando = Integer.parseInt(edtLanDo.getText().toString());
                    boolean boo = isNumeric(String.valueOf(edtGiaTriDo.getText().toString().replace(",", ".").trim()));
                    if(boo)
                    {
                        data_Table3[vitri-2][lando] = edtGiaTriDo.getText().toString().replace(",", ".").trim();
                        lando--;
                        edtLanDo.setText(String.valueOf(lando));
                        edtGiaTriDo.setText(data_Table3[vitri-2][lando]);
                        set_DataTable3(vitri-2);
                        Toast.makeText(FormMain.this, "Đã lưu!", Toast.LENGTH_LONG).show();

                    } else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();

                }
            }
        });
        btnLuuCuongDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lauoutKichThuoc.setVisibility(View.GONE);
                //lauoutCuongDo.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);
                //layoutLucSiet.setVisibility(View.GONE);
                //LƯU TÊN DẦM
                //listDam.set(vitriDam,tvTenMong.getText().toString());
                data_Table3[vitri-2][0] =tvTenMong.getText().toString().replace(",", ".").trim();
                //LƯU KICH THƯỚC DẦM
                data_Table2[vitri][0] =tvTenMong.getText().toString().trim();

                //LƯU CƯƠNG ĐỘ BÊ TÔNG
                lando = Integer.parseInt(edtLanDo.getText().toString());
                data_Table3[vitri-2][lando] = edtGiaTriDo.getText().toString().replace(",", ".").trim();

                data_Table3[vitri-2][18] = edtGocBan.getText().toString().replace(",", ".").trim();

                set_DataTable3(vitri-2);
            }
        });

//endregion
    //region HIỆN TRẠNG VÀ KÍCH THƯỚC
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHienTrangBeTong();
                DialogHientrangCheckBox(edtHienTrang,"HienTrangBeTong","");
            }
        });
        btnSearchDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuDeXuatBeTong();
                DialogHientrangCheckBoxDX(edtDeXuat,"DeXuatBeTong");
            }
        });
        btnNextDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listDeXuat.set(vitriDeXuat,edtDeXuat.getText().toString().replace("\n","").replace(" -",""));
                    vitriDeXuat++;
                    if (vitriDeXuat<listDeXuat.size())
                    {
                        edtDeXuat.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
                        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriDeXuat) + "/" + String.valueOf(listDeXuat.size()));
                    }
                    LuuHienTrangBeTong();
                    LuuDeXuatBeTong();

                    data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                    data_Table2[vitri][2]= textHT;
                    data_Table2[vitri][3]= textDX;
                    set_DataTable2(vitri);}catch (Exception e){}
            }
        });
        btnBackDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriDeXuat>=2){
                        listDeXuat.set(vitriDeXuat,edtDeXuat.getText().toString().replace("\n","").replace(" -",""));
                        vitriDeXuat--;
                        if (vitriHienTrang>0)
                        {
                            edtDeXuat.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
                            tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriDeXuat) + "/" + String.valueOf(listDeXuat.size()));
                        }
                        LuuHienTrangBeTong();
                        LuuDeXuatBeTong();

                        data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                        data_Table2[vitri][2]= textHT;
                        data_Table2[vitri][3]= textDX;
                        set_DataTable2(vitri);}
                }
                catch (Exception e){}
            }
        });
        btnNextHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listHienTrang.set(vitriHienTrang,edtHienTrang.getText().toString().replace("\n","").replace(" -",""));
                    vitriHienTrang++;
                    if (vitriHienTrang<listHienTrang.size())
                    {
                        edtHienTrang.setText(listHienTrang.get(vitriHienTrang).replace("\n",""));
                        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));

                    }
                    LuuHienTrangBeTong();
                    LuuDeXuatBeTong();

                    data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                    data_Table2[vitri][2]= textHT;
                    data_Table2[vitri][3]= textDX;
                    set_DataTable2(vitri);}catch (Exception e){}
            }
        });
        btnBackHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriHienTrang>=2){
                        listHienTrang.set(vitriHienTrang,edtHienTrang.getText().toString().replace("\n","").replace(" -",""));
                        vitriHienTrang--;
                        edtHienTrang.setText(listHienTrang.get(vitriHienTrang).replace("\n",""));
                        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));

                        LuuHienTrangBeTong();
                        LuuDeXuatBeTong();

                        data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                        data_Table2[vitri][2]= textHT;
                        data_Table2[vitri][3]= textDX;
                        set_DataTable2(vitri);}
                }
                catch (Exception e){}
            }
        });
        btnLuuMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutCuongDo.setVisibility(View.GONE);
                lauoutBang.setVisibility(View.GONE);

                LuuHienTrangBeTong();
                LuuDeXuatBeTong();

                data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                data_Table2[vitri][2]= textHT;
                data_Table2[vitri][3]= textDX;

                set_DataTable2(vitri);
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

                if (!KichThuocMong.contains(edtKichThuoc.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuoc.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUp();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }

            }
        });
        //endregion
    };
    public void click_DialogMocCo(final int vitri){

        btnChupHienTrangMocCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(8,"Móc co " +tvTenMong.getText().toString(),edtHienTrangMocCo.getText().toString());
            }
        });
        //region HIỆN TRẠNG VÀ KÍCH THƯỚC
        btnSearchMocCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHienTrangThepMocCo();
                DialogHientrangCheckBoxMocCo(edtHienTrangMocCo,"HienTrangThep","Móc co");
            }
        });
        btnSearchDXMocCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuDeXuatThepMocCo();
                DialogHientrangCheckBoxDXMocCo(edtDeXuatMocCo,"DeXuatThep");
            }
        });
        btnNextDXMocCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listDeXuatMocCo.set(vitriDeXuatMocCo,edtDeXuatMocCo.getText().toString().replace("\n","").replace(" -",""));
                    vitriDeXuatMocCo++;
                    if (vitriDeXuatMocCo<listDeXuatMocCo.size())
                    {
                        edtDeXuatMocCo.setText(listDeXuatMocCo.get(vitriDeXuatMocCo).replace("\n",""));
                        tvViTriDeXuatMocCo.setText("Đề xuất: " + String.valueOf(vitriDeXuatMocCo) + "/" + String.valueOf(listDeXuatMocCo.size()));
                    }
                    LuuHienTrangThepMocCo();
                    LuuDeXuatThepMocCo();

                    //data_Table2[SoMong +3][1] = edtKichThuocMocCo.getText().toString();
                    //data_Table2[SoMong +3][2]= textHTMocCo;
                    data_Table2[SoMong +3][3]= textDXMocCo;
                    set_DataTable2(SoMong +3);}catch (Exception e){}
            }
        });
        btnBackDXMocCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriDeXuatMocCo>=2){
                        listDeXuatMocCo.set(vitriDeXuatMocCo,edtDeXuatMocCo.getText().toString().replace("\n","").replace(" -",""));
                        vitriDeXuatMocCo--;
                        if (vitriHienTrangMocCo>0)
                        {
                            edtDeXuatMocCo.setText(listDeXuatMocCo.get(vitriDeXuatMocCo).replace("\n",""));
                            tvViTriDeXuatMocCo.setText("Đề xuất: " + String.valueOf(vitriDeXuatMocCo) + "/" + String.valueOf(listDeXuatMocCo.size()));
                        }
                        LuuHienTrangThepMocCo();
                        LuuDeXuatThepMocCo();

                        //[SoMong +3][1] = edtKichThuocMocCo.getText().toString();
                        //data_Table2[SoMong +3][2]= textHTMocCo;
                        data_Table2[SoMong +3][3]= textDXMocCo;
                        set_DataTable2(SoMong +3);}
                }
                catch (Exception e){}
            }
        });
        btnNextHienTrangMocCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listHienTrangMocCo.set(vitriHienTrangMocCo,edtHienTrangMocCo.getText().toString().replace("\n","").replace(" -",""));
                    vitriHienTrangMocCo++;
                    if (vitriHienTrangMocCo<listHienTrangMocCo.size())
                    {
                        edtHienTrangMocCo.setText(listHienTrangMocCo.get(vitriHienTrangMocCo).replace("\n",""));
                        tvViTriHienTrangMocCo.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangMocCo) + "/" + String.valueOf(listHienTrangMocCo.size()));
                    }
                    LuuHienTrangThepMocCo();
                    LuuDeXuatThepMocCo();

                    //data_Table2[SoMong +3][1] = edtKichThuocMocCo.getText().toString();
                    //data_Table2[SoMong +3][2]= textHTMocCo;
                    data_Table2[SoMong +3][3]= textDXMocCo;
                    set_DataTable2(SoMong +3);}catch (Exception e){}
            }
        });
        btnBackHienTrangMocCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriHienTrangMocCo>=2){
                        listHienTrangMocCo.set(vitriHienTrangMocCo,edtHienTrangMocCo.getText().toString().replace("\n","").replace(" -",""));
                        vitriHienTrangMocCo--;
                        edtHienTrangMocCo.setText(listHienTrangMocCo.get(vitriHienTrangMocCo).replace("\n",""));
                        tvViTriHienTrangMocCo.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangMocCo) + "/" + String.valueOf(listHienTrangMocCo.size()));

                        LuuHienTrangThepMocCo();
                        LuuDeXuatThepMocCo();

                        //data_Table2[SoMong +3][1] = edtKichThuocMocCo.getText().toString();
                        //data_Table2[SoMong +3][2]= textHTMocCo;
                        data_Table2[SoMong +3][3]= textDXMocCo;
                        set_DataTable2(SoMong +3);}
                }
                catch (Exception e){}
            }
        });
        btnLuuMocCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lauoutKichThuocMocCo.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);
                LuuHienTrangThepMocCo();
                LuuDeXuatThepMocCo();
                //KÍCH THƯỚC
                if (String.valueOf(data_Table2[SoMong+3][1]).trim().equals("") || String.valueOf(data_Table2[SoMong+3][1]).trim().equals("null"))
                    data_Table2[SoMong+3][1]= "- Móng " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuocMocCo.getText().toString().replace("- ","");
                else
                {
                    //data_Table2[SoMong+3][1]=data_Table2[SoMong+3][1] +"\n" + "- " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuoc.getText().toString();
                    if (!data_Table2[SoMong+3][1].contains("Móng " +tvTenMong.getText().toString().split("Móng ")[1]))
                        data_Table2[SoMong+3][1]=data_Table2[SoMong+3][1] +"\n" + "- Móng " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuocMocCo.getText().toString().replace("- ","");
                    else
                    if(String.valueOf(data_Table2[SoMong+3][1]).contains(("- "))){
                        String[] dongHienTrang = data_Table2[SoMong+3][1].split(("- "));
                        for(int j=0;j<dongHienTrang.length;j++)
                        {
                            if (dongHienTrang[j].contains("Móng " +tvTenMong.getText().toString().split("Móng ")[1]))
                            {
                                //edtKichThuoc.setText(s.replace("- " +tvTenMong.getText().toString().split("Móng ")[1] +":" ,""));
                                dongHienTrang[j] = "Móng " +tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuocMocCo.getText().toString().replace("- ","").replace("\n","");
                                if (!KichThuocMong.contains(edtKichThuocMocCo.getText().toString().replace("- ",""))){
                                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocMocCo.getText().toString().replace("- ","")+"_";
                                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                                    setPopUp();
                                }
                                break;
                            }
                        }
                        data_Table2[SoMong+3][1]="";
                        for(String s:dongHienTrang)
                        {
                            if (!s.trim().equals(""))
                                if (String.valueOf(data_Table2[SoMong+3][1]).trim().equals("") || String.valueOf(data_Table2[SoMong+3][1]).trim().equals("null"))
                                    data_Table2[SoMong+3][1]= "- " + s.replace("\n","");
                                else
                                    data_Table2[SoMong+3][1]=data_Table2[SoMong+3][1] +"\n" + "- " + s.replace("\n","");
                        }
                    }
                }
                //HIỆN TRẠNG
                if (String.valueOf(data_Table2[SoMong+3][2]).trim().equals("") || String.valueOf(data_Table2[SoMong+3][2]).trim().equals("null"))
                    data_Table2[SoMong+3][2]= "Móng " + tvTenMong.getText().toString().split("Móng ")[1]+": \n" + textHTMocCo;
                else
                {
                    //int vitrihientrang = 0;
                    //data_Table2[SoMong+3][1]=data_Table2[SoMong+3][1] +"\n" + "- " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuoc.getText().toString();
                    if (!data_Table2[SoMong+3][2].contains("Móng " +tvTenMong.getText().toString().split("Móng ")[1]))
                        data_Table2[SoMong+3][2]=data_Table2[SoMong+3][2] +"\n" + "Móng " + tvTenMong.getText().toString().split("Móng ")[1]+": \n" + textHTMocCo;
                    else
                    if(String.valueOf(data_Table2[SoMong+3][2]).contains(("Móng "))){
                        String[] tachchuoilan1 = data_Table2[SoMong+3][2].split("Móng ");
                        for(int i=0;i<tachchuoilan1.length;i++)
                        {
                            String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                            if (tachchuoilan2[0].contains(tvTenMong.getText().toString().split("Móng ")[1]))
                            {
                                tachchuoilan1[i] = tachchuoilan2[0] + ":\n"+textHTMocCo;
                                break;
                            }
                        }
                        data_Table2[SoMong+3][2]="";
                        for(String s:tachchuoilan1)
                        {
                            if (!s.trim().equals(""))
                                if (String.valueOf(data_Table2[SoMong+3][2]).trim().equals("") || String.valueOf(data_Table2[SoMong+3][2]).trim().equals("null"))
                                    data_Table2[SoMong+3][2]= "Móng " + s;
                                else
                                    data_Table2[SoMong+3][2]=data_Table2[SoMong+3][2] + "Móng " + s;
                        }
                    }
                }
                //ĐỀ XUẤT
                if (String.valueOf(data_Table2[SoMong+3][3]).trim().equals("") || String.valueOf(data_Table2[SoMong+3][3]).trim().equals("null"))
                    data_Table2[SoMong+3][3]= "Móng " + tvTenMong.getText().toString().split("Móng ")[1]+": \n" + textDXMocCo;
                else
                {
                    //int vitrihientrang = 0;
                    //data_Table2[SoMong+3][1]=data_Table2[SoMong+3][1] +"\n" + "- " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuoc.getText().toString();
                    if (!data_Table2[SoMong+3][3].contains("Móng " +tvTenMong.getText().toString().split("Móng ")[1]))
                        data_Table2[SoMong+3][3]=data_Table2[SoMong+3][3] +"\n" + "Móng " + tvTenMong.getText().toString().split("Móng ")[1]+": \n" + textDXMocCo;
                    else
                    if(String.valueOf(data_Table2[SoMong+3][3]).contains(("Móng "))){
                        String[] tachchuoilan1 = data_Table2[SoMong+3][3].split("Móng ");
                        for(int i=0;i<tachchuoilan1.length;i++)
                        {
                            String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                            if (tachchuoilan2[0].contains(tvTenMong.getText().toString().split("Móng ")[1]))
                            {
                                tachchuoilan1[i] = tachchuoilan2[0] + ":\n"+textDXMocCo;
                                break;
                            }
                        }
                        data_Table2[SoMong+3][3]="";
                        for(String s:tachchuoilan1)
                        {
                            if (!s.trim().equals(""))
                                if (String.valueOf(data_Table2[SoMong+3][3]).trim().equals("") || String.valueOf(data_Table2[SoMong+3][3]).trim().equals("null"))
                                    data_Table2[SoMong+3][3]= "Móng " + s;
                                else
                                    data_Table2[SoMong+3][3]=data_Table2[SoMong+3][3] + "Móng " + s;
                        }
                    }
                }
                //data_Table2[SoMong+3][2]= textHTMocCo;
                //data_Table2[SoMong+3][3]= textDXMocCo;
                //set_DataTable7(vitri);

                //data_Table2[SoMong +3][1] = edtKichThuocMocCo.getText().toString();
                //data_Table2[SoMong +3][2]= textHTMocCo;
                //data_Table2[SoMong +3][3]= textDXMocCo;
                set_DataTable2(SoMong +3);
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

                if (!KichThuocMong.contains(edtKichThuocMocCo.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocMocCo.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUpMocCo();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }

            }
        });
        //endregion

    };
    public void click_DialogBulong(final int vitri){
        btnChupHienTrangBulong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(8,"Bu lông neo",edtHienTrangBulong.getText().toString());
            }
        });
        //region HIỆN TRẠNG VÀ KÍCH THƯỚC
        btnSearchBulong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHienTrangThepBulong();
                DialogHientrangCheckBoxBulong(edtHienTrangBulong,"HienTrangThep","Bu lông");
            }
        });
        btnSearchDXBulong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuDeXuatThepBulong();
                DialogHientrangCheckBoxDXBulong(edtDeXuatBulong,"DeXuatThep");
            }
        });
        btnNextDXBulong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listDeXuatBulong.set(vitriDeXuatBulong,edtDeXuatBulong.getText().toString().replace("\n","").replace(" -",""));
                    vitriDeXuatBulong++;
                    if (vitriDeXuatBulong<listDeXuatBulong.size())
                    {
                        edtDeXuatBulong.setText(listDeXuatBulong.get(vitriDeXuatBulong).replace("\n",""));
                        tvViTriDeXuatBulong.setText("Đề xuất: " + String.valueOf(vitriDeXuatBulong) + "/" + String.valueOf(listDeXuatBulong.size()));
                    }
                    LuuHienTrangThepBulong();
                    LuuDeXuatThepBulong();

                    data_Table2[vitri][1] = edtKichThuocBulong.getText().toString();
                    data_Table2[vitri][2]= textHTBulong;
                    data_Table2[vitri][3]= textDXBulong;
                    set_DataTable2(vitri);
                }catch (Exception e){}
            }
        });
        btnBackDXBulong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(vitriDeXuatBulong>=2){
                        listDeXuatBulong.set(vitriDeXuatBulong,edtDeXuatBulong.getText().toString().replace("\n","").replace(" -",""));
                        vitriDeXuatBulong--;
                        if (vitriHienTrangBulong>0)
                        {
                            edtDeXuatBulong.setText(listDeXuatBulong.get(vitriDeXuatBulong).replace("\n",""));
                            tvViTriDeXuatBulong.setText("Đề xuất: " + String.valueOf(vitriDeXuatBulong) + "/" + String.valueOf(listDeXuatBulong.size()));
                        }
                        LuuHienTrangThepBulong();
                        LuuDeXuatThepBulong();

                        data_Table2[vitri][1] = edtKichThuocBulong.getText().toString();
                        data_Table2[vitri][2]= textHTBulong;
                        data_Table2[vitri][3]= textDXBulong;
                        set_DataTable2(vitri);}
                }catch (Exception e){}
            }
        });
        btnNextHienTrangBulong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listHienTrangBulong.set(vitriHienTrangBulong,edtHienTrangBulong.getText().toString().replace("\n","").replace(" -",""));
                    vitriHienTrangBulong++;
                    if (vitriHienTrangBulong<listHienTrangBulong.size())
                    {
                        edtHienTrangBulong.setText(listHienTrangBulong.get(vitriHienTrangBulong).replace("\n",""));
                        tvViTriHienTrangBulong.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangBulong) + "/" + String.valueOf(listHienTrangBulong.size()));

                    }
                    LuuHienTrangThepBulong();
                    LuuDeXuatThepBulong();

                    data_Table2[vitri][1] = edtKichThuocBulong.getText().toString();
                    data_Table2[vitri][2]= textHTBulong;
                    data_Table2[vitri][3]= textDXBulong;
                    set_DataTable2(vitri);}catch (Exception e){}
            }
        });
        btnBackHienTrangBulong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(vitriHienTrangBulong>=2){
                        listHienTrangBulong.set(vitriHienTrangBulong,edtHienTrangBulong.getText().toString().replace("\n","").replace(" -",""));
                        vitriHienTrangBulong--;
                        edtHienTrangBulong.setText(listHienTrangBulong.get(vitriHienTrangBulong).replace("\n",""));
                        tvViTriHienTrangBulong.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangBulong) + "/" + String.valueOf(listHienTrangBulong.size()));

                        LuuHienTrangThepBulong();
                        LuuDeXuatThepBulong();

                        data_Table2[vitri][1] = edtKichThuocBulong.getText().toString();
                        data_Table2[vitri][2]= textHTBulong;
                        data_Table2[vitri][3]= textDXBulong;
                        set_DataTable2(vitri);}
                } catch (Exception e){}
            }
        });
        btnLuuBulong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lauoutKichThuoc.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);
                LuuHienTrangThepBulong();
                LuuDeXuatThepBulong();

                data_Table2[vitri][1] = edtKichThuocBulong.getText().toString();
                data_Table2[vitri][2]= textHTBulong;
                data_Table2[vitri][3]= textDXBulong;

                set_DataTable2(vitri);
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

                if (!KichThuocMong.contains(edtKichThuocBulong.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocBulong.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUpBulong();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }

            }
        });
        //endregion

    };
    public void click_DialogTiepDia(final int vitri){

        btnChupHienTrangCauKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(5,tvTenCauKien.getText().toString(),edtHienTrangCauKien.getText().toString());
            }
        });

        btnlauoutKichThuocCauKien.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupCauKien,autoTransition);
                if (lauoutKichThuocCauKien.getVisibility() == View.GONE) lauoutKichThuocCauKien.setVisibility(View.VISIBLE);
                else lauoutKichThuocCauKien.setVisibility(View.GONE);
            }
        });

        btnlauoutBangCauKien.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupCauKien,autoTransition);
                if (lauoutBangCauKien.getVisibility() == View.GONE) lauoutBangCauKien.setVisibility(View.VISIBLE);
                else lauoutBangCauKien.setVisibility(View.GONE);
            }
        });

        btnBackCauKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCauKien.dismiss();
            }
        });

        tvTenCauKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopupMenu popupMenu = new PopupMenu(FormMain.this, tvTenCauKien);
                for (String s : listCauKienTiepDia)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int vitri =0;
                        for (int i= 0;i<listCauKienTiepDiaTable.size();i++)
                        {
                            if (menuItem.getTitle().toString().contains(listCauKienTiepDiaTable.get(i)))
                            {
                                vitri = i;
                                break;
                            }
                        }
                        DialogTiepDia(menuItem.getTitle().toString(),vitri+1);
                        dialogCauKien.dismiss();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        //region HIỆN TRẠNG VÀ KÍCH THƯỚC
        btnSearchCauKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHienTrangThepCauKien();
                DialogHientrangCheckBoxCauKien(edtHienTrangCauKien,"HienTrangThep","Cấu kiện");
            }
        });
        btnSearchDXCauKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuDeXuatThepCauKien();
                DialogHientrangCheckBoxDXCauKien(edtDeXuatCauKien,"DeXuatThep");
            }
        });
        btnNextDXCauKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listDeXuatCauKien.set(vitriDeXuatCauKien,edtDeXuatCauKien.getText().toString().replace("\n","").replace(" -",""));
                    vitriDeXuatCauKien++;
                    if (vitriDeXuatCauKien<listDeXuatCauKien.size())
                    {
                        edtDeXuatCauKien.setText(listDeXuatCauKien.get(vitriDeXuatCauKien).replace("\n",""));
                        tvViTriDeXuatCauKien.setText("Đề xuất: " + String.valueOf(vitriDeXuatCauKien) + "/" + String.valueOf(listDeXuatCauKien.size()));
                    }
                    LuuHienTrangThepCauKien();
                    LuuDeXuatThepCauKien();

//                    data_Table4[vitri][1] = edtKichThuocCauKien.getText().toString();
//                    data_Table4[vitri][2]= textHTCauKien;
//                    data_Table4[vitri][3]= textDXCauKien;
//                    set_DataTable4(vitri);
                }catch (Exception e){}
            }
        });
        btnBackDXCauKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriDeXuatCauKien>=2){
                        listDeXuatCauKien.set(vitriDeXuatCauKien,edtDeXuatCauKien.getText().toString().replace("\n","").replace(" -",""));
                        vitriDeXuatCauKien--;
                        if (vitriHienTrangCauKien>0)
                        {
                            edtDeXuatCauKien.setText(listDeXuatCauKien.get(vitriDeXuatCauKien).replace("\n",""));
                            tvViTriDeXuatCauKien.setText("Đề xuất: " + String.valueOf(vitriDeXuatCauKien) + "/" + String.valueOf(listDeXuatCauKien.size()));
                        }
                        LuuHienTrangThepCauKien();
                        LuuDeXuatThepCauKien();

//                        data_Table4[vitri][1] = edtKichThuocCauKien.getText().toString();
//                        data_Table4[vitri][2]= textHTCauKien;
//                        data_Table4[vitri][3]= textDXCauKien;
//                        set_DataTable4(vitri);
                    }
                }
                catch (Exception e){}
            }
        });
        btnNextHienTrangCauKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listHienTrangCauKien.set(vitriHienTrangCauKien,edtHienTrangCauKien.getText().toString().replace("\n","").replace(" -",""));
                    vitriHienTrangCauKien++;
                    if (vitriHienTrangCauKien<listHienTrangCauKien.size())
                    {
                        edtHienTrangCauKien.setText(listHienTrangCauKien.get(vitriHienTrangCauKien).replace("\n",""));
                        tvViTriHienTrangCauKien.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangCauKien) + "/" + String.valueOf(listHienTrang.size()));

                    }
                    LuuHienTrangThepCauKien();
                    LuuDeXuatThepCauKien();

//                    data_Table4[vitri][1] = edtKichThuocCauKien.getText().toString();
//                    data_Table4[vitri][2]= textHTCauKien;
//                    data_Table4[vitri][3]= textDXCauKien;
//                    set_DataTable4(vitri);
                }catch (Exception e){}
            }
        });
        btnBackHienTrangCauKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriHienTrangCauKien>=2){
                        listHienTrangCauKien.set(vitriHienTrangCauKien,edtHienTrangCauKien.getText().toString().replace("\n","").replace(" -",""));
                        vitriHienTrangCauKien--;
                        edtHienTrangCauKien.setText(listHienTrangCauKien.get(vitriHienTrangCauKien).replace("\n",""));
                        tvViTriHienTrangCauKien.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangCauKien) + "/" + String.valueOf(listHienTrangCauKien.size()));

                        LuuHienTrangThepCauKien();
                        LuuDeXuatThepCauKien();

//                        data_Table4[vitri][1] = edtKichThuocCauKien.getText().toString();
//                        data_Table4[vitri][2]= textHTCauKien;
//                        data_Table4[vitri][3]= textDXCauKien;
//                        set_DataTable4(vitri);
                    }
                }
                catch (Exception e){}
            }
        });
        btnLuuCauKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lauoutKichThuoc.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);

                LuuHienTrangThepCauKien();
                LuuDeXuatThepCauKien();
                if (tvTenCauKien.getText().toString().contains("Móng")) LuuCauKienTiepDia(vitri);
                else
                {
                    data_Table4[vitri][1] = edtKichThuocCauKien.getText().toString();
                    data_Table4[vitri][2]= textHTCauKien;
                    data_Table4[vitri][3]= textDXCauKien;
                    set_DataTable4(vitri);
                }

                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

                if (!KichThuocMong.contains(edtKichThuocCauKien.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocCauKien.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUpCauKien();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }

            }
        });
        //endregion
    };
    public void click_DialogPhuKien(final int vitri,final ArrayList<String> listPhuKien){
        btnChupHienTrangPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(8,tvTenPhuKien.getText().toString(),edtHienTrangPhuKien.getText().toString());
            }
        });

        btnlauoutKichThuocPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (lauoutKichThuocPhuKien.getVisibility() == View.GONE) lauoutKichThuocPhuKien.setVisibility(View.VISIBLE);
                else lauoutKichThuocPhuKien.setVisibility(View.GONE);
            }
        });

        btnlauoutBangPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lauoutBangPhuKien.getVisibility() == View.GONE) lauoutBangPhuKien.setVisibility(View.VISIBLE);
                else lauoutBangPhuKien.setVisibility(View.GONE);
            }
        });

        btnThoatPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPhuKien.dismiss();
            }
        });

        tvTenPhuKien.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, tvTenPhuKien);
                for (String s : listPhuKien)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int vitri =0;
                        for (int i= 0;i<listPhuKienCot.size();i++)
                        {
                            if (menuItem.getTitle().toString().contains(listPhuKienCot.get(i)))
                            {
                                vitri = i;
                                break;
                            }
                        }
                        dialogPhuKien.dismiss();
                        DialogPhuKien(menuItem.getTitle().toString(),vitri+1,listPhuKien);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        //region HIỆN TRẠNG VÀ KÍCH THƯỚC
        btnSearchPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHienTrangThepPhuKien();
                String tencaukien="";
                if (tvTenPhuKien.getText().toString().contains("Móng"))
                    tencaukien = tvTenPhuKien.getText().toString().split(" Móng")[0].replace(" đầu dưới","").replace(" đầu trên","");
                else if (tvTenPhuKien.getText().toString().contains("Tầng"))
                    tencaukien = tvTenPhuKien.getText().toString().split(" Tầng")[0].replace(" đầu dưới","").replace(" đầu trên","");

                DialogHientrangCheckBoxPhuKien(edtHienTrangPhuKien,"HienTrangThep",tencaukien);
            }
        });
        btnSearchDXPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuDeXuatThepPhuKien();
                DialogHientrangCheckBoxDXPhuKien(edtDeXuatPhuKien,"DeXuatThep");
            }
        });
        btnNextDXPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listDeXuatPhuKien.set(vitriDeXuatPhuKien,edtDeXuatPhuKien.getText().toString().replace("\n","").replace(" -",""));
                    vitriDeXuatPhuKien++;
                    if (vitriDeXuatPhuKien<listDeXuatPhuKien.size())
                    {
                        edtDeXuatPhuKien.setText(listDeXuatPhuKien.get(vitriDeXuatPhuKien).replace("\n",""));
                        tvViTriDeXuatPhuKien.setText("Đề xuất: " + String.valueOf(vitriDeXuatPhuKien) + "/" + String.valueOf(listDeXuatPhuKien.size()));
                    }
                    LuuHienTrangThepPhuKien();
                    LuuDeXuatThepPhuKien();

                }catch (Exception e){}
            }
        });
        btnBackDXPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriDeXuatPhuKien>=2){
                        listDeXuatPhuKien.set(vitriDeXuatPhuKien,edtDeXuatPhuKien.getText().toString().replace("\n","").replace(" -",""));
                        vitriDeXuatPhuKien--;
                        if (vitriHienTrangPhuKien>0)
                        {
                            edtDeXuatPhuKien.setText(listDeXuatPhuKien.get(vitriDeXuatPhuKien).replace("\n",""));
                            tvViTriDeXuatPhuKien.setText("Đề xuất: " + String.valueOf(vitriDeXuatPhuKien) + "/" + String.valueOf(listDeXuatPhuKien.size()));
                        }
                        LuuHienTrangThepPhuKien();
                        LuuDeXuatThepPhuKien();
}
                }
                catch (Exception e){}
            }
        });
        btnNextHienTrangPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listHienTrangPhuKien.set(vitriHienTrangPhuKien,edtHienTrangPhuKien.getText().toString().replace("\n","").replace(" -",""));
                    vitriHienTrangPhuKien++;
                    if (vitriHienTrangPhuKien<listHienTrangPhuKien.size())
                    {
                        edtHienTrangPhuKien.setText(listHienTrangPhuKien.get(vitriHienTrangPhuKien).replace("\n",""));
                        tvViTriHienTrangPhuKien.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangPhuKien) + "/" + String.valueOf(listHienTrangPhuKien.size()));
                    }
                    LuuHienTrangThepPhuKien();
                    LuuDeXuatThepPhuKien();
                    }catch (Exception e){}
            }
        });
        btnBackHienTrangPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriHienTrangPhuKien>=2){
                        listHienTrangPhuKien.set(vitriHienTrangPhuKien,edtHienTrangPhuKien.getText().toString().replace("\n","").replace(" -",""));
                        vitriHienTrangPhuKien--;
                        edtHienTrangPhuKien.setText(listHienTrangPhuKien.get(vitriHienTrangPhuKien).replace("\n",""));
                        tvViTriHienTrangPhuKien.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangPhuKien) + "/" + String.valueOf(listHienTrangPhuKien.size()));

                        LuuHienTrangThepPhuKien();
                        LuuDeXuatThepPhuKien();
}
                }
                catch (Exception e){}
            }
        });
        btnLuuPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LuuHienTrangThepPhuKien();
                LuuDeXuatThepPhuKien();

                if (tvTenPhuKien.getText().toString().contains("Móng")) LuuPhuKienDauDuoi(vitri);
                else if (tvTenPhuKien.getText().toString().contains("Tầng")) LuuPhuKienDauTren(vitri);
                else if (!tvTenPhuKien.getText().toString().contains("Móng") && !tvTenPhuKien.getText().toString().contains("Tầng"))
                {
                    data_Table7[vitri][1]= edtKichThuocPhuKien.getText().toString().replace("- ","").trim().replace("\n","");
                    data_Table7[vitri][2]= textHTPhuKien;
                    data_Table7[vitri][3]= textDXPhuKien;
                    set_DataTable7_Duoi(vitri);
                }
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();


            }
        });
        //endregion

    };
    public void LuuPhuKienDauDuoi(final int vitri){
        //KÍCH THƯỚC
        try
        {
        if (String.valueOf(data_Table7[vitri][1]).trim().equals("") || String.valueOf(data_Table7[vitri][1]).trim().equals("null"))
        {
            data_Table7[vitri][1]= "- Móng " + tvTenPhuKien.getText().toString().split("Móng ")[1]+": " + edtKichThuocPhuKien.getText().toString().replace("- ","");
            if (!KichThuocMong.contains(edtKichThuocPhuKien.getText().toString().replace("- ",""))) {
                KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocPhuKien.getText().toString().replace("- ","")+"_";
                saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                setPopUpPhuKien();
            }
        }
        else
        {
            if (!data_Table7[vitri][1].contains("Móng " +tvTenPhuKien.getText().toString().split("Móng ")[1]))
                data_Table7[vitri][1]=data_Table7[vitri][1] +"\n" + "- Móng " + tvTenPhuKien.getText().toString().split("Móng ")[1]+": " + edtKichThuocPhuKien.getText().toString().replace("- ","");
            else
                if(String.valueOf(data_Table7[vitri][1]).contains(("- "))){
                String[] dongHienTrang = data_Table7[vitri][1].split(("- "));
                for(int j=0;j<dongHienTrang.length;j++)
                {
                    if (dongHienTrang[j].contains("Móng " +tvTenPhuKien.getText().toString().split("Móng ")[1]))
                    {
                        //edtKichThuoc.setText(s.replace("- " +tvTenMong.getText().toString().split("Móng ")[1] +":" ,""));
                        dongHienTrang[j] = "Móng " +tvTenPhuKien.getText().toString().split("Móng ")[1]+": " + edtKichThuocPhuKien.getText().toString().replace("- ","").replace("\n","");

                        if (!KichThuocMong.contains(edtKichThuocPhuKien.getText().toString().replace("- ",""))){
                            KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocPhuKien.getText().toString().replace("- ","")+"_";
                            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                            setPopUpPhuKien();
                        }
                        break;
                    }
                }
                data_Table7[vitri][1]="";
                for(String s:dongHienTrang)
                {
                    if (!s.trim().equals(""))
                    if (String.valueOf(data_Table7[vitri][1]).trim().equals("") || String.valueOf(data_Table7[vitri][1]).trim().equals("null"))
                        data_Table7[vitri][1]= "- " + s.replace("\n","");
                    else
                        data_Table7[vitri][1]=data_Table7[vitri][1] +"\n" + "- " + s.replace("\n","");
                }
            }
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Kích thước của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
        //HIỆN TRẠNG
        try
        {
        if (String.valueOf(data_Table7[vitri][2]).trim().equals("") || String.valueOf(data_Table7[vitri][2]).trim().equals("null"))
            data_Table7[vitri][2]= "Móng " + tvTenPhuKien.getText().toString().split("Móng ")[1]+": \n" + textHTPhuKien;
        else
        {
            //int vitrihientrang = 0;
            //data_Table7[vitri][1]=data_Table7[vitri][1] +"\n" + "- " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuoc.getText().toString();
            if (!data_Table7[vitri][2].contains("Móng " +tvTenPhuKien.getText().toString().split("Móng ")[1]))
                data_Table7[vitri][2]=data_Table7[vitri][2] +"\n" + "Móng " + tvTenPhuKien.getText().toString().split("Móng ")[1]+": \n" + textHTPhuKien;
            else
                if(String.valueOf(data_Table7[vitri][2]).contains(("Móng "))){
                    String[] tachchuoilan1 = data_Table7[vitri][2].split("Móng ");
                    for(int i=0;i<tachchuoilan1.length;i++)
                    {
                        String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                        if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Móng ")[1]))
                        {
                                tachchuoilan1[i] = tachchuoilan2[0] + ":\n"+textHTPhuKien;
                            break;
                        }
                    }
                    data_Table7[vitri][2]="";
                    for(String s:tachchuoilan1)
                    {
                        if (!s.trim().equals(""))
                            if (String.valueOf(data_Table7[vitri][2]).trim().equals("") || String.valueOf(data_Table7[vitri][2]).trim().equals("null"))
                                data_Table7[vitri][2]= "Móng " + s;
                            else
                                data_Table7[vitri][2]=data_Table7[vitri][2] + "Móng " + s;
                    }
                }
        }
        //ĐỀ XUẤT
            if (String.valueOf(data_Table7[vitri][3]).trim().equals("") || String.valueOf(data_Table7[vitri][3]).trim().equals("null"))
                data_Table7[vitri][3]= "Móng " + tvTenPhuKien.getText().toString().split("Móng ")[1]+": \n" + textDXPhuKien;
            else
            {
                //int vitrihientrang = 0;
                //data_Table7[vitri][1]=data_Table7[vitri][1] +"\n" + "- " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuoc.getText().toString();
                if (!data_Table7[vitri][3].contains("Móng " +tvTenPhuKien.getText().toString().split("Móng ")[1]))
                    data_Table7[vitri][3]=data_Table7[vitri][3] +"\n" + "Móng " + tvTenPhuKien.getText().toString().split("Móng ")[1]+": \n" + textDXPhuKien;
                else
                if(String.valueOf(data_Table7[vitri][3]).contains(("Móng "))){
                    String[] tachchuoilan1 = data_Table7[vitri][3].split("Móng ");
                    for(int i=0;i<tachchuoilan1.length;i++)
                    {
                        String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                        if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Móng ")[1]))
                        {
                            tachchuoilan1[i] = tachchuoilan2[0] + ":\n"+textDXPhuKien;
                            break;
                        }
                    }
                    data_Table7[vitri][3]="";
                    for(String s:tachchuoilan1)
                    {
                        if (!s.trim().equals(""))
                            if (String.valueOf(data_Table7[vitri][3]).trim().equals("") || String.valueOf(data_Table7[vitri][3]).trim().equals("null"))
                                data_Table7[vitri][3]= "Móng " + s;
                            else
                                data_Table7[vitri][3]=data_Table7[vitri][3] + "Móng " + s;
                    }
                }
            }
        //data_Table7[vitri][3]= textDXPhuKien;
        set_DataTable7_Duoi(vitri);
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Hiện trạng của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuCauKienTiepDia(final int vitri){
        //KÍCH THƯỚC
        try
        {
            if (String.valueOf(data_Table4[vitri][1]).trim().equals("") || String.valueOf(data_Table4[vitri][1]).trim().equals("null"))
            {
                data_Table4[vitri][1]= "- Móng " + tvTenCauKien.getText().toString().split("Móng ")[1]+": " + edtKichThuocCauKien.getText().toString().replace("- ","");
                if (!KichThuocMong.contains(edtKichThuocCauKien.getText().toString().replace("- ",""))) {
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocCauKien.getText().toString().replace("- ","")+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUpCauKien();
                }
            }
            else
            {
                //data_Table4[vitri][1]=data_Table4[vitri][1] +"\n" + "- " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuoc.getText().toString();
                if (!data_Table4[vitri][1].contains("Móng " +tvTenCauKien.getText().toString().split("Móng ")[1]))
                    data_Table4[vitri][1]=data_Table4[vitri][1] +"\n" + "- Móng " + tvTenCauKien.getText().toString().split("Móng ")[1]+": " + edtKichThuocCauKien.getText().toString().replace("- ","");
                else
                if(String.valueOf(data_Table4[vitri][1]).contains(("- ")))
                {
                    String[] dongHienTrang = data_Table4[vitri][1].split(("- "));
                    for(int j=0;j<dongHienTrang.length;j++)
                    {
                        if (dongHienTrang[j].contains("Móng " +tvTenCauKien.getText().toString().split("Móng ")[1]))
                        {
                            //edtKichThuoc.setText(s.replace("- " +tvTenMong.getText().toString().split("Móng ")[1] +":" ,""));
                            dongHienTrang[j] = "Móng " +tvTenCauKien.getText().toString().split("Móng ")[1]+": " + edtKichThuocCauKien.getText().toString().replace("- ","").replace("\n","");

                            if (!KichThuocMong.contains(edtKichThuocCauKien.getText().toString().replace("- ",""))){
                                KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocCauKien.getText().toString().replace("- ","")+"_";
                                saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                                setPopUpCauKien();
                            }
                            break;
                        }
                    }
                    data_Table4[vitri][1]="";
                    for(String s:dongHienTrang)
                    {
                        if (!s.trim().equals(""))
                            if (String.valueOf(data_Table4[vitri][1]).trim().equals("") || String.valueOf(data_Table4[vitri][1]).trim().equals("null"))
                                data_Table4[vitri][1]= "- " + s.replace("\n","");
                            else
                                data_Table4[vitri][1]=data_Table4[vitri][1] +"\n" + "- " + s.replace("\n","");
                    }
                }
            }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Kích thước của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
        //HIỆN TRẠNG
        try
        {
            if (String.valueOf(data_Table4[vitri][2]).trim().equals("") || String.valueOf(data_Table4[vitri][2]).trim().equals("null"))
                data_Table4[vitri][2]= "Móng " + tvTenCauKien.getText().toString().split("Móng ")[1]+": \n" + textHTCauKien;
            else
            {
                //int vitrihientrang = 0;
                //data_Table4[vitri][1]=data_Table4[vitri][1] +"\n" + "- " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuoc.getText().toString();
                if (!data_Table4[vitri][2].contains("Móng " +tvTenCauKien.getText().toString().split("Móng ")[1]))
                    data_Table4[vitri][2]=data_Table4[vitri][2] +"\n" + "Móng " + tvTenCauKien.getText().toString().split("Móng ")[1]+": \n" + textHTCauKien;
                else
                if(String.valueOf(data_Table4[vitri][2]).contains(("Móng "))){
                    String[] tachchuoilan1 = data_Table4[vitri][2].split("Móng ");
                    for(int i=0;i<tachchuoilan1.length;i++)
                    {
                        String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                        if (tachchuoilan2[0].contains(tvTenCauKien.getText().toString().split("Móng ")[1]))
                        {
                            tachchuoilan1[i] = tachchuoilan2[0] + ":\n"+textHTCauKien;
                            break;
                        }
                    }
                    data_Table4[vitri][2]="";
                    for(String s:tachchuoilan1)
                    {
                        if (!s.trim().equals(""))
                            if (String.valueOf(data_Table4[vitri][2]).trim().equals("") || String.valueOf(data_Table4[vitri][2]).trim().equals("null"))
                                data_Table4[vitri][2]= "Móng " + s;
                            else
                                data_Table4[vitri][2]=data_Table4[vitri][2] + "Móng " + s;
                    }
                }
            }
            //data_Table4[vitri][2]= textHTCauKien;
            //ĐỀ XUẤT
            if (String.valueOf(data_Table4[vitri][3]).trim().equals("") || String.valueOf(data_Table4[vitri][3]).trim().equals("null"))
                data_Table4[vitri][3]= "Móng " + tvTenCauKien.getText().toString().split("Móng ")[1]+": \n" + textDXCauKien;
            else
            {
                //int vitrihientrang = 0;
                //data_Table7[vitri][1]=data_Table7[vitri][1] +"\n" + "- " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuoc.getText().toString();
                if (!data_Table4[vitri][3].contains("Móng " +tvTenCauKien.getText().toString().split("Móng ")[1]))
                    data_Table4[vitri][3]=data_Table4[vitri][3] +"\n" + "Móng " + tvTenCauKien.getText().toString().split("Móng ")[1]+": \n" + textDXCauKien;
                else
                if(String.valueOf(data_Table4[vitri][3]).contains(("Móng "))){
                    String[] tachchuoilan1 = data_Table4[vitri][3].split("Móng ");
                    for(int i=0;i<tachchuoilan1.length;i++)
                    {
                        String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                        if (tachchuoilan2[0].contains(tvTenCauKien.getText().toString().split("Móng ")[1]))
                        {
                            tachchuoilan1[i] = tachchuoilan2[0] + ":\n"+textDXCauKien;
                            break;
                        }
                    }
                    data_Table4[vitri][3]="";
                    for(String s:tachchuoilan1)
                    {
                        if (!s.trim().equals(""))
                            if (String.valueOf(data_Table4[vitri][3]).trim().equals("") || String.valueOf(data_Table4[vitri][3]).trim().equals("null"))
                                data_Table4[vitri][3]= "Móng " + s;
                            else
                                data_Table4[vitri][3]=data_Table4[vitri][3] + "Móng " + s;
                    }
                }
            }
            //data_Table4[vitri][3]= textDXCauKien;
            set_DataTable4(vitri);
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Hiện trạng của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}

    }
    public void LuuPhuKienDauTren(final int vitri){
        //KÍCH THƯỚC
       try
       {
        if (String.valueOf(data_Table7[vitri][1]).trim().equals("") || String.valueOf(data_Table7[vitri][1]).trim().equals("null"))
        {
            data_Table7[vitri][1]= "- Tầng " + tvTenPhuKien.getText().toString().split("Tầng ")[1]+": " + edtKichThuocPhuKien.getText().toString().replace("- ","");
            if (!KichThuocMong.contains(edtKichThuocPhuKien.getText().toString().replace("- ",""))){
                KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocPhuKien.getText().toString().replace("- ","")+"_";
                saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                setPopUpPhuKien();
            }
        }
        else
        {
            //data_Table7[vitri][1]=data_Table7[vitri][1] +"\n" + "- " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuoc.getText().toString();
            if (!data_Table7[vitri][1].contains("Tầng " +tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                data_Table7[vitri][1]=data_Table7[vitri][1] +"\n" + "- Tầng " + tvTenPhuKien.getText().toString().split("Tầng ")[1]+": " + edtKichThuocPhuKien.getText().toString().replace("- ","");
            else
            if(String.valueOf(data_Table7[vitri][1]).contains(("- "))){
                String[] dongHienTrang = data_Table7[vitri][1].split(("- "));
                for(int j=0;j<dongHienTrang.length;j++)
                {
                    if (dongHienTrang[j].contains("Tầng " +tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                    {
                        //edtKichThuoc.setText(s.replace("- " +tvTenMong.getText().toString().split("Móng ")[1] +":" ,""));
                        dongHienTrang[j] = "Tầng " +tvTenPhuKien.getText().toString().split("Tầng ")[1]+": " + edtKichThuocPhuKien.getText().toString().replace("- ","").replace("\n","");
                        if (!KichThuocMong.contains(edtKichThuocPhuKien.getText().toString().replace("- ",""))){
                            KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocPhuKien.getText().toString().replace("- ","")+"_";
                            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                            setPopUpPhuKien();
                        }
                        break;
                    }

                }
                data_Table7[vitri][1]="";
                for(String s:dongHienTrang)
                {
                    if (!s.trim().equals(""))
                        if (String.valueOf(data_Table7[vitri][1]).trim().equals("") || String.valueOf(data_Table7[vitri][1]).trim().equals("null"))
                            data_Table7[vitri][1]= "- " + s.replace("\n","");
                        else
                            data_Table7[vitri][1]=data_Table7[vitri][1] +"\n" + "- " + s.replace("\n","");
                }
            }
        }
       }
       catch (ArithmeticException e)
       {Toast.makeText(FormMain.this, "Kích thước của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
        //HIỆN TRẠNG
        try
        {
            if (String.valueOf(data_Table7[vitri][2]).trim().equals("") || String.valueOf(data_Table7[vitri][2]).trim().equals("null"))
                data_Table7[vitri][2]= "Tầng " + tvTenPhuKien.getText().toString().split("Tầng ")[1]+": \n" + textHTPhuKien;
            else
            {
                //int vitrihientrang = 0;
                //data_Table7[vitri][1]=data_Table7[vitri][1] +"\n" + "- " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuoc.getText().toString();
                if (!data_Table7[vitri][2].contains("Tầng " +tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                    data_Table7[vitri][2]=data_Table7[vitri][2] +"\n" + "Tầng " + tvTenPhuKien.getText().toString().split("Tầng ")[1]+": \n" + textHTPhuKien;
                else
                if(String.valueOf(data_Table7[vitri][2]).contains(("Tầng "))){
                    String[] tachchuoilan1 = data_Table7[vitri][2].split("Tầng ");
                    for(int i=0;i<tachchuoilan1.length;i++)
                    {
                        String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                        if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                        {
                            tachchuoilan1[i] = tachchuoilan2[0] + ":\n"+textHTPhuKien;
                            break;
                        }
                    }
                    data_Table7[vitri][2]="";
                    for(String s:tachchuoilan1)
                    {
                        if (!s.trim().equals(""))
                            if (String.valueOf(data_Table7[vitri][2]).trim().equals("") || String.valueOf(data_Table7[vitri][2]).trim().equals("null"))
                                data_Table7[vitri][2]= "Tầng " + s;
                            else
                                data_Table7[vitri][2]=data_Table7[vitri][2] + "Tầng " + s;
                    }
                }
            }
            //ĐỀ XUẤT
            if (String.valueOf(data_Table7[vitri][3]).trim().equals("") || String.valueOf(data_Table7[vitri][3]).trim().equals("null"))
                data_Table7[vitri][3]= "Tầng " + tvTenPhuKien.getText().toString().split("Tầng ")[1]+": \n" + textDXPhuKien;
            else
            {
                //int vitrihientrang = 0;
                //data_Table7[vitri][1]=data_Table7[vitri][1] +"\n" + "- " + tvTenMong.getText().toString().split("Móng ")[1]+": " + edtKichThuoc.getText().toString();
                if (!data_Table7[vitri][3].contains("Tầng " +tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                    data_Table7[vitri][3]=data_Table7[vitri][3] +"\n" + "Tầng " + tvTenPhuKien.getText().toString().split("Tầng ")[1]+": \n" + textDXPhuKien;
                else
                if(String.valueOf(data_Table7[vitri][3]).contains(("Tầng "))){
                    String[] tachchuoilan1 = data_Table7[vitri][3].split("Tầng ");
                    for(int i=0;i<tachchuoilan1.length;i++)
                    {
                        String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                        if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                        {
                            tachchuoilan1[i] = tachchuoilan2[0] + ":\n"+textDXPhuKien;
                            break;
                        }
                    }
                    data_Table7[vitri][3]="";
                    for(String s:tachchuoilan1)
                    {
                        if (!s.trim().equals(""))
                            if (String.valueOf(data_Table7[vitri][3]).trim().equals("") || String.valueOf(data_Table7[vitri][3]).trim().equals("null"))
                                data_Table7[vitri][3]= "Tầng " + s;
                            else
                                data_Table7[vitri][3]=data_Table7[vitri][3] + "Tầng " + s;
                    }
                }
            }
            set_DataTable7_Tren(vitri);
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Hiện trạng của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void click_DialogThanCot(final int vitri){
        btnHienBangThanCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutTable6.getVisibility() == View.GONE) {layoutTable6.setVisibility(View.VISIBLE);layoutTable10.setVisibility(View.GONE);}
                else layoutTable6.setVisibility(View.GONE);
            }
        });
        btnHienBangKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutTable10.getVisibility() == View.GONE) {layoutTable10.setVisibility(View.VISIBLE);layoutTable6.setVisibility(View.GONE);}
                else layoutTable10.setVisibility(View.GONE);
            }
        });

        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(7,tvTenMong.getText().toString(),edtHienTrang.getText().toString());
            }
        });
        btnChupThanhGiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(8,"Thanh giằng "+tvTenMong.getText().toString(),edtHienTrang.getText().toString());
            }
        });
        btnChupThanhCanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(8,"Thanh cánh "+tvTenMong.getText().toString(),edtHienTrang.getText().toString());
            }
        });

        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE) {
                    lauoutKichThuoc.setVisibility(View.VISIBLE);
                    lauoutKichThuocKheHo.setVisibility(View.GONE);
                }
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });
        btnlauoutKichThuocKheHo.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuocKheHo.getVisibility() == View.GONE) {
                    lauoutKichThuocKheHo.setVisibility(View.VISIBLE);
                    lauoutKichThuoc.setVisibility(View.GONE);
                }
                else lauoutKichThuocKheHo.setVisibility(View.GONE);
            }
        });
        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });

        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, tvTenMong);
                for (String s : listThanCot)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listThanCot.indexOf(menuItem.getTitle().toString());
                        //Toast.makeText(getApplicationContext(),String.valueOf(index), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        DialogThanCot(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        //region HIỆN TRẠNG VÀ KÍCH THƯỚC
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHienTrangThep();
                DialogHientrangCheckBox(edtHienTrang,"HienTrangThep","");
            }
        });
        btnSearchDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuDeXuatThep();
                DialogHientrangCheckBoxDX(edtDeXuat,"DeXuatThep");
            }
        });
        btnNextDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listDeXuat.set(vitriDeXuat,edtDeXuat.getText().toString().replace("\n","").replace(" -",""));
                    vitriDeXuat++;
                    if (vitriDeXuat<listDeXuat.size())
                    {
                        edtDeXuat.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
                        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriDeXuat) + "/" + String.valueOf(listDeXuat.size()));
                    }
                    LuuHienTrangThep();
                    LuuDeXuatThep();

                    data_Table6[vitri][1] = edtKichThuoc.getText().toString();
                    if(edtDoDayThanhCanh.getText().toString().trim().equals(""))
                        data_Table6[vitri][2] = edtThanhCanh.getText().toString();
                    else
                        data_Table6[vitri][2] = edtThanhCanh.getText().toString() + "x" + edtDoDayThanhCanh.getText().toString().trim();
                    data_Table6[vitri][3] = edtThanhGiang.getText().toString();
                    data_Table6[vitri][4]= textHT;
                    data_Table6[vitri][5]= textDX;
                    set_DataTable6(vitri);
                }catch (Exception e){}
            }
        });
        btnBackDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriDeXuat>=2){
                        listDeXuat.set(vitriDeXuat,edtDeXuat.getText().toString().replace("\n","").replace(" -",""));
                        vitriDeXuat--;
                        if (vitriHienTrang>0)
                        {
                            edtDeXuat.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
                            tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriDeXuat) + "/" + String.valueOf(listDeXuat.size()));
                        }
                        LuuHienTrangThep();
                        LuuDeXuatThep();

                        data_Table6[vitri][1] = edtKichThuoc.getText().toString();
                        if(edtDoDayThanhCanh.getText().toString().trim().equals(""))
                            data_Table6[vitri][2] = edtThanhCanh.getText().toString();
                        else
                            data_Table6[vitri][2] = edtThanhCanh.getText().toString() + "x" + edtDoDayThanhCanh.getText().toString().trim();
                        data_Table6[vitri][3] = edtThanhGiang.getText().toString();
                        data_Table6[vitri][4]= textHT;
                        data_Table6[vitri][5]= textDX;
                        set_DataTable6(vitri);}
                }
                catch (Exception e){}
            }
        });
        btnNextHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listHienTrang.set(vitriHienTrang,edtHienTrang.getText().toString().replace("\n","").replace(" -",""));
                    vitriHienTrang++;
                    if (vitriHienTrang<listHienTrang.size())
                    {
                        edtHienTrang.setText(listHienTrang.get(vitriHienTrang).replace("\n",""));
                        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));

                    }
                    LuuHienTrangThep();
                    LuuDeXuatThep();

                    data_Table6[vitri][1] = edtKichThuoc.getText().toString();
                    if(edtDoDayThanhCanh.getText().toString().trim().equals(""))
                        data_Table6[vitri][2] = edtThanhCanh.getText().toString();
                    else
                        data_Table6[vitri][2] = edtThanhCanh.getText().toString() + "x" + edtDoDayThanhCanh.getText().toString().trim();
                    data_Table6[vitri][3] = edtThanhGiang.getText().toString();
                    data_Table6[vitri][4]= textHT;
                    data_Table6[vitri][5]= textDX;
                    set_DataTable6(vitri);}catch (Exception e){}
            }
        });
        btnBackHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriHienTrang>=2){
                        listHienTrang.set(vitriHienTrang,edtHienTrang.getText().toString().replace("\n","").replace(" -",""));
                        vitriHienTrang--;
                        edtHienTrang.setText(listHienTrang.get(vitriHienTrang).replace("\n",""));
                        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));

                        LuuHienTrangThep();
                        LuuDeXuatThep();

                        data_Table6[vitri][1] = edtKichThuoc.getText().toString();
                        if(edtDoDayThanhCanh.getText().toString().trim().equals(""))
                            data_Table6[vitri][2] = edtThanhCanh.getText().toString();
                        else
                            data_Table6[vitri][2] = edtThanhCanh.getText().toString() + "x" + edtDoDayThanhCanh.getText().toString().trim();
                        data_Table6[vitri][3] = edtThanhGiang.getText().toString();
                        data_Table6[vitri][4]= textHT;
                        data_Table6[vitri][5]= textDX;
                        set_DataTable6(vitri);}
                }
                catch (Exception e){}
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lauoutKichThuoc.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);

                LuuHienTrangThep();
                LuuDeXuatThep();

                data_Table6[vitri][1] = edtKichThuoc.getText().toString();
                if(edtDoDayThanhCanh.getText().toString().trim().equals(""))
                    data_Table6[vitri][2] = edtThanhCanh.getText().toString();
                else
                    data_Table6[vitri][2] = edtThanhCanh.getText().toString() + "x" + edtDoDayThanhCanh.getText().toString().trim();
                data_Table6[vitri][3] = edtThanhGiang.getText().toString();
                data_Table6[vitri][4]= textHT;
                data_Table6[vitri][5]= textDX;
                set_DataTable6(vitri);
                if (!KichThuocMong.contains(edtKichThuoc.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuoc.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUp();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }
                if (!KichThuocMong.contains(edtThanhCanh.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtThanhCanh.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUpThanCot();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }
                if (!KichThuocMong.contains(edtThanhGiang.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtThanhGiang.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUpThanCot();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });
        //endregion

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listHienTrang_copy.clear();
                listDeXuat_copy.clear();
                listHienTrang_copy.addAll(listHienTrang) ;
                listDeXuat_copy.addAll(listDeXuat) ;
                textCopy = edtKichThuoc.getText().toString() + "_" +  edtThanhCanh.getText().toString()+ "_" +  edtDoDayThanhCanh.getText().toString()+ "_" +  edtThanhGiang.getText().toString();
                btnPaste.setVisibility(View.VISIBLE);
                Toast.makeText(FormMain.this, "Đã sao chép!", Toast.LENGTH_LONG).show();
            }
        });
        btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormMain.this);
                builder.setTitle("Bạn có muốn dán dữ liệu từ cấu kiện trước?");
                // add the buttons
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                        String[] text = textCopy.split("_");
                        edtKichThuoc.setText(text[0]);
                        //edtHienTrang.setText(text[1]);
                        listHienTrang.clear();
                        listDeXuat.clear();
                        listHienTrang.addAll(listHienTrang_copy);
                        listDeXuat.addAll(listDeXuat_copy);
                        edtHienTrang.setText(listHienTrang.get(1).replace("\n",""));
                        edtDeXuat.setText(listDeXuat.get(1).replace("\n",""));
                        //
                        edtThanhCanh.setText(text[1]);
                        edtDoDayThanhCanh.setText(text[2]);
                        edtThanhGiang.setText(text[3]);
                        //LƯU SỐ LIỆU
                        LuuHienTrangThep();
                        LuuDeXuatThep();

                            data_Table6[vitri][1] = edtKichThuoc.getText().toString();
                            if(edtDoDayThanhCanh.getText().toString().trim().equals(""))
                                data_Table6[vitri][2] = edtThanhCanh.getText().toString();
                            else
                                data_Table6[vitri][2] = edtThanhCanh.getText().toString() + "x" + edtDoDayThanhCanh.getText().toString().trim();
                            data_Table6[vitri][3] = edtThanhGiang.getText().toString();
                            data_Table6[vitri][4]= textHT;
                            data_Table6[vitri][5]= textDX;
                            set_DataTable6(vitri);
                        if (!KichThuocMong.contains(edtKichThuoc.getText().toString())){
                            KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuoc.getText().toString()+"_";
                            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                            setPopUp();
                            //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                        }
                        if (!KichThuocMong.contains(edtThanhCanh.getText().toString())){
                            KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtThanhCanh.getText().toString()+"_";
                            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                            setPopUpThanCot();
                            //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                        }
                        if (!KichThuocMong.contains(edtThanhGiang.getText().toString())){
                            KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtThanhGiang.getText().toString()+"_";
                            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                            setPopUpThanCot();
                            //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {Toast.makeText(FormMain.this, "Dán dữ liệu bị lỗi!", Toast.LENGTH_LONG).show();}
                    }
                });
                builder.setNegativeButton("Không", null);
                // create and show the alert dialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        });
        click_DialogKheHo(vitri+1);
    };
    public void click_DialogDienTro(final int vitri){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(5,tvTenMong.getText().toString(),"");
            }
        });

        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE) lauoutKichThuoc.setVisibility(View.VISIBLE);
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });
        edtKichThuoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().trim().equals(""))
                    if(isNumeric(s.toString()))
                    {
                        double dientro = Double.valueOf(s.toString());
                        if(dientro <10)
                        {edtHienTrang.setText("Đạt");
                        edtHienTrang.setTextColor(getResources().getColor(R.color.colorGreen));}
                        else
                        {
                            edtHienTrang.setText("Không đạt");
                            edtHienTrang.setTextColor(getResources().getColor(R.color.colorRed));
                        }
                    }
            }
        });
        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lauoutKichThuoc.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);
                data_Table5[vitri][0] = edtKichThuoc.getText().toString();
                data_Table5[vitri][1] = "10";
                data_Table5[vitri][2] = edtHienTrang.getText().toString();
                data_Table5[vitri][3]= edtDeXuat.getText().toString();
                set_DataTable5(vitri);
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();


            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, tvTenMong);
                for (String s : listLanDoDienTro)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listLanDoDienTro.indexOf(menuItem.getTitle().toString());
                        dialog.dismiss();
                        //Toast.makeText(getApplicationContext(),String.valueOf(index), Toast.LENGTH_SHORT).show();
                        DialogDienTro(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    };
    public void click_DialogKheHo(final int vitri){

        btnChupHienTrangKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(1,tvTenMong.getText().toString(),edtHienTrangKheHo.getText().toString());
            }
        });


        //region HIỆN TRẠNG VÀ KÍCH THƯỚC
        btnSearchKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHienTrangThepKheHo();
                DialogHientrangCheckBoxKheHo(edtHienTrangKheHo,"HienTrangThep","Khe hở");
            }
        });
        btnSearchDXKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuDeXuatThepKheHo();
                DialogHientrangCheckBoxDXKheHo(edtDeXuatKheHo,"DeXuatThep");
            }
        });
        btnNextDXKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listDeXuatKheHo.set(vitriDeXuatKheHo,edtDeXuatKheHo.getText().toString().replace("\n","").replace(" -",""));
                    vitriDeXuatKheHo++;
                    if (vitriDeXuatKheHo<listDeXuatKheHo.size())
                    {
                        edtDeXuatKheHo.setText(listDeXuatKheHo.get(vitriDeXuatKheHo).replace("\n",""));
                        tvViTriDeXuatKheHo.setText("Đề xuất: " + String.valueOf(vitriDeXuatKheHo) + "/" + String.valueOf(listDeXuatKheHo.size()));
                    }
                    LuuHienTrangThepKheHo();
                    LuuDeXuatThepKheHo();

                    data_Table10[vitri][1] = edtKichThuocKheHo.getText().toString();
                    data_Table10[vitri][2] = edtThanhCanhKheHo.getText().toString();
                    data_Table10[vitri][3] = textHTKheHo;
                    data_Table10[vitri][4] = textDXKheHo;
                    set_DataTable10(vitri);
                }catch (Exception e){}
            }
        });
        btnBackDXKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriDeXuatKheHo>=2){
                        listDeXuatKheHo.set(vitriDeXuatKheHo,edtDeXuatKheHo.getText().toString().replace("\n","").replace(" -",""));
                        vitriDeXuatKheHo--;
                        if (vitriHienTrangKheHo>0)
                        {
                            edtDeXuatKheHo.setText(listDeXuatKheHo.get(vitriDeXuatKheHo).replace("\n",""));
                            tvViTriDeXuatKheHo.setText("Đề xuất: " + String.valueOf(vitriDeXuatKheHo) + "/" + String.valueOf(listDeXuatKheHo.size()));
                        }
                        LuuHienTrangThepKheHo();
                        LuuDeXuatThepKheHo();

                        data_Table10[vitri][1] = edtKichThuocKheHo.getText().toString();
                        data_Table10[vitri][2] = edtThanhCanhKheHo.getText().toString();
                        data_Table10[vitri][3] = textHTKheHo;
                        data_Table10[vitri][4] = textDXKheHo;
                        set_DataTable10(vitri);}
                }
                catch (Exception e){}
            }
        });
        btnNextHienTrangKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    listHienTrangKheHo.set(vitriHienTrangKheHo,edtHienTrangKheHo.getText().toString().replace("\n","").replace(" -",""));
                    vitriHienTrangKheHo++;
                    if (vitriHienTrangKheHo<listHienTrangKheHo.size())
                    {
                        edtHienTrangKheHo.setText(listHienTrangKheHo.get(vitriHienTrangKheHo).replace("\n",""));
                        tvViTriHienTrangKheHo.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangKheHo) + "/" + String.valueOf(listHienTrangKheHo.size()));

                    }
                    LuuHienTrangThepKheHo();
                    LuuDeXuatThepKheHo();

                    data_Table10[vitri][1] = edtKichThuocKheHo.getText().toString();
                    data_Table10[vitri][2] = edtThanhCanhKheHo.getText().toString();
                    data_Table10[vitri][3] = textHTKheHo;
                    data_Table10[vitri][4] = textDXKheHo;
                    set_DataTable10(vitri);}catch (Exception e){}
            }
        });
        btnBackHienTrangKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(vitriHienTrangKheHo>=2){
                        listHienTrangKheHo.set(vitriHienTrangKheHo,edtHienTrangKheHo.getText().toString().replace("\n","").replace(" -",""));
                        vitriHienTrangKheHo--;
                        edtHienTrangKheHo.setText(listHienTrangKheHo.get(vitriHienTrangKheHo).replace("\n",""));
                        tvViTriHienTrangKheHo.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangKheHo) + "/" + String.valueOf(listHienTrangKheHo.size()));

                        LuuHienTrangThepKheHo();
                        LuuDeXuatThepKheHo();

                        data_Table10[vitri][1] = edtKichThuocKheHo.getText().toString();
                        data_Table10[vitri][2] = edtThanhCanhKheHo.getText().toString();
                        data_Table10[vitri][3] = textHTKheHo;
                        data_Table10[vitri][4] = textDXKheHo;
                        set_DataTable10(vitri);}
                }
                catch (Exception e){}
            }
        });
        btnLuuKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lauoutKichThuocKheHo.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);

                LuuHienTrangThepKheHo();
                LuuDeXuatThepKheHo();

                data_Table10[vitri][1] = edtKichThuocKheHo.getText().toString();
                data_Table10[vitri][2] = edtThanhCanhKheHo.getText().toString();
                data_Table10[vitri][3] = textHTKheHo;
                data_Table10[vitri][4] = textDXKheHo;
                set_DataTable10(vitri);
                if (!KichThuocMong.contains(edtKichThuocKheHo.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocKheHo.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUpKheHo();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }
                if (!KichThuocMong.contains(edtThanhCanh.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtThanhCanhKheHo.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUpKheHo();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });
        //endregion

        btnCopyKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listHienTrang_copyKheHo.clear();
                listDeXuat_copyKheHo.clear();
                listHienTrang_copyKheHo.addAll(listHienTrangKheHo) ;
                listDeXuat_copyKheHo.addAll(listDeXuatKheHo) ;
                textCopyKheHo = edtKichThuocKheHo.getText().toString() + "_" +  edtThanhCanhKheHo.getText().toString();
                if (!textCopyKheHo.equals("")) btnPasteKheHo.setVisibility(View.VISIBLE);
                Toast.makeText(FormMain.this, "Đã sao chép!", Toast.LENGTH_LONG).show();
            }
        });
        btnPasteKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormMain.this);
                builder.setTitle("Bạn c ó muốn dán dữ liệu từ cấu kiện trước?");
                // add the buttons
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                        String[] text = textCopyKheHo.split("_");
                        edtKichThuocKheHo.setText(text[0]);
                        //edtHienTrang.setText(text[1]);
                        listHienTrangKheHo.clear();
                        listDeXuatKheHo.clear();
                        listHienTrangKheHo.addAll(listHienTrang_copyKheHo);
                        listDeXuatKheHo.addAll(listDeXuat_copyKheHo);
                        edtHienTrangKheHo.setText(listHienTrangKheHo.get(1).replace("\n",""));
                        edtDeXuatKheHo.setText(listDeXuatKheHo.get(1).replace("\n",""));
                        //
                        edtThanhCanhKheHo.setText(text[1]);
                            //LƯU SỐ LIỆU
                            LuuHienTrangThepKheHo();
                            LuuDeXuatThepKheHo();

                            data_Table10[vitri][1] = edtKichThuocKheHo.getText().toString();
                            data_Table10[vitri][2] = edtThanhCanhKheHo.getText().toString();
                            data_Table10[vitri][3] = textHTKheHo;
                            data_Table10[vitri][4] = textDXKheHo;
                            set_DataTable10(vitri);
                            if (!KichThuocMong.contains(edtKichThuocKheHo.getText().toString())){
                                KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuocKheHo.getText().toString()+"_";
                                saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                                setPopUpKheHo();
                                //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                            }
                            if (!KichThuocMong.contains(edtThanhCanh.getText().toString())){
                                KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtThanhCanhKheHo.getText().toString()+"_";
                                saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                                setPopUpKheHo();
                                //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {Toast.makeText(FormMain.this, "Dán dữ liệu bị lỗi!", Toast.LENGTH_LONG).show();}
                    }
                });

                builder.setNegativeButton("Không", null);
                // create and show the alert dialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        });
    };
    public void click_DialogLucCang(final int vitri){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(3,tvTenMong.getText().toString(),"");
            }
        });
        //region PHỤ KIỆN CỘT
        btnKichThuocPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, btnKichThuocPhuKien);
                for (String s : listPhuKienCotDauTren)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int vitri =0;
                        for (int i= 0;i<listPhuKienCot.size();i++)
                        {
                            if (menuItem.getTitle().toString().contains(listPhuKienCot.get(i)))
                            {
                                vitri = i;
                                break;
                            }
                        }
                        DialogPhuKien(menuItem.getTitle().toString(),vitri+1,listPhuKienCotDauTren);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        //endregion
        ///HIỆN LAYOUT
        btnHienLucSiet.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (layoutLucSiet.getVisibility() == View.GONE) {layoutLucSiet.setVisibility(View.VISIBLE);}
                else layoutLucSiet.setVisibility(View.GONE);
            }
        });
        //HIỆN BẢNG
        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });

        btnHienBangLucSiet.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (layoutTable8.getVisibility() == View.GONE) {layoutTable8.setVisibility(View.VISIBLE);}
                else layoutTable8.setVisibility(View.GONE);
            }
        });
        //NÚT LUU

        btnLuuLucSiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // layoutLucSiet.setVisibility(View.GONE);
                //lauoutBang.setVisibility(View.GONE);
                final int ColumnCount = SoMong*2+1;
                for (int i=2;i<ColumnCount+1;i=i+2){
                    if (String.valueOf(data_Table8[vitri][i]).contains("ull"))
                        data_Table8[vitri][i]="0";
                    Log.d("LỰC SIẾT",String.valueOf(data_Table8[vitri][i+1]));
                    if (isNumeric(listEdtLucSiet.get(i).getText().toString().trim().replace(",", ".")))
                        data_Table8[vitri][i+1] = listEdtLucSiet.get(i).getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, listEdtLucSiet.get(i).getText().toString()+" :Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                }
                set_DataTable8(vitri);
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();
            }
        });
        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, tvTenMong);
                for (String s : listTangDayCo)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        dialog.dismiss();
                        int index = listTangDayCo.indexOf(menuItem.getTitle().toString());
                        DialogLucCang(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    };
    public void click_DialogAnten(){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(8,tvTenMong.getText().toString(),"");
            }
        });

        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE) lauoutKichThuoc.setVisibility(View.VISIBLE);
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });

        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutBang.setVisibility(View.GONE);
                if (!edtLoaiAnten.getText().toString().equals("")) {
                    SoAnten++;
                    saveDataOnCacher(String.valueOf(SoAnten), "SoAnten");
                    set_DataTable11();
                    set_ViewTable11();
                    for (int i = 0; i < 5; i++) {
                        data_Table11[SoAnten][i] = listEdtAnten.get(i).getText().toString();
                    }


                    if (!KichThuocMong.contains(edtLoaiAnten.getText().toString())) {
                        KichThuocMong = KichThuocMong.replace("\n","") + "_" + edtLoaiAnten.getText().toString() + "_";
                        saveDataOnTemplate(KichThuocMong, "LOAIANTEN");
                        setPopUpAnten();
                        //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                    }


                    for (int i = 0; i < 5; i++) {
                        listEdtAnten.get(i).setText("");
                    }
                    set_DataTable11();
                }
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
    public void click_DialogDoNghieng_ChanCot(){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(6,tvTenMong.getText().toString(),"");
            }
        });

        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE) lauoutKichThuoc.setVisibility(View.VISIBLE);
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });

        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutBang.setVisibility(View.GONE);
                if (!GT_X.getText().toString().equals(""))
                {
                    //Toạ đô X
                    if (isNumeric(String.valueOf(GT_X.getText().toString().replace(",", ".").trim())))
                        data_Table12_ChanCot[1][1]=GT_X.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Y
                    if (isNumeric(String.valueOf(GT_Y.getText().toString().replace(",", ".").trim())))
                        data_Table12_ChanCot[1][2]=GT_Y.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Z
                    if (isNumeric(String.valueOf(GT_Z.getText().toString().replace(",", ".").trim())))
                    {
                        data_Table12_ChanCot[1][3]=GT_Z.getText().toString().replace(",", ".").trim();
                        CaoDoChanCot = Double.valueOf(GT_Z.getText().toString().trim().replace(",", "."));
                    }
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();

                    set_DataTable_ChanCot();

                }
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
    public void click_DialogDoNghieng_HuongBac(){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(6,tvTenMong.getText().toString(),"");
            }
        });

        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE) lauoutKichThuoc.setVisibility(View.VISIBLE);
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });

        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutBang.setVisibility(View.GONE);
                if (!GT_X.getText().toString().equals(""))
                {
                    //Toạ đô X
                    if (isNumeric(String.valueOf(GT_X.getText().toString().replace(",", ".").trim())))
                        data_Table12_HuongBac[1][1]=GT_X.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Y
                    if (isNumeric(String.valueOf(GT_Y.getText().toString().replace(",", ".").trim())))
                        data_Table12_HuongBac[1][2]=GT_Y.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Z
                    if (isNumeric(String.valueOf(GT_Z.getText().toString().replace(",", ".").trim())))
                    {
                        data_Table12_HuongBac[1][3]=GT_Z.getText().toString().replace(",", ".").trim();
                    }
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();

                    set_DataTable_HuongBac();

                }
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
    public void click_DialogDoNghieng_DinhCot(){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(6,tvTenMong.getText().toString(),"");
            }
        });

        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE) lauoutKichThuoc.setVisibility(View.VISIBLE);
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });

        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutBang.setVisibility(View.GONE);
                if (!GT_X.getText().toString().equals(""))
                {
                    //Toạ đô X
                    if (isNumeric(String.valueOf(GT_X.getText().toString().replace(",", ".").trim())))
                        data_Table12_DinhCot[1][1]=GT_X.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Y
                    if (isNumeric(String.valueOf(GT_Y.getText().toString().replace(",", ".").trim())))
                        data_Table12_DinhCot[1][2]=GT_Y.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Z
                    if (isNumeric(String.valueOf(GT_Z.getText().toString().replace(",", ".").trim())))
                    {
                        data_Table12_DinhCot[1][3]=GT_Z.getText().toString().replace(",", ".").trim();
                    }
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();

                    set_DataTable_DinhCot();

                }
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
    public void click_DialogDoNghieng_V0(final int vitri){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(6,tvTenMong.getText().toString(),"");
            }
        });

        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE) lauoutKichThuoc.setVisibility(View.VISIBLE);
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });

        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (!GT_X.getText().toString().equals(""))
                {
                    //Toạ đô X
                    if (isNumeric(String.valueOf(GT_X.getText().toString().replace(",", ".").trim())))
                        data_Table12_V0[vitri][1]=GT_X.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Y
                    if (isNumeric(String.valueOf(GT_Y.getText().toString().replace(",", ".").trim())))
                        data_Table12_V0[vitri][2]=GT_Y.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Z
                    if (isNumeric(String.valueOf(GT_Z.getText().toString().replace(",", ".").trim())))
                        data_Table12_V0[vitri][3]=GT_Z.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    set_DataTable12_V0(vitri);
                    if (vitri>1){
                        Boolean kiemtra = TinhKhoangCach(Table12_V0,vitri,4,edtDanhGiaDoNghieng);
                        if (kiemtra)
                        {
                            lauoutKichThuoc.setVisibility(View.GONE);
                            lauoutBang.setVisibility(View.GONE);
                        }
                    }
                    else
                    {
                        lauoutKichThuoc.setVisibility(View.GONE);
                        lauoutBang.setVisibility(View.GONE);
                    }


                }
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });
        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, tvTenMong);
                for (String s : listDoNghieng_V0)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        dialog.dismiss();
                        int index = listDoNghieng_V0.indexOf(menuItem.getTitle().toString());
                        DialogDoNghieng_V0(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
    public void click_DialogDoNghieng_V1(final int vitri){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(6,tvTenMong.getText().toString(),"");
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE) lauoutKichThuoc.setVisibility(View.VISIBLE);
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });

        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (!GT_X.getText().toString().equals("")) {

                    //Toạ đô X
                    if (isNumeric(String.valueOf(GT_X.getText().toString().replace(",", ".").trim())))
                        data_Table12_V1[vitri][1]=GT_X.getText().toString();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Y
                    if (isNumeric(String.valueOf(GT_Y.getText().toString().replace(",", ".").trim())))
                        data_Table12_V1[vitri][2]=GT_Y.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Z
                    if (isNumeric(String.valueOf(GT_Z.getText().toString().replace(",", ".").trim())))
                        data_Table12_V1[vitri][3]=GT_Z.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();

                    set_DataTable12_V1(vitri);
                    if (vitri>1){
                        Boolean kiemtra = TinhKhoangCach(Table12_V1,vitri,4,edtDanhGiaDoNghieng);
                        if (kiemtra)
                        {
                            lauoutKichThuoc.setVisibility(View.GONE);
                            lauoutBang.setVisibility(View.GONE);
                        }
                    }
                    else
                    {
                        lauoutKichThuoc.setVisibility(View.GONE);
                        lauoutBang.setVisibility(View.GONE);
                    }
                }
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });
        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, tvTenMong);
                for (String s : listDoNghieng_V1)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        dialog.dismiss();

                        int index = listDoNghieng_V1.indexOf(menuItem.getTitle().toString());
                        DialogDoNghieng_V1(menuItem.getTitle().toString(),index+1);

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
    public void click_DialogDoNghieng_V2(final int vitri){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(6,tvTenMong.getText().toString(),"");
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE) lauoutKichThuoc.setVisibility(View.VISIBLE);
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });

        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (!GT_X.getText().toString().equals("")) {

                    //Toạ đô X
                    if (isNumeric(String.valueOf(GT_X.getText().toString().replace(",", ".").trim())))
                        data_Table12_V2[vitri][1]=GT_X.getText().toString();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Y
                    if (isNumeric(String.valueOf(GT_Y.getText().toString().replace(",", ".").trim())))
                        data_Table12_V2[vitri][2]=GT_Y.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Z
                    if (isNumeric(String.valueOf(GT_Z.getText().toString().replace(",", ".").trim())))
                        data_Table12_V2[vitri][3]=GT_Z.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    set_DataTable12_V2(vitri);
                    if (vitri>1){
                        Boolean kiemtra = TinhKhoangCach(Table12_V2,vitri,4,edtDanhGiaDoNghieng);
                        if (kiemtra)
                        {
                            lauoutKichThuoc.setVisibility(View.GONE);
                            lauoutBang.setVisibility(View.GONE);
                        }
                    }
                    else
                    {
                        lauoutKichThuoc.setVisibility(View.GONE);
                        lauoutBang.setVisibility(View.GONE);
                    }
                }
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });
        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, tvTenMong);
                for (String s : listDoNghieng_V2)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        dialog.dismiss();

                        int index = listDoNghieng_V2.indexOf(menuItem.getTitle().toString());
                        DialogDoNghieng_V2(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
    public void click_DialogDoNghieng_V3(final int vitri){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(6,tvTenMong.getText().toString(),"");
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE) lauoutKichThuoc.setVisibility(View.VISIBLE);
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });

        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutBang.setVisibility(View.GONE);
                if (!GT_X.getText().toString().equals("")) {

                    //Toạ đô X
                    if (isNumeric(String.valueOf(GT_X.getText().toString().replace(",", ".").trim())))
                        data_Table12_V3[vitri][1]=GT_X.getText().toString();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Y
                    if (isNumeric(String.valueOf(GT_Y.getText().toString().replace(",", ".").trim())))
                        data_Table12_V3[vitri][2]=GT_Y.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Z
                    if (isNumeric(String.valueOf(GT_Z.getText().toString().replace(",", ".").trim())))
                        data_Table12_V3[vitri][3]=GT_Z.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();

                    set_DataTable12_V3(vitri);
                    if (vitri>1){
                        Boolean kiemtra = TinhKhoangCach(Table12_V3,vitri,4,edtDanhGiaDoNghieng);
                        if (kiemtra)
                        {
                            lauoutKichThuoc.setVisibility(View.GONE);
                            lauoutBang.setVisibility(View.GONE);
                        }
                    }
                    else
                    {
                        lauoutKichThuoc.setVisibility(View.GONE);
                        lauoutBang.setVisibility(View.GONE);
                    }
                }
                Toast.makeText(FormMain.this, "Đã lưu số liệu!", Toast.LENGTH_LONG).show();

            }
        });
        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, tvTenMong);
                for (String s : listDoNghieng_V3)
                { popupMenu.getMenu().add(s);}
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        dialog.dismiss();

                        int index = listDoNghieng_V3.indexOf(menuItem.getTitle().toString());
                        DialogDoNghieng_V3(menuItem.getTitle().toString(),index+1);

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
    public void click_DialogKetQuaDoNghieng(){
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE) lauoutKichThuoc.setVisibility(View.VISIBLE);
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });
        edtLoaiCotTinhNghieng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] string = {"Tính theo cột dây co.","Tính theo cột monopole."};
                final PopupMenu popupMenu = new PopupMenu(FormMain.this, edtLoaiCotTinhNghieng);
                for (String s : string)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        edtLoaiCotTinhNghieng.setText(menuItem.getTitle());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                lauoutKichThuoc.setVisibility(View.GONE);
                String dataTable12_V0 = GetTableData(Table12_V0,4);
                saveDataOnCacher(dataTable12_V0,"TABLE12_V0");
                String dataTable12_V1 = GetTableData(Table12_V1,4);
                saveDataOnCacher(dataTable12_V1,"TABLE12_V1");
                String dataTable12_V2 = GetTableData(Table12_V2,4);
                saveDataOnCacher(dataTable12_V2,"TABLE12_V2");
                String dataTable12_V3 = GetTableData(Table12_V3,4);
                saveDataOnCacher(dataTable12_V3,"TABLE12_V3");
                String dataTable12 = dataTable12_V0+dataTable12_V1+dataTable12_V2+dataTable12_V3;
                saveDataOnCacher(dataTable12,"TABLE12");
                if(edtLoaiCotTinhNghieng.getText().toString().contains("dây co"))
                    TinhDoNghiengCot(dialog);
                else
                    TinhDoNghiengCotMonopole(dialog);
            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
    public void click_Dialog_LoaiMong(){

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String ChiTietMong = "@Loai mặt bằng_" + LoaiMatBang + "@Loai đốt_" + LoaiDot + "@Loai móng_" + LoaiMong1+ "@Loai móng_" + LoaiMong2+ "@Loai móng_" + LoaiMong3+ "@Loai móng_" + LoaiMong4;
                saveDataOnCacher(ChiTietMong,"TABLEChiTietCot");
                Toast.makeText(getApplicationContext(), "Lưu thành công!" , Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
    public void click_Dialog_ToaDoMong(){
        btnLuuGocMoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNumeric(edtGocXoay.getText().toString())) {
                    Bitmap bitmap = null;
                    if(SoMong==3) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gocxoay);
                    else if(SoMong==4) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.matbang4mong);
                    Matrix matrix = new Matrix();
                    int gocxoay = Integer.parseInt(edtGocXoay.getText().toString().trim());
                    matrix.postRotate(gocxoay);
                    Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                            matrix, true);
                    imgGocXoay.setImageBitmap(rotated);
                    saveDataOnCacher(edtGocXoay.getText().toString(),"GocMoc");
                }
            }
        });
        btnHienBangCanh.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (layoutBangCanh.getVisibility() == View.GONE) {layoutBangCanh.setVisibility(View.VISIBLE);layoutBangGoc.setVisibility(View.GONE);layoutGocXoay.setVisibility(View.GONE);layoutCaoDoChanCot.setVisibility(View.GONE);}
                else layoutBangCanh.setVisibility(View.GONE);
            }
        });
        btnHienGocXoay.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (layoutGocXoay.getVisibility() == View.GONE) {layoutGocXoay.setVisibility(View.VISIBLE);layoutBangGoc.setVisibility(View.GONE);layoutBangCanh.setVisibility(View.GONE);layoutCaoDoChanCot.setVisibility(View.GONE);}
                else layoutGocXoay.setVisibility(View.GONE);
            }
        });
        btnHienBangGoc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (layoutBangGoc.getVisibility() == View.GONE) {layoutBangGoc.setVisibility(View.VISIBLE);layoutBangCanh.setVisibility(View.GONE);layoutGocXoay.setVisibility(View.GONE);layoutCaoDoChanCot.setVisibility(View.GONE);}
                else layoutBangGoc.setVisibility(View.GONE);
            }
        });
        btnHienCaoDoChanCot.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (layoutCaoDoChanCot.getVisibility() == View.GONE) {layoutCaoDoChanCot.setVisibility(View.VISIBLE);layoutBangCanh.setVisibility(View.GONE);layoutGocXoay.setVisibility(View.GONE);layoutBangGoc.setVisibility(View.GONE);}
                else layoutCaoDoChanCot.setVisibility(View.GONE);
            }
        });
        btnHienBangKetQua.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (layoutBangToaDoMong.getVisibility() == View.GONE) {layoutBangToaDoMong.setVisibility(View.VISIBLE);layoutGocXoay.setVisibility(View.GONE);layoutBangGoc.setVisibility(View.GONE);layoutBangCanh.setVisibility(View.GONE);}
                else layoutBangToaDoMong.setVisibility(View.GONE);
            }
        });
        btnHienBangKetQua_Mstower.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v)
            {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (layoutBangToaDoMong_Mstower.getVisibility() == View.GONE) {layoutBangToaDoMong_Mstower.setVisibility(View.VISIBLE);layoutGocXoay.setVisibility(View.GONE);layoutBangGoc.setVisibility(View.GONE);layoutBangCanh.setVisibility(View.GONE);}
                else layoutBangToaDoMong_Mstower.setVisibility(View.GONE);
            }
        });

        btnLuuBangCanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataTableBangCanhCanhCanh = GetTableData(TableCanhCanhCanh,3);
                saveDataOnCacher(dataTableBangCanhCanhCanh,"TABLECanhCanhCanh");
                TinhBangGoc();
                String dataTableBangCanhGocCanh = GetTableData(TableCanhGocCanh,3);
                saveDataOnCacher(dataTableBangCanhGocCanh,"TABLECanhGocCanh");
                saveDataOnCacher(edtGocXoay.getText().toString(),"GocMoc");
                saveDataOnCacher(edtCaoDoChanCot.getText().toString(),"CaoDoChanCot");

                TinhBangToaDo();
                TinhBangToaDo_Mstower();
                //layoutBangCanh.setVisibility(View.GONE);
                //layoutBangGoc.setVisibility(View.GONE);
                //layoutBangToaDoMong.setVisibility(View.VISIBLE);
                //Toast.makeText(FormMain.this,"Tính toạ độ thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnLuuBangGoc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String dataTableBangCanhGocCanh = GetTableData(TableCanhGocCanh,3);
                saveDataOnCacher(dataTableBangCanhGocCanh,"TABLECanhGocCanh");
                TinhBangToaDo();
                TinhBangToaDo_Mstower();
                if(kiemtrabanggoc)
                {
                    layoutBangGoc.setVisibility(View.GONE);
                    String dataTableBia = GetTableData(TableToaDoMong,4);
                    saveDataOnCacher(dataTableBia,"TABLECot");
                    String dataTable = GetTableData(TableToaDoMong_Mstower,4);
                    saveDataOnCacher(dataTable,"TABLEToaDoMstower");
                    Toast.makeText(FormMain.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
                    layoutBangCanh.setVisibility(View.GONE);
                    layoutBangGoc.setVisibility(View.GONE);
                    layoutBangToaDoMong.setVisibility(View.VISIBLE);
                } else
                    Toast.makeText(FormMain.this,"Kết quả đo các cạnh không hợp lý", Toast.LENGTH_SHORT).show();


            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String dataTableBia = GetTableData(TableToaDoMong,4);
                saveDataOnCacher(dataTableBia,"TABLECot");
                String dataTable = GetTableData(TableToaDoMong_Mstower,4);
                saveDataOnCacher(dataTable,"TABLEToaDoMstower");
                Toast.makeText(getApplicationContext(), "Lưu thành công!" , Toast.LENGTH_SHORT).show();
            }
        });
        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
    public void click_Dialog_CaoDoDayCo(){
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String dataTableBangCanhCanhCanh2 = GetTableData(TableCaoDoDayCo,3);
                saveDataOnCacher(dataTableBangCanhCanhCanh2,"TABLECaoDoDayCo");
                Toast.makeText(getApplicationContext(), "Lưu thành công!" , Toast.LENGTH_SHORT).show();
            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    public void click_Dialog_Mstower(){
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String dataTableBia = GetTableData(TableMstower,9);
                saveDataOnCacher(dataTableBia,"TABLEMsTower");
                Toast.makeText(getApplicationContext(), "Lưu thành công!" , Toast.LENGTH_SHORT).show();
            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    public void SetButtonLoaiMong1(final Dialog dialog){
        final Button btnMong1 = (Button) dialog.findViewById(R.id.btnMong1_Loai1);
        final Button btnMong2 = (Button) dialog.findViewById(R.id.btnMong1_Loai2);
        final Button btnMong3 = (Button) dialog.findViewById(R.id.btnMong1_Loai3);
        final Button btnMong4 = (Button) dialog.findViewById(R.id.btnMong1_Loai4);
        final ImageView imgMong = (ImageView) dialog.findViewById(R.id.ImgMong1);

        btnMong1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong1 = "L1";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong1));

            }
        });
        btnMong2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong1 = "L2";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong2));

            }
        });
        btnMong3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong1 = "L3";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong3));

            }
        });
        btnMong4.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong1 = "L4";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong4));

            }
        });
    }
    public void SetButtonLoaiMong2(final Dialog dialog){
        final Button btnMong1 = (Button) dialog.findViewById(R.id.btnMong2_Loai1);
        final Button btnMong2 = (Button) dialog.findViewById(R.id.btnMong2_Loai2);
        final Button btnMong3 = (Button) dialog.findViewById(R.id.btnMong2_Loai3);
        final Button btnMong4 = (Button) dialog.findViewById(R.id.btnMong2_Loai4);
        final ImageView imgMong = (ImageView) dialog.findViewById(R.id.ImgMong2);

        btnMong1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong2 = "L1";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong1));

            }
        });
        btnMong2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong2 = "L2";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong2));

            }
        });
        btnMong3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong2 = "L3";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong3));

            }
        });
        btnMong4.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong2 = "L4";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong4));

            }
        });
    }
    public void SetButtonLoaiMong3(final Dialog dialog){
        final Button btnMong1 = (Button) dialog.findViewById(R.id.btnMong3_Loai1);
        final Button btnMong2 = (Button) dialog.findViewById(R.id.btnMong3_Loai2);
        final Button btnMong3 = (Button) dialog.findViewById(R.id.btnMong3_Loai3);
        final Button btnMong4 = (Button) dialog.findViewById(R.id.btnMong3_Loai4);
        final ImageView imgMong = (ImageView) dialog.findViewById(R.id.ImgMong3);

        btnMong1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong3 = "L1";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong1));

            }
        });
        btnMong2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong3 = "L2";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong2));

            }
        });
        btnMong3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong3 = "L3";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong3));

            }
        });
        btnMong4.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong3 = "L4";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong4));

            }
        });
    }
    public void SetButtonLoaiMong4(final Dialog dialog){
        final Button btnMong1 = (Button) dialog.findViewById(R.id.btnMong4_Loai1);
        final Button btnMong2 = (Button) dialog.findViewById(R.id.btnMong4_Loai2);
        final Button btnMong3 = (Button) dialog.findViewById(R.id.btnMong4_Loai3);
        final Button btnMong4 = (Button) dialog.findViewById(R.id.btnMong4_Loai4);
        final ImageView imgMong = (ImageView) dialog.findViewById(R.id.ImgMong4);

        btnMong1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong4 = "L1";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong1));

            }
        });
        btnMong2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong4 = "L2";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong2));

            }
        });
        btnMong3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong4 = "L3";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong3));

            }
        });
        btnMong4.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LoaiMong4 = "L4";
                btnMong1.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong4.setBackground(getResources().getDrawable(R.drawable.boder_button));
                btnMong3.setBackground(getResources().getDrawable(R.drawable.boder_table));
                btnMong2.setBackground(getResources().getDrawable(R.drawable.boder_table));
                imgMong.setImageDrawable(getResources().getDrawable(R.drawable.loaimong4));

            }
        });
    }
    //endregion
    /**
     * HIỆN TRẠNG VÀ ĐỀ XUẤT
     */
    public void LuuHienTrangBeTong(){
        try
        {
            listHienTrang.set(vitriHienTrang,edtHienTrang.getText().toString().replace("\n","").replace("- ",""));
            textHT = "";
            for (String s:listHienTrang)
            {
                if (!s.equals(""))
                    if(!textHT.trim().equals(""))
                        textHT = textHT.trim() + "\n"+ "- " + s.trim() ;
                    else textHT = textHT.trim() + "- " + s.trim() ;
            }
            if (!HienTrangBeTong.contains(edtHienTrang.getText().toString().replace("\n",""))){
                HienTrangBeTong = HienTrangBeTong.replace("\n","") +"@"+edtHienTrang.getText().toString().replace("\n","");
                saveDataOnTemplate(HienTrangBeTong,"HienTrangBeTong");
                setPopUpBeTong();
            }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu hiện trạng bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuDeXuatBeTong(){
        try
        {    listDeXuat.set(vitriDeXuat,edtDeXuat.getText().toString().replace("\n","").replace("- ",""));
        textDX = "";
        for (String s:listDeXuat)
        {
            if (!s.equals(""))
                if(!textDX.trim().equals(""))
                    textDX= textDX + "\n"+ "- " + s ;
                else textDX = textDX + "- " + s ;
        }
        if (!DeXuatBeTong.contains(edtDeXuat.getText().toString())){
            DeXuatBeTong = DeXuatBeTong.replace("\n","") +"@"+edtDeXuat.getText().toString().replace("\n","");
            saveDataOnTemplate(DeXuatBeTong,"DeXuatBeTong");
            setPopUpBeTong();
        }
    }
        catch (ArithmeticException e)
                {Toast.makeText(FormMain.this, "Lưu đề xuất bị lỗi:" + e, Toast.LENGTH_SHORT).show();}
    }
    /**
     * HIỆN TRẠNG VÀ ĐỀ XUẤT
     */
    public void LuuHienTrangThep(){
        try
        {    listHienTrang.set(vitriHienTrang,edtHienTrang.getText().toString().replace("\n","").replace("- ",""));
        textHT = "";
        for (String s:listHienTrang)
        {
            if (!s.trim().equals(""))
                if(!textHT.trim().equals(""))
                    textHT = textHT + "\n"+ "- " + s ;
                else textHT ="- " + s ;
        }
        if (!HienTrangThep.contains(edtHienTrang.getText().toString().replace("\n",""))){
            HienTrangThep = HienTrangThep.replace("\n","") +"@"+edtHienTrang.getText().toString().replace("\n","");
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThep();
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu hiện trạng bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuDeXuatThep(){
        try
        {    listDeXuat.set(vitriDeXuat,edtDeXuat.getText().toString().replace("\n","").replace("- ",""));
        textDX = "";
        for (String s:listDeXuat)
        {
            if (!s.trim().equals(""))
                if(!textDX.trim().equals(""))
                    textDX= textDX + "\n"+ "- " + s ;
                else textDX = "- " + s ;
        }
        if (!DeXuatThep.contains(edtDeXuat.getText().toString())){
            DeXuatThep = DeXuatThep.replace("\n","") +"@"+edtDeXuat.getText().toString();
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThep();
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu đề xuất bị lỗi:" + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuHienTrangThepBulong(){
        try
        {    listHienTrangBulong.set(vitriHienTrangBulong,edtHienTrangBulong.getText().toString().replace("\n","").replace("- ",""));
        textHTBulong = "";
        for (String s:listHienTrangBulong)
        {
            if (!s.trim().equals(""))
                if(!textHTBulong.trim().equals(""))
                    textHTBulong = textHTBulong + "\n"+ "- " + s ;
                else textHTBulong ="- " + s ;
        }
        if (!HienTrangThep.contains(edtHienTrangBulong.getText().toString().replace("\n",""))){
            HienTrangThep = HienTrangThep.replace("\n","") +"@"+edtHienTrangBulong.getText().toString().replace("\n","");
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThepBulong();
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu hiện trạng bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuDeXuatThepBulong(){
        try
        {    listDeXuatBulong.set(vitriDeXuatBulong,edtDeXuatBulong.getText().toString().replace("\n","").replace("- ",""));
        textDXBulong = "";
        for (String s:listDeXuatBulong)
        {
            if (!s.trim().equals(""))
                if(!textDXBulong.trim().equals(""))
                    textDXBulong= textDXBulong + "\n"+ "- " + s ;
                else textDXBulong = "- " + s ;
        }
        if (!DeXuatThep.contains(edtDeXuatBulong.getText().toString())){
            DeXuatThep = DeXuatThep.replace("\n","") +"@"+edtDeXuat.getText().toString();
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThepBulong();
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu đề xuất bị lỗi:" + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuHienTrangThepCauKien(){
        try
        {    listHienTrangCauKien.set(vitriHienTrangCauKien,edtHienTrangCauKien.getText().toString().replace("\n","").replace("- ",""));
        textHTCauKien = "";
        for (String s:listHienTrangCauKien)
        {
            if (!s.trim().equals(""))
                if(!textHTCauKien.trim().equals(""))
                    textHTCauKien = textHTCauKien + "\n"+ "- " + s ;
                else textHTCauKien ="- " + s ;
        }
        if (!HienTrangThep.contains(edtHienTrangCauKien.getText().toString().replace("\n",""))){
            HienTrangThep = HienTrangThep.replace("\n","") +"@"+edtHienTrangCauKien.getText().toString().replace("\n","");
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThepCauKien();
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu hiện trạng bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuDeXuatThepCauKien(){
        try
        {    listDeXuatCauKien.set(vitriDeXuatCauKien,edtDeXuatCauKien.getText().toString().replace("\n","").replace("- ",""));
        textDXCauKien = "";
        for (String s:listDeXuatCauKien)
        {
            if (!s.trim().equals(""))
                if(!textDXCauKien.trim().equals(""))
                    textDXCauKien= textDXCauKien + "\n"+ "- " + s ;
                else textDXCauKien = "- " + s ;
        }
        if (!DeXuatThep.contains(edtDeXuatCauKien.getText().toString())){
            DeXuatThep = DeXuatThep.replace("\n","") +"@"+edtDeXuatCauKien.getText().toString();
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThepCauKien();
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu đề xuất bị lỗi:" + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuHienTrangThepMocCo(){
        try
        {    listHienTrangMocCo.set(vitriHienTrangMocCo,edtHienTrangMocCo.getText().toString().replace("\n","").replace("- ",""));
        textHTMocCo = "";
        for (String s:listHienTrangMocCo)
        {
            if (!s.trim().equals(""))
                if(!textHTMocCo.trim().equals(""))
                    textHTMocCo = textHTMocCo + "\n"+ "- " + s ;
                else textHTMocCo ="- " + s ;
        }
        if (!HienTrangThep.contains(edtHienTrangMocCo.getText().toString().replace("\n",""))){
            HienTrangThep = HienTrangThep.replace("\n","") +"@"+edtHienTrangMocCo.getText().toString().replace("\n","");
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThepMocCo();
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu hiện trạng bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuDeXuatThepMocCo(){
        try
        {    listDeXuatMocCo.set(vitriDeXuatMocCo,edtDeXuatMocCo.getText().toString().replace("\n","").replace("- ",""));
        textDXMocCo = "";
        for (String s:listDeXuatMocCo)
        {
            if (!s.trim().equals(""))
                if(!textDXMocCo.trim().equals(""))
                    textDXMocCo= textDXMocCo + "\n"+ "- " + s ;
                else textDXMocCo = "- " + s ;
        }
        if (!DeXuatThep.contains(edtDeXuatMocCo.getText().toString())){
            DeXuatThep = DeXuatThep.replace("\n","") +"@"+edtDeXuatMocCo.getText().toString();
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThepMocCo();
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu đề xuất bị lỗi:" + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuHienTrangThepKheHo(){
        try
        {    listHienTrangKheHo.set(vitriHienTrangKheHo,edtHienTrangKheHo.getText().toString().replace("\n","").replace("- ",""));
        textHTKheHo = "";
        for (String s:listHienTrangKheHo)
        {
            if (!s.trim().equals(""))
                if(!textHTKheHo.trim().equals(""))
                    textHTKheHo = textHTKheHo + "\n"+ "- " + s ;
                else textHTKheHo ="- " + s ;
        }
        if (!HienTrangThep.contains(edtHienTrangKheHo.getText().toString().replace("\n",""))){
            HienTrangThep = HienTrangThep.replace("\n","") +"@"+edtHienTrangKheHo.getText().toString().replace("\n","");
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThep();
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu hiện trạng bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuDeXuatThepKheHo(){
        try
        {   listDeXuatKheHo.set(vitriDeXuatKheHo,edtDeXuatKheHo.getText().toString().replace("\n","").replace("- ",""));
        textDXKheHo = "";
        for (String s:listDeXuatKheHo)
        {
            if (!s.trim().equals(""))
                if(!textDXKheHo.trim().equals(""))
                    textDXKheHo= textDXKheHo + "\n"+ "- " + s ;
                else textDXKheHo ="- " + s ;
        }
        if (!DeXuatThep.contains(edtDeXuatKheHo.getText().toString())){
            DeXuatThep = DeXuatThep.replace("\n","") +"@"+edtDeXuatKheHo.getText().toString();
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThep();
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu đề xuất bị lỗi:" + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuHienTrangThepPhuKien(){
        try
        {    listHienTrangPhuKien.set(vitriHienTrangPhuKien,edtHienTrangPhuKien.getText().toString().replace("\n","").replace("- ",""));
        textHTPhuKien = "";
        for (String s:listHienTrangPhuKien)
        {
            if (!s.trim().equals(""))
                if(!textHTPhuKien.trim().equals(""))
                    textHTPhuKien = textHTPhuKien+ "- " + s  + "\n";
                else textHTPhuKien = "- " + s  + "\n";
        }
        if (!HienTrangThep.contains(edtHienTrangPhuKien.getText().toString().replace("\n",""))){
            HienTrangThep = HienTrangThep.replace("\n","") +"@"+edtHienTrangPhuKien.getText().toString().replace("\n","");
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThepPhuKien();
        }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu hiện trạng bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void LuuDeXuatThepPhuKien(){
        try
        {
                listDeXuatPhuKien.set(vitriDeXuatPhuKien,edtDeXuatPhuKien.getText().toString().replace("\n","").replace("- ",""));
            textDXPhuKien = "";
            for (String s:listDeXuatPhuKien)
            {
                if (!s.equals(""))
                    if(!textDXPhuKien.trim().equals(""))
                        textDXPhuKien= textDXPhuKien + "- " + s+ "\n" ;
                    else textDXPhuKien = "- " + s + "\n";
            }
            if (!DeXuatThep.contains(edtDeXuatPhuKien.getText().toString())){
                DeXuatThep = DeXuatThep.replace("\n","") +"@"+edtDeXuatPhuKien.getText().toString();
                saveDataOnTemplate(DeXuatThep,"DeXuatThep");
                setPopUpThepPhuKien();
            }
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Lưu đề xuất bị lỗi:" + e, Toast.LENGTH_SHORT).show();}
    }
    /*** DIALOG*/
    //region DIALOG
    public void DialogMong(final String tenmong,final int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_mong);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        listPhuKienCotDauDuoi.clear();
        listCauKienTiepDia.clear();
        listHienTrang.clear();
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"LoaiMayDo.txt");

        if(vitri==1)
        {
            listCauKienTiepDia.addAll(Arrays.asList("Thoát sét cho kim thu sét","Thoát sét cho thiết bị treo trên cột","Thoát sét cho chân cột","Thoát sét cho phòng máy"));
            listPhuKienCotDauDuoi.addAll(Arrays.asList("Bản định vị chân cột"));
        }
        else
        {
            listPhuKienCotDauDuoi.addAll(Arrays.asList("Ma ní đầu dưới " + tenmong, "Tăng đơ " + tenmong,"Khoá cáp đầu dưới " + tenmong,"Dây co " + tenmong));
            listCauKienTiepDia.addAll(Arrays.asList("Thoát sét cho cáp thép dây co " + tenmong));
        }

        AnhXa_Dialogmong(dialog);
        click_DialogMong(vitri);
        if (file.exists())
        {
            String text = readText(file);
            edtLoaiMay.setText(text.trim());
        }
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        lando = 1;
        vitriHienTrang=1;
        vitriDeXuat=1;


        set_ViewTable3(vitri);
        if(vitri==1) {btnlauoutKichThuocBulong.setVisibility(View.VISIBLE);btnHienLucSiet.setVisibility(View.GONE);btnlauoutKichThuocMocCo.setVisibility(View.GONE);btnKichThuocPhuKien.setVisibility(View.VISIBLE);btnHienLucCang.setVisibility(View.GONE);btnHienBangLucSiet.setVisibility(View.GONE);btnHienBangLucCang.setVisibility(View.GONE);}
        if(vitri!=1) set_ViewTable8_Duoi(vitri);
        if(vitri!=1) set_ViewTable9(vitri);

        if (!textCopy.equals("")) btnPaste.setVisibility(View.VISIBLE);
        tvTenMong.setText(tenmong);
        setPopUp();
        setPopUpBeTong();
        //MOC CO
        vitriHienTrangMocCo=1;
        vitriDeXuatMocCo=1;
        tvViTriHienTrangMocCo.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangMocCo) + "/" + String.valueOf(listHienTrangMocCo.size()));
        tvViTriDeXuatMocCo.setText("Đề xuất: " + String.valueOf(vitriDeXuatMocCo) + "/" + String.valueOf(listDeXuatMocCo.size()));

        if (!textCopyMocCo.equals("")) btnPasteMocCo.setVisibility(View.VISIBLE);
        setPopUpMocCo();
        setPopUpThepMocCo();
        //BULONG NEO
        vitriHienTrangBulong=1;
        vitriDeXuatBulong=1;
        set_ViewTable2(vitri);
        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriDeXuat) + "/" + String.valueOf(listDeXuat.size()));
        tvViTriHienTrangBulong.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangBulong) + "/" + String.valueOf(listHienTrangBulong.size()));
        tvViTriDeXuatBulong.setText("Đề xuất: " + String.valueOf(vitriDeXuatBulong) + "/" + String.valueOf(listDeXuatBulong.size()));
        setPopUpBulong();
        setPopUpThepBulong();
    }
    public void DialogDam(final String tenmong,int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_dam);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Table3 = dialog.findViewById(R.id.Table3);
        Table2 = dialog.findViewById(R.id.Table2);
        AnhXa_Dialogdam(dialog);
        click_DialogDam(vitri);
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        vitriHienTrang=1;
        vitriDeXuat=1;

        set_ViewTable2(vitri);tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));

        tvTenMong.setText(data_Table2[vitri][0].toString().replace(" co",""));
        if(!tenmong.contains("ầm"))
            set_ViewTable3(vitri);
        else
            set_ViewTable3(vitri-2);
        setPopUpBeTong();
        setPopUp();
    }
    public void DialogTiepDia(final String tenmong,int vitri) {
        dialogCauKien = new Dialog(this,R.style.PauseDialog2);
        dialogCauKien.setContentView(R.layout.dialog_nhapdulieu_caukientiepdia);
        dialogCauKien.show();
        dialogCauKien.setCanceledOnTouchOutside(false);
        AnhXa_DialogTiepDia(dialogCauKien);
        click_DialogTiepDia(vitri);
        vitriHienTrangCauKien=1;vitriDeXuatCauKien=1;
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table4 = dialogCauKien.findViewById(R.id.Table4);
        tvTenCauKien.setText(tenmong);
        set_ViewTable4(vitri);
        tvViTriHienTrangCauKien.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangCauKien) + "/" + String.valueOf(listHienTrangCauKien.size()));
        tvViTriDeXuatCauKien.setText("Đề xuất: " + String.valueOf(vitriHienTrangCauKien) + "/" + String.valueOf(listHienTrangCauKien.size()));
        setPopUpThepCauKien();
        setPopUpCauKien();
    }
    public void DialogPhuKien(final String tenmong,int vitri,final ArrayList<String> listPhuKien) {
        dialogPhuKien = new Dialog(this,R.style.PauseDialog2);
        dialogPhuKien.setContentView(R.layout.dialog_nhapdulieu_phukiencot);
        dialogPhuKien.show();
        dialogPhuKien.setCanceledOnTouchOutside(false);
        AnhXa_DialogPhuKien(dialogPhuKien);
        click_DialogPhuKien(vitri,listPhuKien);
        if (!textCopy.equals("")) btnPaste.setVisibility(View.VISIBLE);
        vitriHienTrangPhuKien=1;vitriDeXuatPhuKien=1;
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table7 = dialogPhuKien.findViewById(R.id.Table7);
        tvTenPhuKien.setText(tenmong);
        if (tvTenPhuKien.getText().toString().contains("Móng")) set_ViewTable7_Duoi(vitri);
        else if (tvTenPhuKien.getText().toString().contains("Tầng")) set_ViewTable7_Tren(vitri);
        else if (!tvTenPhuKien.getText().toString().contains("Móng") && !tvTenPhuKien.getText().toString().contains("Tầng")) set_ViewTable7_Tren(vitri);

        tvViTriHienTrangPhuKien.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangPhuKien) + "/" + String.valueOf(listHienTrangPhuKien.size()));
        tvViTriDeXuatPhuKien.setText("Đề xuất: " + String.valueOf(vitriDeXuatPhuKien) + "/" + String.valueOf(listDeXuatPhuKien.size()));
        setPopUpThepPhuKien();
        setPopUpPhuKien();
    }
    public void DialogThanCot(final String tenmong,int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_thancot);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogThanCot(dialog);
        if(tenmong.equals("Chân cột") ){
            btnlauoutKichThuoc.setVisibility(View.GONE);
        }
        //else vitri--;
        click_DialogThanCot(vitri-1);
        if (!textCopy.equals(""))btnPaste.setVisibility(View.VISIBLE);
        if (!textCopyKheHo.equals("")) btnPasteKheHo.setVisibility(View.VISIBLE);
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        vitriHienTrang=1;vitriDeXuat=1;
        vitriHienTrangKheHo=1;vitriDeXuatKheHo=1;
        Table6 = dialog.findViewById(R.id.Table6);
        Table10 = dialog.findViewById(R.id.Table10);
        tvTenMong.setText(tenmong);
        set_ViewTable6(vitri-1);
        if (vitri <=listLienKetCot.size())
        {
            set_ViewTable10(vitri);
            edtThanhCanhKheHo.setText("<3");
            tvViTriHienTrangKheHo.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangKheHo) + "/" + String.valueOf(listHienTrangKheHo.size()));
            tvViTriDeXuatKheHo.setText("Đề xuất: " + String.valueOf(vitriHienTrangKheHo) + "/" + String.valueOf(listHienTrangKheHo.size()));
            setPopUpKheHo();
            setPopUpThepKheHo();
        }
        else btnlauoutKichThuocKheHo.setVisibility(View.GONE);

        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
        setPopUpThanCot();
        setPopUpThep();

    }
    public void DialogDienTro(final String tenmong,int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_dientro);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogDienTro(dialog);
        click_DialogDienTro(vitri);
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table5 = dialog.findViewById(R.id.Table5);
        tvTenMong.setText(tenmong);
        set_ViewTable5(vitri);
    }
    public void DialogLucCang(final String tenmong,int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_luccang);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        listPhuKienCotDauTren.clear();
        listPhuKienCotDauTren.addAll(Arrays.asList("Vòng ốp móc dây co và bu lông vòng ốp dây co "+ tenmong,"Ma ní đầu trên " + tenmong,"Khoá cáp đầu trên " + tenmong));
        AnhXa_DialogLucCang(dialog);
        click_DialogLucCang(vitri);
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        set_ViewTable8(vitri);
        tvTenMong.setText(tenmong);
        //set_ViewTable10(vitri);
        //setPopUpKheHo();
    }
    public void DialogAnten() {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_anten);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogAnten(dialog);
        click_DialogAnten();
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table11 = dialog.findViewById(R.id.Table11);
        set_ViewTable11();
        setPopUpAnten();
    }
    public void DialogDoNghieng_ChanCot() {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_chancot);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogDoNghieng(dialog);
        click_DialogDoNghieng_ChanCot();
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table12_ChanCot = dialog.findViewById(R.id.Table12_ChanCot);
        set_ViewTable_ChanCot();

    }
    public void DialogDoNghieng_HuongBac() {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_huongbac);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogDoNghieng(dialog);
        click_DialogDoNghieng_HuongBac();
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table12_HuongBac = dialog.findViewById(R.id.Table12_ChanCot);
        set_ViewTable_HuongBac();

    }
    public void DialogDoNghieng_DinhCot() {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_dinhcot);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogDoNghieng(dialog);
        click_DialogDoNghieng_DinhCot();
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table12_DinhCot = dialog.findViewById(R.id.Table12_ChanCot);
        set_ViewTable_DinhCot();

    }
    public void DialogDoNghieng_V0(final String tenmong,int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_donghieng);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogDoNghieng(dialog);
        click_DialogDoNghieng_V0(vitri);
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table12_V0 = dialog.findViewById(R.id.Table12_V0);
        set_ViewTable12_V0(vitri);
        tvTenMong.setText(tenmong);

    }
    public void DialogDoNghieng_V1(final String tenmong,int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_donghieng);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogDoNghieng(dialog);
        click_DialogDoNghieng_V1(vitri);
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table12_V1 = dialog.findViewById(R.id.Table12_V0);
        set_ViewTable12_V1(vitri);
        tvTenMong.setText(tenmong);

    }
    public void DialogDoNghieng_V2(final String tenmong,int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_donghieng);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogDoNghieng(dialog);
        click_DialogDoNghieng_V2(vitri);
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table12_V2 = dialog.findViewById(R.id.Table12_V0);
        set_ViewTable12_V2(vitri);
        tvTenMong.setText(tenmong);

    }
    public void DialogDoNghieng_V3(final String tenmong,int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_donghieng);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogDoNghieng(dialog);
        click_DialogDoNghieng_V3(vitri);
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table12_V3 = dialog.findViewById(R.id.Table12_V0);
        set_ViewTable12_V3(vitri);
        tvTenMong.setText(tenmong);

    }
    public void DialogKetQuaDoNghieng() {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_kq_donghieng);
        dialog.show();

        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogKetQuaDoNghieng(dialog);
        click_DialogKetQuaDoNghieng();
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table12_V0 = dialog.findViewById(R.id.Table12_V0);
        Table12_V1 = dialog.findViewById(R.id.Table12_V1);
        Table12_V2 = dialog.findViewById(R.id.Table12_V2);
        Table12_V3 = dialog.findViewById(R.id.Table12_V3);


        set_ViewKetQuaTable12();
    }
    public void DialogLoaiMong() {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_loaimong);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_Dialog_LoaiMong(dialog);
        click_Dialog_LoaiMong();
        if (SoMong==3)
        {layoutMong4.setVisibility(View.GONE);}

        SetButtonLoaiMong1(dialog);
        SetButtonLoaiMong2(dialog);
        SetButtonLoaiMong3(dialog);
        SetButtonLoaiMong4(dialog);
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */

    }
    public void DialogToaDoMong() {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_toadomong);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_Dialog_ToaDoMong(dialog);
        click_Dialog_ToaDoMong();

        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        setDataTableCanhCanhCanh();
        setDataTableCanhGocCanh();
        setDataTableToaDoMong();
    }
    public void DialogCaoDoDayCo() {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_caododayco);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_Dialog_CaoDoDayCo(dialog);
        click_Dialog_CaoDoDayCo();

        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        setDataTableCaoDoDayCo();
    }
    public void DialogMstower() {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_mstower);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_Dialog_Mstower(dialog);
        click_Dialog_Mstower();

        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        setDataTableMstower();
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLECaoDoDayCo.txt");
        if (file.exists())
            getDataTableMstower();
    }
    //endregion
    /**
     * SET DU LIỆU CÁC BẢNG
     */
    //region SET DỮ LIỆU CÁC BẢNG
    /**BẢNG Tọa độ móng**/
    public void setDataTableMstower() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLEMstower.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =8;
        int Rowcount = TableMstower.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }

        for(int i=1;i<SoDot+1;i++) {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            SetButtonFACE(TableMstower,i,3);
            setDataTable(row,data,i,ColumnCount);

        }

    }
    public void getDataTableMstower() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLEMstower.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = TableMstower.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        /****/
        for(int i=1;i<SoDot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            //STT ĐỐT
            TextView STT = (TextView) (((TableRow) row)).getChildAt(0);
            STT.setText(String.valueOf(i));
            //CHIỀU CAO ĐỐT
            TextView Chieucaodot = (TextView) (((TableRow) row)).getChildAt(1);
            Chieucaodot.setText(String.valueOf(ChieuCaoDot));
            //Kich thước ĐỐT
            final TextView rongdot = (TextView) (((TableRow) row)).getChildAt(2);
            rongdot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(FormMain.this, rongdot);
                    popupMenu.getMenu().add("0.3");
                    popupMenu.getMenu().add("0.4");
                    popupMenu.getMenu().add("0.5");
                    popupMenu.getMenu().add("0.6");
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem menuItem) {
                            for(int i=1;i<SoDot+1;i++)
                            {
                                TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
                                TextView rongdot = (TextView) (((TableRow) row)).getChildAt(2);
                                rongdot.setText(menuItem.getTitle());
                                TextView space = (TextView) (((TableRow) row)).getChildAt(7);
                                space.setText(String.valueOf((int) (Double.parseDouble(ChieuCaoDot) / Double.valueOf(menuItem.getTitle().toString()))));
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });

            final  ArrayList<String> listBulong = new ArrayList<String>();
            listBulong.addAll(Arrays.asList("0.3x0.3@M12-"+String.valueOf(3*SoCot),"0.4x0.4@M18-"+String.valueOf(3*SoCot),"0.6x0.6@M22-"+String.valueOf(3*SoCot)));
            TextView LoaiBulong = (TextView) (((TableRow) row)).getChildAt(4);
            LoaiBulong.setText(TRABANG(KichThuocCot,listBulong));

        }
        TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(1);
        if(SoGaChongXoay>0)
        {
            TextView Cross = (TextView) (((TableRow) row)).getChildAt(5);
            Cross.setText("CT");
        }

    }
    /**BẢNG Cao độ dây co**/
    public void setDataTableCaoDoDayCo() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLECaoDoDayCo.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =3;
        int Rowcount = TableCaoDoDayCo.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableCaoDoDayCo)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        /****/
        for(int i=1;i<SoTangDay+1;i++)
        {

            TableRow row = (TableRow) (((TableLayout) TableCaoDoDayCo)).getChildAt(i);
            setDataTable(row,data,i,ColumnCount);
            row.setVisibility(View.VISIBLE);
            for (int j= 0; j<listGCX.size();j++)
            {
                if (String.valueOf(i).contains(listGCX.get(j).trim()))
                {
                    //Log.d("GCX",listGCX.get(j));
                    setDataTable(row,data,i,ColumnCount);
                    TextView tv = (TextView) (((TableRow) row)).getChildAt(2);
                    tv.setText("1");
                }
                else
                {
                    setDataTable(row,data,i,ColumnCount);
                }
            }

        }


    }
    /**BẢNG Tọa kích thước cạnh**/
    public void setDataTableCanhCanhCanh() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLECanhCanhCanh.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =3;
        int Rowcount = TableCanhCanhCanh.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableCanhCanhCanh)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoMong+1;i++) {
            TableRow row = (TableRow) (((TableLayout) TableCanhCanhCanh)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
        }

    }
    /**BẢNG Tọa kích thước góc**/
    public void setDataTableCanhGocCanh() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLECanhGocCanh.txt");
        String[][] data=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =3;
        int Rowcount = TableCanhGocCanh.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableCanhGocCanh)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoMong+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableCanhGocCanh)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
        }

    }
    /**BẢNG Tọa độ móng**/
    public void setDataTableToaDoMong() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLECot.txt");

        File file2 = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        file2 = new File(file2,"Data"+ MaTram);
        File fileSoGocMoc = new File(file2,"GocMoc.txt");

        File file3 = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        file3 = new File(file3,"Data"+ MaTram);
        File fileCaoDoChanCot = new File(file3,"CaoDoChanCot.txt");

        if (fileSoGocMoc.exists())
        {
            String text = readText(fileSoGocMoc);
            edtGocXoay.setText(text);
        }
        else edtGocXoay.setText("0");
        if (fileCaoDoChanCot.exists())
        {
            String text = readText(fileCaoDoChanCot);
            edtCaoDoChanCot.setText(text);
        }
        else edtCaoDoChanCot.setText("0.0");

        String[][] data=DataforPath(file);
        if (SoMong==4) imgMatBang.setImageDrawable(getResources().getDrawable(R.drawable.matbang2));
        if (SoMong==3) imgMatBang.setImageDrawable(getResources().getDrawable(R.drawable.toadomong));
        if (SoMong==3) imgGocXoay.setImageDrawable(getResources().getDrawable(R.drawable.gocxoay));
        if (SoMong==4) imgGocXoay.setImageDrawable(getResources().getDrawable(R.drawable.matbang4mong));

        if(isNumeric(edtGocXoay.getText().toString())) {
            Bitmap bitmap = null;
            if(SoMong==3) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gocxoay);
            else if(SoMong==4) bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.matbang4mong);
            Matrix matrix = new Matrix();
            int gocxoay = Integer.parseInt(edtGocXoay.getText().toString().trim());
            matrix.postRotate(gocxoay);
            Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                    matrix, true);
            imgGocXoay.setImageBitmap(rotated);
        }
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = TableToaDoMong.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableToaDoMong)).getChildAt(i);
            row.setVisibility(View.GONE);
            TableRow row2 = (TableRow) (((TableLayout) TableCaoDoChanCot)).getChildAt(i);
            row2.setVisibility(View.GONE);
        }
        /****/
        for(int i=1;i<SoMong+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableToaDoMong)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);

            TableRow row2 = (TableRow) (((TableLayout) TableCaoDoChanCot)).getChildAt(i);
            row2.setVisibility(View.VISIBLE);
            if (data.length>1){
            TextView tv = (TextView) (((TableRow) row2)).getChildAt(1);
            tv.setText(data[i][3]); // set selected text data into the
            tv.setHeight(65);
            tv.setTextSize(15);}
        }
        /**SET TEXT ON ROW**/

        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableToaDoMong_Mstower)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        /****/
        for(int i=1;i<SoMong+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableToaDoMong_Mstower)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
        }

    }
    /**BẢNG BÌA**/
    private void DialogThongTinChung() {
        final Dialog dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_thongtinchung_dayco);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogTTC(dialog);

        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLEBia.txt");
        String[][] data=DataforPath(file);
        tvMaTramTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] tenTram = MaTram.split("_");
                tvDiaDiemTTC.setText(tenTram[1]);
                tvMaTramTTC.setText(tenTram[0]);
            }
        });
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,MaTram);
        if(mediaStorageDir.exists())
        {
            File[] files=mediaStorageDir.listFiles();
            if(files.length==8) tbBiaLoaiCot.setText("Tự đứng");
            if(files.length==10) tbBiaLoaiCot.setText("Dây co");
        }
        listLoaiCot.clear();
        listSoMong.clear();
        listSoChanCot.clear();
        listViTriDat.clear();
        listkichthuoccot.clear();
        listchieucaodot.clear();
        listcapdobenbt.clear();
        listsotangdayco.clear();

        listLoaiCot.addAll(Arrays.asList("Tự đứng","Dây co","Cột cóc","Cột chống cứng","Cột monopole","Cột bê tông li tâm"));
        listSoMong.addAll(Arrays.asList("3","4","5","6"));
        listSoChanCot.addAll(Arrays.asList("3","4"));
        listViTriDat.addAll(Arrays.asList("Trên mái","Dưới đất"));
        listkichthuoccot.addAll(Arrays.asList("0.3x0.3","0.4x0.4","0.6x0.6","1.0x1.0"));
        listchieucaodot.addAll(Arrays.asList("3","6"));
        listcapdobenbt.addAll(Arrays.asList("B15","B20","B25","B30","B35"));
        listsotangdayco.addAll(Arrays.asList("1","2","3","4","5","6"));
        /**ÁNH XẠ VỚI DÒNG */
        int ColumnCount =2;
        int Rowcount = TableBiaTTC.getChildCount();
        for(int i=0;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableBiaTTC)).getChildAt(i);
            setDataTable(row,data,i,ColumnCount);
        }
        String[] tenTram = MaTram.split("_");
        tvDiaDiemTTC.setText(tenTram[1]);
        tvMaTramTTC.setText(tenTram[0]);
        SetPopup(listLoaiCot,TableBiaTTC,3,1);
        SetPopup(listSoMong,TableBiaTTC,8,1);
        SetPopup(listViTriDat,TableBiaTTC,6,1);
        SetPopup(listSoChanCot,TableBiaTTC,9,1);
        SetPopup(listkichthuoccot,TableBiaTTC,5,1);
        SetPopup(listchieucaodot,TableBiaTTC,10,1);
        SetPopup(listcapdobenbt ,TableBiaTTC,7,1);
        SetPopup(listsotangdayco ,TableBiaTTC,11,1);
        click_DialogTTC( dialog);
    }
    /**BẢNG 2**/
    public void set_ViewTable2(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE2.txt");
        data_Table2=DataforPath(file);
        listHienTrang.clear();
        listDeXuat.clear();
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table2.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        //Toast.makeText(FormMain.this, data[1][2], Toast.LENGTH_LONG).show();
        /****/
        for(int i=1;i<SoMong+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table2,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
            //setColumnData(row,KichThuocMong,1);
        }
        /**HIỂN THỊ DẦM D1,D2**/
        //Log.d("Vị tri",ViTriDat);
        TableRow BuLong = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-4);
        BuLong.setVisibility(View.VISIBLE);
        setDataTable(BuLong,data_Table2,SoMong+2,ColumnCount);
        ////CheckDataTable(BuLong,data,SoMong+2,ColumnCount);
        TableRow MocCo = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-3);
        MocCo.setVisibility(View.VISIBLE);
        setDataTable(MocCo,data_Table2,SoMong+3,ColumnCount);
        ////CheckDataTable(MocCo,data,SoMong+3,ColumnCount);
        if (ViTriDat.contains("Trênmái"))
        {
            TableRow Dam1 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-2);
            Dam1.setVisibility(View.VISIBLE);
            setDataTable(Dam1,data_Table2,SoMong+4,ColumnCount);
            ////CheckDataTable(Dam1,data,SoMong+4,ColumnCount);
            TableRow Dam2 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-1);
            Dam2.setVisibility(View.VISIBLE);
            setDataTable(Dam2,data_Table2,SoMong+5,ColumnCount);
            ////CheckDataTable(Dam2,data,SoMong+5,ColumnCount);
        }
        /****/
        try
        {
            edtKichThuoc.setText(data_Table2[vitri][1]);
        }catch (Exception e)
        {
            /****/
            String dataTable2 = GetTableData(Table2,4);
            saveDataOnCacher(dataTable2,"TABLE2");
            data_Table2=DataforPath(file);
        }
        //region MÓNG
        edtKichThuoc.setText(data_Table2[vitri][1]);
        //TÁCH THÀNH TỪNG Ý HIỆN TRANG
        String str = String.valueOf(data_Table2[vitri][2]).trim();
        if(!String.valueOf(data_Table2[vitri][2]).trim().equals("null"))
        {
            if (String.valueOf(data_Table2[vitri][2]).contains(("- "))) {
                String[] dongHienTrang = data_Table2[vitri][2].split(("- "));
                for (String s : dongHienTrang) {
                    listHienTrang.add(s.trim().replace("\n", ""));
                }
                String hientrang = listHienTrang.get(vitriHienTrang).replace("\n", "");
                edtHienTrang.setText(hientrang);
            }
             else edtHienTrang.setText(data_Table2[vitri][2]);
        }
        for(int i= 0 ;i<=10;i++)
        {
            listHienTrang.add("");
        }
        //TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
        if(!String.valueOf(data_Table2[vitri][3]).equals(""))
            if(String.valueOf(data_Table2[vitri][3]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table2[vitri][3].split(("- "));
                for( String s:dongHienTrang)
                {
                    listDeXuat.add(s);
                }
                edtDeXuat.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
            }
            else  edtDeXuat.setText(data_Table2[vitri][3]);

        for(int i= 0 ;i<=10;i++)
        {
            listDeXuat.add("");
        }
        //edtDeXuat.setText(data_Table2[vitri][3]);
        //endregion
        // region MÓC CO
        if (!tvTenMong.getText().toString().contains("u lông"))
        {
            try
            {
            listHienTrangMocCo.clear();
            String HienTrang = "";
            //Tách kích thước từng móng đầu dưới
            if(String.valueOf(data_Table2[SoMong+3][1]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table2[SoMong+3][1].split(("- "));
                for( String s:dongHienTrang)
                {
                    if (s.contains("Móng " +tvTenMong.getText().toString().split("Móng ")[1]))
                    {
                        s= s + " ";
                        edtKichThuocMocCo.setText(s.replace("Móng "+tvTenMong.getText().toString().split("Móng ")[1] +": " ,"").replace("\n",""));
                        break;
                    }
                }
            }
            //TÁCH THÀNH TỪNG Ý HIỆN TRANG
            if(String.valueOf(data_Table2[SoMong+3][2]).contains(("Móng ")))
            {
                String[] tachchuoilan1 = data_Table2[SoMong+3][2].split("Móng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenMong.getText().toString().split("Móng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            HienTrang= tachchuoilan2[1];

                        break;
                    }
                }

            }
            else
                edtHienTrangMocCo.setText(data_Table2[SoMong+3][2]);
            if(HienTrang.contains("- "))
            {
                String[] dongHienTrang = HienTrang.split(("- "));
                for( String s:dongHienTrang)
                {
                    listHienTrangMocCo.add(s);
                }

                edtHienTrangMocCo.setText(listHienTrangMocCo.get(vitriHienTrangMocCo).replace("\n",""));
            }

            else
                edtHienTrangMocCo.setText(HienTrang);
            for(int i= 0 ;i<=10;i++)
            {
                listHienTrangMocCo.add("");
            }
            //TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
            listDeXuatMocCo.clear();
                String DeXuat = "";

                if(String.valueOf(data_Table2[SoMong+3][3]).contains(("Móng ")))
                {
                    String[] tachchuoilan1 = data_Table2[SoMong+3][3].split("Móng ");
                    for(int i=0;i<tachchuoilan1.length;i++)
                    {
                        String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                        if (tachchuoilan2[0].contains(tvTenMong.getText().toString().split("Móng ")[1]))
                        {
                            if (tachchuoilan2.length>1)
                                DeXuat= tachchuoilan2[1];

                            break;
                        }
                    }

                }
                else
                    edtDeXuatMocCo.setText(data_Table2[SoMong+3][3]);
                if(DeXuat.contains("- "))
                {
                    String[] dongDeXuat = DeXuat.split(("- "));
                    for( String s:dongDeXuat)
                    {
                        listDeXuatMocCo.add(s);
                    }
                    edtDeXuatMocCo.setText(listDeXuatMocCo.get(vitriDeXuatMocCo).replace("\n",""));
                }

                else
                    edtDeXuatMocCo.setText(DeXuat);
                for(int i= 0 ;i<=10;i++)
                {
                    listDeXuatMocCo.add("");
                }
            }catch (Exception e)
            {
                /****/
                String dataTable2 = GetTableData(Table2,4);
                saveDataOnCacher(dataTable2,"TABLE2");
                data_Table2=DataforPath(file);
                dialog.dismiss();
            }
        }
        //endregion
        //region BULONG
        edtKichThuocBulong.setText(data_Table2[SoMong+2][1]);
        //TÁCH THÀNH TỪNG Ý HIỆN TRANG
        listHienTrangBulong.clear();
        if(!String.valueOf(data_Table2[SoMong+2][2]).equals(""))
            if(String.valueOf(data_Table2[SoMong+2][2]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table2[SoMong+2][2].split(("- "));
                for( String s:dongHienTrang)
                {
                    listHienTrangBulong.add(s);
                }
                edtHienTrangBulong.setText(listHienTrangBulong.get(vitriHienTrangBulong).replace("\n",""));
            }

            else  edtHienTrangBulong.setText(data_Table2[SoMong+2][2]);

        for(int i= 0 ;i<=10;i++)
        {
            listHienTrangBulong.add("");
        }
        //TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
        listDeXuatBulong.clear();
        if(!String.valueOf(data_Table2[SoMong+2][3]).equals(""))
            if(String.valueOf(data_Table2[SoMong+2][3]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table2[SoMong+2][3].split(("- "));
                for( String s:dongHienTrang)
                {
                    listDeXuatBulong.add(s);
                }
                edtDeXuatBulong.setText(listDeXuatBulong.get(vitriDeXuatBulong).replace("\n",""));
            }

            else  edtDeXuatBulong.setText(data_Table2[SoMong+2][3]);

        for(int i= 0 ;i<=10;i++)
        {
            listDeXuatBulong.add("");
        }
        //edtDeXuat.setText(data_Table2[SoMong+2][3]);
        //endregion
    }
    public void set_DataTable2(int vitri) {

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table2.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
            row.setVisibility(View.GONE);
        }

        /****/
        for(int i=1;i<SoMong+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table2,i,ColumnCount);
            ////CheckDataTable(row,data,i,ColumnCount);
            //setColumnData(row,KichThuocMong,1);
        }
        /**HIỂN THỊ DẦM D1,D2**/
        //Log.d("Vị tri",ViTriDat);
        TableRow BuLong = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-4);
        BuLong.setVisibility(View.VISIBLE);
        setDataTable(BuLong,data_Table2,SoMong+2,ColumnCount);
        ////CheckDataTable(BuLong,data,SoMong+2,ColumnCount);
        TableRow MocCo = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-3);
        MocCo.setVisibility(View.VISIBLE);
        setDataTable(MocCo,data_Table2,SoMong+3,ColumnCount);
        ////CheckDataTable(MocCo,data,SoMong+3,ColumnCount);
        if (ViTriDat.contains("Trênmái"))
        {
            TableRow Dam1 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-2);
            Dam1.setVisibility(View.VISIBLE);
            setDataTable(Dam1,data_Table2,SoMong+4,ColumnCount);
            ////CheckDataTable(Dam1,data,SoMong+4,ColumnCount);
            TableRow Dam2 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-1);
            Dam2.setVisibility(View.VISIBLE);
            setDataTable(Dam2,data_Table2,SoMong+5,ColumnCount);
            ////CheckDataTable(Dam2,data,SoMong+5,ColumnCount);
        }
        /****/
        String dataTable2 = GetTableData(Table2,4);
            saveDataOnCacher(dataTable2,"TABLE2");

    }
    /**BẢNG 3*/
    public void set_ViewTable3(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE3.txt");
        data_Table3=DataforPath(file);
        /**DÒNG MÓNG M0*/
        int ColumnCount =19;
        int Rowcount = Table3.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }

        for(int i=1;i<SoMong+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table3,i,ColumnCount);
            ////CheckDataTable(row,data,i,ColumnCount);
        }
        /**HIỂN THỊ DẦM D1,D2**/
        if (ViTriDat.contains("Trênmái"))
        {
            TableRow Dam1 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-2);
            Dam1.setVisibility(View.VISIBLE);
            setDataTable(Dam1,data_Table3,SoMong+2,ColumnCount);
            //CheckDataTable(Dam1,data,SoMong+2,ColumnCount);
            TableRow Dam2 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-1);
            Dam2.setVisibility(View.VISIBLE);
            setDataTable(Dam2,data_Table3,SoMong+3,ColumnCount);
            ////CheckDataTable(Dam2,data,SoMong+3,ColumnCount);
        }
        /****/
        try
        {
            edtGiaTriDo.setText(data_Table3[vitri][1]);
        }catch (Exception e)
        {
            /****/
            String dataTable3 = GetTableData(Table3,ColumnCount);
            saveDataOnCacher(dataTable3,"TABLE3");
            data_Table3=DataforPath(file);
        }
        edtLanDo.setText("1");
        edtGiaTriDo.setText(data_Table3[vitri][1]);
        tvGiaTriTrungBinh.setText(data_Table3[vitri][17]);
        edtGocBan.setText(data_Table3[vitri][18]);
    }
    public void set_DataTable3(int vitri) {
        /**DÒNG MÓNG M0*/
        int ColumnCount =19;
        int Rowcount = Table3.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }

        for(int i=1;i<SoMong+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table3,i,ColumnCount);
            ////CheckDataTable(row,data,i,ColumnCount);
        }
        /**HIỂN THỊ DẦM D1,D2**/
        if (ViTriDat.contains("Trênmái"))
        {
            TableRow Dam1 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-2);
            Dam1.setVisibility(View.VISIBLE);
            setDataTable(Dam1,data_Table3,SoMong+2,ColumnCount);
            //CheckDataTable(Dam1,data,SoMong+2,ColumnCount);
            TableRow Dam2 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-1);
            Dam2.setVisibility(View.VISIBLE);
            setDataTable(Dam2,data_Table3,SoMong+3,ColumnCount);
            ////CheckDataTable(Dam2,data,SoMong+3,ColumnCount);
        }
        String dataTable2 = GetTableData(Table3,ColumnCount);
        saveDataOnCacher(dataTable2,"TABLE3");
    }
    /**BẢNG 4**/
    public void set_ViewTable4(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE4.txt");
        data_Table4=DataforPath(file);
        listHienTrangCauKien.clear();
        listDeXuatCauKien.clear();

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table4.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table4)).getChildAt(i);
            setDataTable(row,data_Table4,i,ColumnCount);
        }
        /****/
        try
        {
            String a = data_Table4[vitri][1];
        }catch (Exception e)
        {
            /****/
            String dataTable4 = GetTableData(Table4,4);
            saveDataOnCacher(dataTable4,"TABLE4");
            data_Table4=DataforPath(file);
        }
//        edtKichThuocCauKien.setText(data_Table4[vitri][1]);
//
//        listHienTrangCauKien.clear();
//        if(!String.valueOf(data_Table4[vitri][2]).equals(""))
//            if(String.valueOf(data_Table4[vitri][2]).contains(("- ")))
//            {
//                String[] dongHienTrang = data_Table4[vitri][2].split(("- "));
//                for( String s:dongHienTrang)
//                {
//                    listHienTrangCauKien.add(s);
//                }
//
//                edtHienTrangCauKien.setText(listHienTrangCauKien.get(vitriHienTrangCauKien).replace("\n",""));
//            }
//
//            else
//                edtHienTrangCauKien.setText(data_Table4[vitri][2]);
//        for(int i= 0 ;i<=10;i++)
//        {
//            listHienTrangCauKien.add("");
//        }
        //TÁCH THÀNH TỪNG Ý HIỆN TRANG
        String HienTrang = "";
        String DeXuat = "";

        if (tvTenCauKien.getText().toString().contains("Móng"))
        {
            //TÁCH THÀNH TỪNG Ý KÍCH THƯỚC
            if(String.valueOf(data_Table4[vitri][1]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table4[vitri][1].split(("- "));
                for( String s:dongHienTrang)
                {
                    if (s.contains("Móng " +tvTenCauKien.getText().toString().split("Móng ")[1]))
                    {
                        s= s + " ";
                        edtKichThuocCauKien.setText(s.replace("Móng "+tvTenCauKien.getText().toString().split("Móng ")[1] +": " ,"").replace("\n",""));
                        break;
                    }
                }
            }
            else
                edtKichThuocCauKien.setText(data_Table4[vitri][1]);
            //TÁCH THÀNH TỪNG Ý HIỆN TRANG
            if(String.valueOf(data_Table4[vitri][2]).contains(("Móng ")))
            {
                String[] tachchuoilan1 = data_Table4[vitri][2].split("Móng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenCauKien.getText().toString().split("Móng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            HienTrang= tachchuoilan2[1];

                        break;
                    }
                }

            }
            else
                edtHienTrangCauKien.setText(data_Table4[vitri][2]);
            if(String.valueOf(data_Table4[vitri][3]).contains(("Móng ")))
            {
                String[] tachchuoilan1 = data_Table4[vitri][3].split("Móng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenCauKien.getText().toString().split("Móng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            DeXuat= tachchuoilan2[1];

                        break;
                    }
                }

            }
            else
                edtDeXuatCauKien.setText(data_Table4[vitri][3]);
        }
        else if (!tvTenCauKien.getText().toString().contains("Móng") && !tvTenCauKien.getText().toString().contains("Tầng")){
            edtKichThuocCauKien.setText(data_Table4[vitri][1]);
            HienTrang =String.valueOf(data_Table4[vitri][2]).replace("null","") ;
            DeXuat =String.valueOf(data_Table4[vitri][3]).replace("null","") ;

        }
        //HIỆN TRẠNG
        if(HienTrang.contains("- "))
        {
            String[] dongHienTrang = HienTrang.split(("- "));
            for( String s:dongHienTrang)
            {
                listHienTrangCauKien.add(s);
            }

            edtHienTrangCauKien.setText(listHienTrangCauKien.get(vitriHienTrangCauKien).replace("\n",""));
        }
        else
            edtHienTrangCauKien.setText(HienTrang);
        for(int i= 0 ;i<=10;i++)
        {
            listHienTrangCauKien.add("");
        }
        //ĐỀ XUẤT
        if(DeXuat.contains("- "))
        {
            String[] dongDeXuat = DeXuat.split(("- "));
            for( String s:dongDeXuat)
            {
                listDeXuatCauKien.add(s);
            }
            edtDeXuatCauKien.setText(listDeXuatCauKien.get(vitriDeXuatCauKien).replace("\n",""));
        }
        else
            edtDeXuatCauKien.setText(DeXuat);
        for(int i= 0 ;i<=10;i++)
        {
            listDeXuatCauKien.add("");
        }
        //TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
       /* if (tvTenCauKien.getText().toString().contains("Móng"))
        {
            //Tách kích thước từng móng đầu dưới
            if(String.valueOf(data_Table4[vitri][3]).contains(("- ")))
            {
                if(String.valueOf(data_Table4[vitri][3]).contains(("Móng ")))
                {
                    String[] dongDeXuat = data_Table4[vitri][3].split(("- "));
                    for( String s:dongDeXuat)
                    {
                        if (s.contains("Móng " +tvTenCauKien.getText().toString().split("Móng ")[1]))
                        {
                            s= s + " ";
                            edtDeXuatCauKien.setText(s.replace("Móng "+tvTenCauKien.getText().toString().split("Móng ")[1] +": " ,"").replace("\n",""));
                            break;
                        }
                    }

                }else
                    DeXuat=data_Table4[vitri][3];

            }
            else if(String.valueOf(data_Table4[vitri][3]).contains(("Móng ")))
            {
                String[] tachchuoilan1 = data_Table4[vitri][3].split("Móng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenCauKien.getText().toString().split("Móng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            DeXuat= tachchuoilan2[1];

                        break;
                    }
                }

            }
            else
                edtDeXuatCauKien.setText(data_Table4[vitri][3]);
        }
        else if (tvTenCauKien.getText().toString().contains("Tầng"))
        {
            //Tách kích thước từng móng đầu dưới
            if(String.valueOf(data_Table4[vitri][3]).contains(("- ")))
            {
                String[] dongDeXuat = data_Table4[vitri][3].split(("- "));
                for( String s:dongDeXuat)
                {
                    if (s.contains("Tầng " +tvTenCauKien.getText().toString().split("Tầng ")[1]))
                    {
                        s= s + " ";
                        edtDeXuatCauKien.setText(s.replace("Tầng "+tvTenCauKien.getText().toString().split("Tầng ")[1] +": " ,"").replace("\n",""));
                        break;
                    }
                }
            }
            if(String.valueOf(data_Table4[vitri][3]).contains(("Tầng ")))
            {
                String[] tachchuoilan1 = data_Table4[vitri][3].split("Tầng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenCauKien.getText().toString().split("Tầng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            DeXuat= tachchuoilan2[1];

                        break;
                    }
                }

            }
            else
                edtDeXuatCauKien.setText(data_Table4[vitri][3]);
        }
        else if (!tvTenCauKien.getText().toString().contains("Móng") && !tvTenCauKien.getText().toString().contains("Tầng")) DeXuat =String.valueOf(data_Table4[vitri][3]).replace("null","") ;
*/
        /*//TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
        listDeXuatCauKien.clear();
        if(!String.valueOf(data_Table4[vitri][3]).equals(""))
            if(String.valueOf(data_Table4[vitri][3]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table4[vitri][3].split(("- "));
                for( String s:dongHienTrang)
                {
                    listDeXuatCauKien.add(s);
                }
                edtDeXuatCauKien.setText(listDeXuatCauKien.get(vitriDeXuatCauKien).replace("\n",""));
            }

            else  edtDeXuatCauKien.setText(data_Table4[vitri][3]);

        for(int i= 0 ;i<=10;i++)
        {
            listDeXuatCauKien.add("");
        }*/
    }
    public void set_DataTable4(int vitri) {

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table4.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table4)).getChildAt(i);
            setDataTable(row,data_Table4,i,ColumnCount);
        }
        String dataTable4 = GetTableData(Table4,4);
        saveDataOnCacher(dataTable4,"TABLE4");
    }
    /**BẢNG 5**/
    public void set_ViewTable5(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE5.txt");
        data_Table5=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table5.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table5)).getChildAt(i);
            setDataTable(row,data_Table5,i,ColumnCount);
        }
        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table5.length<3)
        {
            /****/
            String dataTable5 = GetTableData(Table5,4);
            saveDataOnCacher(dataTable5,"TABLE5");
            data_Table5=DataforPath(file);
        }
        edtKichThuoc.setText(data_Table5[vitri][0]);
        edtHienTrang.setText(data_Table5[vitri][2]);
        edtDeXuat.setText(data_Table5[vitri][3]);
    }
    public void set_DataTable5(int vitri) {

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table5.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table5)).getChildAt(i);
            setDataTable(row,data_Table5,i,ColumnCount);
        }
        String dataTable5 = GetTableData(Table5,4);
        saveDataOnCacher(dataTable5,"TABLE5");
    }
    /**BẢNG 6.2**/
    public void set_ViewTable6(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE6.txt");
        data_Table6=DataforPath(file);

        /**
         * LẤY CHIỀU CAO ĐỐT TỪ MSTOWER
         */
        File fileMSTOWER = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        fileMSTOWER = new File(fileMSTOWER,"Data"+ MaTram);
        fileMSTOWER = new File(fileMSTOWER,"TABLEMstower.txt");
        ArrayList<String> chieuCaoDot = new ArrayList<>();
        if (fileMSTOWER.exists()){
            String[][] dataMSTOWER =DataforPath(fileMSTOWER);
            for(int i= dataMSTOWER.length-1; i>0;i--)
            {
                chieuCaoDot.add(dataMSTOWER[i][1]);
            }
        }
        else
            for (int i=0 ;i<data_Table6.length;i++)
            {
                chieuCaoDot.add(ChieuCaoDot);
            }
        /**DÒNG ĐỐT 1*/
        int ColumnCount =6;
        int Rowcount = Table6.getChildCount();
        if (data_Table6.length<=1)
        {
            /****/
            String dataTable6 = GetTableData(Table6,4);
            saveDataOnCacher(dataTable6,"TABLE6");
            data_Table6=DataforPath(file);
        }
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table6)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table6,i,ColumnCount);
        }
        for(int i=1;i<SoDot +1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table6)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table6,i,ColumnCount);
            try
            {
                data_Table6[i][1] = KichThuocCot + "x"+ chieuCaoDot.get(i-1);
            }
            catch (Exception e)
            {
                data_Table6[i][1] = KichThuocCot + "x"+ ChieuCaoDot;
            }
            setColumnData(row,data_Table6[i][1],1);
        }
        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));


        edtKichThuoc.setText(data_Table6[vitri][1]);
        if (data_Table6[vitri][2] != null)
        if(!data_Table6[vitri][2].contains("x"))
        edtThanhCanh.setText(data_Table6[vitri][2]);
        else
        {
            edtThanhCanh.setText(data_Table6[vitri][2].split("x")[0]);
            edtDoDayThanhCanh.setText(data_Table6[vitri][2].split("x")[1]);
        }
        edtThanhGiang.setText(data_Table6[vitri][3]);
        //TÁCH THÀNH TỪNG Ý HIỆN TRANG
        listHienTrang.clear();
        if(!String.valueOf(data_Table6[vitri][4]).equals(""))
            if(String.valueOf(data_Table6[vitri][4]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table6[vitri][4].split(("- "));
                for( String s:dongHienTrang)
                {
                    listHienTrang.add(s);
                }

                edtHienTrang.setText(listHienTrang.get(vitriHienTrang).replace("\n",""));
            }
            else
                edtHienTrang.setText(data_Table6[vitri][4]);
        for(int i= 0 ;i<=10;i++)
        {
            listHienTrang.add("");
        }
        //TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
        listDeXuat.clear();
        if(!String.valueOf(data_Table6[vitri][5]).equals(""))
            if(String.valueOf(data_Table6[vitri][5]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table6[vitri][5].split(("- "));
                for( String s:dongHienTrang)
                {
                    listDeXuat.add(s);
                }
                edtDeXuat.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
            }

            else  edtDeXuat.setText(data_Table6[vitri][5]);

        for(int i= 0 ;i<=10;i++)
        {
            listDeXuat.add("");
        }
        //edtDeXuat.setText(data_Table6[vitri][5]);

    }
    public void set_DataTable6(int vitri) {

        /**DÒNG ĐỐT 1*/
        int ColumnCount =6;
        int Rowcount = Table6.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table6)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table6,i,ColumnCount);
        }

        for(int i=1;i<SoDot +1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table6)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table6,i,ColumnCount);
            //setColumnData(row,KichThuocCot,1);
        }
        String dataTable6 = GetTableData(Table6,6);
        saveDataOnCacher(dataTable6,"TABLE6");
    }
    /**TABLE 7**/
    public void set_ViewTable7_Tren(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE7_Tren.txt");
        data_Table7=DataforPath(file);
        listHienTrangPhuKien.clear();
        listDeXuatPhuKien.clear();

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table7.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table7)).getChildAt(i);
            setDataTable(row,data_Table7,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table7.length<3)
        {
            /****/
            String dataTable7 = GetTableData(Table7,4);
            saveDataOnCacher(dataTable7,"TABLE7_Tren");
            data_Table7=DataforPath(file);
        }
        //TÁCH THÀNH TỪNG Ý HIỆN TRANG

        String HienTrang = "";
        String DeXuat = "";

        if (tvTenPhuKien.getText().toString().contains("Móng"))
        {
            //TÁCH THÀNH TỪNG Ý Kich thước
            if(String.valueOf(data_Table7[vitri][1]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table7[vitri][1].split(("- "));
                for( String s:dongHienTrang)
                {
                    if (s.contains("Móng " +tvTenPhuKien.getText().toString().split("Móng ")[1]))
                    {
                        s= s + " ";
                        edtKichThuocPhuKien.setText(s.replace("Móng "+tvTenPhuKien.getText().toString().split("Móng ")[1] +": " ,"").replace("\n",""));
                        break;
                    }
                }
            } else edtKichThuocPhuKien.setText(data_Table7[vitri][1]);
            //TÁCH THÀNH TỪNG Ý HIỆN TRANG
            if(String.valueOf(data_Table7[vitri][2]).contains(("Móng ")))
            {
                String[] tachchuoilan1 = data_Table7[vitri][2].split("Móng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Móng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            HienTrang= tachchuoilan2[1];

                        break;
                    }
                }

            }
            else
                edtHienTrangPhuKien.setText(data_Table7[vitri][2]);
            //TÁCH THÀNH TỪNG Ý DỀ XUẤT
            if(String.valueOf(data_Table7[vitri][3]).contains(("Móng ")))
            {
                String[] tachchuoilan1 = data_Table7[vitri][3].split("Móng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Móng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            DeXuat= tachchuoilan2[1];
                        break;
                    }
                }
            }
            else
                edtDeXuatPhuKien.setText(data_Table7[vitri][3]);
        }
        else if (tvTenPhuKien.getText().toString().contains("Tầng"))
        {
            //TÁCH THÀNH TỪNG Ý Kich thước
            if(String.valueOf(data_Table7[vitri][1]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table7[vitri][1].split(("- "));
                for( String s:dongHienTrang)
                {
                    if (s.contains("Tầng " +tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                    {
                        s= s + " ";
                        edtKichThuocPhuKien.setText(s.replace("Tầng "+tvTenPhuKien.getText().toString().split("Tầng ")[1] +": " ,"").replace("\n",""));
                        break;
                    }
                }
            }
            //TÁCH THÀNH TỪNG Ý HIỆN TRANG
            if(String.valueOf(data_Table7[vitri][2]).contains(("Tầng ")))
            {
                String[] tachchuoilan1 = data_Table7[vitri][2].split("Tầng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            HienTrang= tachchuoilan2[1];
                        break;
                    }
                }
            }
            else
                edtHienTrangPhuKien.setText(data_Table7[vitri][2]);
            //TÁCH THÀNH TỪNG Ý DỀ XUẤT
            if(String.valueOf(data_Table7[vitri][3]).contains(("Tầng ")))
            {
                String[] tachchuoilan1 = data_Table7[vitri][3].split("Tầng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            DeXuat= tachchuoilan2[1];

                        break;
                    }
                }
            }
            else
                edtDeXuatPhuKien.setText(data_Table7[vitri][3]);
        }
        else if (!tvTenPhuKien.getText().toString().contains("Móng") && !tvTenPhuKien.getText().toString().contains("Tầng"))
        {
            try
            {
                edtKichThuocPhuKien.setText(data_Table7[vitri][1].replace("null",""));
            }
            catch (Exception e) {

            }

            HienTrang =String.valueOf(data_Table7[vitri][2]).replace("null","");
            DeXuat =String.valueOf(data_Table7[vitri][3]).replace("null","");
        }
        //HIÊN TRẠNG
        if(HienTrang.contains("- "))
        {
            String[] dongHienTrang = HienTrang.split(("- "));
            for( String s:dongHienTrang)
            {
                listHienTrangPhuKien.add(s);
            }
            try{
                edtHienTrangPhuKien.setText(listHienTrangPhuKien.get(vitriHienTrangPhuKien).replace("\n",""));
            }catch (Exception e) {
            }
        }
        else
            edtHienTrangPhuKien.setText(HienTrang);
        //ĐỀ XUẤT
        if(DeXuat.contains("- "))
        {
            String[] dongHienTrang = DeXuat.split(("- "));
            for( String s:dongHienTrang)
            {
                listDeXuatPhuKien.add(s);
            }
            try{
                edtDeXuatPhuKien.setText(listDeXuatPhuKien.get(vitriDeXuatPhuKien).replace("\n",""));
            }catch (Exception e) {

            }
        }
        else
            edtDeXuatPhuKien.setText(DeXuat);
        for(int i= 0 ;i<=10;i++)
        {
            listHienTrangPhuKien.add("");
            listDeXuatPhuKien.add("");
        }
        /*//TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
        listDeXuatPhuKien.clear();
        if(!String.valueOf(data_Table7[vitri][3]).equals(""))
            if(String.valueOf(data_Table7[vitri][3]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table7[vitri][3].split(("- "));
                for( String s:dongHienTrang)
                {
                    listDeXuatPhuKien.add(s);
                }
                edtDeXuatPhuKien.setText(listDeXuatPhuKien.get(vitriDeXuat).replace("\n",""));
            }

            else  edtDeXuatPhuKien.setText(data_Table7[vitri][3]);

        for(int i= 0 ;i<=10;i++)
        {
            listDeXuatPhuKien.add("");
        }*/
        //edtDeXuat.setText(data_Table7[vitri][3]);
    }
    public void set_DataTable7_Tren(int vitri) {
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table7.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table7)).getChildAt(i);
            setDataTable(row,data_Table7,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        String dataTable7 = GetTableData(Table7,4);
        saveDataOnCacher(dataTable7,"TABLE7_Tren");
    }
    /**TABLE 7**/
    public void set_ViewTable7_Duoi(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE7_Duoi.txt");
        data_Table7=DataforPath(file);
        listHienTrangPhuKien.clear();
        listDeXuatPhuKien.clear();
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table7.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table7)).getChildAt(i);
            setDataTable(row,data_Table7,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table7.length<3)
        {
            /****/
            String dataTable7 = GetTableData(Table7,4);
            saveDataOnCacher(dataTable7,"TABLE7_Duoi");
            data_Table7=DataforPath(file);
        }
        //TÁCH THÀNH TỪNG Ý HIỆN TRANG
        String HienTrang = "";
        String DeXuat = "";
        if (tvTenPhuKien.getText().toString().contains("Móng"))
        {
            //TÁCH THÀNH TỪNG Ý Kich thước
            if(String.valueOf(data_Table7[vitri][1]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table7[vitri][1].split(("- "));
                for( String s:dongHienTrang)
                {
                    if (s.contains("Móng " +tvTenPhuKien.getText().toString().split("Móng ")[1]))
                    {
                        s= s + " ";
                        edtKichThuocPhuKien.setText(s.replace("Móng "+tvTenPhuKien.getText().toString().split("Móng ")[1] +": " ,"").replace("\n",""));
                        break;
                    }
                }
            }
            //TÁCH THÀNH TỪNG Ý HIỆN TRANG
            if(String.valueOf(data_Table7[vitri][2]).contains(("Móng ")))
            {
                String[] tachchuoilan1 = data_Table7[vitri][2].split("Móng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Móng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            HienTrang= tachchuoilan2[1];

                        break;
                    }
                }

            }
            else
                edtHienTrangPhuKien.setText(data_Table7[vitri][2]);
            //TÁCH THÀNH TỪNG Ý DỀ XUẤT
            if(String.valueOf(data_Table7[vitri][3]).contains(("Móng ")))
            {
                String[] tachchuoilan1 = data_Table7[vitri][3].split("Móng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Móng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            DeXuat= tachchuoilan2[1];

                        break;
                    }
                }

            }
            else
                edtDeXuatPhuKien.setText(data_Table7[vitri][3]);
        }
        else if (tvTenPhuKien.getText().toString().contains("Tầng"))
        {
            //TÁCH THÀNH TỪNG Ý Kich thước
            if(String.valueOf(data_Table7[vitri][1]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table7[vitri][1].split(("- "));
                for( String s:dongHienTrang)
                {
                    if (s.contains("Tầng " +tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                    {
                        s= s + " ";
                        edtKichThuocPhuKien.setText(s.replace("Tầng "+tvTenPhuKien.getText().toString().split("Tầng ")[1] +": " ,"").replace("\n",""));
                        break;
                    }
                }
            }
            //TÁCH THÀNH TỪNG Ý HIỆN TRANG
            if(String.valueOf(data_Table7[vitri][2]).contains(("Tầng ")))
            {
                String[] tachchuoilan1 = data_Table7[vitri][2].split("Tầng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            HienTrang= tachchuoilan2[1];

                        break;
                    }
                }

            }
            else
                edtHienTrangPhuKien.setText(data_Table7[vitri][2]);
            //TÁCH THÀNH TỪNG Ý DỀ XUẤT
            if(String.valueOf(data_Table7[vitri][3]).contains(("Tầng ")))
            {
                String[] tachchuoilan1 = data_Table7[vitri][3].split("Tầng ");
                for(int i=0;i<tachchuoilan1.length;i++)
                {
                    String[] tachchuoilan2 = tachchuoilan1[i].split(":");
                    if (tachchuoilan2[0].contains(tvTenPhuKien.getText().toString().split("Tầng ")[1]))
                    {
                        if (tachchuoilan2.length>1)
                            DeXuat= tachchuoilan2[1];

                        break;
                    }
                }

            }
            else
                edtDeXuatPhuKien.setText(data_Table7[vitri][3]);
        }
        else if (!tvTenPhuKien.getText().toString().contains("Móng") && !tvTenPhuKien.getText().toString().contains("Tầng")) HienTrang =String.valueOf(data_Table7[vitri][2]).replace("null","") ;
        //HIÊN TRẠNG
        if(HienTrang.contains("- "))
        {
            String[] dongHienTrang = HienTrang.split(("- "));
            for( String s:dongHienTrang)
            {
                listHienTrangPhuKien.add(s);
            }

            edtHienTrangPhuKien.setText(listHienTrangPhuKien.get(vitriHienTrangPhuKien).replace("\n",""));
        }
        else
            edtHienTrangPhuKien.setText(HienTrang);
        //ĐỀ XUẤT
        if(DeXuat.contains("- "))
        {
            String[] dongHienTrang = DeXuat.split(("- "));
            for( String s:dongHienTrang)
            {
                listDeXuatPhuKien.add(s);
            }

            edtDeXuatPhuKien.setText(listDeXuatPhuKien.get(vitriDeXuatPhuKien).replace("\n",""));
        }
        else
            edtDeXuatPhuKien.setText(DeXuat);
        for(int i= 0 ;i<=10;i++)
        {
            listHienTrangPhuKien.add("");
            listDeXuatPhuKien.add("");
        }
        /*//TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
        listDeXuatPhuKien.clear();
        if(!String.valueOf(data_Table7[vitri][3]).equals(""))
            if(String.valueOf(data_Table7[vitri][3]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table7[vitri][3].split(("- "));
                for( String s:dongHienTrang)
                {
                    listDeXuatPhuKien.add(s);
                }
                edtDeXuatPhuKien.setText(listDeXuatPhuKien.get(vitriDeXuat).replace("\n",""));
            }

            else  edtDeXuatPhuKien.setText(data_Table7[vitri][3]);

        for(int i= 0 ;i<=10;i++)
        {
            listDeXuatPhuKien.add("");
        }*/
        //edtDeXuat.setText(data_Table7[vitri][3]);

    }
    public void set_DataTable7_Duoi(int vitri) {

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table7.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table7)).getChildAt(i);
            setDataTable(row,data_Table7,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        String dataTable7 = GetTableData(Table7,4);
        saveDataOnCacher(dataTable7,"TABLE7_Duoi");

    }
    /**TABLE 8 DƯỚI**/
    public void set_ViewTable8_Duoi(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE8_Duoi.txt");
        data_Table8=DataforPath(file);

        /**SET TEXT ON ROW**/
        final int ColumnCount = SoMong*2+1;
        int Rowcount = Table8.getChildCount();
        /**CHO ẨN HẾT CÁC DÒNG*/
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        /**CHO DÒNG ĐẦU HIỂN THỊ VÀ ẨN CÁC CỘT THỪA**/
        TableRow row0 = (TableRow) (((TableLayout) Table8)).getChildAt(0);
        for(int j=SoMong+1;j<row0.getChildCount();j++)
        {
            TextView column = (TextView) (((TableRow) row0)).getChildAt(j);
            column.setVisibility(View.GONE);
        }

        /**CHO CÁC DÒNG CÒN LẠI HIỂN TRỊ VÀ ẨN CÁC CỘT THỪA**/
        for(int i=1;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            for(int j=SoMong*2+1;j<row.getChildCount();j++)
            {
                TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                column.setVisibility(View.GONE);
            }
        }
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
            tv.setText(listTangDayCo.get(i-2).replace("Tầng dây ","T"));
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
        /*if(SoGaChongXoay>0)
        for(int i=SoTangDay+1;i<listTangDayCo.size()+2;i=i+2)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
            tv.setText(listGCX.get(vtgcx)+"a");
            TableRow row2 = (TableRow) (((TableLayout) Table8)).getChildAt(i+1);
            TextView tv2 = (TextView)(((TableRow) row2)).getChildAt(0);
            tv2.setText(listGCX.get(vtgcx)+"b");
            vtgcx++;
            //CheckDataTable(row,data,i-1,ColumnCount);
        }

         */
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            setDataTable2(row,data_Table8,i-1,ColumnCount);
            //CheckDataTable(row,data,i-1,ColumnCount);
        }


        /****/

        try
        {
            for (int j=0;j<listTangDayCo.size();j++)
            {
                listEdtLucSiet.get(j).setText(data_Table8[j+1][(vitri-1)*2]);
            }
        }catch (Exception e)
        {
            /****/
            String dataTable8 = GetTableData(Table8,ColumnCount);
            saveDataOnCacher(dataTable8,"TABLE8_Duoi");
            data_Table8=DataforPath(file);
        }
        for (int j=0;j<listTangDayCo.size();j++)
        {
            listEdtLucSiet.get(j).setText(data_Table8[j+1][(vitri-1)*2]);
        }

    }
    public void set_DataTable8_Duoi(int vitri) {
        /**SET TEXT ON ROW**/
        final int ColumnCount = SoMong*2+1;
        int Rowcount = Table8.getChildCount();

        /**CHO ẨN HẾT CÁC DÒNG*/
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        /**CHO DÒNG ĐẦU HIỂN THỊ VÀ ẨN CÁC CỘT THỪA**/
        TableRow row0 = (TableRow) (((TableLayout) Table8)).getChildAt(0);
        for(int j=SoMong+1;j<row0.getChildCount();j++)
        {
            TextView column = (TextView) (((TableRow) row0)).getChildAt(j);
            column.setVisibility(View.GONE);
        }

        /**CHO CÁC DÒNG CÒN LẠI HIỂN TRỊ VÀ ẨN CÁC CỘT THỪA**/
        for(int i=1;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            for(int j=SoMong*2+1;j<row.getChildCount();j++)
            {
                TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                column.setVisibility(View.GONE);
            }
        }
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
            tv.setText(listTangDayCo.get(i-2).replace("Tầng dây ","T"));
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
        /*
        if(SoGaChongXoay>0)
            for(int i=SoTangDay+1;i<listTangDayCo.size()+2;i=i+2)
            {
                TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
                TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
                tv.setText(listGCX.get(vtgcx)+"a");
                TableRow row2 = (TableRow) (((TableLayout) Table8)).getChildAt(i+1);
                TextView tv2 = (TextView)(((TableRow) row2)).getChildAt(0);
                tv2.setText(listGCX.get(vtgcx)+"b");
                vtgcx++;
                //CheckDataTable(row,data,i-1,ColumnCount);
            }

         */
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            setDataTable2(row,data_Table8,i-1,ColumnCount);
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
        /****/
        String dataTable8 = GetTableData(Table8,ColumnCount);
        saveDataOnCacher(dataTable8,"TABLE8_Duoi");

    }
    /**TABLE 8**/
    public void set_ViewTable8(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE8_tren.txt");
        data_Table8=DataforPath(file);

        /**SET TEXT ON ROW**/
        final int ColumnCount = SoMong*2+1;
        int Rowcount = Table8.getChildCount();
        /**CHO ẨN HẾT CÁC DÒNG*/
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        /**CHO DÒNG ĐẦU HIỂN THỊ VÀ ẨN CÁC CỘT THỪA**/
        TableRow row0 = (TableRow) (((TableLayout) Table8)).getChildAt(0);
        for(int j=SoMong+1;j<row0.getChildCount();j++)
        {
            TextView column = (TextView) (((TableRow) row0)).getChildAt(j);
            column.setVisibility(View.GONE);
        }

        /**CHO CÁC DÒNG CÒN LẠI HIỂN TRỊ VÀ ẨN CÁC CỘT THỪA**/
        for(int i=1;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            for(int j=SoMong*2+1;j<row.getChildCount();j++)
            {
                TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                column.setVisibility(View.GONE);
            }
        }

        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
            tv.setText(listTangDayCo.get(i-2).replace("Tầng dây ","T"));
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
        /*
        for(int i=SoTangDay+2;i<listTangDayCo.size()+2;i=i+2)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
            tv.setText(listGCX.get(vtgcx)+"a");
            TableRow row2 = (TableRow) (((TableLayout) Table8)).getChildAt(i+1);
            TextView tv2 = (TextView)(((TableRow) row2)).getChildAt(0);
            tv2.setText(listGCX.get(vtgcx)+"b");
            vtgcx++;
            //CheckDataTable(row,data,i-1,ColumnCount);
        }

         */
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            setDataTable2(row,data_Table8,i-1,ColumnCount);
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
        /****/
        try
        {
            for (int i=2;i<ColumnCount+2;i=i+2)
            {
                listEdtLucSiet.get(i).setText(data_Table8[vitri][i+1]);
            }
        }catch (Exception e)
        {
            /****/
            String dataTable8 = GetTableData(Table8,ColumnCount);
            saveDataOnCacher(dataTable8,"TABLE8_Tren");
            data_Table8=DataforPath(file);
        }

        for (int i=2;i<ColumnCount+2;i=i+2)
        {
            listEdtLucSiet.get(i).setText(data_Table8[vitri][i+1]);
        }

    }
    public void set_DataTable8(int vitri) {
        /**SET TEXT ON ROW**/
        final int ColumnCount = SoMong*2+1;
        int Rowcount = Table8.getChildCount();
        /**CHO ẨN HẾT CÁC DÒNG*/
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        /**CHO DÒNG ĐẦU HIỂN THỊ VÀ ẨN CÁC CỘT THỪA**/
        TableRow row0 = (TableRow) (((TableLayout) Table8)).getChildAt(0);
        for(int j=SoMong+1;j<row0.getChildCount();j++)
        {
            TextView column = (TextView) (((TableRow) row0)).getChildAt(j);
            column.setVisibility(View.GONE);
        }

        /**CHO CÁC DÒNG CÒN LẠI HIỂN TRỊ VÀ ẨN CÁC CỘT THỪA**/
        for(int i=1;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            for(int j=SoMong*2+1;j<row.getChildCount();j++)
            {
                TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                column.setVisibility(View.GONE);
            }
        }
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
            tv.setText(listTangDayCo.get(i-2).replace("Tầng dây ","T"));
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
        /*
        for(int i=SoTangDay+2;i<listTangDayCo.size()+2;i=i+2)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
            tv.setText(listGCX.get(vtgcx)+"a");
            TableRow row2 = (TableRow) (((TableLayout) Table8)).getChildAt(i+1);
            TextView tv2 = (TextView)(((TableRow) row2)).getChildAt(0);
            tv2.setText(listGCX.get(vtgcx)+"b");
            vtgcx++;
            //CheckDataTable(row,data,i-1,ColumnCount);
        }

         */
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
            setDataTable2(row,data_Table8,i-1,ColumnCount);
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
        /****/
        String dataTable8 = GetTableData(Table8,ColumnCount);
        saveDataOnCacher(dataTable8,"TABLE8_Tren");

    }
    /**TABLE 9**/
    public void set_ViewTable9(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE9.txt");
        data_Table9=DataforPath(file);

        /**SET TEXT ON ROW**/

        final int ColumnCount = (1+SoMong)*2+1;
        int Rowcount = Table9.getChildCount();

        /**CHO ẨN HẾT CÁC DÒNG*/
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        /**CHO DÒNG ĐẦU HIỂN THỊ VÀ ẨN CÁC CỘT THỪA**/
        TableRow row0 = (TableRow) (((TableLayout) Table9)).getChildAt(0);
        for(int j=SoMong+2;j<row0.getChildCount();j++)
        {
            TextView column = (TextView) (((TableRow) row0)).getChildAt(j);
            column.setVisibility(View.GONE);
        }

        /**CHO CÁC DÒNG CÒN LẠI HIỂN TRỊ VÀ ẨN CÁC CỘT THỪA**/
        for(int i=1;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            for(int j=(SoMong+1)*2+1;j<row.getChildCount();j++)
            {
                TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                column.setVisibility(View.GONE);
            }

        }
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {

            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
            tv.setText(listTangDayCo.get(i-2).replace("Tầng dây ","T"));
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
        /*
        int vtgcx=0;
        if(SoGaChongXoay>0)
            for(int i=SoTangDay+SoGaChongXoay;i<listTangDayCo.size()+2;i=i+2)
        {
            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
            tv.setText(listGCX.get(vtgcx)+"a");
            TableRow row2 = (TableRow) (((TableLayout) Table9)).getChildAt(i+1);
            TextView tv2 = (TextView)(((TableRow) row2)).getChildAt(0);
            tv2.setText(listGCX.get(vtgcx)+"b");
            vtgcx++;
            //CheckDataTable(row,data,i-1,ColumnCount);
        }

         */
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            setDataTable(row,data_Table9,i-1,ColumnCount);
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
        try
        {
            for (int j=0;j<listTangDayCo.size()+2;j++)
            {
                listEdtLucCang.get(j).setText(data_Table9[j+1][1+(vitri-1)*2]);
            }
        }catch (Exception e)
        {
            /****/
            String dataTable9 = GetTableData(Table9,ColumnCount);
            saveDataOnCacher(dataTable9,"TABLE9");
            data_Table9=DataforPath(file);
        }
        for (int j=0;j<listTangDayCo.size();j++)
        {
            listEdtLucCang.get(j).setText(data_Table9[j+1][1+(vitri-1)*2]);
        }
    }
    public void set_DataTable9(int vitri) {

        /**SET TEXT ON ROW**/

        final int ColumnCount = (1+SoMong)*2+1;
        int Rowcount = Table9.getChildCount();

        /**CHO ẨN HẾT CÁC DÒNG*/
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        /**CHO DÒNG ĐẦU HIỂN THỊ VÀ ẨN CÁC CỘT THỪA**/
        TableRow row0 = (TableRow) (((TableLayout) Table9)).getChildAt(0);
        for(int j=SoMong+2;j<row0.getChildCount();j++)
        {
            TextView column = (TextView) (((TableRow) row0)).getChildAt(j);
            column.setVisibility(View.GONE);
        }

        /**CHO CÁC DÒNG CÒN LẠI HIỂN TRỊ VÀ ẨN CÁC CỘT THỪA**/
        for(int i=1;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            for(int j=(SoMong+1)*2+1;j<row.getChildCount();j++)
            {
                TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                column.setVisibility(View.GONE);
            }

        }
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {

            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
            tv.setText(listTangDayCo.get(i-2).replace("Tầng dây ","T"));
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
        /*
        int vtgcx=0;
        if(SoGaChongXoay>0)
            for(int i=SoTangDay+SoGaChongXoay;i<listTangDayCo.size()+2;i=i+2)
            {
                TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
                TextView tv = (TextView)(((TableRow) row)).getChildAt(0);
                tv.setText(listGCX.get(vtgcx)+"a");
                TableRow row2 = (TableRow) (((TableLayout) Table9)).getChildAt(i+1);
                TextView tv2 = (TextView)(((TableRow) row2)).getChildAt(0);
                tv2.setText(listGCX.get(vtgcx)+"b");
                vtgcx++;
                //CheckDataTable(row,data,i-1,ColumnCount);
            }

         */
        for(int i=2;i<listTangDayCo.size()+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
            setDataTable(row,data_Table9,i-1,ColumnCount);
            //CheckDataTable(row,data,i-1,ColumnCount);
        }
        /****/
        String dataTable9 = GetTableData(Table9,ColumnCount);
        saveDataOnCacher(dataTable9,"TABLE9");

    }
    /**BẢNG 10**/
    public void set_ViewTable10(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE10.txt");
        data_Table10=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =5;
        int Rowcount = Table10.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table10,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<=listLienKetCot.size();i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table10,i,ColumnCount);
        }
        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table10.length<3)
        {
            /****/
            String dataTable10= GetTableData(Table10,5);
            saveDataOnCacher(dataTable10,"TABLE10");
            data_Table10=DataforPath(file);
        }
        edtKichThuocKheHo.setText(data_Table10[vitri][1]);
        edtThanhCanhKheHo.setText(data_Table10[vitri][2]);
        //TÁCH THÀNH TỪNG Ý HIỆN TRANG
        listHienTrangKheHo.clear();
        if(!String.valueOf(data_Table10[vitri][3]).equals(""))
            if(String.valueOf(data_Table10[vitri][3]).contains(("- ")))
                {
                String[] dongHienTrang = data_Table10[vitri][3].split(("- "));
                for( String s:dongHienTrang)
                {
                    listHienTrangKheHo.add(s);
                }
                edtHienTrangKheHo.setText(listHienTrangKheHo.get(vitriHienTrangKheHo).replace("\n",""));
            }
            else
                edtHienTrangKheHo.setText(data_Table10[vitri][3]);
        for(int i= 0 ;i<=10;i++)
        {
            listHienTrangKheHo.add("");
        }
        //TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
        listDeXuatKheHo.clear();
        if(!String.valueOf(data_Table10[vitri][4]).equals(""))
            if(String.valueOf(data_Table10[vitri][4]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table10[vitri][4].split(("- "));
                for( String s:dongHienTrang)
                {
                    listDeXuatKheHo.add(s);
                }
                edtDeXuatKheHo.setText(listDeXuatKheHo.get(vitriDeXuatKheHo).replace("\n",""));
            }

            else  edtDeXuatKheHo.setText(data_Table10[vitri][4]);

        for(int i= 0 ;i<=10;i++)
        {
            listDeXuatKheHo.add("");
        }
        //edtDeXuat.setText(data_Table10[vitri][4]);

    }
    public void set_DataTable10(int vitri) {

        /**SET TEXT ON ROW**/
        int ColumnCount =5;
        int Rowcount = Table10.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table10,i,ColumnCount);
        }
        for(int i=1; i<=listLienKetCot.size();i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table10,i,ColumnCount);
        }
        /****/
        String dataTable10= GetTableData(Table10,5);
        saveDataOnCacher(dataTable10,"TABLE10");

    }
    /**BẢNG 11**/
    public void set_ViewTable11() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE11.txt");
        data_Table11=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =6;
        int Rowcount = Table11.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table11)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        for(int i=1;i<SoAnten+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table11)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table11,i,ColumnCount);
            SetButtonXoa(Table11,i,5);
        }
    }
    public void set_DataTable11() {

        /**SET TEXT ON ROW**/
        int ColumnCount =6;
        int Rowcount = Table11.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table11)).getChildAt(i);
            row.setVisibility(View.GONE);
        }
        for(int i=1;i<SoAnten+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table11)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table11,i,ColumnCount);
        }

        /****/
        String dataTable11= GetTableData(Table11,5);
        saveDataOnCacher(dataTable11,"TABLE11");
    }
    /**BẢNG 12_V0**/
    public void set_ViewTable_ChanCot() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE12_ChanCot.txt");
        data_Table12_ChanCot=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;


            TableRow row = (TableRow) (((TableLayout) Table12_ChanCot)).getChildAt(1);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_ChanCot,1,ColumnCount);


        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table12_ChanCot.length<=1)
        {
            /****/
            String dataTable12 = GetTableData(Table12_ChanCot,4);
            saveDataOnCacher(dataTable12,"TABLE12_ChanCot");
            data_Table12_ChanCot=DataforPath(file);
        }
        GT_X.setText(data_Table12_ChanCot[1][1]);
        GT_Y.setText(data_Table12_ChanCot[1][2]);
        GT_Z.setText(data_Table12_ChanCot[1][3]);

        if (!GT_Z.getText().toString().trim().equals("")){
            CaoDoChanCot = Double.valueOf(GT_Z.getText().toString().trim());
        }

    }
    public void set_DataTable_ChanCot() {

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_ChanCot.getChildCount();


            TableRow row = (TableRow) (((TableLayout) Table12_ChanCot)).getChildAt(1);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_ChanCot,1,ColumnCount);

        /****/
        String dataTable12 = GetTableData(Table12_ChanCot,4);
        saveDataOnCacher(dataTable12,"TABLE12_ChanCot");

    }
    /**BẢNG 12_V0**/
    public void set_ViewTable_HuongBac() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE12_HuongBac.txt");
        data_Table12_HuongBac=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;


            TableRow row = (TableRow) (((TableLayout) Table12_HuongBac)).getChildAt(1);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_HuongBac,1,ColumnCount);


        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table12_HuongBac.length<=1)
        {
            /****/
            String dataTable12 = GetTableData(Table12_HuongBac,4);
            saveDataOnCacher(dataTable12,"TABLE12_HuongBac");
            data_Table12_HuongBac=DataforPath(file);
        }
        GT_X.setText(data_Table12_HuongBac[1][1]);
        GT_Y.setText(data_Table12_HuongBac[1][2]);
        GT_Z.setText(data_Table12_HuongBac[1][3]);



    }
    public void set_DataTable_HuongBac() {

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_HuongBac.getChildCount();


            TableRow row = (TableRow) (((TableLayout) Table12_HuongBac)).getChildAt(1);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_HuongBac,1,ColumnCount);

        /****/
        String dataTable12 = GetTableData(Table12_HuongBac,4);
        saveDataOnCacher(dataTable12,"TABLE12_HuongBac");

    }
    /**BẢNG 12_V0**/
    public void set_ViewTable_DinhCot() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE12_DinhCot.txt");
        data_Table12_DinhCot=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;


            TableRow row = (TableRow) (((TableLayout) Table12_DinhCot)).getChildAt(1);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_DinhCot,1,ColumnCount);


        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table12_DinhCot.length<=1)
        {
            /****/
            String dataTable12 = GetTableData(Table12_DinhCot,4);
            saveDataOnCacher(dataTable12,"TABLE12_DinhCot");
            data_Table12_DinhCot=DataforPath(file);
        }
        GT_X.setText(data_Table12_DinhCot[1][1]);
        GT_Y.setText(data_Table12_DinhCot[1][2]);
        GT_Z.setText(data_Table12_DinhCot[1][3]);



    }
    public void set_DataTable_DinhCot() {

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_DinhCot.getChildCount();


            TableRow row = (TableRow) (((TableLayout) Table12_DinhCot)).getChildAt(1);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_DinhCot,1,ColumnCount);

        /****/
        String dataTable12 = GetTableData(Table12_DinhCot,4);
        saveDataOnCacher(dataTable12,"TABLE12_DinhCot");

    }
    /**BẢNG 12_V0**/
    public void set_ViewTable12_V0(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE12_V0.txt");
        data_Table12_V0=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V0.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V0)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table12_V0,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V0)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V0,i,ColumnCount);
            KiemTraKhongCach(Table12_V0,i,4);
            TextView tv = (TextView) (((TableRow) row)).getChildAt(0);
            tv.setText("Vòng 0-" + i);
        }

        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table12_V0.length<2)
        {
            /****/
            String dataTable12 = GetTableData(Table12_V0,4);
            saveDataOnCacher(dataTable12,"TABLE12_V0");
            data_Table12_V0=DataforPath(file);
        }
        GT_X.setText(data_Table12_V0[vitri][1]);
        GT_Y.setText(data_Table12_V0[vitri][2]);
        GT_Z.setText(data_Table12_V0[vitri][3]);

    }
    public void set_DataTable12_V0(int vitri) {

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V0.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V0)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table12_V0,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V0)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V0,i,ColumnCount);
            KiemTraKhongCach(Table12_V0,i,4);
        }
        /****/
        String dataTable12 = GetTableData(Table12_V0,4);
        saveDataOnCacher(dataTable12,"TABLE12_V0");

    }
    /**BẢNG 12_V1**/
    public void set_ViewTable12_V1(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE12_V1.txt");
        data_Table12_V1=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V1.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V1)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table12_V1,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V1)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V1,i,ColumnCount);
            KiemTraKhongCach(Table12_V1,i,4);
            TextView tv = (TextView) (((TableRow) row)).getChildAt(0);
            tv.setText("Vòng 1-" + i);
        }
        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table12_V1.length<2)
        {
            /****/
            String dataTable12 = GetTableData(Table12_V1,4);
            saveDataOnCacher(dataTable12,"TABLE12_V1");
            data_Table12_V1=DataforPath(file);
        }
        GT_X.setText(data_Table12_V1[vitri][1]);
        GT_Y.setText(data_Table12_V1[vitri][2]);
        GT_Z.setText(data_Table12_V1[vitri][3]);

    }
    public void set_DataTable12_V1(int vitri) {

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V1.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V1)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table12_V1,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V1)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V1,i,ColumnCount);
            KiemTraKhongCach(Table12_V1,i,4);
        }
        /****/
        String dataTable12 = GetTableData(Table12_V1,4);
        saveDataOnCacher(dataTable12,"TABLE12_V1");

    }
    /**BẢNG 12_V2**/
    public void set_ViewTable12_V2(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE12_V2.txt");
        data_Table12_V2=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V2.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V2)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table12_V2,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V2)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V2,i,ColumnCount);
            KiemTraKhongCach(Table12_V2,i,4);
            TextView tv = (TextView) (((TableRow) row)).getChildAt(0);
            tv.setText("Vòng 2-" + i);
        }
        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table12_V2.length<2)
        {
            /****/
            String dataTable12 = GetTableData(Table12_V2,4);
            saveDataOnCacher(dataTable12,"TABLE12_V2");
            data_Table12_V2=DataforPath(file);
        }
        GT_X.setText(data_Table12_V2[vitri][1]);
        GT_Y.setText(data_Table12_V2[vitri][2]);
        GT_Z.setText(data_Table12_V2[vitri][3]);

    }
    public void set_DataTable12_V2(int vitri) {

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V2.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V2)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table12_V2,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V2)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V2,i,ColumnCount);
            KiemTraKhongCach(Table12_V2,i,4);
        }
        /****/
        String dataTable12 = GetTableData(Table12_V2,4);
        saveDataOnCacher(dataTable12,"TABLE12_V2");

    }
    /**BẢNG 12_V3**/
    public void set_ViewTable12_V3(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE12_V3.txt");
        data_Table12_V3=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V3.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V3)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table12_V3,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V3)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V3,i,ColumnCount);
            KiemTraKhongCach(Table12_V3,i,4);
            TextView tv = (TextView) (((TableRow) row)).getChildAt(0);
            tv.setText("Vòng 3-" + i);
        }
        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table12_V3.length<2)
        {
            /****/
            String dataTable12 = GetTableData(Table12_V3,4);
            saveDataOnCacher(dataTable12,"TABLE12_V3");
            data_Table12_V3=DataforPath(file);
        }
        GT_X.setText(data_Table12_V3[vitri][1]);
        GT_Y.setText(data_Table12_V3[vitri][2]);
        GT_Z.setText(data_Table12_V3[vitri][3]);

    }
    public void set_DataTable12_V3(int vitri) {
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V3.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V3)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data_Table12_V3,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V3)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V3,i,ColumnCount);
            KiemTraKhongCach(Table12_V3,i,4);
        }
        /****/
        String dataTable12 = GetTableData(Table12_V3,4);
        saveDataOnCacher(dataTable12,"TABLE12_V3");

    }
    /**Bảng Kết quả*/
    public void set_ViewKetQuaTable12() {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);

        File fileV1 = new File(file,"TABLE12_V1.txt");
        File fileV2 = new File(file,"TABLE12_V2.txt");
        File fileV3 = new File(file,"TABLE12_V3.txt");
        File fileV0 = new File(file,"TABLE12_V0.txt");
        File  fileChanCot = new File(file,"TABLE12_ChanCot.txt");

        data_Table12_V1=DataforPath(fileV1);
        data_Table12_V2=DataforPath(fileV2);
        data_Table12_V3=DataforPath(fileV3);
        data_Table12_V0=DataforPath(fileV0);
        data_Table12_ChanCot=DataforPath(fileChanCot);
        try{
            if (!data_Table12_ChanCot[1][3].trim().equals("")){
                CaoDoChanCot = Double.valueOf(data_Table12_ChanCot[1][3].trim());
            }
        } catch (Exception e) {}

        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table12_V3.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            ///
            TableRow rowV1 = (TableRow) (((TableLayout) Table12_V1)).getChildAt(i);
            rowV1.setVisibility(View.GONE);
            setDataTable(rowV1,data_Table12_V1,i,ColumnCount);
            //
            TableRow rowV2 = (TableRow) (((TableLayout) Table12_V2)).getChildAt(i);
            rowV2.setVisibility(View.GONE);
            setDataTable(rowV2,data_Table12_V2,i,ColumnCount);
            //
            TableRow rowV3 = (TableRow) (((TableLayout) Table12_V3)).getChildAt(i);
            rowV3.setVisibility(View.GONE);
            setDataTable(rowV3,data_Table12_V3,i,ColumnCount);
            //
            TableRow rowV0 = (TableRow) (((TableLayout) Table12_V0)).getChildAt(i);
            rowV0.setVisibility(View.GONE);
            setDataTable(rowV0,data_Table12_V0,i,ColumnCount);

        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V3)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V3,i,ColumnCount);

            KiemTraKhongCach(Table12_V3,i,4);

        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V2)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V2,i,ColumnCount);

            KiemTraKhongCach(Table12_V2,i,4);

        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V1)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V1,i,ColumnCount);

            KiemTraKhongCach(Table12_V1,i,4);

        }
        for(int i=1;i<SoCot+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table12_V0)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table12_V0,i,ColumnCount);

            KiemTraKhongCach(Table12_V0,i,4);

        }
    }
    public void TinhDoNghiengCot(Dialog dialog){
        try {
        DecimalFormat format = new DecimalFormat("0.000");
        DecimalFormat format0000 = new DecimalFormat("0.0000");
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        file = new File(file,"Data"+ MaTram);
        File fileV1 = new File(file,"TABLE12_V1.txt");
        File fileV2 = new File(file,"TABLE12_V2.txt");
        File fileV3 = new File(file,"TABLE12_V3.txt");
        ArrayList<TextView> Vong1 = new ArrayList<TextView>();
        ArrayList<TextView> Vong2 = new ArrayList<TextView>();
        ArrayList<TextView> Vong3 = new ArrayList<TextView>();
        Vong1.add(X1 = (TextView) dialog.findViewById(R.id.X1));
        Vong1.add(Y1 = (TextView) dialog.findViewById(R.id.Y1));
        Vong1.add(H1 = (TextView) dialog.findViewById(R.id.H1));
        Vong1.add(Dx1 = (TextView) dialog.findViewById(R.id.Dx1));
        Vong1.add(Dy1 = (TextView) dialog.findViewById(R.id.Dy1));
        Vong1.add(e1 = (TextView) dialog.findViewById(R.id.e1));
        Vong1.add(Dl1 = (TextView) dialog.findViewById(R.id.DL1));
        Vong1.add(KL1 = (TextView) dialog.findViewById(R.id.KL1));
        /***Vòng 2**/
        Vong2.add(X2 = (TextView) dialog.findViewById(R.id.X2));
        Vong2.add(Y2 = (TextView) dialog.findViewById(R.id.Y2));
        Vong2.add(H2 = (TextView) dialog.findViewById(R.id.H2));
        Vong2.add(Dx2 = (TextView) dialog.findViewById(R.id.Dx2));
        Vong2.add(Dy2 = (TextView) dialog.findViewById(R.id.Dy2));
        Vong2.add(e2 = (TextView) dialog.findViewById(R.id.e2));
        Vong2.add(Dl2 = (TextView) dialog.findViewById(R.id.DL2));
        Vong2.add(Kl2 = (TextView) dialog.findViewById(R.id.KL2));
        /***Vòng 3**/
        Vong3.add(X3 = (TextView) dialog.findViewById(R.id.X3));
        Vong3.add(Y3 = (TextView) dialog.findViewById(R.id.Y3));
        Vong3.add(H3 = (TextView) dialog.findViewById(R.id.H3));
        Vong3.add(Dx3 = (TextView) dialog.findViewById(R.id.Dx3));
        Vong3.add(Dy3 = (TextView) dialog.findViewById(R.id.Dy3));
        Vong3.add(e3 = (TextView) dialog.findViewById(R.id.e3));
        Vong3.add(Dl3 = (TextView) dialog.findViewById(R.id.DL3));
        Vong3.add(KL3 = (TextView) dialog.findViewById(R.id.KL3));
        int ColumnCount =4;
        String[][] dataKQ = new String[3][8];
        if (fileV1.exists())
        {
            String[][] dataV1=DataforPath(fileV1);
            //Log.d("Data:",dataV1[1][1].toString())  ;
            for (int j=1;j<ColumnCount;j++)
            {
                double Tong = 0;
                for(int i=1;i<=SoCot;i++)
                {
                    Tong = (double) (Tong + Double.parseDouble(dataV1[i][j].toString()));
                }
                if (j!=3){
                    Vong1.get(j-1).setText(String.valueOf(format.format(Tong/SoCot)).replace(",","."));
                    dataKQ[0][j-1] = format.format(Tong/SoCot).replace(",",".");
                }
                else{
                    Vong1.get(j-1).setText(String.valueOf(format.format((Tong/SoCot) - CaoDoChanCot)).replace(",","."));
                    dataKQ[0][j-1] = format.format((Tong/SoCot) - CaoDoChanCot).replace(",",".");
                }

                //Log.d("Data:", String.valueOf(dataKQ[0][j-1]));
            }
        }
        if (fileV2.exists())
        {String[][] dataV2=DataforPath(fileV2);
            for (int j=1;j<ColumnCount;j++)
            {
                double Tong = 0;
                for(int i=1;i<=SoCot;i++)
                {
                    Tong = (double) (Tong + Double.parseDouble(dataV2[i][j].toString()));
                }
                //Vong2.get(j-1).setText(String.valueOf(format.format(Tong/SoCot)).replace(",","."));
                //dataKQ[1][j-1] = String.valueOf(format.format(Tong/SoCot)).replace(",",".");
                if (j!=3){
                    Vong2.get(j-1).setText(String.valueOf(format.format(Tong/SoCot)).replace(",","."));
                    dataKQ[1][j-1] = format.format(Tong/SoCot).replace(",",".");
                }
                else{
                    Vong2.get(j-1).setText(String.valueOf(format.format((Tong/SoCot) - CaoDoChanCot)).replace(",","."));
                    dataKQ[1][j-1] = format.format((Tong/SoCot) - CaoDoChanCot).replace(",",".");
                }
            }

        }
        if (fileV3.exists())
        {String[][] dataV3=DataforPath(fileV3);
            for (int j=1;j<ColumnCount;j++)
            {
                double Tong = 0;
                for(int i=1;i<=SoCot;i++)
                {
                    Tong = (double) (Tong + Double.parseDouble(dataV3[i][j].toString()));
                }
                //Vong3.get(j-1).setText(String.valueOf(format.format(Tong/SoCot)).replace(",","."));
                //dataKQ[2][j-1] = String.valueOf(format.format(Tong/SoCot)).replace(",",".");
                if (j!=3){
                    Vong3.get(j-1).setText(String.valueOf(format.format(Tong/SoCot)).replace(",","."));
                    dataKQ[2][j-1] = format.format(Tong/SoCot).replace(",",".");
                }
                else{
                    Vong3.get(j-1).setText(String.valueOf(format.format((Tong/SoCot) - CaoDoChanCot)).replace(",","."));
                    dataKQ[2][j-1] = format.format((Tong/SoCot) - CaoDoChanCot).replace(",",".");
                }
            }
        }
        /**Tính Dx, DY*/
        for(int i=0;i<2;i++)
        {
            Double Dx = Double.parseDouble(dataKQ[1][i]) - Double.parseDouble(dataKQ[0][i]);
            dataKQ[1][i + 4] = String.valueOf(format0000.format(Dx)).replace(",",".");
            Vong2.get(i + 3).setText(dataKQ[1][i + 4]);

            //Log.d("Data:",dataKQ[1][i]+ "- " +dataKQ[0][i])  ;
        }
        for(int i=0;i<2;i++)
        {
            Double Dy = Double.parseDouble(dataKQ[2][i]) - Double.parseDouble(dataKQ[0][i]);
            dataKQ[2][i + 4] = String.valueOf(format0000.format(Dy)).replace(",",".");
            Vong3.get(i + 3).setText(dataKQ[2][i + 4]);
        }
        /***Tính độ lệch**/
        Double e2 =Math.sqrt( Math.pow(Double.parseDouble(dataKQ[1][4]),2)+ Math.pow(Double.parseDouble(dataKQ[1][5]),2));
        dataKQ[1][6] = String.valueOf(format0000.format(e2)).replace(",",".");
        Vong2.get(5).setText(String.valueOf(format0000.format(e2)).replace(",","."));
        Double e3 =Math.sqrt( Math.pow(Double.parseDouble(dataKQ[2][4]),2)+ Math.pow(Double.parseDouble(dataKQ[2][5]),2));
        dataKQ[2][6] = String.valueOf(format0000.format(e3)).replace(",",".");
        Vong3.get(5).setText(String.valueOf(format0000.format(e3)).replace(",","."));
        /**Tính Độ lệch cho phép**/
        try {
            Double dl1 = Double.parseDouble(Vong1.get(2).getText().toString().replace(",", ".")) / 400;
            Vong1.get(6).setText(String.valueOf(format0000.format(dl1)).replace(",", "."));
            dataKQ[0][7] = String.valueOf(format0000.format(dl1)).replace(",", ".");
            Double dl2 = Double.parseDouble(Vong2.get(2).getText().toString().replace(",", ".")) / 400;
            Vong2.get(6).setText(String.valueOf(format0000.format(dl2)).replace(",", "."));
            dataKQ[1][7] = String.valueOf(format0000.format(dl2)).replace(",", ".");
            Double dl3 = Double.parseDouble(Vong3.get(2).getText().toString().replace(",", ".")) / 400;
            Vong3.get(6).setText(String.valueOf(format0000.format(dl3)).replace(",", "."));
            dataKQ[2][7] = String.valueOf(format0000.format(dl3)).replace(",", ".");
            /**Kiểm tra**/
            if (Double.parseDouble(dataKQ[1][7])>=Double.parseDouble(dataKQ[1][6]))
            {
                Vong2.get(7).setText("Đạt");
                Vong2.get(7).setBackground(getResources().getDrawable(R.drawable.boder_green));

            }
            else {Vong2.get(7).setText("Không đạt");Vong2.get(7).setBackground(getResources().getDrawable(R.drawable.boder_red));}
            if (Double.parseDouble(dataKQ[2][7])>=Double.parseDouble(dataKQ[2][6]))
            {
                Vong3.get(7).setText("Đạt");Vong3.get(7).setBackground(getResources().getDrawable(R.drawable.boder_green));
            }
            else {Vong3.get(7).setText("Không đạt");Vong3.get(7).setBackground(getResources().getDrawable(R.drawable.boder_red));}
        }
        catch (Exception e){Toast.makeText(getApplicationContext(), "Nhập đủ độ cao đo (H) của 4 vòng!" , Toast.LENGTH_SHORT).show();}
        } catch (Exception e) {Toast.makeText(getApplicationContext(), "Nhập đủ tọa độ của 4 vòng!Kiểm tra lại tọa độ Vòng 0 - vòng 3" , Toast.LENGTH_SHORT).show();}
    }
    public void TinhDoNghiengCotMonopole(Dialog dialog){
        DecimalFormat format = new DecimalFormat("0.000");
        DecimalFormat format0000 = new DecimalFormat("0.0000");
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        file = new File(file,"Data"+ MaTram);
        File fileV1 = new File(file,"TABLE12_V1.txt");
        File fileV2 = new File(file,"TABLE12_V2.txt");
        File fileV3 = new File(file,"TABLE12_V3.txt");
        ArrayList<TextView> Vong1 = new ArrayList<TextView>();
        ArrayList<TextView> Vong2 = new ArrayList<TextView>();
        ArrayList<TextView> Vong3 = new ArrayList<TextView>();
        Vong1.add(X1 = (TextView) dialog.findViewById(R.id.X1));
        Vong1.add(Y1 = (TextView) dialog.findViewById(R.id.Y1));
        Vong1.add(H1 = (TextView) dialog.findViewById(R.id.H1));
        Vong1.add(Dx1 = (TextView) dialog.findViewById(R.id.Dx1));
        Vong1.add(Dy1 = (TextView) dialog.findViewById(R.id.Dy1));
        Vong1.add(e1 = (TextView) dialog.findViewById(R.id.e1));
        Vong1.add(Dl1 = (TextView) dialog.findViewById(R.id.DL1));
        Vong1.add(KL1 = (TextView) dialog.findViewById(R.id.KL1));
        /***Vòng 2**/
        Vong2.add(X2 = (TextView) dialog.findViewById(R.id.X2));
        Vong2.add(Y2 = (TextView) dialog.findViewById(R.id.Y2));
        Vong2.add(H2 = (TextView) dialog.findViewById(R.id.H2));
        Vong2.add(Dx2 = (TextView) dialog.findViewById(R.id.Dx2));
        Vong2.add(Dy2 = (TextView) dialog.findViewById(R.id.Dy2));
        Vong2.add(e2 = (TextView) dialog.findViewById(R.id.e2));
        Vong2.add(Dl2 = (TextView) dialog.findViewById(R.id.DL2));
        Vong2.add(Kl2 = (TextView) dialog.findViewById(R.id.KL2));
        /***Vòng 3**/
        Vong3.add(X3 = (TextView) dialog.findViewById(R.id.X3));
        Vong3.add(Y3 = (TextView) dialog.findViewById(R.id.Y3));
        Vong3.add(H3 = (TextView) dialog.findViewById(R.id.H3));
        Vong3.add(Dx3 = (TextView) dialog.findViewById(R.id.Dx3));
        Vong3.add(Dy3 = (TextView) dialog.findViewById(R.id.Dy3));
        Vong3.add(e3 = (TextView) dialog.findViewById(R.id.e3));
        Vong3.add(Dl3 = (TextView) dialog.findViewById(R.id.DL3));
        Vong3.add(KL3 = (TextView) dialog.findViewById(R.id.KL3));
        double A1,A2,B1,B2,C21,C22,C31,C32,C1,C2,D1,X1,Y1;

        int ColumnCount =4;
        String[][] dataKQ = new String[3][8];
        if (fileV1.exists())
        {
            String[][] dataV1=DataforPath(fileV1);
            /**TÍNH A,B,C VÒNG 1*/
            A1 = 2* (Double.parseDouble(dataV1[1][1])-Double.parseDouble(dataV1[2][1]));
            A2 = 2* (Double.parseDouble(dataV1[2][1])-Double.parseDouble(dataV1[3][1]));
            B1 = 2* (Double.parseDouble(dataV1[1][2])-Double.parseDouble(dataV1[2][2]));
            B2 = 2* (Double.parseDouble(dataV1[2][2])-Double.parseDouble(dataV1[3][2]));
            C21 = (Double.parseDouble(dataV1[1][1])*Double.parseDouble(dataV1[1][1])) + (Double.parseDouble(dataV1[1][2])*Double.parseDouble(dataV1[1][2]));
            C22 = (Double.parseDouble(dataV1[2][1])*Double.parseDouble(dataV1[2][1])) + (Double.parseDouble(dataV1[2][2])*Double.parseDouble(dataV1[2][2]));
            C1 = C21-C22;
            C31 = (Double.parseDouble(dataV1[2][1])*Double.parseDouble(dataV1[2][1])) + (Double.parseDouble(dataV1[2][2])*Double.parseDouble(dataV1[2][2]));
            C32 = (Double.parseDouble(dataV1[3][1])*Double.parseDouble(dataV1[3][1])) + (Double.parseDouble(dataV1[3][2])*Double.parseDouble(dataV1[3][2]));
            C2 = C31-C32;
            D1 = A1*B2 - A2*B1;
            X1 = (C1 * B2 - C2*B1)/D1;
            Y1 = (A1*C2-A2*C1)/D1;
            Vong1.get(0).setText(String.valueOf(format.format(X1)).replace(",","."));
            dataKQ[0][0] = format.format(X1).replace(",",".");
            Vong1.get(1).setText(String.valueOf(format.format(Y1)).replace(",","."));
            dataKQ[0][1] = format.format(Y1).replace(",",".");
            /***/

                double Tong = 0;
                for(int i=1;i<=3;i++)
                {
                    Tong = (double) (Tong + Double.parseDouble(dataV1[i][3].toString()));
                }

                Vong1.get(2).setText(String.valueOf(format.format((Tong/3) - CaoDoChanCot)).replace(",","."));
                dataKQ[0][2] = format.format((Tong/3) - CaoDoChanCot).replace(",",".");


        }
        if (fileV2.exists())
        {
            String[][] dataV2=DataforPath(fileV2);
            /**TÍNH A,B,C VÒNG 2*/
            A1 = 2* (Double.parseDouble(dataV2[1][1])-Double.parseDouble(dataV2[2][1]));
            A2 = 2* (Double.parseDouble(dataV2[2][1])-Double.parseDouble(dataV2[3][1]));
            B1 = 2* (Double.parseDouble(dataV2[1][2])-Double.parseDouble(dataV2[2][2]));
            B2 = 2* (Double.parseDouble(dataV2[2][2])-Double.parseDouble(dataV2[3][2]));
            C21 = (Double.parseDouble(dataV2[1][1])*Double.parseDouble(dataV2[1][1])) + (Double.parseDouble(dataV2[1][2])*Double.parseDouble(dataV2[1][2]));
            C22 = (Double.parseDouble(dataV2[2][1])*Double.parseDouble(dataV2[2][1])) + (Double.parseDouble(dataV2[2][2])*Double.parseDouble(dataV2[2][2]));
            C1 = C21-C22;
            C31 = (Double.parseDouble(dataV2[2][1])*Double.parseDouble(dataV2[2][1])) + (Double.parseDouble(dataV2[2][2])*Double.parseDouble(dataV2[2][2]));
            C32 = (Double.parseDouble(dataV2[3][1])*Double.parseDouble(dataV2[3][1])) + (Double.parseDouble(dataV2[3][2])*Double.parseDouble(dataV2[3][2]));
            C2 = C31-C32;
            D1 = A1*B2 - A2*B1;
            X1 = (C1 * B2 - C2*B1)/D1;
            Y1 = (A1*C2-A2*C1)/D1;
            Vong2.get(0).setText(String.valueOf(format.format(X1)).replace(",","."));
            dataKQ[1][0] = format.format(X1).replace(",",".");
            Vong2.get(1).setText(String.valueOf(format.format(Y1)).replace(",","."));
            dataKQ[1][1] = format.format(Y1).replace(",",".");
            /***/
            double Tong = 0;
            for(int i=1;i<=3;i++)
            {
                Tong = (double) (Tong + Double.parseDouble(dataV2[i][3].toString()));
            }

            Vong2.get(2).setText(String.valueOf(format.format((Tong/3) - CaoDoChanCot)).replace(",","."));
            dataKQ[1][2] = format.format((Tong/3) - CaoDoChanCot).replace(",",".");
        }
        if (fileV3.exists())
        {
            String[][] dataV3=DataforPath(fileV3);
            /**TÍNH A,B,C VÒNG 2*/
            A1 = 2* (Double.parseDouble(dataV3[1][1])-Double.parseDouble(dataV3[2][1]));
            A2 = 2* (Double.parseDouble(dataV3[2][1])-Double.parseDouble(dataV3[3][1]));
            B1 = 2* (Double.parseDouble(dataV3[1][2])-Double.parseDouble(dataV3[2][2]));
            B2 = 2* (Double.parseDouble(dataV3[2][2])-Double.parseDouble(dataV3[3][2]));
            C21 = (Double.parseDouble(dataV3[1][1])*Double.parseDouble(dataV3[1][1])) + (Double.parseDouble(dataV3[1][2])*Double.parseDouble(dataV3[1][2]));
            C22 = (Double.parseDouble(dataV3[2][1])*Double.parseDouble(dataV3[2][1])) + (Double.parseDouble(dataV3[2][2])*Double.parseDouble(dataV3[2][2]));
            C1 = C21-C22;
            C31 = (Double.parseDouble(dataV3[2][1])*Double.parseDouble(dataV3[2][1])) + (Double.parseDouble(dataV3[2][2])*Double.parseDouble(dataV3[2][2]));
            C32 = (Double.parseDouble(dataV3[3][1])*Double.parseDouble(dataV3[3][1])) + (Double.parseDouble(dataV3[3][2])*Double.parseDouble(dataV3[3][2]));
            C2 = C31-C32;
            D1 = A1*B2 - A2*B1;
            X1 = (C1 * B2 - C2*B1)/D1;
            Y1 = (A1*C2-A2*C1)/D1;
            Vong3.get(0).setText(String.valueOf(format.format(X1)).replace(",","."));
            dataKQ[2][0] = format.format(X1).replace(",",".");
            Vong3.get(1).setText(String.valueOf(format.format(Y1)).replace(",","."));
            dataKQ[2][1] = format.format(Y1).replace(",",".");
            /***/
            double Tong = 0;
            for(int i=1;i<=3;i++)
            {
                Tong = (double) (Tong + Double.parseDouble(dataV3[i][3].toString()));
            }

            Vong3.get(2).setText(String.valueOf(format.format((Tong/3) - CaoDoChanCot)).replace(",","."));
            dataKQ[2][2] = format.format((Tong/3) - CaoDoChanCot).replace(",",".");
        }

        /**Tính Dx, DY*/
        for(int i=0;i<2;i++)
        {
            Double Dx = Double.parseDouble(dataKQ[1][i]) - Double.parseDouble(dataKQ[0][i]);
            dataKQ[1][i + 4] = String.valueOf(format0000.format(Dx)).replace(",",".");
            Vong2.get(i + 3).setText(dataKQ[1][i + 4]);

            //Log.d("Data:",dataKQ[1][i]+ "- " +dataKQ[0][i])  ;
        }
        for(int i=0;i<2;i++)
        {
            Double Dy = Double.parseDouble(dataKQ[2][i]) - Double.parseDouble(dataKQ[0][i]);
            dataKQ[2][i + 4] = String.valueOf(format0000.format(Dy)).replace(",",".");
            Vong3.get(i + 3).setText(dataKQ[2][i + 4]);
        }
        /***Tính độ lệch**/

        Double e2 =Math.sqrt( Math.pow(Double.parseDouble(dataKQ[1][4]),2)+ Math.pow(Double.parseDouble(dataKQ[1][5]),2));
        dataKQ[1][6] = String.valueOf(format0000.format(e2)).replace(",",".");
        Vong2.get(5).setText(String.valueOf(format0000.format(e2)).replace(",","."));
        Double e3 =Math.sqrt( Math.pow(Double.parseDouble(dataKQ[2][4]),2)+ Math.pow(Double.parseDouble(dataKQ[2][5]),2));
        dataKQ[2][6] = String.valueOf(format0000.format(e3)).replace(",",".");
        Vong3.get(5).setText(String.valueOf(format0000.format(e3)).replace(",","."));
        /**Tính Độ lệch cho phép**/
        try {
            Double dl1 = Double.parseDouble(Vong1.get(2).getText().toString().replace(",", ".")) / 400;
            Vong1.get(6).setText(String.valueOf(format0000.format(dl1)).replace(",", "."));
            dataKQ[0][7] = String.valueOf(format0000.format(dl1)).replace(",", ".");
            Double dl2 = Double.parseDouble(Vong2.get(2).getText().toString().replace(",", ".")) / 400;
            Vong2.get(6).setText(String.valueOf(format0000.format(dl2)).replace(",", "."));
            dataKQ[1][7] = String.valueOf(format0000.format(dl2)).replace(",", ".");
            Double dl3 = Double.parseDouble(Vong3.get(2).getText().toString().replace(",", ".")) / 400;
            Vong3.get(6).setText(String.valueOf(format0000.format(dl3)).replace(",", "."));
            dataKQ[2][7] = String.valueOf(format0000.format(dl3)).replace(",", ".");
            /**Kiểm tra**/
            if (Double.parseDouble(dataKQ[1][7])>=Double.parseDouble(dataKQ[1][6]))
            {
                Vong2.get(7).setText("Đạt");
                Vong2.get(7).setBackground(getResources().getDrawable(R.drawable.boder_green));

            }
            else {Vong2.get(7).setText("Không đạt");Vong2.get(7).setBackground(getResources().getDrawable(R.drawable.boder_red));}
            if (Double.parseDouble(dataKQ[2][7])>=Double.parseDouble(dataKQ[2][6]))
            {
                Vong3.get(7).setText("Đạt");Vong3.get(7).setBackground(getResources().getDrawable(R.drawable.boder_green));
            }
            else {Vong3.get(7).setText("Không đạt");Vong3.get(7).setBackground(getResources().getDrawable(R.drawable.boder_red));}
        }
        catch (Exception e){Toast.makeText(getApplicationContext(), "Nhập đủ độ cao đo (H) của 3 vòng!" , Toast.LENGTH_SHORT).show();}

    }
    /**HIỆN TRẠNG*/
    public void getProductList(TextView editText,String LoaiCauKien){
        try
        {
            String[] strings = editText.getText().toString().split("; ");
            myList.clear();
            LoaiCauKien = DataforPath(LoaiCauKien);
            String[] phuluccon = LoaiCauKien.split("@");
            for (int i = 1; i < phuluccon.length; i ++)
            {
              myList.add(phuluccon[i].trim().replace("\n",""));
            }
            productList = new ArrayList<>();
            Collections.sort(myList);
            for (String HM : myList)
            {
                productList.add(new HienTrang(HM,Kiemtra(HM,strings)));
            }
            listview_ht_adapter = new Listview_HT_Adapter(this,R.layout.list_item_checkbox,productList );
            listView.setAdapter(listview_ht_adapter);
        }
        catch (ArithmeticException e)
        {

        }
    }
    public String DataforPath(String name){
        String s=null;
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "Template");
            file = new File(file, name+".txt");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            s = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    public Boolean Kiemtra(String s,String[] list){
        Boolean b= false;
        for (String string :list)
        {
            if(string.replace("\n","").equalsIgnoreCase(s)) {b=true;break;}
        }
        return b;
    }
    public StringBuilder printSelectedItems()  {
        StringBuilder sb= new StringBuilder();
        ArrayList<HienTrang> countryList = listview_ht_adapter.arraylist;
        for (int i=0 ; i<countryList.size();i++)
        {
            HienTrang country = countryList.get(i);
            if (country.isActive())
            {
                String s= country.getUserName();
                if (sb.length()==0) sb = sb.append(""+s.trim().replace("\n",""));
                else sb = sb.append("; "+s.trim().replace("\n",""));
            }
        }
        //Toast.makeText(this, sb, Toast.LENGTH_LONG).show();
        return sb;
    }
    public void DialogHientrangCheckBox(final EditText tv, final String LoaiCauKien,final String key) {
        try
        {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            textHT = "";
            for (String s:listHienTrang)
            {
                if (!textHT.trim().equals("") && !s.trim().equals(""))
                    textHT = textHT.trim() + "; " + s.trim().replace("\n","") ;
                else textHT = textHT.trim() + s.trim().replace("\n","") ;
            }
            listView = (ListView) dialog.findViewById(R.id.listHienTrang);
            button = (Button) dialog.findViewById(R.id.button);
            editText = (EditText) dialog.findViewById(R.id.edittext);
            editText.setText(textHT);
            getProductList(editText,LoaiCauKien);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        TuHocHienTrang(editText,LoaiCauKien,listHienTrang);
                        //listHienTrang.add(editText.getText().toString().replace("\n",""));
                        vitriHienTrang =1;
                        tv.setText(listHienTrang.get(vitriHienTrang).replace("\n",""));
                        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
                        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
                        for(int i= 0 ;i<=10;i++)
                        {listHienTrang.add("");}
                        dialog.dismiss();
                    }
            });
            listview_ht_adapter.filter(key);
            //this.initListViewData();
            SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
            searchViewCT.setIconifiedByDefault(false);
            searchViewCT.setQueryHint("Tìm kiếm");
            /* Get SearchView autocomplete object.
            final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
            ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, myList);
            searchAutoCompleteCT.setAdapter(newsAdapterCT);

            searchAutoCompleteCT.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                    String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                    searchAutoCompleteCT.setText(queryString);
                    //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
            });*/
            searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    listview_ht_adapter.filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    listview_ht_adapter.filter(newText);
                    return false;
                }
            });
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Hiện trạng của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void DialogHientrangCheckBoxBulong(final EditText tv, final String LoaiCauKien,final String key) {
        try
        {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHTBulong = "";
        for (String s:listHienTrangBulong)
        {
            if (!textHTBulong.trim().equals("") && !s.trim().equals(""))
                textHTBulong = textHTBulong + "; " + s.replace("\n","") ;
            else textHTBulong = textHTBulong + s.replace("\n","") ;
        }
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(textHTBulong);
        getProductList(editText,LoaiCauKien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TuHocHienTrang(editText,LoaiCauKien,listHienTrangBulong);
                    //listHienTrang.add(editText.getText().toString().replace("\n",""));
                    vitriHienTrangBulong =1;
                    tv.setText(listHienTrangBulong.get(vitriHienTrangBulong).replace("\n",""));
                    tvViTriHienTrangBulong.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangBulong) + "/" + String.valueOf(listHienTrangBulong.size()));
                    tvViTriDeXuatBulong.setText("Đề xuất: " + String.valueOf(vitriHienTrangBulong) + "/" + String.valueOf(listHienTrangBulong.size()));

                    for(int i= 0 ;i<=10;i++)
                    {listHienTrangBulong.add("");}
                    dialog.dismiss();
            }
        });
            listview_ht_adapter.filter(key);
            if (listview_ht_adapter.getCount() == 0 ) listview_ht_adapter.filter("");

            //this.initListViewData();
        SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
        searchViewCT.setIconifiedByDefault(false);
        searchViewCT.setQueryHint("Tìm kiếm");
        /* Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, myList);
        searchAutoCompleteCT.setAdapter(newsAdapterCT);

        searchAutoCompleteCT.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoCompleteCT.setText(queryString);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
        }
        });*/
        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Hiện trạng của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void DialogHientrangCheckBoxCauKien(final EditText tv, final String LoaiCauKien,final String key) {
        try
        {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHTCauKien = "";
        for (String s:listHienTrangCauKien)
        {
            if (!textHTCauKien.trim().equals("") && !s.trim().equals(""))
                textHTCauKien = textHTCauKien + "; " + s.replace("\n","") ;
            else textHTCauKien = textHTCauKien + s.replace("\n","") ;
        }
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(textHTCauKien);
        getProductList(editText,LoaiCauKien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TuHocHienTrang(editText,LoaiCauKien,listHienTrangCauKien);
                    //listHienTrang.add(editText.getText().toString().replace("\n",""));
                    vitriHienTrangCauKien =1;
                    tv.setText(listHienTrangCauKien.get(vitriHienTrangCauKien).replace("\n",""));
                    tvViTriHienTrangCauKien.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangCauKien) + "/" + String.valueOf(listHienTrangCauKien.size()));
                    tvViTriDeXuatCauKien.setText("Đề xuất: " + String.valueOf(vitriHienTrangCauKien) + "/" + String.valueOf(listHienTrangCauKien.size()));

                    for(int i= 0 ;i<=10;i++)
                    {listHienTrangCauKien.add("");}
                    dialog.dismiss();
            }
        });
            listview_ht_adapter.filter(key);
            if (listview_ht_adapter.getCount() == 0 ) listview_ht_adapter.filter("");

            //this.initListViewData();
        SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
        searchViewCT.setIconifiedByDefault(false);
        searchViewCT.setQueryHint("Tìm kiếm");
        /* Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, myList);
        searchAutoCompleteCT.setAdapter(newsAdapterCT);

        searchAutoCompleteCT.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoCompleteCT.setText(queryString);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
        }
        });*/
        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Hiện trạng của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void DialogHientrangCheckBoxMocCo(final EditText tv, final String LoaiCauKien,final String key) {
        try
        {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHTMocCo = "";
        for (String s:listHienTrangMocCo)
        {
            if (!textHTMocCo.trim().equals("") && !s.trim().equals(""))
                textHTMocCo = textHTMocCo + "; " + s.replace("\n","") ;
            else textHTMocCo = textHTMocCo + s.replace("\n","") ;
        }
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(textHTMocCo);
        getProductList(editText,LoaiCauKien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuHocHienTrang(editText,LoaiCauKien,listHienTrangMocCo);
                vitriHienTrangMocCo =1;
                tv.setText(listHienTrangMocCo.get(vitriHienTrangMocCo).replace("\n",""));
                tvViTriHienTrangMocCo.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangMocCo) + "/" + String.valueOf(listHienTrangMocCo.size()));
                    for(int i= 0 ;i<=10;i++)
                    {listHienTrangMocCo.add("");}
                    dialog.dismiss();
            }
        });
            listview_ht_adapter.filter(key);
            if (listview_ht_adapter.getCount() == 0 ) listview_ht_adapter.filter("");

            SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
            searchViewCT.setIconifiedByDefault(false);
            searchViewCT.setQueryHint("Tìm kiếm");
        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Hiện trạng của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void DialogHientrangCheckBoxKheHo(final EditText tv,final String LoaiCauKien,final String key) {
        try
        {
            final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHTKheHo = "";
        for (String s:listHienTrangKheHo)
        {
            if (!textHTKheHo.trim().equals("") && !s.trim().equals(""))
                textHTKheHo = textHTKheHo + "; " + s.replace("\n","") ;
            else textHTKheHo = textHTKheHo + s.replace("\n","") ;
        }
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(textHTKheHo);
        getProductList(editText,LoaiCauKien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //tv.setText(editText.getText());
                TuHocHienTrang(editText,LoaiCauKien,listHienTrangKheHo);

                vitriHienTrangKheHo =1;
                tv.setText(listHienTrangKheHo.get(vitriHienTrangKheHo).replace("\n",""));
                tvViTriHienTrangKheHo.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangKheHo) + "/" + String.valueOf(listHienTrangKheHo.size()));
                    for(int i= 0 ;i<=10;i++)
                    {listHienTrangKheHo.add("");}
                    dialog.dismiss();
            }
        });
            listview_ht_adapter.filter(key);
            if (listview_ht_adapter.getCount() == 0 ) listview_ht_adapter.filter("");

            SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
            searchViewCT.setIconifiedByDefault(false);
            searchViewCT.setQueryHint("Tìm kiếm");
        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
    }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Hiện trạng của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void DialogHientrangCheckBoxPhuKien(final EditText tv,final String LoaiCauKien,final String key) {
        try
        {
                final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHTPhuKien = "";
        for (String s:listHienTrangPhuKien)
        {
            if (!textHTPhuKien.trim().equals("") && !s.trim().equals(""))
                textHTPhuKien = textHTPhuKien + "; " + s.replace("\n","") ;
            else textHTPhuKien = textHTPhuKien + s.replace("\n","") ;
        }
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(textHTPhuKien);
        getProductList(editText,LoaiCauKien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //tv.setText(editText.getText());
                TuHocHienTrang(editText,LoaiCauKien,listHienTrangPhuKien);
                vitriHienTrangPhuKien =1;
                tv.setText(listHienTrangPhuKien.get(vitriHienTrangPhuKien).replace("\n",""));
                tvViTriHienTrangPhuKien.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrangPhuKien) + "/" + String.valueOf(listHienTrangPhuKien.size()));
                    for(int i= 0 ;i<=10;i++)
                    {listHienTrangPhuKien.add("");}
                    dialog.dismiss();
            }
        });
            listview_ht_adapter.filter(key);
            if (listview_ht_adapter.getCount() == 0 ) listview_ht_adapter.filter("");
            SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
            searchViewCT.setIconifiedByDefault(false);
            searchViewCT.setQueryHint("Tìm kiếm");
        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
    }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Hiện trạng của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void TuHocHienTrang(final EditText editText,final String LoaiCauKien,ArrayList<String> listHienTrang){
        try
        {
        listHienTrang.clear();
        listHienTrang.add("");
        if(editText.getText().toString().contains(("; ")))
        {
            String[] dongHienTrang =editText.getText().toString().split(("; "));
            for( String s:dongHienTrang)
            {
                listHienTrang.add(s.trim().replace("\n",""));
                //Tự động học hiện trạng
                if (!LoaiCauKien.trim().replace("\n","").equalsIgnoreCase("HienTrangBeTong"))
                {
                    if (!HienTrangThep.contains(s.trim().replace("\n",""))){
                        HienTrangThep = HienTrangThep.replace("\n","") +"@"+s.replace("\n","");
                        saveDataOnTemplate(HienTrangThep,"HienTrangThep");
                        Toast.makeText(FormMain.this, "Thêm mới hiện trạng thép " + s, Toast.LENGTH_SHORT).show();
                    }
                }
                else if (LoaiCauKien.equals("HienTrangBeTong"))
                {
                    if (!HienTrangBeTong.contains(s.trim().replace("\n",""))){
                        HienTrangBeTong = HienTrangBeTong.replace("\n","") +"@"+s.replace("\n","");
                        saveDataOnTemplate(HienTrangBeTong,"HienTrangBeTong");
                        Toast.makeText(FormMain.this, "Thêm mới hiện trạng bê tông " + s, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        else
        {
            listHienTrang.add(editText.getText().toString().replace("\n",""));
            //Tự động học hiện trạng
            if (!LoaiCauKien.equals("HienTrangBeTong"))
            {
                if (!HienTrangThep.contains(editText.getText().toString().replace("\n",""))){
                    HienTrangThep = HienTrangThep.replace("\n","") +"@"+editText.getText().toString().replace("\n","");
                    saveDataOnTemplate(HienTrangThep,"HienTrangThep");
                    Toast.makeText(FormMain.this, "Thêm mới hiện trạng thép " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
            else if (LoaiCauKien.equals("HienTrangBeTong"))
            {
                if (!HienTrangBeTong.contains(editText.getText().toString().replace("\n",""))){
                    HienTrangBeTong = HienTrangBeTong.replace("\n","") +"@"+editText.getText().toString().replace("\n","");
                    saveDataOnTemplate(HienTrangBeTong,"HienTrangBeTong");
                    Toast.makeText(FormMain.this, "Thêm mới hiện trạng bê tông " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Hiện trạng của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    /*** ĐỀ XUẤT*/
    public void getProductListDX(TextView editText,String LoaiCauKien){
        try
        {
            String[] strings = editText.getText().toString().split("; ");

            myList.clear();
            LoaiCauKien = DataforPath(LoaiCauKien);
            String[] phuluccon = LoaiCauKien.split("@");
            for (int i = 1; i < phuluccon.length; i ++) {
                myList.add(phuluccon[i].trim().replace("\n",""));
            }
            Collections.sort(myList);
            productList = new ArrayList<HienTrang>();

            for (String HM : myList)
            {
                productList.add(new HienTrang(HM,Kiemtra(HM,strings)));
            }

            listview_ht_adapter = new Listview_HT_Adapter(this,R.layout.list_item_checkbox,productList );
            listView.setAdapter(listview_ht_adapter);
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Đề xuất của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void DialogHientrangCheckBoxDX(final EditText tv,final String LoaiCauKien) {
        try
        {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHT = "";
        for (String s:listDeXuat)
        {
            if (!textHT.trim().equals("") && !s.trim().equals(""))
                textHT = textHT + "; " + s.replace("\n","") ;
            else textHT = textHT + s.replace("\n","") ;
        }
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(textHT);
        getProductListDX(editText,LoaiCauKien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuHocDeXuat(editText,LoaiCauKien,listDeXuat);
                vitriDeXuat =1;
                tv.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
                tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriDeXuat) + "/" + String.valueOf(listDeXuat.size()));
                for(int i= 0 ;i<=10;i++)
                {
                    listDeXuat.add("");
                }
                dialog.dismiss();

            }
        });

            SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
            searchViewCT.setIconifiedByDefault(false);
            searchViewCT.setQueryHint("Tìm kiếm");
        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
    }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Đề xuất của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void DialogHientrangCheckBoxDXBulong(final EditText tv,final String LoaiCauKien) {
        try
        {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHTBulong = "";
        for (String s:listDeXuatBulong)
        {
            if (!textHTBulong.trim().equals("") && !s.trim().equals(""))
                textHTBulong = textHTBulong + "; " + s.replace("\n","") ;
            else textHTBulong = textHTBulong + s.replace("\n","") ;
        }
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(textHTBulong);
        getProductListDX(editText,LoaiCauKien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuHocDeXuat(editText,LoaiCauKien,listDeXuatBulong);
                vitriDeXuatBulong =1;
                tv.setText(listDeXuatBulong.get(vitriDeXuat).replace("\n",""));
                tvViTriDeXuatBulong.setText("Đề xuất: " + String.valueOf(vitriDeXuatBulong) + "/" + String.valueOf(listDeXuatBulong.size()));
                for(int i= 0 ;i<=10;i++)
                {
                    listDeXuatBulong.add("");
                }
                dialog.dismiss();

            }
        });

            SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
            searchViewCT.setIconifiedByDefault(false);
            searchViewCT.setQueryHint("Tìm kiếm");
        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
    }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Đề xuất của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void DialogHientrangCheckBoxDXCauKien(final EditText tv,final String LoaiCauKien) {
        try
        {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHTCauKien = "";
        for (String s:listDeXuatCauKien)
        {
            if (!textHTCauKien.trim().equals("") && !s.trim().equals(""))
                textHTCauKien = textHTCauKien + "; " + s.replace("\n","") ;
            else textHTCauKien = textHTCauKien + s.replace("\n","") ;
        }
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(textHTCauKien);
        getProductListDX(editText,LoaiCauKien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuHocDeXuat(editText,LoaiCauKien,listDeXuatCauKien);
                vitriDeXuatCauKien =1;
                tv.setText(listDeXuatCauKien.get(vitriDeXuatCauKien).replace("\n",""));
                tvViTriDeXuatCauKien.setText("Đề xuất: " + String.valueOf(vitriDeXuatCauKien) + "/" + String.valueOf(listDeXuatCauKien.size()));
                for(int i= 0 ;i<=10;i++)
                {
                    listDeXuatCauKien.add("");
                }
                dialog.dismiss();

            }
        });

            SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
            searchViewCT.setIconifiedByDefault(false);
            searchViewCT.setQueryHint("Tìm kiếm");
        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
    }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Đề xuất của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void DialogHientrangCheckBoxDXMocCo(final EditText tv,final String LoaiCauKien) {
        try
        {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHTMocCo = "";
        for (String s:listDeXuatMocCo)
        {
            if (!textHTMocCo.trim().equals("") && !s.trim().equals(""))
                textHTMocCo = textHTMocCo + "; " + s.replace("\n","") ;
            else textHTMocCo = textHTMocCo + s.replace("\n","") ;
        }
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(textHTMocCo);
        getProductListDX(editText,LoaiCauKien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuHocDeXuat(editText,LoaiCauKien,listDeXuatMocCo);
                vitriDeXuatMocCo =1;
                tv.setText(listDeXuatMocCo.get(vitriDeXuatMocCo).replace("\n",""));
                tvViTriDeXuatMocCo.setText("Đề xuất: " + String.valueOf(vitriDeXuatMocCo) + "/" + String.valueOf(listDeXuatMocCo.size()));
                for(int i= 0 ;i<=10;i++)
                {
                    listDeXuatMocCo.add("");
                }
                dialog.dismiss();

            }
        });

            SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
            searchViewCT.setIconifiedByDefault(false);
            searchViewCT.setQueryHint("Tìm kiếm");
        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
        }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Đề xuất của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void DialogHientrangCheckBoxDXKheHo(final EditText tv,final String LoaiCauKien) {
        try
        {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHTKheHo = "";
        for (String s:listDeXuatKheHo)
        {
            if (!textHTKheHo.trim().equals("") && !s.trim().equals(""))
                textHTKheHo = textHTKheHo + "; " + s.replace("\n","") ;
            else textHTKheHo = textHTKheHo + s.replace("\n","") ;
        }
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(textHTKheHo);
        getProductListDX(editText,LoaiCauKien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuHocDeXuat(editText,LoaiCauKien,listDeXuatKheHo);
                vitriDeXuatKheHo =1;
                tv.setText(listDeXuatKheHo.get(vitriDeXuatKheHo).replace("\n",""));
                tvViTriDeXuatKheHo.setText("Đề xuất: " + String.valueOf(vitriDeXuatKheHo) + "/" + String.valueOf(listDeXuatKheHo.size()));

                for(int i= 0 ;i<=10;i++)
                {
                    listDeXuatKheHo.add("");
                }
                dialog.dismiss();

            }
        });
            SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
            searchViewCT.setIconifiedByDefault(false);
            searchViewCT.setQueryHint("Tìm kiếm");
        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
    }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Đề xuất của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void DialogHientrangCheckBoxDXPhuKien(final EditText tv,final String LoaiCauKien) {
        try
        {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHTPhuKien = "";
        for (String s:listDeXuatPhuKien)
        {
            if (!textHTPhuKien.trim().equals("") && !s.trim().equals(""))
                textHTPhuKien = textHTPhuKien + "; " + s.replace("\n","") ;
            else textHTPhuKien = textHTPhuKien + s.replace("\n","") ;
        }
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(textHTPhuKien);
        getProductListDX(editText,LoaiCauKien);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TuHocDeXuat(editText,LoaiCauKien,listDeXuatPhuKien);
                vitriDeXuatPhuKien =1;
                tv.setText(listDeXuatPhuKien.get(vitriDeXuatPhuKien).replace("\n",""));
                tvViTriDeXuatPhuKien.setText("Đề xuất: " + String.valueOf(vitriDeXuatPhuKien) + "/" + String.valueOf(listDeXuatPhuKien.size()));
                for(int i= 0 ;i<=10;i++)
                {
                    listDeXuatPhuKien.add("");
                }
                dialog.dismiss();

            }
        });
            SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
            searchViewCT.setIconifiedByDefault(false);
            searchViewCT.setQueryHint("Tìm kiếm");
        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
    }
        catch (ArithmeticException e)
        {Toast.makeText(FormMain.this, "Đề xuất của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    public void TuHocDeXuat(final EditText editText,final String LoaiCauKien,ArrayList<String> listHienTrang){
     try
     {
        listHienTrang.clear();listHienTrang.add("");
        if(editText.getText().toString().contains(("; ")))
        {
            String[] dongHienTrang =editText.getText().toString().split(("; "));
            for( String s:dongHienTrang)
            {
                listHienTrang.add(s.replace("\n",""));
                //Tự động học hiện trạng
                if (!LoaiCauKien.equals("DeXuatBeTong"))
                {
                    if (!DeXuatThep.contains(s.replace("\n",""))){
                        DeXuatThep = DeXuatThep.replace("\n","") +"@"+s.replace("\n","");
                        saveDataOnTemplate(DeXuatThep,"DeXuatThep");
                        Toast.makeText(FormMain.this, "Thêm mới đề xuất thép " + s, Toast.LENGTH_SHORT).show();
                    }
                }
                else if (LoaiCauKien.equals("DeXuatBeTong"))
                {
                    if (!DeXuatBeTong.contains(s.replace("\n",""))){
                        DeXuatBeTong = DeXuatBeTong.replace("\n","") +"@"+s.replace("\n","");
                        saveDataOnTemplate(DeXuatBeTong,"DeXuatBeTong");
                        Toast.makeText(FormMain.this, "Thêm mới đề xuất bê tông " + s, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        else
        {
            listHienTrang.add(editText.getText().toString().replace("\n",""));
            //Tự động học hiện trạng
            if (!LoaiCauKien.equals("DeXuatBeTong"))
            {
                if (!DeXuatThep.contains(editText.getText().toString().replace("\n",""))){
                    DeXuatThep = DeXuatThep.replace("\n","") +"@"+editText.getText().toString().replace("\n","");
                    saveDataOnTemplate(DeXuatThep,"HienTrangThep");
                    Toast.makeText(FormMain.this, "Thêm mới đề xuất thép " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
            else if (LoaiCauKien.equals("HienTrangBeTong"))
            {
                if (!DeXuatBeTong.contains(editText.getText().toString().replace("\n",""))){
                    DeXuatBeTong = DeXuatBeTong.replace("\n","") +"@"+editText.getText().toString().replace("\n","");
                    saveDataOnTemplate(DeXuatBeTong,"DeXuatBeTong");
                    Toast.makeText(FormMain.this, "Thêm mới đề xuất bê tông " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
     }
     catch (ArithmeticException e)
     {Toast.makeText(FormMain.this, "Đề xuất của bạn bị lỗi: " + e, Toast.LENGTH_SHORT).show();}
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = listView.getPositionForView(buttonView);
        if (pos!=ListView.INVALID_POSITION) {
            HienTrang hienTrang = productList.get(pos);
            hienTrang.setActive(isChecked);
            editText.setText(printSelectedItems());
        }
    }
    //endregion
    /**Các Function**/
    //region FUNCTION
    public void NhanBienTruyen() {
        /**Tạo **/
        File Template = new File(Environment.getExternalStorageDirectory(),"DataViettel");
        if (!Template.exists()) {
            if (!Template.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
            else {Toast.makeText(getApplicationContext(), "Dữ liệu được lưu trong DataViettel trên máy bạn" , Toast.LENGTH_SHORT).show();
            }
        }
        readFileDanhGia();
        Intent intent = getIntent();
        MaTram = intent.getStringExtra("TenTram");
        SoMong= Integer.parseInt(intent.getStringExtra("SoMong"));
        SoTangDay= Integer.parseInt(intent.getStringExtra("SoTangDay"));
        SoDot= Integer.parseInt(intent.getStringExtra("SoDot"));
        ViTriDat = intent.getStringExtra("ViTriDat");
        KichThuocMong = intent.getStringExtra("KichThuocMong");
        KichThuocCot = intent.getStringExtra("KichThuocCot");
        SoCot= Integer.parseInt(intent.getStringExtra("SoChanCot"));
        GiaChongXoay = intent.getStringExtra("GiaChongXoay");
        ChieuCaoDot = intent.getStringExtra("ChieuCaoDot");
        MacBeTong = intent.getStringExtra("MacBeTong");
        Latitude = intent.getStringExtra("Lat");
        Logiest = intent.getStringExtra("Long");
        LocalCity = intent.getStringExtra("ViTri");
        TextView tvToaDo = findViewById(R.id.tvToaDo);
        TextView tvViTri = findViewById(R.id.tvViTri);
        tvToaDo.setText(Logiest + "'N" +"  "+ Latitude+ "'E");
        tvViTri.setText(LocalCity);
        listGCX.clear();
        if (ViTriDat.contains("đất")) btnDulieuDam.setVisibility(View.GONE);
        //region Tách gá chống xoay
        try {
            String[] GaChongXay = GiaChongXoay.split(",");
            for (int i=0;i<GaChongXay.length;i++)
            {
                final boolean boo = isNumeric(String.valueOf(GaChongXay[i].trim()));
                if (boo)
                    listGCX.add(GaChongXay[i].trim()) ;
            }
        }
        catch (Exception e)
        {

        }
        SoGaChongXoay= listGCX.size();
        //endregion
        //region Đọc số anten
        File file2 = new File(Environment.getExternalStorageDirectory(), "DataViettel");
        file2 = new File(file2,"Data"+ MaTram);
        File fileSoAnten = new File(file2,"SoAnten.txt");
        if (fileSoAnten.exists())
        {
            String text = readText(fileSoAnten);
            SoAnten = Integer.parseInt(text.trim());
        }
        else SoAnten=0;
        //endregion


    }
    public String[][] DataforPath(File file) {
        /**ĐỌC ĐƯỜNG DẪN*/
        String string="";
        int rowCount;
        int columnCount;
        BufferedReader input = null;
        try {

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null)
            {
                buffer.append(line).append("\n");
            }
            string = buffer.toString();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        /**Gán vào mảng**/
        //Log.d("item",string);
        String[] phuluccon = string.split("@");
        rowCount = 0;
        columnCount = 0;
        for (int j = 0; j < phuluccon.length; j++) {
            String[] phuluccongtac = phuluccon[j].split("_");
            rowCount ++;
            for (int i = 0; i < phuluccongtac.length; i++) {
                //Log.d("item:",phuluccongtac[i]);
                columnCount++;
                //mangString[j][i] = phuluccongtac[i].toString();
            }
        }
        String mangString[][] = new String[rowCount][columnCount];
        for (int j = 0; j < phuluccon.length; j++) {
            String[] phuluccongtac = phuluccon[j].split("_");
            for (int i = 0; i < phuluccongtac.length; i++) {
                //Log.d("item:",phuluccongtac[i]);
                mangString[j][i] = phuluccongtac[i].toString();
            }
        }
        return mangString;
    }
    public void SetPopup(final ArrayList<String> listHT,TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this,textView);
                for (String s:listHT)
                    popupMenu.getMenu().add(s);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        textView.setText(item.getTitle());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }
    public void SetPopup2(final ArrayList<String> listHT,TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain.this,textView);
                for (String s:listHT)
                    popupMenu.getMenu().add(s);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (textView.getText().toString().trim().equals("")){
                            textView.setText(item.getTitle());
                        } else {
                            textView.setText(textView.getText().toString().trim()+","+ item.getTitle());
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogViewHienTrang(textView.getText().toString(),textView);
                return false;
            }
        });
    }
    private void DialogViewHienTrang(String string,final TextView textView) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_view);
        dialog.show();
        final EditText txtTentram = (EditText) dialog.findViewById(R.id.txtHienTrang);
        txtTentram.setText(string);
        Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(txtTentram.getText().toString());
                listHT.clear();
                String[] HT = textView.getText().toString().split(",");
                for (String s:HT)
                {
                    listHT.add(s);
                }
                dialog.dismiss();
            }
        });
        Button btnThoat = (Button) dialog.findViewById(R.id.btnthoat);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
    }
    public void readFileDanhGia(){

        BufferedReader input = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "Template");
            file = new File(file, "DANHGIAHIENTRANG.txt");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            DanhGiaHienTrang = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDataTable(TableRow row,String[][] data,int position,int count){
        try {
            for (int i = 0; i < count; i++) {
                TextView tv = (TextView) (((TableRow) row)).getChildAt(i);
                tv.setText(data[position][i].toString()); // set selected text data into the
                tv.setHeight(65);
                tv.setTextSize(15);
            }
        }catch (Exception e) {}

    }
    public Boolean setDataTableGoc(TableRow row,String[][] data,int position,int count){
        Boolean kiemtra = true;
        try {
            for (int i = 0; i < count; i++) {
                TextView tv = (TextView) (((TableRow) row)).getChildAt(i);
                tv.setText(data[position][i]); // set selected text data into the
                tv.setHeight(65);
                tv.setTextSize(15);
                if (i==2)
                {
                    if(SoMong == 3)
                    {
                        double bien = Math.abs(Double.valueOf(data[position][2]) - 120);
                        if(bien >10 || data[position][i].equals("NaN"))
                        {
                            tv.setBackground(getResources().getDrawable(R.drawable.boder_red));
                            kiemtra = false;
                        }
                        else
                        {
                            tv.setBackground(getResources().getDrawable(R.drawable.boder_green));
                            kiemtra = true;
                        }
                    }
                }
            }
        }catch (Exception e) {}
        return kiemtra;
    }
    public void setColumnData(TableRow row,String data,int position){
        try {

            TextView tv = (TextView) (((TableRow) row)).getChildAt(position);
            tv.setText(data.toString()); // set selected text data into the
            tv.setHeight(65);
            tv.setTextSize(15);

        }catch (Exception e) {}

    }
    public String GetTableData(TableLayout table,int ColumnCount) {
        String string="";
        if(table==Table2)
        {
            int Rowcount = Table2.getChildCount();
            for(int i=1;i<SoMong+2;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
            TableRow BuLong = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-4);
            string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(BuLong,ColumnCount).trim();

            TableRow MocCo = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-3);
            string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(MocCo,ColumnCount).trim();

            if (ViTriDat.contains("Trênmái"))
            {
                TableRow Dam1 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-2);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(Dam1,ColumnCount).trim();
                TableRow Dam2 = (TableRow) (((TableLayout) Table2)).getChildAt(Rowcount-1);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(Dam2,ColumnCount).trim();

            }
        }
        else if(table==Table3)
        {
            int Rowcount = Table3.getChildCount();
            for(int i=1;i<SoMong+2;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
            if (ViTriDat.contains("Trênmái"))
            {
                TableRow Dam1 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-2);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(Dam1,ColumnCount).trim();
                TableRow Dam2 = (TableRow) (((TableLayout) Table3)).getChildAt(Rowcount-1);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(Dam2,ColumnCount).trim();

            }
        }
        else if(table==TableToaDoMong||table==TableToaDoMong_Mstower|| table == TableCanhCanhCanh|| table == TableCanhGocCanh)
        {
            for(int i=1;i<SoMong+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
        }
        else if(table==TableMstower)
        {
            for(int i=1;i<SoDot+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
        }
        else if(table==TableCaoDoDayCo)
        {
            for(int i=1;i<SoTangDay+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) TableCaoDoDayCo)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
                //setColumnData(row,KichThuocMong,1);
            }
        }
        else if (table==TableBiaTTC)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
            }
        }
        if (table==Table4 || table == Table5 || table == Table12_ChanCot|| table == Table12_DinhCot|| table == Table12_HuongBac)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);
            }
        }
        else if (table==Table6)
        {
            for(int i=1;i<SoDot +1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table6)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);
            }
        }
        if (table==Table7)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);
            }
        }
        else if(table==Table8)
        {
            for(int i=2;i<listTangDayCo.size()+2;i++)
            {
                String rowi="["+ String.valueOf(i-1);
                TableRow row = (TableRow) (((TableLayout) Table8)).getChildAt(i);
                for(int j=0;j<SoMong*2+1;j++)
                {
                    TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                    rowi=rowi+"&"+column.getText();
                }
                rowi=rowi+"]";
                string = string+rowi;
            }
        }

        else if(table==Table9)
        {
            for(int i=2;i<listTangDayCo.size()+2;i++)
            {
                String rowi="[";
                TableRow row = (TableRow) (((TableLayout) Table9)).getChildAt(i);
                for(int j=0;j<(SoMong+1)*2+1;j++)
                {
                    TextView column = (TextView) (((TableRow) row)).getChildAt(j);
                    if (j==0)
                        rowi=rowi+column.getText();
                    else
                        rowi=rowi+"&"+column.getText();
                }
                rowi=rowi+"]";
                string = string+rowi;
            }
        }
        if(table==Table10)
        {
            for(int i=1;i<=listLienKetCot.size();i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);
            }
        }
        else if (table==Table11)
        {
            //int Rowcount = table.getChildCount();
            for(int i=1;i<SoAnten+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);
            }
        }
        else if (table==Table12_V0 || table==Table12_V1 || table==Table12_V2|| table==Table12_V3)
        {
            for(int i=1;i<SoCot+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ") + GetRowData(row,ColumnCount);

            }
        }
        return string;
    }
    public String GetRowData(TableRow row,int count) {
        String string="[";
        /**DÒNG MÓNG M0*/
        /**GET TEXT ON ROW**/
        //final String[] str = new String[count];
        for (int i = 0; i < count; i++)
        {
            TextView tv = (TextView) (((TableRow) row)).getChildAt(i);
            if (i==0) string = string + tv.getText().toString().trim().replace("&","");
            if (i!=0) string = string +"&"+ tv.getText().toString().trim().replace("&","");

            //str[i] = tv.getText().toString(); // set selected text data into the
        }
        string = string+"]";
        //string = Arrays.toString(str);
        return string;
    }
    public void saveDataOnCacher(String text,String Name){
        String content = text;
        content = content.replace("[","@");
        content = content.replace("]","");
        content = content.replace("&","_");
        content = content.replace("  ","");
        content = content.replace("   ","");
        File file;
        FileOutputStream outputStream;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "DataViettel");

            file = new File(file, "Data"+ MaTram);
            if (!file.exists())
                if (!file.mkdirs()) {Log.d("App", "failed to create directory");}
            file = new File(file, Name+".txt");
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
            //Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    public void setPopUpBeTong(){
        try
        {
            //ĐỀ XUÂT
            DeXuatBeTong=StringforPath("DeXuatBeTong");
            listDeXuatBeTong.clear();
            String[] phulucdx = DeXuatBeTong.split("@");
                for (int i = 0; i < phulucdx.length; i++)
                {
                    listDeXuatBeTong.add(phulucdx[i]);
                }
        }
        catch (Exception e)
        {
            DeXuatBeTong = UT.DeXuatBeTong;
            saveDataOnTemplate(DeXuatBeTong,"DeXuatBeTong");
            setPopUpBeTong();
        }
        try
        {
            //Hiện trạng
            HienTrangBeTong=StringforPath("HienTrangBeTong");
            listHienTrangBeTong.clear();
            String[] phulucht = HienTrangBeTong.split("@");
                for (int i = 0; i < phulucht.length; i ++)
                {
                    listHienTrangBeTong.add(phulucht[i]);
                }

        }
        catch (Exception e)
        {
            HienTrangBeTong = UT.HienTrangBeTong;
            saveDataOnTemplate(HienTrangBeTong,"HienTrangBeTong");
            setPopUpBeTong();
        }
        /**
         * SETUP POPUP MENU
         */
        Collections.sort(listHienTrangBeTong);
        Collections.sort(listDeXuatBeTong);
        ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listHienTrangBeTong);
        edtHienTrang.setAdapter(adapterHT);
        edtHienTrang.setThreshold(1);
        edtHienTrang.setDropDownHeight(500);

        ArrayAdapter<String> adapterDX = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listDeXuatBeTong);
        edtDeXuat.setAdapter(adapterDX);
        edtDeXuat.setThreshold(1);
        edtDeXuat.setDropDownHeight(500);
    }
    public void setPopUpThep(){
        try
        {
            //ĐỀ XUÂT
            DeXuatThep=StringforPath("DeXuatThep");
            listDeXuatThep.clear();
            String[] phulucdx = DeXuatThep.split("@");
            for (int i = 0; i < phulucdx.length; i++)
            {
                listDeXuatThep.add(phulucdx[i]);
            }
        }
        catch (Exception e)
        {
            DeXuatThep = UT.DeXuatThep;
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThep();
        }
        try
        {
            //Hiện trạng
            HienTrangThep=StringforPath("HienTrangThep");
            listHienTrangThep.clear();
            String[] phulucht = HienTrangThep.split("@");
            for (int i = 0; i < phulucht.length; i ++)
            {
                listHienTrangThep.add(phulucht[i]);
            }

        }
        catch (Exception e)
        {
            HienTrangThep = UT.HienTrangThep;
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThep();
        }
        /**
         * SETUP POPUP MENU
         */
        Collections.sort(listHienTrangThep);
        Collections.sort(listDeXuatThep);
        ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listHienTrangThep);
        edtHienTrang.setAdapter(adapterHT);
        edtHienTrang.setThreshold(1);
        edtHienTrang.setDropDownHeight(500);

        ArrayAdapter<String> adapterDX = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listDeXuatThep);
        edtDeXuat.setAdapter(adapterDX);
        edtDeXuat.setThreshold(1);
        edtDeXuat.setDropDownHeight(500);
    }
    public void setPopUpThepBulong(){
        try
        {
            //ĐỀ XUÂT
            DeXuatThep=StringforPath("DeXuatThep");
            listDeXuatThep.clear();
            String[] phulucdx = DeXuatThep.split("@");
            for (int i = 0; i < phulucdx.length; i++)
            {
                listDeXuatThep.add(phulucdx[i]);
            }
        }
        catch (Exception e)
        {
            DeXuatThep = UT.DeXuatThep;
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThep();
        }
        try
        {
            //Hiện trạng
            HienTrangThep=StringforPath("HienTrangThep");
            listHienTrangThep.clear();
            String[] phulucht = HienTrangThep.split("@");
            for (int i = 0; i < phulucht.length; i ++)
            {
                listHienTrangThep.add(phulucht[i]);
            }

        }
        catch (Exception e)
        {
            HienTrangThep = UT.HienTrangThep;
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThep();
        }
        /**
         * SETUP POPUP MENU
         */
        Collections.sort(listHienTrangThep);
        Collections.sort(listDeXuatThep);
        ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listHienTrangThep);
        edtHienTrangBulong.setAdapter(adapterHT);
        edtHienTrangBulong.setThreshold(1);
        edtHienTrangBulong.setDropDownHeight(500);

        ArrayAdapter<String> adapterDX = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listDeXuatThep);
        edtDeXuatBulong.setAdapter(adapterDX);
        edtDeXuatBulong.setThreshold(1);
        edtDeXuatBulong.setDropDownHeight(500);
    }
    public void setPopUpThepCauKien(){
        try
        {
            //ĐỀ XUÂT
            DeXuatThep=StringforPath("DeXuatThep");
            listDeXuatThep.clear();
            String[] phulucdx = DeXuatThep.split("@");
            for (int i = 0; i < phulucdx.length; i++)
            {
                listDeXuatThep.add(phulucdx[i]);
            }
        }
        catch (Exception e)
        {
            DeXuatThep = UT.DeXuatThep;
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThepCauKien();
        }
        try
        {
            //Hiện trạng
            HienTrangThep=StringforPath("HienTrangThep");
            listHienTrangThep.clear();
            String[] phulucht = HienTrangThep.split("@");
            for (int i = 0; i < phulucht.length; i ++)
            {
                listHienTrangThep.add(phulucht[i]);
            }

        }
        catch (Exception e)
        {
            HienTrangThep = UT.HienTrangThep;
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThepCauKien();
        }
        /**
         * SETUP POPUP MENU
         */
        Collections.sort(listHienTrangThep);
        Collections.sort(listDeXuatThep);
        ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listHienTrangThep);
        edtHienTrangCauKien.setAdapter(adapterHT);
        edtHienTrangCauKien.setThreshold(1);
        edtHienTrangCauKien.setDropDownHeight(500);

        ArrayAdapter<String> adapterDX = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listDeXuatThep);
        edtDeXuatCauKien.setAdapter(adapterDX);
        edtDeXuatCauKien.setThreshold(1);
        edtDeXuatCauKien.setDropDownHeight(500);
    }
    public void setPopUpThepMocCo(){
        try
        {
            //ĐỀ XUÂT
            DeXuatThep=StringforPath("DeXuatThep");
            listDeXuatThep.clear();
            String[] phulucdx = DeXuatThep.split("@");
            for (int i = 0; i < phulucdx.length; i++)
            {
                listDeXuatThep.add(phulucdx[i]);
            }
        }
        catch (Exception e)
        {
            DeXuatThep = UT.DeXuatThep;
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThep();
        }
        try
        {
            //Hiện trạng
            HienTrangThep=StringforPath("HienTrangThep");
            listHienTrangThep.clear();
            String[] phulucht = HienTrangThep.split("@");
            for (int i = 0; i < phulucht.length; i ++)
            {
                listHienTrangThep.add(phulucht[i]);
            }

        }
        catch (Exception e)
        {
            HienTrangThep = UT.HienTrangThep;
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThep();
        }
        /**
         * SETUP POPUP MENU
         */
        Collections.sort(listHienTrangThep);
        Collections.sort(listDeXuatThep);
        ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listHienTrangThep);
        edtHienTrangMocCo.setAdapter(adapterHT);
        edtHienTrangMocCo.setThreshold(1);
        edtHienTrangMocCo.setDropDownHeight(500);

        ArrayAdapter<String> adapterDX = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listDeXuatThep);
        edtDeXuatMocCo.setAdapter(adapterDX);
        edtDeXuatMocCo.setThreshold(1);
        edtDeXuatMocCo.setDropDownHeight(500);
    }
    public void setPopUpThepKheHo(){
        try
        {
            //ĐỀ XUÂT
            DeXuatThep=StringforPath("DeXuatThep");
            listDeXuatThep.clear();
            String[] phulucdx = DeXuatThep.split("@");
            for (int i = 0; i < phulucdx.length; i++)
            {
                listDeXuatThep.add(phulucdx[i]);
            }
        }
        catch (Exception e)
        {
            DeXuatThep = UT.DeXuatThep;
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThepKheHo();
        }
        try
        {
            //Hiện trạng
            HienTrangThep=StringforPath("HienTrangThep");
            listHienTrangThep.clear();
            String[] phulucht = HienTrangThep.split("@");
            for (int i = 0; i < phulucht.length; i ++)
            {
                listHienTrangThep.add(phulucht[i]);
            }

        }
        catch (Exception e)
        {
            HienTrangThep = UT.HienTrangThep;
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThepKheHo();
        }
        /**
         * SETUP POPUP MENU
         */
        Collections.sort(listHienTrangThep);
        Collections.sort(listDeXuatThep);
        ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listHienTrangThep);
        edtHienTrangKheHo.setAdapter(adapterHT);
        edtHienTrangKheHo.setThreshold(1);
        edtHienTrangKheHo.setDropDownHeight(500);

        ArrayAdapter<String> adapterDX = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listDeXuatThep);
        edtDeXuatKheHo.setAdapter(adapterDX);
        edtDeXuatKheHo.setThreshold(1);
        edtDeXuatKheHo.setDropDownHeight(500);
    }
    public void setPopUp(){
        try
        {
            //KICH THƯỚC MÓNG
            KichThuocMong =StringforPath("KICHTHUOC");
            ArraylistKT.clear();
            String[] phuluckt = KichThuocMong.split("--");
            for (int j = 1; j < phuluckt.length; j = j + 1){
                String[] phuluccongtac = phuluckt[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2)
                {
                    ArraylistKT.add(phuluccongtac[i]);
                }
            }
        }
        catch (Exception e)
        {
            KichThuocMong = UT.KichThuocMong;
            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
            setPopUp();
        }

        /**
         * SETUP POPUP MENU
         */

        ArrayAdapter<String> adapterKT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, ArraylistKT);
        edtKichThuoc.setAdapter(adapterKT);
        edtKichThuoc.setThreshold(1);
        edtKichThuoc.setDropDownHeight(500);

    }
    public void setPopUpBulong(){
        try
        {
            //KICH THƯỚC MÓNG
            KichThuocMong =StringforPath("KICHTHUOC");
            ArraylistKT.clear();
            String[] phuluckt = KichThuocMong.split("--");
            for (int j = 1; j < phuluckt.length; j = j + 1){
                String[] phuluccongtac = phuluckt[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2)
                {
                    ArraylistKT.add(phuluccongtac[i]);
                }
            }
        }
        catch (Exception e)
        {
            KichThuocMong = UT.KichThuocMong;
            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
            setPopUp();
        }

        /**
         * SETUP POPUP MENU
         */

        ArrayAdapter<String> adapterKT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, ArraylistKT);
        edtKichThuocBulong.setAdapter(adapterKT);
        edtKichThuocBulong.setThreshold(1);
        edtKichThuocBulong.setDropDownHeight(500);

    }
    public void setPopUpCauKien(){
        try
        {
            //KICH THƯỚC MÓNG
            KichThuocMong =StringforPath("KICHTHUOC");
            ArraylistKT.clear();
            String[] phuluckt = KichThuocMong.split("--");
            for (int j = 1; j < phuluckt.length; j = j + 1){
                String[] phuluccongtac = phuluckt[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2)
                {
                    ArraylistKT.add(phuluccongtac[i]);
                }
            }
        }
        catch (Exception e)
        {
            KichThuocMong = UT.KichThuocMong;
            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
            setPopUp();
        }

        /**
         * SETUP POPUP MENU
         */

        ArrayAdapter<String> adapterKT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, ArraylistKT);
        edtKichThuocCauKien.setAdapter(adapterKT);
        edtKichThuocCauKien.setThreshold(1);
        edtKichThuocCauKien.setDropDownHeight(500);

    }
    public void setPopUpMocCo(){
        try
        {
            //KICH THƯỚC MÓNG
            KichThuocMong =StringforPath("KICHTHUOC");
            ArraylistKT.clear();
            String[] phuluckt = KichThuocMong.split("--");
            for (int j = 1; j < phuluckt.length; j = j + 1){
                String[] phuluccongtac = phuluckt[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2)
                {
                    ArraylistKT.add(phuluccongtac[i]);
                }
            }
        }
        catch (Exception e)
        {
            KichThuocMong = UT.KichThuocMong;
            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
            setPopUp();
        }

        /**
         * SETUP POPUP MENU
         */
        ArrayAdapter<String> adapterKT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, ArraylistKT);
        edtKichThuocMocCo.setAdapter(adapterKT);
        edtKichThuocMocCo.setThreshold(1);
        edtKichThuocMocCo.setDropDownHeight(500);

    }
    public void setPopUpThepPhuKien(){
        try
        {
            //ĐỀ XUÂT
            DeXuatThep=StringforPath("DeXuatThep");
            listDeXuatThep.clear();
            String[] phulucdx = DeXuatThep.split("@");
            for (int i = 0; i < phulucdx.length; i++)
            {
                listDeXuatThep.add(phulucdx[i]);
            }
        }
        catch (Exception e)
        {
            DeXuatThep = UT.DeXuatThep;
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThep();
        }
        try
        {
            //Hiện trạng
            HienTrangThep=StringforPath("HienTrangThep");
            listHienTrangThep.clear();
            String[] phulucht = HienTrangThep.split("@");
            for (int i = 0; i < phulucht.length; i ++)
            {
                listHienTrangThep.add(phulucht[i]);
            }

        }
        catch (Exception e)
        {
            HienTrangThep = UT.HienTrangThep;
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThep();
        }
        /**
         * SETUP POPUP MENU
         */
        Collections.sort(listHienTrangThep);
        Collections.sort(listDeXuatThep);
        ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listHienTrangThep);
        edtHienTrangPhuKien.setAdapter(adapterHT);
        edtHienTrangPhuKien.setThreshold(1);
        edtHienTrangPhuKien.setDropDownHeight(500);

        ArrayAdapter<String> adapterDX = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, listDeXuatThep);
        edtDeXuatPhuKien.setAdapter(adapterDX);
        edtDeXuatPhuKien.setThreshold(1);
        edtDeXuatPhuKien.setDropDownHeight(500);
    }
    public void setPopUpPhuKien(){
        try
        {
            //KICH THƯỚC MÓNG
            KichThuocMong =StringforPath("KICHTHUOC");
            ArraylistKT.clear();
            String[] phuluckt = KichThuocMong.split("--");
            for (int j = 1; j < phuluckt.length; j = j + 1){
                String[] phuluccongtac = phuluckt[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2)
                {
                    ArraylistKT.add(phuluccongtac[i]);
                }
            }
        }
        catch (Exception e)
        {
            KichThuocMong = UT.KichThuocMong;
            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
            setPopUp();
        }

        /**
         * SETUP POPUP MENU
         */

        ArrayAdapter<String> adapterKT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, ArraylistKT);
        edtKichThuocPhuKien.setAdapter(adapterKT);
        edtKichThuocPhuKien.setThreshold(1);
        edtKichThuocPhuKien.setDropDownHeight(500);

    }
    public void setPopUpThanCot(){
        try
        {
            //KICH THƯỚC MÓNG
            KichThuocMong =StringforPath("KICHTHUOC");
            ArraylistKT.clear();
            String[] phuluckt = KichThuocMong.split("--");
            for (int j = 1; j < phuluckt.length; j = j + 1){
                String[] phuluccongtac = phuluckt[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2)
                {
                    ArraylistKT.add(phuluccongtac[i]);
                }
            }
        }
        catch (Exception e)
        {
            KichThuocMong = UT.KichThuocMong;
            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
            setPopUpThanCot();
        }

        /**
         * SETUP POPUP MENU
         */

        ArrayAdapter<String> adapterKT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, ArraylistKT);
        edtKichThuoc.setAdapter(adapterKT);
        edtKichThuoc.setThreshold(1);
        edtKichThuoc.setDropDownHeight(500);

        edtThanhCanh.setAdapter(adapterKT);
        edtThanhCanh.setThreshold(1);
        edtThanhCanh.setDropDownHeight(500);

        edtThanhGiang.setAdapter(adapterKT);
        edtThanhGiang.setThreshold(1);
        edtThanhGiang.setDropDownHeight(500);

    }
    public void setPopUpKheHo(){
        try
        {
            //KICH THƯỚC MÓNG
            KichThuocMong =StringforPath("KICHTHUOC");
            ArraylistKT.clear();
            String[] phuluckt = KichThuocMong.split("--");
            for (int j = 1; j < phuluckt.length; j = j + 1){
                String[] phuluccongtac = phuluckt[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2)
                {
                    ArraylistKT.add(phuluccongtac[i]);
                }
            }
        }
        catch (Exception e)
        {
            KichThuocMong = UT.KichThuocMong;
            saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
            setPopUpKheHo();
        }

        /**
         * SETUP POPUP MENU
         */


        ArrayAdapter<String> adapterKT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, ArraylistKT);
        edtKichThuocKheHo.setAdapter(adapterKT);
        edtKichThuocKheHo.setThreshold(1);
        edtKichThuocKheHo.setDropDownHeight(500);

        edtThanhCanhKheHo.setAdapter(adapterKT);
        edtThanhCanhKheHo.setThreshold(1);
        edtThanhCanhKheHo.setDropDownHeight(500);

    }
    public void setPopUpAnten(){
        try
        {
            //KICH THƯỚC MÓNG
            KichThuocMong =StringforPath("LOAIANTEN");
            ArraylistKT.clear();
            String[] phuluckt = KichThuocMong.split("--");
            for (int j = 1; j < phuluckt.length; j = j + 1){
                String[] phuluccongtac = phuluckt[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2)
                {
                    ArraylistKT.add(phuluccongtac[i]);
                }
            }
        }
        catch (Exception e)
        {
            KichThuocMong = UT.LoaiAnten;
            saveDataOnTemplate(KichThuocMong,"LOAIANTEN");
            setPopUpAnten();
        }

        /**
         * SETUP POPUP MENU
         */
        ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, ArraylistKT);
        edtLoaiAnten.setAdapter(adapterHT);
        edtLoaiAnten.setThreshold(1);
        edtLoaiAnten.setDropDownHeight(500);
        edtLoaiAnten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] string = TRABANGANTEN(edtLoaiAnten.getText().toString());
                edtKichThuocAnten.setText(string[0]);
                edtTrongLuong.setText(string[1]);
            }
        });

        ArrayAdapter<String> adapterKT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, ArraylistKT);
        edtTrongLuong.setAdapter(adapterKT);
        edtTrongLuong.setThreshold(1);
        edtTrongLuong.setDropDownHeight(500);

        ArrayAdapter<String> adapterDX = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, ArraylistKT);
        edtKichThuocAnten.setAdapter(adapterDX);
        edtKichThuocAnten.setThreshold(1);
        edtKichThuocAnten.setDropDownHeight(500);
    }
    public String StringforPath(String name){
        String s=null;
        BufferedReader input = null;
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "Template");
            file = new File(file, name+".txt");
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            s = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    public void saveDataOnTemplate(String text,String Name){
        String content = text;
        File file;
        FileOutputStream outputStream;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "Template");
            file = new File(file, Name+".txt");
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDataTable2(TableRow row,String[][] data,int position,int count){
        try {
            for (int i = 1; i < count; i++) {
                TextView tv = (TextView) (((TableRow) row)).getChildAt(i);
                tv.setText(data[position][i+1].toString()); // set selected text data into the
                tv.setHeight(65);
                tv.setTextSize(15);
            }
        }
        catch (Exception e) {}
    }
    public String readText(File file){
        String text = "";
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            text = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    public String[] TRABANGANTEN (String dauvao) {
        String[] daura=new String[3];
        ArrayList<String> ArraylistKG = new ArrayList<String>();
        ArraylistKG.addAll(Arrays.asList("4G@1471x275x86@16.8","3G 2100@1314x155x70@9.8","Triple band gain thấp@1384x261x146@23.3","Dual band gain cao@2533x261x146@31.2","Dual band gain thấp@1334x261x146@20.3","Twinbeam@1390x301x181@21.5","RRU@425x300x190@15","FEEDER 7/8@0@0","RF@0@0","2G 900@2580x262x116@25.3","2G 1800@1314x155x70@9.8","Diplexer@425x300x190@15"));
        for (int i=0;i< ArraylistKG.size();i++){
            if (ArraylistKG.get(i).toString().contains(dauvao)){
                daura[0] = ArraylistKG.get(i).toString().split("@")[1];
                daura[1] = ArraylistKG.get(i).toString().split("@")[2];
                break;
            }

        }
        return daura;

    }
    public void KiemTraKhongCach( TableLayout table, int rowItem, int columnItem) {
        final DecimalFormat format = new DecimalFormat("0.000");

        TableRow rowCotTren = (TableRow) (((TableLayout) table)).getChildAt(rowItem);

        final EditText tvX1 = (EditText) (((TableRow) rowCotTren)).getChildAt(columnItem-3);
        final EditText tvY1 = (EditText) (((TableRow) rowCotTren)).getChildAt(columnItem-2);
        final TextView tvKQ = (TextView) (((TableRow) rowCotTren)).getChildAt(columnItem);

        int rowCotDuoi =rowItem+1;
        if (rowItem==SoCot) rowCotDuoi = 1;

        TableRow rowCot = (TableRow) (((TableLayout) table)).getChildAt(rowCotDuoi);
        final EditText tvX2 = (EditText) (((TableRow) rowCot)).getChildAt(columnItem-3);
        final EditText tvY2 = (EditText) (((TableRow) rowCot)).getChildAt(columnItem-2);
        tvKQ.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                try {
                    Double X1,X2,Y1,Y2,KQ;
                    //Log.d("x1:",KichThuocCot);
                    X1 = Double.parseDouble(tvX1.getText().toString());
                    X2 = Double.parseDouble(tvX2.getText().toString());
                    Y1 = Double.parseDouble(tvY1.getText().toString());
                    Y2 = Double.parseDouble(tvY2.getText().toString());
                    KQ = Math.sqrt((X1-X2)* (X1-X2) + (Y1-Y2)*(Y1-Y2));
                    String[] splThanCot = KichThuocCot.split("x");
                    Double bThanCot = Double.parseDouble(splThanCot[0]);
                    if (KQ >= bThanCot -0.05 && KQ <= bThanCot + 0.05)
                    {
                        tvKQ.setText(String.valueOf(format.format(KQ)).replace(",","."));
                        tvKQ.setBackground(getResources().getDrawable(R.drawable.boder_green));
                    }
                    else
                    {
                        tvKQ.setText(String.valueOf(format.format(KQ)).replace(",","."));
                        tvKQ.setBackground(getResources().getDrawable(R.drawable.boder_red));
                    }
                }
                catch (Exception e)
                {}
            }
        });

    }
    public Boolean TinhKhoangCach( TableLayout table, int rowItem, int columnItem,TextView textView) {
        Boolean kiemtra = false;
        final DecimalFormat format = new DecimalFormat("0.000");
        TableRow rowCotTren = (TableRow) (((TableLayout) table)).getChildAt(rowItem);

        final EditText tvX1 = (EditText) (((TableRow) rowCotTren)).getChildAt(columnItem-3);
        final EditText tvY1 = (EditText) (((TableRow) rowCotTren)).getChildAt(columnItem-2);

        int rowCotDuoi =rowItem-1;
        //if (rowItem==SoCot) rowCotDuoi = 1;

        TableRow rowCot = (TableRow) (((TableLayout) table)).getChildAt(rowCotDuoi);
        final EditText tvX2 = (EditText) (((TableRow) rowCot)).getChildAt(columnItem-3);
        final EditText tvY2 = (EditText) (((TableRow) rowCot)).getChildAt(columnItem-2);

        try {
            Double X1,X2,Y1,Y2,KQ;
            //Log.d("x1:",KichThuocCot);
            X1 = Double.parseDouble(tvX1.getText().toString());
            X2 = Double.parseDouble(tvX2.getText().toString());
            Y1 = Double.parseDouble(tvY1.getText().toString());
            Y2 = Double.parseDouble(tvY2.getText().toString());
            KQ = Math.sqrt((X1-X2)* (X1-X2) + (Y1-Y2)*(Y1-Y2));
            String[] splThanCot = KichThuocCot.split("x");
            Double bThanCot = Double.parseDouble(splThanCot[0]);
            if (KQ >= bThanCot -0.05 && KQ <= bThanCot + 0.05)
            {
                textView.setText("Toạ độ hợp lý:" + String.valueOf(format.format(KQ)).replace(",",".") + "(m)");
                textView.setTextColor(getResources().getColor(R.color.colorGreen));
                kiemtra = true;
            }
            else
            {
                textView.setText("Sai toạ độ:" + String.valueOf(format.format(KQ)).replace(",",".") + "(m)");
                textView.setTextColor(getResources().getColor(R.color.colorRed));
                kiemtra = false;
            }
        }
        catch (Exception e)
        {}
        return kiemtra;
    }
    public void TinhBangGoc(){
        try
        {
            DecimalFormat format = new DecimalFormat("0.00");
            File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
            file = new File(file,"Data"+ MaTram);
            file = new File(file,"TABLECanhCanhCanh.txt");
            String[][] dataMangCanh =DataforPath(file);
            String[][] dataKQ = new String[SoMong+1][3];
            for (int i=1;i<SoMong;i++)
            {
                double Canh1 = Double.parseDouble(dataMangCanh[i][1].trim());
                double Canh2 = Double.parseDouble(dataMangCanh[i+1][1].trim());
                double CanhA = Double.parseDouble(dataMangCanh[i][2].trim());

                dataKQ[i][0] = dataMangCanh[i][0];
                dataKQ[i+1][0] = dataMangCanh[i+1][0];
                dataKQ[i][1] = dataMangCanh[i][1];
                dataKQ[i+1][1] = dataMangCanh[i+1][1];
                dataKQ[i][2]=String.valueOf(format.format(TinhGoc(Canh1,Canh2,CanhA))).replace(",",".");


            }
            double tongcacgoc = 0;
            for (int i=1;i<SoMong;i++)
            {
                tongcacgoc = tongcacgoc + Double.parseDouble(dataKQ[i][2]);
            }
            dataKQ[SoMong][2]=String.valueOf(format.format(360-tongcacgoc)).replace(",",".");
            /*double Canh1 = Double.parseDouble(dataMangCanh[1][1].trim());
            double Canh2 = Double.parseDouble(dataMangCanh[2][1].trim());
            double CanhA = Double.parseDouble(dataMangCanh[1][2].trim());
            if (SoMong ==4)
                edtGocXoay.setText(String.valueOf(format.format(TinhGoc(Canh1,Canh2,CanhA)/2)).replace(",","."));
            */
            /**SET TEXT ON ROW**/
            int ColumnCount =3;
            int Rowcount = TableCanhGocCanh.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) TableCanhGocCanh)).getChildAt(i);
                row.setVisibility(View.GONE);
                //setDataTable(row,data,i,ColumnCount);
            }
            kiemtrabanggoc = true;
            for(int i=1;i<SoMong+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) TableCanhGocCanh)).getChildAt(i);
                row.setVisibility(View.VISIBLE);
                kiemtrabanggoc = setDataTableGoc(row,dataKQ,i,ColumnCount);
            }
            if (kiemtrabanggoc)
            {
                layoutBangCanh.setVisibility(View.GONE);
                layoutBangGoc.setVisibility(View.GONE);
                layoutBangToaDoMong.setVisibility(View.VISIBLE);
                Toast.makeText(FormMain.this,"Tính toạ độ thành công", Toast.LENGTH_SHORT).show();
            }
            else
            {
                layoutBangCanh.setVisibility(View.GONE);
                layoutBangGoc.setVisibility(View.VISIBLE);
                layoutBangToaDoMong.setVisibility(View.GONE);
                Toast.makeText(FormMain.this,"Kết quả đo các cạnh không hợp lý", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Toast.makeText(FormMain.this,"Kiểm tra lại kích thước các cạnh", Toast.LENGTH_SHORT).show();
        }

    }
    public void TinhBangToaDo(){
        try {
            DecimalFormat format = new DecimalFormat("0.0000");
            //DecimalFormat format0000 = new DecimalFormat("0.0000");
            File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
            file = new File(file, "Data" + MaTram);
            file = new File(file, "TABLECanhgocCanh.txt");
            String[][] dataMangGoc = DataforPath(file);
            String[][] dataKQTG = new String[SoMong + 1][4];
            String[][] dataKQ = new String[SoMong + 1][4];
            double Goc = 0.0;
            double GocMoc = 0.0;
            kiemtrabanggoc = true;
            for(int i=1;i<SoMong+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) TableCanhGocCanh)).getChildAt(i);
                row.setVisibility(View.VISIBLE);
                kiemtrabanggoc = setDataTableGoc(row,dataMangGoc,i,3);
            }

            if (SoMong ==4)  GocMoc = Double.parseDouble(dataMangGoc[1][2].trim())/2;

            if (SoMong ==3) if (!edtGocXoay.getText().toString().equals(""))
                GocMoc = Double.parseDouble(edtGocXoay.getText().toString());

            if (SoMong ==4) if (!edtGocXoay.getText().toString().equals(""))
                if (Double.parseDouble(edtGocXoay.getText().toString())>0)
                    GocMoc = (GocMoc+ Double.parseDouble(edtGocXoay.getText().toString()));
                else
                    GocMoc = -(GocMoc- Double.parseDouble(edtGocXoay.getText().toString()));

            for (int i = 0; i < SoMong; i++) {
                Double ToaDoX = Double.parseDouble(dataMangGoc[i + 1][1].trim());
                double[] ToaDoM = Xoay(ToaDoX, 0, -Goc);
                dataKQTG[i + 1][0] = "Móng M" + String.valueOf(i + 1);
                dataKQTG[i + 1][1] = String.valueOf(format.format(ToaDoM[0]).replace(",", "."));
                dataKQTG[i + 1][2] = String.valueOf(format.format(ToaDoM[1]).replace(",", "."));
                dataKQTG[i + 1][3] = String.valueOf(0);
                Goc = Goc + Double.parseDouble(dataMangGoc[i+1][2].trim());
            }
            for (int i = 0; i < SoMong; i++) {
                Double ToaDoX = Double.parseDouble(dataKQTG[i + 1][1].trim());
                Double ToaDoY = Double.parseDouble(dataKQTG[i + 1][2].trim());
                double[] ToaDoM = Xoay(ToaDoX, ToaDoY, -GocMoc);
                dataKQ[i + 1][0] = "Móng M" + String.valueOf(i + 1);
                dataKQ[i + 1][1] = String.valueOf(format.format(ToaDoM[0]).replace(",", "."));
                dataKQ[i + 1][2] = String.valueOf(format.format(ToaDoM[1]).replace(",", "."));
                dataKQ[i + 1][3] = String.valueOf(0);
            }

            /*double[] ToaDoM1 = Xoay(Double.parseDouble(dataMangGoc[1][1]), 0, GocMoc);

            dataKQ[1][0] = "Móng M" + String.valueOf(1);
            dataKQ[1][1] = String.valueOf(format.format(ToaDoM1[0]).replace(",", "."));
            dataKQ[1][2] = String.valueOf(format.format(ToaDoM1[1]).replace(",", "."));
            dataKQ[1][3] = String.valueOf(0);

            //GocMoc = TinhGocMoc(Double.parseDouble(dataKQ[1][1]),Double.parseDouble(dataKQ[1][2]));
            for (int i = 1; i < SoMong; i++) {
                Double ToaDoX = Double.parseDouble(dataMangGoc[i + 1][1].trim());
                double[] ToaDoM = Xoay(ToaDoX, 0, -GocMoc);
                dataKQ[i + 1][0] = "Móng M" + String.valueOf(i + 1);
                dataKQ[i + 1][1] = String.valueOf(format.format(ToaDoM[0]).replace(",", "."));
                dataKQ[i + 1][2] = String.valueOf(format.format(ToaDoM[1]).replace(",", "."));
                dataKQ[i + 1][3] = String.valueOf(0);
                GocMoc = GocMoc + Double.parseDouble(dataMangGoc[i+1][2].trim());

            }*/
            for (int i = 1; i < SoMong + 1; i++) {
                TableRow row = (TableRow) (((TableLayout) TableCaoDoChanCot)).getChildAt(i);
                TextView tv = (TextView) (((TableRow) row)).getChildAt(1);

                dataKQ[i][3] = tv.getText().toString();
            }
            /**SET TEXT ON ROW**/
            int ColumnCount = 4;
            int Rowcount = TableToaDoMong.getChildCount();
            for (int i = 1; i < Rowcount; i++) {
                TableRow row = (TableRow) (((TableLayout) TableToaDoMong)).getChildAt(i);
                row.setVisibility(View.GONE);
                //setDataTable(row,data,i,ColumnCount);
            }
            for (int i = 1; i < SoMong + 1; i++) {
                TableRow row = (TableRow) (((TableLayout) TableToaDoMong)).getChildAt(i);
                row.setVisibility(View.VISIBLE);
                setDataTable(row, dataKQ, i, ColumnCount);
            }
        }
        catch (Exception e) {}
    }
    public void TinhBangToaDo_Mstower(){
        DecimalFormat format = new DecimalFormat("0.00");
        //DecimalFormat format0000 = new DecimalFormat("0.0000");
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLECanhgocCanh.txt");
        String[][] dataMangGoc =DataforPath(file);
        String[][] dataKQTG = new String[SoMong+1][4];
        String[][] dataKQ = new String[SoMong+1][4];
        Double Goc = 0.0;
        Double GocMoc = 180.0;
        if (SoMong ==4)
        GocMoc = GocMoc + Double.parseDouble(dataMangGoc[1][2].trim())/2;
        for (int i = 0; i < SoMong; i++) {
            Double ToaDoX = Double.parseDouble(dataMangGoc[i + 1][1].trim());
            double[] ToaDoM = Xoay(ToaDoX, 0, -Goc);
            dataKQTG[i + 1][0] = "Móng M" + String.valueOf(i + 1);
            dataKQTG[i + 1][1] = String.valueOf(format.format(ToaDoM[0]).replace(",", "."));
            dataKQTG[i + 1][2] = String.valueOf(format.format(ToaDoM[1]).replace(",", "."));
            dataKQTG[i + 1][3] = String.valueOf(0);
            Goc = Goc + Double.parseDouble(dataMangGoc[i+1][2].trim());
        }
        for (int i = 0; i < SoMong; i++) {
            Double ToaDoX = Double.parseDouble(dataKQTG[i + 1][1].trim());
            Double ToaDoY = Double.parseDouble(dataKQTG[i + 1][2].trim());
            double[] ToaDoM = Xoay(ToaDoX, ToaDoY, GocMoc);
            dataKQ[i + 1][0] = "Móng M" + String.valueOf(i + 1);
            dataKQ[i + 1][1] = String.valueOf(format.format(ToaDoM[0]).replace(",", "."));
            dataKQ[i + 1][2] = String.valueOf(format.format(ToaDoM[1]).replace(",", "."));
            dataKQ[i + 1][3] = String.valueOf(0);
        }
        for (int i = 1; i < SoMong + 1; i++) {
            TableRow row = (TableRow) (((TableLayout) TableCaoDoChanCot)).getChildAt(i);
            TextView tv = (TextView) (((TableRow) row)).getChildAt(1);

            dataKQ[i][3] = tv.getText().toString();
        }
        /**SET TEXT ON ROW**/
        int ColumnCount = 4;
        int Rowcount = TableToaDoMong_Mstower.getChildCount();
        for (int i = 1; i < Rowcount; i++) {
            TableRow row = (TableRow) (((TableLayout) TableToaDoMong_Mstower)).getChildAt(i);
            row.setVisibility(View.GONE);
            //setDataTable(row,data,i,ColumnCount);
        }
        for (int i = 1; i < SoMong + 1; i++) {
            TableRow row = (TableRow) (((TableLayout) TableToaDoMong_Mstower)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row, dataKQ, i, ColumnCount);
        }
    }
    public double TinhGoc(double canha, double canhb, double canhc){
        double Goc = 0.0;
        Goc = Math.acos((((canha * canha) + ((canhb * canhb) - (canhc * canhc))) / (2 * (canha * canhb))));
        Goc = ( Goc * 180 ) / Math.PI;
        return Goc;
    }
    public double[] Xoay(double ToaDoX,double ToaDoY, double Goc){
        Goc = ( Goc *  Math.PI ) /180;
        double[] Diem1 = new double[2];
        Diem1[0] = ((ToaDoX * Math.cos(Goc))
                - (ToaDoY * Math.sin(Goc)));
        Diem1[1] = ((ToaDoX * Math.sin(Goc)) + (ToaDoY * Math.cos(Goc)));
        return Diem1;
    }
    public void SetButtonFACE(final TableLayout table, int rowItem, final int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        final ArrayList<TextView> arrTextView = new ArrayList<TextView>();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                for (int i=1;i<SoDot+1;i++)
                {
                    TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(i);
                    final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
                    arrTextView.add(textView);
                }
               DialogFACEdayco(arrTextView);

            }
        });

    }
    public void SetButtonXoa(TableLayout table, final int rowItem, int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormMain.this);
                builder.setTitle("Bạn có muốn xoá dữ liệu thiết bị này không?");
                // add the buttons
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        XoaDongMaTran(rowItem);
                    }
                });
                builder.setNegativeButton("Không", null);
                // create and show the alert dialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        });
    }
    public void XoaDongMaTran(int rowDelete){
        for (int i=rowDelete;i<SoAnten;i++)
        {
            for (int j=0;j<6;j++)
            {
                data_Table11[i][j] = data_Table11[i+1][j];
            }
        }

        SoAnten--;
        saveDataOnCacher(String.valueOf(SoAnten), "SoAnten");
        set_DataTable11();
        set_ViewTable11();
    }
    private void DialogFACEdayco(final ArrayList<TextView> arrTextView) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_menu_face_dayco);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        /**ÁNH XẠ**/
        final ImageView dhm = dialog.findViewById(R.id.dhm);
        final ImageView dlm = dialog.findViewById(R.id.dlm);
        final ImageView dm = dialog.findViewById(R.id.dm);
        final ImageView drm = dialog.findViewById(R.id.drm);
        final ImageView dmleft = dialog.findViewById(R.id.dmleft);
        final ImageView none = dialog.findViewById(R.id.none);

        /**Button**/
        dhm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                for (TextView tv:arrTextView)
                tv.setText("DMH");
                dialog.dismiss();
            }
        });
        dlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (TextView tv:arrTextView)
                    tv.setText("DLM");
                dialog.dismiss();
            }
        });

        dm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (TextView tv:arrTextView)
                    tv.setText("DM");
                dialog.dismiss();
            }
        });
        drm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (TextView tv:arrTextView)
                    tv.setText("DRM");
                dialog.dismiss();
            }
        });

        dmleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (TextView tv:arrTextView)
                    tv.setText("DM");
                dialog.dismiss();
            }
        });
        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (TextView tv:arrTextView)
                    tv.setText("");
                dialog.dismiss();
            }
        });
    }
    public String TRABANG (String dauvao,ArrayList<String> chuoivao) {
        String daura=null;
        for (int i=0;i< chuoivao.size();i++){
            if (chuoivao.get(i).contains(dauvao)){
                daura = chuoivao.get(i).split("@")[1];
                break;
            }
        }
        return daura;
    }
    public void sapxep(String[] chuoi){
        ArrayList<Student> ar = new ArrayList<Student>();
        for(int i=0;i<=lengthArray;i++)
        {
            String[] tt1 = chuoi[i].split("\\.",2);
            try
            {
                tt[i] = Integer.parseInt(tt1[0]);
                ar.add(new Student(tt[i], tt1[1]));
            }
            catch (Exception e){Toast.makeText(getApplicationContext(), "Hãy thêm số thứ tự đằng trước!!!", Toast.LENGTH_SHORT).show();}
        }

        Collections.sort(ar, new Sortbyroll());
        //System.out.println("\nSorted by rollno");
        for (int y=0; y<ar.size(); y++)
        //System.out.println(ar.get(y));
        {
            ArrayString[y] = ar.get(y).toString();
            System.out.println(ar.get(y));
        }
    }
    class Student{
        int rollno;
        String name;

        // Constructor
        public Student(int rollno, String name)
        {
            this.rollno = rollno;
            this.name = name;
        }

        // Used to print student details in main()
        public String toString()
        {
            return this.rollno + "." + this.name;
        }
    }
    class Sortbyroll implements Comparator<Student> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Student a, Student b)
        {
            return a.rollno - b.rollno;
        }
    }
    public void NextTo_CameraAPI2(int vitriHM,String tenChiTiet,String HienTrang){

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir, MaTram);
        if (mediaStorageDir.exists())
        {
            File[] files = mediaStorageDir.listFiles();
            int i = 0;
            for (File file : files) {
                if (file.getName().split("\\.").length !=1) {
                    ArrayString[i] = file.getName();
                    i++;
                }
            }
            lengthArray = i - 1;
            sapxep(ArrayString);
            mediaStorageDir = new File(mediaStorageDir, ArrayString[vitriHM]);
            ArrayList<String> listcongtac = new ArrayList<String>();
            File[] fileCT = mediaStorageDir.listFiles();
            for(File file:fileCT)
            {
                if (!file.getName().contains("Hình ảnh khác"))
                    listcongtac.add((file.getName()));
            }
            for(File file:fileCT)
            {
                if (file.getName().contains("Hình ảnh khác"))
                    listcongtac.add((file.getName()));
            }
            for (int j=0;j<listcongtac.size();j++)
            {
                duongdananh[j] = listcongtac.get(j);
            }
            Intent intent2 = new Intent(FormMain.this, Camera.class);
            intent2.putExtra("TenHM", ArrayString[vitriHM]);  // Truyền một String
            intent2.putExtra("TenTram", MaTram);  // Truyền một String
            intent2.putExtra("MangCT", duongdananh);  // Truyền một String
            intent2.putExtra("MangString", ArrayString);  // Truyền một String
            intent2.putExtra("SoLuong", String.valueOf(lengthArray));  // Truyền một String
            intent2.putExtra("Long",Logiest);  // Truyền một String
            intent2.putExtra("Lat", Latitude);  // Truyền một String
            intent2.putExtra("ViTri", LocalCity);  // Truyền một String

                int vitriCongTac = listcongtac.size()-1;
                if (vitriHM==8) if (tenChiTiet.contains("ulong neo")) vitriCongTac = indexContain(listcongtac,"bu lông neo");
                if (vitriHM==8) if (tenChiTiet.contains("óng M0")) vitriCongTac = indexContain(listcongtac,"móng cột");
                if (vitriHM==8 && tenChiTiet.contains("M1"))  if (tenChiTiet.contains("ăng đơ"))  vitriCongTac = indexContain(listcongtac,"ăng đơ");
                if (vitriHM==8 && tenChiTiet.contains("M1")) if (tenChiTiet.contains("a ní")) vitriCongTac = indexContain(listcongtac,"ma ní");
                if (vitriHM==8 && tenChiTiet.contains("Tầng dây 1")) if (tenChiTiet.contains("a ní")) vitriCongTac = indexContain(listcongtac,"ma ní");
                if (vitriHM==8 && tenChiTiet.contains("M1")) if (tenChiTiet.contains("óc co")) vitriCongTac = indexContain(listcongtac,"móc co");
                if (vitriHM==8 && tenChiTiet.contains("M1")) if (tenChiTiet.contains("hoá cáp")) vitriCongTac = indexContain(listcongtac,"khoá cáp");
                if (vitriHM==8 && tenChiTiet.contains("Tầng dây 1")) if (tenChiTiet.contains("hoá cáp")) vitriCongTac = indexContain(listcongtac,"khoá cáp");
                if (vitriHM==8 && tenChiTiet.contains("M1")) if (tenChiTiet.contains("ây co")) vitriCongTac = indexContain(listcongtac,"dây co");
                if (vitriHM==8 && tenChiTiet.contains("D1") && tenChiTiet.contains("cánh"))
                    vitriCongTac = indexContain(listcongtac,"thanh cánh");
                if (vitriHM==8 && tenChiTiet.contains("D1") && tenChiTiet.contains("giằng"))
                    vitriCongTac = indexContain(listcongtac,"thanh giằng");
                if (vitriHM==6) if (tenChiTiet.contains("0-1")) vitriCongTac = indexContain(listcongtac,"chuẩn bị");
                if (vitriHM==5) if (tenChiTiet.contains("đo 1")) vitriCongTac = indexContain(listcongtac,"chuẩn bị");
                if (vitriHM==4) if (tenChiTiet.contains("M0")) vitriCongTac = indexContain(listcongtac,"chuẩn bị");
                if (vitriHM==3) if (tenChiTiet.contains("M1")) vitriCongTac = indexContain(listcongtac,"chuẩn bị");
                if (vitriHM==2) if (tenChiTiet.contains("M1")) vitriCongTac = indexContain(listcongtac,"chuẩn bị");

            intent2.putExtra("TenCongTac", duongdananh[vitriCongTac]);  // Truyền một String

            try{intent2.putExtra("TenChiTiet", tenChiTiet);  // Truyền một String
            }catch (Exception e) {intent2.putExtra("TenChiTiet", "");}
            try{intent2.putExtra("HienTrang", HienTrang);  // Truyền một String
            }catch (Exception e) {intent2.putExtra("HienTrang", "");}
            startActivity(intent2);
        }


    }
    public int indexContain(ArrayList<String> arrayList, String key){
        int vitri =0;
        for (int i= 0;i<arrayList.size();i++)
        {
            if (arrayList.get(i).contains(key))
            {
                vitri = i;
                break;
            }
        }
        return vitri;
    }
    //endregion
}
