package com.example.freebox.data;

public class JSONQuanQuanListItem {
	private int mQuanId;
	private int mQuanAvatarNum;
	private String mQuanName;
	private String[] mQuanTag;

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

	public void setQuanTag(String[] tags) {
		this.mQuanTag = tags;
	}

	public int setQuanId() {
		return this.mQuanId ;
	}

	public int setQuanAvatar() {
		return this.mQuanAvatarNum;
	}

	public String setQuanName() {
		return this.mQuanName;
	}

	public String[] setQuanTag() {
		return this.mQuanTag;
	}
}
