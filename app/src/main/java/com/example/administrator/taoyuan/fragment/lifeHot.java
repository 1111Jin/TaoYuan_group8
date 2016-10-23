package com.example.administrator.taoyuan.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.example.administrator.taoyuan.utils.CommonAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class lifeHot extends BaseFragment {


     ArrayList<ListLifeInfo.LifeInfo> lifelist= new ArrayList<ListLifeInfo.LifeInfo>();
    CommonAdapter<ListLifeInfo.LifeInfo>  adapter;
    private static final String TAG = "********";
    private ListView lv_repair;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_repair, null);

        Log.i(TAG, "onCreateView: ssssssss");
        getLifeHotList();
        lv_repair = ((ListView) view.findViewById(R.id.lv_repair_listview));


        return view;
    }

    private void getLifeHotList() {


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
