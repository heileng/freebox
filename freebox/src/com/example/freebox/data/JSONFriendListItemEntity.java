package com.example.freebox.data;

public class JSONFriendListItemEntity {
	private int mAvatarNum;
	private String mNikName;
	private int mOnlineFlag;
	public JSONFriendListItemEntity() {
		// TODO Auto-generated constructor stub
	}
	public void setAvatar(int avatar){
		this.mAvatarNum=avatar;
	}
	public void setNikname(String nikname){
		this.mNikName=nikname;
	}
	public void setOnlineFlag(int onlineflag){
		this.mOnlineFlag=onlineflag;
	}
	public int getAvatar(){
		return this.mAvatarNum;
	}
	public String getNikName(){
		return this.mNikName;
	}
	public int getOnlineFlag(){
		return this.mOnlineFlag;
	}
}
