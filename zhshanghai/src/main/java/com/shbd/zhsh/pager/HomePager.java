/**
 * 
 */
package com.shbd.zhsh.pager;

import android.app.Activity;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author yh
 * 首页
 */
public class HomePager extends BasePager {

	public HomePager(Activity activity) {
		super(activity);
	}

	@Override
	public void initdata() {
		mTvTitle.setText("智慧上海");
		TextView textView=new TextView(mActivity);
		textView.setText("首页");
		mFlContent.addView(textView);
		
		//隐藏侧边栏菜单按钮
		mBtnImgMenu.setVisibility(ImageButton.GONE);
	}

}

