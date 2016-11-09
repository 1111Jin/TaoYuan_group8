package com.example.administrator.taoyuan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_home.RefreshableView;
import com.example.administrator.taoyuan.pojo.Activity;
import com.example.administrator.taoyuan.pojo.ActivityInfo;
import com.example.administrator.taoyuan.pojo.Comment;
import com.example.administrator.taoyuan.utils.CommonAdapter;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.ViewHolder;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.taoyuan.utils.DateUtil.dateToString1;

/**
 * Created by mawuyang on 2016-10-23.
 */
public class ActivityByMeFragment extends BaseFragment {

    private ListView listview;
    List<Activity> aclist = new ArrayList<Activity>();
    CommonAdapter<Activity> adapter;
    private Integer userId;
    RefreshableView refreshableView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_all_repair,null);
        listview = ((ListView) view.findViewById(R.id.lv_repair_listview));
        refreshableView = ((RefreshableView) view.findViewById(R.id.refreshable_view));

        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener(){

            @Override
            public void onRefresh() {
                aclist.clear();
                try {
                    Thread.sleep(3000);
                    getActivityList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        },0);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if(bundle!=null){
            userId=bundle.getInt("userId");
        }

        return view;
    }

    @Override
    public void initView() {

        getActivityList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //从Fragment跳转到非嵌套的Activity页面
                Intent intent = new Intent(getActivity(), com.example.administrator.taoyuan.activity_linli.ActivityInfo.class);
                //带参传值；
                List<Comment> coList  = aclist.get(position).getList();
                intent.putExtra("ActivityInfo", aclist.get(position));
                intent.putExtra("comment",(Serializable) coList);
                Log.i("222","商品信息：======="+aclist.get(position));
                //获取响应码，开启一个Activity；
//                startActivityForResult(intent,REQUESTCODE);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }


    public void getActivityList(){

        RequestParams params = new RequestParams(HttpUtils.localhost_su + "/getAcById?userId=" +userId);
//        params.addBodyParameter("repairState","已派员");
        System.out.println(params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("new  "+result);
//                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm")
//                        .create();
                Gson gson = new Gson();
                Type type=new TypeToken<List<Activity>>(){}.getType();

                List<Activity> alist=new ArrayList<Activity>();
                alist=gson.fromJson(result,type);
                aclist.clear();
                aclist.addAll(alist);



                if (adapter == null) {
                    adapter = new CommonAdapter<Activity>(getActivity(), aclist, R.layout.activity_list_view_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, Activity activity, int position) {
                            //设置item中控件的取值
                            Log.i("123123", "convert: " + position);

                            initItemView(viewHolder, activity, position);
                        }
                    };
                    System.out.println(11);
                    listview.setAdapter(adapter);
                }else{
//                    listview.clear;
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("UnpersonnalFragment", "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void initItemView(ViewHolder viewHolder, Activity activity, int position) {
        ImageView iv_img = ((ImageView) viewHolder.getViewById(R.id.iv_people));
        TextView tv_title = ((TextView) viewHolder.getViewById(R.id.tv_title));
        TextView tv_createTime = ((TextView) viewHolder.getViewById(R.id.tv_time));
        TextView tv_address = ((TextView) viewHolder.getViewById(R.id.tv_address));
        TextView tv_time = ((TextView) viewHolder.getViewById(R.id.time3));
//        TextView tv_join = ((TextView) viewHolder.getViewById(R.id.tv_num));

        x.image().bind(iv_img,HttpUtils.localhost_su+activity.getActivityImg());
        tv_title.setText(activity.getActivityTitle());
        tv_address.setText(activity.getActivityAddress());
        tv_createTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.getCreateTime()));
        tv_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.getBeginTime()));
//        tv_join.setText(activity.getJoinNums().toString());
    }
    }
