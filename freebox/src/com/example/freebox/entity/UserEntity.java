package com.example.freebox.entity;

import android.content.Context;

import com.example.freebox.R;
import com.example.freebox.config.Flags;

public class UserEntity {

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
	private String user_interests;
	private String user_description;
	private String user_briefdescription;
	private String user_contactemail;
	private String user_location;
	private String user_skills;
	private String user_phone;
	private String user_remark;
	private int user_avatar_drawable_id;
	private String user_website;
	public UserEntity my_entity, entity_xiaohei, entity_xiaobai;

	public UserEntity() {
		// TODO Auto-generated constructor stub
		my_entity = new UserEntity("黑棱", R.drawable.heileng, 20, "869242650",
				"男", "天秤座", "篮球");
		entity_xiaohei = new UserEntity("小黑", R.drawable.xiaohei, 20,
				"869242650", "男", "天秤座", "篮球");
	}

	public UserEntity(Context context) {
		this.context = context;
	}

	public UserEntity(String user_name, int user_avatar_drawable_id,
			int user_age, String user_id, String user_gender,
			String user_constellation, String user_hobby) {
		this.user_name = user_name;
		this.user_avatar_drawable_id = user_avatar_drawable_id;
		this.user_age = user_age;
		this.user_gender = user_gender;
		this.user_constellation = user_constellation;
		this.user_interests = user_hobby;
	}

	// 账户手机号
	public void setUserName(String user_name) {
		this.user_name = user_name;
	}

	public void setUserLocation(String location) {
		this.user_location = location;
	}

	public String getLocation() {
		return this.user_location;
	}

	public void setWebsite(String website) {
		this.user_website = website;
	}

	public String getWebsite() {
		return this.user_website;
	}
	public void setPhone(String phone){
		this.user_phone=phone;
	}
	public void setRemark(String remark){
		this.user_remark=remark;
	}
	public String getRemark(){
		return this.user_remark;
	}

	public String getPhone() {
		return this.user_phone;
	}

	// 昵称
	public void setName(String name) {
		this.name = name;
	}

	public void setSkills(String skills) {
		this.user_skills = skills;
	}

	public String getSkills() {
		return this.user_skills;
	}

	// 详情
	public void setDescription(String description) {
		this.user_description = description;
	}

	// 联系邮箱
	public void setContactemail(String contactemail) {
		this.user_contactemail = contactemail;
	}

	// 个性签名
	public void setBriefdescription(String user_briefdescription) {
		this.user_briefdescription = user_briefdescription;
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
	public String getUserInterests() {
		return user_interests;
	}

	public String getContactemail() {
		return this.user_contactemail;
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

	public String getDescription() {
		return this.user_description;
	}

	public int getConline() {
		return conline;
	}

	public String getBriefdescription() {
		return this.user_briefdescription;
	}

	//
	public int getUserDrawable() {
		return user_avatar_drawable_id;
	}
}
