package com.example.freebox;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.freebox.ProfileActivity.DataGetTask;
import com.example.freebox.adapter.AddressListAdapter;
import com.example.freebox.adapter.ChatMsgViewAdapter;
import com.example.freebox.adapter.ContactAdapter;
import com.example.freebox.adapter.IdeasAdapter;
import com.example.freebox.adapter.MessageAdapter;
import com.example.freebox.adapter.QuanQuanAdapter;
import com.example.freebox.adapter.UniteAdapter;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;
import com.example.freebox.data.JSONFriendListItemEntity;
import com.example.freebox.data.JSONFriendsListEntity;
import com.example.freebox.data.JSONIdeasItem;
import com.example.freebox.data.JSONMessageEntity;
import com.example.freebox.data.JSONQuanQuanListEntity;
import com.example.freebox.data.JSONQuanQuanListItem;
import com.example.freebox.entity.ChatMsgEntity;
import com.example.freebox.entity.MessageEntity;
import com.example.freebox.entity.QuanQuanEntity;
import com.example.freebox.entity.UserEntity;
import com.example.freebox.push.DataHandler;
import com.example.freebox.ui.SideBar;
import com.example.freebox.utils.DataBaseHandler;
import com.example.freebox.utils.DataGetHelper;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

	public static Main instance = null;
	private ViewPager mTabPager;
	private ImageView mTabImg;// ����ͼƬ
	private ImageView mTab1, mTab2, mTab3, mTab4, mTab5;
	private int zero = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int one;// ����ˮƽ����λ��
	private int two;
	private int three;
	private int four;
	private LinearLayout mClose;
	private LinearLayout mCloseBtn;
	private View layout;
	private DataGetHelper mDataGetHelper;
	private DataGetHelper mDataGetHelper2;
	private ListView mListView;
	private MessageAdapter mAdapter;
	private ArrayList<MessageEntity> mMessageEntityArrays = new ArrayList<MessageEntity>();
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;

	// ��Ϣ����
	private TextView mFriendMSGCountText;
	private TextView mQuanMSGCountText;
	private TextView mCollegeMSGCountText;
	private TextView mAcademyMSGCountText;
	private TextView mClassMSGCountText;
	private TextView mSystemMSGCountText;
	private int friendscount = 0;
	private int quancount = 0;
	private int collegecount = 0;
	private int academycoount = 0;
	private int classcount = 0;
	private int systemcount = 0;

	private List<MessageEntity> mDataArrays = new ArrayList<MessageEntity>();

	// ��������
	private String mTaskFlagMyQuan = "myquanlist";
	private String mTaskFlagMyFriend = "myfriendlist";
	private String mQuanListJsonString;
	private String mFriendListJsonString;
	// �ҵ�ȦȦ�б�
	private JSONQuanQuanListEntity mQuanListEntity = new JSONQuanQuanListEntity();
	private JSONQuanQuanListItem mQuanItem;
	private List<JSONQuanQuanListItem> mQuanList = new ArrayList<JSONQuanQuanListItem>();
	private ArrayList<QuanQuanEntity> mQuanQuanEntityArrays = new ArrayList<QuanQuanEntity>();
	// �ҵĺ����б�
	private JSONFriendsListEntity mFriendsListEntity = new JSONFriendsListEntity();
	private JSONFriendListItemEntity mFriendItem;
	private List<JSONFriendListItemEntity> mFriendList = new ArrayList<JSONFriendListItemEntity>();
	private ArrayList<UserEntity> mUserEntityArrays = new ArrayList<UserEntity>();
	private Handler mHandler;

	// ͨѶ¼�б�
	private ListView lvAddressGroup, lvContact, lvMyQuanQuan;
	private SideBar indexBar;
	private WindowManager mWindowManager;
	private TextView mDialogText;
	private Button address_back_btn, more_function_btn;
	public UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient = new HttpClientEntity();
	private DataGetTask task;
	private DataGetTask quan_data_task, friend_data_task;

	// ideas��������
	private GridView flaggrid;
	private ListView ideaslist;
	private IdeasAdapter ideaAdapter;
	private IdeasDataGetTask ideasdatagettask;
	private Button sendbutton;
	private EditText ideastext;
	private ArrayList<JSONIdeasItem> mIdeasList = new ArrayList<JSONIdeasItem>();

	// ȦȦ����
	private GridView gridListView;

	private QuanQuanAdapter gridviewadapter;
	private List<String> lstData;

	private UniteAdapter mQuanListAdapter;
	private ContactAdapter mContactAdapter;

	public Main() {
		super();
		DataHandler.MainActivity = this;
	}

	// private Button mRightBtn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_weixin);
		// ����activityʱ���Զ����������
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		instance = this;
		/*
		 * mRightBtn = (Button) findViewById(R.id.right_btn);
		 * mRightBtn.setOnClickListener(new Button.OnClickListener() { @Override
		 * public void onClick(View v) { showPopupWindow
		 * (MainWeixin.this,mRightBtn); } });
		 */

		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mTab1 = (ImageView) findViewById(R.id.img_weixin);
		mTab2 = (ImageView) findViewById(R.id.img_address);
		mTab3 = (ImageView) findViewById(R.id.img_friends);
		mTab4 = (ImageView) findViewById(R.id.img_quanquan);
		mTab5 = (ImageView) findViewById(R.id.img_settings);
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
		mTab5.setOnClickListener(new MyOnClickListener(4));
		Display currDisplay = getWindowManager().getDefaultDisplay();// ��ȡ��Ļ��ǰ�ֱ���
		int displayWidth = currDisplay.getWidth();
		int displayHeight = currDisplay.getHeight();
		one = displayWidth / 5; // ����ˮƽ����ƽ�ƴ�С
		two = one * 2;
		three = one * 3;
		four = one * 4;
		// Log.i("info", "��ȡ����Ļ�ֱ���Ϊ" + one + two + three + "X" + displayHeight);
		// InitImageView();//ʹ�ö���
		// ��Ҫ��ҳ��ʾ��Viewװ��������
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.main_tab_weixin, null);
		mDataGetHelper = new DataGetHelper();
		mMessageEntityArrays = mDataGetHelper.initData();
		mAdapter = new MessageAdapter(this, mMessageEntityArrays);
		// ��Ϣ����
		// mFriendMSGCountText = (TextView)
		// findViewById(R.id.friends_msg_count_text);
		// mQuanMSGCountText = (TextView)
		// findViewById(R.id.quan_msg_count_text);
		// mCollegeMSGCountText = (TextView)
		// findViewById(R.id.college_msg_count_text);
		// mAcademyMSGCountText = (TextView)
		// findViewById(R.id.academy_msg_count_text);
		// mClassMSGCountText = (TextView)
		// findViewById(R.id.class_msg_count_text);
		// mSystemMSGCountText = (TextView)
		// findViewById(R.id.system_msg_count_text);

		// ��ҳ�����ϵ���Ϣ����

		mListView = (ListView) view1.findViewById(R.id.message_listview);
		setMessageData();
		// mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(Main.this, ChatActivity.class);
				intent.putExtra("chat_type", "two");
				String stringid = mDataArrays.get(arg2).getName();
				int id = Integer.parseInt(stringid);
				String username = new DataBaseHandler(Main.this)
						.getUserNameById(stringid);
				Log.i("����˻�", "id:" + id + "�û���:" + username);
				intent.putExtra("name", username);

				intent.putExtra("user_guid", id);
				startActivity(intent);
				Toast.makeText(Main.this, "���һ����Ϣ" + arg2, Toast.LENGTH_SHORT)
						.show();
			}
		});
		// ��ҳ�����Ϣ�ϼ��б�
		View view2 = mLi.inflate(R.layout.main_tab_address, null);
		// ͨѶ¼����ؼ�����
		address_back_btn = (Button) view2.findViewById(R.id.btn_back);
		address_back_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lvContact.setVisibility(View.INVISIBLE);
				indexBar.setVisibility(View.INVISIBLE);
				lvMyQuanQuan.setVisibility(View.INVISIBLE);
				address_back_btn.setVisibility(View.INVISIBLE);
				lvAddressGroup.setVisibility(View.VISIBLE);
			}
		});
		more_function_btn = (Button) view2.findViewById(R.id.more_function_btn);
		more_function_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Main.this, MainTopRightDialog.class);
				intent.putExtra("dialog_type", "address_dialog");
				startActivity(intent);
			}
		});
		lvAddressGroup = (ListView) view2.findViewById(R.id.lv_addres_group);
		lvAddressGroup.setAdapter(new AddressListAdapter(this,
				Flags.FromAddress));
		// �ҵ�ȦȦ
		lvMyQuanQuan = (ListView) view2.findViewById(R.id.lv_my_quanquan);
		lvContact = (ListView) view2.findViewById(R.id.lvContact);
		// mDataGetHelper = new DataGetHelper();
		// �ҵ�ȦȦ�б��ȡ
		initMyQuanListData();
		mHandler = new Handler() {
			public void handleMessage(Message msg) {// �˷�����ui�߳�����
				switch (msg.what) {
				case Flags.MSG_SUCCESS:
					// �����ҵ�ȦȦ�б�
					mDataGetHelper = new DataGetHelper(mQuanListEntity);
					// Log.i("�鿴���",
					// mQuanListEntity.getQuanItem(1).getQuanName());
					mQuanQuanEntityArrays = mDataGetHelper
							.initQuanQuanDataList();
					mQuanListAdapter = new UniteAdapter(Main.this,
							mQuanQuanEntityArrays, true);
					lvMyQuanQuan.setAdapter(mQuanListAdapter);
					break;
				case Flags.MSG_SUCCESS_FRIEND:
					// �����ҵĺ����б�
					mDataGetHelper2 = new DataGetHelper(mFriendsListEntity);
					// Log.i("�鿴���",
					// mFriendsListEntity.getFriendItem(1).getName());
					mUserEntityArrays = mDataGetHelper2.initFriendsDataList();
					mContactAdapter = new ContactAdapter(Main.this,
							mUserEntityArrays);
					lvContact.setAdapter(mContactAdapter);
					break;
				case Flags.MSG_FAILURE:
					new AlertDialog.Builder(Main.this)
							.setIcon(
									getResources().getDrawable(
											R.drawable.login_error_icon))
							.setTitle("��¼����")
							.setMessage("freebox�ʺŻ��������\n���������룡").create()
							.show();
					break;
				case Flags.MSG_NEW_FRIEND_MESSAGE:
					friendscount++;
					Toast.makeText(Main.this, "�յ�һ������Ϣ" + friendscount,
							Toast.LENGTH_LONG).show();
					break;
				case Flags.MSG_NEW_QUAN_MESSAGE:
					quancount++;
					// mQuanMSGCountText.setText("(" + quancount + ")");
					break;
				case Flags.MSG_UPDATE_IDEAS:
					ideaAdapter = new IdeasAdapter(Main.this, mIdeasList);
					ideaslist.setAdapter(ideaAdapter);
					break;
				}
			}
		};

		lvMyQuanQuan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long arg3) {

				Log.i("�����Ϣ", "�����item");
				Intent intent = new Intent();
				intent.putExtra("chat_type", "multi");
				TextView t = (TextView) findViewById(R.id.app_title);
				intent.putExtra("name", mQuanListEntity.getQuanItem(position)
						.getQuanName());
				intent.putExtra("quan_guid",
						mQuanListEntity.getQuanItem(position).getQuanId());
				intent.putExtra("dialog_type", "quanquan");
				intent.setClass(Main.this, ChatActivity.class);
				startActivity(intent);
			}

		});
		lvAddressGroup.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					initMyQuanListData();
					lvContact.setVisibility(View.VISIBLE);
					indexBar.setVisibility(View.VISIBLE);
					address_back_btn.setVisibility(View.VISIBLE);
					lvAddressGroup.setVisibility(View.INVISIBLE);
					break;
				case 1:
					Log.i("�����Ϣ", "�ҵ�ȦȦ");
					initMyQuanListData();
					lvMyQuanQuan.setVisibility(View.VISIBLE);
					address_back_btn.setVisibility(View.VISIBLE);
					lvAddressGroup.setVisibility(View.INVISIBLE);
					break;
				case 2:
					Intent intent = new Intent();
					intent.putExtra("quanquan_type", "У԰ȦȦ");
					intent.setClass(Main.this, QuanQuanProfileActivity.class);
					startActivity(intent);
					break;
				case 3:
					Intent intent2 = new Intent();
					intent2.putExtra("quanquan_type", "ѧԺȦȦ");
					intent2.setClass(Main.this, QuanQuanProfileActivity.class);
					startActivity(intent2);
					break;
				case 4:
					Intent intent3 = new Intent();
					intent3.putExtra("quanquan_type", "�༶ȦȦ");
					intent3.setClass(Main.this, QuanQuanProfileActivity.class);
					startActivity(intent3);
					break;
				}
			}

		});
		// task = new DataGetTask();
		// task.execute(APILinkEntity.mBasicAPI);
		// �����б��ȡ
		// initMyFriendListData();
		// lvContact.setAdapter(new ContactAdapter(this));
		lvContact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long arg3) {
				// TODO Auto-generated method stub
				TextView user_name = (TextView) v
						.findViewById(R.id.contactitem_nick);
				String user_name_text = (String) user_name.getText();
				Intent intent = new Intent();
				intent.putExtra("chat_type", "two");
				intent.putExtra("from", Flags.FromFriend);
				intent.putExtra("user_guid",
						mFriendsListEntity.getFriendItem(position).getUserid());
				intent.putExtra("name",
						mFriendsListEntity.getFriendItem(position)
								.getUserName());
				intent.setClass(Main.this, ProfileActivity.class);
				startActivity(intent);
			}
		});
		indexBar = (SideBar) view2.findViewById(R.id.sideBar);
		indexBar.setListView(lvContact);
		mDialogText = (TextView) LayoutInflater.from(this).inflate(
				R.layout.list_position, null);
		mDialogText.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		mWindowManager.addView(mDialogText, lp);
		indexBar.setTextView(mDialogText);
		// idea����
		View view3 = mLi.inflate(R.layout.main_tab_ideas, null);
		sendbutton = (Button) view3.findViewById(R.id.send_ideas_btn);
		ideastext = (EditText) view3.findViewById(R.id.et_sendideas);
		sendbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(Main.this, "����ideas��Ϣ", Toast.LENGTH_SHORT)
						.show();
				SendIdeasTask task = new SendIdeasTask();
				task.execute(APILinkEntity.mBasicAPI);

			}

		});
		flaggrid = (GridView) view3.findViewById(R.id.flags_grid);
		flaggrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					ideasdatagettask = new IdeasDataGetTask();
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "����");
					break;
				case 1:
					ideasdatagettask = new IdeasDataGetTask();
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "��ʳ");
					break;
				case 2:
					ideasdatagettask = new IdeasDataGetTask();
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "����");
					break;
				case 3:
					ideasdatagettask = new IdeasDataGetTask();
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "��ѧ");
					break;
				case 4:
					ideasdatagettask = new IdeasDataGetTask();
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "��Ӱ");
					break;
				case 5:
					ideasdatagettask = new IdeasDataGetTask();
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "����");
					break;
				}

			}
		});
		ideaslist = (ListView) view3.findViewById(R.id.lv_ideas_list);
		// mAdapter = new MessageAdapter(this, mDataArrays);
		lstData = new ArrayList<String>();
		lstData.add("����");
		lstData.add("��ʳ");
		lstData.add("����");
		lstData.add("��ѧ");
		lstData.add("��Ӱ");
		lstData.add("����");
		QuanQuanAdapter flagadapter = new QuanQuanAdapter(this, lstData, true);
		flaggrid.setAdapter(flagadapter);
		// ȦȦ���������
		View view4 = mLi.inflate(R.layout.main_tab_quanquan, null);
		gridListView = (GridView) view4.findViewById(R.id.list_gridView);
		lstData = new ArrayList<String>();
		lstData.add("��ϷȦ");
		lstData.add("����Ȧ");
		lstData.add("�Ķ�Ȧ");
		lstData.add("���Ȧ");
		gridviewadapter = new QuanQuanAdapter(this, lstData, false);
		gridListView.setAdapter(gridviewadapter);
		gridListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				TextView text = (TextView) v.findViewById(R.id.txt_userAge);
				String app_name_text = (String) text.getText();
				Log.i("�����Ϣ", "" + app_name_text);
				intent.putExtra("app_name", app_name_text);
				intent.setClass(Main.this, AppsActivity.class);
				startActivity(intent);
				Toast toast = Toast.makeText(Main.this, "����" + app_name_text,
						Toast.LENGTH_SHORT);
				toast.show();
			}

		});
		View view5 = mLi.inflate(R.layout.main_tab_settings, null);
		// ÿ��ҳ���view����
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		views.add(view5);
		// ���ViewPager������������
		PagerAdapter mPagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			// @Override
			// public CharSequence getPageTitle(int position) {
			// return titles.get(position);
			// }

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mTabPager.setAdapter(mPagerAdapter);
	}

	private void initMyQuanListData() {
		// TODO Auto-generated method stub
		quan_data_task = new DataGetTask();
		quan_data_task.execute(APILinkEntity.mBasicAPI, mTaskFlagMyQuan);
	}

	private void initMyFriendListData() {
		// TODO Auto-generated method stub
		friend_data_task = new DataGetTask();
		friend_data_task.execute(APILinkEntity.mBasicAPI, mTaskFlagMyFriend);
	}

	// ��ȡ�б�
	public class DataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String result = null;
			Log.i("��ʼ��̨��ȡ", "��ʼtask");
			try {
				// if (arg0[1].equals(mTaskFlagMyFriend)) {

				// ��ȡ�����б�
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				Log.i("�ڶ���token", token);
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("method",
						APILinkEntity.mGetFriendsListMethod));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("auth_token", token));
				params1.add(new BasicNameValuePair("t", token));
				params1.add(new BasicNameValuePair("1", null));
				params1.add(new BasicNameValuePair("o", null));
				try {
					paramsEntity = new UrlEncodedFormEntity(params1, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = mClient.PostData(arg0[0], paramsEntity);
				mFriendListJsonString = result;
				// ��ȡȦȦ�б�
				List<NameValuePair> params2 = new ArrayList<NameValuePair>();
				params2.add(new BasicNameValuePair("method",
						APILinkEntity.mGetMyQuanListMethod));
				params2.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params2.add(new BasicNameValuePair("auth_token", token));
				params2.add(new BasicNameValuePair("APIKey", Flags.APIKEY));
				params2.add(new BasicNameValuePair("token", token));
				try {
					paramsEntity = new UrlEncodedFormEntity(params2, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = mClient.PostData(arg0[0], paramsEntity);
				mQuanListJsonString = result;
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (JSONException e) {

				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				// �����б��ȡ
				JSONObject friendjsonobject = new JSONObject(
						mFriendListJsonString);
				// result��������
				String friend_data_list = friendjsonobject.getString("result");
				JSONObject jsonobject3 = new JSONObject(friend_data_list);
				String s_flag = jsonobject3.getString("s");
				mFriendList.clear();
				if (s_flag.equals("1")) {
					JSONArray friends_array = jsonobject3.getJSONArray("d");
					for (int i = 0; i < friends_array.length(); i++) {
						JSONObject friendjsonitem = friends_array
								.getJSONObject(i);
						int id = friendjsonitem.getInt("guid");
						String name = friendjsonitem.getString("name");
						String username = friendjsonitem.getString("username");
						// int avatarnum=friendjsonitem.getInt("avatar");
						int wonline = friendjsonitem.getInt("wonline");
						int conline = friendjsonitem.getInt("conline");
						mFriendItem = new JSONFriendListItemEntity();
						mFriendItem.setName(name);
						mFriendItem.setGuid(id);
						mFriendItem.setUserName(username);
						mFriendItem.setWonline(wonline);
						mFriendItem.setConline(conline);
						// mFriendItem.setAvatarNum(avatarnum);
						mFriendList.add(mFriendItem);
						// ���û���Ϣ�������ݿ�
						Log.i("�����û���Ϣ", "start");
						UserEntity userentity = new UserEntity();
						userentity.setName(name);
						userentity.setGuid(id);
						userentity.setUserName(username);
						userentity.setWonline(wonline);
						userentity.setConline(conline);
						new DataBaseHandler(Main.this)
								.insertToUserTable(userentity);
						Message message = new Message();
						message.what = Flags.MSG_SUCCESS_FRIEND;
						mHandler.sendMessage(message);
					}
					mFriendsListEntity.setFriendList(mFriendList);
				}
				// ȦȦ�б��ȡ
				JSONObject quanjsonobject = new JSONObject(mQuanListJsonString);
				// result��������
				String quan_data_list = quanjsonobject.getString("result");
				JSONObject jsonobject2 = new JSONObject(quan_data_list);
				String s_flag2 = jsonobject2.getString("s");
				mQuanList.clear();
				if (s_flag2.equals("1")) {
					JSONArray quan_array = jsonobject2.getJSONArray("m");
					for (int i = 0; i < quan_array.length(); i++) {
						Log.i("index", "" + i);
						JSONObject jsonitem = quan_array.getJSONObject(i);
						int id = jsonitem.getInt("id");
						String name = jsonitem.getString("name");
						String tags = jsonitem.getString("tags");
						int img = jsonitem.getInt("img");
						mQuanItem = new JSONQuanQuanListItem();
						mQuanItem.setQuanAvatar(img);
						mQuanItem.setQuanName(name);
						mQuanItem.setQuanTag(tags);
						mQuanItem.setQuanId(id);
						mQuanList.add(mQuanItem);
						QuanQuanEntity quanentity = new QuanQuanEntity();
						Log.i("ȦȦitem����", id + name + tags);
						Message message = new Message();
						message.what = Flags.MSG_SUCCESS;
						mHandler.sendMessage(message);
					}
					mQuanListEntity.setQuanList(mQuanList);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class IdeasDataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String result = null;
			try {
				// if (arg0[1].equals(mTaskFlagMyFriend)) {

				// ��ȡ�����б�
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				Log.i("�ڶ���token", token);
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("method",
						APILinkEntity.mGetIdeaMessageByTag));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("APIKey", Flags.APIKEY));
				params1.add(new BasicNameValuePair("auth_token", token));
				params1.add(new BasicNameValuePair("token", token));
				params1.add(new BasicNameValuePair("q", arg0[1]));
				params1.add(new BasicNameValuePair("o", null));
				try {
					paramsEntity = new UrlEncodedFormEntity(params1, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = mClient.PostData(arg0[0], paramsEntity);
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (JSONException e) {

				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				// ideas�б��ȡ
				JSONObject friendjsonobject = new JSONObject(result);
				// result��������
				String ideas_list = friendjsonobject.getString("result");
				JSONObject jsonobject = new JSONObject(ideas_list);
				String s_flag = jsonobject.getString("s");
				Log.i("���s_flag", s_flag);
				mIdeasList.clear();
				if (s_flag.equals("1")) {
					JSONArray ideas_array = jsonobject.getJSONArray("d");
					for (int i = 0; i < ideas_array.length(); i++) {
						JSONObject ideaitem = ideas_array.getJSONObject(i);
						int id = ideaitem.getInt("id");
						String name = ideaitem.getString("name");
						String username = ideaitem.getString("username");
						int wonline = ideaitem.getInt("wonline");
						int conline = ideaitem.getInt("conline");
						String description = ideaitem.getString("description");
						JSONIdeasItem idea = new JSONIdeasItem();
						idea.setId(id);
						idea.setName(name);
						idea.setUserName(username);
						idea.setWonline(wonline);
						idea.setConline(conline);
						idea.setDescription(description);
						mIdeasList.add(idea);
					}
				} else {
					Toast.makeText(Main.this, "���޸ñ�ǩ��ideas��Ϣ������",
							Toast.LENGTH_SHORT).show();
				}
				Message message = new Message();
				message.what = Flags.MSG_UPDATE_IDEAS;
				mHandler.sendMessage(message);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class SendIdeasTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String result = null;
			Log.i("��ʼ��̨��ȡ", "��ʼtask");
			try {
				// if (arg0[1].equals(mTaskFlagMyFriend)) {

				// ��ȡ�����б�
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				String username = sharedPreferences.getString("user_name",
						"none");
				Log.i("�����û���", username);
				Log.i("�ڶ���token", token);
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("method",
						APILinkEntity.mSendIdeaMessageMethod));
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("APIKey", Flags.APIKEY));
				params1.add(new BasicNameValuePair("auth_token", token));
				params1.add(new BasicNameValuePair("token", token));
				params1.add(new BasicNameValuePair("u", username));
				params1.add(new BasicNameValuePair("txt", ideastext.getText()
						.toString()));
				try {
					paramsEntity = new UrlEncodedFormEntity(params1, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = mClient.PostData(arg0[0], paramsEntity);
				mFriendListJsonString = result;
				Log.i("����ҵĺ����б�", mFriendListJsonString);
				// }else if(arg0[1].equals(mTaskFlagMyQuan)){
				Log.i("�ڶ���token", token);

				// }
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (JSONException e) {

				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				// �����б��ȡ
				JSONObject friendjsonobject = new JSONObject(
						mFriendListJsonString);
				// result��������
				String friend_data_list = friendjsonobject.getString("result");
				JSONObject jsonobject3 = new JSONObject(friend_data_list);
				String s_flag = jsonobject3.getString("s");
				Log.i("���s_flag", s_flag);
				mFriendList.clear();
				if (s_flag.equals("1")) {
					Toast.makeText(Main.this, "�µ�ideas�����ɹ�������",
							Toast.LENGTH_SHORT).show();
					ideastext.setText("");
				} else {
					Toast.makeText(Main.this, "Idea����ʧ�ܣ������ԣ�����",
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void findView() {
		lvContact = (ListView) this.findViewById(R.id.lvContact);
		lvContact.setAdapter(new ContactAdapter(this));
		indexBar = (SideBar) findViewById(R.id.sideBar);
		indexBar.setListView(lvContact);
		mDialogText = (TextView) LayoutInflater.from(this).inflate(
				R.layout.list_position, null);
		mDialogText.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		mWindowManager.addView(mDialogText, lp);
		indexBar.setTextView(mDialogText);
	}

	/**
	 * ͷ��������
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	/*
	 * ҳ���л�����(ԭ����:D.Winter)
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_weixin_pressed));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_address_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_search_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_quanquan_normal));
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, 0, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_settings_normal));
				}
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_address_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_weixin_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_search_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_quanquan_normal));
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, one, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_settings_normal));
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_search_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_weixin_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_address_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_quanquan_normal));
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, two, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_settings_normal));
				}
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_quanquan_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, three, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_weixin_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_address_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_search_normal));
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, three, 0, 0);
					mTab5.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_settings_normal));
				}
				break;
			case 4:
				mTab5.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_settings_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, four, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_weixin_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, four, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_address_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, four, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_search_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, four, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_quanquan_normal));
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(150);
			mTabImg.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // ��ȡ
																				// back��

			if (menu_display) { // ��� Menu�Ѿ��� ���ȹر�Menu
				menuWindow.dismiss();
				menu_display = false;
			} else {
				Intent intent = new Intent();
				// intent.setClass(MainWeixin.this,Exit.class);
				// startActivity(intent);
			}
		}

		else if (keyCode == KeyEvent.KEYCODE_MENU) { // ��ȡ Menu��
			if (!menu_display) {
				// ��ȡLayoutInflaterʵ��
				inflater = (LayoutInflater) this
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				// �����main��������inflate�м����Ŷ����ǰ����ֱ��this.setContentView()�İɣ��Ǻ�
				// �÷������ص���һ��View�Ķ����ǲ����еĸ�
				layout = inflater.inflate(R.layout.main_menu, null);

				// ��������Ҫ�����ˣ����������ҵ�layout���뵽PopupWindow���أ������ܼ�
				menuWindow = new PopupWindow(layout, LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT); // ������������width��height
				// menuWindow.showAsDropDown(layout); //���õ���Ч��
				// menuWindow.showAsDropDown(null, 0, layout.getHeight());
				menuWindow.showAtLocation(this.findViewById(R.id.mainweixin),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��
				// ��λ�ȡ����main�еĿؼ��أ�Ҳ�ܼ�
				mClose = (LinearLayout) layout.findViewById(R.id.menu_close);
				mCloseBtn = (LinearLayout) layout
						.findViewById(R.id.menu_close_btn);

				// �����ÿһ��Layout���е����¼���ע��ɡ�����
				// ���絥��ĳ��MenuItem��ʱ�����ı���ɫ�ı�
				// ����׼����һЩ����ͼƬ������ɫ
				mCloseBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Toast.makeText(Main.this, "�˳�", Toast.LENGTH_LONG)
								.show();
						ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
						manager.restartPackage(getPackageName());
						Main.this.finish();
						// menuWindow.dismiss(); // ��Ӧ����¼�֮��ر�Menu

					}
				});
				menu_display = true;
			} else {
				// �����ǰ�Ѿ�Ϊ��ʾ״̬������������
				menuWindow.dismiss();
				menu_display = false;
			}

			return false;
		}
		return false;
	}

	private static String[] nicks = { "����", "����", "��ɽ", "����", "ŷ����", "����",
			"����", "���", "���", "ܽ�ؽ��", "������", "ӣľ����", "������", "������", "÷����" };

	/**
	 * ����ת��λ����ƴ������ĸ��Ӣ���ַ�����
	 * 
	 * @param chines
	 *            ����
	 * @return ƴ��
	 */

	// ���ñ������Ҳఴť������
	// public void btnmainright(View v) {
	// Intent intent = new Intent(MainWeixin.this, MainTopRightDialog.class);
	// startActivity(intent);
	// Toast.makeText(getApplicationContext(), "����˹��ܰ�ť", Toast.LENGTH_LONG)
	// .show();
	// }

	// public void startchat(View v) { // С�� �Ի�����
	// TextView name = (TextView) v.findViewById(R.id.message_name);
	// String user_name = (String) name.getText();
	// Log.i("�����Ϣ", user_name);
	// Intent intent = new Intent();
	// intent.putExtra("name", user_name);
	// intent.putExtra("chat_type", "two");
	// intent.putExtra("dialog_type", "friend");
	// intent.setClass(Main.this, ChatActivity.class);
	// startActivity(intent);
	// Toast.makeText(getApplicationContext(), "����" + user_name + "����Ϣ",
	// Toast.LENGTH_LONG).show();
	// }

	public void MyProfileEdit(View v) {
		Intent intent = new Intent();
		intent.setClass(Main.this, MyProfileEditActivity.class);
		startActivity(intent);
	}

	private void setMessageData() {
		mDataArrays.clear();
		ArrayList<JSONMessageEntity> messageListEntity = new ArrayList<JSONMessageEntity>();
		DataBaseHandler databasehandler = new DataBaseHandler(Main.this);
		messageListEntity = databasehandler.getAllUnreadMessageEntity();
		// ѭ����Ϣ�б��ҳ������û�
		// ArrayList<String> sourceArrayList = new ArrayList<String>();
		// for (int j = 0; j < messageListEntity.size(); j++) {
		// JSONMessageEntity currentmessage = messageListEntity.get(j);
		// String messagetype = currentmessage.getType();
		// if (messagetype.equals("A")) {
		// }
		// }
		for (int i = 0; i < messageListEntity.size(); i++) {
			MessageEntity entity = new MessageEntity();
			entity.setTime(messageListEntity.get(i).getTimes());
			entity.setContent(messageListEntity.get(i).getContent());
			entity.setName(messageListEntity.get(i).getSources());
			mDataArrays.add(entity);
		}
		mAdapter = new MessageAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		mListView.setSelection(mListView.getCount() - 1);
	}

	public void makeReceiveSound() {
		try {
			Uri notification = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
					notification);
			r.play();
		} catch (Exception e) {
		}

	}

	public void setPushMessage(JSONMessageEntity messageentity) {
		int code = messageentity.getCode();
		makeReceiveSound();
		MessageEntity entity = new MessageEntity();
		// entity.setTime(messageentity.getTimes());
		entity.setContent(messageentity.getContent());
		entity.setName(messageentity.getSources());
		switch (code) {
		case Flags.MSG_P2P:
			Message message = new Message();
			message.what = Flags.MSG_NEW_FRIEND_MESSAGE;
			mHandler.sendMessage(message);
			break;
		case Flags.MSG_Group:
			Message message2 = new Message();
			message2.what = Flags.MSG_NEW_QUAN_MESSAGE;
			mHandler.sendMessage(message2);
			break;
		case Flags.MSG_SYSTEM:
			Message message3 = new Message();
			message3.what = Flags.MSG_NEW_SYSTEM_MESSAGE;
			mHandler.sendMessage(message3);
			break;
		}

		mAdapter.addItem(entity);
		mAdapter.notifyDataSetChanged();
		mListView.setSelection(mListView.getCount() - 1);

	}

}
