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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MyProfileEditActivity extends BaseActivity {
	private static final String[] mGenderString = { "请选择性别", "男", "女" };
	private static final String[] mAvatarNumString = { "请选择头像编号", "1", "2",
			"3", "3", "4", "5" };
	private static final String[] mConstellationString = { "请选择星座", "白羊座",
			"金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座",
			"水瓶座", "双鱼座" };
	private Spinner mGenderSpinner;
	private ArrayAdapter<String> mGenderAdapter;
	private Spinner mConstellationSpinner;
	private ArrayAdapter<String> mConstellationAdapter;
	private Spinner mAvatarSpinner;
	private ArrayAdapter<String> mAvatarAdapter;
	private EditText mNikNameEdit;
	private EditText mAgeEdit;
	private EditText mLocationEdit;
	private EditText mInterstsEdit;
	private EditText mSkillsEdit;
	private EditText mContactEdit;
	private EditText mPhoneEdit;
	private EditText mOtherMobileEdit;
	private EditText mWebsiteEdit;

	// 网络获取
	private UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient = new HttpClientEntity();;
	private DataGetTask task;

	// private String[] mProfileArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("编辑我的资料");
		setContentLayout(R.layout.my_setting_layout);
		setbtn_rightRes(R.drawable.mm_title_btn_summit);
		// 初始化
		mNikNameEdit = (EditText) findViewById(R.id.nik_name_edit);
		mAgeEdit = (EditText) findViewById(R.id.age_edit);
		mLocationEdit = (EditText) findViewById(R.id.location_edit);
		mInterstsEdit = (EditText) findViewById(R.id.intersts_edit);
		mSkillsEdit = (EditText) findViewById(R.id.skills_edit);
		mContactEdit = (EditText) findViewById(R.id.contactmail_edit);
		mPhoneEdit = (EditText) findViewById(R.id.phone_edit);
		mOtherMobileEdit = (EditText) findViewById(R.id.mobile_edit);
		mWebsiteEdit = (EditText) findViewById(R.id.website_edit);
		getbtn_right().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String[] mProfileArray = {
						"\"name\":" + "\"" + mNikNameEdit.getText().toString()
								+ "\"",
						"\"briefdescription\":" + "\"" + "这是个性签名" + "\"",
						"\"location\":" + "\""
								+ mLocationEdit.getText().toString() + "\"",
						"\"interests\":" + "\""
								+ mInterstsEdit.getText().toString() + "\"",
						"\"skills\":" + "\"" + mSkillsEdit.getText().toString()
								+ "\"",
						"\"contactmail\":" + "\""
								+ mContactEdit.getText().toString() + "\"",
						"\"phone\":" + "\"" + mPhoneEdit.getText().toString()
								+ "\"",
						"\"mobile\":" + "\""
								+ mOtherMobileEdit.getText().toString() + "\"",
						"\"website\":" + "\""
								+ mWebsiteEdit.getText().toString() + "\"",
						"\"twitter\":" + "\"" + "翻墙" + "\"",
						"\"avatar\":" + "\""
								+ mAvatarSpinner.getSelectedItem().toString()
								+ "\"",
						"\"sex\":" + "\""
								+ mGenderSpinner.getSelectedItem().toString()
								+ "\"",
						"\"constellation\":"
								+ "\""
								+ mConstellationSpinner.getSelectedItem()
										.toString() + "\"" };
				String name = mProfileArray[0];
				String briefdescription = mProfileArray[1];
				String location = mProfileArray[2];
				String intersts = mProfileArray[3];
				String skills = mProfileArray[4];
				String contactmail = mProfileArray[5];
				String phone = mProfileArray[6];
				String mobile = mProfileArray[7];
				String website = mProfileArray[8];
				String twitter = mProfileArray[9];
				String avatar = mProfileArray[10];
				String sex = mProfileArray[11];
				String constellation = mProfileArray[12];
				task = new DataGetTask();
				task.execute(APILinkEntity.mBasicAPI, name, briefdescription,
						location, intersts, skills, contactmail, phone, mobile,
						website, twitter, avatar, sex, constellation);
				Toast.makeText(
						MyProfileEditActivity.this,
						"昵称："
								+ mNikNameEdit.getText().toString()
								+ "\n"
								+ "头像编号："
								+ mAvatarSpinner.getSelectedItem().toString()
								+ "\n"
								+ "性别："
								+ mGenderSpinner.getSelectedItem().toString()
								+ "\n"
								+ "年龄："
								+ mAgeEdit.getText().toString()
								+ "\n"
								+ "星座："
								+ mConstellationSpinner.getSelectedItem()
										.toString(), Toast.LENGTH_LONG).show();
			}

		});

		// 性别下拉列表
		mGenderSpinner = (Spinner) findViewById(R.id.gender_spinner);
		mGenderAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mGenderString);
		mGenderAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mGenderSpinner.setAdapter(mGenderAdapter);
		mGenderSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// m_tTextView.setText(m_Countries[arg2]);
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		// 头像下拉列表
		mAvatarSpinner = (Spinner) findViewById(R.id.avatar_num_spinner);
		mAvatarAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mAvatarNumString);
		mAvatarAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mAvatarSpinner.setAdapter(mAvatarAdapter);
		mAvatarSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// m_tTextView.setText(m_Countries[arg2]);
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		// 星座下拉列表
		mConstellationSpinner = (Spinner) findViewById(R.id.constellation_spinner);
		mConstellationAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mConstellationString);
		mConstellationAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mConstellationSpinner.setAdapter(mConstellationAdapter);
		mConstellationSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// m_tTextView.setText(m_Countries[arg2]);
						arg0.setVisibility(View.VISIBLE);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	public class DataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String result = null;
			Log.i("开始后台获取", "开始task");
			try {
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				// String[] p = { arg0[1], arg0[2], arg0[3], arg0[4], arg0[5],
				// arg0[6], arg0[7], arg0[8], arg0[9], arg0[10], arg0[11],
				// arg0[12], arg0[13] };
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("method",
						APILinkEntity.mSubmitUserProfileMethod));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("auth_token", token));
				params1.add(new BasicNameValuePair("t", token));
				// params1.add(new BasicNameValuePair("p", "" + p));
				String dot = ",";
				String mkuot = "[";
				params1.add(new BasicNameValuePair("p", "[" + "{" + arg0[1]
						+ dot + arg0[2] + dot + arg0[3] + dot + arg0[4] + dot
						+ arg0[5] + dot + arg0[6] + dot + arg0[7] + dot
						+ arg0[8] + dot + arg0[9] + dot + arg0[10] + dot
						+ arg0[11] + dot + arg0[12] + dot + arg0[13] + "}"
						+ "]"));
				Log.i("字符串啊", "[" + "{" + arg0[1] + dot + arg0[2] + dot
						+ arg0[3] + dot + arg0[4] + dot + arg0[5] + dot
						+ arg0[6] + dot + arg0[7] + dot + arg0[8] + dot
						+ arg0[9] + dot + arg0[10] + dot + arg0[11] + dot
						+ arg0[12] + dot + arg0[13] + "}" + "]");
				// params1.add(new BasicNameValuePair("p", "[" + "{" + arg0[1]
				// + "}" + dot + "{" + arg0[2] + "}" + dot + "{" + arg0[3]
				// + "}" + dot + "{" + arg0[4] + "}" + dot + "{" + arg0[5]
				// + "}" + dot + "{" + arg0[6] + "}" + dot + "{" + arg0[7]
				// + "}" + dot + "{" + arg0[8] + "}" + dot + "{" + arg0[9]
				// + "}" + dot + "{" + arg0[10] + "}" + dot + "{"
				// + arg0[11] + "}" + dot + "{" + arg0[12] + "}" + dot
				// + "{" + arg0[13] + "}"+"]"));
				// Log.i("字符串啊", "[" + "{" + arg0[1]
				// + "}" + dot + "{" + arg0[2] + "}" + dot + "{" + arg0[3]
				// + "}" + dot + "{" + arg0[4] + "}" + dot + "{" + arg0[5]
				// + "}" + dot + "{" + arg0[6] + "}" + dot + "{" + arg0[7]
				// + "}" + dot + "{" + arg0[8] + "}" + dot + "{" + arg0[9]
				// + "}" + dot + "{" + arg0[10] + "}" + dot + "{"
				// + arg0[11] + "}" + dot + "{" + arg0[12] + "}" + dot
				// + "{" + arg0[13] + "}"+"]") ;
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

			try {
				JSONObject mJSONObject;
				mJSONObject = new JSONObject(result);
				String resultdata = mJSONObject.getString("result");
				JSONObject mResultobject = new JSONObject(resultdata);
				String data = mResultobject.getString("s");
				if (data.equals("1")) {
					Toast.makeText(MyProfileEditActivity.this,
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
