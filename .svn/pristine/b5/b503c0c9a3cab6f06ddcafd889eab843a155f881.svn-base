package com.example.wsd_client.presenter.impl;

import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.example.wsd_client.entity.Product;
import com.example.wsd_client.entity.XS_Order_ABase;
import com.example.wsd_client.fragment.IDingdanFragment;
import com.example.wsd_client.fragment.ILiebiaoFragment;
import com.example.wsd_client.modle.IDingdanFragmentModle;
import com.example.wsd_client.modle.IDingdanFragmentModle.DingdanItemDataCallBack;
import com.example.wsd_client.modle.ILiebiaoFragmentModle;
import com.example.wsd_client.modle.ILiebiaoFragmentModle.ProductItemDataCallBack;
import com.example.wsd_client.modle.impl.DingdanFragmentModleImpl;
import com.example.wsd_client.modle.impl.LiebiaoFragmentModleImpl;
import com.example.wsd_client.presenter.IDingdanFragmentPrensenter;
import com.example.wsd_client.presenter.ILiebiaoFragmentPrensenter;
/**
 * 订单中心页面presenter层实现类
 * @author wsd_leiguoqiang
 */
public class DingdanFragmentPrensenterImpl implements IDingdanFragmentPrensenter{
	private IDingdanFragment view;
	private IDingdanFragmentModle modle;
	
	/**
	 * 访问服务器接口时需要携带的参数
	 */
	private Map<String, String> urlParam;
	
	public DingdanFragmentPrensenterImpl(IDingdanFragment view,
			Map<String, String> urlParam) {
		super();
		
		this.view = view;
		this.modle = new DingdanFragmentModleImpl();
		this.urlParam = urlParam;
	}

	public void loadData() {
		modle.loadData(urlParam, new DingdanItemDataCallBack() {
			
			@Override
			public void showData(List<XS_Order_ABase> list) {
				view.setData(list);
				view.showData();
				
			}
		});
	}

	

}
