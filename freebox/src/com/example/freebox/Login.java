package com.example.freebox;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.freebox.Register.DataGetTask;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private EditText mUser; // �ʺű༭��
	private EditText mPassword; // ����༭��
	private HttpClientEntity mClient;
	private DataGetTask task;
	private UrlEncodedFormEntity paramsEntity = null;
	private boolean mLoginFlag = false;
	private static final int MSG_SUCCESS = 1;// ��ȡ�ɹ�
	private static final int MSG_FAILURE = -1;// ��ȡʧ��
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {// �˷�����ui�߳�����
			switch (msg.what) {
			case MSG_SUCCESS:
				Intent intent = new Intent();
				intent.setClass(Login.this, MainWeixin.class);
				startActivity(intent);
				Toast.makeText(getApplicationContext(), "��¼�ɹ�",
						Toast.LENGTH_SHORT).show();
				break;
			case MSG_FAILURE:
				new AlertDialog.Builder(Login.this)
						.setIcon(
								getResources().getDrawable(
										R.drawable.login_error_icon))
						.setTitle("��¼����").setMessage("freebox�ʺŻ��������\n���������룡")
						.create().show();
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mUser = (EditText) findViewById(R.id.login_user_edit);
		mPassword = (EditText) findViewById(R.id.login_passwd_edit);
		mClient = new HttpClientEntity();

	}

	public void login_mainpage(View v) {
		// �ֽ���
		task = new DataGetTask();
		task.execute(APILinkEntity.mBasicAPI);
	}

	public void login_back(View v) { // ������ ���ذ�ť
		this.finish();
	}

	public void reset_password(View v) { // �������밴ť
		Intent intent = new Intent();
		intent.setClass(this, PasswordReset.class);
		startActivity(intent);
	}

	public class DataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String result = null;
			Log.i("��ʼ��̨��ȡ", "��ʼtask");
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("method",
						APILinkEntity.mUserLoginMethod));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("u", mUser.getText()
						.toString()));
				params1.add(new BasicNameValuePair("p", mPassword.getText()
						.toString()));
				try {
					paramsEntity = new UrlEncodedFormEntity(params1, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = mClient.PostData(arg0[0], paramsEntity);
				Log.i("�����ִ", result);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			try {
				JSONObject mJSONObject = new JSONObject(result);
				String r = mJSONObject.getString("result");
				Log.i("JSON", r);
				JSONObject mJSONState = new JSONObject(r);
				int flag = mJSONState.getInt("s");
				if (flag == 1) {
					String username = mUser.getText().toString();
					String token = mJSONState.getString("t");
					Log.i("JSON", token);
					SharedPreferences sharedPreferences = getSharedPreferences(
							"user_config", Context.MODE_PRIVATE);
					Editor editor = sharedPreferences.edit();// ��ȡ�༭��
					editor.putString("auth_token", token);
					editor.putString("user_name", username);
					Log.i("�û���",username);
					editor.commit();// �ύ�޸�
					Toast.makeText(Login.this, token, Toast.LENGTH_LONG).show();
					// �����Ƿ��Ѿ�����sharepreference
					SharedPreferences sharedPreferences2 = getSharedPreferences(
							"user_config", Context.MODE_PRIVATE);
					// getString()�ڶ�������Ϊȱʡֵ�����preference�в����ڸ�key��������ȱʡֵ
					String token2 = sharedPreferences2.getString("auth_token",
							"��");
					mLoginFlag = true;
					Message message = new Message();
					message.what = MSG_SUCCESS;
					mHandler.sendMessage(message);
				} else if (flag == -1) {
					mLoginFlag = false;
					Message message = new Message();
					message.what = MSG_FAILURE;
					mHandler.sendMessage(message);
					Log.i("JSON", mJSONState.getString("m"));
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
