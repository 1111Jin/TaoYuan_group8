package com.example.administrator.taoyuan.activity_my;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.pojo.ListActivityBean;
import com.example.administrator.taoyuan.utils.HttpUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
    @InjectView(R.id.iv_to_myBirthday)
    ImageView ivToMyBirthday;
    @InjectView(R.id.tv_modify_myBirthday)
    TextView tvModifyMyBirthday;
    @InjectView(R.id.rl_myBirthday)
    RelativeLayout rlMyBirthday;

    ListActivityBean.User user;
    Bitmap bm;
    private RelativeLayout rl_back;
    String items[] = {"相册选择", "拍照"};
    String sex[] = {"男", "女"};

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    //相机拍摄照片和视频的标准目录
    private File file ;
    private Uri imageUri;
    private ImageView mImageView;
    private RelativeLayout rl_save;
    private TextView tv_save;


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
        rl_save = ((RelativeLayout) findViewById(R.id.rl_save));
        tv_save = ((TextView) findViewById(R.id.tv_save));
    }

    public void initData() {

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        bm=  intent.getParcelableExtra("head");
        ivModifyMyHead.setImageBitmap(bm);
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
        rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"save",Toast.LENGTH_SHORT).show();

//                sendImg();
//                ListActivityBean.User user_modify=new ListActivityBean.User();
//
//                RequestParams requestParams=new RequestParams(HttpUtils.localhost);
//                requestParams.addQueryStringParameter("user", );
            }
        });

//        tv_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"save++",Toast.LENGTH_SHORT).show();
//            }
//        });
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
                                getPicFromPhoto();

                                break;
                            case 1:
                                //拍照:
                                getPicFromCamera();

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

    private void sendImg() {
        RequestParams params = new RequestParams(HttpUtils.localhost+"upload");//upload 是你要访问的servlet

//        params.addBodyParameter("fileName","fileName");
        Log.i("FILE+++++++", "sendImg: "+file);
        params.addBodyParameter("file",file);
//        params.addBodyParameter("file",file1);

        System.out.println(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("EEEEEEE", "onError: "+ex);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CLIP);
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

        System.out.println("============"+ UUID.randomUUID());
        return sdf.format(date)+"_"+UUID.randomUUID() + ".png";
    }

    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        System.out.println("getPicFromCamera==========="+file.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
//        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String fileName = System.currentTimeMillis() + ".jpg";
//        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功  固定
                        System.out.println("CAMERA_REQUEST"+file.getAbsolutePath());
                        if (file.exists()) {
                            photoClip(Uri.fromFile(file));
                        }
                        break;
                    default:
                        break;
                }
                break;
            case PHOTO_REQUEST:
                if (data != null) {
                    photoClip(data.getData());

                }
                break;
            case PHOTO_CLIP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Log.w("test", "data");
                        Bitmap photo = extras.getParcelable("data");
                        saveImageToGallery(getApplication(),photo);//保存bitmap到本地
                        ivModifyMyHead.setImageBitmap(photo);
                          break;


                    }
                }
                break;
            default:
                break;

        }
    }
}
