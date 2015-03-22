package com.appsomnia.wakemeup.util.listeners;


import com.appsomnia.wakemeup.AlarmReceiverActivity;
import com.appsomnia.wakemeup.data.AlarmData;
import com.appsomnia.wakemeup.internal.DataHandler;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;

public class AlarmActivity_SnoozeButtonListener implements OnClickListener{
	public static String TAG = AlarmActivity_SnoozeButtonListener.class.getSimpleName();
	
	Activity mCallerActivity;
	MediaPlayer mMediaPlayer;
	Vibrator mVibrator;
	
	public AlarmActivity_SnoozeButtonListener(Activity caller, MediaPlayer mediaPlayer, Vibrator vibrator) {
		mCallerActivity = caller;
		mMediaPlayer=mediaPlayer;
		mVibrator = vibrator;
	}

	@Override
	public void onClick(View view) {
		if(mMediaPlayer.isPlaying())
			mMediaPlayer.stop();
		if(mVibrator.hasVibrator())
			mVibrator.cancel();
		
		snoozeAlarm();
		
		mCallerActivity.finish();
	}
	
	private void snoozeAlarm() {
		AlarmData ad = DataHandler.fetchAlarmConfig(mCallerActivity);
		int snoozeStartTime = ad.getSnooze();
		int snoozeStartTimeNumb = snoozeStartTime;
		
		Intent i = new Intent(mCallerActivity, AlarmReceiverActivity.class);
		PendingIntent pi = PendingIntent.getActivity(mCallerActivity, 2, i, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager am = (AlarmManager)mCallerActivity.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(1000*snoozeStartTimeNumb*60),pi);
	}

}
