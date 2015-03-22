package com.appsomnia.wakemeup.data;

public class CallData {
	String mPhoneNumber;
	int mCalltype;
	int mCallDuration;
	
	public CallData(String phNumber, int dircode, int callDuration) {
		mPhoneNumber=phNumber;
		mCalltype=dircode;
		mCallDuration=callDuration;
	}

	public String getPhoneNumber() {
		return mPhoneNumber;
	}

	public void setPhoneNumber(String mPhoneNumber) {
		this.mPhoneNumber = mPhoneNumber;
	}

	public int getCalltype() {
		return mCalltype;
	}

	public void setCalltype(int mCalltype) {
		this.mCalltype = mCalltype;
	}

	public int getCallDuration() {
		return mCallDuration;
	}

	public void setCallDuration(int mCallDuration) {
		this.mCallDuration = mCallDuration;
	}
	
	
	
	
}
