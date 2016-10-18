package com.example.administrator.taoyuan.activity_my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ListActivityBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;

import org.w3c.dom.Text;

public class MyFriend extends AppCompatActivity {

    private ImageView iv_fri_head;
    private TextView tv_fri_name;
    private TextView tv_fri_sex;
    private TextView tv_fri_tel;
    private TextView tv_fri_address;
    private TextView tv_fri_profiles;

    ListActivityBean.User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend);

        initData();
        initView();
        initEvent();
    }

    private void initData(){
        iv_fri_head = ((ImageView) findViewById(R.id.iv_fri_head));
        tv_fri_name = ((TextView) findViewById(R.id.tv_fri_name));
        tv_fri_sex = ((TextView) findViewById(R.id.tv_fri_sex));
        tv_fri_tel = ((TextView) findViewById(R.id.tv_fri_tel));
        tv_fri_address = ((TextView) findViewById(R.id.tv_fri_address));
        tv_fri_profiles = ((TextView) findViewById(R.id.tv_fri_progiles));

    }

    private void initView(){
        Intent intent = getIntent();
        user = intent.getParcelableExtra("friend");
        xUtilsImageUtils.display(iv_fri_head, HttpUtils.localhost+user.userHead,10);
        tv_fri_name.setText(user.userName);
        tv_fri_sex.setText(user.userSex?"男":"女");
        tv_fri_tel.setText(user.userTel);
        tv_fri_address.setText(user.userAddress);
        tv_fri_profiles.setText(user.userProfiles);

    }

    private void initEvent() {
    }


}
