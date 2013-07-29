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

import com.bairuitech.demo.RoomActivity;
import com.example.freebox.Login.DataGetTask;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;
import com.example.freebox.entity.UserEntity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends BaseActivity {
	private TextView user_name, user_gender, user_constellation, user_phone,
			user_personalized_signature, user_location, user_intersts;
	private ImageView user_avatar;
	private UserEntity myEntity;
	public UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient;
	private DataGetTask task;
	private String fromflag;
	private String user_name_text;
	private String chat_type;
	private int user_guid;
	private Button send_msg_btn;
	private Button send_vid_btn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.profile_layout);
		setContentLayout(R.layout.profile_layout);
		// 初始化控件
		myEntity = new UserEntity();
		mClient = new HttpClientEntity();
		user_name = (TextView) findViewById(R.id.user_name_user_id);
		user_gender = (TextView) findViewById(R.id.gender);
		user_constellation = (TextView) findViewById(R.id.constellation);
		user_personalized_signature = (TextView) findViewById(R.id.personalized_signature);
		user_location = (TextView) findViewById(R.id.location);
		user_avatar = (ImageView) findViewById(R.id.user_avatar);
		user_intersts = (TextView) findViewById(R.id.intersts);
		

		// 提取intent信息
		Intent intent = getIntent();
		user_name_text = intent.getStringExtra("name");
		fromflag = intent.getStringExtra("from");
		user_guid = intent.getIntExtra("user_guid", 0);
		chat_type = intent.getStringExtra("chat_type");

		setTitle("详细信息");
		task = new DataGetTask();
		task.execute(APILinkEntity.mBasicAPI, fromflag, user_name_text);
		SharedPreferences sharedPreferences = getSharedPreferences(
				"user_config", Context.MODE_PRIVATE);
		String user_name = sharedPreferences.getString("user_name", "none");
		if (user_name_text.equals(user_name)) {
			setbtn_rightRes(R.drawable.mm_title_btn_edit);
		}
		getbtn_right().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String user_name = sharedPreferences.getString("user_name",
						"none");
				if (user_name_text.equals(user_name)) {
					Intent intent = new Intent();
					intent.setClass(ProfileActivity.this,
							MyProfileEditActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(ProfileActivity.this,
							MainTopRightDialog.class);
					intent.putExtra("name", user_name_text);
					intent.putExtra("dialog_type", "friend_dialog");
					startActivity(intent);
				}
			}
		});
	}

	public void head_xiaohei(View v) { // 头像按钮
		Intent intent = new Intent();
		intent.setClass(ProfileActivity.this, AvatarActivity.class);
		startActivity(intent);
	}
	public void sendMessage(View v){
		Intent intent=new Intent();
		intent.setClass(ProfileActivity.this, ChatActivity.class);
		intent.putExtra("user_guid", user_guid);
		intent.putExtra("chat_type", chat_type);
		intent.putExtra("name", user_name_text);
		startActivity(intent);
	}

	public void sendVideo(View v){
		Intent intent1=new Intent();
		intent1.setClass(ProfileActivity.this, RoomActivity.class);
		startActivity(intent1);
	}
	public class DataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String result = null;
			Log.i("开始后台获取", "开始task");
			try {

				if (arg0[1].equals(Flags.FromMe)) {
					SharedPreferences sharedPreferences = getSharedPreferences(
							"user_config", Context.MODE_PRIVATE);
					String token = sharedPreferences.getString("auth_token",
							"none");
					Log.i("第二次token", token);
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
					params1.add(new BasicNameValuePair("method",
							APILinkEntity.mUserDetailProfileMethod));
					params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
					params1.add(new BasicNameValuePair("auth_token", token));
					params1.add(new BasicNameValuePair("t", token));
					try {
						paramsEntity = new UrlEncodedFormEntity(params1,
								"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					result = mClient.PostData(arg0[0], paramsEntity);
					Log.i("输出回执", result);
				} else if (arg0[1].equals(Flags.FromFriend)) {
					SharedPreferences sharedPreferences = getSharedPreferences(
							"user_config", Context.MODE_PRIVATE);
					String token = sharedPreferences.getString("auth_token",
							"none");
					Log.i("第二次token", token);
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
					params1.add(new BasicNameValuePair("method",
							APILinkEntity.mFriendDetailProfileMethod));
					params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
					params1.add(new BasicNameValuePair("auth_token", token));
					params1.add(new BasicNameValuePair("t", token));
					params1.add(new BasicNameValuePair("fguid", user_name_text));
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

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (JSONException e) {

				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			try {
				JSONObject mJSONObject = new JSONObject(result);
				String data = mJSONObject.getString("result");
				Log.i("JSON", data);
				JSONObject mJSONData = new JSONObject(data);
				int flag = mJSONData.getInt("s");
				if (flag == 1) {
					String profile = mJSONData.getString("d");
					JSONObject mJSONProfile = new JSONObject(profile);
					String description = mJSONProfile.getString("description");
					String briefdescription = mJSONProfile
							.getString("briefdescription");
					String location = mJSONProfile.getString("location");
					String contactemail = mJSONProfile
							.getString("contactemail");
					String phone = mJSONProfile.getString("phone");
					String sex = mJSONProfile.getString("sex");
					String constellation = mJSONProfile
							.getString("constellation");
					String intersts = mJSONProfile.getString("interests");

					SharedPreferences sharedPreferences = getSharedPreferences(
							"user_config", Context.MODE_PRIVATE);
					String username = sharedPreferences.getString("user_name",
							"none");
					Log.i("取出用户名", username);
					if (username.equals(user_name_text)) {
						user_name.setText(username);
					}else{
						user_name.setText(user_name_text);
					}
					user_personalized_signature.setText(briefdescription);
					user_constellation.setText(constellation);
					user_gender.setText(sex);
					user_intersts.setText(intersts);
					user_location.setText(location);
					Log.i("个人资料", constellation + briefdescription
							+ contactemail + phone + location + sex
							+ description);
				} else if (flag == -1) {
					Toast.makeText(ProfileActivity.this, "获取信息错误",
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
