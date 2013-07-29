package com.example.freebox.push;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.freebox.data.JSONMessageEntity;

public class JsonObjectFactory {

	public static JSONMessageEntity parseJsonMessage(String StringObject)
			throws JSONException {
		JSONMessageEntity mMessage = new JSONMessageEntity();
		JSONObject jsonObject = new JSONObject(StringObject);
		mMessage.setType(jsonObject.getString("Type"));
		mMessage.setCode(jsonObject.getInt("Code"));
		mMessage.setTime(jsonObject.getString("time"));
		mMessage.setSources(jsonObject.getString("Source"));
		mMessage.setDest(jsonObject.getString("Dest"));
		mMessage.setContent(jsonObject.getString("Content"));
		return mMessage;
	}

	public void test() {
		StringBuffer mBuffer = new StringBuffer();

	}
}
