package com.example.wsd_client.modle;

import java.util.List;
import java.util.Map;

import com.example.wsd_client.entity.Product;
import com.example.wsd_client.entity.XS_Order_ABase;

/**
 * 订单中心页面modle层接口

 */
public interface IDingdanFragmentModle {
	/**
	 * modle层加载订单中心数据
	 * @param urlParam:服务器接口参数
	 * callBack:回调对象，用于view层数据更新
	 * @return 
	 */
	public void loadData(Map<String, String> urlParam ,DingdanItemDataCallBack callBack);
	/**
	 * 回调接口：用于商品列表数据呈现使用
	 * 
	 */
	public interface DingdanItemDataCallBack{
		/**
		 * 呈现商品列表数据
		 * list:商品列表数据集合
		 */
		public void showData(List<XS_Order_ABase> list);
	}
}
