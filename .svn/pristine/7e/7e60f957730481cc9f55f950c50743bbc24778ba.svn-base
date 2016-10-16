package com.example.wsd_client.modle;

import java.util.List;
import java.util.Map;

import com.example.wsd_client.entity.Product;

/**
 * 商品列表页面modle层接口
 * @author wsd_leiguoqiang
 */
public interface ILiebiaoFragmentModle {
	/**
	 * modle层加载商品列表数据
	 * @param urlParam:服务器接口参数
	 * callBack:回调对象，用于view层数据更新
	 */
	public void loadData(Map<String, String> urlParam ,ProductItemDataCallBack callBack);
	/**
	 * 回调接口：用于商品列表数据呈现使用
	 * @author wsd_leiguoqiang
	 */
	public interface ProductItemDataCallBack{
		/**
		 * 呈现商品列表数据
		 * list:商品列表数据集合
		 */
		public void showData(List<Product> list);
	}
}
