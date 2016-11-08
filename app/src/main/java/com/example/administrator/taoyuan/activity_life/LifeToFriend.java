package com.example.administrator.taoyuan.activity_life;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_my.GetMyActivity;
import com.example.administrator.taoyuan.activity_my.GetMyHelp;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.example.administrator.taoyuan.pojo.ListUserBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class LifeToFriend extends AppCompatActivity {


        private static final String TAG = "Myfriend";
        private ImageView iv_fri_head;
        private TextView tv_fri_name;
        private TextView tv_fri_sex;
        private TextView tv_fri_tel;
        private TextView tv_fri_address;
        private TextView tv_fri_profiles;
        List<ListUserBean.User> userList=new ArrayList<ListUserBean.User>();
        private ListUserBean.User user;
        private RelativeLayout rl_back_fri;
        private Button btn_delete;
        private RelativeLayout rl_activity;
        private RelativeLayout rl_help;
        private ProgressBar progressBar;
        private BaseAdapter adapter;
       ArrayList<ListLifeInfo.LifeInfo> listinfo;
    Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_friend);


        initView();
        initData();
        initEvent();

        }

    private void initView(){
        Intent intent= getIntent();
        userId= intent.getIntExtra("lifeinfo",0);



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


    }


    private void initData(){

        RequestParams params=new RequestParams(HttpUtils.localhost+"/my?userId="+userId);
        Log.i(TAG, "initData: "+params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: "+result);
                Gson gson = new Gson();
                ListUserBean bean = gson.fromJson(result,ListUserBean.class);
                userList=bean.userList;
                user=userList.get(0);

        String imgurl="/head"+user.userHead;
        xUtilsImageUtils.display(iv_fri_head, HttpUtils.localhost+imgurl,10);
        tv_fri_name.setText(user.userName);
        tv_fri_sex.setText(user.userSex?"男":"女");
        tv_fri_tel.setText(user.userTel);
        tv_fri_address.setText(user.userAddress);
        tv_fri_profiles.setText(user.userProfiles);

         btn_delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 RequestParams params = new RequestParams(HttpUtils.localhost_jt + "ApplyAgreeServlet");
                 params.addBodyParameter("userId", String.valueOf(user.userId));
                 params.addBodyParameter("friendTel",user.userTel);
                 x.http().post(params, new Callback.CommonCallback<String>() {
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



            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: "+ex.toString());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

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

    }


}