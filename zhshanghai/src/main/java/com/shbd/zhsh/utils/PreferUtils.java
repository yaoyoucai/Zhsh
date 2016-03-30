package com.shbd.zhsh.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 对sharedPreferences的封装
 * 
 * @author yh
 * 
 */
public class PreferUtils {
	/**
	 * 添加boolean类型的值
	 * 
	 * @param key
	 * @param value
	 * @param context
	 */
	public static void putBoolean(String key, boolean value, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"config", Context.MODE_PRIVATE);
		sharedPreferences.edit().putBoolean(key, value).commit();
	}

	/**
	 * 获取boolean类型的值
	 * 
	 * @param key
	 * @param defVa1ue
	 * @param context
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defVa1ue,
			Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"config", Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, defVa1ue);
	}

	/**
	 * 添加String类型的值
	 * 
	 * @param key
	 * @param value
	 * @param context
	 */
	public static void putString(String key, String value, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"config", Context.MODE_PRIVATE);
		sharedPreferences.edit().putString(key, value).commit();
	}

	/**
	 * 获取String类型的值
	 * 
	 * @param key
	 * @param defVa1ue
	 * @param context
	 * @return
	 */
	public static String getString(String key, String defVa1ue, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"config", Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defVa1ue);
	}

	/**
	 * 添加int类型的值
	 * 
	 * @param key
	 * @param value
	 * @param context
	 */
	public static void putInt(String key, int value, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"config", Context.MODE_PRIVATE);
		sharedPreferences.edit().putInt(key, value).commit();
	}

	/**
	 * 获取int类型的值
	 * 
	 * @param key
	 * @param defVa1ue
	 * @param context
	 * @return
	 */
	public static int getInt(String key, int defVa1ue, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"config", Context.MODE_PRIVATE);
		return sharedPreferences.getInt(key, defVa1ue);
	}
    
	/**
	 * 移除对应的值
	 * @param key
	 * @param context
	 */
	public static void removeString(String key, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"config", Context.MODE_PRIVATE);
		sharedPreferences.edit().remove(key).commit();
	}
}
