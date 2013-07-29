package com.example.freebox.push;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.GetChars;
import android.util.Log;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.example.freebox.utils.Utils;

/**
 * Push��Ϣ����receiver
 */
public class PushMessageReceiver extends BroadcastReceiver {
	/** TAG to Log */
	private String thisclass=this.getClass().getSimpleName();


	/**
	 * 
	 * 
	 * @param context
	 *            Context
	 * @param intent
	 *            ���յ�intent
	 */
	@Override
	public void onReceive(final Context context, Intent intent) {

		Log.d(Utils.TAG, thisclass+">>> Receive intent: \r\n" + intent);
		
		Message msg=new Message();
		Bundle bnd=new Bundle();
		
		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
			//��ȡ��Ϣ����
			String message = intent.getExtras().getString(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
			message=decodeUnicode(message);
			Log.i(Utils.TAG, thisclass+"received message:"+message);
			//�û��ڴ��Զ��崦����Ϣ,���´���Ϊdemo����չʾ��	
			msg.what=DataHandler.HMESSAGERESPONSE;
			bnd.putString("msg", message);
			msg.setData(bnd);
			DataHandler.getInstance().sendMessage(msg);
		} else if (intent.getAction().equals(PushConstants.ACTION_RECEIVE)) {
			//����󶨵ȷ����ķ�������
			//PushManager.startWork()�ķ���ֵͨ��PushConstants.METHOD_BIND�õ�
			
			//��ȡ����
			final String method = intent.getStringExtra(PushConstants.EXTRA_METHOD);
			//�������ش����롣���󶨷��ش��󣨷�0������Ӧ�ý���������������Ϣ��
			//��ʧ�ܵ�ԭ���ж��֣�������ԭ�򣬻�access token���ڡ�
			//�벻Ҫ�ڳ���ʱ���м򵥵�startWork���ã����п��ܵ�����ѭ����
			//����ͨ���������Դ���������������ʱ�����µ����������
			final int errorCode = intent.getIntExtra(PushConstants.EXTRA_ERROR_CODE,PushConstants.ERROR_SUCCESS);
			//��������
			final String content = new String(intent.getByteArrayExtra(PushConstants.EXTRA_CONTENT));
			
			//�û��ڴ��Զ��崦����Ϣ,���´���Ϊdemo����չʾ��	
			Log.d(Utils.TAG, thisclass+ " onMessage: method : " + method);
			Log.d(Utils.TAG, thisclass+" onMessage: result : " + errorCode);
			Log.d(Utils.TAG, thisclass+" onMessage: content : " + content);
			if (errorCode == 0) {
				//��������
				String appid="";
				String channelid="";
				String userid="";
				try {
					JSONObject jsonContent = new JSONObject(content);
					JSONObject params = jsonContent.getJSONObject("response_params");
					appid = params.getString("appid");
					channelid = params.getString("channel_id");
					userid = params.getString("user_id");
					
					SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(context);
					Editor edt=sp.edit();
					edt.putString("appid", appid);
					edt.putString("channelid", channelid);
					edt.putString("userid", userid);
					edt.commit();
				} catch (JSONException e) {
					Log.e(Utils.TAG, "Parse bind json infos error: " + e);
				}
				msg.what=DataHandler.HACTIONRESPONSE;
				DataHandler.getInstance().sendMessage(msg);
				
			} else {
				//���󷵻�
				msg.what=DataHandler.HERRORCODE;
				bnd.putString("error", String.valueOf(errorCode));
				Log.d(Utils.TAG, thisclass+" error code:  " + content);
				if (errorCode == 30607) {
					Log.d("Bind Fail", "update channel token-----!");
				}
			}
		}
	}
	private String decodeUnicode(String theString) {  
	    char aChar;  
	    int len = theString.length();  
	    StringBuffer outBuffer = new StringBuffer(len);  
	    for (int x = 0; x < len;) {  
	        aChar = theString.charAt(x++);  
	        if (aChar == '\\') {  
	            aChar = theString.charAt(x++);  
	            if (aChar == 'u') {  
	                // Read the xxxx   
	                int value = 0;  
	                for (int i = 0; i < 4; i++) {  
	                    aChar = theString.charAt(x++);  
	                    switch (aChar) {  
	                    case '0':  
	                    case '1':  
	                    case '2':  
	                    case '3':  
	                    case '4':  
	                    case '5':  
	                    case '6':  
	                    case '7':  
	                    case '8':  
	                    case '9':  
	                        value = (value << 4) + aChar - '0';  
	                        break;  
	                    case 'a':  
	                    case 'b':  
	                    case 'c':  
	                    case 'd':  
	                    case 'e':  
	                    case 'f':  
	                        value = (value << 4) + 10 + aChar - 'a';  
	                        break;  
	                    case 'A':  
	                    case 'B':  
	                    case 'C':  
	                    case 'D':  
	                    case 'E':  
	                    case 'F':  
	                        value = (value << 4) + 10 + aChar - 'A';  
	                        break;  
	                    default:  
	                        throw new IllegalArgumentException(  
	                                "Malformed   \\uxxxx   encoding.");  
	                    }  
	  
	                }  
	                outBuffer.append((char) value);  
	            } else {  
	                if (aChar == 't')  
	                    aChar = '\t';  
	                else if (aChar == 'r')  
	                    aChar = '\r';  
	                else if (aChar == 'n')  
	                    aChar = '\n';  
	                else if (aChar == 'f')  
	                    aChar = '\f';  
	                outBuffer.append(aChar);  
	            }  
	        } else  
	            outBuffer.append(aChar);  
	    }  
	    return outBuffer.toString();  
	}  
}
