package com.example.wsd_client.fragment.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.MainActivity;
import com.example.wsd_client.adapter.XiaoxiAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.NewsResult;
import com.example.wsd_client.entity.YW_ClientInfo;
import com.example.wsd_client.util.CircleImageView;
import com.example.wsd_client.util.RefreshListView;
import com.example.wsd_client.util.RefreshListView.OnRefreshListener;
import com.example.wsd_client.util.XiaoxiUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class XiaoxiFragment extends Fragment implements OnClickListener,OnCheckedChangeListener{
	private TextView tvTitle;
	private View view;
	private CircleImageView civ;
	private ImageButton ibBack;
	private ImageButton ibCart;
	private RadioGroup rgXiaoxiChoose;
	private RefreshListView lvNews;
	private TextView tvNoNews;
	private int pageNum=0;


	//获取XiaoxiUtil工具中获取的服务器消息的集合
	private List<NewsResult> lists;
	//将XiaoxiUtil工具中获取的服务器消息的倒叙封装到list集合中，并传递给适配器，用于显示最新消息在第一个位置
	private List<NewsResult> list=new ArrayList<NewsResult>();
	
	
	//客户id
	private int clientid;
	/**
	 * 获取XiaoxiUtil工具网络请求的消息数据
	 * 当参数为1：没有消息
	 * 当参数为2：有消息，获取消息的list集合
	 */
	private Handler handler=new Handler(){

		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				tvNoNews.setVisibility(View.VISIBLE);
				lvNews.setVisibility(View.INVISIBLE);
				break;

			case 2:
				tvNoNews.setVisibility(View.INVISIBLE);
				lvNews.setVisibility(View.VISIBLE);
				lists = (List<NewsResult>) msg.obj;
				Myapplication.log("获取消息lists的长度：", lists.size()+"");

				setAdapter();

				//加载完成，隐藏脚布局
				lvNews.onRefreshComplete();

				break;

			case 3:
				Myapplication.log("消息中心liseView没数据时：", "------1---------");
				//上拉加载时没有数据，直接隐藏脚布局
				lvNews.onRefreshComplete();
				Myapplication.toast("没有更多数据啦！");
				Myapplication.log("消息中心liseView没数据时：", "------2---------");
			default:
				break;
			}
		};
	};


	private XiaoxiUtil xiaoxiUtil=new XiaoxiUtil(handler);
	private Map<String, Integer> map=new HashMap<String, Integer>();
	//适配器
	private XiaoxiAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_xiaoxi, null);

		initView();

		setListener();

		return view;
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvTitle=(TextView) view.findViewById(R.id.tv_title);
		civ=(CircleImageView) view.findViewById(R.id.iv_contact);
		ibBack=(ImageButton) view.findViewById(R.id.ib_title_back);
		ibCart=(ImageButton) view.findViewById(R.id.ib_title_cart);
		rgXiaoxiChoose=(RadioGroup) view.findViewById(R.id.rg_xiaoxi_choose);
		lvNews=(RefreshListView) view.findViewById(R.id.lv_xiaoxi);
		tvNoNews=(TextView) view.findViewById(R.id.tv_no_news);

		//获取用户信息
		Myapplication app=(Myapplication) Myapplication.getContext();
		YW_ClientInfo clientInfo = app.getClientInfo();
		List<ClientInfo> result = clientInfo.getResult();
		clientid = result.get(0).getYWC_ClientID();

		tvTitle.setText("消息中心");
		civ.setVisibility(View.INVISIBLE);
		ibBack.setVisibility(View.VISIBLE);
		ibCart.setVisibility(View.INVISIBLE);

		//首次加载界面的时候，显示系统消息
		map.put("pageNum", pageNum);
		map.put("pageSize", 10);
		map.put("clientid", clientid);
		map.put("status", 1);
		xiaoxiUtil.getNews(map,1);
	}

	/**
	 * 配置监听
	 */
	private void setListener() {
		ibBack.setOnClickListener(this);
		rgXiaoxiChoose.setOnCheckedChangeListener(this);
		lvNews.setRefreshListener(new OnRefreshListener() {

			@Override
			public void onLoadMore() {
				pageNum++;
				map.put("pageNum", pageNum);
				xiaoxiUtil.getNews(map,2);
			}
		});
	}

	/**
	 * 配置适配器
	 */
	private void setAdapter() {
		list.clear();
		Myapplication.log("消息中心setAdapter："," ----------------------");
		//将传入的lists集合的数据进行倒叙封装，按照时间的近远规则排序
		for (int i = lists.size()-1; i>=0; i--) {
			list.add(lists.get(i));
		}
		Myapplication.log("消息中心setAdapter：",list.size()+" ----------------------");
		
		adapter = new XiaoxiAdapter(getActivity(), list);
		
		lvNews.setAdapter(adapter);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_title_back:
			Intent intent = new Intent (getActivity(),MainActivity.class);
			startActivity(intent);
			//销毁当前碎片所在的Activity
			getActivity().finish();
			break;

		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		//清空之前获取的list集合里面的数据，更新adapter，这样就能清空之前listView中显示的内容，从而显示新的内容
		lists.clear();
		adapter.notifyDataSetChanged();

		switch (checkedId) {
		case R.id.rb_xiaoxi_system:
			map.put("pageNum", 0);
			map.put("pageSize", 10);
			map.put("clientid", clientid);
			map.put("status", 1);
			xiaoxiUtil.getNews(map,1);
			break;

		case R.id.rb_xiaoxi_order:
			map.put("pageNum", 0);
			map.put("pageSize", 10);
			map.put("clientid", clientid);
			map.put("status", 2);
			xiaoxiUtil.getNews(map,1);
			break;

		case R.id.rb_xiaoxi_amount:
			map.put("pageNum", 0);
			map.put("pageSize", 10);
			map.put("clientid", clientid);
			map.put("status", 3);
			xiaoxiUtil.getNews(map,1);
			break;

		case R.id.rb_xiaoxi_acount:
			map.put("pageNum", 0);
			map.put("pageSize", 10);
			map.put("clientid", clientid);
			map.put("status", 4);
			xiaoxiUtil.getNews(map,1);
			break;
		}

	}
}
