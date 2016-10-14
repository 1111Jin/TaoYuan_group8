package com.example.administrator.taoyuan.activity_my;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ListActivityBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ModifyMyActivity extends AppCompatActivity {

    @InjectView(R.id.all_order_goback)
    ImageView allOrderGoback;
    @InjectView(R.id.iv_to_myHead)
    ImageView ivToMyHead;
    @InjectView(R.id.iv_modify_myHead)
    ImageView ivModifyMyHead;
    @InjectView(R.id.rl_mytx)
    RelativeLayout rlMytx;
    @InjectView(R.id.iv_to_myName)
    ImageView ivToMyName;
    @InjectView(R.id.tv_modify_myName)
    TextView tvModifyMyName;
    @InjectView(R.id.rl_myName)
    RelativeLayout rlMyName;
    @InjectView(R.id.iv_to_mySex)
    ImageView ivToMySex;
    @InjectView(R.id.tv_modify_mySex)
    TextView tvModifyMySex;
    @InjectView(R.id.rl_mySex)
    RelativeLayout rlMySex;
    @InjectView(R.id.iv_to_myTel)
    ImageView ivToMyTel;
    @InjectView(R.id.tv_modify_myTel)
    TextView tvModifyMyTel;
    @InjectView(R.id.rl_myTel)
    RelativeLayout rlMyTel;
    @InjectView(R.id.iv_to_myAddress)
    ImageView ivToMyAddress;
    @InjectView(R.id.tv_modify_myAddress)
    TextView tvModifyMyAddress;
    @InjectView(R.id.rl_modify_myAddress)
    RelativeLayout rlModifyMyAddress;
    @InjectView(R.id.iv_to_myprofiles)
    ImageView ivToMyprofiles;
    @InjectView(R.id.tv_modify_myProfiles)
    TextView tvModifyMyProfiles;
    @InjectView(R.id.rl_myProfiles)
    RelativeLayout rlMyProfiles;

    ListActivityBean.User user;
    private RelativeLayout rl_back;
    String items[]={"相册选择","拍照"};
    public static final int SELECT_PIC=11;
    public static final int TAKE_PHOTO=12;
    //相机拍摄照片和视频的标准目录
    private File file;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_my);
        ButterKnife.inject(this);

        //判断sd卡是否存在，存在
        if(Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED) ){
            //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            file=new File(Environment.getExternalStorageDirectory(),getPhotoFileName());
            imageUri= Uri.fromFile(file);
        }

        initView();
        initData();
        initEvevt();
    }

    public void initView(){
        rl_back = ((RelativeLayout) findViewById(R.id.rl_back));
    }

    public void initData(){

        Intent intent=getIntent();
        user=intent.getParcelableExtra("user");
        if(user!=null){
            tvModifyMyName.setText(user.userName);
            tvModifyMyAddress.setText(user.userProfiles);
            tvModifyMyProfiles.setText(user.userProfiles);
            tvModifyMySex.setText(user.userSex?"男":"女");
            tvModifyMyTel.setText(user.userTel);
        }
    }

    public void initEvevt(){
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.rl_mytx,R.id.rl_myName})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_mytx:
                new AlertDialog.Builder(this).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                //相册选择
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        "image/*");
                                startActivityForResult(intent, SELECT_PIC);
                                break;
                            case 1:
                                //拍照:
                                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                startActivityForResult(intent2,TAKE_PHOTO);

                                break;
                        }
                    }

                }).show();

        }
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }


}
