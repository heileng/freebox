package com.example.freebox.data;

public class JSONQuanProfileEntity {

	private String state;
	private String owner;
	private String activity;
	private String announcement;
	private int guid;

	public JSONQuanProfileEntity() {
		// TODO Auto-generated constructor stub
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setActivity(String a) {
		this.activity = a;
	}

	public void setAnnouncement(String p) {
		this.announcement = p;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}

	public String getState() {
		return this.state;
	}

	public String getOwner() {
		return this.owner;
	}

	public String getActivity() {
		return this.activity;
	}

	public String getAnnouncement() {
		return this.announcement;
	}

	public int getGuid() {
		return this.guid;
	}
}
