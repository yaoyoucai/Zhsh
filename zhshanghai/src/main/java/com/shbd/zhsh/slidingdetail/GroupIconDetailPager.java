/**
 * 
 */
package com.shbd.zhsh.slidingdetail;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * @author yh
 *侧边栏组图详情页
 */
public class GroupIconDetailPager extends BaseDetailPager {

	public GroupIconDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		TextView textView=new TextView(mActivity);
		textView.setText("侧边栏组图详情页");
		return textView;
	}

}
