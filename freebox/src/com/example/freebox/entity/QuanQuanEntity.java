package com.example.freebox.entity;

import android.content.Context;

public class QuanQuanEntity {
	private int mQuanId;
	private int mQuanAvatarNum;
	private Context mContext;
	private String quanquan_name;
	private String quanquan_announcement;
	private String quanquan_activity;
	private String quanquan_intro;
	private UserEntity quanquan_admin;
	private UserEntity[] quanquan_user_list;

	public QuanQuanEntity() {
		// TODO Auto-generated constructor stub
	}

	public QuanQuanEntity(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	public void setQuanId(int id) {
		this.mQuanId = id;
	}

	public void setQuanAvatar(int num) {
		this.mQuanAvatarNum = num;
	}

	public void setQuanQuanIntro(String intro) {
		this.quanquan_intro = intro;
	}

	public void setQuanQuanName(String quanquan_name) {
		this.quanquan_name = quanquan_name;
	}

	public void setQuanQuanAnnouncement(String anouncement) {
		this.quanquan_announcement = anouncement;

	}

	public void setQuanQuanActivity(String anouncement) {
		this.quanquan_announcement = anouncement;

	}

	public int getQuanId() {
		return this.mQuanId;
	}

	public int getQuanAvatar() {
		return this.mQuanAvatarNum;
	}

	public String getQuanQuanName() {
		return this.quanquan_name;
	}

	public String getQuanQuanItro() {
		return this.quanquan_intro;
	}

	public String getQuanQuanAnnouncement() {
		return this.quanquan_announcement;
	}

	public String getQuanQuanActivity() {
		return this.quanquan_activity;
	}
}
