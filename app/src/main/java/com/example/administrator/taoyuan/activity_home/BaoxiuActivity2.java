package com.example.administrator.taoyuan.activity_home;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_my.RepairActivity;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.Leixing;
import com.example.administrator.taoyuan.pojo.RepairBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BaoxiuActivity2 extends Activity {

    @InjectView(R.id.rl)
    RelativeLayout rl;
    @InjectView(R.id.v1)
    View v1;
    @InjectView(R.id.tv1)
    TextView tv1;
    @InjectView(R.id.tv2)
    TextView tv2;
    @InjectView(R.id.tv3)
    TextView tv3;
    @InjectView(R.id.tv4)
    TextView tv4;
    @InjectView(R.id.v2)
    View v2;
    @InjectView(R.id.tv7)
    TextView tv7;
    @InjectView(R.id.tv5)
    TextView tv5;
    @InjectView(R.id.et)
    EditText et;
    @InjectView(R.id.v3)
    View v3;
    @InjectView(R.id.tv6)
    TextView tv6;
    @InjectView(R.id.et1)
    EditText et1;
    @InjectView(R.id.v4)
    View v4;
    @InjectView(R.id.tv8)
    TextView tv8;
    @InjectView(R.id.et2)
    EditText et2;
    @InjectView(R.id.v5)
    View v5;
    @InjectView(R.id.tv9)
    TextView tv9;
    @InjectView(R.id.et3)
    EditText et3;
    @InjectView(R.id.bt)
    Button bt;
    @InjectView(R.id.bt7)
    Button bt7;
    @InjectView(R.id.tv11)
    TextView tv11;
    @InjectView(R.id.iv)
    ImageView iv;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private final static int DIALOG = 0;
    private Dialog dialog = null;
    Leixing leixin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoxiu2);
        ButterKnife.inject(this);
        leixin = (Leixing) getIntent().getSerializableExtra("e");
        tv2.setText(leixin.getLeixing());
        tv4.setText(leixin.getXiangmu());

    }

    @TargetApi(Build.VERSION_CODES.M)
    @OnClick({R.id.bt, R.id.et2, R.id.et3, R.id.tv11,R.id.iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt:
                Intent intent = new Intent(BaoxiuActivity2.this, BaoxiuActivity1.class);
                startActivity(intent);
                break;
            case R.id.et2:
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                view = View.inflate(this, R.layout.date_time_dialog, null);
                final DatePicker datePicker = (DatePicker)view.findViewById(R.id.date_picker);
                final TimePicker timePicker = ((TimePicker)view.findViewById(R.id.time_picker));
                builder.setView(view);

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                datePicker.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),null);

                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(cal.get(Calendar.MINUTE));
                timePicker.setCurrentMinute(Calendar.MINUTE);
                builder.setTitle("选取起始时间");
                builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth()));
                        sb.append(" ");
                        Integer hour=timePicker.getCurrentHour();
                        sb.append((hour<10?"0"+hour:hour+"")).append(":").append(timePicker.getCurrentMinute()).append(":").append("00");
                        //sb.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());

                        et2.setText(sb);
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.et3:
                break;
            case R.id.tv11:
                Intent intent2 = new Intent(BaoxiuActivity2.this, RepairActivity.class);
                startActivity(intent2);
                break;
            case R.id.iv:
                finish();
                break;
        }
    }

    public void baoXiu() {
        String url = Netutil.url + "insertRepair";
        RequestParams params = new RequestParams(url);
        String repairTitle = tv4.getText().toString();
        String repairType = tv2.getText().toString();
        String repairContent = leixin.getContent();
        String repairImg = leixin.getPictureName();
        String repairAddress = et3.getText().toString();
        String repairDate = et2.getText().toString();
        String repairName = et.getText().toString();
        String repairTel = et1.getText().toString();
        String weixiu="null";
        String userId= String.valueOf((((MyApplication) getApplication()).getUser().getUserId()));
        RepairBean rb = new RepairBean(repairTitle, repairType, repairContent, repairImg, repairAddress, repairDate, repairName, repairTel,userId,weixiu);
        Gson gson = new Gson();
        String repairgson = gson.toJson(rb);
        params.addBodyParameter("repairList", repairgson);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast toast = Toast.makeText(BaoxiuActivity2.this, "报修成功", Toast.LENGTH_LONG);
                toast.show();
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast toast = Toast.makeText(BaoxiuActivity2.this, "报修失败，请检查网络是否连接", Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @OnClick(R.id.bt7)
    public void onClick() {
        String number=et1.getText().toString();
        boolean judge = isMobile(number);
        if (et2.getText().toString().equals("")||et2.getText().toString().equals("请选择预约时间")){
            Toast toast=Toast.makeText(BaoxiuActivity2.this,"请选择预约时间",Toast.LENGTH_LONG);
            toast.show();
        }else if(judge==false) {
            Toast toast = Toast.makeText(BaoxiuActivity2.this, "手机号不合法，请重新输入", Toast.LENGTH_SHORT);
            toast.show();
        }else if (et3.getText().toString().equals("")||et3.getText().toString().equals("请输入地址")) {
            Toast toast = Toast.makeText(BaoxiuActivity2.this, "地址不能为空", Toast.LENGTH_SHORT);
            toast.show();
        }else {
            baoXiu();
        }

    }
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

}
