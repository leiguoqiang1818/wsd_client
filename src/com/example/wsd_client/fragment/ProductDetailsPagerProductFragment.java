package com.example.wsd_client.fragment;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.HomeActivity;
import com.example.wsd_client.adapter.ProductDetailsPagerShowViewPagerAdapter;
import com.example.wsd_client.customViews.CustomImageView;
import com.example.wsd_client.customViews.CustomViewPager;
import com.example.wsd_client.entity.Product;
/**
 * 商品基本信息fragment
 * @author wsd_leiguoqiang
 */
public class ProductDetailsPagerProductFragment extends Fragment implements OnPageChangeListener,
OnCheckedChangeListener{
	/**
	 * 需要显示的产品对象
	 */
	private Product product;
	/**
	 * view布局对象
	 */
	private View view;
	/**
	 * 图片资源
	 */
	private int[] imageArray = new int[]{R.drawable.home_vp1,R.drawable.home_vp2,R.drawable.home_vp3
			,R.drawable.home_vp1,R.drawable.home_vp2,R.drawable.home_vp3};
	/**
	 * 定义一个iamgeView数组
	 */
	private List<CustomImageView> list_imageview = new ArrayList<CustomImageView>();
	/**
	 * viewpager
	 */
	@ViewInject(R.id.vp_product_details_pager_fragment)
	private CustomViewPager vp;
	/**
	 * 圆点图片显示下标
	 */
	@ViewInject(R.id.rg_index_product_details_pager_fragment)
	private RadioGroup rg_index;

	@ViewInject(R.id.rb_first_index_product_details_pager_fragment)
	private RadioButton rb_first;

	@ViewInject(R.id.rb_seconde_index_product_details_pager_fragment)
	private RadioButton rb_seconde;

	@ViewInject(R.id.rb_third_index_product_details_pager_fragment)
	private RadioButton rb_third;

	@ViewInject(R.id.rb_fourth_index_product_details_pager_fragment)
	private RadioButton rb_fourth;

	@ViewInject(R.id.rb_fifth_index_product_details_pager_fragment)
	private RadioButton rb_fifth;

	@ViewInject(R.id.rb_sixth_index_product_details_pager_fragment)
	private RadioButton rb_sixth;
	/**
	 * 商品名称
	 */
	@ViewInject(R.id.tv_name_product_details_pager_fragment)
	private TextView tv_product_name;
	/**
	 * 商品价格
	 */
	@ViewInject(R.id.tv_price_product_details_pager_fragment)
	private TextView tv_product_price;

	public ProductDetailsPagerProductFragment(Product product) {
		super();
		this.product = product;
	}
	/**
	 * 自定义方法，改变viewpager里的标记变量
	 */
	public void setClick_flag(boolean click_flag){
		vp.setClick_flag(click_flag);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.fragment_product_details_pager_product, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		x.view().inject(this, view);
		init();
		initListeners();
	}
	
	private void init() {
		//初始化ImageView[]数组,并设置图片
		for(int i = 0;i<imageArray.length;i++){
			CustomImageView imageview = new CustomImageView(getActivity());
			imageview.setImageResource(imageArray[i]);
			imageview.setScaleType(ImageView.ScaleType.FIT_XY);
			list_imageview.add(imageview);
		}
		ProductDetailsPagerShowViewPagerAdapter adapter = new ProductDetailsPagerShowViewPagerAdapter(list_imageview,
				getActivity());
		vp.setAdapter(adapter);
		vp.setCurrentItem(0);
		
		//通过商品对象进行相关属性取值并显示在界面
		tv_product_name.setText(product.getYWM_Name());
		tv_product_price.setText(product.getYWM_Price());

	}

	private void initListeners() {
		vp.setOnPageChangeListener(this);
		rg_index.setOnCheckedChangeListener(this);

	}

	/**
	 * radiogroup 监听
	 * @param group
	 * @param checkedId
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_first_index_product_details_pager_fragment:
			vp.setCurrentItem(0);
			break;
		case R.id.rb_seconde_index_product_details_pager_fragment:
			vp.setCurrentItem(1);
			break;
		case R.id.rb_third_index_product_details_pager_fragment:
			vp.setCurrentItem(2);
			break;
		case R.id.rb_fourth_index_product_details_pager_fragment:
			vp.setCurrentItem(3);
			break;
		case R.id.rb_fifth_index_product_details_pager_fragment:
			vp.setCurrentItem(4);
			break;
		case R.id.rb_sixth_index_product_details_pager_fragment:
			vp.setCurrentItem(5);
			break;
		}
	}

	/**
	 * viewpager滑动监听
	 * @param arg0
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}
	//滑动过程中监听
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
		case 0:
			rb_first.setChecked(true);
			break;
		case 1:
			rb_seconde.setChecked(true);
			break;
		case 2:
			rb_third.setChecked(true);
			break;
		case 3:
			rb_fourth.setChecked(true);
			break;
		case 4:
			rb_fifth.setChecked(true);
			break;
		case 5:
			rb_sixth.setChecked(true);
			break;
		}
	}
}
