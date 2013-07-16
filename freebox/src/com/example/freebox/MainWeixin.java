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
	private ListView mListView;
	private MessageAdapter mAdapter;
	private ArrayList<MessageEntity> mMessageEntityArrays = new ArrayList<MessageEntity>();
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;

	// ͨѶ¼�б�
	private ListView lvAddressGroup, lvContact, lvMyQuanQuan;
	private SideBar indexBar;
	private WindowManager mWindowManager;
	private TextView mDialogText;
	private Button address_back_btn;

	// ideas��������
	private GridView flaggrid;

	// ȦȦ����
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
		mListView = (ListView) view1.findViewById(R.id.message_listview);
		mListView.setAdapter(mAdapter);
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

				Log.i("�����Ϣ", "�����item");
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
					Log.i("�����Ϣ", "�ҵ�ȦȦ");
					lvMyQuanQuan.setVisibility(View.VISIBLE);
					address_back_btn.setVisibility(View.VISIBLE);
					lvAddressGroup.setVisibility(View.INVISIBLE);
					break;
				case 2:
					Intent intent = new Intent();
					intent.putExtra("quanquan_type", "У԰ȦȦ");
					intent.setClass(MainWeixin.this,
							QuanQuanProfileActivity.class);
					startActivity(intent);
					break;
				case 3:
					Intent intent2 = new Intent();
					intent2.putExtra("quanquan_type", "ѧԺȦȦ");
					intent2.setClass(MainWeixin.this,
							QuanQuanProfileActivity.class);
					startActivity(intent2);
					break;
				case 4:
					Intent intent3 = new Intent();
					intent3.putExtra("quanquan_type", "�༶ȦȦ");
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
		// idea����
		View view3 = mLi.inflate(R.layout.main_tab_friends, null);
		flaggrid = (GridView) view3.findViewById(R.id.flags_grid);
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
		lstData.add("����Ȧ");
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
				intent.setClass(MainWeixin.this, AppsActivity.class);
				startActivity(intent);
				Toast toast = Toast.makeText(MainWeixin.this, "����"
						+ app_name_text, Toast.LENGTH_SHORT);
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
						// Toast.makeText(Main.this, "�˳�",
						// Toast.LENGTH_LONG).show();
						Intent intent = new Intent();
						// intent.setClass(MainWeixin.this,Exit.class);
						// startActivity(intent);
						menuWindow.dismiss(); // ��Ӧ����¼�֮��ر�Menu
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

	public void startchat(View v) { // С�� �Ի�����
		TextView name = (TextView) v.findViewById(R.id.message_name);
		String user_name = (String) name.getText();
		Log.i("�����Ϣ", user_name);
		Intent intent = new Intent();
		intent.putExtra("name", user_name);
		intent.putExtra("chat_type", "two");
		intent.putExtra("dialog_type", "friend");
		intent.setClass(MainWeixin.this, ChatActivity.class);
		startActivity(intent);
		Toast.makeText(getApplicationContext(), "����" + user_name + "����Ϣ",
				Toast.LENGTH_LONG).show();
	}
	// public void exit_settings(View v) { //�˳� α���Ի��򡱣���ʵ��һ��activity
	// Intent intent = new Intent (MainWeixin.this,ExitFromSettings.class);
	// startActivity(intent);
	// }
	// public void btn_shake(View v) { //�ֻ�ҡһҡ
	// Intent intent = new Intent (MainWeixin.this,ShakeActivity.class);
	// startActivity(intent);
	// }
}
