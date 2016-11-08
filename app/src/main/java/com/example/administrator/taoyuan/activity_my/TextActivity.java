package com.example.administrator.taoyuan.activity_my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.ListUserBean;
import com.example.administrator.taoyuan.pojo.MsgBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.TitleBar;
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

import cn.jpush.android.api.JPushInterface;

/**
 * Created by mawuyang on 2016-10-28.
 */
public class TextActivity extends Activity {

    private TextView tv_title;
    private TextView tv_content;
    private TitleBar tt;
    private ListView lv_msg;
    private BaseAdapter adapter;
    List<MsgBean> msglist=new ArrayList<MsgBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_activity);
        System.out.println("捡来了");
        lv_msg = ((ListView) findViewById(R.id.lv_msg));
        tt = ((TitleBar) findViewById(R.id.tt_tt));

//        Intent intent = getIntent();
//        if (null != intent) {
//            Bundle bundle = getIntent().getExtras();
//            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
//            tv_title.setText(title);
//            tv_content.setText(content);
//        }
        tt.setLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        addContentView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        adapter=new BaseAdapter() {

            private ViewHolder viewholder=null;
            @Override
            public int getCount() {
                return msglist.size();
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
                    convertView=View.inflate(TextActivity.this, R.layout.msg_item,null);
                    viewholder.title = ((TextView) convertView.findViewById(R.id.tv_title));
                    viewholder.time= ((TextView) convertView.findViewById(R.id.tv_time));
                    convertView.setTag(viewholder);//缓存
                }else{
                    viewholder=(ViewHolder) convertView.getTag();
                }
                MsgBean msg=msglist.get(position);
                viewholder.title.setText("【通知： 】"+msg.title);
                viewholder.time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(msg.time));
                return convertView;
            }
        };

        lv_msg.setAdapter(adapter);
        getMsg();

        lv_msg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("11231");
                Intent intent = new Intent(getApplicationContext(),MsgItem.class);
                intent.putExtra("msg",msglist.get(position));
                System.out.println("12312312");
                startActivityForResult(intent,1);
            }
        });

    }


    public void getMsg(){
        RequestParams requestParams=new RequestParams(HttpUtils.localhost+"getmsg?userId="+((MyApplication)getApplication()).getUser().getUserId());
//        requestParams.addBodyParameter("userId",H);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm")
                        .create();
                Type type = new TypeToken<List<MsgBean>>(){}.getType();
                List<MsgBean> list=gson.fromJson(result,type);
                msglist.addAll(list);
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

        TextView title;
        TextView time;


    }
}
