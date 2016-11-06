package com.example.administrator.taoyuan.activity_linli;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.administrator.taoyuan.pojo.Help;
import com.example.administrator.taoyuan.pojo.User;
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

/**
 * Created by Administrator on 2016/10/24.
 */
public class HelpInfo extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HelpInfo";
    @InjectView(R.id.hl_content_title)
    TitleBar hlContentTitle;
    @InjectView(R.id.iv_tou)
    ImageView ivTou;
    @InjectView(R.id.tv_username)
    TextView tvUsername;
    @InjectView(R.id.tv_time2)
    TextView tvTime2;
    @InjectView(R.id.rl_help_content_rl)
    RelativeLayout rlHelpContentRl;
    @InjectView(R.id.v_line)
    View vLine;
    @InjectView(R.id.iv_help_p)
    ImageView ivHelpP;
    @InjectView(R.id.tv_help_content_content)
    TextView tvHelpContentContent;
    @InjectView(R.id.tv_hl_content)
    TextView tvHlContent;
    @InjectView(R.id.rl_hl_0)
    RelativeLayout rlHl0;
    @InjectView(R.id.tv_help_content_time)
    TextView tvHelpContentTime;
    @InjectView(R.id.tv_hl_time)
    TextView tvHlTime;
    @InjectView(R.id.rl_hl_1)
    RelativeLayout rlHl1;
    @InjectView(R.id.tv_help_content_address)
    TextView tvHelpContentAddress;
    @InjectView(R.id.tv_hl_address)
    TextView tvHlAddress;
    @InjectView(R.id.rl_hl_2)
    RelativeLayout rlHl2;
    @InjectView(R.id.tv_help_content_num)
    TextView tvHelpContentNum;
    @InjectView(R.id.tv_hl_num)
    TextView tvHlNum;
    @InjectView(R.id.rl_hl_3)
    RelativeLayout rlHl3;
    @InjectView(R.id.tv_help_content_integral)
    TextView tvHelpContentIntegral;
    @InjectView(R.id.tv_hl_integral)
    TextView tvHlIntegral;
    @InjectView(R.id.bt_help)
    Button btHelp;
    Activity activity;

    Help help = new Help();
    List<User> userList = new ArrayList<User>();

    @InjectView(R.id.integral)
    TextView integral;
    @InjectView(R.id.tv_help_nums)
    TextView tvHelpNums;
    @InjectView(R.id.gridview)
    GridView gridview;
    @InjectView(R.id.rl_ac_rl)
    RelativeLayout rlAcRl;
    @InjectView(R.id.tv_help_say)
    TextView tvSay;
    @InjectView(R.id.ll_ac_say)
    LinearLayout llAcSay;
    @InjectView(R.id.v_left)
    View vLeft;
    @InjectView(R.id.pull)
    TextView pull;
    @InjectView(R.id.rl_liuyanban)
    RelativeLayout rlLiuyanban;
    @InjectView(R.id.comment_list)
    ListView commentList;
    @InjectView(R.id.hide_down)
    TextView hideDown;
    @InjectView(R.id.comment_content1)
    EditText commentContent1;
    @InjectView(R.id.comment_send)
    Button commentSend;
    @InjectView(R.id.rl_comment)
    RelativeLayout rlComment;
    private TextView tv_help_nums;

    User user;
    User user1;


    public TextView say;
    public ListView list;
    public Button end;
    private List<Comment> data;
    TextView hide_down;
    EditText comment_content;
    Button comment_send;
    LinearLayout rl_enroll;
    RelativeLayout rl_comment;
    private AdapterComment adapterComment;
    private String items[] = {"删除", "取消"};
    Boolean flag =false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_list_view_item_content);
        ButterKnife.inject(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("jinlaile ");
        initView();

        initData();

        initEvent();
    }

    private void initView() {
        say = ((TextView) findViewById(R.id.tv_help_say));
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

    private void initData() {


        /**
         * 获取传过来的互帮、评论信息，赋值
         */
        Intent intent = getIntent();
        help = intent.getParcelableExtra("HelpInfo");
        data=(List<Comment>) intent.getSerializableExtra("comment");
        Log.i("HelpInfo", "initData: ------>" + data);
        if (help != null) {
            xUtilsImageUtils.display(ivTou, HttpUtils.localhost_su + help.getUser().getPhoto(), true);
            tvHlContent.setText(help.getHelpContent());
            tvHlTime.setText(help.getCreateTime() + "");
            tvHlAddress.setText(help.getHelpAddress());
            tvHlNum.setText(help.getNeedNums().toString());
            tvHlIntegral.setText(help.getSendIntegral().toString());
            integral.setText(help.getUser().getIntegral().toString());
            xUtilsImageUtils.display(ivHelpP, HttpUtils.localhost_su + help.getHelpImg(), false);
        }
        /**
         * 判断是否是发布人
         */

        if (help.getUser().getUserId().equals(((MyApplication) getApplication()).getUser().getUserId())) {
            btHelp.setVisibility(View.GONE);
            pull.setVisibility(View.VISIBLE);
            flag=true;
        }

        /**
         * 获取参与人，判断是否参与
         */
        RequestParams requestParams = new RequestParams(HttpUtils.localhost_su + "getuserbyhelpid");
        requestParams.addBodyParameter("helpId", String.valueOf(help.getHelpId()));
        Log.i(TAG, "initData: "+requestParams);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson = new Gson();
                Type type = new TypeToken<List<User>>() {
                }.getType();
                userList = gson.fromJson(result, type);
                tvHelpNums.setText(String.valueOf(userList.size()+"人报名"));
                Boolean flag = false;
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).getUserId().equals(((MyApplication) getApplication()).getUser().getUserId())) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    btHelp.setBackgroundResource(R.color.text_clo);
                    pull.setVisibility(View.VISIBLE);
//                    btHelp.setText("已报名！");
                    btHelp.setEnabled(false);
                    btHelp.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: "+ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
//        Log.i(TAG, "initData: ---"+userList.get(0));

        gradview();
        /**
         * 评论信息
         */

        adapterComment = new AdapterComment(getApplicationContext(),data);
        // 为评论列表设置适配器
        list.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();
        adapterComment.notifyDataSetInvalidated();
        setListViewHeightBasedOnChildren(list);

    }

    public void gradview(){
        /**
         * 显示参与人
         */
        gridview.setAdapter(new BaseAdapter() {
            public ImageView img;

            @Override
            public int getCount() {
                return userList.size();
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
                View view = View.inflate(HelpInfo.this, R.layout.img_view, null);
                user = userList.get(position);
                Log.i(TAG, "getView: "+user);
                img = ((ImageView) view.findViewById(R.id.item_gridview));
                xUtilsImageUtils.display(img, HttpUtils.localhost_su + user.getPhoto(), true);
                return view;
            }
        });
    }

    //设置监听事件；
    public void setListener(){
        say.setOnClickListener(this);
        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
    }

    //用户头像点击事件；
    private void initEvent() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HelpInfo.this, HelpJoin.class);
                intent.putExtra("userInfo",(Parcelable) userList.get(position));
                intent.putExtra("helpId",help.getHelpId());
                intent.putExtra("flag",flag);
                startActivityForResult(intent,1);
            }
        } );

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final Integer userId = data.get(position).getUserId();
                final Integer comId = data.get(position).getId();
                Log.i(TAG, "onItemLongClick: +++++"+comId);

                if (data.get(position).getUserId().equals(((MyApplication)getApplication()).getUser().getUserId())){

                    new AlertDialog.Builder(HelpInfo.this).setItems(items, new DialogInterface.OnClickListener() {
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
                }else{
                    Toast.makeText(HelpInfo.this,"无法操作",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
    @OnClick({R.id.bt_help, R.id.hl_content_title,R.id.pull})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hl_content_title:
                finish();
                break;
            //跳到我要报名界面
            case R.id.bt_help:
                Intent intent = new Intent(HelpInfo.this, JoinHelp.class);
                intent.putExtra("helpId", help.getHelpId());
                startActivityForResult(intent,1);
                break;
            case R.id.tv_help_say:
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
            case R.id.pull:
                if (help.getUser().getUserId() == ((MyApplication) getApplication()).getUser().getUserId()) {
                    //判断：如果活动里面的用户id跟默认的用户地id一样，就表示这个活动是我自己发布的，我就可以让报名的按钮隐藏，
                    pull.setVisibility(View.VISIBLE);
//                    btAcJoin.setVisibility(View.GONE);
                    showPopupWindow(v);
                } else {
                    pull.setVisibility(View.VISIBLE);
                    initPopupWindow(v);
                }
                break;
            default:
                break;
        }
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
                    RequestParams params = new RequestParams(HttpUtils.localhost_su+"deletehelpjoin");
                    params.addBodyParameter("helpId", String.valueOf(help.getHelpId()));
                    params.addBodyParameter("userId", String.valueOf(((MyApplication)getApplication()).getUser().getUserId()));
                    x.http().get(params, new Callback.CacheCallback<String>() {
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
                    btHelp.setVisibility(View.VISIBLE);
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

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.publish_choose_item,new String[]{"取消活动","取 消"});
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
                    RequestParams params = new RequestParams(HttpUtils.localhost_su+"deletehelp");
                    params.addBodyParameter("helpId", String.valueOf(help.getHelpId()));
                    x.http().get(params, new Callback.CommonCallback<String>() {
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

                }else if(position==1){
                    popupWindow.dismiss();

                }
            }
        });
    }

    //发送评论；
    public void sendComment() {
        /**
         * 获得个人信息
         */

        RequestParams re=new RequestParams(HttpUtils.localhost_su+"queryuser");
        re.addBodyParameter("userId",String.valueOf(((MyApplication)getApplication()).getUser().getUserId()));
        System.out.println(re);
        x.http().post(re, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("__________________________________________________"+result);
                Gson gson = new Gson();
                Type type = new TypeToken<User>(){}.getType();
                user1 = gson.fromJson(result,type);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
            }
        });
        if (comment_content.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            // 生成评论数据
            Comment comment = new Comment();
//            comment.setName(activity.getUser().getUserName()+"：");
//            comment.setContent(activity.getUser().getUserName()+":");
//            User user = new User(5);
            comment.setUser(user1);
            comment.setContent(comment_content.getText().toString());
            comment.setCreate(new Timestamp(System.currentTimeMillis()));
            System.out.println("comment====="+comment);
            adapterComment.addComment(comment);
            // 发送完，清空输入框
            comment_content.setText("");

//            Toast.makeText(getApplicationContext(), "评论成功！", Toast.LENGTH_SHORT).show();
            RequestParams params = new RequestParams(HttpUtils.localhost_su + "inserthelpcomment");
            params.addBodyParameter("helpId", String.valueOf(help.getHelpId()));
            params.addBodyParameter("userId", String.valueOf(((MyApplication) getApplication()).getUser().getUserId()));
            params.addBodyParameter("time", String.valueOf(new Timestamp(System.currentTimeMillis())));
            params.addBodyParameter("content", comment.getContent());
            x.http().get(params, new Callback.CommonCallback<String>() {
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

        public AdapterComment(Context c, List<Comment> data){
            this.context = c;
            this.data = data;
        }

        @Override
        public int getCount() {
//            System.out.println("dasdas"+data.size());
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
            System.out.println("+"+position);
            ViewHolder holder = null;
            // 重用convertView
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.omment_item, null);
                holder.comment_photo = ((ImageView) convertView.findViewById(R.id.comment_photo));
                holder.comment_name = (TextView) convertView.findViewById(R.id.comment_name);
                holder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
                holder.comment_time = ((TextView) convertView.findViewById(R.id.comment_time));
                Log.i(TAG, "onItemClick: 留言的用户id：-----"+data.get(position).getUserId());
                Log.i(TAG, "onItemClick: 默认的用户id:------"+((MyApplication)getApplication()).getUser().getUserId());
                convertView.setTag(holder);
            } else
                System.out.println("++++"+data.get(1));
//            Log.i("评论人的Id", "getView: " + data.get(position).getUserId());
//            Log.i("评论人的NAme", "getView: " + data.get(position).getUser().getUserName());
            holder = (ViewHolder) convertView.getTag();
            holder.comment_content.setText(data.get(position).getContent());
            holder.comment_time.setText(new SimpleDateFormat("MM-dd HH:mm").format(data.get(position).getCreate()));
            x.image().bind(holder.comment_photo, HttpUtils.localhost_su + data.get(position).getUser().getPhoto());
            holder.comment_name.setText(data.get(position).getUser().getUserName()+":");


            return convertView;

        }

        /**
         * 添加一条评论,刷新列表
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
        public class ViewHolder{
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
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    /**
     * 根据留言表里的id删除留言表里面的数据；
     * 前提是这条留言是自己发布的
     * @param userId
     * @param acmId
     */
    private void delete(Integer userId, Integer acmId) {
        RequestParams parms = new RequestParams(HttpUtils.localhost_su+"deletecomhelp");
        parms.addBodyParameter("userId", String.valueOf(userId));
        Log.i(TAG, "delete: chuanguoqude  userId::"+String.valueOf(userId));
        parms.addBodyParameter("id", String.valueOf(acmId));
        Log.i(TAG, "delete: chuanguoqude  acmId::"+String.valueOf(acmId));
        x.http().get(parms, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: 删除comment出错了："+ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

//    public void getCommentHelp(){
//        RequestParams re = new RequestParams(HttpUtils.localhost_su+"getcomhelpbyid");
//        re.addBodyParameter("helpId",String.valueOf(help.getHelpId()));
//        Log.i(TAG, "initData@@: " +re);
//        x.http().get(re, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.i(TAG, "onSuccess: "+result);
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<Comment>>(){}.getType();
//                List<Comment> l = gson.fromJson(result,type);
//                data.clear();
//                data.addAll(l);
//                Log.i(TAG, "onSuccess: "+data.get(0).getContent());
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.i(TAG, "onError: " +ex.toString());
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
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                RequestParams re1=new RequestParams(HttpUtils.localhost_su+"gethelpjoin");
                re1.addBodyParameter("helpId",String.valueOf(help.getHelpId()));
                x.http().get(re1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        Type type=new TypeToken<List<User>>(){}.getType();
                        userList.clear();
                        userList=gson.fromJson(result,type);
                        gradview();
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
