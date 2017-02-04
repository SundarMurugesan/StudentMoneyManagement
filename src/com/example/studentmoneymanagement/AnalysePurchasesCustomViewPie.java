package com.example.studentmoneymanagement;

import java.util.HashMap;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class AnalysePurchasesCustomViewPie extends View{

	private Paint paint = new Paint();
	private PurchasesAnalysis2 purchasesAnalysis;
	private HashMap<String, Integer> priceDataPoints;
	private static final int BILLS_PIE_COLOR = 0xBB0066FF;
	private static final int GROCERIES_PIE_COLOR = 0xBB0000FF;
	private static final int ENTERTAINMENT_PIE_COLOR = 0xBBFFFFFF;
	private static final int BORDER_Y_BOTTOM = 20;
	private static final int BORDER_Y_TOP = 20;
	private static final int BORDER_X_LEFT = 20;
	private static final int BORDER_X_RIGHT = 20;
	private static final int TEXT_TO_SQUARE_OFFSET = 5;
	private RectF billsLegendColorBox;
	private RectF groceriesLegendColorBox ;
	private RectF entertainmentLegendColorBox;

	public AnalysePurchasesCustomViewPie(Context context) {
		super(context);
		purchasesAnalysis = new PurchasesAnalysis2(context);
		priceDataPoints =  retrievePriceDataPoints();
	}

	public AnalysePurchasesCustomViewPie(Context context, AttributeSet attrs) {
		super(context, attrs);
		purchasesAnalysis = new PurchasesAnalysis2(context);
		priceDataPoints =  retrievePriceDataPoints();
		invalidate();
		requestLayout();
		setLayerType(View.LAYER_TYPE_HARDWARE, null);

	}

	@Override
	protected void onDraw(Canvas canvas){
		drawPricePie(canvas);
		drawLegend(canvas);
		//drawStatisticsOverlay(canvas);
	}

	private void drawPricePie(Canvas canvas) {
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setStrokeWidth(4);
		paint.setAntiAlias(true);
		paint.setShadowLayer(4, 2, 2, color.darker_gray);

		int billsCount = priceDataPoints.get("bills"), 
				entertainmentCount = priceDataPoints.get("entertainment"), 
				groceriesCount = priceDataPoints.get("groceries");


		int totalCategories = billsCount + entertainmentCount + groceriesCount;
		if(totalCategories == 0)
			totalCategories = 1;
		RectF rect = new RectF(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2);


		paint.setShadowLayer(10, 10, 10, 255);
		paint.setColor(BILLS_PIE_COLOR);
		canvas.drawArc(rect, 0, ((360*billsCount)/totalCategories), true, paint);
		paint.setColor(GROCERIES_PIE_COLOR);
		canvas.drawArc(rect, ((360*billsCount)/totalCategories), ((360*entertainmentCount)/totalCategories ), true, paint);
		paint.setColor(ENTERTAINMENT_PIE_COLOR);
		canvas.drawArc(rect, ((360*billsCount)/totalCategories) + ((360*entertainmentCount)/totalCategories ), ((360*groceriesCount)/totalCategories), true, paint);

		paint.setShadowLayer(200, 200, 200, 0x00000000);


	}

	/**
	 * Method that draws the legend
	 * @param canvas
	 */
	private void drawLegend(Canvas canvas){
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);
		paint.setAntiAlias(true);
		paint.setShadowLayer(4, 2, 2, color.darker_gray);
		float pieYOffset = getHeight()/8;
		float scaleFactor = 0.15F;

		billsLegendColorBox = new RectF(BORDER_X_LEFT + 50, getHeight() - BORDER_Y_BOTTOM, BORDER_X_LEFT, getHeight() - BORDER_Y_BOTTOM - 50);
		groceriesLegendColorBox = new RectF(getWidth()/2 + 50  - BORDER_X_RIGHT , getHeight() - BORDER_Y_BOTTOM, getWidth()/2 - BORDER_X_LEFT, getHeight() - BORDER_Y_BOTTOM - 50);
		entertainmentLegendColorBox = new RectF(getWidth() - BORDER_X_RIGHT - 50 - (TEXT_TO_SQUARE_OFFSET + 15), getHeight() - BORDER_Y_BOTTOM, getWidth() - BORDER_X_RIGHT - (TEXT_TO_SQUARE_OFFSET + 15), getHeight() - BORDER_Y_BOTTOM - 50);

		paint.setColor(BILLS_PIE_COLOR);
		canvas.drawRect(billsLegendColorBox, paint);
		paint.setColor(GROCERIES_PIE_COLOR);
		canvas.drawRect(groceriesLegendColorBox, paint);
		paint.setColor(ENTERTAINMENT_PIE_COLOR);
		canvas.drawRect(entertainmentLegendColorBox, paint);


		paint.setColor(Color.WHITE);
		canvas.drawText("BILLS", billsLegendColorBox.left + TEXT_TO_SQUARE_OFFSET , billsLegendColorBox.centerY(), paint);
		canvas.drawText("GROCERIES", groceriesLegendColorBox.left + TEXT_TO_SQUARE_OFFSET, groceriesLegendColorBox.centerY(), paint);	
		canvas.drawText("ENTERTAINMENT", entertainmentLegendColorBox.left + TEXT_TO_SQUARE_OFFSET, entertainmentLegendColorBox.centerY(), paint);

		paint.setShadowLayer(200, 200, 200, 0x00000000);
	}

	/**
	 * Method that draws the legend
	 * @param canvas
	 */
	/*private void drawStatisticsOverlay(Canvas canvas){
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);
		paint.setAntiAlias(true);
		paint.setShadowLayer(4, 2, 2, color.darker_gray);

		paint.setColor(Color.WHITE);
		RectF rectF = new RectF(getWidth() - BORDER_X_LEFT ,getHeight() - getHeight() - BORDER_Y_BOTTOM - TEXT_TO_SQUARE_OFFSET, getWidth() - BORDER_X_RIGHT , getHeight()/2);
		canvas.drawRoundRect(rectF, 0, 0, paint);
	}*/

	private HashMap<String, Integer> retrievePriceDataPoints() {
		return purchasesAnalysis.getDistinctCategoryData();
	}


	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

}
