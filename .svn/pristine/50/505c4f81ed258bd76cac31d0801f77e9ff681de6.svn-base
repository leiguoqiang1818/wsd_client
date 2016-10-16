package com.example.wsd_client.presenter.impl;

import java.util.List;
import java.util.Map;

import com.example.wsd_client.entity.Product;
import com.example.wsd_client.fragment.ILiebiaoFragment;
import com.example.wsd_client.modle.ILiebiaoFragmentModle;
import com.example.wsd_client.modle.ILiebiaoFragmentModle.ProductItemDataCallBack;
import com.example.wsd_client.modle.impl.LiebiaoFragmentModleImpl;
import com.example.wsd_client.presenter.ILiebiaoFragmentPrensenter;
/**
 * 商品列表页面presenter层实现类
 * @author wsd_leiguoqiang
 */
public class LiebiaoFragmentPrensenterImpl implements ILiebiaoFragmentPrensenter{
	private ILiebiaoFragment view;
	private ILiebiaoFragmentModle modle;
	/**
	 * 访问服务器接口时需要携带的参数
	 */
	private Map<String, String> urlParam;
	
	public LiebiaoFragmentPrensenterImpl(ILiebiaoFragment view,
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
				view.setData(list);
				view.showData();
			}
		});
	}

	

}
