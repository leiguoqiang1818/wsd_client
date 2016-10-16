package com.example.wsd_client.modle.impl;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wsd_client.activity.LoginActivity;
import com.example.wsd_client.application.Myapplication;
import com.google.gson.Gson;
/**
 * 框架类：提供网络访问工具方法
 * @author wsd_leiguoqiang
 */
public class HttpModle {
	/**
	 * @使用场景：网络获取数据，并根据数据进行相应的处理（比如：数据呈现）
	 * @使用位置：mvc模式中，activity界面调用
	 * @param t:泛型类变量，根据服务器返回的字符串而自定义的实体类类型变量
	 * @param url：服务器接口地址
	 * @param params：接口参数集合
	 * @param callBack：回调对象
	 */
	public<T> void getData(final Class<T> t,String url, final Map<String, String> params,final HttpModleCallBack callBack){
		StringRequest sr = new StringRequest(Request.Method.POST, url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				Gson gson = new Gson();
				T instance = gson.fromJson(response, t);
				callBack.httpModleSuccessCallBack(instance);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callBack.httpMdoleErrorCallBack();
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		Myapplication.getRequestQueue().add(sr);
	}
	/**
	 * 框架类中回调接口
	 * @author wsd_leiguoqiang
	 */
	public interface HttpModleCallBack{
		/**
		 * 访问服务器成功时调用的方法
		 * @param object：泛型实体对象
		 */
		public void httpModleSuccessCallBack(Object object); 
		/**
		 * 访问服务器失败时调用的方法
		 */
		public void httpMdoleErrorCallBack();
	}
}
