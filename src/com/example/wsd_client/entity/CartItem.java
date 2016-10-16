package com.example.wsd_client.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体类，用于封装单个购物项的所有数据，包括数量，价格，原料等信息
 * @author wsd_leiguoqiang
 * 需要根据服务器数据进行完善----------------------------------------------------
 */
public class CartItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5625527151277786651L;
	/**
	 * 产品数量
	 */
	private int product_count = 1;
	/**
	 * 产品对象
	 */
	private Product product; 
	/**
	 * 购买参数,包含产品数量
	 */
	private Map<String, String> buyParam = new HashMap<String, String>();
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getProduct_count() {
		return product_count;
	}
	public void setProduct_count(int product_count) {
		this.product_count = product_count;
	}
	public Map<String, String> getBuyParam() {
		return buyParam;
	}
	public void setBuyParam(Map<String, String> buyParam) {
		this.buyParam = buyParam;
	}
	@Override
	public String toString() {
		return "CartItem [product_count=" + product_count + ", product="
				+ product.toString() + ", buyParam=" + buyParam.size() + "]";
	}
	
	
}
