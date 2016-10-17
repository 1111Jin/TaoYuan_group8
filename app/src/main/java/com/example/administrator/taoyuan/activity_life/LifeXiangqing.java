package com.example.administrator.taoyuan.activity_life;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;


import java.util.ArrayList;

public class LifeXiangqing extends AppCompatActivity {

    ListLifeInfo.LifeInfo listinfo;
    private TextView tv_title;
    private TextView tv_name;
    private ImageView iv_photo;
    final ArrayList<ListLifeInfo.LifeInfo> lifelist= new ArrayList<ListLifeInfo.LifeInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiangqing);
        tv_title = ((TextView) findViewById(R.id.tv_title));
        tv_name = ((TextView) findViewById(R.id.tv_name));
        iv_photo = ((ImageView) findViewById(R.id.iv_photo));
        initData();
    }

    private void initData() {

        //获取传过来的ProductInfo
        Intent intent= getIntent();
        listinfo= intent.getParcelableExtra("lifeinfo");

        if(listinfo!=null) {
            //商品名称
            tv_title.setText(listinfo.userName);
            tv_name.setText(listinfo.content);

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

