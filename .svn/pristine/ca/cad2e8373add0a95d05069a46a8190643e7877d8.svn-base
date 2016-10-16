package com.example.wsd_client.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.wsd_client.customViews.CustomImageView;
import com.example.wsd_client.customViews.CustomImageView.OnClickListener;

/**
 * 单个商品详细页面图片展示viewpager适配器
 * @author wsd_leiguoqiang
 */
public class ProductDetailsPagerShowViewPagerAdapter extends PagerAdapter{
	private List<CustomImageView> list;
	private Activity activity;
	
	public ProductDetailsPagerShowViewPagerAdapter(List<CustomImageView> list,Activity activity) {
		super();
		this.list = list;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return list==null?0:list.size();
	}
	
	@Override
	public Object instantiateItem(View container, final int position) {
		((ViewPager)container).addView(list.get(position));
		//给imageView添加点击监听
//		list.get(position).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//跳转到图片展示页面
//				Intent intent = new Intent("action_to_pictrue_show_pager");
//				intent.putExtra("position", position);
//				activity.startActivity(intent);
//			}
//		});
		return list.get(position);
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager)container).removeView(list.get(position));
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	

}
