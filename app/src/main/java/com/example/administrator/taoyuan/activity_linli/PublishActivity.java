package com.example.administrator.taoyuan.activity_linli;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;

public class PublishActivity extends AppCompatActivity {

    private ImageView iv_photo;
    private Button camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        iv_photo = ((ImageView) findViewById(R.id.iv_photo));

        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopupwindow(v);
            }
        });
    }

    private void showpopupwindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.takephotos,null);

        camera = ((Button) contentView.findViewById(R.id.camera));

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PublishActivity.this,"takephotos",Toast.LENGTH_LONG).show();
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
        popupwindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.home_gray));

        // 设置好参数之后再show
        //popupWindow.showAsDropDown(view,100,50);

        popupwindow.showAtLocation(view, Gravity.BOTTOM,0,0);
    }
}
