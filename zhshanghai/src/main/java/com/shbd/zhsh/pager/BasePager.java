package com.shbd.zhsh.pager;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shbd.zhsh.R;
import com.shbd.zhsh.activity.MainActivity;

/**
 * 主页面的基类
 * 
 * @author yh
 * 
 */
public abstract class BasePager {
	public Activity mActivity;
	public FrameLayout mFlContent;
	public ImageButton mBtnImgMenu;
	public TextView mTvTitle;
	public TextView mTvProgress;
	public View mView;

	public BasePager(Activity activity) {
		this.mActivity = activity;
		mView = initView();
	}

	/**
	 * 初始化数据
	 */
	public abstract void initdata();

	/**
	 * 初始化view
	 */
	public View initView() {
		View view = View.inflate(mActivity, R.layout.base_pager_content, null);
		mFlContent = (FrameLayout) view
				.findViewById(R.id.fl_base_pager_content);
		mBtnImgMenu = (ImageButton) view
				.findViewById(R.id.btn_title_bar_img_menu);
		mTvTitle = (TextView) view.findViewById(R.id.tv_title_bar_title);
		mTvProgress = (TextView) view.findViewById(R.id.tv_base_pager_content);

		mBtnImgMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		return view;
	}

	/**
	 * 控制侧边栏的开关
	 */
	private void toggle() {
		MainActivity activity = (MainActivity) mActivity;
		SlidingMenu slidingMenu = activity.getSlidingMenu();
		// 控制侧边栏的打开与关闭 ，若侧边栏为打开状态，调用此方法则关闭侧边栏，反之，则打开
		slidingMenu.toggle();
	}
}
