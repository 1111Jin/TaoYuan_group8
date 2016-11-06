package com.example.administrator.taoyuan.activity_linli;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.Help;
import com.example.administrator.taoyuan.utils.HttpUtils;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PublishHelp extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "发布互帮---";
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
    @InjectView(R.id.release)
    Button release;
    @InjectView(R.id.et_help_integral)
    EditText etHelpIntegral;
    private ImageView iv_photo;

    private File file;
    private Uri imageUri;

    private Button camera;
    private Button choosephoto;

    public static final int SELECT_PIC = 11;
    public static final int TAKE_PHOTO = 12;
    public static final int CROP = 13;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_help);
        ButterKnife.inject(this);
        etHelpTime.setOnTouchListener(this);
        //判断sd卡是否存在，存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            file = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
//            imageUri = Uri.fromFile(file);
        }

        initView();
        initData();

    }

    public void initData() {
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopupwindow(v);
            }
        });

        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer userId = ((MyApplication) getApplication()).getUser().getUserId();
                String helpTitle = etHelpTitle.getText().toString();
                String helpContent = etHelpProfiles.getText().toString();
                String helpImg = "upload/" + file.getName();
                Timestamp helpTime = Timestamp.valueOf((etHelpTime.getText().toString()) + ":00");
                Timestamp ctreateTime = new Timestamp(System.currentTimeMillis());
                Integer needNums = Integer.parseInt(etHelpNumms.getText().toString());
                Integer sendIntegral = Integer.parseInt(etHelpIntegral.getText().toString());
                String helpAddress=etHelpAddress.getText().toString();
                Help help=new Help(userId,helpTitle,helpContent,helpImg,helpTime,ctreateTime,needNums,sendIntegral,helpAddress);


                RequestParams requestParams=new RequestParams(HttpUtils.localhost_su+"addhelp");
                Gson gson = new Gson();
                String helpstr = gson.toJson(help);
                requestParams.addBodyParameter("help",helpstr);
                Log.i(TAG, "onClick: "+requestParams);
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i(TAG, "onSuccess: "+result);
                        Boolean flag = Boolean.valueOf(result);
                        if(flag){
                            Toast.makeText(getApplicationContext(),"发布成功！",Toast.LENGTH_SHORT);
                        }else{
                            Toast.makeText(getApplicationContext(),"发布失败！",Toast.LENGTH_SHORT);
                        }
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
                finish();
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

    public void initView() {
        iv_photo = ((ImageView) findViewById(R.id.iv_help_photo));
    }

    @OnClick(R.id.iv_help_photo)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_help_photo:
                showpopupwindow(view);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //
        switch (requestCode) {
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
//                        ivHelpPhoto.setImageBitmap(bitmap);
                        showImage(bitmap);
                    }
                }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void crop(Uri uri) {
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

    public void showImage(Bitmap bitmap) {
        ivHelpPhoto.setImageBitmap(bitmap);//iv显示图片
        saveImageToGallery(getApplicationContext(),bitmap);//保存文件
        uploadImage();//上传服务器

    }

    //上传照片;
    private void uploadImage() {
        RequestParams params = new RequestParams(HttpUtils.localhost_su + "uploadimg");
        params.setMultipart(true);
        params.addBodyParameter("file", file);
        System.out.println(file.getName());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("图片保存", "onSuccess: 图片传递成功：" + result);
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

            if (v.getId() == R.id.et_help_time){
                final int inType = etHelpTime.getInputType();
                etHelpTime.setInputType(InputType.TYPE_NULL);
                etHelpTime.onTouchEvent(event);
                etHelpTime.setInputType(inType);
                etHelpTime.setSelection(etHelpTime.getText().length());

                builder.setTitle("选取时间");
                builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth()));
                        sb.append(" ");
                        Integer hour=timePicker.getCurrentHour();
                        sb.append((hour<10?"0"+hour:hour+"")).append(":").append(timePicker.getCurrentMinute());
                        //sb.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());

                        etHelpTime.setText(sb);
//                        tvEnd.requestFocus();

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
