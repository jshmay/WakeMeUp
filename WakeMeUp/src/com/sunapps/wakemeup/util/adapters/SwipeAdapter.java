package com.sunapps.wakemeup.util.adapters;

import com.sunapps.wakemeup.R;
import com.sunapps.wakemeup.fragments.TabbedFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SwipeAdapter extends FragmentPagerAdapter{
	private static int NUM_ITEMS = 2;
	Context mContext;
	public SwipeAdapter(FragmentManager fm, Context context) {
		super(fm);
		mContext=context;
	}

	@Override
	public Fragment getItem(int position) {
        switch (position) {
        case 0: // Fragment # 0 - This will show FirstFragment
            return TabbedFragment.newInstance(R.layout.listener_activation_layout);
        case 1: // Fragment # 1 - This will show alarm layout
            return TabbedFragment.newInstance(R.layout.alarm_configuration_layout);
        default:
            return null;
        }
	}

	@Override
	public int getCount() {
		return NUM_ITEMS ;
	}

}
