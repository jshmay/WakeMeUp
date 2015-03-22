package com.appsomnia.wakemeup.util.listeners;


import com.appsomnia.wakemeup.services.MainService;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity_StopButtonListener implements OnClickListener{
	public static String TAG = MainActivity_StopButtonListener.class.getSimpleName();
	
	Activity mCallerActivity;
	
	public MainActivity_StopButtonListener(Activity caller) {
		mCallerActivity = caller;
	}

	@Override
	public void onClick(View v) {
		Log.w(TAG, "Stopping Service");
		mCallerActivity.stopService(new Intent(mCallerActivity.getBaseContext(), MainService.class));
	}

}
