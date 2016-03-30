package com.shbd.zhsh.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
	float startX;
	float startY;
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("ACTION_DOWN");
			startX = ev.getX();
			startY = ev.getY();
			getParent().requestDisallowInterceptTouchEvent(true);

			break;

		case MotionEvent.ACTION_MOVE:
			float endX = ev.getX();
			float endY = ev.getY();
			float dx = endX - startX;
			float dy = endY - startY;
			if (Math.abs(dx) > Math.abs(dy)) {// 左右滑动

				// 向右滑动
				if (dx > 0) {
					if (this.getCurrentItem() == 0) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				} else {
					// 向左滑动
					if (getCurrentItem() == getAdapter().getCount() - 1) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}

				}

			} else {
				// 上下滑动
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

}
