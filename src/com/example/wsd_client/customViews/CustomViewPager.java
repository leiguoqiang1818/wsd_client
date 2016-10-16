package com.example.wsd_client.customViews;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.wsd_client.activity.ProductDetailsPagerActivityImpl;
import com.example.wsd_client.application.Myapplication;
/**
 * 自定义viewpager，用于处理与外层容器滑动监听冲突
 * @author wsd_leiguoqiang
 */
public class CustomViewPager extends ViewPager{
	private float downX;
	private float downY;
	private float upX;
	private float upY;
	/**
	 * 点击标记，用于控制点击能否起作用
	 */
	private boolean click_flag = true;

	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public boolean isClick_flag() {
		return click_flag;
	}

	public void setClick_flag(boolean click_flag) {
		this.click_flag = click_flag;
	}

	//返回true，终止父级容器对滑动事件的监听
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		//告知父级容器不要对该控件的操作进行干扰
		switch (arg0.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			downX = arg0.getX();
			downY = arg0.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_UP:
			getParent().requestDisallowInterceptTouchEvent(true);
			upX = arg0.getX();
			upY = arg0.getY();
			if((upX==downX)&&(upY==downY)&&click_flag){
				Intent intent = new Intent();
				intent.setAction("action_to_pictrue_show_pager");
				Myapplication app = ((Myapplication)(Myapplication.getContext()));
				ProductDetailsPagerActivityImpl productActivity = app.getProductActivity();
				productActivity.startActivity(intent);
				return true;
			}
			break;
		}
		super.onTouchEvent(arg0);
		return true;
	}
}
