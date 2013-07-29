package com.example.freebox;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.freebox.MyProfileEditActivity.DataGetTask;
import com.example.freebox.adapter.ChatMsgViewAdapter;
import com.example.freebox.adapter.ContactAdapter;
import com.example.freebox.adapter.UniteAdapter;
import com.example.freebox.config.Flags;
import com.example.freebox.connection.APILinkEntity;
import com.example.freebox.connection.HttpClientEntity;
import com.example.freebox.data.JSONMessageEntity;
import com.example.freebox.entity.ChatMsgEntity;
import com.example.freebox.entity.UserEntity;
import com.example.freebox.push.DataHandler;
import com.example.freebox.ui.Expressions;
import com.example.freebox.utils.DataBaseHandler;
import com.example.freebox.utils.DataGetHelper;
import com.example.freebox.utils.DatabaseHelper;
import com.example.freebox.utils.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ChatActivity extends BaseActivity implements OnClickListener {
	private Context mCon;
	private ViewPager viewPager;
	private ArrayList<GridView> grids;
	private int[] expressionImages;
	private String[] expressionImageNames;
	private int[] expressionImages1;
	private String[] expressionImageNames1;
	private int[] expressionImages2;
	private String[] expressionImageNames2;
	private Button mBtnSend;
	private ImageButton voiceBtn;
	private ImageButton keyboardBtn;
	private ImageButton biaoqingBtn;
	private ImageButton biaoqingfocuseBtn;
	private LinearLayout ll_fasong;
	private LinearLayout ll_yuyin;
	private LinearLayout page_select;
	private ImageView page0;
	private ImageView page1;
	private ImageView page2;
	private EditText mEditTextContent;
	private ListView mListView;
	private GridView gView1;
	private GridView gView2;
	private GridView gView3;
	private String title_name;
	public static String chat_type;
	private ChatMsgViewAdapter mAdapter;
	private int mQuanId;
	private int mguid;
	private int userid;
	private String username;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	private String[] msgArray = new String[] { "在不在啊？", "有！你呢？" };

	private String[] dateArray = new String[] { "2012-12-09 18:00",
			"2012-12-09 18:10" };
	private final static int COUNT = 8;

	private Handler mHandler;

	// 网络获取
	private UrlEncodedFormEntity paramsEntity;
	private HttpClientEntity mClient = new HttpClientEntity();;
	private DataGetTask task;
	private DataBaseHandler mDataBaseHandler;

	
	public ChatActivity() {
		super();
		DataHandler.ChatActivity=this;
	}
	public int getmQuanId() {
		return mQuanId;
	}
	public void setmQuanId(int mQuanId) {
		this.mQuanId = mQuanId;
	}
	public int getMguid() {
		return mguid;
	}
	public void setMguid(int mguid) {
		this.mguid = mguid;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.chat_activity_layout);
		setContentLayout(R.layout.chat_activity_layout);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mCon = ChatActivity.this;
		Intent intent = getIntent();
		chat_type = intent.getStringExtra("chat_type");
		title_name = intent.getStringExtra("name");
		mguid = intent.getIntExtra("user_guid", 0);
		mQuanId = intent.getIntExtra("quan_guid", 0);

		SharedPreferences sharedPreferences = getSharedPreferences(
				"user_config", Context.MODE_PRIVATE);
		userid = sharedPreferences.getInt("user_guid", 0);
		username = sharedPreferences.getString("user_name", "none");
	
		Log.i("当前聊天界面", "朋友id："+mguid+"朋友名字："+title_name);
		Log.i("圈圈ID", "点击了圈圈id" + mQuanId);
		// mTitleText=(TextView)findViewById(R.id.chat_title_name);
		ll_fasong = (LinearLayout) findViewById(R.id.ll_fasong);
		ll_yuyin = (LinearLayout) findViewById(R.id.ll_yuyin);
		page_select = (LinearLayout) findViewById(R.id.page_select);
		page0 = (ImageView) findViewById(R.id.page0_select);
		page1 = (ImageView) findViewById(R.id.page1_select);
		page2 = (ImageView) findViewById(R.id.page2_select);
		mListView = (ListView) findViewById(R.id.listview);
		// 引入表情
		expressionImages = Expressions.expressionImgs;
		expressionImageNames = Expressions.expressionImgNames;
		expressionImages1 = Expressions.expressionImgs1;
		expressionImageNames1 = Expressions.expressionImgNames1;
		expressionImages2 = Expressions.expressionImgs2;
		expressionImageNames2 = Expressions.expressionImgNames2;

		mHandler = new Handler() {
			public void handleMessage(Message msg) {// 此方法在ui线程运行
				switch (msg.what) {
				case Flags.MSG_NEW_FRIEND_MESSAGE:
					if (chat_type.equals("two")) {
						updateNewMessageData();
						Toast.makeText(ChatActivity.this, "收到一条新的好友消息",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case Flags.MSG_NEW_QUAN_MESSAGE:
					if (chat_type.equals("multi")) {
						updateNewMessageData();
						Toast.makeText(ChatActivity.this, "收到一条新的圈圈消息",
								Toast.LENGTH_SHORT).show();
					}
					break;
				}
			}
		};
		// 创建ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		// 发送
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		// 返回
		// mBtnBack = (Button) findViewById(R.id.btn_back);
		// mBtnBack.setOnClickListener(this);
		// 个人信息
		if (chat_type.equals("multi")) {
			setbtn_rightRes(R.drawable.mm_title_btn_menu);
		} else if (chat_type.equals("two")) {
			setbtn_rightRes(R.drawable.mm_title_btn_contact_normal);
		}
		getbtn_right().setOnClickListener(this);
		// rightBtn = (ImageButton) findViewById(R.id.right_btn);
		// rightBtn.setOnClickListener(this);
		// 语音
		voiceBtn = (ImageButton) findViewById(R.id.chatting_voice_btn);
		voiceBtn.setOnClickListener(this);
		// 键盘
		keyboardBtn = (ImageButton) findViewById(R.id.chatting_keyboard_btn);
		keyboardBtn.setOnClickListener(this);
		// 表情
		biaoqingBtn = (ImageButton) findViewById(R.id.chatting_biaoqing_btn);
		biaoqingBtn.setOnClickListener(this);
		biaoqingfocuseBtn = (ImageButton) findViewById(R.id.chatting_biaoqing_focuse_btn);
		biaoqingfocuseBtn.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		initViewPager();
		setChatMessageData();
//		initData();
	}

	private void initViewPager() {
		LayoutInflater inflater = LayoutInflater.from(this);
		grids = new ArrayList<GridView>();
		gView1 = (GridView) inflater.inflate(R.layout.grid1, null);
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		// 生成24个表情
		for (int i = 0; i < 24; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("image", expressionImages[i]);
			listItems.add(listItem);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(mCon, listItems,
				R.layout.singleexpression, new String[] { "image" },
				new int[] { R.id.image });
		gView1.setAdapter(simpleAdapter);
		gView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeResource(getResources(),
						expressionImages[arg2 % expressionImages.length]);
				ImageSpan imageSpan = new ImageSpan(mCon, bitmap);
				SpannableString spannableString = new SpannableString(
						expressionImageNames[arg2].substring(1,
								expressionImageNames[arg2].length() - 1));
				spannableString.setSpan(imageSpan, 0,
						expressionImageNames[arg2].length() - 2,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				// 编辑框设置数据
				mEditTextContent.append(spannableString);
				System.out.println("edit的内容 = " + spannableString);
			}
		});
		grids.add(gView1);

		gView2 = (GridView) inflater.inflate(R.layout.grid2, null);
		grids.add(gView2);

		gView3 = (GridView) inflater.inflate(R.layout.grid3, null);
		grids.add(gView3);
		System.out.println("GridView的长度 = " + grids.size());

		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return grids.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(grids.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(grids.get(position));
				return grids.get(position);
			}
		};

		viewPager.setAdapter(mPagerAdapter);
		// viewPager.setAdapter();

		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
	}

	private void initData() {
		setTitle(title_name);
		// mTitleText.setText(title_name);
		for (int i = 0; i < COUNT; i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(dateArray[i]);
			if (i % 2 == 0) {
				entity.setName(title_name);
				entity.setMsgType(true);
			} else {
				entity.setName(username);
				entity.setMsgType(false);
			}
			entity.setText(msgArray[i]);
			mDataArrays.add(entity);
		}
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
	}

	// 收到消息更新界面
	private void setChatMessageData() {
		setTitle(title_name);
		mDataArrays.clear();
		mDataBaseHandler = new DataBaseHandler(ChatActivity.this);
		ArrayList<JSONMessageEntity> chatListEntity = new ArrayList<JSONMessageEntity>();
		DataBaseHandler databasehandler=new DataBaseHandler(ChatActivity.this);
		String friend_guid=""+mguid;
		Log.i("朋友guid", mguid+"");
		chatListEntity=databasehandler.getCurrentUserMessageList(friend_guid);
		for (int i = 0; i < chatListEntity.size(); i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(chatListEntity.get(i).getStringTime());
			entity.setName(chatListEntity.get(i).getSources());
			if(chatListEntity.get(i).getSources().equals(friend_guid)){
				entity.setMsgType(true);
			}else{
				entity.setMsgType(false);
			}			
			entity.setText(chatListEntity.get(i).getContent());
			mDataArrays.add(entity);
		}
		viewPager.setVisibility(ViewPager.GONE);
		page_select.setVisibility(page_select.GONE);
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		mListView.setSelection(mListView.getCount() - 1);
	}
	private void updateNewMessageData() {
//		mDataBaseHandler = new DataBaseHandler(ChatActivity.this);
//		ArrayList<JSONMessageEntity> chatListEntity = new ArrayList<JSONMessageEntity>();
//		chatListEntity = mDataBaseHandler.getCurrentUserMessageList(String.valueOf(mguid));
//		for (int i = 0; i < chatListEntity.size(); i++) {
//			ChatMsgEntity entity = new ChatMsgEntity();
//			entity.setDate(chatListEntity.get(i).getStringTime());
//			entity.setName(username);
//			entity.setMsgType(false);
//			entity.setText(chatListEntity.get(i).getContent());
//			mDataArrays.add(entity);
//		}
//		viewPager.setVisibility(ViewPager.GONE);
//		page_select.setVisibility(page_select.GONE);
//		mAdapter.notifyDataSetChanged();
//		mListView.setSelection(mListView.getCount() - 1);
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);
		return sbBuffer.toString();
	}

	@Override
	public void onClick(View v) {
		boolean isFoused = false;
		switch (v.getId()) {
		// 返回
		// case R.id.btn_back:
		// finish();
		// break;
		// 发送
		case R.id.btn_send:

			String content = mEditTextContent.getText().toString();
			System.out.println("edit.get的内容 = " + content);
			if (content.length() > 0) {
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setDate(getDate());
				entity.setName(username);
				entity.setMsgType(false);
				entity.setText(content);
				mDataArrays.add(entity);
				// 更新listview
				mEditTextContent.setText("");
				viewPager.setVisibility(ViewPager.GONE);
				page_select.setVisibility(page_select.GONE);
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(mListView.getCount() - 1);
				DataBaseHandler databasehandler =new DataBaseHandler(ChatActivity.this);
				JSONMessageEntity newmymessage=new JSONMessageEntity();
				newmymessage.setType(DataHandler.MESSAGE_TYPE_CHAT);
				newmymessage.setCode(DataHandler.MESSAGET_CODE_PERSONAL_TALK);
				newmymessage.setContent(content);
				newmymessage.setSources(""+userid);
				newmymessage.setTime(getDate());
				newmymessage.setDest(""+mguid);
				databasehandler.insertToMessageTable(newmymessage, "true");
				task = new DataGetTask();
				task.execute(APILinkEntity.mBasicAPI, content);
			} else {
				Toast.makeText(mCon, "不能发送空消息", Toast.LENGTH_LONG).show();
			}
			break;
		// 个人信息
		case R.id.btn_right:
			if (chat_type.equals("two")) {
				Intent intent = new Intent(ChatActivity.this,
						ProfileActivity.class);
				intent.putExtra("dialog_type", "friend_dialog");
				startActivity(intent);
			} else if (chat_type.equals("multi")) {
				// Intent intent = new Intent(ChatActivity.this,
				// MainTopRightDialog.class);
				// intent.putExtra("dialog_type", "quanquan_dialog");
				Intent intent = new Intent();
				intent.setClass(ChatActivity.this, QuanProfileActivity.class);
				intent.putExtra("quan_guid", mQuanId);
				Log.i("圈圈guid啊啊", "" + mQuanId);
				intent.putExtra("name", title_name);
				startActivity(intent);
			}
			break;
		// 语音
		case R.id.chatting_voice_btn:
			voiceBtn.setVisibility(voiceBtn.GONE);
			keyboardBtn.setVisibility(keyboardBtn.VISIBLE);
			ll_fasong.setVisibility(ll_fasong.GONE);
			ll_yuyin.setVisibility(ll_yuyin.VISIBLE);
			break;
		// 键盘
		case R.id.chatting_keyboard_btn:
			voiceBtn.setVisibility(voiceBtn.VISIBLE);
			keyboardBtn.setVisibility(keyboardBtn.GONE);
			ll_fasong.setVisibility(ll_fasong.VISIBLE);
			ll_yuyin.setVisibility(ll_yuyin.GONE);
			break;
		// 表情
		case R.id.chatting_biaoqing_btn:
			biaoqingBtn.setVisibility(biaoqingBtn.GONE);
			biaoqingfocuseBtn.setVisibility(biaoqingfocuseBtn.VISIBLE);
			viewPager.setVisibility(viewPager.VISIBLE);
			page_select.setVisibility(page_select.VISIBLE);

			break;
		case R.id.chatting_biaoqing_focuse_btn:
			biaoqingBtn.setVisibility(biaoqingBtn.VISIBLE);
			biaoqingfocuseBtn.setVisibility(biaoqingfocuseBtn.GONE);
			viewPager.setVisibility(viewPager.GONE);
			page_select.setVisibility(page_select.GONE);
			break;
		}

	}

	// 点击小黑图像
	public void openuserprofile(View v) { // 标题栏 返回按钮
		Intent intent = new Intent(ChatActivity.this, ProfileActivity.class);
		switch (v.getId()) {
		case R.id.iv_userhead:
			intent.putExtra("name", title_name);
			intent.putExtra("from", Flags.FromFriend);
			startActivity(intent);
			break;
		case R.id.iv_myuserhead:
			Log.i("输出", "点击了我自己的头像");
			SharedPreferences sharedPreferences = getSharedPreferences(
					"user_config", Context.MODE_PRIVATE);
			String username = sharedPreferences.getString("user_name", "none");
			intent.putExtra("name", username);
			intent.putExtra("from", Flags.FromMe);
			startActivity(intent);
			break;
		}

	}

	public class DataGetTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String result = null;
			Log.i(Utils.TAG, "开始task");
			try {
				SharedPreferences sharedPreferences = getSharedPreferences(
						"user_config", Context.MODE_PRIVATE);
				String token = sharedPreferences
						.getString("auth_token", "none");
				int userid = sharedPreferences.getInt("user_guid", 0);
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				
				params1.add(new BasicNameValuePair("api_key", Flags.APIKEY));
				params1.add(new BasicNameValuePair("auth_token", token));
				
				if(mguid>0){
					params1.add(new BasicNameValuePair("fguid", "" + userid));
					params1.add(new BasicNameValuePair("tguid", "" + mguid));
					params1.add(new BasicNameValuePair("method",
							APILinkEntity.mSendP2PMessageMethod));
				}
				else if(mQuanId>0){
						params1.add(new BasicNameValuePair("user_guid", "" + userid));
						params1.add(new BasicNameValuePair("group_guid", "" + mQuanId));
						params1.add(new BasicNameValuePair("method",
								APILinkEntity.mSendQuanMessageMethod));
				}
				else{
					Toast.makeText(ChatActivity.this, "发送目的地信息错误！", Toast.LENGTH_LONG).show();
					return null;					
				}
				Log.i(Utils.TAG, "我的id：" + userid);
				Log.i(Utils.TAG, "目的ID：" + ((mguid==0)?mQuanId:mguid));
				Log.i(Utils.TAG, "发送的信息"+arg0[1]);
				
				
//					byte[] tempstr=arg0[1].getBytes();
//					String temps=new String(tempstr,"UTF-8");
				params1.add(new BasicNameValuePair("message", arg0[1]));
				try {
					StringEntity se = new StringEntity("", "UTF-8");
					// HttpEntity httpentity=new StringEntity(params1, "UTF-8");
					paramsEntity = new UrlEncodedFormEntity(params1,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				result = mClient.PostData(arg0[0], paramsEntity);
				Log.i(Utils.TAG, "输出回执："+result);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {

			try {
				JSONObject mJSONObject;
				mJSONObject = new JSONObject(result);
				String resultdata = mJSONObject.getString("result");
				JSONObject mResultobject = new JSONObject(resultdata);
				String data = mResultobject.getString("s");
				if (data.equals("1")) {
					Toast.makeText(ChatActivity.this,
							mResultobject.getString("m"), Toast.LENGTH_LONG)
							.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ** 指引页面改监听器 */
	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			System.out.println("页面滚动" + arg0);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			System.out.println("换页了" + arg0);
		}

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				page0.setImageDrawable(getResources().getDrawable(
						R.drawable.page_focused));
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));

				break;
			case 1:
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page_focused));
				page0.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				page2.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
				// 生成24个表情
				for (int i = 0; i < 24; i++) {
					Map<String, Object> listItem = new HashMap<String, Object>();
					listItem.put("image", expressionImages1[i]);
					listItems.add(listItem);
				}

				SimpleAdapter simpleAdapter = new SimpleAdapter(mCon,
						listItems, R.layout.singleexpression,
						new String[] { "image" }, new int[] { R.id.image });
				gView2.setAdapter(simpleAdapter);
				gView2.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Bitmap bitmap = null;
						bitmap = BitmapFactory.decodeResource(getResources(),
								expressionImages1[arg2
										% expressionImages1.length]);
						ImageSpan imageSpan = new ImageSpan(mCon, bitmap);
						SpannableString spannableString = new SpannableString(
								expressionImageNames1[arg2]
										.substring(1,
												expressionImageNames1[arg2]
														.length() - 1));
						spannableString.setSpan(imageSpan, 0,
								expressionImageNames1[arg2].length() - 2,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						// 编辑框设置数据
						mEditTextContent.append(spannableString);
						System.out.println("edit的内容 = " + spannableString);
					}
				});
				break;
			case 2:
				page2.setImageDrawable(getResources().getDrawable(
						R.drawable.page_focused));
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				page0.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				List<Map<String, Object>> listItems1 = new ArrayList<Map<String, Object>>();
				// 生成24个表情
				for (int i = 0; i < 24; i++) {
					Map<String, Object> listItem = new HashMap<String, Object>();
					listItem.put("image", expressionImages2[i]);
					listItems1.add(listItem);
				}

				SimpleAdapter simpleAdapter1 = new SimpleAdapter(mCon,
						listItems1, R.layout.singleexpression,
						new String[] { "image" }, new int[] { R.id.image });
				gView3.setAdapter(simpleAdapter1);
				gView3.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Bitmap bitmap = null;
						bitmap = BitmapFactory.decodeResource(getResources(),
								expressionImages2[arg2
										% expressionImages2.length]);
						ImageSpan imageSpan = new ImageSpan(mCon, bitmap);
						SpannableString spannableString = new SpannableString(
								expressionImageNames2[arg2]
										.substring(1,
												expressionImageNames2[arg2]
														.length() - 1));
						spannableString.setSpan(imageSpan, 0,
								expressionImageNames2[arg2].length() - 2,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						// 编辑框设置数据
						mEditTextContent.append(spannableString);
						System.out.println("edit的内容 = " + spannableString);
					}
				});
				break;
			}
		}
	}

	public void setPushMessage(JSONMessageEntity messageentity) {
		int code = messageentity.getCode();
		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setDate(messageentity.getTimes());		
		entity.setText(messageentity.getContent());
		switch (code) {
		case Flags.MSG_P2P:
			entity.setName(messageentity.getSources());
			if(messageentity.getSources().equals(mguid)){
				entity.setMsgType(true);
			}else{
				entity.setMsgType(false);
			}	
//			Message message = new Message();
//			message.what = Flags.MSG_NEW_FRIEND_MESSAGE;
//			mHandler.sendMessage(message);
			break;
		case Flags.MSG_Group:
			entity.setName(messageentity.getDest());
			if(messageentity.getDest().equals(mQuanId)){
				entity.setMsgType(true);
			}else{
				entity.setMsgType(false);
			}	
//			Message message2 = new Message();
//			message2.what = Flags.MSG_NEW_QUAN_MESSAGE;
//			mHandler.sendMessage(message2);
			break;
		}
		
		mAdapter.addItem(entity);
		mAdapter.notifyDataSetChanged();
		mListView.setSelection(mListView.getCount() - 1);
	}

}