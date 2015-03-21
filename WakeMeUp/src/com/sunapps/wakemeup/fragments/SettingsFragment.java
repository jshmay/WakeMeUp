package com.sunapps.wakemeup.fragments;


import com.sunapps.wakemeup.MainActivity;
import com.sunapps.wakemeup.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment{
	public static String TAG = MainActivity.class.getSimpleName();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
