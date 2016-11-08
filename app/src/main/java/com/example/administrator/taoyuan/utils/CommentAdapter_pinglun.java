package com.example.administrator.taoyuan.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_life.LifeToFriend;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2016/11/2.
 */
public class CommentAdapter_pinglun extends BaseAdapter {

    private Context context;
    private  List<ListLifeInfo.LifeInfo> list;
    Integer id;
    private Handler handler;
    int pageNo=1;
    int pageSize=4;
    boolean flag11 = false;
    final ArrayList<ListLifeInfo.LifeInfo> lifelist= new ArrayList<ListLifeInfo.LifeInfo>();
    private RefreshListView lv_lifeinfo;
    CommonAdapter<ListLifeInfo.LifeInfo> adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor1;


    public CommentAdapter_pinglun(Context context, Handler handler, List<ListLifeInfo.LifeInfo> list,Integer id){
        this.context = context;
        this.list = list;
        this.handler = handler;
        this.id=id;
        sharedPreferences = context.getSharedPreferences("dianzan_sp", Context.MODE_PRIVATE);
        editor1 = sharedPreferences.edit();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ListLifeInfo.LifeInfo model = list.get(position);
        System.out.println("-----"+model.getRemarks());
        ViewItemHolder viewItemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_list_lv_dongtai_item,
                    null);

            viewItemHolder = new ViewItemHolder();
            viewItemHolder.iv_photo = (ImageView) convertView
                    .findViewById(R.id.iv_photo);
            viewItemHolder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            viewItemHolder.tv_title = (TextView) convertView
                    .findViewById(R.id.tv_title);
            viewItemHolder.iv_contphoto = (ImageView) convertView
                    .findViewById(R.id.iv_contphoto);
            viewItemHolder.ib_pinglun = (ImageView) convertView
                    .findViewById(R.id.ib_pinglun);
            viewItemHolder.iv_zan = (ImageView) convertView
                    .findViewById(R.id.iv_zan);
            viewItemHolder.share = (ImageView) convertView
                    .findViewById(R.id.share);
            viewItemHolder.lv_remarks = (NoScrollListView) convertView
                    .findViewById(R.id.lv_remarks);
            convertView.setTag(viewItemHolder);


        } else {
            viewItemHolder = (ViewItemHolder) convertView.getTag();
        }


        //赋值HttpUtils.localhost_jt+"imgs/"+dongtai.headphoto+""
        xUtilsImageUtils.display(viewItemHolder.iv_photo, HttpUtils.localhost_jt+"imgs/" + model.getHeadphoto(), true);
        viewItemHolder.iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LifeToFriend.class);
                intent.putExtra("lifeinfo",list.get(position).userId );
//                intent.putExtra("lifeinfo", lifelist.get(position));
                context.startActivity(intent);
            }
        });
        xUtilsImageUtils.display(viewItemHolder.iv_contphoto, HttpUtils.localhost_jt+"imgs/" + model.getContent_photo(), true);
        viewItemHolder.tv_name.setText(model.getContent());
        viewItemHolder.tv_title.setText(model.getUserName());


        //评论点击事件
        viewItemHolder.ib_pinglun
                .setOnClickListener(new ListViewButtonOnClickListener(position));
        viewItemHolder.ib_pinglun.setFocusable(false);
//        List<Remark> replayList = new ArrayList<Remark>();
        //数据库里的评论内容，得到回复人账号,回复人昵称,被回复人账号,被回复人昵称,回复的内容


//        if (model.getRemarks().size() > 0) {
//
//            viewItemHolder.lv_remarks.setVisibility(View.VISIBLE);
//            System.out.println("==="+position);
//            ReplayAdapter replayAdapter = new ReplayAdapter(context, handler, model.getRemarks(), position);
//            viewItemHolder.lv_remarks.setAdapter(replayAdapter);
//        } else {
//            viewItemHolder.lv_remarks.setVisibility(View.GONE);
//        }




        //点赞
        final ImageView im_zan = viewItemHolder.iv_zan;
        im_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPreferences.contains(model.getDontaiId()+"")){//选中状态
                    im_zan.setImageResource(R.drawable.zan);
                    int user_Id =id;    //点赞人Id
                    int dontai_Id = model.getDontaiId();
                    removeZan(user_Id, dontai_Id);
                    Toast.makeText(context,"取消点赞",Toast.LENGTH_SHORT).show();
                    editor1.remove(model.getDontaiId()+"");
                }else{
                    im_zan.setImageResource(R.drawable.zan1);
                    int user_Id = id;   //点赞人Id
                    int dontai_Id = model.getDontaiId();                                  //动态Id
                    String zanTime = String.valueOf(System.currentTimeMillis());     //点赞时间
                    addZan(user_Id, dontai_Id, zanTime);
                    Toast.makeText(context,"点赞成功",Toast.LENGTH_SHORT).show();
                    editor1.putInt(model.getDontaiId()+"",(Integer)im_zan.getTag());
                }
                editor1.commit();
            }
        });
        im_zan.setTag(model.getDontaiId());
        if(sharedPreferences.contains(position+"")){
            im_zan.setImageResource(R.drawable.zan1);
        }else {
            im_zan.setImageResource(R.drawable.zan);
        }
        //分享
        viewItemHolder.share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showShare();
            }
        });

//        System.out.println("=="+model.getRemarks().get(0).getUser());
        ReplayAdapter replayAdapter = new ReplayAdapter(context,handler,model.getRemarks(),position);
        viewItemHolder.lv_remarks.setAdapter(replayAdapter);

        return convertView;
    }


    class ViewItemHolder {
        ImageView iv_photo;
        TextView tv_name;
        TextView  tv_title;
        ImageView iv_contphoto;
        ImageView iv_zan;
        ImageView ib_pinglun;
        ImageView share;
        com.example.administrator.taoyuan.utils.NoScrollListView lv_remarks;

    }

    private class ListViewButtonOnClickListener implements View.OnClickListener{
        private int position;
        public ListViewButtonOnClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ib_pinglun:
                    handler.sendMessage(handler.obtainMessage(10,position));
                    break;
            }
        }
    }

    public void add(ListLifeInfo.LifeInfo comment){
        this.list.add(comment);
        this.notifyDataSetChanged();
    }


    private void showShare() {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("来自桃园的分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("分享");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(context);
    }
    private void addZan(int user_Id, int dontai_Id, String zanTime) {
        RequestParams params = new RequestParams(HttpUtils.localhost_jt + "AddZanServlet");
        params.addBodyParameter("userId", String.valueOf(user_Id));
        params.addBodyParameter("dongtaiId", String.valueOf(dontai_Id));
        params.addBodyParameter("zanTime", zanTime);
        System.out.println(user_Id+"onSuccess ++6666666666666");
        System.out.println(dontai_Id+"onSuccess ++6666666666666");
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
    private void removeZan(int user_Id, int dontai_Id) {
        RequestParams params = new RequestParams(HttpUtils.localhost_jt + "DeleteZanServlet");
        params.addBodyParameter("userId", String.valueOf(user_Id));
        params.addBodyParameter("dongtaiId", String.valueOf(dontai_Id));
        System.out.println(user_Id+"onSuccess --6666666666666");
        System.out.println(dontai_Id+"onSuccess --6666666666666");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("onSuccess 6666666666666");
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