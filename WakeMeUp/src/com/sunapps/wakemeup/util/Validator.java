package com.sunapps.wakemeup.util;

import java.util.Iterator;
import java.util.Set;

import android.telephony.PhoneNumberUtils;

public class Validator {
	public static boolean isValidVipContactNumber(String incomingNumber,Set<String>storedContactList){
		
		Iterator<String> itr = storedContactList.iterator();
		
		while(itr.hasNext()){
			if(PhoneNumberUtils.compare(incomingNumber, itr.next())){
				return true;
			}
		}
		
		return false;
	}
}
