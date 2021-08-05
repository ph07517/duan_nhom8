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
import com.trungfpt.appbanhang.adapter.DienThoaiBaseAdapter;
import com.trungfpt.appbanhang.model.Sanpham;
import com.trungfpt.appbanhang.ultil.CheckConnection;
import com.trungfpt.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    ListView lvdt;
    Toolbar toolbar;
    ArrayList<Sanpham> sanphamArrayList;
    DienThoaiBaseAdapter dienThoaiBaseAdapter;
    View footerView;
    boolean isLoading;
    mHandler mHandler;
    boolean limitdata = false;
    int iddt = 0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
//           Getspdt();
            LoadModedata();
            Getdata(page);
            Getidsp();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(),
                    "Ban kiem tra lai ket noi");
            finish();
        }
        Toorbar();
    }

    private void LoadModedata() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DienThoaiActivity.this,ChiTietSPActivity.class);
                intent.putExtra("thongtinsanpham",sanphamArrayList.get(i));
                startActivity(intent);
            }
        });
        lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && limitdata==false) {
                       isLoading = true;
                       TheardData theardData = new TheardData();
                       theardData.start();
                }
            }
        });

    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    lvdt.addFooterView(footerView);
                    break;
                case 1:
                    Getdata(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class TheardData extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
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
                    lvdt.removeFooterView(footerView);
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
                            sanphamArrayList.add(new Sanpham(id, tendt, giadt, hinhanhdt, motadt, iddt1));
                            dienThoaiBaseAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitdata = true;
                    lvdt.removeFooterView(footerView);
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
                param.put("idsanpham", String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Getidsp() {
        iddt = getIntent().getIntExtra("idloaisanpham", -1);

    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar_maindienthoai);
        lvdt = findViewById(R.id.lv_dienthoai);
        sanphamArrayList = new ArrayList<>();
        dienThoaiBaseAdapter = new DienThoaiBaseAdapter(getApplicationContext(), sanphamArrayList);
        lvdt.setAdapter(dienThoaiBaseAdapter);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = layoutInflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
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