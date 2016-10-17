package com.example.administrator.taoyuan.activity_linli;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ActivityBean;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/17.
 */
public class ActivityInfo extends AppCompatActivity {
    @InjectView(R.id.iv_ac_image)
    ImageView ivAcImage;
    @InjectView(R.id.tv_ac_num)
    TextView tvAcNum;
    @InjectView(R.id.rl_ac_content)
    RelativeLayout rlAcContent;
    @InjectView(R.id.tv_ac_address)
    TextView tvAcAddress;
    @InjectView(R.id.tv_ac_time3)
    TextView tvAcTime3;
    @InjectView(R.id.tv_ac_activity)
    TextView tvAcActivity;
    @InjectView(R.id.tv_ac_acpro)
    TextView tvAcAcpro;
    @InjectView(R.id.ll_ac_l)
    LinearLayout llAcL;
    @InjectView(R.id.tv_ac_master)
    TextView tvAcMaster;
    @InjectView(R.id.bt_ac_join)
    Button btAcJoin;
    @InjectView(R.id.tv_ac_title)
    TextView tvAcTitle;
    @InjectView(R.id.tv_ac_name)
    TextView tvAcName;

    ActivityBean.Activity activity ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_item_content);
        ButterKnife.inject(this);
        initData();
    }

    public void initData(){
        Intent intent = getIntent();
        activity = intent.getParcelableExtra("ActivityInfo");
        Log.i("ActivityInfo", "initData: ------>"+activity);
        if (activity!=null){
            tvAcTitle.setText(activity.activity_title);
            tvAcName.setText(activity.activity_master);
            tvAcAcpro.setText(activity.activity_content);
            tvAcAddress.setText(activity.activity_adress);
            xUtilsImageUtils.display(ivAcImage,"http://10.40.5.23:8080/cmty/upload/"+activity.activity_photo);
        }
    }
    @OnClick(R.id.bt_ac_join)
    public void onClick() {
    }
}
