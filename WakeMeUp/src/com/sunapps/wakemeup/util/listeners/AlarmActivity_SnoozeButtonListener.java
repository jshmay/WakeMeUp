package com.sunapps.wakemeup.util.listeners;


import com.sunapps.wakemeup.AlarmReceiverActivity;
import com.sunapps.wakemeup.data.AlarmData;
import com.sunapps.wakemeup.internal.DataHandler;

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
		if(mMediaPlayer.isPlaying()){
			mMediaPlayer.stop();
			mCallerActivity.finish();
		}
		if(mVibrator.hasVibrator())
			mVibrator.cancel();
		snoozeAlarm();
		
		mCallerActivity.finish();
	}
	
	private void snoozeAlarm() {
		AlarmData ad = DataHandler.fetchAlarmConfig(mCallerActivity);
		String snoozeStartTime = ad.getSnooze()==null?"1":ad.getSnooze();
		int snoozeStartTimeNumb = Integer.parseInt(snoozeStartTime);
		
		Intent i = new Intent(mCallerActivity, AlarmReceiverActivity.class);
		PendingIntent pi = PendingIntent.getActivity(mCallerActivity, 2, i, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager am = (AlarmManager)mCallerActivity.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(1000*snoozeStartTimeNumb*60),pi);
	}

}
