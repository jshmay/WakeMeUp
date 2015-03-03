package com.sunapps.wakemeup.internal;

import java.util.HashSet;
import java.util.Set;

import com.sunapps.wakemeup.AlarmReceiverActivity;
import com.sunapps.wakemeup.MainActivity;
import com.sunapps.wakemeup.R;
import com.sunapps.wakemeup.data.AlarmData;
import com.sunapps.wakemeup.data.VipData;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class DataHandler {
	public static String TAG = MainActivity.class.getSimpleName();
	public static Uri fetchAlarmToneDetailsTemp(Context context) {
		
		String uriStr;
		
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences),Context.MODE_PRIVATE);
		
		if(sharedPref==null)
			return AlarmReceiverActivity.getAlarmUri();
		
		uriStr = sharedPref.getString(context.getString(R.string.alarm_preferences__alarmToneTemp),  "" );
		
		if(uriStr.equals(""))
			return AlarmReceiverActivity.getAlarmUri();
		
		return Uri.parse(uriStr);
	}
	

	public static Uri fetchAlarmToneDetails(Context context) {
		
		String uriStr;
		
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences),Context.MODE_PRIVATE);
		
		if(sharedPref==null)
			return AlarmReceiverActivity.getAlarmUri();
		
		uriStr = sharedPref.getString(context.getString(R.string.alarm_preferences__alarmTone), "");
		
		if(uriStr.equals(""))
			return AlarmReceiverActivity.getAlarmUri();
		
		return Uri.parse(uriStr);
	}
	
	public static VipData fetchVipDetails(Context context)
	{
		String id;
		String name;
		Set<String> contactList;
		
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences),Context.MODE_PRIVATE);
		
		if(sharedPref==null)
			return new VipData("","",new HashSet<String>());
		
		id = sharedPref.getString(context.getString(R.string.alarm_preferences__vip_id), "");
		name = sharedPref.getString(context.getString(R.string.alarm_preferences__vip_name), "");
		contactList =sharedPref.getStringSet(context.getString(R.string.alarm_preferences__vip_contactList), new HashSet<String>());
		
		return new VipData(name, id, contactList);
	}
	
	public static VipData fetchVipDetailsTemp(Context context)
	{
		String id;
		String name;
		Set<String> contactList;
		
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences),Context.MODE_PRIVATE);
		
		if(sharedPref==null)
			return new VipData("","",new HashSet<String>());
		
		id = sharedPref.getString(context.getString(R.string.alarm_preferences__vip_idTemp), "");
		name = sharedPref.getString(context.getString(R.string.alarm_preferences__vip_nameTemp), "");
		contactList =sharedPref.getStringSet(context.getString(R.string.alarm_preferences__vip_contactListTemp), new HashSet<String>());
		
		return new VipData(name, id, contactList);
	}	
	
	public static AlarmData fetchAlarmConfig(Context context)
	{
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences),Context.MODE_PRIVATE);
		String alarmTime;
		String alarmSnooze;
		
		if(sharedPref==null)
			return new AlarmData("1", "1");
		
		alarmTime = sharedPref.getString(context.getString(R.string.alarm_preferences__time), "1");
		alarmSnooze = sharedPref.getString(context.getString(R.string.alarm_preferences__snooze), "1");
		
		return new AlarmData(alarmTime,alarmSnooze);
	}
	
	public static String fetchVipPhoneNumber(Context context)
	{
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences),Context.MODE_PRIVATE);
		String vipNumber;
		
		if(sharedPref==null)
			return "";
		
		vipNumber = sharedPref.getString(context.getString(R.string.alarm_preferences__vip), "");
		
		return vipNumber.trim();
	}

	public static int fetchVipOccurrenceLimit(Context context)
	{
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences),Context.MODE_PRIVATE);
		int vipNumber=2;
		
		if(sharedPref==null)
			return 2;
		
		vipNumber = sharedPref.getInt(context.getString(R.string.alarm_preferences__vipOccurrenceLimit), 2);
		if(vipNumber<=0)
			vipNumber=2;
		
		return vipNumber;
	}
	
	public static int fetchVipOccurence(Context context)
	{
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences),Context.MODE_PRIVATE);
		int vipOccurrence;
		
		if(sharedPref==null)
			return 0;
		
		vipOccurrence = sharedPref.getInt(context.getString(R.string.alarm_preferences__vipOccurrence), 0);
		
		return vipOccurrence;
	}

	public static int incrementVipOccurence(Context context)
	{
		int vipOccurrence=fetchVipOccurence(context);
		saveVipOccurence(context, ++vipOccurrence);
		
		return vipOccurrence;
	}

	public static int decrementVipOccurence(Context context)
	{
		int vipOccurrence=fetchVipOccurence(context);
		saveVipOccurence(context, --vipOccurrence);
		
		return vipOccurrence;
	}
	
	public static boolean saveVipOccurence(Context context, int occurrence)
	{
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(context.getString(R.string.alarm_preferences__vipOccurrence), occurrence);
		editor.commit();
		
		return true;
	}
	
	
	public static boolean saveVipDetailsTemp(Context context, VipData vip)
	{
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(context.getString(R.string.alarm_preferences__vip_nameTemp), vip.getName());
		editor.putString(context.getString(R.string.alarm_preferences__vip_idTemp), vip.getId());
		editor.putStringSet(context.getString(R.string.alarm_preferences__vip_contactListTemp), vip.getContactList());
		editor.commit();
		
		return true;
	}
	
	public static boolean saveVipDetails(Context context, VipData vip)
	{
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(context.getString(R.string.alarm_preferences__vip_name), vip.getName());
		editor.putString(context.getString(R.string.alarm_preferences__vip_id), vip.getId());
		editor.putStringSet(context.getString(R.string.alarm_preferences__vip_contactList), vip.getContactList());
		editor.commit();
		
		return true;
	}
	
	public static boolean saveAlarmDataAndVip(Context context, AlarmData alarmData, String vip)
	{
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(context.getString(R.string.alarm_preferences__time), alarmData.getTime());
		editor.putString(context.getString(R.string.alarm_preferences__snooze), alarmData.getSnooze());
		editor.putString(context.getString(R.string.alarm_preferences__vip), vip);
		editor.commit();
		
		//Reset Phone State Listener
		DataHandler.resetState(context);
		
		return true;
	}
	
	public static boolean saveAlarmDataAndVip(Context context, AlarmData alarmData, VipData vip, int npOccurrenceNum, Uri alarmTone)
	{
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(context.getString(R.string.alarm_preferences__time), alarmData.getTime());
		editor.putString(context.getString(R.string.alarm_preferences__snooze), alarmData.getSnooze());
		editor.putString(context.getString(R.string.alarm_preferences__vip_name), vip.getName());
		editor.putString(context.getString(R.string.alarm_preferences__vip_id), vip.getId());
		editor.putStringSet(context.getString(R.string.alarm_preferences__vip_contactList), vip.getContactList());
		editor.putInt(context.getString(R.string.alarm_preferences__vipOccurrenceLimit), npOccurrenceNum);
		
		RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM, alarmTone);
		
		editor.commit();
		
		//Reset Phone State Listener
		DataHandler.resetState(context);
		
		
		return true;
	}

	public static void resetState(Context context) {
		saveVipOccurence(context, 0);
		saveLastVipCallTime(context, 0);
	}


	public static boolean saveAlarmToneDetailsTemp(Context context,
			Uri uri) {
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.putString(context.getString(R.string.alarm_preferences__alarmToneTemp), uri.toString());
		
		editor.commit();
		
		return true;
	}


	public static long fetchLastVipCallTime(Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences),Context.MODE_PRIVATE);
		long vipCallTime;
		
		if(sharedPref==null)
			return 0;
		
		vipCallTime = sharedPref.getLong(context.getString(R.string.alarm_preferences__vip_callTime), 0);
		
		return vipCallTime;
	}


	public static boolean saveLastVipCallTime(Context context,
			long currentTimeMillis) {
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.putLong(context.getString(R.string.alarm_preferences__vip_callTime), currentTimeMillis);
		
		editor.commit();
		
		return true;
	}
}
