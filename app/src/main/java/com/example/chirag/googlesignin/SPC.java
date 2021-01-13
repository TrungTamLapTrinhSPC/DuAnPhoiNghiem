package com.example.chirag.googlesignin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.google.api.services.driveactivity.v2.model.Edit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

final class SPC { private SPC() {}

    /**
     *Vị trisbangr thiết kế trạm
     */

    static final String DuongDanFileThietKeTram = "/DuLieu/ThietKeTram.txt";
    static final String DuongDanThuMucHinhAnh = "/HinhAnh";
    static final String DuongDanThuMucDuLieu = "/DuLieu";
    static final ArrayList<String> listBangTan = new ArrayList<String>(Arrays.asList("900", "1800", "900/1800", "2100","2300"));
    static final ArrayList<String> ThietKeTram = new ArrayList<String>(Arrays.asList("MaTram", "DiaDiem", "ToaDo", "NgayDo", "ViTriDat"));
    static final ArrayList<String> TenHinhAnh = new ArrayList<String>(Arrays.asList("Hình ảnh công trình hướng sector 1", "Hình ảnh công trình hướng sector 2", "Hình ảnh công trình hướng sector 3", "Hình ảnh công trình hướng sector 4", "Hình ảnh công trình hướng sector 5"));
    static final ArrayList<String> ThietKeNhaDatTram = new ArrayList<String>(Arrays.asList("TenCongTrinh", "SoTang", "ChieuCaoNha", "ChieuDai", "ChieuRong"));
    static final ArrayList<String> ThietKeCot = new ArrayList<String>(Arrays.asList("TenCot", "ChieuCaoCot", "SoChan", "KichThuocCot", "ViTriX", "ViTriY"));
    static final ArrayList<String> ThietKeBTS = new ArrayList<String>(Arrays.asList("TenTramGoc", "ChungLoaiThietBi", "BangTanHoatDong"));
    static final ArrayList<String> ThietKeThanhPhan = new ArrayList<String>(Arrays.asList("TenThanhPhan","ChieuDai", "ChungLoai", "SuyHaodB", "SuyHao"));
    static final ArrayList<String> ThietKeCongTrinh = new ArrayList<String>(Arrays.asList("TenCongTrinh","ChieuCao", "KhoangCach","SoTang", "GocPhuongVi", "DoDay", "DoRong"));
    static final ArrayList<String> ThietKeAnten = new ArrayList<String>
            (Arrays.asList("TenAnten", "ChungLoaiThietBi", "SoMayPhat", "TongCongSuatPhat1", "TongCongSuatPhat2",
            "ChungLoaiAnten", "LoaiAnten", "DoTangIch", "BangTanHoatDong", "DoDaiBucXa", "GocNgang", "GocPhuongVi", "DoCaoAnten1", "DoCaoAnten2",
            "ChungLoaiJumper", "ChieuDaiJumper", "SuyHaodBJumper","SuyHaoJumper",
            "ChungLoaiFeeder", "ChieuDaiFeeder", "SuyHaodBFeeder","SuyHaoFeeder","TongSuyHao"));
    static void SaveListAutoCompleteTextView_json(String nameFile,File pathFile,ArrayList<AutoCompleteTextView> listAutoCompleteTextView,ArrayList<String> arrayList) throws JSONException {
        JSONObject obj = new JSONObject();
        for(int i=0;i<listAutoCompleteTextView.size();i++)
        {
            obj.put(arrayList.get(i),listAutoCompleteTextView.get(i).getText().toString());
        }
        saveTextFile(nameFile,obj.toString(),pathFile);
    }
    static final String TaoThietKeAnten(String TenAnten) throws JSONException {
        String thietke = "";
        JSONObject obj = new JSONObject();
        obj.put(ThietKeAnten.get(0),TenAnten);
        for(int i=1;i<ThietKeAnten.size();i++)
        {
            obj.put(ThietKeAnten.get(i),"0");
        }
//            for (String tk:ThietKeAnten)
//            {
//                if (thietke.equals("")){
//                    thietke = thietke + TenAnten;
//                }
//                else thietke = thietke + "&0";
//            }
        thietke = obj.toString();
        return thietke;
    }
    static final int TimViTri(String key, ArrayList<String> list){
        int vt= -1;
        vt = list.indexOf(key);
        return vt;
    }
    static final String getLastModified(File file){
        String date = "";
        date = new SimpleDateFormat("dd-MM-yyyy").format(
                new Date(new File(String.valueOf(file)).lastModified())
        );
        return date;
    }
    static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    static String readText(File file){
        String text = "";
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            text = buffer.toString().trim();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    static ArrayList<String> readAllLineText(File file){
        ArrayList<String> text = new ArrayList<String>();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            //StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                //buffer.append(line).append("\n");
                text.add(line);
            }
            //text = buffer.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    static Boolean TaoThuMuc(File file) {
        /**THÊM FOLDER GIÁM SÁT*/
        //File Template = new File(Environment.getExternalStorageDirectory(),"DataAppThuChi");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.d("App", "failed to create directory");
                return false;
            }
            else {
                return true;
            }
        }
        else return false;
    }
    static void saveTextFile(String name,String text,File file){
        String content = text;
        FileOutputStream outputStream;
        try {
            if (!file.exists())
                if (!file.mkdirs())
                {
                    Log.d("App", "failed to create directory");
                }
            file = new File(file, name+".txt");
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
            //Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static final File pathDataApp_PNDT =  new File(Environment.getExternalStorageDirectory(),"DataAppPNDT");
    static final File pathTemplate =  new File(Environment.getExternalStorageDirectory(),"Template");
    static void ReadListAutoCompleteTextView_Json(String nameFile,File pathFile,ArrayList<AutoCompleteTextView> listAutoCompleteTextView,ArrayList<String> arrayList) throws JSONException {
        File fileData = new File(pathFile,nameFile);
        if(fileData.exists())
        {
           String text = readText(fileData);
           JSONObject jsonObject = new JSONObject(text);

            for(int i=0;i<arrayList.size();i++)
            {
                listAutoCompleteTextView.get(i).setText(jsonObject.getString(arrayList.get(i)));
            }
        }

    }
    static Bitmap GanToaDo(Bitmap bitmap,String MaTram,String ToaDo,String DiaDiem){
    Bitmap AnhDauRa = null;
    Bitmap newbitmap = null;
    Bitmap bitmap2 = null;
    /**XOAY ẢNH**/
    if (bitmap.getWidth()> bitmap.getHeight())
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),matrix, true);
    }
    else bitmap2 = bitmap;


    Bitmap.Config config = bitmap2.getConfig();
    config = Bitmap.Config.ARGB_8888;
    newbitmap=Bitmap.createBitmap(bitmap2.getWidth(),bitmap2.getHeight(),config);
    Canvas newcanvas = new Canvas(newbitmap);
    newcanvas.drawBitmap(bitmap2,0,0,null);
    Paint painttext = new Paint(Paint.ANTI_ALIAS_FLAG);
    painttext.setColor(Color.WHITE);
    painttext.setTextSize(bitmap2.getWidth()/35);
    //painttext.setShadowLayer(10f,10f,10f,Color.BLACK);
    Rect rectText = new Rect();
    String text ="Mã trạm:"+ MaTram;
    String text2 =ToaDo;
    String text3 =DiaDiem;

    painttext.getTextBounds(text,0,text.length(),rectText);
    newcanvas.drawText(text,0,rectText.height(),painttext);
    newcanvas.drawText(text2,0,2*rectText.height(),painttext);
    newcanvas.drawText(text3,0,3*rectText.height(),painttext);

    AnhDauRa = newbitmap;




    return AnhDauRa;
}
    static ArrayList LayDanhSachThietBi(){
        ArrayList<String> dataThietBi = readAllLineText(new File(pathTemplate,"ListThietBi.txt"));
        ArrayList<String> lstThietBi = new ArrayList<String>();
        for (String itemThietThietBi :dataThietBi)
        {
            String ThietBi = itemThietThietBi.split("&")[1];
            if (!lstThietBi.contains(ThietBi))
            {
                lstThietBi.add(ThietBi);
            }
        }
        return lstThietBi;
    }
    static ArrayList LayDanhSachAnten(){
        ArrayList<String> dataThietBi = readAllLineText(new File(pathTemplate,"ListAnten.txt"));
        ArrayList<String> lstThietBi = new ArrayList<String>();
        for (String itemThietThietBi :dataThietBi)
        {
            String ThietBi = itemThietThietBi.split("&")[1];
            if (!lstThietBi.contains(ThietBi))
            {
                lstThietBi.add(ThietBi);
            }
        }
        return lstThietBi;
    }
    static ArrayList LayDanhSachSuyHao(){
        ArrayList<String> dataThietBi = readAllLineText(new File(pathTemplate,"BangSuyHao.txt"));
        ArrayList<String> lstThietBi = new ArrayList<String>();
        for (String itemThietThietBi :dataThietBi)
        {
            String ThietBi = itemThietThietBi.split("&")[0];
            if (!lstThietBi.contains(ThietBi))
            {
                lstThietBi.add(ThietBi);
            }
        }
        return lstThietBi;
    }

    static void setPopUp(Context context, AutoCompleteTextView edt, ArrayList<String> arrayList,ImageButton imageButton){
        ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(context, R.layout.custom_list_item, R.id.text_view_list_item, arrayList);
        edt.setAdapter(adapterHT);
        edt.setThreshold(1);
        edt.setDropDownHeight(400);
        if (imageButton != null)
        {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(context, imageButton);

                    for (String s : arrayList)
                    { popupMenu.getMenu().add(s); }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem menuItem) {
                            edt.setText(menuItem.getTitle());
                            return false;
                        }
                    });

                    popupMenu.show();
                }
            });
        }
    }
    static ArrayList<String> layCongSuatPhat1(String ChungLoaiThietBi, String BangTanHoatDong){
        ArrayList<String> CongSuat = new ArrayList<String>();
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate,"ListThietBi.txt"));
        for(String item:listThietBi){
            if(item.contains(ChungLoaiThietBi) && item.contains(BangTanHoatDong)){
                String CS = item.split("&")[4];
                CongSuat.add(CS);
            }
        }
        return CongSuat;
    }
    static String layDoTangIchAnten(String ChungLoaiThietBi, String BangTanHoatDong){
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate,"ListAnten.txt"));
        for(String item:listThietBi){
            if(item.contains(ChungLoaiThietBi)){
                String TanSo = item.split("&")[2];
                if(TanSo.contains("-"))
                {
                    String[] mangTanSo = TanSo.split("-");
                    Double to = Double.valueOf(mangTanSo[0]);
                    Double from = Double.valueOf(mangTanSo[1]);
                    if(isNumeric(BangTanHoatDong))
                    {
                        Double BangTan = Double.valueOf(BangTanHoatDong);
                        if(BangTan >= to && BangTan < from)
                        {
                            DoTangIchAnten = item.split("&")[3];
                        }
                    }
                }
            }
        }
        return DoTangIchAnten;
    }
    static String layDoDaiBucXa(String ChungLoaiThietBi, String BangTanHoatDong){
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate,"ListAnten.txt"));
        for(String item:listThietBi){
            if(item.contains(ChungLoaiThietBi)){
                String TanSo = item.split("&")[2];
                if(TanSo.contains("-"))
                {
                    String[] mangTanSo = TanSo.split("-");
                    Double to = Double.valueOf(mangTanSo[0]);
                    Double from = Double.valueOf(mangTanSo[1]);
                    if(isNumeric(BangTanHoatDong))
                    {
                        Double BangTan = Double.valueOf(BangTanHoatDong);
                        if(BangTan >= to && BangTan < from)
                        {
                            DoTangIchAnten = item.split("&")[4];
                        }
                    }
                }
            }
        }
        return DoTangIchAnten;
    }
    static String layGocNgang(String ChungLoaiThietBi, String BangTanHoatDong){
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate,"ListAnten.txt"));
        for(String item:listThietBi){
            if(item.contains(ChungLoaiThietBi)){
                String TanSo = item.split("&")[2];
                if(TanSo.contains("-"))
                {
                    String[] mangTanSo = TanSo.split("-");
                    Double to = Double.valueOf(mangTanSo[0]);
                    Double from = Double.valueOf(mangTanSo[1]);
                    if(isNumeric(BangTanHoatDong))
                    {
                        Double BangTan = Double.valueOf(BangTanHoatDong);
                        if(BangTan >= to && BangTan < from)
                        {
                            DoTangIchAnten = item.split("&")[6];
                        }
                    }
                }
            }
        }
        return "TILT cơ: " +DoTangIchAnten;
    }
    static String laySuyHaodB(String ChungLoaiThietBi, String BangTanHoatDong){
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate,"BangSuyHao.txt"));
        for(String item:listThietBi){
            if(item.contains(ChungLoaiThietBi) && item.contains(BangTanHoatDong) ){
                DoTangIchAnten = item.split("&")[4];
            }
        }
        return DoTangIchAnten;
    }


    //region Công thức tính
    static String TinhCongSuatPhat2(String CongSuatPhat1){
        String CongSuatPhat2 = "";
        Double double_CongSuatPhat2 = 0.0;
        if(isNumeric(CongSuatPhat1))
        {
            Double CS1 = Double.valueOf(CongSuatPhat1);
            double_CongSuatPhat2 = 10 * Math.log10(CS1*1000);
        }
        CongSuatPhat2 = String.valueOf(round(double_CongSuatPhat2,1));

        return CongSuatPhat2;
    }
    static String TinhSuyHao(String ChieuDai,String SuyHaodB){
        String SuyHao = "";
        Double double_CongSuatPhat2 = 0.0;
        if(isNumeric(ChieuDai) && isNumeric(SuyHaodB))
        {
            Double CS1 = Double.valueOf(ChieuDai);
            Double CS2 = Double.valueOf(SuyHaodB);

            double_CongSuatPhat2 = (CS1/100) * CS2;
        }
        SuyHao = String.valueOf(round(double_CongSuatPhat2,4));

        return SuyHao;
    }

    static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    //endregion
}
