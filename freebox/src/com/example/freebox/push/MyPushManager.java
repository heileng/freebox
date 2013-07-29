package com.example.freebox.push;

import android.content.Context;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.example.freebox.utils.Utils;

public class MyPushManager {
	public static void startWork(Context context) {
		//if (!PushManager.isPushEnabled(context))
			PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY,
					Utils.getMetaValue(context, "api_key"));
	}

	public static void stopWork(Context context) {
//		if (PushManager.isPushEnabled(context))
//			PushManager.stopWork(context);
	}
}
