package com.example.wsd_client.fragment;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.HomeActivity;
import com.example.wsd_client.activity.MainActivity;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.Cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint({ "HandlerLeak", "ValidFragment" })
public class HomeFragment extends Fragment implements OnClickListener{
	/**
	 * ViewPager
	 */
	private ViewPager viewPager;

	/**
	 * 装小圆点的数组
	 */
	private ImageView[] tips;
	/**
	 * 图片资源id
	 */
	private int[] imgIdArray=new int[]{R.drawable.home_vp1,R.drawable.home_vp2,R.drawable.home_vp3};

	/**
	 * 装图片的数组
	 */
	private ImageView[] mImageViews=new ImageView[imgIdArray.length];

	private Myapplication app;
	private Cart cart;

	private View view;
	private boolean flag=true;
	private ViewGroup viewGroup;
	private MyAdapter adapter;
	private TextView tvLiebiao,tvKuaisu,tvBaobiao,tvDingdan,tvZhangdan,tvXiaoxi;
	private Context context;
	/**
	 * 标记变量，处理轮播图片使用
	 */
	private int flag_index = 1;

	public HomeFragment(){
		super();
	}
	public HomeFragment(Context context) {
		super();
		this.context = context;
	}

	//定时轮播图片，需要在主线程里面修改 UI
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				int count = adapter.getCount();  
				if (count > 1) { // 多于1个，才循环  
					int index = viewPager.getCurrentItem();  
					index = (index + 1) % count;  
					viewPager.setCurrentItem(index, true);
				}  
				break;
			}
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_home, null);
		//初始化控件
		viewPager=(ViewPager) view.findViewById(R.id.vp_home);
		viewGroup = (ViewGroup) view.findViewById(R.id.viewGroup);
		tvLiebiao=(TextView) view.findViewById(R.id.tv_home_liebiao);
		tvKuaisu=(TextView) view.findViewById(R.id.tv_home_kuaisu);
		tvBaobiao=(TextView) view.findViewById(R.id.tv_home_baobiao);
		tvDingdan=(TextView) view.findViewById(R.id.tv_home_dingdan);
		tvZhangdan=(TextView) view.findViewById(R.id.tv_home_zhangdan);
		tvXiaoxi=(TextView) view.findViewById(R.id.tv_home_xiaoxi);

		viewPager.setCurrentItem(0);
		initData();

		setAdapter();

		setListener();

		return view;
	}

	/**
	 * 初始化控件数据
	 */
	private void initData() {
		app = (Myapplication) Myapplication.getContext();
		//封装图片
		//将点放入到ViewGroup中,即LinearLayout
		//点的个数等于图片的长度
		tips=new ImageView[imgIdArray.length];
		//遍历点的数组
		for(int i=0;i<tips.length;i++){
			//自定义点的图片的高和宽
			ImageView ivPoint=new ImageView(getActivity());
			ivPoint.setLayoutParams(new LayoutParams(10, 10));
			tips[i]=ivPoint;
			//默认第一张图片为红点
			if(i==0){
				tips[i].setBackgroundResource(R.drawable.point_focused);
			}else{
				tips[i].setBackgroundResource(R.drawable.point_unfocused);
			}
			//自定义LinearLayout的宽和高取自ViewGroup的自适应宽高，即点的宽高
			LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			//自定义的左右间距各5dp
			layoutParams.leftMargin=5;
			layoutParams.rightMargin=5;
			viewGroup.addView(ivPoint, layoutParams);

			//封装图片
			ImageView imageView = new ImageView(getActivity());  
			mImageViews[i] = imageView;  
			imageView.setBackgroundResource(imgIdArray[i]);
		}
		//轮播图片
		looperImages();
	}

	/**
	 * 设置ViewPager适配器
	 */
	private void setAdapter() {
		adapter=new MyAdapter();
		viewPager.setAdapter(adapter);
	}

	/**
	 * 设置监听
	 */
	private void setListener() {

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setImageBackground(arg0 % mImageViews.length);
			}

			private void setImageBackground(int selectItems) {
				for(int i=0; i<tips.length; i++){  
					if(i == selectItems){  
						tips[i].setBackgroundResource(R.drawable.point_focused);  
					}else{  
						tips[i].setBackgroundResource(R.drawable.point_unfocused);  
					}  
				}  

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		tvBaobiao.setOnClickListener(this);
		tvDingdan.setOnClickListener(this);
		tvKuaisu.setOnClickListener(this);
		tvLiebiao.setOnClickListener(this);
		tvXiaoxi.setOnClickListener(this);
		tvZhangdan.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent(context,HomeActivity.class);

		switch (v.getId()) {
		//商品列表
		case R.id.tv_home_liebiao:
			flag=false;
			intent.putExtra("TAG", 0);
			startActivity(intent);
			//销毁当前Activity
			break;
			//闪电下单
		case R.id.tv_home_kuaisu:
			flag=false;
			intent.putExtra("TAG", 1);
			startActivity(intent);
			//销毁当前Activity
			break;
			//资金报表
		case R.id.tv_home_baobiao:
			flag=false;
			intent.putExtra("TAG", 2);
			startActivity(intent);
			//销毁当前Activity
			break;
			//订单中心
		case R.id.tv_home_dingdan:
			flag=false;
			intent.putExtra("TAG", 3);
			startActivity(intent);
			//销毁当前Activity
			break;
			//我的资金	
		case R.id.tv_home_zhangdan:
			flag=false;
			intent.putExtra("TAG", 4);
			startActivity(intent);
			//销毁当前Activity
			break;
			//最新消息
		case R.id.tv_home_xiaoxi:
			flag=false;
			intent.putExtra("TAG", 5);
			startActivity(intent);
			//销毁当前Activity
			break;
		}

	}

	/**
	 * 适配器
	 */
	public class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return Integer.MAX_VALUE; 
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;  
		}

		@Override  
		public void destroyItem(View container, int position, Object object) {  
			//((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);  

		}  
		/** 
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数
		 */  
		@Override
		public Object instantiateItem(View container, int position) {
			//余数来决定显示第几张的图片
			try {
				((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mImageViews[position % mImageViews.length];  
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if(flag_index!=1){
			flag =true;
			looperImages();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		//关闭线程
		flag=false;
		flag_index = 0;
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		//隐藏
		if(hidden){
			onPause();
		//显示
		}else{
			onResume();
		}
	}
	
	/**自定义方法，循环轮播图片
	 * 
	 */
	private void looperImages(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(flag){
					try {
						Thread.sleep(2000);
						Message message=Message.obtain();
						message.what=0;
						mHandler.sendMessage(message);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
