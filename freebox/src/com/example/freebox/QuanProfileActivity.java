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

import com.example.freebox.ProfileActivity.DataGetTask;
import com.example.freebox.adapter.AddressListAdapter;
import com.example.freebox.adapter.UniteAdapter;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;
import com.example.freebox.data.JSONQuanProfileEntity;
import com.example.freebox.data.JSONQuanQuanListItem;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class QuanProfileActivity extends BaseActivity {
	private ListView mQuanList;
	private int mQuanGuid;
	public UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient = new HttpClientEntity();
	private DataGetTask task;
	private AddressListAdapter adapter;
	private JSONQuanProfileEntity mProfileEntity;
	private Handler mHandler;
	private ArrayList<String> mProfileArrayList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.quan_profile_layout);
		Intent intent = getIntent();
		mQuanGuid = intent.getIntExtra("quan_guid", 0);
		Log.i("圈圈信息activity", "查询圈圈id" + mQuanGuid);
		mQuanList = (ListView) findViewById(R.id.quan_profile_list);
		setTitle("圈圈资料");
		task = new DataGetTask();
		task.execute(APILinkEntity.mBasicAPI);
		mHandler = new Handler() {
			public void handleMessage(Message msg) {// 此方法在ui线程运行
				switch (msg.what) {
				case Flags.MSG_SUCCESS:
					adapter = new AddressListAdapter(QuanProfileActivity.this,
							Flags.FromQuanProfile, mProfileArrayList);
					mQuanList.setAdapter(adapter);
					break;
				case Flags.MSG_FAILURE:
					new AlertDialog.Builder(QuanProfileActivity.this)
							.setIcon(
									getResources().getDrawable(
											R.drawable.login_error_icon))
							.setTitle("获取错误").setMessage("圈圈资料获取错误！").create()
							.show();
					break;
				}
			}
		};
		getbtn_right().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});
	}

	public class DataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String result = null;
			Log.i("开始后台获取", "开始task");
			try {
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				Log.i("第二次token", token);
				// 获取圈圈列表
				List<NameValuePair> params2 = new ArrayList<NameValuePair>();
				params2.add(new BasicNameValuePair("method",
						APILinkEntity.mGetQuanProfileMethod));
				params2.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params2.add(new BasicNameValuePair("auth_token", token));
				params2.add(new BasicNameValuePair("APIKey", Flags.APIKEY));
				params2.add(new BasicNameValuePair("token", token));
				params2.add(new BasicNameValuePair("guid", "" + mQuanGuid));
				try {
					paramsEntity = new UrlEncodedFormEntity(params2, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = mClient.PostData(arg0[0], paramsEntity);
				Log.i("输出我的圈圈信息", result);

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
				JSONObject jsonobject = new JSONObject(result);
				// result对象生成
				String data_list = jsonobject.getString("result");
				JSONObject jsonobject2 = new JSONObject(data_list);
				String s_flag = jsonobject2.getString("s");
				if (s_flag.equals("1")) {

					int id = jsonobject2.getInt("guid");
//					String name = jsonobject2.getString("name");
//					String tags = jsonobject2.getString("tags");
					String state = jsonobject2.getString("state");
					String owner = jsonobject2.getString("owner");
					String activity = jsonobject2.getString("a");
					String announcement = jsonobject2.getString("p");
					mProfileArrayList
							.add(AddressListAdapter.quan_profile_group[0] + ":"
									+ state);
					mProfileArrayList
							.add(AddressListAdapter.quan_profile_group[1] + ":"
									+ activity);
					mProfileArrayList
							.add(AddressListAdapter.quan_profile_group[2] + ":"
									+ id);
					mProfileArrayList
							.add(AddressListAdapter.quan_profile_group[3] + ":"
									+ owner);
					mProfileArrayList
							.add(AddressListAdapter.quan_profile_group[4] + ":"
									+ announcement);
					mProfileArrayList
							.add(AddressListAdapter.quan_profile_group[5] + ":"
									+ "暂无");
					Message message = new Message();
					message.what = Flags.MSG_SUCCESS;
					mHandler.sendMessage(message);
					Toast.makeText(QuanProfileActivity.this, "获取列表成功",
							Toast.LENGTH_SHORT).show();

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
