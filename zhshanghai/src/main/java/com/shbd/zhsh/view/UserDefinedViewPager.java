/**
 * 
 */
package com.shbd.zhsh.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author yh 自定义viewPager
 */
public class UserDefinedViewPager extends ViewPager {

	public UserDefinedViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	/**
	 * 触发拦截触摸事件
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}



	/**
	 * 重写该方法，并在该方法中不做任何操作，以达到禁止viewPager滑动的效果
	 */
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return true;
	}

}
