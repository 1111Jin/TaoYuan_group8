package com.example.administrator.taoyuan.fragment;




import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_home.BaoxiuActivity1;

import com.example.administrator.taoyuan.activity_home.QianActivity;
import com.example.administrator.taoyuan.activity_home.TellphoneActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment {


    @InjectView(R.id.tb_title)
    Toolbar tbTitle;
    @InjectView(R.id.iv_lianxidianhua)
    ImageView ivLianxidianhua;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.iv_gg)
    ImageView ivGg;
    @InjectView(R.id.tv_wybx)
    TextView tvWybx;
    @InjectView(R.id.tv_wygg)
    TextView tvWygg;
    @InjectView(R.id.tv_xxtz)
    TextView tvXxtz;
    @InjectView(R.id.tv_jjxq)
    TextView tvJjxq;
    @InjectView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @InjectView(R.id.iv_hd1)
    ImageView ivHd1;
    @InjectView(R.id.rl_hd1)
    RelativeLayout rlHd1;
    @InjectView(R.id.iv_hd2)
    ImageView ivHd2;
    @InjectView(R.id.rl_hd2)
    RelativeLayout rlHd2;
    @InjectView(R.id.iv_hd3)
    ImageView ivHd3;
    @InjectView(R.id.rl_hd3)
    RelativeLayout rlHd3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_lianxidianhua, R.id.textView2, R.id.tv_wybx, R.id.tv_wygg, R.id.tv_xxtz, R.id.tv_jjxq, R.id.rl_hd1, R.id.rl_hd2, R.id.rl_hd3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_lianxidianhua:
                Intent intent=new Intent(getActivity(), TellphoneActivity.class);
                startActivity(intent);
                break;
            case R.id.textView2:
                Intent intent2=new Intent(getActivity(), QianActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_wybx:
                Intent intent1=new Intent(getActivity(), BaoxiuActivity1.class);
                startActivity(intent1);
                break;
            case R.id.tv_wygg:
                break;
            case R.id.tv_xxtz:
                break;
            case R.id.tv_jjxq:
                break;
            case R.id.rl_hd1:
                break;
            case R.id.rl_hd2:
                break;
            case R.id.rl_hd3:
                break;
        }
    }
}
