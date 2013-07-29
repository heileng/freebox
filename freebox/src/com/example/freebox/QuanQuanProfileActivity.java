package com.example.freebox;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.freebox.adapter.ContactAdapter;
import com.example.freebox.adapter.IdeasAdapter;
import com.example.freebox.adapter.UniteAdapter;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;
import com.example.freebox.data.JSONIdeasItem;
import com.example.freebox.utils.DataGetHelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class QuanQuanProfileActivity extends BaseActivity {
	private ListView listView;
	private IdeasAdapter adapter;
	private ArrayList<JSONIdeasItem> mIdeasList = new ArrayList<JSONIdeasItem>();
	public UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient = new HttpClientEntity();
	private Handler mHandler;
	private DataGetTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		String name;
		Intent intent = getIntent();
		setTitle(intent.getStringExtra("quanquan_type"));
		setbtn_rightRes(R.drawable.mm_title_btn_search);
		setContentLayout(R.layout.special_quanquan_layout);
		listView = (ListView) findViewById(R.id.lv_quanquan_content);
		task=new DataGetTask();
		task.execute(APILinkEntity.mBasicAPI);
		mHandler = new Handler() {
			public void handleMessage(Message msg) {// 此方法在ui线程运行
				switch (msg.what) {
				case Flags.MSG_SUCCESS:
					break;
				case Flags.MSG_SUCCESS_FRIEND:
					// 设置我的好友列表
					break;
				}
			}
		};
	}
	
	public class DataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String result = null;
			try {
				// if (arg0[1].equals(mTaskFlagMyFriend)) {

				// 获取好友列表
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				Log.i("第二次token", token);
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("method",
						APILinkEntity.mGetXiaoYuanUserListMethod));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("APIKey", Flags.APIKEY));
				params1.add(new BasicNameValuePair("auth_token", token));
				params1.add(new BasicNameValuePair("token", token));
				params1.add(new BasicNameValuePair("sex", null));
				params1.add(new BasicNameValuePair("l", null));
				params1.add(new BasicNameValuePair("sch","zstu group"));
				params1.add(new BasicNameValuePair("c", null));
				try {
					paramsEntity = new UrlEncodedFormEntity(params1, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = mClient.PostData(arg0[0], paramsEntity);
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
			try {
				// ideas列表获取
				Log.i("信息", result);
				JSONObject friendjsonobject = new JSONObject(result);
				// result对象生成
				String ideas_list = friendjsonobject.getString("result");
				JSONObject jsonobject = new JSONObject(ideas_list);
				String s_flag = jsonobject.getString("s");
				Log.i("输出s_flag", s_flag);
				mIdeasList.clear();
				if (s_flag.equals("1")) {
					JSONArray ideas_array = jsonobject.getJSONArray("d");
					for (int i = 0; i < ideas_array.length(); i++) {
						JSONObject ideaitem = ideas_array.getJSONObject(i);
						int id = ideaitem.getInt("guid");
						String name = ideaitem.getString("name");
						String username = ideaitem.getString("tele");
						int wonline = ideaitem.getInt("o");
						int conline = ideaitem.getInt("conline");
						JSONIdeasItem idea = new JSONIdeasItem();
						idea.setId(id);
						idea.setName(name);
						idea.setUserName(username);
						idea.setWonline(wonline);
						idea.setConline(conline);
						Message message = new Message();
						message.what = Flags.MSG_SUCCESS_FRIEND;
						mHandler.sendMessage(message);
						mIdeasList.add(idea);
					}
				} else {
					Toast.makeText(QuanQuanProfileActivity.this, "还未加入校园圈！！！",
							Toast.LENGTH_SHORT).show();
				}
				Message message = new Message();
				message.what = Flags.MSG_SUCCESS;
				mHandler.sendMessage(message);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
