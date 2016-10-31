package com.example.administrator.taoyuan.activity_home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.GongGaoBean;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GonggaoXiangqing extends AppCompatActivity {
    @InjectView(R.id.iv)
    ImageView iv;
    @InjectView(R.id.rl)
    RelativeLayout rl;
    @InjectView(R.id.tv)
    TextView tv;
    @InjectView(R.id.tv1)
    TextView tv1;
    @InjectView(R.id.tv2)
    TextView tv2;
    @InjectView(R.id.iv1)
    ImageView iv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GongGaoBean gongGaoBean = new GongGaoBean();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonggao_xiangqing);
        ButterKnife.inject(this);
         String str=getIntent().getStringExtra("flag");
        if(str.equals("1")) {
            GongGaoBean.GongGao gonggao = (GongGaoBean.GongGao)getIntent().getSerializableExtra("gonggaoList");
            tv.setText(gonggao.getGonggaoTitle());
            tv1.setText(gonggao.getGonggaofabiaoTime());
            tv2.setText(gonggao.getGonggaoContent());
            Picasso.with(GonggaoXiangqing.this).load(Netutil.url+"image/"+gonggao.getGonggaoImg()+".png").networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE);
            Picasso.with(GonggaoXiangqing.this).load(Netutil.url+"image/"+gonggao.getGonggaoImg()+".png").placeholder(R.drawable.load).error(R.mipmap.ic_launcher).into(iv1);
        }else if (str.equals("2")){
            GongGaoBean.GongGao gonggao = (GongGaoBean.GongGao)getIntent().getSerializableExtra("gonggaolist");
            tv.setText(gonggao.getGonggaoTitle());
            tv1.setText(gonggao.getGonggaofabiaoTime());
            tv2.setText(gonggao.getGonggaoContent());
            Picasso.with(GonggaoXiangqing.this).load(Netutil.url+"image/"+gonggao.getGonggaoImg()+".png").networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE);
            Picasso.with(GonggaoXiangqing.this).load(Netutil.url+"image/"+gonggao.getGonggaoImg()+".png").placeholder(R.drawable.load).error(R.mipmap.ic_launcher).into(iv1);
        }

    }

    @OnClick(R.id.iv)
    public void onClick() {
        finish();
    }
}
