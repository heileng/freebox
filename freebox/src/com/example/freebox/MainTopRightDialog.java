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

import com.example.freebox.AddFriendActivity.DataGetTask;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainTopRightDialog extends Activity {
	// private MyDialog dialog;
	private LinearLayout layout;
	private TextView item1, item2, item3, item4, item5, item6;
	private ImageView img1, img2, img3, img4, img5, img6;
	private String type;
	private int type_flag;
	private int QUAND = 0;
	private int FRIENDD = 1;
	private int ADDRESSD = 2;
	private int REMOVEFRIEND = 0;
	private int ADDFRIENDNOTE = 1;
	private int CURRENTCASE;
	private String REMOVEFRIEND_S = "remove";
	private String ADDFRIENDNOTE_S = "addnote";

	// 删除好友
	private UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient = new HttpClientEntity();;
	private DataGetTask task;
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_top_right_dialog);
		layout = (LinearLayout) findViewById(R.id.main_dialog_layout);
		item1 = (TextView) findViewById(R.id.dialog_item1);
		item2 = (TextView) findViewById(R.id.dialog_item2);
		item3 = (TextView) findViewById(R.id.dialog_item3);
		item4 = (TextView) findViewById(R.id.dialog_item4);
		item5 = (TextView) findViewById(R.id.dialog_item5);
		item6 = (TextView) findViewById(R.id.dialog_item6);
		img1 = (ImageView) findViewById(R.id.imageView1);
		img2 = (ImageView) findViewById(R.id.imageView2);
		img3 = (ImageView) findViewById(R.id.imageView3);
		img4 = (ImageView) findViewById(R.id.imageView4);
		img5 = (ImageView) findViewById(R.id.imageView5);
		img6 = (ImageView) findViewById(R.id.imageView6);
		Intent intent = getIntent();
		type = intent.getStringExtra("dialog_type");
		name = intent.getStringExtra("name");
		setDialogText();
		Log.i("type", "" + type_flag);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
	}

	public void setDialogText() {

		if (type.equals("quanquan_dialog")) {
			type_flag = QUAND;
			item1.setText("圈圈信息");
			item2.setText("圈圈活动");
			item3.setText("圈圈号码");
			item4.setText("圈圈大佬");
			item5.setText("圈圈公告");
			item6.setText("圈圈成员");
		} else if (type.equals("friend_dialog")) {
			type_flag = FRIENDD;
			item1.setText("发送名片");
			item2.setText("添加备注");
			item3.setText("加入黑名单");
			item4.setText("删除好友");
			item5.setText("赠送魅力道具");
			item6.setVisibility(View.GONE);
			img6.setVisibility(View.GONE);
		} else if (type.equals("address_dialog")) {
			type_flag = ADDRESSD;
			item1.setText("查找用户");
			item2.setText("添加好友");
			item3.setText("新建圈圈");
			item4.setText("查找圈圈");
			item5.setVisibility(View.GONE);
			item6.setVisibility(View.GONE);
			img5.setVisibility(View.GONE);
			img6.setVisibility(View.GONE);
		}
	}

	public void item1_fun(View v) {
		switch (type_flag) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			Intent intent =new Intent();
			intent.setClass(MainTopRightDialog.this, SearchUserActivity.class);
			startActivity(intent);
			Toast.makeText(this, "查找用户", Toast.LENGTH_LONG).show();
			break;
		}
	}

	public void item2_fun(View v) {
		switch (type_flag) {
		case 0:
			break;
		case 1:
			task = new DataGetTask();
			task.execute(APILinkEntity.mBasicAPI, ADDFRIENDNOTE_S);
			Toast.makeText(this, "添加备注", Toast.LENGTH_LONG).show();
			break;
		case 2:
			Intent intent = new Intent();
			intent.setClass(MainTopRightDialog.this, AddFriendActivity.class);
			startActivity(intent);
			Toast.makeText(this, "添加好友", Toast.LENGTH_LONG).show();
			break;
		}
	}

	public void item3_fun(View v) {
		switch (type_flag) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			Intent intent=new Intent();
			intent.setClass(MainTopRightDialog.this, NewQuanActivity.class);
			startActivity(intent);
			Toast.makeText(this, "新建圈圈", Toast.LENGTH_LONG).show();
			break;
		}
	}

	public void item4_fun(View v) {
		switch (type_flag) {
		case 0:
			break;
		case 1:
			task = new DataGetTask();
			task.execute(APILinkEntity.mBasicAPI, REMOVEFRIEND_S);
			Toast.makeText(MainTopRightDialog.this, "删除好友", Toast.LENGTH_LONG)
					.show();
			;
			break;
		case 2:
			Intent intent=new Intent();
			intent.setClass(MainTopRightDialog.this, SearchQuanActivity.class);
			startActivity(intent);
			Toast.makeText(this, "查找圈圈", Toast.LENGTH_LONG).show();
			break;
		}
	}

	public void item5_fun(View v) {
		switch (type_flag) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		}
	}

	public void item6_fun(View v) {
		switch (type_flag) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public class DataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String result = null;
			Log.i("开始后台获取", "开始task");
			try {
				if (arg0[1].equals(REMOVEFRIEND_S)) {
					CURRENTCASE = REMOVEFRIEND;
				} else if (arg0[1].equals(ADDFRIENDNOTE_S)) {
					CURRENTCASE = ADDFRIENDNOTE;
				}
				switch (CURRENTCASE) {
				case 0:
					
					//删除好友
					SharedPreferences sharedPreferences = getSharedPreferences(
							"user_config", Context.MODE_PRIVATE);
					String token = sharedPreferences.getString("auth_token",
							"none");
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
					params1.add(new BasicNameValuePair("method",
							APILinkEntity.mDeleteFriendMethod));
					params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
					params1.add(new BasicNameValuePair("auth_token", token));
					params1.add(new BasicNameValuePair("t", token));
					params1.add(new BasicNameValuePair("friend", "17777777777"));
					try {
						paramsEntity = new UrlEncodedFormEntity(params1,
								"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					result = mClient.PostData(arg0[0], paramsEntity);
					Log.i("输出回执", result);
					break;
				case 1:
					//添加好友
					SharedPreferences sharedPreferences2 = getSharedPreferences(
							"user_config", Context.MODE_PRIVATE);
					String token2 = sharedPreferences2.getString("auth_token",
							"none");
					List<NameValuePair> params2 = new ArrayList<NameValuePair>();
					params2.add(new BasicNameValuePair("method",
							APILinkEntity.mRemarkFriendsMethod));
					params2.add(new BasicNameValuePair("api_key", Flags.APIKEY));
					params2.add(new BasicNameValuePair("auth_token", token2));
					params2.add(new BasicNameValuePair("t", token2));
					params2.add(new BasicNameValuePair("fguid", "17777777777"));
					params2.add(new BasicNameValuePair("remark", "stu"));
					try {
						paramsEntity = new UrlEncodedFormEntity(params2,
								"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					result = mClient.PostData(arg0[0], paramsEntity);
					Log.i("输出回执", result);
					break;
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

			try {
				JSONObject mJSONObject;
				mJSONObject = new JSONObject(result);
				String resultdata = mJSONObject.getString("result");
				JSONObject mResultobject = new JSONObject(resultdata);
				String data = mResultobject.getString("s");
				if (data.equals("1")) {
					Toast.makeText(MainTopRightDialog.this,
							mResultobject.getString("m"), Toast.LENGTH_LONG)
							.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
