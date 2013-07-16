package com.example.freebox.adapter;

import java.util.List;

import com.example.freebox.R;
import com.example.freebox.adapter.QuanQuanAdapter.ViewHolder;
import com.example.freebox.entity.MessageEntity;
import com.example.freebox.entity.QuanQuanEntity;

import android.content.Context;
import android.graphics.Color;
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

public class UniteAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> mAppsList;
	private List<QuanQuanEntity> quanquanlist;
	private boolean flag = false;

	public UniteAdapter(Context context, List<String> app_list) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mAppsList = app_list;
	}

	public UniteAdapter(Context context, List<QuanQuanEntity> e, boolean flag) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.quanquanlist = e;
		this.flag = flag;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (flag == true)
			return quanquanlist.size();
		else
			return mAppsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAppsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		final int p = position;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.apps_item, null);
			viewHolder = new ViewHolder();
			viewHolder.app_name = (TextView) convertView
					.findViewById(R.id.app_title);
			viewHolder.app_type = (TextView) convertView
					.findViewById(R.id.app_type);
			viewHolder.app_icon = (ImageView) convertView
					.findViewById(R.id.app_icon);
			viewHolder.download_button = (Button) convertView
					.findViewById(R.id.download_button);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.download_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("按钮下载", "点击");
				if (flag == false) {
					Toast.makeText(mContext, "开始下载" + mAppsList.get(p),
							Toast.LENGTH_SHORT).show();
				} else if (flag == true) {
					Toast.makeText(mContext, "活动更新", Toast.LENGTH_SHORT).show();
					;
				}

			}

		});
		if (flag == true) {
			QuanQuanEntity entity = quanquanlist.get(position);
			viewHolder.app_name.setText(entity.getQuanQuanName());
			viewHolder.app_type.setText(entity.getQuanQuanItro());
			viewHolder.download_button.setText("@");
		} else {
			viewHolder.app_name.setText(mAppsList.get(position));
		}

		return convertView;
	}

	static class ViewHolder {
		public ImageView app_icon;
		public TextView app_name;
		public TextView app_type;
		public Button download_button;
	}

}
