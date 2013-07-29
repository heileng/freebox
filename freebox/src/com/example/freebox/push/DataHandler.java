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
	// ��һģʽ
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
			case HMESSAGERESPONSE:// �յ���Ϣ
				result = msg.getData().getString("msg");

				DispatchMessage(result);

				Log.i(Utils.TAG, thisclass + "received message:" + result);
				// if(mainactivity!=null){
				// mainactivity.txtContent.append("\nreceived message:"+result);
				// mainactivity.txtContent.invalidate();
				// }
				break;
			case HACTIONRESPONSE:// �յ�������Ϣ
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
			case HERRORCODE:// �յ�������Ϣ
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
	 * �ַ���Ϣ
	 * 
	 * @param msg
	 *            ��json��ʽ����Ϣ
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
					// �������ʵʱ���½��棬��Ϣ�������ݿ⣨�Ѷ���
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
					// �����棬ʵʱ������Ϣͳ�����֣���Ϣ�������ݿ⣨δ����
					MainActivity.setPushMessage(mItem);
					new DataBaseHandler(MainActivity, null)
							.insertToMessageTable(mItem, "false");
				} else if (MessageCountActivity != null) {
					// ������Ϣ���棬ʵʱ������Ϣͳ�����֣���Ϣ�������ݿ⣨δ����
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
	 * ����Json��Ϣ�����ݲ�ͬ����Ϣ���ָ���Ϣ���� �� �� ��Ϣ���� ʱ��� ��ϢԴ ��ϢĿ�� ��Ϣ���� Type Code Time Source
	 * Dest Content
	 * 
	 * ���ͣ�ϵͳ��Ϣ��A����ʱ������Ϣ��B ��Ϣ���룺 ϵͳ�㲥֪ͨ��Ϣ 1(��ʱ������) ȦȦϵͳ��Ϣ 2����ʱ������ �û�ϵͳ��Ϣ
	 * 3����ʱ������ ��Ե����� 30 ȦȦȺ�� 31 ʱ�����������������Ϣ��ʱ�� ��ϢԴ����Ⱥ����ϵͳ����Աid������Ե㣩�û�id
	 * ��ϢĿ�ģ���Ⱥ����ȦȦid������Ե㣩�û�id ��Ϣ���ݣ���Ϣ�ַ���
	 */

	// ��Ե�������Ϣ����
	// ���������û���������洦�ڻ״̬��
	// 1���ڸ����������ʾ���ݣ�
	// 2��δ�������Ϊ�Ѷ�,����Ϣд�����ݿ�
	// ���������û���������治���ڻ״̬
	// ����1.��Ϣ���������Ϣ�����б��ڻ״̬��
	// 1��δ�������Ϊδ������Ϣд�����ݿ�
	// 2����Ϣ���������Ϣ�����б������Ϣ����1
	// ����2��Ϣ���������Ϣ�����б����ڻ״̬
	// 1��δ�������Ϊδ������Ϣд�����ݿ�

	// ȦȦȺ����Ϣ����
	// ��������Ⱥ��������洦�ڻ״̬��
	// 1���ڸ����������ʾ���ݣ�
	// 2��δ�������Ϊ�Ѷ�,����Ϣд�����ݿ�
	// ��������Ⱥ��������治���ڻ״̬
	// ����1.��Ϣ���������Ϣ�ҵ�ȦȦ���ڻ״̬��
	// 1��δ�������Ϊδ������Ϣд�����ݿ�
	// 2����Ϣ���������Ϣ�ҵ�ȦȦ������Ϣ����1
	// ����2��Ϣ���������Ϣ�ҵ�ȦȦ�����ڻ״̬
	// 1��δ�������Ϊδ������Ϣд�����ݿ�

}