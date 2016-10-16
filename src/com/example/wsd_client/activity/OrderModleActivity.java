package com.example.wsd_client.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.adapter.OrderModleAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.customViews.CustomEnableLinearLayout;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.OrderModle;
import com.example.wsd_client.entity.OrderModleInfo;

/**
 * 订单模式activity，方便记忆下单使用
 * @author wsd_leiguoqiang
 */
public class OrderModleActivity extends BaseActivity implements OnClickListener{
	/**
	 * 订单模式数据集合
	 */
	private List<OrderModleInfo> list_orderModlerInfo = new ArrayList<OrderModleInfo>();
	/**
	 * 订单模式名字数组
	 */
	private List<String> list_orderModleName = new ArrayList<String>();
	/**
	 * listview订单模式列表
	 */
	@ViewInject(R.id.lv_order_modle_pager_orders)
	private ListView lv;
	/**
	 * 添加文本按钮
	 */
	@ViewInject(R.id.tv_order_modle_pager_add)
	private TextView tv_add;
	/**
	 * 第一层布局
	 */
	@ViewInject(R.id.ll_order_modle_pager_first)
	private LinearLayout ll_first;
	/**
	 * 第二层布局
	 */
	@ViewInject(R.id.ll_order_modle_pager_seconde)
	private LinearLayout ll_seconde;
	/**
	 * 第二层确定按钮
	 */
	@ViewInject(R.id.btn_product_details_pager_seconde_confirm)
	private Button btn_confirm;
	
	private Myapplication app;
	/**
	 * 用户名对象
	 */
	private ClientInfo clientInfo = null;

	@ViewInject(R.id.et_order_modle_pager_dingdan_type)
	private EditText et_ladeType;

	@ViewInject(R.id.et_order_modle_pager_buy_phone)
	private EditText et_buy_phone;
	/**
	 * 司机姓名
	 */
	@ViewInject(R.id.et_order_modle_pager_driver_name)
	private EditText et_driverName;
	/**
	 * 司机驾驶证号码
	 */
	@ViewInject(R.id.et_order_modle_pager_driver_number)
	private EditText et_driverNumber;
	/**
	 * 货车牌照
	 */
	@ViewInject(R.id.et_order_modle_pager_cart_number)
	private EditText et_cartNumber;
	/**
	 * 司机手机号码
	 */
	@ViewInject(R.id.et_order_modle_pager_dirver_phone)
	private EditText et_driverPhone;
	/**
	 * 订单模式名称
	 */
	@ViewInject(R.id.et_order_modle_pager_dingdan_name)
	private EditText et_dingdanName;
	/**
	 * 订单模式属性对象
	 */
	private OrderModleInfo orderModleInfo = null;
	/**
	 * 订单模式实体类
	 */
	private OrderModle orderModle;
	/**
	 * 适配器
	 */
	private OrderModleAdapter adapter;
	/**
	 * 无数据状态页面
	 */
	@ViewInject(R.id.ll_order_modle_pager_unlinked)
	private CustomEnableLinearLayout ll_linked;
	/**
	 * 无数据状态立即添加按钮
	 */
	@ViewInject(R.id.tv_order_modle_pager_linked_argin)
	private TextView tv_linked_argin;
	/**
	 * 数据下标值
	 */
	private int position;
	/**
	 * 标记变量，确认按钮是由那种情况触发
	 */
	private int flag_confirm;
	/**
	 * 添加操作
	 */
	public static final int ADD_ORDER = 1;
	/**
	 * 编辑操作
	 */
	public static final int EDITE_ORDER = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_modle_pager);
		x.view().inject(this);
		init();
		initListeners();
	}

	private void init() {
		app = (Myapplication) Myapplication.getContext();
		orderModle = app.getOrderModle();
		//显示订单模式数据
		list_orderModlerInfo = orderModle.readOrderModle().getList_orderModleInfo();
		for(OrderModleInfo orderModle:list_orderModlerInfo){
			list_orderModleName.add(orderModle.getOrderModleName());
		}
		adapter = new OrderModleAdapter(this, list_orderModleName);
		lv.setAdapter(adapter);
		//无数据状态
		displayUnlinked();
	}

	private void initListeners() {
		tv_add.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		tv_linked_argin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//添加按钮
		case R.id.tv_order_modle_pager_add:
			//显示添加编辑页面
			displayEditePager();
			flag_confirm = ADD_ORDER;
			break;
			//第二层确定按钮
		case R.id.btn_product_details_pager_seconde_confirm:
			switch (flag_confirm) {
			//添加操作
			case ADD_ORDER:
				//收集数据
				getorderModleInfo();
				//增加新订单模式数据
				orderModle.addOrderModleInfo(orderModleInfo);
				//进行本地化存储
				orderModle.saveOrderModle();
				//更新订单模式数据列表
				list_orderModleName.add(orderModleInfo.getOrderModleName());
				adapter.notifyDataSetChanged();
				//检查数据状态，是否显示无数据状态
				displayUnlinked();
				//自动切换编辑页面显示状态
				displayEditePager();
				break;
			//编辑操作
			case EDITE_ORDER:
				//收集数据
				getorderModleInfo();
				//先移除，在加入
				list_orderModlerInfo.remove(position);
				list_orderModlerInfo.add(position, orderModleInfo);
				//保存数据
				orderModle.saveOrderModle();
				//更新数据和列表
				list_orderModleName.remove(position);
				list_orderModleName.add(position, orderModleInfo.getOrderModleName());
				adapter.notifyDataSetChanged();
				//自动切换编辑页面显示状态
				displayEditePager();
				break;
			}
			break;
			//无数据状态立即添加按钮
		case R.id.tv_order_modle_pager_linked_argin:
			displayEditePager();
			flag_confirm = ADD_ORDER;
			break;
		}
	}
	/**
	 * 自定义方法，显示订单模式编辑页面
	 */
	public void displayEditePager() {
		//先判断是否用户登录成功
		clientInfo = app.getClientInfo().getResult().get(0);
		if(clientInfo!=null){
			if(ll_seconde.getVisibility()!=View.VISIBLE){
				//显示编辑区域
				ll_first.setVisibility(View.INVISIBLE);
				ll_seconde.setVisibility(View.VISIBLE);
				TranslateAnimation animation = new TranslateAnimation(ll_seconde.getWidth(), 0, 0, 0);
				animation.setDuration(1000);
				ll_seconde.startAnimation(animation);
			}else{
				//隐藏编辑区域
				AlphaAnimation alpha_animation = new AlphaAnimation(1f, 0f);
				alpha_animation.setDuration(1000);
				ll_seconde.startAnimation(alpha_animation);
				ll_seconde.setVisibility(View.INVISIBLE);
				AlphaAnimation alpha_animation_first = new AlphaAnimation(0f, 1f);
				alpha_animation_first.setDuration(1000);
				ll_first.startAnimation(alpha_animation_first);
				ll_first.setVisibility(View.VISIBLE);
			}
			
		}
	}
	/**
	 * 自定义方法，获取ordermodleinfo对象
	 */
	private void getorderModleInfo(){
		orderModleInfo = new OrderModleInfo();
		orderModleInfo.setOrderModleName(et_dingdanName.getText().toString());
		orderModleInfo.setCarcode(et_cartNumber.getText().toString());
		orderModleInfo.setCarid(et_driverNumber.getText().toString());
		orderModleInfo.setCarname(et_driverName.getText().toString());
		orderModleInfo.setCarphone(et_driverPhone.getText().toString());
		orderModleInfo.setLadetype(et_ladeType.getText().toString());
		orderModleInfo.setOrderphone(et_buy_phone.getText().toString());
		Myapplication.log("ordermodleactivity",orderModleInfo.toString());
	}
	/**
	 * 自定义方法，无数据状态显示页面
	 */
	private void displayUnlinked() {
		if(list_orderModleName.size()!=0){
			ll_linked.setVisibility(View.INVISIBLE);
			lv.setVisibility(View.VISIBLE);
		}else{
			ll_linked.setVisibility(View.VISIBLE);
			lv.setVisibility(View.INVISIBLE);
		}
	}

	public void setFlag_confirm(int flag_confirm) {
		this.flag_confirm = flag_confirm;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	@Override
	public void onBackPressed() {
		if(ll_seconde.getVisibility()==View.VISIBLE){
			displayEditePager();
		}else{
			super.onBackPressed();
		}
	}
}
