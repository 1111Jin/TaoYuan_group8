package com.example.administrator.taoyuan.activity_linli;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.Activity;
import com.example.administrator.taoyuan.pojo.Help;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.TitleBar;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/24.
 */
public class HelpInfo extends AppCompatActivity {
    private static final String TAG = "HelpInfo";
    @InjectView(R.id.hl_content_title)
    TitleBar hlContentTitle;
    @InjectView(R.id.iv_tou)
    ImageView ivTou;
    @InjectView(R.id.tv_username)
    TextView tvUsername;
    @InjectView(R.id.tv_time2)
    TextView tvTime2;
    @InjectView(R.id.rl_help_content_rl)
    RelativeLayout rlHelpContentRl;
    @InjectView(R.id.v_line)
    View vLine;
    @InjectView(R.id.iv_help_p)
    ImageView ivHelpP;
    @InjectView(R.id.tv_help_content_content)
    TextView tvHelpContentContent;
    @InjectView(R.id.tv_hl_content)
    TextView tvHlContent;
    @InjectView(R.id.rl_hl_0)
    RelativeLayout rlHl0;
    @InjectView(R.id.tv_help_content_time)
    TextView tvHelpContentTime;
    @InjectView(R.id.tv_hl_time)
    TextView tvHlTime;
    @InjectView(R.id.rl_hl_1)
    RelativeLayout rlHl1;
    @InjectView(R.id.tv_help_content_address)
    TextView tvHelpContentAddress;
    @InjectView(R.id.tv_hl_address)
    TextView tvHlAddress;
    @InjectView(R.id.rl_hl_2)
    RelativeLayout rlHl2;
    @InjectView(R.id.tv_help_content_num)
    TextView tvHelpContentNum;
    @InjectView(R.id.tv_hl_num)
    TextView tvHlNum;
    @InjectView(R.id.rl_hl_3)
    RelativeLayout rlHl3;
    @InjectView(R.id.tv_help_content_integral)
    TextView tvHelpContentIntegral;
    @InjectView(R.id.tv_hl_integral)
    TextView tvHlIntegral;
    @InjectView(R.id.bt_help)
    Button btHelp;
    Activity activity;

    Help help = new Help();
    public TextView integral;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_list_view_item_content);
        integral = ((TextView) findViewById(R.id.integral));
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        help = intent.getParcelableExtra("HelpInfo");
        Log.i("HelpInfo", "initData: ------>"+help);
        if (help!=null){
            xUtilsImageUtils.display(ivTou, HttpUtils.localhost_su+help.getUser().getPhoto(),true);
            tvHlContent.setText(help.getHelpContent());
            tvHlTime.setText(help.getCreateTime()+"");
            tvHlAddress.setText(help.getHelpAddress());
            tvHlNum.setText(help.getNeedNums().toString());
            tvHlIntegral.setText(help.getSendIntegral().toString());
            integral.setText(help.getUser().getIntegral().toString());
            xUtilsImageUtils.display(ivHelpP,HttpUtils.localhost_su+help.getHelpImg(),false);
        }
    }

    @OnClick({R.id.bt_help,R.id.hl_content_title})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hl_content_title:
                finish();
                break;
            //跳到我要报名界面
            case R.id.bt_help:
                Intent intent =new Intent(HelpInfo.this,JoinHelp.class);
                startActivity(intent);
            break;
        }
    }
}
