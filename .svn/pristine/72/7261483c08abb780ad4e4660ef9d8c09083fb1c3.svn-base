package com.example.wsd_client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.example.wsd_client.R;
import com.example.wsd_client.adapter.AccountFragmentPpWindowAdapter;
import com.example.wsd_client.adapter.AccountListViewAdapter;
import com.example.wsd_client.application.Myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.wsd_client.entity.AccountInfo;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.YW_ClientInfo;
import com.example.wsd_client.util.AccountInfoUtil;
import com.example.wsd_client.util.RefreshListView;
import com.example.wsd_client.util.RefreshListView.OnRefreshListener;
import com.example.wsd_client.util.CalendarViewUtil.DateUtils;
import com.example.wsd_client.util.CalendarViewUtil.MonthDateView.DateClick;

/**
 * 账单中心
 */
@SuppressLint("HandlerLeak")
public class AccountFragment extends Fragment implements OnClickListener{
	//	private Cart cart;
	private Myapplication app;

	private View viewPpWindow;
	private ListView lvPpWindow;
	private PopupWindow popupWindow;
	private AccountFragmentPpWindowAdapter ppWindowAdapter;
	private String itemAtPosition;

	//listView适配器
	private AccountListViewAdapter lvAdapter;

	private TextView tvStarttime,tvEndtime,tvChoose,tvNoAccount;
	private Button btnSearch;
	private RefreshListView lvShow;
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

	//作为获取数据页数的参数
	private int pageNum=0;

	//用于封装除list集合中除了tvChoose中显示内容外的其他内容
	private List<String> removeList;

	//要陈列的数据集合
	private List<AccountInfo> lists;

	private Handler handler=new Handler(){


		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			//1:表示更换筛选条件没数据   2：表示有数据  3：表示listView上拉加载没有数据
			switch (msg.what) {
			case 1:
				lvShow.setVisibility(View.INVISIBLE);
				tvNoAccount.setVisibility(View.VISIBLE);
				break;

			case 2:
				lvShow.setVisibility(View.VISIBLE);
				tvNoAccount.setVisibility(View.INVISIBLE);

				Myapplication.log("handler开始运行：","---------------");

				lists = (List<AccountInfo>) msg.obj;
				int i=msg.arg1;

				Myapplication.log("List<AccountInfo>的长度：", lists.size()+"");
				lvAdapter = new AccountListViewAdapter(getActivity(), lists,i);
				lvShow.setAdapter(lvAdapter);

				//加载完成，隐藏脚布局
				lvShow.onRefreshComplete();

				break;

			case 3:
				//上拉加载时没有数据，直接隐藏脚布局
				lvShow.onRefreshComplete();
				Myapplication.toast("没有更多数据啦！");

			}


		};
	};

	private AccountInfoUtil accountInfoUtil=new AccountInfoUtil(handler);
	//网络请求的参数集合
	private Map<String, String> map;

	private AlertDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_account, null);

		initView();

		initData();

		setListener();


		return view;
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		tvStarttime=(TextView) view.findViewById(R.id.tv_account_startTime);
		tvEndtime=(TextView) view.findViewById(R.id.tv_account_endTime);
		tvChoose=(TextView) view.findViewById(R.id.tv_account_choose);
		tvNoAccount=(TextView) view.findViewById(R.id.tv_no_news_account);
		btnSearch=(Button) view.findViewById(R.id.btn_account_choose);
		lvShow=(RefreshListView) view.findViewById(R.id.lv_account);
	}

	/**
	 * 初始化数据
	 */
	@SuppressLint("SimpleDateFormat")
	private void initData() {

		//获取当前系统时间
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		Myapplication.log("当前系统时间time：", time);
		//截取当前的年份和月数
		String yearString=time.substring(0, 4);
		year = Integer.parseInt(yearString); 

		String monthString=time.substring(5, 7);
		month = Integer.parseInt(monthString);
		Myapplication.log("当前的年year和月month：", year+"-------"+month);

		String dayString=time.substring(8, 10);
		day = Integer.parseInt(dayString);

		//获取本月最大天数
		int maxDay=DateUtils.getMonthDays(year, month-1);
		//判断当前时间是否为月末或年末最后一天，查询时结束时间往后延迟一天
		int newDay=0;
		int newYear = 0 ;
		int newMonth=0;

		if(day==maxDay){
			newDay=1;
			if(month==12){
				newYear=year+1;
				newMonth=1;
			}else{
				newMonth=month+1;
				newYear=year;
			}
		}else{
			newDay=day+1;
			newYear=year;
			newMonth=month;
		}

		//获取当前的客户id
		app=(Myapplication) Myapplication.getContext();
		YW_ClientInfo clientInfo = app.getClientInfo();
		List<ClientInfo> result = clientInfo.getResult();
		clientid = result.get(0).getYWC_ClientID();

		String starttime=year+"-"+month+"-01";
		//显示的结束时间
		String endtime=year+"-"+month+"-"+day;
		//查询的实际结束时间
		String newEndTime=newYear+"-"+newMonth+"-"+newDay;
		Myapplication.log("实际查询的结束时间：", newEndTime);
		
		tvStarttime.setText(starttime);
		tvEndtime.setText(endtime);

		//设置五种选择方式的内容集合
		list=new ArrayList<String>();
		list.add("品种汇总");
		list.add("日期汇总");
		list.add("车牌汇总");
		list.add("日期+品种");
		list.add("车牌+日期");

		//封装移除后的数据
		removeList = new ArrayList<String>();
		removeList.addAll(list);
		removeList.remove(0);
		//设置默认显示内容
		tvChoose.setText(list.get(0));
		itemAtPosition="品种汇总";

		map = new HashMap<String, String>();
		map.put("pageNum", pageNum+"");
		map.put("pageSize", "10");
		map.put("starttime",starttime);
		map.put("endtime",newEndTime);
		map.put("clientid", clientid+"");
		map.put("xsstatus", "0");
		Myapplication.log("accountInfoUtil:", "网络请求开始");
		accountInfoUtil.getAccountInfo(map,1);
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
		//上拉加载和下拉刷新监听
		lvShow.setRefreshListener(new OnRefreshListener() {

			@Override
			public void onLoadMore() {
				pageNum++;
				map.put("pageNum", pageNum+"");
				accountInfoUtil.getAccountInfo(map,2);

			}
		});
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_account_startTime:
			//当客户选择开始时间时，将j设置成1
			j=1;
			getTimeDialog();
			break;

		case R.id.tv_account_endTime:
			//当客户选择开始时间时，将j设置成1
			j=2;
			getTimeDialog();
			break;

		case R.id.tv_account_choose:

			showPopWindow(v);
			break;

		case R.id.btn_account_choose:

			search();
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

						tvStarttime.setText(yearChoose+"-"+monthChoose+"-"+dayChoose);
						//退出对话框
						dialog.dismiss();
					}else{
						Myapplication.toast("开始日期不得晚于结束日期，请重新选择开始日期！");
					}

				}else if(j==2){
					yearChoose= monthDateView.getmSelYear();
					monthChoose = monthDateView.getmSelMonth()+1;
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

					Myapplication.log("再看看截取的是不是正确的：", startyear+"-"+startmonth+"-"+startday);

					//当开始日期早于当前显示的结束日期时，才能执行查询功能
					if(yearChoose>startyear||yearChoose==startyear&&monthChoose>startmonth||yearChoose==startyear&&monthChoose==startmonth&&dayChoose>=startday){

						tvEndtime.setText(yearChoose+"-"+monthChoose+"-"+dayChoose);
						//退出对话框
						dialog.dismiss();
					}else{
						Myapplication.toast("结束日期不得早于开始日期，请重新选择结束日期！");
					}
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

				//当更换条件筛选时，将此时的pageNum值变成0
				pageNum=0;

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
		Myapplication.log("看看这时候查询的是第几页：", pageNum+"");
		pageNum=0;
		
		String start=tvStarttime.getText().toString();
		String end=tvEndtime.getText().toString();
		Myapplication.log("查询是的起止时间和当前选择的内容：", start+"----------"+end+"----"+itemAtPosition);

		map.put("starttime",start);

		//获取当前显示的结束时间，执行查询时，将结束时间往后加一天，再执行查询
		String [] i=end.split("[-]");
		String searchYear=i[0];
		String searchMonth=i[1];
		String searchDay=i[2];

		Integer endYear=Integer.parseInt(searchYear);
		Integer endMonth=Integer.parseInt(searchMonth);
		Integer endDay=Integer.parseInt(searchDay);

		//获取本月最大天数
		int maxDay=DateUtils.getMonthDays(endYear, endMonth-1);
		Myapplication.log("最大天数：", maxDay+"");
		//判断当前时间是否为月末或年末最后一天，查询时结束时间往后延迟一天
		int newDay=0;
		int newYear = 0;
		int newMonth=0;

		if(endDay==maxDay){
			newDay=1;
			if(endMonth==12){
				newYear=endYear+1;
				newMonth=1;
			}else{
				newMonth=endMonth+1;
				newYear=endYear;
			}
		}else{
			newDay=endDay+1;
			newYear=endYear;
			newMonth=endMonth;
		}

		//拼装结束时间
		String endTime=newYear+"-"+newMonth+"-"+newDay;
		Myapplication.log("点击查询按钮后的实际结束查询时间：", endTime);
		
		map.put("endtime",endTime);
		map.put("pageNum", pageNum+"");

		if(itemAtPosition.equals("品种汇总")){
			map.put("xsstatus", "0");
		}else if(itemAtPosition.equals("日期汇总")){
			map.put("xsstatus", "1");
		}else if(itemAtPosition.equals("车牌汇总")){
			map.put("xsstatus", "2");
		}else if(itemAtPosition.equals("日期+品种")){
			map.put("xsstatus", "3");
		}else if(itemAtPosition.equals("车牌+日期")){
			map.put("xsstatus", "4");
		}

		accountInfoUtil.getAccountInfo(map,1);

	}


}
