package com.example.freebox.adapter;

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

import com.example.freebox.AddFriendActivity;
import com.example.freebox.R;
import com.example.freebox.AddFriendActivity.DataGetTask;
import com.example.freebox.adapter.SearchFriendListAdapter.ViewHolder;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;
import com.example.freebox.entity.QuanQuanEntity;
import com.example.freebox.entity.UserEntity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchQuanAdapter extends BaseAdapter {
	private Context mContext;
	private String[] mNames;
	private String[] mIntros;
	private Integer[] mGuid;
	private ArrayList<QuanQuanEntity> quanlist;

	private int currentquanflag;

	// 圈圈提交操作
	private UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient = new HttpClientEntity();;
	private DataGetTask task;

	public SearchQuanAdapter(Context context) {
		this.mContext = context;
	}

	public SearchQuanAdapter(Context context, ArrayList<QuanQuanEntity> e) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.quanlist = e;
		this.mNames = new String[this.quanlist.size()];
		this.mIntros = new String[this.quanlist.size()];
		this.mGuid = new Integer[this.quanlist.size()];
		for (int i = 0; i < this.quanlist.size(); i++) {
			this.mNames[i] = this.quanlist.get(i).getQuanQuanName();
			this.mIntros[i] = this.quanlist.get(i).getQuanQuanItro();
			this.mGuid[i] = this.quanlist.get(i).getQuanId();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.quanlist.size();
	}

	@Override
	public Object getItem(int position) {
		return mNames[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getQuanId(int position) {
		return this.mGuid[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int finalposition = position;
		final String nickName = mNames[position];
		final String intro = mIntros[position];
		final int guid = mGuid[position];
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.apps_item, null);
			viewHolder = new ViewHolder();
			// viewHolder.tvCatalog = (TextView) convertView
			// .findViewById(R.id.contactitem_catalog);
			viewHolder.ivAvatar = (ImageView) convertView
					.findViewById(R.id.app_icon);
			viewHolder.tvName = (TextView) convertView
					.findViewById(R.id.app_title);
			viewHolder.tvIntro = (TextView) convertView
					.findViewById(R.id.app_type);
			viewHolder.button = (Button) convertView
					.findViewById(R.id.download_button);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.ivAvatar.setImageResource(R.drawable.heileng);
		viewHolder.tvName.setText(nickName);
		viewHolder.tvIntro.setText(intro);
		viewHolder.button.setText("加入");
		viewHolder.button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				task = new DataGetTask();
				task.execute(guid, finalposition);
				// Toast.makeText(mContext, "您已成功加入"+nickName,
				// Toast.LENGTH_LONG).show();
			}

		});
		return convertView;
	}

	static class ViewHolder {
		TextView tvCatalog;// 目录
		ImageView ivAvatar;// 头像
		TextView tvName;// 昵称
		TextView tvIntro;// 个性签名
		Button button;
	}

	public class DataGetTask extends AsyncTask<Integer, String, String> {

		@Override
		protected String doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			String result = null;
			Log.i("开始后台获取", "开始task");
			currentquanflag = arg0[1];
			try {
				SharedPreferences sharedPreferences = mContext
						.getSharedPreferences("user_config",
								Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("method",
						APILinkEntity.mUserAddQuanMemberMethod));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("APIKey", Flags.APIKEY));
				params1.add(new BasicNameValuePair("auth_token", token));
				params1.add(new BasicNameValuePair("token", token));
				params1.add(new BasicNameValuePair("guid", "" + arg0[0]));
				try {
					paramsEntity = new UrlEncodedFormEntity(params1, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = mClient
						.PostData(APILinkEntity.mBasicAPI, paramsEntity);
				Log.i("加入圈圈", result);
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
					Toast.makeText(mContext,
							"加入" + mNames[currentquanflag] + "成功",
							Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
