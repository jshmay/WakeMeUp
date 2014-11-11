package com.sunapps.wakemeup.services;

import com.sunapps.wakemeup.util.Toaster;
import com.sunapps.wakemeup.util.listeners.MyPhoneStateListener;

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
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG,"In onCreate");
		init();
	}
	
	public void init(){
		mPhoneStateListener = new MyPhoneStateListener(this, "01286689");
		mTelMger = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		mTelMger.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG,"In onDestroy");
		Toaster.print(this, "Received StopCMD");
		mTelMger.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG,"In onStartCommand");
		Toaster.print(this, "Received OnStartCMD");
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	

}
