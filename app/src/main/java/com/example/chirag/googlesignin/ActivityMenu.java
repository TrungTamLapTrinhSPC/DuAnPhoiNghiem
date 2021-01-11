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
import android.widget.ImageButton;
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
import static com.example.chirag.googlesignin.DriveServiceHelper.getGoogleDriveService;

public class ActivityMenu extends AppCompatActivity {
    LinearLayout btn_TongQuanBaoCao;
    ImageButton btn_BackMenu;
    private static final int REQUEST_CODE_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private DriveServiceHelper mDriveServiceHelper;
    Drive mDriveService;
    private static final String TAG = "MainActivity";
    LinearLayout sign_out;
    TextView nameTV;
    TextView emailTV,phantram;
    TextView matram,txtKQ;
    ImageView photoIV;
    GoogleSignInAccount acct;
    ProgressBar progressBar;
    int BienDem=1,tongso =0;
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
                startActivity(intent);
            }
        });
        btn_BackMenu .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void NhanBien() {

    }

    private void AnhXa() {
        btn_TongQuanBaoCao = findViewById(R.id.btn_TongQuanBaoCao);
        btn_BackMenu = findViewById(R.id.btnBackMenu);
        nameTV = findViewById(R.id.name);
        emailTV = findViewById(R.id.email);
        matram = findViewById(R.id.matram);
        txtKQ = findViewById(R.id.txtKQ);
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
            mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getApplicationContext(), account, "appName"));
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
                        mDriveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getApplicationContext(), googleSignInAccount, "appName"));

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
                    if (mDriveServiceHelper == null) {
                        return;
                    }
                    mDriveServiceHelper.searchFolder(matram.getText().toString())
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
    String title,IDfolderHM;

}