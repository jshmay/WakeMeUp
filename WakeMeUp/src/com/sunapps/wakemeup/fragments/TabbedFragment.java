package com.sunapps.wakemeup.fragments;

import com.sunapps.wakemeup.R;
import com.sunapps.wakemeup.data.AlarmData;
import com.sunapps.wakemeup.internal.DataHandler;
import com.sunapps.wakemeup.util.listeners.AlarmActivityConfig_StopAlarmButtonListener;
import com.sunapps.wakemeup.util.listeners.AlarmActivityConfig_SaveButtonListener;
import com.sunapps.wakemeup.util.listeners.AlarmConfigurationTab_AlarmPickerEditTextListener;
import com.sunapps.wakemeup.util.listeners.AlarmConfigurationTab_VipEditTextListener;
import com.sunapps.wakemeup.util.listeners.MainActivity_StartButtonListener;
import com.sunapps.wakemeup.util.listeners.MainActivity_StopButtonListener;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.provider.Settings.System;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class TabbedFragment extends Fragment{
    // Store instance variables
	private static final String TAG=TabbedFragment.class.getSimpleName(); 
    private int layout;

    // newInstance constructor for creating fragment with arguments
    public static TabbedFragment newInstance(int layout) {
    	TabbedFragment fragmentFirst = new TabbedFragment();
        Bundle args = new Bundle();
        args.putInt("layout", layout);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = getArguments().getInt("layout", R.layout.listener_activation_layout);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layout, container, false);
        if(layout==R.layout.listener_activation_layout){
        	initActivationScreen(view);
        }else if(layout==R.layout.alarm_configuration_layout){
        	initAlarmScreen(view);
        }
        return view;
    }

	private void initAlarmScreen(View view) {
		Button strButton = (Button)view.findViewById(R.id.alarm_configuration__button__save);
		strButton.setOnClickListener(new AlarmActivityConfig_SaveButtonListener(getActivity()));
		Button almButton = (Button)view.findViewById(R.id.alarm_configuration__button__alarm_stop);
		almButton.setOnClickListener(new AlarmActivityConfig_StopAlarmButtonListener(getActivity()));
				
		AlarmData ad = DataHandler.fetchAlarmConfig(getActivity());
		EditText npTime = (EditText)view.findViewById(R.id.alarm_configuration__numberpicker__time);
		npTime.setText(ad.getTime());
		EditText npSnooze = (EditText)view.findViewById(R.id.alarm_configuration__numberpicker__snooze);
		npSnooze.setText(ad.getSnooze());
		
		EditText npVipOccurenceLimit = (EditText)view.findViewById(R.id.alarm_configuration__numberpicker__vipOccurenceLimit);
		Log.w(TAG,"npVipOccurenceLimit["+(npVipOccurenceLimit==null)+"]");
		npVipOccurenceLimit.setText(""+DataHandler.fetchVipOccurrenceLimit(getActivity()));		
		EditText etVip = (EditText)view.findViewById(R.id.alarm_configuration__editText__vip);
		etVip.setText(DataHandler.fetchVipDetails(getActivity()).getName());
		etVip.setOnTouchListener(new AlarmConfigurationTab_VipEditTextListener(getActivity()));
		
		Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), RingtoneManager.getActualDefaultRingtoneUri(getActivity(), RingtoneManager.TYPE_ALARM));
		
		Log.w(TAG,"initRingtone["+ringtone.getTitle(getActivity())+"]");
		
		EditText etAlarmRingtone = (EditText)view.findViewById(R.id.alarm_configuration__editText__alarmRingtone);
		etAlarmRingtone.setText(ringtone.getTitle(getActivity()));
		etAlarmRingtone.setOnTouchListener(new AlarmConfigurationTab_AlarmPickerEditTextListener(getActivity()));
		
		Log.v(TAG, "Init initAlarmScreen completed");
	}
    
	private void initActivationScreen(View view) {
		Button strButton = (Button)view.findViewById(R.id.main_activity__button__start);
		strButton.setOnClickListener(new MainActivity_StartButtonListener(getActivity()));
		Button stpButton = (Button)view.findViewById(R.id.main_activity__button__stop);
		stpButton.setOnClickListener(new MainActivity_StopButtonListener(getActivity()));
	}

}
