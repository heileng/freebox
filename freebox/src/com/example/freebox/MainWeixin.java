package com.example.freebox;

import java.util.ArrayList;
import java.util.List;

import com.example.freebox.adapter.AddressListAdapter;
import com.example.freebox.adapter.ContactAdapter;
import com.example.freebox.adapter.MessageAdapter;
import com.example.freebox.adapter.QuanQuanAdapter;
import com.example.freebox.adapter.UniteAdapter;
import com.example.freebox.entity.MessageEntity;
import com.example.freebox.entity.QuanQuanEntity;
import com.example.freebox.ui.SideBar;
import com.example.freebox.utils.DataGetHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainWeixin extends Activity {

	public static MainWeixin instance = null;

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
	private ListView mListView;
	private MessageAdapter mAdapter;
	private ArrayList<MessageEntity> mMessageEntityArrays = new ArrayList<MessageEntity>();
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;

	// 通讯录列表
	private ListView lvAddressGroup, lvContact, lvMyQuanQuan;
	private SideBar indexBar;
	private WindowManager mWindowManager;
	private TextView mDialogText;
	private Button address_back_btn;

	// ideas搜索界面
	private GridView flaggrid;

	// 圈圈内容
	private GridView gridListView;
	private ArrayList<QuanQuanEntity> mQuanQuanEntityArrays = new ArrayList<QuanQuanEntity>();
	private QuanQuanAdapter gridviewadapter;
	private List<String> lstData;

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
		mListView = (ListView) view1.findViewById(R.id.message_listview);
		mListView.setAdapter(mAdapter);
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
		lvAddressGroup = (ListView) view2.findViewById(R.id.lv_addres_group);
		lvAddressGroup.setAdapter(new AddressListAdapter(this));
		lvMyQuanQuan = (ListView) view2.findViewById(R.id.lv_my_quanquan);
		mDataGetHelper = new DataGetHelper();
		mQuanQuanEntityArrays = mDataGetHelper.initQuanQuanData();
		UniteAdapter adapter = new UniteAdapter(MainWeixin.this,
				mQuanQuanEntityArrays, true);
		lvMyQuanQuan.setAdapter(adapter);
		lvMyQuanQuan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long arg3) {

				Log.i("输出信息", "点击了item");
				Intent intent = new Intent();
				intent.putExtra("chat_type", "multi");
				TextView t = (TextView) findViewById(R.id.app_title);
				intent.putExtra("name", t.getText());
				intent.putExtra("dialog_type", "quanquan");
				intent.setClass(MainWeixin.this, ChatActivity.class);
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
					lvContact.setVisibility(View.VISIBLE);
					indexBar.setVisibility(View.VISIBLE);
					address_back_btn.setVisibility(View.VISIBLE);
					lvAddressGroup.setVisibility(View.INVISIBLE);
					break;
				case 1:
					Log.i("输出信息", "我的圈圈");
					lvMyQuanQuan.setVisibility(View.VISIBLE);
					address_back_btn.setVisibility(View.VISIBLE);
					lvAddressGroup.setVisibility(View.INVISIBLE);
					break;
				case 2:
					Intent intent = new Intent();
					intent.putExtra("quanquan_type", "校园圈圈");
					intent.setClass(MainWeixin.this,
							QuanQuanProfileActivity.class);
					startActivity(intent);
					break;
				case 3:
					Intent intent2 = new Intent();
					intent2.putExtra("quanquan_type", "学院圈圈");
					intent2.setClass(MainWeixin.this,
							QuanQuanProfileActivity.class);
					startActivity(intent2);
					break;
				case 4:
					Intent intent3 = new Intent();
					intent3.putExtra("quanquan_type", "班级圈圈");
					intent3.setClass(MainWeixin.this,
							QuanQuanProfileActivity.class);
					startActivity(intent3);
					break;

				}

			}

		});
		lvContact = (ListView) view2.findViewById(R.id.lvContact);
		lvContact.setAdapter(new ContactAdapter(this));
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
				intent.putExtra("name", user_name_text);
				intent.setClass(MainWeixin.this, ProfileActivity.class);
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
		View view3 = mLi.inflate(R.layout.main_tab_friends, null);
		flaggrid = (GridView) view3.findViewById(R.id.flags_grid);
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
		lstData.add("电商圈");
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
				intent.setClass(MainWeixin.this, AppsActivity.class);
				startActivity(intent);
				Toast toast = Toast.makeText(MainWeixin.this, "进入"
						+ app_name_text, Toast.LENGTH_SHORT);
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
						// Toast.makeText(Main.this, "退出",
						// Toast.LENGTH_LONG).show();
						Intent intent = new Intent();
						// intent.setClass(MainWeixin.this,Exit.class);
						// startActivity(intent);
						menuWindow.dismiss(); // 响应点击事件之后关闭Menu
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

	public void startchat(View v) { // 小黑 对话界面
		TextView name = (TextView) v.findViewById(R.id.message_name);
		String user_name = (String) name.getText();
		Log.i("输出信息", user_name);
		Intent intent = new Intent();
		intent.putExtra("name", user_name);
		intent.putExtra("chat_type", "two");
		intent.putExtra("dialog_type", "friend");
		intent.setClass(MainWeixin.this, ChatActivity.class);
		startActivity(intent);
		Toast.makeText(getApplicationContext(), "来自" + user_name + "的消息",
				Toast.LENGTH_LONG).show();
	}
	// public void exit_settings(View v) { //退出 伪“对话框”，其实是一个activity
	// Intent intent = new Intent (MainWeixin.this,ExitFromSettings.class);
	// startActivity(intent);
	// }
	// public void btn_shake(View v) { //手机摇一摇
	// Intent intent = new Intent (MainWeixin.this,ShakeActivity.class);
	// startActivity(intent);
	// }
}
