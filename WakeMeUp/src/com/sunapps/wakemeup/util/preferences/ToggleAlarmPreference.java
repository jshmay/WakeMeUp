package com.sunapps.wakemeup.util.preferences;


import com.sunapps.wakemeup.util.listeners.AlarmConfigurationTab_ToggleServiceListener;

import android.app.Activity;
import android.content.Context;
import android.preference.SwitchPreference;
import android.util.AttributeSet;

public class ToggleAlarmPreference extends SwitchPreference{

	public ToggleAlarmPreference(Context context) {
		super(context);
		init(context);
	}
    public ToggleAlarmPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

	public ToggleAlarmPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
	private void init(Context context) {
		setOnPreferenceChangeListener(new AlarmConfigurationTab_ToggleServiceListener((Activity)context));
	}
	
}
