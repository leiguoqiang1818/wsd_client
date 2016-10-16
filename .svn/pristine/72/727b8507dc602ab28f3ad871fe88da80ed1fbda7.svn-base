package com.example.wsd_client.activity;

import java.util.List;

import com.example.wsd_client.application.Myapplication;

import android.app.Activity;
import android.os.Bundle;
/**
 * 自定义activity，用于添加一些公共方法和功能
 * @author wsd_leiguoqiang
 */
public class BaseActivity extends Activity{
	private List<Activity> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list = Myapplication.getList();
		list.add(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		list.remove(this);
	}
}
