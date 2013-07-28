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

	// �˻��ֻ���
	public void setUserName(String user_name) {
		this.user_name = user_name;
	}

	// �ǳ�
	public void setName(String name) {
		this.name = name;
	}

	// �û�guid
	public void setGuid(int guid) {
		this.user_id = guid;
	}

	// ͷ����
	public void setAvatarNum(int avatar_num) {
		this.avatar_num = avatar_num;
	}

	// ��ҳ����
	public void setWonline(int wonline) {
		this.wonline = wonline;
	}

	// �ͻ�������
	public void setConline(int conline) {
		this.conline = conline;
	}

	// ��ȡ�˻��ֻ�
	public String getUserName() {
		return user_name;
	}
	// ��ȡ�ǳ�
	public String getName() {
		return name;
	}

	// ��ȡ����
	public int getUserAge() {
		return user_age;
	}

	// ��ȡ�Ա�
	public String getUsergender() {
		return user_gender;
	}

	// ��ȡ����
	public String getUserConstellation() {
		return user_constellation;
	}

	// ��ȡ����
	public String getUserHobby() {
		return user_hobby;
	}

	// ��ȡ�û�guid
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
