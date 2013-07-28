package com.example.freebox.config;

public class Flags {
	public static String FromFriend = "friend";
	public static String FromMe = "Me";
	public static String APIKEY = "f9a60ac88209e3e262306ed1eaa7b345b2d78d98";
	public static String FromQuanProfile = "quan_profile";
	public static String FromAddress = "address";
	public static final int MSG_SUCCESS = 1;// 获取成功
	public static final int MSG_FAILURE = -1;// 获取失败
	public static final int MSG_SUCCESS_FRIEND = 2;// 好友列表获取成功
	// 消息分发标志
	public static final int MSG_NEW_SYSTEM_MESSAGE=27;
	public static final int MSG_NEW_QUAN_SYSTEM_MESSAGE=28;
	public static final int MSG_NEW_USER_SYSTEM_MESSAGE=29;
	public static final int MSG_NEW_FRIEND_MESSAGE = 30;
	public static final int MSG_NEW_QUAN_MESSAGE = 31;
	
	//消息编码
	public static final int MSG_SYSTEM=1;
	public static final int MSG_QUAN_SYS=2;
	public static final int MSG_USER_SYS=3;
	public static final int MSG_P2P=30;
	public static final int MSG_Group=31;
	
	// 数据库标签
	public static String UserMessageTable = "user_message";
	public static String QuanMessageTable = "quan_message";
	public static String UserProfileTable = "user_profile";
	public static String QuanProfileTable = "quan_profile";

	// 用户资料表标签
	public static String UserProfileTable_UserId = "user_id";
	public static String UserProfileTable_User_UserName = "user_username";
	public static String UserProfileTable_User_Name = "user_name";
	public static String UserProfileTable_User_Description = "user_description";
	public static String UserProfileTable_User_Briefdescription = "user_briefdescription";
	public static String UserProfileTable_User_Location = "user_location";
	public static String UserProfileTable_User_Phone = "user_phone";
	public static String UserProfileTable_User_Constellation = "user_constellation";
	public static String UserProfileTable_User_Remark = "user_remark";
	public static String UserProfileTable_User_Avatar = "user_avatar";
	public static String UserProfileTable_User_Contactemail = "user_contactemail";
	public static String UserProfileTable_User_Gender = "user_gender";
	public static String UserProfileTable_User_Website = "user_website";
	public static String UserProfileTable_User_Interests = "user_interests";
	public static String UserProfileTable_User_Skills = "user_skills";

}