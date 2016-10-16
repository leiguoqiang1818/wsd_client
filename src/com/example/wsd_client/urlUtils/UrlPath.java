package com.example.wsd_client.urlUtils;
/**
 * 工具类，用于定义一些app常用的rul常量
 * @author wsd_leiguoqiang
 */
public class UrlPath {
	/**
	 * 服务器基本url地址
	 */
	public static final String BASE_URL = "http://151h286a02.iok.la:12230/wsd_cementapp/hello";
	/**
	 * 商品列表url地址
	 */
	public static final String PRODUCT_ITEM_URL = BASE_URL+"/getcementall";
	
	/**
	 * 用户账号信息url地址
	 */
	public static final String USER_INFO=BASE_URL+"/clientlogincheck";
	/**
	 * 用户信息修改url地址
	 */
	public static final String USER_INFO_EDITE = BASE_URL+"/editclient";
	/**
	 * 购买接口
	 */
	public static final String BUY = BASE_URL+"/addxsordera";
	
	/**
	 * 修改订单单据接口
	 */
	public static final String EDIT=BASE_URL+"/editxsordera";
	
	/**
	 * 删除订单单据接口
	 */
	public static final String DELET=BASE_URL+"/delxsordera";
	
	/**
	 * 账户资金查询接口
	 */
	public static final String TSYS_ACCOUNT=BASE_URL+"/getaccountmoney";
	
	/**
	 * 订单中心url地址
	 */
	public static final String ORDERS_CENTRE=BASE_URL+"/getxsoorderall";
	
	/**
	 * 消息中心接口
	 */
	public static final String NEWS_CENTER=BASE_URL+"/getmessagecenter";
	/**
	 * 订单中心删除地址
	 */
	public static final String ORDER_DELETE=BASE_URL+"/delxsordera";
	/**
	 * 订单中心修改地址
	 */
	public static final String ORDER_EDIT=BASE_URL+"/editxsordera";
	/**
	 * 账单中心接口
	 */
	public static final String BILL=BASE_URL+"/getxspool";
}
