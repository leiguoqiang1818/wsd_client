package com.example.wsd_client.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;


import android.os.Handler;
import android.os.Message;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.urlUtils.UrlPath;

/**
 * 实现订单中心的订单增删改功能
 */
public class DingdanOrderUtil {

	/**
	 * 通过构造方法，传入handler，将获取的响应码传出给相应的fragment
	 */
	private Handler handler;


	public DingdanOrderUtil(Handler handler) {
		super();
		this.handler = handler;
	}


	/**
	 * 订单的增加即下订单功能
	 * @param urlParam  网址参数
	 * @param map  需要传递服务器的map集合数据
	 */
	public  void addOrder(Map<String, String> map){
		String clientidString = map.get("clientid");
		Myapplication.log("DingdanUtil客户代码clientid：", clientidString);

		int clientid=Integer.parseInt(clientidString);


		String ladeid = map.get("ladeid");
		String areacode = map.get("areacode");
		String ladetype = map.get("ladetype");
		String cementcode = map.get("cementcode");
		String cementname = map.get("cementname");
		String ordernumber = map.get("ordernumber");
		String orderprice = map.get("orderprice");
		String totalprice = map.get("totalprice");
		String carname = map.get("carname");
		String carid = map.get("carid");
		String carcode = map.get("carcode");
		String carphone = map.get("carphone");
		String orderphone = map.get("orderphone");

		String statusString = map.get("status");
		int  status=Integer.parseInt(statusString);

		String urlParam=UrlPath.BUY+"?clientid="+clientid+"&ladeid="+ladeid+
				"&areacode="+areacode+"&ladetype="+ladetype+"&cementcode="+
				cementcode+"&cementname="+cementname+"&ordernumber="+
				ordernumber+"&orderprice="+orderprice+"&totalprice="+
				totalprice+"&carname="+carname+"&carid="+carid+"&carcode="+
				carcode+"&carphone="+carphone+"&orderphone="+orderphone+
				"&status="+status;

		getUrlResult(urlParam);
	}

	/**
	 * 订单的修改订单功能，但订单号无法修改	
	 * @param urlParam  网址参数
	 * @param map  需要传递服务器的map集合数据
	 */
	public void editOrder(Map<String, String> map){
		String clientidString = map.get("clientid");
		Myapplication.log("DingdanUtil客户代码clientid：", clientidString);

		int clientid=Integer.parseInt(clientidString);


		String ladeid = map.get("ladeid");
		String areacode = map.get("areacode");
		String ladetype = map.get("ladetype");
		String cementcode = map.get("cementcode");
		String cementname = map.get("cementname");
		String ordernumber = map.get("ordernumber");
		String orderprice = map.get("orderprice");
		String totalprice = map.get("totalprice");
		String carname = map.get("carname");
		String carid = map.get("carid");
		String carcode = map.get("carcode");
		String carphone = map.get("carphone");
		String orderphone = map.get("orderphone");

		String statusString = map.get("status");
		int  status=Integer.parseInt(statusString);

		String urlParam=UrlPath.EDIT+"?clientid="+clientid+"&ladeid="+ladeid+
				"&areacode="+areacode+"&ladetype="+ladetype+"&cementcode="+
				cementcode+"&cementname="+cementname+"&ordernumber="+
				ordernumber+"&orderprice="+orderprice+"&totalprice="+
				totalprice+"&carname="+carname+"&carid="+carid+"&carcode="+
				carcode+"&carphone="+carphone+"&orderphone="+orderphone+
				"&status="+status;

		getUrlResult(urlParam);




	}

	/**
	 * 订单中心的订单删除功能
	 * @param map 根据传入的销售单据id和客户id进行删除订单操作
	 */
	public void delete(Map<String, String> map){
		String orderidString = map.get("orderid");
		int orderid=Integer.parseInt(orderidString);

		String clientidString = map.get("clientid");
		int clientid=Integer.parseInt(clientidString);

		String urlParam=UrlPath.DELET+"?orderid="+orderid+"&clientid"+clientid;

		getUrlResult(urlParam);
	}


	/**
	 * 传入网络，获取服务器响应码，执行增删改功能
	 * @param url   传入服务器的网址参数
	 * @return  将获得的响应码通过handler传递给外界调用的fragment
	 * 		total： 1.当传回来为null或者为0时，表示失败；2.若为1或更大的值时，表示成功
	 */
	public  void getUrlResult(final String urlParam){

		new Thread(new Runnable() {


			@Override
			public void run() {
				StringBuffer sb=new StringBuffer();

				try {
					URL url = new URL(urlParam);
					HttpURLConnection conn=(HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					conn.connect();

					InputStream is=conn.getInputStream();
					InputStreamReader isr=new InputStreamReader(is);
					BufferedReader reader=new BufferedReader(isr);
					String line=reader.readLine();
					while(line!=null){
						sb.append(line);
						Myapplication.log("-----------", "sb:"+sb.toString());
						line=reader.readLine();
					}

					JSONObject jsonObject=new JSONObject(sb.toString());
					String total = jsonObject.getString("total");

					Myapplication.log("访问网络，新增订单的返回值total：", total);

					Message message=Message.obtain();
					message.obj=total;
					handler.sendMessage(message);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}


}
