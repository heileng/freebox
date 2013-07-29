package com.example.freebox.adapter;

import java.util.List;

import com.example.freebox.R;
import com.example.freebox.adapter.ContactAdapter.ViewHolder;
import com.example.freebox.entity.UserEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchFriendListAdapter extends BaseAdapter {
	private Context mContext;
	private String[] mNicks;
	private String[] mPhones;
	private List<UserEntity> userlist;

	public SearchFriendListAdapter(Context context) {
		this.mContext = context;
	}

	public SearchFriendListAdapter(Context context, List<UserEntity> e) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.userlist = e;
		this.mNicks = new String[this.userlist.size()];
		this.mPhones = new String[this.userlist.size()];
		for (int i = 0; i < this.userlist.size(); i++) {
			this.mNicks[i] = this.userlist.get(i).getName();
			this.mPhones[i] = this.userlist.get(i).getUserName();
		}

	}

	@Override
	public int getCount() {
		return mNicks.length;
	}

	@Override
	public Object getItem(int position) {
		return mNicks[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final String nickName = mNicks[position];
		final String phone = mPhones[position];
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.contact_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvCatalog = (TextView) convertView
					.findViewById(R.id.contactitem_catalog);
			viewHolder.ivAvatar = (ImageView) convertView
					.findViewById(R.id.contactitem_avatar_iv);
			viewHolder.tvNick = (TextView) convertView
					.findViewById(R.id.contactitem_nick);
			viewHolder.tvSig = (TextView) convertView
					.findViewById(R.id.personalize_sig);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.ivAvatar.setImageResource(R.drawable.default_avatar);
		viewHolder.tvNick.setText(nickName);
		viewHolder.tvSig.setText(phone);
		return convertView;
	}

	static class ViewHolder {
		TextView tvCatalog;// 目录
		ImageView ivAvatar;// 头像
		TextView tvNick;// 昵称
		TextView tvSig;// 个性签名
	}

}
