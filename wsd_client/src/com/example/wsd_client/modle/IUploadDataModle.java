package com.example.wsd_client.modle;

import java.util.Map;

/**
 * 商品上传modle层接口，处理商品数据上传业务
 * @author wsd_leiguoqiang
 */
public interface IUploadDataModle {
	/**
	 * modle层定义的方法：用于上传商品数据
	 * 运用http网络请求中的post请求方法
	 * @param urlPath 服务器访问接口地址
	 * @param hashMap map集合，用于封装post网络请求所携带的参数
	 * @return boolean类型数据，用于判断数据上传 成功还是失败
	 */
	public boolean uploadDataByPost(String urlPath,Map<String,String> hashMap);
}
 