package com.example.administrator.taoyuan.fragment;

import android.animation.ObjectAnimator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_linli.MyAnimations;
import com.example.administrator.taoyuan.activity_linli.PublishActivity;
import com.example.administrator.taoyuan.activity_linli.PublishHelp;
import com.example.administrator.taoyuan.application.MyApplication;
import com.example.administrator.taoyuan.utils.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class linli extends Fragment implements ViewPager.OnPageChangeListener,View.OnClickListener{
    private Button publish;
    private ListView lv_choose;
    View view;

    List<Fragment> fragmentList = new ArrayList<Fragment>();

    private ViewPager viewPager;
    private TextView tab1;
    private TextView tab2;
    private ImageView line_tab;
    private boolean isScrolling = false; // 手指是否在滑动
    private boolean isBackScrolling = false; // 手指离开后的回弹
    private long startTime = 0;
    private long currentTime = 0;
    private int moveOne = 0;
    private ListView lv_list;
    private FrameLayout fm;
    private boolean isShowing;
    private RelativeLayout buttons_wrapper_layout;
    private ImageView buttons_show_hide_button;
    private RelativeLayout buttons_show_hide_button_layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_linli,null);
        MyAnimations.initOffset(getActivity());

        initView();
        Fragment fragment2 = new linli_activity_fragment();
        Fragment fragment1 = new linli_help_fragment();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        initLineImage();

        initEvent();
        return view;

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//
//    }

    private void initView() {
        viewPager = ((ViewPager) view.findViewById(R.id.viewpager));
        tab1 = ((TextView) view.findViewById(R.id.tv_tab1));
        tab2 = ((TextView) view.findViewById(R.id.tab2));
        line_tab = ((ImageView)view.findViewById(R.id.line_tab));
        publish = ((Button) view.findViewById(R.id.bt_publish));
        buttons_wrapper_layout = (RelativeLayout)view.findViewById(R.id.buttons_wrapper_layout);
        buttons_show_hide_button_layout = (RelativeLayout) view.findViewById(R.id.buttons_show_hide_button_layout);
        buttons_show_hide_button = (ImageView) view.findViewById(R.id.buttons_show_hide_button);
    }
    private void initLineImage() {
        //获取屏幕的宽度；
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screen = dm.widthPixels;
        //重新设置下划线的宽度；
        ViewGroup.LayoutParams lp = line_tab.getLayoutParams();
        lp.width = screen / 2;
        line_tab.setLayoutParams(lp);
        //滑动一个页面的距离；
        moveOne = lp.width;
    }
    public void initEvent(){
        viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(),fragmentList));
        viewPager.setCurrentItem(0);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);
        buttons_show_hide_button_layout.setOnClickListener(this);
        for (int i = 0; i < buttons_wrapper_layout.getChildCount(); i++) {
            buttons_wrapper_layout.getChildAt(i).setOnClickListener(new OnClickImageButton());
        }
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupWindow(v);
            }
        });
    }
    public void initPopupWindow(View v){
        View view = View.inflate(getActivity().getApplicationContext(),R.layout.publish_choose,null);
        final PopupWindow popupWindow = new PopupWindow(view,300,180);
        lv_choose = ((ListView) view.findViewById(R.id.lv_publish));
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),R.layout.publish_choose_item,new String[]{"活 动","互 帮"});
        lv_choose.setAdapter(adapter);
        //设置popupwindow属性；
        popupWindow.setOutsideTouchable(true);//获取焦点；
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//设置背景色
        popupWindow.showAsDropDown(v);//设置显示的位置；
        //设置popupwindow中的点击事件；
        lv_choose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();//关闭popupwindow；
                if (position==0){
                    Intent intent = new Intent(getActivity().getApplicationContext(),PublishActivity.class);
                    startActivity(intent);
                }else if (position==1){
                    Intent intent = new Intent(getActivity(), PublishHelp.class);
                    startActivity(intent);
                }
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentTime = System.currentTimeMillis();
        if (isScrolling && (currentTime - startTime > 100)) {
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

    class OnClickImageButton implements View.OnClickListener{

        @Override
        public void onClick(View arg0) {
            switch(arg0.getId()){
                case R.id.button_photo:
                    Toast.makeText(getActivity(), "photo", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.button_people:
                    Toast.makeText(getActivity(), "people", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_tab1:
                viewPager.setCurrentItem(0);
                Toast.makeText(getActivity().getApplicationContext(),"点击了第一个小爽",Toast.LENGTH_LONG);
                break;
            case R.id.tab2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.buttons_show_hide_button_layout:
                if (!isShowing) {
                    MyAnimations.startAnimationsIn(buttons_wrapper_layout, 300);
                    buttons_show_hide_button
                            .startAnimation(MyAnimations.getRotateAnimation(0,
                                    -270, 300));
                } else {
                    MyAnimations
                            .startAnimationsOut(buttons_wrapper_layout, 300);
                    buttons_show_hide_button
                            .startAnimation(MyAnimations.getRotateAnimation(
                                    -270, 0, 300));
                }
                isShowing = !isShowing;
        }
    }

}
