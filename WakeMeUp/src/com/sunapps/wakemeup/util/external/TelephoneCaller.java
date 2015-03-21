package com.sunapps.wakemeup.util.external;

import com.sunapps.wakemeup.MainActivity;
import com.sunapps.wakemeup.data.CallData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.provider.CallLog;
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
	
	public static boolean isRejectedCall(CallData lastCallInfo) {
		Log.w(TAG,"lastCallInfo ["+lastCallInfo.getPhoneNumber()+"]  ["+(lastCallInfo.getCalltype()==CallLog.Calls.INCOMING_TYPE)+"]  ["+lastCallInfo.getCallDuration()+"]");
		
		if(lastCallInfo.getCalltype()==CallLog.Calls.INCOMING_TYPE && lastCallInfo.getCallDuration()==0){
			return true;
		}
		return false;
	}
	
	public static CallData getLastCallInfo(Context context){
		Cursor managedCursor = context.getContentResolver().query( CallLog.Calls.CONTENT_URI,null, null,null, null);
		int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
		int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
		int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
		
		String phNumber="";
		String callType="";
		String callDuration="";
		int dircode=0;
		int callDurationNumber=0;
		
		if(managedCursor.moveToLast()){
			
			phNumber = managedCursor.getString( number );
			callType = managedCursor.getString( type );
			callDuration = managedCursor.getString( duration );
			
			dircode = Integer.parseInt( callType );
			
			if(callDuration!=null){
				if(callDuration.trim().length()!=0)
				{
					callDurationNumber = Integer.parseInt(callDuration.trim());
				}
			}
		}
		
		managedCursor.close();
		
		return new CallData(phNumber,dircode,callDurationNumber);
	}
	
	
}
