package com.example.studentmoneymanagement;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class AnalysePurchasesCustomViewCategories extends View implements CustomLineGraph{
	private Paint paint = new Paint();
	private PurchasesAnalysis2 purchasesAnalysis;
	private float[] groceriesPriceDataPoints;
	private float[] entertainmentPriceDataPoints;
	private float[] billsPriceDataPoints;


	public AnalysePurchasesCustomViewCategories(Context context) {
		super(context);
		purchasesAnalysis = new PurchasesAnalysis2(context);
		groceriesPriceDataPoints = retrievePriceDataPoints("groceries");
		entertainmentPriceDataPoints = retrievePriceDataPoints("entertainment");
		billsPriceDataPoints = retrievePriceDataPoints("bills");
		
	}

	public AnalysePurchasesCustomViewCategories(Context context, AttributeSet attrs) {
		super(context, attrs);
		purchasesAnalysis = new PurchasesAnalysis2(context);
		groceriesPriceDataPoints = retrievePriceDataPoints("groceries");
		entertainmentPriceDataPoints = retrievePriceDataPoints("entertainment");
		billsPriceDataPoints = retrievePriceDataPoints("bills");

		invalidate();
		requestLayout();
	}

	@Override
	protected void onDraw(Canvas canvas){
		drawBackgroundGraph(canvas);

		if (groceriesPriceDataPoints.length != 0){
			drawGroceriesPriceLine(canvas);
		}
		if (entertainmentPriceDataPoints.length != 0){
			drawEntertainmentPriceLine(canvas);
		}
		if (billsPriceDataPoints.length != 0){
			drawBillsPriceLine(canvas);
		}

		drawOverLay(canvas);
	}



	public void drawOverLay(Canvas canvas) {
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(3);
		
		RectF rectF = new RectF();
		rectF.set(0,0,getWidth(), getHeight());
		canvas.drawRoundRect(rectF, 0, 0, paint);
	}

	
	public void drawBackgroundGraph(Canvas canvas) {

		paint.setStyle(Style.STROKE);
		paint.setColor(Color.GRAY);
		int numberOfLines = (int) (getHeight()/5);
		
		
		//calculate the number of lines needed for the background of the graph.
		if (groceriesPriceDataPoints.length != 0 && entertainmentPriceDataPoints.length != 0 && billsPriceDataPoints.length != 0){
			
			float groceriesMaxPrice = getMax(groceriesPriceDataPoints);
			float entertainmentMaxPrice = getMax(entertainmentPriceDataPoints);
			float billsMaxPrice = getMax(billsPriceDataPoints);
			
			if((groceriesMaxPrice >= entertainmentMaxPrice) && (groceriesMaxPrice >= billsMaxPrice)){
				numberOfLines =  (int) ((getYPos(getMax(groceriesPriceDataPoints)) + getYPos(getMin(groceriesPriceDataPoints)))/groceriesPriceDataPoints.length);
			}
			else if((entertainmentMaxPrice >= billsMaxPrice) && (entertainmentMaxPrice >= groceriesMaxPrice)){
				numberOfLines =  (int) ((getYPos(getMax(entertainmentPriceDataPoints)) + getYPos(getMin(entertainmentPriceDataPoints)))/entertainmentPriceDataPoints.length);
			}
			else
				numberOfLines =  (int) ((getYPos(getMax(billsPriceDataPoints)) + getYPos(getMin(billsPriceDataPoints)))/billsPriceDataPoints.length);
		}

		for (int i = numberOfLines; i > 0; i--) {
			paint.setColor(Color.WHITE);
			canvas.drawText(""+(numberOfLines*i), 11, getHeight() - (numberOfLines*i), paint);
			Log.d("BG i:",""+ i);
			Log.d("BG nol:",""+ numberOfLines);
			paint.setColor(Color.WHITE);
			canvas.drawLine(0, numberOfLines*i, getWidth(), numberOfLines*i, paint);
		}

	}

	public void drawGroceriesPriceLine(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, getYPos(groceriesPriceDataPoints[0]));;

		for (int i = 1; i < groceriesPriceDataPoints.length; i++) {
			path.lineTo(getXPos(i), getYPos(groceriesPriceDataPoints[i]));
		}
		
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(4);
		paint.setColor(0xFFFFFFE5);
		paint.setAntiAlias(true);
		paint.setShadowLayer(4, 2, 2, color.darker_gray);
		canvas.drawPath(path, paint);
		
		paint.setStyle(Style.FILL);
		for (int i = 1; i < groceriesPriceDataPoints.length; i++) {
			canvas.drawCircle(getXPos(i), getYPos(groceriesPriceDataPoints[i]), 5, paint);
		}
	}
	
	public void drawEntertainmentPriceLine(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, getYPos(entertainmentPriceDataPoints[0]));;

		
		for (int i = 1; i < entertainmentPriceDataPoints.length; i++) {
			path.lineTo(getXPos(i), getYPos(entertainmentPriceDataPoints[i]));
			//canvas.drawCircle(getXPos(i), getYPos(entertainmentPriceDataPoints[i]), 5, paint);
		}

		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(4);
		paint.setColor(0xFFBB00BB);
		paint.setAntiAlias(true);
		paint.setShadowLayer(4, 2, 2, color.darker_gray);
		canvas.drawPath(path, paint);
		
		paint.setStyle(Style.FILL);
		for (int i = 1; i < entertainmentPriceDataPoints.length; i++) {
			canvas.drawCircle(getXPos(i), getYPos(entertainmentPriceDataPoints[i]), 5, paint);
		}
	}
	
	public void drawBillsPriceLine(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, getYPos(billsPriceDataPoints[0]));;

		for (int i = 1; i < billsPriceDataPoints.length; i++) {
			path.lineTo(getXPos(i), getYPos(billsPriceDataPoints[i]));
			//canvas.drawCircle(getXPos(i), getYPos(billsPriceDataPoints[i]), 5, paint);

		}
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(4);
		paint.setColor(0xFF427919);
		paint.setAntiAlias(true);
		paint.setShadowLayer(4, 2, 2, color.darker_gray);
		canvas.drawPath(path, paint);
		
		paint.setStyle(Style.FILL);
		for (int i = 1; i < billsPriceDataPoints.length; i++) {
			canvas.drawCircle(getXPos(i), getYPos(billsPriceDataPoints[i]), 5, paint);
		}
	}

	public float[] retrievePriceDataPoints(String category) {
		return purchasesAnalysis.getPurchasePricesData(category);
	}

	public float getYPos(float value) {
		float x = value;
		float height = getHeight() - getPaddingTop() - getPaddingBottom();
		float maxValue = getMax(groceriesPriceDataPoints);
		// scale it to the view size
		value = (value / maxValue) * height;
		// invert it so that higher values have lower y
		value = height - value;
		// offset it to adjust for padding
		value += getPaddingTop();

		Log.d("PRICE VALUE: " + x, "RETURN VALUE: " + value);
		Log.d("Padding top: " + getPaddingTop(), "Padding Bottom: " + getPaddingBottom());
		Log.d("getHeight: ", ""+getHeight());

		return value;
	}

	public float getXPos(float value) {
		float width = getWidth() - getPaddingLeft() - getPaddingRight();
		float maxValue = groceriesPriceDataPoints.length - 1;
		// scale it to the view size
		value = (value / maxValue) * width;
		// offset it to adjust for padding
		value += getPaddingLeft();
		return value;
	}

	public float getMax(float[] array) {
		float max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}

	public float getMin(float[] array) {
		float min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] < min) {
				min = array[i];
			}
		}
		return min;
	}

	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec/2);
	}

}

