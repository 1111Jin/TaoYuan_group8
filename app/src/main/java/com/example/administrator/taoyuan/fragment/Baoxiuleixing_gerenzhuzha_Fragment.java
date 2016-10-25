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
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_home.BaoxiuActivity2;
import com.example.administrator.taoyuan.activity_home.BitmapUtils;
import com.example.administrator.taoyuan.activity_home.Netutil;
import com.example.administrator.taoyuan.pojo.Leixing;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class Baoxiuleixing_gerenzhuzha_Fragment extends Fragment {
    @InjectView(R.id.tv)
    TextView tv;
    @InjectView(R.id.bt1)
    Button bt1;
    @InjectView(R.id.bt2)
    Button bt2;
    @InjectView(R.id.bt3)
    Button bt3;
    @InjectView(R.id.bt4)
    Button bt4;
    @InjectView(R.id.bt5)
    Button bt5;
    @InjectView(R.id.et)
    EditText et;
    @InjectView(R.id.bt6)
    Button bt6;
    String str;
    String str1 = null;
    Bundle bundle = new Bundle();
    @InjectView(R.id.btn)
     Button btn;
    private static final int RESULT_OK = 123;
    private static final int REQUEST_CODE = 13;
    @InjectView(R.id.imageView)
    ImageView imageView;
    private ArrayList<String> mResults = new ArrayList<>();
    private Context context;
    private Button shanchu;
    String pictureName = null;
    Bitmap bitmap = null;
    String[] temp = new String[6];
    String content=null;
    List<File> fileList;
    Leixing leixin;
    File file;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baoxiuleixing_gerenzhuzha_, null);
        ButterKnife.inject(this, view);
        if (!"".equals(getArguments().toString())) {
            str = getArguments().getString("c");
        }
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showpopupwindow(v);
                return false;
            }
        });

            return view;
    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6, R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                bt1.setEnabled(false);
                bt2.setEnabled(true);
                bt3.setEnabled(true);
                bt4.setEnabled(true);
                bt5.setEnabled(true);
                str1 = bt1.getText().toString();
                break;
            case R.id.bt2:
                bt1.setEnabled(true);
                bt2.setEnabled(false);
                bt3.setEnabled(true);
                bt4.setEnabled(true);
                bt5.setEnabled(true);
                str1 = bt2.getText().toString();
                break;
            case R.id.bt3:
                bt1.setEnabled(true);
                bt2.setEnabled(true);
                bt3.setEnabled(false);
                bt4.setEnabled(true);
                bt5.setEnabled(true);
                str1 = bt3.getText().toString();
                break;
            case R.id.bt4:
                bt1.setEnabled(true);
                bt2.setEnabled(true);
                bt3.setEnabled(true);
                bt4.setEnabled(false);
                bt5.setEnabled(true);
                str1 = bt4.getText().toString();
                break;
            case R.id.bt5:
                bt1.setEnabled(true);
                bt2.setEnabled(true);
                bt3.setEnabled(true);
                bt4.setEnabled(true);
                bt5.setEnabled(false);
                str1 = bt5.getText().toString();
                break;
            case R.id.btn:
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.bt6:
                content=et.getText().toString();
                if (str.equals("") || str1==null) {
                    Toast toast = Toast.makeText(getActivity(), "报修类型或报修项目不能为空", Toast.LENGTH_LONG);
                    toast.show();
                } else if(content.equals("")&&imageView.getDrawable()==null) {
                    Toast toast = Toast.makeText(getActivity(), "报修内容和图片不能同时为空", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    if (imageView.getDrawable()!=null&&!content.equals("")) {
                         leixin =new Leixing(str,str1,pictureName,content);
                        showImage(bitmap);
                        upLoad(file);
                    }else if (imageView.getDrawable()!=null&&content.equals("")){
                         leixin=new Leixing(str,str1,pictureName,null);
                    }else if (imageView.getDrawable()==null&&!content.equals("")){
                        leixin=new Leixing(str,str1,null,content);
                    }
                        Intent intent2 = new Intent(getActivity(), BaoxiuActivity2.class);
                        bundle.putSerializable("e", leixin);
                        intent2.putExtras(bundle);
                        startActivity(intent2);

                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == REQUEST_CODE) {
                mResults = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                assert mResults != null;
                for (int i = 0; i < mResults.size(); i++) {
                    bitmap =BitmapUtils.decodeSampledBitmapFromFd (mResults.get(i),480,600);
                    temp = mResults.get(i).replaceAll("\\\\","/").split("/");
                }
            for (int j=0;j<temp.length;j++){
                if (temp[j].contains(".jpg")||temp[j].contains(".png")){
                    pictureName=temp[j];
                }
            }
                imageView.setImageBitmap(bitmap);
            btn.setVisibility(View.GONE);
            }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showpopupwindow(View view) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.shanchu,null);
        shanchu = ((Button) contentView.findViewById(R.id.shanchu));
         shanchu.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 imageView.setImageBitmap(null);
                  pictureName="";
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
        popupwindow.showAtLocation(view, Gravity.BOTTOM,0,0);
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
}
