package com.example.freebox.data;

import java.util.List;

public class JSONQuanQuanListEntity {
	private List<JSONQuanQuanListItem> mQuanList;
	public JSONQuanQuanListEntity() {
		// TODO Auto-generated constructor stub
	}
	public JSONQuanQuanListItem getQuanItem(int position){
		return mQuanList.get(position);
	}
}
