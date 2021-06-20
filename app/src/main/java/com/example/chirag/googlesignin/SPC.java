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
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.common.api.internal.LifecycleFragment;
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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

final class SPC {
    private SPC() {
    }

    /**
     * Vị trisbangr thiết kế trạm
     */

    static final String DuongDanFileThietKeTram = "/DuLieu/Data.xml";
    static final String DuongDanThuMucHinhAnh = "/HinhAnh";
    static final String DuongDanThuMucDuLieu = "/DuLieu";
    static final File pathDataApp_PNDT = new File(Environment.getExternalStorageDirectory(), "DataAppPNDT");
    static final File pathTemplate = new File(Environment.getExternalStorageDirectory(), "Template");
    static final ArrayList<String> listLoaiAnten = new ArrayList<String>(Arrays.asList("Định hướng"));

    static final ArrayList<String> listBangTan = new ArrayList<String>(Arrays.asList("900", "1800", "900/1800", "2100", "2300"));
    static final ArrayList<String> listLoaiCongTrinh = new ArrayList<String>(Arrays.asList("Khu nhà ở", "Khu đất trống", "Khu nhà 1 2 tầng", "Khu nhà 3 4 tầng", "Khu nhà xưởng", "Khu nhà cấp 4", "Khu công nghiệp 1 2 tầng", "Khu đường và đồng ruộng", "Khu sân bay, nhà thấp tầng", "Khu bãi xe"));
    static final ArrayList<String> ThietKeTram = new ArrayList<String>(Arrays.asList("MaTram", "DiaDiem", "ToaDo", "NgayDo", "ViTriDat"));
    static final ArrayList<String> TenHinhAnh = new ArrayList<String>(Arrays.asList("Hình ảnh các công trình hướng sector 1", "Hình ảnh các công trình hướng sector 2", "Hình ảnh các công trình hướng sector 3", "Hình ảnh các công trình hướng sector 4", "Hình ảnh các công trình hướng sector 5"));
    static final ArrayList<String> TenHinhAnhTongThe = new ArrayList<String>(Arrays.asList("Hình ảnh biển nhà trạm", "Hình ảnh tổng thể cột anten", "Hình ảnh tổng thể các thiết bị trong phòng máy", "Hình ảnh thiết bị treo trên cột"));
    static final ArrayList<String> ThietKeNhaDatTram = new ArrayList<String>(Arrays.asList("TenCongTrinh", "SoTang", "ChieuCaoNha", "ChieuDai", "ChieuRong"));
    static final ArrayList<String> ThietKeCot = new ArrayList<String>(Arrays.asList("TenCot", "ChieuCaoCot", "SoChan", "KichThuocCot", "ViTriX", "ViTriY", "DanhSachTramGoc"));
    static final ArrayList<String> ThietKeBTS = new ArrayList<String>(Arrays.asList("TenTramGoc", "ChungLoaiThietBi", "BangTanHoatDong", "MangSuDung"));
    static final ArrayList<String> ThietKeThanhPhan = new ArrayList<String>(Arrays.asList("TenThanhPhan", "ChieuDai", "ChungLoai", "SuyHaodB", "SuyHao"));
    static final ArrayList<String> ThietKeCongTrinh = new ArrayList<String>(Arrays.asList("TenCongTrinh", "ChieuCao", "KhoangCach", "SoTang", "GocPhuongVi", "DoDay", "DoRong"));
    static final ArrayList<String> ThietKeAnten = new ArrayList<String>
            (Arrays.asList("TenAnten", "ChungLoaiThietBi", "SoMayPhat", "TongCongSuatPhat1", "TongCongSuatPhat2",
                    "ChungLoaiAnten", "LoaiAnten", "DoTangIch", "BangTanHoatDong", "DoDaiBucXa", "GocNgang", "GocPhuongVi", "DoCaoAnten1", "DoCaoAnten2",
                    "ChungLoaiJumper", "ChieuDaiJumper", "SuyHaodBJumper", "SuyHaoJumper",
                    "ChungLoaiFeeder", "ChieuDaiFeeder", "SuyHaodBFeeder", "SuyHaoFeeder", "TongSuyHao"));



    //    MỚI --------------------------------------------------------------------------------------------------------------%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    static void saveToXml(XML_Tram xml_tram, String nameFile, File pathFile) throws JSONException, SAXException, ParserConfigurationException, IOException, TransformerException {
        saveTextFileTypeXMl(nameFile, pathFile, xml_tram);
    }

    static void saveTextFileTypeXMl(String nameFile, File file, XML_Tram xml_tram) throws SAXException, TransformerException, ParserConfigurationException, IOException {
        if (!file.exists())
            if (!file.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        file = new File(file, nameFile + ".xml");
        writeXml(file, xml_tram);
    }

    static void ReadList_Json(String text, ArrayList<AutoCompleteTextView> listAutoCompleteTextView, ArrayList<String> arrayList) throws JSONException {
        JSONObject jsonObject = new JSONObject(text);
        for (int i = 0; i < arrayList.size(); i++) {
            listAutoCompleteTextView.get(i).setText(jsonObject.getString(arrayList.get(i)));
        }
    }
// END MỚI --------------------------------------------------------------------------------------------------------------%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    //    Cũ --------------------------------------------------------------------------------------------------------------%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    static void SaveListAutoCompleteTextView_json(String nameFile, File pathFile, ArrayList<AutoCompleteTextView> listAutoCompleteTextView, ArrayList<String> arrayList) throws JSONException {
        JSONObject obj = new JSONObject();
        for (int i = 0; i < listAutoCompleteTextView.size(); i++) {
            obj.put(arrayList.get(i), listAutoCompleteTextView.get(i).getText().toString());
        }
        saveTextFile(nameFile, obj.toString(), pathFile);
    }

    static void saveTextFile(String name, String text, File file) {
        String content = text;
        FileOutputStream outputStream;
        try {
            if (!file.exists())
                if (!file.mkdirs()) {
                    Log.d("App", "failed to create directory");
                }
            file = new File(file, name + ".txt");
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
            //Toast.makeText(Table3Activity.this,"Đã lưu thành công", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void ReadListAutoCompleteTextView_Json(String nameFile, File pathFile, ArrayList<AutoCompleteTextView> listAutoCompleteTextView, ArrayList<String> arrayList) throws JSONException {
        File fileData = new File(pathFile, nameFile);
        if (fileData.exists()) {
            String text = readText(fileData);
            JSONObject jsonObject = new JSONObject(text);
            for (int i = 0; i < arrayList.size(); i++) {
                listAutoCompleteTextView.get(i).setText(jsonObject.getString(arrayList.get(i)));
            }
        }

    }

    static String readText(File file) {
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
//    END Cũ --------------------------------------------------------------------------------------------------------------%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    static final String TaoThietKeAnten(String TenAnten) throws JSONException {
        String thietke = "";
        JSONObject obj = new JSONObject();
        obj.put(ThietKeAnten.get(0), TenAnten);
        for (int i = 1; i < ThietKeAnten.size(); i++) {
            obj.put(ThietKeAnten.get(i), "0");
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

    static final int TimViTri(String key, ArrayList<String> list) {
        int vt = -1;
        vt = list.indexOf(key);
        return vt;
    }

    static final String getLastModified(File file) {
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


    static ArrayList<String> readAllLineText(File file) {
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
            } else {
                return true;
            }
        } else return false;
    }


    static Bitmap GanToaDo(Bitmap bitmap, String MaTram, String ToaDo, String DiaDiem) {
        Bitmap AnhDauRa = null;
        Bitmap newbitmap = null;
        Bitmap bitmap2 = null;
        /**XOAY ẢNH**/
        if (bitmap.getWidth() > bitmap.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } else bitmap2 = bitmap;


        Bitmap.Config config = bitmap2.getConfig();
        config = Bitmap.Config.ARGB_8888;
        newbitmap = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(), config);
        Canvas newcanvas = new Canvas(newbitmap);
        newcanvas.drawBitmap(bitmap2, 0, 0, null);
        Paint painttext = new Paint(Paint.ANTI_ALIAS_FLAG);
        painttext.setColor(Color.WHITE);
        painttext.setTextSize(bitmap2.getWidth() / 35);
        //painttext.setShadowLayer(10f,10f,10f,Color.BLACK);
        Rect rectText = new Rect();
        String text = "Mã trạm:" + MaTram;
        String text2 = ToaDo;
        String text3 = DiaDiem;

        painttext.getTextBounds(text, 0, text.length(), rectText);
        newcanvas.drawText(text, 0, rectText.height(), painttext);
        newcanvas.drawText(text2, 0, 2 * rectText.height(), painttext);
        newcanvas.drawText(text3, 0, 3 * rectText.height(), painttext);

        AnhDauRa = newbitmap;


        return AnhDauRa;
    }

    static ArrayList LayDanhSachThietBi() {
        ArrayList<String> dataThietBi = readAllLineText(new File(pathTemplate, "ListThietBi.txt"));
        ArrayList<String> lstThietBi = new ArrayList<String>();
        for (String itemThietThietBi : dataThietBi) {
            String ThietBi = itemThietThietBi.split("&")[1];
            if (!lstThietBi.contains(ThietBi)) {
                lstThietBi.add(ThietBi);
            }
        }
        return lstThietBi;
    }

    static ArrayList LayDanhSachAnten() {
        ArrayList<String> dataThietBi = readAllLineText(new File(pathTemplate, "ListAnten.txt"));
        ArrayList<String> lstThietBi = new ArrayList<String>();
        for (String itemThietThietBi : dataThietBi) {
            String ThietBi = itemThietThietBi.split("&")[1];
            if (!lstThietBi.contains(ThietBi)) {
                lstThietBi.add(ThietBi);
            }
        }
        return lstThietBi;
    }

    static ArrayList LayDanhSachSuyHao() {
        ArrayList<String> dataThietBi = readAllLineText(new File(pathTemplate, "BangSuyHao.txt"));
        ArrayList<String> lstThietBi = new ArrayList<String>();
        for (String itemThietThietBi : dataThietBi) {
            String ThietBi = itemThietThietBi.split("&")[0];
            if (!lstThietBi.contains(ThietBi)) {
                lstThietBi.add(ThietBi);
            }
        }
        return lstThietBi;
    }

    static void setPopUp_img(Context context, TextView edt, ArrayList<String> arrayList, ImageButton imageButton) {
        if (imageButton != null) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(context, imageButton);

                    for (String s : arrayList) {
                        popupMenu.getMenu().add(s);
                    }
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

                    for (String s : arrayList) {
                        popupMenu.getMenu().add(s);
                    }
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

    static void setPopUp(Context context, AutoCompleteTextView edt, ArrayList<String> arrayList, ImageButton imageButton) {
        ArrayAdapter<String> adapterHT = new ArrayAdapter<String>(context, R.layout.custom_list_item, R.id.text_view_list_item, arrayList);
        edt.setAdapter(adapterHT);
        edt.setThreshold(1);
        edt.setDropDownHeight(400);
        if (imageButton != null) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(context, imageButton);

                    for (String s : arrayList) {
                        popupMenu.getMenu().add(s);
                    }
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

    static ArrayList<String> layCongSuatPhat1(String ChungLoaiThietBi, String BangTanHoatDong) {
        ArrayList<String> CongSuat = new ArrayList<String>();
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate, "ListThietBi.txt"));
        for (String item : listThietBi) {
            if (item.contains(ChungLoaiThietBi) && item.contains(BangTanHoatDong)) {
                String CS = item.split("&")[4];
                CongSuat.add(CS);
            }
        }
        return CongSuat;
    }

    static String layMangSuDung(String ChungLoaiThietBi, String BangTanHoatDong) {
        String CongSuat = "";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate, "ListThietBi.txt"));
        for (String item : listThietBi) {
            if (item.contains(ChungLoaiThietBi) && item.contains(BangTanHoatDong)) {
                CongSuat = item.split("&")[2];
            }
        }
        return CongSuat;
    }

    static String layDoTangIchAnten(String ChungLoaiThietBi, String BangTanHoatDong) {
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate, "ListAnten.txt"));
        for (String item : listThietBi) {
            if (item.contains(ChungLoaiThietBi)) {
                String TanSo = item.split("&")[3];
                if (TanSo.contains(BangTanHoatDong)) {
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

    static String layDoDaiBucXa(String ChungLoaiThietBi, String BangTanHoatDong) {
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate, "ListAnten.txt"));
        for (String item : listThietBi) {
            if (item.contains(ChungLoaiThietBi)) {
                String TanSo = item.split("&")[3];
                if (TanSo.contains(BangTanHoatDong)) {
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

    static String layGocNgang(String ChungLoaiThietBi, String BangTanHoatDong) {
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate, "ListAnten.txt"));
        for (String item : listThietBi) {
            if (item.contains(ChungLoaiThietBi)) {
                String TanSo = item.split("&")[3];
                if (TanSo.contains(BangTanHoatDong)) {
                    String GocNgang = item.split("&")[6];
                    if (GocNgang.contains("-")) {
                        DoTangIchAnten = GocNgang.split("-")[1];
                    } else DoTangIchAnten = GocNgang;
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

    static String laySuyHaodB(String ChungLoaiThietBi, String BangTanHoatDong) {
        String DoTangIchAnten = "0.0";
        ArrayList<String> listThietBi = readAllLineText(new File(pathTemplate, "BangSuyHao.txt"));
        for (String item : listThietBi) {
            if (item.contains(ChungLoaiThietBi) && item.contains(BangTanHoatDong)) {
                DoTangIchAnten = item.split("&")[4];
            }
        }
        return DoTangIchAnten;
    }

    //region Công thức tính
    static String TinhCongSuatPhat2(String CongSuatPhat1) {
        String CongSuatPhat2 = "";
        Double double_CongSuatPhat2 = 0.0;
        if (isNumeric(CongSuatPhat1)) {
            Double CS1 = Double.valueOf(CongSuatPhat1);
            double_CongSuatPhat2 = 10 * Math.log10(CS1 * 1000);
        }
        CongSuatPhat2 = String.valueOf(round(double_CongSuatPhat2, 2));

        return CongSuatPhat2;
    }

    static String TinhSuyHao(String ChieuDai, String SuyHaodB) {
        String SuyHao = "";
        Double double_CongSuatPhat2 = 0.0;
        if (isNumeric(ChieuDai) && isNumeric(SuyHaodB)) {
            Double CS1 = Double.valueOf(ChieuDai);
            Double CS2 = Double.valueOf(SuyHaodB);

            double_CongSuatPhat2 = (CS1 / 100) * CS2;
        }
        SuyHao = String.valueOf(round(double_CongSuatPhat2, 4));

        return SuyHao;
    }

    static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*write xml */
    static void writeXml(File file, XML_Tram tram) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        // root element
        Element root = document.createElement("XmlData");
        document.appendChild(root);
        if (tram.getThietKeTram() != null) {
            Element thietKeTram = document.createElement("ThietKeTram");
            thietKeTram.appendChild(document.createTextNode(tram.getThietKeTram()));
            root.appendChild(thietKeTram);
        }
        if (tram.getThietKeNhaTram() != null) {
            Element thietKeNhaTram = document.createElement("ThietKeNhaTram");
            thietKeNhaTram.appendChild(document.createTextNode(tram.getThietKeNhaTram()));
            root.appendChild(thietKeNhaTram);
        }
        if (tram.getThietKeCTCN() != null) {
            Element thietKeCTCN = document.createElement("ThietKeCTCN");
            thietKeCTCN.appendChild(document.createTextNode(tram.getThietKeCTCN()));
            root.appendChild(thietKeCTCN);
        }


        if (tram.getListBTS() != null) {
            if (tram.getListBTS().size() > 0) {
                for (String jsonBts : tram.getListBTS()) {
                    Element itemBts = document.createElement("TramGoc");
                    itemBts.appendChild(document.createTextNode(jsonBts));
                    root.appendChild(itemBts);
                }
            }
        }
        if (tram.getListCot() != null) {
            if (tram.getListCot().size() > 0) {
                for (int i = 0; i < tram.getListCot().size(); i++) {
                    Element cot = document.createElement("COT");
                    root.appendChild(cot);
                    if (tram.getListCot().get(i).getThietKeCot() != null) {
                        Element thietKeCot = document.createElement("ThietKeCot");
                        thietKeCot.appendChild(document.createTextNode(tram.getListCot().get(i).getThietKeCot()));
                        cot.appendChild(thietKeCot);
                    }
                    if (tram.getListCot().get(i).getListBTS_inCot() != null) {
                        if (tram.getListCot().get(i).getListBTS_inCot().size() > 0) {
                            for (int j = 0; j < tram.getListCot().get(i).getListBTS_inCot().size(); j++) {
                                Element bts = document.createElement("BTS");
                                cot.appendChild(bts);
                                if (tram.getListCot().get(i).getListBTS_inCot().get(j).getTenBTS() != null) {
                                    Element tenBts = document.createElement("TenBTS");
                                    tenBts.appendChild(document.createTextNode(tram.getListCot().get(i).getListBTS_inCot().get(j).getTenBTS()));
                                    bts.appendChild(tenBts);
                                }
                                if (tram.getListCot().get(i).getListBTS_inCot().get(j).getListAnten_inBTS() != null) {
                                    if (tram.getListCot().get(i).getListBTS_inCot().get(j).getListAnten_inBTS().size() > 0) {
                                        for (int e = 0; e < tram.getListCot().get(i).getListBTS_inCot().get(j).getListAnten_inBTS().size(); e++) {
                                            Element Anten = document.createElement("Anten");
                                            bts.appendChild(Anten);
                                            if (tram.getListCot().get(i).getListBTS_inCot().get(j).getListAnten_inBTS().get(e).getThietKeAnTen() != null) {
                                                Element thietKeAnten = document.createElement("ThietKeAnten");
                                                thietKeAnten.appendChild(document.createTextNode(tram.getListCot().get(i).getListBTS_inCot().get(j).getListAnten_inBTS().get(e).getThietKeAnTen()));
                                                Anten.appendChild(thietKeAnten);
                                            }
                                            if (tram.getListCot().get(i).getListBTS_inCot().get(j).getListAnten_inBTS().get(e).getListThanhPhan() != null) {
                                                if (tram.getListCot().get(i).getListBTS_inCot().get(j).getListAnten_inBTS().get(e).getListThanhPhan().size() > 0) {
                                                    for (String x : tram.getListCot().get(i).getListBTS_inCot().get(j).getListAnten_inBTS().get(e).getListThanhPhan()) {
                                                        Element thanhPhan = document.createElement("ThanhPhan");
                                                        thanhPhan.appendChild(document.createTextNode(x));
                                                        Anten.appendChild(thanhPhan);
                                                    }
                                                }
                                            }
                                            if (tram.getListCot().get(i).getListBTS_inCot().get(j).getListAnten_inBTS().get(e).getListCongTrinhThapTang() != null) {
                                                if (tram.getListCot().get(i).getListBTS_inCot().get(j).getListAnten_inBTS().get(e).getListCongTrinhThapTang().size() > 0) {
                                                    for (String y : tram.getListCot().get(i).getListBTS_inCot().get(j).getListAnten_inBTS().get(e).getListCongTrinhThapTang()) {
                                                        Element congTrinhThapTang = document.createElement("CongTrinhThapTang");
                                                        congTrinhThapTang.appendChild(document.createTextNode(y));
                                                        Anten.appendChild(congTrinhThapTang);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("success");
        // create the xml file
        //transform the DOM Object to an XML File
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);
    }

    /*read xml*/
    static XML_Tram readXml(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList nodeListData = doc.getElementsByTagName("XmlData");
        Node nodeData = nodeListData.item(0);
        Element elementData = (Element) nodeData;
        XML_Tram tram = new XML_Tram();
        if (elementData.getElementsByTagName("ThietKeTram").getLength() > 0) {
            tram.setThietKeTram(elementData.getElementsByTagName("ThietKeTram").item(0).getTextContent());
        } else tram.setThietKeTram("");

        if (elementData.getElementsByTagName("ThietKeNhaTram").getLength() > 0) {
            tram.setThietKeNhaTram(elementData.getElementsByTagName("ThietKeNhaTram").item(0).getTextContent());
        } else tram.setThietKeNhaTram("");
        if (elementData.getElementsByTagName("ThietKeCTCN").getLength() > 0) {
            tram.setThietKeCTCN(elementData.getElementsByTagName("ThietKeCTCN").item(0).getTextContent());
        } else tram.setThietKeCTCN("");


        if (elementData.getElementsByTagName("TramGoc").getLength() > 0) {
            List<String> listBTSOfTram = new ArrayList<>();
            for (int i = 0; i < elementData.getElementsByTagName("TramGoc").getLength(); i++) {
                listBTSOfTram.add(elementData.getElementsByTagName("TramGoc").item(i).getTextContent());
            }
            tram.setListBTS(listBTSOfTram);
        }
        NodeList listCot = doc.getElementsByTagName("COT");
        List<XML_Cot> listXML_Cot = new ArrayList<>();
        if (listCot.getLength() > 0) {
            for (int i = 0; i < listCot.getLength(); i++) {
                XML_Cot xml_cot = new XML_Cot();
                Node nodeCot = listCot.item(i);
                Element elementCot = (Element) nodeCot;
                if (elementCot.getElementsByTagName("ThietKeCot").getLength() > 0) {
                    xml_cot.setThietKeCot(elementCot.getElementsByTagName("ThietKeCot").item(0).getTextContent());
                } else xml_cot.setThietKeCot("");
                NodeList nodeListXMl_BTS = elementCot.getElementsByTagName("BTS");
                List<XML_BTS> listXML_BTS = new ArrayList<>();
                if (nodeListXMl_BTS.getLength() > 0) {
                    for (int j = 0; j < nodeListXMl_BTS.getLength(); j++) {
                        XML_BTS xml_bts = new XML_BTS();
                        Node nodeBTS = nodeListXMl_BTS.item(j);
                        Element elementBTS = (Element) nodeBTS;
                        if (elementBTS.getElementsByTagName("TenBTS").getLength() > 0) {
                            xml_bts.setTenBTS(elementBTS.getElementsByTagName("TenBTS").item(0).getTextContent());
                        } else xml_bts.setTenBTS("");


                        NodeList nodeListAnten = elementBTS.getElementsByTagName("Anten");
                        List<XML_Anten> listXML_Anten = new ArrayList<>();
                        if (nodeListAnten.getLength() > 0) {
                            for (int e = 0; e < nodeListAnten.getLength(); e++) {
                                XML_Anten xml_anten = new XML_Anten();
                                Node nodeAnten = nodeListAnten.item(e);
                                Element elementAnten = (Element) nodeAnten;
                                if (elementAnten.getElementsByTagName("ThietKeAnten").getLength() > 0) {
                                    xml_anten.setThietKeAnTen(elementAnten.getElementsByTagName("ThietKeAnten").item(0).getTextContent());
                                } else xml_anten.setThietKeAnTen("");

                                if (elementAnten.getElementsByTagName("ThanhPhan").getLength() > 0) {
                                    List<String> listThanhPhan = new ArrayList<>();
                                    for (int f = 0; f < elementAnten.getElementsByTagName("ThanhPhan").getLength(); f++) {
                                        listThanhPhan.add(elementAnten.getElementsByTagName("ThanhPhan").item(f).getTextContent());
                                    }
                                    xml_anten.setListThanhPhan(listThanhPhan);
                                }

                                if (elementAnten.getElementsByTagName("CongTrinhThapTang").getLength() > 0) {
                                    List<String> listCongTrinhThapTang = new ArrayList<>();
                                    for (int f = 0; f < elementAnten.getElementsByTagName("CongTrinhThapTang").getLength(); f++) {
                                        listCongTrinhThapTang.add(elementAnten.getElementsByTagName("CongTrinhThapTang").item(f).getTextContent());
                                    }
                                    xml_anten.setListCongTrinhThapTang(listCongTrinhThapTang);
                                }
                                listXML_Anten.add(xml_anten);
                            }
                        }
                        xml_bts.setListAnten_inBTS(listXML_Anten);
                        listXML_BTS.add(xml_bts);
                    }
                }
                xml_cot.setListBTS_inCot(listXML_BTS);
                listXML_Cot.add(xml_cot);
            }
        }
        tram.setListCot(listXML_Cot);
        return tram;
    }

    //endregion
    //region Thư viện thiết bị
    static final String ListThietBi = "1&Ericsson RBS 2216&2G&900&39.81&Tập trung\n" +
            "2&Ericsson RBS 2216&2G&900&35.56&Tập trung\n" +
            "3&Ericsson RBS 2216&2G&1800&35.48&Tập trung\n" +
            "4&Ericsson RBS 2216&2G&1800&31.7&Tập trung\n" +
            "5&Ericsson RBS 2206&2G&900&67.6&Tập trung\n" +
            "6&Ericsson RBS 2206&2G&900&63.2&Tập trung\n" +
            "7&Ericsson RBS 2206&2G&1800&47.9&Tập trung\n" +
            "8&Ericsson RBS 2206&2G&1800&44.8&Tập trung\n" +
            "9&Ericsson RBS 2106&2G&900&67.6&Tập trung\n" +
            "10&Ericsson RBS 2106&2G&900&63.2&Tập trung\n" +
            "11&Ericsson RBS 2106&2G&1800&47.9&Tập trung\n" +
            "12&Ericsson RBS 2106&2G&1800&44.8&Tập trung\n" +
            "13&Ericsson RBS 2116&2G&900&35.48&Tập trung\n" +
            "14&Ericsson RBS 2116&2G&900&31.7&Tập trung\n" +
            "15&Ericsson RBS 2116&2G&1800&31.62&Tập trung\n" +
            "16&Ericsson RBS 2116&2G&1800&28.26&Tập trung\n" +
            "17&Ericsson RBS 2111&2G&900/1800&20&Tập trung\n" +
            "18&Ericsson RBS 2308&2G&900&2.5&Tập trung\n" +
            "19&Ericsson RBS 2308&2G&1800&2.2&Tập trung\n" +
            "20&Ericsson RBS 2309&2G&900&5&Tập trung\n" +
            "21&Ericsson RBS 2309&2G&1800&5&Tập trung\n" +
            "22&Nokia Flexi EDGE BTS&2G&900/1800&41.7&Tập trung\n" +
            "22&Nokia Flexi EDGE BTS&2G&900/1800&41.7&Tập trung\n" +
            "23&Nokia Flexi EDGE BTS&2G&900/1800&37.24&Tập trung\n" +
            "23&Nokia Flexi EDGE BTS&2G&900/1800&37.24&Tập trung\n" +
            "24& Nokia Flexi Multiradio BTS GSM/EDGE&2G&900/1800&20&Phân tán\n" +
            "25& Nokia Flexi Multiradio BTS GSM/EDGE&2G&900/1800&40&Phân tán\n" +
            "26& Nokia Flexi Multiradio BTS GSM/EDGE&2G&900/1800&60&Phân tán\n" +
            "27& Nokia Flexi Multiradio BTS GSM/EDGE&2G&900/1800&80&Phân tán\n" +
            "28& Nokia Flexi Multiradio BTS GSM/EDGE&2G&900/1800&80&Phân tán\n" +
            "29& Nokia Flexi Multiradio BTS GSM/EDGE&2G&900/1800&80&Phân tán\n" +
            "30& Nokia Flexi Multiradio 10 BTS GSM/EDGE&2G&900/1800&20&Phân tán\n" +
            "31& Nokia Flexi Multiradio 10 BTS GSM/EDGE&2G&900/1800&40&Phân tán\n" +
            "32& Nokia Flexi Multiradio 10 BTS GSM/EDGE&2G&900/1800&60&Phân tán\n" +
            "33& Nokia Flexi Multiradio 10 BTS GSM/EDGE&2G&900/1800&80&Phân tán\n" +
            "34& Nokia Flexi Multiradio 10 BTS GSM/EDGE&2G&900/1800&100&Phân tán\n" +
            "35& Nokia Flexi Multiradio 10 BTS GSM/EDGE&2G&900/1800&120&Phân tán\n" +
            "36&Huawei BTS 3012&2G&900&95.28&Tập trung\n" +
            "37&Huawei BTS 3012&2G&900&44.57&Tập trung\n" +
            "38&Huawei BTS 3012&2G&1800&95.28&Tập trung\n" +
            "39&Huawei BTS 3012&2G&1800&44.57&Tập trung\n" +
            "40&Huawei BTS 3900&2G&900&90&Tập trung\n" +
            "41&Huawei BTS 3900&2G&900&40&Tập trung\n" +
            "42&Huawei BTS 3900&2G&1800&80&Tập trung\n" +
            "43&Huawei BTS 3900&2G&1800&36&Tập trung\n" +
            "44&Huawei DBS 3900&2G&900/1800&40&Phân tán\n" +
            "45&Huawei DBS 3900&2G&900/1800&20&Phân tán\n" +
            "46&Huawei DBS 3900&2G&900/1800&7&Phân tán\n" +
            "47&Ericsson RBS 6601&2G&900&40&Phân tán\n" +
            "48&Ericsson RBS 6601&2G&900&80&Phân tán\n" +
            "49&Ericsson RBS 6601&2G&1800&40&Phân tán\n" +
            "50&Ericsson RBS 6601&2G&1800&80&Phân tán\n" +
            "51&Ericsson RBS 3206&3G&2100&20&Tập trung\n" +
            "52&Ericsson RBS 3206&3G&2100&40&Tập trung\n" +
            "53&Ericsson RBS 3418&3G&2100&20&Phân tán\n" +
            "54&Ericsson RBS 3418&3G&2100&40&Phân tán\n" +
            "55&Ericsson RBS 3418&3G&2100&60&Phân tán\n" +
            "56&Nokia Flexi WCDMA BTS&3G&2100&20&Tập trung\n" +
            "57&Nokia Flexi WCDMA BTS&3G&2100&40&Tập trung\n" +
            "58&Huawei BTS 3900&3G&2100&20&Tập trung\n" +
            "59&Huawei BTS 3900&3G&2100&40&Tập trung\n" +
            "60&Huawei BTS 3900&3G&2100&60&Tập trung\n" +
            "61&Huawei BTS 3900&3G&2100&80&Tập trung\n" +
            "62&Huawei DBS 3900&3G&2100&20&Phân tán\n" +
            "63&Huawei DBS 3900&3G&2100&40&Phân tán\n" +
            "64&Huawei DBS 3900&3G&2100&60&Phân tán\n" +
            "65&Huawei DBS 3900&3G&2100&80&Phân tán\n" +
            "66&ZTE ZXSDR BS8800&3G&2100&20&Tập trung\n" +
            "67&ZTE ZXSDR BS8800&3G&2100&40&Tập trung\n" +
            "68&ZTE ZXSDR BS8800&3G&2100&60&Tập trung\n" +
            "69&ZTE ZXSDR B8200&3G&2100&20&Phân tán\n" +
            "70&ZTE ZXSDR B8200&3G&2100&40&Phân tán\n" +
            "71&ZTE ZXSDR B8200&3G&2100&60&Phân tán\n" +
            "72&ZTE ZXSDR B8200&3G&2100&80&Phân tán\n" +
            "73&ZTE ZXSDR 8700&3G&2100&20&Phân tán\n" +
            "74&ZTE ZXSDR 8700&3G&2100&40&Phân tán\n" +
            "75&ZTE ZXSDR 8700&3G&2100&60&Phân tán\n" +
            "76&ZTE ZXSDR 8700&3G&2100&80&Phân tán\n" +
            "77&Ericsson RBS 6601&3G&2100&20&Phân tán\n" +
            "78&Ericsson RBS 6601&3G&2100&40&Phân tán\n" +
            "79&Ericsson RBS 6601&3G&2100&60&Phân tán\n" +
            "80&Ericsson RBS 6601&3G&2100&80&Phân tán\n" +
            "81&Ericsson RBS 6601&4G_LTE&1800&40&Phân tán\n" +
            "82&Nokia Flexi Multiradio AirScale&4G_LTE&1800&40&Phân tán\n" +
            "83&VTTEK4G-184b&4G_LTE&1800&40&Phân tán\n" +
            "84&ZTE ZXSDR A8602&3G&2100&20&RRU tích hợp anten\n" +
            "85&ZTE ZXSDR A8602&3G&2100&40&RRU tích hợp anten\n" +
            "86&ZTE ZXSDR A8602&3G&2100&60&RRU tích hợp anten\n" +
            "87&ZTE ZXSDR A8602&3G&2100&80&RRU tích hợp anten\n" +
            "88&Ericsson RBS 6601&4G_LTE&2100&40&Phân tán\n" +
            "89&Nokia Flexi Multiradio AirScale&4G_LTE&2100&40&Phân tán\n" +
            "90&Ericsson RBS 6601&4G_LTE&1800&60&Phân tán\n" +
            "91&Ericsson RBS 6601&4G_LTE&1800&120&Phân tán\n" +
            "92&Ericsson RBS 6601&4G_LTE&1800&160&Phân tán\n" +
            "93&Nokia Flexi Multiradio AirScale&4G_LTE&1800&60&Phân tán\n" +
            "94&Nokia Flexi Multiradio AirScale&4G_LTE&1800&120&Phân tán\n" +
            "95&Nokia Flexi Multiradio AirScale&4G_LTE&1800&160&Phân tán\n";
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
    static final String BangSuyHao = "1/2&1800&1/2\" at 1800 MHz&0.103&10.3&0.5&0.5\n" +
            "1/2&2100&1/2\" at 2100 MHz&0.103&10.3&0.5&0.5\n" +
            "1/2&2300&1/2\" at 2300 MHz&0.11543&11.543&0.5&0.5\n" +
            "1/2&900&1/2\" at 900 MHz&0.06822&6.822&0.5&0.5\n" +
            "1/2&1800&1/2\" Feeder&0.103&10.3&0.5&0.5\n" +
            "7/8&1800&7/8\" at 1800 MHz&0.06&6&0.5&0.5\n" +
            "7/8&2100&7/8\" at 2100 MHz&0.06&6&0.5&0.5\n" +
            "7/8&2300&7/8\" at 2300 MHz&0.06624&6.624&0.5&0.5\n" +
            "7/8&900&7/8\" at 900 MHz&0.03861&3.861&0.5&0.5\n" +
            "7/8&1800&7/8\" Feeder&0.06&6&0.5&0.5\n";
    //endregion
}
