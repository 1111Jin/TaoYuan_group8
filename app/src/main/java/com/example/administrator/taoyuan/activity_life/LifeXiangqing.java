package com.example.administrator.taoyuan.activity_life;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.example.administrator.taoyuan.utils.HttpUtils;


import org.xutils.x;

import java.util.ArrayList;

public class LifeXiangqing extends AppCompatActivity {

    ListLifeInfo.LifeInfo listinfo;
    BaseAdapter adapter;
    private TextView tv_title;
    private TextView tv_name;
    private ImageView iv_photo;
    private ImageView iv_contphoto;
    final ArrayList<ListLifeInfo.LifeInfo> lifelist= new ArrayList<ListLifeInfo.LifeInfo>();
    private  Button x_fanhui;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiangqing);
        tv_title = ((TextView) findViewById(R.id.tv_title));
        tv_name = ((TextView) findViewById(R.id.tv_name));
        iv_photo = ((ImageView) findViewById(R.id.iv_photo));
        iv_contphoto = ((ImageView) findViewById(R.id.iv_contphoto));
        x_fanhui = ((Button) findViewById(R.id.x_fanhui));

        initView();
        initData();
        initEvent();

    }

    private void initView() {
    }


    private void initEvent() {
            x_fanhui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

    }

    private void initData() {

        //获取传过来的ProductInfo
        Intent intent= getIntent();
        listinfo= intent.getParcelableExtra("lifeinfo");

        if(listinfo!=null) {
            //商品名称
            tv_title.setText(listinfo.userName);
            tv_name.setText(listinfo.content);
            x.image().bind(iv_photo, HttpUtils.localhost_jt+"imags/"+listinfo.headphoto);
            x.image().bind(iv_contphoto,HttpUtils.localhost_jt+"imags/"+listinfo.content_photo);
//            prodInfoTvDes.setText(listinfo.getName());
//            prodInfoTvPrice.setText(listinfo.getPrice() + "");

            //....
        }
        //获取网络数据，显示用户加入购物车的商品总数量
//        RequestParams requestParams=new RequestParams("http://10.40.5.45:8080/Life/getdongraibypage");
        //传参数：userId
//        MyApplication myApplication= (MyApplication) getApplication();
//        requestParams.addQueryStringParameter("userId", myApplication.getUser().getUserId()+"");

//        x.http().get(requestParams, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                //
//
//                Gson gson=new Gson();
//                ListLifeInfo bean= gson.fromJson(result, ListLifeInfo.class);
//                 System.out.println(bean.status+"----");
//                System.out.println(bean.lifeinfolist.size()+"===");
//                lifelist.addAll( bean.lifeinfolist);



            }









    }


