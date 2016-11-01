package com.example.administrator.taoyuan.activity_linli;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.User;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/29.
 */
public class UserInfo extends AppCompatActivity {
    @InjectView(R.id.all_order_goback)
    ImageView allOrderGoback;
    @InjectView(R.id.rl_back_fri)
    RelativeLayout rlBackFri;
    @InjectView(R.id.iv_fri_head)
    ImageView ivFriHead;
    @InjectView(R.id.tv_fri_name)
    TextView tvFriName;
    @InjectView(R.id.rl_modify_My)
    RelativeLayout rlModifyMy;
    @InjectView(R.id.tv_fri_sex)
    TextView tvFriSex;
    @InjectView(R.id.rl_fri_sex)
    RelativeLayout rlFriSex;
    @InjectView(R.id.tv_fri_tel)
    TextView tvFriTel;
    @InjectView(R.id.rl_fri_tel)
    RelativeLayout rlFriTel;
    @InjectView(R.id.tv_fri_address)
    TextView tvFriAddress;
    @InjectView(R.id.rl_fri_address)
    RelativeLayout rlFriAddress;
    @InjectView(R.id.tv_fri_progiles)
    TextView tvFriProgiles;
    @InjectView(R.id.rl_fri_profiles)
    RelativeLayout rlFriProfiles;
    @InjectView(R.id.rl_fri_life)
    RelativeLayout rlFriLife;
    @InjectView(R.id.rl_fri_activity)
    RelativeLayout rlFriActivity;
    @InjectView(R.id.iv_to_myName)
    ImageView ivToMyName;
    @InjectView(R.id.rl_fri_help)
    RelativeLayout rlFriHelp;
    @InjectView(R.id.btn_fri_delete)
    Button btnFriDelete;

    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("userInfo");
        xUtilsImageUtils.display(ivFriHead, HttpUtils.localhost_su+user.getPhoto(),true);
        tvFriName.setText(user.getUserName());
        tvFriSex.setText(user.getSex());
        tvFriTel.setText(user.getTel());
        tvFriAddress.setText(user.getAddress());
        tvFriProgiles.setText(user.getUserProfiles());


    }

    @OnClick({R.id.btn_fri_delete, R.id.rl_back_fri})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back_fri:
                finish();
                break;
        }
    }
}
