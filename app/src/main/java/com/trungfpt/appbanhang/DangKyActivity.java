package com.trungfpt.appbanhang;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DangKyActivity extends AppCompatActivity {
Button btnDangky;
EditText edtTenKhachHang,edtEmailDK,edtMatKhau,edtNhapLaiMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        AnhXa();
        Dangky();
    }

    private void Dangky() {
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        btnDangky = findViewById(R.id.btn_dangky);
        edtTenKhachHang = findViewById(R.id.edt_tenkhachhangdangky);
        edtEmailDK = findViewById(R.id.edt_emaildangky);
        edtMatKhau = findViewById(R.id.edt_matkhaudangky);
        edtNhapLaiMatKhau = findViewById(R.id.edt_nhaplaimatkhau);
    }
}