package com.example.freebox;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * �̳���Activity�����Ժ󷽱����
 * 
 * @author coder
 * 
 */
public class BaseActivity extends Activity {

	private View titleView;
	private TextView tv_title;
	private Button btn_left;
	private ImageButton btn_right;

	private LinearLayout ly_content;
	// ��������Ĳ���
	private View contentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.common_title);
		titleView = (View) findViewById(R.id.titleview);
		tv_title = (TextView) titleView.findViewById(R.id.tv_title);
		btn_left = (Button) titleView.findViewById(R.id.btn_left);
		btn_right = (ImageButton) titleView.findViewById(R.id.btn_right);

		ly_content = (LinearLayout) findViewById(R.id.ly_content);
		getbtn_left().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BaseActivity.this.finish();

			}
		});
	}

	/***
	 * ������������
	 * 
	 * @param resId
	 *            ��Դ�ļ�ID
	 */
	public void setContentLayout(int resId) {

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(resId, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		contentView.setLayoutParams(layoutParams);
		contentView.setBackgroundDrawable(null);
		if (null != ly_content) {
			ly_content.addView(contentView);
		}

	}
	/***
	 * �Ƴ���������
	 * 
	 * 
	 */
	public void removeContentLayout(){
		int count=ly_content.getChildCount();
		if(null != ly_content&&ly_content.getChildCount()>=2){
			for(int i=1;i<count-1;i++){
				ly_content.removeViewAt(i);
			}
		}
	}

	/***
	 * ������������
	 * 
	 * @param view
	 *            View����
	 */
	public void setContentLayout(View view) {
		if (null != ly_content) {
			ly_content.addView(view);
		}
	}

	/**
	 * �õ����ݵ�View
	 * 
	 * @return
	 */
	public View getLyContentView() {

		return contentView;
	}

	/**
	 * �õ���ߵİ�ť
	 * 
	 * @return
	 */
	public Button getbtn_left() {
		return btn_left;
	}

	/**
	 * �õ��ұߵİ�ť
	 * 
	 * @return
	 */
	public ImageButton getbtn_right() {
		return btn_right;
	}

	/**
	 * ���ñ���
	 * 
	 * @param title
	 */
	public void setTitle(String title) {

		if (null != tv_title) {
			tv_title.setText(title);
		}

	}

	/**
	 * ���ñ���
	 * 
	 * @param resId
	 */
	public void setTitle(int resId) {
		tv_title.setText(getString(resId));
	}

	/**
	 * ������߰�ť��ͼƬ��Դ
	 * 
	 * @param resId
	 */
	public void setbtn_leftRes(int resId) {
		if (null != btn_left) {
			btn_left.setBackgroundResource(resId);
		}

	}

	/**
	 * ������߰�ť��ͼƬ��Դ
	 * 
	 * @param bm
	 */
	public void setbtn_leftRes(Drawable drawable) {
		if (null != btn_left) {
			btn_left.setBackgroundDrawable(drawable);
		}

	}

	/**
	 * �����ұ߰�ť��ͼƬ��Դ
	 * 
	 * @param resId
	 */
	public void setbtn_rightRes(int resId) {
		if (null != btn_right) {
			btn_right.setImageResource(resId);
		}
	}

	/**
	 * �����ұ߰�ť��ͼƬ��Դ
	 * 
	 * @param drawable
	 */
	public void setbtn_rightRes(Drawable drawable) {
		if (null != btn_right) {
			btn_right.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * �����Ϸ��ı�����
	 */
	public void hideTitleView() {

		if (null != titleView) {
			titleView.setVisibility(View.GONE);
		}
	}

	/**
	 * ������ߵİ�ť
	 */
	public void hidebtn_left() {

		if (null != btn_left) {
			btn_left.setVisibility(View.GONE);
		}

	}

	/***
	 * �����ұߵİ�ť
	 */
	public void hidebtn_right() {
		if (null != btn_right) {
			btn_right.setVisibility(View.GONE);
		}

	}

	public BaseActivity() {

	}

}
