package com.example.wsd_client.presenter.impl;

import java.util.Map;

import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.fragment.IMoreFragment;
import com.example.wsd_client.modle.IMoreFragmentModle;
import com.example.wsd_client.modle.IMoreFragmentModle.MoreFragmentCallBack;
import com.example.wsd_client.modle.impl.MoreFragmentModleImpl;
import com.example.wsd_client.presenter.IMoreFragmentPresenter;
/**
 * moreFragment页面presenter层实现类
 * @author wsd_leiguoqiang
 */
public class MoreFragmentPresenterImpl implements IMoreFragmentPresenter{
	private IMoreFragment view;
	private IMoreFragmentModle modle;
	private Map<String, String> userInfo;

	public MoreFragmentPresenterImpl(IMoreFragment view,
			Map<String, String> userInfo) {
		super();
		this.view = view;
		this.modle = new MoreFragmentModleImpl();
		this.userInfo = userInfo;
	}

	@Override
	public void updateUserInfo() {
		modle.updateUserInfo(userInfo, new MoreFragmentCallBack() {
			@Override
			public void updateMoreFragment(ClientInfo clientInfo) {
				//进行view层数据更新
				view.updateView(clientInfo);
			}
		});
	}

}
