package com.shbd.zhsh.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.shbd.zhsh.R;
import com.shbd.zhsh.fragment.ContentFragment;
import com.shbd.zhsh.fragment.SlidingMenuFragment;

/**
 * 主页面
 * 
 * @author yh
 */
public class MainActivity extends SlidingFragmentActivity {

	private static final String SLIDING_MENU_FRAGMENT = "sliding_menu_fragment";
	private static final String CONTENT_FRAGMENT = "content_fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏，必须放在setContentView之前
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.sliding_menu);
		SlidingMenu slidingMenu = getSlidingMenu();

		// 设置触摸模式为全屏触摸
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// 设置侧边栏宽度
		WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		int leaveWidth = (windowManager.getDefaultDisplay().getWidth()) / 4;
		slidingMenu.setBehindWidth(leaveWidth);

		initFragment();
	}

	/**
	 * 初始化Fragment布局
	 */
	private void initFragment() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction beginTransaction = fragmentManager
				.beginTransaction();
		beginTransaction.replace(R.id.fl_content, new ContentFragment(),
				CONTENT_FRAGMENT);
		beginTransaction.replace(R.id.fl_sliding_menu,
				new SlidingMenuFragment(), SLIDING_MENU_FRAGMENT);
		beginTransaction.commit();
	}

	/**
	 * 根据tag查询fragment
	 * @param Tag
	 * @return
	 */
	public Fragment getFragmentByTag(String Tag) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = fragmentManager.findFragmentByTag(Tag);
		return fragment;
	}

}
