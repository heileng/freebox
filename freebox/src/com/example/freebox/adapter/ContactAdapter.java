package com.example.freebox.adapter;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.example.freebox.R;
import com.example.freebox.entity.QuanQuanEntity;
import com.example.freebox.entity.UserEntity;
import com.example.freebox.utils.PinyinComparator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter implements SectionIndexer {
	private Context mContext;
	private String[] mNicks;
	private String[] mPhones;
	private List<UserEntity> userlist;
	private static String[] nicks = { "阿雅", "北风", "张山", "李四", "欧阳锋", "郭靖",
			"黄蓉", "杨过", "凤姐", "芙蓉姐姐", "移联网", "樱木花道", "风清扬", "张三丰", "梅超风" };

	@SuppressWarnings("unchecked")
	public ContactAdapter(Context mContext) {
		this.mContext = mContext;
		this.mNicks = nicks;
		// 排序(实现了中英文混排)
		Arrays.sort(mNicks, new PinyinComparator());
	}

	public ContactAdapter(Context context, List<UserEntity> e) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.userlist = e;
		this.mNicks = new String[this.userlist.size()];
		this.mPhones = new String[this.userlist.size()];
		for (int i = 0; i < this.userlist.size(); i++) {
			this.mNicks[i] = this.userlist.get(i).getName();
			this.mPhones[i] = this.userlist.get(i).getUserName();
		}

	}

	@Override
	public int getCount() {
		return mNicks.length;
	}

	@Override
	public Object getItem(int position) {
		return mNicks[position];
		// return userlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToFirstSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0].charAt(0);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final String nickName = mNicks[position];
		final String phone = mPhones[position];
		// final String nickName = userlist.get(position).getName();
		// final String userName = userlist.get(position).getUserName();
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.contact_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvCatalog = (TextView) convertView
					.findViewById(R.id.contactitem_catalog);
			viewHolder.ivAvatar = (ImageView) convertView
					.findViewById(R.id.contactitem_avatar_iv);
			viewHolder.tvNick = (TextView) convertView
					.findViewById(R.id.contactitem_nick);
			viewHolder.tvSig = (TextView) convertView
					.findViewById(R.id.personalize_sig);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String catalog = converterToFirstSpell(nickName).substring(0, 1);
		if (position == 0) {
			viewHolder.tvCatalog.setVisibility(View.VISIBLE);
			viewHolder.tvCatalog.setText(catalog);
		} else {
			String lastCatalog = converterToFirstSpell(mNicks[position - 1])
					.substring(0, 1);
			// String lastCatalog = converterToFirstSpell(
			// userlist.get(position - 1).getName()).substring(0, 1);
			if (catalog.equals(lastCatalog)) {
				viewHolder.tvCatalog.setVisibility(View.GONE);
			} else {
				viewHolder.tvCatalog.setVisibility(View.VISIBLE);
				viewHolder.tvCatalog.setText(catalog);
			}
		}
		viewHolder.ivAvatar.setImageResource(R.drawable.default_avatar);
		viewHolder.tvNick.setText(nickName);
		viewHolder.tvSig.setText(phone);
		return convertView;
	}

	static class ViewHolder {
		TextView tvCatalog;// 目录
		ImageView ivAvatar;// 头像
		TextView tvNick;// 昵称
		TextView tvSig;// 个性签名
	}

	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < mNicks.length; i++) {
			String l = converterToFirstSpell(mNicks[i]).substring(0, 1);
			char firstChar = l.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	// @Override
	// public int getPositionForSection(int section) {
	// for (int i = 0; i < userlist.size(); i++) {
	// String l = converterToFirstSpell(userlist.get(i).getName())
	// .substring(0, 1);
	// char firstChar = l.toUpperCase().charAt(0);
	// if (firstChar == section) {
	// return i;
	// }
	// }
	// return -1;
	// }

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}
