package com.sunapps.wakemeup.util.listeners;


import com.sunapps.wakemeup.AlarmReceiverActivity;
import com.sunapps.wakemeup.internal.DataHandler;
import com.sunapps.wakemeup.util.Toaster;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class AlarmActivityConfig_StopAlarmButtonListener implements OnClickListener{
	public static String TAG = AlarmActivityConfig_StopAlarmButtonListener.class.getSimpleName();
	
	Activity mCallerActivity;
	
	public AlarmActivityConfig_StopAlarmButtonListener(Activity caller) {
		mCallerActivity = caller;
	}

	@Override
	public void onClick(View view) {
		
		Log.v(TAG,"Stopping Alarm");
		
		stopAlarm();
		
		Log.v(TAG,"Alarm Stopped");
		
	}
	
	private void stopAlarm() {
		Intent i = new Intent(mCallerActivity, AlarmReceiverActivity.class);
		PendingIntent pi = PendingIntent.getActivity(mCallerActivity, 2, i, PendingIntent.FLAG_CANCEL_CURRENT);
		Log.v(TAG,"createAlarm: cancelling pending intent]");
		AlarmManager am = (AlarmManager)mCallerActivity.getSystemService(Context.ALARM_SERVICE);
		am.cancel(pi);
		//reset phone state listener
		DataHandler.resetState(mCallerActivity);
		Toaster.print(mCallerActivity, "Alarm has been cancelled");
	}

}
