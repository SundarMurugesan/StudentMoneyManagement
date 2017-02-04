package com.example.studentmoneymanagement;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class CustomBudgetStatusView extends View{

	private PurchasesAnalysis2 pa2;
	private Paint paint = new Paint();
	private static final int BORDER_OFFSET_X = 10;
	private static final int BORDER_OFFSET_Y = 10;
	
	public CustomBudgetStatusView(Context context, AttributeSet attrs) {
		super(context, attrs);
		pa2 = new PurchasesAnalysis2(context);
		invalidate();
		requestLayout();
	}

	@Override
	protected void onDraw(Canvas canvas){
		drawOverLay(canvas);
		drawBudgetStatus(canvas);
	}

	private void drawBudgetStatus(Canvas canvas) {
		HashMap<String, Integer> distinctCategoryData = pa2.getDistinctCategoryData();

		int billsCount = distinctCategoryData.get("bills");
		int groceriesCount = distinctCategoryData.get("groceries");
		int entertainmentCount = distinctCategoryData.get("entertainment");
		
		paint.setTextSize(24);
		if(billsCount > groceriesCount && billsCount > entertainmentCount){
			canvas.drawText(pa2.analyseBills(), BORDER_OFFSET_X, BORDER_OFFSET_Y + paint.getTextSize(), paint);
			//Log.d("TESST", "PASS");
		}
		else if(groceriesCount > billsCount && groceriesCount > entertainmentCount)
		{
			canvas.drawText(pa2.analyseGroceries(), BORDER_OFFSET_X, BORDER_OFFSET_Y + paint.getTextSize(), paint);
		}
		else if (entertainmentCount > groceriesCount && entertainmentCount > billsCount){
			canvas.drawText(pa2.analyseEntertainment(), BORDER_OFFSET_X, BORDER_OFFSET_Y + paint.getTextSize(), paint); 
		}
		
		canvas.drawText("Total Bills: " + pa2.calculateTotal("bills"), BORDER_OFFSET_X, BORDER_OFFSET_Y + paint.getTextSize()*2, paint);
		canvas.drawText("Total Entertainment: " + pa2.calculateTotal("entertainment"), BORDER_OFFSET_X, BORDER_OFFSET_Y + paint.getTextSize()*3, paint);
		canvas.drawText("Total Groceries: " + pa2.calculateTotal("groceries"), BORDER_OFFSET_X, BORDER_OFFSET_Y + paint.getTextSize()*4, paint);
	

	}

	private void drawOverLay(Canvas canvas) {
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.WHITE);

		RectF rectF = new RectF();
		rectF.set(0,0,getWidth()-10, getHeight()-10);
		canvas.drawRoundRect(rectF, 10, 10, paint);
	}
}
