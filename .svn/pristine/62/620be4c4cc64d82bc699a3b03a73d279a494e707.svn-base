package com.example.wsd_client.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.AccountInfo;
import com.example.wsd_client.urlUtils.UrlPath;

/**
 * 获取账单中心的网络数据工具
 */
public class AccountInfoUtil {

	/**
	 * 通过构造方法，传入handler，将获取的响应码传出给相应的fragment
	 */
	private Handler handler;

	/**
	 * 封装账单中心信息的集合
	 */
	private List<AccountInfo> list = new ArrayList<AccountInfo>();

	/**
	 * 获取当前选择的是五种状态中的一种
	 */
	private Integer xsstatus;

	public AccountInfoUtil(Handler handler) {
		super();
		this.handler = handler;
	}


	/**
	 * 获取服务器消息中心的消息数据
	 * @param map  封装的消息中心请求数据参数
	 * @param i    当传入是1时，清除之前list获取的残留数据；
	 *     	                  当传入是2时，表示在执行上拉加载或者下拉刷新，不清除list里的数据
	 */
	public  void getAccountInfo(Map<String, String> map,int i){
		Myapplication.log("AccountFragment进入网络请求方法：", "333333333333333");

		String pageNumString = map.get("pageNum");
		Integer pageNum=Integer.parseInt(pageNumString);

		String pageSizeString = map.get("pageSize");
		Integer pageSize=Integer.parseInt(pageSizeString);

		String starttime = map.get("starttime");
		String endtime = map.get("endtime");

		String clientidString = map.get("clientid");
		Integer clientid=Integer.parseInt(clientidString);

		String xsstatusString = map.get("xsstatus");
		xsstatus = Integer.parseInt(xsstatusString);

		String urlParam=UrlPath.BILL+"?pageNum="+pageNum+"&pageSize="
				+pageSize+"&starttime="+starttime+"&endtime="+endtime+
				"&clientid="+clientid+"&xsstatus="+xsstatus;

		getAccountInfoResult(urlParam,i);
	}



	/**
	 * 获取数据
	 * @param urlParam：网路地址   tag：五种筛选方式中的一种
	 */
	public void getAccountInfoResult(final String urlParam, final int num){
		//当传入是1时，清除之前lists获取的残留数据；当传入是2时，表示在执行上拉加载或者下拉刷新，不清除lists里的数据
		if(num==1){
			list.clear();
		}else if (num==2){

		}

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

					int total = jsonObject.getInt("total");
					if(total==0){
						//当num为1时，表示更换品种筛选获取数据，当没有数据时，才显示textView
						//当num不为1，即为2时，表示listView做上拉加载操作，若无数据时，也显示listView
						if(num==1){
							Message message=Message.obtain();
							message.what=1;
							handler.sendMessage(message);
						}else if(num==2){
							Message message=Message.obtain();
							message.what=3;
							handler.sendMessage(message);
						}
					}else{
						JSONArray result = jsonObject.getJSONArray("result");
						for (int i = 0; i < result.length(); i++) {
							JSONObject object = result.getJSONObject(i);
							AccountInfo info = new AccountInfo();
							info.setXSO_CementName(object.getString("XSO_CementName"));
							info.setXSO_SetDate(object.getString("XSO_SetDate"));
							info.setXSO_TotalPrice(object.getString("XSO_TotalPrice"));
							info.setXSO_Number(object.getInt("XSO_Number"));
							info.setXSO_CarCode(object.getString("XSO_CarCode"));

							list.add(info);

						}

						Message message=Message.obtain();
						message.what=2;
						message.obj=list;
						message.arg1=xsstatus;
						handler.sendMessage(message);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();


	}

}
