package com.example.wsd_client.activity;

import com.example.wsd_client.R;
import com.example.wsd_client.entity.XS_Order_ABase;
import com.example.wsd_client.util.CircleImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShowOrderActivity extends Activity implements OnClickListener{
	private CircleImageView civ;
	private ImageButton ibBack;
	private ImageButton ibCart;
	
	private XS_Order_ABase order_ABase;

	//tvSetDate:下单时间   tvPrice：产品单价  tvCountDisplay：下单数量
	//tvTotalPrice:总价
	private TextView tvTitle,tvSetDate,tvPrice,tvCountDisplay,tvTotalPrice,tvCementName;
	//etLadeID：订单编号  etCarName：司机姓名   etCarCode：司机驾驶证号
	//etCarPhone：司机电话   etOrderPhone：下单人电话
	private EditText etLadeID,etCarName,etCarID,etCarCode,etCarPhone,etOrderPhone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showorder);
		//初始化控件
		setViews();
		//设置监听
		setListener();
	}
	private void setListener() {
		ibBack.setOnClickListener(this);
		
	}
	//初始化控件
	private void setViews() {
		Intent intent=getIntent();
		order_ABase=(XS_Order_ABase) intent.getSerializableExtra("order_ABase");
		//头部的显示隐藏
				civ=(CircleImageView)findViewById(R.id.iv_contact);
				ibBack=(ImageButton)findViewById(R.id.ib_title_back);
				ibCart=(ImageButton)findViewById(R.id.ib_title_cart);

				tvTitle=(TextView) findViewById(R.id.tv_title);
				tvTitle.setText("已付款订单");
				civ.setVisibility(View.INVISIBLE);
				ibBack.setVisibility(View.VISIBLE);
				ibCart.setVisibility(View.INVISIBLE);
				
				
				//用于显示及编辑的文本初始化
				tvSetDate=(TextView) findViewById(R.id.tv_kuaisu_setDate);
				tvPrice=(TextView) findViewById(R.id.tv_kuaisu_price);
				tvCountDisplay=(TextView) findViewById(R.id.tv_kuaisu_count_display);

				tvCementName=(TextView) findViewById(R.id.tv_kuaisu_cementName);

				tvTotalPrice=(TextView)findViewById(R.id.tv_kuaisu_totalPrice);
				etLadeID=(EditText) findViewById(R.id.et_kuaisu_ladeID);
				etCarName=(EditText) findViewById(R.id.et_kuaisu_carName);
				etCarID=(EditText) findViewById(R.id.et_kuaisu_carID);
				etCarCode=(EditText) findViewById(R.id.et_kuaisu_carCode);
				etCarPhone=(EditText) findViewById(R.id.et_kuaisu_carPhone);
				etOrderPhone=(EditText) findViewById(R.id.et_kuaisu_orderPhone);
				
				
				
				
				etLadeID.setText(order_ABase.getXSO_LadeID());
				tvSetDate.setText(order_ABase.getXSO_SetDate());
				
				tvCementName.setText(order_ABase.getXSO_CementName());
			
				tvPrice.setText("￥"+order_ABase.getXSO_Price());

				tvCountDisplay.setText(order_ABase.getXSO_Number()+"");

				tvTotalPrice.setText("￥"+order_ABase.getXSO_TotalPrice());
				etCarName.setText(order_ABase.getXSO_CarName());
				etCarID.setText(order_ABase.getXSO_CarID());
				etCarCode.setText(order_ABase.getXSO_CarCode());
				etCarPhone.setText(order_ABase.getXSO_CarPhone());
				etOrderPhone.setText(order_ABase.getXSO_OrderPhone());
				
				
				
				//不能编辑
				etLadeID.setEnabled(false);
				etCarName.setEnabled(false);
				etCarCode.setEnabled(false);
				etCarPhone.setEnabled(false);
				etOrderPhone.setEnabled(false);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_title_back:
			finish();
			break;

		default:
			break;
		}
		
	}
}
