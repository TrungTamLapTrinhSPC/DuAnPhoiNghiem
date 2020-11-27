package com.example.chirag.googlesignin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.format.Time;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2BasicFragment extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener {
    private ImageButton btnLuuAnh,btnBack,btnNext,btnSetting,btnTheThoiGian;
    private LinearLayout layoutSetting;
    ImageView imageView;
    String DeXuatBeTong,DeXuatThep,HienTrangBeTong,HienTrangThep;

    private TextView tvToaDo,tvTenCT,tvHienTrang,tvChiTiet,tvHienTrangChitiet,tvMaTram,tvViTri,tvThoiGian,tvTenHM;
    private HorizontalListView horizontalListView;
    private HorizontalAdapter customeAdapter;
    File file , fileLuuDiTat;
    Button btnremoveHT,btnremoveHT2;
    Bitmap AnhGoc;
    ArrayList<String> listTheThoiGian = new ArrayList<String>();
    ArrayList<String> listDoPhanGiaiAnh = new ArrayList<String>();
    ArrayList<String> listDoSang = new ArrayList<String>();
    ArrayList<String> Arraylist = new ArrayList<String>();
    private String cameraId,DanhGiaHienTrang;
    OutputStream outputStream;
    ArrayList<String> ArraylistTram = new ArrayList<String>();
    private ArrayList<Model> imageModelArrayList;
    ArrayList<String> listHT = new ArrayList<String>();
    ArrayList<String> listCT = new ArrayList<String>();
    ArrayList<String> ArraylistHT = new ArrayList<String>();
    private String Bien,vitrichup= null, longi = null, latgi = null,TenChiTiet="",tenHM,tenTram,tenCT,Loai="1";
    String[] duongdananh= new String[100];
    String[] ArrayHM = new String[1000];
    int posi,biendem,lengthDuongDan,chatLuongAnh=2,doSang = 10;
    Switch switchLiveCam;
    TextView txtLiveCam;
    //Save to FILE
    Time today;
    Boolean allwayon=true,dialog_on = false;
    public static final String TAG = "ListViewExample";
    private HT_Adapter listview_ht_adapter;
    private List<HienTrang> productList;
    AutoCompleteTextView tvTenChiTiet;
    private ListView listView;
    private EditText editText;
    private Button button;
    private ArrayList<String> myList = new ArrayList<String>();
    protected CameraCharacteristics cameraCharacteristics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2_basic_fragment2);
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        AnhXa();
        NhanBien();
        SettingToaDo();
        String text2 = longi + "'N" + " - " + latgi + "'E";
        today = new Time(Time.getCurrentTimezone());

        /**
         * Gắn thẻ
         */
        //From Java 1.4 , you can use keyword 'assert' to check expression true or false
        tvMaTram.setText(tenTram);
        tvToaDo.setText(text2);
        tvViTri.setText(vitrichup);
        //////////////////
        switchLiveCam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String str = (switchLiveCam.isChecked() ? "Bạn đang chụp cho dị tật" : "");
                txtLiveCam.setText(str) ;
                if(txtLiveCam.getText().toString().contains("dị tật"))
                    if(!dialog_on) DialogSetting();
            }
        });
        ///////////////////////
        btnLuuAnh.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (!tvTenCT.getText().toString().contains("Dị tật bất thường") || !tvHienTrang.getText().toString().equals("")) {
                    today = new Time(Time.getCurrentTimezone());
                    today.setToNow();
                    if (!tvTenCT.getText().toString().contains("Dị tật bất thường") && tvHienTrang.getText().toString().equals("") && !switchLiveCam.isChecked())
                        takePicture();
                    else if (tvTenCT.getText().toString().contains("Dị tật bất thường") || !tvHienTrang.getText().toString().equals("") || switchLiveCam.isChecked()) {
                        fileLuuDiTat = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
                        fileLuuDiTat = new File(fileLuuDiTat, tenTram);
                        fileLuuDiTat = new File(fileLuuDiTat, ArrayHM[lengthDuongDan - 1]);
                        if (!tvHienTrang.getText().toString().equals(""))
                            fileLuuDiTat = new File(fileLuuDiTat, tvChiTiet.getText().toString().replace("_", "") + " - " + tvHienTrang.getText().toString().replace("_", ""));
                        else
                            fileLuuDiTat = new File(fileLuuDiTat, tvChiTiet.getText().toString() + "-" + today.format("%kh%Ms%S"));
                        if (!fileLuuDiTat.exists()) {
                            if (!fileLuuDiTat.mkdirs()) {
                                Log.d("App", "failed to create directory");
                            } else {
                                //Toast.makeText(getApplicationContext(), "Đã lưu ảnh vào mục 10 dị tật", Toast.LENGTH_SHORT).show();
                            }
                        }
                        takePicture();
                    }
                } else {DialogSetting();switchLiveCam.setChecked(false);}
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                    DialogZoom(GetBitMaptoPath(tenTram,tvTenHM.getText().toString(),tvTenCT.getText().toString()));
                return false;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!tvTenCT.getText().toString().contains("Dị tật bất thường") && tvHienTrang.getText().toString().equals(""))
                    NextTo_AnhChupActivity(posi);
            }
        });
        tvTenCT.setText(tenCT);
        tvTenHM.setText(tenHM);
        SetImagetoView();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    if (ArraylistTram.size() > 0) {
                        int position = ArraylistTram.indexOf(tvTenCT.getText().toString());
                        if (position != 0 && position !=ArraylistTram.size()-1)
                            tvTenCT.setText(ArraylistTram.get(position - 1));
                        else tvTenCT.setText(ArraylistTram.get(0));
                        SetImagetoView();
                    }
                }
                catch (Exception e)
                {

                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ArraylistTram.size() > 0)
                {
                    int position = ArraylistTram.indexOf(tvTenCT.getText().toString());
                    if (position != ArraylistTram.size() - 2)
                        tvTenCT.setText(ArraylistTram.get(position + 1));
                    else tvTenCT.setText(ArraylistTram.get(0));
                    SetImagetoView();
                }
            }
        });
        tvTenCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(Camera2BasicFragment.this,tvTenCT);
                for (String s:ArraylistTram)
                {
                    if (!s.contains("+Thêm công việc khác"))
                    {
                        try {
                            File filegoc = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
                            filegoc = new File(filegoc, tenTram);
                            filegoc = new File(filegoc, tenHM);
                            filegoc = new File(filegoc, s);
                            File[] lengthFD = filegoc.listFiles();
                            if (lengthFD.length >0)
                            {
                                SubMenu sub3= popupMenu.getMenu().addSubMenu(s);
                            }
                            else popupMenu.getMenu().add(s);
                        } catch (AbstractMethodError a) {}
                    }   else popupMenu.getMenu().add(s);
                }
;
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (!item.getTitle().toString().contains("+Thêm công việc khác"))
                        {tvTenCT.setText(item.getTitle());
                        SetImagetoView();}
                        else DialogThemCongViec();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(layoutSetting.getVisibility()==View.GONE)
                    layoutSetting.setVisibility(View.VISIBLE);
                else layoutSetting.setVisibility(View.GONE);*/
                DialogSetting();
            }
        });
        btnTheThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCheDoChup();
            }
        });

    }
    private static final int minCompensationRange = 0;
    private static final int maxCompensationRange = 2;


    public void setBrightness(int value) {
        int brightness = (int) (minCompensationRange + (maxCompensationRange - minCompensationRange) * (value / 100f));
        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, brightness);
        applySettings();
    }

    private void applySettings() {
        try {
            mCaptureSession.setRepeatingRequest(mPreviewRequestBuilder.build(), null, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * Conversion from screen rotation to JPEG orientation.
     */
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String FRAGMENT_DIALOG = "dialog";

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }
    public void AnhXa(){
        tvTenHM = findViewById(R.id.tvTenHM);
        imageView = findViewById(R.id.imageView);
        mTextureView = findViewById(R.id.texture);
        tvToaDo = findViewById(R.id.tvToaDo);
        btnBack = findViewById(R.id.btnback);
        btnNext = findViewById(R.id.btnNext);
        btnLuuAnh = findViewById(R.id.btnLuuAnh);
        tvTenCT = findViewById(R.id.tvTenCT);
        layoutSetting = findViewById(R.id.layoutSetting) ;
        btnSetting = findViewById(R.id.btnSetting);
        tvMaTram = findViewById(R.id.tvMaTram);
        tvThoiGian = findViewById(R.id.tvThoiGian);
        tvViTri = findViewById(R.id.tvViTri);
        btnTheThoiGian = findViewById(R.id.btnTheThoiGian);
        switchLiveCam = (Switch) findViewById(R.id.switchLive);
        txtLiveCam = (TextView) findViewById(R.id.txtditat);
        tvChiTiet = findViewById(R.id.tvTenChiTiet);
        tvHienTrang = findViewById(R.id.tvHienTrangChiTiet);
    }
    /**
     * Tag for the {@link Log}.
     */

    /**
     * Camera state: Showing camera preview.
     */
    private static final int STATE_PREVIEW = 0;

    /**
     * Camera state: Waiting for the focus to be locked.
     */
    private static final int STATE_WAITING_LOCK = 1;

    /**
     * Camera state: Waiting for the exposure to be precapture state.
     */
    private static final int STATE_WAITING_PRECAPTURE = 2;

    /**
     * Camera state: Waiting for the exposure state to be something other than precapture.
     */
    private static final int STATE_WAITING_NON_PRECAPTURE = 3;

    /**
     * Camera state: Picture was taken.
     */
    private static final int STATE_PICTURE_TAKEN = 4;

    /**
     * Max preview width that is guaranteed by Camera2 API
     */
    private static final int MAX_PREVIEW_WIDTH = 1280;
    /**
     * Max preview height that is guaranteed by Camera2 API
     */
    private static final int MAX_PREVIEW_HEIGHT = 720;

    /**
     * {@link TextureView.SurfaceTextureListener} handles several lifecycle events on a
     * {@link TextureView}.
     */
    private final TextureView.SurfaceTextureListener mSurfaceTextureListener
            = new TextureView.SurfaceTextureListener() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            openCamera(width, height);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        }

    };

    /**
     * ID of the current {@link CameraDevice}.
     */
    private String mCameraId;

    /**
     * An {@link AutoFitTextureView} for camera preview.
     */
    private AutoFitTextureView mTextureView;

    /**
     * A {@link CameraCaptureSession } for camera preview.
     */
    private CameraCaptureSession mCaptureSession;

    /**
     * A reference to the opened {@link CameraDevice}.
     */
    private CameraDevice mCameraDevice;

    /**
     * The {@link Size} of camera preview.
     */
    private Size mPreviewSize;

    /**
     * {@link CameraDevice.StateCallback} is called when {@link CameraDevice} changes its state.
     */
    private void showToast(final String text) {
        final Activity activity = Camera2BasicFragment.this;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            // This method is called when the camera is opened.  We start camera preview here.
            mCameraOpenCloseLock.release();
            mCameraDevice = cameraDevice;
            createCameraPreviewSession();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice)
        {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
            finish();
        }

    };

    /**
     * An additional thread for running tasks that shouldn't block the UI.
     */
    private HandlerThread mBackgroundThread;

    /**
     * A {@link Handler} for running tasks in the background.
     */
    private Handler mBackgroundHandler;

    /**
     * An {@link ImageReader} that handles still image capture.
     */
    private ImageReader mImageReader;

    /**
     * This is the output file for our picture.
     */
    private File mFile;

    /**
     * This a callback object for the {@link ImageReader}. "onImageAvailable" will be called when a
     * still image is ready to be saved.
     */
    private final ImageReader.OnImageAvailableListener mOnImageAvailableListener
            = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            /** DƯỜNG DẪN Lưu ảnh**/
            if (!tvTenCT.getText().toString().contains("Dị tật bất thường") && tvHienTrang.getText().toString().equals("") && !txtLiveCam.getText().toString().contains("dị tật"))
            {
                File filegoc = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
                filegoc = new File(filegoc, tenTram);
                filegoc = new File(filegoc, tenHM);
                filegoc = new File(filegoc, tvTenCT.getText().toString());
                if(filegoc.exists()){
                    /// ngày giờ
                    today = new Time(Time.getCurrentTimezone());
                    today.setToNow();
                    String time = new String();
                    File[] lengthFD = filegoc.listFiles();
                    if (lengthFD.length == 0)
                        Bien = "--1--";
                    else if (lengthFD.length > 0)
                    {
                        for (File file : lengthFD) {
                            String[] Check = file.getPath().split("--");

                            if (Check[1].equals("1")) {
                                File anhCu = new File(file.getPath());
                                File anhMoi = new File(Check[0] + "--0--" + Check[2]);
                                anhCu.renameTo(anhMoi);
                                Log.d(" Đã chuyển", anhMoi.getName());
                            }
                        }
                        Bien = "--1--";
                    }
                    String gio = today.format("%k:%M:%S").replace(":", "@").replace("_", "@");
                    String Chitiet = tvChiTiet.getText().toString().replace("\\.", ",").replace("_", "").replace("@", "");
                    time = "@HIENTRANG" + "@" + "@GIO" + "@" + Bien;
                    time = time.replace("@HIENTRANG", Chitiet);
                    time = time.replace("@GIO", gio);
                    ///
                    mFile = new File(filegoc, time + ".jpg");
                    mBackgroundHandler.post(new Camera2BasicFragment.ImageSaver(reader.acquireNextImage(), mFile));
                }

            }

            else if (tvTenCT.getText().toString().contains("Dị tật bất thường") || !tvHienTrang.getText().toString().equals("") || txtLiveCam.getText().toString().contains("dị tật"))
            {
                today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                String gio = today.format("%kh%Ms%S").replace(":", "@").replace("_", "@");
                if(fileLuuDiTat.exists()){
                    mFile = new File(fileLuuDiTat, "" + "@" + gio+ "@" + "--1--" + ".jpg");
                    mBackgroundHandler.post(new Camera2BasicFragment.ImageSaver(reader.acquireNextImage(), mFile));
                    if(switchLiveCam.isChecked()) switchLiveCam.setChecked(false);
                    String str = (switchLiveCam.isChecked() ? "Bạn đang chụp cho dị tật" : "");
                    txtLiveCam.setText(str) ;
                    Toast.makeText(getApplicationContext(), "Đã lưu ảnh vào mục 10 dị tật", Toast.LENGTH_SHORT).show();
                }


            }

        }

    };

    /**
     * {@link CaptureRequest.Builder} for the camera preview
     */
    private CaptureRequest.Builder mPreviewRequestBuilder;

    /**
     * {@link CaptureRequest} generated by {@link #mPreviewRequestBuilder}
     */
    private CaptureRequest mPreviewRequest;

    /**
     * The current state of camera state for taking pictures.
     *
     * @see #mCaptureCallback
     */
    private int mState = STATE_PREVIEW;

    /**
     * A {@link Semaphore} to prevent the app from exiting before closing the cCamera2BasicFragment
     */
    private Semaphore mCameraOpenCloseLock = new Semaphore(1);

    /**
     * Whether the current camera device supports Flash or not.
     */
    private boolean mFlashSupported;

    /**
     * Orientation of the camera sensor
     */
    private int mSensorOrientation;

    /**
     * A {@link CameraCaptureSession.CaptureCallback} that handles events related to JPEG capture.
     */
    private CameraCaptureSession.CaptureCallback mCaptureCallback
            = new CameraCaptureSession.CaptureCallback() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private void process(CaptureResult result) {
            switch (mState) {
                case STATE_PREVIEW: {
                    // We have nothing to do when the camera preview is working normally.
                    break;
                }
                case STATE_WAITING_LOCK: {
                    Integer afState = result.get(CaptureResult.CONTROL_AF_STATE);
                    if (afState == null) {
                        captureStillPicture();
                    } else if (CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED == afState ||
                            CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED == afState) {
                        // CONTROL_AE_STATE can be null on some devices
                        Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                        if (aeState == null ||
                                aeState == CaptureResult.CONTROL_AE_STATE_CONVERGED) {
                            mState = STATE_PICTURE_TAKEN;
                            captureStillPicture();
                        } else {
                            runPrecaptureSequence();
                        }
                    }
                    break;
                }
                case STATE_WAITING_PRECAPTURE: {
                    // CONTROL_AE_STATE can be null on some devices
                    Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null ||
                            aeState == CaptureResult.CONTROL_AE_STATE_PRECAPTURE ||
                            aeState == CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED) {
                        mState = STATE_WAITING_NON_PRECAPTURE;
                    }
                    break;
                }
                case STATE_WAITING_NON_PRECAPTURE: {
                    // CONTROL_AE_STATE can be null on some devices
                    Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null || aeState != CaptureResult.CONTROL_AE_STATE_PRECAPTURE) {
                        mState = STATE_PICTURE_TAKEN;
                        captureStillPicture();
                    }
                    break;
                }
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session,
                                        @NonNull CaptureRequest request,
                                        @NonNull CaptureResult partialResult) {
            process(partialResult);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                       @NonNull CaptureRequest request,
                                       @NonNull TotalCaptureResult result) {
            process(result);
        }

    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static Size chooseOptimalSize(Size[] choices, int textureViewWidth,
                                          int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {

        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<>();
        // Collect the supported resolutions that are smaller than the preview Surface
        List<Size> notBigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight &&
                    option.getHeight() == option.getWidth() * h / w) {
                if (option.getWidth() >= textureViewWidth &&
                        option.getHeight() >= textureViewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }

        // Pick the smallest of those big enough. If there is no one big enough, pick the
        // largest of those not big enough.
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new Camera2BasicFragment.CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new Camera2BasicFragment.CompareSizesByArea());
        } else {
            Log.e(TAG, "Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    public static Camera2BasicFragment newInstance()
    {
        return new Camera2BasicFragment();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        zoom = null;
        startBackgroundThread();
        // When the screen is turned off and turned back on, the SurfaceTexture is already
        // available, and "onSurfaceTextureAvailable" will not be called. In that case, we can open
        // a camera and start preview from here (otherwise, we wait until the surface is ready in
        // the SurfaceTextureListener).
        if (mTextureView.isAvailable()) {
            openCamera(mTextureView.getWidth(), mTextureView.getHeight());
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onPause() {
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION)
        {
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Sets up member variables related to cCamera2BasicFragment
     *
     * @param width  The width of available size for camera preview
     * @param height The height of available size for camera preview
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("SuspiciousNameCombination")
    private void setUpCameraOutputs(int width, int height) {
        Activity activity = Camera2BasicFragment.this;
        CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId : manager.getCameraIdList()) {
                cameraCharacteristics
                        = manager.getCameraCharacteristics(cameraId);

                // We don't use a front facing camera in this sample.
                Integer facing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }
                maximumZoomLevel = (cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM));

                StreamConfigurationMap map = cameraCharacteristics.get(
                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (map == null) {
                    continue;
                }

                // For still image captures, we use the largest available size.
                Size largest = Collections.max(
                        Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                        new Camera2BasicFragment.CompareSizesByArea());
                mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
                        ImageFormat.JPEG, /*maxImages*/2);
                mImageReader.setOnImageAvailableListener(
                        mOnImageAvailableListener, mBackgroundHandler);


                // Find out if we need to swap dimension to get the preview size relative to sensor
                // coordinate.
                int displayRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                //noinspection ConstantConditions
                mSensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                boolean swappedDimensions = false;
                switch (displayRotation) {
                    case Surface.ROTATION_0:
                    case Surface.ROTATION_180:
                        if (mSensorOrientation == 90 || mSensorOrientation == 270) {
                            swappedDimensions = true;
                        }
                        break;
                    case Surface.ROTATION_90:
                    case Surface.ROTATION_270:
                        if (mSensorOrientation == 0 || mSensorOrientation == 180) {
                            swappedDimensions = true;
                        }
                        break;
                    default:
                        Log.e(TAG, "Display rotation is invalid: " + displayRotation);
                }

                Point displaySize = new Point();
                activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
                int rotatedPreviewWidth = width;
                int rotatedPreviewHeight = height;
                int maxPreviewWidth = displaySize.x;
                int maxPreviewHeight = displaySize.y;

                if (swappedDimensions) {
                    rotatedPreviewWidth = height;
                    rotatedPreviewHeight = width;
                    maxPreviewWidth = displaySize.y;
                    maxPreviewHeight = displaySize.x;
                }

                if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
                    maxPreviewWidth = MAX_PREVIEW_WIDTH;
                }

                if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
                    maxPreviewHeight = MAX_PREVIEW_HEIGHT;
                }

                // Danger, W.R.! Attempting to use too large a preview size could  exceed the camera
                // bus' bandwidth limitation, resulting in gorgeous previews but the storage of
                // garbage capture data.
                mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                        rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
                        maxPreviewHeight, largest);

                // We fit the aspect ratio of TextureView to the size of preview we picked.
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mTextureView.setAspectRatio(
                            mPreviewSize.getWidth(), mPreviewSize.getHeight());
                } else {
                    mTextureView.setAspectRatio(
                            mPreviewSize.getHeight(), mPreviewSize.getWidth());
                }

                // Check if the flash is supported.
                Boolean available = cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                mFlashSupported = available == null ? false : available;

                mCameraId = cameraId;
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            // Currently an NPE is thrown when the Camera2API is used but not supported on the
            // device this code runs.

        }
    }
    /**
     * Opens the camera specified by {@link Camera2BasicFragment#mCameraId}.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openCamera(int width, int height) {
        if (ContextCompat.checkSelfPermission(Camera2BasicFragment.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestCameraPermission();
            }
            return;
        }
        setUpCameraOutputs(width, height);
        configureTransform(width, height);
        Activity activity = Camera2BasicFragment.this;
        CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);

        try {
            if (!mCameraOpenCloseLock.tryAcquire(100, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
            manager.openCamera(mCameraId, mStateCallback, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.", e);
        }
    }

    /**
     * Closes the current {@link CameraDevice}.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void closeCamera() {
        try {
            mCameraOpenCloseLock.acquire();
            if (null != mCaptureSession) {
                mCaptureSession.close();
                mCaptureSession = null;
            }
            if (null != mCameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (null != mImageReader) {
                mImageReader.close();
                mImageReader = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        } finally {
            mCameraOpenCloseLock.release();
        }
    }

    /**
     * Starts a background thread and its {@link Handler}.
     */
    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    /**
     * Stops the background thread and its {@link Handler}.
     */
    private void stopBackgroundThread() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBackgroundThread.quitSafely();
        }
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    /**
     * Creates a new {@link CameraCaptureSession} for camera preview.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createCameraPreviewSession() {
        try {
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            assert texture != null;
            // We configure the size of default buffer to be the size of camera preview we want.
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            // This is the output Surface we need to start preview.
            Surface surface = new Surface(texture);
            // We set up a CaptureRequest.Builder with the output Surface.
            mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

            mPreviewRequestBuilder.addTarget(surface);
            // Here, we create a CameraCaptureSession for camera preview.
            mCameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            // The camera is already closed
                            if (null == mCameraDevice) {
                                return;
                            }

                            // When the session is ready, we start displaying the preview.
                            mCaptureSession = cameraCaptureSession;
                            try {
                                // Auto focus should be continuous for camera preview.
                                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                                String string= DataforPath("CHEDOCHUP");
                                String[] listCheDo = string.split("@");
                                if (listCheDo.length<20)
                                {
                                    string = UT.CheDoChup;
                                    listCheDo = string.split("@");
                                    saveDataOnCacher(string,"CHEDOCHUP");
                                }

                                final String[] tDoSang = {listCheDo[19].toString()};
                                switch (tDoSang[0])
                                {
                                    case "Cao":
                                        doSang = 100;setBrightness(doSang);

                                        break;
                                    case "Trung bình":
                                        doSang = 50;setBrightness(doSang);

                                        break;
                                    case "Thấp":
                                        doSang = 10;setBrightness(doSang);

                                        break;
                                }                                //mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, 12);
                                //mPreviewRequestBuilder.set(CaptureRequest.COLOR_CORRECTION_GAINS, new RggbChannelVector(86, 86, 86, 86));
                                // Flash is automatically enabled when necessary.
                                //setAutoFlash(mPreviewRequestBuilder);

                                // Finally, we start displaying the camera preview.
                                mPreviewRequest = mPreviewRequestBuilder.build();
                                mCaptureSession.setRepeatingRequest(mPreviewRequest,
                                        mCaptureCallback, mBackgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(
                                @NonNull CameraCaptureSession cameraCaptureSession) {
                        }
                    }, null
            );
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Configures the necessary {@link Matrix} transformation to `mTextureView`.
     * This method should be called after the camera preview size is determined in
     * setUpCameraOutputs and also the size of `mTextureView` is fixed.
     *
     * @param viewWidth  The width of `mTextureView`
     * @param viewHeight The height of `mTextureView`
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void configureTransform(int viewWidth, int viewHeight) {
        Activity activity = Camera2BasicFragment.this;
        if (null == mTextureView || null == mPreviewSize || null == activity) {
            return;
        }
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, mPreviewSize.getHeight(), mPreviewSize.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / mPreviewSize.getHeight(),
                    (float) viewWidth / mPreviewSize.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180, centerX, centerY);
        }
        mTextureView.setTransform(matrix);
    }

    /**
     * Initiate a still image capture.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void takePicture() {
        lockFocus();

    }

    /**
     * Lock the focus as the first step for a still image capture.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void lockFocus() {
        try {
            // This is how to tell the camera to lock focus.
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                    CameraMetadata.CONTROL_AF_TRIGGER_START);
            // Tell #mCaptureCallback to wait for the lock.
            mState = STATE_WAITING_LOCK;
            mCaptureSession.capture(mPreviewRequestBuilder.build(), mCaptureCallback,
                    mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * Run the precapture sequence for capturing a still image. This method should be called when
     * we get a response in {@link #mCaptureCallback} from {@link #lockFocus()}.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void runPrecaptureSequence() {
        try {
            // This is how to tell the camera to trigger.
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER,
                    CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START);
            // Tell #mCaptureCallback to wait for the precapture sequence to be set.
            mState = STATE_WAITING_PRECAPTURE;
            mCaptureSession.capture(mPreviewRequestBuilder.build(), mCaptureCallback,
                    mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Capture a still picture. This method should be called when we get a response in
     * {@link #mCaptureCallback} from both {@link #lockFocus()}.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void captureStillPicture() {

        try {
            final Activity activity = Camera2BasicFragment.this;
            if (null == activity || null == mCameraDevice) {
                return;
            }
            // This is the CaptureRequest.Builder that we use to take a picture.
            final CaptureRequest.Builder captureBuilder =
                    mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(mImageReader.getSurface());

            // Use the same AE and AF modes as the preview.
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            //setAutoFlash(captureBuilder);

            // Orientation
            int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, getOrientation(rotation));
            //Zoom
            if (zoom != null) {
                captureBuilder.set(CaptureRequest.SCALER_CROP_REGION, zoom);
            }

            CameraCaptureSession.CaptureCallback CaptureCallback
                    = new CameraCaptureSession.CaptureCallback() {

                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                               @NonNull CaptureRequest request,
                                               @NonNull TotalCaptureResult result)
                {
                    unlockFocus();
                }
            };

            mCaptureSession.stopRepeating();
            mCaptureSession.abortCaptures();
            mCaptureSession.capture(captureBuilder.build(), CaptureCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the JPEG orientation from the specified screen rotation.
     *
     * @param rotation The screen rotation.
     * @return The JPEG orientation (one of 0, 90, 270, and 360)
     */
    private int getOrientation(int rotation) {
        // Sensor orientation is 90 for most devices, or 270 for some devices (eg. Nexus 5X)
        // We have to take that into account and rotate JPEG properly.
        // For devices with orientation of 90, we simply return our mapping from ORIENTATIONS.
        // For devices with orientation of 270, we need to rotate the JPEG 180 degrees.
        return (ORIENTATIONS.get(rotation) + mSensorOrientation + 270) % 360;
    }

    /**
     * Unlock the focus. This method should be called when still image capture sequence is
     * finished.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void unlockFocus() {
        try {
            // Reset the auto-focus trigger
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                    CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
            mCaptureSession.capture(mPreviewRequestBuilder.build(), mCaptureCallback,
                    mBackgroundHandler);
            // After this, the camera will go back to the normal state of preview.
            mState = STATE_PREVIEW;
            //resume Zoom effect after taking a picture
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            if (zoom != null) mPreviewRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, zoom);
            mCaptureSession.setRepeatingRequest(mPreviewRequestBuilder.build(), mCaptureCallback,
                    mBackgroundHandler);
        } catch (final CameraAccessException e) {

        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setAutoFlash(CaptureRequest.Builder requestBuilder) {
        if (mFlashSupported) {
            requestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
        }
    }

    /**
     * Saves a JPEG {@link Image} into the specified { @link File}.
     */
    private class ImageSaver implements Runnable {
        /**
         * The JPEG image
         */
        private final Image mImage;
        /**
         * The file we save the image into.
         */
        private final File mFile;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        ImageSaver(Image image, File file)
        {
            mImage = image;
            mFile = file;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            FileOutputStream output = null;
            Bitmap  bitmap2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            bitmap2 = BITMAP_RESIZER(bitmap2, bitmap2.getWidth() / chatLuongAnh, bitmap2.getHeight() / chatLuongAnh);
            /**XOAY ẢNH**/
            if (bitmap2.getWidth()> bitmap2.getHeight())
            {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(),matrix, true);
            }
            AnhGoc = bitmap2;
            bitmap2 = GanToaDo(bitmap2);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG,80,stream);
            byte[] byteArray = stream.toByteArray();

            //bytes2 = byteBuffer.array();
            try {
                output = new FileOutputStream(mFile);
                output.write(byteArray);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mImage.close();
                if (null != output)
                {
                    try
                    {
                        output.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            setText(imageView,AnhGoc);
        }
    }
    private void setText(final ImageView imageView,final Bitmap bitmap){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (allwayon)
                {
                    imageView.setImageBitmap(bitmap);
                    Toast.makeText(getApplicationContext(),"Đã lưu ảnh!", Toast.LENGTH_SHORT).show();
                    tvHienTrang.setText("");
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);
                    ArraylistTram = LayDanhSachCT(tenHM);
                }
                else {
                    Nextto_Camera();
                }


            }
        });
    }
    private void setText2(final TextView textView,String string){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(string);
            }
        });
    }

    /**
     * Compares two {@code Size}s based on their areas.
     */
    static class CompareSizesByArea implements Comparator<Size> {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }
    /**
     * Shows OK/Cancel confirmation dialog about camera permission.
     */
    public static class ConfirmationDialog extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            parent.requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA_PERMISSION);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Activity activity = parent.getActivity();
                                    if (activity != null)
                                    {
                                        activity.finish();
                                    }
                                }
                            })
                    .create();
        }
    }
    @Override
    public void onBackPressed()
    {
        NextTo_CongTacActivity(posi);
    }
    private void NextTo_CongTacActivity(int position){
        String[] Stt = tenHM.split("\\.");
        Intent intent = new Intent(Camera2BasicFragment.this,congtacActivity.class);
        intent.putExtra("TenHM",tenHM);  // Truyền một String
        intent.putExtra("TenTram", tenTram);  // Truyền một String
        intent.putExtra("Long", longi);  // Truyền một String
        intent.putExtra("Lat", latgi);  // Truyền một String
        intent.putExtra("ViTri", vitrichup);  // Truyền một String
        intent.putExtra("MangString", ArrayHM);  // Truyền một String
        intent.putExtra("position", Stt[0]);  // Truyền một String
        intent.putExtra("TenChiTiet", TenChiTiet);  // Truyền một String
        intent.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
        finish();
        startActivity(intent);
    }
    public Bitmap BITMAP_RESIZER(Bitmap bitmap,int newWidth,int newHeight){
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
    public void SettingToaDo(){
        int month = today.month + 1;
        /***/
        String string= DataforPath("CHEDOCHUP");
        String[] listCheDo = string.split("@");
        /***/
        if (!latgi.equals("null"))
        {

            /**
             * Toạ độ
             */
            if (!listCheDo[11].contains("On"))
            tvToaDo.setVisibility(View.GONE);
            /**
             * Vị Trí
             */
            if (!listCheDo[13].contains("On"))
                tvViTri.setVisibility(View.GONE);
            /**
             * Thẻ thời gian
             */
            if (!listCheDo[9].contains("None"))
            {
                //String text4 = listCheDo[9].replace("hh:mm",String.valueOf(today.format("%k:%M"))).replace("dd",String.valueOf(today.monthDay)).replace("mm",String.valueOf(month)).replace("yyyy",String.valueOf(today.year));
                //tvThoiGian.setText(text4);
            }
            else tvThoiGian.setVisibility(View.GONE);
        }
    }
    public Bitmap GanToaDo(Bitmap bitmap2){
        Bitmap AnhDauRa = null;
        Bitmap newbitmap = null;
        /***/
        String string= DataforPath("CHEDOCHUP");
        String[] listCheDo = string.split("@");
        /***/

        Bitmap.Config config = bitmap2.getConfig();
        config = Bitmap.Config.ARGB_8888;
        newbitmap=Bitmap.createBitmap(bitmap2.getWidth(),bitmap2.getHeight(),config);
        Canvas newcanvas = new Canvas(newbitmap);
        newcanvas.drawBitmap(bitmap2,0,0,null);
        Paint painttext = new Paint(Paint.ANTI_ALIAS_FLAG);
        painttext.setColor(Color.WHITE);
        painttext.setTextSize(bitmap2.getWidth()/35);
        //painttext.setShadowLayer(10f,10f,10f,Color.BLACK);
        Rect rectText = new Rect();
        String[] maTram = tenTram.split("_");
        String text ="Mã trạm:"+maTram[0];
        String text2 =longi+"'N"+" - "+latgi+"'E";
        String text3 =vitrichup;
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int month = today.month +1;
        painttext.getTextBounds(text,0,text.length(),rectText);
        newcanvas.drawText(text,0,rectText.height(),painttext);
        /**
         * Toạ độ
         */
        if (listCheDo[11].contains("On"))
            if (!latgi.equals("null"))
                newcanvas.drawText(text2,0,2*rectText.height(),painttext);
        /**
         * Vị Trí
         */
        int hViTri = 3;
        if (listCheDo[11].contains("Off")) hViTri= 2;
        if (listCheDo[13].contains("On"))
            if (!latgi.equals("null"))
                newcanvas.drawText(text3,0,hViTri*rectText.height(),painttext);
        /**
         * Thẻ thời gian
         */
        if (!listCheDo[9].contains("None"))
        {
            int hThoiGian = 4;
            if (listCheDo[11].contains("Off")&& listCheDo[13].contains("On")) hThoiGian= 3;
            if (listCheDo[11].contains("Off")&& listCheDo[13].contains("Off")) hThoiGian= 2;
            //String text4 =today.monthDay + "/"+month+ "/" + today.year+ " " + today.format("%k:%M");
            String text4 = listCheDo[9].replace("hh:mm",String.valueOf(today.format("%k:%M"))).replace("dd",String.valueOf(today.monthDay)).replace("mm",String.valueOf(month)).replace("yyyy",String.valueOf(today.year));
            newcanvas.drawText(text4,0,hThoiGian*rectText.height(),painttext);
        }
        int hChiTiet = 5;
        String text4 = tvChiTiet.getText().toString();
        newcanvas.drawText(text4,0,hChiTiet*rectText.height(),painttext);
        AnhDauRa = newbitmap;


        return AnhDauRa;
    }

    private void NextTo_AnhChupActivity(int position){
        Intent intent = new Intent(Camera2BasicFragment.this,anhchupActivity.class);
        intent.putExtra("TenTram", tenTram);  // Truyền một String
        intent.putExtra("TenHangMuc",tenHM);  // Truyền một String
        intent.putExtra("TenCongTac",tvTenCT.getText());  //Truyền một String
        intent.putExtra("position",  String.valueOf(position));  // Truyền một String
        intent.putExtra("MangCT",  duongdananh);  // Truyền một String
        intent.putExtra("Long", longi);  // Truyền một String
        intent.putExtra("Lat", latgi);  // Truyền một String
        intent.putExtra("ViTri", vitrichup);  // Truyền một String
        intent.putExtra("MangString", ArrayHM);  // Truyền một String
        intent.putExtra("TenChiTiet",TenChiTiet);  // Truyền một String
        intent.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
        startActivity(intent);
    }
    AdapterView.OnItemClickListener HonItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(getApplicationContext(), ArrayHM[position].toString(), Toast.LENGTH_SHORT).show();
            //NextTo_CongTacActivity(position);
            ArraylistTram = LayDanhSachCT(ArrayHM[position].toString());
            tvTenHM.setText(ArrayHM[position].toString());
            if(!ArrayHM[position].toString().contains("Hình ảnh dị tật"))
            {
                tvTenCT.setText(ArraylistTram.get(0).toString());
                imageModelArrayList = populateData(Integer.parseInt(ArrayHM[position].toString().split("\\.")[0]));
                tenHM = ArrayHM[position].toString();
                //Log.d("hjhjh",imageModelArrayList.size()+"");
                customeAdapter = new HorizontalAdapter(Camera2BasicFragment.this, imageModelArrayList);
                horizontalListView.setAdapter(customeAdapter);
                horizontalListView.scrollTo(Integer.parseInt(ArrayHM[position].toString().split("\\.")[0]) * 50);
                SetImagetoView();
            }
            else
            {
                tvTenCT.setText("Dị tật bất thường");
                imageModelArrayList = populateData(Integer.parseInt(ArrayHM[position].toString().split("\\.")[0]));
                tenHM = ArrayHM[position].toString();
                //Log.d("hjhjh",imageModelArrayList.size()+"");
                customeAdapter = new HorizontalAdapter(Camera2BasicFragment.this, imageModelArrayList);
                horizontalListView.setAdapter(customeAdapter);
                horizontalListView.scrollTo(Integer.parseInt(ArrayHM[position].toString().split("\\.")[0]) * 50);
                SetImagetoView();
            }

        }
    };
    AdapterView.OnItemLongClickListener HonItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            Toast.makeText(getApplicationContext(), ArrayHM[position].toString(), Toast.LENGTH_SHORT).show();
            return true;
        }
    };
    public void SetImagetoView(){
            Bitmap bitmap = GetBitMaptoPath(tenTram, tvTenHM.getText().toString(), tvTenCT.getText().toString());
            if (bitmap!=null)
            {
                final int THUMBNAIL_SIZE = 120;
                Float width = new Float(bitmap.getWidth());
                Float height = new Float(bitmap.getHeight());
                Float ratio = width / height;
                bitmap = Bitmap.createScaledBitmap(bitmap, (int) (THUMBNAIL_SIZE * ratio), THUMBNAIL_SIZE, false);
                imageView.setImageBitmap(bitmap);
            }
            else
            imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_image));

    }
    public ArrayList<String>SortArrayList(ArrayList<String> arrayList) {
        ArrayList<String> arrayList1 = new ArrayList<String>();
        Collections.sort(arrayList);
        for (String file:arrayList)
        {
            if (!file.contains("Hình ảnh khác"))
            {
                arrayList1.add(file);
            }
        }
        for (String file:arrayList)
        {
            if (file.contains("Hình ảnh khác"))
            {
                arrayList1.add(file);

            }
        }
        return arrayList1;
    }
    private ArrayList<Model> populateData(int viTriHM){
        ArrayList<Model> list = new ArrayList<>();
        try{
            //Log.d("Tên hạng mục",tenHM);
            String[] vt = tenHM.split("\\.");
            final int posi = Integer.parseInt(vt[0]);
            for(int i = 0; i <=biendem; i++){
                Model imageModel = new Model();
                String[] position = ArrayHM[i].split("\\.");
                //Log.d("ListHM",  ArrayHM[i]);
                imageModel.setName(position[0]);
                if (Integer.parseInt(position[0])==viTriHM)
                    imageModel.setImage_drawable(R.drawable.duongbotron5);
                else
                    imageModel.setImage_drawable(R.drawable.duongbotron2);
                list.add(imageModel);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error #51", Toast.LENGTH_SHORT).show();
        }
        return list;
    }
    public ArrayList<String> LayDanhSachCT(String tenHM) {
        File filegoc = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
        filegoc = new File(filegoc, tenTram);
        filegoc = new File(filegoc,tenHM);
        File[] listCT= filegoc.listFiles();
        ArrayList<String> strings = new ArrayList<String>();
        for (File s:listCT)
        {
            strings.add(s.getName());
        }
        ArraylistTram = SortArrayList(strings);

        ArraylistTram.add("+Thêm công việc khác");
        return ArraylistTram;
    }
    @SuppressLint("RestrictedApi")
    private void DialogHienTrang(final TextView textView, final ArrayList<String> arrayList,final String loai){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_search);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Button btnOK = (Button) dialog.findViewById(R.id.btnRename);
        /**SEARCH VIEW**/
        ArraylistHT.clear();
        DanhGiaHienTrang = DataforPath("DANHGIAHIENTRANG");
        String[] phuluccon = DanhGiaHienTrang.split("--");
        //Log.d("length:", String.valueOf(phuluccon.length));
        //Log.d("read 1:",phuluccon[1]);
        for (int j = 1; j < phuluccon.length; j = j + 1) {
            if (loai.contains("1"))
            {
                if (j==4||j==5||j==6||j==7||j==8)
                {
                    String[] phuluccongtac = phuluccon[j].split("_");
                    //Log.d("Tên hạng mục:", phuluccongtac[0]);
                    for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                        //Log.d("Tên Hiện trạng:",phuluccongtac[i]);
                        ArraylistHT.add(phuluccongtac[i]);
                    }
                }
            }
            else if (loai.contains("2"))
            {
                if (j==1||j==2||j==3||j==8)
                {
                    String[] phuluccongtac = phuluccon[j].split("_");
                    //Log.d("Tên hạng mục:", phuluccongtac[0]);
                    for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                        //Log.d("Tên Hiện trạng:",phuluccongtac[i]);
                        ArraylistHT.add(phuluccongtac[i]);
                    }
                }
            }
        }
        SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchHientrang);
        searchViewCT.setQueryHint("Nhập ghi chú");

        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        searchAutoCompleteCT.setThreshold(1);
        searchAutoCompleteCT.setDropDownHeight(500);
        final EditText searchEditTextCK = (EditText)  searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditTextCK.setTextColor(getResources().getColor(R.color.colorPrimary));
        searchEditTextCK.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        if (loai.contains("2"))
            searchEditTextCK.setText(textView.getText());
        ImageView searchMagIcon = (ImageView)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchMagIcon.setImageResource(R.drawable.ic_search_xam_24dp);
        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ArraylistHT);
        searchAutoCompleteCT.setAdapter(newsAdapterCT);
        searchAutoCompleteCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoCompleteCT.setText(queryString);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loai.contains("1"))
                {
                    if (textView.getText().equals(""))
                        textView.setText(textView.getText() + searchEditTextCK.getText().toString());
                    else
                        textView.setText(textView.getText() + ", " + searchEditTextCK.getText().toString());
                    arrayList.add(searchEditTextCK.getText().toString());
                    if (!DanhGiaHienTrang.contains(searchEditTextCK.getText().toString()))
                    {
                        DanhGiaHienTrang = DanhGiaHienTrang +"_"+searchEditTextCK.getText().toString()+"_";
                        saveDataOnCacher(DanhGiaHienTrang,"DANHGIAHIENTRANG");
                    }
                    dialog.dismiss();

                }
                else if (loai.contains("2"))
                {
                    if
                            (textView.getText().equals(""))
                        textView.setText(textView.getText() + searchEditTextCK.getText().toString());
                    else
                        textView.setText(textView.getText() + ", " + searchEditTextCK.getText().toString());
                    arrayList.add(searchEditTextCK.getText().toString());
                    if (!DanhGiaHienTrang.contains(searchEditTextCK.getText().toString()))
                    {
                        DanhGiaHienTrang = DanhGiaHienTrang +"_"+searchEditTextCK.getText().toString()+"_";
                        saveDataOnCacher(DanhGiaHienTrang,"DANHGIAHIENTRANG");
                    }
                    dialog.dismiss();
                }
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
    public void Nextto_Camera(){
        /*Intent intent = getIntent();
        tenHM = intent.getStringExtra("TenHM");
        tenTram = intent.getStringExtra("TenTram");
        tenCT = intent.getStringExtra("TenCongTac");
        String ss = intent.getStringExtra("position");
        TenChiTiet = intent.getStringExtra("TenChiTiet");
        Loai = intent.getStringExtra("Loai");
        posi = Integer.parseInt(ss);
        ArrayHM = intent.getStringArrayExtra("MangString");
        String soluongHM = intent.getStringExtra("SoLuong");
        biendem = Integer.parseInt(soluongHM);
        latgi = intent.getStringExtra("Lat");
        duongdananh = intent.getStringArrayExtra("MangCT");
        longi = intent.getStringExtra("Long");
        vitrichup = intent.getStringExtra("ViTri");*/

        finish();
        Intent intent2 = new Intent(Camera2BasicFragment.this, Camera2BasicFragment.class);
        intent2.putExtra("TenHM", tenHM);  // Truyền một String
        intent2.putExtra("TenTram", tenTram);  // Truyền một String
        intent2.putExtra("MangCT", duongdananh);  // Truyền một String
        intent2.putExtra("TenCongTac", tenCT);  // Truyền một String
        intent2.putExtra("position", String.valueOf(posi));  // Truyền một String
        intent2.putExtra("Loai", Loai);  // Truyền một String

        intent2.putExtra("MangString", ArrayHM);  // Truyền một String
        intent2.putExtra("SoLuong", String.valueOf(biendem));  // Truyền một String
        intent2.putExtra("Long",longi);  // Truyền một String
        intent2.putExtra("Lat", latgi);  // Truyền một String
        intent2.putExtra("ViTri", vitrichup);  // Truyền một String

        startActivity(intent2);
    }
    public void NhanBien(){
        Intent intent = getIntent();
        tenHM = intent.getStringExtra("TenHM");
        tenTram = intent.getStringExtra("TenTram");
        tenCT = intent.getStringExtra("TenCongTac");
        String ss = intent.getStringExtra("position");
        TenChiTiet = intent.getStringExtra("TenChiTiet");
        Loai = intent.getStringExtra("Loai");
        posi = Integer.parseInt(ss);
        ArrayHM = intent.getStringArrayExtra("MangString");
        String soluongHM = intent.getStringExtra("SoLuong");
        biendem = Integer.parseInt(soluongHM);
        latgi = intent.getStringExtra("Lat");
        duongdananh = intent.getStringArrayExtra("MangCT");
        longi = intent.getStringExtra("Long");
        vitrichup = intent.getStringExtra("ViTri");

        ArraylistTram = LayDanhSachCT(tenHM);
        horizontalListView = (HorizontalListView) findViewById(R.id.HorizontalListView);
        String[] position = tenHM.split("\\.");
        int sc = Integer.parseInt(position[0]);
        imageModelArrayList = populateData(sc);
        //Log.d("hjhjh",imageModelArrayList.size()+"");
        customeAdapter = new HorizontalAdapter(Camera2BasicFragment.this, imageModelArrayList);
        horizontalListView.setAdapter(customeAdapter);
        horizontalListView.setOnItemClickListener(HonItemClick);
        horizontalListView.setOnItemLongClickListener(HonItemLongClickListener);
        horizontalListView.scrollTo(sc * 10);



        String string= DataforPath("CHEDOCHUP");
        String[] listCheDo = string.split("@");
        if (listCheDo.length<20)
        {
            string = UT.CheDoChup;
            listCheDo = string.split("@");
            saveDataOnCacher(string,"CHEDOCHUP");
        }

        final String[] allwarlive = {listCheDo[17].toString()};
        if (allwarlive[0].contains("On")) allwayon = true;
        else  allwayon = false;

        final String[] tDoPhanGiai = {listCheDo[15].toString()};
        switch (tDoPhanGiai[0])
        {
            case "QHD (2560x1440)":
                chatLuongAnh = 1;
                break;
            case "FHD (1920x1080)":
                chatLuongAnh = 2;
                break;
            case "HD (1280x720)":
                chatLuongAnh = 3;
                break;
            case "VGA (640x480)":
                chatLuongAnh = 4;
                break;
        }
        final String[] tDoSang = {listCheDo[19].toString()};
        switch (tDoSang[0])
        {
            case "Cao":
                doSang = 100;
                break;
            case "Trung bình":
                doSang = 50;
                break;
            case "Thấp":
                doSang = 10;
                break;
        }
        try
        {
            for(String s:ArrayHM)
            {
                Log.d("lentgh duong dan",s);
                lengthDuongDan++;
            }
        }
        catch (Exception e)
        {

        }
    }
    public Bitmap GetBitMaptoPath(String MaTram,String TenHangMuc,String TenCongTac){
        Bitmap bitmap = null;
        try
        {
            File BitmapStorageDir = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
            BitmapStorageDir = new File(BitmapStorageDir, MaTram);
            BitmapStorageDir = new File(BitmapStorageDir, TenHangMuc);
            BitmapStorageDir = new File(BitmapStorageDir, TenCongTac);
            if(BitmapStorageDir.exists())
            {
                File[] files = BitmapStorageDir.listFiles();
                if (files.length > 0)
                {
                    for (File fil : files)
                    {
                        if(fil.exists() && fil.getName().contains("--"))
                        {
                            String[] Check = fil.getName().split("--");
                            if (Check[1].equals("1"))
                            {
                                bitmap = BitmapFactory.decodeFile(fil.getPath());
                            }
                        }
                    }
                }
                else if (files.length == 0)
                {
                    bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_image);

                    return bitmap;
                }
            }
            //KIỂM TRA LẦN CUỐI
            if (bitmap==null) bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_image);
        }
        catch (Exception e)
        {
            bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_image);
        }
        return bitmap;
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
                    allwayon = switchlivecam.isChecked();
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
                    final PopupMenu popupMenu = new PopupMenu(Camera2BasicFragment.this, edtVitri);
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
                    final PopupMenu popupMenu = new PopupMenu(Camera2BasicFragment.this, edtDoSang);
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
                    final PopupMenu popupMenu = new PopupMenu(Camera2BasicFragment.this, edtDoPhanGiai);
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
                    switch (edtDoPhanGiai.getText().toString())
                    {
                        case "QHD (2560x1440)":
                            chatLuongAnh = 1;
                            break;
                        case "FHD (1920x1080)":
                            chatLuongAnh = 2;
                            break;
                        case "HD (1280x720)":
                            chatLuongAnh = 3;
                            break;
                        case "VGA (640x480)":
                            chatLuongAnh = 4;
                            break;
                    }
                    switch (edtDoSang.getText().toString())
                    {
                        case "Cao":
                            doSang = 100;setBrightness(doSang);
                            break;
                        case "Trung bình":
                            doSang = 50;setBrightness(doSang);
                            break;
                        case "Thấp":
                            doSang = 10;setBrightness(doSang);
                            break;

                    }
                    SettingToaDo();
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
    private void DialogThemCongViec(){
       final Dialog dialog = new Dialog(this,R.style.PauseDialog);
        dialog.setContentView(R.layout.dialog_rename2);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        final EditText txtTentram = (EditText) dialog.findViewById(R.id.txtMaTram);
        Button btnOK = (Button) dialog.findViewById(R.id.btnRename);
        TextView tvTen = (TextView) dialog.findViewById(R.id.tvTen);
        tvTen.setText("Thêm công việc:");
        /**gắn tên thu mục*/
        txtTentram.setText("Công tác");
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String tentrammoi = txtTentram.getText().toString();
                File filegoc = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
                filegoc = new File(filegoc, tenTram);
                filegoc = new File(filegoc,tenHM);
                File filenew = new File(filegoc,tentrammoi);
                /**Tạo thu muc mới**/
                if (!filenew.exists()) {
                    if (!filenew.mkdirs()) {
                        Log.d("App", "failed to create directory");
                    }
                    else
                    {

                    }
                }
                dialog.dismiss();
                tvTenCT.setText(tentrammoi);
                Toast.makeText(getApplicationContext(), "Đã thêm thư mục mới!", Toast.LENGTH_SHORT).show();
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
    //Zooming
    public float finger_spacing = 0;
    public float zoom_level = 1f;
    public float maximumZoomLevel;
    public Rect zoom;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            Rect rect = cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            if (rect == null) return false;
            float currentFingerSpacing;

            if (event.getPointerCount() == 2) { //Multi touch.
                currentFingerSpacing = getFingerSpacing(event);
                float delta = 0.05f;
                if (finger_spacing != 0) {
                    if (currentFingerSpacing > finger_spacing)
                    {
                        if ((maximumZoomLevel - zoom_level) <= delta)
                        {
                            delta = maximumZoomLevel - zoom_level;
                        }
                        zoom_level = zoom_level + delta;
                    } else if (currentFingerSpacing < finger_spacing){
                        if ((zoom_level - delta) < 1f) {
                            delta = zoom_level - 1f;
                        }
                        zoom_level = zoom_level - delta;
                    }
                    float ratio = (float) 1 / zoom_level;
                    int croppedWidth = rect.width() - Math.round((float)rect.width() * ratio);
                    int croppedHeight = rect.height() - Math.round((float)rect.height() * ratio);
                    zoom = new Rect(croppedWidth/2, croppedHeight/2,
                            rect.width() - croppedWidth/2, rect.height() - croppedHeight/2);
                    mPreviewRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, zoom);
                }
                finger_spacing = currentFingerSpacing;
            } else { //Single touch point, needs to return true in order to detect one more touch point
                return true;
            }
            mCaptureSession.setRepeatingRequest(mPreviewRequestBuilder.build(), mCaptureCallback, mBackgroundHandler);
            return true;
        } catch (final Exception e) {
            if (BuildConfig.DEBUG) e.printStackTrace();

            return true;
        }
    }
    private float getFingerSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
    /**
     * HIỆN TRẠNG*/
    public void getProductList(TextView editText){
        try
        {
            String[] strings = editText.getText().toString().split("; ");

            myList.clear();
            HienTrangBeTong = DataforPath("HienTrangBeTong");
            String[] phuluccon = HienTrangBeTong.split("@");
            for (int i = 1; i < phuluccon.length; i ++)
            {
                myList.add(phuluccon[i].toString().replace("\n",""));
            }
            HienTrangThep = DataforPath("HienTrangThep");
            String[] phuluccon2 = HienTrangThep.split("@");
            for (int i = 1; i < phuluccon2.length; i ++) {
                myList.add(phuluccon2[i].toString().replace("\n",""));
            }
            productList = new ArrayList<HienTrang>();
            Collections.sort(myList);
            for (String HM : myList)
            {
                productList.add(new HienTrang(HM,Kiemtra(HM,strings)));
            }

            listview_ht_adapter = new HT_Adapter(this,R.layout.list_item_hientrang,productList );
            listView.setAdapter(listview_ht_adapter);
        }
        catch (ArithmeticException e)
        {

        }
    }
    // When user click "Print Selected Items".
    public Boolean Kiemtra(String s,String[] list){
        Boolean b= false;
        for (String string :list)
        {
            if(string.equals(s)) {b=true;break;}
        }
        return b;
    }
    public StringBuilder printSelectedItems()  {
        StringBuilder sb= new StringBuilder();
        ArrayList<HienTrang> countryList = listview_ht_adapter.arraylist;
        for (int i=0 ; i<countryList.size();i++)
        {
            HienTrang country = countryList.get(i);
            if (country.isActive())
            {
                String s= country.getUserName();
                if (sb.length()==0) sb = sb.append(""+s);
                else sb = sb.append("; "+s);
            }
        }
        //Toast.makeText(this, sb, Toast.LENGTH_LONG).show();
        return sb;
    }
    public void DialogHientrangCheckBox() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        editText.setText(tvHienTrangChitiet.getText());
        getProductList(tvHienTrangChitiet);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() <=80)
                    if(editText.getText().toString().contains(("; ")))
                    {
                        String[] dongHienTrang =editText.getText().toString().split(("; "));
                        for( String s:dongHienTrang)
                        {
                            //Tự động học hiện trạng
                            if (!tvChiTiet.getText().toString().equals("Móng")) {
                                if (!HienTrangThep.contains(s.replace("\n", ""))) {
                                    HienTrangThep = HienTrangThep.replace("\n", "") + "@" + s.replace("\n", "");
                                    saveDataOnTemplate(HienTrangThep, "HienTrangThep");
                                    Toast.makeText(Camera2BasicFragment.this, "Thêm mới hiện trạng thép " + s, Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                if (!HienTrangBeTong.contains(s.replace("\n",""))){
                                    HienTrangBeTong = HienTrangBeTong.replace("\n","") +"@"+s.replace("\n","");
                                    saveDataOnTemplate(HienTrangBeTong,"HienTrangBeTong");
                                    Toast.makeText(Camera2BasicFragment.this, "Thêm mới hiện trạng bê tông " + s, Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        tvHienTrangChitiet.setText(editText.getText());
                        dialog.dismiss();

                    } else {
                        //Tự động học hiện trạng
                        if (!tvChiTiet.getText().toString().equals("Móng")) {
                            if (!HienTrangThep.contains(editText.getText().toString().replace("\n", ""))) {
                                HienTrangThep = HienTrangThep.replace("\n", "") + "@" + editText.getText().toString().replace("\n", "");
                                saveDataOnTemplate(HienTrangThep, "HienTrangThep");
                                Toast.makeText(Camera2BasicFragment.this, "Thêm mới hiện trạng thép " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            if (!HienTrangBeTong.contains(editText.getText().toString().replace("\n",""))){
                                HienTrangBeTong = HienTrangBeTong.replace("\n","") +"@"+editText.getText().toString().replace("\n","");
                                saveDataOnTemplate(HienTrangBeTong,"HienTrangBeTong");
                                Toast.makeText(Camera2BasicFragment.this, "Thêm mới hiện trạng bê tông " + editText.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        {   tvHienTrangChitiet.setText(editText.getText());
                            dialog.dismiss();
                        }
                    }
                else Toast.makeText(Camera2BasicFragment.this, "Không được nhập quá 80 ký tự!", Toast.LENGTH_LONG).show();

            }
        });

        //this.initListViewData();
        SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);

        searchViewCT.setIconifiedByDefault(false);

        searchViewCT.setQueryHint("Tìm kiếm");


        searchViewCT.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listview_ht_adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listview_ht_adapter.filter(newText);
                return false;
            }
        });
    }
    public void saveDataOnTemplate(String text,String Name){
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
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = listView.getPositionForView(buttonView);
        if (pos!=ListView.INVALID_POSITION) {
            HienTrang hienTrang = productList.get(pos);
            hienTrang.setActive(isChecked);
            //Toast.makeText(getApplicationContext(),hienTrang.getUserName(), Toast.LENGTH_SHORT).show();
            editText.setText(printSelectedItems());
        }
    }
    public void DialogSetting(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialoghientrang);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog_on = true;

        /** drop down menu*/
        tvTenChiTiet =  dialog.findViewById(R.id.tvChiTiet) ;
        tvTenChiTiet.setText(tvChiTiet.getText());
        tvHienTrangChitiet = dialog.findViewById(R.id.tvHienTrang) ;
        Button btnThoat = dialog.findViewById(R.id.btnthoat);
        Button btnOK = dialog.findViewById(R.id.btnOK);
        btnremoveHT = dialog.findViewById(R.id.btnremoveHT);
        btnremoveHT2 = dialog.findViewById(R.id.btnremoveHT2);

        Arraylist.clear();
        Arraylist.addAll(Arrays.asList(UT.listCauKienTiepDia));
        Arraylist.addAll(Arrays.asList(UT.listMong));
        Arraylist.addAll(Arrays.asList(UT.listPhuKienCot));
        Arraylist.addAll(Arrays.asList(UT.listThanCot));
        Arraylist.addAll(Arrays.asList(UT.listTKheHo));

        ArrayAdapter<String> adapterKT = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.text_view_list_item, Arraylist);
        tvTenChiTiet.setAdapter(adapterKT);
        tvTenChiTiet.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        tvTenChiTiet.setThreshold(1);
        tvTenChiTiet.setDropDownHeight(300);

        tvHienTrangChitiet.setText(tvHienTrang.getText());
        tvHienTrangChitiet.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v) {
                DialogHienTrang(tvHienTrang,listHT,"2");
                return false;
            }
        });
        tvHienTrangChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogHientrangCheckBox();
                /*
                final PopupMenu popupMenu = new PopupMenu(Camera2BasicFragment.this,tvHienTrang);
                DanhGiaHienTrang = DataforPath("DANHGIAHIENTRANG");
                String[] phuluccon = DanhGiaHienTrang.split("--");
                //Log.d("length:", String.valueOf(phuluccon.length));
                //Log.d("read 1:",phuluccon[1]);
                for (int j = 1; j < phuluccon.length; j = j + 1) {
                    if (j==1||j==2||j==3)
                    {
                        String[] phuluccongtac = phuluccon[j].split("_");
                        Log.d("Tên hạng mục:", phuluccongtac[0]);
                        SubMenu sub3 = popupMenu.getMenu().addSubMenu(phuluccongtac[0].toString());
                        for (int i = 1; i < phuluccongtac.length; i = i + 2) {
                            Log.d("Tên công tác:", phuluccongtac[i]);
                            sub3.add(0, i - 1, i - 1, phuluccongtac[i].toString());
                        }
                    }
                }
                popupMenu.getMenu().add("Khác");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_SHORT).show();
                        if (item.getTitle().equals("Bê tông") || item.getTitle().equals("Thép") || item.getTitle().equals("Khác")|| item.getTitle().equals("Nền đất")|| item.getTitle().equals("Cấu kiện")) {
                            if ( item.getTitle().equals("Khác")) {
                                DialogHienTrang(tvHienTrang,listHT,"2");
                            }
                        }

                        else
                        {
                            listHT.add(item.getTitle().toString());
                        }
                        String HT = "";
                        tvHienTrang.clearComposingText();
                        for (int i = 0; i < listHT.size(); i++) {
                            if (i == 0)
                                HT=HT + listHT.get(i);
                            else
                                HT=HT + ", " + listHT.get(i);
                        }
                        tvHienTrang.setText(HT);
                        return false;
                    }
                });
                popupMenu.show(); */
            }
        });

        /** xóa hiện trạng đã tạo*/
        btnremoveHT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*final PopupMenu popupMenu = new PopupMenu(Camera2BasicFragment.this,btnremoveHT);
                for(String s : listHT)
                {
                    popupMenu.getMenu().add(s);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        listHT.remove(menuItem.getTitle());
                        tvHienTrang.setText("");
                        for (int i=0;i<listHT.size();i++)
                        {
                            if (i==0)
                                tvHienTrang.setText(tvHienTrang.getText() + listHT.get(i));
                            else
                                tvHienTrang.setText(tvHienTrang.getText() + ", " + listHT.get(i));
                        }
                        return false;
                    }
                });
                popupMenu.show();*/
                AlertDialog.Builder builder = new AlertDialog.Builder(Camera2BasicFragment.this);
                builder.setTitle("Bạn có muốn xoá hiện trạng này không?");
                // add the buttons
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvTenChiTiet.setText("");                    }
                });
                builder.setNegativeButton("Không", null);
                // create and show the alert dialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        });
        /** xóa hiện trạng đã tạo*/
        btnremoveHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*final PopupMenu popupMenu = new PopupMenu(Camera2BasicFragment.this,btnremoveHT);
                for(String s : listCT)
                {
                    popupMenu.getMenu().add(s);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        listCT.remove(menuItem.getTitle());
                        tvChiTiet.setText("");
                        for (int i=0;i<listCT.size();i++)
                        {
                            if (i==0)
                                tvChiTiet.setText(tvChiTiet.getText() + listCT.get(i));
                            else
                                tvChiTiet.setText(tvChiTiet.getText() + ", " + listCT.get(i));
                        }

                        return false;
                    }
                });
                popupMenu.show();*/
                AlertDialog.Builder builder = new AlertDialog.Builder(Camera2BasicFragment.this);
                builder.setTitle("Bạn có muốn xoá hiện trạng này không?");
                // add the buttons
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvHienTrangChitiet.setText("");                    }
                });
                builder.setNegativeButton("Không", null);
                // create and show the alert dialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvChiTiet.setText(tvTenChiTiet.getText());
                if (tvTenChiTiet.getText().toString().contains("Móng"))
                {
                    String tencaukien = tvTenChiTiet.getText().toString().split(" Móng")[0].replace(" đầu dưới","").replace(" đầu trên","");
                    tvHienTrang.setText(tvHienTrangChitiet.getText().toString().replace(tencaukien,""));
                }
                else if (tvTenChiTiet.getText().toString().contains("Tầng"))
                {
                    String tencaukien = tvTenChiTiet.getText().toString().split(" Tầng")[0].replace(" đầu dưới","").replace(" đầu trên","");
                    tvHienTrang.setText(tvHienTrangChitiet.getText().toString().replace(tencaukien,""));
                }
                else if (tvTenChiTiet.getText().toString().contains("Bu lông neo"))
                {
                    tvHienTrang.setText(tvHienTrangChitiet.getText().toString().replace("Bu lông","").replace("Bu lông neo",""));
                }
                else
                {
                    tvHienTrang.setText(tvHienTrangChitiet.getText().toString());
                }

                if(tvHienTrangChitiet.getText().toString().trim().equals(""))
                {
                    if(switchLiveCam.isChecked()) switchLiveCam.setChecked(false);
                }
                dialog_on = false;
                dialog.dismiss();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchLiveCam.isChecked()) switchLiveCam.setChecked(false);
                dialog_on=false;
                dialog.dismiss();
            }
        });
    }


}
