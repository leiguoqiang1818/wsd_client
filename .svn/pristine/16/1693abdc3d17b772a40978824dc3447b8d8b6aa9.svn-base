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
import com.example.wsd_client.entity.NewsResult;
import com.example.wsd_client.urlUtils.UrlPath;

/**
 * 消息中心网络获取消息内容工具
 */
public class XiaoxiUtil {

	/**
	 * 通过构造方法，传入handler，将获取的响应码传出给相应的fragment
	 */
	private Handler handler;

	/**
	 * 封装消息的集合
	 */
	private List<NewsResult> lists=new ArrayList<NewsResult>();

	public XiaoxiUtil(Handler handler) {
		super();
		this.handler = handler;
	}


	/**
	 * 获取服务器消息中心的消息数据
	 * @param map  封装的消息中心请求数据参数
	 * @param i  当传入是1时，表示进行消息类别筛选
	 * 			   当传入是2时，表示进行listView的刷新
	 */
	public  void getNews(Map<String, Integer> map,int i){
		int pageNum = map.get("pageNum");
		int pageSize = map.get("pageSize");
		int clientid = map.get("clientid");
		int status = map.get("status");

		String urlParam=UrlPath.NEWS_CENTER+"?pageNum="+pageNum+"&pageSize="
				+pageSize+"&clientid="+clientid+"&status="+status;

		getUrlResult(urlParam,i);
	}

	/**
	 * 根据传入的网址，获取消息中心，然后通过handler传递出去
	 * @param urlParam 查询用户消息的网址链接
	 */
	public  void getUrlResult(final String urlParam,final int num){
		//当传入是1时，清除之前lists获取的残留数据；当传入是2时，表示在执行上拉加载或者下拉刷新，不清除lists里的数据
		if(num==1){
			lists.clear();
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
					//当total值为0时，表示没有消息；当total为大于0时，此时有消息
					int total=jsonObject.getInt("total");
					if(total==0){
						//当num为1时，表示更换消息类型筛选获取数据，当没有数据时，才显示textView
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
						JSONArray result=jsonObject.getJSONArray("result");
						for(int i=0;i<result.length();i++){
							NewsResult newsResult=new NewsResult();

							JSONObject object=result.getJSONObject(i);
							int messageID=object.getInt("MessageID");
							String messageInfo=object.getString("MessageInfo");
							String inputDate = object.getString("InputDate");

							newsResult.setMessageID(messageID);
							newsResult.setMessageInfo(messageInfo);
							newsResult.setInputDate(inputDate);

							lists.add(newsResult);

						}
						Message message=Message.obtain();
						message.what=2;
						message.obj=lists;
						handler.sendMessage(message);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
