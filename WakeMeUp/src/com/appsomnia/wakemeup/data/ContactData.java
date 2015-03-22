package com.appsomnia.wakemeup.data;

public class ContactData {
	String mFullName="";
	String mPhoneNumber="";
	boolean isEmpty=true;
	
	public ContactData(String fullName, String phoneNumber){
		mFullName=fullName;
		mPhoneNumber=phoneNumber;
		isEmpty=false;
	}

	public String getFullName() {
		return mFullName;
	}

	public void setFullName(String mFullName) {
		isEmpty=false;
		this.mFullName = mFullName;
	}

	public String getPhoneNumber() {
		return mPhoneNumber;
	}

	public void setPhoneNumber(String mPhoneNumber) {
		isEmpty=false;
		this.mPhoneNumber = mPhoneNumber;
	}
	
	
}
