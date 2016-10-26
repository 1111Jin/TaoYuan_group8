package com.example.administrator.taoyuan.fragment;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_life.LifeXiangqing;
import com.example.administrator.taoyuan.activity_life.fabu;
import com.example.administrator.taoyuan.pojo.ListInfo;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.RefreshListView;
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


public class lifeHot extends Life implements RefreshListView.OnRefreshUploadChangeListener{

    private RefreshListView lv_lifeinfo;
    BaseAdapter adapter;
    List<ListLifeInfo.LifeInfo> lifelist= new ArrayList<ListLifeInfo.LifeInfo>();
    private  ListView lv_dongtai1;
    ListInfo listinfo;
    public static final Integer REQUSETCODE=1;
    private Button tv_fabu;
    int orderFlag=0;
    int pageNo=1;
    int pageSize=7;
    boolean flag11 = true;
    Handler handler=new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_life_list,null);
        lv_lifeinfo = ((RefreshListView) view.findViewById(R.id.lv_dongtai_life));

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
                intent.putExtra("lifeinfo", lifelist.get(position-1));

                startActivityForResult(intent, 1);

            }
        });

        lv_lifeinfo.setOnRefreshUploadChangeListener(this);


    }


    public void initData() {
        adapter=new BaseAdapter() {


            @Override
            public int getCount() {
                return lifelist.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {


                View view1=  View.inflate(getActivity(), R.layout.activity_list_lv_dongtai_item,null);
                ImageView iv_photo = ((ImageView) view1.findViewById(R.id.iv_photo));
                ImageView iv_contphoto = ((ImageView) view1.findViewById(R.id.iv_contphoto));
                TextView  tv_title= ((TextView) view1.findViewById(R.id.tv_title));
                TextView tv_name= ((TextView) view1.findViewById(R.id.tv_name));


                ListLifeInfo.LifeInfo dongtai=lifelist.get(position);
                try {
                    tv_title.setText(URLDecoder.decode(dongtai.userName,"utf-8"));
                    tv_name.setText(URLDecoder.decode(dongtai.content,"utf-8"));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // x.image().bind(iv_photo,"http://10.40.5.12:8080/Life/imags/"+dongtai.headphoto+"");
                xUtilsImageUtils.display(iv_photo, HttpUtils.localhost_jt+"imgs/"+dongtai.headphoto+"",true);
                xUtilsImageUtils.display(iv_contphoto, HttpUtils.localhost_jt+"imgs/"+dongtai.content_photo+"",false);
                return view1;
            }
        };
        lv_lifeinfo.setAdapter(adapter);
        getLifeInfoList();
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
                Toast.makeText(lifeHot.this.getActivity(),ex.toString(),Toast.LENGTH_LONG).show();
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


