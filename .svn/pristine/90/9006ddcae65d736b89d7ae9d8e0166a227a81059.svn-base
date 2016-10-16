package com.example.wsd_client.adapter;

import java.util.List;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.MainActivity;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.fragment.impl.UploadCartDataViewImpl;
/**
 * 购物车商品列表adapter，用于显示购物车中所有的购物项信息
 * @author wsd_leiguoqiang
 * 需要根据服务器数据进行完善----------------------------------------------------
 */
public class CartAdapter extends BaseAdapter{
	/**
	 * CartItem数据集合
	 */
	private List<CartItem> list;
	/**
	 * 上下文对象
	 */
	private Context context;
	/**
	 * listview对象
	 */
	private ListView listView;
	/**
	 * 购物车fragment对象
	 */
	private UploadCartDataViewImpl uploadCartView;
	/**
	 * 标记变量，用于判断是否显示删除图标和选择图标
	 * 值为false：显示选择图标
	 * 值为true：显示删除图标
	 */
	private boolean flag = false;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	/**
	 * 总价文本
	 */
	private TextView tv_total_price;
	/**
	 * 标记变量，服务于选择图片
	 */
	private boolean flag_select_all = false;

	public boolean isFlag_select_all() {
		return flag_select_all;
	}

	public void setFlag_select_all(boolean flag_select_all) {
		this.flag_select_all = flag_select_all;
	}
	/**
	 * 结算数据集合
	 */
	private List<CartItem> list_total_cartItem;
	/**
	 * 结算购物项数目
	 */
	private TextView tv_total_cartItem_count;
	/**
	 * 全选文本按钮
	 */
	private TextView tv_select_all_button;

	private Myapplication app = (Myapplication) Myapplication.getContext();

	private Cart cart = app.getCart();

	public CartAdapter(List<CartItem> list, List<CartItem> list_total_cartItem,UploadCartDataViewImpl fragment, ListView listView ,
			TextView tv_total_price ,TextView tv_total_cartItem_count ,TextView tv_select_all_button) {
		super();
		this.list = list;
		this.context = fragment.getActivity();
		this.listView = listView;
		this.tv_total_price = tv_total_price;
		this.list_total_cartItem = list_total_cartItem;
		this.tv_total_cartItem_count = tv_total_cartItem_count;
		this.tv_select_all_button = tv_select_all_button;
		this.uploadCartView = fragment;
	}

	@Override
	public int getCount() {
		return list==null?0:list.size();
	}

	//自定义viewholder
	class ViewHolder{
		/**
		 * 删除图标
		 */
		ImageView iv_delete;
		/**
		 * 选择图标
		 */
		ImageView iv_select;
		/**
		 * 展示小图
		 */
		ImageView iv_small_pictrue;
		/**
		 * 商品名字
		 */
		TextView tv_product_name;
		/**
		 * 商品价格
		 */
		TextView tv_product_price;
		/**
		 * 商品属性
		 */
		TextView tv_product_type;
		/**
		 * 商品数量
		 */
		TextView tv_product_count;
		/**
		 * 减少
		 */
		ImageView iv_reduce;
		/**
		 * 增加
		 */
		ImageView iv_add;
		/**
		 * 显示的数量
		 */
		TextView tv_display_count;
		/**
		 * 产品编辑按钮
		 */
		TextView tv_product_edite;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		//获取当前数据
		CartItem cartItem = list.get(arg0);
		Myapplication.log("cartadapter", "数据类容"+cartItem.getProduct_count());
		ViewHolder holder = null;
		if(convertView==null){
			//获取view对象
			convertView = View.inflate(context, R.layout.modle_for_adapter_cart_items, null);
			//获取子控件对象
			holder = new ViewHolder();
			holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_fragment_cart_pager_delete_button);
			holder.iv_select = (ImageView) convertView.findViewById(R.id.iv_fragment_cart_pager_selecte);
			holder.iv_small_pictrue = (ImageView) convertView.findViewById(R.id.iv_fragmnet_cart_pager_pic_show);

			holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_product_name);
			holder.tv_product_price = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_product_price);
			holder.tv_product_type = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_product_attribute);
			holder.tv_product_count = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_product_count);
			holder.tv_product_edite = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_product_edite);

			holder.iv_reduce = (ImageView) convertView.findViewById(R.id.iv_modle_for_adapter_product_reduce);
			holder.iv_add = (ImageView) convertView.findViewById(R.id.iv_modle_for_adapter_product_add);
			holder.tv_display_count = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_count_display);
			//将holder添加到convertView中
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();

		//设置子控件内容
		holder.tv_product_name.setText(cartItem.getProduct().getYWM_Name());
		holder.tv_product_price.setText(cartItem.getProduct().getYWM_Price()+"");
		holder.tv_product_type.setText(cartItem.getProduct().getYWM_Code());
		holder.tv_product_count.setText("X"+cartItem.getBuyParam().get("ordernumber"));
		holder.tv_display_count.setText(cartItem.getBuyParam().get("ordernumber"));

		//给子控件添加标记
		holder.tv_display_count.setTag("tv_display_count"+arg0);
		holder.tv_product_count.setTag("tv_product_count"+arg0);
		holder.iv_delete.setTag("iv_delete"+arg0);
		holder.iv_select.setTag("iv_select"+arg0);

		//判断删除图标是否显示
		if(!flag){
			holder.iv_delete.setScaleX(0);
			holder.iv_delete.setScaleY(0);
			holder.iv_delete.setVisibility(View.INVISIBLE);
			holder.iv_select.setScaleX(1);
			holder.iv_select.setScaleY(1);
			holder.iv_select.setVisibility(View.VISIBLE);
			//当正常显示选择图标时候，判断是否是全选状态
			if(flag_select_all){
				holder.iv_select.setSelected(true);
			}else if(list_total_cartItem.contains(list.get(arg0))){
				holder.iv_select.setSelected(true);
			}else{
				holder.iv_select.setSelected(false);
			}
		}else{
			holder.iv_delete.setScaleX(1);
			holder.iv_delete.setScaleY(1);
			holder.iv_delete.setVisibility(View.VISIBLE);
			holder.iv_select.setScaleX(0);
			holder.iv_select.setScaleY(0);
			//当处于编辑状态的时候，让holder.iv_selected处于非选择状态
			holder.iv_select.setSelected(false);
			holder.iv_select.setVisibility(View.INVISIBLE);
		}

		//自控件添加监听器
		holder.iv_add.setOnClickListener(new AddAndReduceListener(arg0, AddAndReduceListener.FLAG_ADD, holder.iv_select));
		holder.iv_reduce.setOnClickListener(new AddAndReduceListener(arg0, AddAndReduceListener.FLAG_REDUCE, holder.iv_select));
		holder.iv_delete.setOnClickListener(new DeleteListener(arg0));
		holder.iv_select.setOnClickListener(new AmendSelectIconListener(holder.iv_select ,arg0));
		holder.tv_product_edite.setOnClickListener(new AddAndReduceListener(arg0, AddAndReduceListener.FLAG_EDITE, null));
		return convertView;
	}

	/**
	 * 修改选择文本按钮监听类
	 * @author wsd_leiguoqiang
	 */
	class AmendSelectIconListener implements View.OnClickListener{
		/**
		 * 选择图标
		 */
		private ImageView iv_select_icon;
		/**
		 * 数据下标
		 */
		private int position;

		public AmendSelectIconListener(ImageView iv_select_icon , int position) {
			super();
			this.iv_select_icon = iv_select_icon;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			if(iv_select_icon.isSelected()){
				iv_select_icon.setSelected(false);
				//修改结算集合中数据
				list_total_cartItem.remove(list.get(position));
				//改变全选文本按钮状态
				tv_select_all_button.setTextColor(((Activity)context).getResources().getColor(R.color.black_light));
				Drawable drawable = ((Activity)context).getResources().getDrawable(R.drawable.icon_unseclete);
				tv_select_all_button.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
				//改变全选标记变量
				flag_select_all = false;
			}else{
				iv_select_icon.setSelected(true);
				//修改结算集合中数据
				list_total_cartItem.add(list.get(position));
				//修改全选按钮的选择状态
				if(list_total_cartItem.size()==list.size()){
					//改变全选文本按钮状态
					tv_select_all_button.setTextColor(((Activity)context).getResources().getColor(R.color.wathet_blue));
					Drawable drawable = ((Activity)context).getResources().getDrawable(R.drawable.icon_seclete);
					tv_select_all_button.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
					//改变全选标记变量
					flag_select_all = true;
				}
			}
			//更新合计总价和结算数量
			tv_total_price.setText("合计：￥"+cart.totalPrice(list_total_cartItem));
			tv_total_cartItem_count.setText("结算("+list_total_cartItem.size()+")");
			//判断结算集合中是否有数据
			if(list_total_cartItem.size()!=0){
				tv_total_cartItem_count.setBackgroundColor(((Activity)context).getResources().getColor(R.color.red));
				tv_total_cartItem_count.setClickable(true);
			}else{
				tv_total_cartItem_count.setBackgroundColor(((Activity)context).getResources().getColor(R.color.gray_light));
				tv_total_cartItem_count.setClickable(false);
			}

		}
	}

	/**
	 * 自定义删除图标是否显示方法,商品购物车列表Activity调用
	 */
	public void deleteIconShow(){

		if(!flag){
			//需要遍历数据集合中的每个数据，并进行删除图片的是否显示操作
			for(int i = 0;i<list.size();i++){
				final ImageView iv_delete_icon = (ImageView) listView.findViewWithTag("iv_delete"+i);
				final ImageView iv_select_icon = (ImageView) listView.findViewWithTag("iv_select"+i);
				//删除图标
				ObjectAnimator anim = ObjectAnimator.ofFloat(iv_delete_icon, "abc", 1f, 0f);
				//选择图标
				ObjectAnimator anim_select = ObjectAnimator.ofFloat(iv_select_icon, "abc", 0f , 1f);
				anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator valueAnimator) {
						float val = (Float) valueAnimator.getAnimatedValue();
						iv_delete_icon.setScaleX(val);
						iv_delete_icon.setScaleY(val);
					}
				});
				anim.setDuration(500);
				anim.start();

				anim_select.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						float val = (Float) animation.getAnimatedValue();
						iv_select_icon.setScaleX(val);
						iv_select_icon.setScaleY(val);
					}
				});
				anim_select.setDuration(500);
				anim_select.start();
			}
		}else{
			for(int i = 0;i<list.size();i++){
				final ImageView iv_delete_icon = (ImageView) listView.findViewWithTag("iv_delete"+i);
				final ImageView iv_select_icon = (ImageView) listView.findViewWithTag("iv_select"+i);
				//删除图标
				ObjectAnimator anim = ObjectAnimator.ofFloat(iv_delete_icon, "abc", 0f, 1f);
				//选择图标
				ObjectAnimator anim_select = ObjectAnimator.ofFloat(iv_select_icon, "abc", 1f , 0f);
				anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						float val = (Float) animation.getAnimatedValue();
						iv_delete_icon.setScaleX(val);
						iv_delete_icon.setScaleY(val);
					}
				});
				anim.setDuration(500);
				anim.start();

				anim_select.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						float val = (Float) animation.getAnimatedValue();
						iv_select_icon.setScaleX(val);
						iv_select_icon.setScaleY(val);
					}
				});
				anim_select.setDuration(500);
				anim_select.start();
			}
		}

	}

	/**
	 * 自定义删除按钮事件监听类
	 * @author wsd_leiguoqiang
	 */
	class DeleteListener implements View.OnClickListener{
		/**
		 * 数据下标值
		 */
		private int position;

		public DeleteListener(int position) {
			super();
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			int id = list.get(position).getProduct().getYWM_ID();
			//删除数据
			cart.deleteCartItem(id, CartAdapter.this);
			//保存数据
			cart.saveCartData();
			if(cart.getList_cartItem().size()==0){
				((MainActivity)context).getTv_cart_acount().setVisibility(View.INVISIBLE);
			}else{
				((MainActivity)context).getTv_cart_acount().setText(cart.getList_cartItem().size()+"");
			}
		}
	}

	/**
	 *  自定义增加和减少按钮事件监听类，单个商品编辑监听类
	 * @author wsd_leiguoqiang
	 */
	class AddAndReduceListener implements View.OnClickListener{
		//定义增加常量
		public	static final int FLAG_ADD = 1;
		//定义减少常量
		public	static final int FLAG_REDUCE = 2;
		//定义减少常量
		public	static final int FLAG_EDITE = 3;
		/**
		 * 数据下标变量，用于查找标记控件
		 */
		private int position;
		/**
		 * 动作类型变量，用于标记是增加还是减少
		 */
		private int flag;
		/**
		 * 单个购物项选择图标
		 */
		private ImageView iv_select_icon;

		public AddAndReduceListener(int position, int flag , ImageView iv_select_icon) {
			super();
			this.position = position;
			this.flag = flag;
			this.iv_select_icon =  iv_select_icon;
		}

		@Override
		public void onClick(View v) {
			Cart cart = app.getCart();
			TextView tv_display_count = (TextView) listView.findViewWithTag("tv_display_count"+position);
			TextView tv_producte_count = (TextView) listView.findViewWithTag("tv_product_count"+position);
			int count = Integer.parseInt(tv_display_count.getText().toString());
			switch (flag) {
			case 1:
				count++;
				//更新数据
				tv_display_count.setText(count+"");
				tv_producte_count.setText("x"+count);
				//更新购物车数据集合
				list.get(position).getBuyParam().put("ordernumber", count+"");
				//更新总价数据
				if(iv_select_icon.isSelected()){
					list_total_cartItem.clear();
					list_total_cartItem.addAll(list);
					tv_total_price.setText("合计:￥"+cart.totalPrice(list_total_cartItem));
				}
				break;
			case 2:
				count--;
				if(count<=1){
					count = 1;
				}
				//更新数据
				tv_display_count.setText(count+"");
				tv_producte_count.setText("x"+count);
				//更新购物车数据集合
				list.get(position).getBuyParam().put("ordernumber", count+"");
				if(iv_select_icon.isSelected()){
					//更新结算数据集合
					tv_total_price.setText("合计:￥"+cart.totalPrice(list_total_cartItem));
				}
				break;
			//单个商品编辑按钮
			case 3:
				//弹出购买参数容器
				uploadCartView.displayBuyParam(position);
				//禁用第一层子控件监听
				uploadCartView.ll_first.setChildClickable(false);
				//传递数据下标到购物车页面
				uploadCartView.setPosition(position);
				break;
			}
			if(flag!=3){
				//更新数据集合
				cart.amendCount(list.get(position).getProduct().getYWM_ID(), count);
				notifyDataSetChanged();
			}
		}

	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}


}
