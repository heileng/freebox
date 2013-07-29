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

import com.example.freebox.ProfileActivity.DataGetTask;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.Toast;

public class AddFriendActivity extends BaseActivity {
	private EditText userid;
	private Button btn;
	private UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient = new HttpClientEntity();;
	private DataGetTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.add_friend_layout);
		userid = (EditText) findViewById(R.id.user_guid);
		btn = (Button) findViewById(R.id.confirm);
		setTitle("��Ӻ���");
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				task = new DataGetTask();
				task.execute(APILinkEntity.mBasicAPI);
			}

		});
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
						APILinkEntity.mAddFriendMethod));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("auth_token", token));
				params1.add(new BasicNameValuePair("t", token));
				params1.add(new BasicNameValuePair("friend", userid.getText()
						.toString()));
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
				JSONObject mJSONObject;
				mJSONObject = new JSONObject(result);
				String resultdata=mJSONObject.getString("result");
				JSONObject mResultobject=new JSONObject(resultdata);
				String data = mResultobject.getString("s");
				if(data.equals("1")){
					AddFriendActivity.this.finish();
					Toast.makeText(AddFriendActivity.this, "������ӳɹ���", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(AddFriendActivity.this, "���Ѿ���ӹ����û���", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
