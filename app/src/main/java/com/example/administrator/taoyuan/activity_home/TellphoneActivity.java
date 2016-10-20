package com.example.administrator.taoyuan.activity_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.MainActivity;
import com.example.administrator.taoyuan.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TellphoneActivity extends AppCompatActivity {

    @InjectView(R.id.all_order_goback)
    ImageView allOrderGoback;
    @InjectView(R.id.rl_back_home1)
    RelativeLayout rlBackHome1;
    @InjectView(R.id.rl_back_home)
    RelativeLayout rlBackHome;
    @InjectView(R.id.tv_wydh)
    TextView tvWydh;
    @InjectView(R.id.iv_wydhphone)
    ImageView ivWydhphone;
    @InjectView(R.id.tv_kfdh)
    TextView tvKfdh;
    @InjectView(R.id.iv_kfdhphone)
    ImageView ivKfdhphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tellphone);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.rl_back_home1, R.id.iv_wydhphone, R.id.iv_kfdhphone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back_home1:
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_wydhphone:
                break;
            case R.id.iv_kfdhphone:
                break;
        }
    }
}
