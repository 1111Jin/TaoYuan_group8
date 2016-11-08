package com.example.administrator.taoyuan.activity_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_my.IntegralGet;
import com.example.administrator.taoyuan.activity_my.IntegralUse;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.AllJifen;
import com.example.administrator.taoyuan.pojo.JiFen;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class JifenActivity extends AppCompatActivity {

    @InjectView(R.id.iv)
    ImageView iv;
    @InjectView(R.id.rl)
    RelativeLayout rl;
    @InjectView(R.id.my_integral)
    TextView myIntegral;
    @InjectView(R.id.rl1)
    RelativeLayout rl1;
    @InjectView(R.id.btn_integral_cash)
    Button btnIntegralCash;
    @InjectView(R.id.btn_integral_msg)
    Button btnIntegralMsg;
    @InjectView(R.id.btn_integral_xiang)
    Button btnIntegralXiang;
    @InjectView(R.id.lv)
    ListView lv;
    private SwipeMenuListView slistView;
    private BaseAdapter adapter;
    List<JiFen> jiFenList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaoxi);
        ButterKnife.inject(this);
        LayoutInflater infla = LayoutInflater.from(this);
        View headView = infla.inflate(R.layout.head_item, null);
        lv.addHeaderView(headView, null, true);
       getAlljifen();
        adapter=new BaseAdapter() {
            private ViewHolder viewholder = null;

            @Override
            public int getCount() {
                return jiFenList.size();
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
                if (convertView == null) {
                    viewholder = new ViewHolder();
                    convertView = View.inflate(JifenActivity.this, R.layout.jifen_item, null);
                    viewholder.shumu = ((TextView) convertView.findViewById(R.id.tv_shu));
                    viewholder.xiangmu = ((TextView) convertView.findViewById(R.id.tv_mu));
                    viewholder.time = ((TextView) convertView.findViewById(R.id.tv_time));
                    convertView.setTag(viewholder);//缓存
                } else {
                    viewholder = (ViewHolder) convertView.getTag();
                }
                viewholder.shumu.setText(jiFenList.get(position).jifenNum+"");
                viewholder.xiangmu.setText(jiFenList.get(position).xiangmu);
                viewholder.time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(jiFenList.get(position).time)+"");
                return convertView;
            }
        };
        getJifenXiangqing();
        lv.setAdapter(adapter);
}



    @OnClick({R.id.iv, R.id.btn_integral_cash, R.id.btn_integral_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv:
                finish();
                break;
            case R.id.btn_integral_cash:
                Intent intent1=new Intent(JifenActivity.this,IntegralGet.class);
                startActivity(intent1);
                break;
            case R.id.btn_integral_msg:
                Intent intent2 = new Intent(JifenActivity.this,IntegralUse.class);
                startActivity(intent2);
                break;
        }
    }
    public void  getJifenXiangqing(){
        RequestParams requestParams=new RequestParams(Netutil.url+"getJifenXiangqing?userId="+(((MyApplication) getApplication()).getUser().getUserId()));
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<JiFen>>(){}.getType();
                List<JiFen> list=gson.fromJson(result,type);
                jiFenList.addAll(list);
                adapter.notifyDataSetChanged();
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

        TextView shumu;
        TextView xiangmu;
        TextView time;
    }
    public  void  getAlljifen(){
        RequestParams requestParams=new RequestParams(Netutil.url+"getallJifen?userId="+(((MyApplication) getApplication()).getUser().getUserId()));
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<AllJifen>(){}.getType();
                AllJifen allJifen=gson.fromJson(result,type);
               myIntegral.setText(allJifen.getAlljiFen()+"");
                adapter.notifyDataSetChanged();
                System.out.println(allJifen.getAlljiFen()+"///////");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString()+"?????????");

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
