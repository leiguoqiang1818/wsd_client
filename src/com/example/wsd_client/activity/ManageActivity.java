package com.example.wsd_client.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import com.example.wsd_client.R;
import com.example.wsd_client.adapter.AccountFragmentPpWindowAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.XS_Order_ABase;
import com.example.wsd_client.urlUtils.UrlPath;
import com.example.wsd_client.util.CircleImageView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ManageActivity extends Activity implements OnClickListener{

	//封装popupWindow要显示的数据
	private List<String> list;
	private ListView lvPpWindow;
	private View viewPpWindow;
	private PopupWindow popupWindow;
	private AccountFragmentPpWindowAdapter ppWindowAdapter;
	private String itemAtPosition;
	//用于封装除list集合中除了tvChoose中显示内容外的其他内容
		private List<String> removeList;
	
	
	private CircleImageView civ;
	private ImageButton ibBack;
	private ImageButton ibCart;

	//修改删除按钮
	private Button btn_edit,btn_delete;

	private XS_Order_ABase order_ABase;
	/**
	 * 计数加减按钮
	 */
	private ImageView iv_add,iv_reduce;
	//tvSetDate:下单时间   tvPrice：产品单价  tvCountDisplay：下单数量
	//tvTotalPrice:总价
	private TextView tvTitle,tvSetDate,tvPrice,tvCountDisplay,tvTotalPrice,tvCementName;
	//etLadeID：订单编号  etCarName：司机姓名   etCarCode：司机驾驶证号
	//etCarPhone：司机电话   etOrderPhone：下单人电话
	private EditText etLadeID,etCarName,etCarID,etCarCode,etCarPhone,etOrderPhone;

	//计算 商品的数量和总价
	int count=0;
	double c=0.0;
	 double mul=0.0;
	//
	private Myapplication myapplication=(Myapplication) getApplication();
	//存放地址集合
	private String urlParam;

	//dialog
	private AlertDialog.Builder builder;

	int positon;

	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			//点击确定删除
			case 0:
				String delete=(String) msg.obj;
				Myapplication.toast(delete);
				Bundle bundle=new Bundle();
				bundle.putInt("positon", positon);
				Intent intent=getIntent();
				intent.putExtra("positon", bundle);
				setResult(6,intent);
				finish();
				break;
				//点击确定修改
			case 1:
				String edit=(String) msg.obj;
				if(edit!=null){
					Myapplication.toast("不存在对应的单据号，不能修改");
				}else {
					Myapplication.toast("修改完成");
					Bundle bundle2=new Bundle();
					bundle2.putInt("positon", positon);
					Intent intent2=new Intent();
					intent2.putExtra("positon", bundle2);
					setResult(6,intent2);
					finish();
				}

				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage);

		//x.view().inject(this);
		//初始化控件
		initView();
		//控件赋值
		setView();
		//设置监听
		setListener();
	}

	/**
	 * 显示赋值
	 */
	private void setView() {
		etLadeID.setText(order_ABase.getXSO_LadeID());
		tvSetDate.setText(order_ABase.getXSO_SetDate());
		tvCementName.setText(order_ABase.getXSO_CementName());
		
		tvPrice.setText("￥"+order_ABase.getXSO_Price());

		tvCountDisplay.setText(count+"");

		tvTotalPrice.setText("￥"+order_ABase.getXSO_TotalPrice());
		etCarName.setText(order_ABase.getXSO_CarName());
		etCarID.setText(order_ABase.getXSO_CarID());
		etCarCode.setText(order_ABase.getXSO_CarCode());
		etCarPhone.setText(order_ABase.getXSO_CarPhone());
		etOrderPhone.setText(order_ABase.getXSO_OrderPhone());
		//司机姓名定位
		etCarName.setFocusable(true);
		//不能编辑
		etLadeID.setEnabled(false);
		//etLadeID.setKeyListener(null);
		
		//设置二种选择方式的内容集合
		list=new ArrayList<String>();
		list.add(order_ABase.getXSO_CementName());
		list.add("PC42.5R袋装");
		list.add("PC32.5袋装");
		list.add("散装42.5");

		//封装移除后的数据
		removeList = new ArrayList<String>();
		removeList.addAll(list);
		removeList.remove(0);
		//设置默认显示内容
		tvCementName.setText(list.get(0));
		itemAtPosition="未付款";

	}
	/**
	 * 初始化控件
	 */
	private void initView() {
		//显示隐藏部分，由于加载同一个布局文件
		
		tvCementName=(TextView)findViewById(R.id.tv_kuaisu_cementName);
	
		tvCementName.setVisibility(View.VISIBLE);
	
		
		//头部的显示隐藏
		civ=(CircleImageView)findViewById(R.id.iv_contact);
		ibBack=(ImageButton)findViewById(R.id.ib_title_back);
		ibCart=(ImageButton)findViewById(R.id.ib_title_cart);

		tvTitle=(TextView) findViewById(R.id.tv_title);
		tvTitle.setText("订单管理");
		civ.setVisibility(View.INVISIBLE);
		ibBack.setVisibility(View.VISIBLE);
		ibCart.setVisibility(View.INVISIBLE);
		
		
		
		
		
		//用于显示及编辑的文本初始化
		tvSetDate=(TextView) findViewById(R.id.tv_kuaisu_setDate);
		tvPrice=(TextView) findViewById(R.id.tv_kuaisu_price);
		tvCountDisplay=(TextView) findViewById(R.id.tv_kuaisu_count_display);



		tvTotalPrice=(TextView)findViewById(R.id.tv_kuaisu_totalPrice);
		etLadeID=(EditText) findViewById(R.id.et_kuaisu_ladeID);
		etCarName=(EditText) findViewById(R.id.et_kuaisu_carName);
		etCarID=(EditText) findViewById(R.id.et_kuaisu_carID);
		etCarCode=(EditText) findViewById(R.id.et_kuaisu_carCode);
		etCarPhone=(EditText) findViewById(R.id.et_kuaisu_carPhone);
		etOrderPhone=(EditText) findViewById(R.id.et_kuaisu_orderPhone);
		//添加减少按钮
		iv_reduce=(ImageView) findViewById(R.id.iv_kuaisu_reduce);
		iv_add=(ImageView) findViewById(R.id.iv_kuaisu_add);
		//添加修改删除按钮
		btn_edit=(Button) findViewById(R.id.btn_kuaisu_edit);
		btn_delete=(Button) findViewById(R.id.btn_kuaisu_delete);

		Intent intent=getIntent();
		order_ABase=(XS_Order_ABase) intent.getSerializableExtra("order_ABase");
		positon=intent.getIntExtra("position",0);
		
		
		
		Myapplication.log("订单消息内容",order_ABase+""+positon );

		//初始化商品单价转换为double类型

		//精确的问题
		c=Double.parseDouble(order_ABase.getXSO_Price());
		String []a=order_ABase.getXSO_Number().split("\\.");
		String b=a[0];
		count=Integer.parseInt(b);


		Myapplication.log("老子验证你都要用log", count+"");
	}

	/**
	 * 按钮监听事件
	 */
	private void setListener() {
		ibBack.setOnClickListener(this);
		iv_add.setOnClickListener(this);
		iv_reduce.setOnClickListener(this);
		btn_edit.setOnClickListener(this);
		btn_delete.setOnClickListener(this);
		tvCementName.setOnClickListener(this);
	}
	/**
	 * 精确计算double类型数据的乘法
	 */
	public static double mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		//返回按钮
		case R.id.ib_title_back:
			//不需要intent实现跳转
			//直接销毁当前activity
			finish();
			break;
		//添加按钮
		case R.id.iv_kuaisu_add:
			if(count>=1){
				count++;
			}

			tvCountDisplay.setText(""+count);
			mul=mul(c,count);
			tvTotalPrice.setText("￥"+mul);
			break;
			//删除按钮
		case R.id.iv_kuaisu_reduce:
			if(count>1){
				count--;
			}
			tvCountDisplay.setText(""+count);
			mul=mul(c,count);
			tvTotalPrice.setText("￥"+mul);
			break;
			//修改按钮
		case R.id.btn_kuaisu_edit:
			builder=new Builder(ManageActivity.this);
			builder.setMessage("确定修改该订单吗?");
			builder.setTitle("提示");
			builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					int clientid=order_ABase.getXSO_ClientID();//客户id
					String ladeid=order_ABase.getXSO_LadeID();//订单编号
					String areacode=order_ABase.getXSO_AreaCode();//销售区域
					
					String ladetype=order_ABase.getXSO_LadeType();//订单类型
					if(ladetype==null){
						ladetype="APP";
					}
					String cementcode=order_ABase.getXSO_CementCode();//品种代码
					String cementname=order_ABase.getXSO_CementName();//品种名称
				
					String ordernumber=tvCountDisplay.getText().toString()+".0";//下单数量
					String orderprice=order_ABase.getXSO_Price();//下单价格
					String totalprice=tvTotalPrice.getText().toString().substring(1, tvTotalPrice.getText().toString().length());//总金额
					String carname=etCarName.getText().toString();//拉货司机名称
					String carid=etCarID.getText().toString();//拉货司机驾驶证号
					String carcode=etCarCode.getText().toString();//拉货车牌号
					String carphone=etCarPhone.getText().toString();//司机电话
					String orderphone=etOrderPhone.getText().toString();//下单人员联系电话
					
					int status=0;//目前默认给它为0	
					
					Myapplication.log("转码后", cementname);
					urlParam=UrlPath.ORDER_EDIT+"?clientid="+clientid
							+"&ladeid="+ladeid
							+"&areacode="+areacode
							+"&ladetype="+"APP"
							+"&cementcode="+cementcode
							+"&cementname="+cementname
							+"&ordernumber="+ordernumber
							+"&orderprice="+orderprice
							+"&totalprice="+totalprice
							+"&carname="+carname
							+"&carid="+carid
							+"&carcode="+carcode
							+"&carphone="+carphone
							+"&orderphone="+orderphone
							+"&status="+status
							;
				
					new Thread(new Runnable() {
						StringBuffer sb=new StringBuffer();
						@Override
						public void run() {
							try {
								Myapplication.log("网址", urlParam);
								URL url = new URL(urlParam);
								HttpURLConnection conn=(HttpURLConnection) url.openConnection();
								conn.setConnectTimeout(5000);
								conn.setRequestMethod("GET");
								conn.connect();
								InputStream is=conn.getInputStream();
								InputStreamReader isr=new InputStreamReader(is);
								BufferedReader reader=new BufferedReader(isr);
								String line=reader.readLine();
								while(line!=null){
									sb.append(line);
									Myapplication.log("-----------", "sb:"+sb.toString());
									line=reader.readLine();
								}
								JSONObject object=new JSONObject(sb.toString());
								JSONObject object2=object.getJSONObject("status");
								String reason=object2.getString("status_reason");
								URLEncoder.encode(reason, "UTF-8");
								Myapplication.log("转码后的", reason);
								Message message=new Message();
								message.what=1;
								message.obj=reason;
								Myapplication.log("获取字段是否正确", reason);
								handler.sendMessage(message);
								
								is.close();
								isr.close();
								reader.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
					dialog.dismiss();
				}
			});
			builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
			break;
			//删除按钮
		case R.id.btn_kuaisu_delete:
			builder=new AlertDialog.Builder(ManageActivity.this);
			builder.setMessage("确定删除该订单吗?");
			builder.setTitle("提示");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(final DialogInterface dialog, int which) {
					int clientid=order_ABase.getXSO_ClientID();
					int orderdid=order_ABase.getXSO_OrderID();
					urlParam=UrlPath.ORDER_DELETE+"?orderid="+orderdid+"&clientid="+clientid;
					new Thread(new Runnable() {
						StringBuffer sb=new StringBuffer();
						@Override
						public void run() {
							try {
								URL url = new URL(urlParam);
								HttpURLConnection conn=(HttpURLConnection) url.openConnection();
								conn.setConnectTimeout(5000);
								conn.setRequestMethod("GET");
								conn.connect();
								InputStream is=conn.getInputStream();
								InputStreamReader isr=new InputStreamReader(is);
								BufferedReader reader=new BufferedReader(isr);
								String line=reader.readLine();
								while(line!=null){
									sb.append(line);
									Myapplication.log("-----------", "sb:"+sb.toString());
									line=reader.readLine();
								}
								JSONObject object=new JSONObject(sb.toString());

								String result=object.getString("result");
							
								Message message=new Message();
								message.what=0;
								message.obj=result;
								Myapplication.log("获取字段是否正确", result);
								handler.sendMessage(message);

								is.close();
								isr.close();
								reader.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
					dialog.dismiss();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
			break;
		//	
		case R.id.tv_kuaisu_cementName:
			showPopWindow(v);
			break;
		}
	}
	  private void showPopWindow(View v) {
		  if(popupWindow==null){
				LayoutInflater layoutInflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				viewPpWindow = layoutInflater.inflate(R.layout.popup_window_listview, null);
				lvPpWindow = (ListView) viewPpWindow.findViewById(R.id.lv_popup_window);
				Myapplication.log("ppWindow适配开始：", "111111111111111111111111111");
				ppWindowAdapter = new AccountFragmentPpWindowAdapter(this, removeList);
				lvPpWindow.setAdapter(ppWindowAdapter);
				Myapplication.log("ppWindow适配结束：", "222222222222222222222222");
				//创建一个PopupWindow对象
				popupWindow=new PopupWindow(viewPpWindow,tvCementName.getMeasuredWidth(),LayoutParams.WRAP_CONTENT,true);
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
					itemAtPosition =  removeList.get(position);
					tvCementName.setText(itemAtPosition);
					removeList.clear();
					Myapplication.log("removeList.clear()的数据长度：", removeList.size()+"");
					ppWindowAdapter.notifyDataSetChanged();
					tvCementName.setText(itemAtPosition);
					removeList.addAll(list);
					removeList.remove(itemAtPosition);
					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			});
		
	}

	/* 
     * 对json字符串进行过滤,防止乱码和黑框 
     */  
    public static String JsonFilter(String jsonstr){  
        return jsonstr.substring(jsonstr.indexOf("{")).replace("\r\n","\n");   
    }  
}
