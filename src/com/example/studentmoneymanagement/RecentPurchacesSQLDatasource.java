package com.example.studentmoneymanagement;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/*
 * 
 * Maintains the conenction for purchaces database.
 * Database operations are done in this class.
 * 
 */
public class RecentPurchacesSQLDatasource {
	private SQLiteDatabase database;
	private RecentPurchacesSQLContract recentPurchacesHelper;
	private static final int CURSOR_COLUMN_NAME_STORE=0;
	private static final int CURSOR_COLUMN_NAME_ITEM=1;
	private static final int CURSOR_COLUMN_NAME_PRICE=2;
	private static final int CURSOR_COLUMN_NAME_CATEGORY=3;
	private static final int CURSOR_COLUMN_NAME_DEBIT_CREDIT=4;
	
	private String[] columns = {RecentPurchacesSQLContract.COLUMN_NAME_STORE, RecentPurchacesSQLContract.COLUMN_NAME_ITEM, 
			RecentPurchacesSQLContract.COLUMN_NAME_PRICE, RecentPurchacesSQLContract.COLUMN_NAME_CATEGORY, RecentPurchacesSQLContract.COLUMN_NAME_DEBIT_CREDIT};
	
	public RecentPurchacesSQLDatasource(Context context){
		recentPurchacesHelper = new RecentPurchacesSQLContract(context);
	}
	
	public void open() throws SQLException{
		database = recentPurchacesHelper.getWritableDatabase();
		Log.d("DATABASE OPENED", database.getPath());
	}
	
	public void close(){
		recentPurchacesHelper.close();
	}
	
	/*Purchaces Database operations*/
	public void createNewPurchace(String store, String item, String price, String category){
		
		this.open();
		ContentValues values = new ContentValues();
		
		values.put(RecentPurchacesSQLContract.COLUMN_NAME_STORE, store);
		values.put(RecentPurchacesSQLContract.COLUMN_NAME_ITEM, item);
		values.put(RecentPurchacesSQLContract.COLUMN_NAME_PRICE, price);
		values.put(RecentPurchacesSQLContract.COLUMN_NAME_CATEGORY, category);
		values.put(RecentPurchacesSQLContract.COLUMN_NAME_DEBIT_CREDIT, "credit");
		
		database.insert(RecentPurchacesSQLContract.TABLE_NAME, null, values);
		this.close();
	}
	
	/**
	 * Purchases Database Operations - creates a new purchase
	 * 
	 * @param store
	 * @param item
	 * @param price
	 * @param category
	 * @param debit_credit
	 */
	public void createNewPurchace(String store, String item, String price, String category, String debit_credit){
		
		this.open();
		ContentValues values = new ContentValues();
		
		values.put(RecentPurchacesSQLContract.COLUMN_NAME_STORE, store);
		values.put(RecentPurchacesSQLContract.COLUMN_NAME_ITEM, item);
		values.put(RecentPurchacesSQLContract.COLUMN_NAME_PRICE, price);
		values.put(RecentPurchacesSQLContract.COLUMN_NAME_CATEGORY, category);
		values.put(RecentPurchacesSQLContract.COLUMN_NAME_DEBIT_CREDIT, debit_credit);
		
		database.insert(RecentPurchacesSQLContract.TABLE_NAME, null, values);
		this.close();
	}
	
	/*
	 ***************************************** readData() **********************************
	 * get the user's last purchased item. 
	 */
	public String[] readData(){
		//ArrayList<Wallet> purchaseList = new ArrayList<Wallet>();
		//return all columns in the database with no WHERE clause /group order
		
		this.open();
		Cursor cursor = database.query(RecentPurchacesSQLContract.TABLE_NAME, columns, null, null, null, null, null);
		if(cursor.getCount() <= 0)
			createNewPurchace("Default Store", "default Product", "0", "other");
		
		cursor.moveToFirst();
		String[] c = {cursor.getString(CURSOR_COLUMN_NAME_STORE), cursor.getString(CURSOR_COLUMN_NAME_ITEM), cursor.getString(CURSOR_COLUMN_NAME_PRICE)
				, cursor.getString(CURSOR_COLUMN_NAME_CATEGORY), cursor.getString(CURSOR_COLUMN_NAME_DEBIT_CREDIT)};
		cursor.close();
		this.close();
		return c;
	}
	
	public ArrayList<String[]> readDataAll() {
		// TODO Auto-generated method stub
		//return all columns in the database with no WHERE clause /group order
		this.open();
		
		ArrayList<String[]> purchases = new ArrayList<String[]>();
		Cursor cursor = database.query(RecentPurchacesSQLContract.TABLE_NAME, columns, null, null, null, null, null);
		while(cursor.moveToNext() && cursor.getCount() > 0){
			String[] c = {cursor.getString(CURSOR_COLUMN_NAME_STORE), cursor.getString(CURSOR_COLUMN_NAME_ITEM), cursor.getString(CURSOR_COLUMN_NAME_PRICE), cursor.getString(CURSOR_COLUMN_NAME_CATEGORY), cursor.getString(CURSOR_COLUMN_NAME_DEBIT_CREDIT)};
			Log.d("RECENT PURCHASES READALL", c[0] + "- " + c[1] + "- " + c[2] + "- "+ c[3] + "- " + c[4]);
			purchases.add(c);
		}
		cursor.close();
		
		this.close();
		
		return purchases;
	}

	public void deleteAllData() {
		this.open();
		database.delete(RecentPurchacesSQLContract.TABLE_NAME, null, null);
		this.close();
	}
}
