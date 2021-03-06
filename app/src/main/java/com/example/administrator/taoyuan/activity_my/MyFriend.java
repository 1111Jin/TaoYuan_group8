package com.example.administrator.taoyuan.activity_my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ListUserBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class MyFriend extends AppCompatActivity {

    private static final String TAG = "Myfriend";
    private ImageView iv_fri_head;
    private TextView tv_fri_name;
    private TextView tv_fri_sex;
    private TextView tv_fri_tel;
    private TextView tv_fri_address;
    private TextView tv_fri_profiles;

    ListUserBean.User user;
    private RelativeLayout rl_back_fri;
    private Button btn_delete;
    private RelativeLayout rl_activity;
    private RelativeLayout rl_help;
    private RelativeLayout rl_fri_address;
    private RelativeLayout rl_fri_live;

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

        rl_back_fri = ((RelativeLayout) findViewById(R.id.rl_back_fri));
        btn_delete = ((Button) findViewById(R.id.btn_fri_delete));

        rl_activity = ((RelativeLayout) findViewById(R.id.rl_fri_activity));
        rl_help = ((RelativeLayout) findViewById(R.id.rl_fri_help));
        rl_fri_address= ((RelativeLayout) findViewById(R.id.rl_fri_address));
        rl_fri_live = ((RelativeLayout) findViewById(R.id.rl_fri_life));


    }

    private void initView(){
        Intent intent = getIntent();
        user = intent.getParcelableExtra("friend");
        System.out.println(user.friendId);
        xUtilsImageUtils.display(iv_fri_head, HttpUtils.localhost+"/head"+user.userHead,10);
        tv_fri_name.setText(user.userName);
        tv_fri_sex.setText(user.userSex?"男":"女");
        tv_fri_tel.setText(user.userTel);
        tv_fri_address.setText(user.userAddress);
        tv_fri_profiles.setText(user.userProfiles);

    }

    private void initEvent() {

        rl_back_fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams requestParams=new RequestParams(HttpUtils.localhost+"deletefriend");
                Gson gson=new Gson();
                String friend=gson.toJson(user);
                requestParams.addBodyParameter("friend",friend);
                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i(TAG, "onSuccess: "+result);
                        if(result.equals("success")){
                            Toast.makeText(getApplicationContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"删除成失败！",Toast.LENGTH_SHORT).show();

                        }
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
            }
        });

        rl_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GetMyActivity.class);
//                System.out.println("333"+user.userId);
                intent.putExtra("user",user);
                startActivityForResult(intent,1);
            }
        });


        rl_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GetMyHelp.class);
                intent.putExtra("user",user);
                startActivityForResult(intent,2);
            }
        });

        rl_fri_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer u = user.friendId;
                System.out.println(u);
                RequestParams requestParams=new RequestParams("http://10.40.5.55:8080/ty/tsAlias");
                requestParams.addBodyParameter("title","5号发送的消息");
                requestParams.addBodyParameter("alias",String.valueOf(u));
                System.out.println(requestParams);
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
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

            }
        });

        rl_fri_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LiveActivity.class);
                intent.putExtra("user",user);
                startActivityForResult(intent,1);
            }
        });

    }


}
