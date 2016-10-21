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

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by mawuyang on 2016-10-20.
 */
public class AllRepairFragment extends BaseFragment {

    List<ReListActivityBean.Repair> repairlist = new ArrayList<ReListActivityBean.Repair>();
    CommonAdapter<ReListActivityBean.Repair> adapter;

    private static final String TAG = "********";
    private ListView lv_repair;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_repair, null);

        Log.i(TAG, "onCreateView: ssssssss");
        getUserList();
        lv_repair = ((ListView) view.findViewById(R.id.lv_repair_listview));


        return view;
    }

    public void getUserList() {
        RequestParams params = new RequestParams(HttpUtils.localhost + "/getallrepair?userId=" + HttpUtils.userId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                ReListActivityBean bean = gson.fromJson(result, ReListActivityBean.class);

//                repairlist.clear();
                repairlist.addAll(bean.repairList);

                if (adapter == null) {
                    adapter = new CommonAdapter<ReListActivityBean.Repair>(getActivity(), repairlist, R.layout.fragment_repair_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, ReListActivityBean.Repair repair, int position) {
                            //设置item中控件的取值
                            Log.i(TAG, "convert: " + position);
                            initItemView(viewHolder, repair, position);
                        }
                    };
                    lv_repair.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: " + ex.getMessage());
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

        Log.i(TAG, "initItemView: " + repair.repairType);
        tv_repair_type.setText(repair.repairType);
        tv_repair_state.setText(repair.repairState);
        tv_repair_address.setText(repair.repairAddress);
        tv_repair_userName.setText(repair.userName);
        tv_repair_servicemanName.setText(repair.servicemanName);
        tv_repair_title.setText(repair.repairTitle);
        tv_repair_content.setText(repair.repairContent);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
