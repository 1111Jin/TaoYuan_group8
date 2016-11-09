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
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_home.RefreshableView;
import com.example.administrator.taoyuan.pojo.AcJoin;
import com.example.administrator.taoyuan.pojo.Activity;
import com.example.administrator.taoyuan.pojo.ActivityInfo;
import com.example.administrator.taoyuan.pojo.Comment;
import com.example.administrator.taoyuan.utils.CommonAdapter;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.ViewHolder;
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

/**
 * Created by mawuyang on 2016-10-24.
 */
public class ActivityByAttendFragment extends BaseFragment {

    private ListView lv_list;
    List<AcJoin> aclist = new ArrayList<AcJoin>();
    CommonAdapter<AcJoin> adapter;
    private Integer userId;
    RefreshableView refreshableView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_repair,null);
        lv_list = ((ListView) view.findViewById(R.id.lv_repair_listview));
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
        getActivityList();

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //从Fragment跳转到非嵌套的Activity页面
                Intent intent = new Intent(getActivity(), com.example.administrator.taoyuan.activity_linli.ActivityInfo.class);
                //带参传值；
                List<Comment> coList  = aclist.get(position).getComments();
//                RequestParams requestParams=new RequestParams(HttpUtils.localhost_su+"/queryacbyid");
//                requestParams.addBodyParameter("acId",String.valueOf(aclist.get(position).getActivityId()));
//                x.http().get(requestParams, new Callback.CommonCallback<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        System.out.println(result);
//                        Gson gson=new Gson();
//                        activity = gson.fromJson(result,Activity.class);
//                        if (activity != null) {
//                            tvAcTitle.setText(activity.getActivityTitle());
//                            tvAcName.setText(activity.getUser().getUserName());
//                            tvAcAcpro.setText(activity.getActivityContent());
//                            tvAcAddress.setText(activity.getActivityAddress());
//                            tvAcTime3.setText(dateToString1(activity.getBeginTime()) + " -- " + dateToString1(activity.getEndTime()));
//                            xUtilsImageUtils.display(ivAcImage, HttpUtils.localhost_su + activity.getActivityImg());
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable ex, boolean isOnCallback) {
//                        System.out.println("错误：："+ex.toString());
//                    }
//
//                    @Override
//                    public void onCancelled(CancelledException cex) {
//
//                    }
//
//                    @Override
//                    public void onFinished() {
//
//                    }
//                });
                intent.putExtra("ActivityInfo", aclist.get(position).getActivity());
                intent.putExtra("comment",(Serializable) coList);
                Log.i("222","商品信息：======="+aclist.get(position));
                //获取响应码，开启一个Activity；
//                startActivityForResult(intent,REQUESTCODE);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    public void getActivityList(){
        RequestParams params = new RequestParams(HttpUtils.localhost_su + "/getAc?userId=" + userId);
//        params.addBodyParameter("repairState","已派员");
        System.out.println(params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("attend"+result);
                Gson gson = new Gson();
                Type type=new TypeToken<List<AcJoin>>(){}.getType();
                List<AcJoin> alist=new ArrayList<AcJoin>();
                alist=gson.fromJson(result,type);
                aclist.clear();
                aclist.addAll(alist);

                System.out.println("Biaoshi "+aclist.get(0));

                if (adapter == null) {
                    adapter = new CommonAdapter<AcJoin>(getActivity(), aclist, R.layout.activity_list_view_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, AcJoin activity, int position) {
                            //设置item中控件的取值
                            Log.i("123123", "convert: " + position);

                            initItemView(viewHolder, activity, position);
                        }
                    };

                    lv_list .setAdapter(adapter);
                }else{
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

    public void initItemView(ViewHolder viewHolder, AcJoin activity, int position) {
        ImageView iv_img = ((ImageView) viewHolder.getViewById(R.id.iv_people));
        TextView tv_title = ((TextView) viewHolder.getViewById(R.id.tv_title));
        TextView tv_createTime = ((TextView) viewHolder.getViewById(R.id.tv_time));
        TextView tv_address = ((TextView) viewHolder.getViewById(R.id.tv_address));
        TextView tv_time = ((TextView) viewHolder.getViewById(R.id.time3));
//        TextView tv_join = ((TextView) viewHolder.getViewById(R.id.tv_num));

        x.image().bind(iv_img,HttpUtils.localhost_su+activity.getActivity().getActivityImg());
        tv_title.setText(activity.getActivity().getActivityTitle());
        tv_address.setText(activity.getActivity().getActivityAddress());
        tv_createTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.getActivity().getCreateTime()));
        tv_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.getActivity().getBeginTime()));
//        tv_join.setText(activity.joinNums.toString());
    }
}
