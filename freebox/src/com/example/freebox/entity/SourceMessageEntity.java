package com.example.freebox.entity;

public class SourceMessageEntity {
	private String type;
	private int code;
	private String source;
	private String dest;

	public SourceMessageEntity() {
		// TODO Auto-generated constructor stub
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getType() {
		return this.type;
	}

	public String getDest() {
		return this.dest;
	}

	public String getSource() {
		return this.source;
	}

	public int getCode() {
		return this.code;
	}
}
