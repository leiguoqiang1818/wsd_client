package com.example.wsd_client.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.CollectionActivity;
import com.example.wsd_client.activity.LoginActivity;
import com.example.wsd_client.activity.OrderModleActivity;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.constant.Constant;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.YW_ClientInfo;
import com.example.wsd_client.presenter.impl.MoreFragmentPresenterImpl;
import com.example.wsd_client.util.SharedPreferenceUtils;
/**
 * moreFragment页面veiw层实现类
 */
public class MoreFragment extends Fragment implements IMoreFragment,OnClickListener{
	private View view;
	/**
	 * 第一层容器
	 */
	@ViewInject(R.id.ll_fragment_more_first)
	private LinearLayout ll_first_container;
	/**
	 * 第二层容器
	 */
	@ViewInject(R.id.ll_fragment_more_seconde)
	private LinearLayout ll_seconde_container;
	/**
	 * 用户头像
	 */
	@ViewInject(R.id.iv_fragment_more_userhead)
	private com.example.wsd_client.util.CircleImageView iv_userHead;
	/**
	 * 登录按钮
	 */
	@ViewInject(R.id.tv_fragment_more_login)
	private TextView tv_login;
	/**
	 * 用户显示按钮
	 */
	@ViewInject(R.id.tv_fragment_more_username)
	private TextView tv_username;
	/**
	 * 确认修改按钮
	 */
	@ViewInject(R.id.btn_fragment_more_confirm)
	private Button btn_confirm;

	private MoreFragmentPresenterImpl presenter;
	/**
	 * map集合，进行用户修改数据的封装
	 */
	private Map<String, String> userInfo = new HashMap<String, String>();
	/**
	 * 用户登录代码
	 */
	@ViewInject(R.id.tv_fragment_more_client_code)
	private TextView tv_client_code;
	/**
	 * 用户名称
	 */
	@ViewInject(R.id.et_fragment_more_clientname)
	private EditText et_client_name;
	/**
	 * 销售区域
	 */
	@ViewInject(R.id.et_fragment_more_areacode)
	private EditText et_areacode;
	/**
	 * 联系人
	 */
	@ViewInject(R.id.et_fragment_more_linkman)
	private EditText et_linkman;
	/**
	 * 电话号码
	 */
	@ViewInject(R.id.et_fragment_more_linkphone)
	private EditText et_linkphone;
	/**
	 * 用户密码
	 */
	@ViewInject(R.id.et_fragment_more_password)
	private EditText et_password;
	/**
	 * 返回键
	 */
	@ViewInject(R.id.iv_more_fragment_seconde_back)
	private ImageView iv_back;
	/**
	 * 退出程序按钮
	 */
	@ViewInject(R.id.tv_more_fragment_pager_out)
	private TextView tv_out;
	/**
	 * 我的订单模式
	 */
	@ViewInject(R.id.tv_more_fragment_pager_my_order_modle)
	private TextView tv_order_modle;
	private Myapplication app;
	/**
	 * 我的收藏
	 */
	@ViewInject(R.id.tv_more_fragment_pager_my_collect)
	private TextView tv_my_collection;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_more, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		x.view().inject(this, view);

		init();

		//获取用户代码
		YW_ClientInfo clientInfo = app.getClientInfo();
		List<ClientInfo> result = clientInfo.getResult();
		String clientcode = result.get(0).getYWC_ClientCode();
		tv_client_code.setText(clientcode);

		initListeners();
	}

	private void init() {
		app = ((Myapplication)(Myapplication.getContext()));
		//显示用户名或登录按钮
		String userName = app.getClientInfo().getResult().get(0).getYWC_ClientName();
		if(!TextUtils.isEmpty(userName)){
			//判断用户是否登录，登录和用户显示切换
			tv_username.setVisibility(View.VISIBLE);
			tv_login.setVisibility(View.GONE);
			tv_username.setText(userName);
			iv_userHead.setClickable(true);
		}else{
			//判断用户是否登录，登录和用户显示切换
			tv_username.setVisibility(View.GONE);
			tv_login.setVisibility(View.VISIBLE);
			iv_userHead.setClickable(false);
		}
	}

	private void initListeners() {
		iv_userHead.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		tv_out.setOnClickListener(this);
		tv_order_modle.setOnClickListener(this);
		tv_my_collection.setOnClickListener(this);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//登录按钮
		case R.id.tv_fragment_more_login:
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			getActivity().startActivityForResult(intent, Constant.REQUESTCODE_MORE_FRAGMENT_TO_LOGIN_ACTIVITY);
			break;
			//头像按钮
		case R.id.iv_fragment_more_userhead:
			//页面进行切换
			TranslateAnimation animation_one = new TranslateAnimation(0, -ll_first_container.getWidth(), 0, 0);
			animation_one.setDuration(1000);
			ll_first_container.startAnimation(animation_one);
			ll_first_container.setVisibility(View.INVISIBLE);
			ll_seconde_container.setVisibility(View.VISIBLE);
			TranslateAnimation animation_two = new TranslateAnimation(ll_seconde_container.getWidth(), 0, 0, 0);
			animation_two.setDuration(1000);
			ll_seconde_container.startAnimation(animation_two);
			break;
		case R.id.btn_fragment_more_confirm:
			//当用户已经登录的情况之下才执行此操作
			if(!TextUtils.isEmpty(tv_username.getText().toString())){
				//得到所有需要更改的信息并封装到map集合中
				//将客户id添加到集合中,通过服务器返回的响应体获得
				int clientId = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0).getYWC_ClientID();
				userInfo.put("clientid", clientId+"");
				userInfo.put("clientcode", tv_client_code.getText().toString());
				userInfo.put("clientname", et_client_name.getText().toString());
				userInfo.put("linkman", et_linkman.getText().toString());
				userInfo.put("linkphone", et_linkphone.getText().toString());
				userInfo.put("password", et_password.getText().toString());
				userInfo.put("areacode", et_areacode.getText().toString());
				//调用presenter方法进行用户数据修改
				presenter = new MoreFragmentPresenterImpl(this, userInfo);
				presenter.updateUserInfo();
			}
			break;
			//返回键
		case R.id.iv_more_fragment_seconde_back:
			ll_first_container.setVisibility(View.VISIBLE);
			TranslateAnimation animation_four = new TranslateAnimation(-ll_first_container.getWidth(), 0, 0, 0);
			animation_four.setDuration(1000);
			ll_first_container.startAnimation(animation_four);

			TranslateAnimation animation_three = new TranslateAnimation(0, ll_seconde_container.getWidth(), 0, 0);
			animation_three.setDuration(1000);
			ll_seconde_container.startAnimation(animation_three);
			ll_seconde_container.setVisibility(View.INVISIBLE);

			break;
			//退出按钮
		case R.id.tv_more_fragment_pager_out:
			//弹出对话框，退出当前账号和关闭程序
			final AlertDialog dialog =  new AlertDialog.Builder(getActivity()).create();
			View view = View.inflate(getActivity(), R.layout.dialog_modle_out_order, null);
			LinearLayout ll_close_current_user = (LinearLayout) view.findViewById(R.id.ll_close_dialog_close_current_user);
			LinearLayout ll_close_app = (LinearLayout) view.findViewById(R.id.ll_close_dialog_close_app);
			dialog.setView(view, 0, 0, 0, 0);
			dialog.show();
			//设置dialog大小
			LayoutParams params = dialog.getWindow().getAttributes();
			params.height = (int) (ll_first_container.getHeight()*0.5);
			params.width = (int) (ll_first_container.getWidth()*0.8);
			dialog.getWindow().setAttributes(params);
			dialog.setCanceledOnTouchOutside(true);

			ll_close_current_user.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					//用户信息清除
					app.getClientInfo().deleteClientInfo();
					//跳转到登录界面
					SharedPreferenceUtils.setCheckedBoolean("cbAutomatic", false, getActivity());
					Intent intent = new Intent(getActivity(), LoginActivity.class);
					intent.putExtra("fromMoreFragment", 1);
					startActivity(intent);

				}
			});
			ll_close_app.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					//关闭应用程序
					Myapplication.finishActivity(Myapplication.getList());
				}
			});
			break;
			//订单模式
		case R.id.tv_more_fragment_pager_my_order_modle:
			Intent in = new Intent(getActivity(), OrderModleActivity.class);
			startActivity(in);
			break;
			//我的收藏
		case R.id.tv_more_fragment_pager_my_collect:
			Intent intent_collection = new Intent(getActivity(), CollectionActivity.class);
			startActivity(intent_collection);
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//正常显示用户名信息
		if(requestCode==Constant.REQUESTCODE_MORE_FRAGMENT_TO_LOGIN_ACTIVITY
				&&resultCode==getActivity().RESULT_OK){
			iv_userHead.setClickable(true);
			String userName = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0).getYWC_ClientName();
			tv_username.setText(userName);
			tv_username.setVisibility(View.VISIBLE);
			tv_login.setVisibility(View.GONE);
		}
	}

	//更新界面使用
	@Override
	public void updateView(ClientInfo clientInfo) {
		//显示第一层，隐藏第二层
		ll_first_container.setVisibility(View.VISIBLE);
		ll_seconde_container.setVisibility(View.GONE);
		//重新显示用户名信息，用户信息实体类需要更新属性值,在modle层进行，用户数据修改成功之后进行更新属性值
		tv_username.setText(clientInfo.getYWC_ClientName());
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
