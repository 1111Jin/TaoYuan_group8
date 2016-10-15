package com.example.administrator.taoyuan.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.application.MyApplication;

/**
 * Created by mawuyang on 2016-10-15.
 */
public class Modify_myName_fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_modify_my_name_activity, null);
        EditText etName= (EditText) view.findViewById(R.id.et_edit_name);
        String name=etName.getText().toString();
        System.out.println(name);


        //初始化姓名

        return view;

    }
}
