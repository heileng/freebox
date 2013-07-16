package com.example.freebox.adapter;

import java.util.List;

import com.example.freebox.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QuanQuanAdapter extends BaseAdapter {

	private Context context;
	private List<String> lstDate;
	private boolean flag = false;

	public QuanQuanAdapter(Context mContext, List<String> list, boolean flag) {
		this.context = mContext;
		lstDate = list;
		this.flag = flag;
	}

	@Override
	public int getCount() {
		return lstDate.size();
	}

	@Override
	public Object getItem(int position) {
		return lstDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (flag == false) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.quanquan_item, null);
			} else if (flag == true) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.search_flags_item, null);
			}
			viewHolder = new ViewHolder();
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.txt_userAge);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (flag == false) {
			switch (position) {
			case 0:
				convertView.setBackgroundColor(Color.CYAN);
				break;
			case 1:
				convertView.setBackgroundColor(Color.YELLOW);
				break;
			case 2:
				convertView.setBackgroundColor(Color.RED);
				break;
			case 3:
				convertView.setBackgroundColor(Color.GREEN);
				break;
			}
		}

		viewHolder.content.setText(lstDate.get(position));
		return convertView;
	}

	static class ViewHolder {
		public TextView name;
		public TextView content;
	}

}
