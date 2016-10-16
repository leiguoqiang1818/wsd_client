package com.example.wsd_client.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wsd_client.R;

public class SplashActivity extends Activity {

	private ImageView ivSplash;
	private TextView tvSplash;
	private Button btn_ignore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initView();
		initListeners();
		anima();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		ivSplash=(ImageView) findViewById(R.id.iv_splash);
		tvSplash=(TextView) findViewById(R.id.tv_splash);
		btn_ignore = (Button) findViewById(R.id.btn_ignore);
	}

	private void initListeners() {
		btn_ignore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
			}
		});
	}

	/**
	 * 设置动画
	 */
	private void anima() {
		//旋转动画
		RotateAnimation ra = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		//动画持续时间
		ra.setDuration(1000);
		//动画结束后，显示结束后界面
		ra.setFillAfter(true);

		//缩放动画
		ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(1000);
		sa.setFillAfter(true);

		//渐变动画
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(2000);
		aa.setFillAfter(true);

		//动画集合
		AnimationSet set=new AnimationSet(true);
		set.addAnimation(ra);
		set.addAnimation(sa);
		set.addAnimation(aa);

		//启动动画
		ivSplash.startAnimation(set);

		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				tvSplash.setVisibility(View.VISIBLE);

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
						startActivity(intent);
						finish();
					}
				}, 500);
			}
		});
	}

}
