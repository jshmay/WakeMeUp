package com.appsomnia.wakemeup.util.listeners;


import com.appsomnia.wakemeup.services.MainService;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity_StartButtonListener implements OnClickListener{
	public static String TAG = MainActivity_StartButtonListener.class.getSimpleName();
	
	Activity mCallerActivity;
	
	public MainActivity_StartButtonListener(Activity caller) {
		mCallerActivity = caller;
	}

	@Override
	public void onClick(View v) {
		Log.w(TAG, "Starting Service");
		mCallerActivity.startService(new Intent(mCallerActivity.getBaseContext(), MainService.class));
	}

}
