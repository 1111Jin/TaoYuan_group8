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

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_linli.ActivityInfo;
import com.example.administrator.taoyuan.pojo.ActivityBean;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.administrator.taoyuan.utils.DateUtil.dateToString;

/**
 * Created by Administrator on 2016/9/22.
 */
public class linli_activity_fragment extends linli {
    private ImageView iv_people;
    private TextView tv_time;
    private TextView tv_title;
    private BaseAdapter adapter;
    final List<ActivityBean.Activity> activityList = new ArrayList<ActivityBean.Activity>();
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
    public void onActivityCreated(Bundle savedInstanceState) {


        initEvent();
        super.onActivityCreated(savedInstanceState);

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
                ActivityBean.Activity activity = activityList.get(position);
                iv_people = ((ImageView) view.findViewById(R.id.iv_people));
                tv_time = ((TextView) view.findViewById(R.id.tv_time));
                tv_title = ((TextView) view.findViewById(R.id.tv_title));
                address = ((TextView) view.findViewById(R.id.tv_address));
                time = ((TextView) view.findViewById(R.id.tv_ac_time3));
                line = ((View) view.findViewById(R.id.vv_line));


                xUtilsImageUtils.display(iv_people,"http://10.40.5.23:8080/cmty/"+activity.activity_photo+"",false);
                tv_title.setText(activity.activity_title);
                address.setText(activity.activity_adress);
                tv_time.setText(dateToString(activity.activity_time));
//                time.setText(activity.activity_begintime+"-"+activity.activity_endtime);
//                time.setText(dateToString(activity.activity_begintime));

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
                Log.i("linli_activity_fragment", "onSuccess:activity数据传递进来了： =====>"+result);

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
                Type type=new TypeToken<List<ActivityBean.Activity>>(){}.getType();
//                Gson gson = new Gson();
                List<ActivityBean.Activity> aclist = gson.fromJson(result,type);

                Log.i("linli_activity_fragment", "onSuccess:activityBean对象:-------> "+aclist.get(0));

                activityList.addAll(aclist);


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
