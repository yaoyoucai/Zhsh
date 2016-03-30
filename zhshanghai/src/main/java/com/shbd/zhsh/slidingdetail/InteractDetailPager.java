/**
 * 
 */
package com.shbd.zhsh.slidingdetail;

import com.shbd.zhsh.activity.MainActivity;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * @author yh
 *侧边栏互动详情页
 */
public class InteractDetailPager extends BaseDetailPager {

	public InteractDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		TextView textView=new TextView(mActivity);
		textView.setText("侧边栏互动详情页");
		return textView;
	}

}
