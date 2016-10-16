package com.example.wsd_client.fragment;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.MainActivity;
import com.example.wsd_client.util.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class BaobiaoFragment extends Fragment implements OnClickListener{
	private TextView tvTitle;
	private View view;
	private CircleImageView civ;
	private ImageButton ibBack;
	private ImageButton ibCart;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_baobiao, null);
		
		initView();

		setAdapter();

		setListener();
		return view;
	}
	
	
	/**
	 * 初始化控件
	 */
	private void initView() {
		tvTitle=(TextView) view.findViewById(R.id.tv_title);
		civ=(CircleImageView) view.findViewById(R.id.iv_contact);
		ibBack=(ImageButton) view.findViewById(R.id.ib_title_back);
		ibCart=(ImageButton) view.findViewById(R.id.ib_title_cart);
		tvTitle.setText("资金报表");
		civ.setVisibility(View.INVISIBLE);
		ibBack.setVisibility(View.VISIBLE);
		ibCart.setVisibility(View.INVISIBLE);
		
	}


	private void setAdapter() {
		// TODO Auto-generated method stub
		
	}


	private void setListener() {
		ibBack.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_title_back:
			Intent intent = new Intent (getActivity(),MainActivity.class);
			startActivity(intent);
			//销毁当前碎片所在的Activity
			getActivity().finish();
			break;

		default:
			break;
		}
		
	}
}
