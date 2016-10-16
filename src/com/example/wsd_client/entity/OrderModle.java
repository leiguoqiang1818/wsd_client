package com.example.wsd_client.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.constant.Constant;

/**
 * 实体类： 订单模式实体类
 * @author wsd_leiguoqiang
 */
public class OrderModle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6140386255451557481L;
	/**
	 * 订单模式数据集合
	 */
	private List<OrderModleInfo> list_orderModleInfo = new ArrayList<OrderModleInfo>();

	public List<OrderModleInfo> getList_orderModleInfo() {
		return list_orderModleInfo;
	}

	public void setList_orderModleInfo(List<OrderModleInfo> list_orderModleInfo) {
		this.list_orderModleInfo = list_orderModleInfo;
	}

	/**
	 * 增加一条订单模式数据
	 */
	public void addOrderModleInfo(OrderModleInfo orderinfo){
		String orderName = orderinfo.getOrderModleName();
		for(OrderModleInfo order:list_orderModleInfo){
			if(orderName.equals(order.getOrderModleName())){
				Myapplication.toast("订单名已存在，请重置");
				return;
			}
		}
		//保存数据
		list_orderModleInfo.add(orderinfo);
		saveOrderModle();
		Myapplication.toast("订单模式添加成功");
	}

	/**
	 * 保存订单模式数据
	 * 利用用户id号与数据绑定
	 */
	public void saveOrderModle(){
		ClientInfo clientInfo = ((Myapplication) Myapplication.getContext()).getClientInfo().getResult().get(0);
		if(clientInfo!=null){
			try {
				int clientID = clientInfo.getYWC_ClientID();
				//获取file对象
				File file = new File(((Myapplication) Myapplication.getContext()).getCacheDir(), clientID+Constant.ORDER_MODLE_CACHE_FILE_NAME);
				//获取对象输出流
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
				//向文件写入orderModle对象
				oos.writeObject(this);
				//冲刷数据
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
	 * 读取订单模式数据
	 * @return OrderModle对象
	 */
	public OrderModle readOrderModle(){
		ClientInfo clientInfo = ((Myapplication) Myapplication.getContext()).getClientInfo().getResult().get(0);
		if(clientInfo!=null){
			int clientID = clientInfo.getYWC_ClientID();
			File file = new File(Myapplication.getContext().getCacheDir(), clientID+Constant.ORDER_MODLE_CACHE_FILE_NAME);
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			OrderModle orderModle = null;
			if(ois!=null){
				try {
					orderModle = (OrderModle) ois.readObject();
				} catch (OptionalDataException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(orderModle==null){
					try {
						ois.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return new OrderModle();
				}
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Myapplication.log("ordermodle实体页面------读取成功", orderModle.toString());
				return orderModle;
			}
		}
		return new OrderModle();
	}

	@Override
	public String toString() {
		return "OrderModle [list_orderModleInfo=" + list_orderModleInfo.toString() + "]";
	}
}
