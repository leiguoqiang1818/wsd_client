package com.example.wsd_client.application;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.wsd_client.activity.ProductDetailsPagerActivityImpl;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.entity.CollectionProduct;
import com.example.wsd_client.entity.OrderModle;
import com.example.wsd_client.entity.YW_ClientInfo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;
/**
 * application
 * @author wsd_leiguoqiang
 */
public class Myapplication extends Application{
	/**
	 * log方法全局标记变量
	 */
	private static boolean flag = true;
	/**
	 * list集合，用于封装activity
	 */
	private static List<Activity> list = new ArrayList<Activity>();

	/**
	 * context对象，共app其他组件使用
	 */
	private static Context context;
	/**
	 * 购物项集合
	 */
	private List<CartItem> list_cartItem = new ArrayList<CartItem>();
	/**
	 * 购物车对象
	 */
	private Cart cart = new Cart();
	/**
	 * 订单模式对象
	 */
	private OrderModle orderModle = new OrderModle();
	/**
	 * 收藏产品对象
	 */
	private CollectionProduct collectionProduct = new CollectionProduct();
	/**
	 * volley框架消息列队对象
	 */
	private static RequestQueue requestQueue;

	private YW_ClientInfo clientInfo;
	/**
	 * 商品详情页面activity
	 */
	private ProductDetailsPagerActivityImpl productActivity;
	/**
	 * 购物车数量标记变量
	 */
	private boolean new_cart_data = false;

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	//初始化数据
	private void init() {
		x.Ext.init(this);
		context = this;
		requestQueue = Volley.newRequestQueue(this);
	}

	/**
	 * app全局测试方法
	 * @param tag
	 * @param logcat
	 */
	public static void log(String tag , String logcat){
		if(flag==true){
			Log.i(tag, logcat);
		}
	}

	/**
	 * 结束当前app，用于退出按钮使用
	 * @param list
	 */
	public static void finishActivity(List<Activity> list){
		for(Activity activity:list){
			activity.finish();
		}
		Process.killProcess(Process.myPid());
	}

	/**
	 * toast显示
	 * @param string
	 */
	public static void toast(String string){
		Toast.makeText(context, string , Toast.LENGTH_SHORT).show();
	}

	public static List<Activity> getList() {
		return list;
	}

	public static Context getContext() {
		return context;
	}

	public List<CartItem> getList_cartItem() {
		return list_cartItem;
	}

	public void setList_cartItem(List<CartItem> list_cartItem) {
		this.list_cartItem = list_cartItem;
	}

	public static RequestQueue getRequestQueue() {
		return requestQueue;
	}

	public YW_ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(YW_ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public ProductDetailsPagerActivityImpl getProductActivity() {
		return productActivity;
	}

	public void setProductActivity(ProductDetailsPagerActivityImpl productActivity) {
		this.productActivity = productActivity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public OrderModle getOrderModle() {
		return orderModle;
	}

	public void setOrderModle(OrderModle orderModle) {
		this.orderModle = orderModle;
	}

	public boolean isNew_cart_data() {
		return new_cart_data;
	}

	public void setNew_cart_data(boolean new_cart_data) {
		this.new_cart_data = new_cart_data;
	}

	public CollectionProduct getCollectionProduct() {
		return collectionProduct;
	}

	public void setCollectionProduct(CollectionProduct collectionProduct) {
		this.collectionProduct = collectionProduct;
	}
	
}
