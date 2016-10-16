package com.example.wsd_client.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtils {

	/**
	 * 保存用户代码和密码到偏好设置
	 * @param usercode  用户代码
	 * @param userpwd   用户密码
	 * @param context   上下文对象
	 * @return  用户信息集合
	 */
	public static boolean saveInfo(String usercode,String userpwd,Context context){
		try{
			SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
			Editor edit = sp.edit();
			edit.putString("usercode", usercode);
			edit.putString("userpwd", userpwd);
			edit.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取偏好设置中存储的用户代码和密码
	 * @param context 上下文对象
	 * @return map集合封装用户信息
	 */
	public static Map<String, String> getInfo(Context context){
		try{
			SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
			String usercode = sp.getString("usercode", null);
			String userpwd = sp.getString("userpwd", null);
			Map<String, String> map=new HashMap<String, String>();
			map.put("usercode", usercode);
			map.put("userpwd", userpwd);
			return map;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取记录之前用户的按钮点击状态
	 * @param checkName 被点击的按钮的名称
	 * @param context 上下文对象
	 * @return  是否记录被点击状态
	 */
	public static boolean getCheckedBoolean(String checkName,Context context) {
		SharedPreferences sp = context.getSharedPreferences("checkState", Context.MODE_PRIVATE);
		boolean isCheck = sp.getBoolean(checkName, false);
		return isCheck;
	}

	/**
	 * 传入按键被点击状态
	 * @param checkName  被点击的按键名称
	 * @param value	    被点击的状态，为boolean类型
	 * @param context  上下文对象
	 */
	public static void setCheckedBoolean(String checkName,Boolean value ,Context context){
		SharedPreferences sp = context.getSharedPreferences("checkState", Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putBoolean(checkName, value);
		edit.commit();
		
	}
	
	/**
	 * 记录下当前登录时的时间毫秒值
	 * @param i  当前勾选自动登录时，距离1970年1月1日0：0：0的时间毫秒值
	 */
	public static void setCurrentMillies(long i,Context context){
		SharedPreferences sp=context.getSharedPreferences("time", Context.MODE_PRIVATE);
		Editor editor=sp.edit();
		editor.putLong("timeMillies", i);
		editor.commit();
		
	}
	
	/**
	 * 获取上次登录时的时间毫秒值
	 * @param context  上下文对象
	 * @return  上次登录时存储的时间距离1970年1月1日0：0：0的毫秒值
	 */
	public static long getCurrentMillies(Context context ){
		SharedPreferences sp=context.getSharedPreferences("time", Context.MODE_PRIVATE);
		long i=sp.getLong("timeMillies", 0);
		return i;
	}
}
