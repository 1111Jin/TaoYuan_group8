package com.example.administrator.taoyuan.activity_home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
    @InjectView(R.id.iv2)
    ImageView iv2;
    GongGaoBean gongGaoBean = new GongGaoBean();
    GongGaoBean.GongGao gonggao;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonggao_xiangqing);
        ButterKnife.inject(this);
        String str = getIntent().getStringExtra("flag");
        if (str.equals("1")) {
             gonggao = (GongGaoBean.GongGao) getIntent().getSerializableExtra("gonggaoList");
            url=Netutil.url + "image/" + gonggao.getGonggaoImg() + ".png";
            tv.setText(gonggao.getGonggaoTitle());
            tv1.setText(gonggao.getGonggaofabiaoTime());
            tv2.setText(gonggao.getGonggaoContent());
            Picasso.with(GonggaoXiangqing.this).load(url).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE);
            Picasso.with(GonggaoXiangqing.this).load(url).placeholder(R.drawable.load).error(R.mipmap.ic_launcher).into(iv1);
        } else if (str.equals("2")) {
            gonggao = (GongGaoBean.GongGao) getIntent().getSerializableExtra("gonggaolist");
            url=Netutil.url + "image/" + gonggao.getGonggaoImg() + ".png";
            tv.setText(gonggao.getGonggaoTitle());
            tv1.setText(gonggao.getGonggaofabiaoTime());
            tv2.setText(gonggao.getGonggaoContent());
            Picasso.with(GonggaoXiangqing.this).load(url).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE);
            Picasso.with(GonggaoXiangqing.this).load(url).placeholder(R.drawable.load).error(R.drawable.error).into(iv1);
        }

    }

    @OnClick({R.id.iv, R.id.iv2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv:
                finish();
                break;
            case R.id.iv2:
                showShare1();
                break;
        }
    }
    private void showShare1() {
        ShareSDK.initSDK(GonggaoXiangqing.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(gonggao.getGonggaoTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        //oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(gonggao.getGonggaoContent());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(url);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://10.40.5.24:8080/webpro6/index.jsp");

// 启动分享GUI
        oks.show(GonggaoXiangqing.this);
    }
}
