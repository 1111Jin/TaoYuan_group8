package com.example.administrator.taoyuan.activity_life;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;

import com.example.administrator.taoyuan.fragment.AllRepairFragment;
import com.example.administrator.taoyuan.fragment.BaseFragment;
import com.example.administrator.taoyuan.fragment.Life;

import com.example.administrator.taoyuan.fragment.Life2;
import com.example.administrator.taoyuan.fragment.UnPersonnalFragment;
import com.example.administrator.taoyuan.fragment.lifeFriends;
import com.example.administrator.taoyuan.fragment.lifeHot;
import com.example.administrator.taoyuan.utils.DisplayUtil;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

public class LifeShare extends AppCompatActivity {


    private static final String TAG = "LifeShare";
    private IndicatorViewPager indicatorViewPager;
    List<BaseFragment> lists=new ArrayList<BaseFragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_share);

        initData();

        ViewPager viewPager = (ViewPager) findViewById(R.id.moretab_viewPager);
        ScrollIndicatorView scrollIndicatorView = (ScrollIndicatorView) findViewById(R.id.moretab_indicator);
        float unSelectSize = 12;
        float selectSize = unSelectSize * 1.3f;
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(0xFF2196F3, Color.GRAY).setSize(selectSize, unSelectSize));

        scrollIndicatorView.setScrollBar(new ColorBar(this, 0xFF2196F3, 4));

        viewPager.setOffscreenPageLimit(1);
        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);
        indicatorViewPager.setAdapter(new LifeAdapter(getSupportFragmentManager()));
    }

    private void initData() {
        lists.add(new lifeFriends());
        lists.add(new Life2());
        lists.add(new lifeHot());


    }
    public  class LifeAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] versions = {"朋友","所有人", "热门"};
    public LifeAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_UNCHANGED;
        }
        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.tab_fragment, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(versions[position]);

            int witdh = getTextWidth(textView);
            int padding = DisplayUtil.dipToPix(getApplicationContext(), 8);
            //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
            //1.3f是根据上面字体大小变化的倍数1.3f设置
            textView.setWidth((int) (witdh * 1.3f) + padding);

            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            return lists.get(position);
        }

        private int getTextWidth(TextView textView) {
            if (textView == null) {
                return 0;
            }
            Rect bounds = new Rect();
            String text = textView.getText().toString();
            Paint paint = textView.getPaint();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int width = bounds.left + bounds.width();
            return width;
        }
    }
}

