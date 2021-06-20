package com.example.chirag.googlesignin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import static com.example.chirag.googlesignin.DriveServiceHelper.getGoogleDriveService;

public class ActivityMenu extends AppCompatActivity {
    LinearLayout btn_TongQuanBaoCao,tv_PhuLucAnh,tv_TrangChinh,upload;
    ImageButton btn_BackMenu;
    private static final int REQUEST_CODE_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private Class_DriveServiceHelper mClassDriveServiceHelper;

    Drive mDriveService;
    private static final String TAG = "MainActivity";
    LinearLayout sign_out;
    TextView nameTV;
    TextView emailTV,phantram;
    TextView matram,txtKQ;
    ImageView photoIV;
    GoogleSignInAccount acct;
    ProgressBar progressBar;
            String MaTram,ViTri,ToaDo;
    int BienDem=1,tongso =0,SoFile = 0;
    double tyle = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        AnhXa();
        NhanBien();
        SuKien();
    }

    private void SuKien() {
        btn_TongQuanBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ActivityMenu.this,ActivityXemTruocBaoCao.class);
                intent.putExtra("MaTram",MaTram);
                startActivity(intent);
            }
        });
        btn_BackMenu .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_TrangChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ActivityMenu.this,Activity_MenuTram.class);
                startActivity(intent);
            }
        });

        tv_PhuLucAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ActivityMenu.this,Activity_DanhSach_AnhChup.class);
                intent.putExtra("MaTram",MaTram);
                intent.putExtra("DiaDiem",ViTri);
                intent.putExtra("ToaDo",ToaDo);
                startActivity(intent);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMenu.this);
                builder.setTitle("Bạn muốn gửi folder này lên Google drive?");
                // add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        uploadToDrive(MaTram);
                    }
                });
                builder.setNegativeButton("không", null);
                // create and show the alert dialog
                AlertDialog dialog2 = builder.create();
                dialog2.show();

            }
        });

    }

    private void NhanBien()
    {
            Intent intent = getIntent();
            MaTram = intent.getStringExtra("MaTram");
            ViTri = intent.getStringExtra("DiaDiem");
            ToaDo = intent.getStringExtra("ToaDo");
            matram.setText(MaTram);
    }

    private void AnhXa() {
        btn_TongQuanBaoCao = findViewById(R.id.btn_TongQuanBaoCao);
        btn_BackMenu = findViewById(R.id.btnBackMenu);
        tv_TrangChinh = findViewById(R.id.tv_TrangChinh);
        nameTV = findViewById(R.id.name);
        emailTV = findViewById(R.id.email);
        matram = findViewById(R.id.matram);
        txtKQ = findViewById(R.id.txtKQ);
        upload = findViewById(R.id.upload);
        tv_PhuLucAnh = findViewById(R.id.tv_PhuLucAnh);
        photoIV = findViewById(R.id.photo);
        phantram = findViewById(R.id.phantram);
        progressBar = findViewById(R.id.simpleProgressBar);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        acct = GoogleSignIn.getLastSignedInAccount(ActivityMenu.this);
        GoogleSignInClient client = GoogleSignIn.getClient(this, gso);
        startActivityForResult(client.getSignInIntent(),400);

        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            nameTV.setText(personName);
            emailTV.setText(personEmail);
            Glide.with(this).load(personPhoto).into(photoIV);

        }
    }
    public void upload(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMenu.this);
        builder.setTitle("Bạn muốn gửi folder này lên Google drive?");
        // add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DialogAdd();
            }
        });
        builder.setNegativeButton("không", null);
        // create and show the alert dialog
        AlertDialog dialog2 = builder.create();
        dialog2.show();
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ActivityMenu.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(ActivityMenu.this, MenuTramActivity.class));
                        finish();
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (account == null) {

            signIn();

        } else {
            mClassDriveServiceHelper = new Class_DriveServiceHelper(Class_DriveServiceHelper.getGoogleDriveService(getApplicationContext(), account, "appName"));
        }
    }
    private void signIn() {

        mGoogleSignInClient = buildGoogleSignInClient();
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }
    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .requestEmail()
                        .build();
        return GoogleSignIn.getClient(getApplicationContext(), signInOptions);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData);
                }
                break;


        }

        super.onActivityResult(requestCode, resultCode, resultData);
    }
    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        Log.d(TAG, "Signed in as " + googleSignInAccount.getEmail());
                        mClassDriveServiceHelper = new Class_DriveServiceHelper(Class_DriveServiceHelper.getGoogleDriveService(getApplicationContext(), googleSignInAccount, "appName"));
                        Log.e(TAG, "handleSignInResult: " + mDriveService);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unable to sign in.", e);
                    }
                });
    }
    private String DialogAdd(){
        final Dialog dialog = new Dialog(this,R.style.PauseDialog);
        dialog.setContentView(R.layout.dialog_user_drive);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        File userName = new File(Environment.getExternalStorageDirectory(),"Template");
        userName = new File(userName,"UserName.txt");
        EditText edtTenNguoiDung = dialog.findViewById(R.id.edtTenNguoiDung);
        Button btnOK = dialog.findViewById(R.id.btnOK);
        Button btnThoat = dialog.findViewById(R.id.btnthoat);
        String name = "";
        if(userName.exists())
        {
            name = readText(userName);
        }
        if (!name.equals(""))
            edtTenNguoiDung.setText(name);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File finalUserName = new File(Environment.getExternalStorageDirectory(),"Template");
                String userName = edtTenNguoiDung.getText().toString();
                saveTextFile("UserName", userName, finalUserName);
                if(userName.length() > 2) {
                    if (mClassDriveServiceHelper == null) {
                        return;
                    }
                    mClassDriveServiceHelper.searchFolder(matram.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
                                @Override
                                public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
                                    if (googleDriveFileHolders.size() == 0) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMenu.this);
                                        builder.setTitle("Chưa có thư mục này trên Drive! Bạn muốn tiếp tục cập nhật thư mục này?");
                                        // add the buttons
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

//                                                TaiThuMucDuLieu(matram.getText().toString(), userName);
//                                                TaiThuMucAnh(matram.getText().toString());
                                            }
                                        });
                                        builder.setNegativeButton("không", null);
                                        // create and show the alert dialog
                                        AlertDialog dialog2 = builder.create();
                                        dialog2.show();
                                    } else {

//                                        TaiThuMucDuLieu(matram.getText().toString(), userName);
//                                        TaiThuMucAnh(matram.getText().toString());
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                }
                            });
                } else {Toast.makeText(ActivityMenu.this,"Bạn chưa nhập tên người dùng!",Toast.LENGTH_SHORT).show();}
                dialog.dismiss();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return name;
    }
    public void saveTextFile(String name, String text, File file){
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
    public String readText(File file){
        String text = "";
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            text = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
    /**
     *
     */
    private static boolean mBusy;
    String title;
    @SuppressLint("StaticFieldLeak")
    private void uploadToDrive(final String tenTram) {
        if (tenTram != null && !mBusy) {
            txtKQ.setText("Bắt đầu tải dữ liệu:\n");
            new AsyncTask<Void, String, Void>() {
                private String findOrCreateFolder(String prnt, String titl,String kieu, final File fl){
                    String id = null;
                    if (kieu.equals("folder"))
                    {
                        List<GoogleDriveFileHolder> cvs = mClassDriveServiceHelper.search(prnt,titl);
                        BienDem++;
                        if (cvs.size() > 0)
                        {
                            title = "Đã có: ";
                            id =  cvs.get(0).getId();
                        }
                        else
                        {
                            id = mClassDriveServiceHelper.CreateFoler(prnt, titl);
                            title = "Đã đồng bộ: ";
                        }
                        if (id != null && !title.equals("Đã có: "))
                            title += titl;
                        else if (id != null && title.equals("Đã có: "))
                            title = "";
                        else
                            title = "Chưa tạo: " + titl;
                        publishProgress(title);
                    }
                    else if (kieu.equals("image"))
                    {
                        List<GoogleDriveFileHolder> cvs = mClassDriveServiceHelper.seachFile(prnt,titl,"image/jpeg");
                        BienDem++;
                        if (cvs.size() > 0) {
                            title = "Đã có: ";
                            id =  cvs.get(0).getId();
                        } else {
                            //id = mDriveServiceHelper.CreateFoler(prnt, titl);
                            try
                            {
                                id = mClassDriveServiceHelper.uploadImage(fl, "image/jpeg", prnt);
                                title = "Đã đồng bộ: ";
                            }catch (Exception e){
                                title = "Chưa tạo: ";
                            }
                        }

                        if (id != null && !title.equals("Đã có: "))
                            title += titl;
                        else if (id != null && title.equals("Đã có: "))
                            title = "";
                        else
                            title = "Chưa tạo: " + titl;

                        publishProgress(title);
                    }
                    return id;
                }
                private String up_HinhAnh(String id,java.io.File folder){
                    for (java.io.File child_file : folder.listFiles())
                    {
                        if(child_file.isDirectory())
                        {

                            String id_img = findOrCreateFolder(id,child_file.getName(),"folder",null);
                            BienDem++;
                            Log.d("TEST",child_file.getPath());
                            for (java.io.File img_file : child_file.listFiles())
                            {

                                List<GoogleDriveFileHolder> cvs = mClassDriveServiceHelper.seachFile(id_img,img_file.getName(),"image/jpeg");
                                if (cvs.size() == 0)
                                {
                                    try {
                                        BienDem++;
                                        mClassDriveServiceHelper.uploadImage(img_file, "image/jpeg",id_img);
                                    }
                                    catch (Exception e)
                                    {
                                        Log.d("Lỗi: ",img_file.getPath());
                                    }
                                }
                                Log.d("TEST",img_file.getPath());
                            }
                        }

                    }
                    return id;
                }

                private String[] upload(String id, File child_file){
                        String[] KQ = new String[0];
                        if(child_file.isDirectory())
                        {

                            KQ = new String[]{findOrCreateFolder(id, child_file.getName(), "folder", null).toString(), "folder"};
                            Log.d("TEST",child_file.getPath());
                        }
                        else if(child_file.isFile() && child_file.getName().contains(".txt")){

                            List<GoogleDriveFileHolder> cvs = mClassDriveServiceHelper.search(id,child_file.getName());
                            BienDem++;
                            //Log.d("TT", String.valueOf(cvs.size()));
                            if (cvs.size() == 0) {
                                KQ = new String[]{mClassDriveServiceHelper.uploadImage(child_file,UT.MIME_TXT, id),"text"};
                            } else if (cvs.size() != 0) {
                                try {
                                    mClassDriveServiceHelper.delete(cvs.get(0).getId());
                                    KQ = new String[]{findOrCreateFolder(id, child_file.getName(),"text",child_file),"text"};

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            Log.d("TEST",child_file.getPath());
                        }
                        else if(child_file.isFile() && child_file.getName().contains(".jpg")){

                            List<GoogleDriveFileHolder> cvs = mClassDriveServiceHelper.seachFile(id,child_file.getName(),"image/jpeg");
                            if (cvs.size() == 0)
                            {
                                try {
                                    KQ = new String[]{mClassDriveServiceHelper.uploadImage(child_file, "image/jpeg",id),"image"};

                                }
                                catch (Exception e)
                                {
                                    Log.d("Lỗi: ",child_file.getPath());
                                }
                            }
                            Log.d("TEST",child_file.getPath());
                        }

                    return KQ;
                }
                //Tạo tree file
                @SuppressLint("WrongThread")
                @Override
                protected Void doInBackground(Void... params) {
                    BienDem = 1;
                    tongso = DemSoFile(tenTram);
                    progressBar.setMax(tongso);
                    mBusy = true;
                    File folder = new File(SPC.pathDataApp_PNDT,tenTram);
                    String IDfolderTRAMTong = findOrCreateFolder("root", tenTram,"folder",null);
                    File DuLieu = new File(folder,"DuLieu");
                    String id_DuLieu = findOrCreateFolder(IDfolderTRAMTong,DuLieu.getName(),"folder",null);

                    File HinhAnh = new File(folder,"HinhAnh");
                    String id_HinhAnh = findOrCreateFolder(IDfolderTRAMTong,HinhAnh.getName(),"folder",null);
                    up_HinhAnh(id_HinhAnh,HinhAnh);
                    for (File File_Cot:DuLieu.listFiles()){

                        //TẠO DANH SACH THƯ MỤC CÔT
                        String[] id_COT = upload(id_DuLieu,File_Cot);
                        if(id_COT[1].equals("folder")){
                            //TẠO DANH SÁCH THU MỤC BTS
                            for(File File_BTS:File_Cot.listFiles())
                            {
                                String[] id_BTS = upload(id_COT[0],File_BTS);
                                if(id_BTS[1].equals("folder")){
                                    //TẠO DANH SÁCH THU MỤC ANTEN
                                    for(File File_ANTEN:File_BTS.listFiles()){
                                        String[] id_ANTEN = upload(id_BTS[0],File_ANTEN);

                                        if(id_ANTEN[1].equals("folder")){

                                            //TẠO DANH SÁCH THU MỤC CONG TRINH
                                            for(File File_CongTrinh:File_ANTEN.listFiles()){
                                                String[] id_CongTrinh = upload(id_ANTEN[0],File_CongTrinh);

                                                if(id_CongTrinh[1].equals("folder")){
                                                    for(File File_Text:File_CongTrinh.listFiles()){
                                                        String[] id_Text = upload(id_CongTrinh[0],File_Text);

                                                    }
                                                }

                                            }


                                        }

                                    }
                                }
                            }
                        }
                    }

                    return null;
                }

                @Override
                protected void onProgressUpdate(String... strings) { super.onProgressUpdate(strings);
                    if (strings[0].contains("Đã có: ")){
                        BienDem++;
                        tyle = (Double.valueOf(BienDem)/Double.valueOf(tongso))*100;
                        progressBar.setProgress(BienDem);
                        phantram.setVisibility(View.VISIBLE);
                        phantram.setText(Math.round(tyle) + " %");
                    }
                    else
                    {
                        BienDem++;
                        tyle = (Double.valueOf(BienDem)/Double.valueOf(tongso))*100;
                        progressBar.setProgress(BienDem);
                        phantram.setVisibility(View.VISIBLE);
                        phantram.setText(Math.round(tyle) + " %");
                        txtKQ.append("\n" + strings[0]);
                    }
                }
                @Override
                protected void onPostExecute(Void nada) { super.onPostExecute(nada);
                    txtKQ.setText("Đã tải lên toàn bộ dữ liệu \n");
                    txtKQ.append("Đã tải lên toàn bộ hình ảnh");
                    phantram.setText(100 + " %");
                    mBusy = false;
                }
            }.execute();
        }
    }
    public Integer DemSoFile(String tenTram){
        java.io.File folder = new java.io.File(SPC.pathDataApp_PNDT,tenTram);
        GetlistFile(folder);
        return SoFile;
    }
    public void GetlistFile(java.io.File folder){
        /*for (java.io.File child_file : folder.listFiles()) {
            if(child_file.isDirectory()){
                SoFile++;
                //Log.d("TEST",child_file.getPath());
                GetlistFile(child_file);
            }
            else if(child_file.isFile() && child_file.getName().contains(".txt")){
                SoFile++;
                //Log.d("TEST",child_file.getPath());

            }
            else if(child_file.isFile() && child_file.getName().contains(".jpg")){
                SoFile++;
                //Log.d("TEST",child_file.getPath());
            }
        }*/
        File DuLieu = new File(folder,"DuLieu");
        File HinhAnh = new File(folder,"HinhAnh");
        SoFile = 3;
        //Đếm số lượng file có trong Dulieu
        int count_DuLieu = DuLieu.listFiles().length;
        SoFile += count_DuLieu;
        //Đếm số lượng file có trong HinhAnh
        int count_HinhAnh = HinhAnh.listFiles().length;
        SoFile += count_HinhAnh;
        for (File item_Cot:DuLieu.listFiles()){
            if (item_Cot.isDirectory() && !item_Cot.getName().contains("DanhSachBTS"))
            {
                //Đếm số lượng file có trong Cot
                int count_Cot = item_Cot.listFiles().length;
                SoFile += count_Cot;
                for (File item_BTS:item_Cot.listFiles())
                {
                    if (item_BTS.isDirectory())
                    {
                        //Đếm số lượng file có trong BTS
                        int count_BTS = item_Cot.listFiles().length;
                        SoFile += count_BTS;
                        for (File item_Anten:item_BTS.listFiles())
                        {
                            if (item_Anten.isDirectory())
                            {
                                //Đếm số lượng file có trong Anten
                                int count_Anten = item_Anten.listFiles().length;
                                SoFile += count_Anten;
                                for (File item_CongTrinh:item_BTS.listFiles())
                                {
                                    if (item_CongTrinh.isDirectory())
                                    {
                                        //Đếm số lượng file có trong CongTrinh
                                        int count_CongTrinh = item_CongTrinh.listFiles().length;
                                        SoFile += count_CongTrinh;
                                    }

                                }
                            }

                        }
                    }
                }
            }
            else if (item_Cot.isDirectory() && item_Cot.getName().contains("DanhSachBTS"))
            {
                //Đếm số lượng file có trong DanhSachBTS
                int count_DanhSachBTS = item_Cot.listFiles().length;
                SoFile += count_DanhSachBTS;
            }
        }
    }
}