package com.example.administrator.taoyuan.activity_my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ListUserBean;
import com.example.administrator.taoyuan.utils.TitleBar;

public class Instill extends AppCompatActivity {

    private RelativeLayout rl_management;
    private ImageView iv_head;
    ListUserBean.User user;
    Bitmap bm;
    private TitleBar title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instill);

        initView();
        initData();
        initEvent();
    }

    public void initView(){
        rl_management = ((RelativeLayout) findViewById(R.id.rl_management));
        iv_head = ((ImageView) findViewById(R.id.iv_head));
        title = ((TitleBar) findViewById(R.id.t_ins));
    }

    public void initData(){

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        bm = intent.getParcelableExtra("head");
        iv_head.setImageBitmap(bm);
    }

    public void initEvent(){
        title.setLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
