package com.example.administrator.taoyuan.activity_my;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.fragment.ActivityByAttendFragment;
import com.example.administrator.taoyuan.fragment.ActivityByMeFragment;
import com.example.administrator.taoyuan.pojo.ListUserBean;
import com.example.administrator.taoyuan.utils.HttpUtils;
import com.example.administrator.taoyuan.utils.TitleBar;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.DrawableBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

public class GetMyActivity extends AppCompatActivity {

    private ScrollIndicatorView tab;
    private ViewPager viewpage;
    private int unSelectTextColor;
    private String[] names = {"我发布的活动", "我参加的活动"};
    private String[] namess={"他发布的活动","他参加的活动"};
    private LayoutInflater inflate;
    List<Fragment> list = new ArrayList<Fragment>();
    private IndicatorViewPager indicatorViewPager;
    private TitleBar titleBar;
    private Integer userId1 = HttpUtils.userId;
    ListUserBean.User user;
//    private FragmentManager manager;
//    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_my);
//        manager=getFragmentManager();
        initData();
        initView();
    }

    public void initData() {

        tab = ((ScrollIndicatorView) findViewById(R.id.moretab_indicator));
        viewpage = ((ViewPager) findViewById(R.id.moretab_viewPager));
        titleBar = ((TitleBar) findViewById(R.id.tt_back));

        Intent intent=getIntent();
        user = intent.getParcelableExtra("user");
//        System.out.println("123"+user.friendId);
//        userId=Integer.parseInt(intent.getStringExtra("userId"));
//        userId1 = intent.getIntExtra("Id",HttpUtils.userId);
//        System.out.println("22"+);

        if(user!=null) {
            userId1 = user.friendId;
        }

        System.out.println("111+"+userId1);

        tab.setBackgroundColor(Color.RED);
        tab.setScrollBar(new DrawableBar(this, R.drawable.round_border_white_selector, ScrollBar.Gravity.CENTENT_BACKGROUND) {
            @Override
            public int getHeight(int tabHeight) {
                return tabHeight - dipToPix(12);
            }

            @Override
            public int getWidth(int tabWidth) {
                return tabWidth - dipToPix(12);
            }
        });
    }
    public void initView(){
        unSelectTextColor = Color.WHITE;
        tab.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.RED, unSelectTextColor));

        viewpage.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(tab, viewpage);
        inflate = LayoutInflater.from(getApplicationContext());
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        list.add(new ActivityByMeFragment());
        list.add(new ActivityByAttendFragment());

        titleBar.setLeft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_fragment, container, false);
            }
            TextView textView = (TextView) convertView;
            if (userId1.equals(HttpUtils.userId)) {
                textView.setText(names[position % names.length]);
            }else{
                TextView titlestr = ((TextView) titleBar.findViewById(R.id.title));
                titlestr.setText("他的活动");
                textView.setText(namess[position % namess.length]);
            }
            int padding = dipToPix(10);
            textView.setPadding(padding, 0, padding, 0);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
//            MoreFragment fragment = new MoreFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt(MoreFragment.INTENT_INT_INDEX, position);
//            fragment.setArguments(bundle);
            Fragment fragment1 = list.get(position);
            Bundle bundle = new Bundle();
//            String strValue = userId.toString().trim();
            bundle.putInt("userId",userId1);
            fragment1.setArguments(bundle);
            return fragment1;
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_NONE;
        }

    }

        /**
         * 根据dip值转化成px值
         *
         * @param dip
         * @return
         */
    private int dipToPix(float dip) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
        return size;
    }

}
