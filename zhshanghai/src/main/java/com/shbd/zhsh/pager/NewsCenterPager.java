package com.shbd.zhsh.pager;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shbd.zhsh.activity.MainActivity;
import com.shbd.zhsh.bean.NewsCenterData;
import com.shbd.zhsh.fragment.SlidingMenuFragment;
import com.shbd.zhsh.global.Constants;
import com.shbd.zhsh.slidingdetail.BaseDetailPager;
import com.shbd.zhsh.slidingdetail.GroupIconDetailPager;
import com.shbd.zhsh.slidingdetail.InteractDetailPager;
import com.shbd.zhsh.slidingdetail.NewsDetailPager;
import com.shbd.zhsh.slidingdetail.SubjectDetailPager;
import com.shbd.zhsh.utils.CacheUtils;
import com.shbd.zhsh.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yh 新闻
 */
public class NewsCenterPager extends BasePager {
	private NewsCenterData mNewsCenterData;
	private List<BaseDetailPager> detailPagers;

	public NewsCenterPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initdata() {
		mTvTitle.setText("新闻");

		String cache = CacheUtils.getCache(Constants.URL.NEWS_CENTER_URL,
				mActivity);
		// 若有缓存,则先显示缓存
		if (!TextUtils.isEmpty(cache)) {
			System.out.println("读取缓存");
			processResult(cache);
		}

		getDataFromServer();

	}

	/*
	 * 从服务器获取数据
	 */
	private void getDataFromServer() {
		System.out.println("正在加载数据");

		mTvProgress.setText("正在加载数据");
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET,
				Constants.URL.NEWS_CENTER_URL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						processResult(responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {

						mTvProgress.setText("加载失败");
						ToastUtils.showToast(mActivity, msg);
						error.printStackTrace();
					}
				});
	}

	/**
	 * 处理网络数据
	 * 
	 * @param result
	 */
	private void processResult(String result) {
		mTvProgress.setVisibility(TextView.GONE);
		Gson gson = new Gson();
		mNewsCenterData = gson.fromJson(result, NewsCenterData.class);
		setSlidingMenuData();

		// 填充侧边栏菜单详情页面
		detailPagers = new ArrayList<BaseDetailPager>();
		detailPagers.add(new NewsDetailPager(mActivity, mNewsCenterData.data
				.get(0).children));
		detailPagers.add(new SubjectDetailPager(mActivity));
		detailPagers.add(new GroupIconDetailPager(mActivity));
		detailPagers.add(new InteractDetailPager(mActivity));

		// 首次进入时，显示侧边栏新闻详情页面
		changeCurrentDetailPager(0);
		
		
		// 将json数据存入缓存
		CacheUtils.putCache(Constants.URL.NEWS_CENTER_URL, result, mActivity);

	}

	/**
	 * 填充侧边栏数据
	 */
	private void setSlidingMenuData() {
		MainActivity mainActivity = (MainActivity) mActivity;
		SlidingMenuFragment fragment = (SlidingMenuFragment) mainActivity
				.getFragmentByTag("sliding_menu_fragment");
		fragment.setData(mNewsCenterData.data);
	}

	public void changeCurrentDetailPager(int position) {
		BaseDetailPager baseDetailPager = detailPagers.get(position);
		// 初始化数据
		baseDetailPager.initData();
		mFlContent.removeAllViews();
		mFlContent.addView(baseDetailPager.mRootView);
	}
}
