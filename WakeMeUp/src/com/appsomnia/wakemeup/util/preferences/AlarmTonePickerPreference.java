package com.appsomnia.wakemeup.util.preferences;


import com.appsomnia.wakemeup.util.listeners.AlarmConfigurationTab_AlarmTonePickerListener;

import android.app.Activity;
import android.content.Context;
import android.preference.RingtonePreference;
import android.util.AttributeSet;

public class AlarmTonePickerPreference extends RingtonePreference{

	public AlarmTonePickerPreference(Context context) {
		super(context);
		init(context);
	}
    public AlarmTonePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

	public AlarmTonePickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
	private void init(Context context) {//Assumed the context is of type activity and it worked...less waja3 rass
		setOnPreferenceChangeListener(new AlarmConfigurationTab_AlarmTonePickerListener((Activity)context));
	}
	
}
