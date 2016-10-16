package com.example.wsd_client.fragment.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.MainActivity;
import com.example.wsd_client.adapter.KuaisuPpWindowAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.Product;
import com.example.wsd_client.entity.YW_ClientInfo;
import com.example.wsd_client.fragment.IKuaisuFragment;
import com.example.wsd_client.presenter.impl.KuaisuFragmentPresenterImpl;
import com.example.wsd_client.util.CircleImageView;
import com.example.wsd_client.util.DingdanOrderUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


@SuppressLint("SimpleDateFormat")
public class KuaisuFragment extends Fragment implements IKuaisuFragment,OnClickListener{
	//tvSetDate:下单时间   tvPrice：产品单价  tvCountDisplay：下单数量
	//tvTotalPrice:总价
	private TextView tvTitle,tvSetDate,tvPrice,tvCountDisplay,tvTotalPrice,tvCementName;
	private View view;
	private View viewPpWindow;
	private ListView lvPpWindow;
	private CircleImageView civ;
	private ImageButton ibBack;
	private ImageButton ibCart;
	//etLadeID：订单编号  etCarName：司机姓名   etCarCode：司机驾驶证号
	//etCarPhone：司机电话   etOrderPhone：下单人电话
	private EditText etLadeID,etCarName,etCarID,etCarCode,etCarPhone,etOrderPhone;
	private ImageView ivReduce,ivAdd;
	private Button btnSubmit;

	//通过presenter层获取model层加载的商品信息数据
	private List<Product> lists=new ArrayList<Product>();
	private KuaisuFragmentPresenterImpl presenter;
	private Map<String, String> urlParam;

	//通过Myapplication获取用户登录时保存的用户信息数据
	private YW_ClientInfo clientInfo;
	private List<ClientInfo> result;
	//用于封装lists集合里除去tvCementName显示内容的其他内容的集合
	private List<Product> removeList;
	private String time;
	private KuaisuPpWindowAdapter ppWindowAdapter;
	private PopupWindow popupWindow;
	//下单数量
	private int count=1;
	//popWindow：商品选择的用户点击位置
	private Product itemAtPosition;
	private double mul;

	/**
	 * 获取DingdanOrderUtil中，服务器传回来的数据
	 * 1.当传回来为null或者为0时，表示失败；2.若为1或更大的值时，表示成功
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Myapplication.log("快速页面传值msg:", msg+"");
			String str = (String) msg.obj;
			if(str.equals("null")||str.equals("0")){
				Myapplication.toast("下单失败，请检查该订单号是否已经存在！");
			}else if(str.equals("1")){
				Myapplication.toast("已成功下单！");
			}

		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_kuaisu, null);

		initView();

		setListener();

		return view;
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvTitle=(TextView) view.findViewById(R.id.tv_title);
		civ=(CircleImageView) view.findViewById(R.id.iv_contact);
		ibBack=(ImageButton) view.findViewById(R.id.ib_title_back);
		ibCart=(ImageButton) view.findViewById(R.id.ib_title_cart);
		tvTitle.setText("闪电下单");
		civ.setVisibility(View.INVISIBLE);
		ibBack.setVisibility(View.VISIBLE);
		ibCart.setVisibility(View.INVISIBLE);
		tvSetDate=(TextView) view.findViewById(R.id.tv_kuaisu_setDate);
		tvPrice=(TextView) view.findViewById(R.id.tv_kuaisu_price);
		tvCountDisplay=(TextView) view.findViewById(R.id.tv_kuaisu_count_display);
		tvTotalPrice=(TextView) view.findViewById(R.id.tv_kuaisu_totalPrice);
		etLadeID=(EditText) view.findViewById(R.id.et_kuaisu_ladeID);
		etCarName=(EditText) view.findViewById(R.id.et_kuaisu_carName);
		etCarID=(EditText) view.findViewById(R.id.et_kuaisu_carID);
		etCarCode=(EditText) view.findViewById(R.id.et_kuaisu_carCode);
		etCarPhone=(EditText) view.findViewById(R.id.et_kuaisu_carPhone);
		etOrderPhone=(EditText) view.findViewById(R.id.et_kuaisu_orderPhone);
		ivReduce=(ImageView) view.findViewById(R.id.iv_kuaisu_reduce);
		ivAdd=(ImageView) view.findViewById(R.id.iv_kuaisu_add);
		btnSubmit=(Button) view.findViewById(R.id.btn_kuaisu_submit);
		tvCementName=(TextView) view.findViewById(R.id.tv_kuaisu_cementName);

		//获取商品信息
		urlParam = new HashMap<String, String>();
		urlParam.put("pageNum", "0");
		urlParam.put("pageSize", "100");
		presenter = new KuaisuFragmentPresenterImpl(this, urlParam);
		presenter.loadData();

	}

	/**
	 * 配置监听
	 */
	private void setListener() {
		ibBack.setOnClickListener(this);
		ivAdd.setOnClickListener(this);
		ivReduce.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		tvCementName.setOnClickListener(this);
	}


	@Override
	public void setDataProduct(List<Product> list) {
		lists.addAll(list);
		//默认显示list集合中的第一个数据
		tvCementName.setText(lists.get(0).getYWM_Name());

		itemAtPosition=lists.get(0);
		double d = Double.parseDouble(itemAtPosition.getYWM_Price());

		mul = mul(d, count);
		tvTotalPrice.setText("￥"+mul+"元");


		Myapplication.log("获取modle层加载的产品信息数据的数量：", lists.size()+"");
		//用于封装lists集合里除去tvCementName显示内容的其他内容的集合
		removeList = new ArrayList<Product>();
		removeList.addAll(list);
		removeList.remove(0);

		Myapplication.log("获取modle层加载的产品信息数据的数量及removeList的长度：", lists.size()+"---------"+removeList.size());



	}

	@Override
	public void setDataUserInfo() {
		Myapplication app=(Myapplication) Myapplication.getContext();
		clientInfo = app.getClientInfo();
		result = clientInfo.getResult();

	}

	@Override
	public void showData() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=new Date();
		time = sdf.format(date);
		tvSetDate.setText(time);

	}

	@Override
	public void onClick(View v) {

		double a=Double.parseDouble(itemAtPosition.getYWM_Price());

		switch (v.getId()) {
		case R.id.ib_title_back:
			Intent intent = new Intent (getActivity(),MainActivity.class);
			startActivity(intent);
			//销毁当前碎片所在的Activity
			getActivity().finish();
			break;

		case R.id.iv_kuaisu_add:
			count+=1;
			tvCountDisplay.setText(count+"");
			mul = mul(a, count);
			tvTotalPrice.setText("￥"+mul+"元");
			break;

		case R.id.iv_kuaisu_reduce:
			if(count>1){
				count-=1;
				tvCountDisplay.setText(count+"");
				mul = mul(a, count);
				tvTotalPrice.setText("￥"+mul+"元");
			}
			break;

		case R.id.btn_kuaisu_submit:

			login();

			break;

		case R.id.tv_kuaisu_cementName:

			showPopWindow(v);

			break;
		default:
			break;
		}

	}

	/**
	 * popupWindow显示
	 * @param v
	 */

	@SuppressWarnings("deprecation")
	private void showPopWindow(View v) {
		if(popupWindow==null){
			LayoutInflater layoutInflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewPpWindow = layoutInflater.inflate(R.layout.popup_window_listview, null);
			lvPpWindow = (ListView) viewPpWindow.findViewById(R.id.lv_popup_window);

			Myapplication.log("ppWindow适配开始：", "111111111111111111111111111");

			ppWindowAdapter = new KuaisuPpWindowAdapter(getActivity(), removeList);
			lvPpWindow.setAdapter(ppWindowAdapter);

			Myapplication.log("ppWindow适配结束：", "222222222222222222222222");
			//创建一个PopupWindow对象
			popupWindow=new PopupWindow(viewPpWindow,tvCementName.getMeasuredWidth(),android.view.ViewGroup.LayoutParams.WRAP_CONTENT,true);
		}

		//使其聚焦
		popupWindow.setFocusable(true);
		//设置允许在外点击和点击返回键消失
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		//设置显示位置,在tvPpWindow下方
		popupWindow.showAsDropDown(tvCementName);

		lvPpWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				itemAtPosition = (Product) removeList.get(position);
				tvPrice.setText("￥"+itemAtPosition.getYWM_Price()+"元");

				count=1;
				tvCountDisplay.setText("1");
				double c=Double.parseDouble(itemAtPosition.getYWM_Price());
				mul = mul(c, count);
				tvTotalPrice.setText("￥"+mul+"元");

				removeList.clear();
				Myapplication.log("removeList.clear()的数据长度：", removeList.size()+"");
				ppWindowAdapter.notifyDataSetChanged();
				tvCementName.setText(itemAtPosition.getYWM_Name());
				removeList.addAll(lists);
				removeList.remove(itemAtPosition);


				if (popupWindow != null) {
					popupWindow.dismiss();
				}

			}
		});


	}

	/**
	 * 登录时调用，将信息传入到DingdanOrderUtil()中，传入handler值，并让其赋值传出，获取服务器的响应结果
	 * etLadeID：订单编号  etCarName：司机姓名   etCarCode：司机驾驶证号
	 * etCarPhone：司机电话   etOrderPhone：下单人电话
	 */
	private void login() {
		setDataUserInfo();

		String ladeID=etLadeID.getText().toString().trim();
		String carName=etCarName.getText().toString();
		String carID=etCarID.getText().toString();
		String carCode=etCarCode.getText().toString();
		String carPhone=etCarPhone.getText().toString();
		String orderPhone=etOrderPhone.getText().toString();

		Map<String, String> map=new HashMap<String, String>();
		map.put("clientid", result.get(0).getYWC_ClientID()+"");
		map.put("ladeid", ladeID);
		map.put("areacode", result.get(0).getYWC_AreaCode());
		map.put("ladetype", "APP");
		map.put("cementcode", itemAtPosition.getYWM_Code());
		map.put("cementname", itemAtPosition.getYWM_Name());
		map.put("ordernumber", tvCountDisplay.getText().toString());
		map.put("orderprice", itemAtPosition.getYWM_Price());
		map.put("totalprice", mul+"");
		map.put("carname", carName);
		map.put("carid", carID);
		map.put("carcode", carCode);
		map.put("carphone", carPhone);
		map.put("orderphone", orderPhone);
		map.put("status", 0+"");

		DingdanOrderUtil dou=new DingdanOrderUtil(handler);
		dou.addOrder(map);
	}

	/**
	 * 精确计算double类型数据的乘法
	 */
	public static double mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
}
