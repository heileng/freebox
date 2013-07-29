package com.example.freebox.data;

public class JSONIdeasItem {
	private String user_name;
	private String name;
	private int wonline;
	private int conline;
	private int id;
	private String description;

	public JSONIdeasItem() {
		// TODO Auto-generated constructor stub
	}

	public void setUserName(String username) {
		this.user_name = username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// 网页在线
	public void setWonline(int wonline) {
		this.wonline = wonline;
	}

	// 客户端在线
	public void setConline(int conline) {
		this.conline = conline;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getUserName() {
		return this.user_name;
	}

	public String getDescription() {
		return this.description;
	}

	public int getWonline() {
		return wonline;
	}

	public int getConline() {
		return conline;
	}
}
