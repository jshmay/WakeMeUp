package com.sunapps.wakemeup.util.listeners;


import com.sunapps.wakemeup.R;
import com.sunapps.wakemeup.data.AlarmData;
import com.sunapps.wakemeup.internal.DataHandler;
import com.sunapps.wakemeup.util.Toaster;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class AlarmActivityConfig_SaveButtonListener implements OnClickListener{
	public static String TAG = AlarmActivityConfig_SaveButtonListener.class.getSimpleName();
	
	Activity mCallerActivity;
	
	public AlarmActivityConfig_SaveButtonListener(Activity caller) {
		mCallerActivity = caller;
	}

	@Override
	public void onClick(View view) {
		
		AlarmData ad=new AlarmData(1, 1);
		
		EditText npTime = (EditText)mCallerActivity.findViewById(R.id.alarm_configuration__numberpicker__time);
		EditText npSnooze = (EditText)mCallerActivity.findViewById(R.id.alarm_configuration__numberpicker__snooze);
		EditText npOccurrence = (EditText)mCallerActivity.findViewById(R.id.alarm_configuration__numberpicker__vipOccurenceLimit);
		
		
		int npTimeNum = Integer.parseInt(npTime.getText()==null?"0":npTime.getText().toString());
		int npSnoozeNum = Integer.parseInt(npSnooze.getText()==null?"0":npSnooze.getText().toString());
		int npOccurrenceNum = Integer.parseInt(npOccurrence.getText()==null?"0":npOccurrence.getText().toString());
		
		
		if(npTimeNum==0)
			npTimeNum=1;
		if(npSnoozeNum==0)
			npSnoozeNum=1;
		if(npOccurrenceNum==0)
			npOccurrenceNum=1;
		
		ad.setTime(npTimeNum);
		ad.setSnooze(npSnoozeNum);
		
		Log.w(TAG, "Saving Data ["+ad.getTime()+"] ["+ad.getSnooze()+"]");
		
		DataHandler.saveAlarmDataAndVip(mCallerActivity, ad, DataHandler.fetchVipDetailsTemp(mCallerActivity),npOccurrenceNum, DataHandler.fetchAlarmToneDetailsTemp(mCallerActivity));
		
		Toaster.print(mCallerActivity, "Alarm Config Saved");
		
	}

}
