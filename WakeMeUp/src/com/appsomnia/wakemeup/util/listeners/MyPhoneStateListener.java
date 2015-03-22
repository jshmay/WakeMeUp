package com.appsomnia.wakemeup.util.listeners;

import com.appsomnia.wakemeup.AlarmReceiverActivity;
import com.appsomnia.wakemeup.data.AlarmData;
import com.appsomnia.wakemeup.data.VipData;
import com.appsomnia.wakemeup.internal.DataHandler;
import com.appsomnia.wakemeup.util.Toaster;
import com.appsomnia.wakemeup.util.Validator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyPhoneStateListener extends PhoneStateListener{
	public static String TAG=MyPhoneStateListener.class.getSimpleName();
	
	boolean mRing=false;
	boolean mAnswered=false;
	
	long ringStart=0;
	
	String mCallerPhoneNumber="";
	
	int missedCallsFromVip=0;
	
	Context mContext;
	
	public MyPhoneStateListener (Context context){
		resetRingData();
		mContext=context;
	}
	
	public void resetRingData(){
		mRing=false;
		mAnswered=false;
		ringStart=0;
		Log.d(TAG,"ring data reset");
	}
	
	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		
		VipData vip=DataHandler.fetchVipDetails(mContext);
		int npOccurrence = DataHandler.fetchVipOccurrenceLimit(mContext);
		
        if(state==TelephonyManager.CALL_STATE_RINGING)
        {
        	Log.d(TAG,"In Ring State");
        	ringStart=System.currentTimeMillis();
            mRing=true;
            mCallerPhoneNumber= incomingNumber;
            Log.w(TAG,"Got number test ["+mCallerPhoneNumber+"]");
        }
        else if(state==TelephonyManager.CALL_STATE_OFFHOOK)
        {
        	if(Validator.isValidVipContactNumber(mCallerPhoneNumber, vip.getContactList())){
        		Log.w(TAG,"Phone call came from VIP and has been answered");
        	}
        	mRing=false;
        	mAnswered=true;
        }
        else if (state==TelephonyManager.CALL_STATE_IDLE)
        {
        	Log.d(TAG,"In Idle State");
        	// If phone was ringing(ring=true) and not received(callReceived=false) , then it is a missed call
        	if(mRing&&!mAnswered&&Validator.isValidVipContactNumber(mCallerPhoneNumber, vip.getContactList()))
        	{

        		if(System.currentTimeMillis()-DataHandler.fetchLastVipCallTime(mContext)>300000)
        		{
        			missedCallsFromVip=0;
        			DataHandler.saveVipOccurence(mContext, missedCallsFromVip);
        			Log.w(TAG,"Passed 5 minutes limit");
        		}
        		
        		DataHandler.saveLastVipCallTime(mContext,System.currentTimeMillis());
        		
        		missedCallsFromVip=DataHandler.incrementVipOccurence(mContext);
        		
        		if(missedCallsFromVip>=npOccurrence){
        			Log.w(TAG,"VIP Missed Calls reached limit of ["+missedCallsFromVip+"] initiate emergency alarm");
        			//StartAlarm
        			createAlarm();
        			missedCallsFromVip=0;
        			DataHandler.saveVipOccurence(mContext, missedCallsFromVip);
        		}else{
            		Log.w(TAG,"It was A VIP MISSED CALL from : ["+mCallerPhoneNumber+"] It happened ["+missedCallsFromVip+"] time(s)");
            		//Toaster.print(mContext,"It was A VIP MISSED CALL from : ["+mCallerPhoneNumber+"] It happened ["+missedCallsFromVip+"] time(s)");              			
        		}
        		
        		DataHandler.saveLastVipCallTime(mContext,System.currentTimeMillis());
    	
        	}else if(mRing&&!mAnswered){
        		Log.w(TAG,"It was A normal missed CALL from : ["+mCallerPhoneNumber+"]");
        		//Toaster.print(mContext,"It was A normal missed CALL from : ["+mCallerPhoneNumber+"]");
        	}else if(!mRing&&mAnswered&&Validator.isValidVipContactNumber(mCallerPhoneNumber, vip.getContactList())){
        		Log.w(TAG,"You answered CALL from : ["+mCallerPhoneNumber+"]");
        		missedCallsFromVip=0;
        		DataHandler.saveVipOccurence(mContext, missedCallsFromVip);
        		//Toaster.print(mContext,"You answered VIP CALL from : ["+mCallerPhoneNumber+"]");
        	}else if(!mRing&&mAnswered){
        		Log.w(TAG,"You answered normal CALL from : ["+mCallerPhoneNumber+"]");
        		//Toaster.print(mContext,"You answered CALL from : ["+mCallerPhoneNumber+"]");
        	}

        	mRing=false;
        	mAnswered=false;
        }
	}

	private void createAlarm() {
		Log.v(TAG, "Setting Alarm");
		AlarmData ad = DataHandler.fetchAlarmConfig(mContext);
		int alarmStartTime = ad.getTime();
		int alarmStartTimeNumb = alarmStartTime;
		
		Intent i = new Intent(mContext, AlarmReceiverActivity.class);
		PendingIntent pi = PendingIntent.getActivity(mContext, 2, i, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager am = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(1000*alarmStartTimeNumb*60),pi);
		Toaster.print(mContext, "Alarm has been set");
	}
	
}
