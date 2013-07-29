package com.bairuitech.anychat;


import java.util.Date;

import android.view.OrientationEventListener;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


// AnyChat Camera��װ�࣬ʵ�ֱ�����Ƶ�ɼ�
public class AnyChatSensorHelper implements SensorEventListener{

	private AnyChatOrientationEventListener orientationListener = null;
	
	public void InitSensor(Context context) {
		if(orientationListener == null) {
			orientationListener = new AnyChatOrientationEventListener(context, SensorManager.SENSOR_DELAY_NORMAL);
			orientationListener.enable();
		}

		// ��ȡ�������������
		SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		// ��ȡ���ٶȴ�����
		Sensor mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// ��ȡ���ٶȴ���������Ϣ��SensorManager. SENSOR_DELAY_NORMAL��ʾ��ȡ���ڣ�ȡ��������
		sm.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void DestroySensor() {
		orientationListener.disable();
	}

	private float LastXSpead = 0;
	private float LastYSpead = 0;
	private float LastZSpead = 0;
	
	private boolean bCameraNeedFocus = false; // ������Ƿ���Ҫ�Խ�
	private Date LastSportTime = new Date(); // �ϴ��˶�ʱ��
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (Sensor.TYPE_ACCELEROMETER != event.sensor.getType()) {
            return;
        }	
		float X = event.values[0]; // ˮƽx������ٶ� �����徲ֹʱ�ڣ�0--1֮�䣩
		float Y = event.values[1]; // ˮƽY������ٶ� �����徲ֹʱ�ڣ�0--1֮�䣩
		float Z = event.values[1]; // ��ֱZ������ٶ� �����徲ֹʱ�ڣ�9.5--10֮�䣩

		if ((Math.abs(X - LastXSpead) <= 0.5) && (Math.abs(Y - LastYSpead) <= 0.5) && (Math.abs(Z - LastZSpead) <= 0.5)) // ��ֹ״̬
		{
			Date now = new Date();
			long interval = now.getTime() - LastSportTime.getTime();
			if (bCameraNeedFocus && interval > 1000) {
				bCameraNeedFocus = false;
				// ����ǲ���Java��Ƶ�ɼ�������Java���������ͷ�Զ��Խ�����
				if(AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA)
					AnyChatCoreSDK.mCameraHelper.CameraAutoFocus();
				else
					AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FOCUSCTRL, 1);
			}
		} else {
			bCameraNeedFocus = true;
			LastSportTime.setTime(System.currentTimeMillis());
		}
		LastXSpead = X;
		LastYSpead = Y;
		LastZSpead = Z;
	}
}


class AnyChatOrientationEventListener extends OrientationEventListener{
	public AnyChatOrientationEventListener(Context context, int rate) { 
		super(context, rate);  
	}
	   
	@Override public void onOrientationChanged(int degree) {
		int ANYCHAT_DEVICEORIENTATION_UNKNOW 			= 0;
//		int ANYCHAT_DEVICEORIENTATION_FACEUP			= 1;		// Device oriented flat, face up
//		int ANYCHAT_DEVICEORIENTATION_FACEDOWN			= 2;		// Device oriented flat, face down
		int ANYCHAT_DEVICEORIENTATION_LANDSCAPELEFT		= 3;		// Device oriented horizontally, home button on the right
		int ANYCHAT_DEVICEORIENTATION_LANDSCAPERIGHT	= 4;		// Device oriented horizontally, home button on the left
		int ANYCHAT_DEVICEORIENTATION_PORTRAIT			= 5;		// Device oriented vertically, home button on the bottom
		int ANYCHAT_DEVICEORIENTATION_PORTRAITUPSIDE	= 6;		// Device oriented vertically, home button on the top
		
		//ȷ���ɽǶ�����Ļ����Ķ�Ӧ��Χ  
		int orientation = ANYCHAT_DEVICEORIENTATION_UNKNOW;
        if(degree > 325 || degree <= 45){  
        	orientation = ANYCHAT_DEVICEORIENTATION_PORTRAIT;  
        }else if(degree > 45 && degree <= 135){  
        	orientation = ANYCHAT_DEVICEORIENTATION_LANDSCAPERIGHT;
        }else if(degree > 135 && degree < 225){  
        	orientation = ANYCHAT_DEVICEORIENTATION_PORTRAITUPSIDE; 
        }else {  
        	orientation = ANYCHAT_DEVICEORIENTATION_LANDSCAPELEFT;
        } 
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_ORIENTATION, orientation);
	}
}
