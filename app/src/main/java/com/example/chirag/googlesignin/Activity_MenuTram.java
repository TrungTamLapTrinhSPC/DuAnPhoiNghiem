package com.example.chirag.googlesignin;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Activity_MenuTram extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    //region Đặt các biến
        private ViewStub stubGrid;
        private ViewStub stubList;
        Dialog dialog;
        TextView tvToaDo,tvViTri;
        ListView listView;
        ImageButton btnRestart,btnSearch,btnGrid;
        private String vitrichup= "null",thanhpho ="null", longi = "null", latgi = "null";
        private GridView girdView;
        private Adapter_Listview listViewAdapterListview;
        private Adapter_Gridview adapterGridview;
        private List<DoiTuong_Tram> doiTuongTramList;
        private File mediaStorageDir;
        List<String> listFile = new ArrayList<String>();
        ArrayList<String> listTram = new ArrayList<String>();
        int vt;
        static final  int VIEW_MODE_LISTVIEW = 0;
        static final  int VIEW_MODE_GRIDVIEW = 1;
        boolean checkkey=false;
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
                    DialogThemTram(Gravity.CENTER);
                }
            });
            btnGrid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(VIEW_MODE_LISTVIEW == curnntView) {
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
    private void TaoThuVien(){
        if(SPC.TaoThuMuc(SPC.pathDataApp_PNDT))
            Toast.makeText(Activity_MenuTram.this,"Đã lưu thư viện", Toast.LENGTH_SHORT).show();
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
        btnRestart =  findViewById(R.id.btnRestart);
        btnSearch = findViewById(R.id.btnSearch);
        btnGrid = findViewById(R.id.btnGrid);
        fab = (FloatingActionButton) findViewById(R.id.fab);

    }
    //region Settup ListView
        private void SettupListView()
        {
            mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
            getProductList(mediaStorageDir);
            SharedPreferences sharedPreferences = getSharedPreferences("ViewsMode", MODE_PRIVATE);
            curnntView = sharedPreferences.getInt("currentView", VIEW_MODE_LISTVIEW);
            switchView();
        }
        private void switchView(){
            if(VIEW_MODE_LISTVIEW == curnntView) {
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
        private void setAdapters(){
            if(VIEW_MODE_LISTVIEW == curnntView) {
                listViewAdapterListview = new Adapter_Listview(this, R.layout.list_item, doiTuongTramList);
                listView.setAdapter(listViewAdapterListview);
            } else
            {
                adapterGridview = new Adapter_Gridview(this, R.layout.grid_item, doiTuongTramList);
                girdView.setAdapter(adapterGridview);
            }
        }
        public List<DoiTuong_Tram> getProductList(File f){
            doiTuongTramList = new ArrayList<>();
            File[] files=f.listFiles();
            listFile.clear();

                for (File file:files)
                {
                    String[] output = file.getName().split("_");
                    listTram.add(output[0]);
                    listTram.add(output[1]);
                    doiTuongTramList.add(new DoiTuong_Tram(R.drawable.cotdayco, output[0], output[1]));
                }


            listViewAdapterListview = new Adapter_Listview(this, R.layout.list_item, doiTuongTramList);
            listView.setAdapter(listViewAdapterListview);
            return doiTuongTramList;
        }
    //endregion
    //region Cấp các quyền
        private void CapQuyen() {
            initPermission();
            initPermission_W();
        }
        public void initPermission(){

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
        public void initPermission_W(){
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
                                    tvToaDo.setText(longi + "'N" +"  "+ latgi+ "'E");
                                    tvViTri.setText(vitrichup);
                                }
                            }
                        });
            }
        }
        private  String herelocation(double lat,double lom){
            String cityName="";
            Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocation(lat,lom,10);
                if(addresses.size()>0){
                    for(Address adr:addresses){
                        if(adr.getLocality() != null && adr.getLocality().length() >0)
                        {String[] TP = adr.getAddressLine(0).split(",");
                            cityName = adr.getAddressLine(0);
                            thanhpho =TP[TP.length-3].trim() + ","+ TP[TP.length-2].trim();
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

        public static void restartActivity(Activity act){

            Intent intent=new Intent();
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
        protected void onStop(){
            if (googleApiClient.isConnected()){
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
        public boolean onKeyDown(int keyCode, KeyEvent event)  {
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
                intent.putExtra("TenTram", doiTuongTramList.get(vt).getTitle()+"_"+ doiTuongTramList.get(vt).getDescription());  // Truyền một String
                startActivity(intent);        } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
                Toast.makeText(Activity_MenuTram.this, "Failed", Toast.LENGTH_LONG).show();
            }
        }
    //endregion
    //region Dialog
        private void DialogMenu(int vt2){
            dialog = new Dialog(Activity_MenuTram.this,R.style.PauseDialog);
            dialog.setContentView(R.layout.dialog_menu_main);
            dialog.show();
            vt = vt2;
            Button btnupload = (Button) dialog.findViewById(R.id.btnupload);
            btnupload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Activity_MenuTram.this);
                    if (account==null)
                        signIn();
                    else if(account != null)
                    {
                        Intent intent = new Intent(Activity_MenuTram.this, Activity_GoogleDrive.class);
                        intent.putExtra("TenTram", doiTuongTramList.get(vt).getTitle()+"_"+ doiTuongTramList.get(vt).getDescription());  // Truyền một String
                        startActivity(intent);
                        dialog.dismiss();
                    }
                    dialog.dismiss();
                }
            });
        }
        private void DialogThemTram(int gravity){
            final Dialog dialogthongso = new Dialog(Activity_MenuTram.this,R.style.PauseDialog);
            dialogthongso.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogthongso.setContentView(R.layout.dialog_themtram);
            Window window= dialogthongso.getWindow();
            if (window==null){return;}
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowArr = window.getAttributes();
            windowArr.gravity = gravity;
            window.setAttributes(windowArr);
            dialogthongso.show();
        };
    //endregion
}