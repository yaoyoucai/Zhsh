package com.shbd.zhsh.adapter;

import com.shbd.zhsh.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 新手引导页面viewpager使用的adapter
 * 
 * @author yh
 * 
 */
public class ViewPagerAdapter extends PagerAdapter {
	private Context mContext;
	private int[] mImageIds ;

	public ViewPagerAdapter(Context context,int[] imageIds) {
		this.mContext = context;
		this.mImageIds = imageIds;
	}

	@Override
	public int getCount() {
		return mImageIds.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView=new ImageView(mContext);
		imageView.setBackgroundResource(mImageIds[position]);
		container.addView(imageView);
		return imageView;
	}

}
