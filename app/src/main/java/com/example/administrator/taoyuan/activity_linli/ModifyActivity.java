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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.Activity;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.administrator.taoyuan.utils.DateUtil.dateToString;

public class ModifyActivity extends AppCompatActivity implements View.OnTouchListener{

    public EditText title;
    public EditText content;
    public ImageView photo;
    public EditText beg;
    public EditText end;
    public EditText nums;
    public EditText address;
    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +
            "/" + getPhotoFileName());

    private static final String TAG = "PublishActivity";
    private String items[] = {"拍照", "从相册选择"};
    String path = "";

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    String url;
    Activity activity;
    public Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        Intent intent = getIntent();
        activity = intent.getParcelableExtra("acInfo");
        initView();
        initEvent();
        initData();
//        getData();
    }


    private void initView() {
        title = ((EditText) findViewById(R.id.ac_modify_title));
        content = ((EditText) findViewById(R.id.ac_modify_content));
        photo = ((ImageView) findViewById(R.id.ac_modify_ph));
        beg = ((EditText) findViewById(R.id.ac_et_bg));
        end = ((EditText) findViewById(R.id.ac_et_ed));
        nums = ((EditText) findViewById(R.id.ac_tv_nums));
        address = ((EditText) findViewById(R.id.et_md_address));
        save = ((Button) findViewById(R.id.ac_md_save));
    }

    private void initData() {
        title.setText(activity.getActivityTitle());
        content.setText(activity.getActivityContent());
        xUtilsImageUtils.display(photo, HttpUtils.localhost_su+activity.getActivityImg(),false);
        url=activity.getActivityImg();
        beg.setText(dateToString(activity.getBeginTime()));
        end.setText(dateToString(activity.getEndTime()));
        nums.setText(activity.getJoinNums()+"");
        address.setText(activity.getActivityAddress());
    }

    private void initEvent(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                RequestParams params = new RequestParams(HttpUtils.localhost_su+"modifyactivity");
                Gson gson = new Gson();
                String acInfo = gson.toJson(activity);
                params.addBodyParameter("acInfo",acInfo);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(ModifyActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i(TAG, "onError: 修改的地方出错误了："+ex);
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
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ModifyActivity.this).setItems(items, new DialogInterface.OnClickListener() {
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
            }
        });

        beg.setOnTouchListener(this);
        end.setOnTouchListener(this);
    }

    private void getData() {
        Integer acId = activity.getActivityId();
        Integer userId = ((MyApplication)getApplication()).getUser().getUserId();
        String acTitle = title.getText().toString();
        String acContent = content.getText().toString();
        String acAdd = address.getText().toString();
        Timestamp acBeg = Timestamp.valueOf(beg.getText().toString()+":00");
        Timestamp acEnd = Timestamp.valueOf(end.getText().toString()+":00");
        Timestamp create = new Timestamp(System.currentTimeMillis());
        Integer acNum = Integer.parseInt(nums.getText()+"");
        String acAd = address.getText()+"";
//        String Imgurl="upload/"+file.getName();
        String Imgurl=url;
        Log.i(TAG, "要传递的图片url"+Imgurl);
        activity = new Activity(acId,userId,acTitle,acContent,Imgurl,acBeg,create,acEnd,acNum,acAdd);
        Log.i(TAG, "getData: 要传过去的修改的活动信息："+activity);
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
                        Bitmap photo1= extras.getParcelable("data");
                        //保存到本地；
                        saveImageToGallery(ModifyActivity.this, photo1);
                        photo.setImageBitmap(photo1);
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
        url=file.getName();
        System.out.println(url);
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

    //日期插件；
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.date_time_dialog,null);
            final DatePicker datePicker = (DatePicker)view.findViewById(R.id.date_picker);
            final TimePicker timePicker = ((TimePicker)view.findViewById(R.id.time_picker));
            builder.setView(view);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            datePicker.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),null);

            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(cal.get(Calendar.MINUTE));
            timePicker.setCurrentMinute(Calendar.MINUTE);

            if (v.getId() == R.id.ac_et_bg){
                final int inType = beg.getInputType();
                beg.setInputType(InputType.TYPE_NULL);
                beg.onTouchEvent(event);
                beg.setInputType(inType);
                beg.setSelection(beg.getText().length());

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

                        beg.setText(sb);
                        end.requestFocus();

                        dialog.cancel();
                    }
                });
            }else if (v.getId() == R.id.ac_et_ed){
                int inType = end.getInputType();
                end.setInputType(InputType.TYPE_NULL);
                end.onTouchEvent(event);
                end.setInputType(inType);
                end.setSelection(end.getText().length());

                builder.setTitle("选取结束时间");
                builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth()));
                        sb.append(" ");
                        sb.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());

                        end.setText(sb);
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
