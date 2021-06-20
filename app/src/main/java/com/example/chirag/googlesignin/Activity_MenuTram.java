package com.example.chirag.googlesignin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Activity_MenuTram extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    //region Đặt các biến
    private ViewStub stubGrid;
    private ViewStub stubList;
    Dialog dialog;
    TextView tvToaDo, tvViTri;
    ListView listView;
    ImageButton btnRestart, btnSearch, btnGrid;
    private String vitrichup = "null", thanhpho = "null", longi = "null", latgi = "null";
    private GridView girdView;
    private Adapter_Listview listViewAdapterListview;
    private Adapter_Gridview adapterGridview;
    private List<DoiTuong_Tram> danhsachTram;
    List<String> listFile = new ArrayList<String>();
    ArrayList<String> listTram = new ArrayList<String>();
    int vt;
    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    boolean checkkey = false;
    private int curnntView = VIEW_MODE_LISTVIEW;
    public static final int RequestPermissionCode = 1;
    GoogleApiClient googleApiClient;
    FusedLocationProviderClient fusedLocationProviderClient;
    int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    FloatingActionButton fab;


    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_tram_main);
        CapQuyen();
        TaoThuVien();
        AnhXa();
        NhanBien();
        KetNoiGmail();
        SettupListView();
        SuKien();
    }

    //region Sự kiện
    private void SuKien() {
        listView.setOnItemLongClickListener(onItemLongClickListener);
        listView.setOnItemClickListener(onItemClick);
        girdView.setOnItemLongClickListener(onItemLongClickListener);
        girdView.setOnItemClickListener(onItemClick);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogThemTram(Gravity.CENTER, "Thêm trạm mới", "Thêm trạm");
            }
        });
        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VIEW_MODE_LISTVIEW == curnntView) {
                    curnntView = VIEW_MODE_GRIDVIEW;
                    btnGrid.setImageResource(R.drawable.ic_view_module_black_24dp);
                } else {
                    curnntView = VIEW_MODE_LISTVIEW;
                    btnGrid.setImageResource(R.drawable.ic_view_list_black_24dp);
                }
                switchView();
            }
        });
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(Activity_MenuTram.this, Activity_DanhSach_Cot.class);
            intent.putExtra("MaTram", danhsachTram.get(position).getMaTram());
            intent.putExtra("DiaDiem", vitrichup);
            intent.putExtra("ToaDo", tvToaDo.getText().toString());
            startActivity(intent);
        }
    };
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

    //endregion
    private void NhanBien() {
    }

    private void TaoThuVien() {
        if (SPC.TaoThuMuc(SPC.pathDataApp_PNDT))
            Toast.makeText(Activity_MenuTram.this, "Đã tạo bộ nhớ", Toast.LENGTH_SHORT).show();
        if (SPC.TaoThuMuc(SPC.pathTemplate))
            Toast.makeText(Activity_MenuTram.this, "Đã lưu thư viện", Toast.LENGTH_SHORT).show();

        SPC.saveTextFile("ListAnten", SPC.ListAnten, SPC.pathTemplate);
        SPC.saveTextFile("BangSuyHao", SPC.BangSuyHao, SPC.pathTemplate);
        SPC.saveTextFile("ListThietBi", SPC.ListThietBi, SPC.pathTemplate);
        SPC.LayDanhSachThietBi();

    }

    private void AnhXa() {
        stubGrid = (ViewStub) findViewById(R.id.stub_grid1);
        stubList = (ViewStub) findViewById(R.id.stub_list);
        tvToaDo = findViewById(R.id.tvToaDo);
        tvViTri = findViewById(R.id.tvViTri);
        stubList.inflate();
        stubGrid.inflate();
        girdView = (GridView) findViewById(R.id.myGrid);
        listView = (ListView) findViewById(R.id.mylistView);
        btnRestart = findViewById(R.id.btnRestart);
        btnSearch = findViewById(R.id.btnSearch);
        btnGrid = findViewById(R.id.btnGrid);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    //region Settup ListView
    private void SettupListView() {
        getProductList();
        SharedPreferences sharedPreferences = getSharedPreferences("ViewsMode", MODE_PRIVATE);
        curnntView = sharedPreferences.getInt("currentView", VIEW_MODE_LISTVIEW);
        switchView();
    }

    private void switchView() {
        if (VIEW_MODE_LISTVIEW == curnntView) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.GONE);
            //Display gridview
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }

    private void setAdapters() {
        if (VIEW_MODE_LISTVIEW == curnntView) {
            listViewAdapterListview = new Adapter_Listview(this, R.layout.list_item, danhsachTram);
            listView.setAdapter(listViewAdapterListview);
        } else {
            adapterGridview = new Adapter_Gridview(this, R.layout.grid_item, danhsachTram);
            girdView.setAdapter(adapterGridview);
        }
    }

    public List<DoiTuong_Tram> getProductList() {
        danhsachTram = new ArrayList<>();
        File[] files = SPC.pathDataApp_PNDT.listFiles();
        listFile.clear();
        try {
            for (File file : files) {
                File fileThietKe = new File(SPC.pathDataApp_PNDT, file.getName().trim() + SPC.DuongDanFileThietKeTram);
                if (fileThietKe.exists()) {
                    XML_Tram xml_tram = SPC.readXml(fileThietKe);
                    if (xml_tram != null) {
                        String SoTramGoc = "0" + DemSoTramGoc(file);
                        String thietke = xml_tram.getThietKeTram();
                        JSONObject jsonObject = new JSONObject(thietke);
                        if (!thietke.equals("")) {
                            String MaTram = jsonObject.getString("MaTram");
                            String DiaDiem = jsonObject.getString("DiaDiem");
                            String NgaySua = jsonObject.getString("NgayDo");
                            System.out.println(MaTram);
                            danhsachTram.add(new DoiTuong_Tram(MaTram, NgaySua, DiaDiem, SoTramGoc));
                        }
                    }

                }
            }
            listViewAdapterListview = new Adapter_Listview(this, R.layout.list_item, danhsachTram);
            listView.setAdapter(listViewAdapterListview);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return danhsachTram;
    }

    private String DemSoTramGoc(File fileTram) {
        int sotram = 0;
        File[] listCot = new File(fileTram, "DuLieu").listFiles();
        for (File cot : listCot) {
            if (cot.isDirectory()) {
                File[] listTram = cot.listFiles();
                sotram += listTram.length - 1;
            }
        }
        return String.valueOf(sotram);
    }

    //endregion
    //region Cấp các quyền
    private void CapQuyen() {
        initPermission();
        initPermission_W();
    }

    public void initPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(Activity_MenuTram.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(Activity_MenuTram.this, "Đang đợi cấp quyền, vui lòng đợi trong giây lát!", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    public void initPermission_W() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(Activity_MenuTram.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(Activity_MenuTram.this, "Đang đợi cấp quyền, vui lòng đợi trong giây lát!", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                String city = herelocation(location.getLatitude(), location.getLongitude());
                                //Toast.makeText(getApplicationContext(), "Vị trí hiện tại :" + city, Toast.LENGTH_SHORT).show();
                                vitrichup = city;
                                longi = Double.toString(location.getLongitude());
                                latgi = Double.toString(location.getLatitude());
                                //                                Log.d("Lccation",longi);
                                //                                Log.d("Lccation",latgi);
                                //                                Log.d("Lccation",vitrichup);
                                tvToaDo.setText(longi + "'N" + "  " + latgi + "'E");
                                tvViTri.setText(vitrichup);
                            }
                        }
                    });
        }
    }

    private String herelocation(double lat, double lom) {
        String cityName = "";
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat, lom, 10);
            if (addresses.size() > 0) {
                for (Address adr : addresses) {
                    if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                        String[] TP = adr.getAddressLine(0).split(",");
                        cityName = adr.getAddressLine(0);
                        thanhpho = TP[TP.length - 3].trim() + "," + TP[TP.length - 2].trim();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    //endregion
    //region Kết nối google
    private void KetNoiGmail() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public static void restartActivity(Activity act) {

        Intent intent = new Intent();
        intent.setClass(act, act.getClass());
        act.startActivity(intent);
        act.finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Activity_MenuTram.this, new
                String[]{ACCESS_FINE_LOCATION}, RequestPermissionCode);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("MenuTramActivity", "Connection suspendedd");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("MenuTramActivity", "Connection failed: " + connectionResult.getErrorCode());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(Activity_MenuTram.this, Activity_GoogleDrive.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(Activity_MenuTram.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    //endregion
    //region Dialog
    private void DialogMenu(int vt2) {
        final Dialog dialogthongso = new Dialog(Activity_MenuTram.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_menu_main);
        Window window = dialogthongso.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();
        vt = vt2;
        Button btnupload = (Button) dialogthongso.findViewById(R.id.btnupload);
        Button btnXoa = (Button) dialogthongso.findViewById(R.id.btnXoa);
        Button btnSua = (Button) dialogthongso.findViewById(R.id.btnmenudoiten);

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Activity_MenuTram.this);
                if (account == null)
                    signIn();
                else if (account != null) {
                    Intent intent = new Intent(Activity_MenuTram.this, Activity_GoogleDrive.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
                dialog.dismiss();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_MenuTram.this);
                builder.setTitle("Bạn muốn xóa trạm này không?");
                builder.setMessage("Thư mục sẽ bị xóa vĩnh viễn khỏi thiết bị!!");
                // add the buttons
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File file = new File(SPC.pathDataApp_PNDT, danhsachTram.get(vt).getMaTram());
                        try {
                            FileUtils.deleteDirectory(file);
                            Toast.makeText(getApplicationContext(), "Đã xóa thư mục!", Toast.LENGTH_SHORT).show();
                            getProductList();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dialogthongso.dismiss();
                    }
                });
                builder.setNegativeButton("không", null);
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDoiten(vt);
                dialogthongso.dismiss();

            }
        });
    }

    private void DialogDoiten(int vt2) {
        final Dialog dialogthongso = new Dialog(Activity_MenuTram.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_edit);
        Window window = dialogthongso.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = Gravity.CENTER;
        window.setAttributes(windowArr);
        dialogthongso.show();
        vt = vt2;

        Button btnSua = (Button) dialogthongso.findViewById(R.id.btnLuuThongSo);
        AutoCompleteTextView edtMaTram = (AutoCompleteTextView) dialogthongso.findViewById(R.id.edtMaTram);
        edtMaTram.setText(danhsachTram.get(vt).getMaTram());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtMaTram.getText().toString().trim().equals("")) {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(Activity_MenuTram.this);
                    builder.setTitle("Bạn muốn đổi tên trạm này không?");
                    // add the buttons
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            File fileOld = new File(SPC.pathDataApp_PNDT, danhsachTram.get(vt).getMaTram());
                            File fileNew = new File(SPC.pathDataApp_PNDT, edtMaTram.getText().toString());
                            File pathXmlNew = new File(fileNew,"DuLieu/Data.xml");
                            if (!fileNew.exists()) {
                                boolean result = fileOld.renameTo(fileNew);
                                System.out.println(result);
                                if (result){
                                    try {
                                        XML_Tram xml_tram = SPC.readXml(pathXmlNew);
                                        String thietke = xml_tram.getThietKeTram();
                                        JSONObject jsonObject = new JSONObject(thietke);
                                        if (!thietke.equals("")) {
                                            jsonObject.remove("MaTram");
                                            jsonObject.put("MaTram",edtMaTram.getText().toString());
                                            xml_tram.setThietKeTram(jsonObject.toString());
                                        }
                                        System.out.println(xml_tram.toString());
                                        SPC.writeXml(pathXmlNew,xml_tram);
                                    } catch (ParserConfigurationException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (SAXException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (TransformerException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(Activity_MenuTram.this, "Đã đổi tên!", Toast.LENGTH_SHORT).show();
                                    getProductList();
                                    dialogthongso.dismiss();
                                }else Toast.makeText(Activity_MenuTram.this, "Đổi tên thất bại", Toast.LENGTH_SHORT).show();

                            } else
                                Toast.makeText(Activity_MenuTram.this, "Đã có trạm này!", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.setNegativeButton("không", null);
                    // create and show the alert dialog
                    AlertDialog dialog2 = builder.create();
                    dialog2.show();

                }

            }
        });
    }

    private void DialogThemTram(int gravity, String title, String titleButton) {
        final Dialog dialogthongso = new Dialog(Activity_MenuTram.this, R.style.PauseDialog);
        dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogthongso.setContentView(R.layout.dialog_themtram);
        Window window = dialogthongso.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowArr = window.getAttributes();
        windowArr.gravity = gravity;
        window.setAttributes(windowArr);
        dialogthongso.show();
        /**
         * Ánh xạ
         */
        TextView tvTitle = dialogthongso.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        Button btnLuuThongSo = dialogthongso.findViewById(R.id.btnLuuThongSo);
        btnLuuThongSo.setText(titleButton);
        AutoCompleteTextView edtMaTram = dialogthongso.findViewById(R.id.edtMaTram);
        AutoCompleteTextView edtDiaDiem = dialogthongso.findViewById(R.id.edtDiaDiem);
        edtDiaDiem.setText(thanhpho);
        AutoCompleteTextView edtToaDo = dialogthongso.findViewById(R.id.edtToaDo);
        edtToaDo.setText(longi + "'N" + "  " + latgi + "'E");
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        AutoCompleteTextView edtViTriDat = dialogthongso.findViewById(R.id.edtViTriDat);
        AutoCompleteTextView edtNgayDo = dialogthongso.findViewById(R.id.edtNgayDo);
        edtNgayDo.setText(timeStamp);
        RadioButton checkbox_trenmai = dialogthongso.findViewById(R.id.checkbox_trenmai);
        RadioButton checkbox_duoidat = dialogthongso.findViewById(R.id.checkbox_duoidat);
        RadioGroup radioGroup = (RadioGroup) dialogthongso.findViewById(R.id.radioGroup);
        /**
         * Sự kiện
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkbox_trenmai.isChecked()) edtViTriDat.setText("Trên mái");
                else edtViTriDat.setText("Dưới đất");
            }
        });
        btnLuuThongSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AutoCompleteTextView> listAutoCompleteTextView = new ArrayList<AutoCompleteTextView>(Arrays.asList(edtMaTram, edtDiaDiem, edtToaDo, edtNgayDo, edtViTriDat));
                for (AutoCompleteTextView edt : listAutoCompleteTextView) {
                    if (edt.getText().toString().trim().equals("")) {
                        Toast.makeText(Activity_MenuTram.this, "Hãy nhập đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        File pathTramMoi = new File(SPC.pathDataApp_PNDT, edtMaTram.getText().toString());
                        File pathHinhAnh = new File(pathTramMoi, "HinhAnh");
                        File pathDuLieu = new File(pathTramMoi, "DuLieu");
                        SPC.TaoThuMuc(pathTramMoi);
                        SPC.TaoThuMuc(pathDuLieu);
                        SPC.TaoThuMuc(pathHinhAnh);
                        if (pathDuLieu.exists()) {
                            try {
                                XML_Tram xml_tram = new XML_Tram();
                                JSONObject obj = new JSONObject();
                                for (int i = 0; i < listAutoCompleteTextView.size(); i++) {
                                    obj.put(SPC.ThietKeTram.get(i), listAutoCompleteTextView.get(i).getText().toString());
                                }
                                xml_tram.setThietKeTram(obj.toString());
                                SPC.saveToXml(xml_tram, "Data", pathDuLieu);
//                                SPC.SaveListAutoCompleteTextView_json("ThietKeTram",pathDuLieu,listAutoCompleteTextView,SPC.ThietKeTram);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParserConfigurationException e) {
                                e.printStackTrace();
                            } catch (SAXException e) {
                                e.printStackTrace();
                            } catch (TransformerException e) {
                                e.printStackTrace();
                            }
                            getProductList();
                            Toast.makeText(Activity_MenuTram.this, "Đã tạo trạm " + pathTramMoi.getName(), Toast.LENGTH_SHORT).show();
                            dialogthongso.dismiss();
                        }
                    }

                }
            }
        });

    }

    ;
    //endregion
}