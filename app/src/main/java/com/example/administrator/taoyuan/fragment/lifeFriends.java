package com.example.administrator.taoyuan.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;


public class lifeFriends extends Life {

    private ListView lv_lifeinfo;
    BaseAdapter adapter;
    final ArrayList<ListLifeInfo.LifeInfo> lifelist= new ArrayList<ListLifeInfo.LifeInfo>();
    private  ListView lv_dongtai1;
    ListInfo listinfo;
    public static final Integer REQUSETCODE=1;
    private Button tv_fabu;
    int orderFlag=0;
    int pageNo=1;
    int pageSize=2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_life_list,null);
        lv_lifeinfo = ((ListView) view.findViewById(R.id.lv_dongtai_life));

        ImageView view3=new ImageView(getActivity());
        view3.setAdjustViewBounds(true);//去掉上下空白
        view3.setImageResource( R.drawable.background);
        lv_lifeinfo.addHeaderView(view3,null,false);

        initData();
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
        super.onActivityCreated(savedInstanceState);
    }

    public  void initView() {

    }


    public void initEvent() {

        lv_lifeinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转
                Log.i("点击事件", "onItemClick: "+position);

                Intent intent=new Intent(getActivity(), LifeXiangqing.class);

                //点击item的信息
                intent.putExtra("lifeinfo", lifelist.get(position-1));

                startActivityForResult(intent,1);

            }
        });




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
                xUtilsImageUtils.display(iv_photo, HttpUtils.localhost_jt+"imags/"+dongtai.headphoto+"",true);
                xUtilsImageUtils.display(iv_contphoto, HttpUtils.localhost_jt+"imags/"+dongtai.content_photo+"",false);
                return view1;
            }
        };
        lv_lifeinfo.setAdapter(adapter);
        getLifeInfoList();
    }

//  http://localhost:8080/Life/getdongraibypage?orderFlag=0&pageNo=1&pageSize=1
    private void getLifeInfoList() {
        RequestParams params = new RequestParams(HttpUtils.localhost_jt+"getdongraibypage");
        params.addQueryStringParameter("orderFlag",orderFlag+"");//排序标记
        params.addQueryStringParameter("pageNo",pageNo+"");
        params.addQueryStringParameter("pageSize",pageSize+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson=new Gson();
                ListLifeInfo bean= gson.fromJson(result, ListLifeInfo.class);
//                System.out.println(bean.status+"----");
//                System.out.println(bean.lifeinfolist.size()+"===");
                lifelist.addAll( bean.lifeinfolist);


                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(lifeFriends.this.getActivity(),ex.toString(),Toast.LENGTH_LONG).show();
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

}


