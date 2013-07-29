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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.freebox.SearchUserActivity.DataGetTask;
import com.example.freebox.adapter.SearchFriendListAdapter;
import com.example.freebox.adapter.SearchQuanAdapter;
import com.example.freebox.adapter.UniteAdapter;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;
import com.example.freebox.data.JSONFriendListItemEntity;
import com.example.freebox.data.JSONQuanQuanListEntity;
import com.example.freebox.data.JSONQuanQuanListItem;
import com.example.freebox.entity.QuanQuanEntity;
import com.example.freebox.utils.DataGetHelper;

public class SearchQuanActivity extends BaseActivity {
	private EditText quanname;
	private Button btn;
	private ListView mLvQuan;
	private UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient = new HttpClientEntity();;
	private DataGetTask task;
	// 我的圈圈列表
	private JSONQuanQuanListEntity mQuanListEntity = new JSONQuanQuanListEntity();
	private JSONQuanQuanListItem mQuanItem;
	private List<JSONQuanQuanListItem> mQuanList = new ArrayList<JSONQuanQuanListItem>();
	private ArrayList<QuanQuanEntity> mQuanQuanEntityArrays = new ArrayList<QuanQuanEntity>();
	private Handler mHandler;
	private DataGetHelper mDataGetHelper;
	private SearchQuanAdapter mSearchQuanAdapter;
	private ListView mLvFriends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.search_quan_layout);
		quanname = (EditText) findViewById(R.id.quan_name);
		btn = (Button) findViewById(R.id.confirm);
		mLvQuan = (ListView) findViewById(R.id.lvSearchQuan);
		mLvQuan.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(SearchQuanActivity.this, QuanProfileActivity.class);
				Toast.makeText(SearchQuanActivity.this, ""+mSearchQuanAdapter.getQuanId(position), Toast.LENGTH_LONG).show();
				intent.putExtra("quan_guid",mSearchQuanAdapter.getQuanId(position));
				startActivity(intent);
			}
			
		});
		btn.setText("搜索圈圈");
		setTitle("搜索圈圈");
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				task = new DataGetTask();
				task.execute(APILinkEntity.mBasicAPI);
			}

		});
		mHandler = new Handler() {
			public void handleMessage(Message msg) {// 此方法在ui线程运行
				switch (msg.what) {
				case Flags.MSG_SUCCESS:
					// 设置我的圈圈列表
					mDataGetHelper = new DataGetHelper(mQuanListEntity);
					mQuanQuanEntityArrays = mDataGetHelper
							.initQuanQuanDataList();
					mSearchQuanAdapter = new SearchQuanAdapter(
							SearchQuanActivity.this, mQuanQuanEntityArrays);
					mLvQuan.setAdapter(mSearchQuanAdapter);
					break;
				case Flags.MSG_FAILURE:
					new AlertDialog.Builder(SearchQuanActivity.this)
							.setIcon(
									getResources().getDrawable(
											R.drawable.login_error_icon))
							.setTitle("登录错误")
							.setMessage("freebox帐号或密码错误，\n请重新输入！").create()
							.show();
					break;
				}
			}
		};

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
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("method",
						APILinkEntity.mSearchQuanMethod));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("auth_token", token));
				params1.add(new BasicNameValuePair("t", token));
				params1.add(new BasicNameValuePair("q", quanname.getText()
						.toString()));
				params1.add(new BasicNameValuePair("l", null));
				params1.add(new BasicNameValuePair("o", null));
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
				// 圈圈列表获取
				JSONObject quanjsonobject = new JSONObject(result);
				// result对象生成
				String quan_data_list = quanjsonobject.getString("result");
				JSONObject jsonobject2 = new JSONObject(quan_data_list);
				String s_flag2 = jsonobject2.getString("s");
				mQuanList.clear();
				if (s_flag2.equals("1")) {
					JSONArray quan_array = jsonobject2.getJSONArray("d");
					for (int i = 0; i < quan_array.length(); i++) {
						Log.i("index", "" + i);
						JSONObject jsonitem = quan_array.getJSONObject(i);
						int id = jsonitem.getInt("guid");
						String name = jsonitem.getString("name");
						String tags = jsonitem.getString("tags");
						int head = jsonitem.getInt("head");
						mQuanItem = new JSONQuanQuanListItem();
						mQuanItem.setQuanAvatar(head);
						mQuanItem.setQuanName(name);
						mQuanItem.setQuanTag(tags);
						mQuanItem.setQuanId(id);
						mQuanList.add(mQuanItem);
						Message message = new Message();
						message.what = Flags.MSG_SUCCESS;
						mHandler.sendMessage(message);
					}
					mQuanListEntity.setQuanList(mQuanList);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
