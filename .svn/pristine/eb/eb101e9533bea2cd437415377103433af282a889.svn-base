package com.example.wsd_client.modle.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.BuyReponse;
import com.example.wsd_client.modle.IBuyModle;
import com.example.wsd_client.urlUtils.UrlPath;

/**
 * 购买modle实现类，服务于购买操作
 * @author wsd_leiguoqiang
 */
public class BuyModleImpl implements IBuyModle{

	@SuppressWarnings("unchecked")
	@Override
	public void buyFromCart(List<Map<String, String>> listBuyParam,
			BuyModleCallBack callBack, List<String> list_YWM_ID) {
		//考虑到同时多个商品购买，并且保持数据的一致性，用异步线程去处理购买的请求
		//购买参数和商品id数据是同步操作，故存在一一对应的正确关系
		int i = 0;
		for(Map<String, String> buyParam:listBuyParam){
			new BuyAsyncTask(buyParam, callBack,list_YWM_ID.get(i),(i+1)).execute(buyParam);
			Myapplication.log("产品id号码", list_YWM_ID.get(i)+"");
			Myapplication.log("线程标记号", (i+1)+"");
			i++;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buyFromProductDetials(Map<String, String> buyParam,
			BuyModleCallBack callBack) {
		new BuyAsyncTask(buyParam, callBack, "0", 0).execute(buyParam);
	}

	/**
	 * 自定义异步线程
	 * @author wsd_leiguoqiang
	 * @param BuyReponse:服务器响应实体对象
	 */
	class BuyAsyncTask extends AsyncTask<Map<String, String>, Void, BuyReponse>{
		/**
		 * 购买参数
		 */
		private Map<String, String> buyParam;
		/**
		 * 回调对象
		 */
		private BuyModleCallBack callBack;
		/**
		 * 产品id编号(单个商品购买默认0)
		 */
		private String productId;
		/**
		 * 线程顺序标记(单个商品购买默认0)
		 */
		private int index_flag;

		public BuyAsyncTask(Map<String, String> buyParam,
				BuyModleCallBack callBack,String productId,int index_flag) {
			super();
			this.buyParam = buyParam;
			this.callBack = callBack;
			this.productId = productId;
			this.index_flag = index_flag;
		}

		@Override
		protected BuyReponse doInBackground(Map<String, String>... params) {
			//进行服务器访问,获取访问参数
			StringBuilder stringbuilder = new StringBuilder();
			//创建数据封装实体
			BuyReponse buyreponse = new BuyReponse();
			//取出参数,并存入stringbuilder中
			for(String key:buyParam.keySet()){
				try {
					stringbuilder.append(key).append("=").append(URLEncoder.encode(buyParam.get(key),"utf-8")).append("&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			stringbuilder.deleteCharAt(stringbuilder.length()-1);
			Myapplication.log("-----购买参数", stringbuilder.toString());
			//将字符串转换成byte数组
			byte[] bytearray = stringbuilder.toString().getBytes();
			try {
				URL url = new URL(UrlPath.BUY);
				//获取连接对象
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				//设置请求方法
				connection.setRequestMethod("POST");
				//设置输入输出流
				connection.setDoInput(true);
				connection.setDoOutput(true);
				//设置不缓存
				connection.setUseCaches(false);
				connection.setConnectTimeout(5000);
				//设置参数文本类型
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("Content-Length", String.valueOf(bytearray.length));
				OutputStream os;
				os = connection.getOutputStream();
				//将数据传入服务器
				os.write(bytearray);
				os.flush();
				Myapplication.log("----服务器响应码", connection.getResponseCode()+"");
				//获取输入输出流
				InputStream is = connection.getInputStream();
				//根据响应码判断网络连接是否成功
				if(connection.getResponseCode()==200){
					//从输入流中获取服务器返回的数据
					StringBuilder sb = new StringBuilder();
					//将字节流转换成缓冲字符流，提高读取速度
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String string = br.readLine();
					while(string!=null){
						sb.append(string);
						string = br.readLine();
					}
					br.close();
					Myapplication.log("--服务器响应数据", sb.toString());
					//解析json数据
					JSONObject jsonObject = new JSONObject(sb.toString());
					
					String total = jsonObject.getString("total");
					buyreponse.setTotal(total);
					
					String result = jsonObject.getString("result");
					buyreponse.setResult(result);
					
					JSONObject status = jsonObject.getJSONObject("status");
					String reason = status.getString("status_reason");
					buyreponse.setReason(reason);
				}
				/**
				 *  {
    					"status": {
        						"status_code": -1,
        						"status_reason": "订单号1469412631340已经存在，不能再新增"
    							},
    					"result": null,
    					"total": null
					}
				 **/
				is.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return buyreponse;
		}

		@Override
		protected void onPostExecute(BuyReponse result) {
			super.onPostExecute(result);
			//根据服务器响应数据进行相关操作
			if(result.getTotal().equals("1")){
				//调用回调对象，进行主线程相关操作
				//购物和数据更新，跳转到订单中心页面
				callBack.updateAndToBuyCenter(productId,index_flag);
			}else{
				Myapplication.toast(result.getReason());
			}
		}
	}
}
