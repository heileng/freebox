package com.example.freebox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class QuanQuanProfileActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String name;
		Intent intent=getIntent();
		setTitle(intent.getStringExtra("quanquan_type"));
		setbtn_rightRes(R.drawable.mm_title_btn_search);
		setContentLayout(R.layout.special_quanquan_layout);
	}
}
