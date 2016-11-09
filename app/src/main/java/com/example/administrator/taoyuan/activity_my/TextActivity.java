package com.example.administrator.taoyuan.activity_my;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_home.Netutil;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.MsgBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.TitleBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mawuyang on 2016-10-28.
 */
public class TextActivity extends Activity {

    private TextView tv_title;
    private TextView tv_content;
    private TitleBar tt;
    private SwipeMenuListView lv_msg;
    private BaseAdapter adapter;
    List<MsgBean> msglist=new ArrayList<MsgBean>();
    List<MsgBean> mlist=new ArrayList<>();
    Bundle bundle=new Bundle();
   String msgid;
    int i;
    final int RESULT_CODE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_activity);
        lv_msg = ((SwipeMenuListView) findViewById(R.id.lv_msg));
        tt = ((TitleBar) findViewById(R.id.tt_tt));
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(R.color.common_bg);
                // set item width
                deleteItem.setWidth(dp2px(TextActivity.this,90));
                // set a icon
                deleteItem.setIcon(R.drawable.delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        lv_msg.setMenuCreator(creator);
        lv_msg.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        lv_msg.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                msglist.remove(position);
                lv_msg.setAdapter(adapter);
                return false;
            }
        });
        tt.setLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_msg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                msgid= String.valueOf(msglist.get(position).getId());
                upDateflag();
                Intent intent = new Intent(getApplicationContext(),MsgItem.class);
                intent.putExtra("msg",msglist.get(position));
                startActivityForResult(intent,1);
            }
        });
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
                if (msglist.get(position).flag.equals("1")){
                    convertView.setBackgroundResource(R.color.yidu);
                }else {
                    convertView.setBackgroundResource(R.color.cream_colored);
                }
                System.out.println(msglist.get(position).toString()+"//"+position);
                viewholder.title.setText("【通知： 】"+msglist.get(position).title);
                viewholder.time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(msglist.get(position).time));
                return convertView;
            }
        };

    }


    public void getMsg(){
        RequestParams requestParams=new RequestParams(Netutil.url+"getMsg?userId="+((MyApplication)getApplication()).getUser().getUserId());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                Type type = new TypeToken<List<MsgBean>>(){}.getType();
                List<MsgBean> list=gson.fromJson(result,type);
                msglist.addAll(list);
                for (int i=0;i<list.size();i++){
                    if (list.get(i).flag.equals("0")){
                        mlist.add(list.get(i));
                    }
                }
                i=mlist.size();
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
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public  void upDateflag(){
        RequestParams params = new RequestParams(Netutil.url + "updateFlag");
        String upFlag=msgid;
        params.addBodyParameter("flag", upFlag);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(".dfgdfgsfvxcv");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString()+"shibai");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        msglist.clear();
        getMsg();
        lv_msg.setAdapter(adapter);
    }
}
