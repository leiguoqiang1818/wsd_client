package com.example.wsd_client.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



import com.example.wsd_client.adapter.CartAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.constant.Constant;


/**
 * 实体类，用于封装list<CartItem>属性和基于CartItem的增，删，改，查的方法
 * 此实体类应用于购物车列表页面使用
 * @author wsd_leiguoqiang
 */
public class Cart implements Serializable{
	/**
	 * 序列化版本
	 */
	private static final long serialVersionUID = 2611726014694287407L;
	/**
	 * 购物项集合
	 */
	private List<CartItem> list_cartItem = new ArrayList<CartItem>();

	public List<CartItem> getList_cartItem() {
		return list_cartItem;
	}

	public void setList_cartItem(List<CartItem> list_cartItem) {
		this.list_cartItem = list_cartItem;
	}

	/**
	 * 修改商品的数量
	 */
	public void amendCount(long id,int number){
		//根据产品的id号进行数据的修改
		for(int i = 0;i<list_cartItem.size();i++){
			int productId = list_cartItem.get(i).getProduct().getYWM_ID();
			if((id+"").equals(productId+"")){
				list_cartItem.get(i).setProduct_count(number);
				//购买参同时修改(下单数量)
				list_cartItem.get(i).getBuyParam().put("ordernumber", number+"");
				//本地化数据存储
				saveCartData();
				return;
			}
		}
	}

	/**
	 * 添加购物项，添加商品到购物车
	 */
	public void addCartItem(long id,Product product){
		//已经存在购物车,则数量增加1
		for(CartItem cartItem:list_cartItem){
			int productId = cartItem.getProduct().getYWM_ID();
			if((id+"").equals(productId+"")){
				//将数量属性增加1
				cartItem.setProduct_count(cartItem.getProduct_count()+1);
				return;
			}
		}
		//不存在购物车,则往购物项集合中添加一条数据
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		list_cartItem.add(cartItem);
		//将数据进行本地化存储
		saveCartData();
	}
	/**
	 * 删除购物项
	 * 使用场景：单个购物项的删除
	 */
	public void deleteCartItem(int id,CartAdapter adapter){
		//通过id号进行数据删除
		for(int i = 0;i<list_cartItem.size();i++){
			CartItem cartItem = list_cartItem.get(i);
			int cartItem_id = cartItem.getProduct().getYWM_ID();
			if((id+"").equals(cartItem_id+"")){
				list_cartItem.remove(i);
				//将新数据持久化到本地
				saveCartData();
				break;
			}
		}
		//购物车页面更新数据
		adapter.notifyDataSetChanged();
	}

	/**
	 * 得出购物车的总价
	 * 参数：list结算数据集合
	 * @return double类型总价
	 */
	public double totalPrice(List<CartItem> list){
		BigDecimal total = new BigDecimal(0);
		for(CartItem cartItem:list){
			//精确大数据
			BigDecimal b1 = new BigDecimal(cartItem.getProduct().getYWM_Price());
			BigDecimal b2 = new BigDecimal(cartItem.getBuyParam().get("ordernumber"));
			total = (b1.multiply(b2)).add(total);
		}
		//保留两位小数
		DecimalFormat df = new DecimalFormat("#.00");
		String price = df.format(total.doubleValue());
		return Double.parseDouble(price);
	}
	/**
	 * 单个商品总价
	 * @param cartItem:购物项对象
	 * @return 
	 */
	public double singleTotalPrice(CartItem cartItem){
		BigDecimal bd_price = new BigDecimal(cartItem.getProduct().getYWM_Price());
		BigDecimal bd_acount = new BigDecimal(cartItem.getBuyParam().get("ordernumber"));
		//保留两位小数
		DecimalFormat df = new DecimalFormat("#.00");
		String price = df.format((bd_price.multiply(bd_acount)).doubleValue());
		return Double.parseDouble(price);
	}
	/**
	 * 单个商品总价:临时使用，cart对象不做保存之前使用
	 * @param cartItem:购物项对象
	 * @return 
	 */
	public double temporarySingleTotalPrice(CartItem cartItem,int acount){
		BigDecimal bd_price = new BigDecimal(cartItem.getProduct().getYWM_Price());
		BigDecimal bd_acount = new BigDecimal(acount);
		//保留两位小数
		DecimalFormat df = new DecimalFormat("#.00");
		String price = df.format((bd_price.multiply(bd_acount)).doubleValue());
		return Double.parseDouble(price);
	}
	/**
	 * 购物车数据持久化本地存储
	 */
	public void saveCartData(){
		//当用户登录时方可进行本地化存储
		YW_ClientInfo clientInfo = ((Myapplication)(Myapplication.getContext())).getClientInfo();
		if(clientInfo!=null){
			int clientId = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0).getYWC_ClientID();
			//获取file文件对象,用户id和购物车数据进行一一对应
			File file = new File(Myapplication.getContext().getCacheDir(),clientId+Constant.CART_CACHE_FILE_NAME);
			try {
				//获取序列化对象输出流
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
				//将流写入本地
				oos.writeObject(this);
				oos.flush();
				oos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 购物车数据本地获取
	 */
	public Cart readCartData(){
		YW_ClientInfo clientInfo = ((Myapplication)(Myapplication.getContext())).getClientInfo();
		if(clientInfo!=null){
			
			int clientId = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0).getYWC_ClientID();
			try {
				File file = new File(Myapplication.getContext().getCacheDir(), clientId+Constant.CART_CACHE_FILE_NAME);
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				Cart cart = (Cart) ois.readObject();
				if(cart==null){
					ois.close();
					return new Cart();
				}
				ois.close();
				return cart;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new Cart();
	}
}
