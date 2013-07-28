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
	public void setQuanList(List<JSONQuanQuanListItem> quanList){
		this.mQuanList=quanList;
	}
	public List<JSONQuanQuanListItem> getQuanList(){
		return this.mQuanList;
	}
}
