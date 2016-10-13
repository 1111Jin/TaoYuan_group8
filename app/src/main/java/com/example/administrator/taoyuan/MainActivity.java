package com.example.administrator.taoyuan;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.administrator.taoyuan.fragment.Life;
import com.example.administrator.taoyuan.fragment.home;
import com.example.administrator.taoyuan.fragment.linli;
import com.example.administrator.taoyuan.fragment.my;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_tab;
    private ImageView iv_photo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        initview();
        initdata();


    }



    private void initview() {
        switchFragment(new Life());
        rg_tab = ((RadioGroup) findViewById(R.id.rg_tab));
    }

    private void initdata() {
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
        this.getFragmentManager().beginTransaction().replace(R.id.fl_content,fragment).commit();
    }

}
