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
	private ImageView mTabImg;// 动画图片
	private ImageView mTab1, mTab2, mTab3, mTab4, mTab5;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;// 单个水平动画位移
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

	// 消息界面
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

	// 任务属性
	private String mTaskFlagMyQuan = "myquanlist";
	private String mTaskFlagMyFriend = "myfriendlist";
	private String mQuanListJsonString;
	private String mFriendListJsonString;
	// 我的圈圈列表
	private JSONQuanQuanListEntity mQuanListEntity = new JSONQuanQuanListEntity();
	private JSONQuanQuanListItem mQuanItem;
	private List<JSONQuanQuanListItem> mQuanList = new ArrayList<JSONQuanQuanListItem>();
	private ArrayList<QuanQuanEntity> mQuanQuanEntityArrays = new ArrayList<QuanQuanEntity>();
	// 我的好友列表
	private JSONFriendsListEntity mFriendsListEntity = new JSONFriendsListEntity();
	private JSONFriendListItemEntity mFriendItem;
	private List<JSONFriendListItemEntity> mFriendList = new ArrayList<JSONFriendListItemEntity>();
	private ArrayList<UserEntity> mUserEntityArrays = new ArrayList<UserEntity>();
	private Handler mHandler;

	// 通讯录列表
	private ListView lvAddressGroup, lvContact, lvMyQuanQuan;
	private SideBar indexBar;
	private WindowManager mWindowManager;
	private TextView mDialogText;
	private Button address_back_btn, more_function_btn;
	public UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient = new HttpClientEntity();
	private DataGetTask task;
	private DataGetTask quan_data_task, friend_data_task;

	// ideas搜索界面
	private GridView flaggrid;
	private ListView ideaslist;
	private IdeasAdapter ideaAdapter;
	private IdeasDataGetTask ideasdatagettask;
	private Button sendbutton;
	private EditText ideastext;
	private ArrayList<JSONIdeasItem> mIdeasList = new ArrayList<JSONIdeasItem>();

	// 圈圈内容
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
		// 启动activity时不自动弹出软键盘
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
		Display currDisplay = getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
		int displayWidth = currDisplay.getWidth();
		int displayHeight = currDisplay.getHeight();
		one = displayWidth / 5; // 设置水平动画平移大小
		two = one * 2;
		three = one * 3;
		four = one * 4;
		// Log.i("info", "获取的屏幕分辨率为" + one + two + three + "X" + displayHeight);
		// InitImageView();//使用动画
		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.main_tab_weixin, null);
		mDataGetHelper = new DataGetHelper();
		mMessageEntityArrays = mDataGetHelper.initData();
		mAdapter = new MessageAdapter(this, mMessageEntityArrays);
		// 消息数字
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

		// 首页界面上的消息队列

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
				Log.i("点击账户", "id:" + id + "用户名:" + username);
				intent.putExtra("name", username);

				intent.putExtra("user_guid", id);
				startActivity(intent);
				Toast.makeText(Main.this, "点击一条信息" + arg2, Toast.LENGTH_SHORT)
						.show();
			}
		});
		// 主页面的消息合集列表
		View view2 = mLi.inflate(R.layout.main_tab_address, null);
		// 通讯录界面控件部分
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
		// 我的圈圈
		lvMyQuanQuan = (ListView) view2.findViewById(R.id.lv_my_quanquan);
		lvContact = (ListView) view2.findViewById(R.id.lvContact);
		// mDataGetHelper = new DataGetHelper();
		// 我的圈圈列表获取
		initMyQuanListData();
		mHandler = new Handler() {
			public void handleMessage(Message msg) {// 此方法在ui线程运行
				switch (msg.what) {
				case Flags.MSG_SUCCESS:
					// 设置我的圈圈列表
					mDataGetHelper = new DataGetHelper(mQuanListEntity);
					// Log.i("查看结果",
					// mQuanListEntity.getQuanItem(1).getQuanName());
					mQuanQuanEntityArrays = mDataGetHelper
							.initQuanQuanDataList();
					mQuanListAdapter = new UniteAdapter(Main.this,
							mQuanQuanEntityArrays, true);
					lvMyQuanQuan.setAdapter(mQuanListAdapter);
					break;
				case Flags.MSG_SUCCESS_FRIEND:
					// 设置我的好友列表
					mDataGetHelper2 = new DataGetHelper(mFriendsListEntity);
					// Log.i("查看结果",
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
							.setTitle("登录错误")
							.setMessage("freebox帐号或密码错误，\n请重新输入！").create()
							.show();
					break;
				case Flags.MSG_NEW_FRIEND_MESSAGE:
					friendscount++;
					Toast.makeText(Main.this, "收到一条新信息" + friendscount,
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

				Log.i("输出信息", "点击了item");
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
					Log.i("输出信息", "我的圈圈");
					initMyQuanListData();
					lvMyQuanQuan.setVisibility(View.VISIBLE);
					address_back_btn.setVisibility(View.VISIBLE);
					lvAddressGroup.setVisibility(View.INVISIBLE);
					break;
				case 2:
					Intent intent = new Intent();
					intent.putExtra("quanquan_type", "校园圈圈");
					intent.setClass(Main.this, QuanQuanProfileActivity.class);
					startActivity(intent);
					break;
				case 3:
					Intent intent2 = new Intent();
					intent2.putExtra("quanquan_type", "学院圈圈");
					intent2.setClass(Main.this, QuanQuanProfileActivity.class);
					startActivity(intent2);
					break;
				case 4:
					Intent intent3 = new Intent();
					intent3.putExtra("quanquan_type", "班级圈圈");
					intent3.setClass(Main.this, QuanQuanProfileActivity.class);
					startActivity(intent3);
					break;
				}
			}

		});
		// task = new DataGetTask();
		// task.execute(APILinkEntity.mBasicAPI);
		// 好友列表获取
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
		// idea界面
		View view3 = mLi.inflate(R.layout.main_tab_ideas, null);
		sendbutton = (Button) view3.findViewById(R.id.send_ideas_btn);
		ideastext = (EditText) view3.findViewById(R.id.et_sendideas);
		sendbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(Main.this, "发送ideas消息", Toast.LENGTH_SHORT)
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
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "体育");
					break;
				case 1:
					ideasdatagettask = new IdeasDataGetTask();
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "美食");
					break;
				case 2:
					ideasdatagettask = new IdeasDataGetTask();
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "交友");
					break;
				case 3:
					ideasdatagettask = new IdeasDataGetTask();
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "文学");
					break;
				case 4:
					ideasdatagettask = new IdeasDataGetTask();
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "电影");
					break;
				case 5:
					ideasdatagettask = new IdeasDataGetTask();
					ideasdatagettask.execute(APILinkEntity.mBasicAPI, "唱歌");
					break;
				}

			}
		});
		ideaslist = (ListView) view3.findViewById(R.id.lv_ideas_list);
		// mAdapter = new MessageAdapter(this, mDataArrays);
		lstData = new ArrayList<String>();
		lstData.add("体育");
		lstData.add("美食");
		lstData.add("交友");
		lstData.add("文学");
		lstData.add("电影");
		lstData.add("唱歌");
		QuanQuanAdapter flagadapter = new QuanQuanAdapter(this, lstData, true);
		flaggrid.setAdapter(flagadapter);
		// 圈圈界面的内容
		View view4 = mLi.inflate(R.layout.main_tab_quanquan, null);
		gridListView = (GridView) view4.findViewById(R.id.list_gridView);
		lstData = new ArrayList<String>();
		lstData.add("游戏圈");
		lstData.add("音乐圈");
		lstData.add("阅读圈");
		lstData.add("软件圈");
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
				Log.i("输出信息", "" + app_name_text);
				intent.putExtra("app_name", app_name_text);
				intent.setClass(Main.this, AppsActivity.class);
				startActivity(intent);
				Toast toast = Toast.makeText(Main.this, "进入" + app_name_text,
						Toast.LENGTH_SHORT);
				toast.show();
			}

		});
		View view5 = mLi.inflate(R.layout.main_tab_settings, null);
		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		views.add(view5);
		// 填充ViewPager的数据适配器
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

	// 获取列表
	public class DataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String result = null;
			Log.i("开始后台获取", "开始task");
			try {
				// if (arg0[1].equals(mTaskFlagMyFriend)) {

				// 获取好友列表
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				Log.i("第二次token", token);
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
				// 获取圈圈列表
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
				// 好友列表获取
				JSONObject friendjsonobject = new JSONObject(
						mFriendListJsonString);
				// result对象生成
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
						// 将用户信息存入数据库
						Log.i("存入用户信息", "start");
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
				// 圈圈列表获取
				JSONObject quanjsonobject = new JSONObject(mQuanListJsonString);
				// result对象生成
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
						Log.i("圈圈item属性", id + name + tags);
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

				// 获取好友列表
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				Log.i("第二次token", token);
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
				// ideas列表获取
				JSONObject friendjsonobject = new JSONObject(result);
				// result对象生成
				String ideas_list = friendjsonobject.getString("result");
				JSONObject jsonobject = new JSONObject(ideas_list);
				String s_flag = jsonobject.getString("s");
				Log.i("输出s_flag", s_flag);
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
					Toast.makeText(Main.this, "还无该标签的ideas信息！！！",
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
			Log.i("开始后台获取", "开始task");
			try {
				// if (arg0[1].equals(mTaskFlagMyFriend)) {

				// 获取好友列表
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				String username = sharedPreferences.getString("user_name",
						"none");
				Log.i("发布用户名", username);
				Log.i("第二次token", token);
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
				Log.i("输出我的好友列表", mFriendListJsonString);
				// }else if(arg0[1].equals(mTaskFlagMyQuan)){
				Log.i("第二次token", token);

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
				// 好友列表获取
				JSONObject friendjsonobject = new JSONObject(
						mFriendListJsonString);
				// result对象生成
				String friend_data_list = friendjsonobject.getString("result");
				JSONObject jsonobject3 = new JSONObject(friend_data_list);
				String s_flag = jsonobject3.getString("s");
				Log.i("输出s_flag", s_flag);
				mFriendList.clear();
				if (s_flag.equals("1")) {
					Toast.makeText(Main.this, "新的ideas发布成功！！！",
							Toast.LENGTH_SHORT).show();
					ideastext.setText("");
				} else {
					Toast.makeText(Main.this, "Idea发布失败，请重试！！！",
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
	 * 头标点击监听
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
	 * 页卡切换监听(原作者:D.Winter)
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
			animation.setFillAfter(true);// True:图片停在动画结束位置
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
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 获取
																				// back键

			if (menu_display) { // 如果 Menu已经打开 ，先关闭Menu
				menuWindow.dismiss();
				menu_display = false;
			} else {
				Intent intent = new Intent();
				// intent.setClass(MainWeixin.this,Exit.class);
				// startActivity(intent);
			}
		}

		else if (keyCode == KeyEvent.KEYCODE_MENU) { // 获取 Menu键
			if (!menu_display) {
				// 获取LayoutInflater实例
				inflater = (LayoutInflater) this
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				// 这里的main布局是在inflate中加入的哦，以前都是直接this.setContentView()的吧？呵呵
				// 该方法返回的是一个View的对象，是布局中的根
				layout = inflater.inflate(R.layout.main_menu, null);

				// 下面我们要考虑了，我怎样将我的layout加入到PopupWindow中呢？？？很简单
				menuWindow = new PopupWindow(layout, LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT); // 后两个参数是width和height
				// menuWindow.showAsDropDown(layout); //设置弹出效果
				// menuWindow.showAsDropDown(null, 0, layout.getHeight());
				menuWindow.showAtLocation(this.findViewById(R.id.mainweixin),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
				// 如何获取我们main中的控件呢？也很简单
				mClose = (LinearLayout) layout.findViewById(R.id.menu_close);
				mCloseBtn = (LinearLayout) layout
						.findViewById(R.id.menu_close_btn);

				// 下面对每一个Layout进行单击事件的注册吧。。。
				// 比如单击某个MenuItem的时候，他的背景色改变
				// 事先准备好一些背景图片或者颜色
				mCloseBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Toast.makeText(Main.this, "退出", Toast.LENGTH_LONG)
								.show();
						ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
						manager.restartPackage(getPackageName());
						Main.this.finish();
						// menuWindow.dismiss(); // 响应点击事件之后关闭Menu

					}
				});
				menu_display = true;
			} else {
				// 如果当前已经为显示状态，则隐藏起来
				menuWindow.dismiss();
				menu_display = false;
			}

			return false;
		}
		return false;
	}

	private static String[] nicks = { "阿雅", "北风", "张山", "李四", "欧阳锋", "郭靖",
			"黄蓉", "杨过", "凤姐", "芙蓉姐姐", "移联网", "樱木花道", "风清扬", "张三丰", "梅超风" };

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */

	// 设置标题栏右侧按钮的作用
	// public void btnmainright(View v) {
	// Intent intent = new Intent(MainWeixin.this, MainTopRightDialog.class);
	// startActivity(intent);
	// Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG)
	// .show();
	// }

	// public void startchat(View v) { // 小黑 对话界面
	// TextView name = (TextView) v.findViewById(R.id.message_name);
	// String user_name = (String) name.getText();
	// Log.i("输出信息", user_name);
	// Intent intent = new Intent();
	// intent.putExtra("name", user_name);
	// intent.putExtra("chat_type", "two");
	// intent.putExtra("dialog_type", "friend");
	// intent.setClass(Main.this, ChatActivity.class);
	// startActivity(intent);
	// Toast.makeText(getApplicationContext(), "来自" + user_name + "的消息",
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
		// 循环消息列表，找出所有用户
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
