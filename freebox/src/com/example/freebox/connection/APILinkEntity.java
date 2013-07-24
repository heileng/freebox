package com.example.freebox.connection;


public class APILinkEntity {
	public static String mBasicAPI="http://10.11.246.167/freebox/services/api/rest/json/";
	//注册与密码
	public static String mPasswordResetMethod="qurp";
	public static String mPasswordChangeMethod="qurnp";
	public static String mRegisterMethod="qur";
	//用户信息
	public static String mUserLoginMethod="qlgin";
	public static String mUserLogoutMethod="qolgt";
	public static String mTokenRefresh="qobt";
	public static String mUserDetailProfileMethod="qugp";
	public static String mSubmitUserProfileAPI;
	//我的好友
	public static String mGetFriendsListMethod="qugf";
	public static String mRemarkFriendsMethod="quredt";
	public static String mSearchUserMethod="qusch";
	public static String mAddFriendMethod="qufa";
	public static String mFriendDetailProfileMethod="qugud";
	public static String mDeleteFriendMethod="qufr";
	//圈圈功能
	public static String mGetMyQuanListMethod="qgggi";
	public static String mNewQuanMethod="qgcg";
	public static String mGetQuanMemberListMethod="qggm";
	public static String mOwnerAddQuanMemberMethod="qgoau";
	public static String mUserAddQuanMemberMethod="qgau";
	public static String mUserQuitQuanMethod="qgul";
	public static String mOwner2UserQuitQuanMethod="qgudya";
	public static String mOwnerDissoveQuanAPI;
	public static String mGetQuanProfileAPI;
	public static String mPublishAnnouncementAPI;
	public static String mPublishActivityAPI;
	public static String mSearchQuanAPI;
	
	
}
