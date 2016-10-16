package com.example.wsd_client.presenter.impl;

import java.util.List;
import java.util.Map;


import com.example.wsd_client.entity.Product;
import com.example.wsd_client.fragment.IKuaisuFragment;
import com.example.wsd_client.modle.ILiebiaoFragmentModle;
import com.example.wsd_client.modle.ILiebiaoFragmentModle.ProductItemDataCallBack;
import com.example.wsd_client.modle.impl.LiebiaoFragmentModleImpl;
import com.example.wsd_client.presenter.IKuaisuFragmentPresenter;

/**
 * 快速下单页面获取商品数据presenter实现类
 *
 */
public class KuaisuFragmentPresenterImpl implements IKuaisuFragmentPresenter{
	private IKuaisuFragment view;
	private ILiebiaoFragmentModle modle;
	
	/**
	 * 访问服务器接口时需要携带的参数
	 */
	private Map<String, String> urlParam;
	
	
	public KuaisuFragmentPresenterImpl(IKuaisuFragment view,
			Map<String, String> urlParam) {
		super();
		this.view = view;
		this.modle = new LiebiaoFragmentModleImpl();
		this.urlParam = urlParam;
	}

	@Override
	public void loadData() {
		modle.loadData(urlParam, new ProductItemDataCallBack() {
			
			@Override
			public void showData(List<Product> list) {
				view.setDataProduct(list);
				view.showData();
				
			}
		});
		
	}

}
