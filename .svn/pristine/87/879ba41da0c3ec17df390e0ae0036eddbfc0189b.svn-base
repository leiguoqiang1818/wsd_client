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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.adapter.CollectionAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.customViews.CustomEnableLinearLayout;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.CollectionProduct;
import com.example.wsd_client.entity.OrderModle;
import com.example.wsd_client.entity.OrderModleInfo;
import com.example.wsd_client.entity.Product;
import com.example.wsd_client.presenter.impl.BuyPresenterImpl;
import com.example.wsd_client.util.CircleImageView;

/**
 *  收藏夹页面
 * @author wsd_leiguoqiang
 */
public class CollectionActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	/**
	 * listview
	 */
	@ViewInject(R.id.lv_liebiao)
	private ListView lv_collection;
	/**
	 * 数据集合
	 */
	private List<Product> lists = new ArrayList<Product>();
	/**
	 * 适配器
	 */
	private CollectionAdapter collectiionAdapter;
	/**
	 * 第二层容器
	 */
	@ViewInject(R.id.ll_product_item_pager_seconde)
	private LinearLayout ll_seconde;
	/**
	 * 第一层容器
	 */
	@ViewInject(R.id.ll_product_item_pager_first)
	private LinearLayout ll_first;

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
	@ViewInject(R.id.ll_product_item_pager_seconde_head)
	private LinearLayout ll_seconde_head;
	/**
	 * 第二层容器确认按钮
	 */
	@ViewInject(R.id.btn_product_item_pager_seconde_confirm)
	private Button btn_confirm;
	/**
	 * 第二层删除按钮
	 */
	@ViewInject(R.id.iv_product_item_pager_to_buy_delete)
	private ImageView iv_seconde_delete;
	/**
	 * 购买操作presenter
	 */
	private BuyPresenterImpl buyPresenter;
	/**
	 * 购买参数集合
	 */
	private Map<String, String> buyParam;
	/**
	 * 司机名称
	 */
	@ViewInject(R.id.et_product_item_pager_driver_name)
	private EditText et_driver_name;
	/**
	 * 司机驾驶证号码
	 */
	@ViewInject(R.id.et_product_item_pager_driver_number)
	private EditText et_driver_number;
	/**
	 * 订单类型
	 */
	@ViewInject(R.id.et_product_item_pager_dingdan_type)
	private EditText et_dingdan_type;
	/**
	 * 货车牌号
	 */
	@ViewInject(R.id.et_product_item_pager_cart_number)
	private EditText et_cart_number;
	/**
	 * 司机电话号码
	 */
	@ViewInject(R.id.et_product_item_pager_dirver_phone)
	private EditText et_dirver_phone;
	/**
	 * 下单人的电话号码
	 */
	@ViewInject(R.id.et_product_item_pager_buy_phone)
	private EditText et_buy_phone;
	/**
	 * myapplication对象
	 */
	private Myapplication app;
	/**
	 * 购买数量
	 */
	@ViewInject(R.id.tv_product_item_pager_to_buy_count_display)
	private TextView tv_product_count;
	/**
	 * 订单总价
	 */
	@ViewInject(R.id.tv_product_item_pager_to_buy_total_price)
	private TextView tv_total_price;
	/**
	 * 集合数据下标值
	 */
	private int position ;
	/**
	 * 商品单价
	 */
	@ViewInject(R.id.tv_product_item_pager_to_buy_price)
	private TextView tv_price;
	/**
	 * 增加按钮
	 */
	@ViewInject(R.id.iv_product_item_pager_to_buy_product_add)
	private ImageView iv_add;
	/**
	 * 减少按钮
	 */
	@ViewInject(R.id.iv_product_item_pager_to_buy_product_reduce)
	private ImageView iv_reduce;
	/**
	 * 购物车对象
	 */
	private Cart cart;
	/**
	 * 收藏夹对象
	 */
	private CollectionProduct collection;
	/**
	 * 订单模式，快速下单使用
	 */
	@ViewInject(R.id.ll_product_item_pager_dingdan_my_modle)
	private LinearLayout ll_order_modle;
	/**
	 * 订单选择文本按钮
	 */
	@ViewInject(R.id.tv_product_item_pager_selecte_dingdan)
	private TextView tv_selecte_dinddan_modle;
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
	 * 无网络连接布局
	 */
	@ViewInject(R.id.ll_fragment_liebiao_pager_unlinked)
	private CustomEnableLinearLayout ll_unlinked;
	/**
	 * 无数据时，重新加载文本按钮
	 */
	@ViewInject(R.id.tv_fragment_liebiao_pager_linked_argin)
	private TextView tv_linked_argin;
	/**
	 * 第二层容器商品名字
	 */
	@ViewInject(R.id.tv_product_item_pager_to_buy_product_name)
	private TextView tv_seconde_product_name;
	/**
	 * 头部公共部分
	 */
	@ViewInject(R.id.rl_title_bar)
	private RelativeLayout rl_head;
	/**
	 * 第三部分搜索框
	 */
	@ViewInject(R.id.rl_liebiao_fragment_pager_third)
	private RelativeLayout rl_search;
	/**
	 * 第三层容器搜索框edittext
	 */
	@ViewInject(R.id.et_liebiao_fragment_search)
	private EditText et_third_search;

	/**
	 * 页面标题
	 */
	private TextView tvTitle;
	/**
	 * 圆形图标
	 */
	private CircleImageView civ;
	/**
	 * 页面返回键
	 */
	private ImageButton ibBack;
	/**
	 * 购物车图标
	 */
	private ImageButton ibCart;
	/**
	 * 购物车数量图标
	 */
	private TextView tvAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_liebiao);
		init();
		initListeners();
	}

	private void initListeners() {
		ibBack.setOnClickListener(this);
		ibCart.setOnClickListener(this);
		lv_collection.setOnItemClickListener(this);
		btn_confirm.setOnClickListener(this);
		ll_seconde_head.setOnClickListener(this);
		iv_seconde_delete.setOnClickListener(this);
		iv_add.setOnClickListener(this);
		iv_reduce.setOnClickListener(this);
		ll_order_modle.setOnClickListener(this);
		tv_linked_argin.setOnClickListener(this);
	}

	private void init() {
		x.view().inject(this);
		app = (Myapplication) Myapplication.getContext();
		cart = app.getCart();
		collection = app.getCollectionProduct();
		orderModle = app.getOrderModle();
		tvTitle=(TextView)findViewById(R.id.tv_title);
		civ=(CircleImageView)findViewById(R.id.iv_contact);
		ibBack=(ImageButton)findViewById(R.id.ib_title_back);
		ibCart=(ImageButton)findViewById(R.id.ib_title_cart);
		tvAccount=(TextView)findViewById(R.id.tv_cart_index);
		tvTitle.setText("收藏夹");
		civ.setVisibility(View.INVISIBLE);
		ibBack.setVisibility(View.VISIBLE);
		ibCart.setVisibility(View.VISIBLE);
		//进行数据采集
		lists = collection.getList_products();
		Myapplication.log("收藏夹数据长度", lists.size()+"");
		//进行数据显示
		collectiionAdapter=new CollectionAdapter(lists, this);
		lv_collection.setAdapter(collectiionAdapter);
		//初始化购物车图标数量
		int acount = ((Myapplication)(Myapplication.getContext())).getCart().getList_cartItem().size();
		if(acount>0){
			tvAccount.setText(acount+"");
			tvAccount.setVisibility(View.VISIBLE);
		}else{
			tvAccount.setVisibility(View.INVISIBLE);
		}
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
		tv_product_count.setText("1");
		tv_selecte_dinddan_modle.setText(null);
		tv_price.setText("单价:￥"+lists.get(position).getYWM_Price());
		tv_total_price.setText("总价:￥"+lists.get(position).getYWM_Price());
		tv_seconde_product_name.setText("名称:"+lists.get(position).getYWM_Name());
		//弹出购买参数容器
		ll_seconde.setVisibility(View.VISIBLE);
		TranslateAnimation animation_up = new TranslateAnimation(0, 0, ll_seconde.getHeight(), 0);
		animation_up.setDuration(1000);
		ll_seconde.startAnimation(animation_up);
	}

	public void setClick_flag(int click_flag) {
		this.click_flag = click_flag;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, ProductDetailsPagerActivityImpl.class);
		Product product = lists.get(position);
		intent.putExtra("product", product);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//返回键
		case R.id.ib_title_back:
			finish();
			break;
			//购物车图标按钮
		case R.id.ib_title_cart:
			Intent in =new Intent(this,MainActivity.class);
			in.putExtra("TAG",2);
			startActivity(in);
			break;
			// 第二层个容器确认按钮
		case R.id.btn_product_item_pager_seconde_confirm:
			switch (click_flag) {
			//加入到购物车
			case ADD_TO_CART:
				//判断集合中是否已经存在此商品（通过产品id进行判断）
				int productId = lists.get(position).getYWM_ID();
				//获取购物车中的数据
				List<CartItem> list_cartItem = cart.getList_cartItem();
				for(CartItem cartItem:list_cartItem){
					int productID = cartItem.getProduct().getYWM_ID();
					if(productId==productID){
						TranslateAnimation animation_down = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
						animation_down.setDuration(1000);
						ll_seconde.startAnimation(animation_down);
						ll_seconde.setVisibility(View.INVISIBLE);
						Myapplication.toast("此商品已存在购物车");
						return;
					}
				}
				//创建一个新cartitem对象，并将对象添加到购物车数据集合中
				CartItem cartItem = new CartItem();
				//设置产品对象
				cartItem.setProduct(lists.get(position));
				//设置购买参数对象
				cartItem.setBuyParam(getBuyParam());
				//保存数据到本地
				list_cartItem.add(cartItem);
				cart.saveCartData();
				//更新购物车数量小图标
				tvAccount.setText(list_cartItem.size()+"");
				tvAccount.setVisibility(View.VISIBLE);
				TranslateAnimation animation_down = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
				animation_down.setDuration(1000);
				ll_seconde.startAnimation(animation_down);
				ll_seconde.setVisibility(View.INVISIBLE);
				Myapplication.toast("成功加入购物车");
				break;
				//立即购买
			case PROMPTLY_BUY:
				buyPresenter = new BuyPresenterImpl(getBuyParam(), this);
				buyPresenter.buyFromCollection();
				TranslateAnimation animation_down_one = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
				animation_down_one.setDuration(1000);
				ll_seconde.startAnimation(animation_down_one);
				ll_seconde.setVisibility(View.INVISIBLE);
				break;
			}
			break;
			//第二层透明部分
		case R.id.ll_product_item_pager_seconde_head:
			TranslateAnimation animation_down = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
			animation_down.setDuration(1000);
			ll_seconde.startAnimation(animation_down);
			ll_seconde.setVisibility(View.INVISIBLE);
			break;
			//第二层删除按钮
		case R.id.iv_product_item_pager_to_buy_delete:
			TranslateAnimation animation_down_delete = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
			animation_down_delete.setDuration(1000);
			ll_seconde.startAnimation(animation_down_delete);
			ll_seconde.setVisibility(View.INVISIBLE);
			break;
			//增加
		case R.id.iv_product_item_pager_to_buy_product_add:
			int acount_add = Integer.parseInt(tv_product_count.getText().toString());
			tv_product_count.setText((++acount_add)+"");
			tv_total_price.setText("总价:￥"+totalPrice(lists.get(position).getYWM_Price(), acount_add)+"");
			break;
			//减少
		case R.id.iv_product_item_pager_to_buy_product_reduce:
			int acount_reduce = Integer.parseInt(tv_product_count.getText().toString());
			acount_reduce--;
			if(acount_reduce<1){
				acount_reduce = 1;
			}
			tv_product_count.setText(acount_reduce+"");
			tv_total_price.setText("总价:￥"+totalPrice(lists.get(position).getYWM_Price(), acount_reduce)+"");
			break;
			//订单模式按钮
		case R.id.ll_product_item_pager_dingdan_my_modle:
			//显示popupdindow
			showPopypwindow();
			break;
			//重新加载数据文本按钮
		case R.id.tv_fragment_liebiao_pager_linked_argin:
			//重新进行服务器网络请求，服务器暂且无此功能
			//			presenter.loadData();
			break;
		}
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
	/**
	 * 自定义方法，显示popupdindow，进行快速下单
	 */
	private void showPopypwindow() {
		//弹出popopwindown(制作view，myapplication获取订单模式数据，显示数据)
		View view = View.inflate(this, R.layout.popupwindown_view_modle_listview, null);
		ListView lv_dingdan = (ListView) view.findViewById(R.id.lv_dingdan_my_modle);
		//进行数据采集
		final List<String> list_dingdan_name = new ArrayList<String>();
		list_orderModleInfo = orderModle.getList_orderModleInfo();
		for(OrderModleInfo orderModleInfo:list_orderModleInfo){
			list_dingdan_name.add(orderModleInfo.getOrderModleName());
		}
		//进行数据呈现
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.base_textview_for_dapter_item_modle_font_white, list_dingdan_name);
		lv_dingdan.setAdapter(adapter);
		popupwindown = new PopupWindow(view, tv_selecte_dinddan_modle.getWidth(), ll_seconde.getHeight()/3);
		popupwindown.showAsDropDown(tv_selecte_dinddan_modle, 0, 2);
		//父级背景改成透明灰色
		WindowManager.LayoutParams layout = getWindow().getAttributes();
		layout.alpha = 0.5f;
		getWindow().setAttributes(layout);
		//设置焦点和更新窗口状态
		popupwindown.setFocusable(true);
		popupwindown.update();
		//焦点之外点击进行取消popupwindow
		popupwindown.setOutsideTouchable(false);
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
		popupwindown.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams layout = getWindow().getAttributes();
				layout.alpha = 1f;
				getWindow().setAttributes(layout);
			}
		});
		lv_dingdan.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//点击那个就把该内容标题显示到选择文本中
				tv_selecte_dinddan_modle.setText(list_dingdan_name.get(position));
				//取出对象中内容，对其他控件进行赋值
				setOrderData(position);
				popupwindown.setFocusable(false);
				popupwindown.dismiss();
				popupwindown = null;
				System.gc();
			}
		});
	}
	/**
	 * 自定义获取购买参数
	 */
	private Map<String, String> getBuyParam() {
		//新建购物参数对象
		buyParam = new HashMap<String, String>();
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
		buyParam.put("cementcode",lists.get(position).getYWM_Code());
		//品种名称
		buyParam.put("cementname",lists.get(position).getYWM_Name());
		//下单数量
		buyParam.put("ordernumber",tv_product_count.getText().toString());
		//下单价格
		buyParam.put("orderprice",lists.get(position).getYWM_Price());
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
	 * 购买操作完成时调用的方法
	 */
	public void buyCompleted(){
		//跳转到用户订单中心页面
		Intent intent = new Intent(this, HomeActivity.class);
		intent.putExtra("TAG", 3);
		startActivity(intent);
		Myapplication.toast("购买成功");
	}
}
