/**
 * 
 */
package com.shbd.zhsh.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shbd.zhsh.R;
import com.shbd.zhsh.activity.MainActivity;
import com.shbd.zhsh.pager.BasePager;
import com.shbd.zhsh.pager.GovaffairsPager;
import com.shbd.zhsh.pager.HomePager;
import com.shbd.zhsh.pager.NewsCenterPager;
import com.shbd.zhsh.pager.SettingPager;
import com.shbd.zhsh.pager.SmartServicePager;
import com.shbd.zhsh.slidingdetail.BaseDetailPager;
import com.shbd.zhsh.slidingdetail.GroupIconDetailPager;
import com.shbd.zhsh.slidingdetail.InteractDetailPager;
import com.shbd.zhsh.slidingdetail.NewsDetailPager;
import com.shbd.zhsh.slidingdetail.SubjectDetailPager;

/**
 * @author yh 主体内容fragment
 */
public class ContentFragment extends BaseFragment {
	private List<BasePager> pagerList = new ArrayList<BasePager>();
	private ViewPager mVpContent;
	private RadioGroup mRgContent;

	@Override
	public View initView() {
		View contentView = View.inflate(mActivity, R.layout.fragment_content,
				null);
		mVpContent = (ViewPager) contentView.findViewById(R.id.vp_content);
		mRgContent = (RadioGroup) contentView.findViewById(R.id.rg_content);
		return contentView;
	}

	@Override
	public void initData() {
		pagerList.add(new HomePager(mActivity));
		pagerList.add(new NewsCenterPager(mActivity));
		pagerList.add(new SmartServicePager(mActivity));
		pagerList.add(new GovaffairsPager(mActivity));
		pagerList.add(new SettingPager(mActivity));
		
		mVpContent.setAdapter(new ContentViewPagerAdapter());

		// 为底层标签设置点击事件
		mRgContent.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_content_home:
					// true表示有平滑移动效果，false则相反
					mVpContent.setCurrentItem(0, true);
					pagerList.get(0).initdata();
					setSlidingMenuMode(false);
					break;
				case R.id.rb_content_news_center:
					mVpContent.setCurrentItem(1, true);
					pagerList.get(1).initdata();
					setSlidingMenuMode(true);
					break;
				case R.id.rb_content_smart_service:
					mVpContent.setCurrentItem(2, true);
					pagerList.get(2).initdata();
					setSlidingMenuMode(true);
					break;
				case R.id.rb_content_gov:
					mVpContent.setCurrentItem(3, true);
					pagerList.get(3).initdata();
					setSlidingMenuMode(true);
					break;
				case R.id.rb_content_setting:
					mVpContent.setCurrentItem(4, true);
					pagerList.get(4).initdata();
					setSlidingMenuMode(false);
					break;
				}
			}

		});

		setSlidingMenuMode(false);
		// 默认加载首页数据
		pagerList.get(0).initdata();

	}

	class ContentViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pagerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager basePager = pagerList.get(position);
			container.addView(basePager.mView);
			// 不建议在此处初始化数据，因为viewpager会默认加载当前页和下一页数据，消耗流量
			// basePager.initdata();
			return basePager.mView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			BasePager basePager = pagerList.get(position);
			container.removeView(basePager.mView);
		}
	}

	/**
	 * 设置侧边栏显示状态
	 * 
	 * @param state
	 */
	private void setSlidingMenuMode(boolean state) {
		MainActivity mainActivity = (MainActivity) getActivity();
		SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
		if (state) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
	
	public NewsCenterPager getNewsCenterPager() {
		return (NewsCenterPager) pagerList.get(1);
	}
	
}
