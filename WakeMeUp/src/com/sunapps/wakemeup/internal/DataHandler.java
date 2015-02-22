package com.sunapps.wakemeup.internal;

import java.util.Set;

import com.sunapps.wakemeup.R;
import com.sunapps.wakemeup.data.AlarmData;

import android.content.Context;
import android.content.SharedPreferences;

public class DataHandler {
	
	public static AlarmData fetchAlarmConfig(Context context)
	{
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences),Context.MODE_PRIVATE);
		String alarmTime;
		String alarmSnooze;
		
		if(sharedPref==null)
			return new AlarmData("1", "1");
		
		alarmTime = sharedPref.getString(context.getString(R.string.alarm_preferences__time), "0");
		alarmSnooze = sharedPref.getString(context.getString(R.string.alarm_preferences__snooze), "0");
		
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
	
	public static boolean saveAlarmDataAndVip(Context context, AlarmData alarmData, String vip)
	{
		SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.alarm_preferences), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(context.getString(R.string.alarm_preferences__time), alarmData.getTime());
		editor.putString(context.getString(R.string.alarm_preferences__snooze), alarmData.getSnooze());
		editor.putString(context.getString(R.string.alarm_preferences__vip), vip);
		editor.commit();
		
		//Reset Phone State Listener
		DataHandler.saveVipOccurence(context, 0);
		
		return true;
	}
}
