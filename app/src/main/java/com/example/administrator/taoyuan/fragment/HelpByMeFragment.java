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
import com.example.administrator.taoyuan.activity_linli.HelpInfo;
import com.example.administrator.taoyuan.pojo.Help;
import com.example.administrator.taoyuan.pojo.ListUserBean;
import com.example.administrator.taoyuan.utils.CommonAdapter;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.ViewHolder;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mawuyang on 2016-10-24.
 */
public class HelpByMeFragment extends BaseFragment {

    private ListView lv_list;
    private Integer userId;
    List<Help> aclist = new ArrayList<Help>();
    CommonAdapter<Help> adapter;
    private ListUserBean.User user;
    RefreshableView refreshableView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_all_repair,null);
        lv_list = ((ListView) v.findViewById(R.id.lv_repair_listview));
        refreshableView = ((RefreshableView) v.findViewById(R.id.refreshable_view));

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
        Bundle bundle = getArguments();
        if(bundle!=null){
            userId=bundle.getInt("userId");
        }
//        System.out.println("传过来的Id"+userId);
        getActivityList();
        return v;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        RequestParams re=new RequestParams(HttpUtils.localhost+"/my?userId="+ userId);
        x.http().get(re, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();

                ListUserBean bean=gson.fromJson(result, ListUserBean.class);
                user = bean.userList.get(0);
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

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HelpInfo.class);
                intent.putExtra("HelpInfo",aclist.get(position));
//                intent.putExtra("user",(Serializable) helpList.get(position).getUser());
                intent.putExtra("comment",(Serializable) aclist.get(position).getComment());
                startActivity(intent);
            }
        });

    }

    public void getActivityList(){
        RequestParams params = new RequestParams(HttpUtils.localhost_su + "/gethelpme?userId=" + userId);
//        params.addBodyParameter("repairState","已派员");
        System.out.println(params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                Type type = new TypeToken<List<Help>>(){}.getType();
                aclist=gson.fromJson(result,type);

//                Type t1=new TypeToken<List<HelpInfo>>(){}.getType();
//                List<HelpInfo> helplist=gson.fromJson(map.get("helplist").toString(),t1);
//                System.out.println(helplist.get(0).helpImg);

//                System.out.println(aclist.get(0));
//
                if (adapter == null) {
                    adapter = new CommonAdapter<Help>(getActivity(), aclist, R.layout.help_list_view_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, Help activity, int position) {
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

    public void initItemView(ViewHolder viewHolder, Help activity, int position) {
        ImageView iv_img = ((ImageView) viewHolder.getViewById(R.id.iv_tou));
        TextView tv_username = ((TextView) viewHolder.getViewById(R.id.tv_username));
        TextView tv_title = ((TextView) viewHolder.getViewById(R.id.tv_help_title));
        ImageView iv_help = ((ImageView) viewHolder.getViewById(R.id.iv_hl_img));
        TextView tv_time = ((TextView) viewHolder.getViewById(R.id.tv_time2));


        xUtilsImageUtils.display(iv_img, HttpUtils.localhost+"/head"+user.userHead,true);
        tv_username.setText(user.userName);
        tv_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.getHelpTime()));
        tv_title.setText(activity.getHelpTitle());
        x.image().bind(iv_help,HttpUtils.localhost_su+activity.getHelpImg());

    }
}
