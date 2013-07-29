package com.example.freebox.push;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.android.common.logging.Log;
import com.baidu.android.pushservice.PushConstants;
import com.example.freebox.Main;
import com.example.freebox.ChatActivity;
import com.example.freebox.MessageCountActivity;
import com.example.freebox.Welcome;
import com.example.freebox.data.JSONMessageEntity;
import com.example.freebox.utils.DataBaseHandler;
import com.example.freebox.utils.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class DataHandler extends Handler {

	public static final int HERRORCODE = -1;
	public static final int HACTIONRESPONSE = 1;
	public static final int HMESSAGERESPONSE = 2;
	public static final String MESSAGE_TYPE_SYSTEM = "A";
	public static final String MESSAGE_TYPE_CHAT = "B";
	public static final int MESSAGET_CODE_GROUP_TALK = 31;
	public static final int MESSAGET_CODE_PERSONAL_TALK = 30;
	public static final int MESSAGET_CODE_NOTIFICATION = 1;
	public static final int MESSAGET_GROUP_NOTIFICATION = 2;
	public static final int MESSAGET_USER_NOTIFICATION = 3;
	public static Main MainActivity = null;
	public static ChatActivity ChatActivity = null;
	public static MessageCountActivity MessageCountActivity = null;
	// public static MainActivity mainactivity=null;

	private String thisclass = this.getClass().getSimpleName();
	// 单一模式
	private static DataHandler datahandler = null;

	public static DataHandler getInstance() {
		if (datahandler == null) {
			datahandler = new DataHandler();
		}
		return datahandler;
	}

	@Override
	public void handleMessage(Message msg) {
		String result = null;

		try {
			switch (msg.what) {
			case HMESSAGERESPONSE:// 收到消息
				result = msg.getData().getString("msg");

				DispatchMessage(result);

				Log.i(Utils.TAG, thisclass + "received message:" + result);
				// if(mainactivity!=null){
				// mainactivity.txtContent.append("\nreceived message:"+result);
				// mainactivity.txtContent.invalidate();
				// }
				break;
			case HACTIONRESPONSE:// 收到服务信息
				// if(mainactivity!=null){
				// SharedPreferences
				// sp=PreferenceManager.getDefaultSharedPreferences(mainactivity);
				// String appid=sp.getString("appid", "");
				// String channelid=sp.getString("channelid", "");
				// String userid=sp.getString("userid", "");
				// mainactivity.txtContent.setText("appid:"+appid+"\nchannelid:"+channelid+"\nuserid:"+userid);
				// mainactivity.txtContent.invalidate();
				// }
				break;
			case HERRORCODE:// 收到错误信息
				result = msg.getData().getString("error");
				// if(mainactivity!=null){
				// Toast.makeText(mainactivity, result,
				// Toast.LENGTH_LONG).show();
				// }
				Log.i(Utils.TAG, thisclass + "received message:" + result);
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分发消息
	 * 
	 * @param msg
	 *            ：json格式的消息
	 */
	private void DispatchMessage(String msg) {

		JSONMessageEntity mItem;
		try {
			mItem = JsonObjectFactory.parseJsonMessage(msg);
			if (mItem.getType().compareTo("A") == 0) {
				switch (mItem.getCode()) {
				case MESSAGET_CODE_NOTIFICATION:
					// setPushMessage
					break;
				case MESSAGET_GROUP_NOTIFICATION:
					break;
				case MESSAGET_USER_NOTIFICATION:
					break;
				default:
					break;
				}
			} else if (mItem.getType().compareTo("B") == 0) {
				if (ChatActivity != null) {
					// 聊天界面实时更新界面，消息存入数据库（已读）
					int code = mItem.getCode();
					if ((ChatActivity.chat_type.equals("multi") && code == MESSAGET_CODE_GROUP_TALK)
							&&String.valueOf(ChatActivity.getmQuanId()).equals(mItem.getDest())
							|| (ChatActivity.chat_type.equals("two") && code == MESSAGET_CODE_PERSONAL_TALK)
							&&String.valueOf(ChatActivity.getMguid()).equals(mItem.getSources())) {
						ChatActivity.setPushMessage(mItem);
						new DataBaseHandler(ChatActivity, null)
								.insertToMessageTable(mItem, "true");
					} else {
						new DataBaseHandler(ChatActivity, null)
								.insertToMessageTable(mItem, "false");
					}
				} else if (MainActivity != null) {
					// 主界面，实时更新消息统计数字，消息存入数据库（未读）
					MainActivity.setPushMessage(mItem);
					new DataBaseHandler(MainActivity, null)
							.insertToMessageTable(mItem, "false");
				} else if (MessageCountActivity != null) {
					// 二级消息界面，实时更新消息统计数字，消息存入数据库（未读）
					MessageCountActivity.setPushMessage(mItem);
					new DataBaseHandler(MessageCountActivity, null)
							.insertToMessageTable(mItem, "false");
				} else {
					new DataBaseHandler(MainActivity, null)
							.insertToMessageTable(mItem, "false");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 解析Json消息，根据不同的消息，分割消息内容 类 型 消息编码 时间戳 消息源 消息目的 消息内容 Type Code Time Source
	 * Dest Content
	 * 
	 * 类型：系统消息：A；即时聊天信息：B 消息编码： 系统广播通知消息 1(暂时不处理) 圈圈系统消息 2（暂时不处理） 用户系统消息
	 * 3（暂时不处理） 点对点聊天 30 圈圈群聊 31 时间戳：服务器推送消息的时间 消息源：（群发）系统管理员id；（点对点）用户id
	 * 消息目的：（群发）圈圈id；（点对点）用户id 消息内容：消息字符串
	 */

	// 点对点聊天消息处理
	// 条件：该用户的聊天界面处于活动状态，
	// 1、在该聊天界面显示内容，
	// 2、未读标记设为已读,将消息写入数据库
	// 条件：该用户的聊天界面不处于活动状态
	// 条件1.消息主界面或消息好友列表处于活动状态，
	// 1、未读标记设为未读，消息写入数据库
	// 2、消息主界面或消息好友列表的新消息数加1
	// 条件2消息主界面或消息好友列表不处于活动状态
	// 1、未读标记设为未读，消息写入数据库

	// 圈圈群聊消息处理
	// 条件：该群的聊天界面处于活动状态，
	// 1、在该聊天界面显示内容，
	// 2、未读标记设为已读,将消息写入数据库
	// 条件：该群的聊天界面不处于活动状态
	// 条件1.消息主界面或消息我的圈圈处于活动状态，
	// 1、未读标记设为未读，消息写入数据库
	// 2、消息主界面或消息我的圈圈的新消息数加1
	// 条件2消息主界面或消息我的圈圈不处于活动状态
	// 1、未读标记设为未读，消息写入数据库

}