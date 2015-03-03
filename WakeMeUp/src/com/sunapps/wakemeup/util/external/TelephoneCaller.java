package com.sunapps.wakemeup.util.external;

import com.sunapps.wakemeup.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.provider.ContactsContract;
import android.util.Log;

public class TelephoneCaller {
	public static String TAG = MainActivity.class.getSimpleName();
	public static final int PICK_CONTACT = 301;
	public static final int PICK_ALARM = 302;
	
	public static void callContactIntent(Activity activity){
		Log.v(TAG, "In callContactIntent");
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		activity.startActivityForResult(intent, PICK_CONTACT);
	}

	public static void callAlarmPickerIntent(Activity activity) {
		Log.v(TAG, "In callAlarmPickerIntent");
		
		Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        
        activity.startActivityForResult(intent, PICK_ALARM);		
	}
	

	
}
