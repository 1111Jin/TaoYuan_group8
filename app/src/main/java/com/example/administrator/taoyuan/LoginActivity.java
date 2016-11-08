package com.example.administrator.taoyuan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.User;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler mHandler;
    String str = null;
    EditText account;
    EditText password;
    private Button loginButton;
    private SharedPreferences sp;

    Integer fff = -1;
    private CheckBox rem_pw;
    private CheckBox auto_login;

    String name;
    String psd;
    private TextView btn_register;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);

        account = (EditText) findViewById(R.id.loginAccount_id);
        password = (EditText) findViewById(R.id.password_id);
        loginButton = (Button) findViewById(R.id.login);
        rem_pw = ((CheckBox) findViewById(R.id.ck_rem_pw));
        auto_login = ((CheckBox) findViewById(R.id.ck_auto_login));
        btn_register = ((TextView) findViewById(R.id.btn_register));
        // 对登录按钮设置监听，方法由下面的Onclick实现
        loginButton.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        //判断记住密码多选框的状态
        if(sp.getBoolean("ISCHECK", false))
        {
            //设置默认是记录密码状态
            rem_pw.setChecked(true);
            account.setText(sp.getString("USER_NAME", ""));
            password.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if(sp.getBoolean("AUTO_ISCHECK", false))
            {
                //设置默认是自动登录状态
                auto_login.setChecked(true);
                //跳转界面
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        }

        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (rem_pw.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        //监听自动登录多选框事件
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });
    }



    @Override
    /**
     * 实现登录按钮的跳转
     */
    public void onClick(View v) {
        // 根据id判断单击的是哪个控件，固定写法
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this, register.class);
                startActivityForResult(intent, 1);
            default:
                break;
        }
    }

    /**
     * 登录方法
     */
    public boolean login() {

        if (isUserNameAndPwdValid()) {

            name=account.getText().toString();
            psd=password.getText().toString();

            //主线程
            try {
                RequestParams re = new RequestParams(HttpUtils.localhost + "login");
                re.addBodyParameter("loginName", name);
                re.addBodyParameter("psd", psd);
                System.out.println(re);
                x.http().get(re, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        fff=1;
                        str = result;
                        System.out.println(str + "1111111");
                        if(str.equals("null")){
                            Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_LONG).show();
                        }
                        Gson gson = new Gson();
                        User user = gson.fromJson(result, User.class);
                        ((MyApplication) getApplication()).setUser(user);

                        //登录成功和记住密码框为选中状态才保存用户信息
                        if(rem_pw.isChecked())
                        {
                            //记住用户名、密码、
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("USER_NAME", name);
                            editor.putString("PASSWORD",psd);
                            editor.commit();
                        }

                        if (!user.equals(null)) {
                            //实现界面的跳转
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            //关闭当前界面
                            finish();
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
            } catch (Exception e) {
                e.printStackTrace();
                //使用-1代表程序异常
                fff = -2;
                str = e.toString();
            }

//            switch (fff) {
//                case -1:
//                    Toast.makeText(LoginActivity.this, "服务器连接失败!",
//                            Toast.LENGTH_SHORT).show();
//                    break;
//                case -2:
//                    Toast.makeText(LoginActivity.this, "哎呀,出错啦...",
//                            Toast.LENGTH_SHORT).show();
//                    break;
//                case 1:
//                    String temp = (String) str;
//                    System.out.println(temp + "33333333");
//                    Gson gson = new Gson();
//                    User user = gson.fromJson(temp, User.class);
////                            System.out.println(temp+"222222222222");
//                    ((MyApplication) getApplication()).setUser(user);
//
//                    //登录成功和记住密码框为选中状态才保存用户信息
//                    if(rem_pw.isChecked())
//                    {
//                        //记住用户名、密码、
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putString("USER_NAME", name);
//                        editor.putString("PASSWORD",psd);
//                        editor.commit();
//                    }
//
//                    if (user.getUserId() != 0) {
//                        //实现界面的跳转
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        //关闭当前界面
//                        finish();
//                    } else {
//                        //实现界面的跳转
////                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
////                                startActivity(intent);
////                                //关闭当前界面
////                                finish();
//                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
//                    }
//            }

        }



    return false;
}

    /**
     * 判断用户名和密码是否有效
     *
     * @return
     */
    public boolean isUserNameAndPwdValid() {
        // 用户名和密码不得为空
        if (account.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.accountName_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.password_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
