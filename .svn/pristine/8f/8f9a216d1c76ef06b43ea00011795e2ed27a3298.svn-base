package com.example.wsd_client.application;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.os.Process;
import android.util.Log;
/**
 * application组件
 * @author wsd_leiguoqiang
 */
public class Myapplication extends Application{
	/**
	 * app全局log方法控制变量
	 */
	private static boolean flag = true;
	/**
	 * list集合，封装activity
	 */
	private static List<Activity> list_activity = new ArrayList<Activity>();

	@Override
	public void onCreate() {
		super.onCreate();
		
	}
	
	/**
	 * 全局log方法，共开发过程中程序调试使用
	 * @param tag
	 * @param logcat
	 */
	public static void log(String tag,String logcat){
		if(flag==true){
			Log.i(tag, logcat);
		}
	}
	/**
	 * 用于整个app退出程序使用
	 * @param list 
	 */
	public static void finishActivity(List<Activity> list){
		for(Activity activity:list){
			activity.finish();
		}
		//结束app进程
		Process.killProcess(Process.myPid());
	}
	
	
}
