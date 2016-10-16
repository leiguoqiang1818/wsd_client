package com.example.wsd_client.presenter.impl;

import java.util.List;

import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.fragment.IUploadCartDataView;
import com.example.wsd_client.modle.IUploadCartDataModle;
import com.example.wsd_client.modle.IUploadCartDataModle.CartDataCallBack;
import com.example.wsd_client.modle.impl.UploadCartDataModleImpl;
import com.example.wsd_client.presenter.IUploadCartDataPresenter;

/**
 * presenter层接口实现类，服务于购物车页面加载数据
 * @author wsd_leiguoqiang
 */
public class UploadCartDataPresenterImpl implements IUploadCartDataPresenter{
	private IUploadCartDataView view;
	private IUploadCartDataModle modle;
	private String path;
	
	public UploadCartDataPresenterImpl(IUploadCartDataView view,String path) {
		super();
		this.view = view;
		this.modle = new UploadCartDataModleImpl();
		this.path = path;
	}

	@Override
	public void uploadCartData(String path) {
		//调用modle层业务逻辑
		modle.uploadCartData(path, new CartDataCallBack() {

			@Override
			public void updateCart(List<CartItem> list) {
				view.setData(list);
				view.setAdapter();
			}
		});
	}

}
