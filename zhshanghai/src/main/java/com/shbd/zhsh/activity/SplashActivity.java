package com.shbd.zhsh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.shbd.zhsh.R;
import com.shbd.zhsh.utils.PreferUtils;

/**
 * 闪屏页面
 * 
 * @author yh
 */
public class SplashActivity extends Activity {
	private RelativeLayout mRlRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initView();
		// 旋转
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setDuration(2000);
		rotateAnimation.setFillAfter(true);

		// 缩放
		ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setDuration(2000);
		scaleAnimation.setFillAfter(true);

		// 渐变
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(2000);
		scaleAnimation.setFillAfter(true);

		// 动画集合
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(rotateAnimation);
		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(alphaAnimation);

		mRlRoot.startAnimation(animationSet);

		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				boolean isShowGuide = PreferUtils.getBoolean("show_guide",
						true, getApplicationContext());

				Intent intent = new Intent();
				// 判断是否跳转引导页面
				if (isShowGuide) {
					intent.setClass(getApplicationContext(),GuideActivity.class);
				} else {
					// 跳转到主页面
					intent.setClass(getApplicationContext(), MainActivity.class);
				}
				startActivity(intent);
				finish();

			}
		});
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		mRlRoot = (RelativeLayout) findViewById(R.id.rl_splash);
	}

}
