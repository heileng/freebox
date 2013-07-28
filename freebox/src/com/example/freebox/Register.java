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

import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {
	private EditText mPassword; // 密码编辑框
	private EditText mEmail; // 邮箱
	private EditText mUserName; // 用户名
	private EditText mCfPassword;
	private TextView mAlertText;
	private EditText mPhoneNum;
	private HttpClientEntity mClient;
	private DataGetTask task;
	private UrlEncodedFormEntity paramsEntity = null;
	private String URL = "http://10.11.246.167/freebox/services/api/rest/json/";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		mUserName = (EditText) findViewById(R.id.register_username_edit);
		mEmail = (EditText) findViewById(R.id.register_email_edit);
		mPassword = (EditText) findViewById(R.id.register_passwd_edit);
		mCfPassword = (EditText) findViewById(R.id.confirm_passwd_edit);
		mAlertText = (TextView) findViewById(R.id.alert_text);
		mPhoneNum = (EditText) findViewById(R.id.register_phone_edit);
		mClient = new HttpClientEntity();

	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	public void register(View v) {

		if (mPassword.getText().toString()
				.equals(mCfPassword.getText().toString())) {
			// mClient = new HttpClientEntity();
			// String and = "&";
			// String url = URL + "?" + "method=user.register" + and + "name="
			// + mUser.getText().toString() + and + "username="
			// + mUserName.getText().toString() + and + "email="
			// + mEmail.getText().toString() + and + "password="
			// + mPassword.getText().toString()+and+"api_key="+Flags.APIKEY;
			// Log.i("打印地址", url);
			task = new DataGetTask();
			task.execute(URL);
		} else {
			mAlertText.setText("两次输入的密码不一致，请重新输入");
			mPassword.setText(null);
			mCfPassword.setText(null);
		}

	}

	public class DataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String result = null;
			Log.i("开始后台获取", "开始task");
			try {
				// HttpClientEntity cliententity = new HttpClientEntity();
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("method",
						APILinkEntity.mRegisterMethod));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("n", mUserName.getText()
						.toString()));
				params1.add(new BasicNameValuePair("e", mEmail.getText()
						.toString()));
				params1.add(new BasicNameValuePair("u", mPhoneNum.getText()
						.toString()));
				params1.add(new BasicNameValuePair("p", mPassword.getText()
						.toString()));
				SharedPreferences sp = PreferenceManager
						.getDefaultSharedPreferences(Register.this);
				String channelid = sp.getString("channelid", "none");
				String userid = sp.getString("userid", "none");
				String appid = sp.getString("appid", "none");
				Log.i("百度用户id", userid);
				Log.i("百度channelid", channelid);

				params1.add(new BasicNameValuePair("buid", userid));
				params1.add(new BasicNameValuePair("bcid", channelid));
				try {
					paramsEntity = new UrlEncodedFormEntity(params1, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = mClient.PostData(arg0[0], paramsEntity);
				Log.i("输出回执", result);
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
			Toast.makeText(Register.this, result, Toast.LENGTH_SHORT).show();
		}
	}
}
