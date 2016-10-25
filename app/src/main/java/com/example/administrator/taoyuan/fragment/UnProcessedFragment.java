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
import com.example.administrator.taoyuan.pojo.ReListActivityBean;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by mawuyang on 2016-10-21.
 */
public class UnProcessedFragment extends BaseFragment {

    private ListView lv_repair_listview;
    List<ReListActivityBean.Repair> repairlist = new ArrayList<ReListActivityBean.Repair>();
    CommonAdapter<ReListActivityBean.Repair> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_all_repair,null);
        lv_repair_listview = ((ListView) v.findViewById(R.id.lv_repair_listview));
        getUserList();
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

    }

    public void getUserList() {
        RequestParams params = new RequestParams(HttpUtils.localhost + "/getallrepair?userId=" + HttpUtils.userId);
        params.addBodyParameter("repairState","未受理");
        System.out.println(params);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm")
                        .create();
                Type type = new TypeToken<List<ReListActivityBean.Repair>>(){}.getType();
                List<ReListActivityBean.Repair> bean = gson.fromJson(result, type);

//                repairlist.clear();
                repairlist.addAll(bean);

                if (adapter == null) {
                    adapter = new CommonAdapter<ReListActivityBean.Repair>(getActivity(), repairlist, R.layout.fragment_repair_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ReListActivityBean.Repair repair, int position) {
                            //设置item中控件的取值
                            Log.i("123123", "convert: " + position);
                            initItemView(viewHolder, repair, position);
                        }
                    };
                    lv_repair_listview.setAdapter(adapter);
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

    public void initItemView(ViewHolder viewHolder, ReListActivityBean.Repair repair, int position) {

        TextView tv_repair_type = ((TextView) viewHolder.getViewById(R.id.item_repair_type));
        TextView tv_repair_state = ((TextView) viewHolder.getViewById(R.id.tv_repair_state));
        TextView tv_repair_address = ((TextView) viewHolder.getViewById(R.id.tv_repair_address));
        TextView tv_repair_userName = ((TextView) viewHolder.getViewById(R.id.tv_repair_userName));
        TextView tv_repair_servicemanName = ((TextView) viewHolder.getViewById(R.id.tv_repair_servicemanName));
        TextView tv_repair_title = ((TextView) viewHolder.getViewById(R.id.tv_repair_title));
        ImageView iv_repair_img = ((ImageView) viewHolder.getViewById(R.id.iv_repair_img));
        TextView tv_repair_content = ((TextView) viewHolder.getViewById(R.id.tv_repair_content));

        Log.i("UnpersonnalFragment", "initItemView: " + repair.repairType);
        tv_repair_type.setText(repair.repairType);
        tv_repair_state.setText(repair.repairState);
        tv_repair_address.setText(repair.repairAddress);
        tv_repair_userName.setText(repair.userName);
        tv_repair_servicemanName.setText(repair.servicemanName);
        tv_repair_title.setText(repair.repairTitle);
        tv_repair_content.setText(repair.repairContent);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
