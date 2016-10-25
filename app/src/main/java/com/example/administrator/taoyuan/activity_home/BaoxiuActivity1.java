package com.example.administrator.taoyuan.activity_home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.fragment.Baoxiuleixing_gerenzhuzha_Fragment;
import com.example.administrator.taoyuan.fragment.Baoxiuleixing_gonggongquyu_Fragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BaoxiuActivity1 extends AppCompatActivity {
    @InjectView(R.id.bt1)
    Button bt1;
    @InjectView(R.id.bt2)
    Button bt2;
    String a;
    Bundle bundle=new Bundle();
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoxiu1);
        ButterKnife.inject(this);
        bundle.putString("c","");
        fragment=new Baoxiuleixing_gerenzhuzha_Fragment();
        fragment.setArguments(bundle);
        switchFragment(fragment);
    }

    @OnClick({R.id.bt1, R.id.bt2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                bt1.setEnabled(false);
                bt2.setEnabled(true);
                a=bt1.getText().toString();
                fragment=new Baoxiuleixing_gerenzhuzha_Fragment();
                bundle.putString("c",a);
                fragment.setArguments(bundle);
                break;
            case R.id.bt2:
                bt2.setEnabled(false);
                bt1.setEnabled(true);
                a=bt2.getText().toString();
                fragment=new Baoxiuleixing_gonggongquyu_Fragment();
                bundle.putString("d",a);
                fragment.setArguments(bundle);
                break;
        }
        switchFragment(fragment);
    }
    private void switchFragment(Fragment fragment) {
        this.getFragmentManager().beginTransaction().replace(R.id.fl,fragment).commit();
    }
}
