package com.bairuitech.demo;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.example.freebox.R;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class LoginActivity extends Activity implements AnyChatBaseEvent {
	private LinearLayout fullLayout;
	private LinearLayout mainLayout;
	private LinearLayout progressLayout;
	private Button configBtn;
	private Button loginBtn;
	private CheckBox saveCheckBox;
	private ConfigEntity configEntity;
	private EditText nameEditText;
	private EditText passwordEditText;
	private CheckBox anonymousCheckBox;
	private boolean bNeedRelease = false;

	public AnyChatCoreSDK anychat;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDisPlayMetrics();
		configEntity = ConfigService.LoadConfig(this);
		InitialSDK();
		InitialLayout();
	}
	
	

	private void InitialSDK() {
		if (anychat == null) {
			anychat = new AnyChatCoreSDK();
			anychat.SetBaseEvent(this);
			if (configEntity.useARMv6Lib != 0)
				AnyChatCoreSDK.SetSDKOptionInt(
						AnyChatDefine.BRAC_SO_CORESDK_USEARMV6LIB, 1);
			anychat.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
			bNeedRelease = true;
		}
	}

	private void InitialLayout() {
		fullLayout = new LinearLayout(this);
		fullLayout.setBackgroundResource(R.drawable.login_bk);
		// fullLayout.setBackgroundColor(Color.WHITE);
		fullLayout.setOrientation(LinearLayout.VERTICAL);
		fullLayout.setOnTouchListener(touchListener);

		mainLayout = new LinearLayout(this);
		mainLayout.setBackgroundColor(Color.TRANSPARENT);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setOnTouchListener(touchListener);

		LayoutParams normalEditLayoutLp = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		// �������Logo
		ImageView iv = new ImageView(this);
		iv.setScaleType(ScaleType.CENTER_INSIDE);
		iv.setBackgroundResource(R.drawable.logo);
		// mainLayout.addView(iv,new LayoutParams(257,96));

		// �������
		TextView titleLabel = new TextView(this);
		titleLabel.setTextColor(Color.YELLOW);
		titleLabel.setTextSize(24);

		titleLabel.setText("AnyChat for Android");
		titleLabel.setGravity(Gravity.CENTER);

		mainLayout.addView(titleLabel, new LayoutParams(ScreenInfo.WIDTH, 100));

		// �����û���
		LinearLayout nameLayout = new LinearLayout(this);
		nameLayout.setPadding(10, 0, 0, 0);
		nameLayout.setOrientation(LinearLayout.HORIZONTAL);

		TextView nameLabel = new TextView(this);
		nameLabel.setTextColor(Color.BLACK);
		nameLabel.setText("�û�����");
		nameLayout.addView(nameLabel, new LayoutParams(ScreenInfo.WIDTH / 5,
				LayoutParams.WRAP_CONTENT));

		nameEditText = new EditText(this);
		nameLayout.addView(nameEditText, new LayoutParams(
				ScreenInfo.WIDTH * 4 / 5 - 20, LayoutParams.WRAP_CONTENT));
		mainLayout.addView(nameLayout, normalEditLayoutLp);
		if (configEntity.IsSaveNameAndPw) {
			nameEditText.setText(configEntity.name);
		}
		// ��������
		LinearLayout passwordLayout = new LinearLayout(this);
		passwordLayout.setPadding(10, 0, 0, 0);
		passwordLayout.setOrientation(LinearLayout.HORIZONTAL);

		TextView passwordLabel = new TextView(this);
		passwordLabel.setTextColor(Color.BLACK);
		passwordLabel.setText("���룺");
		passwordLayout.addView(passwordLabel, new LayoutParams(
				ScreenInfo.WIDTH / 5, LayoutParams.WRAP_CONTENT));

		passwordEditText = new EditText(this);
		passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		passwordLayout.addView(passwordEditText, new LayoutParams(
				ScreenInfo.WIDTH * 4 / 5 - 20, LayoutParams.WRAP_CONTENT));
		mainLayout.addView(passwordLayout, normalEditLayoutLp);
		if (configEntity.IsSaveNameAndPw) {
			passwordEditText.setText(configEntity.password);
		}

		// �����Ƿ񱣴�
		saveCheckBox = new CheckBox(this);
		saveCheckBox.setText("�����û���������");
		saveCheckBox.setTextColor(Color.BLACK);
		saveCheckBox.setChecked(configEntity.IsSaveNameAndPw);
		mainLayout.addView(saveCheckBox, normalEditLayoutLp);

		// �����Ƿ�������¼
		anonymousCheckBox = new CheckBox(this);
		anonymousCheckBox.setTextColor(Color.BLACK);
		anonymousCheckBox.setText("������¼");
		mainLayout.addView(anonymousCheckBox, normalEditLayoutLp);
		// ��¼ʱ��ʾ��¼����
		LinearLayout layout = new LinearLayout(this);
		layout.setGravity(Gravity.CENTER);
		progressLayout = new LinearLayout(this);
		progressLayout.setBackgroundColor(Color.DKGRAY);
		progressLayout.setOrientation(LinearLayout.HORIZONTAL);
		progressLayout.setGravity(Gravity.CENTER_VERTICAL);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 5, 5, 5);
		ProgressBar progressBar = new ProgressBar(this, null,
				android.R.attr.progressBarStyleLarge);
		TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setText("���ڵ�¼�У����Ժ�!");
		progressLayout.addView(progressBar, params);
		progressLayout.addView(textView, params);
		progressLayout.setVisibility(View.GONE);
		layout.addView(progressLayout, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		// ����汾��Ϣ
		LinearLayout versionLayout = new LinearLayout(this);
		versionLayout.setOrientation(LinearLayout.HORIZONTAL);

		TextView versionLabel = new TextView(this);
		versionLabel.setTextColor(Color.YELLOW);
		versionLabel.setText("core version: " + anychat.GetSDKMainVersion()
				+ "." + anychat.GetSDKSubVersion() + " ("
				+ anychat.GetSDKBuildTime() + ")");
		versionLabel.setGravity(Gravity.LEFT);
		versionLayout.addView(versionLabel, new LayoutParams(ScreenInfo.WIDTH,
				LayoutParams.WRAP_CONTENT));

		LinearLayout btnLayout = new LinearLayout(this);
		btnLayout.setOrientation(LinearLayout.HORIZONTAL);

		// ����ײ���ť
		configBtn = new Button(this);
		configBtn.setText("����");
		btnLayout.addView(configBtn, new LayoutParams(ScreenInfo.WIDTH / 2,
				LayoutParams.WRAP_CONTENT));
		configBtn.setOnClickListener(listener);

		loginBtn = new Button(this);
		loginBtn.setText("��¼");
		btnLayout.addView(loginBtn, new LayoutParams(ScreenInfo.WIDTH / 2,
				LayoutParams.WRAP_CONTENT));
		loginBtn.setOnClickListener(listener);

		fullLayout.addView(mainLayout, new LayoutParams(ScreenInfo.WIDTH,
				ScreenInfo.HEIGHT * 2 / 4));
		fullLayout.addView(layout, new LayoutParams(ScreenInfo.WIDTH,
				ScreenInfo.HEIGHT * 1 / 4));
		fullLayout.addView(btnLayout, new LayoutParams(ScreenInfo.WIDTH,
				LayoutParams.WRAP_CONTENT));
		fullLayout.addView(versionLayout, new LayoutParams(ScreenInfo.WIDTH,
				LayoutParams.WRAP_CONTENT));

		this.setContentView(fullLayout);
	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			if (v == loginBtn) {
				Login();

			}
			if (v == configBtn) {
				Intent intent = new Intent("com.bairuitech.demo.ConfigActivity");
				startActivity(intent);
			}
		}
	};

	private void Login() {
		if (saveCheckBox.isChecked()) {
			configEntity.IsSaveNameAndPw = true;
			configEntity.name = nameEditText.getEditableText().toString();
			configEntity.password = passwordEditText.getText().toString();
			ConfigService.SaveConfig(this, configEntity);

		} else {
			configEntity.IsSaveNameAndPw = false;
			ConfigService.SaveConfig(this, configEntity);
		}
		// û�������û���������Ҳû�й�ѡ������¼������ʾ�û�
		if (nameEditText.getText().length() == 0
				&& !anonymousCheckBox.isChecked()) {
			Toast.makeText(this, "�������û���", Toast.LENGTH_SHORT).show();
			return;
		}

		this.anychat.Connect(configEntity.ip, configEntity.port);
		if (anonymousCheckBox.isChecked()) {
			int time = this.anychat.Login("android", "");
			Toast.makeText(this, String.valueOf(time), Toast.LENGTH_LONG)
					.show();
		} else {
			int time = this.anychat.Login(nameEditText.getText().toString(),
					passwordEditText.getText().toString());
			Toast.makeText(this, String.valueOf(time), Toast.LENGTH_LONG)
					.show();
		}
		loginBtn.setClickable(false);
		progressLayout.setVisibility(View.VISIBLE);
	}

	OnTouchListener touchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent e) {
			// TODO Auto-generated method stub
			switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				try {
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(LoginActivity.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
				} catch (Exception excp) {

				}
				break;
			case MotionEvent.ACTION_UP:

				break;
			}
			return false;
		}
	};

	private void setDisPlayMetrics() {
		DisplayMetrics dMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		ScreenInfo.WIDTH = dMetrics.widthPixels;
		ScreenInfo.HEIGHT = dMetrics.heightPixels;
	}

	protected void onDestroy() {

		if (bNeedRelease) {
			anychat.Release(); // �ر�SDK
		}
		super.onDestroy();
	}

	protected void onResume() {
		configEntity = ConfigService.LoadConfig(this);
		anychat.SetBaseEvent(this);
		super.onResume();
	}

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		if (!bSuccess) {
			loginBtn.setClickable(true);
			Toast.makeText(this, "���ӷ�����ʧ�ܣ��Զ����������Ժ�...", Toast.LENGTH_SHORT)
					.show();
			progressLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		Toast.makeText(this, "���ӹرգ�error��" + dwErrorCode, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		if (dwErrorCode == 0) {
			Toast.makeText(this, "��¼�ɹ���", Toast.LENGTH_SHORT).show();
			bNeedRelease = false;
			Intent itent = new Intent();
			itent.setClass(this, HallActivity.class);
			startActivity(itent);
			finish();
		} else {
			Toast.makeText(this, "��¼ʧ�ܣ�������룺" + dwErrorCode, Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		// TODO Auto-generated method stub

	}
}
