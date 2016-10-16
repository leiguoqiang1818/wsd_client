package com.example.wsd_client.fragment.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.HomeActivity;
import com.example.wsd_client.activity.MainActivity;
import com.example.wsd_client.activity.ProductDetailsPagerActivityImpl;
import com.example.wsd_client.adapter.CartAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.customViews.CustomEnableLinearLayout;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.OrderModle;
import com.example.wsd_client.entity.OrderModleInfo;
import com.example.wsd_client.fragment.IUploadCartDataView;
import com.example.wsd_client.presenter.impl.BuyPresenterImpl;
import com.example.wsd_client.presenter.impl.UploadCartDataPresenterImpl;
/**
 * view层实现类，服务于购车页面数据的呈现
 * @author wsd_leiguoqiang
 */
public class UploadCartDataViewImpl extends Fragment implements IUploadCartDataView ,OnClickListener , OnItemClickListener{
	/**
	 * 购物项数据集合
	 */
	private List<CartItem> list_cartItem;
	/**
	 * 购物车结算集合，用于总价的计算
	 */
	private List<CartItem> list_total_cartItem;

	/**
	 * listview,呈现购物项数据
	 */
	@ViewInject(R.id.lv_fragment_cart_pager_cartitems)
	private ListView lv_cartItem;
	/**
	 * view对象
	 */
	private View view;
	/** 
	 * presenter对象
	 */
	private UploadCartDataPresenterImpl presenter;
	/**
	 * 购物车服务器接口
	 */
	private String path;
	/**
	 * 编辑文本按钮
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_edite_button)
	private TextView tv_edite;
	/**
	 * 全选文本按钮
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_seclete_all)
	private TextView tv_select_all;
	/**
	 * 标记变量，服务于全选文本按钮
	 */
	private boolean flag_select_all = false;
	/**
	 * 购物车适配器
	 */
	private CartAdapter adapter;
	/**
	 * 总价格文本
	 */
	private TextView tv_total_price;
	/**
	 * 结算购物项的数量
	 */
	private TextView tv_total_cartItem_count;
	/**
	 * 即将进行购买的商品id编号集合
	 */
	private List<String> list_productIds = new ArrayList<String>();
	/**
	 * 结算文本按钮
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_total_button)
	private TextView tv_buy;
	/**
	 * 购买操作presenter对象
	 */
	private BuyPresenterImpl buyPresenter;
	/**
	 * 购买参数集合
	 */
	private List<Map<String, String>> list_buyParams = new ArrayList<Map<String,String>>();
	/**
	 * 购物车对象
	 */
	private Cart cart;
	/**
	 * application对象
	 */
	private Myapplication app;
	/**
	 * 无网络连接时候显示布局
	 */
	@ViewInject(R.id.ll_fragment_cart_pager_unlinked)
	private CustomEnableLinearLayout ll_unlinked;
	/**
	 * 重新加载文本按钮
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_linked_argin)
	private TextView tv_linked_argin;
	/**
	 * 购物车再次呈现标记变量
	 */
	private boolean flag_data_argin = false;
	/**
	 * 司机名称
	 */
	@ViewInject(R.id.et_fragment_cart_pager_driver_name)
	private EditText et_driver_name;
	/**
	 * 司机驾驶证号码
	 */
	@ViewInject(R.id.et_fragment_cart_pager_driver_number)
	private EditText et_driver_number;
	/**
	 * 订单类型
	 */
	@ViewInject(R.id.et_fragment_cart_pager_dingdan_type)
	private EditText et_dingdan_type;
	/**
	 * 货车牌号
	 */
	@ViewInject(R.id.et_fragment_cart_pager_cart_number)
	private EditText et_cart_number;
	/**
	 * 司机电话号码
	 */
	@ViewInject(R.id.et_fragment_cart_pager_dirver_phone)
	private EditText et_dirver_phone;
	/**
	 * 下单人的电话号码
	 */
	@ViewInject(R.id.et_fragment_cart_pager_buy_phone)
	private EditText et_buy_phone;

	/**
	 * 订单模式，快速下单使用
	 */
	@ViewInject(R.id.ll_fragment_cart_pager_dingdan_my_modle)
	private LinearLayout ll_order_modle;
	/**
	 * 订单选择文本按钮
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_selecte_dingdan)
	private TextView tv_selecte_dinddan_modle;
	/**
	 * 购买数量
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_to_buy_count_display)
	private TextView tv_product_count;
	/**
	 * 订单总价
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_to_buy_total_price)
	private TextView tv_one_total_price;
	/**
	 * 集合数据下标值
	 */
	private int position ;
	/**
	 * 全局订单模式数据对象
	 */
	private OrderModle orderModle;
	/**
	 * 用户订单模式详细信息集合
	 */
	private List<OrderModleInfo> list_orderModleInfo;
	/**
	 * popupwindown对象
	 */
	private PopupWindow popupwindown;
	/**
	 * 第一层容器
	 */
	@ViewInject(R.id.ll_fragment_cart_pager_first)
	public CustomEnableLinearLayout ll_first;
	/**
	 * 第二层容器
	 */
	@ViewInject(R.id.ll_fragment_cart_pager_seconde)
	private CustomEnableLinearLayout ll_seconde;
	/**
	 * 商品单价  
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_to_buy_price)
	private TextView tv_price;
	/**
	 * 第二层容器确认按钮
	 */
	@ViewInject(R.id.btn_fragment_cart_pager_seconde_confirm)
	private Button btn_confirm;
	/**
	 * 第二层容器透明部分
	 */
	@ViewInject(R.id.ll_fragment_cart_pager_seconde_head)
	private LinearLayout ll_seconde_head;
	/**
	 * 第二层容器删除按钮
	 */
	@ViewInject(R.id.iv_fragment_cart_pager_to_buy_delete)
	private ImageView iv_seconde_delete;
	/**
	 * 产品增加按钮
	 */
	@ViewInject(R.id.iv_fragment_cart_pager_to_buy_product_add)
	private ImageView tv_product_add;
	/**
	 * 产品减少按钮
	 */
	@ViewInject(R.id.iv_fragment_cart_pager_to_buy_product_reduce)
	private ImageView tv_product_reduce;
	/**
	 * 第二层容器单个商品的总价
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_to_buy_total_price)
	private TextView tv_sigle_total_price;
	/**
	 * 订单模式显示按钮
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_selecte_dingdan)
	private TextView tv_order_modle;
	/**
	 * 购买参数页面商品名称
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_to_buy_product_name)
	private TextView tv_seconde_product_name;
	/**
	 * 保存原始数据集合
	 */
	private List<CartItem> list_old_cartItem = new ArrayList<CartItem>();
	/**
	 * dialog对象，用于加载数据时候用户体验时候使用
	 */
	private AlertDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.fragment_cart, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		x.view().inject(this, view);
		init();
		initListeners();
	}

	/**
	 * 初始化变量
	 */
	private void init() {
		//显示数据加载动画
//		loadingData("数据努力加载中");
		app = (Myapplication) Myapplication.getContext();
		//隐藏购物车图标
		((MainActivity) getActivity()).getTv_cart_acount().setVisibility(View.INVISIBLE);
		app.setNew_cart_data(false);
		orderModle = app.getOrderModle();
		cart = app.getCart();
		tv_total_cartItem_count = (TextView) view.findViewById(R.id.tv_fragment_cart_pager_total_button);
		list_total_cartItem = new ArrayList<CartItem>();
		lv_cartItem = (ListView) view.findViewById(R.id.lv_fragment_cart_pager_cartitems);
		tv_total_price = (TextView) view.findViewById(R.id.tv_fragment_cart_pager_total);
		//创建presenter对象
		presenter = new UploadCartDataPresenterImpl(this, path);
		//进行购物车数据加载
		presenter.uploadCartData(path);
	}

	/**
	 * 设置监听器
	 */
	private void initListeners() {
		tv_select_all.setOnClickListener(this);
		tv_edite.setOnClickListener(this);
		tv_total_cartItem_count.setOnClickListener(this);
		lv_cartItem.setOnItemClickListener(this);
		tv_buy.setOnClickListener(this);
		tv_linked_argin.setOnClickListener(this);
		ll_order_modle.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		iv_seconde_delete.setOnClickListener(this);
		ll_seconde_head.setOnClickListener(this);
		tv_product_add.setOnClickListener(this);
		tv_product_reduce.setOnClickListener(this);
	}

	@Override
	public void setData(List<CartItem> list) {
		list_cartItem = list;
		adapter = new CartAdapter(list_cartItem, list_total_cartItem,this, 
				lv_cartItem,tv_total_price , tv_total_cartItem_count,tv_select_all);
	}

	@Override
	public void setAdapter() {
		lv_cartItem.setAdapter(adapter);
		//关闭加载动画
//		loadDataCompleted();
		//是否显示无数据状态
		displayUnlinked();
	}

	/**
	 * 点击事件监听
	 * @param v
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		//全选文本按钮监听 
		case R.id.tv_fragment_cart_pager_seclete_all:
			//清空结算集合中的所有数据
			list_total_cartItem.clear();
			//当选择图标显示的时候，全选按钮才监听
			if(!adapter.isFlag()){
				Drawable drawable = null;
				if(!flag_select_all){
					drawable = getResources().getDrawable(R.drawable.icon_seclete);
					//改变图片
					tv_select_all.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
					tv_select_all.setTextColor(getActivity().getResources().getColor(R.color.wathet_blue));
					flag_select_all = true;
					//全部选择之后，得出最后总价，并且更新总价数据
					//全选的时候购物车集合数据和结算数据是一样的
					//将数据全部添加到结算集合中
					list_total_cartItem.addAll(list_cartItem);
					double total = cart.totalPrice(list_total_cartItem);
					//显示总价
					tv_total_price.setText("合计:￥"+total);
					//显示购物项总数量
					tv_total_cartItem_count.setText("结算("+list_total_cartItem.size()+")");
					if(list_total_cartItem.size()!=0){
						tv_total_cartItem_count.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
						tv_total_cartItem_count.setClickable(true);
					}
					//更新adapter
					adapter.setFlag_select_all(true);
					adapter.notifyDataSetChanged();
				}else{
					//取消全选时候，初始化数据
					initializeData();
					//更新adapter
					adapter.setFlag_select_all(false);
					adapter.notifyDataSetChanged();
				}
			}
			break;

			//编辑文本按钮监听
		case R.id.tv_fragment_cart_pager_edite_button:
			boolean flag = adapter.isFlag();
			if(!flag){
				// 改变全选文本按钮相关状态
				Drawable drawable = getResources().getDrawable(R.drawable.icon_unseclete);
				//改变图片
				tv_select_all.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
				tv_select_all.setTextColor(getActivity().getResources().getColor(R.color.black_light));
				flag_select_all = false;
				tv_total_price.setText("合计:￥0.00");
				list_total_cartItem.clear();
				tv_total_cartItem_count.setText("结算("+list_total_cartItem.size()+")");
				tv_total_cartItem_count.setBackgroundColor(getActivity().getResources().getColor(R.color.gray_light));
				tv_total_cartItem_count.setClickable(false);
				//改变选择文本按钮标记变量值
				adapter.setFlag_select_all(false);

				//改变cartAdapter里面的显示标记变量的值
				adapter.setFlag(true);
				//				tv_edite.setTextColor(getActivity().getResources().getColor(R.color.red));
				tv_edite.setText("完成");
				//更新cartAdapter,隐藏所有的选择图标,显示所有的删除图标
				adapter.notifyDataSetChanged();
			}else{
				//改变cartAdapter里面的显示标记变量的值
				adapter.setFlag(false);
				//				tv_edite.setTextColor(getActivity().getResources().getColor(R.color.white));
				tv_edite.setText("编辑全部");
				//更新cartAdapter,隐藏所有的删除图标,显示所有的选择图标
				adapter.notifyDataSetChanged();
			}
			//显示动画效果
			//			adapter.deleteIconShow();
			break;
			//购买结算按钮
		case R.id.tv_fragment_cart_pager_total_button:
			//启动加载动画
			loadingData("正在努力购买中");
			//数据清除
			list_buyParams.clear();
			list_productIds.clear();
			//根据结算集合进行数据采集
			for(CartItem cartItem:list_total_cartItem){
				//获取所有购买参数集合，产品id集合
				list_buyParams.add(cartItem.getBuyParam());
				list_productIds.add(cartItem.getProduct().getYWM_ID()+"");
			}
			for(CartItem cartitem:list_cartItem){
				Myapplication.log("购物车数据集合",cartitem.getBuyParam().toString());
			}
			//获得presenter对象
			buyPresenter = new BuyPresenterImpl(this, list_buyParams , list_productIds);
			buyPresenter.buyFromCart();
			break;
			//断网页面，重新加载按钮
		case R.id.tv_fragment_cart_pager_linked_argin:
			//进入商品列表页面
			Intent intent = new Intent(getActivity(), HomeActivity.class);
			intent.putExtra("TAG", 0);
			startActivity(intent);
			break;
			//第二层容器确认按钮
		case R.id.btn_fragment_cart_pager_seconde_confirm:
			//得到需要修改的数据的下标值
			//获取购买参数数据，并且保存到相应的数据集合中
			list_cartItem.get(position).setBuyParam(getBuyParam());
			//保存购物车数据
			cart.saveCartData();
			//更新购物车数据呈现页面
			adapter.notifyDataSetChanged();
			//退出第二层容器
			hideSecondePager();
			Myapplication.toast("编辑成功");
			break;
			//第二层容器删除按钮
		case R.id.iv_fragment_cart_pager_to_buy_delete:
			hideSecondePager();
			break;
			//第二层容器透明部分
		case R.id.ll_fragment_cart_pager_seconde_head:
			hideSecondePager();
			break;
			//第二层容器产品减少按钮
		case R.id.iv_fragment_cart_pager_to_buy_product_reduce:
			int acount = Integer.parseInt(tv_product_count.getText().toString());
			acount--;
			if(acount<1){
				tv_product_count.setText("1");
				acount = 1;
			}else{
				tv_product_count.setText(acount+"");
			}
			//更新单个商品总价
			tv_sigle_total_price.setText("总价：￥"+cart.temporarySingleTotalPrice(list_cartItem.get(position), acount));
			break;
			//第二层容器产品增加按钮
		case R.id.iv_fragment_cart_pager_to_buy_product_add:
			int total = Integer.parseInt(tv_product_count.getText().toString());
			tv_product_count.setText((++total)+"");
			//更新单个商品总价
			tv_sigle_total_price.setText("总价：￥"+cart.temporarySingleTotalPrice(list_cartItem.get(position), total));
			break;
			//订单模式布局按钮
		case R.id.ll_fragment_cart_pager_dingdan_my_modle:
			//呈现订单模式下拉菜单
			showPopupWindown();
			break;
		}
	}
	/**
	 * listView监听
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//跳转到商品详情页面
		Intent intent = new Intent(getActivity(), ProductDetailsPagerActivityImpl.class);
		intent.putExtra("product", list_cartItem.get(position).getProduct());
		startActivity(intent);
	}
	/**
	 * 购买操作完成时调用的方法
	 */
	public void buyCompleted(String productId,int index_flag){
		//更新购物车数据集合
		for(int j = 0;j<list_cartItem.size();j++){
			//根据产品id号进行判断是否删除
			int id = list_cartItem.get(j).getProduct().getYWM_ID();
			if(productId.equals(id+"")){
				list_cartItem.remove(j);
				break;
			}
		}
		cart.saveCartData();

		//如果线程标记值和id编号结合的长度是一致，说明批量购买操作已经全部完成
		if(index_flag==list_productIds.size()){
			//初始化页面数据
			initializeData();
			//更新购物车列表
			adapter.setFlag_select_all(false);
			adapter.notifyDataSetChanged();
			//跳转到用户订单中心
			Intent intent = new Intent(getActivity(), HomeActivity.class);
			intent.putExtra("TAG", 3);
			startActivity(intent);
			//终止加载动画
			loadDataCompleted();
			Myapplication.toast("批量购买成功");
		}
	}

	/**
	 * 自定义方法，隐藏第二层容器,恢复第一层监听事件
	 */
	private void hideSecondePager(){
		TranslateAnimation animation = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
		animation.setDuration(500);
		ll_seconde.setAnimation(animation);
		ll_seconde.setVisibility(View.INVISIBLE);
		ll_first.setChildClickable(true);
	}
	/**
	 * 自定义方法，批量购买操作之后，页面重新显示的时候，进行初始化数据
	 */
	private void initializeData(){
		//全选按钮初始化
		Drawable drawable = getResources().getDrawable(R.drawable.icon_unseclete);
		//改变图片
		tv_select_all.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
		tv_select_all.setTextColor(getActivity().getResources().getColor(R.color.black_light));
		flag_select_all = false;
		tv_total_price.setText("合计:￥0.0");
		//显示购物项总数量
		tv_total_cartItem_count.setText("结算("+"0"+")");
		tv_total_cartItem_count.setBackgroundColor(getActivity().getResources().getColor(R.color.gray_light));
		tv_total_cartItem_count.setClickable(false);
	}
	/**
	 * 自定义方法，显示无数据状态页面
	 */
	private void displayUnlinked() {
		//判断有无数据,进行断网页面的呈现
		if(list_cartItem.size()!=0){
			lv_cartItem.setVisibility(View.VISIBLE);
			ll_unlinked.setVisibility(View.INVISIBLE);
		}else{
			lv_cartItem.setVisibility(View.INVISIBLE);
			ll_unlinked.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 自定义获取购买参数
	 */
	private Map<String, String> getBuyParam() {
		//新建购物参数对象
		Map<String, String> buyParam = new HashMap<String, String>();
		//将购买参数添加进来
		ClientInfo clientInfo = app.getClientInfo().getResult().get(0);
		//客户id
		buyParam.put("clientid",clientInfo.getYWC_ClientID()+"");
		//订单编号
		String str = clientInfo.getYWC_ClientID()+"";
		String string = System.currentTimeMillis()+"";
		StringBuilder sb = new StringBuilder();
		sb.append(str).append(string);
		Myapplication.log("订单编号", sb.toString());
		buyParam.put("ladeid",sb.toString());
		//销售区域
		buyParam.put("areacode",clientInfo.getYWC_AreaCode());
		//订单类型
		buyParam.put("ladetype",et_dingdan_type.getText().toString());
		//品种代码
		buyParam.put("cementcode",list_cartItem.get(position).getProduct().getYWM_Code());
		//品种名称
		buyParam.put("cementname",list_cartItem.get(position).getProduct().getYWM_Name());
		//下单数量
		buyParam.put("ordernumber",tv_product_count.getText().toString());
		//下单价格
		buyParam.put("orderprice",list_cartItem.get(position).getProduct().getYWM_Price());
		//下单总价
		String total_price = tv_total_price.getText().toString();
		buyParam.put("totalprice",total_price.substring(total_price.indexOf("￥")+1, total_price.length()));
		//拉货司机姓名
		buyParam.put("carname",et_driver_name.getText().toString());
		//司机驾驶证号码
		buyParam.put("carid",et_driver_number.getText().toString());
		//货车牌照号码
		buyParam.put("carcode",et_cart_number.getText().toString());
		//司机电话号码
		buyParam.put("carphone", et_dirver_phone.getText().toString());
		//下单人电话
		buyParam.put("orderphone", et_buy_phone.getText().toString());
		//下单状态，默认开始值为0
		buyParam.put("status", 0+"");
		return buyParam;
	}
	/**
	 * 自定义方法，进行购买参数的自动赋值
	 */
	private void setOrderData(int i){
		//得到数据对象
		OrderModleInfo orderModleInfo = list_orderModleInfo.get(i);
		et_buy_phone.setText(orderModleInfo.getOrderphone());
		et_cart_number.setText(orderModleInfo.getCarcode());
		et_dingdan_type.setText(orderModleInfo.getLadetype());
		et_dirver_phone.setText(orderModleInfo.getCarphone());
		et_driver_name.setText(orderModleInfo.getCarname());
		et_driver_number.setText(orderModleInfo.getCarid());
	}
	/**
	 * 自定义方法：清除所有购买参数,并且显示购买参数布局
	 */
	public void displayBuyParam(int position){
		et_buy_phone.setText("");
		et_cart_number.setText("");
		et_dingdan_type.setText("");
		et_dirver_phone.setText("");
		et_driver_name.setText("");
		et_driver_number.setText("");
		tv_order_modle.setText(null);
		tv_product_count.setText(list_cartItem.get(position).getBuyParam().get("ordernumber"));
		tv_price.setText("单价:￥"+list_cartItem.get(position).getProduct().getYWM_Price());
		tv_sigle_total_price.setText("总价:￥"+cart.singleTotalPrice(list_cartItem.get(position)));
		tv_seconde_product_name.setText("名称:"+list_cartItem.get(position).getProduct().getYWM_Name());
		//弹出购买参数容器
		ll_seconde.setVisibility(View.VISIBLE);
		TranslateAnimation animation_up = new TranslateAnimation(0, 0, ll_seconde.getHeight(), 0);
		animation_up.setDuration(500);
		ll_seconde.startAnimation(animation_up);
	}
	/**
	 * 自定义方法，显示popupwindown下拉菜单
	 */
	private void showPopupWindown() {
		//父级容器窗口对象
		final Window window = getActivity().getWindow();
		final WindowManager.LayoutParams layout = window.getAttributes();
		View view = View.inflate(getActivity(), R.layout.popupwindown_view_modle_listview, null);
		ListView lv_order = (ListView) view.findViewById(R.id.lv_dingdan_my_modle);
		//进行数据采集
		final List<String> list_orderModleName = new ArrayList<String>();
		for(int i=0;i<orderModle.getList_orderModleInfo().size();i++){
			list_orderModleName.add(orderModle.getList_orderModleInfo().get(i).getOrderModleName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.base_textview_for_dapter_item_modle_font_white, list_orderModleName);
		lv_order.setAdapter(adapter);
		popupwindown = new PopupWindow(view, tv_order_modle.getWidth(), ll_seconde.getHeight()/3);
		popupwindown.showAsDropDown(tv_order_modle, 0, 2);
		popupwindown.setAnimationStyle(R.anim.popupwindown_display);
		//设置父级窗口背景
		layout.alpha = 0.5f;
		window.setAttributes(layout);
		popupwindown.setFocusable(true);
		popupwindown.update();
		popupwindown.setOutsideTouchable(false);
		//里面的listview设置监听
		lv_order.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//显示订单模式文本内容
				tv_order_modle.setText(list_orderModleName.get(position));
				//销毁popupwindown
				popupwindown.dismiss();
				popupwindown = null;
				System.gc();
				//自动设置购买参数内容
				setOrderData(position);
			}
		});
		//设置popypwindow销毁监听
		popupwindown.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				layout.alpha = 1f;
				window.setAttributes(layout);
			}
		});
		//设置popupwindow非聚焦区域监听
		popupwindown.getContentView().setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				popupwindown.setFocusable(false);
				popupwindown.dismiss();
				popupwindown = null;
				System.gc();
				return false;
			}
		});
	}
	/**
	 * 设置需要修改的数据的下标值
	 * @param position
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	/**
	 * 自定义方法，加载数据时候，显示加载动画效果
	 * str:需要显示的文字信息
	 */
	private void loadingData(String str){
		dialog = new AlertDialog.Builder(getActivity()).create();
		View view = View.inflate(getActivity(), R.layout.dialog_loading_data, null);
		TextView text = (TextView) view.findViewById(R.id.tv_dialog_load_data);
		text.setText(str);
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}
	/**
	 * 自定义方法，关闭加载数据显示动画
	 */
	private void loadDataCompleted() {
		dialog.dismiss();
		dialog = null;
		System.gc();
	}

	@Override
	public void onResume() {
		super.onResume();
		//开启加载数据动画
//		loadingData("数据努力加载中");
		//数据已经查看，改变购物车新数据状态
		((MainActivity) getActivity()).getTv_cart_acount().setVisibility(View.INVISIBLE);
		app.setNew_cart_data(false);
		//购物车数据重新加载,如果是再次显示页面，则重新加载数据
		if(flag_data_argin){
			presenter.uploadCartData(path);
			//当购物车数据有变化，并且当前选择图标为显示状态
			if((list_cartItem.size()!=list_old_cartItem.size())&&(adapter.isFlag()==false)){
				//全选按钮初始化
				Drawable drawable = getResources().getDrawable(R.drawable.icon_unseclete);
				//改变图片
				tv_select_all.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
				tv_select_all.setTextColor(getActivity().getResources().getColor(R.color.black_light));
				flag_select_all = false;
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		flag_data_argin = true;
		list_old_cartItem.addAll(list_cartItem);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(hidden){
			onPause();
		}else{
			onResume();
		}
	}

}
