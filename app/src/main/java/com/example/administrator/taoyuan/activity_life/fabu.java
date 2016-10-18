package com.example.administrator.taoyuan.activity_life;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class fabu extends AppCompatActivity {

    @InjectView(R.id.btn_fabu)
    Button btnFabu;
    @InjectView(R.id.btn_fabu1)
    Button btnFabu1;
    @InjectView(R.id.ss)
    RelativeLayout ss;
    @InjectView(R.id.leixing)
    TextView leixing;
    @InjectView(R.id.ll_leixing)
    LinearLayout llLeixing;
    @InjectView(R.id.biaoti)
    TextView biaoti;
    @InjectView(R.id.tv_biaoti)
    EditText tvBiaoti;
    @InjectView(R.id.neirong)
    TextView neirong;
    @InjectView(R.id.neirong_c)
    EditText neirongC;
    @InjectView(R.id.tupian)
    TextView tupian;
    @InjectView(R.id.tupian_1)
    ImageView tupian1;
    @InjectView(R.id.editText)
    TextView editText;
    private Button btn_fabu1;

    private ArrayList<ListLifeInfo.LifeInfo> lifelist = new ArrayList<ListLifeInfo.LifeInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_life);
        ButterKnife.inject(this);

//        initView();
//        initData();
//        initEvent();

    }

//    private void initView() {
//
//    }
//
//    private void initData() {
//
//    }
//
//    private void initEvent() {
//
//    }


    @OnClick(R.id.btn_fabu1)
    public void onClick() {
        //访问服务器
        //添加到网络；
        RequestParams requestParams = new RequestParams(HttpUtils.localhost_jt + "getLifefabuInfo");

//            requestParams.addBodyParameter("content",neirongC.getText().toString());

        //对象转换成json数据
//            Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
//                    .create();
        Gson gson = new Gson();
        ListLifeInfo.LifeInfo ln = new ListLifeInfo.LifeInfo();
        ln.content = neirongC.getText().toString();

        String content = gson.toJson(ln);

        requestParams.addBodyParameter("content", content);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("fabu", "onSuccess: ");
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


}
