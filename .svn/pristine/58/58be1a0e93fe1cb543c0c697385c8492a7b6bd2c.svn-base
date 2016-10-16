package com.example.wsd_client.activity;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


import com.example.wsd_client.R;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CollectionProduct;
import com.example.wsd_client.entity.OrderModle;
import com.example.wsd_client.entity.YW_ClientInfo;
import com.example.wsd_client.urlUtils.UrlPath;
import com.example.wsd_client.util.SharedPreferenceUtils;
import com.google.gson.Gson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class LoginActivity extends Activity implements OnClickListener{

	private EditText etUsercode,etPassword,etCodePhoto;//验证码
	private RelativeLayout rl3;
	private ImageView ivCode;
	private CheckBox cbRem,cbAutomatic;//自动登陆
	private Button btnLogin;
	private TextView tvHelp,tvNew;
	private String usercode;
	private String userpwd;
	private String code;
	private String urlParma;
	//用于记录用户输入次数，当达到三次输入错误时，跳出验证码
	private int i=0;

	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			//用户输入账号密码正确时，传入的值为1
			case 1:
				login(usercode,userpwd);
				break;

				//用户输入账号密码不正确时，传入的值为2
			case 2:
				Myapplication.toast("登录失败，请检查账户和密码是否正确");
				etUsercode.setText("");
				etPassword.setText("");
				etCodePhoto.setText("");
				//聚焦，光标跳转到用户输入的地方
				etUsercode.requestFocus();
				i++;
				if(i>2){
					rl3.setVisibility(View.VISIBLE);
				}
				break;

				//网络连接不可用时，传入的值为3
			case 3:
				Myapplication.toast("请检查网络是否连接！");
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initView();

		setCheckState();

		setListener();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		etUsercode=(EditText) findViewById(R.id.et_login_usercode);
		etPassword=(EditText) findViewById(R.id.et_login_userpassword);
		etCodePhoto=(EditText) findViewById(R.id.et_indentification_code);
		ivCode=(ImageView) findViewById(R.id.iv_code);
		cbRem=(CheckBox) findViewById(R.id.cb_login_remember);
		cbAutomatic=(CheckBox) findViewById(R.id.cb_login_automatic);
		btnLogin=(Button) findViewById(R.id.btn_login);
		tvHelp=(TextView) findViewById(R.id.tv_login_help);
		tvNew=(TextView) findViewById(R.id.tv_login_new);
		rl3=(RelativeLayout) findViewById(R.id.rl3);
	}


	/**
	 * 通过偏好设置获取记录按键是否是被勾选状态
	 * 自动登录时，计算上次手动登录记录的时间毫秒值，换算出当7天内可自动登录，7天后需手动登录
	 */
	private void setCheckState() {
		boolean isCheckRem = SharedPreferenceUtils.getCheckedBoolean("cbRem", LoginActivity.this);
		boolean isCheckAuto = SharedPreferenceUtils.getCheckedBoolean("cbAutomatic", LoginActivity.this);
		Map<String, String> info = SharedPreferenceUtils.getInfo(LoginActivity.this);
		usercode=info.get("usercode");
		userpwd=info.get("userpwd");
		if(usercode!=null&&userpwd!=null){
			if(isCheckRem){
				cbRem.setChecked(true);
				etUsercode.setText(usercode);
				etPassword.setText(userpwd);
				if(isCheckAuto){
					//获取当前的时间毫秒值
					long currentMillies=System.currentTimeMillis();
					//获取上次存储的时间毫秒值
					long lastMillies=SharedPreferenceUtils.getCurrentMillies(this);
					//计算出上次自动登录和当前的时间差是否为7天，7天之内自动登录，7天之后则要重新输入账号密码
					//7天的时间毫秒值
					long i=7*24*60*60*1000;
					//时间差
					long timeDifference=currentMillies-lastMillies;
					if(timeDifference<i){
						cbAutomatic.setChecked(true);
						uploadInfo(usercode,userpwd);
					}else{
						cbAutomatic.setChecked(false);
						cbRem.setChecked(false);
						etPassword.setText("");
						SharedPreferenceUtils.saveInfo(usercode, "", LoginActivity.this);
						SharedPreferenceUtils.setCheckedBoolean("cbAutomatic", false, LoginActivity.this);
						SharedPreferenceUtils.setCheckedBoolean("cbRem", false, LoginActivity.this);
					}
				}
			}
		}else{
			cbAutomatic.setChecked(false);
			cbRem.setChecked(false);
			SharedPreferenceUtils.setCheckedBoolean("cbRem", false, this);
			SharedPreferenceUtils.setCheckedBoolean("cbAutomatic", false, this);
		}
	}


	private void setListener() {
		ivCode.setOnClickListener(LoginActivity.this);
		cbRem.setOnClickListener(LoginActivity.this);
		cbAutomatic.setOnClickListener(LoginActivity.this);
		btnLogin.setOnClickListener(LoginActivity.this);
		tvHelp.setOnClickListener(LoginActivity.this);
		tvNew.setOnClickListener(LoginActivity.this);

	}

	@Override
	public void onClick(View v) {

		initData();

		switch (v.getId()) {
		case R.id.iv_code:

			break;

		case R.id.btn_login:
			//手动登录时，若记住密码和自动登录按钮被勾选，则记录下当前的登录时间毫秒值，并覆盖之前的数据
			if(cbRem.isChecked()&&cbAutomatic.isChecked()){
				long currentMillies=System.currentTimeMillis();
				SharedPreferenceUtils.setCurrentMillies(currentMillies, LoginActivity.this);
			}
			uploadInfo(usercode,userpwd);

			break;

		case R.id.tv_login_help:

			break;

		case R.id.tv_login_new:

			break;

		}

	}


	/**
	 * 初始化数据
	 */
	private void initData() {
		//获取用户输入的用户代码、密码、验证码
		usercode = etUsercode.getText().toString().trim();
		userpwd = etPassword.getText().toString();
		code = etCodePhoto.getText().toString();
	}

	/**
	 * 
	 * 将用户输入的用户名和密码传入服务器，并获取响应码
	 * @param urlParma  传入服务器账户参数
	 */
	private void uploadInfo( String usercode,String userpwd) {

		urlParma=UrlPath.USER_INFO+"?usercode="+usercode+"&userpwd="+userpwd;
		new Thread(new Runnable() {

			@Override
			public void run() {
				StringBuffer sb=new StringBuffer();

				try {
					URL url = new URL(urlParma);
					HttpURLConnection conn=(HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					conn.connect();

					//根据返回值获取当前网络状态是否正常
					if(conn.getResponseCode()!=HttpURLConnection.HTTP_OK){
						Message message=Message.obtain();
						message.what=3;
						handler.sendMessage(message);
					}

					InputStream is=conn.getInputStream();
					InputStreamReader isr=new InputStreamReader(is);
					BufferedReader reader=new BufferedReader(isr);
					String line=reader.readLine();
					while(line!=null){
						sb.append(line);
						Myapplication.log("-----------", "sb:"+sb.toString());
						line=reader.readLine();
					}

					//获取total的值
					Gson gson = new Gson();
					YW_ClientInfo json = gson.fromJson(sb.toString(), YW_ClientInfo.class);

					//将获取到的用户信息封装到Myapplication中
					Myapplication app=(Myapplication) Myapplication.getContext();
					app.setClientInfo(json);

					int total = json.getTotal();
					Myapplication.log("-----------", "totla:"+total);
					if(total>0){
						//根据total的值，若大于0，等表示账号密码正确，登录成功；若等于0，则登录失败
						Message message=Message.obtain();
						message.what=1;
						handler.sendMessage(message);
					}else{
						Message message=Message.obtain();
						message.what=2;
						handler.sendMessage(message);
					}

					is.close();
					isr.close();
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	/**
	 * 登录成功时，执行的操作
	 */
	private void login(String ucode,String upwd) {
		if(cbRem.isChecked()){
			SharedPreferenceUtils.setCheckedBoolean("cbRem", true, this);
			SharedPreferenceUtils.saveInfo(ucode, upwd, this);

		}else{
			SharedPreferenceUtils.setCheckedBoolean("cbRem", false, this);
		}

		if(cbAutomatic.isChecked()){
			SharedPreferenceUtils.setCheckedBoolean("cbAutomatic", true, this);
		}else{
			SharedPreferenceUtils.setCheckedBoolean("cbAutomatic", false, this);
		}

		if(rl3.getVisibility()==View.VISIBLE){
			if(code.equals("7364")){
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}else{
				Myapplication.toast("验证码输入错误，请重新输入");
				etCodePhoto.setText("");
			}
		}else{
			//获取购物车数据
			Myapplication app = (Myapplication) Myapplication.getContext();
			//加载购物车数据
			Cart cart = app.getCart();
			cart = cart.readCartData();
			app.setCart(cart);
			if(cart.getList_cartItem().size()!=0){
				app.setNew_cart_data(true);
			}
			//加载订单模式数据
			OrderModle orderModle = app.getOrderModle();
			orderModle = orderModle.readOrderModle();
			app.setOrderModle(orderModle);
			//加载收藏产品对象
			CollectionProduct collectionProduct = app.getCollectionProduct();
			collectionProduct = collectionProduct.readCollectionnProduct();
			app.setCollectionProduct(collectionProduct);

			//跳转到mainactivity
			Intent intent = new Intent(LoginActivity.this,MainActivity.class);
			intent.putExtra("TAG", 0);
			startActivity(intent);
			finish();
		}
	}


}
