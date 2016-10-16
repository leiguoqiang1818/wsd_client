package com.example.wsd_client.activity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.customViews.CustomEnableLinearLayout;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.CollectionProduct;
import com.example.wsd_client.entity.OrderModle;
import com.example.wsd_client.entity.OrderModleInfo;
import com.example.wsd_client.entity.Product;
import com.example.wsd_client.fragment.ProductDetailsPageProductDetailsFragment;
import com.example.wsd_client.fragment.ProductDetailsPagerEvaluateFragment;
import com.example.wsd_client.fragment.ProductDetailsPagerProductFragment;
import com.example.wsd_client.presenter.impl.BuyPresenterImpl;

/**
 * 商品详情页面，显示单个商品的详细信息
 * @author wsd_leiguoqiang
 */
public class ProductDetailsPagerActivityImpl extends FragmentActivity implements OnCheckedChangeListener,OnPageChangeListener ,OnClickListener{

	/**
	 * radioGroup,三个选择按钮
	 */
	@ViewInject(R.id.rg_product_details_pager)
	private RadioGroup rg_radioGroup;
	/**
	 * 商品基本信息
	 */
	@ViewInject(R.id.rb_product_details_pager_product)
	private RadioButton rb_product;
	/**
	 * 商品详情 
	 */
	@ViewInject(R.id.rb_product_details_pager_product_details)
	private RadioButton rb_product_details;
	/**
	 *评价
	 */
	@ViewInject(R.id.rb_product_details_pager_evaluate)
	private RadioButton rb_evaluate;
	/**
	 * 收藏按钮
	 */
	@ViewInject(R.id.tv_product_details_pager_collect)
	private TextView tv_collect;

	/**
	 * 立即购买按钮
	 */
	@ViewInject(R.id.btn_product_details_pager_buy)
	private Button btn_buy;

	/**
	 * ViewPager
	 */
	@ViewInject(R.id.vp_product_details_pager)
	private ViewPager vp;
	/**
	 * list集合，存放fragment
	 */
	private List<Fragment> list_fragment = new ArrayList<Fragment>();

	private Fragment productFragment;
	private Fragment productDetailsFragment;
	private Fragment evaluateFragment;

	/**
	 * 返回按钮
	 */
	@ViewInject(R.id.iv_product_details_pager_back)
	private ImageView iv_back;
	/**
	 * 购物车按钮
	 */
	@ViewInject(R.id.iv_product_details_pager_cart)
	private ImageView iv_cart;
	/**
	 * 购物车图标数量提示
	 */
	@ViewInject(R.id.tv_product_details_pager_cart_count)
	private TextView tv_cart_count;
	/**
	 * 添加到购物车按钮
	 */
	@ViewInject(R.id.btn_product_details_pager_add_to_cart)
	private Button btn_add_to_cart;
	/**
	 * 购物车中购物项数量
	 */
	private int cart_count;
	/**
	 * 产品对象
	 */
	private Product product;
	/**
	 * 第二层容器
	 */
	@ViewInject(R.id.ll_product_detials_pager_seconde)
	private CustomEnableLinearLayout ll_seconde;
	/**
	 * 第一层容器
	 */
	@ViewInject(R.id.ll_product_detials_pager_first)
	private CustomEnableLinearLayout ll_first;
	/**
	 * 加入购物车和立即购买携带标记变量
	 */
	private int click_flag;
	/**
	 * 添加到购物车
	 */
	private static final int ADD_TO_CART = 1;
	/**
	 * 立即购买
	 */
	private static final int PROMPTLY_BUY = 2;
	/**
	 * 第二层容器头部透明部分
	 */
	@ViewInject(R.id.ll_product_details_pager_seconde_head)
	private LinearLayout ll_seconde_head;
	/**
	 * 第二层容器确认按钮
	 */
	@ViewInject(R.id.btn_product_details_pager_seconde_confirm)
	private Button btn_confirm;
	/**
	 * 第二层删除按钮
	 */
	@ViewInject(R.id.iv_product_details_pager_to_buy_delete)
	private ImageView iv_seconde_delete;
	/**
	 * 购买操作presenter
	 */
	private BuyPresenterImpl presenter;
	private Map<String, String> buyParam;
	/**
	 * 司机名称
	 */
	@ViewInject(R.id.et_product_details_pager_driver_name)
	private EditText et_driver_name;
	/**
	 * 司机驾驶证号码
	 */
	@ViewInject(R.id.et_product_details_pager_driver_number)
	private EditText et_driver_number;
	/**
	 * 订单类型
	 */
	@ViewInject(R.id.et_product_details_pager_dingdan_type)
	private EditText et_dingdan_type;
	/**
	 * 货车牌号
	 */
	@ViewInject(R.id.et_product_details_pager_cart_number)
	private EditText et_cart_number;
	/**
	 * 司机电话号码
	 */
	@ViewInject(R.id.et_product_details_pager_dirver_phone)
	private EditText et_dirver_phone;
	/**
	 * 下单人的电话号码
	 */
	@ViewInject(R.id.et_product_details_pager_buy_phone)
	private EditText et_buy_phone;
	/**
	 * myapplication对象
	 */
	private Myapplication app;
	/**
	 * 购买数量
	 */
	@ViewInject(R.id.tv_product_details_pager_to_buy_count_display)
	private TextView tv_product_count;
	/**
	 * 订单总价
	 */
	@ViewInject(R.id.tv_product_details_pager_to_buy_total_price)
	private TextView tv_total_price;
	/**
	 * 商品单价
	 */
	@ViewInject(R.id.tv_product_details_pager_to_buy_price)
	private TextView tv_price;
	/**
	 * 增加按钮
	 */
	@ViewInject(R.id.iv_product_details_pagerto_buy_product_add)
	private ImageView iv_add;
	/**
	 * 减少
	 */
	@ViewInject(R.id.iv_product_details_pagerto_buy_product_reduce)
	private ImageView iv_reduce;
	/**
	 * 订单模式布局
	 */
	@ViewInject(R.id.ll_product_detials_pager_dingdan_my_modle)
	private LinearLayout ll_order_modle;
	/**
	 * 订单模式显示文本
	 */
	@ViewInject(R.id.tv_product_detials_pager_selecte_dingdan)
	private TextView tv_selecte_dinddan_modle;

	private List<OrderModleInfo> list_orderModleInfo;
	private OrderModle orderModle;
	private PopupWindow popupwindown;
	/**
	 * 第二层容器商品名称
	 */
	@ViewInject(R.id.tv_product_details_pager_to_buy_product_name)
	private TextView tv_seconde_product_name;
	/**
	 * 收藏夹对象
	 */
	private CollectionProduct collection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail_info_pager);
		x.view().inject(this);
		init();
		initListeners();
	}

	private void initListeners() {
		rg_radioGroup.setOnCheckedChangeListener(this);
		vp.setOnPageChangeListener(this);
		tv_collect.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		iv_cart.setOnClickListener(this);
		btn_add_to_cart.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		btn_buy.setOnClickListener(this);
		ll_seconde_head.setOnClickListener(this);
		iv_seconde_delete.setOnClickListener(this);
		iv_add.setOnClickListener(this);
		iv_reduce.setOnClickListener(this);
		ll_order_modle.setOnClickListener(this);
	}

	private void init() {
		app = (Myapplication) Myapplication.getContext();
		app.setProductActivity(this);
		orderModle = app.getOrderModle();
		collection = app.getCollectionProduct();
		//获取产品对象
		Intent intent = getIntent();
		product = (Product) intent.getSerializableExtra("product");
		productFragment = new ProductDetailsPagerProductFragment(product);
		productDetailsFragment = new ProductDetailsPageProductDetailsFragment();
		evaluateFragment = new ProductDetailsPagerEvaluateFragment();

		list_fragment.add(productFragment);
		list_fragment.add(productDetailsFragment);
		list_fragment.add(evaluateFragment);
		//设置Viewpager适配器
		FragmentManager manager =  getSupportFragmentManager();
		ViewPagerAdapter adapter = new ViewPagerAdapter(manager, list_fragment);
		vp.setAdapter(adapter);
		vp.setCurrentItem(0);
		//获取购物车中商品数量
		cart_count = app.getCart().getList_cartItem().size();
		tv_cart_count.setText(cart_count+"");
		buyParam = new HashMap<String, String>();
		//初始化商品收藏状态
		if(collection.existCollectioin(product)){
			tv_collect.setSelected(true);
		}
	}

	/**
	 * ViewPager的适配器
	 * @author wsd_leiguoqiang
	 */
	class ViewPagerAdapter extends FragmentPagerAdapter{
		private List<Fragment> list_fragment = new ArrayList<Fragment>();

		public ViewPagerAdapter(FragmentManager fm , List<Fragment> list) {
			super(fm);
			this.list_fragment = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			return list_fragment.get(arg0);
		}

		@Override
		public int getCount() {
			return list_fragment.size();
		}

	}

	/**
	 * viewPager刚刚按下，但还没有开始滑动
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	/**
	 * 滑动过程中监听
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	/**
	 * 滑动完成，已经成功滑动到另一个pager
	 */
	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
		case 0:
			rb_product.setChecked(true);
			break;
		case 1:
			rb_product_details.setChecked(true);
			break;
		case 2:
			rb_evaluate.setChecked(true);
			break;
		}
	}

	/**
	 * radiogroup的监听
	 * @param group
	 * @param checkedId
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_product_details_pager_product:
			vp.setCurrentItem(0);
			break;
		case R.id.rb_product_details_pager_product_details:
			vp.setCurrentItem(1);
			break;
		case R.id.rb_product_details_pager_evaluate:
			vp.setCurrentItem(2);
			break;
			//收藏按钮
		case R.id.tv_product_details_pager_collect:

			break;

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//收藏夹按钮
		case R.id.tv_product_details_pager_collect:
			if(tv_collect.isSelected()){
				tv_collect.setSelected(false);
				// 从收藏夹中取出商品
				collection.deleteCollectionProduct(product);
			}else{
				tv_collect.setSelected(true);
				//将商品放入收藏夹
				collection.addCollectionProduct(product);
			}
			break;
			//添加到购物车
		case R.id.btn_product_details_pager_add_to_cart:
			//当从商品列表页面跳转过来时候，可以将商品添加到购物车中
			//显示购买参数，并且携带标记标量，填写完购买参数之后，点击确认按钮，才放入购物车
			displayBuyParam(product);
			((ProductDetailsPagerProductFragment)productFragment).setClick_flag(false);
			click_flag = ADD_TO_CART;
			break;
			//立即购买
		case R.id.btn_product_details_pager_buy:
			displayBuyParam(product);
			click_flag = PROMPTLY_BUY;
			((ProductDetailsPagerProductFragment)productFragment).setClick_flag(false);
			break;
			//返回按钮
		case R.id.iv_product_details_pager_back:
			finish();
			break;
			//购物车按钮
		case R.id.iv_product_details_pager_cart:
			Intent intent_cart = new Intent(this, MainActivity.class);
			intent_cart.putExtra("TAG", 2);
			startActivity(intent_cart);
			break;
			//第二层透明部分监听
		case R.id.ll_product_details_pager_seconde_head:
			TranslateAnimation animation_down = new TranslateAnimation(0, 0, 0,ll_seconde.getHeight());
			animation_down.setDuration(2000);
			ll_seconde.setVisibility(View.INVISIBLE);
			((ProductDetailsPagerProductFragment)productFragment).setClick_flag(true);
			break;
			//第二层容器里面确认按钮
		case R.id.btn_product_details_pager_seconde_confirm:
			switch (click_flag) {
			//添加到购物车
			case 1:
				//判断购物车中是否已经存在该产品（产品id号）
				int productId = product.getYWM_ID();
				//循环比较productId
				for(int i = 0;i<app.getList_cartItem().size();i++){
					if(app.getList_cartItem().get(i).getProduct().getYWM_ID()==productId){
						Myapplication.toast("购物车已存在该商品");
						return;
					}
				}
				//创建一个新cartitem对象，并将对象添加到购物车数据集合中
				CartItem cartItem = new CartItem();
				//设置产品对象
				cartItem.setProduct(product);
				//设置购买参数对象
				cartItem.setBuyParam(getBuyParam());
				//持久化保存购物车数据
				Cart cart = app.getCart();
				cart.getList_cartItem().add(cartItem);
				cart.saveCartData();
				//更新购物车数量小图标
				tv_cart_count.setText(cart.getList_cartItem().size()+"");
				ll_seconde.setVisibility(View.GONE);
				Myapplication.toast("成功加入购物车");
				break;
				//立即购买操作
			case 2:
				presenter = new BuyPresenterImpl(ProductDetailsPagerActivityImpl.this, getBuyParam());
				presenter.buyFromProductDetails();
				break;
			}
			break;
			//删除图标监听 
		case R.id.iv_product_details_pager_to_buy_delete:
			ll_seconde.setVisibility(View.INVISIBLE);
			TranslateAnimation animation_down_delete = new TranslateAnimation(0, 0, 0,ll_first.getHeight());
			animation_down_delete.setDuration(2000);
			((ProductDetailsPagerProductFragment)productFragment).setClick_flag(true);
			break;
			//增加sdgsdgds
		case R.id.iv_product_details_pagerto_buy_product_add:
			int acount_add = Integer.parseInt(tv_product_count.getText().toString());
			tv_product_count.setText((++acount_add)+"");
			tv_total_price.setText("总价：￥"+totalPrice(product.getYWM_Price(), acount_add)+"");
			break;
			//减少
		case R.id.iv_product_details_pagerto_buy_product_reduce:
			int acount_reduce = Integer.parseInt(tv_product_count.getText().toString());
			acount_reduce--;
			if(acount_reduce<1){
				acount_reduce = 1;
			}
			tv_product_count.setText(acount_reduce+"");
			tv_total_price.setText("总价：￥"+totalPrice(product.getYWM_Price(), acount_reduce)+"");
			break;
			//订单模式布局
		case R.id.ll_product_detials_pager_dingdan_my_modle:
			//显示订单模式布局
			showPopupwindow();
			break;
		}
	}
	/**
	 * 自定义方法,显示popupwindow对象使用，进行记忆下单使用
	 */
	private void showPopupwindow() {
		//父级容器窗口对象
		final Window window = getWindow();
		final WindowManager.LayoutParams layout = window.getAttributes();
		//弹出popopwindown(制作view，myapplication获取订单模式数据，显示数据)
		View view = View.inflate(ProductDetailsPagerActivityImpl.this, R.layout.popupwindown_view_modle_listview, null);
		ListView lv_dingdan = (ListView) view.findViewById(R.id.lv_dingdan_my_modle);
		//进行数据采集
		final List<String> list_dingdan_name = new ArrayList<String>();
		list_orderModleInfo = orderModle.getList_orderModleInfo();
		for(OrderModleInfo orderModleInfo:list_orderModleInfo){
			list_dingdan_name.add(orderModleInfo.getOrderModleName());
		}
		//进行数据呈现
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductDetailsPagerActivityImpl.this, R.layout.base_textview_for_dapter_item_modle_font_white, list_dingdan_name);
		lv_dingdan.setAdapter(adapter);
		lv_dingdan.setFocusable(true);
		popupwindown = new PopupWindow(view, tv_selecte_dinddan_modle.getWidth(), ll_seconde.getHeight()/3);
		popupwindown.showAsDropDown(tv_selecte_dinddan_modle, 0, 2);
		//设置父级窗口背景
		layout.alpha = 0.5f;
		window.setAttributes(layout);
		popupwindown.setFocusable(true);
		popupwindown.update();
		//其他地方点击关闭
		//焦点之外点击进行popupwindow销毁
		popupwindown.setOutsideTouchable(true);
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
		lv_dingdan.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//点击那个就把该内容标题显示到选择文本中
				tv_selecte_dinddan_modle.setText(list_dingdan_name.get(position));
				popupwindown.setFocusable(false);
				//取出对象中内容，对其他控件进行赋值
				setOrderData(position);
				popupwindown.dismiss();
				popupwindown = null;
				System.gc();
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
	}

	/**
	 * 自定义获取购买参数
	 */
	private Map<String, String> getBuyParam() {
		//清除集合中所有数据
		buyParam.clear();
		//将购买参数添加进来
		ClientInfo clientInfo = app.getClientInfo().getResult().get(0);
		//客户id
		buyParam.put("clientid",clientInfo.getYWC_ClientID()+"");
		//订单编号
		buyParam.put("ladeid",clientInfo.getYWC_ClientID()+System.currentTimeMillis()+"");
		//销售区域
		buyParam.put("areacode",clientInfo.getYWC_AreaCode());
		//订单类型
		buyParam.put("ladetype",et_dingdan_type.getText().toString());
		//品种代码
		buyParam.put("cementcode",product.getYWM_Code());
		//品种名称
		buyParam.put("cementname",product.getYWM_Name());
		//下单数量
		buyParam.put("ordernumber",tv_product_count.getText().toString());
		//下单价格
		buyParam.put("orderprice",product.getYWM_Price());
		//订单总价
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
	 * 购买操作完成时调用的方法
	 */
	public void buyCompleted(){
		//隐藏第二层容器
		ll_seconde.setVisibility(View.GONE);
		//跳转到用户订单中心页面
		Intent intent = new Intent(this, HomeActivity.class);
		intent.putExtra("TAG", 3);
		startActivity(intent);
	}

	/**
	 * 自定义方法：清除所有购买参数,并且显示购买参数布局
	 */
	public void displayBuyParam(Product product){
		et_buy_phone.setText("");
		et_cart_number.setText("");
		et_dingdan_type.setText("");
		et_dirver_phone.setText("");
		et_driver_name.setText("");
		et_driver_number.setText("");
		tv_product_count.setText("1");
		tv_selecte_dinddan_modle.setText(null);
		tv_seconde_product_name.setText("名称:"+product.getYWM_Name());
		tv_price.setText("单价:￥"+product.getYWM_Price());
		tv_total_price.setText("总价:￥"+product.getYWM_Price());
		//弹出购买参数容器
		ll_seconde.setVisibility(View.VISIBLE);
		TranslateAnimation animation_up = new TranslateAnimation(0, 0, ll_seconde.getHeight(), 0);
		animation_up.setDuration(1000);
		ll_seconde.startAnimation(animation_up);
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
	 *自定义方法，得出临时总价 
	 * @param price：单价
	 * @param acount：数量
	 */
	private double totalPrice(String price,int acount){
		BigDecimal bd_price = new BigDecimal(price);
		BigDecimal bd_acount = new BigDecimal(acount);
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.parseDouble(df.format(bd_price.multiply(bd_acount).doubleValue()));
	}
}
