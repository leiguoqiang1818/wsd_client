package com.example.wsd_client.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import com.example.wsd_client.R;
import com.example.wsd_client.application.Myapplication;

/**
 * 图片展示Activity，可用于单个商品详情图片展示或其他图片展示
 * @author wsd_leiguoqiang
 */
public class PictureShowActivity extends BaseActivity implements ViewFactory,OnTouchListener{

	/**
	 * imageSwitcher对象
	 */
	@ViewInject(R.id.is_picture_show_pager)
	private ImageSwitcher is;

	/**
	 * 手势按下去的起始x位置
	 */
	private int downX = 0;
	private int downY = 0;
	/**
	 * 手势按下去的结束x位置
	 */
	private int upX = 0;
	private int upY = 0;
	/**
	 * imageSwitcher显示的图片资源标记
	 */
	private int index_imageSwitcher = 0;

	/**
	 * 图片资源
	 */
	private int[] imageArray = new int[]{R.drawable.home_vp1,R.drawable.home_vp2,R.drawable.home_vp3
			,R.drawable.home_vp1,R.drawable.home_vp2,R.drawable.home_vp3};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_show);
		x.view().inject(this);
		init();
		initListeners();
	}


	private void init() {
		Intent intent = getIntent();
		index_imageSwitcher = intent.getIntExtra("position", 0);
		//为imageSwitcher控件设置viewFactory
		is.setFactory(this);
		//设置默认图片资源进行显示
		is.setImageResource(imageArray[index_imageSwitcher]);
	}

	private void initListeners() {
		is.setOnTouchListener(this);
	}

	/**
	 * iamgeSwitcher控件必须实现的类，为了区分开控件和图片
	 */
	@Override
	public View makeView() {
		return new ImageView(this);
	}

	/**
	 * ontouchLietener监听器的实现方法
	 * @param v
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		//imageSwitcher控件监听
		case R.id.is_picture_show_pager:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = (int)event.getX();
				downY = (int) event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				//向右移动
				if(event.getX()>downX){
					TranslateAnimation animation_in = new TranslateAnimation(-((float)(is.getWidth())), ((event.getX()-downX)-((float)(is.getWidth()))), (float)(is.getTop()), (float)(is.getTop()));
					TranslateAnimation animation_out = new TranslateAnimation(0f, event.getX(), (float)(is.getTop()), (float)(is.getTop()));
					is.setInAnimation(animation_in);
					is.setOutAnimation(animation_out);
				}else{
					TranslateAnimation animation_in = new TranslateAnimation((float)(is.getWidth()), ((float)(is.getWidth())-(downX-event.getX())), (float)(is.getTop()), (float)(is.getTop()));
					TranslateAnimation animation_out = new TranslateAnimation(0f, -(downX-event.getX()), (float)(is.getTop()), (float)(is.getTop()));
					is.setInAnimation(animation_in);
					is.setOutAnimation(animation_out);
				}
				break;
			case MotionEvent.ACTION_UP:
				upX = (int) event.getX();
				upY = (int) event.getY();
				//判断左移动
				if((downX-upX)>100){
					index_imageSwitcher--;
					if(index_imageSwitcher<0){
						index_imageSwitcher = imageArray.length-1;
					}
					//可以添加动画进来
					//前一张左边出去，右边一张进来
					//					TranslateAnimation animation_out = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0, Animation.RELATIVE_TO_SELF,-(is.getWidth()), 
					//							Animation.RELATIVE_TO_SELF,0, Animation.RELATIVE_TO_SELF,0);
					//					animation_out.setDuration(1000);
					//					is.setOutAnimation(animation_out);
					//					TranslateAnimation animation_in = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -(is.getWidth()), Animation.RELATIVE_TO_SELF, 0,
					//							Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
					//					animation_in.setDuration(1000);
					//					is.setInAnimation(animation_in);
					is.setInAnimation(AnimationUtils.loadAnimation(PictureShowActivity.this, R.anim.animation_left_out));
					is.setOutAnimation(AnimationUtils.loadAnimation(PictureShowActivity.this, R.anim.animation_right_in));
					is.setImageResource(imageArray[index_imageSwitcher]);
					Myapplication.log("滑动距离", (downX-upX)+"图片资源"+index_imageSwitcher);
					//判断右移动
				}else if((upX-downX)>100){
					index_imageSwitcher++;
					if(index_imageSwitcher>(imageArray.length-1)){
						index_imageSwitcher = 0;
					}
					is.setInAnimation(AnimationUtils.loadAnimation(PictureShowActivity.this, R.anim.animation_left_in));
					is.setOutAnimation(AnimationUtils.loadAnimation(PictureShowActivity.this, R.anim.animation_right_out));
					is.setImageResource(imageArray[index_imageSwitcher]);
					Myapplication.log("滑动距离", (upX-downX)+"图片资源"+index_imageSwitcher);
					//如果起始和结束位置是同一个地点，说明时点击操纵
				}else if((downX==upX)&&(downY==upY)){
					finish();
				}
				break;
			}
			break;
		}
		return true;
	}
}
