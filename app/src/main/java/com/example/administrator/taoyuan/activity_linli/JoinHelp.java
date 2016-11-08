package com.example.administrator.taoyuan.activity_linli;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.Help;
import com.example.administrator.taoyuan.pojo.HelpJoin;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.TitleBar;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class JoinHelp extends AppCompatActivity {

    @InjectView(R.id.join_helptitle)
    TitleBar joinHelptitle;
    @InjectView(R.id.et_helptel)
    EditText etHelptel;
    @InjectView(R.id.ll_joinhelp_tel)
    LinearLayout llJoinhelpTel;
    @InjectView(R.id.tv_helptelp)
    TextView tvHelptelp;
    @InjectView(R.id.help_self)
    EditText helpSelf;
    @InjectView(R.id.joinhelp_hand)
    Button joinhelpHand;
    Integer helpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_help);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        helpId = intent.getIntExtra("helpId",-1);
    }



    @OnClick(R.id.joinhelp_hand)
    public void onClick() {
        Integer userId=((MyApplication)getApplication()).getUser().getUserId();

        String userTel=etHelptel.getText().toString();
        String selfPro=helpSelf.getText().toString();

        HelpJoin helpJoin = new HelpJoin(userId,helpId,userTel,selfPro);
        Gson gson = new Gson();
        String helpJoinstr = gson.toJson(helpJoin);
        System.out.println(helpJoinstr);
        RequestParams requestParams=new RequestParams(HttpUtils.localhost_su+"inserthljoinservlet");
        requestParams.addBodyParameter("hlJoin",helpJoinstr);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        finish();
    }
}
