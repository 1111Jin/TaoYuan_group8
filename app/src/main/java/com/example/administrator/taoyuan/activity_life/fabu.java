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
    private Button btn_fabu1;

    private ArrayList<ListLifeInfo.LifeInfo> lifelist = new ArrayList<ListLifeInfo.LifeInfo>();

    //头像的存储完整路径
//    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/"+
//            getPhotoFileName());

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;
    boolean flag=true;
    String path="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_life);
        ButterKnife.inject(this);

        initView();
        initData();
        initEvent();

    }


    private void initView() {

    }

    private void initData() {

    }

    private void initEvent() {
        btnFabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });




    }

//    @OnClick(R.id.tupian_1)
//    public void onClick1() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("请选择");
//        EditText tv = new EditText(fabu.this);
//        builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (which == 0) {
//                    getPicFromCamera();
////                    flag=true;
//                } else {
//                    getPicFromPhoto();
////                    flag=false;
//                }
//            }
//        });
//        builder.show();
//
//    }






    @OnClick(R.id.btn_fabu1)
    public void onClick() {
        //访问服务器
        //添加到网络；
        RequestParams requestParams = new RequestParams(HttpUtils.localhost_jt + "getLifefabuInfo");



        Gson gson = new Gson();
        ListLifeInfo.LifeInfo ln = new ListLifeInfo.LifeInfo();
        ln.content = neirongC.getText().toString();
        ln.userId=HttpUtils.userId;


//        if(flag) {
//            requestParams.addBodyParameter("file", file);
//            System.out.println(file.getName()+"++++++++++++++++++++++++++++++++");
//        }else {
//            requestParams.addBodyParameter("file", new File(path));
//            System.out.println(path+"++++++++++++++++++++++++++++++++");
//        }

        String content = gson.toJson(ln);
//
        requestParams.addBodyParameter("lifefabu", content);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("fabu", "onSuccess: ");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex+"++++++++++++++++++++++++++++++++");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }

//    private static String getPhotoFileName() {
//        Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
//        return sdf.format(date)+"_"+ UUID.randomUUID() + ".png";
//    }
//
//    private void getPicFromCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        System.out.println("getPicFromCamera==========="+file.getAbsolutePath());
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//        startActivityForResult(intent, CAMERA_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case CAMERA_REQUEST:
//                switch (resultCode) {
//                    case -1://-1表示拍照成功  固定
//                        System.out.println("CAMERA_REQUEST"+file.getAbsolutePath());
//                        tupian1.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//                        break;
//                    default:
//                        break;
//                }
//                break;
//            case PHOTO_REQUEST:
////
//                if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
//                    Log.e("TAG->onresult","ActivityResult resultCode error");
//                    return;
//                }
//                Bitmap bm = null;
//                //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
//                ContentResolver resolver = getContentResolver();
//                //此处的用于判断接收的Activity是不是你想要的那个
//                if (requestCode == PHOTO_REQUEST) {
//                    try {
//                        Uri originalUri = data.getData();        //获得图片的uri
//                        bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
//                        //显得到bitmap图片
//                        tupian1.setImageBitmap(bm);
//                        //这里开始的第二部分，获取图片的路径：
//                        String[] proj = {MediaStore.Images.Media.DATA};
//                        //好像是android多媒体数据库的封装接口，具体的看Android文档
//                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
//                        //按我个人理解 这个是获得用户选择的图片的索引值
//                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
//                        cursor.moveToFirst();
//                        //最后根据索引值获取图片路径
//                        path = cursor.getString(column_index);
//                        System.out.println(path);
//
//                    }catch (IOException e) {
//                        Log.e("TAG-->Error",e.toString());
//                    }
//                }
//                break;
//            case PHOTO_CLIP:
//                if (data != null) {
//                    Bundle extras = data.getExtras();
//                    if (extras != null) {
//                        Log.w("test", "data");
//                        Bitmap photo = extras.getParcelable("data");
//                        saveImageToGallery(getApplication(),photo);//保存bitmap到本地
//                        tupian1.setImageBitmap(photo);
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//
//    }
//
//    public void saveImageToGallery(Context context, Bitmap bmp) {
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
//            fos.flush();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), file.getName(), null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
//    }
//
//    private void getPicFromPhoto() {
//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                "image/*");
//        startActivityForResult(intent, PHOTO_REQUEST);
//    }
}
