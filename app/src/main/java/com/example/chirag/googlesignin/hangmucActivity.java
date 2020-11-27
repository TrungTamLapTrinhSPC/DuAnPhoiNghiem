package com.example.chirag.googlesignin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhapdulieudodac.FormMain;
import com.example.nhapdulieudodac.FormMain_CotTuDung;
import com.r0adkll.slidr.Slidr;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class hangmucActivity extends AppCompatActivity {
    private ViewStub stubList;
    ImageButton btnHome,btnBack,popup,btnsetting,btnSearch;
    LinearLayout layoutSetting,layoutLine,layoutSearch;
    TextView tvHangMuc;
    ArrayList<String> listViTriCongTac = new ArrayList<String>();
    ArrayList<String> listTheThoiGian = new ArrayList<String>();
    ArrayList<String> listHangMuc = new ArrayList<String>();
    ArrayList<String> listItem = new ArrayList<String>();
    ArrayList<String> listItem2 = new ArrayList<String>();
    String phulucluu,TenChiTiet="",TenVitri="Nhập dữ liệu đo đạc";
    private String vitrichup= null, longi = null, latgi = null;
    private ListView listView;
    private Listview_HM_Adapter listview_hm_adapter;
    private List<HangMuc> productList;
    private int curnntView = 0;
    List<String> listFile = new ArrayList<String>();
    private File mediaStorageDir;
    private String tenTram;
    Integer lengthArray;
    File[] files2;
    String[] ArrayString = new String[1000];
    Integer[] tt = new Integer[100];
    static final  int VIEW_MODE_GRIDVIEW = 1;
    String[] listCheDo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangmuc);
        Slidr.attach(this);
        /**ẨN HIỆN THANH TRẠNG THÁI*/
        layoutSetting = (LinearLayout) findViewById(R.id.LayoutSetting);
        layoutSearch = (LinearLayout) findViewById(R.id.LayoutSearch);
        layoutLine = (LinearLayout) findViewById(R.id.layoutLine);
        layoutSetting.setVisibility(View.GONE);
        layoutLine.setVisibility(View.GONE);
        layoutSearch.setVisibility(View.GONE);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutSearch.getVisibility() == View.GONE)
                {
                    layoutSearch.setVisibility(View.VISIBLE);
                }
                else if (layoutSearch.getVisibility() == View.VISIBLE)
                {
                    layoutSearch.setVisibility(View.GONE);
                }
            }
        });
        /***/
        stubList = (ViewStub) findViewById(R.id.stub_list_HM);
        stubList.inflate();
        listView = (ListView) findViewById(R.id.mylistView_HM);
        tvHangMuc = (TextView) findViewById(R.id.tvHangMuc);
        try
        {
            Intent intent = getIntent();
            tenTram = intent.getStringExtra("TenTram");
            latgi = intent.getStringExtra("Lat");
            longi = intent.getStringExtra("Long");
            vitrichup = intent.getStringExtra("ViTri");
            if (longi.equals("null") || latgi.equals("null") || vitrichup.equals("null")|| vitrichup.equals("") )
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(hangmucActivity.this);
                builder.setTitle("Bạn chưa có tọa độ! Bạn có muốn tiếp tục không?");
                // add the buttons
                builder.setPositiveButton("Lấy lại tọa độ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Bỏ qua và tiếp tục", null);
                // create and show the alert dialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
            File Templat = new File(Environment.getExternalStorageDirectory(),"Template");
            File txtCheDoChup = new File(Templat,"CHEDOCHUP.txt");
            if(!txtCheDoChup.exists())
                saveDataOnCacher(UT.CheDoChup,"CHEDOCHUP");
            String string = DataforPath("CHEDOCHUP");
           listCheDo = string.split("@");

        }
        catch (Exception e)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(hangmucActivity.this);
            builder.setTitle("Lỗi thiếu biến!");
            // add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    onBackPressed();
                }
            });
            builder.setNegativeButton("Bỏ qua và tiếp tục", null);
            // create and show the alert dialog
            AlertDialog dialog1 = builder.create();
            dialog1.show();
        }
        if (listCheDo.length < 10) saveDataOnCacher(UT.CheDoChup,"CHEDOCHUP");
        /**CHUYỂN DANH SÁCH VÀO LISTVIEW*/
        try {
            mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
            mediaStorageDir = new File(mediaStorageDir, tenTram);
            /**Lấy danh sách*/
            productList = new ArrayList<>();
            File[] files = mediaStorageDir.listFiles();
            listFile.clear();
            int i = 0;
            for (File file : files) {
                if (file.getName().split("\\.").length !=1) {
                    //Log.d("folder hạng muc",file.getName());
                    ArrayString[i] = file.getName();
                    //Log.d("chuoi: ",ArrayString[i].toString());
                    i++;
                }
                else
                {
                    FileUtils.deleteDirectory(file);
                }
            }
            lengthArray = i - 1;
            sapxep(ArrayString);
            getProductList(listItem);
        }
        catch (Exception e)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(hangmucActivity.this);
            builder.setTitle("Lỗi Tải danh sách hạng mục #1!");
            // add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    onBackPressed();
                }
            });
            builder.setNegativeButton("Bỏ qua và tiếp tục", null);
            // create and show the alert dialog
            AlertDialog dialog1 = builder.create();
            dialog1.show();
        }
        /***/
        String[] output = tenTram.split("_");
        tvHangMuc.setText(output[0]);
        /**SEARCH VEIW CÔNG TÁC*/
            listViTriCongTac.add("Tất cả công tác");
            listViTriCongTac.add("Công tác trên cao");
            listViTriCongTac.add("Công tác dưới đất");

            SearchView searchViewCT = (SearchView) findViewById(R.id.SearchViewCT);
            // Get SearchView autocomplete object.
            final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
            ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listHangMuc);
            searchAutoCompleteCT.setAdapter(newsAdapterCT);
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
                listview_hm_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_hm_adapter.filter(newText);
                return false;
            }
        });
        /**BUTTON SETTING*/
        DialogSetting2();
        btnsetting = (ImageButton) findViewById(R.id.btnsettingmenu);
        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSetting2();
            }
        });
        /**POPUP MENU*/
            popup = (ImageButton) findViewById(R.id.btnpopupmenu);
            popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(hangmucActivity.this,popup);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu2,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.itemCheDoChup:
                                    DialogCheDoChup();
                                    break;
//                                case R.id.itemTable:
//                                    File mediaStorageGIAMSAT = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
//                                    mediaStorageGIAMSAT = new File(mediaStorageGIAMSAT,tenTram);
//                                    if(mediaStorageGIAMSAT.exists())
//                                    {
//                                        File[] files=mediaStorageGIAMSAT.listFiles();
//                                        if(files.length==10)
//                                        {
//                                            Intent intent3 = new Intent(hangmucActivity.this, Table3Activity.class);
//                                            intent3.putExtra("TenTram", tenTram);  // Truyền một String
//                                            intent3.putExtra("SoMong", "0");  // Truyền một String
//                                            intent3.putExtra("SoDot", "0");  // Truyền một String
//                                            intent3.putExtra("ViTriDat", "Trên mái");  // Truyền một String
//                                            intent3.putExtra("SoTangDay", "0");  // Truyền một String
//                                            intent3.putExtra("SoChanCot", "3");  // Truyền một String
//                                            intent3.putExtra("KichThuocMong", "0.5x0.5x0.6");  // Truyền một String
//                                            intent3.putExtra("KichThuocCot", "0.3x0.3");  // Truyền một String
//                                            startActivity(intent3);
//                                        }
//                                        if(files.length==8)
//                                        {
//                                            Intent intent3 = new Intent(hangmucActivity.this, Table1Activity.class);
//                                            intent3.putExtra("TenTram", tenTram);  // Truyền một String
//                                            intent3.putExtra("SoMong", "4");  // Truyền một String
//                                            intent3.putExtra("SoDot", "0");  // Truyền một String
//                                            intent3.putExtra("ViTriDat", "Trên mái");  // Truyền một String
//                                            intent3.putExtra("KichThuocMong", "0.58x0.58x0.6 m");  // Truyền một String
//                                            intent3.putExtra("KichThuocChanCot", "0.46x0.46 m");  // Truyền một String
//                                            intent3.putExtra("KichThuocDinhCot", "2.7x2.7 m");  // Truyền một String
//                                            intent3.putExtra("SoChanCot", "4");  // Truyền một String
//                                            startActivity(intent3);
//                                        }
//                                    }
//
//                                break;
                                case R.id.itemrestartHT:
                                    final AlertDialog.Builder builder4 = new AlertDialog.Builder(hangmucActivity.this);
                                    builder4.setTitle("Cập nhật lại Thư viện đang dùng?");
                                    // add the buttons
                                    builder4.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            File Templat = new File(Environment.getExternalStorageDirectory(),"Template");
                                            File HienTrangBeTong = new File(Templat,"HienTrangBeTong.txt");
                                            File HienTrangThep = new File(Templat,"HienTrangThep.txt");
                                            File DeXuatBeTong = new File(Templat,"DeXuatBeTong.txt");
                                            File DeXuatThep = new File(Templat,"DeXuatThep.txt");
                                            HienTrangBeTong.delete();
                                            HienTrangThep.delete();
                                            DeXuatBeTong.delete();
                                            DeXuatThep.delete();
                                            saveDataOnCacher(UT.HienTrangBeTong,"HienTrangBeTong");
                                            saveDataOnCacher(UT.HienTrangThep,"HienTrangThep");
                                            saveDataOnCacher(UT.DeXuatBeTong,"DeXuatBeTong");
                                            saveDataOnCacher(UT.DeXuatThep,"DeXuatThep");
                                            Toast.makeText(getApplicationContext(), "Đã cập nhật lại thư viện!" , Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    builder4.setNegativeButton("không", null);
                                    // create and show the alert dialog
                                    AlertDialog dialog111 = builder4.create();
                                    dialog111.show();
                                    break;
                                case R.id.itemluuphuluc:
                                    luuphuluc();
                                    break;
                                case R.id.itemrestartphuluc:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(hangmucActivity.this);
                                    builder.setTitle("Cập nhật lại cây thư mục đang dùng?");
                                    // add the buttons
                                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            /**THÊM FOLDER GIÁM SÁT*/
                                            File Templat = new File(Environment.getExternalStorageDirectory(),"Template");
                                            File txtCOTDAYCO = new File(Templat,"COTDAYCO.txt");
                                            File txtCOTTUDUNG = new File(Templat,"COTTUDUNG.txt");
                                            try{
                                                txtCOTDAYCO.delete();
                                                txtCOTTUDUNG.delete();
                                                Templat.delete();
                                                File Template = new File(Environment.getExternalStorageDirectory(),"Template");
                                                if (!Template.exists())
                                                {
                                                    if (!Template.mkdirs())
                                                    {
                                                        Log.d("App", "failed to create directory");
                                                    }
                                                    else
                                                        {
                                                            Toast.makeText(getApplicationContext(), "Đã thêm folder Template vào bộ nhớ" , Toast.LENGTH_SHORT).show();
                                                        saveDataOnCacher(UT.TramDayCo,"COTDAYCO");
                                                        /***Tạo phụ lục Tự ĐỨNG*/
                                                        saveDataOnCacher(UT.TramTuDung,"COTTUDUNG");
                                                    }
                                                }
                                                saveDataOnCacher(UT.TramDayCo,"COTDAYCO");
                                                /***Tạo phụ lục Tự ĐỨNG*/
                                                saveDataOnCacher(UT.TramTuDung,"COTTUDUNG");

                                                AlertDialog.Builder builder = new AlertDialog.Builder(hangmucActivity.this);
                                                builder.setTitle("Xóa bỏ thư mục trạm bị lỗi và tạo lại thư mục tương tự.");
                                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        onBackPressed();
                                                    }
                                                });
                                                // create and show the alert dialog
                                                AlertDialog dialog1 = builder.create();
                                                dialog1.show();
                                            }
                                            catch (Exception e){}

                                        }
                                    });
                                    builder.setNegativeButton("không", null);
                                    // create and show the alert dialog
                                    AlertDialog dialog1 = builder.create();
                                    dialog1.show();
                                    break;

                                case R.id.itemBaoCao:
                                    Intent intent = new Intent(hangmucActivity.this, Main4Activity.class);
                                    intent.putExtra("TenTram",tenTram);  // Truyền một String
                                    startActivity(intent);
                                    break;

                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        /***/
        SharedPreferences sharedPreferences = getSharedPreferences("ViewsMode", MODE_PRIVATE);
        curnntView = sharedPreferences.getInt("currentView", VIEW_MODE_GRIDVIEW);
        /**Register item lick*/
        listview_hm_adapter = new Listview_HM_Adapter(this, R.layout.list_item_hangmuc, productList);
        listView.setAdapter(listview_hm_adapter);
        listView.setOnItemClickListener(onItemClick);
        listView.setOnItemLongClickListener(onItemLongClickListener);
        //listView.setOnItemLongClickListener(onItemLongClickListener);
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                onBackPressed();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHome.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                Intent intent = new Intent(hangmucActivity.this, MenuTramActivity.class);

                startActivity(intent);
            }
        });
        listItem2.clear();
        listItem2.addAll(listItem);
    }
    public List<HangMuc> getProductList(ArrayList<String> ArrayString){
        listHangMuc.clear();
        productList.clear();
        try {
            for (String HM : ArrayString) {
                //Log.d("Chuỗi đầu vào: ", HM);
                listHangMuc.add(HM);
                productList.add(new HangMuc(R.drawable.ic_folder_open_black_24dp, HM));
            }
            listview_hm_adapter = new Listview_HM_Adapter(this, R.layout.list_item_hangmuc, productList);
            listView.setAdapter(listview_hm_adapter);
        }
        catch (ArithmeticException e)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(hangmucActivity.this);
            builder.setTitle("Lỗi tải danh sách hạng mục #2!\n"+"Error: " + e.getMessage());
            // add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    //onBackPressed();
                }
            });
            // create and show the alert dialog
            AlertDialog dialog1 = builder.create();
            dialog1.show();
        }
        return productList;
    }
/**SỰ KIỆN CLICK ITEM*/
    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /***/
            int lengthList = lengthArray;
            //Log.d("Lengh", String.valueOf(lengthArray));
            if(!TenVitri.equals("Tất cả công tác"))
            {
                ArrayString = new String[1000];
                int i=0;
                for (String s:listItem2) {
                    ArrayString[i] = s;
                    i++;
                }
                lengthList = listItem2.size()-1;
            }
            else
                lengthList = lengthArray;

            /**Do any thing when user click to item*/
            Toast.makeText(getApplicationContext(), productList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(hangmucActivity.this,congtacActivity.class);
            intent.putExtra("TenHM", productList.get(position).getTitle());  // Truyền một String
            intent.putExtra("TenTram", tenTram);  // Truyền một String
            intent.putExtra("TenChiTiet", TenChiTiet);  // Truyền một String
            intent.putExtra("position", String.valueOf(position));  // Truyền một String
            intent.putExtra("MangString", ArrayString);  // Truyền một String
            intent.putExtra("SoLuong",String.valueOf(lengthList));  // Truyền một String
            intent.putExtra("Long", longi);  // Truyền một String
            intent.putExtra("Lat", latgi);  // Truyền một String
            intent.putExtra("ViTri", vitrichup);  // Truyền một String
            startActivity(intent);
        }
    };
    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            DialogMenu(position);
            return true;
        }
    };
    private void DialogMenu(int vtCT){
       Dialog dialogmenu = new Dialog(this,R.style.PauseDialog);
        dialogmenu.setContentView(R.layout.dialog_menu_hangmuc);
        dialogmenu.show();
        Button btnmenuxoa= (Button) dialogmenu.findViewById(R.id.btnmenuxoa);

        btnmenuxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(hangmucActivity.this);
                builder.setTitle("Bạn muốn xóa thư mục này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //xoaFolder(tenTram,tenHM,productList.get(vtCT).getTitle());
                        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
                        mediaStorageDir = new File(mediaStorageDir, tenTram);
                        mediaStorageDir = new File(mediaStorageDir, productList.get(vtCT).getTitle());
                        if (mediaStorageDir.exists())
                        {
                            try {
                                FileUtils.deleteDirectory(mediaStorageDir);
                                /**CHUYỂN DANH SÁCH VÀO LISTVIEW*/
                                try {
                                    mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
                                    mediaStorageDir = new File(mediaStorageDir, tenTram);
                                    /**Lấy danh sách*/
                                    productList = new ArrayList<>();
                                    File[] files = mediaStorageDir.listFiles();
                                    listItem.clear();
                                    listFile.clear();
                                    int ii = 0;
                                    for (File file : files) {
                                        if (file.getName().split("\\.").length !=1) {
                                            //Log.d("folder hạng muc",file.getName());
                                            ArrayString[ii] = file.getName();
                                            //Log.d("chuoi: ",ArrayString[i].toString());
                                            ii++;
                                        }
                                        else
                                        {
                                            FileUtils.deleteDirectory(file);
                                        }
                                    }
                                    lengthArray = ii - 1;
                                    sapxep(ArrayString);
                                    getProductList(listItem);
                                }
                                catch (Exception e)
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(hangmucActivity.this);
                                    builder.setTitle("Lỗi Tải danh sách hạng mục #1!");
                                    // add the buttons
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            btnBack.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                            onBackPressed();
                                        }
                                    });
                                    builder.setNegativeButton("Bỏ qua và tiếp tục", null);
                                    // create and show the alert dialog
                                    AlertDialog dialog1 = builder.create();
                                    dialog1.show();
                                }
                                /***/

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        dialogmenu.dismiss();
                    }
                });
                builder.setNegativeButton("không", null);
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();
            }
        });

    };
    ArrayList<String> listDoPhanGiaiAnh = new ArrayList<String>();
    ArrayList<String> listDoSang = new ArrayList<String>();
    public void DialogCheDoChup() {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_chodocup);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            /***/

            String string = DataforPath("CHEDOCHUP");
            String[] listCheDo = string.split("@");
            if (listCheDo.length<18)
            {
                string = UT.CheDoChup;
                listCheDo = string.split("@");
                saveDataOnCacher(string,"CHEDOCHUP");
            }
            /***/
            final String[] tChitiet = {listCheDo[1].toString()};
            final String[] tThuong = {listCheDo[3].toString()};
            final String[] tKhuyetTat = {listCheDo[5].toString()};
            final String[] tTheChung = {listCheDo[7].toString()};
            final String[] tTheThoiGian = {listCheDo[9].toString()};
            final String[] tToaDo = {listCheDo[11].toString()};
            final String[] tViTri = {listCheDo[13].toString()};
            final String[] tDoPhanGiai = {listCheDo[15].toString()};
            final String[] tLiveCam = {listCheDo[17].toString()};
            final String[] tDoSang = {listCheDo[19].toString()};


            final Switch switchchitiet = (Switch) dialog.findViewById(R.id.switchChiTiet);
            final Switch switchthuong = (Switch) dialog.findViewById(R.id.switchThuong);
            final Switch switchkhuyettat = (Switch) dialog.findViewById(R.id.switchDiTat);
            final Switch switchthechung = (Switch) dialog.findViewById(R.id.switchTheChung);
            final Switch switchtoado = (Switch) dialog.findViewById(R.id.switchToaDo);
            final Switch switchvitri = (Switch) dialog.findViewById(R.id.switchDiaDiem);
            final Switch switchlivecam = (Switch) dialog.findViewById(R.id.switchLive);

            if (tChitiet[0].equals("On")) switchchitiet.setChecked(true);
            else switchchitiet.setChecked(false);
            if (tThuong[0].equals("On")) switchthuong.setChecked(true);
            else switchthuong.setChecked(false);
            if (tKhuyetTat[0].contains("On")) switchkhuyettat.setChecked(true);
            else switchkhuyettat.setChecked(false);
            if (tTheChung[0].equals("On")) switchthechung.setChecked(true);
            else switchthechung.setChecked(false);
            if (tToaDo[0].equals("On")) switchtoado.setChecked(true);
            else switchtoado.setChecked(false);
            if (tViTri[0].contains("On")) switchvitri.setChecked(true);
            else switchvitri.setChecked(false);
            if (tLiveCam[0].contains("On")) switchlivecam.setChecked(true);
            else switchlivecam.setChecked(false);

            final TextView txt1 = (TextView) dialog.findViewById(R.id.txt1);
            txt1.setText(tChitiet[0]);
            final TextView txt2 = (TextView) dialog.findViewById(R.id.txt2);
            txt2.setText(tThuong[0]);
            final TextView txt3 = (TextView) dialog.findViewById(R.id.txt3);
            txt3.setText(tKhuyetTat[0]);
            final TextView txt4 = (TextView) dialog.findViewById(R.id.txt4);
            txt4.setText(tTheChung[0]);
            final TextView txt5 = (TextView) dialog.findViewById(R.id.txt5);
            txt5.setText(tToaDo[0]);
            final TextView txt6 = (TextView) dialog.findViewById(R.id.txt6);
            txt6.setText(tViTri[0]);
            final TextView txt7 = (TextView) dialog.findViewById(R.id.txt7);
            txt7.setText(tLiveCam[0]);
            switchchitiet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tChitiet[0] = (switchchitiet.isChecked() ? "On" : "Off");
                    txt1.setText(tChitiet[0]);
                }
            });
            switchthuong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tThuong[0] = (switchthuong.isChecked() ? "On" : "Off");
                    txt2.setText(tThuong[0]);

                }
            });
            switchkhuyettat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tKhuyetTat[0] = (switchkhuyettat.isChecked() ? "On" : "Off");
                    txt3.setText(tKhuyetTat[0]);
                }
            });
            /**
             * Thẻ
             */
            switchthechung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tTheChung[0] = (switchthechung.isChecked() ? "On" : "Off");
                    txt4.setText(tTheChung[0]);
                }
            });
            switchtoado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tToaDo[0] = (switchtoado.isChecked() ? "On" : "Off");
                    txt5.setText(tToaDo[0]);

                }
            });
            switchvitri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tViTri[0] = (switchvitri.isChecked() ? "On" : "Off");
                    txt6.setText(tViTri[0]);
                }
            });

            switchlivecam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    tLiveCam[0] = (switchlivecam.isChecked() ? "On" : "Off");
                    //allwayon = switchlivecam.isChecked();
                    txt7.setText(tLiveCam[0]);
                }
            });

            /***
             * Thẻ thời gian
             */
            listTheThoiGian.clear();
            listTheThoiGian.add("None");
            listTheThoiGian.add("dd/mm/yyyy hh:mm");
            listTheThoiGian.add("hh:mm");
            listTheThoiGian.add("dd/mm/yyyy");

            final TextView edtVitri =dialog.findViewById(R.id.edtThoiGian);
            edtVitri.setText(tTheThoiGian[0]);
            /**TẠO POPUP EDIT TEXT*/
            edtVitri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(hangmucActivity.this, edtVitri);
                    for (String s : listTheThoiGian) {
                        popupMenu.getMenu().add(s);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem menuItem) {
                            edtVitri.setText(menuItem.getTitle().toString());
                            tTheThoiGian[0]= (String) edtVitri.getText();
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            /***
             * Thẻ độ sáng
             */
            listDoSang.clear();
            listDoSang.add("Cao");
            listDoSang.add("Trung bình");
            listDoSang.add("Thấp");

            final TextView edtDoSang =dialog.findViewById(R.id.edtDoSang);
            edtDoSang.setText(tDoSang[0]);
            /**TẠO POPUP EDIT TEXT*/
            edtDoSang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(hangmucActivity.this, edtDoSang);
                    for (String s : listDoSang) {
                        popupMenu.getMenu().add(s);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem menuItem) {
                            edtDoSang.setText(menuItem.getTitle().toString());
                            tDoSang[0]= (String) edtDoSang.getText();
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            /***
             * Thẻ độ phân giải ảnh
             */
            listDoPhanGiaiAnh.clear();
            listDoPhanGiaiAnh.add("QHD (2560x1440)");
            listDoPhanGiaiAnh.add("FHD (1920x1080)");
            listDoPhanGiaiAnh.add("HD (1280x720)");
            listDoPhanGiaiAnh.add("VGA (640x480)");

            final TextView edtDoPhanGiai =dialog.findViewById(R.id.edtChatLuongAnh);
            edtDoPhanGiai.setText(tDoPhanGiai[0]);
            /**TẠO POPUP EDIT TEXT*/
            edtDoPhanGiai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(hangmucActivity.this, edtDoPhanGiai);
                    for (String s : listDoPhanGiaiAnh) {
                        popupMenu.getMenu().add(s);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem menuItem) {
                            edtDoPhanGiai.setText(menuItem.getTitle().toString());
                            tDoPhanGiai[0]= (String) edtDoPhanGiai.getText();
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            /**
             * Buton
             */
            Button btnThoat = dialog.findViewById(R.id.btnthoat);
            btnThoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            Button btnOK = dialog.findViewById(R.id.btnOK);
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveDataOnCacher("1@" + tChitiet[0] + "@2@" + tThuong[0] + "@3@" + tKhuyetTat[0] + "@4@" + tTheChung[0]+ "@5@" + tTheThoiGian[0] + "@6@" + tToaDo[0] + "@7@" + tViTri[0]+ "@8@" + tDoPhanGiai[0]+ "@9@" + tLiveCam[0]+ "@10@" + tDoSang[0], "CHEDOCHUP");

                    dialog.dismiss();
                }
            });
        }
        catch (Exception e)
        {
            File Templat = new File(Environment.getExternalStorageDirectory(),"Template");
            File txtCheDoChup = new File(Templat,"CHEDOCHUP.txt");
            if (txtCheDoChup.exists()){txtCheDoChup.delete();}
            if(!txtCheDoChup.exists())
                saveDataOnCacher(UT.CheDoChup,"CHEDOCHUP");
        }
    }
    private void DialogSetting2(){

            final Dialog dialog = new Dialog(this, R.style.PauseDialog);
            dialog.setContentView(R.layout.dialog_chon_thaotac);
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            ImageButton btnChupAnh = dialog.findViewById(R.id.btnChupAnh);
            ImageButton btnDuLieu = dialog.findViewById(R.id.btnBaoCao);
            btnChupAnh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu = new PopupMenu(hangmucActivity.this, btnChupAnh);
                    for (String s : listViTriCongTac)
                    {
                        popupMenu.getMenu().add(s);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem menuItem) {
                            TenVitri = menuItem.getTitle().toString();
                            if (listItem.size() == 10) {
                                /**KIỂM TRA VỊ TRÍ LÀM VIỆC*/
                                if (TenVitri.equals("Công tác trên cao")) {
                                    listItem2.clear();
                                    listItem2.add(listItem.get(1));
                                    listItem2.add(listItem.get(3));
                                    listItem2.add(listItem.get(7));
                                    listItem2.add(listItem.get(8));
                                    listItem2.add(listItem.get(9));
                                } else if (TenVitri.equals("Công tác dưới đất")) {
                                    listItem2.clear();
                                    listItem2.add(listItem.get(0));//1
                                    listItem2.add(listItem.get(2));//3
                                    listItem2.add(listItem.get(3));//4
                                    listItem2.add(listItem.get(4));//5
                                    listItem2.add(listItem.get(5));//6
                                    listItem2.add(listItem.get(6));//7
                                    listItem2.add(listItem.get(7));//8
                                    listItem2.add(listItem.get(8));//9
                                    listItem2.add(listItem.get(9));//10
                                } else if (TenVitri.equals("Tất cả công tác")) {
                                    listItem2.clear();
                                    listItem2.addAll(listItem);
                                } else if (TenVitri.equals("Nhập dữ liệu đo đạc")){
                                    Intent intent3 = new Intent(hangmucActivity.this, FormMain.class);
                                    intent3.putExtra("TenTram", tenTram);  // Truyền một String
                                    intent3.putExtra("SoMong", "0");  // Truyền một String
                                    intent3.putExtra("SoDot", "0");  // Truyền một String
                                    intent3.putExtra("ViTriDat", "Trên mái");  // Truyền một String
                                    intent3.putExtra("SoTangDay", "0");  // Truyền một String
                                    intent3.putExtra("SoChanCot", "3");  // Truyền một String
                                    intent3.putExtra("KichThuocMong", "0.5x0.5x0.6");  // Truyền một String
                                    intent3.putExtra("KichThuocCot", "0.3x0.3");  // Truyền một String
                                    intent3.putExtra("Long", longi);  // Truyền một String
                                    intent3.putExtra("Lat", latgi);  // Truyền một String
                                    intent3.putExtra("ViTri", vitrichup);  // Truyền một String
                                    startActivity(intent3);

                                }
                                /***/
                            } else if (listItem.size() == 8) {
                                /**KIỂM TRA VỊ TRÍ LÀM VIỆC*/
                                if (TenVitri.equals("Công tác trên cao")) {
                                    listItem2.clear();
                                    listItem2.add(listItem.get(1));
                                    listItem2.add(listItem.get(5));
                                    listItem2.add(listItem.get(7));
                                    TenChiTiet = "Đốt D1";
                                } else if (TenVitri.equals("Công tác dưới đất")) {
                                    listItem2.clear();
                                    listItem2.add(listItem.get(0));//1
                                    listItem2.add(listItem.get(2));//3
                                    listItem2.add(listItem.get(3));//4
                                    listItem2.add(listItem.get(4));//5
                                    listItem2.add(listItem.get(5));//6
                                    listItem2.add(listItem.get(6));//7
                                    listItem2.add(listItem.get(7));//8
                                    TenChiTiet="Móng M1";
                                } else if (TenVitri.equals("Tất cả công tác")) {
                                    listItem2.clear();
                                    listItem2.addAll(listItem);
                                } else if (TenVitri.equals("Nhập dữ liệu đo đạc")){
                                    Intent intent3 = new Intent(hangmucActivity.this, FormMain_CotTuDung.class);
                                    intent3.putExtra("TenTram", tenTram);  // Truyền một String
                                    intent3.putExtra("SoMong", "4");  // Truyền một String
                                    intent3.putExtra("SoDot", "0");  // Truyền một String
                                    intent3.putExtra("ViTriDat", "Trên mái");  // Truyền một String
                                    intent3.putExtra("KichThuocMong", "0.58x0.58x0.6 m");  // Truyền một String
                                    intent3.putExtra("KichThuocChanCot", "0.46x0.46 m");  // Truyền một String
                                    intent3.putExtra("KichThuocDinhCot", "2.7x2.7 m");  // Truyền một String
                                    intent3.putExtra("SoChanCot", "4");  // Truyền một String
                                    intent3.putExtra("Long", longi);  // Truyền một String
                                    intent3.putExtra("Lat", latgi);  // Truyền một String
                                    intent3.putExtra("ViTri", vitrichup);  // Truyền một String
                                    startActivity(intent3);
                                }
                            }
                            if (listItem2.size() == 1)
                                listItem2 = listItem;
                            else if (listItem2.size() > 2)
                                getProductList(listItem2);
                            dialog.dismiss();
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
            btnDuLieu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TenVitri = "Nhập dữ liệu đo đạc";
                    if (listItem.size() == 10) {
                        /**KIỂM TRA VỊ TRÍ LÀM VIỆC*/
                        if (TenVitri.equals("Công tác trên cao")) {
                            listItem2.clear();
                            listItem2.add(listItem.get(1));
                            listItem2.add(listItem.get(3));
                            listItem2.add(listItem.get(7));
                            listItem2.add(listItem.get(8));
                            listItem2.add(listItem.get(9));
                        } else if (TenVitri.equals("Công tác dưới đất")) {
                            listItem2.clear();
                            listItem2.add(listItem.get(0));//1
                            listItem2.add(listItem.get(2));//3
                            listItem2.add(listItem.get(3));//4
                            listItem2.add(listItem.get(4));//5
                            listItem2.add(listItem.get(5));//6
                            listItem2.add(listItem.get(6));//7
                            listItem2.add(listItem.get(7));//8
                            listItem2.add(listItem.get(8));//9
                            listItem2.add(listItem.get(9));//10
                        } else if (TenVitri.equals("Tất cả công tác")) {
                            listItem2.clear();
                            listItem2.addAll(listItem);
                        } else if (TenVitri.equals("Nhập dữ liệu đo đạc")){
                            Intent intent3 = new Intent(hangmucActivity.this, FormMain.class);
                            intent3.putExtra("TenTram", tenTram);  // Truyền một String
                            intent3.putExtra("SoMong", "0");  // Truyền một String
                            intent3.putExtra("SoDot", "0");  // Truyền một String
                            intent3.putExtra("ViTriDat", "Trên mái");  // Truyền một String
                            intent3.putExtra("SoTangDay", "0");  // Truyền một String
                            intent3.putExtra("SoChanCot", "3");  // Truyền một String
                            intent3.putExtra("KichThuocMong", "0.5x0.5x0.6");  // Truyền một String
                            intent3.putExtra("KichThuocCot", "0.3x0.3");  // Truyền một String
                            intent3.putExtra("Long", longi);  // Truyền một String
                            intent3.putExtra("Lat", latgi);  // Truyền một String
                            intent3.putExtra("ViTri", vitrichup);  // Truyền một String
                            startActivity(intent3);

                        }
                        /***/
                    } else if (listItem.size() == 8) {
                        /**KIỂM TRA VỊ TRÍ LÀM VIỆC*/
                        if (TenVitri.equals("Công tác trên cao")) {
                            listItem2.clear();
                            listItem2.add(listItem.get(1));
                            listItem2.add(listItem.get(5));
                            listItem2.add(listItem.get(7));
                            TenChiTiet = "Đốt D1";
                        } else if (TenVitri.equals("Công tác dưới đất")) {
                            listItem2.clear();
                            listItem2.add(listItem.get(0));//1
                            listItem2.add(listItem.get(2));//3
                            listItem2.add(listItem.get(3));//4
                            listItem2.add(listItem.get(4));//5
                            listItem2.add(listItem.get(5));//6
                            listItem2.add(listItem.get(6));//7
                            listItem2.add(listItem.get(7));//8
                            TenChiTiet="Móng M1";
                        } else if (TenVitri.equals("Tất cả công tác")) {
                            listItem2.clear();
                            listItem2.addAll(listItem);
                        } else if (TenVitri.equals("Nhập dữ liệu đo đạc")){
                            Intent intent3 = new Intent(hangmucActivity.this, FormMain_CotTuDung.class);
                            intent3.putExtra("TenTram", tenTram);  // Truyền một String
                            intent3.putExtra("SoMong", "4");  // Truyền một String
                            intent3.putExtra("SoDot", "0");  // Truyền một String
                            intent3.putExtra("ViTriDat", "Trên mái");  // Truyền một String
                            intent3.putExtra("KichThuocMong", "0.58x0.58x0.6 m");  // Truyền một String
                            intent3.putExtra("KichThuocChanCot", "0.46x0.46 m");  // Truyền một String
                            intent3.putExtra("KichThuocDinhCot", "2.7x2.7 m");  // Truyền một String
                            intent3.putExtra("SoChanCot", "4");  // Truyền một String
                            intent3.putExtra("Long", longi);  // Truyền một String
                            intent3.putExtra("Lat", latgi);  // Truyền một String
                            intent3.putExtra("ViTri", vitrichup);  // Truyền một String
                            startActivity(intent3);
                        }
                    }
                    if (listItem2.size() == 1)
                        listItem2 = listItem;
                    else if (listItem2.size() > 2)
                        getProductList(listItem2);
                    dialog.dismiss();
                }
            });

    }
    private  void luuphuluc(){
       File PHULUCStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
       PHULUCStorageDir = new File(PHULUCStorageDir,tenTram);
        File[] files=PHULUCStorageDir.listFiles();
        listFile.clear();
        phulucluu="";
        int biendem=0;
        for (File file:files){
            if (file.getName()!= "\n") {
                //{Log.d("folder hạng muc",file.getName());
                phulucluu =phulucluu + "--" + file.getName();
                biendem++;
                File PHULUCStorageDir2 = new File(PHULUCStorageDir, file.getName());
                files2 = PHULUCStorageDir2.listFiles();
                listFile.clear();
            }
            for (File fileCT:files2){
                if (fileCT.getName()!= "\n") {

                    //Log.d("folder cong tac",fileCT.getName());
                    phulucluu = phulucluu + "_" + fileCT.getName()+ "_";

                }
            }

        }
       // Log.d("folder cong tac",phulucluu);
        //Log.d("folder cong tac", String.valueOf(biendem));
        if (biendem==10)
            saveDataOnCacher(phulucluu,"COTDAYCO");
        else if (biendem==8)
            saveDataOnCacher(phulucluu,"COTTUDUNG");
        Toast.makeText(getApplicationContext(), "Đã lưu phụ lục bạn vừa thay đổi", Toast.LENGTH_SHORT).show();

    };
    public void saveDataOnCacher(String text,String Name){
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
                listItem.add(ar.get(y).toString());
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
    class Sortbyroll implements Comparator<Student>{
        // Used for sorting in ascending order of
        // roll number
        public int compare(Student a, Student b)
        {
            return a.rollno - b.rollno;
        }
    }
}
