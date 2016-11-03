package com.example.administrator.taoyuan.activity_life;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.fragment.Life;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


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


    private ArrayList<ListLifeInfo.LifeInfo> lifelist = new ArrayList<ListLifeInfo.LifeInfo>();
    Bitmap bm;
    String items[]={"相册选择","拍照"};

    public static final int SELECT_PIC=11;
    public static final int TAKE_PHOTO=12;
    public static final int CROP=13;

    private File file ;
    private Uri imageUri;
    private ImageView mImageView;
    private RelativeLayout rl_save;
    private TextView tv_save;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_life);
        ButterKnife.inject(this);


        //判断sd卡是否存在，存在
        if(Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED) ){
            //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            file=new File(Environment.getExternalStorageDirectory(),getPhotoFileName());
            imageUri= Uri.fromFile(file);


        }


        initEvent();
    }



    private void initEvent() {
        btnFabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
    @OnClick(R.id.tupian_1)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tupian_1:
                //修改头像
                new AlertDialog.Builder(this).setTitle("选择").setItems(items,new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

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
                break;

        }
    }


    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";

    }



    public void crop(Uri uri){
        //  intent.setType("image/*");
        //裁剪
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //
        switch (requestCode){
            case SELECT_PIC:
                //相册选择
                if (data != null) {
                    crop(data.getData());

                }

                break;
            case TAKE_PHOTO:
                crop(Uri.fromFile(file));
                break;


            case CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {

                        Bitmap bitmap = extras.getParcelable("data");
                        showImage(bitmap);
                    }
                }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    //显示图片，上传服务器
    public void showImage(Bitmap bitmap){
        tupian1.setImageBitmap(bitmap);//iv显示图片
        saveImage(bitmap);//保存文件
//        uploadImage();//上传服务器

    }

    //将bitmap保存在文件中
    public void saveImage(Bitmap bitmap){
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
    }



    @OnClick(R.id.btn_fabu1)
    public void onClick() {
        //访问服务器
        //添加到网络；
        RequestParams requestParams = new RequestParams(HttpUtils.localhost_jt + "getLifefabuInfo");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file);

        Gson gson = new Gson();
        ListLifeInfo.LifeInfo ln = new ListLifeInfo.LifeInfo();
        ln.content = neirongC.getText().toString();
        ln.userId = HttpUtils.userId;

        String content = gson.toJson(ln);

        requestParams.addBodyParameter("lifefabu", content);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("fabu", "onSuccess: ");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex + "++++++++++++++++++++++++++++++++");
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




    }
