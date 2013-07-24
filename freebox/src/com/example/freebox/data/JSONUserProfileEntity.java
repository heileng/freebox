package com.example.freebox.data;

public class JSONUserProfileEntity {
	private int mAvatarNum;
	private String mNikName;
	private String mGender;
	private String mAge;
	private String mConstellation;
	private String[] mInterst;
	 public JSONUserProfileEntity() {
		// TODO Auto-generated constructor stub
	}
	public void setAvatar(int avatar){
		this.mAvatarNum=avatar;
	}
	public void setNikName(String nikname){
		this.mNikName=nikname;
	}
	public void setGender(String gender){
		this.mGender=gender;
	}
	public void setAge(String age){
		this.mAge=age;
	}
	public void setConstellation(String constellation){
		this.mConstellation=constellation;
	}
	public void setIntersts(String[] intersts){
		this.mInterst=intersts;
	}
	
	
	public int getAvatar(){
		return this.mAvatarNum;
	}
	public String getNikName(){
		return this.mNikName;
	}
	public String getGender(){
		return this.mGender;
	}
	public String getAge(){
		return this.mAge;
	}
	public String getConstellation(){
		return this.mConstellation;
	}
	public String[] setIntersts(){
		return this.mInterst;
	}
}
