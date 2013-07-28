package com.example.freebox.data;

import android.content.Context;

public class JSONFriendListItemEntity {
	private String user_name;
	private String name;
	private int user_age;
	private Context context;
	private int user_id;
	private int avatar_num;
	private int wonline;
	private int conline;
	private String user_gender;
	private String user_constellation;
	private String user_hobby;

	public JSONFriendListItemEntity() {
		// TODO Auto-generated constructor stub
	}

	// 账户手机号
	public void setUserName(String user_name) {
		this.user_name = user_name;
	}

	// 昵称
	public void setName(String name) {
		this.name = name;
	}

	// 用户guid
	public void setGuid(int guid) {
		this.user_id = guid;
	}

	// 头像编号
	public void setAvatarNum(int avatar_num) {
		this.avatar_num = avatar_num;
	}

	// 网页在线
	public void setWonline(int wonline) {
		this.wonline = wonline;
	}

	// 客户端在线
	public void setConline(int conline) {
		this.conline = conline;
	}

	// 获取账户手机
	public String getUserName() {
		return user_name;
	}
	// 获取昵称
	public String getName() {
		return name;
	}

	// 获取年龄
	public int getUserAge() {
		return user_age;
	}

	// 获取性别
	public String getUsergender() {
		return user_gender;
	}

	// 获取星座
	public String getUserConstellation() {
		return user_constellation;
	}

	// 获取爱好
	public String getUserHobby() {
		return user_hobby;
	}

	// 获取用户guid
	public int getUserid() {
		return user_id;
	}

	public int getAvatarNum() {
		return avatar_num;
	}

	public int getWonline() {
		return wonline;
	}

	public int getConline() {
		return conline;
	}
}
