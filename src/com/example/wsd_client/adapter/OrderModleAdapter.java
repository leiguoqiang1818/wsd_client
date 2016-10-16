package com.example.wsd_client.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.OrderModleActivity;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.OrderModle;
import com.example.wsd_client.entity.OrderModleInfo;
/**
 * 订单模式adapter
 * @author wsd_leiguoqiang
 */
public class OrderModleAdapter extends BaseAdapter{
	private Context context;
	/**
	 * 数据集合，订单名称集合
	 */
	private List<String> list_order_name;
	/**
	 * 订单模式对象
	 */
	private OrderModle orderModle;
	private List<OrderModleInfo> list_orderModleInfo;

	public OrderModleAdapter(Context context, List<String> list_order_name) {
		super();
		this.context = context;
		this.list_order_name = list_order_name;
		this.orderModle = ((Myapplication)Myapplication.getContext()).getOrderModle();
		this.list_orderModleInfo = orderModle.getList_orderModleInfo();
	}
	@Override
	public int getCount() {
		return list_order_name==null?0:list_order_name.size();
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final String orderName = list_order_name.get(position);
		ViewHolder holder = null;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.base_layout_for_dapter_item__order_modle, null);
			holder = new ViewHolder();
			holder.btn_delete = (Button) convertView.findViewById(R.id.btn_base_textview_item_delete);
			holder.btn_edite = (Button) convertView.findViewById(R.id.btn_base_textview_item_edite);
			holder.tv_order_name = (TextView) convertView.findViewById(R.id.tv_order_name);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_order_name.setText(orderName);
		holder.btn_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//删除数据，再更新数据
				list_order_name.remove(position);
				OrderModleAdapter.this.notifyDataSetChanged();
				//保存总数据
				for(OrderModleInfo orderModleInfo:list_orderModleInfo){
					if(orderName.equals(orderModleInfo.getOrderModleName())){
						list_orderModleInfo.remove(orderModleInfo);
						break;
					}
				}
				orderModle.saveOrderModle();
			}
		});
		holder.btn_edite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OrderModleActivity orderAcitivity = ((OrderModleActivity)context);
				//弹出编辑页面
				orderAcitivity.displayEditePager();
				//确认按钮
				orderAcitivity.setFlag_confirm(orderAcitivity.EDITE_ORDER);
				orderAcitivity.setPosition(position);
			}
		});
		return convertView;
	}

	class ViewHolder{
		Button btn_edite;
		Button btn_delete;
		TextView tv_order_name;
	}
	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
