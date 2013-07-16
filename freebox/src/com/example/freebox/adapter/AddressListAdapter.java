package com.example.freebox.adapter;

import com.example.freebox.R;
import com.example.freebox.adapter.ContactAdapter.ViewHolder;

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
	private String[] address_group = { "�ҵĺ���", "�ҵ�ȦȦ", "У԰ȦȦ", "ѧԺȦȦ",
			"�༶ȦȦ" };

	public AddressListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return address_group.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return address_group.length;
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
		Log.i("���λ������", address_group[position]);
		viewHolder.address_group_item_text.setText(address_group[position]);
		return convertView;
	}

	static class ViewHolder {
		TextView address_group_item_text;// ͨѶ¼��������
		ImageView ivAvatar;// ����ͷ��
	}

}
