package com.example.freebox.data;

import java.util.List;

public class JSONFriendsListEntity {
	private List<JSONFriendListItemEntity> mFriendList;

	public JSONFriendsListEntity() {
		// TODO Auto-generated constructor stub
	}
	public JSONFriendListItemEntity getFriendItem(int position){
		return mFriendList.get(position);
	}
	public void setFriendList(List<JSONFriendListItemEntity> mFriendList){
		this.mFriendList=mFriendList;
	}
	public List<JSONFriendListItemEntity> getFriendList(){
		return this.mFriendList;
	}
}
