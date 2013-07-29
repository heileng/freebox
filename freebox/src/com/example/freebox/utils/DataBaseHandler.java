package com.example.freebox.utils;

import java.util.ArrayList;
import java.util.Date;

import com.example.freebox.config.Flags;
import com.example.freebox.data.JSONMessageEntity;
import com.example.freebox.entity.UserEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseHandler {
	private SQLiteDatabase db;
	private Context context;
	public DatabaseHelper myHelper;
	private UserEntity userEntity;

	public DataBaseHandler(Context contex, UserEntity userEntity) {
		// TODO Auto-generated constructor stub
		this.context = contex;
		this.userEntity = userEntity;
	}

	public DataBaseHandler(Context contex) {
		// TODO Auto-generated constructor stub
		this.context = contex;
	}

	// public long insertToUserTable(String tname, int tage, String ttel) {
	// // ContentValues类似map，存入的是键值对
	// ContentValues contentValues = new ContentValues();
	// contentValues.put("tname", tname);
	// contentValues.put("tage", tage);
	// contentValues.put("ttel", ttel);
	// return db.insert(tname, null, contentValues);
	// }

	// 将用户信息保存到数据库
	public void insertToUserTable(UserEntity userentity) {
		// ContentValues类似map，存入的是键值对
		int result;
		myHelper = new DatabaseHelper(context);
		SQLiteDatabase db = myHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		Cursor cursor = db.query(DatabaseHelper.user_profile_table,
				new String[] { Flags.UserProfileTable_UserId,
						Flags.UserProfileTable_User_UserName }, null, null,
				null, null, null);
		int nameIndex = cursor.getColumnIndex(Flags.UserProfileTable_UserId);
		boolean user_exist_flag = false;
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			result = cursor.getInt(nameIndex);
			if (userentity.getUserid() == result) {
				user_exist_flag = true;
			}
		}
		if (user_exist_flag == false) {
			contentValues.put(Flags.UserProfileTable_UserId,
					userentity.getUserid());
			contentValues.put("user_userid", "" + userentity.getUserid());
			contentValues.put(Flags.UserProfileTable_User_Description,
					userentity.getDescription());
			contentValues.put(Flags.UserProfileTable_User_Avatar,
					userentity.getAvatarNum());
			contentValues.put(Flags.UserProfileTable_User_Briefdescription,
					userentity.getBriefdescription());
			contentValues.put(Flags.UserProfileTable_User_Constellation,
					userentity.getUserConstellation());
			contentValues.put(Flags.UserProfileTable_User_Contactemail,
					userentity.getContactemail());
			contentValues.put(Flags.UserProfileTable_User_Interests,
					userentity.getUserInterests());
			contentValues.put(Flags.UserProfileTable_User_Location,
					userentity.getLocation());
			contentValues.put(Flags.UserProfileTable_User_Gender,
					userentity.getUsergender());
			contentValues.put(Flags.UserProfileTable_User_UserName,
					userentity.getUserName());
			contentValues.put(Flags.UserProfileTable_User_Name,
					userentity.getName());
			contentValues.put(Flags.UserProfileTable_User_Skills,
					userentity.getSkills());
			contentValues.put(Flags.UserProfileTable_User_Website,
					userentity.getWebsite());
			contentValues.put(Flags.UserProfileTable_User_Phone,
					userentity.getPhone());
			contentValues.put(Flags.UserProfileTable_User_Remark,
					userentity.getRemark());
			db.insert(Flags.UserProfileTable, null, contentValues);
		}
		cursor.close();
		contentValues.clear();
		db.close();

	}

	public String getUserNameById(String id) {
		String result = null;
		String user_id;
		String username;
		myHelper = new DatabaseHelper(context);
		SQLiteDatabase db = myHelper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.user_profile_table,
				new String[] { "user_userid", "user_username" }, null, null, null,
				null, null);
		int idIndex = cursor.getColumnIndex("user_userid");
		int usernameIndex = cursor.getColumnIndex("user_username");
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			user_id = cursor.getString(idIndex);
			username = cursor.getString(usernameIndex);
			if (id.equals(user_id)) {
				result = username;
			}
		}
		return result;
	}

	// 将信息存入数据库
	public void insertToMessageTable(JSONMessageEntity messageentity,
			String read_flag) {
		// ContentValues类似map，存入的是键值对
		// int result;
		myHelper = new DatabaseHelper(context);
		SQLiteDatabase db = myHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("type", messageentity.getType());
		contentValues.put("code", messageentity.getCode());
		contentValues.put("time", messageentity.getTimes());
		contentValues.put("source", messageentity.getSources());
		contentValues.put("dest", messageentity.getDest());
		contentValues.put("content", messageentity.getContent());
		contentValues.put("read_flag", read_flag);
		Log.i("写入数据库", "" + messageentity.getType() + messageentity.getCode()
				+ messageentity.getTimes() + messageentity.getSources()
				+ messageentity.getDest() + read_flag);
		db.insert("message_table", null, contentValues);
	}

	// 获取指定userid的用户的信息
	public ArrayList<JSONMessageEntity> getCurrentUserMessageList(String user_id) {
		int code;
		String type, source, dest, content;
		String time;
		String user_id2 = user_id;
		ArrayList<JSONMessageEntity> messageList = new ArrayList<JSONMessageEntity>();
		myHelper = new DatabaseHelper(context);
		SQLiteDatabase db = myHelper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.message_table, new String[] {
				"type", "code", "time", "source", "dest", "content" }, null,
				null, null, null, null);
		int typeIndex = cursor.getColumnIndex("type");
		int codeIndex = cursor.getColumnIndex("code");
		int timeIndex = cursor.getColumnIndex("time");
		int sourceIndex = cursor.getColumnIndex("source");
		int destIndex = cursor.getColumnIndex("dest");
		int contentIndex = cursor.getColumnIndex("content");
		Log.i("数据库处理", "" + typeIndex + codeIndex + sourceIndex);
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			type = cursor.getString(typeIndex);
			code = cursor.getInt(codeIndex);
			source = cursor.getString(sourceIndex);
			dest = cursor.getString(destIndex);
			content = cursor.getString(contentIndex);
			time = cursor.getString(timeIndex);
			Log.i("guid", user_id);
			Log.i("source", source);
			Log.i("dest", dest);
			Log.i("user_id2", user_id2);
			Log.i("是不是", "" + type.equals("B"));
			if (type.equals("B") && code == 30
					&& ((dest.equals(user_id) || source.equals(user_id)))) {
				JSONMessageEntity messageentity = new JSONMessageEntity();
				messageentity.setTime(time);
				messageentity.setContent(content);
				messageentity.setSources(source);
				messageList.add(messageentity);
			}
		}
		return messageList;
	}

	public ArrayList<JSONMessageEntity> getCurrentQuanMessageList(String quan_id) {
		int code;
		String type, source, dest, content;
		String time;
		ArrayList<JSONMessageEntity> messageList = new ArrayList<JSONMessageEntity>();
		myHelper = new DatabaseHelper(context);
		SQLiteDatabase db = myHelper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.message_table, new String[] {
				"type", "code", "time", "source", "dest", "content" }, null,
				null, null, null, null);
		int typeIndex = cursor.getColumnIndex("type");
		int codeIndex = cursor.getColumnIndex("code");
		int timeIndex = cursor.getColumnIndex("time");
		int sourceIndex = cursor.getColumnIndex("source");
		int destIndex = cursor.getColumnIndex("dest");
		int contentIndex = cursor.getColumnIndex("content");

		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			type = cursor.getString(typeIndex);
			code = cursor.getInt(codeIndex);
			source = cursor.getString(sourceIndex);
			dest = cursor.getString(destIndex);
			content = cursor.getString(contentIndex);
			time = cursor.getString(timeIndex);
			if (type.equals("B") && code == 31
					&& (source.equals(quan_id) || dest.equals(quan_id))) {
				JSONMessageEntity messageentity = new JSONMessageEntity();
				messageentity.setTime(time);
				messageentity.setContent(content);
				messageentity.setSources(source);
				messageList.add(messageentity);
			}

		}
		return messageList;
	}

	// 获取数据库里面所有的未读数据
	public ArrayList<JSONMessageEntity> getAllUnreadMessageEntity() {
		ArrayList<JSONMessageEntity> messageentitylist = new ArrayList<JSONMessageEntity>();
		int code;
		String type;
		String readflag;
		String time;
		String source;
		String dest;
		String content;
		myHelper = new DatabaseHelper(context);
		SQLiteDatabase db = myHelper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.message_table, new String[] {
				"type", "code", "time", "source", "dest", "content",
				"read_flag" }, null, null, null, null, null);
		int typeIndex = cursor.getColumnIndex("type");
		int codeIndex = cursor.getColumnIndex("code");
		int timeIndex = cursor.getColumnIndex("time");
		int sourceIndex = cursor.getColumnIndex("source");
		int destIndex = cursor.getColumnIndex("dest");
		int contentIndex = cursor.getColumnIndex("content");
		int readflagIndex = cursor.getColumnIndex("read_flag");
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			type = cursor.getString(typeIndex);
			code = cursor.getInt(codeIndex);
			time = cursor.getString(timeIndex);
			content = cursor.getString(contentIndex);
			dest = cursor.getString(destIndex);
			source = cursor.getString(sourceIndex);
			readflag = cursor.getString(readflagIndex);
			if (readflag.equals("false")) {
				JSONMessageEntity messageentity = new JSONMessageEntity();
				messageentity.setTime(time);
				messageentity.setContent(content);
				messageentity.setSources(source);
				messageentity.setType(type);
				messageentity.setDest(dest);
				messageentity.setCode(code);
				messageentitylist.add(messageentity);
			}
		}
		return messageentitylist;
	}

	// 获取所有的朋友的未读消息
	public int getAllUnreadFriendsMessageCount() {
		int code;
		String type;
		String readflag;
		int friendcount = 0;
		myHelper = new DatabaseHelper(context);
		SQLiteDatabase db = myHelper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.message_table, new String[] {
				"type", "code", "read_flag" }, null, null, null, null, null);
		int typeIndex = cursor.getColumnIndex("type");
		int codeIndex = cursor.getColumnIndex("code");
		int readflagIndex = cursor.getColumnIndex("read_flag");
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			type = cursor.getString(typeIndex);
			code = cursor.getInt(codeIndex);
			readflag = cursor.getString(readflagIndex);
			if (type.equals("B") && code == 30 && readflag.equals("false")) {
				friendcount++;
			}
		}
		return friendcount;
	}

	// 获取所有的朋友的未读消息
	public int getSpcificFriendsMessageCount(String user_id) {
		int code;
		String type, readflag, source;
		int time;
		int count = 0;
		myHelper = new DatabaseHelper(context);
		SQLiteDatabase db = myHelper.getWritableDatabase();
		Cursor cursor = db.query(DatabaseHelper.message_table, new String[] {
				"type", "code", "source", "read_flag" }, null, null, null,
				null, null);
		int sourceIndex = cursor.getColumnIndex("source");
		int typeIndex = cursor.getColumnIndex("type");
		int readflagIndex = cursor.getColumnIndex("content");
		int codeIndex = cursor.getColumnIndex("code");

		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			source = cursor.getString(sourceIndex);
			type = cursor.getString(typeIndex);
			readflag = cursor.getString(readflagIndex);
			code = cursor.getInt(codeIndex);
			if (type.equals("B") && code == 30 && source.equals(user_id)) {
				count++;
			}
		}
		return 0;
	}
}
