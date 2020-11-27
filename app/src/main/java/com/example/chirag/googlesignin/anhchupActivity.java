package com.example.chirag.googlesignin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class anhchupActivity extends AppCompatActivity {
    ImageButton btnHome,btnBack,popup;
    private ViewStub stubList;
    private ListView listView;
    private int curnntView = 0;
    TextView tvCongTac,tvHangMuc;
    Dialog dialogmenu;
    private Listview_CT_Adapter listview_ct_adapter;
    private List<CongTac> productList;
    private String tenHM,TenCongTac,tentram,biendau;
    List<String> listFile = new ArrayList<String>();
    private File mediaStorageDir;
    static final  int VIEW_MODE_GRIDVIEW = 1;
    int vt,hien=1,posi,lengthDuongDan;
    private String vitrichup= null, longi = null, latgi = null,TenChiTiet="";
    int preSelectedIndex = -1;
    String[] duongdananh= new String[100];
    private String tentramcu,tentrammoi;
    File fileoldDoup,filenewDoup,fileTaoMoi;
    ByteArrayOutputStream bytearrayoutputstream;
    byte[] BYTE;
    String[] ArrayHM = new String[1000];
    ArrayList<String> listCongTac = new ArrayList<String>();
    ArrayList<String> listPath = new ArrayList<String>();
    int biendem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anhchup);
        Slidr.attach(this);
        //Anhxa
        stubList = (ViewStub) findViewById(R.id.stub_list_CT);
        stubList.inflate();
        listView = (ListView) findViewById(R.id.mylistView_CT);
        tvCongTac = (TextView) findViewById(R.id.tvCongTac);
        tvHangMuc = (TextView) findViewById(R.id.tvHangMuc);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        popup = (ImageButton) findViewById(R.id.btnpopupmenu);
        /**BIẾN NHẬN*/
        try {
            Intent intent = getIntent();
            tenHM = intent.getStringExtra("TenHangMuc");
            tentram = intent.getStringExtra("TenTram");
            String ss = intent.getStringExtra("position");
            TenChiTiet = intent.getStringExtra("TenChiTiet");
            posi = Integer.parseInt(ss);
            ArrayHM = intent.getStringArrayExtra("MangString");
            String soluongHM = intent.getStringExtra("SoLuong");
            biendem = Integer.parseInt(soluongHM);
            latgi = intent.getStringExtra("Lat");
            TenCongTac = intent.getStringExtra("TenCongTac");
            longi = intent.getStringExtra("Long");
            vitrichup = intent.getStringExtra("ViTri");
            duongdananh = intent.getStringArrayExtra("MangCT");
            lengthDuongDan = 0;
            String[] CT = TenCongTac.split("_");
            tvCongTac.setText(CT[0]);
            tvHangMuc.setText(tenHM);
        }
        catch (Exception e)
        {}

        /**CÁCH LẤY KÍCH THƯỚC STRING[]*/
            try
            {
                for(String s:duongdananh)
                {
                    Log.d("lentgh duong dan",s);
                    lengthDuongDan++;
                }
            }
            catch (Exception e)
            {

            }
        /***/


        //String[] HM = tenHM.split("_");
        //set text


        tvHangMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(anhchupActivity.this, hangmucActivity.class);
                intent.putExtra("TenHM",tenHM);  // Truyền một String
                intent.putExtra("TenTram", tentram);  // Truyền một String
                intent.putExtra("Long", longi);  // Truyền một String
                intent.putExtra("Lat", latgi);  // Truyền một String
                intent.putExtra("ViTri", vitrichup);  // Truyền một String
                startActivity(intent);
            }
        });
        ////
        mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,tentram);
        mediaStorageDir = new File(mediaStorageDir,tenHM);
        //Tạo đường dẫn ảnh
        File[] files=mediaStorageDir.listFiles();
        listFile.clear();
        lengthDuongDan=0;
        for (File file:files)
        {
            duongdananh[lengthDuongDan] = file.getName();
            //Log.d("biến nhận",duongdananh[lengthDuongDan]);
            lengthDuongDan++;
        }
        mediaStorageDir = new File(mediaStorageDir,TenCongTac);
        getProductList(mediaStorageDir);
        /***/
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHome.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent = new Intent(anhchupActivity.this, MenuTramActivity.class);
                startActivity(intent);
            }
        });
        //// popup menu
        /** fab chụp ảnh*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabChupTuDong);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String s = intent.getStringExtra("position");
                posi = Integer.parseInt(s);
                latgi = intent.getStringExtra("Lat");
                TenChiTiet = intent.getStringExtra("TenChiTiet");
                longi = intent.getStringExtra("Long");
                vitrichup = intent.getStringExtra("ViTri");
                //Log.d("biến nhận",duongdananh[posi]);
                Intent intent2 = new Intent(anhchupActivity.this,Camera2BasicFragment.class);
                intent2.putExtra("TenHM",tenHM);  // Truyền một String
                intent2.putExtra("TenTram",tentram);  // Truyền một String
                intent2.putExtra("MangCT",  duongdananh);  // Truyền một String
                intent2.putExtra("TenCongTac",TenCongTac);  // Truyền một String
                intent2.putExtra("position", String.valueOf(posi));  // Truyền một String
                intent2.putExtra("Long", longi);  // Truyền một String
                intent2.putExtra("Lat", latgi);  // Truyền một String
                intent2.putExtra("ViTri", vitrichup);  // Truyền một String
                intent2.putExtra("TenChiTiet", TenChiTiet);  // Truyền một String
                intent2.putExtra("MangString", ArrayHM);  // Truyền một String
                intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
                finish();
                startActivity(intent2);
            }
        });
        /****/
        /**fab button*/
        FloatingActionButton fabNext = (FloatingActionButton) findViewById(R.id.fabNext);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = getIntent();
                    String s = intent.getStringExtra("position");
                    posi = Integer.parseInt(s);
                    latgi = intent.getStringExtra("Lat");
                    longi = intent.getStringExtra("Long");
                    vitrichup = intent.getStringExtra("ViTri");
                    if (posi != lengthDuongDan - 1) {
                        Log.d("biến nhận next", s);
                        if (duongdananh[posi + 1] != null) {
                            Nextto_AnhChupActivity(posi + 1);
                        }
                    } else {
                        onBackPressed();
                    }
                }
                catch (Exception e)
                {
                    onBackPressed();

                }
            }
        });
        FloatingActionButton fabBack = (FloatingActionButton) findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                Intent intent = getIntent();
                String s = intent.getStringExtra("position");
                posi = Integer.parseInt(s);
                    latgi = intent.getStringExtra("Lat");
                    longi = intent.getStringExtra("Long");
                    vitrichup = intent.getStringExtra("ViTri");
                Log.d("biến nhận",s);
                    Nextto_AnhChupActivity(posi-1);
                }catch (Exception e) {onBackPressed();}
            }
        });
        /***/
        popup = (ImageButton) findViewById(R.id.btnpopupmenu);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(anhchupActivity.this,popup);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.itemBaoCao:
                                Intent intent = new Intent(anhchupActivity.this,Main4Activity.class);
                                intent.putExtra("TenTram",tentram);  // Truyền một String
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        //// nut back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent2=new Intent();
                intent2.setClass(anhchupActivity.this, congtacActivity.class);
                intent2.putExtra("TenTram", tentram);  // Truyền một String
                intent2.putExtra("TenHM", tenHM);  // Truyền một String
                intent2.putExtra("Long", longi);  // Truyền một String
                intent2.putExtra("Lat", latgi);  // Truyền một String
                intent2.putExtra("ViTri", vitrichup);  // Truyền một String
                intent2.putExtra("MangString", ArrayHM);  // Truyền một String
                intent2.putExtra("TenChiTiet", TenChiTiet);  // Truyền một String
                intent2.putExtra("position", String.valueOf(posi));  // Truyền một String
                intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
                finish();
                startActivity(intent2);
            }
        });
        ////
        SharedPreferences sharedPreferences = getSharedPreferences("ViewsMode", MODE_PRIVATE);
        curnntView = sharedPreferences.getInt("currentView", VIEW_MODE_GRIDVIEW);
        //Register item lick
        listview_ct_adapter = new Listview_CT_Adapter(this, R.layout.list_item_congtac, productList);
        listView.setAdapter(listview_ct_adapter);
        listView.setOnItemClickListener(onItemClick);
        listView.setOnItemLongClickListener(onItemLongClickListener);

    }
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            DialogMenu(position);
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(100);
            return true;
        }
    };
    /**LIST VIEW*/
    public List<CongTac> getProductList(File f){
        try {
        productList = new ArrayList<>();
        listCongTac.clear();
        listPath.clear();
        File[] files=f.listFiles();
        for (File file:files){
                //Log.d("length", String.valueOf(files.length));
                if(files.length==1)
                {
                    try {
                    String[] Check = file.getPath().split("--");
                    //Log.d("Check",Check[1]);
                    if (Check[1].equals("0"))
                    {
                        File anhCu = new File(file.getPath());
                        //Log.d("Ảnh cũ",anhCu.getPath());
                        File anhMoi = new File(Check[0]+"--1--"+Check[2]);
                        anhCu.renameTo(anhMoi);
                        file = anhMoi;
                    }}
                    catch (Exception e)
                    {
                        File anhCu = new File(file.getPath());
                        String[] Check = file.getPath().split("--");
                        File anhMoi = new File(file.getPath().replace(".jpg","")+"--0--.jpg");
                        anhCu.renameTo(anhMoi);
                        file = anhMoi;
                    }
                }

        }
        for (File file:files)
        {
            listCongTac.add(file.getName().toString());
            listPath.add(file.getPath());
        }
        //Collections.sort(listCongTac);
        for (int i=0;i<listCongTac.size();i++)
            {
                try {
                    String nameFile = listCongTac.get(i).toString();
                    String PathFile = listPath.get(i).toString();
                    //Log.d("Ảnh mới",file.getPath());
                    String[] CT = nameFile.split("@");
                    String hientrang = CT[0];
                    String ngay = CT[1] + ":" + CT[2] + ":" + CT[3];
                    Bitmap myBitmap = BitmapFactory.decodeFile(PathFile);
                    bytearrayoutputstream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytearrayoutputstream);
                    BYTE = bytearrayoutputstream.toByteArray();
                    //Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE, 0, BYTE.length);
                    //Bitmap bitmap = BITMAP_RESIZER(bitmap2, bitmap2.getWidth() / 1, bitmap2.getHeight() / 1);
                    String[] Check = nameFile.split("--");
                    if (Check[1].equals("0"))
                        productList.add(new CongTac(false, hientrang, ngay, PathFile));
                    else if (Check[1].equals("1"))
                        productList.add(new CongTac(true, hientrang, ngay, PathFile));
                }
                catch (Exception e) {
                    try{
                    String nameFile = listCongTac.get(i).toString();
                    String PathFile = listPath.get(i).toString();
                    //Log.d("Ảnh mới",file.getPath());
                    Bitmap myBitmap = BitmapFactory.decodeFile(PathFile);
                    bytearrayoutputstream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytearrayoutputstream);
                    BYTE = bytearrayoutputstream.toByteArray();
                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE, 0, BYTE.length);
                    Bitmap bitmap = BITMAP_RESIZER(bitmap2, bitmap2.getWidth() / 1, bitmap2.getHeight() / 1);
                    String[] Check = nameFile.split("--");
                    if (Check[1].equals("0"))
                        productList.add(new CongTac(false, nameFile.replace("@","-"),"12:60:60", PathFile));
                    else if (Check[1].equals("1"))
                        productList.add(new CongTac(true, nameFile.replace("@","-"), "12:60:60", PathFile));}
                        catch (Exception s)
                        {

                        }
                }
            }
        }

        catch (ArithmeticException e)
        {
            e.printStackTrace();
        }
        Collections.sort(productList, new Comparator<CongTac>() {
            @Override
            public int compare(CongTac o1, CongTac o2) {
                String[] Ho1 = o1.getNgaychup().split(":");
                String[] Ho2 = o2.getNgaychup().split(":");
                int Go1 = Integer.parseInt(Ho1[0].replace(" ",""));
                int Go2 = Integer.parseInt(Ho2[0].replace(" ",""));
                int Po1 = Integer.parseInt(Ho1[1].replace(" ",""));
                int Po2 = Integer.parseInt(Ho2[1].replace(" ",""));
                int So1 = Integer.parseInt(Ho1[2].replace(" ",""));
                int So2 = Integer.parseInt(Ho2[2].replace(" ",""));
                int hieu = 0;
                if (Go1 < Go2)
                    hieu= Go2 - Go1;
                else if(Go1==Go1)
                    hieu= Po2 - Po1;
                else if(Go1==Go2 && Po1 == Po2)
                    hieu= So2 - So1;
                return hieu;
            }

        });
        return productList;
    }
    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           // DialogZoom(productList.get(position).getImageID());
            CongTac model = productList.get(position); //changed it to model because viewers will confused about it
            //Log.d("Item name:", model.getDuongDan());
            model.setSelected(true);
            productList.set(position, model);
            if (preSelectedIndex > -1){
                CongTac preRecord = productList.get(preSelectedIndex);
                preRecord.setSelected(false);
                productList.set(preSelectedIndex, preRecord);
            }
            preSelectedIndex = position;
            //now update adapter so we are going to make a update method in adapter
            //now declare adapter final to access in inner method
            listview_ct_adapter.updateRecords(productList);

        }
    };
    private void DialogMenu(int vt2){
        dialogmenu = new Dialog(this,R.style.PauseDialog);
        dialogmenu.setContentView(R.layout.dialog_menu2);
        dialogmenu.show();
        vt = vt2;
        /***/
        String ngaygio = productList.get(vt).getNgaychup();
        Boolean selected = productList.get(vt).isSelected;
        String isselec;
        if (selected)
        {isselec="--1--";
            //Log.d("Item name:", isselec);
             }
        else
        {isselec="--0--";
            //Log.d("Item name:", isselec);
             }
        String[] ngaygio3 = ngaygio.split(":");
       final String ImageName = productList.get(vt).getHientrang()+"@"+ngaygio3[0]+"@"+ngaygio3[1]+"@"+ngaygio3[2]+"@"+isselec+".jpg";
        /***/
        Button btnChonAnh = (Button) dialogmenu.findViewById(R.id.btnmenuthemBaoCao);
        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(anhchupActivity.this);
                builder.setTitle("Bạn muốn thêm ảnh này vào báo cáo không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int iI) {
                        DialogDuplicate(ImageName);
                    }
                });
                builder.setNegativeButton("Không", null);
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();
            }
        });
    /****/
        Button btnmenuthemanh = (Button) dialogmenu.findViewById(R.id.btnmenuthemanh);
        Button btnmenuchuplai = (Button) dialogmenu.findViewById(R.id.btnmenuchuplai);
        final Button btnmovefile = (Button) dialogmenu.findViewById(R.id.btnMoveFile);
        btnmovefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(anhchupActivity.this, btnmovefile);
                for (int j=0 ;j<lengthDuongDan;j++) {
                    popupMenu.getMenu().add(duongdananh[j]);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(anhchupActivity.this);
                        builder.setTitle("Bạn muốn chuyển đến công tác này không?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int iI) {
                                File filedan = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
                                filedan = new File(filedan,tentram);
                                filedan = new File(filedan,tenHM);
                                File filedancu = new File(filedan,TenCongTac);
                                filedan = new File(filedan,menuItem.getTitle().toString());
                                filedan = new File(filedan,ImageName);
                                filedan =new File(filedan.getPath().replace("--0--","--1--"));
                                filedancu = new File(filedancu,ImageName);

                                Log.d("đường dẫn muốn đi:", filedancu.getPath());
                                Log.d("đường dẫn muốn đến:", filedan.getPath());
                                filedancu.renameTo(filedan);
                                Toast.makeText(getApplicationContext(),"Đã chuyển hình ảnh sang công tác "+menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                dialogmenu.dismiss();
                                Nextto_AnhChupActivity(posi);
                            }
                        });
                        builder.setNegativeButton("Không", null);
                        // create and show the alert dialog
                        AlertDialog dialog2 = builder.create();
                        dialog2.show();
                        return false;
                    }
                });
                popupMenu.show();

            }
        });
        /****/
        btnmenuchuplai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(anhchupActivity.this,ChinhSuaHinhAnh.class);
                String ngaygio = productList.get(vt).getNgaychup();
                Boolean selected = productList.get(vt).isSelected;
                String isselec;
                if (selected)
                {isselec="--1--";
                    //Log.d("Item name:", isselec);
                }
                else
                {
                    isselec="--0--";
                    //Log.d("Item name:", isselec);
                }
                String[] ngaygio3 = ngaygio.split(":");
                String tentramcu = productList.get(vt).getHientrang()+"@"+ngaygio3[0]+"@"+ngaygio3[1]+"@"+ngaygio3[2]+"@"+isselec+".jpg";
                intent2.putExtra("DuongDanAnh",mediaStorageDir.getPath());
                intent2.putExtra("Tenanhsua",tentramcu);
                intent2.putExtra("HienTrang",productList.get(vt).getHientrang());
                intent2.putExtra("TenChiTiet",TenChiTiet);
                intent2.putExtra("MangCT", duongdananh);  // Truyền một String
                intent2.putExtra("TenHM",tenHM);  // Truyền một String
                intent2.putExtra("TenTram",tentram);  // Truyền một String
                intent2.putExtra("TenCongTac",TenCongTac);  // Truyền một String
                intent2.putExtra("position",  String.valueOf(posi));  // Truyền một String
                intent2.putExtra("Long", longi);  // Truyền một String
                intent2.putExtra("Lat", latgi);  // Truyền một String
                intent2.putExtra("ViTri", vitrichup);  // Truyền một String
                intent2.putExtra("MangString", ArrayHM);  // Truyền một String
                intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
                finish();
                startActivity(intent2);
            }
        });
        Button btnmenuxoa= (Button) dialogmenu.findViewById(R.id.btnmenuxoa);
        btnmenuxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(anhchupActivity.this);
                builder.setTitle("Bạn muốn xóa ảnh này không?");
                //builder.setMessage("Bạn muốn xóa folder này không?");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String ngaygio = productList.get(vt).getNgaychup();
                        Boolean selected = productList.get(vt).isSelected;
                        String isselec;
                        if (selected)
                        {isselec="--1--";
                            //Log.d("Item name:", isselec);
                        }
                        else
                        {isselec="--0--";
                            //Log.d("Item name:", isselec);
                        }
                        String[] ngaygio3 = ngaygio.split(":");
                        String tentramcu = productList.get(vt).getHientrang()+"@"+ngaygio3[0]+"@"+ngaygio3[1]+"@"+ngaygio3[2]+"@"+isselec+".jpg";
                        File fileold = new File(mediaStorageDir,tentramcu);
                        Log.d("link",fileold.getPath());
                        fileold.delete();
                        Intent intent = getIntent();
                        String s = intent.getStringExtra("position");
                        posi = Integer.parseInt(s);
                        Nextto_AnhChupActivity(posi);
                    }
                });
                builder.setNegativeButton("không", null);
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();
            }
        });
        btnmenuthemanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String s = intent.getStringExtra("position");
                posi = Integer.parseInt(s);
                Intent intent2 = new Intent(anhchupActivity.this,chupanhActivity.class);
                intent2.putExtra("TenHM",tenHM);  // Truyền một String
                intent2.putExtra("TenTram",tentram);  // Truyền một String
                intent2.putExtra("MangCT",  duongdananh);  // Truyền một String
                intent2.putExtra("TenCongTac",TenCongTac);  // Truyền một String
                intent2.putExtra("position", String.valueOf(posi));  // Truyền một String
                intent2.putExtra("Long", longi);  // Truyền một String
                intent2.putExtra("TenChiTiet", TenChiTiet);  // Truyền một String
                intent2.putExtra("Lat", latgi);  // Truyền một String
                intent2.putExtra("ViTri", vitrichup);  // Truyền một String
                intent2.putExtra("MangString", ArrayHM);  // Truyền một String
                intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
                finish();
                startActivity(intent2);
            }
        });
        Button btnXem = (Button) dialogmenu.findViewById(R.id.btnmenuxem);
        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DialogZoom(productList.get(vt).getImageID());
                dialogmenu.dismiss();
            }
        });
    };
    public void Nextto_AnhChupActivity(int position)
    {
        Intent intent2 = new Intent(anhchupActivity.this,anhchupActivity.class);
        intent2.putExtra("TenTram", tentram);  // Truyền một String
        intent2.putExtra("TenHangMuc",tenHM);  // Truyền một String
        intent2.putExtra("MangCT",  duongdananh);  // Truyền một String
        intent2.putExtra("TenCongTac",duongdananh[position]);  // Truyền một String
        intent2.putExtra("position", String.valueOf(position));  // Truyền một String
        intent2.putExtra("Long", longi);  // Truyền một String
        intent2.putExtra("Lat", latgi);  // Truyền một String
        intent2.putExtra("TenChiTiet", TenChiTiet);  // Truyền một String
        intent2.putExtra("ViTri", vitrichup);  // Truyền một String
        intent2.putExtra("MangString", ArrayHM);  // Truyền một String
        intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
        finish();
        startActivity(intent2);
    }
    public void readData(){
        BufferedReader input = null;
        File file = null;
        try {
            Log.d("ten",tentram+".txt")    ;
            file = new File(Environment.getExternalStorageDirectory(), tentram+".txt"); // Pass getFilesDir() and "MyFile" to read file
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            biendau = buffer.toString();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public Bitmap BITMAP_RESIZER(Bitmap bitmap,int newWidth,int newHeight) {
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
    private void DialogZoom(Bitmap bitmap){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_zoom);
        dialog.show();
        ImageView imageView =(ImageView) dialog.findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
        FloatingActionButton fabBack = (FloatingActionButton) dialog.findViewById(R.id.btnThoat);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void DialogDuplicate(final String ImageName){
      final Dialog dialog = new Dialog(this,R.style.PauseDialog);
        dialog.setContentView(R.layout.dialog_rename2);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        fileTaoMoi = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        fileTaoMoi = new File(fileTaoMoi,tentram);
        fileTaoMoi = new File(fileTaoMoi,tenHM);

        tentramcu =  TenCongTac;
        final EditText txtTentram = (EditText) dialog.findViewById(R.id.txtMaTram);
        Button btnOK = (Button) dialog.findViewById(R.id.btnRename);
        TextView tvTen = (TextView) dialog.findViewById(R.id.tvTen);
        tvTen.setText("Thêm thư mục");
        /**gắn tên thu mục*/
        String string = TenCongTac;
        char chr = string.charAt(string.length()-1);
        char[] name= new char[string.length()-1];
        string.getChars(0,string.length()-1,name,0);
        //Log.d("New char", String.valueOf(name));
        final boolean boo = isNumeric(String.valueOf(chr));

        if (boo)
        {
            int number = Integer.parseInt(String.valueOf(chr));
            //Log.d("New number", String.valueOf(number+1));
            txtTentram.setText(String.valueOf(name)+String.valueOf(number+1));
            tentrammoi = txtTentram.getText().toString();

        }
        else
        {
            tentramcu =  TenCongTac;
            tentrammoi = TenCongTac+" 1";
            fileoldDoup = new File(mediaStorageDir,tentramcu);
            filenewDoup = new File(mediaStorageDir,tentrammoi);
            String string2 = tentramcu + " 2";
            txtTentram.setText(string2);
        }
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (boo)
                {
                    tentrammoi = txtTentram.getText().toString();
                    File filenew = new File(fileTaoMoi,tentrammoi);
                    /**Tạo thu muc mới**/
                    if (!filenew.exists()) {
                        if (!filenew.mkdirs()) {
                            Log.d("App", "failed to create directory");
                        } else {
                        }
                    }
                }
                else
                {
                    tentrammoi = txtTentram.getText().toString();
                    fileoldDoup.renameTo(filenewDoup);
                    File filenew = new File(fileTaoMoi,tentrammoi);
                    /**Tạo thu muc mới**/
                    if (!filenew.exists()) {
                        if (!filenew.mkdirs()) {
                            Log.d("App", "failed to create directory");
                        } else {
                        }
                    }
                }
                //productList.remove(vt);
                //productList.set(vt,new HangMuc(R.drawable.ic_folder_open_black_24dp,tentrammoi.toString()));

                //restartActivity(hangmucActivity.this);
                Toast.makeText(getApplicationContext(), "Đã thêm thư mục " + tentrammoi, Toast.LENGTH_SHORT).show();
                File filedan = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
                filedan = new File(filedan,tentram);
                filedan = new File(filedan,tenHM);
                File filedancu = new File(filedan,TenCongTac);
                filedan = new File(filedan,tentrammoi);
                filedan = new File(filedan,ImageName);
                filedancu = new File(filedancu,ImageName);
                String[] Check = filedancu.getPath().toString().split("--");
                if (Check[1].equals("0"))
                {
                    File anhCu = new File(filedancu.getPath());
                    //Log.d("Ảnh cũ",anhCu.getPath());
                    File anhMoi = new File(Check[0]+"--1--"+Check[2]);
                    anhCu.renameTo(anhMoi);
                    filedancu = anhMoi;
                }
                String[] Check2 = filedan.getPath().toString().split("--");
                if (Check2[1].equals("0"))
                {
                    File anhCu = new File(filedan.getPath());
                    //Log.d("Ảnh cũ",anhCu.getPath());
                    File anhMoi = new File(Check2[0]+"--1--"+Check2[2]);
                    anhCu.renameTo(anhMoi);
                    filedan = anhMoi;
                }
                Log.d("đường dẫn muốn đi:", filedancu.getPath());
                Log.d("đường dẫn muốn đến:", filedan.getPath());
                filedancu.renameTo(filedan);
                Toast.makeText(getApplicationContext(),"Đã chuyển hình ảnh sang công tác "+tentrammoi, Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                dialogmenu.dismiss();
                Nextto_AnhChupActivity(posi+1);
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
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    public void xoaFileHienTrang(){
        File file;
        file = new File(Environment.getExternalStorageDirectory(), "Template");
        file = new File(file, "DANHGIAHIENTRANG.txt");
        file.delete();

    }
}
