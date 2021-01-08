package com.example.chirag.googlesignin;

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
    static final ArrayList<String> ThietKeTram = new ArrayList<String>(Arrays.asList("MaTram", "DiaDiem", "ToaDo", "ViTriDat", "NgayDo"));
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

}
