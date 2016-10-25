package com.example.administrator.taoyuan.activity_my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.administrator.taoyuan.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Modify_myName_activity extends AppCompatActivity {

    @InjectView(R.id.rl_save)
    RelativeLayout rlSave;
    @InjectView(R.id.rl_back)
    RelativeLayout rlBack;
    @InjectView(R.id.et_edit_name)
    EditText etEditName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_my_name_activity);
        ButterKnife.inject(this);

        initData();
        initEvent();


    }

    public void initData(){
        Intent i=getIntent();
        etEditName.setText(i.getStringExtra("name"));

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ModifyMyActivity.class);
                String name=etEditName.getText().toString();
                Log.i("LOG", "onClick: "+name);
                intent.putExtra("name",name);
                setResult(RESULT_OK,intent);
                finish();

            }
        });
    }

    public void initEvent(){

    }


}
