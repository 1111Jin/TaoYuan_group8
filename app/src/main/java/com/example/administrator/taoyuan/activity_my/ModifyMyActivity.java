package com.example.administrator.taoyuan.activity_my;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.fragment.Modify_myName_fragment;
import com.example.administrator.taoyuan.pojo.ListActivityBean;
import com.example.administrator.taoyuan.utils.HttpUtils;

import org.xutils.http.RequestParams;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ModifyMyActivity extends AppCompatActivity {

    @InjectView(R.id.all_order_goback)
    ImageView allOrderGoback;
    @InjectView(R.id.rl_back)
    RelativeLayout rlBack;
    @InjectView(R.id.rl_save)
    RelativeLayout rlSave;
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
    @InjectView(R.id.iv_to_myBirthday)
    ImageView ivToMyBirthday;
    @InjectView(R.id.tv_modify_myBirthday)
    TextView tvModifyMyBirthday;
    @InjectView(R.id.rl_myBirthday)
    RelativeLayout rlMyBirthday;

    ListActivityBean.User user;
    private RelativeLayout rl_back;
    String items[] = {"相册选择", "拍照"};
    String sex[] = {"男", "女"};
    public static final int SELECT_PIC = 11;
    public static final int TAKE_PHOTO = 12;
    //相机拍摄照片和视频的标准目录
    private File file;
    private Uri imageUri;
    Modify_myName_fragment modify_myName_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_my);
        ButterKnife.inject(this);


        //判断sd卡是否存在，存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            file = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
            imageUri = Uri.fromFile(file);
        }

        initView();
        initData();
        initEvevt();
    }

    public void initView() {
        rl_back = ((RelativeLayout) findViewById(R.id.rl_back));
    }

    public void initData() {

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        if (user != null) {
            tvModifyMyName.setText(user.userName);
            tvModifyMyAddress.setText(user.userProfiles);
            tvModifyMyProfiles.setText(user.userProfiles);
            tvModifyMySex.setText(user.userSex ? "男" : "女");
            tvModifyMyTel.setText(user.userTel);
        }
    }

    public void initEvevt() {
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListActivityBean.User user_modify=new ListActivityBean.User();

                RequestParams requestParams=new RequestParams(HttpUtils.localhost);
//                requestParams.addQueryStringParameter("user", );
            }
        });
    }

    @OnClick({R.id.rl_mytx, R.id.rl_myName, R.id.rl_mySex,R.id.rl_myTel,R.id.rl_modify_myAddress,R.id.rl_myProfiles,R.id.rl_myBirthday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mytx:
                new AlertDialog.Builder(this).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
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
                                startActivityForResult(intent2, TAKE_PHOTO);

                                break;
                        }
                    }

                }).show();
                break;
            case R.id.rl_myName:
                final RelativeLayout modify_myName=(RelativeLayout) getLayoutInflater().inflate(R.layout.activity_modify_my_name_activity,null);
                new AlertDialog.Builder(this).setView(modify_myName)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               EditText et_name=((EditText) modify_myName.findViewById(R.id.et_edit_name));
                                String name=et_name.getText().toString();
                                tvModifyMyName.setText(name);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                             }
                        })

                        .show();
                break;
            case R.id.rl_mySex:
                int a = user.userSex ? 0 : 1;
                new AlertDialog.Builder(this).setSingleChoiceItems(sex, a, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                tvModifyMySex.setText("男");
                                break;
                            case 1:
                                tvModifyMySex.setText("女");
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.rl_myTel:
                final RelativeLayout modify_mytel=(RelativeLayout) getLayoutInflater().inflate(R.layout.activity_modify_my_name_activity,null);
                new AlertDialog.Builder(this).setView(modify_mytel)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText et_tel=((EditText) modify_mytel.findViewById(R.id.et_edit_name));
                                String tel=et_tel.getText().toString();
                                tvModifyMyTel.setText(tel);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        .show();
                break;
            case R.id.rl_modify_myAddress:
                final RelativeLayout modify_myaddress=(RelativeLayout) getLayoutInflater().inflate(R.layout.activity_modify_my_name_activity,null);
                new AlertDialog.Builder(this).setView(modify_myaddress)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText et_name=((EditText) modify_myaddress.findViewById(R.id.et_edit_name));
                                String name=et_name.getText().toString();
                                tvModifyMyAddress.setText(name);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        .show();
                break;
            case R.id.rl_myProfiles:
                final RelativeLayout modify_myprofiles=(RelativeLayout) getLayoutInflater().inflate(R.layout.activity_modify_my_name_activity,null);
                new AlertDialog.Builder(this).setView(modify_myprofiles)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText et_name=((EditText) modify_myprofiles.findViewById(R.id.et_edit_name));
                                String name=et_name.getText().toString();
                                tvModifyMyProfiles.setText(name);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        .show();
                break;
            case R.id.rl_myBirthday:
                final RelativeLayout modify_mybirthday=(RelativeLayout) getLayoutInflater().inflate(R.layout.activity_modify_my_name_activity,null);
                new AlertDialog.Builder(this).setView(modify_mybirthday)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText et_name=((EditText) modify_mybirthday.findViewById(R.id.et_edit_name));
                                String name=et_name.getText().toString();
                                tvModifyMyBirthday.setText(name);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })

                        .show();
                break;



        }
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                String name = data.getStringExtra("name");
                Log.i("Result name", "onActivityResult: " + name);
                tvModifyMyName.setText(name);
                break;
            default:
                break;

        }
    }
}
