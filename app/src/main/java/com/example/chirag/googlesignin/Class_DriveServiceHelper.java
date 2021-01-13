package com.example.chirag.googlesignin;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Nullable;

/**
 * A utility for performing read/write operations on Drive files via the REST API and opening a
 * file picker UI via Storage Access Framework.
 */
public class Class_DriveServiceHelper {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Drive mDriveService;
    private static boolean mBusy;
    String title,IDfolderHM;
    public Class_DriveServiceHelper(Drive driveService) {

        mDriveService = driveService;
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
    /**
     * Creates a text file in the user's My Drive folder and returns its file ID.
     */
    //region Function không dùng
    public Task<String> createFile(final String fileName) {
        return Tasks.call(mExecutor, new Callable<String>() {
            @Override
            public String call() throws Exception {
                File metadata = new File()
                        .setParents(Collections.singletonList("root"))
                        .setMimeType("text/plain")
                        .setName(fileName);

                File googleFile = mDriveService.files().create(metadata).execute();
                if (googleFile == null) {
                    throw new IOException("Null result when requesting file creation.");
                }

                return googleFile.getId();
            }
        });
    }
    public Task<String> createFile(final String fileName, @Nullable final String folderId) {
        return Tasks.call(mExecutor, new Callable<String>() {
            @Override
            public String call() throws Exception {
                List<String> root;
                if (folderId == null) {
                    root = Collections.singletonList("root");
                } else {

                    root = Collections.singletonList(folderId);
                }

                File metadata = new File()
                        .setParents(root)
                        .setMimeType("text/plain")
                        .setName(fileName);

                File googleFile = mDriveService.files().create(metadata).execute();
                if (googleFile == null) {
                    throw new IOException("Null result when requesting file creation.");
                }

                return googleFile.getId();
            }
        });
    }
    public Task<GoogleDriveFileHolder> createTextFileIfNotExist(final String fileName, final String content, @Nullable final String folderId) {
        return Tasks.call(mExecutor, new Callable<GoogleDriveFileHolder>() {
            @Override
            public GoogleDriveFileHolder call() throws Exception {
                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();

                FileList result = mDriveService.files().list()
                        .setQ("mimeType = 'text/plain' and name = '" + fileName + "' ")
                        .setSpaces("drive")
                        .execute();

                if (result.getFiles().size() > 0) {
                    googleDriveFileHolder.setId(result.getFiles().get(0).getId());
                    return googleDriveFileHolder;
                } else {

                    List<String> root;
                    if (folderId == null) {
                        root = Collections.singletonList("root");
                    } else {

                        root = Collections.singletonList(folderId);
                    }

                    File metadata = new File()
                            .setParents(root)
                            .setMimeType("text/plain")
                            .setName(fileName);
                    ByteArrayContent contentStream = ByteArrayContent.fromString("text/plain", content);

                    File googleFile = mDriveService.files().create(metadata, contentStream).execute();
                    if (googleFile == null) {
                        throw new IOException("Null result when requesting file creation.");
                    }

                    googleDriveFileHolder.setId(googleFile.getId());

                    return googleDriveFileHolder;
                }
            }
        });
    }
    public Task<Pair<String, String>> readFile(final String fileId) {
        return Tasks.call(mExecutor, new Callable<Pair<String, String>>() {
            @Override
            public Pair<String, String> call() throws Exception {
                // Retrieve the metadata as a File object.
                File metadata = mDriveService.files().get(fileId).execute();
                String name = metadata.getName();

                // Stream the file contents to a String.
                try (InputStream is = mDriveService.files().get(fileId).executeMediaAsInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    String contents = stringBuilder.toString();

                    return Pair.create(name, contents);
                }
            }
        });
    }
    public Task<Void> deleteFolderFile(final String fileId) {
        return Tasks.call(mExecutor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                // Retrieve the metadata as a File object.
                if (fileId != null) {
                    mDriveService.files().delete(fileId).execute();
                }

                return null;
            }
        });
    }
    public Task<GoogleDriveFileHolder> uploadFile(final File googleDiveFile, final AbstractInputStreamContent content) {
        return Tasks.call(mExecutor, new Callable<GoogleDriveFileHolder>() {
            @Override
            public GoogleDriveFileHolder call() throws Exception {
                // Retrieve the metadata as a File object.
                File fileMeta = mDriveService.files().create(googleDiveFile, content).execute();
                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                googleDriveFileHolder.setId(fileMeta.getId());
                googleDriveFileHolder.setName(fileMeta.getName());
                return googleDriveFileHolder;
            }
        });
    }
    public int countChar(String str, char c) {
        int count = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }

        return count;
    }
    public Task<GoogleDriveFileHolder> createFolderIfNotExist(final String folderName, @Nullable final String parentFolderId) {
        return Tasks.call(mExecutor, new Callable<GoogleDriveFileHolder>() {
            @Override
            public GoogleDriveFileHolder call() throws Exception {
                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                FileList result = mDriveService.files().list()
                        .setQ("mimeType = '" + "application/vnd.google-apps.folder"  + "' and name = '" + folderName + "' ")
                        .setSpaces("drive")
                        .execute();

                if (result.getFiles().size() > 0) {
                    googleDriveFileHolder.setId(result.getFiles().get(0).getId());
                    googleDriveFileHolder.setName(result.getFiles().get(0).getName());
//                googleDriveFileHolder.setModifiedTime(result.getFiles().get(0).getCreatedTime().getValue());
//                googleDriveFileHolder.setSize(result.getFiles().get(0).getSize());

                    googleDriveFileHolder.setId(result.getFiles().get(0).getId());
                    return googleDriveFileHolder;

                } else {

                    Log.d(TAG, "createFolderIfNotExist: not found");
                    List<String> root;
                    if (parentFolderId == null) {
                        root = Collections.singletonList("root");
                    } else {

                        root = Collections.singletonList(parentFolderId);
                    }
                    File metadata = new File()
                            .setParents(root)
                            .setMimeType("application/vnd.google-apps.folder" )
                            .setName(folderName);

                    File googleFile = mDriveService.files().create(metadata).execute();
                    if (googleFile == null) {
                        throw new IOException("Null result when requesting file creation.");
                    }
                    googleDriveFileHolder.setId(googleFile.getId());
                    return googleDriveFileHolder;
                }
            }
        });
    }
    public Task<InputStream> downloadFile(final String fileId) {
        return Tasks.call(mExecutor, new Callable<InputStream>() {
            @Override
            public InputStream call() throws Exception {
                // Retrieve the metadata as a File object.
                return mDriveService.files().get(fileId).executeMediaAsInputStream();
            }
        });
    }
    public Task<Void> exportFile(final java.io.File fileSaveLocation, final String fileId, final String mimeType) {
        return Tasks.call(mExecutor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                // Retrieve the metadata as a File object.
                OutputStream outputStream = new FileOutputStream(fileSaveLocation);
                mDriveService.files().export(fileId, mimeType).executeMediaAndDownloadTo(outputStream);
                return null;
            }
        });
    }
    private List<GoogleDriveFileHolder> seachFold(String prnId,String folderName) {
        List<GoogleDriveFileHolder> googleDriveFileHolderList = new ArrayList<>();
        // Retrive the metadata as a File object.
        FileList result = null;
        try {
            if (prnId != null) {
                result = mDriveService.files().list()
                        .setQ("mimeType = '" + "application/vnd.google-apps.folder"  + "' and name = '" + folderName + "' ")
                        .setFields("files(name)")
                        .setSpaces("appDataFolder")
                        .execute();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < result.getFiles().size(); i++) {

            GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
            googleDriveFileHolder.setId(result.getFiles().get(i).getId());
            googleDriveFileHolder.setName(result.getFiles().get(i).getName());
            googleDriveFileHolderList.add(googleDriveFileHolder);
        }

        return googleDriveFileHolderList;
    }
    /**
     * Updates the file identified by {@code fileId} with the given {@code name} and {@code
     * content}.
     */
    public Task<Void> saveFile(final String fileId, final String name, final String content) {
        return Tasks.call(mExecutor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                // Create a File containing any metadata changes.
                File metadata = new File().setName(name);

                // Convert content to an AbstractInputStreamContent instance.
                ByteArrayContent contentStream = ByteArrayContent.fromString("text/plain", content);

                // Update the metadata and contents.
                mDriveService.files().update(fileId, metadata, contentStream).execute();
                return null;
            }
        });
    }

    /**
     * Returns a {@link FileList} containing all the visible files in the user's My Drive.
     *
     * <p>The returned list will only contain files visible to this app, i.e. those which were
     * created by this app. To perform operations on files not created by the app, the project must
     * request Drive Full Scope in the <a href="https://play.google.com/apps/publish">Google
     * Developer's Console</a> and be submitted to Google for verification.</p>
     */
    public Task<List<GoogleDriveFileHolder>> queryFiles() {
        return Tasks.call(mExecutor, new Callable<List<GoogleDriveFileHolder>>() {
                    @Override
                    public List<GoogleDriveFileHolder> call() throws Exception {
                        List<GoogleDriveFileHolder> googleDriveFileHolderList = new ArrayList<>();


                        FileList result = mDriveService.files().list().setFields("files(id, name,size,createdTime,modifiedTime,starred,mimeType)").setSpaces("drive").execute();

                        for (int i = 0; i < result.getFiles().size(); i++) {

                            GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                            googleDriveFileHolder.setId(result.getFiles().get(i).getId());
                            googleDriveFileHolder.setName(result.getFiles().get(i).getName());
                            if (result.getFiles().get(i).getSize() != null) {
                                googleDriveFileHolder.setSize(result.getFiles().get(i).getSize());
                            }

                            if (result.getFiles().get(i).getModifiedTime() != null) {
                                googleDriveFileHolder.setModifiedTime(result.getFiles().get(i).getModifiedTime());
                            }

                            if (result.getFiles().get(i).getCreatedTime() != null) {
                                googleDriveFileHolder.setCreatedTime(result.getFiles().get(i).getCreatedTime());
                            }

                            if (result.getFiles().get(i).getStarred() != null) {
                                googleDriveFileHolder.setStarred(result.getFiles().get(i).getStarred());
                            }

                            if (result.getFiles().get(i).getMimeType() != null) {
                                googleDriveFileHolder.setMimeType(result.getFiles().get(i).getMimeType());
                            }
                            googleDriveFileHolderList.add(googleDriveFileHolder);

                        }


                        return googleDriveFileHolderList;


                    }
                }
        );
    }
    public Task<List<GoogleDriveFileHolder>> queryFiles(@Nullable final String folderId) {
        return Tasks.call(mExecutor, new Callable<List<GoogleDriveFileHolder>>() {
                    @Override
                    public List<GoogleDriveFileHolder> call() throws Exception {
                        List<GoogleDriveFileHolder> googleDriveFileHolderList = new ArrayList<>();
                        String parent = "root";
                        if (folderId != null) {
                            parent = folderId;
                        }

                        FileList result = mDriveService.files().list().setQ("'" + parent + "' in parents").setFields("files(id, name,size,createdTime,modifiedTime,starred,mimeType)").setSpaces("drive").execute();

                        for (int i = 0; i < result.getFiles().size(); i++) {

                            GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                            googleDriveFileHolder.setId(result.getFiles().get(i).getId());
                            googleDriveFileHolder.setName(result.getFiles().get(i).getName());
                            if (result.getFiles().get(i).getSize() != null) {
                                googleDriveFileHolder.setSize(result.getFiles().get(i).getSize());
                            }

                            if (result.getFiles().get(i).getModifiedTime() != null) {
                                googleDriveFileHolder.setModifiedTime(result.getFiles().get(i).getModifiedTime());
                            }

                            if (result.getFiles().get(i).getCreatedTime() != null) {
                                googleDriveFileHolder.setCreatedTime(result.getFiles().get(i).getCreatedTime());
                            }

                            if (result.getFiles().get(i).getStarred() != null) {
                                googleDriveFileHolder.setStarred(result.getFiles().get(i).getStarred());
                            }
                            if (result.getFiles().get(i).getMimeType() != null) {
                                googleDriveFileHolder.setMimeType(result.getFiles().get(i).getMimeType());
                            }

                            googleDriveFileHolderList.add(googleDriveFileHolder);

                        }


                        return googleDriveFileHolderList;


                    }
                }
        );
    }
    /**
     * Returns an {@link Intent} for opening the Storage Access Framework file picker.
     */
    public Intent createFilePickerIntent() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        return intent;
    }
    /**
     * Opens the file at the {@code uri} returned by a Storage Access Framework {@link Intent}
     * created by {@link #createFilePickerIntent()} using the given {@code contentResolver}.
     */
    public Task<Pair<String, String>> openFileUsingStorageAccessFramework(
            final ContentResolver contentResolver, final Uri uri) {
        return Tasks.call(mExecutor, new Callable<Pair<String, String>>() {
            @Override
            public Pair<String, String> call() throws Exception {
                // Retrieve the document's display name from its metadata.
                String name;
                try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        name = cursor.getString(nameIndex);
                    } else {
                        throw new IOException("Empty cursor returned for file.");
                    }
                }

                // Read the document's contents as a String.
                String content;
                try (InputStream is = contentResolver.openInputStream(uri);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    content = stringBuilder.toString();
                }

                return Pair.create(name, content);
            }
        });
    }
    //endregion
    public Task<GoogleDriveFileHolder> createTextFile(final String fileName, final String content, @Nullable final String folderId) {
        return Tasks.call(mExecutor, new Callable<GoogleDriveFileHolder>() {
            @Override
            public GoogleDriveFileHolder call() throws Exception {

                List<String> root;
                if (folderId == null) {
                    root = Collections.singletonList("root");
                } else {

                    root = Collections.singletonList(folderId);
                }

                File metadata = new File()
                        .setParents(root)
                        .setMimeType("text/plain")
                        .setName(fileName);
                ByteArrayContent contentStream = ByteArrayContent.fromString("text/plain", content);

                File googleFile = mDriveService.files().create(metadata, contentStream).execute();
                if (googleFile == null) {
                    throw new IOException("Null result when requesting file creation.");
                }
                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                googleDriveFileHolder.setId(googleFile.getId());
                return googleDriveFileHolder;
            }
        });
    }
    public String CreateFoler(String folderId, String folderName) {
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
    public String uploadImage(final java.io.File localFile, final String mimeType, @Nullable final String folderId) {

        List<String> root;
        if (folderId == null) {
            root = Collections.singletonList("root");
        } else {

            root = Collections.singletonList(folderId);
        }

        File metadata = new File()
                .setParents(root)
                .setMimeType(mimeType)
                .setName(localFile.getName());

        FileContent fileContent = new FileContent(mimeType, localFile);

        File fileMeta = null;
        try {
            fileMeta = mDriveService.files().create(metadata, fileContent).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
        googleDriveFileHolder.setId(fileMeta.getId());
        googleDriveFileHolder.setName(fileMeta.getName());
        return googleDriveFileHolder.getId();

    }
    public List<GoogleDriveFileHolder> search( String parentId ,String fileName){
        File file = null;
        FileList result = null;
        StringBuffer fileQuery = new StringBuffer();
        fileQuery.append( "name = '" + fileName + "'" );
        if ( parentId != null ) {
            fileQuery.append( " and '" + parentId + "' in parents and trashed=false" );
        }
        List<GoogleDriveFileHolder> googleDriveFileHolderList = new ArrayList<>();

        try {
            result = mDriveService.files().list()
                    .setQ( fileQuery.toString() ).execute();
            for (int i = 0; i < result.getFiles().size(); i++) {

                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                googleDriveFileHolder.setId(result.getFiles().get(i).getId());
                googleDriveFileHolder.setName(result.getFiles().get(i).getName());
                googleDriveFileHolderList.add(googleDriveFileHolder);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDriveFileHolderList;
    }
    public List<GoogleDriveFileHolder> seachFile(String parentId ,String fileName,String mimeType) {
        List<GoogleDriveFileHolder> googleDriveFileHolderList = new ArrayList<>();
        // Retrive the metadata as a File object.
        FileList result = null;
        StringBuffer fileQuery = new StringBuffer();
        fileQuery.append("name = '" + fileName + "' and mimeType ='" + mimeType + "' and trashed =" + false);
        if ( parentId != null ) {
            fileQuery.append( " and '" + parentId + "' in parents and trashed=false" );
        }
        try {
            result = mDriveService.files().list()
                    .setQ(fileQuery.toString())//nam='filename' and mineType='minetype'
                    .setSpaces("drive")
                    .setFields("files(id, name,size,createdTime,modifiedTime,starred)")
                    .execute();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < result.getFiles().size(); i++)
        {
            GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
            googleDriveFileHolder.setId(result.getFiles().get(i).getId());
            googleDriveFileHolder.setName(result.getFiles().get(i).getName());
            googleDriveFileHolder.setModifiedTime(result.getFiles().get(i).getModifiedTime());
            googleDriveFileHolder.setSize(result.getFiles().get(i).getSize());
            googleDriveFileHolderList.add(googleDriveFileHolder);
        }

        return googleDriveFileHolderList;
    }
    /*public Task<String> TaiThuMucAnh(String tenTram) {
        return Tasks.call(mExecutor, new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (!mBusy) {
                    new AsyncTask<Void, String, Void>() {
                        private String findOrCreateFolder(String prnt, String titl){
                            List<GoogleDriveFileHolder> cvs = search(prnt,titl);
                            String id, txt;
                            if (cvs.size() > 0) {
                                title = "found ";
                                id =  cvs.get(0).getId();
                            } else {
                                id = CreateFoler(prnt, titl);
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
                                            List<GoogleDriveFileHolder> cvs = seachFile(fileCT.getName(),"image/jpeg");
                                            if (cvs.size() == 0)
                                            {
                                                try {
                                                    id = uploadImage(fl, "image/jpeg", IDfolderCT);
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
                            //mDispTxt.append("\n" + strings[0]);
                        }
                        @Override
                        protected void onPostExecute(Void nada) { super.onPostExecute(nada);
                            //mDispTxt.append("\n\nDONE");
                            mBusy = false;
                        }
                    }.execute();
                }
                return title;
            }
        });
    }*/
    public Task<String> TaiThuMucDuLieu(String tenTram,String userName) {
        return Tasks.call(mExecutor, new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (!mBusy) {
                    //mDispTxt.append("Đang tải hình ảnh lên\n");
                    new AsyncTask<Void, String, Void>() {
                        private String findOrCreateFolder(String prnt, String titl){

                            List<GoogleDriveFileHolder> cvs = search(prnt,titl);
                            String id, txt;
                            if (cvs.size() > 0) {
                                title = "found ";
                                id =  cvs.get(0).getId();
                            } else {
                                id = CreateFoler(prnt, titl);
                                title = "created ";
                            }
                            if (id != null)
                                title += titl;
                            else
                                title = "failed " + titl;
                            publishProgress(title);
                            return id;
                        }
                        //Tạo tree filen
                        @Override
                        protected Void doInBackground(Void... params) {
                            mBusy = true;
                            java.io.File folder = new java.io.File("/storage/emulated/0/GIAMSAT/"+tenTram);
                            java.io.File folderData = new java.io.File("/storage/emulated/0/DataViettel/"+"Data"+tenTram);
                            java.io.File folderDataBia = new java.io.File("/storage/emulated/0/DataViettel/"+"Data"+tenTram+"/TABLEBia.txt");
                            String IDfolderTRAMTong = findOrCreateFolder("root", tenTram);///tên folder
                            //title = tenTram;
                            if (folderDataBia.exists()) {
                                String IDfolderDataTRAM = findOrCreateFolder(IDfolderTRAMTong, "Data" + tenTram + "_user " + userName);
                                for (java.io.File fileDATA : folderData.listFiles()) {
                                    if (IDfolderDataTRAM != null) {
                                        List<GoogleDriveFileHolder> cvs = search(IDfolderDataTRAM,fileDATA.getName());
                                        //Log.d("TT", String.valueOf(cvs.size()));
                                        if (cvs.size() == 0) {
                                            java.io.File fl = new java.io.File(folderData + "/" + fileDATA.getName());///tên hình ảnh
                                            //PHẢI KIỂM TRA tRỐNG
                                            uploadImage(fl,UT.MIME_TXT, IDfolderDataTRAM);

                                        } else if (cvs.size() != 0) {
                                            try {
                                                mDriveService.files().delete(cvs.get(0).getId()).execute();
                                                java.io.File fl = new java.io.File(folderData + "/" + fileDATA.getName());///tên hình ảnh
                                                uploadImage(fl,UT.MIME_TXT, IDfolderDataTRAM);

                                            } catch (IOException e) {
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
                            //mDispTxt.append("\n" + strings[0]);
                        }
                        @Override
                        protected void onPostExecute(Void nada) { super.onPostExecute(nada);
                            //mDispTxt.append("\n\nDONE");
                            mBusy = false;
                        }
                    }.execute();
                }
                return title;
            }
        });
    }
    public Boolean KiemTraTrong(java.io.File file) {
        Boolean kt = true;
        String data_tabel = StringforPath(file);
        if (data_tabel.contains("@Móng M0___@Móng M1___")) kt = false;
        if (data_tabel.contains("@Móng M0__________________@Móng M1__________________")) kt = false;
        if (data_tabel.contains("@_10__@_10__@_10__")) kt = false;
        if (data_tabel.contains("@Đốt 1_____@Đốt 2_____")) kt = false;
        if (data_tabel.contains("@1_T1______@2_T2______")) kt = false;
        if (data_tabel.contains("@T1_600_700__X__X__X__X")) kt = false;
        if (data_tabel.contains("@Đốt 1-2____@Đốt 2-3____")) kt = false;
        if (data_tabel.contains("@Vòng 0-1___@Vòng 0-2___@Vòng 0-3___")) kt = false;
        if (data_tabel.contains("@Móng M1___@Móng M2___")) kt = false;
        if (data_tabel.contains("@Tăng đơ___@Ma ní đầu dưới___@Ma ní đầu trên___@Khóa cáp đầu dưới___@Khóa cáp đầu trên___@Vòng ốp móc dây co và bu lông vòng ốp dây co___")) kt = false;
        if (data_tabel.contains("@Thoát sét cho kim thu sét___@Thoát sét cho thiết bị treo trên cột___@Thoát sét cho chân cột___@Thoát sét cho cáp thép dây co___")) kt = false;
        return  kt;
    }
    public void delete(String id){
        try {
            mDriveService.files().delete(id).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String StringforPath(java.io.File file){
        String s=null;
        BufferedReader input = null;
        try {
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
    public Task<GoogleDriveFileHolder> createFolder(final String folderName, @Nullable final String folderId) {
        return Tasks.call(mExecutor, new Callable<GoogleDriveFileHolder>() {
            @Override
            public GoogleDriveFileHolder call() throws Exception {
                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();

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

                File googleFile = mDriveService.files().create(metadata).execute();
                if (googleFile == null) {
                    throw new IOException("Null result when requesting file creation.");
                }
                googleDriveFileHolder.setId(googleFile.getId());
                return googleDriveFileHolder;
            }
        });
    }
    /**
     * Opens the file identified by {@code fileId} and returns a {@link Pair} of its name and
     * contents.
     */
    public Task<GoogleDriveFileHolder> uploadFile(final java.io.File localFile, final String mimeType, @Nullable final String folderId) {
        return Tasks.call(mExecutor, new Callable<GoogleDriveFileHolder>() {
            @Override
            public GoogleDriveFileHolder call() throws Exception {
                // Retrieve the metadata as a File object.

                List<String> root;
                if (folderId == null) {
                    root = Collections.singletonList("root");
                } else {

                    root = Collections.singletonList(folderId);
                }

                File metadata = new File()
                        .setParents(root)
                        .setMimeType(mimeType)
                        .setName(localFile.getName());

                FileContent fileContent = new FileContent(mimeType, localFile);

                File fileMeta = mDriveService.files().create(metadata, fileContent).execute();
                GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                googleDriveFileHolder.setId(fileMeta.getId());
                googleDriveFileHolder.setName(fileMeta.getName());
                return googleDriveFileHolder;
            }
        });
    }
    public Task<List<GoogleDriveFileHolder>> searchFolder(final String folderName) {
        return Tasks.call(mExecutor, new Callable<List<GoogleDriveFileHolder>>() {
            @Override
            public List<GoogleDriveFileHolder> call() throws Exception {
                List<GoogleDriveFileHolder> googleDriveFileHolderList = new ArrayList<>();
                // Retrive the metadata as a File object.
                FileList result = mDriveService.files().list()
                        .setQ("mimeType = '" + "application/vnd.google-apps.folder"  + "' and name = '" + folderName + "' ")
                        .setSpaces("drive")
                        .execute();

                for (int i = 0; i < result.getFiles().size(); i++) {

                    GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                    googleDriveFileHolder.setId(result.getFiles().get(i).getId());
                    googleDriveFileHolder.setName(result.getFiles().get(i).getName());

                    googleDriveFileHolderList.add(googleDriveFileHolder);
                }

                return googleDriveFileHolderList;
            }
        });
    }
    private static final String TAG = "DriveServiceHelper";
    public Task<List<GoogleDriveFileHolder>> searchFile(final String fileName, final String mimeType) {
        return Tasks.call(mExecutor, new Callable<List<GoogleDriveFileHolder>>() {
            @Override
            public List<GoogleDriveFileHolder> call() throws Exception {
                List<GoogleDriveFileHolder> googleDriveFileHolderList = new ArrayList<>();
                // Retrive the metadata as a File object.
                FileList result = mDriveService.files().list()
                        .setQ("name = '" + fileName + "' and mimeType ='" + mimeType + "'")
                        .setSpaces("drive")
                        .setFields("files(id, name,size,createdTime,modifiedTime,starred)")
                        .execute();


                for (int i = 0; i < result.getFiles().size(); i++) {
                    GoogleDriveFileHolder googleDriveFileHolder = new GoogleDriveFileHolder();
                    googleDriveFileHolder.setId(result.getFiles().get(i).getId());
                    googleDriveFileHolder.setName(result.getFiles().get(i).getName());
                    googleDriveFileHolder.setModifiedTime(result.getFiles().get(i).getModifiedTime());
                    googleDriveFileHolder.setSize(result.getFiles().get(i).getSize());
                    googleDriveFileHolderList.add(googleDriveFileHolder);

                }

                return googleDriveFileHolderList;
            }
        });
    }
    public Task<Void> downloadFile(final java.io.File fileSaveLocation, final String fileId) {
        return Tasks.call(mExecutor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                // Retrieve the metadata as a File object.
                OutputStream outputStream = new FileOutputStream(fileSaveLocation);
                mDriveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
                return null;
            }
        });
    }




}