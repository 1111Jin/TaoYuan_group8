package com.example.administrator.taoyuan.activity_linli;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.administrator.taoyuan.utils.DateUtil.dateToString1;

/**
 * Created by Administrator on 2016/10/17.
 */
public class ActivityInfo extends AppCompatActivity implements View.OnClickListener {
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
    User user1;
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
    int maxLine = 5;//textView设置最大显示行数；

    List<Comment> comList = new ArrayList<>();
    public TextView name;
    public TextView content;
    private String items[] = {"删除", "取消"};
    public ImageView bot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_item_content);
        ButterKnife.inject(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initData();

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
    public void setListener() {
        say.setOnClickListener(this);
        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
    }


    //用户头像点击事件；
    private void initEvent() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActivityInfo.this, UserInfo.class);
                intent.putExtra("userInfo", (Parcelable) users.get(position));
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final Integer userId = data.get(position).getUserId();
                final Integer comId = data.get(position).getId();
                Log.i(TAG, "onItemLongClick: +++++" + comId);

                if (data.get(position).getUserId().equals(((MyApplication) getApplication()).getUser().getUserId())) {

                    new AlertDialog.Builder(ActivityInfo.this).setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    delete(userId, comId);
                                    data.remove(position);
                                    adapterComment.notifyDataSetChanged();
                                    break;
                                case 1:
                                    dialog.dismiss();
                            }
                        }
                    }).show();
                } else {
                    Toast.makeText(ActivityInfo.this, "无法操作", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }


    //获取数据；
    public void initData() {
        Intent intent = getIntent();
        activity = intent.getParcelableExtra("ActivityInfo");
        data = (List<Comment>) intent.getSerializableExtra("comment");
        Log.i(TAG, "initData: _+_+++" + data);
        Log.i("ActivityInfo", "initData: ------>" + activity);
        //本页面显示的活动信息；
        if (activity != null) {
            tvAcTitle.setText(activity.getActivityTitle());
            tvAcName.setText(activity.getUser().getUserName());
            tvAcAcpro.setText(activity.getActivityContent());//设置文本内容；
            tvAcAddress.setText(activity.getActivityAddress());
            tvAcTime3.setText(dateToString1(activity.getBeginTime()) + " -- " + dateToString1(activity.getEndTime()));
            xUtilsImageUtils.display(ivAcImage, HttpUtils.localhost_su + activity.getActivityImg());
            Log.i(TAG, "initData: QQQQQQQQQQQQQQ" + data);
        }
        /**
         * 判断是否为发布人；
         */
        if (activity.getUser().getUserId().equals(((MyApplication) getApplication()).getUser().getUserId())) {
            //右上角按钮显示；
            pull.setVisibility(View.VISIBLE);
            //参与按钮消失；
            btAcJoin.setVisibility(View.GONE);
            end.setVisibility(View.GONE);

        }

        /**
         * 获取参与活动的用户信息；
         */
        getJoinUserById(activity.getActivityId());
        /**
         * 获得用户信息
         */

//        RequestParams re = new RequestParams(HttpUtils.localhost_su + "queryuser");
//        re.addBodyParameter("userId", String.valueOf(((MyApplication) getApplication()).getUser().getUserId()));
//        System.out.println(re);
//        x.http().post(re, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                System.out.println("__________________________________________________" + result);
//                Gson gson = new Gson();
//                Type type = new TypeToken<User>() {
//                }.getType();
//                user1 = gson.fromJson(result, type);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                System.out.println(ex.toString());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//            }
//        });

        gridview();

        adapterComment = new AdapterComment(getApplicationContext(), data);
        // 为评论列表设置适配器
        list.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();
        adapterComment.notifyDataSetInvalidated();
        setListViewHeightBasedOnChildren(list);
    }

    public void gridview() {
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
                Log.i(TAG, "getView: " + user);
                //如果：参与人的userId与默认的userId相同，就表示我已经参与了这个活动；
                if (user.getUserId().equals(((MyApplication) getApplication()).getUser().getUserId())) {
                    Log.i(TAG, "getView: 打印用户的id为：" + user.getUserId());
                    pull.setVisibility(View.VISIBLE);
                    btAcJoin.setVisibility(View.GONE);
                }
                img = ((ImageView) view.findViewById(R.id.item_gridview));
                xUtilsImageUtils.display(img, HttpUtils.localhost_su + user.getPhoto(), true);
                return view;
            }
        });
    }

    //取消报名、取消  参与人
    public void initPopupWindow(View v) {
        //content
        View view = LayoutInflater.from(this).inflate(R.layout.publish_choose, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //listview设置数据源
        ListView lv = (ListView) view.findViewById(R.id.lv_publish);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.publish_choose_item, new String[]{"取消报名", "取 消"});
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
                if (position == 0) {
                    RequestParams params = new RequestParams(HttpUtils.localhost_su + "deleteacjoin");
                    params.addBodyParameter("acId", String.valueOf(activity.getActivityId()));
                    params.addBodyParameter("userId", String.valueOf(((MyApplication) getApplication()).getUser().getUserId()));
                    x.http().post(params, new Callback.CacheCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i(TAG, "onSuccess: 删除参与用户：" + result);
                            getJoinUserById(activity.getActivityId());
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

//                    btAcJoin.setVisibility(View.VISIBLE);
//                    end.setVisibility(View.GONE);
                } else if (position == 1) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    //修改活动、取消活动、取消,发布人；
    public void showPopupWindow(View v) {
        //content
        View view = LayoutInflater.from(this).inflate(R.layout.publish_choose, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //listview设置数据源
        ListView lv = (ListView) view.findViewById(R.id.lv_publish);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.publish_choose_item, new String[]{"修改活动", "取消活动", "取 消"});
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
                if (position == 0) {
                    Intent intent = new Intent(ActivityInfo.this, ModifyActivity.class);
                    intent.putExtra("acInfo", activity);
                    startActivityForResult(intent, 0);

                } else if (position == 1) {
                    RequestParams params = new RequestParams(HttpUtils.localhost_su + "deleteactivity");
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
                } else if (position == 2) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    @OnClick({R.id.bt_ac_join, R.id.pull, R.id.ac_con_title})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ac_join:
                Intent intent = new Intent(ActivityInfo.this, JoinActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("acId", activity.getActivityId());
                intent.putExtras(bundle);
//                startActivity(intent);
                startActivityForResult(intent, 0);
                Log.i(TAG, "onClick: +++++++>传递到下一个页面的id" + activity.getActivityId());
                break;
            case R.id.pull:
                if (activity.getUser().getUserId().equals(((MyApplication) getApplication()).getUser().getUserId())) {
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

    /**
     * 内部类，自定义的适配器；
     */
    public class AdapterComment extends BaseAdapter {

        Context context;
        List<Comment> data;

        public AdapterComment(Context c, List<Comment> data) {
            this.context = c;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            System.out.println("+" + position);
            ViewHolder holder = null;
            // 重用convertView
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.omment_item, null);
                holder.comment_photo = ((ImageView) convertView.findViewById(R.id.comment_photo));
                holder.comment_name = (TextView) convertView.findViewById(R.id.comment_name);
                holder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
                holder.comment_time = ((TextView) convertView.findViewById(R.id.comment_time));
                Log.i(TAG, "onItemClick: 留言的用户id：-----" + data.get(position).getUserId());
                Log.i(TAG, "onItemClick: 默认的用户id:------" + ((MyApplication) getApplication()).getUser().getUserId());
                convertView.setTag(holder);
            } else
                System.out.println("++++" + data.size());
            Log.i("评论人的Id", "getView: " + data.get(position).getUserId());
            Log.i("评论人的NAme", "getView: " + data.get(position).getUser().getUserName());
            holder = (ViewHolder) convertView.getTag();
            holder.comment_content.setText(data.get(position).getContent());
            holder.comment_time.setText(new SimpleDateFormat("MM-dd HH:mm").format(data.get(position).getCreate()));
            x.image().bind(holder.comment_photo, HttpUtils.localhost_su + data.get(position).getUser().getPhoto());
            holder.comment_name.setText(data.get(position).getUser().getUserName() + ":");


            return convertView;

        }

        /**
         * 添加一条评论,刷新列表
         *
         * @param comment
         */
        public void addComment(Comment comment) {
            data.add(comment);
            System.out.println(".........." + data.size());
            notifyDataSetChanged();
            notifyDataSetInvalidated();
        }

        /**
         * 静态类，便于GC回收
         */
        public class ViewHolder {
            ImageView comment_photo;
            TextView comment_name;
            TextView comment_content;
            TextView comment_time;
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 0:
                System.out.println("回来了");
//                RequestParams requestParams=new RequestParams(HttpUtils.localhost_su+"/queryacbyid");
//                requestParams.addBodyParameter("acId",String.valueOf(activity.getActivityId()));
//                x.http().get(requestParams, new Callback.CommonCallback<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        System.out.println(result);
//                        Gson gson=new Gson();
//                        activity = gson.fromJson(result,Activity.class);
//                        if (activity != null) {
//                            tvAcTitle.setText(activity.getActivityTitle());
//                            tvAcName.setText(activity.getUser().getUserName());
//                            tvAcAcpro.setText(activity.getActivityContent());
//                            tvAcAddress.setText(activity.getActivityAddress());
//                            tvAcNum.setText("需要人数："+activity.getJoinNums()+"");
//                            getJoinUserById(activity.getActivityId());
//                            tvAcTime3.setText(dateToString1(activity.getBeginTime()) + " -- " + dateToString1(activity.getEndTime()));
//                            xUtilsImageUtils.display(ivAcImage, HttpUtils.localhost_su + activity.getActivityImg());
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable ex, boolean isOnCallback) {
//                        System.out.println("错误：："+ex.toString());
//                    }
//
//                    @Override
//                    public void onCancelled(CancelledException cex) {
//
//                    }
//
//                    @Override
//                    public void onFinished() {
//
//                    }
//                });
                getJoinUserById(activity.getActivityId());
                RequestParams re = new RequestParams(HttpUtils.localhost + "tstag");
                String tag = String.valueOf(activity.getActivityId());
                String title = "您参加的活动更新了";
                String content = "有wyMa发布的活动：" + activity.getActivityContent() + "有新的变化！";
                re.addBodyParameter("tag", tag);
                re.addBodyParameter("title", title);
                re.addBodyParameter("content", content);
                x.http().get(re, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("发送通知");

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

    public void getJoinUserById(Integer acId) {
        RequestParams requestParams = new RequestParams(HttpUtils.localhost_su + "queryac");
        requestParams.addBodyParameter("acId", String.valueOf(acId));
        Log.i(TAG, "initData: " + requestParams);
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
                tvAcNum.setText("需要人数：" + activity.getJoinNums() + "");
                tvAcBmNums.setText(num + "人报名");
                Log.i("info1111", "onSuccess: jjjj" + tvAcBmNums.getText());
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUserId().equals(((MyApplication) getApplication()).getUser().getUserId())) {
                        pull.setVisibility(View.VISIBLE);
                        btAcJoin.setVisibility(View.GONE);
                    }
                    if (users.size() == activity.getJoinNums()) {
                        end.setVisibility(View.VISIBLE);
                        pull.setVisibility(View.VISIBLE);
                        btAcJoin.setVisibility(View.GONE);
                    }
                }
//                if (user.getUserId().equals(((MyApplication)getApplication()).getUser().getUserId())|| users.size()==activity.getJoinNums()){
//                    pull.setVisibility(View.VISIBLE);
//                    btAcJoin.setVisibility(View.GONE);
//                }
//                if (users.size() == activity.getJoinNums()) {
//                    end.setVisibility(View.VISIBLE);
//                    pull.setVisibility(View.VISIBLE);
//                    btAcJoin.setVisibility(View.GONE);
//                }
                gridview();
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
    }

    /**
     * 根据留言表里的id删除留言表里面的数据；
     * 前提是这条留言是自己发布的
     *
     * @param userId
     * @param acmId
     */
    private void delete(Integer userId, Integer acmId) {
        RequestParams parms = new RequestParams(HttpUtils.localhost_su + "deletecom");
        parms.addBodyParameter("userId", String.valueOf(userId));
        Log.i(TAG, "delete: chuanguoqude  userId::" + String.valueOf(userId));
        parms.addBodyParameter("id", String.valueOf(acmId));
        Log.i(TAG, "delete: chuanguoqude  acmId::" + String.valueOf(acmId));
        x.http().post(parms, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: 删除comment出错了：" + ex);
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
