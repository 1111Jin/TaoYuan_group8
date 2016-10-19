package com.example.administrator.taoyuan.activity_linli;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PublishActivity extends AppCompatActivity {

    private String items[]={"拍照","从相册选择"};

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    //头像的存储完整路径；
    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+
            "/"+getPhotoFileName());
    private ImageView addPic;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        initView();
        initEvent();
    }

    private void initView() {
        addPic = ((ImageView) findViewById(R.id.iv_photo));
    }


    private void initEvent() {
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PublishActivity.this).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //把拍照的相机调出来；
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                //以下这句指定调用相机拍照后的照片存储的路径；
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                startActivityForResult(intent,CAMERA_REQUEST);
                                break;
                            case 1:
                                Intent intt = new Intent(Intent.ACTION_PICK,null);
                                intt.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                                startActivityForResult(intt,PHOTO_REQUEST);
                        }
                    }
                }).show();
            }
        });
    }

    //使用系统当前时间加以调整作为照片的名称；
    private String getPhotoFileName(){
        Date date = new Date(System.currentTimeMillis());//当前时间；
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");//格式化；
        return sdf.format(date)+"_"+ UUID.randomUUID()+".png";//返回转换格式后的图片名；UUID随机生成的，唯一的
    }

    //关闭某个Activity时，会自动调用此方法；
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAMERA_REQUEST:
                switch (resultCode){
                    case -1://-1表示拍照成功；
                        System.out.println("CAMERA_REQUEST"+file.getAbsolutePath());
                        if (file.exists()){
                            photoClip(Uri.fromFile(file));
                        }
                    default:
                        break;
                }
                break;
            case PHOTO_CLIP:
                if (data!=null){
                    //activity之间互相传数据；
                    Bundle extras = data.getExtras();
                    if (extras!=null){
                        Bitmap photo = extras.getParcelable("data");
                        //保存到本地；
                        saveImageToGallery(PublishActivity.this,photo);
                        addPic.setImageBitmap(photo);
                    }
                }
                break;
            case PHOTO_REQUEST:
                if (data!=null){
                    photoClip(data.getData());
                }
                break;
        }
    }
    private void saveImageToGallery(Context context, Bitmap photo) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //把图片进行压缩，压缩成JPEG、PNG格式；
            photo.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //把文件插入到系统图库；
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),file.getAbsolutePath(),file.getName(),null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //通知手机更新图库；
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.parse("file://"+file.getAbsolutePath())));
    }
    //图片裁剪；
    private void photoClip(Uri uri) {
        //调用系统中自带的图片修剪；
        Intent intent = new Intent("com.android.camera.action.CROP");
        //图片路径及类型；
        intent.setDataAndType(uri,"image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可剪裁；
        intent.putExtra("crop","true");
        //aspectX aspectY是宽高的比例；
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //outputX outputY 是裁剪图片的宽高；
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,PHOTO_CLIP);
    }
}
