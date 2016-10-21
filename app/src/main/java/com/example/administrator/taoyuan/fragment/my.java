package com.example.administrator.taoyuan.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_my.GetAllUserActivity;
import com.example.administrator.taoyuan.activity_my.GetMyActivity;
import com.example.administrator.taoyuan.activity_my.ModifyMyActivity;
import com.example.administrator.taoyuan.activity_my.RepairActivity;
import com.example.administrator.taoyuan.pojo.ListActivityBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
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
 * A simple {@link Fragment} subclass.
 */
public class my extends Fragment {


    private static final int REQUSETCODE =1;
    private static final String TAG = "Log";
    Bitmap bm;
    @InjectView(R.id.iv_mymsg)
    ImageView ivMymsg;
    @InjectView(R.id.tv_myname)
    TextView tvMyname;
    @InjectView(R.id.tv_myintegral)
    TextView tvMyintegral;
    @InjectView(R.id.tv_myprofiles)
    TextView tvMyprofiles;
    @InjectView(R.id.rl_modify_My)
    RelativeLayout rlModifyMy;
    @InjectView(R.id.btn_myfriend)
    Button btnMyfriend;
    @InjectView(R.id.btn_myrepair)
    Button btnMyrepair;
    @InjectView(R.id.btn_myactivity)
    Button btnMyactivity;
    @InjectView(R.id.btn_myshoucang)
    Button btnMyshoucang;
    @InjectView(R.id.btn_myinstill)
    Button btnMyinstill;

    private List<ListActivityBean.User> list=new ArrayList<ListActivityBean.User>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);


        initView(view);
        initData();

        ButterKnife.inject(this, view);
        return view;
    }

    public void initView(View view) {


    }


    public void initData() {

        RequestParams requestParams=new RequestParams(HttpUtils.localhost+"/my?userId="+HttpUtils.userId);

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: "+result);
                Gson gson=new Gson();

                ListActivityBean bean=gson.fromJson(result, ListActivityBean.class);

                list=bean.userList;
                String url=list.get(0).userHead;
                System.out.println(url);
                xUtilsImageUtils.display(ivMymsg,HttpUtils.localhost+url,true);


                tvMyname.setText(list.get(0).userName);
                System.out.println(list.get(0).userName);
                tvMyintegral.setText("积分："+list.get(0).userTel);

                tvMyprofiles.setText(list.get(0).userProfiles);

                Log.i(TAG, "onSuccess: "+list.get(0).userAddress);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.rl_modify_My,R.id.btn_myfriend,R.id.btn_myactivity,R.id.btn_myrepair,R.id.btn_myshoucang,R.id.btn_myinstill})
     public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_modify_My:
                Toast.makeText(getActivity(),"modify_my",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),ModifyMyActivity.class);

                bm =((BitmapDrawable) (ivMymsg).getDrawable()).getBitmap();
                    intent.putExtra("user", list.get(0));
                    intent.putExtra("head", bm);
                    startActivityForResult(intent, REQUSETCODE);

                break;
            case R.id.btn_myfriend:
                Intent intent1 = new Intent(getActivity(), GetAllUserActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_myactivity:
                Intent intent2=new Intent(getActivity(), GetMyActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_myshoucang:

                break;
            case R.id.btn_myrepair:
                Intent intent4 = new Intent(getActivity(), RepairActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_myinstill:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUSETCODE:
                ListActivityBean.User u=data.getParcelableExtra("user");
                tvMyname.setText(u.userName);
                System.out.println(u.userName);
                tvMyintegral.setText(u.userTel);

                tvMyprofiles.setText(u.userProfiles);
                break;

        }
    }
}
