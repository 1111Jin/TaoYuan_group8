package com.example.administrator.taoyuan.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.LifeInfo;
import com.example.administrator.taoyuan.pojo.ListInfo;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.example.administrator.taoyuan.pojo.Remark;
import com.example.administrator.taoyuan.pojo.user_jt;
import com.example.administrator.taoyuan.utils.CommentAdapter_pinglun;
import com.example.administrator.taoyuan.utils.CommonAdapter;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.NoTouchLinearLayout;
import com.example.administrator.taoyuan.utils.RefreshListView;
import com.example.administrator.taoyuan.utils.ViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LifeAll extends Life implements RefreshListView.OnRefreshUploadChangeListener{

    private RefreshListView lv_lifeinfo;
    //CommonAdapter<ListLifeInfo.LifeInfo> adapter;
    private CommentAdapter_pinglun adapter;
     ArrayList<ListLifeInfo.LifeInfo> lifelist= new ArrayList<ListLifeInfo.LifeInfo>();


    private  ListView lv_dongtai1;
    ListInfo listinfo;
    public static final Integer REQUSETCODE=1;
    private Button tv_fabu;
    int pageNo=1;
    int pageSize=7;
    boolean flag11 = false;
    user_jt.friend_agree user;

    ViewHolder viewHolder;
    private ImageButton ib_share;
    private EditText mCommentEdittext;//评论输入框



    private boolean isReply = false;
    private int commentPosition = -1;
    private int replayPosition = -1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor1;

    private Remark r1;
    private Remark r2;


    private Button mSendBut;//评论按钮
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 10:
                    Toast.makeText(getActivity(),msg.obj+"评论信息",Toast.LENGTH_LONG).show();
                    isReply = false;//评论信息
                    editVgLyt.setVisibility(View.VISIBLE);
                    onFocusChange(true);
                    commentPosition= (int)msg.obj;
                    break;
                case 11:
                    Toast.makeText(getActivity(),msg.obj+"回复信息",Toast.LENGTH_LONG).show();
                    isReply = true;//回复信息
                    editVgLyt.setVisibility(View.VISIBLE);
                    onFocusChange(true);

                    String[] attrs = ((String)msg.obj).split(" ");// dongtai   huifu  0 2
                    commentPosition = Integer.parseInt(attrs[0]);
                    replayPosition = Integer.parseInt(attrs[1]);
                    break;

            }
        }
    };
    private NoTouchLinearLayout editVgLyt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_life_list,null);
        lv_lifeinfo = ((RefreshListView) view.findViewById(R.id.lv_dongtai_life));
        sharedPreferences = getActivity().getSharedPreferences("dianzan_sp", Context.MODE_PRIVATE);
        editor1 = sharedPreferences.edit();
        ib_share = ((ImageButton) view.findViewById(R.id.share));


        editVgLyt = ((NoTouchLinearLayout) view.findViewById(R.id.edit_vg_lyt));
        mSendBut = (Button) view.findViewById(R.id.but_comment_send);
        ClickListener cl = new ClickListener();
        mSendBut.setOnClickListener(cl);
        //处理返回键
        editVgLyt.setOnResizeListener(new NoTouchLinearLayout.OnResizeListener() {
            @Override
            public void OnResize() {
                editVgLyt.setVisibility(View.GONE);//输入框消息
                onFocusChange(false);//软键盘消息
            }
        });
        mCommentEdittext = (EditText) view.findViewById(R.id.edit_comment);


        System.out.println("createView");
        initEvent();
        initData();
        return view;


    }

    private void initData() {
        getLifeInfoList();
    }

    public void initEvent() {
//        lv_lifeinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //跳转
//                Log.i("点击事件", "onItemClick: " + position);
//
//                Intent intent = new Intent(getActivity(), LifeXiangqing.class);
//
//                //点击item的信息
//                intent.putExtra("lifeinfo", lifelist.get(position - 1));
//
//                startActivityForResult(intent, 1);
//
//            }
//        });
        lv_lifeinfo.setOnRefreshUploadChangeListener(this);
    }



    //  http://localhost:8080/Life/getdongraibypage?pageNo=1&pageSize=1
    private void getLifeInfoList() {
        System.out.println("21312222221312313123");
        RequestParams params = new RequestParams(HttpUtils.localhost_jt+"getdongraibypage");

        params.addQueryStringParameter("pageNo",pageNo+"");
        params.addQueryStringParameter("pageSize",pageSize+"");
        System.out.println(params);
//        params.addQueryStringParameter("dongtaiId",1+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("1"+result);
                Gson gson=new Gson();
                ListLifeInfo bean = gson.fromJson(result, ListLifeInfo.class);
//                lifelist.addAll(bean);

                lifelist.addAll(bean.lifeinfolist);
                System.out.println("pppppppp====="+lifelist);
                System.out.println("pppppppp=====qqqqqqqq"+lifelist.get(0).getRemarks());
                Integer id=((MyApplication)getActivity().getApplication()).getUser().getUserId();
                if (adapter == null) {
                    adapter = new CommentAdapter_pinglun(getActivity(),handler,lifelist,id) {
                    };
                    lv_lifeinfo.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
            }
        }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "无法获取网络数据，请检查网络连接", Toast.LENGTH_SHORT).show();
                System.out.println(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


    @Override
    public void onRefresh() {
        pageNo = 1; //每次刷新，让pageNo变成初始值1
        //1秒后发一个消息
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flag11 = true;
                getLifeInfoList();  //再次获取数据
                lv_lifeinfo.completeRefresh();  //刷新数据后，改变页面为初始页面：隐藏头部
                Toast.makeText(getActivity(), "数据已更新", Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }

    @Override
    public void onPull() {
        pageNo++;
        //原来数据基础上增加
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flag11 = false;
                getLifeInfoList();//没搞懂
            }
        }, 1000);
    }

    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        mCommentEdittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (isFocus) {
                    //显示输入法
                    mCommentEdittext.requestFocus();//获取焦点
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(mCommentEdittext.getWindowToken(), 0);
                    editVgLyt.setVisibility(View.GONE);
                }
            }
        }, 100);
    }
    /**
     * 点击屏幕其他地方收起输入法
     */
    public boolean  dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getActivity().getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                onFocusChange(false);

            }
            return super.getActivity().dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getActivity().getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return getActivity().onTouchEvent(ev);
    }
    /**
     * 隐藏或者显示输入框
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            /**
             *这堆数值是算我的下边输入区域的布局的，
             * 规避点击输入区域也会隐藏输入区域
             */

            v.getLocationInWindow(leftTop);
            int left = leftTop[0] - 50;
            int top = leftTop[1] - 50;
            int bottom = top + v.getHeight() + 300;
            int right = left + v.getWidth() + 120;
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 事件点击监听器
     */
    private final class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.but_comment_send:
                    if (isReply) {
                        replyComment();
                    } else {
                        publishComment();
                    }
                    editVgLyt.setVisibility(View.GONE);
                    onFocusChange(false);
                    break;
            }
        }
    }

    public void replyComment(){
        user_jt.friend_agree fatheruser= lifelist.get(commentPosition).getRemarks().get(replayPosition-1).getUser();
        user_jt.friend_agree user=new user_jt.friend_agree(5,"小强");
        String remarkContent= mCommentEdittext.getText().toString().trim();
        long remarkTime = System.currentTimeMillis();
        Date timestamp=new Date(remarkTime);
        r2= new Remark(user,remarkContent,fatheruser);
        lifelist.get(commentPosition).getRemarks().add(replayPosition,r2);
        adapter.notifyDataSetChanged();

        RequestParams params = new RequestParams(HttpUtils.localhost_jt+ "ReplayRemarkServlet");

        params.addBodyParameter("dynamicId",lifelist.get(commentPosition).dontaiId+"");
        params.addBodyParameter("userId",((MyApplication)getActivity().getApplication()).getUser().getUserId()+"");

        params.addBodyParameter("fatherUserId",fatheruser.getUserId()+"");
        try {
            params.addBodyParameter("remarkContent", URLEncoder.encode(remarkContent,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "评论失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void publishComment(){
        if (replayPosition+1!=0) {
            user_jt.friend_agree fatheruser = lifelist.get(commentPosition + 1).getRemarks().get(replayPosition + 1).getUser();
        }
        user_jt.friend_agree user=new user_jt.friend_agree(5,"小强");
        String remarkContent= mCommentEdittext.getText().toString().trim();

        r1= new Remark(user,remarkContent);
        lifelist.get(commentPosition).getRemarks ().add(r1);
        adapter.notifyDataSetChanged();
        //插入到数据库

        String remarkContent1= mCommentEdittext.getText().toString().trim();
        RequestParams params = new RequestParams(HttpUtils.localhost_jt+ "CommenRemarkServlet");
        params.addBodyParameter("dynamicId",lifelist.get(commentPosition).dontaiId+"");
        params.addBodyParameter("userId",((MyApplication)getActivity().getApplication()).getUser().getUserId()+"");

        try {
            params.addBodyParameter("remarkContent", URLEncoder.encode(remarkContent1,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "评论失败", Toast.LENGTH_SHORT).show();
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










