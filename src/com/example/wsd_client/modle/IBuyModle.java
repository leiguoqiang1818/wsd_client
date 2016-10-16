package com.example.wsd_client.modle;

import java.util.List;
import java.util.Map;

import com.example.wsd_client.entity.CartItem;

/**
 * 购买modle层接口
 * @author wsd_leiguoqiang
 */
public interface IBuyModle {
	/**
	 * 此方法用于从购物车页面进行产品批量购买操作
	 * 购买方法，购买之后要将购物车中的商品移除掉，并且跳转到订单中心页面
	 * 如果是商品详情页面则不需要
	 * @param listBuyParam:购买参数,考虑到一次同事购买多个商品
	 * @param callBack:回调接口
	 * @param list_YWM_ID:产品id号
	 */
	public void buyFromCart(List<Map<String, String>> listBuyParam,BuyModleCallBack callBack,List<String> list_YWM_ID);
	/**
	 * 此方法用于从商品详情页面进行产品购买操作
	 * @param buyParam:购买参数
	 * @param callBack:回调接口
	 * @param productId:产品id编号
	 */
	public void buyFromProductDetials(Map<String, String> buyParam,BuyModleCallBack callBack);

	/**
	 * 购买回调接口
	 * @author wsd_leiguoqiang
	 */
	public interface BuyModleCallBack{
		/**
		 * 1)如果是从购物车页面进行购物操作：更新购物车数据，跳转到订单中心页面
		 * 2)如果是从商品详情页面进行购物操作：跳转到订单中心页面
		 * @param productId:产品id编号，从商品详情页面进行购买操作默认值为0即可
		 * @param index_flag:标记变量,从商品详情页面进行购买操作默认值为1即可
		 */
		public void updateAndToBuyCenter(String productId,int index_flag);
	}
}
