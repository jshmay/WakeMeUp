package com.sunapps.wakemeup.util;

import com.sunapps.wakemeup.util.listeners.MainActivity_StartButtonListener;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class PhoneTools {
	public static void fetchContactPhoneNumber(Context context){

	       Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
	       String name = "Default";
	       String phoneNumber = "Phone Number";
	       while (phones.moveToNext())
	        {
	          name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	          phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	       }
	        phones.close();

	        Toaster.print(context, "@PhoneTools -- Fetched: ["+name+"] ["+phoneNumber+"]");
	}
}
