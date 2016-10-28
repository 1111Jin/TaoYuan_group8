package com.example.administrator.taoyuan.activity_my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.utils.TitleBar;

public class LiveActivity extends AppCompatActivity {

    private TitleBar tt_live;
    private ListView lv_live;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        initView();
        initData();
    }

    public void initView(){
        tt_live = ((TitleBar) findViewById(R.id.tt_live));
        lv_live = ((ListView) findViewById(R.id.lv_live));
    }

    public void initData(){
        tt_live.setLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
