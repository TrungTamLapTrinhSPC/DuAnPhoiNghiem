package com.example.chirag.googlesignin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Main4Activity extends AppCompatActivity {
    private MultiLevelListView listView;
    ImageButton btnHome,btnBack;

    private List<Multip_list> productList;
    List<String> listFile = new ArrayList<String>();
    Integer[] tt = new Integer[100];
    String[] ArrayString = new String[100];
    int lengthArray;
    private File mediaStorageDir,hangmucStorageDir,congtacStorageDir;
    private String tenHM,tenTram,bien,biendau,tenhm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
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
                Intent intent = new Intent(Main4Activity.this, MenuTramActivity.class);
                startActivity(intent);
            }
        });
        listView = (MultiLevelListView)findViewById(R.id.listView);
        listView.setAdapter(this.getHorizontalScrollableItemAdapter());

    }

    // Returns an Adapter for populating the top container ListView.
    // Each item from the adapter are a HorizontalScrollableItem, since the items themselves will
    // be presenting a child horizontal ListView.
    private HorizontalScrollableItemAdapter getHorizontalScrollableItemAdapter() {
        List<HorizontalScrollableItem> items = new ArrayList<>();
        // Tên hạng mục
        try {
            Intent intent = getIntent();
            tenTram = intent.getStringExtra("TenTram");
            // Log.d("loi",tenTram);
            mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "GIAMSAT");
            mediaStorageDir = new File(mediaStorageDir, tenTram);/// lấy link vào hang mục
            int ii = 0;
            File[] tenhangmuc = mediaStorageDir.listFiles();
            for (File file : tenhangmuc) {
                if (file.getName().split("\\.").length != 1) {
                    //Log.d("folder hạng muc",file.getName());
                    ArrayString[ii] = file.getName();
                    //Log.d("chuoi: ",ArrayString[i].toString());
                    ii++;
                }
            }

            lengthArray = ii - 1;
            sapxep(ArrayString);
            for (int i = 0; i <= lengthArray; i++) {
                items.add(getHorizontalScrollableItem(i, ArrayString[i]));
            }

        }
        catch (Exception e){
            AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
            builder.setTitle("Lỗi Tạo Hạng mục!");
            builder.setNegativeButton("OK", null);
            // create and show the alert dialog
            AlertDialog dialog1 = builder.create();
            dialog1.show();}
        final HorizontalScrollableItemAdapter adapter = new HorizontalScrollableItemAdapter(this,
                R.layout.layout_containing_horizontal_listview,
                items.toArray(new HorizontalScrollableItem[items.size()]));
        return adapter;
    }

    // Creates a HorizontalScrollableItem. Each of these items would also contain an adapter for
    // populating the horizontal ListView with vertical ListViews.
    private HorizontalScrollableItem getHorizontalScrollableItem(int num,String stringHM) {
        List<VerticalScrollableItem> items = getVerticalScrollableItems(num,stringHM);

        VerticalScrollableItemAdapter adapter = new VerticalScrollableItemAdapter(R.layout.layout_containing_vertical_listview, items, listView);

        // Each HorizontalScrollableItem also contains an adapter
        return new HorizontalScrollableItem(adapter,stringHM);

    }

    public Uri GetUriImagtoPath(File BitmapStorageDir)
    {
        Uri uriImage = null;
        try
        {
            if (BitmapStorageDir.exists())
            {
                        if (BitmapStorageDir.getPath().contains(".jpg"))
                        {
                            String[] Check = BitmapStorageDir.getName().split("--");
                            if(Check[1].equals("1"))
                            {
                                /*//LẤY ẢNH BỊ NẶNG
                                Bitmap myBitmap = BitmapFactory.decodeFile(file.getPath());
                                bytearrayoutputstream = new ByteArrayOutputStream();
                                myBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytearrayoutputstream);
                                BYTE = bytearrayoutputstream.toByteArray();
                                Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);
                                bitmap =bitmap2;*/
                                //CÁCH 2
                                uriImage =Uri.parse(BitmapStorageDir.getPath());
                            }
                        }
                        else
                        {
                            uriImage= Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ico_camera);
                            BitmapStorageDir.delete();
                        }
                    }
        }
        catch (Exception e)
        {

                uriImage= Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ico_camera);

        }
        return uriImage;
    }

    private List<VerticalScrollableItem> getVerticalScrollableItems(int num,String tenHangMuc) {

        List<VerticalScrollableItem> items = new ArrayList<>();
        ///Thêm ảnh
        productList = new ArrayList<>();
        /// Thêm Công tác
        Intent intent = getIntent();
        tenTram = intent.getStringExtra("TenTram");
        mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"GIAMSAT");
        mediaStorageDir = new File(mediaStorageDir,tenTram);/// lấy link vào hang mục
        hangmucStorageDir = new File(mediaStorageDir,tenHangMuc);/// lấy link vào hang mục
        if ( hangmucStorageDir.exists()){
            listFile.clear();
            File[] tencongtac = hangmucStorageDir.listFiles();
            try {
                for (File TCT : tencongtac) {
                    if (!TCT.getName().contains("Hình ảnh khác")) {
                        congtacStorageDir = new File(TCT.getPath());
                        File[] tenAnh = congtacStorageDir.listFiles();
                        List<Multip_list> productList2 = new ArrayList<>();
                        //Log.d("folder hạng muc", String.valueOf(tenAnh.length));
                        if (tenAnh.length > 0) {
                            for (File TA : tenAnh)
                            {
                                //Log.d("folder hạng muc",TA.getPath());
                                if (TA.getPath().contains(".jpg"))
                                {
                                   /* String[] Check = TA.getName().split("--");
                                    if (Check[1].equals("1")) {*/
                                    /*ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                                    Bitmap myBitmap = BitmapFactory.decodeFile(TA.getPath());
                                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytearrayoutputstream);
                                    byte[] BYTE = bytearrayoutputstream.toByteArray();
                                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE, 0, BYTE.length);
                                    Bitmap bitmap = BITMAP_RESIZER(bitmap2, 250, 250);*/
                                        Uri uri = GetUriImagtoPath(TA);
                                        productList2.add(new Multip_list(uri));
                                    /*} else {
                                        //productList2.add(new Multip_list(icon));
                                    }*/
                                }
                            }
                            productList = productList2;
                            Multip_list_Adapter gridviewAdapter = new Multip_list_Adapter(this, R.layout.mul_list_item, productList);
                            items.add(new VerticalScrollableItem(gridviewAdapter, TCT.getName()));

                        } else if (tenAnh.length == 0) {
                            List<Multip_list> productList3 = new ArrayList<>();

                            Uri uriImage= Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ico_camera);

                            productList3.add(new Multip_list(uriImage));
                            productList = productList3;
                            Multip_list_Adapter gridviewAdapter = new Multip_list_Adapter(this, R.layout.mul_list_item, productList);
                            items.add(new VerticalScrollableItem(gridviewAdapter, TCT.getName()));
                        }
                    }
                }

            }
            catch (Exception e){
                AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                builder.setTitle("Lỗi tạo hình ảnh!");
                builder.setNegativeButton("OK", null);
                // create and show the alert dialog
                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        }


        return items;
    }

    public void sapxep(String[] chuoi){

        ArrayList<Student> ar = new ArrayList<Student>();
        for(int i=0;i<=lengthArray;i++)
        {
            String[] tt1 = chuoi[i].split("\\.",2);
            try
            {
                tt[i] = Integer.parseInt(tt1[0]);
                ar.add(new Main4Activity.Student(tt[i], tt1[1]));
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), "Hãy thêm số thứ tự đằng trước!!!", Toast.LENGTH_SHORT).show();}

        }

        Collections.sort(ar, new Sortbyroll());
        //System.out.println("\nSorted by rollno");
        for (int y=0; y<ar.size(); y++)
            //System.out.println(ar.get(y));
            ArrayString[y] = ar.get(y).toString();
    }
    class Student
    {
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

    class Sortbyroll implements Comparator<Student>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Main4Activity.Student a, Main4Activity.Student b)
        {
            return a.rollno - b.rollno;
        }
    }
    private void DialogZoom(Bitmap bitmap){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_zoom);
        dialog.show();
        ImageView imageView =(ImageView) dialog.findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
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


}
