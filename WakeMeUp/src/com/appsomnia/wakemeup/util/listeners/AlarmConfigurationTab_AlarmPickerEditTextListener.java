package com.appsomnia.wakemeup.util.listeners;

import com.appsomnia.wakemeup.MainActivity;
import com.appsomnia.wakemeup.util.external.TelephoneCaller;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class AlarmConfigurationTab_AlarmPickerEditTextListener implements OnTouchListener {
	public static String TAG = MainActivity.class.getSimpleName();
	Activity mCallerActivity;
	
	public AlarmConfigurationTab_AlarmPickerEditTextListener(Activity callerActivity){
		mCallerActivity=callerActivity;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.v(TAG, "On Touch Called");
		 if (event.getAction() == MotionEvent.ACTION_DOWN)
		 {
			 TelephoneCaller.callAlarmPickerIntent(mCallerActivity);
		 }
		return false;
	}
	
}
