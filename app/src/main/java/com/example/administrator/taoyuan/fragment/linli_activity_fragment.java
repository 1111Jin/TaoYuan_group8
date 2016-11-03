package com.example.administrator.taoyuan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_linli.ActivityInfo;
import com.example.administrator.taoyuan.pojo.Activity;
import com.example.administrator.taoyuan.pojo.Comment;
import com.example.administrator.taoyuan.pojo.User;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.taoyuan.utils.DateUtil.dateToString;
import static com.example.administrator.taoyuan.utils.DateUtil.dateToString1;

/**
 * Created by Administrator on 2016/9/22.
 */
public class linli_activity_fragment extends linli {
    private static final String TAG = "linli_activity_fragment";
    private ImageView iv_people;
    private TextView tv_time;
    private TextView tv_title;
    private BaseAdapter adapter;
    List<Activity> activityList = new ArrayList<Activity>();

    private ListView lv_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.linli_activity_listview,null);
        lv_list  = ((ListView) view.findViewById(R.id.ac_listview));
        initData();
        return view;
    }

    @Override
    public void onStart() {
        initData();
        super.onStart();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initEvent();
        super.onActivityCreated(savedInstanceState);

    }


    //跳转到另一个activity（具体的单个信息显示页面）
    public void initEvent(){
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //从Fragment跳转到非嵌套的Activity页面
                Intent intent = new Intent(getActivity(), ActivityInfo.class);
                //带参传值；
                List<Comment> coList  = activityList.get(position).getList();
                intent.putExtra("ActivityInfo", activityList.get(position));
                intent.putExtra("comment",(Serializable) coList);
                Log.i("222","商品信息：======="+activityList.get(position));
                //获取响应码，开启一个Activity；
//                startActivityForResult(intent,REQUESTCODE);
                startActivity(intent);
            }
        });
    }

    //获取服务端传递过来的数据；
    public void initData() {
        adapter = new BaseAdapter() {
            private TextView time;
            private View line;
            private TextView address;

            @Override
            public int getCount() {
                return activityList.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = View.inflate(getActivity(),R.layout.activity_list_view_item,null);
                Activity activity = activityList.get(position);
                iv_people = ((ImageView) view.findViewById(R.id.iv_people));
                tv_time = ((TextView) view.findViewById(R.id.tv_time));
                tv_title = ((TextView) view.findViewById(R.id.tv_title));
                address = ((TextView) view.findViewById(R.id.tv_address));
                line = ((View) view.findViewById(R.id.vv_line));

                time = ((TextView) view.findViewById(R.id.time3));

                xUtilsImageUtils.display(iv_people,HttpUtils.localhost_su+activity.getActivityImg()+"",false);
                tv_title.setText(activity.getActivityTitle());
                address.setText(activity.getActivityAddress());
                tv_time.setText(dateToString(activity.getCreateTime()));
                time.setText(dateToString1(activity.getBeginTime())+" -- "+dateToString1(activity.getEndTime()));

                return view;
            }

        };
        lv_list.setAdapter(adapter);


        //从服务器拿数据；
        getActivityList();

    }


    private void getActivityList(){

        RequestParams params = new RequestParams(HttpUtils.localhost_su+"queryactivity");


        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.i("linli_activity_fragment", "onSuccess:activity数据传递进来了： =====>"+result);

//                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
                Gson gson = new Gson();
                Type type=new TypeToken<List<Activity>>(){}.getType();
                activityList = gson.fromJson(result,type);

//                activityList.clear();
                Log.i("linli_activity_fragment", "onSuccess:activityBean对象:-------> "+activityList);

//                activityList.addAll(newacList);


                System.out.println("ActivityList:"+activityList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("linli_activity_fragment", "onError: ------->>>错误："+ex);
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
