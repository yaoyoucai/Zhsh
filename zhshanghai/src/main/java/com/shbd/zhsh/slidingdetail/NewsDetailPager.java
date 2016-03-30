/**
 * 
 */
package com.shbd.zhsh.slidingdetail;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shbd.zhsh.R;
import com.shbd.zhsh.activity.MainActivity;
import com.shbd.zhsh.bean.NewsCenterData.NewsTabData;
import com.shbd.zhsh.pager.TabDetailPager;
import com.viewpagerindicator.TabPageIndicator;


/**
 * @author yh 侧边栏新闻详情页
 */
public class NewsDetailPager extends BaseDetailPager {
	private ViewPager mVpContent;
	private TabPageIndicator mIndicator;
	private ImageView mIvArr;
	private ArrayList<NewsTabData> mData;
	private List<TabDetailPager> mTabDetailPagers;
	

	public NewsDetailPager(Activity mActivity, ArrayList<NewsTabData> children) {
		super(mActivity);
		this.mData = children;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.pager_menu_detail_news,
				null);
		mVpContent = (ViewPager) view.findViewById(R.id.vp_menu_detail_news);
		mIndicator=(TabPageIndicator) view.findViewById(R.id.indicator_menu_detail_news);
		mIvArr=(ImageView) view.findViewById(R.id.iv_menu_detail_news);
		mIvArr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int currentItem = mVpContent.getCurrentItem();		
				currentItem++;
				mVpContent.setCurrentItem(currentItem);
			}
		});
		return view;
	}

	@Override
	public void initData() {
		mTabDetailPagers = new ArrayList<TabDetailPager>();
		for (int i = 0; i < mData.size(); i++) {
			mTabDetailPagers.add(new TabDetailPager(mActivity,mData.get(i)));
		}
		mVpContent.setAdapter(new ViewPagerAdapter());
        
//		mVpContent.setOnPageChangeListener(new OnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int position) {
//				if (position == 0) {
//					setSlidingMenuMode(true);
//				} else {
//					setSlidingMenuMode(false);
//				}
//			}
//
//			@Override
//			public void onPageScrolled(int position, float positionOffset,
//					int positionOffsetPixels) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int state) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//		
		//将指示器与viewPager进行绑定
		mIndicator.setViewPager(mVpContent);
		
		//将页面改变的监听交给指示器
		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				if (position == 0) {
					setSlidingMenuMode(true);
				} else {
					setSlidingMenuMode(false);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});
	}

	class ViewPagerAdapter extends PagerAdapter {
		//给指示器设置标签内容
		@Override
		public CharSequence getPageTitle(int position) {
			return mData.get(position).title;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTabDetailPagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mTabDetailPagers.get(position).mRootView);
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			TabDetailPager pager = mTabDetailPagers.get(position);
			container.addView(pager.mRootView);
			return pager.mRootView;
		}
	}

	/**
	 * 设置侧边栏显示状态
	 * 
	 * @param state
	 */
	private void setSlidingMenuMode(boolean state) {
		MainActivity mainActivity = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
		if (state) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
	
	
}
