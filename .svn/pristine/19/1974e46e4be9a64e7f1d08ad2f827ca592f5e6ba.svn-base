package com.example.wsd_client.adapter;

import java.util.List;

import com.example.wsd_client.R;
import com.example.wsd_client.entity.NewsResult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class XiaoxiAdapter extends BaseAdapter{
	
	private Context context;
	private List<NewsResult> lists;
	
	
	public XiaoxiAdapter(Context context, List<NewsResult> lists) {
		super();
		this.context = context;
		this.lists = lists;
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
		ViewHolder holder;
		NewsResult newsResult = lists.get(position);
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.xiaoxi_item, null);
			holder.tvMessageID=(TextView) convertView.findViewById(R.id.tv_xiaoxi_messageID);
			holder.tvmeMsageInfo=(TextView) convertView.findViewById(R.id.tv_xiaoxi_messageInfo);
			holder.tvInputDate=(TextView) convertView.findViewById(R.id.tv_xiaoxi_inputDate);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		holder.tvMessageID.setText(position+1+"");
		holder.tvmeMsageInfo.setText(newsResult.getMessageInfo());
		holder.tvInputDate.setText(newsResult.getInputDate());
		
		return convertView;
	}

	
	class ViewHolder{
		TextView tvMessageID;
		TextView tvmeMsageInfo;
		TextView tvInputDate;
	}
}
