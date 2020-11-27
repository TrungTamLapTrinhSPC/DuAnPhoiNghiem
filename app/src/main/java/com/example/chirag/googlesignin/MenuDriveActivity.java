package com.example.chirag.googlesignin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

public class MenuDriveActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    private DriveServiceHelper mDriveServiceHelper;
    Drive mDriveService;
    private static final String TAG = "MainActivity";
    private Button login;
    private LinearLayout gDriveAction;
    private Button searchFile;
    private Button searchFolder;
    private Button createTextFile;
    private Button createFolder;
    private Button uploadFile;
    private Button downloadFile;
    private Button deleteFileFolder;
    private TextView email;
    private Button viewFileFolder;
    private String idt;
    GoogleDriveFileHolder googleDriveFileHolder2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_drive);
        initView();


        searchFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDriveServiceHelper == null) {
                    return;
                }
                mDriveServiceHelper.searchFile("textfilename.txt", "text/plain")
                        .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
                            @Override
                            public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
                                Gson gson = new Gson();
                                Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolders));
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

        searchFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDriveServiceHelper == null) {
                    return;
                }

                mDriveServiceHelper.searchFolder("CreateFolder")
                        .addOnSuccessListener(new OnSuccessListener<List<GoogleDriveFileHolder>>() {
                            @Override
                            public void onSuccess(List<GoogleDriveFileHolder> googleDriveFileHolders) {
                                Gson gson = new Gson();
                                Toast.makeText(MenuDriveActivity.this,"đã tìm thấy thư mục!",Toast.LENGTH_SHORT).show();

                                Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolders));
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

        createTextFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDriveServiceHelper == null) {
                    return;
                }
                // you can provide  folder id in case you want to save this file inside some folder.
                // if folder id is null, it will save file to the root
                mDriveServiceHelper.createTextFile("textfilename.txt", "some text", null)
                        .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                            @Override
                            public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
                                Gson gson = new Gson();
                                Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolder));
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

        createFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDriveServiceHelper == null) {
                    return;
                }
                // you can provide  folder id in case you want to save this file inside some folder.
                // if folder id is null, it will save file to the root
                mDriveServiceHelper.createFolder("CreateFolder", null)
                        .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                            @Override
                            public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
                                Gson gson = new Gson();
                                Toast.makeText(MenuDriveActivity.this,"Tạo thư mục thành công!" ,Toast.LENGTH_SHORT).show();
                                String jsonString = gson.toJson(googleDriveFileHolder);
                                JSONObject jsonResult = null;
                                try {

                                        Log.e(TAG, "onSuccess: " + googleDriveFileHolder.getId());

                                        mDriveServiceHelper.uploadFile(new java.io.File( Environment.getExternalStorageDirectory().getPath(), "1234.txt"), "text/plain", googleDriveFileHolder.getId())
                                                .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                                                    @Override
                                                    public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
                                                        Gson gson = new Gson();
                                                        Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolder));
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d(TAG, "onFailure: " + e.getMessage());
                                                    }
                                                });

                                } catch (Exception ex) {
                                    ex.printStackTrace();
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

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDriveServiceHelper == null) {
                    return;
                }
                mDriveServiceHelper.uploadFile(new java.io.File( Environment.getExternalStorageDirectory().getPath(), "pic.jpg"), "image/jpeg", null)
                        .addOnSuccessListener(new OnSuccessListener<GoogleDriveFileHolder>() {
                            @Override
                            public void onSuccess(GoogleDriveFileHolder googleDriveFileHolder) {
                                Gson gson = new Gson();
                                Log.d(TAG, "onSuccess: " + gson.toJson(googleDriveFileHolder));
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

        downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDriveServiceHelper == null) {
                    return;
                }
                mDriveServiceHelper.downloadFile(new java.io.File( Environment.getExternalStorageDirectory().getPath(), "filename.txt"), "google_drive_file_id_here")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

        /*viewFileFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDriveServiceHelper == null) {
                    return;
                }
                mDriveServiceHelper.createTree("HCM1341_Hồ Chí Minh")
                        .addOnSuccessListener(new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(String googleDriveFileHolder)
                            {
                                Log.e(TAG, "onSuccess: " + googleDriveFileHolder);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: " + e.getMessage());
                            }
                        });
            }
        });*/


    }
    private String CreateFoler(String id, String til)
    {
        Log.e("ID", "onFailure: " + create(id, til));
        return idt;
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (account == null) {

            signIn();

        } else {
            email.setText(account.getEmail());
            mDriveService = getGoogleDriveService(getApplicationContext(), account, "appName");
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
                        email.setText(googleSignInAccount.getEmail());
                        mDriveService = getGoogleDriveService(getApplicationContext(), googleSignInAccount, "appName");

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
    private void initView() {
        email = findViewById(R.id.email);
        gDriveAction = findViewById(R.id.g_drive_action);
        searchFile = findViewById(R.id.search_file);
        searchFolder = findViewById(R.id.search_folder);
        createTextFile = findViewById(R.id.create_text_file);
        createFolder = findViewById(R.id.create_folder);
        uploadFile = findViewById(R.id.upload_file);
        downloadFile = findViewById(R.id.download_file);
        deleteFileFolder = findViewById(R.id.delete_file_folder);
        viewFileFolder = findViewById(R.id.view_file_folder);
    }
    public static Drive getGoogleDriveService(Context context, GoogleSignInAccount account, String appName) {
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        context, Collections.singleton(DriveScopes.DRIVE_FILE));
        credential.setSelectedAccount(account.getAccount());
        Drive googleDriveService =
                new Drive.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new GsonFactory(),
                        credential)
                        .setApplicationName(appName)
                        .build();
        return googleDriveService;
    }
    public  String create (final String folderName, @Nullable final String folderId)
    {

        List<String> root;
        if (folderId == null) {
            root = Collections.singletonList("root");
        } else {

            root = Collections.singletonList(folderId);
        }
        File metadata = new File()
                .setParents(root)
                .setMimeType("application/vnd.google-apps.folder" )
                .setName(folderName);

        File googleFile = null;
        try {
            googleFile = mDriveService.files().create(metadata).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleFile.getId();
    }
}
