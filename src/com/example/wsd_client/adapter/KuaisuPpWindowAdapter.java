package com.example.wsd_client.adapter;

import java.util.List;

import com.example.wsd_client.R;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KuaisuPpWindowAdapter extends BaseAdapter {
	
	private Context context;
	private List<Product> lists;
	
	public KuaisuPpWindowAdapter(Context context, List<Product> lists) {
		super();
		this.context = context;
		this.lists = lists;
		Myapplication.log("spinnerAdapterµÄlistsÊý¾Ý£º", lists.size()+"");
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
		Product info = lists.get(position);
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.item_popup_window, null);
			holder.tvPpWindow=(TextView) convertView.findViewById(R.id.tv_item_kuaisu_ppWindow_show);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		holder.tvPpWindow.setText(info.getYWM_Name());
		
		return convertView;
	}

	class ViewHolder{
		TextView tvPpWindow;
	}
}
