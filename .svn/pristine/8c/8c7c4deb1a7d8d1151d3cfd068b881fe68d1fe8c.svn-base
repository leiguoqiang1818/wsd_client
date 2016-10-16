package com.example.wsd_client.modle;

import java.util.List;

import com.example.wsd_client.entity.CartItem;

/**
 * modle层接口，服务于购物车页面的数据加载
 * @author wsd_leiguoqiang
 */
public interface IUploadCartDataModle {
	/**
	 * 加载购物车页面数据
	 * @param path 服务器接口路径
	 * @param callBack 回调对象
	 */
	public void uploadCartData(String path,CartDataCallBack callBack);
	/**
	 * 加载购物车页面数据时候调用的回调接口
	 * @author wsd_leiguoqiang
	 */
	public interface CartDataCallBack{
		/**
		 * 更新购物车界面数据
		 * @param list 加载完的数据集合
		 */
		public void updateCart(List<CartItem> list);
	}
}
