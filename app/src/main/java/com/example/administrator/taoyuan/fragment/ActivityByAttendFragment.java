package com.example.administrator.taoyuan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ActivityInfo;
import com.example.administrator.taoyuan.utils.CommonAdapter;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.ViewHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mawuyang on 2016-10-24.
 */
public class ActivityByAttendFragment extends BaseFragment {

    private ListView lv_list;
    List<ActivityInfo> aclist = new ArrayList<ActivityInfo>();
    CommonAdapter<ActivityInfo> adapter;
    private Integer userId;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_repair,null);
        lv_list = ((ListView) view.findViewById(R.id.lv_repair_listview));
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if(bundle!=null){
            userId=bundle.getInt("userId");
        }
        getActivityList();
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
        RequestParams params = new RequestParams(HttpUtils.localhost + "/joinactivitybyid?userId=" + userId);
//        params.addBodyParameter("repairState","已派员");
        System.out.println(params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm")
                        .create();
                Type type=new TypeToken<List<ActivityInfo>>(){}.getType();

                List<ActivityInfo> alist=new ArrayList<ActivityInfo>();
                alist=gson.fromJson(result,type);
                aclist.addAll(alist);

                System.out.println(aclist.get(0));

                if (adapter == null) {
                    adapter = new CommonAdapter<ActivityInfo>(getActivity(), aclist, R.layout.activity_list_view_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ActivityInfo activity, int position) {
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

    public void initItemView(ViewHolder viewHolder, ActivityInfo activity, int position) {
        ImageView iv_img = ((ImageView) viewHolder.getViewById(R.id.iv_people));
        TextView tv_title = ((TextView) viewHolder.getViewById(R.id.tv_title));
        TextView tv_createTime = ((TextView) viewHolder.getViewById(R.id.tv_time));
        TextView tv_address = ((TextView) viewHolder.getViewById(R.id.tv_address));
        TextView tv_time = ((TextView) viewHolder.getViewById(R.id.time3));
        TextView tv_join = ((TextView) viewHolder.getViewById(R.id.tv_num));

        tv_title.setText(activity.activityTitle);
        tv_address.setText(activity.status);
        tv_createTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.createTime));
        tv_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.beginTime));
        tv_join.setText(activity.joinNums.toString());
    }
}
