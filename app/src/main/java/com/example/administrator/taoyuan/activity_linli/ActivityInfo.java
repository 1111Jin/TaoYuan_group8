package com.example.administrator.taoyuan.activity_linli;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.Activity;
import com.example.administrator.taoyuan.pojo.User;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.TitleBar;
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

import static com.example.administrator.taoyuan.utils.DateUtil.dateToString1;

/**
 * Created by Administrator on 2016/10/17.
 */
public class ActivityInfo extends AppCompatActivity {
    private static final String TAG = "ActivityInfo";
    @InjectView(R.id.iv_ac_image)
    ImageView ivAcImage;
    @InjectView(R.id.tv_ac_num)
    TextView tvAcNum;
    @InjectView(R.id.rl_ac_content)
    RelativeLayout rlAcContent;
    @InjectView(R.id.tv_ac_address)
    TextView tvAcAddress;
    @InjectView(R.id.tv_ac_time3)
    TextView tvAcTime3;
    @InjectView(R.id.tv_ac_activity)
    TextView tvAcActivity;
    @InjectView(R.id.tv_ac_acpro)
    TextView tvAcAcpro;
    @InjectView(R.id.ll_ac_l)
    LinearLayout llAcL;
    @InjectView(R.id.tv_ac_master)
    TextView tvAcMaster;
    @InjectView(R.id.bt_ac_join)
    Button btAcJoin;
    @InjectView(R.id.tv_ac_title)
    TextView tvAcTitle;
    @InjectView(R.id.tv_ac_name)
    TextView tvAcName;

    @InjectView(R.id.pull)
    TextView pull;
    @InjectView(R.id.ac_con_title)
    TitleBar acConTitle;
    @InjectView(R.id.v_bgline)
    View vBgline;
    @InjectView(R.id.rl_ac_master)
    RelativeLayout rlAcMaster;
    @InjectView(R.id.ac_bm_nums)
    TextView acBmNums;
    @InjectView(R.id.tv_ac_bm_nums)
    TextView tvAcBmNums;
    @InjectView(R.id.v_line_item)
    View vLineItem;
    @InjectView(R.id.gridview)
    GridView gridview;
    @InjectView(R.id.rl_ac_rl)
    RelativeLayout rlAcRl;

    private ListView lv_choose;
    Activity activity;
    List<User> users = new ArrayList<>();
    public Button ac;
    public Button cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_item_content);
        gridview = ((GridView) findViewById(R.id.gridview));
         ButterKnife.inject(this);
        initData();
        initEvent();
        System.out.println(activity.getUser().getUserId());
        System.out.println("++++++++"+((MyApplication)getApplication()).getUser().getUserId());
        if (activity.getUser().getUserId()==((MyApplication)getApplication()).getUser().getUserId()){
            pull.setVisibility(View.VISIBLE);
            btAcJoin.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        //点击按钮之后，按钮消失；
//        btAcJoin.setVisibility(View.GONE);
        pull.setVisibility(View.VISIBLE);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        pull.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupwindow(v);
//            }
//        });
//
//    }

    private void initEvent() {
        gridview.setAdapter(new BaseAdapter() {
            public ImageView img;

            @Override
            public int getCount() {
                return users.size();
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
                View view = View.inflate(ActivityInfo.this, R.layout.img_view, null);
                User user = users.get(position);
                img = ((ImageView) view.findViewById(R.id.item_gridview));
                xUtilsImageUtils.display(img, HttpUtils.localhost_su + user.getPhoto(), true);
                return view;
            }
        });

    }

    public void initData() {
        Intent intent = getIntent();
        activity = intent.getParcelableExtra("ActivityInfo");
        System.out.println(activity);
        //将活动id传递给服务端，通过服务端的方法获得相应的用户信息；
        RequestParams requestParams = new RequestParams(HttpUtils.localhost_su + "queryac");
        Integer acId = activity.getActivityId();
        Log.i(TAG, "onItemClick: --------->传递id：" + acId);
        requestParams.addBodyParameter("acId", String.valueOf(acId));
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: result是=====>>：" + result);
                Gson gson = new Gson();
                Type type = new TypeToken<List<User>>() {
                }.getType();
                users = gson.fromJson(result, type);
                Log.i(TAG, "onSuccess: 根据活动id返还回来的参与人的信息：" + users);
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

        Log.i("ActivityInfo", "initData: ------>" + activity);
        if (activity != null) {
            tvAcTitle.setText(activity.getActivityTitle());
            tvAcName.setText(activity.getUser().getUserName());
            tvAcAcpro.setText(activity.getActivityContent());
            tvAcAddress.setText(activity.getActivityAddress());
            tvAcTime3.setText(dateToString1(activity.getBeginTime()) + " -- " + dateToString1(activity.getEndTime()));
            xUtilsImageUtils.display(ivAcImage, HttpUtils.localhost_su + activity.getActivityImg());
        }
    }

    private void showPopupwindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.cancel_ac, null);

        ac = ((Button) contentView.findViewById(R.id.cancel_ac));
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btAcJoin.setVisibility(View.VISIBLE);
            }

        });
        cancel = ((Button) contentView.findViewById(R.id.cancel));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final PopupWindow popupwindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupwindow.setTouchable(true);

        popupwindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupwindow.setBackgroundDrawable(new BitmapDrawable());

        // 设置好参数之后再show
//        popupWindow.showAsDropDown(view,100,50);
        popupwindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    @OnClick({R.id.bt_ac_join,R.id.pull,R.id.ac_con_title})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ac_join:

            Intent intent = new Intent(ActivityInfo.this, JoinActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("acId", activity.getActivityId());
            intent.putExtras(bundle);
            startActivity(intent);
            Log.i(TAG, "onClick: +++++++>传递到下一个页面的id" + activity.getActivityId());
                break;
            case R.id.pull:
                showPopupwindow(v);
                break;
            case R.id.ac_con_title:
                finish();
                break;
        }

    }


}
