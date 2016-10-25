package com.example.administrator.taoyuan.fragment;

import android.animation.ObjectAnimator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_life.fabu;
import com.example.administrator.taoyuan.pojo.ListInfo;
import com.example.administrator.taoyuan.pojo.ListLifeInfo;
import com.example.administrator.taoyuan.utils.MyFragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class Life extends Fragment implements ViewPager.OnPageChangeListener,View.OnClickListener{


    private ImageView line_tab;
    private ListView lv_lifeinfo;
    BaseAdapter adapter;
    final ArrayList<ListLifeInfo.LifeInfo> lifelist= new ArrayList<ListLifeInfo.LifeInfo>();
    private  ListView lv_dongtai1;
    ListInfo listinfo;
    public static final Integer REQUSETCODE=1;
    private Button tv_fabu;
    private boolean isScrolling = false; // 手指是否在滑动
    private boolean isBackScrolling = false; // 手指离开后的回弹
    View view;
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    private ViewPager viewPager;
    private RadioButton tab1;
    private RadioButton tab2;
    private RadioButton tab3;
    private int moveOne = 0;
    private long startTime = 0;
    private long currentTime = 0;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_activity_life,null);

        initView();
        Fragment fragment2 = new lifeFriends();
        Fragment fragment1 = new lifeHot();
        Fragment fragment3 = new Life2();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        initEvent();

//        initLineImage();
        return view;


    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }
    private void initView() {
        viewPager = ((ViewPager) view.findViewById(R.id.viewPager));
        tab1 = ((RadioButton) view.findViewById(R.id.rb_peng));
        tab2 = ((RadioButton) view.findViewById(R.id.rb_huati));
        tab3 = ((RadioButton) view.findViewById(R.id.rb_suoyou));
        line_tab = ((ImageView)view.findViewById(R.id.line_tab_life));
        tv_fabu = ((Button)view.findViewById(R.id.tv_fabu));
    }
//    private void initLineImage() {
//        //获取屏幕的宽度；
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int screen = dm.widthPixels;
//        //重新设置下划线的宽度；
//        ViewGroup.LayoutParams lp = line_tab.getLayoutParams();
//        lp.width = screen / 2;
//        line_tab.setLayoutParams(lp);
//        //滑动一个页面的距离；
//        moveOne = lp.width;
//    }
    public void initEvent(){
        viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(),fragmentList));
//        viewPager.setCurrentItem(0);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);


        tv_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),fabu.class);
                intent.putExtra("orderDeatils",tv_fabu.getClass());
                startActivityForResult(intent,1);

            }
        });
    }





    /** * 下划线跟随手指的滑动而移动 * @param toPosition * @param positionOffsetPixels */
    private void movePositionX(int toPosition, float positionOffsetPixels) {
        // TODO Auto-generated method stub
        float curTranslationX = line_tab.getTranslationX();
        float toPositionX = moveOne * toPosition + positionOffsetPixels;
        ObjectAnimator animator = ObjectAnimator.ofFloat(line_tab, "translationX", curTranslationX, toPositionX);
        animator.setDuration(500); animator.start();
    }

    //下划线滑动到新的选项卡中；
    private void movePositionX(int toPosition) {
        // TODO Auto-generated method stub
        movePositionX(toPosition, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_peng:
                viewPager.setCurrentItem(0);
                Toast.makeText(getActivity().getApplicationContext(),"点击了第一个小爽",Toast.LENGTH_LONG);
                break;
            case R.id.rb_huati:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rb_suoyou:
                viewPager.setCurrentItem(2);
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentTime = System.currentTimeMillis();
        if (isScrolling && (currentTime - startTime > 200)) {
            movePositionX(position, moveOne * positionOffset);
            startTime = currentTime;
        }
        if (isBackScrolling) {
            movePositionX(position);
        }
    }

    @Override
    public void onPageSelected(int position) {

        switch (position){
            case 0:
                tab1.setTextColor(Color.BLACK);
                tab2.setTextColor(Color.GRAY);
                break;
            case 1:
                tab1.setTextColor(Color.GRAY);
                tab2.setTextColor(Color.BLACK);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        switch (state){
            case 1:
                isScrolling = true;
                isBackScrolling = false;
                break;
            case 2:
                isScrolling = false;
                isBackScrolling = true;
                break;
            default:
                isScrolling = false;
                isBackScrolling = false;
                break;
        }
    }
}


















