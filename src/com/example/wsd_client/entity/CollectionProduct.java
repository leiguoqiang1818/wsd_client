package com.example.wsd_client.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.constant.Constant;

/**
 * 实体类：封装收藏的产品
 * @author wsd_leiguoqiang
 */
public class CollectionProduct implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3777180327718027332L;
	/**
	 * 商品数据集合
	 */
	private List<Product> list_products = new ArrayList<Product>();

	public List<Product> getList_products() {
		return list_products;
	}
	public void setList_products(List<Product> list_products) {
		this.list_products = list_products;
	}

	/**
	 * 自定义方法，保存数据
	 */
	public void saveCollectionProduct(){
		//当用户登录时方可进行本地化存储
		YW_ClientInfo clientInfo = ((Myapplication)(Myapplication.getContext())).getClientInfo();
		if(clientInfo!=null){
			int clientId = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0).getYWC_ClientID();
			//获取file文件对象,用户id和购物车数据进行一一对应
			File file = new File(Myapplication.getContext().getCacheDir(),clientId+Constant.COLLECTION_PRODUCT_CACHE_FILE_NAME);
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
	 * 自定义方法，读取收藏数据
	 * @return
	 */
	public CollectionProduct readCollectionnProduct(){
		YW_ClientInfo clientInfo = ((Myapplication)(Myapplication.getContext())).getClientInfo();
		if(clientInfo!=null){
			int clientId = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0).getYWC_ClientID();
			try {
				File file = new File(Myapplication.getContext().getCacheDir(), clientId+Constant.COLLECTION_PRODUCT_CACHE_FILE_NAME);
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				CollectionProduct collectionProduct = (CollectionProduct) ois.readObject();
				if(collectionProduct==null){
					ois.close();
					return new CollectionProduct();
				}
				ois.close();
				return collectionProduct;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new CollectionProduct();
	}
	/**
	 * 自定义方法，添加收藏商品,最高限额20条
	 */
	public void addCollectionProduct(Product product){
		//得到产品id
		int product_id = product.getYWM_ID();
		if(list_products.size()!=0){
			for(Product collectionProduct:list_products){
				if(collectionProduct.getYWM_ID()==product_id){
					Myapplication.toast("此商品已经存在收藏夹");
					return;
				}
			}
			//将数据添加到集合中
			list_products.add(0,product);
			//保存数据到本地
			saveCollectionProduct();
		}else{
			//将数据添加到集合中
			list_products.add(0,product);
			//保存数据到本地
			saveCollectionProduct();
		}
		//判断是否超过20条数据
		if(list_products.size()>20){
			list_products.remove(list_products.size()-1);
		}
	}
	/**
	 * 自定义方法，删除收藏夹中某个商品
	 * @param product
	 */
	public void deleteCollectionProduct(Product product){
		//得到产品id
		int product_id = product.getYWM_ID();
			for(Product collectionProduct:list_products){
				if(collectionProduct.getYWM_ID()==product_id){
					list_products.remove(collectionProduct);
				}
			}
			//保存数据到本地
			saveCollectionProduct();
	}
	/**
	 * 自定义方法，判断是否已经存在收藏夹里面
	 * @param product
	 * @return
	 */
	public boolean existCollectioin(Product product){
		int product_id = product.getYWM_ID();
		for(Product collectionProduct:list_products){
			if(product_id==collectionProduct.getYWM_ID()){
				return true;
			}
		}
		return false;
	}
}
