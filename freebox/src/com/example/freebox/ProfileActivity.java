package com.example.freebox;

import com.example.freebox.entity.UserEntity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends BaseActivity {
	private TextView user_name;
	private ImageView user_avatar;
	private UserEntity myEntity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.profile_layout);
		setContentLayout(R.layout.profile_layout);
		myEntity = new UserEntity();
		user_name = (TextView) findViewById(R.id.user_name_user_id);
		user_avatar = (ImageView) findViewById(R.id.user_avatar);
		Intent intent = getIntent();
		String user_name_text = intent.getStringExtra("name");
		Log.i("输出信息啦", user_name_text);
		setTitle("详细信息");
		getbtn_right().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ProfileActivity.this,
						MainTopRightDialog.class);
				intent.putExtra("dialog_type", "friend_dialog");
				startActivity(intent);
			}

		});
		user_name.setText(user_name_text);
		if (user_name_text.equals("小黑")) {
			Log.i("输出信息2", user_name_text);

			user_avatar.setImageResource(myEntity.entity_xiaohei
					.getUserDrawable());
		}
		if (user_name_text.equals("黑棱")) {
			Log.i("输出信息3", "这是黑棱" + user_name_text);
			user_name.setText(user_name_text);
			user_avatar.setImageResource(myEntity.my_entity.getUserDrawable());
		}

	}

	public void head_xiaohei(View v) { // 头像按钮
		Intent intent = new Intent();
		intent.setClass(ProfileActivity.this, AvatarActivity.class);
		startActivity(intent);
	}

}
