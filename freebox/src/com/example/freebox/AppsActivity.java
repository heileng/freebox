package com.example.freebox;

import java.util.ArrayList;
import java.util.List;

import com.example.freebox.adapter.UniteAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AppsActivity extends BaseActivity {
	private ListView apps_listview;
	private int quanquan_flag;
	private List<String> apps_list;
	private UniteAdapter appsadapter ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		hidebtn_right();
		Intent intent = getIntent();
		String quanquan_name_text = intent.getStringExtra("app_name");
		setTitle(quanquan_name_text);
		if (quanquan_name_text.equals("��ϷȦ")) {
			quanquan_flag = 0;
		} else if (quanquan_name_text.equals("����Ȧ")) {
			quanquan_flag = 1;
		} else if (quanquan_name_text.equals("����Ȧ")) {
			quanquan_flag = 2;
		} else if (quanquan_name_text.equals("���Ȧ")) {
			quanquan_flag = 3;
		}
		
		switch (quanquan_flag) {
		case 0:
			setContentLayout(R.layout.apps_layout);
			apps_listview = (ListView) findViewById(R.id.apps_listview);
			apps_list = new ArrayList<String>();
			apps_list.add("��ŭ��С��");
			apps_list.add("��������");
			apps_list.add("�̿�����");
			apps_list.add("֩����");
			apps_list.add("������");
			apps_list.add("ˮ������");
			appsadapter = new UniteAdapter(this, apps_list);
			apps_listview.setAdapter(appsadapter);
			break;
		case 1:
			setContentLayout(R.layout.music_layout);
			break;
		case 2:
			setContentLayout(R.layout.apps_layout);
			
			LinearLayout layout=(LinearLayout)findViewById(R.id.ly_content);
			layout.setBackgroundResource(R.drawable.background);

			apps_listview = (ListView) findViewById(R.id.apps_listview);
			apps_list = new ArrayList<String>();
			apps_list.add("�����г����ɻ����ף�");
			apps_list.add("��������");
			apps_list.add("���۵���");
			apps_list.add("��ĩ����");
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, apps_list);
			apps_listview.setAdapter(adapter);
			break;
		case 3:
			setContentLayout(R.layout.apps_layout);
			apps_listview = (ListView) findViewById(R.id.apps_listview);
			apps_list = new ArrayList<String>();
			apps_list.add("�����Ӱ");
			apps_list.add("΢��");
			apps_list.add("΢��");
			apps_list.add("qq");
			apps_list.add("������");
			apps_list.add("����");
			appsadapter = new UniteAdapter(this, apps_list);
			apps_listview.setAdapter(appsadapter);
			break;
		}

	}
}
