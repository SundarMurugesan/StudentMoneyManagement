package com.example.studentmoneymanagement;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter{

	private ArrayList<String[]> listOfPurchases;
	Activity _activity;

	public CustomListAdapter(Activity activity) {
		// TODO Auto-generated constructor stub
		_activity = activity;

		RecentPurchacesSQLDatasource purchasesDatasource = new RecentPurchacesSQLDatasource(activity);
		listOfPurchases = purchasesDatasource.readDataAll();
		
		
	}

	public View getView(int position, View convertView, ViewGroup parent){
		View v = convertView;
		
		LayoutInflater vi = (LayoutInflater)_activity.getSystemService(_activity.LAYOUT_INFLATER_SERVICE);
		v = vi.inflate(R.layout.check_purchases_main2, parent, false);


		// for(int index = 0; index < listOfPurchases.size(); index++){
		//TextView tt = (TextView) v.findViewById(R.id.icon);
		TextView tt1 = (TextView) v.findViewById(R.id.item);
		TextView tt2 = (TextView) v.findViewById(R.id.price);
		ImageView imageView = (ImageView) v.findViewById(R.id.categoryImage);

		tt1.setText(listOfPurchases.get(position)[1] + " (" + listOfPurchases.get(position)[0] +")");
		tt2.setText("$"+listOfPurchases.get(position)[2]);

		if("groceries".equalsIgnoreCase(listOfPurchases.get(position)[3])){
			imageView.setImageResource(R.drawable.shoppingcart2);
		}
		if("entertainment".equalsIgnoreCase(listOfPurchases.get(position)[3])){
			imageView.setImageResource(R.drawable.games2);
		}
		if("bills".equalsIgnoreCase(listOfPurchases.get(position)[3])){
			imageView.setImageResource(R.drawable.bills2);
		}
		if("other".equalsIgnoreCase(listOfPurchases.get(position)[3])){
			imageView.setImageResource(R.drawable.other2);
		}
		
		if("debit".equalsIgnoreCase(listOfPurchases.get(position)[4]))
			tt2.setTextColor(Color.GREEN);
		//  }
		return v;

	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listOfPurchases.size();
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listOfPurchases.get(position);
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
