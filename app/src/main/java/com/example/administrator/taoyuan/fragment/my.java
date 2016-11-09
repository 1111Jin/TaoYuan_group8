package com.example.administrator.taoyuan.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_home.BadgeView;
import com.example.administrator.taoyuan.activity_home.Netutil;
import com.example.administrator.taoyuan.activity_my.GetAllUserActivity;
import com.example.administrator.taoyuan.activity_my.GetMyActivity;
import com.example.administrator.taoyuan.activity_my.GetMyHelp;
import com.example.administrator.taoyuan.activity_my.Instill;
import com.example.administrator.taoyuan.activity_my.LiveActivity;
import com.example.administrator.taoyuan.activity_my.ModifyMyActivity;
import com.example.administrator.taoyuan.activity_my.MyIntegral;
import com.example.administrator.taoyuan.activity_my.RepairActivity;
import com.example.administrator.taoyuan.activity_my.TextActivity;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.AllJifen;
import com.example.administrator.taoyuan.pojo.ListUserBean;
import com.example.administrator.taoyuan.pojo.MsgBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class my extends Fragment {

    List<MsgBean> mlist = new ArrayList<>();
    private static final int REQUSETCODE = 1;
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
    @InjectView(R.id.btn_myHelp)
    Button btnMyHelp;
    @InjectView(R.id.btn_myinstill)
    Button btnMyinstill;
    @InjectView(R.id.btn_mydongtai)
    Button btnMydongtai;
    @InjectView(R.id.btn_msg)
    ImageView btnMsg;
    final int RESULT_CODE = 101;
    final int REQUEST_CODE = 1;
    private List<ListUserBean.User> list = new ArrayList<ListUserBean.User>();
    int i;
    BadgeView badgeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        initView(view);
        ButterKnife.inject(this, view);
        getMsg();
        getAlljifen();
        badgeView=new BadgeView(getActivity(),btnMsg);
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        return view;
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(getActivity(),requestCode+""+resultCode+"",Toast.LENGTH_LONG).show();
//        if(requestCode==REQUEST_CODE) {
//            if(resultCode==RESULT_CODE) {
//
//                i= data.getIntExtra("second",0);
//            }
//            System.out.println(i+"///////////");
//        }
//        BadgeView badge = new BadgeView(getActivity(), btnMsg);
//        badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//        System.out.println(i+"ggg");
//        if (i> 0) {
//            badge.setText(String.valueOf(i));
//            badge.show();
//        } else {
//            badge.setVisibility(View.GONE);
//        }
//
//    }

    public void initView(View view) {


    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        getMsg();
    }

    public void initData() {

        RequestParams requestParams = new RequestParams(HttpUtils.localhost + "/my?userId=" + HttpUtils.userId);

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: " + result);
                Gson gson = new Gson();

                ListUserBean bean = gson.fromJson(result, ListUserBean.class);

                list = bean.userList;
                String url = "/head" + list.get(0).userHead;
                System.out.println(url);
                xUtilsImageUtils.display(ivMymsg, HttpUtils.localhost + url, true);


                tvMyname.setText(list.get(0).userName);
//                System.out.println(list.get(0).userName);

                tvMyprofiles.setText(list.get(0).userProfiles);

                Log.i(TAG, "onSuccess: " + list.get(0).userAddress);
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

    @OnClick({R.id.rl_modify_My, R.id.btn_myfriend, R.id.btn_msg, R.id.btn_myactivity, R.id.btn_myrepair, R.id.btn_myHelp, R.id.btn_myinstill, R.id.btn_mydongtai})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_modify_My:
                Intent intent = new Intent(getActivity(), ModifyMyActivity.class);
                bm = ((BitmapDrawable) (ivMymsg).getDrawable()).getBitmap();
                intent.putExtra("user", list.get(0));
                intent.putExtra("head", bm);
                startActivityForResult(intent, REQUSETCODE);

                break;
            case R.id.btn_myfriend:
                Intent intent1 = new Intent(getActivity(), GetAllUserActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_myactivity:
                Intent intent2 = new Intent(getActivity(), GetMyActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_myHelp:
                Intent intent3 = new Intent(getActivity(), GetMyHelp.class);
                startActivity(intent3);
                break;
            case R.id.btn_myrepair:
                Intent intent4 = new Intent(getActivity(), RepairActivity.class);
                startActivity(intent4);
                break;
            //设置
            case R.id.btn_myinstill:
                Intent intent5 = new Intent(getActivity(), Instill.class);
                bm = ((BitmapDrawable) (ivMymsg).getDrawable()).getBitmap();
                intent5.putExtra("user", list.get(0));
                intent5.putExtra("head", bm);
                startActivity(intent5);
                break;
            case R.id.btn_mydongtai:
                Intent intent7 = new Intent(getActivity(), LiveActivity.class);

                startActivity(intent7);

                break;

            case R.id.btn_msg:
                Intent intent8 = new Intent(getActivity(), TextActivity.class);
                startActivityForResult(intent8, REQUEST_CODE);
                break;
        }
    }


    public void getMsg() {
        RequestParams requestParams = new RequestParams(Netutil.url + "getMsg?userId=" +((MyApplication)getActivity().getApplication()).getUser().getUserId());
//        requestParams.addBodyParameter("userId",H);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                Type type = new TypeToken<List<MsgBean>>() {
                }.getType();
                List<MsgBean> list = gson.fromJson(result, type);
                mlist.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).flag.equals("0")) {
                        mlist.add(list.get(i));
                    }
                }
                i = mlist.size();
                if (i > 0) {
                    badgeView.setText(String.valueOf(i));
                    System.out.println("显示");
                    badgeView.show();
                } else {
                    badgeView.setVisibility(View.GONE);
                    System.out.println("不显示");
                }
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
    public  void  getAlljifen() {
        RequestParams requestParams = new RequestParams(Netutil.url + "getallJifen?userId=" +((MyApplication)getActivity().getApplication()).getUser().getUserId());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<AllJifen>() {
                }.getType();
                AllJifen allJifen = gson.fromJson(result, type);
                tvMyintegral.setText("积分：" +allJifen.alljiFen);
                System.out.println(allJifen.getAlljiFen() + "///////");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString() + "?????????");

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
