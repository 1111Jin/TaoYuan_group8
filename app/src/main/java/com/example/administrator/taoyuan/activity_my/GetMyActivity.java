package com.example.administrator.taoyuan.activity_my;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.fragment.ActivityByAttendFragment;
import com.example.administrator.taoyuan.fragment.ActivityByMeFragment;
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
    private LayoutInflater inflate;
    List<Fragment> list = new ArrayList<Fragment>();
    private IndicatorViewPager indicatorViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_my);
        initData();
        initView();
    }

    public void initData() {

        tab = ((ScrollIndicatorView) findViewById(R.id.moretab_indicator));
        viewpage = ((ViewPager) findViewById(R.id.moretab_viewPager));

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
            textView.setText(names[position % names.length]);
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
            return list.get(position);
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
