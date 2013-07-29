package com.example.freebox;

import android.os.Bundle;

import com.example.freebox.BaseActivity;
import com.example.freebox.R;
import com.example.freebox.data.JSONMessageEntity;

public class MessageCountActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.message_count_layout);
	}

	public void setPushMessage(JSONMessageEntity messageentity) {

	}
}
