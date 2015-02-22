package com.sunapps.wakemeup.data;

public class AlarmData {
	String mTime;
	String mSnooze;
	
	public AlarmData(String alarmTime, String alarmSnooze) {
		mTime=alarmTime;
		mSnooze=alarmSnooze;
	}
	public String getTime() {
		return mTime;
	}
	public void setTime(String mTime) {
		this.mTime = mTime;
	}
	public String getSnooze() {
		return mSnooze;
	}
	public void setSnooze(String mSnooze) {
		this.mSnooze = mSnooze;
	}
	
}

