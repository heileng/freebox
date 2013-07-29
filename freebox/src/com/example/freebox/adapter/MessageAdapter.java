package com.example.freebox.adapter;

import java.util.List;

import com.example.freebox.R;
import com.example.freebox.adapter.ChatMsgViewAdapter.ViewHolder;
import com.example.freebox.entity.MessageEntity;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {
	private List<MessageEntity> messagelist;
	private Context ctx;
	private LayoutInflater mInflater;

    public MessageAdapter(Context context, List<MessageEntity> messagelist) {
        this.ctx = context;
        this.messagelist = messagelist;
        mInflater = LayoutInflater.from(context);
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messagelist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return messagelist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void addItem(MessageEntity message){
		messagelist.add(message);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MessageEntity entity = messagelist.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.message_item_layout, null);
			viewHolder = new ViewHolder();
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.message_time);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.message_name);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.message_content);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
    
	    viewHolder.time.setText(entity.getTime());
	    viewHolder.name.setText(entity.getName());
	    viewHolder.content.setText(entity.getContent());
	    
	    return convertView;
	}

	static class ViewHolder {
		public Bitmap avatar;
		public TextView time;
		public TextView name;
		public TextView content;
	}

}
