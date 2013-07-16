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
 * 继承于Activity用于以后方便管理
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
	// 内容区域的布局
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
	 * 设置内容区域
	 * 
	 * @param resId
	 *            资源文件ID
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
	 * 移除内容区域
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
	 * 设置内容区域
	 * 
	 * @param view
	 *            View对象
	 */
	public void setContentLayout(View view) {
		if (null != ly_content) {
			ly_content.addView(view);
		}
	}

	/**
	 * 得到内容的View
	 * 
	 * @return
	 */
	public View getLyContentView() {

		return contentView;
	}

	/**
	 * 得到左边的按钮
	 * 
	 * @return
	 */
	public Button getbtn_left() {
		return btn_left;
	}

	/**
	 * 得到右边的按钮
	 * 
	 * @return
	 */
	public ImageButton getbtn_right() {
		return btn_right;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {

		if (null != tv_title) {
			tv_title.setText(title);
		}

	}

	/**
	 * 设置标题
	 * 
	 * @param resId
	 */
	public void setTitle(int resId) {
		tv_title.setText(getString(resId));
	}

	/**
	 * 设置左边按钮的图片资源
	 * 
	 * @param resId
	 */
	public void setbtn_leftRes(int resId) {
		if (null != btn_left) {
			btn_left.setBackgroundResource(resId);
		}

	}

	/**
	 * 设置左边按钮的图片资源
	 * 
	 * @param bm
	 */
	public void setbtn_leftRes(Drawable drawable) {
		if (null != btn_left) {
			btn_left.setBackgroundDrawable(drawable);
		}

	}

	/**
	 * 设置右边按钮的图片资源
	 * 
	 * @param resId
	 */
	public void setbtn_rightRes(int resId) {
		if (null != btn_right) {
			btn_right.setImageResource(resId);
		}
	}

	/**
	 * 设置右边按钮的图片资源
	 * 
	 * @param drawable
	 */
	public void setbtn_rightRes(Drawable drawable) {
		if (null != btn_right) {
			btn_right.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * 隐藏上方的标题栏
	 */
	public void hideTitleView() {

		if (null != titleView) {
			titleView.setVisibility(View.GONE);
		}
	}

	/**
	 * 隐藏左边的按钮
	 */
	public void hidebtn_left() {

		if (null != btn_left) {
			btn_left.setVisibility(View.GONE);
		}

	}

	/***
	 * 隐藏右边的按钮
	 */
	public void hidebtn_right() {
		if (null != btn_right) {
			btn_right.setVisibility(View.GONE);
		}

	}

	public BaseActivity() {

	}

}
