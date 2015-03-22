package com.appsomnia.wakemeup.data;

public class AlarmData {
	int mTime;
	int mSnooze;
	
	public AlarmData(int alarmTime, int alarmSnooze) {
		mTime=alarmTime;
		mSnooze=alarmSnooze;
	}
	public int getTime() {
		return mTime;
	}
	public void setTime(int mTime) {
		this.mTime = mTime;
	}
	public int getSnooze() {
		return mSnooze;
	}
	public void setSnooze(int mSnooze) {
		this.mSnooze = mSnooze;
	}
	
}

