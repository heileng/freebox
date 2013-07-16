package com.example.freebox.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.freebox.entity.MessageEntity;
import com.example.freebox.entity.QuanQuanEntity;



public class DataGetHelper {
	private ArrayList<MessageEntity> mDataArrays = new ArrayList<MessageEntity>();
	private ArrayList<QuanQuanEntity> mMyQuanQuanList=new ArrayList<QuanQuanEntity>();
	private String message_url;
	private String[] msgArray, timeArray, nameArray;

	public DataGetHelper() {
		// TODO Auto-generated constructor stub
	}
	public void getMessageData() {
		nameArray = new String[] { "С��", "С��", "С��", "lele", "�ܿ�", "����", "����",
				"����" };
		msgArray = new String[] { "����", "��ð�", "�����", "�ڼ���", "�Է�����",
				"˯�����𣡣�", "�국", "��", };

		timeArray = new String[] { "2012-09-01 18:00", "2012-09-01 18:10",
				"2012-09-01 18:11", "2012-09-01 18:20", "2012-09-01 18:30",
				"2012-09-01 18:35", "2012-09-01 18:40", "2012-09-01 18:50" };

	}
	public void getQuanQuanData() {
		nameArray = new String[] { "ȦȦ1��", "ȦȦ2��", "ȦȦ3��", "ȦȦ4��", "ȦȦ5��", "ȦȦ6��", "ȦȦ7��",
				"ȦȦ1��" };
		msgArray = new String[] { "ȦȦ���", "ȦȦ���", "ȦȦ���", "ȦȦ���", "ȦȦ���",
				"ȦȦ���", "ȦȦ���", "ȦȦ���", };

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
	public ArrayList<QuanQuanEntity> initQuanQuanData(){
		getQuanQuanData();
		for(int i=0;i<nameArray.length;i++){
			QuanQuanEntity entity=new QuanQuanEntity();
			entity.setQuanQuanName(nameArray[i]);
			entity.setQuanQuanIntro(msgArray[i]);
			mMyQuanQuanList.add(entity);		
		}
		return mMyQuanQuanList;
	}

}
