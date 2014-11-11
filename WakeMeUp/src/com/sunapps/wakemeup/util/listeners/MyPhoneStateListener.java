package com.sunapps.wakemeup.util.listeners;

import com.sunapps.wakemeup.util.Toaster;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyPhoneStateListener extends PhoneStateListener{
	public static String TAG=MyPhoneStateListener.class.getSimpleName();
	
	public static String mVipPhoneNumber="";
	
	boolean mRing=false;
	boolean mAnswered=false;
	
	long ringStart=0;
	
	String mCallerPhoneNumber="";
	
	int missedCallsFromVip=0;
	
	Context mContext;
	
	public MyPhoneStateListener (Context context,String vipPhoneNumber){
		mRing=false;
		mAnswered=false;
		ringStart=0;
		mContext=context;
		mVipPhoneNumber=vipPhoneNumber;
	}
	
	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
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
        	if(mCallerPhoneNumber.equals(mVipPhoneNumber)){
        		Log.w(TAG,"Phone call came from VIP and has been answered");
        	}
        	mRing=false;
        	mAnswered=true;
        }
        else if (state==TelephonyManager.CALL_STATE_IDLE)
        {
        	Log.d(TAG,"In Idle State");
        	// If phone was ringing(ring=true) and not received(callReceived=false) , then it is a missed call
        	if(mRing&&!mAnswered&&mCallerPhoneNumber.equals(mVipPhoneNumber))
        	{
            	long totalRingTime=System.currentTimeMillis()-ringStart;
            	
            	if(totalRingTime<40000){
            		//You rejected the call
            		Log.w(TAG,"Manual VIP rejection for : ["+mCallerPhoneNumber+"]");
            		missedCallsFromVip=0;
            	}else{
            		missedCallsFromVip++;
            		if(missedCallsFromVip>3){
            			//StartAlarm
            			missedCallsFromVip=0;
            		}
            		Log.w(TAG,"It was A VIP MISSED CALL from : ["+mCallerPhoneNumber+"] It happened ["+missedCallsFromVip+"] time(s)");
            		Toaster.print(mContext,"It was A VIP MISSED CALL from : ["+mCallerPhoneNumber+"] It happened ["+missedCallsFromVip+"] time(s)");           		
            	}
        	}else if(mRing&&!mAnswered){
        		Log.w(TAG,"It was A normal missed CALL from : ["+mCallerPhoneNumber+"]");
        		Toaster.print(mContext,"It was A normal missed CALL from : ["+mCallerPhoneNumber+"]");
        	}else if(!mRing&&mAnswered&&mCallerPhoneNumber.equals(mVipPhoneNumber)){
        		Log.w(TAG,"You answered CALL from : ["+mCallerPhoneNumber+"]");
        		missedCallsFromVip=0;
        		Toaster.print(mContext,"You answered VIP CALL from : ["+mCallerPhoneNumber+"]");
        	}else if(!mRing&&mAnswered){
        		Log.w(TAG,"You answered normal CALL from : ["+mCallerPhoneNumber+"]");
        		Toaster.print(mContext,"You answered CALL from : ["+mCallerPhoneNumber+"]");
        	}

        	mRing=false;
        	mAnswered=false;
        }
		
	}

}
