package com.example.listcheckboxhientrang;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityHienTrang extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener {
    public static final String TAG = "ListViewExample";
    private Listview_HT_Adapter listview_ht_adapter;
    private List<HienTrang> productList;

    private ListView listView;
    private EditText editText;
    private Button button;
    private ArrayList<String> myList = new ArrayList<String>();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hien_trang);

        listView = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.edittext);


        myList.add("Han rỉ nhẹ");
        myList.add("Sơn bị bong tróc");
        myList.add("Khe hở lắp ghép không đảm bảo");
        myList.add("Hết mỡ bò");
        myList.add("Thiếu đai ốc");
        myList.add("Bong tróc, tách lớp");
        myList.add("Nứt bề mặt");
        DialogHientrang();
        /*
        getProductList(myList);

        //
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHientrang();
               // printSelectedItems();
            }
        });

        //this.initListViewData();
        SearchView searchViewCT = (SearchView) findViewById(R.id.SearchViewCT);
        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, myList);
        searchAutoCompleteCT.setAdapter(newsAdapterCT);
        searchViewCT.setIconifiedByDefault(false);
        searchAutoCompleteCT.setThreshold(1);
        searchAutoCompleteCT.setDropDownHeight(500);
        searchViewCT.setQueryHint("Tìm kiếm");

        searchAutoCompleteCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoCompleteCT.setText(queryString);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
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
        });*/
    }
    public void getProductList(ArrayList<String> ArrayString){
        try
        {
            productList = new ArrayList<HienTrang>();

            for (String HM : ArrayString)
            {
                productList.add(new HienTrang(HM,false));
            }

            listview_ht_adapter = new Listview_HT_Adapter(this,R.layout.list_item,productList );
            listView.setAdapter(listview_ht_adapter);
        }
        catch (ArithmeticException e)
        {

        }
    }

    // When user click "Print Selected Items".
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
    public void DialogHientrang() {
       final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hientrang_checkmenu);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        listView = (ListView) dialog.findViewById(R.id.listHienTrang);
        button = (Button) dialog.findViewById(R.id.button);
        editText = (EditText) dialog.findViewById(R.id.edittext);
        getProductList(myList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                editText.setText(sb);
            }
        });

        //this.initListViewData();
        SearchView searchViewCT = (SearchView) dialog.findViewById(R.id.SearchViewCT);
        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoCompleteCT = (SearchView.SearchAutoComplete)searchViewCT.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoCompleteCT.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        ArrayAdapter<String> newsAdapterCT = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, myList);
        searchAutoCompleteCT.setAdapter(newsAdapterCT);
        searchViewCT.setIconifiedByDefault(false);

        searchViewCT.setQueryHint("Tìm kiếm");

        searchAutoCompleteCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoCompleteCT.setText(queryString);
                //Toast.makeText(ActionBarSearchActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });
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
}
