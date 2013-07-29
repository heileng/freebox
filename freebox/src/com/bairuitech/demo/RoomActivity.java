package com.bairuitech.demo;

import java.util.ArrayList;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.bairuitech.anychat.AnyChatTextMsgEvent;
import com.example.freebox.R;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RoomActivity extends Activity implements AnyChatBaseEvent,
		AnyChatTextMsgEvent {
	private LinearLayout fullLayout;
	private LinearLayout mainLayout;
	private Button sendBtn;

	private ListView userListView;
	private MessageListView messageListView;
	private BaseAdapter userListAdapter;

	public AnyChatCoreSDK anychat;

	private ArrayList<String> idList = new ArrayList<String>();
	private ArrayList<String> userList = new ArrayList<String>();
	private ArrayList<String> messageList = new ArrayList<String>();

	private EditText messageEditText;
	private ConfigEntity configEntity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userListAdapter = new UserListListAdapter(this);
		initData();
		Intent intent = getIntent();
		intent.getIntExtra("RoomID", 0);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		InitialLayout();

	}

	public void initData() {
		configEntity = ConfigService.LoadConfig(this);
		this.InitialPreSDK();
		anychat.Connect(configEntity.ip, configEntity.port);
		int times = anychat.Login("quanquan", "1");
		this.ApplyVideoConfig();
		anychat.Connect(configEntity.ip, configEntity.port);
		int rooms = anychat.EnterRoom(1, "");
		Toast.makeText(this, String.valueOf(times) + String.valueOf(rooms),
				Toast.LENGTH_LONG).show();
	}

	private void InitialPreSDK() {
		if (anychat == null) {
			anychat = new AnyChatCoreSDK();
			if (configEntity.useARMv6Lib != 0)
				AnyChatCoreSDK.SetSDKOptionInt(
						AnyChatDefine.BRAC_SO_CORESDK_USEARMV6LIB, 1);
			anychat.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
			anychat.SetBaseEvent(this);
			anychat.SetTextMessageEvent(this);
		}
	}

	private void ApplyVideoConfig() {
		ConfigEntity configEntity = ConfigService.LoadConfig(this);
		if (configEntity.configMode == 1) // �Զ�����Ƶ��������
		{
			// ���ñ�����Ƶ��������ʣ��������Ϊ0�����ʾʹ����������ģʽ��
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_LOCALVIDEO_BITRATECTRL,
					configEntity.videoBitrate);
			if (configEntity.videoBitrate == 0) {
				// ���ñ�����Ƶ���������
				AnyChatCoreSDK.SetSDKOptionInt(
						AnyChatDefine.BRAC_SO_LOCALVIDEO_QUALITYCTRL,
						configEntity.videoQuality);
			}
			// ���ñ�����Ƶ�����֡��
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_LOCALVIDEO_FPSCTRL,
					configEntity.videoFps);
			// ���ñ�����Ƶ����Ĺؼ�֡���
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_LOCALVIDEO_GOPCTRL,
					configEntity.videoFps * 4);
			// ���ñ�����Ƶ�ɼ��ֱ���
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL,
					configEntity.resolution_width);
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL,
					configEntity.resolution_height);
			// ������Ƶ����Ԥ�������ֵԽ�󣬱�������Խ�ߣ�ռ��CPU��ԴҲ��Խ�ߣ�
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_LOCALVIDEO_PRESETCTRL,
					configEntity.videoPreset);
		}
		// ����Ƶ������Ч
		AnyChatCoreSDK.SetSDKOptionInt(
				AnyChatDefine.BRAC_SO_LOCALVIDEO_APPLYPARAM,
				configEntity.configMode);
		// P2P����
		AnyChatCoreSDK.SetSDKOptionInt(
				AnyChatDefine.BRAC_SO_NETWORK_P2PPOLITIC,
				configEntity.enableP2P);
		// ������ƵOverlayģʽ����
		AnyChatCoreSDK.SetSDKOptionInt(
				AnyChatDefine.BRAC_SO_LOCALVIDEO_OVERLAY,
				configEntity.videoOverlay);
		// ������������
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_ECHOCTRL,
				configEntity.enableAEC);
		// ƽ̨Ӳ����������
		AnyChatCoreSDK.SetSDKOptionInt(
				AnyChatDefine.BRAC_SO_CORESDK_USEHWCODEC,
				configEntity.useHWCodec);
		// ��Ƶ��תģʽ����
		AnyChatCoreSDK.SetSDKOptionInt(
				AnyChatDefine.BRAC_SO_LOCALVIDEO_ROTATECTRL,
				configEntity.videorotatemode);
		// ��Ƶƽ������ģʽ����
		AnyChatCoreSDK.SetSDKOptionInt(
				AnyChatDefine.BRAC_SO_STREAM_SMOOTHPLAYMODE,
				configEntity.smoothPlayMode);
		// ��Ƶ�ɼ���������
		AnyChatCoreSDK.SetSDKOptionInt(
				AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER,
				configEntity.videoCapDriver);
		// ������Ƶ�ɼ�ƫɫ��������
		AnyChatCoreSDK.SetSDKOptionInt(
				AnyChatDefine.BRAC_SO_LOCALVIDEO_FIXCOLORDEVIA,
				configEntity.fixcolordeviation);
		// ��Ƶ��ʾ��������
		AnyChatCoreSDK.SetSDKOptionInt(
				AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL,
				configEntity.videoShowDriver);
		// ��Ƶ������������
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_PLAYDRVCTRL,
				configEntity.audioPlayDriver);
		// ��Ƶ�ɼ���������
		AnyChatCoreSDK.SetSDKOptionInt(
				AnyChatDefine.BRAC_SO_AUDIO_RECORDDRVCTRL,
				configEntity.audioRecordDriver);
	}

	private void InitialLayout() {
		this.setTitle("ȦȦ��Ƶ����");
		fullLayout = new LinearLayout(this);
		fullLayout.setBackgroundResource(R.drawable.chat_bk);
		// fullLayout.setBackgroundColor(Color.WHITE);
		fullLayout.setOrientation(LinearLayout.VERTICAL);
		fullLayout.setOnTouchListener(touchListener);
		mainLayout = new LinearLayout(this);
		mainLayout.setBackgroundColor(Color.TRANSPARENT);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setOnTouchListener(touchListener);
		LinearLayout sendLayout = new LinearLayout(this);
		sendLayout.setOrientation(LinearLayout.HORIZONTAL);
		messageEditText = new EditText(this);
		sendBtn = new Button(this);
		sendBtn.setText("ȷ��");
		sendBtn.setOnClickListener(listener);

		sendLayout.addView(messageEditText, new LayoutParams(
				ScreenInfo.WIDTH * 4 / 5, LayoutParams.FILL_PARENT));
		sendLayout.addView(sendBtn, new LayoutParams(ScreenInfo.WIDTH / 5,
				LayoutParams.FILL_PARENT));
		mainLayout.addView(sendLayout, new LayoutParams(
				LayoutParams.FILL_PARENT, ScreenInfo.HEIGHT / 10));

		TextView tvMessage = new TextView(this);
		tvMessage.setTextColor(Color.WHITE);
		tvMessage.setPadding(0, 2, 0, 2);
		tvMessage.setTextSize(18);
		tvMessage.setText("����");
		tvMessage.setBackgroundColor(Color.GRAY);
		mainLayout.addView(tvMessage, new LayoutParams(
				LayoutParams.FILL_PARENT, ScreenInfo.HEIGHT * 1 / 20));

		messageListView = new MessageListView(this);
		messageListView.SetFileList(messageList);

		mainLayout.addView(messageListView, new LayoutParams(ScreenInfo.WIDTH,
				ScreenInfo.HEIGHT * 4 / 10));

		TextView tv = new TextView(this);
		tv.setBackgroundColor(Color.GRAY);
		tv.setTextColor(Color.WHITE);
		tv.setPadding(0, 2, 0, 2);
		tv.setTextSize(18);
		tv.setText("����");
		tv.setBackgroundColor(Color.GRAY);
		mainLayout.addView(tv, new LayoutParams(LayoutParams.FILL_PARENT,
				ScreenInfo.HEIGHT * 1 / 20));

		userListView = new ListView(this);
		userListView.setCacheColorHint(0);
		userListView.setBackgroundColor(Color.TRANSPARENT);
		userListView.setAdapter(userListAdapter);
		userListView.setOnItemClickListener(itemClickListener);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.weight = 1;
		mainLayout.addView(userListView, layoutParams);
		fullLayout.addView(mainLayout, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		// fullLayout.addView(btnLayout,new
		// LayoutParams(ScreenInfo.WIDTH,ScreenInfo.HEIGHT/10));
		this.setContentView(fullLayout);
	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			if (v == sendBtn) {
				SendMessage();
			}
		}
	};
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			StartVideoChat(arg2);
		}
	};

	public class UserListListAdapter extends BaseAdapter {
		private Context context;

		public UserListListAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return userList.size();
		}

		@Override
		public Object getItem(int position) {
			return userList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = new TextView(context);
			tv.setTextColor(Color.YELLOW);
			tv.setPadding(4, 4, 4, 4);
			tv.setTextSize(24);
			tv.setBackgroundColor(color.black);
			tv.setText(userList.get(position));
			return tv;
		}
	}

	private void SendMessage() {
		anychat.SendTextMessage(-1, 0, messageEditText.getText().toString());
		messageList.add("��˵: " + messageEditText.getText().toString());
		// messageListView.setStackFromBottom(true);
		messageListView.SetFileList(messageList);
		messageEditText.setText("");
		messageListView
				.setSelection(messageListView.getAdapter().getCount() - 1);

	}

	public void StartVideoChat(int position) {
		Intent intent = new Intent();
		intent.putExtra("UserID", idList.get(position));
		intent.setClass(RoomActivity.this, VideoActivity.class);
		startActivity(intent);
	}

	private OnTouchListener touchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent e) {
			// TODO Auto-generated method stub
			switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				try {
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(RoomActivity.this
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

	protected void onDestroy() {
		Log.e("******RoomActivity***********", "RoomActivity  onDestroy");
		anychat.LeaveRoom(-1);
		super.onDestroy();
	}

	protected void onResume() {
		anychat.SetBaseEvent(this);
		idList.clear();
		userList.clear();
		int[] userID = anychat.GetOnlineUser();
		for (int i = 0; i < userID.length; i++) {
			idList.add("" + userID[i]);
			Toast.makeText(this, String.valueOf(userID[i]), Toast.LENGTH_LONG)
					.show();
		}
		for (int i = 0; i < userID.length; i++) {
			userList.add("ȦȦ�Ñ�: " + String.valueOf(i));

		}
		userListAdapter.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		// TODO Auto-generated method stub
		Log.e("********RoomActivity*********", "OnAnyChatEnterRoomMessage");

	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub
		Log.e("********RoomActivity*********", "OnAnyChatOnlineUserMessage   "
				+ dwUserNum);

	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		// TODO Auto-generated method stub
		if (bEnter) {
			idList.add("" + dwUserId);
			userList.add(anychat.GetUserName(dwUserId));
			userListAdapter.notifyDataSetChanged();

		} else {
			for (int i = 0; i < idList.size(); i++) {
				if (idList.get(i).equals("" + dwUserId)) {
					idList.remove(i);
					userList.remove(i);
					userListAdapter.notifyDataSetChanged();
				}
			}

		}
	}

	@Override
	public void OnAnyChatTextMessage(int dwFromUserid, int dwToUserid,
			boolean bSecret, String message) {
		messageList.add(anychat.GetUserName(dwFromUserid) + "˵: " + message);
		// messageListView.setStackFromBottom(true);
		messageListView.SetFileList(messageList);
		messageListView
				.setSelection(messageListView.getAdapter().getCount() - 1);
		// messageListView.scrollTo(0, Integer.MAX_VALUE);
	}

}
