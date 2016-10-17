package com.example.administrator.taoyuan.activity_linli;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.taoyuan.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PublishHelp extends AppCompatActivity {

    @InjectView(R.id.et_help_title)
    EditText etHelpTitle;
    @InjectView(R.id.et_help_profiles)
    EditText etHelpProfiles;
    @InjectView(R.id.et_help_time)
    EditText etHelpTime;
    @InjectView(R.id.iv_help_photo)
    ImageView ivHelpPhoto;
    @InjectView(R.id.ll_help_ll)
    LinearLayout llHelpLl;
    @InjectView(R.id.et_help_address)
    EditText etHelpAddress;
    @InjectView(R.id.et_help_numms)
    EditText etHelpNumms;
    private ImageView iv_photo;

    private File file;
    private Uri imageUri;

    private Button camera;
    private Button choosephoto;
    private static final int SELECT_PIC = 0;
    public static final int TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_help);
        ButterKnife.inject(this);
        //判断sd卡是否存在，存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            file = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
            imageUri = Uri.fromFile(file);
        }

        initView();

        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopupwindow(v);
            }
        });
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    private void showpopupwindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.takephotos, null);

        camera = ((Button) contentView.findViewById(R.id.camera));

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent2, TAKE_PHOTO);
            }

        });
        choosephoto = ((Button) contentView.findViewById(R.id.choosePhoto));
        choosephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册选择
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, SELECT_PIC);
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
        //popupWindow.showAsDropDown(view,100,50);
        popupwindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public void initView(){
        iv_photo = ((ImageView) findViewById(R.id.iv_help_photo));
    }

    @OnClick(R.id.iv_help_photo)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_help_photo:
                showpopupwindow(view);
        }
    }
}
