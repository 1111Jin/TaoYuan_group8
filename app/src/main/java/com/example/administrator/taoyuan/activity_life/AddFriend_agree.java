package com.example.administrator.taoyuan.activity_life;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.user_jt;
import com.example.administrator.taoyuan.utils.CommonAdapter;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.ViewHolder;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class AddFriend_agree extends AppCompatActivity {

    private ListView lv_agree;
    CommonAdapter<user_jt.friend_agree> adapter;
    private List<user_jt.friend_agree> userinfo=new ArrayList<user_jt.friend_agree>();
    private Button agree;
    user_jt.friend_agree userJt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_agree);

        lv_agree = ((ListView) findViewById(R.id.lv_friend_agree));

        initData();
        initEvent();
        initView();
    }

    public void initView() {


    }

    private void initEvent() {

    }

    private void initData() {

            adapter=new CommonAdapter<user_jt.friend_agree>(AddFriend_agree.this,userinfo,R.layout.addfriend_agree_item) {
                    @Override
            public void convert(ViewHolder viewHolder, user_jt.friend_agree friend_agree, final int position) {

                userJt=userinfo.get(position);
                ImageButton ib_head = viewHolder.getViewById(R.id.ib_head);
                agree = viewHolder.getViewById(R.id.agree);
                TextView  nicheng= viewHolder.getViewById(R.id.nicheng);
                TextView  jianjie= viewHolder.getViewById(R.id.jianjie);
                nicheng.setText(userJt.getUserName());
                jianjie.setText(userJt.getUserProfiles());
                xUtilsImageUtils.display(ib_head, HttpUtils.localhost_jt+"imgs/"+userJt.getUserHead()+"",false);

                agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestParams params = new RequestParams(HttpUtils.localhost_jt + "AddFriendServlet");
                        params.addBodyParameter("userId", String.valueOf(((MyApplication)getApplication()).getUser().getUserId()));
                        params.addBodyParameter("friendTel", userinfo.get(position).getUserTel());
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                System.out.println(result);
                                Integer line = Integer.parseInt(result);
                                if(line.equals(1)){
                                    System.out.println("已同意");
                                    agree.setBackgroundResource(R.color.none);
                                    agree.setText("已同意");
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

//                @Override
//                public View getView(int position, View convertView, ViewGroup parent) {
//                    ViewHolder viewHolder=ViewHolder.get(getApplicationContext(),R.layout.addfriend_agree_item,convertView,null);
//                    convert(viewHolder,userinfo.get(position),position);
//                    agree.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(AddFriend_agree.this, "dianjishijian ", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    return convertView;
//                }
            };
        lv_agree.setAdapter(adapter);
        addFriendInfo();
    }

    private void addFriendInfo() {
        RequestParams params = new RequestParams(HttpUtils.localhost_jt+"FriendagreeServlet");

        int tel = ((MyApplication)getApplication()).getUser().getUserId();
        params.addBodyParameter("userId",String.valueOf(tel));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("1000000"+result);
                Gson gson=new Gson();
                user_jt been=gson.fromJson(result,user_jt.class);
                System.out.println(been+"7897899789789789");
                userinfo.addAll(been.friendAgrees);
                adapter.notifyDataSetChanged();
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


}
