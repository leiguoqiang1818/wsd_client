package com.example.wsd_client.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.wsd_client.R;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.entity.Product;
import com.example.wsd_client.fragment.impl.LiebiaoFragment;
import com.example.wsd_client.urlUtils.UrlPath;
import com.example.wsd_client.util.BitmapLruCache;

public class LiebiaoAdapter extends BaseAdapter{

	private Context context;
	/**
	 * 产品集合
	 */
	private List<Product> lists;
	/**
	 * 第二层容器对象
	 */
	private LinearLayout ll_seconde;
	private LiebiaoFragment liebiaofragment;
	
	//ImagerLoader的创建
	private ImageLoader loader;
	private RequestQueue mQu;// 请求队列
	private ImageCache cach;
	
	public LiebiaoAdapter(List<Product> lists,LinearLayout ll_seconde,LiebiaoFragment liebiaofragment) {
		super();
		this.context = Myapplication.getContext();
		this.ll_seconde = ll_seconde;
		this.lists = lists;
		this.liebiaofragment = liebiaofragment;
		mQu=Myapplication.getRequestQueue();
		cach = new BitmapLruCache();
		loader = new ImageLoader(mQu, cach);

	}

	@Override
	public int getCount() {
		return lists==null?0:lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Product info = lists.get(position);
		final ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.liebiao_item, null);
			holder.tvCode=(TextView) convertView.findViewById(R.id.tv_liebiao_code);
			holder.tvName=(TextView) convertView.findViewById(R.id.tv_liebiao_name);
			holder.tvKind=(TextView) convertView.findViewById(R.id.tv_liebiao_kind);
			holder.tvPrice=(TextView) convertView.findViewById(R.id.tv_liebiao_price);
			holder.btn_add_to_cart=(Button) convertView.findViewById(R.id.btn_liebiao_buy);
			holder.btn_promptly_buy = (Button) convertView.findViewById(R.id.btn_liebiao_buynow);
			holder.btnBuy=(RadioButton) convertView.findViewById(R.id.btn_liebiao_buy);
			holder.ivPicture=(ImageView) convertView.findViewById(R.id.iv_liebiao);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}

		holder.tvCode.setText("品种代码："+info.getYWM_Code());
		holder.tvName.setText("品种名称："+info.getYWM_Name());
		holder.tvKind.setText("种类："+info.getYWM_Kind());
		holder.tvPrice.setText("单价：￥"+info.getYWM_Price());
		holder.tvPrice.setTextColor(Color.RED);

		String requestUrl=UrlPath.PRODUCT_ITEM_URL+"?"+info.getYWM_PicName();
		loader.get(requestUrl, new ImageListener() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				holder.ivPicture.setImageResource(R.drawable.ic_launcher);
				
			}
			
			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				if(isImmediate){
					Bitmap bitmap = response.getBitmap();
					holder.ivPicture.setImageBitmap(bitmap);
				}
			}
		}, 0, 0);
		
		//将用户点击欲购买的产品通过产品编码传给LiebiaoFragment的购物车按钮
		holder.btn_add_to_cart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				liebiaofragment.displayBuyParam(position);
				//改变标记变量
				liebiaofragment.setClick_flag(1);
				liebiaofragment.setPosition(position);
			}
		});
		//立即购买监听
		holder.btn_promptly_buy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int productId = lists.get(position).getYWM_ID();
				Cart cart = new Cart();
				List<CartItem> list_cartItem = cart.getList_cartItem();
				for(CartItem cartItem:list_cartItem){
					int productID = cartItem.getProduct().getYWM_ID();
					//先判断购物车中有没有该商品，有的话直接跳转到购物车界面
					if(productId==productID){
						Myapplication.toast("商品已在购物车，请购买");
						//并且跳转到购物车页面
						Intent intent = new Intent("main_activity");
						intent.putExtra("TAB", 2);
						liebiaofragment.getActivity().startActivity(intent);
						return;
					}
				}
				//弹出购买参数容器
				liebiaofragment.displayBuyParam(position);
				//改变标记变量
				liebiaofragment.setClick_flag(2);
				liebiaofragment.setPosition(position);
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView tvCode;
		TextView tvName;
		TextView tvKind;
		TextView tvPrice;
		Button btn_add_to_cart;
		Button btn_promptly_buy;
		RadioButton btnBuy;
		ImageView ivPicture;
	}

}
