package com.sunapps.wakemeup.data;

import java.util.HashSet;
import java.util.Set;

public class VipData {
	String mName;
	String mId;
	Set<String> mContactList;
	
	
	public VipData(String name, String id, Set<String>contactList){
		mName=name;
		mId=id;
		mContactList=contactList;		
	}
	
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	public String getId() {
		return mId;
	}
	public void setId(String mId) {
		this.mId = mId;
	}
	public Set<String> getContactList() {
		return mContactList;
	}
	public void setContactList(Set<String> mContactList) {
		this.mContactList = mContactList;
	}
	
	
}
