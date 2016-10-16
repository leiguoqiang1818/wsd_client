package com.example.wsd_client.customViews;

import com.example.wsd_client.application.Myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class CustomImageView extends ImageView{
	private int downX = 0;
	private int downY = 0;
	private OnClickListener onClickListener = null;

	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomImageView(Context context) {
		super(context);
	}

	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setClickable(boolean clickable) {
		super.setClickable(true);
	}
	/**
	 * 重写该方法，用于滑动监听冲突问题
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			Myapplication.log("customImageView开始开始点击", "被成功点击");
			downX = (int) event.getX();
			downY = (int) event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_UP:
			getParent().requestDisallowInterceptTouchEvent(true);
			Myapplication.log("customImageView点击与结束是否一致", "downX："+downX+"event.getX()"+event.getX());
			if(downX==(int)(event.getX())&&downY==(int)(event.getY())){
				//点击需要做的事情
				if(onClickListener!=null){
					onClickListener.onClick(this);
					Myapplication.log("customImageView点击测试", "被成功点击");
					return true;
				}
			}else{
				return false;
			}
			break;

		}
		Myapplication.log("customImageView点击测试", "按下操作");
		return false;

	}

	public void setOnClickListener(OnClickListener onClickListener){
		this.onClickListener = onClickListener;
	}
	/**
	 * imageveiw自定义点击监听类
	 * @author wsd_leiguoqiang
	 * v,自定义监听参数
	 */
	public interface OnClickListener{
		/**
		 * 
		 * @param v:控件本身对象
		 */
		public void onClick(View v);
	} 
}
