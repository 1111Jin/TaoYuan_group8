package com.example.administrator.taoyuan.activity_my;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.example.administrator.taoyuan.pojo.ListUserBean;
import com.example.administrator.taoyuan.utils.CommonAdapter;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.TitleBar;
import com.example.administrator.taoyuan.utils.ViewHolder;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class LiveActivity extends AppCompatActivity {

    private TitleBar tt_live;
    private ListView lv_live;
    CommonAdapter<ListLifeInfo.LifeInfo> adapter;
    List<ListLifeInfo.LifeInfo> livelist = new ArrayList<ListLifeInfo.LifeInfo>();

    int pageNo = 1;
    int pageSize = 7;
    Handler handler = new Handler();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor1;
    ViewHolder viewHolder;
    private Integer userId1 = HttpUtils.userId;
    ListUserBean.User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        sharedPreferences = LiveActivity.this.getApplicationContext().getSharedPreferences("dianzan_sp", Context.MODE_PRIVATE);
        editor1 = sharedPreferences.edit();
        Intent intent=getIntent();
        user = intent.getParcelableExtra("user");

        if(user!=null) {
            userId1 = user.friendId;
        }
        initView();
        initData();
    }

    public void initView() {
        tt_live = ((TitleBar) findViewById(R.id.tt_live));
        lv_live = ((ListView) findViewById(R.id.lv_live));
    }

    public void initData() {
        if (!userId1.equals(HttpUtils.userId)) {
            TextView titlestr = ((TextView) tt_live.findViewById(R.id.title));
            titlestr.setText("他的动态");

        }
        tt_live.setLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new CommonAdapter<ListLifeInfo.LifeInfo>(getApplicationContext(), livelist, R.layout.activity_list_lv_dongtai_item) {
            @Override
            public void convert(ViewHolder viewHolder, ListLifeInfo.LifeInfo lifeInfo, final int position) {
                ImageView iv_photo = viewHolder.getViewById(R.id.iv_photo);
                ImageView iv_contphoto = viewHolder.getViewById(R.id.iv_contphoto);
                TextView tv_title = viewHolder.getViewById(R.id.tv_title);
                TextView tv_name = viewHolder.getViewById(R.id.tv_name);
                final ImageButton iv_zan = viewHolder.getViewById(R.id.iv_zan);


                final ListLifeInfo.LifeInfo dongtai = livelist.get(position);
                try {
                    tv_title.setText(URLDecoder.decode(dongtai.userName, "utf-8"));
                    tv_name.setText(URLDecoder.decode(dongtai.content, "utf-8"));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                xUtilsImageUtils.display(iv_photo, HttpUtils.localhost_jt + "imgs/" + dongtai.headphoto + "", true);
                xUtilsImageUtils.display(iv_contphoto, HttpUtils.localhost_jt + "imgs/" + dongtai.content_photo + "", false);
                iv_zan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sharedPreferences.contains(position + "")) {//选中状态
                            iv_zan.setImageResource(R.drawable.zan);
                            int user_Id = ((MyApplication) getApplication()).getUser().getUserId();    //点赞人Id
                            int dontai_Id = dongtai.dontaiId;
                            removeZan(user_Id, dontai_Id);
                            Toast.makeText(getApplicationContext(), "取消点赞", Toast.LENGTH_SHORT).show();
                            editor1.remove(position + "");
                        } else {
                            iv_zan.setImageResource(R.drawable.zan1);
                            int user_Id = ((MyApplication) getApplication()).getUser().getUserId();    //点赞人Id
                            int dontai_Id = dongtai.dontaiId;                                                  //动态Id
                            String zanTime = String.valueOf(System.currentTimeMillis());                             //点赞时间
                            addZan(user_Id, dontai_Id, zanTime);
                            Toast.makeText(getApplicationContext(), "点赞成功", Toast.LENGTH_SHORT).show();
                            editor1.putInt(position + "", (Integer) iv_zan.getTag());
                        }
                        editor1.commit();
                    }
                });
                iv_zan.setTag(dongtai.dontaiId);
                if (sharedPreferences.contains(position + "")) {
                    iv_zan.setImageResource(R.drawable.zan1);
                } else {
                    iv_zan.setImageResource(R.drawable.zan);
                }

                viewHolder.getViewById(R.id.share).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        showShare();
                    }
                });
            }
        };

        lv_live.setAdapter(adapter);

        getLifeInfoList();
    }


    private void getLifeInfoList() {
        RequestParams params = new RequestParams(HttpUtils.localhost_jt + "getdongraibypage");

        params.addQueryStringParameter("pageNo", pageNo + "");
        params.addQueryStringParameter("pageSize", pageSize + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("1" + result);
                Gson gson = new Gson();
//                Type type = new TypeToken<List<ListLifeInfo.LifeInfo>>() {
//                }.getType();
//                List<ListLifeInfo.LifeInfo> bean = new ArrayList<ListLifeInfo.LifeInfo>();

                ListLifeInfo bean = gson.fromJson(result, ListLifeInfo.class);

//                if (flag11) {
//                    lifelist.clear();
//                } else {
//                    if (bean.size() == 0) {//服务器没有返回新的数据
//                        pageNo--; //下一次继续加载这一页
//                        Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
//                        lv_lifeinfo.completeLoad();//没获取到数据也要改变界面
//                        return;
//                    }
//                }
                livelist.addAll(bean.lifeinfolist);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void addZan(int user_Id, int dontai_Id, String zanTime) {
        RequestParams params = new RequestParams(HttpUtils.localhost_jt + "AddZanServlet");
        params.addBodyParameter("userId", String.valueOf(user_Id));
        params.addBodyParameter("dongtaiId", String.valueOf(dontai_Id));
        params.addBodyParameter("zanTime", zanTime);
        System.out.println(user_Id + "onSuccess ++6666666666666");
        System.out.println(dontai_Id + "onSuccess ++6666666666666");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void removeZan(int user_Id, int dontai_Id) {
        RequestParams params = new RequestParams(HttpUtils.localhost_jt + "DeleteZanServlet");
        params.addBodyParameter("userId", String.valueOf(user_Id));
        params.addBodyParameter("dongtaiId", String.valueOf(dontai_Id));
        System.out.println(user_Id + "onSuccess --6666666666666");
        System.out.println(dontai_Id + "onSuccess --6666666666666");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("onSuccess 6666666666666");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void showShare() {
        ShareSDK.initSDK(getApplicationContext());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(getApplicationContext());
    }
}
