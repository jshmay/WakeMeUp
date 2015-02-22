package com.sunapps.wakemeup.util.listeners;

import com.sunapps.wakemeup.MainActivity;
import com.sunapps.wakemeup.util.external.TelephoneCaller;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class AlarmConfigurationTab_VipEditTextListener implements OnTouchListener {
	public static String TAG = MainActivity.class.getSimpleName();
	Activity mCallerActivity;
	
	public AlarmConfigurationTab_VipEditTextListener(Activity callerActivity){
		mCallerActivity=callerActivity;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.v(TAG, "On Touch Called");
		 if (event.getAction() == MotionEvent.ACTION_DOWN)
		 {
			 TelephoneCaller.callContactIntent(mCallerActivity);
		 }
		return false;
	}
	
}
