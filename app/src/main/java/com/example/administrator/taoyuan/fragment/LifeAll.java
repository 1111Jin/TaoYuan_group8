package com.example.administrator.taoyuan.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_life.LifeToFriend;
import com.example.administrator.taoyuan.activity_life.LifeXiangqing;
import com.example.administrator.taoyuan.activity_my.MyFriend;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.ListInfo;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.example.administrator.taoyuan.pojo.Zan;
import com.example.administrator.taoyuan.utils.CommonAdapter;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.RefreshListView;
import com.example.administrator.taoyuan.utils.ViewHolder;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class LifeAll extends Life implements RefreshListView.OnRefreshUploadChangeListener{

    private RefreshListView lv_lifeinfo;
    CommonAdapter<ListLifeInfo.LifeInfo> adapter;
    final List<ListLifeInfo.LifeInfo> lifelist= new ArrayList<ListLifeInfo.LifeInfo>();
    private  ListView lv_dongtai1;
    ListInfo listinfo;
    public static final Integer REQUSETCODE=1;
    private Button tv_fabu;

    int pageNo=1;
    int pageSize=4;
    boolean flag11 = false;
    Handler handler=new Handler();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor1;
    ViewHolder viewHolder;
    private ImageButton ib_share;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_life_list,null);
        lv_lifeinfo = ((RefreshListView) view.findViewById(R.id.lv_dongtai_life));
        sharedPreferences = LifeAll.this.getActivity().getSharedPreferences("dianzan_sp", Context.MODE_PRIVATE);
        editor1 = sharedPreferences.edit();
        ib_share = ((ImageButton) view.findViewById(R.id.share));


        initData();
        initEvent();
        return view;


    }





    public void initEvent() {
//        lv_lifeinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //跳转
//                Log.i("点击事件", "onItemClick: " + position);
//
//                Intent intent = new Intent(getActivity(), LifeXiangqing.class);
//
//                //点击item的信息
//                intent.putExtra("lifeinfo", lifelist.get(position - 1));
//
//                startActivityForResult(intent, 1);
//
//            }
//        });
        lv_lifeinfo.setOnRefreshUploadChangeListener(this);

    }
    public void initData() {
        adapter=new CommonAdapter<ListLifeInfo.LifeInfo>(getActivity(),lifelist,R.layout.activity_list_lv_dongtai_item) {
            @Override
            public void convert(ViewHolder viewHolder, final ListLifeInfo.LifeInfo lifeInfo, final int position) {

                final View view1=  View.inflate(getActivity(), R.layout.activity_list_lv_dongtai_item,null);
                ImageView iv_photo = viewHolder.getViewById(R.id.iv_photo);
                ImageView iv_contphoto = viewHolder.getViewById(R.id.iv_contphoto);
                TextView  tv_title= viewHolder.getViewById(R.id.tv_title);
                TextView tv_name= viewHolder.getViewById(R.id.tv_name);
                final ImageButton iv_zan = viewHolder.getViewById(R.id.iv_zan);



                final ListLifeInfo.LifeInfo dongtai=lifelist.get(position);
                try {
                    tv_title.setText(URLDecoder.decode(dongtai.userName,"utf-8"));
                    tv_name.setText(URLDecoder.decode(dongtai.content,"utf-8"));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                xUtilsImageUtils.display(iv_photo, HttpUtils.localhost_jt+"imgs/"+dongtai.headphoto+"",true);
                xUtilsImageUtils.display(iv_contphoto, HttpUtils.localhost_jt+"imgs/"+dongtai.content_photo+"",false);

                iv_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), LifeToFriend.class);
                        intent.putExtra("lifeinfo", lifelist.get(position));
                        startActivityForResult(intent, 1);
                    }
                });

                iv_zan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sharedPreferences.contains(position+"")){//选中状态
                            iv_zan.setImageResource(R.drawable.zan);
                            int user_Id = ((MyApplication) getActivity().getApplication()).getUser().getUserId();    //点赞人Id
                            int dontai_Id = dongtai.dontaiId;
                            removeZan(user_Id, dontai_Id);
                            Toast.makeText(getActivity(),"取消点赞",Toast.LENGTH_SHORT).show();
                            editor1.remove(position+"");
                        }else{
                            iv_zan.setImageResource(R.drawable.zan1);
                            int user_Id = ((MyApplication) getActivity().getApplication()).getUser().getUserId();    //点赞人Id
                            int dontai_Id = dongtai.dontaiId;                                                  //动态Id
                            String zanTime = String.valueOf(System.currentTimeMillis());                             //点赞时间
                            addZan(user_Id, dontai_Id, zanTime);
                            Toast.makeText(getActivity(),"点赞成功",Toast.LENGTH_SHORT).show();
                            editor1.putInt(position+"",(Integer)iv_zan.getTag());
                        }
                        editor1.commit();
                    }
                });
                iv_zan.setTag(dongtai.dontaiId);
                if(sharedPreferences.contains(position+"")){
                    iv_zan.setImageResource(R.drawable.zan1);
                }else {
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


        lv_lifeinfo.setAdapter(adapter);

        getLifeInfoList();
    }

    private void addZan(int user_Id, int dontai_Id, String zanTime) {
        RequestParams params = new RequestParams(HttpUtils.localhost_jt + "AddZanServlet");
        params.addBodyParameter("userId", String.valueOf(user_Id));
        params.addBodyParameter("dongtaiId", String.valueOf(dontai_Id));
        params.addBodyParameter("zanTime", zanTime);
        System.out.println(user_Id+"onSuccess ++6666666666666");
        System.out.println(dontai_Id+"onSuccess ++6666666666666");
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
        System.out.println(user_Id+"onSuccess --6666666666666");
        System.out.println(dontai_Id+"onSuccess --6666666666666");
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

    //  http://localhost:8080/Life/getdongraibypage?pageNo=1&pageSize=1
    private void getLifeInfoList() {
        RequestParams params = new RequestParams(HttpUtils.localhost_jt+"getdongraibypage");

        params.addQueryStringParameter("pageNo",pageNo+"");
        params.addQueryStringParameter("pageSize",pageSize+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("1"+result);
                Gson gson=new Gson();
//                Type type = new TypeToken<List<ListLifeInfo.LifeInfo>>() {
//                }.getType();
//                List<ListLifeInfo.LifeInfo> bean = new ArrayList<ListLifeInfo.LifeInfo>();

                ListLifeInfo bean = gson.fromJson(result, ListLifeInfo.class);
//
                if (flag11) {
                    lifelist.clear();
                }
//                else {
//                    if (bean.lifeinfolist.size() == 0) {//服务器没有返回新的数据
//                        System.out.println("=====++++++====="+bean.lifeinfolist.size());
//                        pageNo--; //下一次继续加载这一页
//                        Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
//                        lv_lifeinfo.completeLoad();//没获取到数据也要改变界面
//                        return;
//                    }
//                }
                lifelist.addAll(bean.lifeinfolist);

                System.out.println("pppppppp"+bean.lifeinfolist);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "无法获取网络数据，请检查网络连接", Toast.LENGTH_SHORT).show();
                System.out.println(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void onRefresh() {
        pageNo = 1; //每次刷新，让pageNo变成初始值1
        //1秒后发一个消息
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flag11 = true;
                getLifeInfoList();  //再次获取数据
                lv_lifeinfo.completeRefresh();  //刷新数据后，改变页面为初始页面：隐藏头部
                Toast.makeText(getActivity(), "数据已更新", Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }

    @Override
    public void onPull() {
        pageNo++;
        //原来数据基础上增加
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flag11 = false;
                getLifeInfoList();
            }
        }, 1000);
    }


    private void showShare() {
        ShareSDK.initSDK(getActivity());
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
        oks.setText("桃园分享");
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
        oks.show(getActivity());
    }






}










