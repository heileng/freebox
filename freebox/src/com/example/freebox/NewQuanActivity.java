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

import com.example.freebox.Login.DataGetTask;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewQuanActivity extends BaseActivity {
	private static final String[] mQuanAvatarNumString = { "请选择头像编号", "1", "2",
			"3", "3", "4", "5" };
	private static final String[] mTagString = { "生活", "音乐", "电影", "运动", "游戏",
			"美食", "动画", "编程" };
	private Spinner mQuanAvatarSpinner;
	private Spinner mTagSpinner;
	private ArrayAdapter<String> mTagAdapter;
	private ArrayAdapter<String> mAvatarAdapter;
	private EditText mNameEdit;
	private EditText mAnnouncementEdit;
	private EditText mDesEdit;
	private EditText mActivityEdit;

	private HttpClientEntity mClient = new HttpClientEntity();;
	private DataGetTask task;
	private UrlEncodedFormEntity paramsEntity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.new_quan_layout);
		setTitle("新建圈圈");
		mNameEdit = (EditText) findViewById(R.id.quan_name_edit);
		mAnnouncementEdit = (EditText) findViewById(R.id.anno_edit);
		mActivityEdit = (EditText) findViewById(R.id.activity_edit);
		mDesEdit=(EditText)findViewById(R.id.des_edit);
		mQuanAvatarSpinner = (Spinner) findViewById(R.id.quan_avatar_num_spinner);
		mAvatarAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mQuanAvatarNumString);
		mAvatarAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mQuanAvatarSpinner.setAdapter(mAvatarAdapter);
		mQuanAvatarSpinner
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
		mTagSpinner = (Spinner) findViewById(R.id.tag_spinner);
		mTagAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mTagString);
		mTagAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mTagSpinner.setAdapter(mTagAdapter);
		mTagSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// m_tTextView.setText(m_Countries[arg2]);
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		getbtn_right().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				task = new DataGetTask();
				task.execute(APILinkEntity.mBasicAPI);
				Toast.makeText(NewQuanActivity.this, "提交圈圈数据",
						Toast.LENGTH_SHORT).show();
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
				String owner = sharedPreferences.getString("user_name", "none");
				Log.i("第二次token", token);
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("method",
						APILinkEntity.mNewQuanMethod));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("auth_token", token));
				params1.add(new BasicNameValuePair("token", token));
				params1.add(new BasicNameValuePair("APIKey", Flags.APIKEY));
				params1.add(new BasicNameValuePair("n", mNameEdit.getText()
						.toString()));
				params1.add(new BasicNameValuePair("anno", mActivityEdit
						.getText().toString()));
				params1.add(new BasicNameValuePair("des", mDesEdit.getText()
						.toString()));
				params1.add(new BasicNameValuePair("tag", mTagSpinner
						.getSelectedItem().toString()));
				params1.add(new BasicNameValuePair("img", mQuanAvatarSpinner
						.getSelectedItem().toString()));
				params1.add(new BasicNameValuePair("activity", mActivityEdit
						.getText().toString()));
				params1.add(new BasicNameValuePair("activity", mActivityEdit
						.getText().toString()));
				params1.add(new BasicNameValuePair("ownername", owner));
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
			try {
				JSONObject mJSONObject;
				mJSONObject = new JSONObject(result);
				String resultdata=mJSONObject.getString("result");
				JSONObject mResultobject=new JSONObject(resultdata);
				String data = mResultobject.getString("s");
				if(data.equals("1")){
					NewQuanActivity.this.finish();
					Toast.makeText(NewQuanActivity.this, "新建圈圈成功！", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(NewQuanActivity.this, "新建圈圈失败！", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
