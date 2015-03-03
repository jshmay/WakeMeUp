package com.sunapps.wakemeup;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

import com.sunapps.wakemeup.data.VipData;
import com.sunapps.wakemeup.internal.DataHandler;
import com.sunapps.wakemeup.util.Toaster;
import com.sunapps.wakemeup.util.adapters.SwipeAdapter;
import com.sunapps.wakemeup.util.external.TelephoneCaller;
import com.sunapps.wakemeup.util.transformers.ZoomOutPageTransformer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends FragmentActivity {
	public static String TAG = MainActivity.class.getSimpleName();
	
	FragmentPagerAdapter adapterViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new SwipeAdapter(getSupportFragmentManager(),this);
        vpPager.setAdapter(adapterViewPager);
        vpPager.setPageTransformer(true, new ZoomOutPageTransformer());
	}
	
	@Override
	protected void onDestroy() {
		Log.v(TAG, "destroyed mainactivity");
		super.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		Log.v(TAG, "received response from contact intent ["+requestCode+"] VS ["+TelephoneCaller.PICK_CONTACT+"]");
		
		  switch (requestCode) {
		    case (TelephoneCaller.PICK_CONTACT) :
		      if (resultCode == Activity.RESULT_OK) {
		        Uri contactData = data.getData();
		        ContentResolver cr = getContentResolver();
		        Cursor c =  cr.query(contactData, null, null, null, null);
		        if (c.moveToFirst()) {
			          String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
			          String contactName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			          Set<String> contactList=new HashSet<String>();
			          
			          
			          Cursor phones = cr.query(Phone.CONTENT_URI, null,
			        	        Phone.CONTACT_ID + " = " + contactId, null, null);
			          String phoneNumber="";
	        	    while (phones.moveToNext()) {
	        	    	phoneNumber = phones.getString(phones.getColumnIndex(Phone.NUMBER));
	        	        /*int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
	        	        
	        	        switch (type) {
	        	            case Phone.TYPE_HOME:
	        	                // do something with the Home number here...
	        	                break;
	        	            case Phone.TYPE_MOBILE:
	        	                // do something with the Mobile number here...
	        	                break;
	        	            case Phone.TYPE_WORK:
	        	                // do something with the Work number here...
	        	                break;
	        	        }*/
	        	        contactList.add(phoneNumber);
	        	    }
	        	    phones.close();
        	    
	        	
        	    DataHandler.saveVipDetailsTemp(this, new VipData(contactName, contactId, contactList));//save temporary data to internal memory
        	    
        		EditText tvVip = (EditText)findViewById(R.id.alarm_configuration__editText__vip);
        		tvVip.setText(contactName);
		          Toaster.print(this, "Fetched name: name["+contactName+"] & phone["+phoneNumber+"]");
		        }
		      }
		      break;
		    case (TelephoneCaller.PICK_ALARM):
		        if (resultCode == -1) {
		            final Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
		            
		            DataHandler.saveAlarmToneDetailsTemp(this, uri);//save temporary data to internal memory
	        		
		            EditText tvVip = (EditText)findViewById(R.id.alarm_configuration__editText__alarmRingtone);
	        		tvVip.setText(RingtoneManager.getRingtone(this, uri).getTitle(this));	            
		            /*if (uri != null) {
		                RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM, uri);
		            }*/
		            Toaster.print(this, "Alarm tone has been chosen");
		        }		    	
		    break;
		  }
		  Log.v(TAG, "onActivityResult - done");
	}
}
