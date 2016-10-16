package com.example.wsd_client.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.example.wsd_client.R;
import com.example.wsd_client.activity.ManageActivity;
import com.example.wsd_client.entity.XS_Order_ABase;

/**
 * 订单中心adapter
 */
public class DingdanAdapter extends BaseAdapter {
	private List<XS_Order_ABase>lists;
	private Context context;
	
	public DingdanAdapter(List<XS_Order_ABase> lists, Context context) {
		super();
		this.lists = lists;
		this.context = context;
	}

	@Override
	public int getCount() {
		
		return lists.size()==0?0:lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.dingdan_item, null);
			holder.tvLadeid=(TextView) convertView.findViewById(R.id.tv_dingdan_item_ladeid);
			holder.tvSetdate=(TextView) convertView.findViewById(R.id.tv_dingdan_item_setDate);
			holder.tvCementname=(TextView) convertView.findViewById(R.id.tv_dingdan_item_cementname);
			holder.tvCount=(TextView) convertView.findViewById(R.id.tv_dingdan_item_count);
			//holder.btn_detail=(Button) convertView.findViewById(R.id.btn_dingdan_item_detail);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final XS_Order_ABase order_ABase=lists.get(position);
		holder.tvLadeid.setText(""+order_ABase.getXSO_LadeID());
		holder.tvSetdate.setText(""+order_ABase.getXSO_SetDate());
		holder.tvCementname.setText(""+order_ABase.getXSO_CementName());
		
		holder.tvCount.setText((position+1)+"");
//		holder.btn_detail.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent=new Intent(context, ManageActivity.class);
//				
//				intent.putExtra("order_ABase",order_ABase );
//				context.startActivity(intent);
//				
//			}
//		});
		return convertView;
	}
	static class ViewHolder{
		public TextView tvLadeid;//订单编号
		public TextView tvSetdate;//下单时间
		public TextView tvCementname;//商品名称
		public TextView tvCount;//商品计数
		public Button btn_detail;//查看详情按钮
	}
}
