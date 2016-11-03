package com.example.administrator.taoyuan.activity_linli;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.pojo.Activity;
import com.example.administrator.taoyuan.pojo.Comment;
import com.example.administrator.taoyuan.pojo.User;
import com.example.administrator.taoyuan.utils.AdapterComment;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.TitleBar;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.administrator.taoyuan.utils.DateUtil.dateToString1;

/**
 * Created by Administrator on 2016/10/17.
 */
public class ActivityInfo extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ActivityInfo";
    private Integer num = -1;
    @InjectView(R.id.iv_ac_image)
    ImageView ivAcImage;
    @InjectView(R.id.tv_ac_num)
    TextView tvAcNum;
    @InjectView(R.id.rl_ac_content)
    RelativeLayout rlAcContent;
    @InjectView(R.id.tv_ac_address)
    TextView tvAcAddress;
    @InjectView(R.id.tv_ac_time3)
    TextView tvAcTime3;
    @InjectView(R.id.tv_ac_activity)
    TextView tvAcActivity;
    @InjectView(R.id.tv_ac_acpro)
    TextView tvAcAcpro;
    @InjectView(R.id.ll_ac_l)
    LinearLayout llAcL;
    @InjectView(R.id.tv_ac_master)
    TextView tvAcMaster;
    @InjectView(R.id.bt_ac_join)
    Button btAcJoin;
    @InjectView(R.id.tv_ac_title)
    TextView tvAcTitle;
    @InjectView(R.id.tv_ac_name)
    TextView tvAcName;
    @InjectView(R.id.pull)
    TextView pull;
    @InjectView(R.id.ac_con_title)
    TitleBar acConTitle;
    @InjectView(R.id.v_bgline)
    View vBgline;
    @InjectView(R.id.rl_ac_master)
    RelativeLayout rlAcMaster;
    @InjectView(R.id.ac_bm_nums)
    TextView acBmNums;
    @InjectView(R.id.tv_ac_bm_nums)
    TextView tvAcBmNums;
    @InjectView(R.id.v_line_item)
    View vLineItem;
    @InjectView(R.id.gridview)
    GridView gridview;
    @InjectView(R.id.rl_ac_rl)
    RelativeLayout rlAcRl;

    private ListView lv_choose;
    Activity activity;
    List<Comment> co;
    List<User> users = new ArrayList<>();
    public Button end;
    User user;
    BaseAdapter adapter;
    public TextView say;
    public ListView list;
    List<Comment> data;
    TextView hide_down;
    EditText comment_content;
    Button comment_send;
    LinearLayout rl_enroll;
    RelativeLayout rl_comment;
    private AdapterComment adapterComment;
    int pageNo = 1;
    int pageSize = 3;

    List<Comment> comList = new ArrayList<>();
    public TextView name;
    public TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_item_content);
        ButterKnife.inject(this);
        initView();
        initData();
        initEvent();
//        getData();
        //如果：活动里面的userId等于默认的userId，就表示这个活动是我自己发布的；
        if (activity.getUser().getUserId()==((MyApplication)getApplication()).getUser().getUserId()){
            //右上角按钮显示；
            pull.setVisibility(View.VISIBLE);
            //参与按钮消失；
            btAcJoin.setVisibility(View.GONE);

        }
    }



    private void initView() {
        say = ((TextView) findViewById(R.id.tv_say));
        gridview = ((GridView) findViewById(R.id.gridview));
        list = ((ListView) findViewById(R.id.comment_list));
        end = ((Button) findViewById(R.id.bt_ac_end));
        // 初始化数据
        data = new ArrayList<>();
        //设置输入框；
        hide_down = (TextView) findViewById(R.id.hide_down);
        comment_content = (EditText) findViewById(R.id.comment_content1);
        comment_send = (Button) findViewById(R.id.comment_send);
        rl_enroll = (LinearLayout) findViewById(R.id.ll_ac_say);
        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
        //完成后将内容放入list中
        setListener();
    }

    //设置监听事件；
    public void setListener(){
        say.setOnClickListener(this);
        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }
    //用户头像点击事件；
    private void initEvent() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActivityInfo.this, UserInfo.class);
                intent.putExtra("userInfo",(Parcelable) user);
                startActivity(intent);
            }
        } );
    }

//    private void getData(){
//        RequestParams params = new RequestParams(HttpUtils.localhost_su+"querycommnet");
//        params.addBodyParameter("acId", String.valueOf(activity.getActivityId()));
//        params.addBodyParameter("userId", String.valueOf(((MyApplication)getApplication()).getUser().getUserId()));
//        params.addBodyParameter("pageNo",pageNo+"");
//        params.addBodyParameter("pageSize",pageSize+"");
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                List<Comment> newComList = new ArrayList<Comment>();
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<Comment>>(){}.getType();
//                newComList = gson.fromJson(result,type);
//                comList.clear();
//                comList.addAll(newComList);
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//
//    }

    //获取数据；
    public void initData() {
        Intent intent = getIntent();
        //获取活动信息；
        activity = intent.getParcelableExtra("ActivityInfo");
        //获取评论信息；
        data=(List<Comment>) intent.getSerializableExtra("comment");
        //将活动id传递给服务端，通过服务端的方法获得相应的用户信息；
        RequestParams requestParams = new RequestParams(HttpUtils.localhost_su + "queryac");
        Integer acId = activity.getActivityId();
        Log.i(TAG, "onItemClick: --------->传递id：" + acId);
        requestParams.addBodyParameter("acId", String.valueOf(acId));
        Log.i(TAG, "initData: "+requestParams);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: result是=====>>：" + result);
                Gson gson = new Gson();
                Type type = new TypeToken<List<User>>() {
                }.getType();
                //获取传递过来的参与人员的信息；
                users = gson.fromJson(result, type);
                //参与的人数；
                num = users.size();
                tvAcNum.setText("需要人数："+activity.getJoinNums()+"");
                tvAcBmNums.setText(num+"人报名");
                Log.i("info1111", "onSuccess: jjjj"+tvAcBmNums.getText());
                if (user.getUserId()==((MyApplication)getApplication()).getUser().getUserId()|| users.size()==activity.getJoinNums()){
//                    end.setVisibility(View.VISIBLE);
                    pull.setVisibility(View.VISIBLE);
                    btAcJoin.setVisibility(View.GONE);
                }
                Log.i(TAG, "onSuccess: 根据活动id返还回来的参与人的信息：" + users);
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

        Log.i("ActivityInfo", "initData: ------>" + activity);
        //本页面显示的活动信息；
        if (activity != null) {
            tvAcTitle.setText(activity.getActivityTitle());
            tvAcName.setText(activity.getUser().getUserName());
            tvAcAcpro.setText(activity.getActivityContent());
            tvAcAddress.setText(activity.getActivityAddress());
            tvAcTime3.setText(dateToString1(activity.getBeginTime()) + " -- " + dateToString1(activity.getEndTime()));
            xUtilsImageUtils.display(ivAcImage, HttpUtils.localhost_su + activity.getActivityImg());
            Log.i(TAG, "initData: QQQQQQQQQQQQQQ" + data);
        }
        //评论信息；
        gridview.setAdapter(new BaseAdapter() {
            public ImageView img;

            @Override
            public int getCount() {
                return users.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = View.inflate(ActivityInfo.this, R.layout.img_view, null);
                user = users.get(position);
                Log.i(TAG, "getView: "+user);
                //如果：参与人的userId与默认的userId相同，就表示我已经参与了这个活动；
                if (user.getUserId()==((MyApplication)getApplication()).getUser().getUserId()){
                    Log.i(TAG, "getView: 打印用户的id为："+user.getUserId());
                    pull.setVisibility(View.VISIBLE);
                    btAcJoin.setVisibility(View.GONE);
                }
                img = ((ImageView) view.findViewById(R.id.item_gridview));
                xUtilsImageUtils.display(img, HttpUtils.localhost_su + user.getPhoto(), true);
                return view;
            }
        });
        adapterComment = new AdapterComment(getApplicationContext(),data);
        // 为评论列表设置适配器
        list.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(list);
    }


    //取消报名、取消  参与人
    public void initPopupWindow(View v){
        //content
        View view=LayoutInflater.from(this).inflate(R.layout.publish_choose,null);
        final PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        //listview设置数据源
        ListView lv= (ListView) view.findViewById(R.id.lv_publish);

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.publish_choose_item,new String[]{"取消报名","取 消"});
        lv.setAdapter(arrayAdapter);

        //popupwiondow外面点击，popupwindow消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //listview的item点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关闭popupwidow
                popupWindow.dismiss();
                //排序
                if(position==0){
                    RequestParams params = new RequestParams(HttpUtils.localhost_su+"deleteacjoin");
                    params.addBodyParameter("acId", String.valueOf(activity.getActivityId()));
                    params.addBodyParameter("userId", String.valueOf(((MyApplication)getApplication()).getUser().getUserId()));
                    x.http().post(params, new Callback.CacheCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i(TAG, "onSuccess: 删除参与用户："+result);
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Log.i(TAG, "onError: 删除异常");
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }

                        @Override
                        public boolean onCache(String result) {
                            return false;
                        }
                    });
                    btAcJoin.setVisibility(View.VISIBLE);
                    end.setVisibility(View.GONE);
                }else if(position==1){
                    popupWindow.dismiss();
                }
            }
        });
    }
    //修改活动、取消活动、取消,发布人；
    public void showPopupWindow(View v){
        //content
        View view=LayoutInflater.from(this).inflate(R.layout.publish_choose,null);
        final PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        //listview设置数据源
        ListView lv= (ListView) view.findViewById(R.id.lv_publish);

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.publish_choose_item,new String[]{"修改活动","取消活动","取 消"});
        lv.setAdapter(arrayAdapter);

        //popupwiondow外面点击，popupwindow消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //显示在v的下面
//        popupWindow.showAsDropDown(v);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //listview的item点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关闭popupwidow
                popupWindow.dismiss();
                //排序
                if(position==0){
                    Intent intent = new Intent(ActivityInfo.this,ModifyActivity.class);
                    intent.putExtra("acInfo",activity);
                    startActivityForResult(intent,0);

                }else if(position==1){
                    RequestParams params = new RequestParams(HttpUtils.localhost_su+"deleteactivity");
                    params.addBodyParameter("acId", String.valueOf(activity.getActivityId()));
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {

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
                }else if(position==2){
                    popupWindow.dismiss();
                }
            }
        });
    }

    @OnClick({R.id.bt_ac_join,R.id.pull,R.id.ac_con_title})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ac_join:
                Intent intent = new Intent(ActivityInfo.this, JoinActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("acId", activity.getActivityId());
                intent.putExtras(bundle);
                startActivity(intent);
                Log.i(TAG, "onClick: +++++++>传递到下一个页面的id" + activity.getActivityId());
                break;
            case R.id.pull:
                if (activity.getUser().getUserId() == ((MyApplication) getApplication()).getUser().getUserId()) {
                    //判断：如果活动里面的用户id跟默认的用户地id一样，就表示这个活动是我自己发布的，我就可以让报名的按钮隐藏，
                    pull.setVisibility(View.VISIBLE);
                    btAcJoin.setVisibility(View.GONE);
                    showPopupWindow(v);
                } else {
                    pull.setVisibility(View.VISIBLE);
                    initPopupWindow(v);
                }
                break;
            case R.id.ac_con_title:
                finish();
                break;
            case R.id.tv_say:
                // 弹出输入法
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                // 显示评论框
                rl_enroll.setVisibility(View.GONE);
                rl_comment.setVisibility(View.VISIBLE);
                break;
            case R.id.hide_down:
                // 隐藏评论框
                rl_enroll.setVisibility(View.VISIBLE);
                rl_comment.setVisibility(View.GONE);
                // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                InputMethodManager im = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                break;
            case R.id.comment_send:
                sendComment();
                initData();
                break;
            default:
                break;
        }

    }

    //发送评论；
    public void sendComment() {
        if (comment_content.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            // 生成评论数据
            Comment comment = new Comment();
//            comment.setName(activity.getUser().getUserName()+"：");
//            comment.setContent(activity.getUser().getUserName()+":");
//            User user = new User(5);
            comment.setUser(user);
            comment.setContent(comment_content.getText().toString());
            comment.setCreate(new Timestamp(System.currentTimeMillis()));
            System.out.println(comment);
            adapterComment.addComment(comment);
            // 发送完，清空输入框
            comment_content.setText("");

            Toast.makeText(getApplicationContext(), "评论成功！", Toast.LENGTH_SHORT).show();
            RequestParams params = new RequestParams(HttpUtils.localhost_su + "insertcomment");
            params.addBodyParameter("acId", String.valueOf(activity.getActivityId()));
            params.addBodyParameter("userId", String.valueOf(((MyApplication) getApplication()).getUser().getUserId()));
            params.addBodyParameter("time", String.valueOf(new Timestamp(System.currentTimeMillis())));
            params.addBodyParameter("content", comment.getContent());
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {


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
    }

    //解决ListView中只能显示一条数据的方法；
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case 0:
                System.out.println("回来了");
                RequestParams requestParams=new RequestParams(HttpUtils.localhost_su+"/queryacbyid");
                requestParams.addBodyParameter("acId",String.valueOf(activity.getActivityId()));
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println(result);
                        Gson gson=new Gson();
                        activity = gson.fromJson(result,Activity.class);
                        if (activity != null) {
                            tvAcTitle.setText(activity.getActivityTitle());
                            tvAcName.setText(activity.getUser().getUserName());
                            tvAcAcpro.setText(activity.getActivityContent());
                            tvAcAddress.setText(activity.getActivityAddress());
                            tvAcTime3.setText(dateToString1(activity.getBeginTime()) + " -- " + dateToString1(activity.getEndTime()));
                            xUtilsImageUtils.display(ivAcImage, HttpUtils.localhost_su + activity.getActivityImg());

                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("错误：："+ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                RequestParams re=new RequestParams(HttpUtils.localhost+"tstag");
                String tag=String.valueOf(activity.getActivityId());
                String title = "您参加的活动更新了";
                String content = "有wyMa发布的活动："+activity.getActivityContent()+"有新的变化！";
                re.addBodyParameter("tag",tag);
                re.addBodyParameter("content",content);
                x.http().get(re, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {


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
    }
}
