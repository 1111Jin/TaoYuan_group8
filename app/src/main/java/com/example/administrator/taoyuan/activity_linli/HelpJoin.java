package com.example.administrator.taoyuan.activity_linli;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.User;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HelpJoin extends AppCompatActivity {

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
    @InjectView(R.id.btn_help_true)
    Button btnHelpTrue;
    @InjectView(R.id.btn_help_false)
    Button btnHelpFalse;

    User user;
    Boolean id;
    Integer helpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_join);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        user = intent.getParcelableExtra("userInfo");
        id=intent.getBooleanExtra("flag",false);
        helpId=intent.getIntExtra("helpId",0);
        if(!id){
            btnHelpTrue.setEnabled(false);
            btnHelpTrue.setVisibility(View.GONE);
            btnHelpFalse.setEnabled(false);
            btnHelpFalse.setVisibility(View.GONE);
        }
        xUtilsImageUtils.display(ivFriHead, HttpUtils.localhost_su + user.getPhoto(), true);
        tvFriName.setText(user.getUserName());
        tvFriSex.setText(user.getSex());
        tvFriTel.setText(user.getTel());
        tvFriAddress.setText(user.getAddress());
        tvFriProgiles.setText(user.getUserProfiles());


    }

    @OnClick({R.id.btn_help_true, R.id.rl_back_fri,R.id.btn_help_false})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back_fri:
                finish();
                break;
            case R.id.btn_help_true:
                RequestParams re1=new RequestParams(HttpUtils.localhost+"tsAlias");
                String title="互帮通知";
                String content="您申请的互帮得到了同意！请按时参加！";
                String alias=String.valueOf(user.getUserId());
                re1.addBodyParameter("title",title);
                re1.addBodyParameter("content",content);
                re1.addBodyParameter("alias",alias);
                System.out.println(re1);
                x.http().get(re1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                finish();
                break;
            case R.id.btn_help_false:
                RequestParams re2=new RequestParams(HttpUtils.localhost+"tsAlias");
                String title1="互帮通知";
                String content1="非常抱歉，你报名的互帮未被同意！";
                String alias1=String.valueOf(user.getUserId());
                re2.addBodyParameter("title",title1);
                re2.addBodyParameter("content",content1);
                re2.addBodyParameter("alias",alias1);
                System.out.println(re2);
                x.http().get(re2, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                /**
                 * 删除参与表中的数据
                 */
                RequestParams re3 = new RequestParams(HttpUtils.localhost_su+"deletehelpjoin");
                re3.addBodyParameter("helpId",String.valueOf(helpId));
                re3.addBodyParameter("userId",String.valueOf(user.getUserId()));
                System.out.println(re3);
                x.http().get(re3, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("HelpJoin+++"+ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                finish();
                break;
            default:
                break;
        }
    }
}
