package com.example.wsd_client.adapter;

import java.util.List;

import com.example.wsd_client.R;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.AccountInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccountListViewAdapter extends BaseAdapter {
	
	private Context context;
	private List<AccountInfo> lists;
	private int i;
	
	public AccountListViewAdapter(Context context, List<AccountInfo> lists,
			int i) {
		super();
		this.context = context;
		this.lists = lists;
		this.i = i;
		Myapplication.log("adapter中五种选择方式被选中的下标：", i+"");
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
	public View getView(int position, View convertView, ViewGroup parent) {
		AccountInfo info = lists.get(position);
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.item_account, null);
			holder.ll1=(LinearLayout) convertView.findViewById(R.id.ll1_account);
			holder.ll2=(LinearLayout) convertView.findViewById(R.id.ll2_account);
			holder.ll3=(LinearLayout) convertView.findViewById(R.id.ll3_account);
			holder.ll4=(LinearLayout) convertView.findViewById(R.id.ll4_account);
			holder.tv1=(TextView) convertView.findViewById(R.id.tv1_account);
			holder.tv2=(TextView) convertView.findViewById(R.id.tv2_account);
			holder.tv3=(TextView) convertView.findViewById(R.id.tv3_account);
			holder.tv4=(TextView) convertView.findViewById(R.id.tv4_account);
			holder.tv1Title=(TextView) convertView.findViewById(R.id.tv1_account_title);
			holder.tv2Title=(TextView) convertView.findViewById(R.id.tv2_account_title);
			holder.tv3Title=(TextView) convertView.findViewById(R.id.tv3_account_title);
			holder.tv4Title=(TextView) convertView.findViewById(R.id.tv4_account_title);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		//重点突出筛选的条件信息
		holder.tv1.setTextSize(14);
		holder.tv1.setTextColor(context.getResources().getColor(R.color.wathet_blue));
		
		if(i==0){
			holder.ll1.setVisibility(View.VISIBLE);
			holder.ll2.setVisibility(View.VISIBLE);
			holder.ll3.setVisibility(View.VISIBLE);
			holder.ll4.setVisibility(View.GONE);
			
			holder.tv1Title.setText("商品名称：");
			holder.tv2Title.setText("购买数量：");
			holder.tv3Title.setText("商品总价：");
			
			holder.tv1.setText(info.getXSO_CementName());
			holder.tv2.setText(info.getXSO_Number()+"");
			holder.tv3.setText("￥"+info.getXSO_TotalPrice()+"元");
		}else if(i==1){
			holder.ll1.setVisibility(View.VISIBLE);
			holder.ll2.setVisibility(View.VISIBLE);
			holder.ll3.setVisibility(View.VISIBLE);
			holder.ll4.setVisibility(View.GONE);
			
			holder.tv1Title.setText("购买时间：");
			holder.tv2Title.setText("购买数量：");
			holder.tv3Title.setText("商品总价：");
			
			holder.tv1.setText(info.getXSO_SetDate());
			holder.tv2.setText(info.getXSO_Number()+"");
			holder.tv3.setText("￥"+info.getXSO_TotalPrice()+"元");
		}else if(i==2){
			holder.ll1.setVisibility(View.VISIBLE);
			holder.ll2.setVisibility(View.VISIBLE);
			holder.ll3.setVisibility(View.VISIBLE);
			holder.ll4.setVisibility(View.GONE);
			
			holder.tv1Title.setText("运输车牌：");
			holder.tv2Title.setText("购买数量：");
			holder.tv3Title.setText("商品总价：");
			
			holder.tv1.setText(info.getXSO_CarCode());
			holder.tv2.setText(info.getXSO_Number()+"");
			holder.tv3.setText("￥"+info.getXSO_TotalPrice()+"元");
		}else if(i==3){
			holder.ll1.setVisibility(View.VISIBLE);
			holder.ll2.setVisibility(View.VISIBLE);
			holder.ll3.setVisibility(View.VISIBLE);
			holder.ll4.setVisibility(View.VISIBLE);
			
			holder.tv1Title.setText("购买日期：");
			holder.tv2Title.setText("商品名称：");
			holder.tv3Title.setText("购买数量：");
			holder.tv4Title.setText("商品总价：");
			
			//重点突出筛选的条件信息
			holder.tv2.setTextSize(14);
			holder.tv2.setTextColor(context.getResources().getColor(R.color.wathet_blue));
			
			holder.tv1.setText(info.getXSO_SetDate());
			holder.tv2.setText(info.getXSO_CementName());
			holder.tv3.setText(info.getXSO_Number()+"");
			holder.tv4.setText("￥"+info.getXSO_TotalPrice()+"元");
		}else if(i==4){
			holder.ll1.setVisibility(View.VISIBLE);
			holder.ll2.setVisibility(View.VISIBLE);
			holder.ll3.setVisibility(View.VISIBLE);
			holder.ll4.setVisibility(View.VISIBLE);
			
			holder.tv1Title.setText("运输车牌：");
			holder.tv2Title.setText("购买日期：");
			holder.tv3Title.setText("购买数量：");
			holder.tv4Title.setText("商品总价：");
			
			//重点突出筛选的条件信息
			holder.tv2.setTextSize(14);
			holder.tv2.setTextColor(context.getResources().getColor(R.color.wathet_blue));
			
			holder.tv1.setText(info.getXSO_CarCode());
			holder.tv2.setText(info.getXSO_SetDate());
			holder.tv3.setText(info.getXSO_Number()+"");
			holder.tv4.setText("￥"+info.getXSO_TotalPrice()+"元");
		}
		return convertView;
	}

	class ViewHolder{
		TextView tv1Title;
		TextView tv2Title;
		TextView tv3Title;
		TextView tv4Title;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		LinearLayout ll1;
		LinearLayout ll2;
		LinearLayout ll3;
		LinearLayout ll4;
	}
	
}
