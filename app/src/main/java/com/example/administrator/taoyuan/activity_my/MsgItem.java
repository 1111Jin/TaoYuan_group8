package com.example.administrator.taoyuan.activity_my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.MsgBean;
import com.example.administrator.taoyuan.utils.TitleBar;

public class MsgItem extends AppCompatActivity {

    private TitleBar tt;
    private TextView title;
    private TextView content;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_item);

        tt = ((TitleBar) findViewById(R.id.titleBar));
        title = ((TextView) findViewById(R.id.tv_title));
        time = ((TextView) findViewById(R.id.tv_time));
        content = ((TextView) findViewById(R.id.tv_content));

        tt.setLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent=getIntent();
        MsgBean msg = intent.getParcelableExtra("msg");
        title.setText(msg.getTitle());
        content.setText(msg.getMsg());
        time.setText(msg.getTime()+"");
    }
}
