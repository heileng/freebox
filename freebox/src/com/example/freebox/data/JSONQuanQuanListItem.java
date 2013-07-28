package com.example.freebox.data;

public class JSONQuanQuanListItem {
	private int mQuanId;
	private int mQuanAvatarNum;
	private String mQuanName;
	private String mQuanActivity;
	private String mQuanAnnouncement;
	private String mQuanTag;

	public JSONQuanQuanListItem() {
		// TODO Auto-generated constructor stub
	}

	public void setQuanId(int id) {
		this.mQuanId = id;
	}

	public void setQuanAvatar(int num) {
		this.mQuanAvatarNum = num;
	}

	public void setQuanName(String name) {
		this.mQuanName = name;
	}
	public void setQuanActivity(String activity) {
		this.mQuanActivity = activity;
	}
	public void setQuanAnnouncement(String announcement) {
		this.mQuanAnnouncement = announcement;
	}

	public void setQuanTag(String tags) {
		this.mQuanTag = tags;
	}

	public int getQuanId() {
		return this.mQuanId ;
	}

	public int getQuanAvatar() {
		return this.mQuanAvatarNum;
	}

	public String getQuanName() {
		return this.mQuanName;
	}

	public String getQuanTag() {
		return this.mQuanTag;
	}
	public String getActivity(){
		return this.mQuanActivity;
	}
	public String getAnnouncement(){
		return this.mQuanAnnouncement;
	}
}
