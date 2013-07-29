package com.example.freebox.adapter;

import java.util.List;

import com.example.freebox.R;
import com.example.freebox.data.JSONIdeasItem;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IdeasAdapter extends BaseAdapter {
	private List<JSONIdeasItem> idealist;
	private Context ctx;
	private LayoutInflater mInflater;

    public IdeasAdapter(Context context, List<JSONIdeasItem> idealist) {
        this.ctx = context;
        this.idealist = idealist;
        mInflater = LayoutInflater.from(context);
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return idealist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return idealist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void addItem(JSONIdeasItem idea){
		idealist.add(idea);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		JSONIdeasItem entity = idealist.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.message_item_layout, null);
			viewHolder = new ViewHolder();
			viewHolder.wonline = (TextView) convertView
					.findViewById(R.id.message_time);
			viewHolder.username = (TextView) convertView
					.findViewById(R.id.message_name);
			viewHolder.description = (TextView) convertView
					.findViewById(R.id.message_content);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
    
	    viewHolder.wonline.setText(""+entity.getWonline());
	    viewHolder.username.setText(entity.getUserName());
	    viewHolder.description.setText(entity.getDescription());
	    
	    return convertView;
	}

	static class ViewHolder {
		public Bitmap avatar;
		public TextView wonline;
		public TextView username;
		public TextView description;
	}

}
