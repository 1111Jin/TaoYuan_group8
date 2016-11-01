package com.example.administrator.taoyuan.activity_home;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_home.adapter.MyAdapter;
import com.example.administrator.taoyuan.pojo.GongGaoBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class GonggaoActivity extends Activity {
    ArrayList<GongGaoBean.GongGao> gonggaoList = new ArrayList<>();
    ListView listView;
    @InjectView(R.id.img_float)
    ImageView imgFloat;
    @InjectView(R.id.iv)
    ImageView iv;
    private MyAdapter adapter;
    int pageNo = 1;
    int pageSize = 20;
    RefreshableView refreshableView;
    Bundle bundle = new Bundle();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gonggao);
        ButterKnife.inject(this);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GongGaoBean gongGaoBean=new GongGaoBean();

                GongGaoBean.GongGao gongGao= gongGaoBean.new GongGao(gonggaoList.get(position).gonggaoId,gonggaoList.get(position).getGonggaoContent(),
                        gonggaoList.get(position).gonggaoImg,gonggaoList.get(position).gonggaofabiaoTime,gonggaoList.get(position).gonggaoendTime,
                        gonggaoList.get(position).gonggaoAddress,gonggaoList.get(position).gonggaoTitle);
                bundle.putSerializable("gonggaoList",gongGao);
                bundle.putString("flag","1");
                Intent intent=new Intent(GonggaoActivity.this,GonggaoXiangqing.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        getGonggao();
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo++;
                try {
                    Thread.sleep(3000);
                    getGonggao();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);
        LayoutInflater infla = LayoutInflater.from(this);
        View headView = infla.inflate(R.layout.foot_item, null);
        TextView textView = ((TextView) headView.findViewById(R.id.tv));
        listView.addFooterView(headView, null, true);
    }

    public void getGonggao() {
        RequestParams params = new RequestParams(Netutil.url + "ckgongGao");
        params.addQueryStringParameter("pageNo", pageNo + "");
        params.addQueryStringParameter("pageSize", pageSize + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GongGaoBean bean = gson.fromJson(result, GongGaoBean.class);
                if (bean.ggList.size() == 0) {
                    pageNo--; //下一次继续加载这一页
                    Toast.makeText(GonggaoActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                gonggaoList.clear();
                gonggaoList.addAll(bean.ggList);
                if (adapter == null) {
                    adapter = new MyAdapter(GonggaoActivity.this, gonggaoList);
                    listView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(GonggaoActivity.this, "请求数据失败,请检查网络是否连接", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @OnClick({R.id.iv, R.id.img_float})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv:
                finish();
                break;
            case R.id.img_float:
                listView.smoothScrollToPosition(0);
                break;
        }
    }
}















