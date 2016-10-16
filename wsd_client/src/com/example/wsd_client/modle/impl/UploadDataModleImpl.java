package com.example.wsd_client.modle.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.example.wsd_client.modle.IUploadDataModle;

/**
 * 商品上传modle层接口实现类，处理商品上传业务
 * 
 * @author wsd_leiguoqiang
 */
public class UploadDataModleImpl implements IUploadDataModle {

	@Override
	public boolean uploadDataByPost(String urlPath, Map<String, String> hashMap) {
		// 在没有运用网络框架的情况下，用http网络协议的post请求方法

		// 创建缓冲字符串对象，用于拼接字符串
		StringBuilder builder = new StringBuilder();
		if ((hashMap != null) && (!hashMap.isEmpty())) {

			// 遍历map集合，取出集合中的数据，作为post请求的参数
			for (Map.Entry<String, String> param : hashMap.entrySet()) {
				try {
					builder.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(),"utf-8")).append("&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			builder.deleteCharAt(builder.length()-1);
		}
		byte[] byteArray = builder.toString().getBytes();
		try {
			// 创建url对象
			URL url = new URL(urlPath);
			// 创建httpurlclient链接对象
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 设置输出流
			connection.setDoOutput(true);
			// 缓存取消设置
			connection.setUseCaches(false);
			//设置内容类型和长度
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//内容类型   
			connection.setRequestProperty("Content-Length",String.valueOf(byteArray.length));//长度   
			// 得到输出流，并向服务器输出
			OutputStream out = connection.getOutputStream();
			//向服务器写入数据
			out.write(byteArray);
			// 冲刷并关闭输出流
			out.flush();
			out.close();
			// 对服务器响应码进行判断，成功与否并作为该方法的返回值
			if(connection.getResponseCode()==200){
				return true;
			}else{
				return false;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
