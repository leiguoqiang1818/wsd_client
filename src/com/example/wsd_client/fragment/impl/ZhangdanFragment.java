package com.example.wsd_client.fragment.impl;

import java.util.List;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.MainActivity;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.YW_ClientInfo;
import com.example.wsd_client.util.CircleImageView;
import com.example.wsd_client.util.ZhangdanAmountUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class ZhangdanFragment extends Fragment implements OnClickListener{
	private TextView tvTitle;
	private View view;
	private CircleImageView civ;
	private ImageButton ibBack;
	private ImageButton ibCart;
	//tvClientName:用户名    tvAmount：账户余额
	private TextView tvClientName,tvAmount;
	//ZhangdanAmountUtil传回服务器中获取的账户余额信息
	private String str;

	/**
	 * 获取ZhangdanAmountUtil中，服务器传回来的数据,即为账户余额
	 */
	private Handler handler = new Handler(){

		public void handleMessage(android.os.Message msg) {
			Myapplication.log("账户余额:", msg+"");
			str = (String) msg.obj;
			Myapplication.log("账户余额str的值", str);
			//设置用户账户余额显示
			tvAmount.setText("￥"+str+"元");
		};
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_zhangdan, null);
		
		initView();
		
		initData();
		
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
		tvClientName=(TextView) view.findViewById(R.id.tv_zhangdan_clientName);
		tvAmount=(TextView) view.findViewById(R.id.tv_zhangdan_amount);
		
		tvTitle.setText("我的资金");
		civ.setVisibility(View.INVISIBLE);
		ibBack.setVisibility(View.VISIBLE);
		ibCart.setVisibility(View.INVISIBLE);
	}

	/**
	 * 初始化数据，获取用户的个人信息及账户余额信息
	 */
	private void initData() {
		//获取用户信息，并提取出clientid：客户id  clientName:用户名 显示
		Myapplication app=(Myapplication) Myapplication.getContext();
		YW_ClientInfo clientInfo = app.getClientInfo();
		List<ClientInfo> result = clientInfo.getResult();
		int clientid = result.get(0).getYWC_ClientID();
		String clientName = result.get(0).getYWC_ClientName();
		
		ZhangdanAmountUtil util=new ZhangdanAmountUtil(handler);
		util.getAmount(clientid);
		
		tvClientName.setText(clientName);
	}

	/**
	 * 配置监听
	 */
	private void setListener() {
		ibBack.setOnClickListener(this);
		
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
}
