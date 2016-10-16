package com.example.wsd_client.fragment;

import com.example.wsd_client.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * …Ã∆∑œÍ«Èfragment
 * @author wsd_leiguoqiang
 */
public class ProductDetailsPageProductDetailsFragment extends Fragment{

	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.fragment_product_details_pager_product_detials, null);
		return view;
	}
}
