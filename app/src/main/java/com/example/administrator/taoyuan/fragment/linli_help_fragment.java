package com.example.administrator.taoyuan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_linli.HelpInfo;
import com.example.administrator.taoyuan.pojo.Help;
import com.example.administrator.taoyuan.pojo.ListHelpBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public class linli_help_fragment extends linli{

    private ListView lv_help;
    private BaseAdapter adapter;

    List<Help> helpList = new ArrayList<Help>();
    private FrameLayout fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.linli_activity_listview,null);
        lv_help = ((ListView)view.findViewById(R.id.ac_listview));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initdata();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initEvent();
        super.onActivityCreated(savedInstanceState);
    }

    public void initEvent(){
        lv_help.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),HelpInfo.class);
                intent.putExtra("HelpInfo",helpList.get(position));
//                intent.putExtra("user",(Serializable) helpList.get(position).getUser());
                intent.putExtra("comment",(Serializable) helpList.get(position).getComment());
                startActivity(intent);

            }
        });
    }

    private void initdata() {

        adapter = new BaseAdapter() {
            public ImageView helpImg;
            private TextView help_title;
            private TextView help_content;
            private TextView tv_time2;
            private ImageView iv_tou;
            private TextView tv_username;

            @Override
            public int getCount() {
                return helpList.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = View.inflate(getActivity().getApplicationContext(),R.layout.help_list_view_item,null);
                iv_tou = ((ImageView) view.findViewById(R.id.iv_tou));
                tv_username = ((TextView) view.findViewById(R.id.tv_username));
                tv_time2 = ((TextView) view.findViewById(R.id.tv_time2));
//                help_content = ((TextView) view.findViewById(R.id.tv_help_content));
                help_title = ((TextView) view.findViewById(R.id.tv_help_title));
                helpImg = ((ImageView) view.findViewById(R.id.iv_hl_img));


                Help helpBean = helpList.get(position);
                tv_username.setText(URLDecoder.decode(helpBean.getUser().getUserName()));
                tv_time2.setText(helpBean.getCreateTime()+"");
                help_title.setText(URLDecoder.decode(helpBean.getHelpTitle()));
//                help_content.setText(URLDecoder.decode(helpBean.help_content));
                xUtilsImageUtils.display(iv_tou, HttpUtils.localhost_su+helpBean.getUser().getPhoto()+"",true);
                xUtilsImageUtils.display(helpImg,HttpUtils.localhost_su+helpBean.getHelpImg()+"",false);
                return view;
            }
        };
        lv_help.setAdapter(adapter);

        gethelpList();
    }

    private void gethelpList() {
        Log.i("linli_help_fragment", "onSuccess: =====>help数据传递进来了");

        RequestParams params = new RequestParams(HttpUtils.localhost_su+"queryhelp");
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.i("linli_help_fragment", "onSuccess: help数据拿到了：=====>"+result);

                System.out.println(result);
                //土司，打印
//                Toast.makeText(getActivity().getApplicationContext(),result,Toast.LENGTH_LONG).show();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Help>>(){}.getType();
                helpList = gson.fromJson(result,type);


                Log.i("linli_help_fragment", "onSuccess: help接收数据对象：=====>"+helpList.get(0).getComment());
//                helpList.clear();
//                helpList.addAll(helpList);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("12312"+ex.toString());

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
