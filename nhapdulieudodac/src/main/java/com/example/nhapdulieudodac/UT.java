package com.example.nhapdulieudodac;
/**
 * Copyright 2015 Sean Janson. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

final class UT {  private UT() {}
  private static final String L_TAG = "_X_";
  static final String MYROOT = "Tên folder";
  static final String MIME_TXT = "text/plain";
  static final String[] listCauKienTiepDia = {"Thoát sét cho kim thu sét","Thoát sét cho thiết bị treo trên cột","Thoát sét cho chân cột","Thoát sét cho cáp thép dây co","Thoát sét cho phòng máy"};
  static final String[] listPhuKienCot = {"Bu lông neo","Tăng đơ móng M1","Tăng đơ móng M2","Tăng đơ móng M3","Tăng đơ móng M4","Ma ní móng M1","Ma ní móng M2","Ma ní móng M3","Ma ní móng M4","Khoá cáp tầng dây 1","Khoá cáp tầng dây 2","Khoá cáp tầng dây 3","Khoá cáp tầng dây 4","Vòng ốp móc dây co và bu lông vòng ốp dây co","Dây co","Gá chống xoay","Gá treo Anten (Viba)","Mặt bích bịt đầu cột","Kim thu sét"};
  static final String[] listMong = {"Móng M0","Móng M1","Móng M2","Móng M3","Móng M4","Móng M5","Móng M6"};
  static final String[] listThanCot = {"Đốt D1","Đốt D2","Đốt D3","Đốt D4","Mốt D5","Đốt D6","Đốt D7","Đốt D8","Đốt D9","Đốt D10","Đốt D11","Đốt D12"};
  static final String[] listTKheHo= {"Đốt D1-2","Đốt D2-3","Đốt D3-4","Đốt D4-5","Đốt D5-6","Đốt D6-7","Đốt D7-8","Đốt D8-9","Đốt D9-10","Đốt D10-11","Đốt D11-12","Đốt D12-13"};
  static final String DeXuatBeTong = "Không.@Gia cố móng đảm bảo an toàn.@Đổ bê tông chống cỏ mọc cho móng.@Dùng vữa xi măng mác cao trát vào vị trí bị rỗ, hở cốt thép.";
  static final String DeXuatThep ="Siết chặt lại đai ốc đảm bảo chắc chắn, kín khít.@Vệ sinh, sơn kẽm lạnh cho cấu kiện bị han rỉ.@Vệ sinh, bôi mỡ bảo dưỡng cho cấu kiện.@Bổ sung ê cu thiếu cho cấu kiện.@Gia cố móc co.@Vệ sinh sạch sẽ móc co.@Thay thế dây thoát sét bị han rỉ nặng.@Thay thế dây thoát sét bị đứt.@Bổ sung dây thoát sét.@Hàn mối hàn cadwell cho dây thoát sét bị đứt.@Gia cố thanh giằng bị đứt, gãy.@Thay thế đốt cột bị han rỉ nặng, mục ruỗng.@Siết chặt lại đai ốc đảm bảo chắc chắn, kín khít.@Vệ sinh, sơn kẽm lạnh cho cấu kiện bị han rỉ.@Bổ sung ê cu thiếu cho cấu kiện.@Thay thế cấu kiện bị han rỉ nặng, nứt vỡ.@Thay thế dây co bị đứt sợi, nối.@Chèn bản đệm thép vào khe hở.";
  static final String HienTrangBeTong ="Bê tông cổ móng có hiện tượng nứt vỡ, bong tróc.@Bê tông cổ móng không hiện tượng nứt vỡ, bong tróc.@Nền đất có có hiện tượng lún, chuyển dịch, nứt hay xói mòn.@Nền đất không có hiện tượng lún, chuyển dịch, nứt hay xói mòn.@Nền đất xung quanh đã đổ bê tông chống cỏ mọc.@Nền đất xung quanh chưa đổ bê tông chống cỏ mọc.@Mặt cánh móng cao hơn mặt đất xung quanh.@Mặt cánh móng thấp hơn hơn mặt đất xung quanh.@Mặt cánh móng cao hơn mặt đất xung quanh.@Bê tông dầm có có hiện tượng  nứt vỡ, bong tróc.@Bê tông dầm không có hiện tượng  nứt vỡ, bong tróc.@Bê tông có bị rỗ/hở cốt thép.@Bê tông không bị rỗ/hở cốt thép.";
  static final String HienTrangThep = "Các đai ốc đã xiết chặt đảm bảo độ kín khít.@Các đai ốc chưa xiết chặt đảm bảo độ kín khít.@Bu lông neo có han rỉ, cong vênh hay nứt gãy.@Bu lông neo không han rỉ, cong vênh hay nứt gãy.@Bu lông neo đủ ê cu.@Bu lông neo thiếu ê cu.@Bu lông neo đủ mỡ bảo dưỡng.@Bu lông neo thiếu mỡ bảo dưỡng.@Các cấu kiện không có hiện tượng cong vênh, rạn nứt hay han rỉ.@Móc co có xuất hiện vết nứt, cong vênh, han rỉ.@Móc co không xuất hiện vết nứt, cong vênh, han rỉ.@Bề mặt các móc co có bẩn, rêu mốc.@Bề mặt các móc co không bẩn, rêu mốc.@Không có hiện tượng đứt, han gỉ.@Không không hiện tượng đứt, han gỉ.@Không có hiện tượng bong tróc vỏ bọc, hay đứt gãy.@Không có hiện tượng han rỉ, hay đứt gãy.@Không có dây thoát sét cho kim thu sét.@Không bị han rỉ.@Thanh giằng có đứt gãy, có bị han rỉ.@Thanh giằng không đứt gãy, không bị han rỉ.@Thanh cánh có đứt gãy, có bị han rỉ.@Thanh cánh không đứt gãy, không bị han rỉ.@Mặt bích chân cột có bị han rỉ.@Mặt bích chân cột không bị han rỉ.@Thanh giằng cao trình bị đứt gãy.@Thanh cánh đốt bị han rỉ nặng, mục ruỗng.@Cấu kiện có hiện tượng cong vênh, nứt gãy.@Cấu kiện không có hiện tượng cong vênh, nứt gãy.@Có bị han rỉ.@Tăng đơ có hiện tượng cong vênh, nứt gãy.@Tăng đơ không có hiện tượng cong vênh, nứt gãy.@Tăng đơ có bị han rỉ.@Tăng đơ không bị han rỉ.@Ma ní có hiện tượng cong vênh, nứt gãy.@Ma ní không có hiện tượng cong vênh, nứt gãy.@Ma ní có bị han rỉ.@Ma ní không bị han rỉ.@Khóa cáp có hiện tượng cong vênh, nứt gãy.@Khóa cáp không có hiện tượng cong vênh, nứt gãy.@Khóa cáp có bị han rỉ.@Khóa cáp không bị han rỉ.@Khóa cáp đầy đủ đai ốc.@Bong tróc lớp sơn phủ.@Không có lớp sơn phủ.@Bản định vị có hiện tượng han rỉ.@Đủ mỡ bảo dưỡng.@Khe hở đã được chêm đệm vòng móng ngựa.@Khe hở lắp ghép chân C1 không đảm bảo.@Khe hở lắp ghép chân C2 không đảm bảo.@Khe hở lắp ghép chân C3 không đảm bảo.@Đủ ê cu, đủ mỡ bảo dưỡng.@Thiếu ê cu, thiếu mỡ bảo dưỡng.@Bu lông liên kết có đầy đủ ê cu, có bị han rỉ, đủ mỡ bảo dưỡng.@Bu lông liên kết không đầy đủ ê cu, không bị han rỉ, thiếu mỡ bảo dưỡng.@Dây co có bị đứt sợi.@Dây co không bị đứt sợi.@Dây co có bị han rỉ.@Dây co không bị han rỉ.@Khe hở lắp ghép chân có đảm bảo.@Khe hở lắp ghép chân không đảm bảo.@Bu lông nối đốt đủ ê cu, đủ mỡ bảo dưỡng.@Bu lông nối đốt thiếu ê cu, thiếu mỡ bảo dưỡng.";

  static final String TITL = "titl";
  static final String GDID = "gdid";
  static final String LoaiAnten ="--Kích thước móng"+
          "_4G_"+
          "_3G 2100_"+
          "_Triple band gain thấp_"+
          "_Dual band gain cao_"+
          "_Dual band gain thấp_"+
          "_Twinbeam_"+
          "_RRU_"+
          "_FEEDER 7/8_"+
          "_RF_"+
          "_2G 900_"+
          "_2G 1800_"+
          "_2G 1800_"+
          "_Diplexer_"+
          "--Kích thước của bạn";
  static final String KichThuocAnten="--Kích thước anten"+
          "_147x275x86_"+
          "_1334x261x146_"+
          "_1214x155x70_"+
          "_1384x261x146_"+
          "_2533x261x146_"+
          "_1334x261x146_"+
          "_1390x301x181_"+
          "_425x300x190_"+
          "_2580x262x116_"+
          "_1314x155x7_"+
          "_425x300x190_"+
          "--Kích thước của bạn";
  static final String TrongLuongAnten="--Trong lượng anten"+
          "_16.8_"+
          "_9.8_"+
          "_23.3_"+
          "_31.2_"+
          "_20.3_"+
          "_21.5_"+
          "_15_"+
          "_25.3_"+
          "--Kích thước của bạn";
  static final String KichThuocMong ="--Kích thước móng"+
          "_0.3x0.3x0.58_"+
          "_0.5x0.5x0.58_"+
          "_0.4x0.4x0.58_"+
          "_M12-4_"+
          "_M18-3_"+
          "_M22-3_"+
          "_0.3.x0.3_"+
          "_0.4.x0.4_"+
          "_0.6.x0.6_"+
          "_Cáp đồng trần C50_"+
          "_Dây đồng bọc M35_"+
          "_Cáp thép mạ kẽm D8_"+
          "_Cáp thép mạ kẽm D10_"+
          "_Cáp thép mạ kẽm D12_"+
          "_Cáp thép mạ kẽm D18_"+
          "_Cáp thép mạ kẽm D20_"+
          "_D6_"+
          "_D8_"+
          "_D10_"+
          "_D12_"+
          "_Bu lông M12_"+
          "_Bu lông M16_"+
          "_Bu lông M20_"+
          "_Bu lông M22_"+
          "_0.30x0.30x3.0_"+
          "_0.30x0.30x6.0_"+
          "_0.40x0.40x3.0_"+
          "_0.40x0.40x6.0_"+
          "_D31.8x4_"+
          "--Kích thước của bạn";
  static final String DeXuatMong ="--Đề xuất móng"+
          "_Không_"+
          "_Trát lại bằng vữa xi măng_"+
          "_Bổ sung dây thoát sét_"+
          "_Vệ sinh và bôi mỡ cho ma ní đầu trên_"+
          "_Bổ sung Ecu hãm còn thiếu cho khóa cáp đầu trên_"+
          "_Vệ sinh và bôi mỡ bảo dưỡng cho toàn bộ bu lông_"+
          "_Vệ sinh và bôi mỡ bảo dưỡng cho toàn bộ bu lông_"+
          "_Tháo dỡ_"+
          "--Kích thước của bạn";
  static final String CheDoChup = "1@Off@2@Off@3@Off@4@On@5@dd/mm/yyyy hh:mm@6@On@7@On@8@FHD (1920x1080)@9@On@10@Trung bình";
  static final String HienTrang = "--Thép"+
          "_Han rỉ nhẹ_"+
          "_Han rỉ nặng_"+
          "_Cong vênh_"+
          "_Sơn bị bong tróc_"+
          "_Đứt gãy_"+
          "_Không có mỡ_"+
          "_Khe hở lắp ghép không đảm bảo_"+
          "_Hết mỡ bò_"+
          "_Khô mỡ_"+
          "_Rạn nứt_"+
          "_Thiếu đai ốc_"+
          "_Khác_"+
"--Bê tông" +
          "_Lún_"+
          "_Nứt bề mặt_"+
          "_vỡ vụn_"+
          "_Đốm ố bị ẩm_"+
          "_Bong tróc, tách lớp_"+
          "_Rỗ tổ ong_"+
          "_Rỉ cốt thép_"+
          "_Khác_"+
"--Nền đất" +
          "_Lún nền đất_"+
          "_Dịch chuyển nền đất_"+
          "_Xói mòn_"+
          "_Chưa đổ bê tông_"+
          "_Khác_"+
"--Cấu kiện"+
          "_Bulong neo_"+
          "_Thanh Cánh_"+
          "_Thanh Giằng_"+
          "_Tăng đơ_"+
          "_Ma ní_"+
          "_Móc co_"+
          "_Kim thu sét_"+
          "_Gá treo anten_"+
          "_Gá chống xoay_"+
          "_Dây thoát sét kim thu sét_"+
          "_Dây thoát sét thiết bị_"+
          "_Dây thoát sét chân cột_"+
          "_Dây thoát sét dây co_"+
          "_Dây thoát sét phòng máy_"+
          "_Bản ốp_"+
          "_Khóa cáp_"+
          "_Bulong nối đốt_"+
          "_Bulong kiên kết thanh giằng_"+
          "_Khác_"+
"--Móng"+
        "_Móng M0_"+
        "_Móng M1_"+
        "_Móng M2_"+
        "_Móng M3_"+
        "_Móng M4_"+
        "_Móng M5_"+
        "_Móng M6_"+
        "_Móng M7_"+
        "_Móng M8_"+
          "_Móng M9_"+
          "_Móng M10_"+
          "_Móng M11_"+
          "_Móng M12_"+
"--Đốt"+
        "_Đốt D1_"+
        "_Đốt D2_"+
        "_Đốt D3_"+
        "_Đốt D4_"+
        "_Đốt D5_"+
        "_Đốt D6_"+
        "_Đốt D7_"+
        "_Đốt D8_"+
        "_Đốt D9_"+
        "_Đốt D10_"+
        "_Đốt D11_"+
        "_Đốt D12_"+
          "_D1-2_"+
          "_D2-3_"+
          "_D3-4_"+
          "_D4-5_"+
          "_D5-6_"+
          "_D6-7_"+
          "_D7-8_"+
          "_D8-9_"+
          "_D9-10_"+
          "_D10-11_"+
          "_D11-12_"+
"--Tầng co"+
          "_T1_"+
          "_T2_"+
          "_T3_"+
          "_T4_"+
          "_T5_"+
          "_T6_"+
          "_T7_"+
          "_T8_"+
          "_T9_"+
          "_T10_"+
          "_T11_"+
        "_Khác_"+
"--Hiện trạng của bạn" +
    "_Vòng ốp móc dây co: không có hiện tượng cong vênh rạn nứt hay han gỉ_" +
    "_Bu lông vòng ốp dây co: đầy đủ ecru hãm và vòng đệm. Có hiện tượng han gỉ nhẹ, khô mỡ_" +
    "_Cáp thép dây co không có hiện tượng han gỉ, đứt gãy_"

          ;

  static final String[] tenThanhpho = new String[] {"Hà Nội","Hồ Chí Minh","An Giang","Bà Rịa – Vũng Tàu","Bạc Liêu"
          ,"Bắc Giang","Bắc Kạn","Bắc Ninh","Bến Tre","Bình Dương","Bình Định","Bình Phước","Bình Thuận","Cao Bằng","Cà Mau","Cần Thơ","Hải Phòng","Đà Nẵng","Gia Lai","Hòa Bình","Hà Giang","Hà Nam","Hà Tĩnh","Hưng Yên","Hải Dương","Hậu Giang","Điện Biên","Đắk Nông","Đồng Nai","Đồng Tháp"
          ,"Khánh Hòa","Kiên Giang","Kon Tum","Lai Châu","Long An","Lào Cai","Lạng Sơn","Nam Định","Nghệ An","Ninh Bình","Ninh Thuận","Phú Thọ","Phú Yên","Quảng Bình","Quảng Nam","Quảng Ngãi","Quảng Ninh","Quảng Trị","Sóc Trăng","Sơn La","Thanh Hóa","Thái Bình","Thái Nguyên","Thừa Thiên – Huế","Tiền Giang"
          ,"Trà Vinh","Tuyên Quang","Tây Ninh","Vĩnh Long","Vĩnh Phúc","Yên Bái"};
  private static final String TITL_FMT = "yyMMdd-HHmmss";
  static final String TramDayCo = "--1.Hình ảnh tổng thể cột anten" +
          "_Hình ảnh biển nhà trạm_" +
          "_Hình ảnh mặt đứng cột anten_" +
          "_Hình ảnh tổng thể mặt bằng trạm_" +
          "_Hình ảnh thiết bị treo trên cột_" +
          "_Hình ảnh khác tổng thể cột anten_" +

          "--2.Công tác kiểm tra khe hở cấu kiện lắp ghép" +
          "_Công tác chuẩn bị kiểm tra khe hở cấu kiện lắp ghép_" +
          "_Công tác kiểm tra khe hở giữa cấu kiện lắp ghép_" +
          "_Hình ảnh khác công tác kiểm tra khe hở lắp ghép_"+
          "--3.Công tác đo lực căng trong dây co" +
          "_Chụp màn hình cài đặt máy đo_" +
          "_Công tác đo lực căng trong dây co_" +
          "_Hình ảnh khác công tác đo lực căng trong dây co_" +

          "--4.Công tác kiểm tra lực siết khóa cáp" +
          "_ Công tác kiểm tra lực siết ê-cu khóa cáp đầu dưới_" +
          "_ Công tác kiểm tra lực siết ê-cu khóa cáp đầu trên_" +
          "_Hình ảnh khác công tác kiểm tra lực siết khóa cáp_" +

          "--5.Công tác kiểm tra cường độ bê tông móng" +
          "_Công tác chuẩn bị kiểm tra cường độ bê tông móng_" +
          "_Công tác kiểm tra cường độ bê tông móng_" +
          "_Hình ảnh khác công tác kiểm tra cường độ bê tông móng_"+

          "--6.Công tác đo điện trở nối đất hệ thống chống sét" +
          "_Công tác chuẩn bị đo điện trở nối đất hệ thống chống sét_" +
          "_Công tác đo điện trở nối đất hệ thống chống sét_" +
          "_Hình ảnh khác công tác đo điện trở nối đất hệ thống chống sét_"+

          "--7.Công tác đo nghiêng cột anten" +
          "_Công tác chuẩn bị kiểm tra độ thẳng đứng của cột anten_" +
          "_Công tác đo độ thẳng đứng của cột anten_" +
          "_Hình ảnh khác công tác đo nghiêng cột anten_"+

          "--8.Công tác trèo cao(kiểm tra đường hàn, lực siết ê-cu,..)" +
          "_Công tác chuẩn bị trèo cao_" +
          "_Thao tác trèo cao tại chân cột_" +
          "_Thao tác trèo cao tại giữa cột_" +
          "_Thao tác trèo cao tại đỉnh cột_" +
          "_Hình ảnh khác công tác trèo cao_" +

          "--9.Công tác đo kích thước cấu kiện cột anten" +
          "_Chuẩn bị đo kích thước cấu kiện cột anten_" +
          "_Đo kích thước móng_" +
          "_Đo kích thước tiết diện bu lông neo_" +
          "_Đo kích thước thanh cánh_" +
          "_Đo kích thước thanh giằng_" +
          "_Đo kích thước tăng đơ_" +
          "_Đo kích thước ma ní_" +
          "_Đo kích thước khóa cáp_" +
          "_Đo kích thước dây cơ_" +
          "_Hình ảnh khác công tác đo kích thước cấu kiện cột anten_" +
          "--10.Hình ảnh dị tật bất thường";
  static final String TramTuDung = "--1.Hình ảnh tổng thể cột anten" +
          "_Hình ảnh biển nhà trạm_" +
          "_Hình ảnh mặt đứng cột anten_" +
          "_Hình ảnh tổng thể mặt bằng trạm_" +
          "_Hình ảnh thiết bị treo trên cột_" +
          "_Hình ảnh khác tổng thể cột anten_" +

          "--2.Công tác kiểm tra khe hở cấu kiện lắp ghép" +
          "_Công tác chuẩn bị kiểm tra khe hở cấu kiện lắp ghép_" +
          "_Công tác kiểm tra khe hở giữa cấu kiện lắp ghép_" +
          "_Hình ảnh khác công tác kiểm tra khe hở lắp ghép_"+

          "--3.Công tác kiểm tra cường độ bê tông móng" +
          "_Công tác chuẩn bị kiểm tra cường độ bê tông móng_" +
          "_Công tác kiểm tra cường độ bê tông móng_" +
          "_Hình ảnh khác công tác kiểm tra cường độ bê tông móng_"+

          "--4.Công tác đo điện trở nối đất hệ thống chống sét" +
          "_Công tác chuẩn bị đo điện trở nối đất hệ thống chống sét_" +
          "_Công tác đo điện trở nối đất hệ thống chống sét_" +
          "_Hình ảnh khác công tác đo điện trở nối đất hệ thống chống sét_"+

          "--5.Công tác đo nghiêng cột anten" +
          "_Công tác chuẩn bị kiểm tra độ thẳng đứng của cột anten_" +
          "_Công tác đo độ thẳng đứng của cột anten_" +
          "_Hình ảnh khác công tác đo nghiêng cột anten_"+

          "--6.Công tác trèo cao(kiểm tra đường hàn, lực siết ê-cu,..)" +
          "_Công tác chuẩn bị trèo cao_" +
          "_Thao tác trèo cao tại chân cột_" +
          "_Thao tác trèo cao tại giữa cột_" +
          "_Thao tác trèo cao tại đỉnh cột_" +
          "_Hình ảnh khác công tác trèo cao_" +

          "--7.Công tác đo kích thước cấu kiện cột anten" +
          "_Hình ảnh chuẩn bị đo kích thước cấu kiện cột anten_" +
          "_Đo kích thước tiết diện bu lông neo_" +
          "_Đo kích thước thanh cánh_" +
          "_Đo kích thước thanh giằng_" +
          "_Đo kích thước tăng đơ_" +
          "_Đo kích thước ma ní_" +
          "_Đo kích thước khóa cáp_" +
          "_Hình ảnh khác công tác đo kích thước cấu kiện cột anten_" +

          "--8.Hình ảnh dị tật bất thường";
  private static SharedPreferences pfs;
  static Context acx;
  static void init(Context ctx) {
    acx = ctx.getApplicationContext();
    pfs = PreferenceManager.getDefaultSharedPreferences(acx);
  }

  static class AM { private AM(){}
    private static final String ACC_NAME = "account_name";
    private static String mEmail = null;

    static void setEmail(String email) {
      UT.pfs.edit().putString(ACC_NAME, (mEmail = email)).apply();
    }
    static String getEmail() {
      return mEmail != null ? mEmail : (mEmail = UT.pfs.getString(ACC_NAME, null));
    }
  }

  static ContentValues newCVs(String titl, String gdId, String mime) {
    ContentValues cv = new ContentValues();
    if (titl != null) cv.put(TITL, titl);
    if (gdId != null) cv.put(GDID, gdId);
    return cv;
  }

  private static File cchFile(String flNm) {
    File cche = UT.acx.getExternalCacheDir();
    return (cche == null || flNm == null) ? null : new File(cche.getPath() + File.separator + flNm);
  }
  static File str2File(String str, String name) {
    if (str == null) return null;
    byte[] buf = str.getBytes();
    File fl = cchFile(name);
    if (fl == null) return null;
    BufferedOutputStream bs = null;
    try {
      bs = new BufferedOutputStream(new FileOutputStream(fl));
      bs.write(buf);
    } catch (Exception e) { le(e); }
    finally {
      if (bs != null) try {
        bs.close();
      } catch (Exception e) { le(e); }
    }
    return fl;
  }
    static byte[] file2Bytes(File file) {
        if (file != null) try {
            return is2Bytes(new FileInputStream(file));
        } catch (Exception e) {}
        return null;
    }
  static byte[] is2Bytes(InputStream is) {
    byte[] buf = null;
    BufferedInputStream bufIS = null;
    if (is != null) try {
      ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
      bufIS = new BufferedInputStream(is);
      buf = new byte[4096];
      int cnt;
      while ((cnt = bufIS.read(buf)) >= 0) {
        byteBuffer.write(buf, 0, cnt);
      }
      buf = byteBuffer.size() > 0 ? byteBuffer.toByteArray() : null;
    } catch (Exception ignore) {}
    finally {
      try {
        if (bufIS != null) bufIS.close();
      } catch (Exception ignore) {}
    }
    return buf;
  }

  static String time2Titl(Long milis) {       // time -> yymmdd-hhmmss
    Date dt = (milis == null) ? new Date() : (milis >= 0) ? new Date(milis) : null;
    return (dt == null) ? null : new SimpleDateFormat(TITL_FMT, Locale.US).format(dt);
  }
  static String titl2Month(String titl) {
    return titl == null ? null : ("20" + titl.substring(0, 2) + "-" + titl.substring(2, 4));
  }
  static void saveDataOnCacher(String text,String Name,File file){
    String content = text;

    FileOutputStream outputStream;
    try {
      file = new File(file, Name);
      outputStream = new FileOutputStream(file);
      outputStream.write(content.getBytes());
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  static String DataforPath(File file){
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

  static void CopyFile(File file1,File file2,String nameFile)
  {
    File newf = new File(file1,nameFile);
    //Log.d("Data",newf.getPath());
    String data1 = DataforPath(newf);
    //String[] data = data1.split("@""")
    saveDataOnCacher(data1,nameFile,file2);
  }
  static void le(Throwable ex) {  Log.e(L_TAG, Log.getStackTraceString(ex));  }
  static void lg(String msg) {  Log.d(L_TAG, msg); }
}


