package com.example.administrator.taoyuan;



import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.taoyuan.fragment.Life;
import com.example.administrator.taoyuan.fragment.home;
import com.example.administrator.taoyuan.fragment.linli;
import com.example.administrator.taoyuan.fragment.my;

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
//        this.getFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commit();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,fragment).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switchFragment(new my());

    }


}
