package com.shbd.zhsh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shbd.zhsh.R;
import com.shbd.zhsh.adapter.ViewPagerAdapter;
import com.shbd.zhsh.utils.PreferUtils;

/**
 * 新手引导页面
 * 
 * @author yh
 * 
 */
public class GuideActivity extends Activity implements OnClickListener{
	private ViewPager mVpGuide;
	private LinearLayout mLlGuide;
	private ImageView mIvRedPoint;
	private Button mBtStart;
	private float mPointInteval;

	private int[] mImageIds = new int[] { R.mipmap.guide_1,
			R.mipmap.guide_2, R.mipmap.guide_3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initView();
		initData();

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		mVpGuide.setAdapter(new ViewPagerAdapter(getApplicationContext(),
				mImageIds));
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView imageView = new ImageView(getApplicationContext());
			imageView.setBackgroundResource(R.drawable.shape_circle_default);

			// 设置小圆点之间的间距
			final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			if (i > 0) {
				params.leftMargin = 10;
			}
			imageView.setLayoutParams(params);
			mLlGuide.addView(imageView);
		}

		mIvRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// 获取小圆点之间的间距(必须在页面绘制完成之后获取)
						mPointInteval = mLlGuide.getChildAt(1).getLeft()
								- mLlGuide.getChildAt(0).getLeft();
						// 获取完成后，移除监听
						mIvRedPoint.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});

		mVpGuide.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				if (position == 2) {
					mBtStart.setVisibility(Button.VISIBLE);
				}
				else {
					mBtStart.setVisibility(Button.INVISIBLE);
				}
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				float moveDistance = mPointInteval * (position + arg1);
				final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				params.leftMargin = (int) moveDistance;
				mIvRedPoint.setLayoutParams(params);
			}

			@Override
			public void onPageScrollStateChanged(int position) {

			}
		});
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		mVpGuide = (ViewPager) findViewById(R.id.vp_guide);
		mLlGuide = (LinearLayout) findViewById(R.id.ll_guide);
		mIvRedPoint = (ImageView) findViewById(R.id.iv_guide_red_point);
		mBtStart=(Button) findViewById(R.id.bt_guide_start);
		mBtStart.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		  //新手引导页面完成后，下次进入不再显示
          PreferUtils.putBoolean("show_guide", false, getApplicationContext()); 	
          
          //进入主页面
          Intent intent=new Intent(getApplicationContext(), MainActivity.class);
          startActivity(intent);
          
          //销毁当前activity
          finish();
	}

 
}
