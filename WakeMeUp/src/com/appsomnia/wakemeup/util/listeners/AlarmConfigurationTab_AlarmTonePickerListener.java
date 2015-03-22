package com.appsomnia.wakemeup.util.listeners;

import android.app.Activity;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class AlarmConfigurationTab_AlarmTonePickerListener implements OnPreferenceChangeListener{
	
	public static String TAG = AlarmConfigurationTab_AlarmTonePickerListener.class.getSimpleName();
	
	Activity mCallerActivity;
	
	public AlarmConfigurationTab_AlarmTonePickerListener(Activity caller) {
		mCallerActivity = caller;
	}
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		String tonePath = ""+newValue;
		RingtoneManager.setActualDefaultRingtoneUri(mCallerActivity, RingtoneManager.TYPE_ALARM, Uri.parse(tonePath));
		//DataHandler.saveAlarmToneDetails(mCallerActivity, Uri.parse(tonePath));
		return true;
	}

}
