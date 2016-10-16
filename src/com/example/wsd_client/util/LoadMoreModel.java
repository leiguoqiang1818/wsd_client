package com.example.wsd_client.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.XS_Order_ABase;
import com.example.wsd_client.entity.XS_Order_ABaseListItem;
import com.google.gson.Gson;

/**
 * 	订单中心
 * 
 *	加载更多数据
 */



public class LoadMoreModel {
	List<XS_Order_ABase> list = new ArrayList<XS_Order_ABase>();


	@SuppressWarnings("unused")
	public List<XS_Order_ABase> Loadmoredata(String url){

		StringBuffer sb=new StringBuffer();

		try {
			URL urlParam = new URL(url);
			HttpURLConnection conn=(HttpURLConnection) urlParam.openConnection();
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
			//运用gson框架对json数据String进行解析并封装到product实体类中
			Gson gson = new Gson();
			XS_Order_ABaseListItem dingdanListItem = gson.fromJson(sb.toString(), XS_Order_ABaseListItem.class);
			Myapplication.log("用到这个model了", dingdanListItem.getResult().toString()+"");
			//将解析的数据全部存入list<XS_Order_ABase>集合中
			list = dingdanListItem.getResult();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return list;





	}






}
