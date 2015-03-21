package com.sunapps.wakemeup.util.listeners;

import com.sunapps.wakemeup.AlarmReceiverActivity;
import com.sunapps.wakemeup.internal.DataHandler;
import com.sunapps.wakemeup.services.MainService;
import com.sunapps.wakemeup.util.Toaster;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.util.Log;

public class AlarmConfigurationTab_ToggleServiceListener implements OnPreferenceChangeListener{
	
	public static String TAG = AlarmConfigurationTab_ToggleServiceListener.class.getSimpleName();
	
	Activity mCallerActivity;
	
	public AlarmConfigurationTab_ToggleServiceListener(Activity caller) {
		mCallerActivity = caller;
	}
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		boolean switched = ((SwitchPreference) preference).isChecked();
		if(!switched){
			mCallerActivity.startService(new Intent(mCallerActivity.getBaseContext(), MainService.class));
			Toaster.print(mCallerActivity, "Service Started");
		}
		else{
			stopAlarm();
			mCallerActivity.stopService(new Intent(mCallerActivity.getBaseContext(), MainService.class));
			Toaster.print(mCallerActivity, "Service Stopped");
		}
			
		return true;
	}
	
	private void stopAlarm() {
		Intent i = new Intent(mCallerActivity, AlarmReceiverActivity.class);
		PendingIntent pi = PendingIntent.getActivity(mCallerActivity, 2, i, PendingIntent.FLAG_CANCEL_CURRENT);
		Log.v(TAG,"createAlarm: cancelling pending intent]");
		AlarmManager am = (AlarmManager)mCallerActivity.getSystemService(Context.ALARM_SERVICE);
		am.cancel(pi);
		//reset phone state listener
		DataHandler.resetState(mCallerActivity);
		//Toaster.print(mCallerActivity, "Alarm has been cancelled");
		Log.v(TAG,"createAlarm: Alarm has been cancelled");
	}

}
