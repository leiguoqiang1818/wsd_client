package com.example.wsd_client.customViews;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wsd_client.R;

/**
 * 自定义listview，可以实现上拉，下来刷新
 * 基本实现原理：
 * 1）给listview添加头部和底部布局
 * 2）重写外层组件的onTouchEvent方法达到显示和隐藏头部和底部布局
 * 3）当布局完全显示出来的时候，进行相应的显示图案和文字改变，提示用户可以进行刷新操作
 * 4）进行相应数据加载操作，进行新的数据呈现，从而实现listview刷新操作
 * 5)恢复listview，在回调方法中调用downRefreshCompleted()和upRefreshCompleted()方法
 * @author wsd_leiguoqiang
 */
public class CustomListView extends ListView{
	/**
	 * 刷新成功
	 */
	private static final int REFRESH_SECUSESS =  0;
	/**
	 * 刷新失败
	 */
	private static final int REFRESH_FAIL =  -1;
	/**
	 * listview状态标记标量，默认开始状态为下拉操作状态
	 */
	private int LISTVIEW_STATUS = 1;
	/**
	 * 下拉操作状态
	 */
	private static final int REFRESH_DOWN = 1;
	/**
	 * 释放刷新状态
	 */
	private static final int RELEASE_TO_REFRESH = 2;
	/**
	 * 正在刷新状态
	 */
	private static final int REFRESHING = 3;
	/**
	 * 完成刷新状态
	 */
	private static final int RELEASE_COMPLETED = 4;
	/**
	 * 手势按下的y坐标值
	 */
	private float down_y;
	/**
	 * 手势移动的距离
	 */
	private float moved_y;
	/**
	 * head_view和bottom_view的padding隐藏值
	 */
	private int padding_value;
	/**
	 * 头部箭头提示对象
	 */
	private ImageView iv_head_view;
	/**
	 * 头部加载动画对象
	 */
	private ProgressBar pb_head_view;
	/**
	 * 底部箭头提示对象
	 */
	private ImageView iv_bottom_view;
	/**
	 * 底部加载动画对象
	 */
	private ProgressBar pb_bottom_view;
	/**
	 *  头部文本控件对象
	 */
	private TextView tv_head_view;
	/**
	 *  底部文本控件对象
	 */
	private TextView tv_bottom_view;
	/**
	 * 底部view对象
	 */
	private View bottom_view;
	/**
	 * 头部view对象
	 */
	private View head_view;
	/**
	 * 手势效果缩放比例，造成用力滑动的感觉
	 */
	private int scal = 3;
	/**
	 * 上拉和下拉标记变俩个，默认true为下拉，false为上拉
	 */
	private boolean flag_orientation = true;
	/**
	 * 下拉监听对象
	 */
	private DownRefreshListener downRefreshListener = null;
	/**
	 * 上拉监听对象
	 */
	private UpRefreshListener upRefreshListener = null;
	/**
	 * handler对象，用于延迟操作
	 */
	private Handler handler = new Handler();

	public CustomListView(Context context) {
		super(context);
		init();
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	/**
	 * 自定义方法，初始化listview相关数据
	 */
	private void init(){
		addHeadView();
		addBottomView();
	}
	/**
	 * 添加头部布局
	 */
	private void addHeadView() {
		//初始化headview
		head_view = View.inflate(getContext(), R.layout.head_view,null);
		//获取箭头imageview和加载动画progressbar，文本对象
		iv_head_view = (ImageView) head_view.findViewById(R.id.iv_head_view);
		pb_head_view = (ProgressBar) head_view.findViewById(R.id.pb_head_view);
		tv_head_view = (TextView) head_view.findViewById(R.id.tv_head_view);
		//进行测量
		head_view.measure(0, 0);
		//获取headview的高度
		int head_view_height = head_view.getMeasuredHeight();
		//对padding_value进行赋值
		padding_value = head_view_height;
		//利用padding属性将headview默认隐藏起来
		head_view.setPadding(0, -head_view_height, 0, 0);
		//将headview添加到listview中
		this.addHeaderView(head_view);
	}
	/**
	 * 添加底部布局
	 */
	private void addBottomView() {
		//初始化bottomview
		bottom_view = View.inflate(getContext(), R.layout.bottom_view,null);
		//底部箭头图标
		iv_bottom_view = (ImageView) bottom_view.findViewById(R.id.iv_bottom_view);
		//底部加载动画
		pb_bottom_view = (ProgressBar) bottom_view.findViewById(R.id.pb_bottom_view);
		//文本提示控件
		tv_bottom_view = (TextView) bottom_view.findViewById(R.id.tv_bottom_view);
		//进行测量
		bottom_view.measure(0, 0);
		//获取bottomview的高度
		int bottom_view_height = bottom_view.getMeasuredHeight();
		//利用padding属性将bottomview默认隐藏起来
		bottom_view.setPadding(0, 0, 0, -bottom_view_height);
		//将bottomview添加到listview中
		this.addFooterView(bottom_view);
	}
	/**
	 * 重写ontouchevent方法，进行listview头部和底部布局控制
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//根据滑动的距离刷新操作
		switch (ev.getAction()) {

		//手势按下
		case MotionEvent.ACTION_DOWN:
			//记录按下去的的y坐标值
			down_y = ev.getY();
			break;

			//手势滑动
		case MotionEvent.ACTION_MOVE:
			//记录当前y值
			float current_y = ev.getY();
			//当listview_status状态值为下拉操作状态(1)时，进行下面操作
			if(LISTVIEW_STATUS==1){
				//向下滑动,进行下拉刷新操作,控制头部view对象
				if(current_y-down_y>0){
					//给刷新标记变量赋值
					flag_orientation = true;
					moved_y = current_y-down_y;
					//进行head_view的padding属性设置
					head_view.setPadding(0, (int)(moved_y/scal-padding_value), 0, 0);
					//当moved_y超过padding_value时，进行状状态改变和图案的改变动画
					if(moved_y>(padding_value*2)){
						//如果listview是下拉刷新状态的时候，进行状态和图案改变
						if(LISTVIEW_STATUS==REFRESH_DOWN){
							//箭头改变，旋转动画
							imageview_animation(iv_head_view);
							//状态改成:释放刷新
							LISTVIEW_STATUS = RELEASE_TO_REFRESH;
							//文字改变：提示刷新
							tv_head_view.setText("释放刷新...");
						}
					}
					//向上滑动，进行上拉刷新操作
				}else{
					//给刷新标记标量赋值
					flag_orientation = false;
					//获取移动y值
					moved_y = down_y-current_y;
					//进行bottom_view的padding属性设置
					bottom_view.setPadding(0, 0, 0, (int)(moved_y/scal-padding_value));

					//当moved_y超过padding_value时，进行状状态改变和图案的改变动画
					if(moved_y>(padding_value*2)){
						//箭头改变，旋转动画
						imageview_animation(iv_bottom_view);
						//状态改成:释放刷新
						LISTVIEW_STATUS = RELEASE_TO_REFRESH;
						//文字改变：提示刷新
						tv_bottom_view.setText("释放刷新...");
					}
				}
				//当listview状态为释放刷新状态时候，也进行头部和底部布局的移动
			}else if(LISTVIEW_STATUS==RELEASE_TO_REFRESH){
				//下拉操作
				if(flag_orientation){
					//对移动的距离进行赋值
					moved_y = current_y-down_y;
					//进行head_view的padding属性设置
					head_view.setPadding(0, (int)(moved_y/scal-padding_value), 0, 0);
					//上拉操作
				}else{
					//获取移动y值
					moved_y = down_y-current_y;
					//进行bottom_view的padding属性设置
					bottom_view.setPadding(0, 0, 0, (int)(moved_y/scal-padding_value));
				}
			}

			break;

			//手势离开屏幕	
		case MotionEvent.ACTION_UP:
			//大前提：listview处于释放刷新状态
			if(LISTVIEW_STATUS==RELEASE_TO_REFRESH){
				//判断下拉还是上拉刷新，true为下拉，false为上拉
				//下拉刷新--------------------------------------------------
				if(flag_orientation){
					//向下滑动，判断滑动距离有没有超过padding_value
					//超过就进行刷新操作，listview状态值的改变，加载动画的呈现，箭头图标的隐藏，文字的改变
					if(moved_y>(padding_value*2)){
						//改成正在刷新状态
						LISTVIEW_STATUS = REFRESHING;
						//显示加载动画
						pb_head_view.setVisibility(View.VISIBLE);
						//隐藏箭头
						iv_head_view.setVisibility(View.GONE);
						//改变文字提示
						tv_head_view.setText("正在刷新...");
						//调用下拉刷新监听
						if(downRefreshListener!=null){
							downRefreshListener.downLoadData(this);
						}
						//没有超过就进行隐藏head_view，listview状态值的改变，箭头旋转方向，文字的改变
					}else{
						head_view.setPadding(0, -padding_value, 0, 0);
					}

					//上拉刷新----------------------------------------------
				}else{
					//向上滑动，判断滑动距离有没有超过padding_value
					//超过就进行刷新操作，listview状态值的改变，加载动画的呈现，箭头图标的隐藏，文字的改变
					if(moved_y>(padding_value*2)){
						//改成正在刷新状态
						LISTVIEW_STATUS = REFRESHING;
						//显示加载动画
						pb_bottom_view.setVisibility(View.VISIBLE);
						//隐藏箭头
						iv_bottom_view.setVisibility(View.INVISIBLE);
						//改变文字提示
						tv_bottom_view.setText("正在刷新...");
						//调用上拉刷新监听
						if(upRefreshListener!=null){
							upRefreshListener.upLoadData(this);
						}
						//没有超过就进行隐藏head_view，listview状态值的改变，箭头旋转方向，文字的改变
					}else{
						bottom_view.setPadding(0, 0, 0, -padding_value);
					}
				}

				//当listview处于下拉刷新状态的时候，进行head_veiw和bottom_view的隐藏操作
			}else if(LISTVIEW_STATUS==REFRESH_DOWN){
				//下拉操作
				if(flag_orientation){
					head_view.setPadding(0, -padding_value, 0, 0);
					//上拉操作
				}else{
					bottom_view.setPadding(0, 0, 0, -padding_value);
				}
			}
			break;
		}
		return super.onTouchEvent(ev);
	}
	/**
	 * 自定义方法：对imageview进行旋转动画操作
	 * @param iv:imageview对象
	 */
	private void imageview_animation(ImageView iv){
		//创建旋转属性动画对象
		ObjectAnimator rotateAnimotion = ObjectAnimator.ofFloat(iv, "rotation", 0f, 180f);
		//设置动画时间
		rotateAnimotion.setDuration(500);
		//开始动画
		rotateAnimotion.start();
	} 
	/**
	 * 下拉刷新接口，用于扩展下拉刷新操作
	 * @author wsd_leiguoqiang
	 */
	public interface DownRefreshListener{
		/**
		 * 加载数据方法，进行下拉刷新的具体数据加载操作
		 * listview:用于对完成刷新方法的调用
		 */
		public void downLoadData(CustomListView listview);
	}
	/**
	 * 上拉刷新接口，用于扩展上拉刷新操作
	 * @author wsd_leiguoqiang
	 */
	public interface UpRefreshListener{
		/**
		 * 加载数据方法，进行下拉刷新的具体数据加载操作
		 * listview:用于对完成刷新方法的调用
		 */
		public void upLoadData(CustomListView listview);
	}

	public void setDownRefreshListener(DownRefreshListener downRefreshListener) {
		this.downRefreshListener = downRefreshListener;
	}

	public void setUpRefreshListener(UpRefreshListener upRefreshListener) {
		this.upRefreshListener = upRefreshListener;
	}

	/**
	 * 下拉刷新完成回调方法，用于恢复listview的状态
	 */
	public void downRefreshCompleted(){
		//更改listview状态，显示刷新完成状态
		//显示箭头图标
		iv_head_view.setVisibility(View.VISIBLE);
		//隐藏加载动画
		pb_head_view.setVisibility(View.INVISIBLE);
		//设置提示文本
		tv_head_view.setText("刷新完成...");
		//500毫秒之后进行头部布局的隐藏
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				//隐藏head_view
				head_view.setPadding(0, -(int)padding_value, 0, 0);
				//更改箭头方向
				imageview_animation(iv_head_view);
				//改变listview的状态值,变成下拉刷新状态
				LISTVIEW_STATUS = REFRESH_DOWN;
				//改变文本提示内容
				tv_head_view.setText("下拉刷新...");
			}
		}, 500);
	}
	
	/**
	 * 上拉刷新完成回调方法，用于恢复listview的状态
	 */
	public void upRefreshCompleted(){
		//更改listview状态，显示刷新完成状态
		//显示箭头图标
		iv_bottom_view.setVisibility(View.VISIBLE);
		//隐藏加载动画
		pb_bottom_view.setVisibility(View.INVISIBLE);
		//设置提示文本
		tv_bottom_view.setText("刷新完成...");
		//500毫秒之后进行头部布局的隐藏
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				//隐藏head_view
				bottom_view.setPadding(0, 0, 0, -(int)padding_value);
				//改变listview的状态值,变成下拉刷新状态
				LISTVIEW_STATUS = REFRESH_DOWN;
				//改变文本提示内容
				tv_bottom_view.setText("下拉刷新...");
			}
		}, 500);
	}
}
