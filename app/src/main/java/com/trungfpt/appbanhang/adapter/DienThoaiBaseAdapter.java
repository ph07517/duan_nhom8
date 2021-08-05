package com.trungfpt.appbanhang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trungfpt.appbanhang.R;
import com.trungfpt.appbanhang.model.Sanpham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienThoaiBaseAdapter extends BaseAdapter {
    Context context ;
    ArrayList <Sanpham> listSanPham;

    public DienThoaiBaseAdapter(Context context, ArrayList<Sanpham> listSanPham) {
        this.context = context;
        this.listSanPham = listSanPham;
    }

    @Override
    public int getCount() {
        return listSanPham.size();
    }

    @Override
    public Object getItem(int i) {
        return listSanPham.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    class ViewHolder{
        ImageView ingDienThoai;
        TextView tvTenDT,tvGiaDT,tvMoTaDT;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view==null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.dienthoai_item,null);
            viewHolder.ingDienThoai = view.findViewById(R.id.img_dienthoaiv);
            viewHolder.tvTenDT = view.findViewById(R.id.tv_tenspdienthoai);
            viewHolder.tvGiaDT = view.findViewById(R.id.tv_giapdienthoai);
            viewHolder.tvMoTaDT = view.findViewById(R.id.tv_motaspdienthoai);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.tvTenDT.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaDT.setText("Giá: "+decimalFormat.format(sanpham.getGiasp())+" vnđ");
        Glide.with(context).load(sanpham.getHinhanhsp()).placeholder(R.drawable.ic_launcher_background).into(viewHolder.ingDienThoai);
        viewHolder.tvMoTaDT.setMaxLines(3);
        viewHolder.tvMoTaDT.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.tvMoTaDT.setText(sanpham.getMotasp());
        return view;
    }
}
