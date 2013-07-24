package com.example.freebox.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.content.Entity;
import android.util.Log;

public class HttpClientEntity {
	private HttpClient client;
	private String URL;

	public HttpClientEntity() {
		Log.i("����Client", "ʵ�崴��");
		this.client = new DefaultHttpClient();
	}

	public String GetData(String URL) throws ClientProtocolException,
			IOException, JSONException {
		StringBuilder url = new StringBuilder(URL);
		HttpGet get = new HttpGet(url.toString());
		HttpResponse r = client.execute(get);
		HttpEntity e = r.getEntity();
		String data = EntityUtils.toString(e);
		return data;
	}
	

	public String PostData(String URL, UrlEncodedFormEntity paramsEntity)
			throws ClientProtocolException, IOException, JSONException {
//		HttpEntity entity=null ;
		String data = null;
		// ʹ��POST����
		HttpPost method = new HttpPost(URL);
		// ���ò���
		method.setEntity(paramsEntity);
		// ִ��
		HttpResponse response = client.execute(method);
		// ����ɹ�
		Log.i("����״̬", ""+response.getStatusLine().getStatusCode());
//		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			data=EntityUtils.toString(entity);
//		}
		
		Log.i("�鿴������Ϣ", data);
		return data;
	}

}
