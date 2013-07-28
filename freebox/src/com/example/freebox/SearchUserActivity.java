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
import com.example.freebox.adapter.ContactAdapter;
import com.example.freebox.adapter.SearchFriendListAdapter;
import com.example.freebox.adapter.UniteAdapter;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;
import com.example.freebox.data.JSONFriendListItemEntity;
import com.example.freebox.data.JSONFriendsListEntity;
import com.example.freebox.entity.UserEntity;
import com.example.freebox.utils.DataGetHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchUserActivity extends BaseActivity {
	private EditText userid;
	private Button btn;
	private UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient = new HttpClientEntity();;
	private DataGetTask task;

	// �ҵĺ����б�
	private JSONFriendsListEntity mFriendsListEntity = new JSONFriendsListEntity();
	private JSONFriendListItemEntity mFriendItem;
	private List<JSONFriendListItemEntity> mFriendList = new ArrayList<JSONFriendListItemEntity>();
	private ArrayList<UserEntity> mUserEntityArrays = new ArrayList<UserEntity>();
	private Handler mHandler;
	private DataGetHelper mDataGetHelper;
	private SearchFriendListAdapter mSearchFriendListAdapter;
	private ListView mLvFriends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.search_friends_layout);
		userid = (EditText) findViewById(R.id.user_phone);
		userid.setHint("�ǳ�/�ֻ���/��Ȥ����/����");
		btn = (Button) findViewById(R.id.confirm);
		mLvFriends = (ListView) findViewById(R.id.lvSearchFriends);
		btn.setText("�����û�");
		setTitle("�����û�");
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				task = new DataGetTask();
				task.execute(APILinkEntity.mBasicAPI);
			}

		});
		mHandler = new Handler() {
			public void handleMessage(Message msg) {// �˷�����ui�߳�����
				switch (msg.what) {
				case Flags.MSG_SUCCESS:
					// �����ҵĺ����б�
					mDataGetHelper = new DataGetHelper(mFriendsListEntity);
//					Log.i("�鿴���", mFriendsListEntity.getFriendItem(1).getName());
					mUserEntityArrays = mDataGetHelper.initFriendsDataList();
					mSearchFriendListAdapter = new SearchFriendListAdapter(
							SearchUserActivity.this, mUserEntityArrays);
					mLvFriends.setAdapter(mSearchFriendListAdapter);
					break;
				case Flags.MSG_FAILURE:
					new AlertDialog.Builder(SearchUserActivity.this)
							.setIcon(
									getResources().getDrawable(
											R.drawable.login_error_icon))
							.setTitle("��¼����")
							.setMessage("freebox�ʺŻ��������\n���������룡").create()
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
			Log.i("��ʼ��̨��ȡ", "��ʼtask");
			try {
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("method",
						APILinkEntity.mSearchUserMethod));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("auth_token", token));
				params1.add(new BasicNameValuePair("t", token));
				params1.add(new BasicNameValuePair("q", userid.getText()
						.toString()));
				params1.add(new BasicNameValuePair("l", null));
				params1.add(new BasicNameValuePair("o", null));
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

			try {
				JSONObject friendjsonobject = new JSONObject(result);
				// result��������
				String friend_data_list = friendjsonobject.getString("result");
				JSONObject jsonobject3 = new JSONObject(friend_data_list);
				String s_flag = jsonobject3.getString("s");
				Log.i("���s_flag", s_flag);
				mFriendList.clear();
				if (s_flag.equals("1")) {
					JSONArray friends_array = jsonobject3.getJSONArray("d");
					for (int i = 0; i < friends_array.length(); i++) {
						JSONObject friendjsonitem = friends_array
								.getJSONObject(i);
						int id = friendjsonitem.getInt("guid");
						String name = friendjsonitem.getString("name");
						String username = friendjsonitem.getString("username");
						// int avatarnum=friendjsonitem.getInt("avatar");
						int wonline = friendjsonitem.getInt("wonline");
						int conline = friendjsonitem.getInt("conline");
						mFriendItem = new JSONFriendListItemEntity();
						mFriendItem.setName(name);
						mFriendItem.setUserName(username);
						mFriendItem.setWonline(wonline);
						mFriendItem.setConline(conline);
						// mFriendItem.setAvatarNum(avatarnum);
						mFriendList.add(mFriendItem);
						Log.i("����item����", id + name + username + wonline
								+ conline);
						Message message = new Message();
						message.what = Flags.MSG_SUCCESS;
						mHandler.sendMessage(message);
						Toast.makeText(SearchUserActivity.this, "�����б��ʼ���ɹ�",
								Toast.LENGTH_SHORT).show();
					}
					mFriendsListEntity.setFriendList(mFriendList);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
