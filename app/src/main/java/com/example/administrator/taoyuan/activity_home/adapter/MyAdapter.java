package com.example.administrator.taoyuan.activity_home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_home.GonggaoActivity;
import com.example.administrator.taoyuan.activity_home.GonggaoXiangqing;
import com.example.administrator.taoyuan.activity_home.Netutil;
import com.example.administrator.taoyuan.pojo.GongGaoBean;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/10/27.
 */
public class MyAdapter extends BaseAdapter {
private Context context;
    LayoutInflater mInflater;
    private ArrayList<GongGaoBean.GongGao> gonggaoList;
    private TextView tv1;
    Bundle bundle = new Bundle();

    public MyAdapter(Context context,ArrayList<GongGaoBean.GongGao> gonggaoList) {
        this.context=context;
        this.gonggaoList=gonggaoList;
    }
    static class ViewHolder
    {
        public TextView tv;
        public ImageView igv;
        public  TextView tv3;

    }
    @Override
    public int getCount() {
        return gonggaoList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.gonggao_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            holder.igv = (ImageView) convertView.findViewById(R.id.igv);
            holder.tv3= (TextView) convertView.findViewById(R.id.tv3);
            tv1 = ((TextView) convertView.findViewById(R.id.tv1));
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(context).load(gonggaoList.get(position).getGonggaoImg()).networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE);
        Picasso.with(context).load(Netutil.url+"image/"+gonggaoList.get(position).getGonggaoImg()+".png").placeholder(R.drawable.load).error(R.drawable.error).into(holder.igv);
        holder.tv.setText(gonggaoList.get(position).gonggaoTitle);
        holder.tv3.setText(gonggaoList.get(position).getGonggaofabiaoTime());
        return convertView;
        }

}
