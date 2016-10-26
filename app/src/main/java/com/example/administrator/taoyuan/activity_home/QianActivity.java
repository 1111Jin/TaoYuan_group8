package com.example.administrator.taoyuan.activity_home;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.taoyuan.R;
import com.example.administrator.taoyuan.activity_home.db.SinInDao;

public class QianActivity extends Activity {



	private List<String> list = new ArrayList<String>();
	private ArrayList<HashMap<String, Object>> sinalist,alisttmp;
	private GridView gdDate;
	private int dayMaxNum;
	private int year,month,day,ym;
	Calendar cal=Calendar.getInstance();
	private int week;
	SinInDao sdao = new SinInDao(QianActivity.this);
	Time t=new Time();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qiandao);
		gdDate = (GridView)findViewById(R.id.gdDate);
		TextView txtNowDate = (TextView)findViewById(R.id.txtNowDate);
		//取本地时间（时间应该从服务器获取）
		t.setToNow();
		year=t.year;
		month=t.month+1;
		day = t.monthDay;
		Calendar rightNow = Calendar.getInstance();
		rightNow.set(year, month, 1);
		week=(rightNow.DAY_OF_WEEK);
		final String date=year+"-"+month+"-"+day;
		txtNowDate.setText(date);
		sdao.open();
		initData();

		gdDate.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				//判断是否已经签到 从服务器获取签到信息
				//模拟从本地数据库获取信息
				System.out.println(arg2-week+3+"////////////////////");
				if (day!=arg2-week+3){
					Toast toast=Toast.makeText(QianActivity.this,"只能签到今天",Toast.LENGTH_SHORT);
					toast.show();
				}
				if(day==arg2-week+3)//只能当天签到
				{
					sinalist = sdao.findSinInfo("zhangsan",year+"-"+month+"-"+(arg2-week+3),"0");
					if(sinalist.size()>0)
					{
						Toast.makeText(getApplicationContext(), "已经签过到不能重复签到", Toast.LENGTH_SHORT).show();
					}
					else
					{
						//在数据库插入一条数据
						sdao.insertSinInfo("zhangsan", "张三", year+"-"+month+"-"+(arg2-week+3),year+""+month);
						initData();
					}
				}
			}
		});
	}

	void initData(){
		sinalist = sdao.findSinInfo("zhangsan","",year+""+month);//查询当月已签到的日期
		list.clear();
		//此处是用处：每个月1号如果不是周一的话，若其假设其为周三，就用2个元素占下集合中的前两位，让1号对应到相应周数。
		for (int i = 0; i <week-2; i++) {
			list.add(1+"");
		}
		dayMaxNum = getCurrentMonthDay();
		for(int i=0;i<dayMaxNum;i++)
		{
			list.add(i+1+"");
		}
		gdDate.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gdDate.setAdapter(new getDayNumAdapter(getApplicationContext()));
	}
	class getDayNumAdapter extends BaseAdapter {
		Context c;
		public getDayNumAdapter(Context c)
		{
			this.c = c;
		}
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = LinearLayout.inflate(c, R.layout.date_mb, null);
			TextView txtWeek = (TextView)v.findViewById(R.id.txtWeekDateMB);
			TextView txtDay = (TextView)v.findViewById(R.id.txtDayDateMB);

			switch (position)
			{
				case 0:
					txtWeek.setText("一");
					break;
				case 1:
					txtWeek.setText("二");
					break;
				case 2:
					txtWeek.setText("三");
					break;
				case 3:
					txtWeek.setText("四");
					break;
				case 4:
					txtWeek.setText("五");
					break;
				case 5:
					txtWeek.setText("六");
					break;
				case 6:
					txtWeek.setText("日");
					break;
			}
			if(position<7)
			{
				txtWeek.setVisibility(View.VISIBLE);
			}

			if(position<week-2){
				txtDay.setVisibility(View.GONE);
			}
			int lstDay = Integer.parseInt(list.get(position));

			//标记当前日期
			if(day==lstDay)
			{
				txtDay.setText(list.get(position).toString());
				txtDay.setTextColor(Color.BLUE);
				txtDay.setTypeface(Typeface.DEFAULT_BOLD);
			}
			else
				txtDay.setText(list.get(position).toString());

			//标记已签到后的背景
			for(int i=0;i<sinalist.size();i++)
			{
				String nowdate = sinalist.get(i).get("sindate").toString();
				String[] nowdatearr = nowdate.split("-");
				if(lstDay==Integer.parseInt(nowdatearr[2])){
					txtDay.setBackgroundResource(R.color.red);
					txtDay.setTextColor(Color.WHITE);
				}
			}
			return v;
		}

	}
	//获取当月的 天数
	public static int getCurrentMonthDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
}
