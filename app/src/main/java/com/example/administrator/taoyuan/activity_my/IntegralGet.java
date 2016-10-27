package com.example.administrator.taoyuan.activity_my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.utils.TitleBar;

public class IntegralGet extends AppCompatActivity {

    private TitleBar tt_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_get);

        tt_get = ((TitleBar) findViewById(R.id.tt_get));
        tt_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
