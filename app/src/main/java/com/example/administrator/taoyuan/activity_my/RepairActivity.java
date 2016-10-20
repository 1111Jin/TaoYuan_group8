package com.example.administrator.taoyuan.activity_my;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.fragment.AllRepairFragment;
import com.example.administrator.taoyuan.fragment.AlreadyRepairFragment;
import com.example.administrator.taoyuan.fragment.BaseFragment;
import com.example.administrator.taoyuan.fragment.PersonnalFragment;
import com.example.administrator.taoyuan.fragment.UnLookFragment;
import com.example.administrator.taoyuan.fragment.UnPersonnalFragment;
import com.example.administrator.taoyuan.fragment.UnRemarkFragment;

import java.util.ArrayList;
import java.util.List;

public class RepairActivity extends AppCompatActivity {

    List<BaseFragment> listf=new ArrayList<BaseFragment>();
    private ViewPager vp_repair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);

        vp_repair = ((ViewPager) findViewById(R.id.order_fragment_viewpager));

        listf.add(new AllRepairFragment());
        listf. add(new UnLookFragment());
        listf.add(new UnPersonnalFragment());
        listf.add(new PersonnalFragment());
        listf.add(new AlreadyRepairFragment());
        listf.add(new UnRemarkFragment());


        vp_repair.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return listf.get(position);
            }

            @Override
            public int getCount() {
                return listf.size();
            }
        });

    }
}
