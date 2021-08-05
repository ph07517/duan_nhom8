package com.trungfpt.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trungfpt.appbanhang.R;
import com.trungfpt.appbanhang.adapter.LaptopBaseAdapter;
import com.trungfpt.appbanhang.model.Sanpham;
import com.trungfpt.appbanhang.ultil.CheckConnection;
import com.trungfpt.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    ListView lvlaptop;
    Toolbar toolbar;
    ArrayList<Sanpham> listLaptop;
    LaptopBaseAdapter laptopBaseAdapter;
    int idlaptop = 0;
    int page = 1;

    View footerViewLT;
    boolean  isLoading;
    boolean limitdataLap;
    myhandlerLaptop myhandlerLaptop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            Getdata(page);
            Toorbar();
            LoadModeData();
            Getidsp();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"kiem tra ket noi");
        }
    }

    private void LoadModeData() {
        lvlaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LaptopActivity.this, ChiTietSPActivity.class);
                intent.putExtra("thongtinsanpham",listLaptop.get(i));
                startActivity(intent);
            }
        });
        lvlaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                  if (firstItem + visibleItem == totalItem && totalItem != 0 && isLoading ==false&& limitdataLap==false){
                      isLoading = true;
                           ThreadLaptop threadLaptop = new ThreadLaptop();
                           threadLaptop.start();
                  }
            }
        });
}

public class myhandlerLaptop extends Handler{
    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case 0:
                lvlaptop.removeFooterView(footerViewLT);
                break;
            case 1:
                Getdata(++page);
                isLoading = false;
                break;
        }
        super.handleMessage(msg);
    }
}
public class ThreadLaptop extends Thread{
    @Override
    public void run() {
        myhandlerLaptop.sendEmptyMessage(0);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message message = myhandlerLaptop.obtainMessage(1);
        myhandlerLaptop.sendMessage(message);
        super.run();
    }
}


    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar_mainLaptop);
        lvlaptop = findViewById(R.id.lv_laptop);
        listLaptop = new ArrayList<>();
        laptopBaseAdapter = new LaptopBaseAdapter(getApplicationContext(), listLaptop);
        lvlaptop.setAdapter(laptopBaseAdapter);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerViewLT = layoutInflater.inflate(R.layout.progress_laptop,null);
        myhandlerLaptop = new myhandlerLaptop();
    }
    private void Getidsp() {
        idlaptop = getIntent().getIntExtra("idloaisanpham", -1);

    }
    private void Toorbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void Getdata(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.DuongDanDienThoaiFPT + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tendt = "";
                int giadt = 0;
                String hinhanhdt = "";
                String motadt = "";
                int iddt1 = 0;
                if (response != null && response.length() != 2) {
                    lvlaptop.removeFooterView(footerViewLT);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tendt = jsonObject.getString("tensp");
                            giadt = jsonObject.getInt("giasp");
                            hinhanhdt = jsonObject.getString("hinhanhsp");
                            motadt = jsonObject.getString("motasp");
                            iddt1 = jsonObject.getInt("idsanpham");
                            listLaptop.add(new Sanpham(id, tendt, giadt, hinhanhdt, motadt, iddt1));
                            laptopBaseAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitdataLap = true;
                    lvlaptop.removeFooterView(footerViewLT);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"het du lieu");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cart:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}