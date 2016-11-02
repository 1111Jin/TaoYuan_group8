package com.example.administrator.taoyuan;




import android.content.Intent;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.fragment.Life;
import com.example.administrator.taoyuan.fragment.home;
import com.example.administrator.taoyuan.fragment.linli;
import com.example.administrator.taoyuan.fragment.my;
import com.example.administrator.taoyuan.pojo.User;
import com.example.administrator.taoyuan.utils.HttpUtils;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_tab;
    private ImageView iv_photo1;
    private RadioButton rb_home;
    private RadioButton rb_ll;
    private RadioButton rb_shq;
    private RadioButton rb_my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JPushInterface.setDebugMode(true);//如果时正式版就改成false
        JPushInterface.init(this);

        String alias=String.valueOf(((MyApplication)getApplication()).getUser().getUserId());
//设置别名，单对单传递
        JPushInterface.setAlias(this, //上下文对象
                alias, //别名
                new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        Log.d("alias", "set alias result is" + i);
                    }
                });


//        //设置Tag，群组
//        Set<String> sets = new HashSet<>();
////        sets.add("sport");//运行第二个模拟器上时把这个注掉
//        sets.add("game");
//        sets.add("music");//运行第二个模拟器上时把这个打开
//
//        JPushInterface.setTags(this, sets, new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//                Log.d("alias", "set tag result is" + i);
//            }
//        });

        initview();
        initdata();
    }

    private void initview() {
        rg_tab = ((RadioGroup) findViewById(R.id.rg_tab));
        rb_home = ((RadioButton) findViewById(R.id.rb_rb1));
        rb_ll = ((RadioButton) findViewById(R.id.rb_rb2));
        rb_shq = ((RadioButton) findViewById(R.id.rb_rb3));
        rb_my = ((RadioButton) findViewById(R.id.rb_rb4));
    }

    private void initdata() {
        rb_home.setChecked(true);
        switchFragment(new home());
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment=null;
                switch (checkedId){
                    case R.id.rb_rb1:
                        fragment=new home();
                        break;
                    case R.id.rb_rb2:
                        fragment=new linli();
                        break;
                    case R.id.rb_rb3:
                        fragment=new Life();
                        break;
                    case R.id.rb_rb4:
                        fragment=new my();
                        break;
                }
                switchFragment(fragment);
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commit();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switchFragment(new my());
//
//    }


}
