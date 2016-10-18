package com.example.administrator.taoyuan.activity_my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.MainActivity;
import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.fragment.my;
import com.example.administrator.taoyuan.pojo.ListActivityBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class GetAllUserActivity extends AppCompatActivity {
    private static final int RESULTCODE = 1;
    private static final String TAG = "getAllUserActivity";
    private ListView lv_userList;
    private BaseAdapter adapter;
    final List<ListActivityBean.User> userList=new ArrayList<ListActivityBean.User>();
    private ProgressBar progressBar;
    private RelativeLayout rl_back_my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_user);

        initView();
        initData();
    }

    public void initView(){
        lv_userList = ((ListView) findViewById(R.id.lv_userList));
        progressBar = ((ProgressBar) findViewById(R.id.progressBar));
        rl_back_my = ((RelativeLayout) findViewById(R.id.rl_back_my));
    }


    public void initData(){
        adapter=new BaseAdapter() {
            private ImageView iv_head;
            private ViewHolder viewholder=null;


            @Override
            public int getCount() {
                return userList.size();
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

                if(convertView==null){
                    viewholder=new ViewHolder();
                    convertView=View.inflate(GetAllUserActivity.this,R.layout.activity_list_lv_userlist_item,null);
                    viewholder.iv_head = ((ImageView) convertView.findViewById(R.id.iv_userHead));
                    viewholder.tv_name= ((TextView) convertView.findViewById(R.id.tv_userName));
                    viewholder.tv_profiles= ((TextView) convertView.findViewById(R.id.tv_userProfiles));
                    convertView.setTag(viewholder);//缓存
                }else{
                    viewholder=(ViewHolder) convertView.getTag();
                }
                ListActivityBean.User user=userList.get(position);

                xUtilsImageUtils.display(viewholder.iv_head,HttpUtils.localhost+user.userHead,10);
                viewholder.tv_name.setText(user.userName);
                viewholder.tv_profiles.setText(user.userProfiles);
                return convertView;
            }
        };
        lv_userList.setAdapter(adapter);

        getUserList();

        rl_back_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        lv_userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: "+position);
                ListActivityBean.User friend=userList.get(position);
                Intent intent = new Intent(getApplicationContext(),MyFriend.class);
                intent.putExtra("friend",friend);
                startActivityForResult(intent,1);
            }
        });
    }

    public void getUserList() {
        progressBar.setVisibility(View.VISIBLE);

        RequestParams params=new RequestParams(HttpUtils.localhost+"/getmyfriend?userId="+HttpUtils.userId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson=new Gson();
                ListActivityBean bean=gson.fromJson(result,ListActivityBean.class);
                userList.addAll(bean.userList);
                System.out.println(bean.status);
                System.out.println(bean.userList.size());
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
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

    public static class ViewHolder{
        ImageView iv_head;
        TextView tv_title;
        TextView tv_name;
        //        TextView tv_tel;
        TextView tv_profiles;
    }
}
