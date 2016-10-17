package com.example.administrator.taoyuan.activity_life;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;

import com.example.administrator.taoyuan.pojo.ListInfo;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class ActivityLife extends AppCompatActivity {
    private static final String TAG ="ListActivity" ;
    private ListView lv_lifeinfo;
    private BaseAdapter adapter;
    final ArrayList<ListLifeInfo.LifeInfo> lifelist=new ArrayList<ListLifeInfo.LifeInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lv_lifeinfo = ((ListView) findViewById(R.id.lv_dongtai));
        adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return lifelist.size();
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

                Log.i(TAG,"加载listview item position:"+position);
                View view=  View.inflate(getApplicationContext(), R.layout.activity_list_lv_dongtai_item,null);
                TextView tv_title= ((TextView) view.findViewById(R.id.tv_title));
                TextView tv_name= ((TextView) view.findViewById(R.id.tv_name));
                ListLifeInfo.LifeInfo dongtai=lifelist.get(position);
                tv_title.setText(dongtai.userName);
                tv_name.setText(dongtai.content);
                return view;
            }
        };

        lv_lifeinfo.setAdapter(adapter);


        getLifeInfoList();


    }

    private List<ListInfo> getLifeInfoList() {

        RequestParams params = new RequestParams("http://10.50.62.57:8080/Life/getdongraibypage");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson=new Gson();
                ListLifeInfo bean= gson.fromJson(result, ListLifeInfo.class);
//                 System.out.println(bean.status+"----");
//                System.out.println(bean.lifeinfolist.size()+"===");
                lifelist.addAll( bean.lifeinfolist);

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
        return null;
    }
}
