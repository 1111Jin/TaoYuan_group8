package com.example.administrator.taoyuan.activity_my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ReListActivityBean;

public class GetItemRepair extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_item_repair);

//        tv = ((TextView) findViewById(R.id.tv_1));

//        Intent intent=getIntent();
//        ReListActivityBean.Repair re=intent.getParcelableExtra("item");
//
//        tv.setText(re.toString());
    }
}
