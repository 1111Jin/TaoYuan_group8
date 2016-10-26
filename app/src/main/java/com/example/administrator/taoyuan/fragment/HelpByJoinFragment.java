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
import com.example.administrator.taoyuan.pojo.HelpInfo;
import com.example.administrator.taoyuan.pojo.ListUserBean;
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

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mawuyang on 2016-10-24.
 */
public class HelpByJoinFragment extends BaseFragment {
    private ListView lv_list;
    private Integer userId;
    List<HelpInfo> aclist = new ArrayList<HelpInfo>();
    CommonAdapter<HelpInfo> adapter;
    private ListUserBean.User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_all_repair,null);
        lv_list = ((ListView) v.findViewById(R.id.lv_repair_listview));
        Bundle bundle = getArguments();
        if(bundle!=null){
            userId=bundle.getInt("userId");
        }
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
        RequestParams re=new RequestParams(HttpUtils.localhost+"/my?userId="+ HttpUtils.userId);
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

    }

    public void getActivityList(){
        RequestParams params = new RequestParams(HttpUtils.localhost + "/getjoinhelp?userId=" + userId);
        System.out.println(params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm")
                        .create();
                Type type=new TypeToken<List<HelpInfo>>(){}.getType();
                aclist=gson.fromJson(result,type);

//                Type t1=new TypeToken<List<HelpInfo>>(){}.getType();
//                List<HelpInfo> helplist=gson.fromJson(map.get("helplist").toString(),t1);
//                System.out.println(helplist.get(0).helpImg);

//                System.out.println(aclist.get(0));
//
                if (adapter == null) {
                    adapter = new CommonAdapter<HelpInfo>(getActivity(), aclist, R.layout.help_list_view_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, HelpInfo activity, int position) {
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

    public void initItemView(ViewHolder viewHolder, HelpInfo activity, int position) {
        ImageView iv_img = ((ImageView) viewHolder.getViewById(R.id.iv_tou));
        TextView tv_username = ((TextView) viewHolder.getViewById(R.id.tv_username));
        TextView tv_title = ((TextView) viewHolder.getViewById(R.id.tv_help_title));
//        ImageView iv_help = ((ImageView) viewHolder.getViewById(R.id.tv_help_title));
        TextView tv_time = ((TextView) viewHolder.getViewById(R.id.tv_time2));

        String imgurl = "/head"+user.userHead;
        xUtilsImageUtils.display(iv_img, HttpUtils.localhost+imgurl,true);
        tv_username.setText(user.userName);
        tv_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(activity.helpTime));
        tv_title.setText(activity.helpTitle);
//        x.image().bind(iv_help,HttpUtils.localhost+activity.helpImg);

    }
}
