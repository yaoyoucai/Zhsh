/**
 * 
 */
package com.shbd.zhsh.slidingdetail;

import android.app.Activity;
import android.view.View;

import com.shbd.zhsh.activity.MainActivity;

/**
 * @author yh
 *侧边栏菜单详情基类
 */
public abstract class BaseDetailPager {
	public Activity mActivity; 
	public View mRootView;
     public BaseDetailPager(Activity activity) {
    	 this.mActivity=activity;
    	 mRootView=initView();
	}
     
	public abstract View initView() ;
	
	public void initData(){
		
	};
}
