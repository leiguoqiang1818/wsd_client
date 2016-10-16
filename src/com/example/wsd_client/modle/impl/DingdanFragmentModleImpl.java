package com.example.wsd_client.modle.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wsd_client.application.Myapplication;

import com.example.wsd_client.entity.XS_Order_ABase;
import com.example.wsd_client.entity.XS_Order_ABaseListItem;
import com.example.wsd_client.modle.IDingdanFragmentModle;

import com.example.wsd_client.urlUtils.UrlPath;
import com.google.gson.Gson;
/**
 * 商品列表页面modle层实现类，用于网络加载商品列表数据
 * @author wsd_leiguoqiang
 */
public class DingdanFragmentModleImpl implements IDingdanFragmentModle{
	

	/**
	 * 获取volley框架requestqueue对象
	 */
	private RequestQueue rq = Volley.newRequestQueue(Myapplication.getContext());
	
	@Override
	public  void loadData(final Map<String, String> urlParam,
			final DingdanItemDataCallBack callBack) {

		//运用volley框架进行服务器接口访问，并得到json数据String
		StringRequest sr = new StringRequest(Request.Method.POST, UrlPath.ORDERS_CENTRE, 
				new Response.Listener<String>() {
			List<XS_Order_ABase> list = new ArrayList<XS_Order_ABase>();
			@Override
			public void onResponse(String response) {
				//运用gson框架对json数据String进行解析并封装到product实体类中
				Gson gson = new Gson();
				XS_Order_ABaseListItem dingdanListItem = gson.fromJson(response, XS_Order_ABaseListItem.class);
				Myapplication.log("modle----数据", dingdanListItem.getResult().toString()+"");
				//将解析的数据全部存入list<XS_Order_ABase>集合中
				list = dingdanListItem.getResult();
				Myapplication.log("modle----数据", list.size()+"");
				//运用回调对对象，对数据进行view层呈现
				callBack.showData(list);
			
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Myapplication.log("volley返回错误数据", error.toString());
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				
				
				return urlParam;
			}
		};
		rq.add(sr);
		



	}

}
