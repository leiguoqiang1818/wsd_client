package com.example.wsd_client.presenter.impl;

import java.util.List;
import java.util.Map;

import com.example.wsd_client.activity.CollectionActivity;
import com.example.wsd_client.activity.ProductDetailsPagerActivityImpl;
import com.example.wsd_client.fragment.ILiebiaoFragment;
import com.example.wsd_client.fragment.ProductDetailsPagerProductFragment;
import com.example.wsd_client.fragment.impl.LiebiaoFragment;
import com.example.wsd_client.fragment.impl.UploadCartDataViewImpl;
import com.example.wsd_client.modle.IBuyModle;
import com.example.wsd_client.modle.IBuyModle.BuyModleCallBack;
import com.example.wsd_client.modle.impl.BuyModleImpl;
import com.example.wsd_client.presenter.IBuyPresenter;
/**
 * 购买操作presenter接口实现类
 * @author wsd_leiguoqiang
 */
public class BuyPresenterImpl implements IBuyPresenter{
	/**
	 * 购物车页面实现类
	 */
	private UploadCartDataViewImpl cartView;
	/**
	 * 购物车页面购买参数集合
	 */
	private List<Map<String, String>> list_buyParams;
	/**
	 * 产品id编号集合
	 */
	private List<String> list_productIds;


	/**
	 * 商品详情页面实现类
	 */
	private ProductDetailsPagerActivityImpl producView;
	/**
	 * 商品详情页面购买参数
	 */
	private Map<String, String> buyParam;

	/**
	 * 商品列表页面
	 */
	private ILiebiaoFragment productItemView;
	/**
	 * 收藏夹页面
	 */
	private CollectionActivity collectionActivity;

	/**
	 * 购买操作modle对象
	 */
	private IBuyModle buyModle;

	/**
	 * 从购物车页面进行购买操作时使用
	 * @param cartView
	 * @param list_buyParams
	 * @param list_productIds
	 * @param buyModle
	 */
	public BuyPresenterImpl(UploadCartDataViewImpl cartView,
			List<Map<String, String>> list_buyParams,
			List<String> list_productIds) {
		super();
		this.cartView = cartView;
		this.list_buyParams = list_buyParams;
		this.list_productIds = list_productIds;
		this.buyModle = new BuyModleImpl();
	}

	/**
	 * 从商品详情页面进行购买操作是使用
	 * @param producView
	 * @param buyParam
	 * @param buyModle
	 */
	public BuyPresenterImpl(ProductDetailsPagerActivityImpl producView,
			Map<String, String> buyParam) {
		super();
		this.producView = producView;
		this.buyParam = buyParam;
		this.buyModle = new BuyModleImpl();
	}

	/**
	 * 从商品列表 页面进行购买操作
	 * @param buyParam：购买参数集合
	 * @param productItemView：商品列表view
	 */
	public BuyPresenterImpl(Map<String, String> buyParam,
			ILiebiaoFragment productItemView) {
		super();
		this.buyParam = buyParam;
		this.productItemView = productItemView;
		this.buyModle = new BuyModleImpl();
	}

	/**
	 * 从收藏夹页面进行购买操作
	 * @param buyParam：购买参数集合
	 * @param productItemView：商品列表view
	 */
	public BuyPresenterImpl(Map<String, String> buyParam,
			CollectionActivity collectionActivity) {
		super();
		this.buyParam = buyParam;
		this.collectionActivity = collectionActivity;
		this.buyModle = new BuyModleImpl();
	}

	@Override
	public void buyFromCart() {
		buyModle.buyFromCart(list_buyParams, new BuyModleCallBack() {

			@Override
			public void updateAndToBuyCenter(String productId,int index_flag) {
				cartView.buyCompleted(productId,index_flag);
			}
		}, list_productIds);

	}

	@Override
	public void buyFromProductDetails() {
		buyModle.buyFromProductDetials(buyParam, new BuyModleCallBack() {

			@Override
			public void updateAndToBuyCenter(String productId,int index_flag) {
				//商品详情页面进行回调时，不需要参数
				producView.buyCompleted();
			}
		});
	}

	@Override
	public void buyFromProductItem() {
		buyModle.buyFromProductDetials(buyParam, new BuyModleCallBack() {

			@Override
			public void updateAndToBuyCenter(String productId, int index_flag) {
				//从商品列表页面购买时不需要参数
				productItemView.buyCompleted();
			}
		});
	}

	@Override
	public void buyFromCollection() {
		buyModle.buyFromProductDetials(buyParam, new BuyModleCallBack() {

			@Override
			public void updateAndToBuyCenter(String productId, int index_flag) {
				//从收藏夹页面购买时不需要参数
				collectionActivity.buyCompleted();
			}
		});
	}
}
