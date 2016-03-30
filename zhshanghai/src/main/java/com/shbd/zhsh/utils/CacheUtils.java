/**
 * 
 */
package com.shbd.zhsh.utils;

import android.content.Context;

/**
 * @author yh 读写缓存
 */
public class CacheUtils {
	/**
	 * 将缓存数据保存起来
	 * 
	 * @param key
	 * @param value
	 * @param context
	 */
	public static void putCache(String key, String value, Context context) {
		PreferUtils.putString(key, value, context);
	}
    
	/**
	 * 取出缓存数据
	 * @param key
	 * @param context
	 * @return
	 */
	public static String getCache(String key, Context context) {
		return PreferUtils.getString(key, null, context);
	}

}
