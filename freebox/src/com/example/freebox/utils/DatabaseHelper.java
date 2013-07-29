package com.example.freebox.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 默认就在数据库里创建4张表
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	public static  String name = "FREE_BOX_DB";// 数据库名称
	private static final int version = 1;// 数据库版本
	public static String user_profile_table="user_profile";
	public static String quan_profile_table="quan_profile";
	public static String message_table="message_table";

	public DatabaseHelper(Context context) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e("DBOpenHelper", "创建表");
		db.execSQL("CREATE TABLE IF NOT EXISTS message_table (type varchar(60), code int,time varchar(60),  source varchar(60),  dest varchar(60),  content varchar(60),read_flag varchar(60))");
		db.execSQL("CREATE TABLE IF NOT EXISTS user_profile (user_id integer primary key autoincrement,user_userid varchar(60),user_description varchar(60),user_name varchar(60), user_username varchar(60),user_briefdescription varchar(60),user_location varchar(60),user_phone varchar(60),user_constellation varchar(60),user_remark varchar(60),user_interests varchar(60),user_skills varchar(60),user_contactemail varchar(60),user_website varchar(60),user_gender varchar(60),user_avatar varchar(60))");
		db.execSQL("CREATE TABLE IF NOT EXISTS quan_profile (quan_id integer primary key autoincrement, quan_state varchar(60), quan_owner varchar(60),quan_activity varchar(60),quan_announcement varchar(60))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("DBOpenHelper", "更新表");
		db.execSQL("DROP TABLE IF EXISTS user_message");
		db.execSQL("DROP TABLE IF EXISTS quan_message");
		db.execSQL("DROP TABLE IF EXISTS user_profile");
		db.execSQL("DROP TABLE IF EXISTS quan_profile");
		onCreate(db);
	}
}
