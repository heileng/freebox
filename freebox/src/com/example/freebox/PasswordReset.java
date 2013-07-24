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

import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordReset extends BaseActivity {

	private TextView mUserPhone;
	private TextView mUserPasswdOrigin;
	private TextView mUserPasswdNew;
	private TextView mUserPasswdNewC;
	private Button mBtnReset;
	private Button mBtnChange;
	private DataGetTask task;
	private HttpClientEntity mClient;
	private UrlEncodedFormEntity paramsEntity = null;
	private boolean buttonflag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.reset_password_layout);
		// 编辑框
		mUserPhone = (TextView) findViewById(R.id.phonenumber_edit);
		mUserPasswdOrigin = (TextView) findViewById(R.id.passwd_origin_edit);
		mUserPasswdNew = (TextView) findViewById(R.id.passwd_new_edit);
		mUserPasswdNewC = (TextView) findViewById(R.id.passwd_new_repeat_edit);
		// 按钮
		mClient = new HttpClientEntity();
		mBtnReset = (Button) findViewById(R.id.reset_btn);
		mBtnReset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				task = new DataGetTask();
				task.execute(APILinkEntity.mBasicAPI, "reset");
			}
		});
		mBtnChange = (Button) findViewById(R.id.change_btn);
		mBtnChange.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String np = mUserPasswdNew.getText().toString();
				String npc = mUserPasswdNewC.getText().toString();
				if (np.equals(npc)) {
					task = new DataGetTask();
					task.execute(APILinkEntity.mBasicAPI, "change");
				} else {
					new AlertDialog.Builder(PasswordReset.this)
							.setIcon(
									getResources().getDrawable(
											R.drawable.login_error_icon))
							.setTitle("密码重复错误").setMessage("请重新确认密码").create()
							.show();
				}
			}
		});

		setTitle("重置密码");
	}

	public class DataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String result = null;
			Log.i("开始后台获取", "开始task");
			try {
				if (arg0[1].equals("reset")) {
					buttonflag = false;
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
					params1.add(new BasicNameValuePair("method",
							APILinkEntity.mPasswordResetMethod));
					params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
					params1.add(new BasicNameValuePair("u", mUserPhone
							.getText().toString()));
					try {
						paramsEntity = new UrlEncodedFormEntity(params1,
								"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					result = mClient.PostData(arg0[0], paramsEntity);
					Log.i("输出回执", result);
				} else if (arg0[1].equals("change")) {
					buttonflag = true;
					SharedPreferences sharedPreferences = getSharedPreferences(
							"user_config", Context.MODE_PRIVATE);
					String token = sharedPreferences.getString("auth_token",
							"none");
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
					params1.add(new BasicNameValuePair("method",
							APILinkEntity.mPasswordChangeMethod));
					params1.add(new BasicNameValuePair("auth_token", token));
					params1.add(new BasicNameValuePair("t", token));
					params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
					params1.add(new BasicNameValuePair("np", mUserPasswdNew
							.getText().toString()));
					params1.add(new BasicNameValuePair("op", mUserPasswdOrigin
							.getText().toString()));
					try {
						paramsEntity = new UrlEncodedFormEntity(params1,
								"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					result = mClient.PostData(arg0[0], paramsEntity);
					Log.i("输出回执", result);
				}
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
				if (buttonflag == false) {

					JSONObject data = new JSONObject(result);
					JSONObject passdata = new JSONObject(
							data.getString("result"));
					String newpassword = passdata.getString("p");
					new AlertDialog.Builder(PasswordReset.this)
							.setIcon(
									getResources().getDrawable(
											R.drawable.login_error_icon))
							.setTitle("密码找回").setMessage(newpassword).create()
							.show();
				} else if (buttonflag == true) {
					JSONObject data = new JSONObject(result);
					JSONObject passdata = new JSONObject(
							data.getString("result"));
					String flag = passdata.getString("s");
					if (flag.equals("1")) {
						new AlertDialog.Builder(PasswordReset.this)
								.setIcon(
										getResources().getDrawable(
												R.drawable.login_error_icon))
								.setTitle("密码修改").setMessage("修改成功").create()
								.show();
					} else if (flag.equals("-1")) {
						new AlertDialog.Builder(PasswordReset.this)
								.setIcon(
										getResources().getDrawable(
												R.drawable.login_error_icon))
								.setTitle("密码修改").setMessage("修改失败").create()
								.show();
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
