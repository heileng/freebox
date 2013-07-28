package com.example.freebox;


import com.example.freebox.push.MyPushManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Welcome extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }
    public void welcome_login(View v) {  
    	MyPushManager.startWork(this);
      	Intent intent = new Intent();
		intent.setClass(Welcome.this,Login.class);
		startActivity(intent);
		//this.finish();
      }  
    public void welcome_register(View v) { 
    	MyPushManager.startWork(this);
      	Intent intent = new Intent();
		intent.setClass(Welcome.this,Register.class);
		startActivity(intent);
		//this.finish();
      }  
   
}
