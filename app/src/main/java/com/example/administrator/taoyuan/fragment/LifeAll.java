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
import com.example.administrator.taoyuan.activity_life.LifeXiangqing;
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

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class LifeAll extends Life implements RefreshListView.OnRefreshUploadChangeListener{

    private RefreshListView lv_lifeinfo;
    BaseAdapter adapter;
    final List<ListLifeInfo.LifeInfo> lifelist= new ArrayList<ListLifeInfo.LifeInfo>();
    private  ListView lv_dongtai1;
    ListInfo listinfo;
    public static final Integer REQUSETCODE=1;
    private Button tv_fabu;

    int pageNo=1;
    int pageSize=7;
    boolean flag11 = true;
    Handler handler=new Handler();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor1;
    ViewHolder viewHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_life_list,null);
        lv_lifeinfo = ((RefreshListView) view.findViewById(R.id.lv_dongtai_life));
        sharedPreferences = LifeAll.this.getActivity().getSharedPreferences("dianzan_sp", Context.MODE_PRIVATE);
        editor1 = sharedPreferences.edit();
//        ImageView view3=new ImageView(getActivity());
//        view3.setAdjustViewBounds(true);//去掉上下空白
//        view3.setImageResource( R.drawable.background);
//        lv_lifeinfo.addHeaderView(view3,null,false);

        initEvent();
        initData();
        return view;


    }



    public  void initView() {

    }


    public void initEvent() {

        lv_lifeinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转
                Log.i("点击事件", "onItemClick: " + position);

                Intent intent = new Intent(getActivity(), LifeXiangqing.class);

                //点击item的信息
                intent.putExtra("lifeinfo", lifelist.get(position - 1));

                startActivityForResult(intent, 1);

            }
        });

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
                // x.image().bind(iv_photo,"http://10.40.5.12:8080/Life/imags/"+dongtai.headphoto+"");
                xUtilsImageUtils.display(iv_photo, HttpUtils.localhost_jt+"imgs/"+dongtai.headphoto+"",true);
                xUtilsImageUtils.display(iv_contphoto, HttpUtils.localhost_jt+"imgs/"+dongtai.content_photo+"",false);
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

    //  http://localhost:8080/Life/getdongraibypage?orderFlag=0&pageNo=1&pageSize=1
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


//                if (flag11) {
//                    lifelist.clear();
//                } else {
//                    if (bean.lifeinfolist.size() == 0) {//服务器没有返回新的数据
//                        pageNo--; //下一次继续加载这一页
//                        Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
//                        lv_lifeinfo.completeLoad();//没获取到数据也要改变界面
//                        return;
//                    }
//                }
                lifelist.addAll(bean.lifeinfolist);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(LifeAll.this.getActivity(),ex.toString(),Toast.LENGTH_LONG).show();
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
}










