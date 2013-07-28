package com.example.freebox.utils;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.freebox.data.JSONFriendsListEntity;
import com.example.freebox.data.JSONQuanQuanListEntity;
import com.example.freebox.entity.MessageEntity;
import com.example.freebox.entity.QuanQuanEntity;
import com.example.freebox.entity.UserEntity;

public class DataGetHelper {
	private ArrayList<MessageEntity> mDataArrays = new ArrayList<MessageEntity>();
	private ArrayList<QuanQuanEntity> mMyQuanQuanList = new ArrayList<QuanQuanEntity>();
	private ArrayList<UserEntity> mFriendList=new ArrayList<UserEntity>();
	private String message_url;
	private String[] msgArray, timeArray, nameArray;
	private ArrayList<String> nameArrays = new ArrayList<String>();
	private ArrayList<String> msgArrays = new ArrayList<String>();
	private ArrayList<Integer> mQuanGuidArrays = new ArrayList<Integer>();
	private ArrayList<String> mFriendNameArrays = new ArrayList<String>();
	private ArrayList<String> mFriendNoteArrays = new ArrayList<String>();
	private JSONQuanQuanListEntity jsonquanlistentity;
	private JSONFriendsListEntity jsonfriendlistentity;

	public DataGetHelper() {
		// TODO Auto-generated constructor stub
	}

	public DataGetHelper(JSONQuanQuanListEntity jsonquanlistentity) {
		this.jsonquanlistentity = jsonquanlistentity;
	}

	public DataGetHelper(JSONFriendsListEntity jsonfriendlistentity) {
		this.jsonfriendlistentity = jsonfriendlistentity;
	}

	public void getMessageData() {
		nameArray = new String[] { "小白", "小红", "小黑", "lele", "杰克", "黑棱", "蓝天",
				"丁丁" };
		msgArray = new String[] { "在吗", "你好啊", "在哪里！", "在家吗？", "吃饭！！",
				"睡觉了吗！！", "魂淡", "滚", };

		timeArray = new String[] { "2012-09-01 18:00", "2012-09-01 18:10",
				"2012-09-01 18:11", "2012-09-01 18:20", "2012-09-01 18:30",
				"2012-09-01 18:35", "2012-09-01 18:40", "2012-09-01 18:50" };

	}

	public void getQuanQuanData() {
		for (int i = 0; i < this.jsonquanlistentity.getQuanList().size(); i++) {
			nameArrays
					.add(this.jsonquanlistentity.getQuanItem(i).getQuanName());
			msgArrays.add(this.jsonquanlistentity.getQuanItem(i).getQuanTag());
			mQuanGuidArrays.add(this.jsonquanlistentity.getQuanItem(i).getQuanId());
			Log.i("输出标签", this.jsonquanlistentity.getQuanItem(i).getQuanName());
			Log.i("输出标签", this.jsonquanlistentity.getQuanItem(i).getQuanTag());
		}
	}



	public ArrayList<MessageEntity> reflashData() {
		getMessageData();
		initData();
		return mDataArrays;
	}

	public ArrayList<MessageEntity> initData() {
		getMessageData();
		for (int i = 0; i < nameArray.length; i++) {
			MessageEntity entity = new MessageEntity();
			entity.setName(nameArray[i]);
			entity.setContent(msgArray[i]);
			entity.setTime(timeArray[i]);
			mDataArrays.add(entity);
		}
		return mDataArrays;
	}

	public ArrayList<QuanQuanEntity> initQuanQuanData() {
		getQuanQuanData();
		for (int i = 0; i < nameArray.length; i++) {
			QuanQuanEntity entity = new QuanQuanEntity();
			entity.setQuanQuanName(nameArray[i]);
			entity.setQuanQuanIntro(msgArray[i]);
			entity.setQuanId(mQuanGuidArrays.get(i));
			mMyQuanQuanList.add(entity);
		}
		return mMyQuanQuanList;
	}

	public ArrayList<QuanQuanEntity> initQuanQuanDataList() {
		getQuanQuanData();
		for (int i = 0; i < nameArrays.size(); i++) {
			QuanQuanEntity entity = new QuanQuanEntity();
			entity.setQuanQuanName(nameArrays.get(i));
			entity.setQuanQuanIntro(msgArrays.get(i));
			entity.setQuanId(mQuanGuidArrays.get(i));
			mMyQuanQuanList.add(entity);
		}
		return mMyQuanQuanList;
	}

	public void getFriendListData() {

		for (int i = 0; i < this.jsonfriendlistentity.getFriendList().size(); i++) {
			mFriendNameArrays.add(this.jsonfriendlistentity.getFriendItem(i)
					.getName());
			mFriendNoteArrays.add(this.jsonfriendlistentity.getFriendItem(i)
					.getUserName());
			Log.i("输出标签", this.jsonfriendlistentity.getFriendItem(i)
					.getUserName());
			Log.i("输出标签", this.jsonfriendlistentity.getFriendItem(i).getName());
		}
	}
	public ArrayList<UserEntity> initFriendsDataList() {
		getFriendListData();
		for (int i = 0; i < mFriendNameArrays.size(); i++) {
			UserEntity entity = new UserEntity();
			entity.setName(mFriendNameArrays.get(i));
			entity.setUserName(mFriendNoteArrays.get(i));
			mFriendList.add(entity);
		}
		return mFriendList;
	}

}
