package com.example.administrator.taoyuan.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.taoyuan.R;

/**
 * Created by mawuyang on 2016-10-17.
 */
public class TitleBar extends RelativeLayout {
    private RelativeLayout left;
    private TextView titleStr;
    private RelativeLayout right;
    private ImageView leftImage;
    private Button rightText;
    private TextView left_Text;

    public TitleBar(Context context) {
        super(context);
    }

    //配置文件
    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view=LayoutInflater.from(context).inflate(R.layout.titlebar,this,true);
        left = ((RelativeLayout) view.findViewById(R.id.left));
        titleStr = ((TextView) view.findViewById(R.id.title));
        right = ((RelativeLayout) view.findViewById(R.id.right));
        leftImage = ((ImageView) view.findViewById(R.id.iv_leftImage));
        left_Text = ((TextView) view.findViewById(R.id.left_text));
//        rightText = ((Button) view.findViewById(R.id.right_text));


        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        String title = ta.getString(R.styleable.TitleBar_titleStr);
        titleStr.setText(title);

        Boolean leftFlag=ta.getBoolean(R.styleable.TitleBar_showLeft,true);
        if (!leftFlag){
            left.setVisibility(view.INVISIBLE);
        }
//        Boolean rightFlag=ta.getBoolean(R.styleable.TitleBar_showRight,true);
//        if (!leftFlag){
//            right.setVisibility(view.INVISIBLE);
//        }

        String lefttext = ta.getString(R.styleable.TitleBar_leftText);
        left_Text.setText(lefttext);
//        String righttext = ta.getString(R.styleable.TitleBar_rightText);
//        rightText.setText(righttext);

    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLeft(@Nullable OnClickListener l){
        left.setOnClickListener(l);
    }

//    public void setRight(@Nullable OnClickListener l){
//        right.setOnClickListener(l);
//    }




}
