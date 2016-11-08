package com.example.administrator.taoyuan.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.user_jt;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by mawuyang on 2016-10-20.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    Context context;
    List<T> lists;
    int layoutId;
    public CommonAdapter(Context context, List<T> lists,int layoutId){

        this.context=context;
        this.lists=lists;
        this.layoutId=layoutId;

    }

    @Override
    public int getCount() {
        return (lists!=null)?lists.size():0;
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);//每个item的数据源
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //找到控件，赋值
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=ViewHolder.get(context,layoutId,convertView,parent);
        convert(viewHolder,lists.get(position),position);
        return viewHolder.getConvertView();
    }


    //取出控件，赋值
    public abstract void  convert(ViewHolder viewHolder,T t,int position);

    public void changeState(final Button agree, final List<user_jt.friend_agree> userinfo, final int position, final int userId){
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agree.setVisibility(View.VISIBLE);
                agree.setText("已同意");
                RequestParams params = new RequestParams(HttpUtils.localhost_jt + "AddFriendServlet");
                params.addBodyParameter("userId", String.valueOf(userId));
                params.addBodyParameter("friendTel", userinfo.get(position).getUserTel());
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
//                        Log.i(TAG, "onSuccess: " + result);
                        if (result.equals("1")) {
                            agree.setVisibility(View.VISIBLE);
                            agree.setText("已同意");
//                            Toast.makeText(getApplicationContext(), "添加成功！", Toast.LENGTH_SHORT).show();
                        } else {
//                                    agree.setVisibility(View.VISIBLE);
//                                    agree.setText("已同意");
//                            Toast.makeText(getApplicationContext(), "添加成功！", Toast.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }
}
