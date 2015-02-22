package com.sunapps.wakemeup.util.external;

import com.sunapps.wakemeup.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;

public class TelephoneCaller {
	public static String TAG = MainActivity.class.getSimpleName();
	public static final int PICK_CONTACT = 301;
	
	public static void callContactIntent(Activity activity){
		Log.v(TAG, "In callContactIntent");
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		activity.startActivityForResult(intent, PICK_CONTACT);
	}
	

	
}
