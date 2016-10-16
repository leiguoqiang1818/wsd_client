package com.example.wsd_client.modle.impl;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.modle.IMoreFragmentModle;
import com.example.wsd_client.urlUtils.UrlPath;

/**
 * moreFragment页面modle层接口
 * @author wsd_leiguoqiang
 */
public class MoreFragmentModleImpl implements IMoreFragmentModle{

	@Override
	public void updateUserInfo(final Map<String, String> userInfo,
			final MoreFragmentCallBack callBack) {
		//volley框架进行服务器访问
		StringRequest sr = new StringRequest(Request.Method.POST, UrlPath.USER_INFO_EDITE, 
				new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				//完善情况下需要对返回对象进行判断
				try {
					JSONObject jsonObject = new JSONObject(response);
					int total = jsonObject.getInt("total");
					//数据成功返回
					if(total==1){
						//将map集合中的属性值全部赋值给本地缓存用户信息实体对象中，进行用户数据更新
						ClientInfo clientInfo = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0);
						clientInfo.setYWC_AreaCode(userInfo.get("areacode"));
						clientInfo.setYWC_PassWord(userInfo.get("password"));
						clientInfo.setYWC_LinkPhone(userInfo.get("linkphone"));
						clientInfo.setYWC_LinkMan(userInfo.get("linkman"));
						clientInfo.setYWC_ClientName(userInfo.get("clientname"));
						clientInfo.setYWC_ClientCode(userInfo.get("clientcode"));
						Myapplication.toast("用户设置成功");
						//callback对象调用
						callBack.updateMoreFragment(clientInfo);
						//返回不成功
					}else{
						JSONObject object = jsonObject.getJSONObject("- status");
						String reason = object.getString("status_reason");
						Myapplication.toast(reason);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Myapplication.toast("网络繁忙，请稍后再试"+error.toString());
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return userInfo;
			}
		};
		Myapplication.getRequestQueue().add(sr);
	}


}
