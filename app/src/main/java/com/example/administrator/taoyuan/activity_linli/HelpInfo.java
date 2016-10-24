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
import com.example.administrator.taoyuan.pojo.ListHelpBean;
import com.example.administrator.taoyuan.utils.TitleBar;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/24.
 */
public class HelpInfo extends AppCompatActivity {
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

    ListHelpBean.Help help = new ListHelpBean.Help();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_list_view_item_content);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        help = intent.getParcelableExtra("HelpInfo");
        Log.i("HelpInfo", "initData: ------>"+help);
        if (help!=null){
            xUtilsImageUtils.display(ivTou,"http://10.0.2.2:8080/cmty/upload/"+help.help_photo);
            tvHlContent.setText(help.help_title);
            tvHlTime.setText(help.time);
            tvHlAddress.setText(help.address);
            tvHlNum.setText(help.persons.toString());
            tvHlIntegral.setText(help.send_integral.toString());
            xUtilsImageUtils.display(ivHelpP,"http://10.0.2.2:8080/cmty/upload/"+help.help_photo);
        }
    }

    @OnClick({R.id.bt_help,R.id.hl_content_title})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hl_content_title:
                finish();
        }
    }
}
