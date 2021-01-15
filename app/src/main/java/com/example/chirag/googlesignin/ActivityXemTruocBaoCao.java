package com.example.chirag.googlesignin;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.chirag.googlesignin.SPC.ThietKeAnten;

public class ActivityXemTruocBaoCao extends AppCompatActivity {
    TableLayout TableXemBaoCao;
    JSONObject jsonObject;
    JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_truoc_bao_cao);

        AnhXa();
        LayDuLieuJson();
    }

    private void LayDuLieuJson() {
        String text = "[" +
                "   {\"TenAnten\":\"Anten1\",\"ChungLoaiThietBi\":\"EricssonRBS\",\"SoMayPhat\":\"2\",\"TongCongSuatPhat1\":\"35.48\",\"TongCongSuatPhat2\":\"45.5\",\"ChungLoaiAnten\":\"PFS\",\"LoaiAnten\":\"Định hướng\",\"DoTangIch\":\"17.5\",\"BangTanHoatDong\":\"1800\",\"DoDaiBucXa\":\"1.35\",\"GocNgang\":\"8\",\"GocPhuongVi\":\"65\",\"DoCaoAnten1\":\"31.65\",\"DoCaoAnten2\":\"33\",\"ChungLoaiJumper\":\"1/2\",\"ChieuDaiJumper\":\"6\",\"SuyHaodBJumper\":\"0\",\"SuyHaoJumper\":\"0\",\"ChungLoaiFeeder\":\"7/8\",\"ChieuDaiFeeder\":\"10\",\"SuyHaodBFeeder\":\"0\",\"SuyHaoFeeder\":\"0\",\"TongSuyHao\":\"2\"},\n" +
                "    {\"TenAnten\":\"Anten2\",\"ChungLoaiThietBi\":\"EricssonRBS\",\"SoMayPhat\":\"2\",\"TongCongSuatPhat1\":\"35.48\",\"TongCongSuatPhat2\":\"45.5\",\"ChungLoaiAnten\":\"PFS\",\"LoaiAnten\":\"Định hướng\",\"DoTangIch\":\"17.5\",\"BangTanHoatDong\":\"1800\",\"DoDaiBucXa\":\"1.35\",\"GocNgang\":\"8\",\"GocPhuongVi\":\"65\",\"DoCaoAnten1\":\"31.65\",\"DoCaoAnten2\":\"33\",\"ChungLoaiJumper\":\"1/2\",\"ChieuDaiJumper\":\"6\",\"SuyHaodBJumper\":\"0\",\"SuyHaoJumper\":\"0\",\"ChungLoaiFeeder\":\"7/8\",\"ChieuDaiFeeder\":\"10\",\"SuyHaodBFeeder\":\"0\",\"SuyHaoFeeder\":\"0\",\"TongSuyHao\":\"2\"},\n" +
                "    {\"TenAnten\":\"Anten3\",\"ChungLoaiThietBi\":\"EricssonRBS\",\"SoMayPhat\":\"2\",\"TongCongSuatPhat1\":\"35.48\",\"TongCongSuatPhat2\":\"45.5\",\"ChungLoaiAnten\":\"PFS\",\"LoaiAnten\":\"Định hướng\",\"DoTangIch\":\"17.5\",\"BangTanHoatDong\":\"1800\",\"DoDaiBucXa\":\"1.35\",\"GocNgang\":\"8\",\"GocPhuongVi\":\"65\",\"DoCaoAnten1\":\"31.65\",\"DoCaoAnten2\":\"33\",\"ChungLoaiJumper\":\"1/2\",\"ChieuDaiJumper\":\"6\",\"SuyHaodBJumper\":\"0\",\"SuyHaoJumper\":\"0\",\"ChungLoaiFeeder\":\"7/8\",\"ChieuDaiFeeder\":\"10\",\"SuyHaodBFeeder\":\"0\",\"SuyHaoFeeder\":\"0\",\"TongSuyHao\":\"2\"},\n" +
                "]";
        try {
            jsonArray = new JSONArray(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int SoAnten = 5;
        int SoHang = ThietKeAnten.size();

        for(int j=3; j <= 9; j++)
        {
            for(int i=0; i <= SoHang-1; i++) {
                TableRow row = (TableRow) (((TableLayout) TableXemBaoCao)).getChildAt(i);
                TextView tv = (TextView) (((TableRow) row)).getChildAt(j);
                    tv.setVisibility(View.GONE);
            }
        }

        for(int j=3; j <= SoAnten; j++)
        {
            try {
                jsonObject = jsonArray.getJSONObject(j-3);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i=0; i <= SoHang-1; i++) {

                    TableRow row = (TableRow) (((TableLayout) TableXemBaoCao)).getChildAt(i);
                    TextView tv = (TextView) (((TableRow) row)).getChildAt(j);
                    try {
                        tv.setText(jsonObject.getString(ThietKeAnten.get(i)));
                        tv.setVisibility(View.VISIBLE);
                        tv.setTextSize(15);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }
    }

    private void AnhXa() {
        TableXemBaoCao = (TableLayout) findViewById(R.id.TableXemTruocBaoCao);
    }

}