package com.example.wsd_client.modle;

import java.util.Map;

import com.example.wsd_client.entity.ClientInfo;

/**
 * 更多fragment页面modle层接口，处理用户网络数据修改等业务操作
 * @author wsd_leiguoqiang
 */
public interface IMoreFragmentModle {
	/**
	 * 向服务器发出请求，进行用户数据修改操作
	 * @param userInfo：用户信息需要修改的数据集合
	 */
	public void updateUserInfo(Map<String, String> userInfo,MoreFragmentCallBack callBack);
	/**
	 * moreFragment页面回调接口
	 * @author wsd_leiguoqiang
	 */
	public interface MoreFragmentCallBack{
		/**
		 * 进行view层数据更新使用
		 * @param clientInfo:服务器返回的用户信息对象
		 */
		public void updateMoreFragment(ClientInfo clientInfo);
	}
}
