package com.example.wsd_client.presenter;
/**
 * 购买操作presenter接口
 * @author wsd_leiguoqiang
 */
public interface IBuyPresenter {
	/**
	 * 从购物车界面进行商品购买
	 */
	public void buyFromCart();
	/**
	 * 从商品详情页面进行购买
	 */
	public void buyFromProductDetails();
	/**
	 * 从商品列表页面进行购买
	 */
	public void buyFromProductItem();
	/**
	 * 从收藏夹页面进行购买
	 */
	public void buyFromCollection();
}
