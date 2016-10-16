package com.example.wsd_client.modle.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.modle.IUploadCartDataModle;

/**
 * modle层实现类，服务于购物车数据呈现页面
 * @author wsd_leiguoqiang
 */
public class UploadCartDataModleImpl implements IUploadCartDataModle{

	@Override
	public void uploadCartData(String path, CartDataCallBack callBack) {
		Myapplication app = (Myapplication) Myapplication.getContext();
		//本地化读取数据
		Cart cart = app.getCart();
		//申明数据集合
		List<CartItem> list = new ArrayList<CartItem>();
		list = cart.getList_cartItem();
		Myapplication.log("购物车modle层", list.toString());
		//利用回调对象进行view层数据更新
		callBack.updateCart(list);
	}

}
