package com.example.nhapdulieudodac;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class FormMain_CotTuDung extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener{
    //region KHỞI TẠO BIẾN
    Dialog dialog;
    ViewGroup viewGroup,viewGroupMain;
    /**
     * CHỤP ẢNH
     */
    ImageButton btnChupHienTrang,btnChupCuongDo,btnLocHienTrang;
    /**
     * IMAGEBUTTON
     */
    ImageButton btnCopyTTX,btnThoat,btnBackMong,btnSearch,btnSearchDX;
    //Dialog_TTC
    ImageButton btnBack,btnNext,btnBackHienTrang,btnNextHienTrang,btnBackDX,btnNextDX;
    /**
     * BUTTON
     */
    Button btnChonMong,btnThongTinChung,btnCopy,btnPaste;
    //Dialog_Main
    Button btnDuLieuDoMong,btnDulieuDam,btnMstower,btnThietBiMatDat,btnDoDienTro,btnKiemTraKheHo;
    Button btnCauTrucCot,btnThietBiTrenCot;
    Button btnVong0,btnVong1,btnVong2,btnVong3,btnKetQuaDoNghieng;
    ScrollView layoutTable2,layoutTable3;
    //Dialog
    Button btnLuu,btnLuuMong,btnLuuCuongDo;
    //Dialog_TTC
    Button btnLuuTTC;

    /**
     * TEXTVIEW
     */
    //Dialog
    TextView tvTenMong,tvGiaTriTrungBinh,tvDiaDiemTTC,tvViTriHienTrang,edtGocBan,tvViTriDeXuat;
    //Dialog_TTC
    TextView tvViTriDatTTC,tvSoMongTTC,tvSoTangDayTTC,tvChieuCaoDotTTC,tvDoCaoTTC,tvLoaiCotTTC,edtLoaiMay,edtDanhGia;
    //Dialog_Đo nghiêng
    TextView X1,X2,X3,Y1,Y2,Y3,H1,H2,H3,Dx1,Dx2,Dx3,Dy1,Dy2,Dy3,e1,e2,e3,Dl1,Dl2,Dl3,KL1,Kl2,KL3;

    /**
     * EDITTEXXT
     */
    //Dialog
    AutoCompleteTextView edtKichThuoc,edtHienTrang,edtDeXuat,edtThanhGiang,edtThanhCanh;
    EditText edtGiaTriDo,edtLanDo;
    //Dialog_TTC
    EditText tvSoChanCotTTC,tvMaTramTTC,tvKichThuocCotTTC,tvSoDotTTC,tvKichThuocChanCotTTC,tvKichThuocDinhCotTTC;

    //Dialog_Anten
    AutoCompleteTextView edtCaoTrinh,edtSoLuong,edtLoaiAnten,edtKichThuocAnten,edtTrongLuong;
    //Dialog_đo nghiêng
    EditText GT_X,GT_Y,GT_Z;
    EditText tbBiaMacBeTong;
    /**
     * STRING
     */
    String MaTram, LocalCity, Logiest, Latitude,ViTriDat,KichThuocMong,DeXuatMong,KichThuocCot,DanhGiaHienTrang,GiaChongXoay,MacBeTong;
    String textCopy="",textPaste="",KichThuocChanCot,KichThuocDinhCot,textDX,textHT;
    String DeXuatBeTong,DeXuatThep,HienTrangBeTong,HienTrangThep;

    String[][] data_Table2;
    String[][] data_Table3;
    String[][] data_Table4;
    String[][] data_Table5;
    String[][] data_Table6;

    String[][] data_Table10;
    String[][] data_Table11;
    String[][] data_Table12;
    String[][] data_Table12_V0;
    String[][] data_Table12_V1;
    String[][] data_Table12_V2;
    String[][] data_Table12_V3;
    /**
     * LinearLayout
     */
    //Main
    LinearLayout btnHienBangMong,btnHienBangCuongDo,btnMoThongTinChung,btnMoDoMong,btnMoTiepDia,btnMoThanCot,btnMoDoNghieng,btnMoNoiNghiep,btnMoNgoaiNghiep;
    LinearLayout layoutDoMong,layoutTiepDia,layoutThanCot,layoutDoNghieng;
    //Dialog
    LinearLayout lauoutKichThuoc,lauoutCuongDo,lauoutBang,btnlauoutKichThuoc,btnlauoutCuongDo,btnlauoutBang,layoutNoiNghiep,layoutNgoaiNghiep;


    /**
     * INTEGER
     */
    int lengthArray,SoMong,SoDot,SoTangDay,SoCot,lando=1,vitriMong,vitriDam,vitriHienTrang=1,SoGaChongXoay,SoAnten,vitriDeXuat=1;
    /**
     * TABLELAYOUT
     */
    TableLayout Table12_V3,Table12_V2,Table12_V1,Table12_V0,Table11,Table10,Table6,Table5,Table4,Table3,Table2,TableMstower,TableBiaTTC;

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
    //Dam
    ArrayList<String> listDam = new ArrayList<String>();

    //TiepDia
    ArrayList<String> listCauKienTiepDia = new ArrayList<String>();
    ArrayList<String> listLanDoDienTro= new ArrayList<String>();
    //ThanCot
    ArrayList<String> listThanCot = new ArrayList<String>();
    //PhuKienCot
    ArrayList<String> listPhuKienCot = new ArrayList<String>();


    //Dialog_Cấu kiện tiếp địa
    ArrayList<String> ArraylistHT = new ArrayList<String>();
    ArrayList<String> ArraylistKT = new ArrayList<String>();
    ArrayList<String> ArraylistDX = new ArrayList<String>();
    ArrayList<EditText> listEdtAnten = new ArrayList<EditText>();
    //DoNghieng
    ArrayList<String> listDoNghieng_V0 = new ArrayList<String>();
    ArrayList<String> listDoNghieng_V1 = new ArrayList<String>();
    ArrayList<String> listDoNghieng_V2 = new ArrayList<String>();
    ArrayList<String> listDoNghieng_V3 = new ArrayList<String>();

    ArrayList<String> listHT = new ArrayList<String>();
    ArrayList<String> listDitatMuc10 = new ArrayList<String>();
    ArrayList<String> listGCX = new ArrayList<String>();
    //Dialog_TTC
    ArrayList<String> listLoaiCot = new ArrayList<String>();
    ArrayList<String> listLienKetCot = new ArrayList<String>();
    ArrayList<String> listViTriDat = new ArrayList<String>();
    ArrayList<String> listcapdobenbt = new ArrayList<String>();
    ArrayList<String> listkichthuocchancot = new ArrayList<String>();
    ArrayList<String> listkichthuocdinhcot = new ArrayList<String>();

    /**
     * HIỆN TRẠNG
     */
    private Listview_HT_Adapter_TuDung listview_ht_adapter;
    private List<HienTrang> productList;
    private ListView listView;
    private EditText editText;
    private Button button;
    private ArrayList<String> myList = new ArrayList<String>();
    private ArrayList<String> listLoaiMayDo = new ArrayList<String>();

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_main__cot_tu_dung);
        /**
         * Sửa một số lỗi nhỏ:
         * -Chưa có chiều cao trong bảng toạ độ móng
         * -Chưa tính modun cho loại đốt DLM
         */
        AnhXa_Main();
        NhanBienTruyen();
        click_Main();
        if (SoDot==0) DialogThongTinChung();
        //region MÓNG
        for (int i=1 ;i<=SoMong;i++)
        {
            listMong.add("Móng M" + String.valueOf(i));
        }
        listDam.add("Bulong neo");
        listLoaiMayDo.addAll(Arrays.asList("C380","HT-75","HTH225"));
        //endregion

        //region THÂN CỘT
        for (int i=0;i<SoDot;i++)
        {
            listThanCot.add("Đốt D" + String.valueOf(i+1));
        }

        //endregion
        //region LIÊN KẾT THÂN CỘT
        for (int i=0;i<SoDot-1;i++)
        {
            listLienKetCot.add("Đốt D" + String.valueOf(i+1) + "-" + String.valueOf(i+2));
        }
        //endregion
        //region Tiếp Địa
        listCauKienTiepDia.addAll(Arrays.asList("Thoát sét cho kim thu sét","Thoát sét cho thiết bị treo trên cột","Thoát sét cho chân cột","Thoát sét cho phòng máy","Thoát sét cho máy nổ"));
        listLanDoDienTro.addAll(Arrays.asList("Lần đo 1","Lần đo 2","Lần đo 3"));
        //endregion

        for (int i=0;i<SoCot;i++)
        {
            listDoNghieng_V0.add("Toạ độ vòng 0-" +String.valueOf(i+1));
            listDoNghieng_V1.add("Toạ độ vòng 1-" +String.valueOf(i+1));
            listDoNghieng_V2.add("Toạ độ vòng 2-" +String.valueOf(i+1));
            listDoNghieng_V3.add("Toạ độ vòng 3-" +String.valueOf(i+1));
        }
    }
    /**
     * ÁNH XẠ
     */
    //region ÁNH XẠ
    public void AnhXa_Main(){
        viewGroupMain = findViewById(R.id.viewgroupmain);

        btnChonMong = findViewById(R.id.btnDuLieuDoMong);
        btnThongTinChung = findViewById(R.id.btnLuuThongTinChung);btnThongTinChung.setVisibility(View.GONE);
        btnDulieuDam = findViewById(R.id.btnDuLieuDam);
        btnDuLieuDoMong = findViewById(R.id.btnDuLieuDoMong);

        btnMstower = findViewById(R.id.btnMstower);
        btnThietBiMatDat = findViewById(R.id.btnThietBiMatDat);
        btnDoDienTro = findViewById(R.id.btnDoDienTro);
        btnCauTrucCot = findViewById(R.id.btnCauTrucCot);
        btnKiemTraKheHo = findViewById(R.id.btnKiemTraKheHo);

        btnThietBiTrenCot = findViewById(R.id.btnThietBiTrenCot);
        btnVong0 = findViewById(R.id.btnVong0);
        btnVong1 = findViewById(R.id.btnVong1);
        btnVong2 = findViewById(R.id.btnVong2);
        btnVong3 = findViewById(R.id.btnVong3);
        btnKetQuaDoNghieng= findViewById(R.id.btnKetQuaDoNghieng);

        btnMoThongTinChung = findViewById(R.id.btnMoThongTinChung);
        btnMoDoMong = findViewById(R.id.btnMoDoMong);
        btnMoTiepDia = findViewById(R.id.btnMoTiepDia);
        btnMoThanCot = findViewById(R.id.btnMoThanCot);
        btnMoDoNghieng = findViewById(R.id.btnMoDoNghieng);
        btnMoNoiNghiep = findViewById(R.id.btnMoNoiNghiep);
        btnMoNgoaiNghiep = findViewById(R.id.btnMoNgoaiNghiep);

        layoutDoMong = findViewById(R.id.layoutDoMong);layoutDoMong.setVisibility(View.GONE);
        layoutTiepDia = findViewById(R.id.layoutTiepDia);layoutTiepDia.setVisibility(View.GONE);
        layoutThanCot = findViewById(R.id.layoutThanCot);layoutThanCot.setVisibility(View.GONE);
        layoutDoNghieng = findViewById(R.id.layoutDoNghieng);layoutDoNghieng.setVisibility(View.GONE);
        layoutNoiNghiep = findViewById(R.id.layoutNoiNghiep);layoutNoiNghiep.setVisibility(View.GONE);
        layoutNgoaiNghiep = findViewById(R.id.layoutNgoaiNghiep);layoutNgoaiNghiep.setVisibility(View.GONE);
    }
    public void AnhXa_DialogTTC(Dialog dialog){

        /**ÁNH XẠ**/
        tvViTriDatTTC = (TextView) dialog.findViewById(R.id.tbBiaViTri);
        tvChieuCaoDotTTC = (EditText) dialog.findViewById(R.id.tbBiaChieuCaoDot);
        tvDoCaoTTC = (EditText) dialog.findViewById(R.id.tbBiaDoCao);
        tvMaTramTTC = (EditText) dialog.findViewById(R.id.tbBiaMaTram);
        tvDiaDiemTTC = (EditText) dialog.findViewById(R.id.tbBiaDiaDiem);
        tvLoaiCotTTC = (TextView) dialog.findViewById(R.id.tbBiaLoaiCot);
        tvSoDotTTC = (EditText) dialog.findViewById(R.id.tbBiaSoDot);
        tbBiaMacBeTong = (EditText) dialog.findViewById(R.id.tbBiaMacBeTong);
        tvKichThuocChanCotTTC = (EditText) dialog.findViewById(R.id.tbBiaKichThuocChanCot);
        tvKichThuocDinhCotTTC = (EditText) dialog.findViewById(R.id.tbBiaKichThuocDinhCot);
        btnLuu = (Button) dialog.findViewById(R.id.btnLuuThongTinChung);
        TableBiaTTC = (TableLayout) dialog.findViewById(R.id.TableBia);
        btnCopyTTX = (ImageButton) dialog.findViewById(R.id.btnCopy);
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);

    }
    public void AnhXa_Dialogmong(Dialog dialog){
        btnLocHienTrang = dialog.findViewById(R.id.btnLocHienTrang);

        layoutTable3 = dialog.findViewById(R.id.layoutTable3);layoutTable3.setVisibility(View.GONE);
        layoutTable2 = dialog.findViewById(R.id.layoutTable2);layoutTable2.setVisibility(View.GONE);

        Table3 = dialog.findViewById(R.id.Table3);
        Table2 = dialog.findViewById(R.id.Table2);

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
        btnBack = dialog.findViewById(R.id.btnBack);
        btnNext = dialog.findViewById(R.id.btnNext);
        btnBackHienTrang = dialog.findViewById(R.id.btnBackHienTrang);
        btnNextHienTrang = dialog.findViewById(R.id.btnNextHienTrang);
        btnBackMong  = dialog.findViewById(R.id.btnBackMong );
        btnCopy = dialog.findViewById(R.id.btnCopy);


        btnPaste = dialog.findViewById(R.id.btnPaste);btnPaste.setVisibility(View.GONE);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocMong);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutCuongDo = dialog.findViewById(R.id.layoutDoCuongDo);lauoutCuongDo.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocMong);
        btnlauoutCuongDo = dialog.findViewById(R.id.btnHienDoCuongDo);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
        btnHienBangMong = dialog.findViewById(R.id.btnHienBangMong);
        btnHienBangCuongDo = dialog.findViewById(R.id.btnHienBangCuongDo);
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);
        btnChupCuongDo = dialog.findViewById(R.id.btnChupCuongDo);

        tvViTriHienTrang = dialog.findViewById((R.id.tvViTriHienTrang));
        tvViTriDeXuat= dialog.findViewById((R.id.tvViTriDeXuat));
        btnSearch = dialog.findViewById((R.id.btnSearch));
        btnSearchDX = dialog.findViewById((R.id.btnSearchDX));

        btnBackDX = dialog.findViewById(R.id.btnBackDX);
        btnNextDX = dialog.findViewById(R.id.btnNextDX);

    }
    public void AnhXa_Dialogdam(Dialog dialog){
        btnLocHienTrang = dialog.findViewById(R.id.btnLocHienTrang);

        layoutTable2 = dialog.findViewById(R.id.layoutTable2);layoutTable2.setVisibility(View.GONE);

        Table2 = dialog.findViewById(R.id.Table2);

        viewGroup =  dialog.findViewById(R.id.viewgroup);

        tvTenMong = dialog.findViewById(R.id.tvTenMong);

        edtKichThuoc = dialog.findViewById(R.id.edtKichThuocMong);
        edtHienTrang = dialog.findViewById(R.id.edtDanhGiaMong);
        edtDeXuat= dialog.findViewById(R.id.edtDeXuatMong);

        btnLuuMong = dialog.findViewById(R.id.btnDuLieuDoMong);
        btnBackHienTrang = dialog.findViewById(R.id.btnBackHienTrang);
        btnNextHienTrang = dialog.findViewById(R.id.btnNextHienTrang);
        btnBackMong  = dialog.findViewById(R.id.btnBackMong );
        btnCopy = dialog.findViewById(R.id.btnCopy);

        btnPaste = dialog.findViewById(R.id.btnPaste);btnPaste.setVisibility(View.GONE);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocMong);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocMong);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
        btnHienBangMong = dialog.findViewById(R.id.btnHienBangMong);
        tvViTriHienTrang = dialog.findViewById((R.id.tvViTriHienTrang));
        tvViTriDeXuat= dialog.findViewById((R.id.tvViTriDeXuat));
        btnSearch = dialog.findViewById((R.id.btnSearch));
        btnSearchDX = dialog.findViewById((R.id.btnSearchDX));
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);

        btnBackDX = dialog.findViewById(R.id.btnBackDX);
        btnNextDX = dialog.findViewById(R.id.btnNextDX);

    }
    public void AnhXa_DialogTiepDia(Dialog dialog){
        btnLocHienTrang = dialog.findViewById(R.id.btnLocHienTrang);

        tvTenMong = dialog.findViewById(R.id.tvTenCauKien);
        viewGroup =  dialog.findViewById(R.id.viewgroup);

        edtKichThuoc = dialog.findViewById(R.id.edtKichThuocCauKien);
        edtHienTrang = dialog.findViewById(R.id.edtDanhGiaCauKien);
        edtDeXuat= dialog.findViewById(R.id.edtDeXuatCauKien);

        btnLuu = dialog.findViewById(R.id.btnDuLieuDoCauKien);
        btnBackMong = dialog.findViewById(R.id.btnBackCauKien);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocCauKien);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);
        btnBackHienTrang = dialog.findViewById(R.id.btnBackHienTrang);
        btnNextHienTrang = dialog.findViewById(R.id.btnNextHienTrang);
        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocCauKien);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
        tvViTriHienTrang = dialog.findViewById((R.id.tvViTriHienTrang));
        tvViTriDeXuat= dialog.findViewById((R.id.tvViTriDeXuat));
        btnSearch = dialog.findViewById((R.id.btnSearch));
        btnSearchDX = dialog.findViewById((R.id.btnSearchDX));
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);
        btnBackDX = dialog.findViewById(R.id.btnBackDX);
        btnNextDX = dialog.findViewById(R.id.btnNextDX);
    }
    public void AnhXa_DialogKheHo(Dialog dialog){
        btnLocHienTrang = dialog.findViewById(R.id.btnLocHienTrang);

        tvTenMong = dialog.findViewById(R.id.tvTenThanCot);
        viewGroup =  dialog.findViewById(R.id.viewgroup);

        edtKichThuoc = dialog.findViewById(R.id.edtDuongKinhBulong);
        edtThanhCanh = dialog.findViewById(R.id.edtKhoangCachKheHo);
        edtHienTrang = dialog.findViewById(R.id.edtDanhGiaThanCot);
        edtDeXuat= dialog.findViewById(R.id.edtDeXuatThanCot);
        btnCopy = dialog.findViewById(R.id.btnCopy);
        btnPaste = dialog.findViewById(R.id.btnPaste);btnPaste.setVisibility(View.GONE);
        btnLuu = dialog.findViewById(R.id.btnDuLieuDoThanCot);
        btnBackMong = dialog.findViewById(R.id.btnBackThanCot);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocThanCot);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);
        btnBackHienTrang = dialog.findViewById(R.id.btnBackHienTrang);
        btnNextHienTrang = dialog.findViewById(R.id.btnNextHienTrang);
        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocThanCot);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
        tvViTriHienTrang = dialog.findViewById((R.id.tvViTriHienTrang));
        tvViTriDeXuat= dialog.findViewById((R.id.tvViTriDeXuat));
        btnSearch = dialog.findViewById((R.id.btnSearch));
        btnSearchDX = dialog.findViewById((R.id.btnSearchDX));
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);

        btnBackDX = dialog.findViewById(R.id.btnBackDX);
        btnNextDX = dialog.findViewById(R.id.btnNextDX);
    }
    public void AnhXa_DialogThanCot(Dialog dialog){
        btnLocHienTrang = dialog.findViewById(R.id.btnLocHienTrang);

        tvTenMong = dialog.findViewById(R.id.tvTenThanCot);
        viewGroup =  dialog.findViewById(R.id.viewgroup);

        edtKichThuoc = dialog.findViewById(R.id.edtKichThuocThanCot);
        edtHienTrang = dialog.findViewById(R.id.edtDanhGiaThanCot);
        edtDeXuat= dialog.findViewById(R.id.edtDeXuatThanCot);
        edtThanhCanh= dialog.findViewById(R.id.edtThanhCanhThanCot);
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

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocThanCot);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
        tvViTriHienTrang = dialog.findViewById((R.id.tvViTriHienTrang));
        tvViTriDeXuat= dialog.findViewById((R.id.tvViTriDeXuat));
        btnSearch = dialog.findViewById((R.id.btnSearch));
        btnSearchDX = dialog.findViewById((R.id.btnSearchDX));

        btnBackDX = dialog.findViewById(R.id.btnBackDX);
        btnNextDX = dialog.findViewById(R.id.btnNextDX);
    }
    public void AnhXa_DialogDienTro(Dialog dialog){
        tvTenMong = dialog.findViewById(R.id.tvLanDo);
        viewGroup =  dialog.findViewById(R.id.viewgroup);

        edtKichThuoc = dialog.findViewById(R.id.edtDienTro);
        edtDeXuat= dialog.findViewById(R.id.edtGhiChu);
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);


        btnLuu = dialog.findViewById(R.id.btnDuLieuDoThanCot);
        btnBackMong = dialog.findViewById(R.id.btnBackThanCot);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocThanCot);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocThanCot);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
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
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocMong);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocMong);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
    }
    public void AnhXa_DialogKetQuaDoNghieng(Dialog dialog){

        btnLuu = dialog.findViewById(R.id.btnTinhNghieng);
        btnBackMong = dialog.findViewById(R.id.btnBackMong);
        lauoutKichThuoc = dialog.findViewById(R.id.layoutKichThuocMong);lauoutKichThuoc.setVisibility(View.GONE);
        lauoutBang = dialog.findViewById(R.id.layoutXemDangBang);lauoutBang.setVisibility(View.GONE);
        viewGroup =  dialog.findViewById(R.id.viewgroup);

        btnlauoutKichThuoc = dialog.findViewById(R.id.btnHienKichThuocMong);
        btnlauoutBang = dialog.findViewById(R.id.btnHienBang);
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (layoutNoiNghiep.getVisibility() == View.GONE)
                {layoutNoiNghiep.setVisibility(View.VISIBLE);layoutNgoaiNghiep.setVisibility(View.GONE);btnThongTinChung.setVisibility(View.GONE);}
                else layoutNoiNghiep.setVisibility(View.GONE);
            }
        });
        btnMoNgoaiNghiep.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (layoutNgoaiNghiep.getVisibility() == View.GONE)
                {layoutNgoaiNghiep.setVisibility(View.VISIBLE);layoutNoiNghiep.setVisibility(View.GONE);btnThongTinChung.setVisibility(View.GONE);}
                else layoutNgoaiNghiep.setVisibility(View.GONE);
            }
        });
        //region THÔNG TIN CHUNG
        btnThongTinChung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThongTinChung();
            }
        });
        btnKiemTraKheHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, btnKiemTraKheHo);
                for (String s : listLienKetCot)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listLienKetCot.indexOf(menuItem.getTitle().toString());
                        //Toast.makeText(getApplicationContext(),String.valueOf(index), Toast.LENGTH_SHORT).show();
                        DialogKheHo(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnMoThongTinChung.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (btnThongTinChung.getVisibility() == View.GONE)
                {btnThongTinChung.setVisibility(View.VISIBLE);layoutDoMong.setVisibility(View.GONE);
                    layoutTiepDia.setVisibility(View.GONE);layoutThanCot.setVisibility(View.GONE);layoutDoNghieng.setVisibility(View.GONE);}
                else btnThongTinChung.setVisibility(View.GONE);
            }
        });
        //endregion
        //region ĐO DẦM, MONG, BULONG
        btnDuLieuDoMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, btnChonMong);
                for (String s : listMong)
                { popupMenu.getMenu().add(s); }
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
                DialogDam(5);
            }
        });

        btnMoDoMong.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                    layoutDoMong.setVisibility(View.VISIBLE);btnThongTinChung.setVisibility(View.GONE);
                    layoutTiepDia.setVisibility(View.GONE);layoutThanCot.setVisibility(View.GONE);layoutDoNghieng.setVisibility(View.GONE);
                }

                else layoutDoMong.setVisibility(View.GONE);
            }
        });
        //endregion


        //endregion

        btnMstower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogMstower();
            }
        });
        //endregion
        //region HÊ THÔNG TIẾP ĐỊA
        btnMoTiepDia.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (layoutTiepDia.getVisibility() == View.GONE)
                {
                    layoutTiepDia.setVisibility(View.VISIBLE);layoutDoMong.setVisibility(View.GONE);btnThongTinChung.setVisibility(View.GONE);
                    layoutThanCot.setVisibility(View.GONE);layoutDoNghieng.setVisibility(View.GONE);
                }
                else layoutTiepDia.setVisibility(View.GONE);
            }
        });
        btnThietBiMatDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, btnThietBiMatDat);
                for (String s : listCauKienTiepDia)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listCauKienTiepDia.indexOf(menuItem.getTitle().toString());
                        //Toast.makeText(getApplicationContext(),String.valueOf(index), Toast.LENGTH_SHORT).show();
                        DialogTiepDia(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnDoDienTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, btnDoDienTro);
                for (String s : listLanDoDienTro)
                {popupMenu.getMenu().add(s);}
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)

            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (layoutThanCot.getVisibility() == View.GONE)
                {
                    layoutThanCot.setVisibility(View.VISIBLE);btnThongTinChung.setVisibility(View.GONE);
                    layoutTiepDia.setVisibility(View.GONE);layoutDoMong.setVisibility(View.GONE);layoutDoNghieng.setVisibility(View.GONE);
                }
                else layoutThanCot.setVisibility(View.GONE);
            }
        });
        btnCauTrucCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, btnCauTrucCot);
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

        btnThietBiTrenCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAnten();
            }
        });
        //endregion
        //region ĐO NGHIÊNG CỘT
        btnMoDoNghieng.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroupMain,autoTransition);
                if (layoutDoNghieng.getVisibility() == View.GONE)
                {layoutDoNghieng.setVisibility(View.VISIBLE);btnThongTinChung.setVisibility(View.GONE);layoutDoMong.setVisibility(View.GONE);
                    layoutTiepDia.setVisibility(View.GONE);layoutThanCot.setVisibility(View.GONE);}
                else layoutDoNghieng.setVisibility(View.GONE);
            }
        });
        btnVong0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, btnVong0);
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
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, btnVong1);
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
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, btnVong2);
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
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, btnVong3);
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
        /**GÁN BIẾN**/
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(0);
            }
        });
        tvMaTramTTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] tenTram = MaTram.split("_");
                tvDiaDiemTTC.setText(tenTram[1]);
                tvMaTramTTC.setText(tenTram[0]);
            }
        });
        btnCopyTTX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrayListTR = new ArrayList<String>();

                File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
                File[] files1=mediaStorageDir.listFiles();
                for (File f:files1)
                {
                    if(f.exists())
                    {
                        File[] files=f.listFiles();
                        if(files.length==8) arrayListTR.add(f.getName());
                    }
                }

                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, btnCopyTTX);
                for (int j=0 ;j<arrayListTR.size();j++) {
                    popupMenu.getMenu().add(arrayListTR.get(j));
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        File file1 = new File(Environment.getExternalStorageDirectory(),"DataViettel/Data"+item.getTitle());Log.d("1",file1.getPath());
                        File file2 = new File(Environment.getExternalStorageDirectory(),"DataViettel/Data"+MaTram);Log.d("2",file2.getPath());
                        UT.CopyFile(file1,file2,"TABLEBia.txt");
                        dialog.dismiss();
                        DialogThongTinChung();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvViTriDatTTC.getText().equals("")&&!tvSoDotTTC.getText().equals("")&&!tvKichThuocChanCotTTC.getText().equals("")&&!tvKichThuocDinhCotTTC.getText().equals(""))
                {

                    String dataTableBia = GetTableData(TableBiaTTC, 2);
                    saveDataOnCacher(dataTableBia, "TABLEBia");
                    Intent intent3 = new Intent(FormMain_CotTuDung.this, FormMain_CotTuDung.class);
                    intent3.putExtra("TenTram", MaTram);  // Truyền một String
                    intent3.putExtra("SoMong","4"); // Truyền một String
                    intent3.putExtra("SoDot", tvSoDotTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                    intent3.putExtra("MacBeTong", tbBiaMacBeTong.getText().toString().replace(" ", "").trim());  // Truyền một String
                    intent3.putExtra("ViTriDat", tvViTriDatTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                    intent3.putExtra("KichThuocMong", "0.5x0.5x0.58");  // Truyền một String
                    intent3.putExtra("KichThuocChanCot", tvKichThuocChanCotTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                    intent3.putExtra("KichThuocDinhCot", tvKichThuocDinhCotTTC.getText().toString().replace(" ", "").trim());  // Truyền một String
                    intent3.putExtra("SoChanCot", String.valueOf(4));  // Truyền một String
                    intent3.putExtra("Lat", Latitude);  // Truyền một String
                    intent3.putExtra("Long", Logiest);  // Truyền một String
                    intent3.putExtra("ViTri", LocalCity);  // Truyền một String
                    finish();
                    startActivity(intent3);
                }
                else Toast.makeText(getApplicationContext(),"Bạn chưa nhập đủ dữ liệu bảng", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void click_DialogMong(final int vitri){
        //region KÍCH THƯỚC MONG + BÊ TÔNG
        btnLocHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocHienTrangTuAnh(tvTenMong.getText().toString().trim());
            }
        });
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(6);
            }
        });
        btnChupCuongDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(2);
            }
        });
        edtGocBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] string = {"-90","0","90"};
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, edtGocBan);
                for (String s : string)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem)
                    {
                        edtGocBan.setText(menuItem.getTitle());
                        return false;
                    }
                });
                popupMenu.show();
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
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        edtLoaiMay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, edtLoaiMay);
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
        btnlauoutCuongDo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        btnHienBangMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutTable2.getVisibility() == View.GONE) {layoutTable2.setVisibility(View.VISIBLE);layoutTable3.setVisibility(View.GONE);}
                else layoutTable2.setVisibility(View.GONE);
            }
        });
        btnHienBangCuongDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutTable3.getVisibility() == View.GONE) {layoutTable3.setVisibility(View.VISIBLE);layoutTable2.setVisibility(View.GONE);}
                else layoutTable3.setVisibility(View.GONE);
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
                        } else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();

                    }else Toast.makeText(FormMain_CotTuDung.this, "Không được để trống dữ liệu!", Toast.LENGTH_LONG).show();
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
                            saveDataOnCacher(edtLoaiMay.getText().toString(),"LoaiMayDo");

                            set_DataTable3(vitri);
                            Toast.makeText(FormMain_CotTuDung.this, "Đã lưu!", Toast.LENGTH_LONG).show();
                        } else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();

                    }else Toast.makeText(FormMain_CotTuDung.this, "Không được để trống dữ liệu!", Toast.LENGTH_LONG).show();
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
                        saveDataOnCacher(edtLoaiMay.getText().toString(),"LoaiMayDo");
                        Toast.makeText(FormMain_CotTuDung.this, "Đã lưu!", Toast.LENGTH_LONG).show();

                }
            }
        });
        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, tvTenMong);
                for (String s : listMong)
                { popupMenu.getMenu().add(s); }
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
                                DialogMong(menuItem.getTitle().toString(),vitriMong);
                                //Toast.makeText(FormMain_CotTuDung.this, String.valueOf(chr), Toast.LENGTH_LONG).show();
                            }
                        }
                        //searchAutoCompleteCT.setText(menuItem.getTitle().toString());
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listHienTrang_copy.clear();
                listDeXuat_copy.clear();
                listHienTrang_copy.addAll(listHienTrang) ;
                listDeXuat_copy.addAll(listDeXuat) ;
                textCopy = edtKichThuoc.getText().toString() + "_" ;
                if (!textCopy.equals("")) btnPaste.setVisibility(View.VISIBLE);
                Toast.makeText(FormMain_CotTuDung.this, "Đã sao chép!", Toast.LENGTH_LONG).show();
            }
        });
        btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormMain_CotTuDung.this);
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
                LuuHienTrangBeTong();
                DialogHientrangCheckBox(edtHienTrang,"HienTrangBeTong");
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

                if (!KichThuocMong.contains(edtKichThuoc.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuoc.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUp();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }

            }
        });
        //endregion

        //region BUTTON LƯU
        btnLuuCuongDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutCuongDo.setVisibility(View.GONE);
                lauoutBang.setVisibility(View.GONE);
                saveDataOnCacher(edtLoaiMay.getText().toString(),"LoaiMayDo");

                lando = Integer.parseInt(edtLanDo.getText().toString());
                data_Table3[vitri][lando] = edtGiaTriDo.getText().toString().replace(",", ".").trim();
                data_Table3[vitri][17] = tvGiaTriTrungBinh.getText().toString();
                data_Table3[vitri][18] = edtGocBan.getText().toString();

                set_DataTable3(vitri);
            }
        });
        //endregion
    };
    public void click_DialogDam(final int vitri){
        btnLocHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocHienTrangTuAnh(tvTenMong.getText().toString().trim());
            }
        });
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(6);
            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        btnHienBangMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutTable2.getVisibility() == View.GONE) {layoutTable2.setVisibility(View.VISIBLE);}
                else layoutTable2.setVisibility(View.GONE);
            }
        });

        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutKichThuoc.getVisibility() == View.GONE)
                {lauoutKichThuoc.setVisibility(View.VISIBLE);}
                else lauoutKichThuoc.setVisibility(View.GONE);
            }
        });

        btnlauoutBang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });


        //region HIỆN TRẠNG VÀ KÍCH THƯỚC
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHienTrangBeTong();
                DialogHientrangCheckBox(edtHienTrang,"HienTrangBeTong");
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
                lauoutBang.setVisibility(View.GONE);

                LuuHienTrangBeTong();
                LuuDeXuatBeTong();

                data_Table2[vitri][1] = edtKichThuoc.getText().toString();
                data_Table2[vitri][2]= textHT;
                data_Table2[vitri][3]= textDX;

                set_DataTable2(vitri);

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
    public void click_DialogTiepDia(final int vitri){
        btnLocHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocHienTrangTuAnh(tvTenMong.getText().toString().trim());
            }
        });
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(3);
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
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

        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, tvTenMong);
                for (String s : listCauKienTiepDia)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        int index = listCauKienTiepDia.indexOf(menuItem.getTitle().toString());
                        //Toast.makeText(getApplicationContext(),String.valueOf(index), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        DialogTiepDia(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        //region HIỆN TRẠNG VÀ KÍCH THƯỚC
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHienTrangThep();
                DialogHientrangCheckBox(edtHienTrang,"HienTrangThep");
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

                    data_Table4[vitri][1] = edtKichThuoc.getText().toString();
                    data_Table4[vitri][2]= textHT;
                    data_Table4[vitri][3]= textDX;
                    set_DataTable4(vitri);
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

                        data_Table4[vitri][1] = edtKichThuoc.getText().toString();
                        data_Table4[vitri][2]= textHT;
                        data_Table4[vitri][3]= textDX;
                        set_DataTable4(vitri);}
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

                    data_Table4[vitri][1] = edtKichThuoc.getText().toString();
                    data_Table4[vitri][2]= textHT;
                    data_Table4[vitri][3]= textDX;
                    set_DataTable4(vitri);}catch (Exception e){}
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

                        data_Table4[vitri][1] = edtKichThuoc.getText().toString();
                        data_Table4[vitri][2]= textHT;
                        data_Table4[vitri][3]= textDX;
                        set_DataTable4(vitri);}
                }
                catch (Exception e){}
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutBang.setVisibility(View.GONE);

                LuuHienTrangThep();
                LuuDeXuatThep();

                data_Table4[vitri][1] = edtKichThuoc.getText().toString();
                data_Table4[vitri][2]= textHT;
                data_Table4[vitri][3]= textDX;
                set_DataTable4(vitri);

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
    public void click_DialogThanCot(final int vitri){
        btnLocHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocHienTrangTuAnh(tvTenMong.getText().toString().trim());
            }
        });
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(5);
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
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

        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, tvTenMong);
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

        //region HIỆN TRẠNG VÀ KÍCH THƯỚC
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHienTrangThep();
                DialogHientrangCheckBox(edtHienTrang,"HienTrangThep");
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

                    data_Table6[vitri][1]= textHT;
                    data_Table6[vitri][2]= textDX;
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

                        data_Table6[vitri][1]= textHT;
                        data_Table6[vitri][2]= textDX;
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

                    data_Table6[vitri][1]= textHT;
                    data_Table6[vitri][2]= textDX;
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

                        data_Table6[vitri][1]= textHT;
                        data_Table6[vitri][2]= textDX;
                        set_DataTable6(vitri);}
                }
                catch (Exception e){}
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutBang.setVisibility(View.GONE);

                LuuHienTrangThep();
                LuuDeXuatThep();

                data_Table6[vitri][1]= textHT;
                data_Table6[vitri][2]= textDX;
                set_DataTable6(vitri);

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
                btnPaste.setVisibility(View.VISIBLE);
                Toast.makeText(FormMain_CotTuDung.this, "Đã sao chép!", Toast.LENGTH_LONG).show();
            }
        });
        btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormMain_CotTuDung.this);
                builder.setTitle("Bạn có muốn dán dữ liệu từ cấu kiện trước?");
                // add the buttons
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //edtHienTrang.setText(text[1]);
                        listHienTrang.clear();
                        listDeXuat.clear();
                        listHienTrang.addAll(listHienTrang_copy);
                        listDeXuat.addAll(listDeXuat_copy);
                        edtHienTrang.setText(listHienTrang.get(1).replace("\n",""));
                        edtDeXuat.setText(listDeXuat.get(1).replace("\n",""));
                        //

                    }
                });
                builder.setNegativeButton("Không", null);
                // create and show the alert dialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        });
    };
    public void click_DialogDienTro(final int vitri){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(3);
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutBang.setVisibility(View.GONE);
                data_Table5[vitri][0] = edtKichThuoc.getText().toString();
                data_Table5[vitri][3]= edtDeXuat.getText().toString();
                set_DataTable5(vitri);

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
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, tvTenMong);
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
        btnLocHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocHienTrangTuAnh(tvTenMong.getText().toString().trim());
            }
        });
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(1);
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(viewGroup,autoTransition);
                if (lauoutBang.getVisibility() == View.GONE) lauoutBang.setVisibility(View.VISIBLE);
                else lauoutBang.setVisibility(View.GONE);
            }
        });
        //region HIỆN TRẠNG VÀ KÍCH THƯỚC
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LuuHienTrangThep();
                DialogHientrangCheckBox(edtHienTrang,"HienTrangThep");
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

                    data_Table10[vitri][1] = edtKichThuoc.getText().toString();
                    data_Table10[vitri][2] = edtThanhCanh.getText().toString();
                    data_Table10[vitri][3] = textHT;
                    data_Table10[vitri][4] = textDX;
                    set_DataTable10(vitri);
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

                        data_Table10[vitri][1] = edtKichThuoc.getText().toString();
                        data_Table10[vitri][2] = edtThanhCanh.getText().toString();
                        data_Table10[vitri][3] = textHT;
                        data_Table10[vitri][4] = textDX;
                        set_DataTable10(vitri);}
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

                    data_Table10[vitri][1] = edtKichThuoc.getText().toString();
                    data_Table10[vitri][2] = edtThanhCanh.getText().toString();
                    data_Table10[vitri][3] = textHT;
                    data_Table10[vitri][4] = textDX;
                    set_DataTable10(vitri);}catch (Exception e){}
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

                        data_Table10[vitri][1] = edtKichThuoc.getText().toString();
                        data_Table10[vitri][2] = edtThanhCanh.getText().toString();
                        data_Table10[vitri][3] = textHT;
                        data_Table10[vitri][4] = textDX;
                        set_DataTable10(vitri);}
                }
                catch (Exception e){}
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lauoutKichThuoc.setVisibility(View.GONE);
                lauoutBang.setVisibility(View.GONE);

                LuuHienTrangThep();
                LuuDeXuatThep();

                data_Table10[vitri][1] = edtKichThuoc.getText().toString();
                data_Table10[vitri][2] = edtThanhCanh.getText().toString();
                data_Table10[vitri][3] = textHT;
                data_Table10[vitri][4] = textDX;
                set_DataTable10(vitri);
                if (!KichThuocMong.contains(edtKichThuoc.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtKichThuoc.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUp();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }
                if (!KichThuocMong.contains(edtThanhCanh.getText().toString())){
                    KichThuocMong = KichThuocMong.replace("\n","") +"_"+edtThanhCanh.getText().toString()+"_";
                    saveDataOnTemplate(KichThuocMong,"KICHTHUOC");
                    setPopUpKheHo();
                    //Toast.makeText(FormMain.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                }
            }
        });
        //endregion

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, tvTenMong);
                for (String s : listLienKetCot)
                { popupMenu.getMenu().add(s); }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        dialog.dismiss();
                        int index = listLienKetCot.indexOf(menuItem.getTitle().toString());
                        DialogKheHo(menuItem.getTitle().toString(),index+1);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listHienTrang_copy.clear();
                listDeXuat_copy.clear();
                listHienTrang_copy.addAll(listHienTrang) ;
                listDeXuat_copy.addAll(listDeXuat) ;
                textCopy = edtKichThuoc.getText().toString() + "_" +  edtThanhCanh.getText().toString();
                if (!textCopy.equals("")) btnPaste.setVisibility(View.VISIBLE);
                Toast.makeText(FormMain_CotTuDung.this, "Đã sao chép!", Toast.LENGTH_LONG).show();
            }
        });
        btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormMain_CotTuDung.this);
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
                        //
                        edtThanhCanh.setText(text[1]);
                    }
                });
                builder.setNegativeButton("Không", null);
                // create and show the alert dialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        });
    };
    public void click_DialogAnten(){
        btnChupHienTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTo_CameraAPI2(6);
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                        KichThuocMong = KichThuocMong + "_" + edtLoaiAnten.getText().toString() + "_";
                        saveDataOnTemplate(KichThuocMong, "LOAIANTEN");
                        setPopUpAnten();
                        //Toast.makeText(FormMain_CotTuDung.this, "Đã tạo mới kích thước!", Toast.LENGTH_LONG).show();
                    }

                    for (int i = 0; i < 5; i++) {
                        listEdtAnten.get(i).setText("");
                    }
                    set_DataTable11();
                }
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
                NextTo_CameraAPI2(4);
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                        data_Table12_V0[vitri][1]=GT_X.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Y
                    if (isNumeric(String.valueOf(GT_Y.getText().toString().replace(",", ".").trim())))
                        data_Table12_V0[vitri][2]=GT_Y.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Z
                    if (isNumeric(String.valueOf(GT_Z.getText().toString().replace(",", ".").trim())))
                        data_Table12_V0[vitri][3]=GT_Z.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    set_DataTable12_V0(vitri);

                }

            }
        });
        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, tvTenMong);
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
                NextTo_CameraAPI2(4);
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                        data_Table12_V1[vitri][1]=GT_X.getText().toString();
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Y
                    if (isNumeric(String.valueOf(GT_Y.getText().toString().replace(",", ".").trim())))
                        data_Table12_V1[vitri][2]=GT_Y.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Z
                    if (isNumeric(String.valueOf(GT_Z.getText().toString().replace(",", ".").trim())))
                        data_Table12_V1[vitri][3]=GT_Z.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();

                    set_DataTable12_V1(vitri);
                }
            }
        });
        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, tvTenMong);
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
                NextTo_CameraAPI2(4);
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                        data_Table12_V2[vitri][1]=GT_X.getText().toString();
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Y
                    if (isNumeric(String.valueOf(GT_Y.getText().toString().replace(",", ".").trim())))
                        data_Table12_V2[vitri][2]=GT_Y.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Z
                    if (isNumeric(String.valueOf(GT_Z.getText().toString().replace(",", ".").trim())))
                        data_Table12_V2[vitri][3]=GT_Z.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    set_DataTable12_V2(vitri);
                }
            }
        });
        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, tvTenMong);
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
                NextTo_CameraAPI2(4);
            }
        });
        btnlauoutKichThuoc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Y
                    if (isNumeric(String.valueOf(GT_Y.getText().toString().replace(",", ".").trim())))
                        data_Table12_V3[vitri][2]=GT_Y.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();
                    //Toạ đô Z
                    if (isNumeric(String.valueOf(GT_Z.getText().toString().replace(",", ".").trim())))
                        data_Table12_V3[vitri][3]=GT_Z.getText().toString().replace(",", ".").trim();
                    else Toast.makeText(FormMain_CotTuDung.this, "Giá trị không được chứa ký tự chữ!", Toast.LENGTH_LONG).show();

                    set_DataTable12_V3(vitri);
                }
            }
        });
        tvTenMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this, tvTenMong);
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                TinhDoNghiengCot(dialog);
            }
        });

        btnBackMong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    };
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

    };

    /**
     * HIỆN TRẠNG VÀ ĐỀ XUẤT
     */
    public void LuuHienTrangBeTong()
    {
        listHienTrang.set(vitriHienTrang,edtHienTrang.getText().toString().replace("\n","").replace("- ",""));
        textHT = "";
        for (String s:listHienTrang)
        {
            if (!s.equals(""))
                if(!textHT.trim().equals(""))
                    textHT = textHT + "\n"+ "- " + s ;
                else textHT = textHT + "- " + s ;
        }
        if (!HienTrangBeTong.contains(edtHienTrang.getText().toString().replace("\n",""))){
            HienTrangBeTong = HienTrangBeTong.replace("\n","") +"@"+edtHienTrang.getText().toString().replace("\n","");
            saveDataOnTemplate(HienTrangBeTong,"HienTrangBeTong");
            setPopUpBeTong();
        }
    }
    public void LuuDeXuatBeTong()
    {
        listDeXuat.set(vitriDeXuat,edtDeXuat.getText().toString().replace("\n","").replace("- ",""));
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
    /**
     * HIỆN TRẠNG VÀ ĐỀ XUẤT
     */
    public void LuuHienTrangThep()
    {
        listHienTrang.set(vitriHienTrang,edtHienTrang.getText().toString().replace("\n","").replace("- ",""));
        textHT = "";
        for (String s:listHienTrang)
        {
            if (!s.equals(""))
                if(!textHT.trim().equals(""))
                    textHT = textHT + "\n"+ "- " + s ;
                else textHT = textHT + "- " + s ;
        }
        if (!HienTrangThep.contains(edtHienTrang.getText().toString().replace("\n",""))){
            HienTrangThep = HienTrangThep.replace("\n","") +"@"+edtHienTrang.getText().toString().replace("\n","");
            saveDataOnTemplate(HienTrangThep,"HienTrangThep");
            setPopUpThep();
        }
    }
    public void LuuDeXuatThep()
    {
        listDeXuat.set(vitriDeXuat,edtDeXuat.getText().toString().replace("\n","").replace("- ",""));
        textDX = "";
        for (String s:listDeXuat)
        {
            if (!s.equals(""))
                if(!textDX.trim().equals(""))
                    textDX= textDX + "\n"+ "- " + s ;
                else textDX = textDX + "- " + s ;
        }
        if (!DeXuatThep.contains(edtDeXuat.getText().toString())){
            DeXuatThep = DeXuatThep.replace("\n","") +"@"+edtDeXuat.getText().toString();
            saveDataOnTemplate(DeXuatThep,"DeXuatThep");
            setPopUpThep();
        }
    }
    //endregion
    /**
     * DIALOG
     */
    //region DIALOG
    public void DialogMong(final String tenmong,final int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_mong_tudung);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_Dialogmong(dialog);
        click_DialogMong(vitri);
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"LoaiMayDo.txt");
        if (file.exists())
        {
            String text = readText(file);
            edtLoaiMay.setText(text.trim());
        }
        lando = 1;
        vitriHienTrang=1;
        vitriDeXuat=1;
        set_ViewTable2(vitri);tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriDeXuat) + "/" + String.valueOf(listDeXuat.size()));

        set_ViewTable3(vitri);
        if (!textCopy.equals("")) btnPaste.setVisibility(View.VISIBLE);
        tvTenMong.setText(data_Table2[vitri][0]);
        vitriHienTrang=1;
        setPopUp();
        setPopUpBeTong();


    }
    public void DialogDam(int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_dam_tudung);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Table3 = dialog.findViewById(R.id.Table3);
        Table2 = dialog.findViewById(R.id.Table2);
        AnhXa_Dialogdam(dialog);
        click_DialogDam(vitri);
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        lando = 1;
        vitriHienTrang=1;
        vitriDeXuat=1;
        set_ViewTable2(vitri);tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriDeXuat) + "/" + String.valueOf(listDeXuat.size()));

        tvTenMong.setText(data_Table2[vitri][0]);


        setPopUp();
        setPopUpBeTong();
    }
    public void DialogTiepDia(final String tenmong,int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_caukientiepdia_tudung);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogTiepDia(dialog);
        click_DialogTiepDia(vitri);
        vitriHienTrang=1;vitriDeXuat=1;
        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table4 = dialog.findViewById(R.id.Table4);
        tvTenMong.setText(tenmong);
        set_ViewTable4(vitri);
        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
        setPopUpThep();setPopUp();
    }
    public void DialogThanCot(final String tenmong,int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_thancot_tudung);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogThanCot(dialog);
        click_DialogThanCot(vitri);
        btnPaste.setVisibility(View.VISIBLE);
        vitriHienTrang=1;vitriDeXuat=1;

        Table6 = dialog.findViewById(R.id.Table6);
        tvTenMong.setText(tenmong);
        set_ViewTable6(vitri);
        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
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
    public void DialogKheHo(final String tenmong,int vitri) {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_kheho_tudung);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogKheHo(dialog);
        click_DialogKheHo(vitri);
        if (!textCopy.equals("")) btnPaste.setVisibility(View.VISIBLE);

        /**
         * CÀI ĐẶT HIỂN THỊ DỮ LIỆU
         */
        Table10 = dialog.findViewById(R.id.Table10);
        tvTenMong.setText(tenmong);
        set_ViewTable10(vitri);
        tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
        tvViTriDeXuat.setText("Đề xuất: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
        setPopUpKheHo();
        setPopUpThep();
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

    public void DialogMstower() {
        dialog = new Dialog(this,R.style.PauseDialog2);
        dialog.setContentView(R.layout.dialog_nhapdulieu_mstower_tudung);
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
            setDataTableMstower();
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
        /**DÒNG ĐỐT 1*/
        int ColumnCount =9;
        int Rowcount = TableMstower.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.GONE);
            setDataTable(row,data,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
        }

        for(int i=1;i<SoDot +1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) TableMstower)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data,i,ColumnCount);
            TextView tv = (TextView) (((TableRow) row)).getChildAt(0);
            tv.setText("Đốt " + String.valueOf(SoDot+1-i));
            SetButtonFACE(TableMstower,i,4);
            SetButtonPLAN(TableMstower,i,5);
            SetButtonHIP(TableMstower,i,6);
        }

    }

    private void DialogFACE(final TextView ChieuCaoDot,final TextView KichThuocDay,final TextView tv,final TextView SoMoDun,final TextView ChieuCaoMoDun) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_menu_face);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        /**ÁNH XẠ**/
        final ImageView xma = dialog.findViewById(R.id.dhm);
        final ImageView xma_m = dialog.findViewById(R.id.hx2);
        final ImageView dlm = dialog.findViewById(R.id.dlm);
        final ImageView k1_m1 = dialog.findViewById(R.id.drm);
        final ImageView k2 = dialog.findViewById(R.id.k2);
        final ImageView none = dialog.findViewById(R.id.none);

        /**Button**/
        xma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("XMA");
                DecimalFormat format = new DecimalFormat("0.00");
                DecimalFormat format0 = new DecimalFormat("0");
                Double chieucaodot=  Double.parseDouble(ChieuCaoDot.getText().toString());
                Double kichthuocday=  Double.parseDouble(KichThuocDay.getText().toString());
                Double somodun=   chieucaodot/kichthuocday -1;
                Double chieucaomodun= chieucaodot/somodun ;
                SoMoDun.setText(format0.format(somodun).replace(",","."));
                ChieuCaoMoDun.setText(format.format(chieucaomodun).replace(",","."));
                dialog.dismiss();
            }
        });
        xma_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("XMA,M");
                DecimalFormat format = new DecimalFormat("0.00");
                DecimalFormat format0 = new DecimalFormat("0");
                Double chieucaodot=  Double.parseDouble(ChieuCaoDot.getText().toString());
                Double kichthuocday=  Double.parseDouble(KichThuocDay.getText().toString());
                Double somodun=   chieucaodot/kichthuocday -1;
                Double chieucaomodun= chieucaodot/somodun ;
                SoMoDun.setText(format0.format(somodun).replace(",","."));
                ChieuCaoMoDun.setText(format.format(chieucaomodun).replace(",","."));
                dialog.dismiss();
            }
        });
        dlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("DLM");
                DecimalFormat format = new DecimalFormat("0.00");
                DecimalFormat format0 = new DecimalFormat("0");
                Double chieucaodot=  Double.parseDouble(ChieuCaoDot.getText().toString());
                Double kichthuocday=  Double.parseDouble(KichThuocDay.getText().toString());
                Double somodun=   chieucaodot/kichthuocday -1;
                Double chieucaomodun= chieucaodot/somodun ;
                SoMoDun.setText(format0.format(somodun).replace(",","."));
                ChieuCaoMoDun.setText(format.format(chieucaomodun).replace(",","."));
                dialog.dismiss();
            }
        });
        k1_m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("K1,M1");
                dialog.dismiss();
            }
        });
        k2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("K2");
                dialog.dismiss();
            }
        });
        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
                dialog.dismiss();
            }
        });
    }
    private void DialogPLAN(final TextView tv) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_menu_plan);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        /**ÁNH XẠ**/
        final ImageView pl2a = dialog.findViewById(R.id.pl2a);
        final ImageView pl3s = dialog.findViewById(R.id.pl3s);
        final ImageView pld = dialog.findViewById(R.id.pld);
        final ImageView plx = dialog.findViewById(R.id.plx);
        final ImageView none = dialog.findViewById(R.id.none);

        /**Button**/
        pl2a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("PL2A");
                dialog.dismiss();
            }
        });
        pl3s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("PL3S");
                dialog.dismiss();
            }
        });
        pld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("PLD");
                dialog.dismiss();
            }
        });
        plx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("PLX");
                dialog.dismiss();
            }
        });
        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
                dialog.dismiss();
            }
        });
    }
    private void DialogHIP(final TextView tv) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_menu_hip);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        /**ÁNH XẠ**/
        final ImageView hd = dialog.findViewById(R.id.dm);
        final ImageView hk = dialog.findViewById(R.id.dhm);
        final ImageView hka = dialog.findViewById(R.id.dlm);
        final ImageView hs = dialog.findViewById(R.id.drm);
        final ImageView ht = dialog.findViewById(R.id.dmleft);
        final ImageView hx2 = dialog.findViewById(R.id.hx2);
        final ImageView none = dialog.findViewById(R.id.none);

        /**Button**/
        hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HD");
                dialog.dismiss();
            }
        });
        hk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HK");
                dialog.dismiss();
            }
        });
        hka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HKA");
                dialog.dismiss();
            }
        });
        hs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HS");
                dialog.dismiss();
            }
        });
        ht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HT");
                dialog.dismiss();
            }
        });
        hx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("HX2");
                dialog.dismiss();
            }
        });
        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
                dialog.dismiss();
            }
        });
    }
    /**BẢNG BÌA**/
    private void DialogThongTinChung() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_thongtinchung_tudung);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        AnhXa_DialogTTC(dialog);
        /***/
        btnChupHienTrang = dialog.findViewById(R.id.btnChupHienTrang);

        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");/**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLEBia.txt");
        String[][] data=DataforPath(file);

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,MaTram);
        if(mediaStorageDir.exists())
        {
            File[] files=mediaStorageDir.listFiles();
            if(files.length==8) tvLoaiCotTTC.setText("Tự đứng");
            if(files.length==10) tvLoaiCotTTC.setText("Dây co");
        }

        /***/

        listLoaiCot.addAll(Arrays.asList("Tự đứng","Cột cóc","Cột chống cứng","Cột monopole","Cột bê tông li tâm"));
        listViTriDat.addAll(Arrays.asList("Trên mái","Dưới đất"));
        listcapdobenbt.addAll(Arrays.asList("B15","B20","B25"));
        listkichthuocchancot.addAll(Arrays.asList("4.0x4.0","5.0x5.0","6.0x6.0","7.0x7.0"));
        listkichthuocdinhcot.addAll(Arrays.asList("0.4x0.4","0.5x0.5","0.6x0.6","0.7x0.7"));
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
        SetPopup(listViTriDat,TableBiaTTC,7,1);
        SetPopup(listcapdobenbt ,TableBiaTTC,8,1);
        SetPopup(listkichthuocchancot,TableBiaTTC,5,1);
        SetPopup(listkichthuocdinhcot,TableBiaTTC,6,1);
        click_DialogTTC(dialog);
    }
    /**BẢNG 2**/
    public void set_ViewTable2(int vitri) {
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
        file = new File(file,"Data"+ MaTram);
        file = new File(file,"TABLE2.txt");
        data_Table2=DataforPath(file);
        /**SET TEXT ON ROW**/
        int ColumnCount =4;
        int Rowcount = Table2.getChildCount();
        for(int i=1;i<Rowcount;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
            row.setVisibility(View.GONE);
        }

        //Toast.makeText(FormMain_CotTuDung.this, data[1][2], Toast.LENGTH_LONG).show();
        /****/
        for(int i=1;i<SoMong+2;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table2)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table2,i,ColumnCount);
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
        edtKichThuoc.setText(data_Table2[vitri][1]);
        //TÁCH THÀNH TỪNG Ý HIỆN TRANG
        listHienTrang.clear();
        if(!String.valueOf(data_Table2[vitri][2]).equals(""))
            if(String.valueOf(data_Table2[vitri][2]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table2[vitri][2].split(("- "));
                for( String s:dongHienTrang)
                {
                    listHienTrang.add(s);
                }
                edtHienTrang.setText(listHienTrang.get(vitriHienTrang).replace("\n",""));
            }
            else if (Kiemtra(data_Table2[vitri][0],listDitatMuc10))
            {
                Kiemtra(data_Table2[vitri][0],listDitatMuc10);
            }
            else  edtHienTrang.setText(data_Table2[vitri][2]);

        for(int i= 0 ;i<=10;i++)
        {
            listHienTrang.add("");
        }
        //TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
        listDeXuat.clear();
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

        for(int i=1;i<SoMong+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table3,i,ColumnCount);
            ////CheckDataTable(row,data,i,ColumnCount);
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

        for(int i=1;i<SoMong+1;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table3,i,ColumnCount);
            ////CheckDataTable(row,data,i,ColumnCount);
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
            edtKichThuoc.setText(data_Table4[vitri][1]);
        }catch (Exception e)
        {
            /****/
            String dataTable4 = GetTableData(Table4,4);
            saveDataOnCacher(dataTable4,"TABLE4");
            data_Table4=DataforPath(file);
        }
        edtKichThuoc.setText(data_Table4[vitri][1]);

        listHienTrang.clear();
        if(!String.valueOf(data_Table4[vitri][2]).equals(""))
            if(String.valueOf(data_Table4[vitri][2]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table4[vitri][2].split(("- "));
                for( String s:dongHienTrang)
                {
                    listHienTrang.add(s);
                }

                edtHienTrang.setText(listHienTrang.get(vitriHienTrang).replace("\n",""));
            }
            else if (Kiemtra(data_Table4[vitri][0],listDitatMuc10))
            {
                Kiemtra(data_Table4[vitri][0],listDitatMuc10);
            }
            else
                edtHienTrang.setText(data_Table4[vitri][2]);
        for(int i= 0 ;i<=10;i++)
        {
            listHienTrang.add("");
        }
        //TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
        listDeXuat.clear();
        if(!String.valueOf(data_Table4[vitri][3]).equals(""))
            if(String.valueOf(data_Table4[vitri][3]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table4[vitri][3].split(("- "));
                for( String s:dongHienTrang)
                {
                    listDeXuat.add(s);
                }
                edtDeXuat.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
            }

            else  edtDeXuat.setText(data_Table4[vitri][3]);

        for(int i= 0 ;i<=10;i++)
        {
            listDeXuat.add("");
        }
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
        /**DÒNG ĐỐT 1*/
        int ColumnCount =3;
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
        /****/
        //Log.d("Length",String.valueOf(data_Table2.length));
        if (data_Table6.length<3)
        {
            /****/
            String dataTable6 = GetTableData(Table6,3);
            saveDataOnCacher(dataTable6,"TABLE6");
            data_Table6=DataforPath(file);
        }

        //TÁCH THÀNH TỪNG Ý HIỆN TRANG
        listHienTrang.clear();
        if(!String.valueOf(data_Table6[vitri][1]).equals(""))
            if(String.valueOf(data_Table6[vitri][1]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table6[vitri][1].split(("- "));
                for( String s:dongHienTrang)
                {
                    listHienTrang.add(s);
                }

                edtHienTrang.setText(listHienTrang.get(vitriHienTrang).replace("\n",""));
            }
            else if (Kiemtra(data_Table6[vitri][0],listDitatMuc10))
            {
                Kiemtra(data_Table6[vitri][0],listDitatMuc10);
            }
            else
                edtHienTrang.setText(data_Table6[vitri][1]);
        for(int i= 0 ;i<=10;i++)
        {
            listHienTrang.add("");
        }
        //TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
        listDeXuat.clear();
        if(!String.valueOf(data_Table6[vitri][2]).equals(""))
            if(String.valueOf(data_Table6[vitri][2]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table6[vitri][2].split(("- "));
                for( String s:dongHienTrang)
                {
                    listDeXuat.add(s);
                }
                edtDeXuat.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
            }

            else  edtDeXuat.setText(data_Table6[vitri][2]);

        for(int i= 0 ;i<=10;i++)
        {
            listDeXuat.add("");
        }
        //edtDeXuat.setText(data_Table6[vitri][5]);

    }
    public void set_DataTable6(int vitri) {

        /**DÒNG ĐỐT 1*/
        int ColumnCount =3;
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
        String dataTable6 = GetTableData(Table6,3);
        saveDataOnCacher(dataTable6,"TABLE6");
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
        for(int i=1;i<SoDot;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table10,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
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
        edtKichThuoc.setText(data_Table10[vitri][1]);
        edtThanhCanh.setText(data_Table10[vitri][2]);
        //TÁCH THÀNH TỪNG Ý HIỆN TRANG
        listHienTrang.clear();
        if(!String.valueOf(data_Table10[vitri][3]).equals(""))
            if(String.valueOf(data_Table10[vitri][3]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table10[vitri][3].split(("- "));
                for( String s:dongHienTrang)
                {
                    listHienTrang.add(s);
                }

                edtHienTrang.setText(listHienTrang.get(vitriHienTrang).replace("\n",""));
            }
            else if (Kiemtra(data_Table10[vitri][0],listDitatMuc10))
            {
                Kiemtra(data_Table10[vitri][0],listDitatMuc10);
            }
            else
                edtHienTrang.setText(data_Table10[vitri][3]);
        for(int i= 0 ;i<=10;i++)
        {
            listHienTrang.add("");
        }
        //TÁCH THÀNH TỪNG Ý ĐỀ XUẤT
        listDeXuat.clear();
        if(!String.valueOf(data_Table10[vitri][4]).equals(""))
            if(String.valueOf(data_Table10[vitri][4]).contains(("- ")))
            {
                String[] dongHienTrang = data_Table10[vitri][4].split(("- "));
                for( String s:dongHienTrang)
                {
                    listDeXuat.add(s);
                }
                edtDeXuat.setText(listDeXuat.get(vitriDeXuat).replace("\n",""));
            }

            else  edtDeXuat.setText(data_Table10[vitri][4]);

        for(int i= 0 ;i<=10;i++)
        {
            listDeXuat.add("");
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
            //CheckDataTable(row,data,i,ColumnCount);
        }
        for(int i=1;i<SoDot;i++)
        {
            TableRow row = (TableRow) (((TableLayout) Table10)).getChildAt(i);
            row.setVisibility(View.VISIBLE);
            setDataTable(row,data_Table10,i,ColumnCount);
            //CheckDataTable(row,data,i,ColumnCount);
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
        data_Table12_V1=DataforPath(fileV1);
        data_Table12_V2=DataforPath(fileV2);
        data_Table12_V3=DataforPath(fileV3);
        data_Table12_V0=DataforPath(fileV0);

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

        DecimalFormat format = new DecimalFormat("0.00");
        DecimalFormat format0000 = new DecimalFormat("0.0000");
        File file = new File(Environment.getExternalStorageDirectory(), "DataViettel");        /**THÊM FOLDER GIÁM SÁT*/
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
                Vong1.get(j-1).setText(String.valueOf(format.format(Tong/SoCot)).replace(",","."));
                dataKQ[0][j-1] = String.valueOf(format.format(Tong/SoCot).replace(",","."));
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
                Vong2.get(j-1).setText(String.valueOf(format.format(Tong/SoCot)).replace(",","."));
                dataKQ[1][j-1] = String.valueOf(format.format(Tong/SoCot)).replace(",",".");
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
                Vong3.get(j-1).setText(String.valueOf(format.format(Tong/SoCot)).replace(",","."));
                dataKQ[2][j-1] = String.valueOf(format.format(Tong/SoCot)).replace(",",".");
            }
        }
        /**Tính Dx, DY*/
        for(int i=0;i<2;i++)
        {
            Double Dx = Double.parseDouble(dataKQ[1][i]) - Double.parseDouble(dataKQ[0][i]);
            dataKQ[1][i + 4] = String.valueOf(format0000.format(Dx)).replace(",",".");
            Vong2.get(i + 3).setText(dataKQ[1][i + 4]);

            //Log.d("Data:",dataKQ[1][i]+ "-" +dataKQ[0][i])  ;
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
            else {
                Toast.makeText(getApplicationContext(), "Dữ liệu được lưu trong DataViettel trên máy bạn" , Toast.LENGTH_SHORT).show();
            }
        }
        readFileDanhGia();

        Intent intent = getIntent();
        MaTram = intent.getStringExtra("TenTram");
        SoMong= Integer.parseInt(intent.getStringExtra("SoMong"));
        SoDot= Integer.parseInt(intent.getStringExtra("SoDot"));
        ViTriDat = intent.getStringExtra("ViTriDat");
        KichThuocMong = intent.getStringExtra("KichThuocMong");
        KichThuocChanCot = intent.getStringExtra("KichThuocChanCot");
        KichThuocDinhCot = intent.getStringExtra("KichThuocDinhCot");
        MacBeTong = intent.getStringExtra("MacBeTong");
        SoCot= Integer.parseInt(intent.getStringExtra("SoChanCot"));
        Latitude = intent.getStringExtra("Lat");
        Logiest = intent.getStringExtra("Long");
        LocalCity = intent.getStringExtra("ViTri");
        TextView tvToaDo = findViewById(R.id.tvToaDo);
        TextView tvViTri = findViewById(R.id.tvViTri);
        tvToaDo.setText(Logiest + "'N" +"  "+ Latitude+ "'E");
        tvViTri.setText(LocalCity);
        listGCX.clear();

        //if (ViTriDat.contains("đất")) btnDulieuDam.setVisibility(View.GONE);
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
        SoGaChongXoay= listGCX.size()*2;
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
        //region Đọc hiện trạng.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,MaTram);/// lấy link vào hang mục
        File[] tenhangmuc =mediaStorageDir.listFiles();
        File fileDiTat = new File(mediaStorageDir,tenhangmuc[tenhangmuc.length-1].getName());
        File[] listDiTat =fileDiTat.listFiles();
        for (File file:listDiTat)
        {
            listDitatMuc10.add(file.getName());
        }
        //endregion

    }
    public void LocHienTrangTuAnh(String tenCauKien){
        //region Đọc hiện trạng.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,MaTram);/// lấy link vào hang mục
        File[] tenhangmuc =mediaStorageDir.listFiles();
        File fileDiTat = new File(mediaStorageDir,tenhangmuc[tenhangmuc.length-1].getName());
        File[] listDiTat =fileDiTat.listFiles();
        for (File file:listDiTat)
        {
            listDitatMuc10.add(file.getName());
        }
        if (Kiemtra(tenCauKien,listDitatMuc10))
        {
            Kiemtra(tenCauKien,listDitatMuc10);
        }
        else Toast.makeText(FormMain_CotTuDung.this,"Chưa có mục dị tật cho cấu kiện này!", Toast.LENGTH_SHORT).show();

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
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this,textView);
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
                final PopupMenu popupMenu = new PopupMenu(FormMain_CotTuDung.this,textView);
                for (String s:listHT)
                    popupMenu.getMenu().add(s);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (textView.getText().toString().equals("")){
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


        }
        else if(table==Table3)
        {
            int Rowcount = Table3.getChildCount();
            for(int i=1;i<SoMong+1;i++)
            {
                TableRow row = (TableRow) (((TableLayout) Table3)).getChildAt(i);
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

        else if (table==TableBiaTTC)
        {
            int Rowcount = table.getChildCount();
            for(int i=1;i<Rowcount;i++)
            {
                TableRow row = (TableRow) (((TableLayout) table)).getChildAt(i);
                string=string.replace("_ ","_").replace("  "," ").trim() + GetRowData(row,ColumnCount).trim();
            }
        }
        if (table==Table4 || table == Table5)
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

        if(table==Table10)
        {
            for(int i=1;i<SoDot;i++)
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
        edtKichThuoc.setAdapter(adapterKT);
        edtKichThuoc.setThreshold(1);
        edtKichThuoc.setDropDownHeight(500);

        edtThanhCanh.setAdapter(adapterKT);
        edtThanhCanh.setThreshold(1);
        edtThanhCanh.setDropDownHeight(500);



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
        try
        {
            //ĐỀ XUÂT
            DeXuatMong=StringforPath("TRONGLUONGANTEN");
            ArraylistDX.clear();
            String[] phulucdx = DeXuatMong.split("--");
            for (int j = 1; j < phulucdx.length; j = j + 1){
                String[] phuluccongtac = phulucdx[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2)
                {
                    ArraylistDX.add(phuluccongtac[i]);
                }
            }
        }
        catch (Exception e)
        {
            DeXuatMong = UT.TrongLuongAnten;
            saveDataOnTemplate(DeXuatMong,"TRONGLUONGANTEN");
            setPopUpAnten();
        }
        try
        {
            //Hiện trạng
            DanhGiaHienTrang=StringforPath("KICHTHUOCANTEN");
            ArraylistHT.clear();
            String[] phulucht = DanhGiaHienTrang.split("--");
            for (int j = 1; j < phulucht.length; j = j + 1){
                String[] phuluccongtac = phulucht[j].split("_");
                //Log.d("Tên hạng mục:", phuluccongtac[0]);
                for (int i = 1; i < phuluccongtac.length; i = i + 2)
                {
                    ArraylistHT.add(phuluccongtac[i]);
                }
            }
        }
        catch (Exception e)
        {
            DanhGiaHienTrang = UT.KichThuocAnten;
            saveDataOnTemplate(DanhGiaHienTrang,"KICHTHUOCANTEN");
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
        ArraylistKG.addAll(Arrays.asList("4G@147x275x86@16.8","3G@1334x261x146@9.8","3G 2100@1214x155x70@9.8","Triple band gain thấp@1384x261x146@23.3","Dual band gain cao@2533x261x146@31.2","Dual band gain thấp@1334x261x146@20.3","Twinbeam@1390x301x181@21.5","RRU@425x300x190@15","FEEDER 7/8@0@0","RF@0@0","2G 900@2580x262x116@25.3","2G 1800@1314x155x70@9.8","Diplexer@425x300x190@15"));
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
        final DecimalFormat format = new DecimalFormat("0.00");

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
                    tvKQ.setText(String.valueOf(format.format(KQ)).replace(",","."));
                    tvKQ.setBackground(getResources().getDrawable(R.drawable.boder_green));


                }
                catch (Exception e)
                {}
            }
        });

    }
    public double[] Xoay(double ToaDoX,double ToaDoY, double Goc){
        Goc = ( Goc *  Math.PI ) /180;
        double[] Diem1 = new double[2];
        Diem1[0] = ((ToaDoX * Math.cos(Goc))
                - (ToaDoY * Math.sin(Goc)));
        Diem1[1] = ((ToaDoX * Math.sin(Goc)) + (ToaDoY * Math.cos(Goc)));
        return Diem1;
    }
    public void SetButtonXoa(TableLayout table, final int rowItem, int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormMain_CotTuDung.this);
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
    public Boolean Kiemtra(String s,ArrayList<String> list){
        Boolean b= false;
        for (String string :list)
        {
            if(string.contains(s))
            {
                b=true;
                String hientrang = "";
                if(string.contains(" - "))  hientrang = string.split(" - ")[1];
                listHienTrang.clear();
                listHienTrang.add("");
                vitriHienTrang = 1;
                if((hientrang.contains("; ")))
                {
                    String[] dongHienTrang = hientrang.split(("; "));
                    for( String ss:dongHienTrang)
                    {
                        listHienTrang.add(ss);
                    }
                    for (int i= 1;i<10;i++) listHienTrang.add("");
                    edtHienTrang.setText(listHienTrang.get(vitriHienTrang).replace("\n",""));
                    tvViTriHienTrang.setText("Đánh giá hiện trạng: " + String.valueOf(vitriHienTrang) + "/" + String.valueOf(listHienTrang.size()));
                }
                else  edtHienTrang.setText(hientrang);
                break;
            }
        }
        return b;
    }
    public void SetButtonFACE(TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);

        final TextView ChieuCaoDot = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem-3);
        final TextView KichThuocDay = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem-2);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        final TextView SoMoDun = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem+3);
        final TextView ChieuCaoMoDun = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem+4);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFACE(ChieuCaoDot,KichThuocDay,textView,SoMoDun,ChieuCaoMoDun);
            }
        });

    }
    public void SetButtonPLAN(TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogPLAN(textView);
            }
        });

    }
    public void SetButtonHIP(TableLayout table,int rowItem,int columnItem) {
        TableRow rowLoaiCot = (TableRow) (((TableLayout) table)).getChildAt(rowItem);
        final TextView textView = (TextView) (((TableRow) rowLoaiCot)).getChildAt(columnItem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogHIP(textView);
            }
        });

    }

    /**
     * HIỆN TRẠNG*/
    public void getProductList(TextView editText,String LoaiCauKien){
        try
        {
            String[] strings = editText.getText().toString().split("; ");

            myList.clear();
            LoaiCauKien = DataforPath(LoaiCauKien);
            String[] phuluccon = LoaiCauKien.split("@");
            for (int i = 1; i < phuluccon.length; i ++) {
                myList.add(phuluccon[i].toString().replace("\n",""));

            }

            productList = new ArrayList<HienTrang>();
            Collections.sort(myList);
            for (String HM : myList)
            {
                productList.add(new HienTrang(HM,Kiemtra(HM,strings)));
            }

            listview_ht_adapter = new Listview_HT_Adapter_TuDung(this,R.layout.list_item_checkbox,productList );
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
            if(string.equals(s)) {b=true;break;}
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
                if (sb.length()==0) sb = sb.append(""+s);
                else sb = sb.append("; "+s);
            }
        }
        //Toast.makeText(this, sb, Toast.LENGTH_LONG).show();
        return sb;
    }
    public void DialogHientrangCheckBox(final EditText tv, final String LoaiCauKien) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        textHT = "";
        for (String s:listHienTrang)
        {
            if (!textHT.trim().equals("") && !s.trim().equals(""))
                textHT = textHT + "; " + s.replace("\n","") ;
            else textHT = textHT + s.replace("\n","") ;
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

        //this.initListViewData();
        SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, myList);
        searchAutoCompleteCT.setAdapter(newsAdapterCT);
        searchViewCT.setIconifiedByDefault(false);

        searchViewCT.setQueryHint("Tìm kiếm");

        searchAutoCompleteCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoCompleteCT.setText(queryString);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
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
    public void TuHocHienTrang(final EditText editText,final String LoaiCauKien,ArrayList<String> listHienTrang){
        listHienTrang.clear();listHienTrang.add("");
        if(editText.getText().toString().contains(("; ")))
        {
            String[] dongHienTrang =editText.getText().toString().split(("; "));
            for( String s:dongHienTrang)
            {
                listHienTrang.add(s.replace("\n",""));
                //Tự động học hiện trạng
                if (!LoaiCauKien.equals("HienTrangBeTong"))
                {
                    if (!HienTrangThep.contains(s.replace("\n",""))){
                        HienTrangThep = HienTrangThep.replace("\n","") +"@"+s.replace("\n","");
                        saveDataOnTemplate(HienTrangThep,"HienTrangThep");
                        Toast.makeText(FormMain_CotTuDung.this, "Thêm mới hiện trạng thép " + s, Toast.LENGTH_SHORT).show();
                    }
                }
                else if (LoaiCauKien.equals("HienTrangBeTong"))
                {
                    if (!HienTrangBeTong.contains(s.replace("\n",""))){
                        HienTrangBeTong = HienTrangBeTong.replace("\n","") +"@"+s.replace("\n","");
                        saveDataOnTemplate(HienTrangBeTong,"HienTrangBeTong");
                        Toast.makeText(FormMain_CotTuDung.this, "Thêm mới hiện trạng bê tông " + s, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(FormMain_CotTuDung.this, "Thêm mới hiện trạng thép " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
            else if (LoaiCauKien.equals("HienTrangBeTong"))
            {
                if (!HienTrangBeTong.contains(editText.getText().toString().replace("\n",""))){
                    HienTrangBeTong = HienTrangBeTong.replace("\n","") +"@"+editText.getText().toString().replace("\n","");
                    saveDataOnTemplate(HienTrangBeTong,"HienTrangBeTong");
                    Toast.makeText(FormMain_CotTuDung.this, "Thêm mới hiện trạng bê tông " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * ĐỀ XUẤT*/
    public void getProductListDX(TextView editText,String LoaiCauKien){
        try
        {
            String[] strings = editText.getText().toString().split("; ");

            myList.clear();
            LoaiCauKien = DataforPath(LoaiCauKien);
            String[] phuluccon = LoaiCauKien.split("@");
            for (int i = 1; i < phuluccon.length; i ++) {
                myList.add(phuluccon[i].toString().replace("\n",""));
            }
            Collections.sort(myList);
            productList = new ArrayList<HienTrang>();

            for (String HM : myList)
            {
                productList.add(new HienTrang(HM,Kiemtra(HM,strings)));
            }

            listview_ht_adapter = new Listview_HT_Adapter_TuDung(this,R.layout.list_item_checkbox,productList );
            listView.setAdapter(listview_ht_adapter);
        }
        catch (ArithmeticException e)
        {

        }
    }
    public void DialogHientrangCheckBoxDX(final EditText tv, final String LoaiCauKien) {
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

        //this.initListViewData();
        SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, myList);
        searchAutoCompleteCT.setAdapter(newsAdapterCT);
        searchViewCT.setIconifiedByDefault(false);

        searchViewCT.setQueryHint("Tìm kiếm");

        searchAutoCompleteCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoCompleteCT.setText(queryString);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
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
    public void TuHocDeXuat(final EditText editText,final String LoaiCauKien,ArrayList<String> listHienTrang){
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
                        Toast.makeText(FormMain_CotTuDung.this, "Thêm mới đề xuất thép " + s, Toast.LENGTH_SHORT).show();
                    }
                }
                else if (LoaiCauKien.equals("DeXuatBeTong"))
                {
                    if (!DeXuatBeTong.contains(s.replace("\n",""))){
                        DeXuatBeTong = DeXuatBeTong.replace("\n","") +"@"+s.replace("\n","");
                        saveDataOnTemplate(DeXuatBeTong,"DeXuatBeTong");
                        Toast.makeText(FormMain_CotTuDung.this, "Thêm mới đề xuất bê tông " + s, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(FormMain_CotTuDung.this, "Thêm mới đề xuất thép " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
            else if (LoaiCauKien.equals("HienTrangBeTong"))
            {
                if (!DeXuatBeTong.contains(editText.getText().toString().replace("\n",""))){
                    DeXuatBeTong = DeXuatBeTong.replace("\n","") +"@"+editText.getText().toString().replace("\n","");
                    saveDataOnTemplate(DeXuatBeTong,"DeXuatBeTong");
                    Toast.makeText(FormMain_CotTuDung.this, "Thêm mới đề xuất bê tông " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
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
    public void NextTo_CameraAPI2(int vitriHM){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir, MaTram);
        /**Lấy danh sách*/
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
        Intent intent2 = new Intent();
        intent2 = new Intent(FormMain_CotTuDung.this, Camera.class);
        intent2.putExtra("TenHM", ArrayString[vitriHM]);  // Truyền một String
        intent2.putExtra("TenTram", MaTram);  // Truyền một String
        intent2.putExtra("MangCT", duongdananh);  // Truyền một String
        intent2.putExtra("TenCongTac", duongdananh[listcongtac.size()-1]);  // Truyền một String
        intent2.putExtra("MangString", ArrayString);  // Truyền một String
        intent2.putExtra("SoLuong", String.valueOf(lengthArray));  // Truyền một String
        intent2.putExtra("Long",Logiest);  // Truyền một String
        intent2.putExtra("Lat", Latitude);  // Truyền một String
        intent2.putExtra("ViTri", LocalCity);  // Truyền một String
        try{intent2.putExtra("TenChiTiet", tvTenMong.getText().toString());  // Truyền một String
             }catch (Exception e) {intent2.putExtra("TenChiTiet", "");}

        startActivity(intent2);

    }
    //endregion
}
