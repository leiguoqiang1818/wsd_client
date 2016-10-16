package com.example.wsd_client.fragment;

import java.util.List;

import android.content.Intent;

import com.example.wsd_client.activity.HomeActivity;
import com.example.wsd_client.entity.Product;

/**
 * 商品列表view层接口，用于规范数据呈现使用
 * @author wsd_leiguoqiang
 */
public interface ILiebiaoFragment {
	/**
	 * view层设置数据方法
	 * @param list：数据集合
	 */
	public void setData(List<Product> list);
	/**
	 * view层呈现数据方法
	 */
	public void showData();
	/**
	 * 购买操作完成时调用的方法
	 */
	public void buyCompleted();
		
}
