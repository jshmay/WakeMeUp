package com.sunapps.wakemeup;

import java.io.IOException;

import com.sunapps.wakemeup.util.external.TelephoneCaller;
import com.sunapps.wakemeup.util.listeners.AlarmActivity_OkButtonListener;
import com.sunapps.wakemeup.util.listeners.AlarmActivity_SnoozeButtonListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
	public class AlarmReceiverActivity extends Activity{
	public static final String TAG=AlarmReceiverActivity.class.getSimpleName();
	
	MediaPlayer mMediaPlayer;
	PowerManager.WakeLock mWakeLock;
	Vibrator mVibrator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "In onCreate");
		init();
		playSound(this,getAlarmUri());
		vibrate(this);
	}
	private void vibrate(Context context) {
		long[] pattern = {0, 500, 1000};

		if(mVibrator.hasVibrator())
			mVibrator.vibrate(pattern,0);
	}
	public static Uri getAlarmUri() {
		
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if(alert==null)
		alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		if(alert==null)
		alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		
		return alert;
	}
	private void playSound(Context context,	Uri alertUri) {
		Log.v(TAG, "In playSound");
		try {
			mMediaPlayer.setDataSource(context, alertUri);
			
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			Log.v(TAG, "Sound Started");
			
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "Could not load sound file");
		}
	
	}
	
	private void init() {
		Log.v(TAG, "Started Init");
		PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Wake Lock");
		mWakeLock.acquire();
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN|
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
				WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN|
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
				WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		setContentView(R.layout.alam_activity_layout);
		
		mMediaPlayer = new MediaPlayer();
		mVibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
		
		Button okButton = (Button)findViewById(R.id.alarm_activity__button__ok);
		okButton.setOnClickListener(new AlarmActivity_OkButtonListener(this,mMediaPlayer,mVibrator));
		Button snoozeButton = (Button)findViewById(R.id.alarm_activity__button__snooze);
		snoozeButton.setOnClickListener(new AlarmActivity_SnoozeButtonListener(this,mMediaPlayer,mVibrator));
	}
	@Override
	protected void onDestroy() {
		Log.v(TAG,"Destroying");
		super.onDestroy();
		mMediaPlayer.release();
		if(mVibrator.hasVibrator())
			mVibrator.cancel();
	}
	
	@Override
		protected void onPause() {
			super.onPause();
			if(mWakeLock.isHeld())
				mWakeLock.release();
		}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

	    if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
	    	Log.v(TAG,"Hit Volume Key");
	    	if(mMediaPlayer.isPlaying())
	    		mMediaPlayer.pause();
			if(mVibrator.hasVibrator())
				mVibrator.cancel();
			
			return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	
	
	

}
