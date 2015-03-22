package com.appsomnia.wakemeup.util.preferences;


import com.appsomnia.wakemeup.internal.DataHandler;
import com.appsomnia.wakemeup.util.listeners.AlarmConfigurationTab_VipEditTextListener;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.EditTextPreference;
import android.text.InputType;
import android.util.AttributeSet;

public class ContactListPickerPreference extends EditTextPreference{

	public ContactListPickerPreference(Context context) {
		super(context);
		init(context);
	}
    public ContactListPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

	public ContactListPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
	private void init(Context context) {//Assumed the context is of type activity and it worked...less waja3 rass
		getEditText().setOnTouchListener(new AlarmConfigurationTab_VipEditTextListener((Activity)context));
		getEditText().setInputType(InputType.TYPE_NULL);
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		super.onClick(dialog, which);
		
		if(which == DialogInterface.BUTTON_POSITIVE) {
			DataHandler.confirmVipDetails(getContext());
	    }
	}
	
}
