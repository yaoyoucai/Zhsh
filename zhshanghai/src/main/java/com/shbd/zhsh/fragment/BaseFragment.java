/**
 * 
 */
package com.shbd.zhsh.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author yh
 * fragment基类
 */
public abstract class BaseFragment extends Fragment{
	public Activity mActivity;
	 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity=getActivity();//获取所在activity
	}
    
	/**
	 * 初始化fragment布局
	 */
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container, Bundle savedInstanceState) {
		View initView = initView();
		return initView;
	}
    
	  
		/**
		 * activity创建结束
		 */
	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	public abstract View initView();

	public void initData() {

	};
	
	
	
}
