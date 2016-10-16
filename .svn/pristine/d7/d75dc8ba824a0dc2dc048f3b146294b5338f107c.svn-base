package com.example.wsd_client.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.wsd_client.R;
import com.example.wsd_client.fragment.BaobiaoFragment;
import com.example.wsd_client.fragment.DingdanFragment;
import com.example.wsd_client.fragment.impl.KuaisuFragment;
import com.example.wsd_client.fragment.impl.LiebiaoFragment;
import com.example.wsd_client.fragment.impl.XiaoxiFragment;
import com.example.wsd_client.fragment.impl.ZhangdanFragment;

public class HomeActivity extends FragmentActivity {

	private FragmentManager manager;
	private FragmentTransaction transaction;
	private LiebiaoFragment liebiaoFragment = new LiebiaoFragment();
	private KuaisuFragment kuaisuFragment = new KuaisuFragment();
	private BaobiaoFragment baobiaoFragment = new BaobiaoFragment();
	private DingdanFragment dingdanFragment = new DingdanFragment();
	private ZhangdanFragment zhangdanFragment = new ZhangdanFragment();
	private XiaoxiFragment xiaoxiFragment = new XiaoxiFragment();
	private ArrayList<Fragment> lists = new ArrayList<Fragment>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		//封装碎片
		lists.add(liebiaoFragment);
		lists.add(kuaisuFragment);
		lists.add(baobiaoFragment);
		lists.add(dingdanFragment);
		lists.add(zhangdanFragment);
		lists.add(xiaoxiFragment);

		//根据Intent传入的参数设置当前显示的碎片页面
		manager = getSupportFragmentManager();
		Intent intent = getIntent();
		int i = intent.getIntExtra("TAG", 0);
		
		
		transaction=manager.beginTransaction();
		transaction.replace(R.id.fl_home_container, lists.get(i));
		transaction.commit();
	}
}
