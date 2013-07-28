package com.example.freebox.adapter;

import java.util.ArrayList;

import com.example.freebox.R;
import com.example.freebox.adapter.ContactAdapter.ViewHolder;
import com.example.freebox.config.Flags;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddressListAdapter extends BaseAdapter {
	private Context mContext;
	private String flag;
	private String[] address_group = { "我的好友", "我的圈圈", "校园圈圈", "学院圈圈", "班级圈圈" };
	public static String[] quan_profile_group = { "圈圈状态", "圈圈活动", "圈圈号码", "圈圈大佬",
			"圈圈公告", "圈圈成员" };

	private ArrayList<String> profile=new ArrayList<String>();
	public AddressListAdapter(Context context, String flag) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.flag = flag;
	}

	public AddressListAdapter(Context context, String flag,ArrayList<String> profile) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.flag = flag;
		this.profile=profile;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (flag.equals(Flags.FromAddress)) {
			return address_group.length;
		} else {
			return quan_profile_group.length;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (flag.equals(Flags.FromAddress)) {
			return address_group.length;
		} else {
			return quan_profile_group.length;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.addres_group_item, null);
			viewHolder = new ViewHolder();
			viewHolder.address_group_item_text = (TextView) convertView
					.findViewById(R.id.address_group_item_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Log.i("输出位置数据", address_group[position]);
		if (flag.equals(Flags.FromAddress)) {
			viewHolder.address_group_item_text.setText(address_group[position]);
		} else {
			viewHolder.address_group_item_text
					.setText(profile.get(position));
		}
		return convertView;
	}

	static class ViewHolder {
		TextView address_group_item_text;// 通讯录单个分组
		ImageView ivAvatar;// 分组头像
	}

}
