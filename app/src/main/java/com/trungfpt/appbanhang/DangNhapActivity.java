package com.trungfpt.appbanhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.trungfpt.appbanhang.activity.MainActivity;

public class DangNhapActivity extends AppCompatActivity {
    Button btnDangNhap,btnDangKy;
    EditText edtEmail,edtMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        AnhXa();
        DangKy();
        DangNhap();
    }

    private void DangNhap() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DangNhapActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void DangKy() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DangNhapActivity.this,DangKyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        btnDangNhap = findViewById(R.id.btn_dangnhap);
        btnDangKy = findViewById(R.id.btn_dangkymdn);
        edtEmail = findViewById(R.id.edt_emaildangnhap);
        edtMatKhau = findViewById(R.id.edt_matkhau);
    }
}