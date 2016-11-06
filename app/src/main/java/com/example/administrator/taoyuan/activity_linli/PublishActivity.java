package com.example.administrator.taoyuan.activity_linli;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.Activity;
import com.example.administrator.taoyuan.pojo.ActivityBean;
import com.example.administrator.taoyuan.utils.DateUtil;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {

    @InjectView(R.id.tv_bt)
    EditText tvBt;
    @InjectView(R.id.tv_content1)
    EditText tvContent1;
    @InjectView(R.id.tv_begin)
    EditText tvBegin;
    @InjectView(R.id.tv_end)
    EditText tvEnd;
    @InjectView(R.id.iv_photo)
    ImageView ivPhoto;
    @InjectView(R.id.ll)
    LinearLayout ll;
    @InjectView(R.id.tv_hdress)
    EditText tvHdress;
    @InjectView(R.id.tv_hl_nums)
    EditText tvHlNums;
    @InjectView(R.id.bt_submit)
    Button btSubmit;
    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +
            "/" + getPhotoFileName());

    private static final String TAG = "PublishActivity";
    private String items[] = {"拍照", "从相册选择"};
    String path = "";

    Activity activity;

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.inject(this);
        tvBegin.setOnTouchListener(this);
        tvEnd.setOnTouchListener(this);

    }

    //使用系统当前时间加以调整作为照片的名称；
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }


    //关闭某个Activity时，会自动调用此方法；
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功；
                        System.out.println("CAMERA_REQUEST" + file.getAbsolutePath());
                        if (file.exists()) {
                            photoClip(Uri.fromFile(file));
                        }
                    default:
                        break;
                }
                break;
            case PHOTO_CLIP:
                if (data != null) {
                    //activity之间互相传数据；
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        //保存到本地；
                        saveImageToGallery(PublishActivity.this, photo);
                        ivPhoto.setImageBitmap(photo);
                        uploadImage();
                    }

                }
                break;
            case PHOTO_REQUEST:
                if (data != null) {
                    photoClip(data.getData());
                }
                break;
        }
    }

    //上传照片;
    private void uploadImage() {
        RequestParams params = new RequestParams(HttpUtils.localhost_su+"uploadimg");
        params.setMultipart(true);
        params.addBodyParameter("file", file);
        System.out.println(file.getName());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: 图片传递成功："+result);
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

    //图片保存；
    private void saveImageToGallery(Context context, Bitmap photo) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //把图片进行压缩，压缩成JPEG、PNG格式；
            photo.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //把文件插入到系统图库；
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //通知手机更新图库；
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    //图片裁剪；
    private void photoClip(Uri uri) {
        //调用系统中自带的图片修剪；
        Intent intent = new Intent("com.android.camera.action.CROP");
        //图片路径及类型；
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可剪裁；
        intent.putExtra("crop", "true");
        //aspectX aspectY是宽高的比例；
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //outputX outputY 是裁剪图片的宽高；
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CLIP);
    }

    //获取输入的数据：
    private void getData(){
        Integer userId = ((MyApplication)getApplication()).getUser().getUserId();
        System.out.println("获取一个固定的userId:"+userId);
        String name = tvBt.getText().toString();
        String pro =tvContent1.getText().toString();
        String Imgurl="upload/"+file.getName();
        System.out.println("传上去的图片的url"+Imgurl);
        Timestamp beg = Timestamp.valueOf((tvBegin.getText().toString())+":00");
//        Timestamp ed = DateUtil.stringToDate(tvEnd.getText().toString());
        Timestamp ed = Timestamp.valueOf((tvEnd.getText().toString())+":00");
        String addr = tvHdress.getText().toString();
        Integer num = Integer.parseInt(tvHlNums.getText().toString());
        Timestamp create = new Timestamp(System.currentTimeMillis());

        /**
         * Integer userId,String activityTitle,String activityContent,Timestamp beginTime, Timestamp endTime,
         String activityImg,String activityAddress,Timestamp createTime,Integer joinNums
         */
        //封装成一个对象；
        activity = new Activity(userId,name,pro,beg,ed,Imgurl,addr,create,num);
    }

    //数据传输(插入数据库)；
    private void submitData() {
        getData();

        RequestParams request = new RequestParams(HttpUtils.localhost_su+"insertactivity");
        //gson串，将对象转化成string类型的传递给服务端；
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
        Gson gson = new Gson();
        String acInfo = gson.toJson(activity);
        System.out.println("要传递过去的数据："+acInfo);
        request.addBodyParameter("acInfo",acInfo);
        //传递数据；
        x.http().post(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: 客户端要传递的数据(ps:表示已经可以传递)：" + result);
                System.out.println("客户端要传递的数据(ps:表示已经可以传递)：");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: 出现错误了："+ex);
                System.out.println("onError: 出现错误了："+ex);

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    //按钮点击事件：拍照和发布按钮
    @OnClick({R.id.iv_photo, R.id.bt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_photo:
                new AlertDialog.Builder(PublishActivity.this).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //把拍照的相机调出来；
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                //以下这句指定调用相机拍照后的照片存储的路径；
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                startActivityForResult(intent, CAMERA_REQUEST);
                                break;
                            case 1:
                                Intent intt = new Intent(Intent.ACTION_PICK, null);
                                intt.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(intt, PHOTO_REQUEST);
                        }
                    }
                }).show();
                break;
            case R.id.bt_submit:
                Toast.makeText(getApplicationContext(), "发布成功！", Toast.LENGTH_SHORT).show();
                submitData();
                finish();
                break;
        }
    }

    //日期插件；
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this,R.layout.date_time_dialog,null);
            final DatePicker datePicker = (DatePicker)view.findViewById(R.id.date_picker);
            final TimePicker timePicker = ((TimePicker)view.findViewById(R.id.time_picker));
            builder.setView(view);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            datePicker.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),null);

            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(cal.get(Calendar.MINUTE));
            timePicker.setCurrentMinute(Calendar.MINUTE);

            if (v.getId() == R.id.tv_begin){
                final int inType = tvBegin.getInputType();
                tvBegin.setInputType(InputType.TYPE_NULL);
                tvBegin.onTouchEvent(event);
                tvBegin.setInputType(inType);
                tvBegin.setSelection(tvBegin.getText().length());

                builder.setTitle("选取起始时间");
                builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth()));
                        sb.append(" ");
                        Integer hour=timePicker.getCurrentHour();
                        sb.append((hour<10?"0"+hour:hour+"")).append(":").append(timePicker.getCurrentMinute());
                        //sb.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());

                        tvBegin.setText(sb);
                        tvEnd.requestFocus();

                        dialog.cancel();
                    }
                });
            }else if (v.getId() == R.id.tv_end){
                int inType = tvEnd.getInputType();
                tvEnd.setInputType(InputType.TYPE_NULL);
                tvEnd.onTouchEvent(event);
                tvEnd.setInputType(inType);
                tvEnd.setSelection(tvEnd.getText().length());

                builder.setTitle("选取结束时间");
                builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth()));
                        sb.append(" ");
                        sb.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());

                        tvEnd.setText(sb);
                        dialog.cancel();
                    }
                });
            }

            Dialog dialog = builder.create();
            dialog.show();
        }
        return true;
    }
}
