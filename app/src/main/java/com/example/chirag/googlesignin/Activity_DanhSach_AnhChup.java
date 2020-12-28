package com.example.chirag.googlesignin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

public class Activity_DanhSach_AnhChup extends AppCompatActivity {
    ListView listview;
    ImageButton btnBack;
    List<DoiTuong_AnhChup> list_AnhChup = new ArrayList<>();
    Adapter_DoiTuong_AnhChup adapter_doiTuong_AnhChup;
    File pathHinhAnh;
    String MaTram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_anhchup);
        getWindow().setSoftInputMode(WindowManager.
                LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        AnhXa();
        SuKien();
        NhanBien();
        SettupListView();
    }

    private void SettupListView()
    {
        list_AnhChup.clear();//Hoan thay đổi
        if (pathHinhAnh.isDirectory())
        {
            File[] listImageName = pathHinhAnh.listFiles();
            for (File ImgName : listImageName){
                if(ImgName.isDirectory())
                {
                    File[] Img  = ImgName.listFiles();
                    if(Img.length!=0)
                    {
                        String NgaySua = SPC.getLastModified(Img[0]);
                        Uri uriImage = Uri.parse(Img[0].getPath());
                        list_AnhChup.add(new DoiTuong_AnhChup(ImgName.getName(),uriImage,NgaySua));
                    }
                    else
                    {
                        list_AnhChup.add(new DoiTuong_AnhChup(ImgName.getName(),null,"Chưa chụp"));
                    }
                }
            }
        }

        /**HIỂN THỊ RA MÀN HÌNH*/
        adapter_doiTuong_AnhChup = new Adapter_DoiTuong_AnhChup(list_AnhChup, Activity_DanhSach_AnhChup.this,R.layout.item_anh_chup);
        listview.setAdapter(adapter_doiTuong_AnhChup);


    }

    private void NhanBien() {
        Intent intent =getIntent();//Nhận biến truyền từ trang danh sách cột
        MaTram= intent.getStringExtra("MaTram");
        pathHinhAnh= new File(SPC.pathDataApp_PNDT,MaTram+ SPC.DuongDanThuMucHinhAnh);
    }

    private void SuKien()
    {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(Activity_DanhSach_AnhChup.this,Activity_DanhSach_Cot.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void AnhXa() {
        listview = findViewById(R.id.listview_AnhChup);
        btnBack = findViewById(R.id.btnBack);
    }

}
