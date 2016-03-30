/**
 * 
 */
package com.shbd.zhsh.pager;

import android.app.Activity;
import android.widget.ImageButton;

/**
 * @author yh 首页
 */
public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initdata() {
		// 隐藏侧边栏菜单按钮
		mBtnImgMenu.setVisibility(ImageButton.GONE);
	}

}
