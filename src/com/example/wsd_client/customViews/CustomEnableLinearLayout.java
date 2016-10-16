package com.example.wsd_client.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 自定义linearlayout:用于控制所有子控件
 * 默认情况下子控件是可以接受监听的
 * @author wsd_leiguoqiang
 */
public class CustomEnableLinearLayout extends LinearLayout {
	//子控件是否可以接受点击事件
	private boolean childClickable = true;

	public CustomEnableLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomEnableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	public CustomEnableLinearLayout(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		//返回true则拦截子控件所有点击事件，如果childclickable为true，则需返回false
		return !childClickable;
	}

	public void setChildClickable(boolean clickable) {
		childClickable = clickable;
	}
}