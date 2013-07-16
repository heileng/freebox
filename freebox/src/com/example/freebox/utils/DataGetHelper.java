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
		nameArray = new String[] { "Ð¡°×", "Ð¡ºì", "Ð¡ºÚ", "lele", "½Ü¿Ë", "ºÚÀâ", "À¶Ìì",
				"¶¡¶¡" };
		msgArray = new String[] { "ÔÚÂð", "ÄãºÃ°¡", "ÔÚÄÄÀï£¡", "ÔÚ¼ÒÂð£¿", "³Ô·¹£¡£¡",
				"Ë¯¾õÁËÂð£¡£¡", "»êµ­", "¹ö", };

		timeArray = new String[] { "2012-09-01 18:00", "2012-09-01 18:10",
				"2012-09-01 18:11", "2012-09-01 18:20", "2012-09-01 18:30",
				"2012-09-01 18:35", "2012-09-01 18:40", "2012-09-01 18:50" };

	}
	public void getQuanQuanData() {
		nameArray = new String[] { "È¦È¦1ºÅ", "È¦È¦2ºÅ", "È¦È¦3ºÅ", "È¦È¦4ºÅ", "È¦È¦5ºÅ", "È¦È¦6ºÅ", "È¦È¦7ºÅ",
				"È¦È¦1ºÅ" };
		msgArray = new String[] { "È¦È¦¼ò½é", "È¦È¦¼ò½é", "È¦È¦¼ò½é", "È¦È¦¼ò½é", "È¦È¦¼ò½é",
				"È¦È¦¼ò½é", "È¦È¦¼ò½é", "È¦È¦¼ò½é", };

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
