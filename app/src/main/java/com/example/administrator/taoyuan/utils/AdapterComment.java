package com.example.administrator.taoyuan.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.Comment;

import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyp on 2016/8/10.
 */
public class AdapterComment extends BaseAdapter {

    Context context;


    List<Comment> data;


    public AdapterComment(Context c, List<Comment> data){
        this.context = c;
        this.data = data;
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        System.out.println("+"+position);
        ViewHolder holder = null;
        // 重用convertView
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.omment_item, null);
            holder.comment_photo = ((ImageView) convertView.findViewById(R.id.comment_photo));
            holder.comment_name = (TextView) convertView.findViewById(R.id.comment_name);
            holder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
            holder.comment_time = ((TextView) convertView.findViewById(R.id.comment_time));
            holder.delete = ((TextView) convertView.findViewById(R.id.delete));
            convertView.setTag(holder);
        } else
            System.out.println("++++"+data.size());
            Log.i("评论人的Id", "getView: " + data.get(position).getUserId());
            holder = (ViewHolder) convertView.getTag();
            holder.comment_content.setText(data.get(position).getContent());
            holder.comment_time.setText(new SimpleDateFormat("MM-dd HH:mm").format(data.get(position).getCreate()));
            x.image().bind(holder.comment_photo, HttpUtils.localhost_su + data.get(position).getUser().getPhoto());
            holder.comment_name.setText(data.get(position).getUser().getUserName()+":");

        return convertView;
    }

    /**
     * 添加一条评论,刷新列表
     * @param comment
     */
    public void addComment(Comment comment){
        List<Comment> newData = new ArrayList<Comment>();

        newData.addAll(data);
        data.clear();
        newData.add(comment);
//        data.add(comment);
        data.addAll(newData);
//
        System.out.println(".........."+data.size());
        notifyDataSetChanged();

        notifyDataSetInvalidated();
    }

    /**
     * 静态类，便于GC回收
     */
    public static class ViewHolder{
        TextView delete;
        ImageView comment_photo;
        TextView comment_name;
        TextView comment_content;
        TextView comment_time;
    }

}
