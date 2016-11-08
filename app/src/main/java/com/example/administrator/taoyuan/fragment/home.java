package com.example.administrator.taoyuan.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_home.BaoxiuActivity1;
import com.example.administrator.taoyuan.activity_home.GonggaoActivity;
import com.example.administrator.taoyuan.activity_home.GonggaoXiangqing;
import com.example.administrator.taoyuan.activity_home.HuodongTuijian;
import com.example.administrator.taoyuan.activity_home.Netutil;
import com.example.administrator.taoyuan.activity_home.QianActivity;
import com.example.administrator.taoyuan.activity_home.TellphoneActivity;
import com.example.administrator.taoyuan.activity_home.JifenActivity;
import com.example.administrator.taoyuan.activity_linli.ActivityInfo;
import com.example.administrator.taoyuan.pojo.Activity;
import com.example.administrator.taoyuan.pojo.Comment;
import com.example.administrator.taoyuan.pojo.GongGaoBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment {


    @InjectView(R.id.tb_title)
    Toolbar tbTitle;
    @InjectView(R.id.iv_lianxidianhua)
    ImageView ivLianxidianhua;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.tv_wybx)
    TextView tvWybx;
    @InjectView(R.id.tv_wygg)
    TextView tvWygg;
    @InjectView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @InjectView(R.id.v1)
    View v1;
    ArrayList<GongGaoBean.GongGao> gonggaoList = new ArrayList<>();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String imagePath;
    String titlel;
    @InjectView(R.id.tv12)
    TextView tv12;
    @InjectView(R.id.HorizontalListView)
    com.example.administrator.taoyuan.activity_home.HorizontalListView HorizontalListView;
    private Banner banner;
    private Context context;
    View view1;
    Bundle bundle = new Bundle();
    private FragmentManager manager;
    private FragmentTransaction ft;
    private BaseAdapter adapter;
    private ImageView iv_people;
    private TextView tv_title;
    final List<Activity> activityList = new ArrayList<Activity>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getActivity());
        view1 = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.inject(this, view1);
        getGonggao();
        initData();
        HorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ActivityInfo.class);
                //带参传值；
                List<Comment> coList  = activityList.get(position).getList();
                intent.putExtra("ActivityInfo", activityList.get(position));
                intent.putExtra("comment",(Serializable) coList);
                startActivity(intent);
            }
        });
        banner = ((Banner) view1.findViewById(R.id.banner));
        return view1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_lianxidianhua, R.id.textView2, R.id.tv_wybx, R.id.tv_wygg, R.id.tv_jjxq, R.id.tv12})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_lianxidianhua:
                Intent intent = new Intent(getActivity(), TellphoneActivity.class);
                startActivity(intent);
                break;
            case R.id.textView2:
                Intent intent2 = new Intent(getActivity(), QianActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_wybx:
                Intent intent1 = new Intent(getActivity(), BaoxiuActivity1.class);
                startActivity(intent1);
                break;
            case R.id.tv_wygg:
                Intent intent3 = new Intent(getActivity(), GonggaoActivity.class);
                startActivity(intent3);
                break;
            case R.id.tv_jjxq:
                Intent intent5=new Intent(getActivity(), JifenActivity.class);
                startActivity(intent5);
                break;
            case R.id.tv12:
                Intent intent4 = new Intent(getActivity(), HuodongTuijian.class);
                startActivity(intent4);
                break;
        }
    }

    public void getGonggao() {
        final List<String> titles = new ArrayList<>();
        final List<String> image = new ArrayList<>();
        RequestParams params = new RequestParams(Netutil.url + "ckgongGao");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GongGaoBean bean = gson.fromJson(result, GongGaoBean.class);
                gonggaoList.addAll(bean.ggList);
                if (gonggaoList.size() > 5) {
                    titles.add(gonggaoList.get(gonggaoList.size() - 5).getGonggaoTitle());
                    titles.add(gonggaoList.get(gonggaoList.size() - 4).getGonggaoTitle());
                    titles.add(gonggaoList.get(gonggaoList.size() - 3).getGonggaoTitle());
                    titles.add(gonggaoList.get(gonggaoList.size() - 2).getGonggaoTitle());
                    titles.add(gonggaoList.get(gonggaoList.size() - 1).getGonggaoTitle());
                    image.add(Netutil.url + "image/" + gonggaoList.get(gonggaoList.size() - 5).getGonggaoImg() + ".png");
                    image.add(Netutil.url + "image/" + gonggaoList.get(gonggaoList.size() - 4).getGonggaoImg() + ".png");
                    image.add(Netutil.url + "image/" + gonggaoList.get(gonggaoList.size() - 3).getGonggaoImg() + ".png");
                    image.add(Netutil.url + "image/" + gonggaoList.get(gonggaoList.size() - 2).getGonggaoImg() + ".png");
                    image.add(Netutil.url + "image/" + gonggaoList.get(gonggaoList.size() - 1).getGonggaoImg() + ".png");
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                    banner.setImageLoader(new GlideImageLoader());
                    banner.setIndicatorGravity(BannerConfig.RIGHT);
                    banner.setDelayTime(3000);
                    banner.isAutoPlay(true);
                    banner.setBannerTitles(titles);
                    banner.setImages(image);
                    banner.setOnBannerClickListener(new OnBannerClickListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            GongGaoBean gongGaoBean = new GongGaoBean();
                            GongGaoBean.GongGao gongGao = gongGaoBean.new GongGao(gonggaoList.get(gonggaoList.size() - 6 + position).gonggaoId, gonggaoList.get(gonggaoList.size() - 6 + position).getGonggaoContent(),
                                    gonggaoList.get(gonggaoList.size() - 6 + position).gonggaoImg, gonggaoList.get(gonggaoList.size() - 6 + position).gonggaofabiaoTime, gonggaoList.get(gonggaoList.size() - 6 + position).gonggaoendTime,
                                    gonggaoList.get(gonggaoList.size() - 6 + position).gonggaoAddress, gonggaoList.get(gonggaoList.size() - 6 + position).gonggaoTitle);
                            bundle.putSerializable("gonggaoList", gongGao);
                            bundle.putString("flag","1");
                            Intent intent = new Intent(getActivity(), GonggaoXiangqing.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
                banner.start();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "请求数据失败,请检查网络是否连接", Toast.LENGTH_SHORT).show();
                System.out.println(ex.toString() + "???????????????");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public class GlideImageLoader implements ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    //获取服务端传递过来的数据；
    public void initData() {
        adapter = new BaseAdapter() {
            private TextView time;
            private View line;
            private TextView address;

            @Override
            public int getCount() {
                return activityList.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = View.inflate(getActivity(), R.layout.huodong_item, null);
                Activity activity = activityList.get(position);
                iv_people = ((ImageView) view.findViewById(R.id.iv));
                tv_title = ((TextView) view.findViewById(R.id.tv));
                xUtilsImageUtils.display(iv_people, HttpUtils.localhost_su + activity.getActivityImg() + "", false);
                tv_title.setText(activity.getActivityTitle());

                return view;
            }

        };
        HorizontalListView.setAdapter(adapter);
        //从服务器拿数据；
        getActivityList();

    }
    private void getActivityList() {

        RequestParams params = new RequestParams(HttpUtils.localhost_su + "queryactivity");


        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.i("linli_activity_fragment", "onSuccess:activity数据传递进来了： =====>" + result);

                List<Activity> newacList = new ArrayList<Activity>();

                Gson gson = new Gson();
                Type type = new TypeToken<List<Activity>>() {
                }.getType();
                newacList = gson.fromJson(result, type);

                activityList.clear();
                 for (int i=0;i<newacList.size();i++) {
                           if (i>=newacList.size()-5) {
                               activityList.add(newacList.get(i));
                           }
                 }

                System.out.println("ActivityList:" + activityList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("linli_activity_fragment", "onError: ------->>>错误：" + ex);
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

}
