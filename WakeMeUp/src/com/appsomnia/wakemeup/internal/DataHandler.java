package com.appsomnia.wakemeup.internal;

import java.util.HashSet;
import java.util.Set;

import com.appsomnia.wakemeup.AlarmReceiverActivity;
import com.appsomnia.wakemeup.MainActivity;
import com.appsomnia.wakemeup.R;
import com.appsomnia.wakemeup.data.AlarmData;
import com.appsomnia.wakemeup.data.VipData;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;

public class DataHandler {
	public static String TAG = MainActivity.class.getSimpleName();
	public static Uri fetchAlarmToneDetailsTemp(Context context) {
		
		String uriStr;
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		
		if(sharedPref==null)
			return AlarmReceiverActivity.getAlarmUri();
		
		uriStr = sharedPref.getString(context.getString(R.string.alarm_preferences__alarmToneTemp),  "" );
		
		if(uriStr.equals(""))
			return AlarmReceiverActivity.getAlarmUri();
		
		return Uri.parse(uriStr);
	}
	

	public static Uri fetchAlarmToneDetails(Context context) {
		
		String uriStr;
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		
		if(sharedPref==null)
			return Uri.parse("");
		
		uriStr = sharedPref.getString(context.getString(R.string.alarm_preferences__alarmTone), "");
		
		return Uri.parse(uriStr);
	}
	
	public static VipData fetchVipDetails(Context context)
	{
		String id;
		String name;
		Set<String> contactList;
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		
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
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		
		if(sharedPref==null)
			return new VipData("","",new HashSet<String>());
		
		id = sharedPref.getString(context.getString(R.string.alarm_preferences__vip_idTemp), "");
		name = sharedPref.getString(context.getString(R.string.alarm_preferences__vip_nameTemp), "");
		contactList =sharedPref.getStringSet(context.getString(R.string.alarm_preferences__vip_contactListTemp), new HashSet<String>());
		
		return new VipData(name, id, contactList);
	}	
	
	public static AlarmData fetchAlarmConfig(Context context)
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		int alarmTime;
		int alarmSnooze;
		
		if(sharedPref==null)
			return new AlarmData(1, 1);
		
		alarmTime = sharedPref.getInt(context.getString(R.string.alarm_preferences__time), 1);
		alarmSnooze = sharedPref.getInt(context.getString(R.string.alarm_preferences__snooze), 1);
		
		return new AlarmData(alarmTime,alarmSnooze);
	}
	
	public static String fetchVipPhoneNumber(Context context)
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		String vipNumber;
		
		if(sharedPref==null)
			return "";
		
		vipNumber = sharedPref.getString(context.getString(R.string.alarm_preferences__vip), "");
		
		return vipNumber.trim();
	}

	public static int fetchVersionNumber(Context context)
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		int versionNumber=0;
		
		if(sharedPref==null)
			return 0;
		
		versionNumber = sharedPref.getInt(context.getString(R.string.version_number),0);
		return versionNumber;
	}
	
	public static int fetchVipOccurrenceLimit(Context context)
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
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
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
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
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(context.getString(R.string.alarm_preferences__vipOccurrence), occurrence);
		editor.commit();
		
		return true;
	}
	/**
	 * Sets temporary data to concrete data for vip details
	 * @param context
	 * @return
	 */
	public static boolean confirmVipDetails(Context context)
	{
		VipData vp = fetchVipDetailsTemp(context);
		saveVipDetails(context, vp);
		
		return true;
	}	
	
	public static boolean saveVipDetailsTemp(Context context, VipData vip)
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(context.getString(R.string.alarm_preferences__vip_nameTemp), vip.getName());
		editor.putString(context.getString(R.string.alarm_preferences__vip_idTemp), vip.getId());
		editor.putStringSet(context.getString(R.string.alarm_preferences__vip_contactListTemp), vip.getContactList());
		
		editor.commit();
		
		return true;
	}
	
	public static boolean saveVipDetails(Context context, VipData vip)
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(context.getString(R.string.alarm_preferences__vip_name), vip.getName());
		editor.putString(context.getString(R.string.alarm_preferences__vip_id), vip.getId());
		editor.putStringSet(context.getString(R.string.alarm_preferences__vip_contactList), vip.getContactList());
		editor.commit();
		
		return true;
	}
	
	public static boolean saveAlarmDataAndVip(Context context, AlarmData alarmData, String vip)
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(context.getString(R.string.alarm_preferences__time), alarmData.getTime());
		editor.putInt(context.getString(R.string.alarm_preferences__snooze), alarmData.getSnooze());
		editor.putString(context.getString(R.string.alarm_preferences__vip), vip);
		editor.commit();
		
		//Reset Phone State Listener
		DataHandler.resetState(context);
		
		return true;
	}
	
	public static boolean saveAlarmDataAndVip(Context context, AlarmData alarmData, VipData vip, int npOccurrenceNum, Uri alarmTone)
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(context.getString(R.string.alarm_preferences__time), alarmData.getTime());
		editor.putInt(context.getString(R.string.alarm_preferences__snooze), alarmData.getSnooze());
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
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.putString(context.getString(R.string.alarm_preferences__alarmToneTemp), uri.toString());
		
		editor.commit();
		
		return true;
	}


	public static long fetchLastVipCallTime(Context context) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		long vipCallTime;
		
		if(sharedPref==null)
			return 0;
		
		vipCallTime = sharedPref.getLong(context.getString(R.string.alarm_preferences__vip_callTime), 0);
		
		return vipCallTime;
	}


	public static boolean saveLastVipCallTime(Context context,
			long currentTimeMillis) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.putLong(context.getString(R.string.alarm_preferences__vip_callTime), currentTimeMillis);
		
		editor.commit();
		
		return true;
	}


	public static void clearData(Context context) {
		DataHandler.resetState(context);
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.remove(context.getString(R.string.alarm_preferences__toggleService)).commit();
		editor.remove(context.getString(R.string.is_fresh)).commit();
	}


	public static boolean saveCurrentVersionNumber(Context context,
			int version) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.putInt(context.getString(R.string.version_number), version);
		editor.commit();
		
		return true;		
	}


	public static boolean saveIsNotNewlyInstalled(Context context) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.putBoolean(context.getString(R.string.is_fresh), false);
		editor.commit();
		
		return true;	
	}
	
	public static boolean fetchIsFreshInstallation(Context context) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		boolean isFresh;
		
		if(sharedPref==null)
			return true;
		
		isFresh = sharedPref.getBoolean(context.getString(R.string.is_fresh), true);
		
		return isFresh;
	}
	
}
