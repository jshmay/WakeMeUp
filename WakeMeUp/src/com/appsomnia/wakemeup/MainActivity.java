package com.appsomnia.wakemeup;

import java.util.HashSet;
import java.util.Set;

import com.appsomnia.wakemeup.data.VipData;
import com.appsomnia.wakemeup.fragments.SettingsFragment;
import com.appsomnia.wakemeup.internal.DataHandler;
import com.appsomnia.wakemeup.util.DialogHandler;
import com.appsomnia.wakemeup.util.external.TelephoneCaller;
import com.appsomnia.wakemeup.util.preferences.ContactListPickerPreference;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {
	public static final int VERSION = 3;
	
	public static String TAG = MainActivity.class.getSimpleName();
	
	private SettingsFragment mSFrag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        
        mSFrag = new SettingsFragment();
        ft.replace(android.R.id.content,mSFrag);
        ft.commit();//I have a feeling this is wrong...why would we want to replace ?
	}
	
	private void init() {
        int versionNumber = DataHandler.fetchVersionNumber(this);
        if(versionNumber!=VERSION){
        	DataHandler.clearData(this);
        	DataHandler.saveCurrentVersionNumber(this,VERSION);
        }
        boolean isFresh = DataHandler.fetchIsFreshInstallation(this);
        if(isFresh){
        	DialogHandler.printAlertDialog(this, "Tips", this.getString(R.string.app_tips));
        	DataHandler.saveIsNotNewlyInstalled(this);
        }
	}

	@Override
	protected void onDestroy() {
		Log.v(TAG, "destroyed mainactivity");
		super.onDestroy();
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);

		Log.v(TAG, "received response from contact intent ["+requestCode+"] VS ["+TelephoneCaller.PICK_CONTACT+"]");
		
		  switch (requestCode) {
		    case (TelephoneCaller.PICK_CONTACT) :
		      if (resultCode == Activity.RESULT_OK) {
		    	  fetchContactDetails(data);
		      }
		      break;
		  }
		  Log.v(TAG, "onActivityResult - done");
	}


	private void fetchContactDetails(Intent data) {
        Uri contactData = data.getData();
        ContentResolver cr = this.getContentResolver();
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
    	    //Toaster.print(this, "Fetched name: name["+contactName+"] & phone["+phoneNumber+"]");
    	    ContactListPickerPreference clp = (ContactListPickerPreference)mSFrag.findPreference("alarm_preferences__vip_name");
    	    clp.getEditText().setText(contactName);
        
        }		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.mainActivity_menu_about:
	        	DialogHandler.printAlertDialog(this,"About",this.getString(R.string.app_about));
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
}
