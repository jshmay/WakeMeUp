package com.sunapps.wakemeup;

import com.sunapps.wakemeup.util.listeners.MainActivity_StartButtonListener;
import com.sunapps.wakemeup.util.listeners.MainActivity_StopButtonListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity{
	public static String TAG = MainActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);
		init();
	}
	
	private void init() {
		Button strButton = (Button)findViewById(R.id.main_activity__button__start);
		strButton.setOnClickListener(new MainActivity_StartButtonListener((Activity)this));
		Button stpButton = (Button)findViewById(R.id.main_activity__button__stop);
		stpButton.setOnClickListener(new MainActivity_StopButtonListener((Activity)this));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
