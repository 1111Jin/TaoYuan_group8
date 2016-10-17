package com.example.administrator.taoyuan.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_linli.PublishActivity;


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linli,null);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), PublishActivity.class);
                startActivity(intent);
            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    private void switchFragment(Fragment fragment) {
        this.getFragmentManager().beginTransaction().replace(R.id.fl_content, fragment).commit();
    }

}
