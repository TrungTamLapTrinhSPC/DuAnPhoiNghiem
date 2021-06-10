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
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

final class SPC { private SPC() {}

    /**
     *Vị trisbangr thiết kế trạm
     */

    static final String DuongDanFileThietKeTram = "/DuLieu/ThietKeTram.txt";
    static final String DuongDanThuMucHinhAnh = "/HinhAnh";
    static final String DuongDanThuMucDuLieu = "/DuLieu";
    static final ArrayList<String> listLoaiAnten = new ArrayList<String>(Arrays.asList("Định hướng"));
    static final ArrayList<String> listBangTan = new ArrayList<String>(Arrays.asList("900", "1800", "900/1800", "2100","2300"));
    static final ArrayList<String> listSoDauConnecter = new ArrayList<String>(Arrays.asList("2", "4"));
    static final ArrayList<String> listLoaiCongTrinh = new ArrayList<String>(Arrays.asList("Khu nhà ở", "Khu đất trống", "Khu nhà 1 2 tầng", "Khu nhà 3 4 tầng","Khu nhà xưởng","Khu nhà cấp 4","Khu công nghiệp 1 2 tầng","Khu đường và đồng ruộng","Khu sân bay, nhà thấp tầng","Khu bãi xe"));
    static final ArrayList<String> ThietKeTram = new ArrayList<String>(Arrays.asList("MaTram", "DiaDiem", "ToaDo", "NgayDo", "ViTriDat"));
    static final ArrayList<String> TenHinhAnh = new ArrayList<String>(Arrays.asList("Hình ảnh các công trình hướng sector 1", "Hình ảnh các công trình hướng sector 2", "Hình ảnh các công trình hướng sector 3", "Hình ảnh các công trình hướng sector 4", "Hình ảnh các công trình hướng sector 5"));
    static final ArrayList<String> TenHinhAnhTongThe = new ArrayList<String>(Arrays.asList("Hình ảnh biển nhà trạm", "Hình ảnh tổng thể cột anten", "Hình ảnh tổng thể các thiết bị trong phòng máy", "Hình ảnh thiết bị treo trên cột"));
    static final ArrayList<String> ThietKeNhaDatTram = new ArrayList<String>(Arrays.asList("TenCongTrinh", "SoTang", "ChieuCaoNha", "ChieuDai", "ChieuRong"));
    static final ArrayList<String> ThietKeCot = new ArrayList<String>(Arrays.asList("TenCot", "ChieuCaoCot", "SoChan", "KichThuocCot", "ViTriX", "ViTriY","DanhSachTramGoc"));
    static final ArrayList<String> ThietKeBTS = new ArrayList<String>(Arrays.asList("TenTramGoc", "ChungLoaiThietBi", "BangTanHoatDong","MangSuDung","SoMayThuPhatSong"));
    static final ArrayList<String> ThietKeThanhPhan = new ArrayList<String>(Arrays.asList("TenThanhPhan","SuyHao"));
    static final ArrayList<String> ThietKeCongTrinh = new ArrayList<String>(Arrays.asList("TenCongTrinh","ChieuCao", "KhoangCach","SoTang", "GocPhuongVi", "DoDay", "DoRong"));
    static final ArrayList<String> ThietKeAnten = new ArrayList<String>(Arrays.asList("TenAnten", "ChungLoaiThietBi", "SoMayPhat", "TongCongSuatPhat1", "TongCongSuatPhat2",
            "ChungLoaiAnten", "LoaiAnten", "DoTangIch", "BangTanHoatDong", "DoDaiBucXa", "GocNgang", "GocPhuongVi", "DoCaoAnten1", "DoCaoAnten2",
            "ChungLoaiJumper", "ChieuDaiJumper", "SuyHaodBJumper","SuyHaoJumper",
            "ChungLoaiFeeder", "ChieuDaiFeeder", "SuyHaodBFeeder","SuyHaoFeeder","Connecter","TongSuyHaoConnecter","TongSuyHao"));
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
    static double round(double value, int places){
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
                try {
                    listAutoCompleteTextView.get(i).setText(jsonObject.getString(arrayList.get(i)));

                } catch (Exception e) {
                    listAutoCompleteTextView.get(i).setText("0");
                }

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
    static ArrayList LayDanhSachTenThanhPhan(){
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
    static ArrayList LayDanhSachSuyHao(){
        ArrayList<String> dataThietBi = readAllLineText(new File(pathTemplate,"BangSuyHao.txt"));
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
    static void setPopUp_img(Context context, TextView edt, ArrayList<String> arrayList, ImageButton imageButton){
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
            edt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(context, edt);

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
    static String laySoMayPhat(String ChungLoaiThietBi, String BangTanHoatDong){
        String CongSuat ="";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate,"ListThietBi.txt"));
        for(String item:listThietBi){
            if(item.contains(ChungLoaiThietBi) && item.contains(BangTanHoatDong)){
                CongSuat = item.split("&")[6];
            }
        }
        return CongSuat;
    }
    static String layMangSuDung(String ChungLoaiThietBi, String BangTanHoatDong){
        String CongSuat ="";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate,"ListThietBi.txt"));
        for(String item:listThietBi){
            if(item.contains(ChungLoaiThietBi) && item.contains(BangTanHoatDong)){
                CongSuat = item.split("&")[2];
            }
        }
        return CongSuat;
    }
    static String layDoTangIchAnten(String ChungLoaiThietBi, String BangTanHoatDong){
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate,"ListAnten.txt"));
        for(String item:listThietBi){
            if(item.contains(ChungLoaiThietBi)){
                String TanSo = item.split("&")[3];
                if(TanSo.contains(BangTanHoatDong))
                {
                    DoTangIchAnten = item.split("&")[4];
                }
                /*if(TanSo.contains("-"))
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
                }*/
            }
        }
        return DoTangIchAnten;
    }
    static String layDoDaiBucXa(String ChungLoaiThietBi, String BangTanHoatDong){
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate,"ListAnten.txt"));
        for(String item:listThietBi){
            if(item.contains(ChungLoaiThietBi)){
                String TanSo = item.split("&")[3];
                if(TanSo.contains(BangTanHoatDong))
                {
                    DoTangIchAnten = item.split("&")[5];
                }
                /*String TanSo = item.split("&")[2];
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
                }*/
            }
        }
        return DoTangIchAnten;
    }
    static String layGocNgang(String ChungLoaiThietBi, String BangTanHoatDong){
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate,"ListAnten.txt"));
        for(String item:listThietBi){
            if(item.contains(ChungLoaiThietBi)){
                String TanSo = item.split("&")[3];
                if(TanSo.contains(BangTanHoatDong))
                {
                   String GocNgang = item.split("&")[6];
                   if (GocNgang.contains("-"))
                   {
                       DoTangIchAnten = GocNgang.split("-")[1];
                   }
                   else DoTangIchAnten = GocNgang;
                }
                /*String TanSo = item.split("&")[2];
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
                }*/
            }
        }
        return DoTangIchAnten;
    }
    static String laySuyHaodB(String TenthietBi,String ChungLoaiThietBi, String BangTanHoatDong){
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate,"BangSuyHao.txt"));
        for(String item:listThietBi){
            if(item.contains(ChungLoaiThietBi) && item.contains(BangTanHoatDong) && item.contains(TenthietBi)){
                DoTangIchAnten = item.split("&")[3];
            }
        }
        return DoTangIchAnten;
    }
    public static Bitmap BITMAP_RESIZER(Bitmap bitmap, int newWidth, int newHeight){
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;

    }
    public static String DataforPath(String name){
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
    public void saveDataOnTemplate(String text, String Name){
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
    public static void saveDataOnCacher(String text, String Name){
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

    //region Công thức tính
    static String TinhCongSuatPhat2(String CongSuatPhat1){
        String CongSuatPhat2 = "";
        Double double_CongSuatPhat2 = 0.0;
        if(isNumeric(CongSuatPhat1))
        {
            Double CS1 = Double.valueOf(CongSuatPhat1);
            double_CongSuatPhat2 = 10 * Math.log10(CS1*1000);
        }
        CongSuatPhat2 = String.valueOf(round(double_CongSuatPhat2,2));

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
    //region Thư viện thiết bị
    static final String ListThietBi = "1&ERICSSON RBS 2106&GSM&900&48.30&&2\n" +
            "2&ERICSSON RBS 2106&GSM&900&48.01&&4\n" +
            "3&ERICSSON RBS 2106&GSM&1800&46.80&&2\n" +
            "4&ERICSSON RBS 2106&GSM&1800&46.51&&4\n" +
            "5&ERICSSON RBS 2111 (RRU-N)&GSM&900&43.01&&2\n" +
            "6&ERICSSON RBS 2111 (RRU-N)&GSM&1800&43.01&&2\n" +
            "7&ERICSSON RBS 2116&GSM&900&47.99&&2\n" +
            "8&ERICSSON RBS 2116&GSM&900&47.48&&4\n" +
            "9&ERICSSON RBS 2116&GSM&1800&47.48&&2\n" +
            "10&ERICSSON RBS 2116&GSM&1800&46.99&&4\n" +
            "11&ERICSSON RBS 2206&GSM&900&47.48&&2\n" +
            "12&ERICSSON RBS 2206&GSM&900&46.99&&4\n" +
            "13&ERICSSON RBS 2206&GSM&1800&46.53&&2\n" +
            "14&ERICSSON RBS 2206&GSM&1800&46.02&&4\n" +
            "15&ERICSSON RBS 2216&GSM&900&47.99&&2\n" +
            "16&ERICSSON RBS 2216&GSM&900&47.48&&4\n" +
            "17&ERICSSON RBS 2216&GSM&1800&47.48&&2\n" +
            "18&ERICSSON RBS 2216&GSM&1800&46.99&&4\n" +
            "19&ERICSSON RBS 2308&GSM&900&33.98&Anten tích hợp &2\n" +
            "20&ERICSSON RBS 2308&GSM&1800&33.42&Anten tích hợp &2\n" +
            "21&ERICSSON RBS 2309&GSM&900&36.99&Anten tích hợp &2\n" +
            "22&ERICSSON RBS 2309&GSM&1800&36.99&Anten tích hợp &2\n" +
            "23&ERICSSON RBS 3206&UMTS&2100&47.78&&1\n" +
            "24&ERICSSON RBS 3206&UMTS&2100&47.78&&2\n" +
            "25&ERICSSON RBS 3206&UMTS&2100&47.78&&3\n" +
            "26&ERICSSON RBS 3206&UMTS&2100&47.78&&4\n" +
            "27&ERICSSON RBS 3418 (RRUW-01)&UMTS&2100&47.78&&1\n" +
            "28&ERICSSON RBS 3418 (RRUW-01)&UMTS&2100&47.78&&2\n" +
            "29&ERICSSON RBS 3418 (RRUW-01)&UMTS&2100&47.78&&3\n" +
            "30&ERICSSON RBS MICRO 6501&UMTS&1800&36.99&Anten tích hợp &1\n" +
            "31&ERICSSON RBS MICRO 6501&UMTS&2100&36.99&Anten tích hợp &2\n" +
            "32&ERICSSON RBS MICRO 6502&LTE&1800&36.99&Anten tích hợp &1\n" +
            "33&ERICSSON RBS MICRO 6502&LTE&2100&36.99&Anten tích hợp &2\n" +
            "34&ERICSSON RBS 6601 (RRUS 01)&GSM&900&49.03&&1\n" +
            "35&ERICSSON RBS 6601 (RRUS 01)&GSM&900&49.03&&2\n" +
            "36&ERICSSON RBS 6601 (RRUS 01)&GSM&900&49.03&&3\n" +
            "37&ERICSSON RBS 6601 (RRUS 01)&GSM&900&49.03&&4\n" +
            "38&ERICSSON RBS 6601 (RRUS 01)&GSM&1800&49.03&&1\n" +
            "39&ERICSSON RBS 6601 (RRUS 01)&GSM&1800&49.03&&2\n" +
            "40&ERICSSON RBS 6601 (RRUS 01)&GSM&1800&49.03&&3\n" +
            "41&ERICSSON RBS 6601 (RRUS 01)&GSM&1800&49.03&&4\n" +
            "42&ERICSSON RBS 6601 (RRUS 01)&UMTS&2100&49.03&&1\n" +
            "43&ERICSSON RBS 6601 (RRUS 01)&UMTS&2100&49.03&&2\n" +
            "44&ERICSSON RBS 6601 (RRUS 01)&UMTS&2100&49.03&&3\n" +
            "45&ERICSSON RBS 6601 (RRUS 01)&UMTS&2100&49.03&&4\n" +
            "46&ERICSSON RBS 6601 (Radio 2219)&GSM&900&47.78&&1\n" +
            "47&ERICSSON RBS 6601 (Radio 2219)&GSM&900&47.78&&2\n" +
            "48&ERICSSON RBS 6601 (Radio 2219)&GSM&LTE&1800&49.03&&1\n" +
            "49&ERICSSON RBS 6601 (Radio 2219)&GSM&LTE&1800&49.03&&2\n" +
            "50&ERICSSON RBS 6601 (RRUS 32)&LTE&1800&46.02&&1\n" +
            "51&ERICSSON RBS 6601 (Radio 4415)&LTE&2100&46.02&&1\n" +
            "52&ERICSSON RBS 6601 (Radio 4415)&LTE&2100&46.02&&2\n" +
            "53&ERICSSON RBS 6601 (Radio 4428)&LTE&2100&46.02&&1\n" +
            "54&ERICSSON RBS 6601 (Radio 4428)&LTE&2100&49.03&&2\n" +
            "55&HUAWEI BTS3012&GSM&900&49.03&&2\n" +
            "56&HUAWEI BTS3012&GSM&900&49.03&&4\n" +
            "57&HUAWEI BTS3012&GSM&1800&49.03&&2\n" +
            "58&HUAWEI BTS3012&GSM&1800&49.03&&4\n" +
            "59&HUAWEI BTS3900&GSM&900&49.03&&2\n" +
            "60&HUAWEI BTS3900&GSM&900&49.03&&4\n" +
            "61&HUAWEI BTS3900&GSM&1800&49.03&&2\n" +
            "62&HUAWEI BTS3900&GSM&1800&49.03&&4\n" +
            "63&HUAWEI BTS3900&UMTS&2100&49.03&&1\n" +
            "64&HUAWEI BTS3900&UMTS&2100&49.03&&2\n" +
            "65&HUAWEI BTS3900&UMTS&2100&48.57&&3\n" +
            "66&HUAWEI DBS3900 (RRU3804)&UMTS&2100&47.78&&1\n" +
            "67&HUAWEI DBS3900 (RRU3804)&UMTS&2100&47.78&&2\n" +
            "68&HUAWEI DBS3900 (RRU3804)&UMTS&2100&47.78&&3\n" +
            "69&HUAWEI DBS3900 (RRU3804)&UMTS&2100&47.78&&4\n" +
            "70&HUAWEI DBS3900 (RRU3826)&UMTS&2100&49.03&&1\n" +
            "71&HUAWEI DBS3900 (RRU3826)&UMTS&2100&49.03&&2\n" +
            "72&HUAWEI DBS3900 (RRU3826)&UMTS&2100&48.92&&3\n" +
            "73&HUAWEI DBS3900 (RRU3826)&UMTS&2100&49.03&&4\n" +
            "74&HUAWEI DBS3900 (RRU3926)&GSM&900&49.03&&1\n" +
            "75&HUAWEI DBS3900 (RRU3926)&GSM&900&49.03&&2\n" +
            "76&HUAWEI DBS3900 (RRU3926)&GSM&900&49.08&&3\n" +
            "77&HUAWEI DBS3900 (RRU3926)&GSM&900&49.03&&4\n" +
            "78&HUAWEI DBS3900 (RRU3926)&GSM&900&49.03&&5\n" +
            "79&HUAWEI DBS3900 (RRU3926)&GSM&900&48.57&&6\n" +
            "80&HUAWEI DBS3900 (RRU3926)&GSM&900&48.45&&7\n" +
            "81&HUAWEI DBS3900 (RRU3926)&GSM&900&47.48&&8\n" +
            "82&HUAWEI DBS3900 (RRU3936)&GSM&900&49.03&Bổ sung&1\n" +
            "83&HUAWEI DBS3900 (RRU3936)&GSM&900&49.03&Bổ sung&2\n" +
            "84&HUAWEI DBS3900 (RRU3936)&GSM&900&49.08&Bổ sung&3\n" +
            "85&HUAWEI DBS3900 (RRU3936)&GSM&900&49.03&Bổ sung&4\n" +
            "86&HUAWEI DBS3900 (RRU3936)&GSM&900&49.03&Bổ sung&5\n" +
            "87&HUAWEI DBS3900 (RRU3936)&GSM&900&48.57&Bổ sung&6\n" +
            "88&HUAWEI DBS3900 (RRU3936)&GSM&900&48.45&Bổ sung&7\n" +
            "89&HUAWEI DBS3900 (RRU3936)&GSM&900&47.48&Bổ sung&8\n" +
            "90&HUAWEI DBS3900 (RRU3936)&GSM&1800&49.03&Bổ sung&1\n" +
            "91&HUAWEI DBS3900 (RRU3936)&GSM&1800&49.03&Bổ sung&2\n" +
            "92&HUAWEI DBS3900 (RRU3936)&GSM&1800&49.08&Bổ sung&3\n" +
            "93&HUAWEI DBS3900 (RRU3936)&GSM&1800&49.03&Bổ sung&4\n" +
            "94&HUAWEI DBS3900 (RRU3936)&GSM&1800&49.03&Bổ sung&5\n" +
            "95&HUAWEI DBS3900 (RRU3936)&GSM&1800&48.57&Bổ sung&6\n" +
            "96&HUAWEI DBS3900 (RRU3936)&GSM&1800&48.45&Bổ sung&7\n" +
            "97&HUAWEI DBS3900 (RRU3936)&GSM&1800&47.48&Bổ sung&8\n" +
            "98&HUAWEI DBS3900 (RRU3008 V1)&GSM&900&46.02&&1\n" +
            "99&HUAWEI DBS3900 (RRU3008 V1)&GSM&900&49.03&&2\n" +
            "100&HUAWEI DBS3900 (RRU3008 V1)&GSM&900&47.78&&3\n" +
            "101&HUAWEI DBS3900 (RRU3008 V1)&GSM&900&47.78&&4\n" +
            "102&HUAWEI DBS3900 (RRU3008 V1)&GSM&900&47.78&&5\n" +
            "103&HUAWEI DBS3900 (RRU3008 V1)&GSM&900&47.78&&6\n" +
            "104&HUAWEI DBS3900 (RRU3008 V1)&GSM&900&46.90&&7\n" +
            "105&HUAWEI DBS3900 (RRU3008 V1)&GSM&900&46.43&&8\n" +
            "106&HUAWEI DBS3900 (RRU3008 V2)&GSM&900&46.02&&1\n" +
            "107&HUAWEI DBS3900 (RRU3008 V2)&GSM&900&49.03&&2\n" +
            "108&HUAWEI DBS3900 (RRU3008 V2)&GSM&900&47.78&&3\n" +
            "109&HUAWEI DBS3900 (RRU3008 V2)&GSM&900&49.03&&4\n" +
            "110&HUAWEI DBS3900 (RRU3008 V2)&GSM&900&48.13&&5\n" +
            "111&HUAWEI DBS3900 (RRU3008 V2)&GSM&900&48.92&&6\n" +
            "112&HUAWEI DBS3900 (RRU3008 V2)&GSM&900&48.45&&7\n" +
            "113&HUAWEI DBS3900 (RRU3008 V2)&GSM&900&49.03&&8\n" +
            "114&HUAWEI DBS3900 (RRU3008 V1)&GSM&1800&46.02&&1\n" +
            "115&HUAWEI DBS3900 (RRU3008 V1)&GSM&1800&49.03&&2\n" +
            "116&HUAWEI DBS3900 (RRU3008 V1)&GSM&1800&47.78&&3\n" +
            "117&HUAWEI DBS3900 (RRU3008 V1)&GSM&1800&47.78&&4\n" +
            "118&HUAWEI DBS3900 (RRU3008 V1)&GSM&1800&47.78&&5\n" +
            "119&HUAWEI DBS3900 (RRU3008 V1)&GSM&1800&47.78&&6\n" +
            "120&HUAWEI DBS3900 (RRU3008 V1)&GSM&1800&46.90&&7\n" +
            "121&HUAWEI DBS3900 (RRU3008 V1)&GSM&1800&46.43&&8\n" +
            "122&HUAWEI DBS3900 (RRU3952)&UMTS&2100&47.78&Bổ sung&1\n" +
            "123&HUAWEI DBS3900 (RRU3952)&UMTS&2100&50.79&Bổ sung&2\n" +
            "124&HUAWEI DBS3900 (RRU3952)&UMTS&2100&49.54&Bổ sung&3\n" +
            "125&HUAWEI DBS3900 (RRU3952)&UMTS&2100&50.79&Bổ sung&4\n" +
            "126&NOKIA FLEXI EDGE BTS&GSM&900&46.99&&2\n" +
            "127&NOKIA FLEXI EDGE BTS&GSM&900&46.99&&4\n" +
            "128&NOKIA FLEXI EDGE BTS&GSM&1800&46.99&&2\n" +
            "129&NOKIA FLEXI EDGE BTS&GSM&1800&46.99&&4\n" +
            "130&NOKIA FLEXI WCDMA BTS&UMTS&2100&46.99&&1\n" +
            "131&NOKIA FLEXI WCDMA BTS&UMTS&2100&46.99&&2\n" +
            "132&NOKIA FLEXI WCDMA BTS&UMTS&2100&46.99&&3\n" +
            "133&NOKIA FLEXI WCDMA BTS&UMTS&2100&46.99&&4\n" +
            "134&NOKIA FLEXI MULTIRADIO 10 BTS (FHDB)&GSM&900&47.78&&1\n" +
            "135&NOKIA FLEXI MULTIRADIO 10 BTS (FHDB)&GSM&900&47.78&&2\n" +
            "136&NOKIA FLEXI MULTIRADIO 10 BTS (FHDB)&GSM&900&47.78&&3\n" +
            "137&NOKIA FLEXI MULTIRADIO 10 BTS (FHDB)&GSM&900&47.78&&4\n" +
            "138&NOKIA FLEXI MULTIRADIO 10 BTS (FHEB)&GSM&1800&47.78&&1\n" +
            "139&NOKIA FLEXI MULTIRADIO 10 BTS (FHEB)&GSM&1800&47.78&&2\n" +
            "140&NOKIA FLEXI MULTIRADIO 10 BTS (FHEB)&GSM&1800&47.78&&3\n" +
            "141&NOKIA FLEXI MULTIRADIO 10 BTS (FHEB)&GSM&1800&47.78&&4\n" +
            "142&NOKIA FLEXI MULTIRADIO 10 BTS (FXDB)&GSM&900&49.03&&1\n" +
            "143&NOKIA FLEXI MULTIRADIO 10 BTS (FXDB)&GSM&900&49.03&&2\n" +
            "144&NOKIA FLEXI MULTIRADIO 10 BTS (FXDB)&GSM&900&49.03&&3\n" +
            "145&NOKIA FLEXI MULTIRADIO 10 BTS (FXDB)&GSM&900&49.03&&4\n" +
            "146&NOKIA FLEXI MULTIRADIO 10 BTS (FXEB)&GSM&1800&49.03&&1\n" +
            "147&NOKIA FLEXI MULTIRADIO 10 BTS (FXEB)&GSM&1800&49.03&&2\n" +
            "148&NOKIA FLEXI MULTIRADIO 10 BTS (FXEB)&GSM&1800&49.03&&3\n" +
            "149&NOKIA FLEXI MULTIRADIO 10 BTS (FXEB)&GSM&1800&49.03&&4\n" +
            "150&NOKIA FLEXI MULTIRADIO 10 BTS (FRGT)&UMTS&2100&49.03&&1\n" +
            "151&NOKIA FLEXI MULTIRADIO 10 BTS (FRGT)&UMTS&2100&49.03&&2\n" +
            "152&NOKIA FLEXI MULTIRADIO 10 BTS (FRGT)&UMTS&2100&49.03&&3\n" +
            "153&NOKIA FLEXI MULTIRADIO 10 BTS (FRGT)&UMTS&2100&49.03&&4\n" +
            "154&NOKIA FLEXI MULTIRADIO 10 BTS (FRGY)&UMTS&2100&47.78&&1\n" +
            "155&NOKIA FLEXI MULTIRADIO 10 BTS (FRGY)&UMTS&2100&47.78&&2\n" +
            "156&NOKIA FLEXI MULTIRADIO 10 BTS (FRGY)&UMTS&2100&47.78&&3\n" +
            "157&NOKIA FLEXI MULTIRADIO 10 BTS (FRGY)&UMTS&2100&47.78&&4\n" +
            "158&NOKIA FLEXI MULTIRADIO 10 BTS (FRGU)&UMTS&2100&47.78&&1\n" +
            "159&NOKIA FLEXI MULTIRADIO 10 BTS (FRGU)&UMTS&2100&47.78&&2\n" +
            "160&NOKIA FLEXI MULTIRADIO 10 BTS (FRGU)&UMTS&2100&47.78&&3\n" +
            "161&NOKIA FLEXI MULTIRADIO 10 BTS (FRGX)&UMTS&2100&49.03&&1\n" +
            "162&NOKIA FLEXI MULTIRADIO 10 BTS (FRGX)&UMTS&2100&49.03&&2\n" +
            "163&NOKIA FLEXI MULTIRADIO 10 BTS (FRGX)&UMTS&2100&49.03&&3\n" +
            "164&NOKIA FLEXI MULTIRADIO 10 BTS (FRGQ)&UMTS&2100&49.03&&1\n" +
            "165&NOKIA FLEXI MULTIRADIO 10 BTS (FRGQ)&UMTS&2100&49.03&&2\n" +
            "166&NOKIA FLEXI MULTIRADIO 10 BTS (FRGQ)&UMTS&2100&49.03&&3\n" +
            "167&NOKIA FLEXI MULTIRADIO AIRSCALE (FHED)&LTE&1800&46.02&&1\n" +
            "168&NOKIA FLEXI MULTIRADIO AIRSCALE (FHED)&LTE&1800&46.02&&2\n" +
            "169&NOKIA FLEXI MULTIRADIO AIRSCALE (AHEB)&LTE&1800&46.02&&1\n" +
            "170&NOKIA FLEXI MULTIRADIO AIRSCALE (FRGU)&LTE&2100&47.78&Bổ sung&1\n" +
            "171&VTTEK4G-184b (vRRU-184b)&LTE&1800&46.02&&1\n" +
            "172&ZTE ZXSDR B8200 (ZXSDR R8840)&UMTS&2100&47.78&&1\n" +
            "173&ZTE ZXSDR B8200 (ZXSDR R8840)&UMTS&2100&47.78&&2\n" +
            "174&ZTE ZXSDR B8200 (ZXSDR R8840)&UMTS&2100&47.78&&3\n" +
            "175&ZTE ZXSDR B8200 (ZXSDR R8840)&UMTS&2100&47.78&&4\n" +
            "176&ZTE ZXSDR B8200 (ZXSDR R8881)&UMTS&2100&49.03&&1\n" +
            "177&ZTE ZXSDR B8200 (ZXSDR R8881)&UMTS&2100&49.03&&2\n" +
            "178&ZTE ZXSDR B8200 (ZXSDR R8881)&UMTS&2100&49.03&&3\n" +
            "179&ZTE ZXSDR B8200 (ZXSDR R8881)&UMTS&2100&49.03&&4\n" +
            "180&ZTE ZXSDR BS8700 (ZXSDR R8881)&UMTS&2100&49.03&&1\n" +
            "181&ZTE ZXSDR BS8700 (ZXSDR R8881)&UMTS&2100&49.03&&2\n" +
            "182&ZTE ZXSDR BS8700 (ZXSDR R8881)&UMTS&2100&49.03&&3\n" +
            "183&ZTE ZXSDR BS8700 (ZXSDR R8881)&UMTS&2100&49.03&&4\n" +
            "184&ZTE ZXSDR BS8700 (ZXSDR A8602)&UMTS&2100&46.02&Anten tích hợp &1\n" +
            "185&ZTE ZXSDR BS8700 (ZXSDR A8602)&UMTS&2100&46.02&Anten tích hợp &2\n" +
            "186&ZTE ZXSDR BS8700 (ZXSDR A8602)&UMTS&2100&46.02&Anten tích hợp &3\n" +
            "187&ZTE ZXSDR BS8700 (ZXSDR A8602)&UMTS&2100&46.02&Anten tích hợp &4\n" +
            "188&ZTE ZXSDR BS8800&UMTS&2100&47.78&&1\n" +
            "189&ZTE ZXSDR BS8800&UMTS&2100&47.78&&2\n" +
            "190&ZTE ZXSDR BS8800&UMTS&2100&47.78&&3\n";
    static final String ListAnten = "1&ACE SXPWL4WH-14/15-65/65V-iVT&Định hướng&900&14.5&1.6& 2–10\n" +
            "2&ACE SXPWL4WH-14/15-65/65V-iVT&Định hướng&1800&14.5&1.6& 2–10\n" +
            "3&ACE SXPWL4WH-14/15-65/65V-iVT&Định hướng&2100&14.5&1.6& 2–10\n" +
            "4&Agisson A19451803&Định hướng&1800&17.8&1.306&2\n" +
            "5&Agisson A19451803&Định hướng&1800&17.7&1.306&6\n" +
            "6&Agisson A19451803&Định hướng&1800&17.5&1.306&10\n" +
            "7&Agisson A19451803&Định hướng&2100&18&1.306&2\n" +
            "8&Agisson A19451803&Định hướng&2100&17.7&1.306&6\n" +
            "9&Agisson A19451803&Định hướng&2100&17.5&1.306&10\n" +
            "10&Agisson DX-1710-2170-65-18i-M-0575&Định hướng&1800&18&1.306&0-10\n" +
            "11&Agisson DX-1710-2170-65-18i-M-0575&Định hướng&2100&18&1.306&0-10\n" +
            "12&Agisson DX-806-960-65-18i-0F&Định hướng&900&17.8&2.57&0\n" +
            "13&Agisson DX-806-960-65-18i-6F&Định hướng&900&17.8&2.57&6\n" +
            "14&Andrew 858DG65T6ESY&Định hướng&900&17.8&2.45&6\n" +
            "15&Andrew 932DG65T6EKL&Định hướng&1800&18&1.3&6\n" +
            "16&Andrew DB858DG65ESY&Định hướng&900&18.1&2.44&0\n" +
            "17&Andrew HBV-3020DS-T0M&Định hướng&1800&21.8&1.96&0\n" +
            "18&Andrew HBV-3020DS-T0M&Định hướng&2100&22.5&1.96&0\n" +
            "19&Andrew HBX-6516DS-T0M&Định hướng&1800&17.9&1.3&0\n" +
            "20&Andrew HBX-6516DS-T0M&Định hướng&2100&18.1&1.3&0\n" +
            "21&Andrew HBX-6516DS-T6M&Định hướng&1800&17.6&1.3&6\n" +
            "22&Andrew HBX-6516DS-T6M&Định hướng&2100&17.6&1.3&6\n" +
            "23&Andrew HBX-6516DS-VTM&Định hướng&1800&17.7&1.3056&0-10\n" +
            "24&Andrew HBX-6516DS-VTM&Định hướng&2100&18&1.3056&0-10\n" +
            "25&Anten tích hợp trong thiết bị ZTE ZXSDR A8602&Định hướng&1800&15&0.77&-2-12\n" +
            "26&Anten tích hợp trong thiết bị ZTE ZXSDR A8602&Định hướng&2100&15&0.77&-2-12\n" +
            "27&Argus NPX204F-0&Định hướng&1800&16&0.55&0\n" +
            "28&Argus NPX204F-0&Đẳng hướng&2100&17&0.55&0\n" +
            "29&Argus NPX204F-6&Đẳng hướng&1800&16&0.55&6\n" +
            "30&Argus NPX204F-6&Định hướng&2100&17&0.55&6\n" +
            "31&Cellmax CMA-B/3324&Định hướng&1800&23.3&2.07&0; 2\n" +
            "32&Cellmax CMA-B/3324&Định hướng&2100&24.4&2.07&0; 2\n" +
            "33&Cellmax CMA-BTLBHH/6517 / 2121&Định hướng&900&17.1&2.06&0 - 8\n" +
            "34&Cellmax CMA-BTLBHH/6517 / 2121&Định hướng&1800&20.4&2.06&0 - 6\n" +
            "35&Cellmax CMA-BTLBHH/6517 / 2121&Định hướng&2100&21.3&2.06&0 - 6\n" +
            "36&Commscope 2HH-38A-R4-V2&Định hướng&1800&19.3&1.224&2-10\n" +
            "37&Commscope 2HH-38A-R4-V2&Định hướng&1900&19.7&1.224&2-10\n" +
            "38&Commscope 2HH-38A-R4-V2&Định hướng&2100&20&1.224&2-10\n" +
            "39&Commscope ARGUS NPX212R&Định hướng&1800&20&1.6&0-10\n" +
            "40&Commscope ARGUS NPX212R&Định hướng&2100&21&1.6&0-10\n" +
            "41&Commscope CSH-6516A-VT&Định hướng&1800&17.1&1.989&0\n" +
            "42&Commscope CSH-6516A-VT&Định hướng&2100&17.6&1.989&0\n" +
            "43&Commscope HBXX-3817TB1-A2M&Định hướng&1800&18.7&1.39&0\n" +
            "44&Commscope HBXX-3817TB1-A2M&Định hướng&1800&19&1.39&5\n" +
            "45&Commscope HBXX-3817TB1-A2M&Định hướng&1800&18.9&1.39&10\n" +
            "46&Commscope HBXX-3817TB1-A2M&Định hướng&2100&19.5&1.39&0\n" +
            "47&Commscope HBXX-3817TB1-A2M&Định hướng&2100&19.7&1.39&5\n" +
            "48&Commscope HBXX-3817TB1-A2M&Định hướng&2100&19.2&1.39&10\n" +
            "49&Commscope HBXX-3817TB1-A2M/ Commscope HBXX-3817TB1-VTM&Định hướng&1800&18.7&1.39&0\n" +
            "50&Commscope HBXX-3817TB1-A2M/ Commscope HBXX-3817TB1-VTM&Định hướng&1800&19&1.39&5\n" +
            "51&Commscope HBXX-3817TB1-A2M/ Commscope HBXX-3817TB1-VTM&Định hướng&1800&18.9&1.39&10\n" +
            "52&Commscope HBXX-3817TB1-A2M/ Commscope HBXX-3817TB1-VTM&Định hướng&2100&19.5&1.39&0\n" +
            "53&Commscope HBXX-3817TB1-A2M/ Commscope HBXX-3817TB1-VTM&Định hướng&2100&19.7&1.39&5\n" +
            "54&Commscope HBXX-3817TB1-A2M/ Commscope HBXX-3817TB1-VTM&Định hướng&2100&19.2&1.39&10\n" +
            "55&Commscope HBXX-3817TB1-VTM&Định hướng&1800&19.3&1.39&0-10\n" +
            "56&Commscope HBXX-3817TB1-VTM&Định hướng&2100&19.9&1.39&0-10\n" +
            "57&Commscope HBXX-3817TB-VTM&Định hướng&1800&19.2&1.39&0-10\n" +
            "58&Commscope HBXX-3817TB-VTM&Định hướng&2100&19.8&1.39&0-10\n" +
            "59&Commscope HBXX-9016DS-VTM&Định hướng&1800&17.5&1.896&0-6\n" +
            "60&Commscope HBXX-9016DS-VTM&Định hướng&2100&17.5&1.896&0-6\n" +
            "61&Commscope HWXX-6516DS2-A2M&Định hướng&1800&17.4&1.39&0-10\n" +
            "62&Commscope HWXX-6516DS2-A2M&Định hướng&2100&17.9&1.39&0-10\n" +
            "63&Commscope HWXX-6516DS2-A2M&Định hướng&2300&18.2&1.39&0-10\n" +
            "64&Commscope HWXX-6516DS2-A2M&Định hướng&2600&18.5&1.39&0-10\n" +
            "65&Commscope NNNOX310R-V3&Định hướng&1800&17.5&1.748&0-10\n" +
            "66&Commscope NNNOX310R-V3&Định hướng&2100&18&1.748&0-10\n" +
            "67&Commscope RV4-65D-R5-V5&Định hướng&900&17.4&2.688&0–10\n" +
            "68&Commscope RV4-65D-R5-V5&Định hướng&1800&16.8&2.688& 2–12\n" +
            "69&Commscope RV4-65D-R5-V5&Định hướng&2100&17.4&2.688& 2–12\n" +
            "70&Commscope RV65A-1X2&Định hướng&900&14.3&1.412&0-15\n" +
            "71&Commscope RV65A-1X2&Định hướng&1800&17.1&1.412&2-12\n" +
            "72&Commscope RV65A-1X2&Định hướng&2100&17.5&1.412&2-12\n" +
            "73&Commscope RV65A-1X2&Định hướng&2500&18.1&1.412&2-12\n" +
            "74&Commscope RVV-65D-M&Định hướng&900&17.3&2.645&0-10\n" +
            "75&Commscope RVV-65D-M&Định hướng&1800&17.1&2.645&2-12\n" +
            "76&Commscope RVV-65D-M&Định hướng&2100&17.7&2.645&2-12\n" +
            "77&Commscope RVV-65D-M&Định hướng&2500&17.9&2.645&2-12\n" +
            "78&Commscope RVV-65D-M&Định hướng&2700&18.5&2.645&2-12\n" +
            "79&Grentech SGR-DGCTX-BG-G7&Định hướng&900&7&0.21&0\n" +
            "80&Grentech SGR-DGCTX-BG-G7&Định hướng&1800&9&0.21&0\n" +
            "81&Grentech SGR-DGCTX-BG-G7&Định hướng&2100&9&0.21&0\n" +
            "82&Grentech SGR-DGCTX-BG-G7/8&Định hướng&900&7&0.21&0\n" +
            "83&Grentech SGR-DGCTX-BG-G7/8&Định hướng&1800&9&0.21&0\n" +
            "84&Grentech SGR-DGCTX-BG-G7/8&Định hướng&2100&9&0.21&0\n" +
            "85&Huawei ADU451816V02&Định hướng&1800&17.4&1.365&0-12\n" +
            "86&Huawei ADU451816V02&Định hướng&2100&17.9&1.365&0-12\n" +
            "87&Huawei ADU451816V02&Định hướng&2300&18.2&1.365&0-12\n" +
            "88&Huawei ADU451816V02&Định hướng&2500&18.4&1.365&0-12\n" +
            "89&Huawei ADU4518R9&Định hướng&900&14.7&1.499&0-15\n" +
            "90&Huawei ADU4518R9&Định hướng&1800&16.8&1.499&2-12\n" +
            "91&Huawei ADU4518R9&Định hướng&2100&17.2&1.499&2-12\n" +
            "92&Huawei AMB4520R0&Định hướng&1800&19.2&1.468&0-10\n" +
            "93&Huawei AMB4520R0&Định hướng&2100&19.7&1.468&0-10\n" +
            "94&Huawei APE4517R00&Định hướng&900&17&2.528&0–10\n" +
            "95&Huawei APE4517R00&Định hướng&1800&17.1&2.528&0–10\n" +
            "96&Huawei APE4517R00&Định hướng&2100&17.3&2.528&0–10\n" +
            "97&HXTDJBSD 870-1880-17065-F-D&Định hướng&900&17.5&2.6&6\n" +
            "98&HXTDJBSD 870-1880-17065-F-D&Định hướng&1800&17.5&2.6&6\n" +
            "99&Kathrein 739 490&Định hướng&1800&15.5&0.66&0\n" +
            "100&Kathrein 739496&Định hướng&1800&18&1.3&6\n" +
            "101&Kathrein 739624&Định hướng&900&18&2.58&0\n" +
            "102&Kathrein 739630&Định hướng&900&18&2.58&0\n" +
            "103&Kathrein 739634&Định hướng&900&17&1.93&6\n" +
            "104&Kathrein 739636&Định hướng&900&18&2.58&6\n" +
            "105&Kathrein 739650&Định hướng&900&18.85&2.58&0\n" +
            "106&Kathrein 741336&Định hướng&900&17.5&2.58&6\n" +
            "107&Kathrein 741336&Định hướng&1800&17.5&2.58&6\n" +
            "108&Kathrein 742214&Định hướng&1800&17.5&1.14&0-8\n" +
            "109&Kathrein 742214&Định hướng&2100&18&1.14&0-8\n" +
            "110&Kathrein 742215&Định hướng&1800&17.7&1.302&0-10\n" +
            "111&Kathrein 742215&Định hướng&2100&18&1.302&0-10\n" +
            "112&Kathrein 742222V01&Định hướng&900&11.8&0.579&0\n" +
            "113&Kathrein 742222V01&Định hướng&1800&12.5&0.579&0\n" +
            "114&Kathrein 742222V01&Định hướng&2100&13.6&0.579&0\n" +
            "115&Kathrein 742225&Định hướng&900&16.8&2.52&0.5-7\n" +
            "116&Kathrein 742225&Định hướng&1800&17.8&2.52&0- 6\n" +
            "117&Kathrein 742225&Định hướng&2100&18.3&2.52&0- 6\n" +
            "118&Kathrein 742270V03&Định hướng&900&14.6&1.384&0-14\n" +
            "119&Kathrein 742270V03&Định hướng&1800&17.1&1.384&0-8\n" +
            "120&Kathrein 742270V03&Định hướng&2100&17.1&1.384&0-8\n" +
            "121&Kathrein 800 10204&Định hướng&900&17.7&2.254&0\n" +
            "122&Kathrein 800 10208&Định hướng&900&18&2.574&6\n" +
            "123&Kathrein 800 10215&Định hướng&900&18&2.57&0\n" +
            "124&Kathrein 800 10294&Định hướng&900&17&1.93&6\n" +
            "125&Kathrein 800 10426&Định hướng&1800&17.9&1.3&2\n" +
            "126&Kathrein 800 10426&Định hướng&2100&18.3&1.3&2\n" +
            "127&Kathrein 800 10428&Định hướng&1800&17.7&1.302&6\n" +
            "128&Kathrein 800 10428&Định hướng&2100&18.1&1.302&6\n" +
            "129&Kathrein 80010622V01&Định hướng&1800&17.4&1.471&2-14\n" +
            "130&Kathrein 80010622V01&Định hướng&2100&18&1.471&2-14\n" +
            "131&Kathrein 80010622V01&Định hướng&2500&18.6&1.471&2-14\n" +
            "132&Kathrein 80010622V01&Định hướng&2700&18.9&1.471&2-14\n" +
            "133&Kathrein 80010761&Định hướng&1800&11&0.278&3\n" +
            "134&Kathrein 80010761&Định hướng&2100&11.5&0.278&3\n" +
            "135&Kathrein 80010866 (R1)&Định hướng&900&17.3&2.44&1 - 10\n" +
            "136&Kathrein 80010866 (Y1)&Định hướng&1800&17.4&2.44&2.5 - 12\n" +
            "137&Kathrein 80010866 (Y1)&Định hướng&2100&17.9&2.44&2.5 - 12\n" +
            "138&Kathrein 80010866 (Y2)&Định hướng&1800&17.4&2.44&2.5 - 12\n" +
            "139&Kathrein 80010866 (Y2)&Định hướng&2100&18&2.44&2.5 - 12\n" +
            "140&Kathrein CS 7276111&Định hướng&2100&18&1.3&0\n" +
            "141&Kathrein KRE 101 1985/1&Định hướng&2100&18&1.31&0-10\n" +
            "142&M3 D-XX-DWH-17-65-VT-DM-V-A&Định hướng&1800&17.3&1.43&0-8\n" +
            "143&M3 D-XX-DWH-17-65-VT-DM-V-A&Định hướng&2100&17.7&1.43&0-8\n" +
            "144&M3 D-XX-DWH-17-65-VT-DM-V-A&Định hướng&2500&18&1.43&0-8\n" +
            "145&M3 D-XX-WLH-14(17)-65-VT-DM-V-A&Định hướng&900&14.5&1.58&0-12\n" +
            "146&M3 D-XX-WLH-14(17)-65-VT-DM-V-A&Định hướng&1800&16.8&1.58&0-10\n" +
            "147&M3 D-XX-WLH-14(17)-65-VT-DM-V-A&Định hướng&2100&17.2&1.58&0-10\n" +
            "148&RFS APX15GV-15DWVB-C&Định hướng&900&16&2.08&0-10\n" +
            "149&RFS APX15GV-15DWVB-C&Định hướng&1800&18.3&2.08&0-10\n" +
            "150&RFS APX15GV-15DWVB-C&Định hướng&2100&18.5&2.08&0-10\n" +
            "151&RFS APX18-206516L-CT0&Định hướng&1800&17.6&1.35&0\n" +
            "152&RFS APX18-206516L-CT0&Định hướng&2100&18.6&1.35&0\n" +
            "153&RFS APX18-206516L-CT2&Định hướng&1800&17.5&1.35&2\n" +
            "154&RFS APX18-206516L-CT2&Định hướng&2100&18&1.35&2\n" +
            "155&RFS APX18-206516L-CT6&Định hướng&1800&17.5&1.349&6\n" +
            "156&RFS APX18-206516L-CT6&Định hướng&2100&18&1.349&6\n" +
            "157&RFS APX86-906515L-CT0&Định hướng&900&17&2.08&0\n" +
            "158&RFS APX86-906515L-CT6&Định hướng&900&17&2.08&6\n" +
            "159&RFS APX86-906516L-CT0&Định hướng&900&18&2.6&0\n" +
            "160&RFS APX86-906516L-CT6&Định hướng&900&17.5&2.6&6\n" +
            "161&RFS APXV18-206516S-C&Định hướng&1800&18.4&1.35&0-10\n" +
            "162&RFS APXV18-206516S-C&Định hướng&2100&18.4&1.35&0-10\n" +
            "163&RFS APXVLL13N-C-A20&Định hướng&1800&17.5&1.37&0-12\n" +
            "164&RFS APXVLL13N-C-A20&Định hướng&1900&17.6&1.37&0-12\n" +
            "165&RFS APXVLL13N-C-A20&Định hướng&2100&17.7&1.37&0-12\n" +
            "166&RFS APXVLL13N-C-A20&Định hướng&2200-2700&18.5&1.37&0-12\n" +
            "167&Rosenberger  2G6WF-00V&Định hướng&900&16&2.69&0–10\n" +
            "168&Rosenberger  2G6WF-00V&Định hướng&1800&16.2&2.69&0–10\n" +
            "169&Rosenberger  2G6WF-00V&Định hướng&2100&16.9&2.69&0–10\n" +
            "170&Rosenberger 6W22ME-01&Định hướng&1800&19.5&2.09&2–10\n" +
            "171&Rosenberger 6W22ME-01&Định hướng&2100&19.9&2.09&2–10\n" +
            "172&Rosenberger G4WD-00V&Định hướng&900&15.6&1.995&0–10\n" +
            "173&Rosenberger G4WD-00V&Định hướng&1800&15.4&1.995&0–10\n" +
            "174&Rosenberger G4WD-00V&Định hướng&2100&15.6&1.995&0–10\n" +
            "175&Rosenberger G4WHE-10V&Định hướng&900&15.7&2.09&0–10\n" +
            "176&Rosenberger G4WHE-10V&Định hướng&1800&17.2&2.09&0–10\n" +
            "177&Rosenberger G4WHE-10V&Định hướng&2100&18.4&2.09&0–10\n" +
            "178&Rosenberger MB-4UKX33V1-01&Định hướng&1800&18.4&1.218&2-10\n" +
            "179&Rosenberger MB-4UKX33V1-01&Định hướng&1900&18.9&1.218&2-10\n" +
            "180&Rosenberger MB-4UKX33V1-01&Định hướng&2100&19.2&1.218&2-10\n" +
            "181&Rosenberger MB-4WKX33VB1-00&Định hướng&1800&18.7&2.69&2-10\n" +
            "182&Rosenberger MB-4WKX33VB1-00&Định hướng&1900&19.3&2.69&2-10\n" +
            "183&Rosenberger MB-4WKX33VB1-00&Định hướng&2100&19.6&2.69&2-10\n" +
            "184&Rosenberger MB-4WKX33VB1-00&Định hướng&2500&19.4&2.69&2-10\n";
    static final String BangSuyHao = "Connector&Connector&900&0.05\n" +
            "Connector&Connector&1800&0.07\n" +
            "Connector&Connector&2100&0.07\n" +
            "Dualband Combiner&Dualband Combiner&900&0.15\n" +
            "Dualband Combiner&Dualband Combiner&1800&0.25\n" +
            "Dualband Combiner&Dualband Combiner&2100&0.35\n" +
            "Feeder&7/8\"&900&3.67\n" +
            "Feeder&7/8\"&1800&5.04\n" +
            "Feeder&7/8\"&2100&5.53\n" +
            "Feeder&1/2\"&900&7.12\n" +
            "Feeder&1/2\"&1800&10.06\n" +
            "Feeder&1/2\"&2100&10.96\n" +
            "Jumper&1/2\"&900&7.12\n" +
            "Jumper&1/2\"&1800&10.06\n" +
            "Jumper&1/2\"&2100&10.96\n" +
            "Connector&Andrew AL5DF-PS&&0.05";
    //endregion
}
