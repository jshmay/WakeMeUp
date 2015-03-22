package com.appsomnia.wakemeup.util.listeners;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;

public class AlarmActivity_OkButtonListener implements OnClickListener{
	public static String TAG = AlarmActivity_OkButtonListener.class.getSimpleName();
	
	Activity mCallerActivity;
	MediaPlayer mMediaPlayer;
	Vibrator mVibrator;
	
	public AlarmActivity_OkButtonListener(Activity caller, MediaPlayer mediaPlayer, Vibrator vibrator) {
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
		
		mCallerActivity.finish();
	}

}
