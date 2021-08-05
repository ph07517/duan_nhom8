package com.trungfpt.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.trungfpt.appbanhang.activity.LaptopActivity;
import com.trungfpt.appbanhang.activity.DienThoaiActivity;
import com.trungfpt.appbanhang.R;
import com.trungfpt.appbanhang.model.Loaisp;

import java.util.ArrayList;

public class LoaispAdapter extends RecyclerView.Adapter<LoaispAdapter.ViewHolder> {
    Context context;
    ArrayList<Loaisp> listLoaisp;

    public LoaispAdapter(Context context, ArrayList<Loaisp> listLoaisp) {
        this.context = context;
        this.listLoaisp = listLoaisp;
    }

    @NonNull
    @Override
    public LoaispAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_listview_loaisp,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaispAdapter.ViewHolder holder, final int position) {
        Loaisp loaisp = listLoaisp.get(position);
        holder.tvTensp.setText(loaisp.getTensanpham());
        Glide.with(context).load(loaisp.getHinhanhloaisp()).into(holder.imgHinhAnhsp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 0:
                        Intent intent1 = new Intent(context, LaptopActivity.class);
                        intent1.putExtra("idloaisanpham",listLoaisp.get(position).getId());
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                        break;
                    case 1:
                        Intent intent = new Intent(context, DienThoaiActivity.class);
                        intent.putExtra("idloaisanpham",listLoaisp.get(position).getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listLoaisp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTensp;
        ImageView imgHinhAnhsp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTensp = itemView.findViewById(R.id.tv_loaisp);
            imgHinhAnhsp = itemView.findViewById(R.id.img_loaisp);
        }
    }
}
