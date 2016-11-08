package com.example.administrator.taoyuan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.taoyuan.pojo.ListUserBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class register extends AppCompatActivity {

    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.name)
    EditText name;
    @InjectView(R.id.phone)
    EditText phone;
    @InjectView(R.id.zc_address)
    EditText zcAddress;
    @InjectView(R.id.zc_profiles)
    EditText zcProfiles;
    @InjectView(R.id.rb_man)
    RadioButton rbMan;
    @InjectView(R.id.rb_woman)
    RadioButton rbWoman;
    @InjectView(R.id.sex)
    RadioGroup sex;
    @InjectView(R.id.register_btn)
    Button registerBtn;
    String temp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.inject(this);
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rbMan.getId()==checkedId){
                    temp=rbMan.getText().toString();
                }else if(rbWoman.getId()==checkedId){
                    temp=rbWoman.getText().toString();
                }
            }
        });

    }

    @OnClick(R.id.register_btn)
    public void onClick() {
        //访问服务器
        //添加到网络；
        RequestParams requestParams = new RequestParams(HttpUtils.localhost + "registerServlet");
        requestParams.setMultipart(true);


        Gson gson = new Gson();
        ListUserBean.User user = new ListUserBean.User();
        user.setUserName(name.getText().toString());
        user.setUserAddress(zcAddress.getText().toString());
        user.setUserPsd(password.getText().toString());


        System.out.println(temp);
        user.setUserSex(temp.equals("男")?true:false);
        user.setUserTel(phone.getText().toString());
        user.setUserProfiles(zcProfiles.getText().toString());


        String registerInfo = gson.toJson(user);
        System.out.println("wwwww"+registerInfo);

        requestParams.addBodyParameter("register", registerInfo);
        System.out.println(requestParams);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("register", "onSuccess: ");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex + "++++++++++++++++++++++++++++++++");
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
