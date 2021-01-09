package com.example.chirag.googlesignin;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;

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
    static void SaveListEditText_json(String nameFile,File pathFile,ArrayList<EditText> listEditText,ArrayList<String> arrayList) throws JSONException {
        JSONObject obj = new JSONObject();
        for(int i=0;i<listEditText.size();i++)
        {
            obj.put(arrayList.get(i),listEditText.get(i).getText().toString());
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
//    static void SaveListEditText(String nameFile,File pathFile,ArrayList<EditText> listEditText){
//        String text = "";
//        for(EditText edt:listEditText)
//        {
//            if(text.equals(""))
//            {
//                text += edt.getText().toString().trim().replace("\n","");
//            }
//            else text += "&"+ edt.getText().toString().trim().replace("\n","");
//        }
//        saveTextFile(nameFile,text,pathFile);
//    }
    static void ReadListEditText_Json(String nameFile,File pathFile,ArrayList<EditText> listEditText,ArrayList<String> arrayList) throws JSONException {
        File fileData = new File(pathFile,nameFile);
        if(fileData.exists())
        {
           String text = readText(fileData);
           JSONObject jsonObject = new JSONObject(text);

            for(int i=0;i<arrayList.size();i++)
            {
                listEditText.get(i).setText(jsonObject.getString(arrayList.get(i)));
            }
        }

    }
//    static void ReadListEditText(String nameFile,File pathFile,ArrayList<EditText> listEditText){
//        File fileData = new File(pathFile,nameFile);
//        if(fileData.exists())
//        {
//            String[] text = readText(fileData).split("&");
//            for(int i=0;i<text.length;i++)
//            {
//                try{
//                    String data = text[i].trim();
//                    listEditText.get(i).setText(data);
//                }catch (Exception e) {}
//            }
//        }
//
//    }
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
}
