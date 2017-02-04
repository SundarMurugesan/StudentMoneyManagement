package com.example.studentmoneymanagement;

import java.math.BigDecimal;

import com.example.studentmoneymanagement.R;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class RecentPurchacesActivity extends MainActivity {

	//RecentPurchases rp = new RecentPurchases();
	private Button addPurchaseButton;
	private Button backButton;
	private Button recentPurchaseButton;
	private Button analysePurchaseButton;
	private Button clearPurchasesButton;
	
	private Button entertainmentButton;
	private Button billsButton;
	private Button groceriesButton;
	private Button creditButton;
	private Button debitButton;
	
	private EditText productInput;
	private EditText priceInput;
	private EditText storeInput;
	private boolean isEntertainmentPressed = false;
	private boolean isBillsPressed = false;
	private boolean isGroceriesPressed = false;
	

	private boolean creditButtonPressed = false;
	private boolean isCreditButtonPressed = false;
	private boolean debitButtonPressed = false;
	private boolean isdebitButtonPressed = false;
	
	//private RadioButton bills;
	//private RadioButton entertainment;
	//private RadioButton groceries;
	private String category;
	//private RadioGroup radioGroup; 
	
	//Wallet wallet = super.getWallet();
	protected RecentPurchacesSQLDatasource purchasesDatasource;
	private WalletSQLDatasource walletDatasource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.purchaces_activity_main);
		purchasesDatasource = new RecentPurchacesSQLDatasource(this);
		walletDatasource = new WalletSQLDatasource(this);
		
		
		backButton = (Button) findViewById(R.id.backbutton2);
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		
				finish();
			}
		});
		
		addPurchaseButton = (Button) findViewById(R.id.addapurchasebutton1);
		addPurchaseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				//retrieve recent purchase information and store in database.
				storeInput = (EditText)findViewById(R.id.storeEditText);
				productInput = (EditText)findViewById(R.id.productEditText);
				priceInput = (EditText)findViewById(R.id.priceEditText);
				
				if(storeInput.getText() == null || storeInput.getText().toString().equals(""))
					storeInput.setText("unknown store");
				if(productInput.getText() == null || productInput.getText().toString().equals(""))
					productInput.setText("unknown product");
				if(priceInput.getText() == null || priceInput.getText().toString().equals(""))
					priceInput.setText("0.00");
				
				if(isBillsPressed)
					category = "bills";
				else if(isEntertainmentPressed)
					category = "entertainment";
				else if(isGroceriesPressed)
					category = "groceries";
				else
					category = "other";
				

				String data[] = walletDatasource.readData();
				String priceString = priceInput.getText().toString();
				//priceString = priceString.contains("+") || priceString.contains("-") ? priceString.substring(1, priceString.length()) : priceString; 
				String debit_credit =  debitButton.isSelected() ? "debit" :  "credit";
				
				BigDecimal priceInputDecimal = new BigDecimal(priceString);
				BigDecimal walletDecimal = new BigDecimal(data[1]);
				BigDecimal budgetDecimal = new BigDecimal(data[2]);
				
				//precision safety for price input.
				priceInputDecimal = priceInputDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
				if(debitButton.isSelected()){
					walletDecimal = walletDecimal.add(priceInputDecimal);
				}
				else{
					walletDecimal = walletDecimal.subtract(priceInputDecimal);
					budgetDecimal = walletDecimal.subtract(priceInputDecimal);
				}
				
				//precision safety for budget and wallet.
				walletDecimal = walletDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
				budgetDecimal = budgetDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
				
				purchasesDatasource.createNewPurchace(storeInput.getText().toString(), productInput.getText().toString(), priceInputDecimal.toString(), category, debit_credit);
				
				walletDatasource.updateWallet(walletDecimal.toString(), budgetDecimal.toString(), data[0]);
				
				Context context = getApplicationContext();
				CharSequence text = "New " + debit_credit + " purchase was added";
				int duration = Toast.LENGTH_SHORT;

				storeInput.setHint("Store");
				productInput.setHint("Product");
				priceInput.setHint("Price");
				
				 Toast toast = Toast.makeText(context, text, duration);
				 
				 toast.show();
			}
		});
		
		recentPurchaseButton = (Button) findViewById(R.id.recentpurchasebutton1);
		recentPurchaseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), CheckPurchasesActivity.class);
				startActivity(i);
			}
		});
		
		analysePurchaseButton = (Button) findViewById(R.id.analysepurchasebutton1);
		analysePurchaseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), AnalysePurchasesActivity2.class);
				startActivity(i);
			}
		});	
		
		clearPurchasesButton = (Button) findViewById(R.id.clearpurchasesbutton1);
		clearPurchasesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//	purchasesDatasource.open();
				purchasesDatasource.deleteAllData();	
			//	purchasesDatasource.close();
				
				Context context = getApplicationContext();
				CharSequence text = "All Purchases are removed";
				int duration = Toast.LENGTH_SHORT;

				 Toast toast = Toast.makeText(context, text, duration);
				 toast.show();
			}
		});
		
		entertainmentButton = (Button) findViewById(R.id.entertainmentChoiceBtn);
		entertainmentButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if( entertainmentButton.isSelected()){
					entertainmentButton.setSelected(false);				
				}
				else{
					entertainmentButton.setSelected(true);
				}
				
				isEntertainmentPressed = entertainmentButton.isSelected();
				billsButton.setSelected(false);
				groceriesButton.setSelected(false);
		
			}
		});
		
		
		billsButton = (Button) findViewById(R.id.billsChoiceBtn);
		billsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if( billsButton.isSelected()){
					billsButton.setSelected(false);
				}
				else{
					billsButton.setSelected(true);
				}
				
				isBillsPressed = billsButton.isSelected();
				entertainmentButton.setSelected(false);
				groceriesButton.setSelected(false);
		
			}
		});
		
		
		groceriesButton = (Button) findViewById(R.id.groceriesChoiceBtn);
		groceriesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if( groceriesButton.isSelected()){
					groceriesButton.setSelected(false);					
				}
				else{
					groceriesButton.setSelected(true);
					isGroceriesPressed = true;
				}
		
				isGroceriesPressed = groceriesButton.isSelected();
				entertainmentButton.setSelected(false);
				billsButton.setSelected(false);
			}
			
		});
		
		creditButton = (Button) findViewById(R.id.creditBtn);
		creditButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if( creditButton.isSelected()){
					creditButton.setSelected(false);					
				}
				else{
					creditButton.setSelected(true);
					creditButtonPressed = true;
				}
		
				isCreditButtonPressed = creditButton.isSelected();
				debitButton.setSelected(false);
			}
			
		});
		
		debitButton = (Button) findViewById(R.id.debitBtn);
		debitButton.setOnClickListener(new View.OnClickListener() {


			public void onClick(View v) {
				if( debitButton.isSelected()){
					debitButton.setSelected(false);					
				}
				else{
					debitButton.setSelected(true);
					debitButtonPressed = true;
				}
		
				isdebitButtonPressed = debitButton.isSelected();
				creditButton.setSelected(false);
			}
			
		});
		
		
		if( !debitButton.isSelected()){
			creditButton.setSelected(true);					
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
