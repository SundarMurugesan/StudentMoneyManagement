package com.example.studentmoneymanagement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map.Entry;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

/*
 * 
 * This class will collect all the recent purchases 
 * data and perform an analysis.
 * 
 */
public class PurchasesAnalysis2 extends View{

	private RecentPurchacesSQLDatasource rpDatasource; 
	private ArrayList<String[]> purchaseList = new ArrayList<String[]>();
	
	
	public PurchasesAnalysis2(Context context){
		super(context);
		//init();
		rpDatasource = new RecentPurchacesSQLDatasource(context);
		purchaseList = rpDatasource.readDataAll();
	}

	
	public float[] getPurchasePricesData(){
		float[] purchasesPriceList = new float[purchaseList.size()];

		for(int index = 0; index < purchasesPriceList.length; index++){
			purchasesPriceList[index] = Float.parseFloat(purchaseList.get(index)[2]);
		}
		return purchasesPriceList;
	}
	
	public float[] getPurchasePricesData(String category){
		float[] purchasesPriceList = new float[purchaseList.size()];
		int count = 0;
		
		for(int index = 0; index < purchaseList.size(); index++){
			if(purchaseList.get(index)[3].equals(category)){
				purchasesPriceList[index] =  Float.parseFloat(purchaseList.get(index)[2]);
			}
			count++;
		}
		
		return Arrays.copyOf(purchasesPriceList, count);
		
	}
	
	public HashMap<String, Integer> getDistinctCategoryData(){
		HashMap<String, Integer> temp = new HashMap<String, Integer>();
		
		temp.put("bills", Integer.valueOf(0));
		temp.put("entertainment", Integer.valueOf(0));
		temp.put("groceries", Integer.valueOf(0));
		
		int billsCount = 0, entertainmentCount = 0, groceriesCount = 0;
		
		for(int index = 0; index < purchaseList.size(); index++){
			if(purchaseList.get(index)[3].equals("bills"))
				billsCount++;
			else if(purchaseList.get(index)[3].equals("entertainment"))
				entertainmentCount++;
			else
				groceriesCount++;
		}
		
		temp.put("bills", Integer.valueOf(billsCount));
		temp.put("entertainment", Integer.valueOf(entertainmentCount));
		temp.put("groceries",Integer.valueOf(groceriesCount));
		
		return temp;
	}
	
	public BigDecimal getAveragePurchasesPerMonth(){
		return null;
	}


	public String analyseBills() {
		String text = "You are spending too much on Bills! ";
		
		String[] biggestPurchase = retrieveBiggestPurchase("bills");
		
		text+="Looking at your purchase history, the biggest purchase you have was " + biggestPurchase[1] + " at store " + biggestPurchase[2];
		
		return text;
	}
	
	public String analyseGroceries() {
		String text = "You are spending too much on Groceries! ";
		
		String[] biggestPurchase = retrieveBiggestPurchase("groceries");
		
		text+="Looking at your purchase history, the biggest purchase you have was " + biggestPurchase[1] + " at store " + biggestPurchase[2];
		return text;
	}
	
	public String analyseEntertainment() {
		String text = "You are spending too much on Entertainment! ";
		
		String[] biggestPurchase = retrieveBiggestPurchase("entertainment");
		
		text+="Looking at your purchase history, the biggest purchase you have was " + biggestPurchase[1] + " at store " + biggestPurchase[2];
		
		return text;
	}
	
	

	public String[] retrieveBiggestPurchase(String category){
		String[] biggestPurchase = null;
		double biggestPrice = 0.0;
		
		//retrieve information for the most spent item on Bills
		for(int index = 0; index < purchaseList.size(); index++){
			if(purchaseList.get(index)[3].equals(category)){
				double temp = Double.parseDouble(purchaseList.get(index)[2]);
				
				if(temp > biggestPrice){
					biggestPrice = temp;
					biggestPurchase = purchaseList.get(index);
				}
			}
		}
		
		return biggestPurchase;
	}
	
	
	public double calculateTotal(String category){
		double total = 0.0;
		
		//retrieve information for the category
		for(int index = 0; index < purchaseList.size(); index++){
			if(purchaseList.get(index)[3].equals(category)){
				double price = Double.parseDouble(purchaseList.get(index)[2]);
				
				total+=price;
			}
		}
		
		return total;
	}
	
	
	public String[] walletStatus(){
		//walletDatasource.open();
		String[] wallet = rpDatasource.readData();
		//rpDatasource.close();

		String[] walletStatusUpdate = new String[2];
		
		//check if over/under the user-set budget:
		//over: wallet money is less than budget money
		//under = wallet money is more than budget money
		if(Double.parseDouble(wallet[1]) < Double.parseDouble(wallet[2]))
			walletStatusUpdate[0] = "over";
		else
			walletStatusUpdate[0] = "under";
		
		//set a colour for the budget status:
		//0 = bad(red)
		//3 = good(green)
		if(Double.parseDouble(wallet[1]) > Double.parseDouble(wallet[2])*0.20 + Double.parseDouble(wallet[2]))
			walletStatusUpdate[1] = "3";
		else if(Double.parseDouble(wallet[1]) < Double.parseDouble(wallet[2])*0.20 + Double.parseDouble(wallet[2])
				&& Double.parseDouble(wallet[1]) > Double.parseDouble(wallet[2])*0.10 + Double.parseDouble(wallet[2]))
			walletStatusUpdate[1] =  "2";
		else if(Double.parseDouble(wallet[1]) < Double.parseDouble(wallet[2])*0.10 + Double.parseDouble(wallet[2])
				&& Double.parseDouble(wallet[1]) > Double.parseDouble(wallet[2])*0.05 + Double.parseDouble(wallet[2]))
			walletStatusUpdate[1] = "1";
		else
			walletStatusUpdate[1] = "0";
		
		return walletStatusUpdate;
	}
}
