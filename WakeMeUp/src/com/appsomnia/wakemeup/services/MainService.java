package com.appsomnia.wakemeup.services;

import com.appsomnia.wakemeup.data.AlarmData;
import com.appsomnia.wakemeup.data.VipData;
import com.appsomnia.wakemeup.internal.DataHandler;
import com.appsomnia.wakemeup.util.listeners.MyPhoneStateListener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainService extends Service {

	public static final String TAG = MainService.class.getSimpleName();
	
	TelephonyManager mTelMger=null;
	MyPhoneStateListener mPhoneStateListener=null;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG,"In onCreate");
		init();
	}
	
	public void init(){
		mPhoneStateListener = new MyPhoneStateListener(this);
		mTelMger = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		mTelMger.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG,"In onDestroy");
		mTelMger.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG,"In onStartCommand");
		Log.w(TAG,"All the data needed:");
		VipData vp = DataHandler.fetchVipDetails(getApplicationContext());
		Log.w(TAG,"VIP ["+vp.getId()+"] ["+vp.getName()+"] ["+vp.getContactList().size()+"]");
		AlarmData ad = DataHandler.fetchAlarmConfig(getApplicationContext());
		Log.w(TAG,"Alarm Config - Time["+ad.getTime()+"] Snooze["+ad.getSnooze()+"]");
		String alarmTone = ""+DataHandler.fetchAlarmToneDetails(getApplicationContext());
		Log.w(TAG,"Alarm Tone - Path["+alarmTone+"]");
		int occurrence = DataHandler.fetchVipOccurence(getApplicationContext());
		int occurrenceLimit = DataHandler.fetchVipOccurrenceLimit(getApplicationContext());
		long lastVipCall = DataHandler.fetchLastVipCallTime(getApplicationContext());
		Log.w(TAG,"VIP Calls - Calls["+occurrence+"] CallsLimit["+occurrenceLimit+"] CallsLast["+lastVipCall+"]");
		Log.w(TAG,"All the data needed:");
		
		return START_STICKY;
	}
	
	

}
