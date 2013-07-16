package com.example.freebox;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainTopRightDialog extends Activity {
	//private MyDialog dialog;
	private LinearLayout layout;
	private TextView item1,item2,item3,item4,item5,item6 ;
	private ImageView img1,img2,img3,img4,img5,img6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_top_right_dialog);
		//dialog=new MyDialog(this);
		layout=(LinearLayout)findViewById(R.id.main_dialog_layout);
		item1=(TextView)findViewById(R.id.dialog_item1);
		item2=(TextView)findViewById(R.id.dialog_item2);
		item3=(TextView)findViewById(R.id.dialog_item3);
		item4=(TextView)findViewById(R.id.dialog_item4);
		item5=(TextView)findViewById(R.id.dialog_item5);
		item6=(TextView)findViewById(R.id.dialog_item6);
		img6=(ImageView)findViewById(R.id.imageView6);
		setDialogText();
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "��ʾ����������ⲿ�رմ��ڣ�", 
						Toast.LENGTH_SHORT).show();	
			}
		});
	}

	public void setDialogText(){
		Intent intent=getIntent();
		String type=intent.getStringExtra("dialog_type");
		if(type.equals("quanquan_dialog")){
			item1.setText("ȦȦ״̬");
			item2.setText("ȦȦ�");
			item3.setText("ȦȦ����");
			item4.setText("ȦȦ����");
			item5.setText("ȦȦ����");
			item6.setText("ȦȦ��Ա");
		}else if(type.equals("friend_dialog")){
			item6.setVisibility(View.GONE);
			img6.setVisibility(View.GONE);
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	public void sendNameCard(View v) {  
		Toast.makeText(getApplicationContext(), item1.getText(), 
				Toast.LENGTH_SHORT).show();   	
      }  
	public void remarkName(View v) {  
		Toast.makeText(getApplicationContext(), item2.getText(), 
				Toast.LENGTH_SHORT).show();
      }  
	public void addToBlackList(View v){
		Toast.makeText(getApplicationContext(), item3.getText(), 
				Toast.LENGTH_SHORT).show();
	}
	public void deleteFriend(View v){
		Toast.makeText(getApplicationContext(), item4.getText(), 
				Toast.LENGTH_SHORT).show();
	}
	public void sendPresent(View v){
		Toast.makeText(getApplicationContext(), item5.getText(), 
				Toast.LENGTH_SHORT).show();
	}
	public void findMember(View v){
		Toast.makeText(getApplicationContext(), item5.getText(), 
				Toast.LENGTH_SHORT).show();
	}

}
