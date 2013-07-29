package com.example.freebox.entity;

import android.graphics.Bitmap;

public class MessageEntity {
	private String name;
	private String content;
	private String time;
	private Bitmap avatar;
	private int code;
	private String type;
	private String source;
	private String dest;
	
	private int messagecount;

	public MessageEntity() {
		// TODO Auto-generated constructor stub
	}

	public MessageEntity(String name, String content, String time, Bitmap avatar) {
		super();
		this.name = name;
		this.content = content;
		this.time = time;
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}
	public int getMessageCount(){
		return messagecount;
	}
	public void setMessageCount(int count){
		this.messagecount=count;
	}

	public String getTime() {
		return time;
	}

	public Bitmap getAvatar() {
		return avatar;
	}

	public String getContent() {
		return content;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setAvatar(Bitmap avatar) {
		this.avatar = avatar;
	}

}
