package com.example.administrator.taoyuan.activity_home;

import com.example.administrator.taoyuan.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class QiandaoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new HuaCalendarView(QiandaoActivity.this,mClickSignInCallBack));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private CalendarViewAdapter.onClickSignInCallBack mClickSignInCallBack = new CalendarViewAdapter.onClickSignInCallBack() {
		
		@Override
		public void onTimeOut(String msg) {
			
		}
		
		@Override
		public void onSucess() {
			Toast.makeText(QiandaoActivity.this,"签到成功", Toast.LENGTH_LONG).show();
		}
		
		@Override
		public void onFail(String error) {
			
		}
	};
}
