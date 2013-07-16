package com.example.freebox.entity;

import com.example.freebox.R;

public class UserEntity {

	private String user_name;
	private int user_age;
	private String user_id;
	private String user_gender;
	private String user_constellation;
	private String user_hobby;
	private int user_avatar_drawable_id;
	public UserEntity my_entity,entity_xiaohei,entity_xiaobai;

	public UserEntity() {
		// TODO Auto-generated constructor stub
		my_entity=new UserEntity("ºÚÀâ",R.drawable.heileng,20,"869242650","ÄÐ","Ìì³Ó×ù","ÀºÇò");
		entity_xiaohei=new UserEntity("Ð¡ºÚ",R.drawable.xiaohei,20,"869242650","ÄÐ","Ìì³Ó×ù","ÀºÇò");
	}

	public UserEntity(String user_name,int user_avatar_drawable_id, int user_age,String user_id, String user_gender,
			String user_constellation, String user_hobby) {
		this.user_name = user_name;
		this.user_avatar_drawable_id=user_avatar_drawable_id;
		this.user_age = user_age;
		this.user_gender = user_gender;
		this.user_constellation = user_constellation;
		this.user_hobby = user_hobby;
	}

	
	public String getUserName() {
		return user_name;
	}

	public int getUserAge() {
		return user_age;
	}

	public String getUsergender() {
		return user_gender;
	}

	public String getUserConstellation() {
		return user_constellation;
	}

	public String getUserHobby() {
		return user_hobby;
	}
	public String getUserid() {
		return user_id;
	}
	public int getUserDrawable() {
		return user_avatar_drawable_id;
	}
}
