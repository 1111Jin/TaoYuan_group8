package com.example.administrator.taoyuan.activity_my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ListUserBean;
import com.example.administrator.taoyuan.utils.TitleBar;

public class MyIntegral extends AppCompatActivity {

    ListUserBean.User user ;
    private TextView tv_my_integral;
    private Button btn_integral_cash;
    private Button btn_integral_msg;
    private TitleBar tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_integral);

        initView();
        initData();
    }


    public void initView(){
        tt = ((TitleBar) findViewById(R.id.tt));
        tv_my_integral = ((TextView) findViewById(R.id.my_integral));
        btn_integral_cash = ((Button) findViewById(R.id.btn_integral_cash));
        btn_integral_msg = ((Button) findViewById(R.id.btn_integral_msg));
    }

    public void initData(){
        Intent intent=getIntent();
        Integer integral=intent.getIntExtra("integral",0);
        System.out.println(integral);
//        if(user!=null) {
        tv_my_integral.setText(String.valueOf(integral));
//        }

        tt.setLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_integral_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),IntegralGet.class);
                startActivity(intent1);
            }
        });

        btn_integral_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),IntegralUse.class);
                startActivity(intent2);
            }
        });
    }
}
