package com.example.administrator.taoyuan.fragment;


import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_home.BaoxiuActivity2;
import com.example.administrator.taoyuan.activity_home.BitmapUtils;
import com.example.administrator.taoyuan.activity_home.Netutil;
import com.example.administrator.taoyuan.pojo.Leixing;
import com.example.administrator.taoyuan.pojo.RepairBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class Baoxiuleixing_gonggongquyu_Fragment extends Fragment {
    @InjectView(R.id.et)
    EditText et;
    @InjectView(R.id.btn)
    Button btn;
    @InjectView(R.id.imageView)
    ImageView imageView;
    String str;
    private static final int REQUEST_CODE = 13;
    @InjectView(R.id.btn1)
    Button btn1;
    private ArrayList<String> mResults = new ArrayList<>();
    private Context context;
    private Button shanchu;
    String pictureName = null;
    Bitmap bitmap = null;
    String[] temp = new String[6];
    String content = null;
    List<File> fileList;
    Leixing leixin;
    File file;
    Bundle bundle = new Bundle();
    String time;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baoxiuleixing_gonggongquyu, null);
        ButterKnife.inject(this, view);
        if (!"".equals(getArguments().toString())) {
            str = getArguments().getString("d");
        }
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showpopupwindow(v);
                return false;
            }
        });
        System.out.println(str.toString()+"???????????????");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.btn, R.id.btn1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.btn1:
                Date date=new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                time= sdf.format(date);
                content=et.getText().toString();
                if(content.equals("")&&imageView.getDrawable()==null) {
                Toast toast = Toast.makeText(getActivity(), "报修内容和图片不能同时为空", Toast.LENGTH_LONG);
                toast.show();
                }else {
                    if (imageView.getDrawable() != null && !content.equals("")) {
                        leixin = new Leixing(str, null, pictureName, content);
                        showImage(bitmap);
                        upLoad(file);
                    } else if (imageView.getDrawable() != null && content.equals("")) {
                        leixin = new Leixing(str, null, pictureName, null);
                    } else if (imageView.getDrawable() == null && !content.equals("")) {
                        leixin = new Leixing(str, null, null, content);
                    }
                    baoXiu();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == REQUEST_CODE) {
            if (data != null) {
                mResults = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                assert mResults != null;
                for (int i = 0; i < mResults.size(); i++) {
                    bitmap = BitmapUtils.decodeSampledBitmapFromFd(mResults.get(i), 480, 600);
                    temp = mResults.get(i).replaceAll("\\\\", "/").split("/");
                }
                for (int j = 0; j < temp.length; j++) {
                    if (temp[j].contains(".jpg") || temp[j].contains(".png")) {
                        pictureName = temp[j];
                    }
                }
                imageView.setImageBitmap(bitmap);
                btn.setVisibility(View.GONE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showpopupwindow(View view) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.shanchu, null);
        shanchu = ((Button) contentView.findViewById(R.id.shanchu));
        shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(null);
                pictureName = "";
                btn.setVisibility(View.VISIBLE);
            }
        });
        final PopupWindow popupwindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupwindow.setTouchable(true);

        popupwindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        popupwindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
    public void showImage(Bitmap bitmap){
        saveImage(bitmap);//保存文件
    }
    //将bitmap保存在文件中
    public void saveImage(Bitmap bitmap){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM
            file = new File(Environment.getExternalStorageDirectory(), pictureName);
        }
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,fos);
    }
    public  void upLoad(File file){
        RequestParams params = new RequestParams(Netutil.url + "upLoadPhotoServlet");
        params.setMultipart(true);
        params.addBodyParameter("img", file);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("上传成功" + result.toString());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString() + "????????????????");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    public void baoXiu() {
        String url = Netutil.url+"insertRepair";
        RequestParams params = new RequestParams(url);
        String repairTitle =null;
        String repairType =str;
        String repairContent =leixin.getContent() ;
        String repairImg =leixin.getPictureName();
        String repairAddress = null;
        String repairDate =time;
        String repairName = null;
        String repairTel ="0";
        RepairBean rb = new RepairBean(repairTitle, repairType, repairContent, repairImg, repairAddress, repairDate, repairName, repairTel);
        Gson gson = new Gson();
        String repairgson = gson.toJson(rb);
        params.addBodyParameter("repairList", repairgson);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result.toString() + "?//////////////");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString() + "///////////////");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
