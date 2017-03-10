package com.aqiu.onekey.utils;

import android.content.Context;
import android.widget.Toast;

/**
 *单例显示Toast的方法 
 */
public class Tutils {

	
	private static Toast toast;

	public static void showToast(Context context, String msg){
		if(toast == null){
			toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		}
		toast.setText(msg);
		toast.show();
	}
}
