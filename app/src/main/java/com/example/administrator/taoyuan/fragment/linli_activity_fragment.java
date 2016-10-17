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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_linli.ActivityInfo;
import com.example.administrator.taoyuan.pojo.ActivityBean;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class linli_activity_fragment extends linli {
    private ListView lv_list;
    private ImageView iv_people;
    private TextView tv_time;
    private TextView tv_title;

    private BaseAdapter adapter;
    View view;
    final List<ActivityBean.Activity> activityList = new ArrayList<ActivityBean.Activity>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_linli,null);
        initView();
        initData();
        initEvent();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void initView() {
        lv_list = ((ListView) view.findViewById(R.id.lv_activity));
    }

    public void initEvent(){
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //从Fragment跳转到非嵌套的Activity页面
                Intent intent = new Intent(getActivity(), ActivityInfo.class);
                //带参传值；
                intent.putExtra("ActivityInfo",activityList.get(position));
                Log.i("222","商品信息：======="+activityList.get(position));
                //获取响应码，开启一个Activity；
//                startActivityForResult(intent,REQUESTCODE);
                startActivity(intent);
            }
        });
    }

    public void initData() {
        adapter = new BaseAdapter() {
            private TextView address;

            @Override
            public int getCount() {
                return activityList.size();
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

                View view = View.inflate(getActivity(),R.layout.activity_list_view_item,null);
                ActivityBean.Activity activity = activityList.get(position);
                iv_people = ((ImageView) view.findViewById(R.id.iv_people));
//                tv_time = ((TextView) view.findViewById(R.id.tv_time));
                tv_title = ((TextView) view.findViewById(R.id.tv_title));
                address = ((TextView) view.findViewById(R.id.tv_address));



                xUtilsImageUtils.display(iv_people,"http://10.40.5.23:8080/cmty/upload/"+activity.activity_photo+"",false);
                tv_title.setText(activity.activity_title);
                address.setText(activity.activity_adress);

                return view;
            }

        };
        lv_list.setAdapter(adapter);


        //从服务器拿数据；
        getActivityList();

    }


    private void getActivityList(){

        RequestParams params = new RequestParams("http://10.40.5.23:8080/cmty/tomain");


        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.i("linli_activity_fragment", "onSuccess: =====>"+result);

                Gson gson = new Gson();

                ActivityBean bean = gson.fromJson(result,ActivityBean.class);

                activityList.addAll(bean.activityList);


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
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
