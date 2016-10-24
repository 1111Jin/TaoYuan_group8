package com.example.administrator.taoyuan.fragment;


//import android.app.Fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_linli.PublishActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class linli extends Fragment {
    private Button rb_help;
    private Button rb_activity;
    private RadioGroup rg_two;
    private Button publish;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment oldfragment;
    private Fragment newfragment;
    private ListView lv_choose;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_linli,null);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        rg_two = ((RadioGroup) getActivity().findViewById(R.id.rg_two));
        publish = ((Button) getActivity().findViewById(R.id.bt_publish));

        rg_two.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment = null;
                switch (checkedId){
                    case R.id.rb_help:
                        fragment = new linli_help_fragment();
                        break;
                    case R.id.rb_activity:
                        fragment = new linli_activity_fragment();
                        break;
                }
                switchFragment(fragment);
            }

        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupWindow(v);
            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    private void switchFragment(Fragment fragment) {
        this.getFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commit();
    }

    public void initPopupWindow(View v){
        View view = View.inflate(getActivity().getApplicationContext(),R.layout.publish_choose,null);
        final PopupWindow popupWindow = new PopupWindow(view,220,180);
        lv_choose = ((ListView) view.findViewById(R.id.lv_publish));
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),R.layout.publish_choose_item,new String[]{"活动","互帮"});
        lv_choose.setAdapter(adapter);

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

                }
            }
        });
    }

}
