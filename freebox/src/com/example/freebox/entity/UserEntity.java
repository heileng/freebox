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
		my_entity = new UserEntity("����", R.drawable.heileng, 20, "869242650",
				"��", "�����", "����");
		entity_xiaohei = new UserEntity("С��", R.drawable.xiaohei, 20,
				"869242650", "��", "�����", "����");
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

	// �˻��ֻ���
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

	// �ǳ�
	public void setName(String name) {
		this.name = name;
	}

	public void setSkills(String skills) {
		this.user_skills = skills;
	}

	public String getSkills() {
		return this.user_skills;
	}

	// ����
	public void setDescription(String description) {
		this.user_description = description;
	}

	// ��ϵ����
	public void setContactemail(String contactemail) {
		this.user_contactemail = contactemail;
	}

	// ����ǩ��
	public void setBriefdescription(String user_briefdescription) {
		this.user_briefdescription = user_briefdescription;
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
	public String getUserInterests() {
		return user_interests;
	}

	public String getContactemail() {
		return this.user_contactemail;
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
