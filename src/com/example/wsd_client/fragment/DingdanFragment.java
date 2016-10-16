package com.example.wsd_client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.ManageActivity;
import com.example.wsd_client.activity.ShowOrderActivity;
import com.example.wsd_client.adapter.AccountFragmentPpWindowAdapter;
import com.example.wsd_client.adapter.DingdanAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.XS_Order_ABase;
import com.example.wsd_client.presenter.impl.DingdanFragmentPrensenterImpl;
import com.example.wsd_client.urlUtils.UrlPath;
import com.example.wsd_client.util.CircleImageView;
import com.example.wsd_client.util.LoadMoreModel;
import com.example.wsd_client.util.CalendarViewUtil.DateUtils;
import com.example.wsd_client.util.CalendarViewUtil.MonthDateView.DateClick;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;





public class DingdanFragment extends Fragment implements IDingdanFragment,OnClickListener{
	//没有数据下显示
	private TextView tv_order;
	//头部
	private TextView tvTitle;
	private	CircleImageView civ;
	private ImageButton ibBack,ibCart;
	
	//时间选择
	private Myapplication app;

	private View viewPpWindow;
	private ListView lvPpWindow;
	private PopupWindow popupWindow;
	private AccountFragmentPpWindowAdapter ppWindowAdapter;
	private String itemAtPosition;

	//listView适配器
	private DingdanAdapter adapter;

	private TextView tvStarttime,tvEndtime,tvChoose,tvNoAccount;
	private Button btnSearch;
	private ListView lv_dingdan;
	private View view;

	//封装popupWindow要显示的数据
	private List<String> list;
	//当前月份
	private Integer month;
	//当前年份
	private Integer year;
	//当前天数
	private Integer day;
	//客户id
	private int clientid;

	//自定义Dialog的控件初始化
	private ImageView ivLeft;
	private ImageView ivRight;
	private TextView tvDate;
	private TextView tvWeek;
	private TextView tvToday;
	private com.example.wsd_client.util.CalendarViewUtil.MonthDateView monthDateView;
	private Button btnEnsure;
	private Button btnCancel;

	//根据j的赋值，当j=1时，表示客户选择开始时间；当j=2时，表示客户选择结束时间
	private int j;

	//用于封装除list集合中除了tvChoose中显示内容外的其他内容
	private List<String> removeList;

	private Map<String, String> urlParam;

	private AlertDialog dialog;



	private DingdanFragmentPrensenterImpl presenter;
	private List<XS_Order_ABase>lists=new ArrayList<XS_Order_ABase>();
	private List<XS_Order_ABase>new_lists=new ArrayList<XS_Order_ABase>();
	XS_Order_ABase order_ABase;
	private LoadMoreModel model;

	//初始化的一些参数
	int count=0;
	String starttime;
	String endtime;
	int xsstatus=0;
	String finaltime;
	boolean atbottom=false; 
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				List<XS_Order_ABase>list=(List<XS_Order_ABase>) msg.obj;
				Log.d("既然获取到了，有没有传出来呢", list.size()+"");
				lists.addAll(list);
				//Collections.reverse(lists);
				Log.d("既然获取到了，有没有传出来呢", lists.size()+"");
				//Collections.reverse(lists);
				adapter.notifyDataSetChanged();
				  //判断请求回的数据集合是否存在数据
				   if(list.size()<1){
					   //没有数据弹出提示
					 Myapplication.toast("已经没有可加载的数据了");
					   
				   }
				break;

			default:
				break;
			}
		};
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_dingdan, null);

		initView();
		model=new LoadMoreModel();
		initData();

		setListener();


		return view;
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		
		//用与没有有数据显示
		tv_order=(TextView) view.findViewById(R.id.tv_order);
		tv_order.setVisibility(view.INVISIBLE);
		
		
		tvTitle=(TextView) view.findViewById(R.id.tv_title);
		civ=(CircleImageView) view.findViewById(R.id.iv_contact);
		ibBack=(ImageButton) view.findViewById(R.id.ib_title_back);
		ibCart=(ImageButton) view.findViewById(R.id.ib_title_cart);
		tvTitle.setText("订单中心");
		civ.setVisibility(View.INVISIBLE);
		ibBack.setVisibility(View.VISIBLE);
		ibCart.setVisibility(View.INVISIBLE);
		
		
		tvStarttime=(TextView) view.findViewById(R.id.tv_dingdan_startTime);
		tvEndtime=(TextView) view.findViewById(R.id.tv_dingdan_endTime);
		tvChoose=(TextView) view.findViewById(R.id.tv_dingdan_choose);
		tvNoAccount=(TextView) view.findViewById(R.id.tv_order);
		btnSearch=(Button) view.findViewById(R.id.btn_dingdan_choose);
		lv_dingdan=(ListView) view.findViewById(R.id.lv_dingdan);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {

		//获取当前系统时间
		Date date=new Date(System.currentTimeMillis());
		
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

		String time=format.format(date);
		Myapplication.log("当前系统时间time：", time);
		//截取当前的年份和月数
		String yearString=time.substring(0, 4);
		//year = Integer.parseInt(yearString); 

		String monthString=time.substring(5, 7);
		//month = Integer.parseInt(monthString);
		//Myapplication.log("当前的年year和月month：", year+"-------"+month);

		String dayString=time.substring(8, 10);
		day = Integer.parseInt(dayString)+1;

		//获取当前的客户id
		app=(Myapplication) Myapplication.getContext();

		clientid =app.getClientInfo().getResult().get(0).getYWC_ClientID();

		starttime=yearString+"-"+monthString+"-01";
		endtime=yearString+"-"+monthString+"-"+day;
		
		tvStarttime.setText(starttime);
		tvEndtime.setText(time);

		//设置二种选择方式的内容集合
		list=new ArrayList<String>();
		list.add("未付款");
		list.add("已付款");


		//封装移除后的数据
		removeList = new ArrayList<String>();
		removeList.addAll(list);
		removeList.remove(0);
		//设置默认显示内容
		tvChoose.setText(list.get(0));
		itemAtPosition="未付款";

		urlParam = new HashMap<String, String>();
		urlParam.put("pageNum", count+"");
		urlParam.put("pageSize", "10");
		urlParam.put("starttime",starttime);
		urlParam.put("endtime",endtime);
		urlParam.put("clientid", clientid+"");
		urlParam.put("xsstatus", "0");
		Myapplication.log("accountInfoUtil:", "网络请求开始");
		//accountInfoUtil.getAccountInfo(map);
		presenter=new DingdanFragmentPrensenterImpl(this, urlParam);
		presenter.loadData();
		Myapplication.log("accountInfoUtil:", "网络请求结束");
	}


	/**
	 * 监听按钮点击事件
	 */
	private void setListener() {
		tvStarttime.setOnClickListener(this);
		tvEndtime.setOnClickListener(this);
		tvChoose.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		ibBack.setOnClickListener(this);
		//订单条目点击事件
		lv_dingdan.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Myapplication.log("xxxx", xsstatus+"");
				if(xsstatus==0){
					Intent intent=new Intent(getActivity(), ManageActivity.class);
				order_ABase=lists.get(position);

				intent.putExtra("order_ABase",order_ABase );
				intent.putExtra("position", position);
				Myapplication.log("position", position+"");
				startActivityForResult(intent, 6);
				}else if(xsstatus==1){
					Intent intent=new Intent(getActivity(),ShowOrderActivity.class);
					order_ABase=lists.get(position);

					intent.putExtra("order_ABase",order_ABase );
					startActivity(intent);
				}
				
			}
		});
		//订单列表滑动事件
		//即上拉加载更多
		lv_dingdan.setOnScrollListener(new OnScrollListener() {
			
			//声明布尔类型变量-->监控我们是否滑动到末尾
			boolean atbottom=false;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				//三种状态
				//SCROLL_STATE_FLING
				//SCROLL_STATE_IDLE
				//SCROLL_STATE_TOUCH_SCROLL
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_FLING:

					break;
					//让listview 空闲的时候更新数据
				case OnScrollListener.SCROLL_STATE_IDLE:
					// 向服务器发送请求;
					if(atbottom){
					count++;
					new Thread(new Runnable() {
						
						String url =UrlPath.ORDERS_CENTRE
								+"?pageNum="+count
								+"&pageSize="+"10"
								+"&starttime="+starttime
								+"&endtime="+endtime
								+"&clientid="+clientid
								+"&xsstatus="+xsstatus
								;
						
						@Override
						public void run() {
							Log.d("刷新穿入的参数校验", "开始时间"+starttime+"结束时间"+endtime);
							Log.d("地址", url);
							model=new LoadMoreModel();
								new_lists=model.Loadmoredata(url);
								Message message=handler.obtainMessage(0, new_lists);
								Log.d("既然获取到了，有没有传出来呢", new_lists.size()+"");
								handler.sendMessage(message);
						}
					}).start();

					}
				
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:

					break;
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//如何判断已经滚动 listview末尾
				if(firstVisibleItem+
						visibleItemCount==totalItemCount){
					atbottom=true;//通知liextview空闲更新
				}else{
					atbottom=false;
				}

			}
		});
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==6&&resultCode==6){
			//String orderid=data.getStringExtra("orderid");
			Bundle bundle=data.getBundleExtra("positon");
			int index=bundle.getInt("positon");
			Myapplication.log("返回来的下标值", index+"");
			lists.remove(index);
			adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_dingdan_startTime:
			//当客户选择开始时间时，将j设置成1
			j=1;
			getTimeDialog();
			break;

		case R.id.tv_dingdan_endTime:
			//当客户选择开始时间时，将j设置成1
			j=2;
			getTimeDialog();
			break;
			//付款状态
		case R.id.tv_dingdan_choose:

			showPopWindow(v);
			break;
			//查询
		case R.id.btn_dingdan_choose:

			search();
			break;
			//返回
		case R.id.ib_title_back:
			getActivity().finish();
			break;
		}

	}  


	/**
	 * 显示自定义AlertDialog
	 * i:当传入为1时，表示客户选择开始时间    当传入为2时，表示客户选择结束时间
	 */
	private void getTimeDialog() {
		AlertDialog.Builder builder= new AlertDialog.Builder(getActivity(), R.style.add_dialog);

		View viewCalendar=LayoutInflater.from(getActivity()).inflate(R.layout.alertdialog_date, null);
		dialog = builder.create();
		dialog.setView(viewCalendar, 0, 0, 0, 0);

		ivLeft=(ImageView) viewCalendar.findViewById(R.id.iv_left);
		ivRight=(ImageView) viewCalendar.findViewById(R.id.iv_right);
		tvToday=(TextView) viewCalendar.findViewById(R.id.tv_today);
		tvWeek=(TextView) viewCalendar.findViewById(R.id.week_text);
		tvDate=(TextView) viewCalendar.findViewById(R.id.date_text);
		btnEnsure=(Button) viewCalendar.findViewById(R.id.btn_dialog_ensure);
		btnCancel=(Button) viewCalendar.findViewById(R.id.btn_dialog_cancel);
		monthDateView= (com.example.wsd_client.util.CalendarViewUtil.MonthDateView) viewCalendar.findViewById(R.id.monthDateView);

		monthDateView.setTextView(tvDate, tvWeek);
		dialog.show();

		//设置dialog的大小，一定要放在show()方法的后面
		android.view.WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
		attributes.height=view.getHeight()-view.getHeight()/8;
		attributes.width=view.getWidth()-view.getWidth()/8;
		dialog.getWindow().setAttributes(attributes);

		//设置dialog的显示位置
		Window window=dialog.getWindow();
		window.setGravity(Gravity.CENTER);

		//设置按空白部分能够取消对话框
		dialog.setCanceledOnTouchOutside(true);

		monthDateView.setDateClick(new DateClick() {

			public void onClickOnDate() {
				//当前时间的点击事件
			}
		});

		Myapplication.log("看看有没有执行到这一步", "有执行到这一步的---------");

		setOnlistener();

	}

	private void setOnlistener(){
		ivLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				monthDateView.onLeftClick();
			}
		});

		ivRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				monthDateView.onRightClick();
			}
		});

		tvToday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				monthDateView.setTodayToView();
			}
		});

		btnEnsure.setOnClickListener(new OnClickListener() {

			private int yearChoose;
			private int monthChoose;
			private int dayChoose;

			@Override
			public void onClick(View v) {

				if(j==1){
					yearChoose = monthDateView.getmSelYear();
					monthChoose = monthDateView.getmSelMonth()+1;
					dayChoose = monthDateView.getmSelDay();

					//获取当前显示的结束时间
					String eTime=tvEndtime.getText().toString();

					String [] i=eTime.split("[-]");
					String eYear=i[0];
					String eMonth=i[1];
					String eDay=i[2];

					Integer endyear=Integer.parseInt(eYear);
					Integer endmonth=Integer.parseInt(eMonth);
					Integer endday=Integer.parseInt(eDay);

					Myapplication.log("看看截取的是不是正确的：", endyear+"-"+endmonth+"-"+endday);

					//当开始日期早于当前显示的结束日期时，才能执行查询功能
					if(yearChoose<endyear||yearChoose==endyear&&monthChoose<endmonth||yearChoose==endyear&&monthChoose==endmonth&&dayChoose<=endday){
						if(endmonth<10){
							
						}
						tvStarttime.setText(yearChoose+"-"+monthChoose+"-"+dayChoose);
						//退出对话框
						dialog.dismiss();
					}else{
						Myapplication.toast("开始日期不得晚于结束日期，请重新选择开始日期！");
					}

				}else if(j==2){
					yearChoose= monthDateView.getmSelYear();
					monthChoose = monthDateView.getmSelMonth()+1;
					Myapplication.log("月份", monthChoose+"");
					dayChoose = monthDateView.getmSelDay();

					//获取当前显示的开始时间
					String sTime=tvStarttime.getText().toString();

					String [] i=sTime.split("[-]");
					String sYear=i[0];
					String sMonth=i[1];
					String sDay=i[2];

					Integer startyear=Integer.parseInt(sYear);
					Integer startmonth=Integer.parseInt(sMonth);
					Integer startday=Integer.parseInt(sDay);
					
//					 if(month == 12){//若果是12月份，则变成1月份
//				            year = mSelYear+1;
//				            month = 0;
//				        }else if(DateUtils.getMonthDays(year, month) == day){
//				            //如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
//				            month = month + 1;
//				            day = DateUtils.getMonthDays(year, month);
//				        }else{
//				            month = month + 1;
//				        }
				
					
					
					
					Myapplication.log("再看看截取的是不是正确的：", startyear+"-"+startmonth+"-"+startday);

					//当开始日期早于当前显示的结束日期时，才能执行查询功能
					if(yearChoose>startyear||yearChoose==startyear&&monthChoose>startmonth||yearChoose==startyear&&monthChoose==startmonth&&dayChoose>=startday){

						tvEndtime.setText(yearChoose+"-"+monthChoose+"-"+(dayChoose));
						//退出对话框
						dialog.dismiss();
					}else{
						Myapplication.toast("结束日期不得早于开始日期，请重新选择结束日期！");
					}
					int k=DateUtils.getMonthDays(yearChoose, monthChoose-1);
					Myapplication.log("k", k+"");
					if(DateUtils.getMonthDays(yearChoose, monthChoose-1)==dayChoose&&monthChoose!=12){
						monthChoose=monthChoose+1;
						dayChoose=1;
					}else if (monthChoose==12&&DateUtils.getMonthDays(yearChoose, 11)==dayChoose) {
						monthChoose=1;
						yearChoose=yearChoose+1;
						dayChoose=1;
					}else {
						dayChoose=dayChoose+1;
					}
					
					//Myapplication.log("选择后的时间校验", endtime);
					endtime=yearChoose+"-"+monthChoose+"-"+dayChoose;
					Myapplication.log("选择后的时间校验", endtime);
				}

			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//退出对话框
				dialog.dismiss();
			}
		});
	}
	@SuppressWarnings("deprecation")
	private void showPopWindow(View v) {
		if(popupWindow==null){
			LayoutInflater layoutInflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewPpWindow = layoutInflater.inflate(R.layout.popup_window_listview, null);
			lvPpWindow = (ListView) viewPpWindow.findViewById(R.id.lv_popup_window);
			Myapplication.log("ppWindow适配开始：", "111111111111111111111111111");
			ppWindowAdapter = new AccountFragmentPpWindowAdapter(getActivity(), removeList);
			lvPpWindow.setAdapter(ppWindowAdapter);
			Myapplication.log("ppWindow适配结束：", "222222222222222222222222");
			//创建一个PopupWindow对象
			popupWindow=new PopupWindow(viewPpWindow,tvChoose.getMeasuredWidth(),LayoutParams.WRAP_CONTENT,true);
		}
		//使其聚焦
		popupWindow.setFocusable(true);
		//设置允许在外点击和点击返回键消失
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		//设置显示位置,在tvPpWindow下方
		popupWindow.showAsDropDown(tvChoose);
		lvPpWindow.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				itemAtPosition =  removeList.get(position);
				tvChoose.setText(itemAtPosition);
				removeList.clear();
				Myapplication.log("removeList.clear()的数据长度：", removeList.size()+"");
				ppWindowAdapter.notifyDataSetChanged();
				tvChoose.setText(itemAtPosition);
				removeList.addAll(list);
				removeList.remove(itemAtPosition);
				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}
		});
	}
	/**
	 * 执行查询操作
	 */
	private void search() {
		starttime=tvStarttime.getText().toString();
		
		Myapplication.log("查询是的结束时间", endtime);
		Myapplication.log("查询是的起止时间和当前选择的内容：", starttime+"----------"+endtime+"----"+itemAtPosition);
		urlParam.put("starttime",starttime);
		urlParam.put("endtime",endtime);
		if(itemAtPosition.equals("未付款")){
			urlParam.put("xsstatus", "0");
			xsstatus=0;
		}else if(itemAtPosition.equals("已付款")){
			urlParam.put("xsstatus", "1");
			xsstatus=1;
		}
		presenter.loadData();
		adapter.notifyDataSetChanged();
		Myapplication.log("dddddd", lists+"");
		count=0;
//		if(lists==null){
//			lv_dingdan.setVisibility(View.INVISIBLE);
//			tv_order.setVisibility(View.VISIBLE);
//		}
		//accountInfoUtil.getAccountInfo(map);
	}
	@Override
	public void setData(List<XS_Order_ABase> list) {
		Collections.reverse(list);
		lists=list;
		Myapplication.log("zzzzzzz", ""+lists.size());
		if(lists.size()<1){
			lv_dingdan.setVisibility(View.INVISIBLE);
			tv_order.setVisibility(View.VISIBLE);
		}else {
			lv_dingdan.setVisibility(view.VISIBLE);
			tv_order.setVisibility(view.INVISIBLE);
		}
		//倒序
		//Collections.reverse(lists);
	}
	@Override
	public void showData() {
		adapter=new DingdanAdapter(lists, getActivity());
		lv_dingdan.setAdapter(adapter);
	}
}
