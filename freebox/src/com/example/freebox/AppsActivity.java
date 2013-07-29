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
		if (quanquan_name_text.equals("游戏圈")) {
			quanquan_flag = 0;
		} else if (quanquan_name_text.equals("音乐圈")) {
			quanquan_flag = 1;
		} else if (quanquan_name_text.equals("电商圈")) {
			quanquan_flag = 2;
		} else if (quanquan_name_text.equals("软件圈")) {
			quanquan_flag = 3;
		}
		
		switch (quanquan_flag) {
		case 0:
			setContentLayout(R.layout.apps_layout);
			apps_listview = (ListView) findViewById(R.id.apps_listview);
			apps_list = new ArrayList<String>();
			apps_list.add("愤怒的小鸟");
			apps_list.add("神庙逃亡");
			apps_list.add("刺客信条");
			apps_list.add("蜘蛛侠");
			apps_list.add("切绳子");
			apps_list.add("水果忍者");
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
			apps_list.add("跳蚤市场（旧货交易）");
			apps_list.add("餐饮外卖");
			apps_list.add("打折电器");
			apps_list.add("周末促销");
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, apps_list);
			apps_listview.setAdapter(adapter);
			break;
		case 3:
			setContentLayout(R.layout.apps_layout);
			apps_listview = (ListView) findViewById(R.id.apps_listview);
			apps_list = new ArrayList<String>();
			apps_list.add("豆瓣电影");
			apps_list.add("微博");
			apps_list.add("微信");
			apps_list.add("qq");
			apps_list.add("人人网");
			apps_list.add("米聊");
			appsadapter = new UniteAdapter(this, apps_list);
			apps_listview.setAdapter(appsadapter);
			break;
		}

	}
}
