package com.example.freebox.data;

import java.util.Date;

public class JSONMessageEntity {
	String type;
	int code;
	String time;
	String Sources;
	String Dest;
	String Content;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	public String getStringTime(){
//		String time=""+times.getDate();
		return time;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the times
	 */
	public String getTimes() {
		return time;
	}

	/**
	 * @param times
	 *            the times to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/*
	 * times form Unix temp
	 */

//	public void setTimes(int timeStamp) {
//		this.times = new java.util.Date((long) timeStamp * 1000);
//	}

	/**
	 * @return the sources
	 */
	public String getSources() {
		return Sources;
	}

	/**
	 * @param sources
	 *            the sources to set
	 */
	public void setSources(String sources) {
		Sources = sources;
	}

	/**
	 * @return the dest
	 */
	public String getDest() {
		return Dest;
	}

	/**
	 * @param dest
	 *            the dest to set
	 */
	public void setDest(String dest) {
		Dest = dest;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return Content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		Content = content;
	}

}
