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
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.chirag.googlesignin.Class_DriveServiceHelper.getGoogleDriveService;


public class Activity_GoogleDrive extends AppCompatActivity {
    private static final int REQUEST_CODE_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private Class_DriveServiceHelper mClassDriveServiceHelper;
    Drive mDriveService;
    private static final String TAG = "MainActivity";
    LinearLayout sign_out;
    TextView nameTV;
    TextView emailTV,phantram;
    TextView idTV,matram,txtKQ;
    ImageView photoIV;
    GoogleSignInAccount acct;
    ProgressBar progressBar;
    int BienDem=1,tongso =0;
    double tyle = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        sign_out = findViewById(R.id.log_out);
        nameTV = findViewById(R.id.name);
        emailTV = findViewById(R.id.email);
        idTV = findViewById(R.id.id);
        matram = findViewById(R.id.matram);
        txtKQ = findViewById(R.id.txtKQ);
        photoIV = findViewById(R.id.photo);
        phantram = findViewById(R.id.phantram);
        progressBar = findViewById(R.id.simpleProgressBar);
        Intent intent = getIntent();
        String tenTram = intent.getStringExtra("TenTram");
        matram.setText(tenTram);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        acct = GoogleSignIn.getLastSignedInAccount(Activity_GoogleDrive.this);
        GoogleSignInClient client = GoogleSignIn.getClient(this, gso);
        startActivityForResult(client.getSignInIntent(),400);


        //driveServiceHelper = new DriveServiceHelper(googleDriveService);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            nameTV.setText("Name: "+personName);
            emailTV.setText("Email: "+personEmail);
            idTV.setText("ID: "+personId);
            Glide.with(this).load(personPhoto).into(photoIV);

        }

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }
    /**public void upload(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        builder.setTitle("Bạn muốn gửi folder này lên Google drive?");
        // add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mDriveServiceHelper == null) {
                    return;
                }
                mDriveServiceHelper.searchFolder(matram.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
                            @Override
                            public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
                                if (googleDriveFileHolders.size() ==0)
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                                    builder.setTitle("Chưa có thư mục này trên Drive! Bạn muốn tiếp tục cập nhật thư mục này?");
                                    // add the buttons
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            mDriveServiceHelper.createTree(matram.getText().toString())
                                                    .addOnSuccessListener(new OnSuccessListener<String>() {
                                                        @Override
                                                        public void onSuccess(String googleDriveFileHolder)
                                                        {
                                                            txtKQ.setText("Đã tải lên thành công!");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.e(TAG, "onFailure: " + e.getMessage());
                                                        }
                                                    });
                                        }
                                    });
                                    builder.setNegativeButton("không", null);
                                    // create and show the alert dialog
                                    AlertDialog dialog2 = builder.create();
                                    dialog2.show();
                                }
                                else
                                {
                                    mDriveServiceHelper.createTree(matram.getText().toString())
                                            .addOnSuccessListener(new OnSuccessListener<String>() {
                                                @Override
                                                public void onSuccess(String googleDriveFileHolder)
                                                {
                                                    txtKQ.setText("Đã tải lên thành công!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e(TAG, "onFailure: " + e.getMessage());
                                                }
                                            });
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.getMessage());
                            }
                        });


            }
        });
        builder.setNegativeButton("không", null);
        // create and show the alert dialog
        AlertDialog dialog2 = builder.create();
        dialog2.show();
    }*/
    public void upload(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_GoogleDrive.this);
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
                        Toast.makeText(Activity_GoogleDrive.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Activity_GoogleDrive.this, Activity_MenuTram.class));
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
            mClassDriveServiceHelper = new Class_DriveServiceHelper(getGoogleDriveService(getApplicationContext(), account, "appName"));
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
                        mClassDriveServiceHelper = new Class_DriveServiceHelper(getGoogleDriveService(getApplicationContext(), googleSignInAccount, "appName"));

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
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_GoogleDrive.this);
                                        builder.setTitle("Chưa có thư mục này trên Drive! Bạn muốn tiếp tục cập nhật thư mục này?");
                                        // add the buttons
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                /*mDriveServiceHelper.TaiThuMucDuLieu(matram.getText().toString(), userName)
                                                        .addOnSuccessListener(new OnSuccessListener<String>() {
                                                            @Override
                                                            public void onSuccess(String googleDriveFileHolder) {
                                                                txtKQ.setText("Đã tải lên thành công!");
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.e(TAG, "onFailure: " + e.getMessage());
                                                            }
                                                        });*/
                                                /*mDriveServiceHelper.TaiThuMucAnh(matram.getText().toString())
                                                        .addOnSuccessListener(new OnSuccessListener<String>() {
                                                            @Override
                                                            public void onSuccess(String googleDriveFileHolder) {
                                                                txtKQ.setText("Đã tải lên thành công!");
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.e(TAG, "onFailure: " + e.getMessage());
                                                            }
                                                        });*/
                                                TaiThuMucDuLieu(matram.getText().toString(), userName);
                                                TaiThuMucAnh(matram.getText().toString());
                                            }
                                        });
                                        builder.setNegativeButton("không", null);
                                        // create and show the alert dialog
                                        AlertDialog dialog2 = builder.create();
                                        dialog2.show();
                                    } else {
                                      /*  mDriveServiceHelper.TaiThuMucDuLieu(matram.getText().toString(), userName)
                                                .addOnSuccessListener(new OnSuccessListener<String>() {
                                                    @Override
                                                    public void onSuccess(String googleDriveFileHolder) {
                                                        txtKQ.setText("Đã tải lên thành công!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e(TAG, "onFailure: " + e.getMessage());
                                                    }
                                                });*/
                                       /* mDriveServiceHelper.TaiThuMucAnh(matram.getText().toString())
                                                .addOnSuccessListener(new OnSuccessListener<String>() {
                                                    @Override
                                                    public void onSuccess(String googleDriveFileHolder) {
                                                        txtKQ.setText("Đã tải lên thành công!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e(TAG, "onFailure: " + e.getMessage());
                                                    }
                                                });*/
                                        TaiThuMucDuLieu(matram.getText().toString(), userName);
                                        TaiThuMucAnh(matram.getText().toString());
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                }
                            });
                } else {Toast.makeText(Activity_GoogleDrive.this,"Bạn chưa nhập tên người dùng!",Toast.LENGTH_SHORT).show();}
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
    String title,IDfolderHM;
    private void TaiThuMucAnh(final String tenTram) {
        if (tenTram != null && !mBusy) {
            txtKQ.setText("Bắt đầu tải dữ liệu:\n");
            new AsyncTask<Void, String, Void>() {
                private String findOrCreateFolder(String prnt, String titl,String kieu, final File fl){
                    String id = null, txt;
                    if (kieu.equals("folder")){
                        List<GoogleDriveFileHolder> cvs = mClassDriveServiceHelper.search(prnt,titl);
                        if (cvs.size() > 0) {
                            title = "Đã có: ";
                            id =  cvs.get(0).getId();
                        } else {
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
                    }else if (kieu.equals("image")){
                        List<GoogleDriveFileHolder> cvs = mClassDriveServiceHelper.seachFile(prnt,titl,"image/jpeg");
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
                //Tạo tree file
                @SuppressLint("WrongThread")
                @Override
                protected Void doInBackground(Void... params) {
                    BienDem = 1;
                    tongso = DemSoFile(tenTram);
                    progressBar.setMax(tongso);
                    mBusy = true;
                    File folder = new File("/storage/emulated/0/GIAMSAT/"+tenTram);
                    File folderData = new File("/storage/emulated/0/DataViettel/"+"Data"+tenTram);
                    File folderDataBia = new File("/storage/emulated/0/DataViettel/"+"Data"+tenTram+"/TABLEBia.txt");
                    String IDfolderTRAMTong = findOrCreateFolder("root", tenTram,"folder",null);///tên folder
                    String IDfolderTRAM = findOrCreateFolder(IDfolderTRAMTong, tenTram,"folder",null);///tên folder
                    String IDfolderDataTRAM = findOrCreateFolder(IDfolderTRAMTong, "Data" + tenTram,"folder",null);
                    //title = tenTram;
                    for (File fileTRAM : folder.listFiles()) {
                        if (IDfolderTRAM != null)
                        {
                            String[] name = fileTRAM.getName().split("\n");
                            IDfolderHM = findOrCreateFolder(IDfolderTRAM,name[0],"folder",null);
                            File folderHM = new File(folder+"/"+fileTRAM.getName());
                            for (File fileHM : folderHM.listFiles()) {
                                String IDfolderCT = findOrCreateFolder(IDfolderHM, fileHM.getName(),"folder",null);
                                File folderCT = new File(folderHM+"/"+fileHM.getName());
                                for (File fileCT : folderCT.listFiles()) {
                                    File fl = new File(folderCT + "/" + fileCT.getName());///tên hình ảnh
                                    String IDImage = findOrCreateFolder(IDfolderCT, fl.getName(),"image",fl);
                                    /*List<GoogleDriveFileHolder> cvs = mDriveServiceHelper.seachFile(fileCT.getName(),"image/jpeg");
                                    if (cvs.size() == 0)
                                    {
                                       id = mDriveServiceHelper.uploadImage(fl, "image/jpeg", IDfolderCT);
                                    }*/
                                }
                            }
                        }
                    }
                    //                            if (folderDataBia.exists()) {
                    //                                String IDfolderDataTRAM = findOrCreateFolder(IDfolderTRAMTong, "Data" + tenTram);
                    //                                for (java.io.File fileDATA : folderData.listFiles()) {
                    //                                    if (IDfolderDataTRAM != null) {
                    //                                        List<GoogleDriveFileHolder> cvs = search(IDfolderDataTRAM,fileDATA.getName());
                    //                                        //Log.d("TT", String.valueOf(cvs.size()));
                    //                                        if (cvs.size() == 0) {
                    //                                            java.io.File fl = new java.io.File(folderData + "/" + fileDATA.getName());///tên hình ảnh
                    //                                            //PHẢI KIỂM TRA tRỐNG
                    //                                            if(KiemTraTrong(fl)) uploadImage(fl,UT.MIME_TXT, IDfolderDataTRAM);
                    //
                    //                                        } else if (cvs.size() != 0) {
                    //                                            try {
                    //                                                mDriveService.files().delete(cvs.get(0).getId()).execute();
                    //                                                java.io.File fl = new java.io.File(folderData + "/" + fileDATA.getName());///tên hình ảnh
                    //                                                if(KiemTraTrong(fl)) uploadImage(fl,UT.MIME_TXT, IDfolderDataTRAM);
                    //
                    //                                            } catch (IOException e) {
                    //                                                e.printStackTrace();
                    //                                            }
                    //
                    //                                        }
                    //                                    }
                    //                                }
                    //                            }
                    //                            try
                    //                            {
                    //                                //mDispTxt.setText("Đã tải lên google drive thành công\n");
                    //                            }
                    //                            catch (Exception e) {}
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
                    mBusy = false;
                }
            }.execute();
        }
    }
    private void TaiThuMucDuLieu(final String tenTram,String userName) {
        if (tenTram != null && !mBusy) {
            txtKQ.setText("Bắt đầu tải dữ liệu\n");
            new AsyncTask<Void, String, Void>() {
                private String findOrCreateFolder(String prnt, String titl,String kieu, final File fl){
                    String id = null, txt;
                    if (kieu.equals("folder")){
                        List<GoogleDriveFileHolder> cvs = mClassDriveServiceHelper.search(prnt,titl);
                        if (cvs.size() > 0) {
                            title = "Đã có: ";
                            id =  cvs.get(0).getId();
                        } else {
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
                    }else if (kieu.equals("text")){
                        List<GoogleDriveFileHolder> cvs = mClassDriveServiceHelper.seachFile(prnt,titl,"image/jpeg");
                        if (cvs.size() > 0) {
                            title = "Đã có: ";
                            id =  cvs.get(0).getId();
                        } else {
                            //id = mDriveServiceHelper.CreateFoler(prnt, titl);
                            id= mClassDriveServiceHelper.uploadImage(fl,UT.MIME_TXT, prnt);
                            title = "Đã đồng bộ: ";
                        }
                        if (id != null && !title.equals("Đã có: "))
                        {
                            String tenbang = TraNoiDung(titl);
                            if (!tenbang.equals(""))
                                title += tenbang;
                            else
                                title += titl;
                        }
                        else if (id != null && title.equals("Đã có: "))
                            title = "";
                        else
                            title = "Chưa tạo: " + titl;

                        publishProgress(title);
                    }
                    return id;
                }
                //Tạo tree filen
                @Override
                protected Void doInBackground(Void... params) {
                    mBusy = true;
                    BienDem = 1;
                    tongso = DemSoFileDuLieu(tenTram);
                    progressBar.setMax(tongso);
                    File folder = new File("/storage/emulated/0/GIAMSAT/"+tenTram);
                    File folderData = new File("/storage/emulated/0/DataViettel/"+"Data"+tenTram);
                    File folderDataBia = new File("/storage/emulated/0/DataViettel/"+"Data"+tenTram+"/TABLEBia.txt");
                    String IDfolderTRAMTong = findOrCreateFolder("root", tenTram,"folder",null);///tên folder
                    //title = tenTram;
                    if (folderDataBia.exists()) {
                        String IDfolderDataTRAM = findOrCreateFolder(IDfolderTRAMTong, "Data" + tenTram + "_user " + userName,"folder",null);
                        for (File fileDATA : folderData.listFiles()) {
                            if (IDfolderDataTRAM != null) {
                                List<GoogleDriveFileHolder> cvs = mClassDriveServiceHelper.search(IDfolderDataTRAM,fileDATA.getName());
                                //Log.d("TT", String.valueOf(cvs.size()));
                                if (cvs.size() == 0) {
                                    File fl = new File(folderData + "/" + fileDATA.getName());///tên hình ảnh
                                    mClassDriveServiceHelper.uploadImage(fl,UT.MIME_TXT, IDfolderDataTRAM);

                                } else if (cvs.size() != 0) {
                                    try {
                                        mClassDriveServiceHelper.delete(cvs.get(0).getId());
                                        File fl = new File(folderData + "/" + fileDATA.getName());///tên hình ảnh
                                        String IDImage = findOrCreateFolder(IDfolderDataTRAM, fl.getName(),"text",fl);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }
                    }
                    try
                    {
                        //mDispTxt.setText("Đã tải lên google drive thành công\n");
                    }
                    catch (Exception e) {}
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
                    mBusy = false;
                }
            }.execute();
        }
    }
    public String TraNoiDung (String dauvao) {
        String daura="";
        ArrayList<String> ArraylistKG = new ArrayList<String>();
        //AI (CI)
        ArraylistKG.add("TABLE2.txt@Bảng hiện trạng móng");
        ArraylistKG.add("TABLEBia.txt@Bảng thông tin chung");
        ArraylistKG.add("TABLE3.txt@Bảng cường độ bê tông móng");
        ArraylistKG.add("TABLE4.txt@Bảng hệ thống tiếp địa");
        ArraylistKG.add("TABLE5.txt@Bảng giá trị điện trở");
        ArraylistKG.add("TABLE6.txt@Bảng đo đạc cấu trúc cột");
        ArraylistKG.add("TABLE7_Tren.txt@Bảng phụ kiện trên cột");
        ArraylistKG.add("TABLE7_Duoi.txt@Bảng phụ kiện dưới mặt đất");
        ArraylistKG.add("TABLE8_Tren.txt@Bảng lực siết bu lôn đầu trên");
        ArraylistKG.add("TABLE8_Duoi.txt@Bảng lực siết bu lôn đầu dưới");
        ArraylistKG.add("TABLE9.txt@Bảng đo lực căng dây co");
        ArraylistKG.add("TABLE10.txt@Bảng độ siết chặt bu long");
        ArraylistKG.add("TABLE11.txt@Bảng thiết bị treo trên cột");
        ArraylistKG.add("TABLE12_V0.txt@Bảng tọa độ vòng 0");
        ArraylistKG.add("TABLE12_V1.txt@Bảng tọa độ vòng 1");
        ArraylistKG.add("TABLE12_V2.txt@Bảng tọa độ vòng 2");
        ArraylistKG.add("TABLE12_V3.txt@Bảng tọa độ vòng 3");



        for (int i=0;i< ArraylistKG.size();i++){
            if (ArraylistKG.get(i).toString().contains(dauvao)){
                daura = ArraylistKG.get(i).toString().split("@")[1];
                break;
            }

        }
        return daura;
    }
/*
    public Task<String> TaiThuMucAnh(String tenTram) {
        return Tasks.call(mExecutor, new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (!mBusy) {
                    new AsyncTask<Void, String, Void>() {
                        private String findOrCreateFolder(String prnt, String titl){
                            List<GoogleDriveFileHolder> cvs = mDriveServiceHelper.search(prnt,titl);
                            String id, txt;
                            if (cvs.size() > 0) {
                                title = "found ";
                                id =  cvs.get(0).getId();
                            } else {
                                id = mDriveServiceHelper.CreateFoler(prnt, titl);
                                title = "created ";
                            }
                            if (id != null)
                                title += titl;
                            else
                                title = "failed " + titl;
                            publishProgress(title);
                            return id;
                        }
                        //Tạo tree file
                        @Override
                        protected Void doInBackground(Void... params) {
                            mBusy = true;
                            java.io.File folder = new java.io.File("/storage/emulated/0/GIAMSAT/"+tenTram);
                            java.io.File folderData = new java.io.File("/storage/emulated/0/DataViettel/"+"Data"+tenTram);
                            java.io.File folderDataBia = new java.io.File("/storage/emulated/0/DataViettel/"+"Data"+tenTram+"/TABLEBia.txt");
                            String IDfolderTRAMTong = findOrCreateFolder("root", tenTram);///tên folder
                            String IDfolderTRAM = findOrCreateFolder(IDfolderTRAMTong, tenTram);///tên folder
                            String IDfolderDataTRAM = findOrCreateFolder(IDfolderTRAMTong, "Data" + tenTram);

                            //title = tenTram;
                            for (java.io.File fileTRAM : folder.listFiles()) {
                                if (IDfolderTRAM != null)
                                {
                                    String[] name = fileTRAM.getName().split("\n");
                                    IDfolderHM = findOrCreateFolder(IDfolderTRAM,name[0]);
                                    java.io.File folderHM = new java.io.File(folder+"/"+fileTRAM.getName());
                                    for (java.io.File fileHM : folderHM.listFiles()) {
                                        String IDfolderCT = findOrCreateFolder(IDfolderHM, fileHM.getName());
                                        java.io.File folderCT = new java.io.File(folderHM+"/"+fileHM.getName());
                                        for (java.io.File fileCT : folderCT.listFiles()) {
                                            java.io.File fl = new java.io.File(folderCT + "/" + fileCT.getName());///tên hình ảnh
                                            String id = null;
                                            List<GoogleDriveFileHolder> cvs = mDriveServiceHelper.seachFile(fileCT.getName(),"image/jpeg");
                                            if (cvs.size() == 0)
                                            {
                                                try {
                                                    id = mDriveServiceHelper.uploadImage(fl, "image/jpeg", IDfolderCT);
                                                }
                                                catch (Exception e)
                                                {
                                                    Log.d("Lỗi: ",fl.getPath());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
//                            if (folderDataBia.exists()) {
//                                String IDfolderDataTRAM = findOrCreateFolder(IDfolderTRAMTong, "Data" + tenTram);
//                                for (java.io.File fileDATA : folderData.listFiles()) {
//                                    if (IDfolderDataTRAM != null) {
//                                        List<GoogleDriveFileHolder> cvs = search(IDfolderDataTRAM,fileDATA.getName());
//                                        //Log.d("TT", String.valueOf(cvs.size()));
//                                        if (cvs.size() == 0) {
//                                            java.io.File fl = new java.io.File(folderData + "/" + fileDATA.getName());///tên hình ảnh
//                                            //PHẢI KIỂM TRA tRỐNG
//                                            if(KiemTraTrong(fl)) uploadImage(fl,UT.MIME_TXT, IDfolderDataTRAM);
//
//                                        } else if (cvs.size() != 0) {
//                                            try {
//                                                mDriveService.files().delete(cvs.get(0).getId()).execute();
//                                                java.io.File fl = new java.io.File(folderData + "/" + fileDATA.getName());///tên hình ảnh
//                                                if(KiemTraTrong(fl)) uploadImage(fl,UT.MIME_TXT, IDfolderDataTRAM);
//
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            }
//
//                                        }
//                                    }
//                                }
//                            }
//                            try
//                            {
//                                //mDispTxt.setText("Đã tải lên google drive thành công\n");
//                            }
//                            catch (Exception e) {}
                            return null;
                        }

                        @Override
                        protected void onProgressUpdate(String... strings) { super.onProgressUpdate(strings);
                            txtKQ.append("\n" + strings[0]);
                        }
                        @Override
                        protected void onPostExecute(Void nada) { super.onPostExecute(nada);
                            txtKQ.append("\n\nDONE");
                            mBusy = false;
                        }
                    }.execute();
                }
                return title;
            }
        });
    }
*/
    public Integer DemSoFile(String tenTram){
        int SoFile = 0;
        java.io.File folder = new java.io.File("/storage/emulated/0/GIAMSAT/"+tenTram);
        java.io.File folderData = new java.io.File("/storage/emulated/0/DataViettel/"+"Data"+tenTram);
        java.io.File folderDataBia = new java.io.File("/storage/emulated/0/DataViettel/"+"Data"+tenTram+"/TABLEBia.txt");
        for (java.io.File fileTRAM : folder.listFiles()) {
                SoFile++;
                java.io.File folderHM = new java.io.File(folder+"/"+fileTRAM.getName());
                for (java.io.File fileHM : folderHM.listFiles()) {
                    SoFile++;
                    java.io.File folderCT = new java.io.File(folderHM+"/"+fileHM.getName());
                    for (java.io.File fileCT : folderCT.listFiles()) {
                        SoFile++;
                    }
                }
        }
        return SoFile+4;
    }
    public Integer DemSoFileDuLieu(String tenTram){
        int SoFile = 0;
        java.io.File folder = new java.io.File("/storage/emulated/0/GIAMSAT/"+tenTram);
        java.io.File folderData = new java.io.File("/storage/emulated/0/DataViettel/"+"Data"+tenTram);
        java.io.File folderDataBia = new java.io.File("/storage/emulated/0/DataViettel/"+"Data"+tenTram+"/TABLEBia.txt");
        //title = tenTram;
        if (folderDataBia.exists()) {
            for (java.io.File fileDATA : folderData.listFiles()) {
                SoFile++;
            }
        }
        return SoFile+3;
    }
}