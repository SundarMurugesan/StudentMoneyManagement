package com.example.studentmoneymanagement;

import java.util.ArrayList;
import com.example.studentmoneymanagement.R;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class CheckPurchasesActivity extends MainActivity {

	
	//DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
	private ListView purchasesList;
	//private String strLine = null;
    private CustomListAdapter m_adapter;
    

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.check_purchases_main);
	
		m_adapter = new CustomListAdapter(this);
		purchasesList = (ListView) findViewById(R.id.purchaseList);	
		purchasesList.setAdapter(m_adapter);
		
	
		m_adapter.notifyDataSetChanged();
	
	}
	
	
}