package com.shbd.zhsh.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * utils工具类
 * 
 * @author yh
 * 
 */
public class ToastUtils {
	/**
	 * 显示吐司
	 * @param context
	 * @param msg
	 */
	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
