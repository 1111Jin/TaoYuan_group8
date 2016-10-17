package com.example.administrator.taoyuan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ListHelpBean;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public class linli_help_fragment extends linli{

    View view;
    private ListView lv_help;
    private BaseAdapter adapter;

    final List<ListHelpBean.Help> helpList = new ArrayList<ListHelpBean.Help>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_linli,null);

        initview();
        initdata();
        return view;
    }


    private void initview() {
        lv_help = ((ListView) view.findViewById(R.id.lv_activity));
    }

    private void initdata() {

        adapter = new BaseAdapter() {
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
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = View.inflate(getActivity().getApplicationContext(),R.layout.help_list_view_item,null);
                iv_tou = ((ImageView) view.findViewById(R.id.iv_tou));
                tv_username = ((TextView) view.findViewById(R.id.tv_username));
                tv_time2 = ((TextView) view.findViewById(R.id.tv_time2));
                help_content = ((TextView) view.findViewById(R.id.tv_help_content));
                help_title = ((TextView) view.findViewById(R.id.tv_help_title));


                ListHelpBean.Help helpBean = helpList.get(position);
                tv_username.setText(URLDecoder.decode(helpBean.userName));
                tv_time2.setText(helpBean.time);
                help_title.setText(URLDecoder.decode(helpBean.help_title));
                help_content.setText(URLDecoder.decode(helpBean.help_content));
                xUtilsImageUtils.display(iv_tou,"http://10.40.5.23:8080/cmty/upload/"+helpBean.help_photo+"",true);
                return view;
            }
        };
        lv_help.setAdapter(adapter);

        gethelpList();
    }

    private void gethelpList() {
        RequestParams params = new RequestParams("http://10.0.2.2:8080/cmty/toshowhelp");
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                //土司，打印
//                Toast.makeText(getActivity().getApplicationContext(),result,Toast.LENGTH_LONG).show();
                Gson gson = new Gson();

                ListHelpBean bean = gson.fromJson(result,ListHelpBean.class);

                helpList.addAll(bean.helpList);


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString());

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
