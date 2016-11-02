package com.example.administrator.taoyuan.activity_linli;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.AcJoin;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.TitleBar;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2016/10/27.
 */
public class JoinActivity extends AppCompatActivity {
    private static final String TAG ="JoinActivity" ;
    @InjectView(R.id.join_title)
    TitleBar joinTitle;
    @InjectView(R.id.et_tel)
    EditText etTel;
    @InjectView(R.id.ll_join_tel)
    LinearLayout llJoinTel;
    @InjectView(R.id.tv_telp)
    TextView tvTelp;
    @InjectView(R.id.self)
    EditText self;
    @InjectView(R.id.join_hand)
    Button joinHand;
    String tel;
    Integer userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_content);
        ButterKnife.inject(this);



    }

    @OnClick({R.id.join_hand,R.id.join_title})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_hand:
            Bundle bundle = new Bundle();
            bundle = this.getIntent().getExtras();
            final Integer acId = bundle.getInt("acId");
            Log.i(TAG, "onClick: =======接收到的acID为：" + acId);
            userId = ((MyApplication) getApplication()).getUser().getUserId();
            tel = etTel.getText().toString();
            String selfp = self.getText().toString();
            Log.i(TAG, "onClick: ------获取输入的电话号码：" + tel);


            //若是电话号码为空，则不能继续报名；
            if (tel.equals("") || tel == null) {
                Toast.makeText(this, "电话号码不能为空！！", Toast.LENGTH_SHORT).show();
            } else {
                RequestParams params = new RequestParams(HttpUtils.localhost_su + "insertacjoin");
                AcJoin acJoin = new AcJoin(acId, userId, tel, selfp);
                Gson gson = new Gson();
                String acJson = gson.toJson(acJoin);
                Log.i(TAG, "onClick: 传递到服务端的数据：" + acJson);
                params.addBodyParameter("acJoin", acJson);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i(TAG, "onSuccess: 成功插入参与表：" + result);
                        Toast.makeText(getApplicationContext(), "参与成功", Toast.LENGTH_SHORT).show();
                        //设置Tag，群组
                        Set<String> sets = new HashSet<>();
                        String tag=String.valueOf(acId);
//                       sets.add("sport");//运行第二个模拟器上时把这个注掉
                         sets.add(tag);//运行第二个模拟器上时把这个打开

                        JPushInterface.setTags(getApplicationContext(), sets, new TagAliasCallback() {
                         @Override
                             public void gotResult(int i, String s, Set<String> set) {
                         Log.d("alias", "set tag result is" + i);
                        }
                        });
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e(TAG, "onError: 插入参与表的地方出错了：" + ex);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

                finish();
            }

                break;
            case R.id.join_title:
                finish();


        }
    }
}
