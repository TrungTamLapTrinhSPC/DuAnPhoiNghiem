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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

final class SPC { private SPC() {}

    /**
     *Vị trisbangr thiết kế trạm
     */

    static final String DuongDanFileThietKeTram = "/DuLieu/ThietKeTram.txt";
    static final String DuongDanThuMucHinhAnh = "/HinhAnh";
    static final ArrayList<String> ThietKeTram = new ArrayList<String>(Arrays.asList("MaTram", "DiaDiem", "ToaDo", "ViTriDat", "ChieuCaoNha", "ChieuRong", "ChieuDai"));
    static final ArrayList<String> ThietKeCot = new ArrayList<String>(Arrays.asList("TenCot", "ChieuCaoCot", "SoChan", "KichThuocCot", "ViTriX", "ViTriY"));
    static final ArrayList<String> ThietKeBTS = new ArrayList<String>(Arrays.asList("TenTramGoc", "ChungLoaiThietBi", "BangTanHoatDong"));
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
    static void SaveListEditText(String nameFile,File pathFile,ArrayList<EditText> listEditText){
        String text = "";
        for(EditText edt:listEditText)
        {
            if(text.equals(""))
            {
                text += edt.getText().toString().trim().replace("\n","");
            }
            else text += "&"+ edt.getText().toString().trim().replace("\n","");
        }
        saveTextFile(nameFile,text,pathFile);
    }
    static void ReadListEditText(String nameFile,File pathFile,ArrayList<EditText> listEditText){
        File fileData = new File(pathFile,nameFile);
        if(fileData.exists())
        {
            String[] text = readText(fileData).split("&");
            for(int i=0;i<text.length;i++)
            {
                try{
                    listEditText.get(i).setText(text[i].trim());
                }catch (Exception e) {}
            }
        }

    }

}
