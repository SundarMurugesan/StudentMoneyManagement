package com.example.studentmoneymanagement;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class AnalysePurchasesCustomView extends View implements Runnable{

	private Paint paint = new Paint();
	private PurchasesAnalysis2 purchasesAnalysis;
	private float[] priceDataPoints;
	private float highestPurchase;


	public AnalysePurchasesCustomView(Context context) {
		super(context);
		purchasesAnalysis = new PurchasesAnalysis2(context);
		priceDataPoints =  retrievePriceDataPoints();
		
		invalidate();
	}

	public AnalysePurchasesCustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		purchasesAnalysis = new PurchasesAnalysis2(context);
		priceDataPoints =  retrievePriceDataPoints();
		
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas){
		drawBackgroundGraph(canvas);
		
		if (priceDataPoints.length != 0){
			drawPriceLine(canvas);
		}
		
		drawOverLay(canvas);
	}

	

	private void drawOverLay(Canvas canvas) {
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(3);
		
        RectF rectF = new RectF();
        rectF.set(0,0,getWidth(), getHeight());
        canvas.drawRoundRect(rectF, 0, 0, paint);
	}

	private void drawBackgroundGraph(Canvas canvas) {
		
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.GRAY);
		int linesRange = 5;
		
		//calculate the number of lines needed for the background of the graph.
		if (priceDataPoints.length != 0)
			linesRange =  (int) ((getYPos(getMax(priceDataPoints)) + getYPos(getMin(priceDataPoints)))/priceDataPoints.length);
		else
			linesRange =  (int) (getHeight()/5);
		
		
		for (int i = linesRange; i >= 0; i--) {
			paint.setColor(Color.WHITE);
			canvas.drawText(""+(linesRange*i), 11, getHeight() -( linesRange*i), paint);
			Log.d("BG i:",""+ i*linesRange);
			Log.d("BG nol:",""+ linesRange);
			paint.setColor(Color.WHITE);
			canvas.drawLine(0,linesRange*i, getWidth(), linesRange*i, paint);
		}

	}

	private void drawPriceLine(Canvas canvas) {
		paint.setStyle(Style.STROKE);
		Path path = new Path();
		path.moveTo(0, getYPos(priceDataPoints[0]));

		paint.setStyle(Style.FILL);
		paint.setColor(0xFF33B5E5);
		
		for (int i = 1; i < priceDataPoints.length; i++) {
			if(highestPurchase < priceDataPoints[i]){
				highestPurchase = priceDataPoints[i];
			}
			path.lineTo(getXPos(i), getYPos(priceDataPoints[i]));
		}
		
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(4);
		paint.setColor(0xFF33B5E5);
		paint.setAntiAlias(true);
		paint.setShadowLayer(4, 2, 2, color.darker_gray);
		canvas.drawPath(path, paint);
		
		paint.setStyle(Style.FILL);
		paint.setColor(0xFFFFFFFF);
		
		for (int i = 1; i < priceDataPoints.length; i++) {
			canvas.drawCircle(getXPos(i), getYPos(priceDataPoints[i]), 5, paint);
		}
		
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(4);
		paint.setColor(0xFF33B5E5);
		
		float max = getMax(priceDataPoints);
		canvas.drawCircle(getXPos(max), getYPos(max), 600, paint);
		paint.setShadowLayer(0, 0, 0, 0);
	}
	
	private float[] retrievePriceDataPoints() {
		return purchasesAnalysis.getPurchasePricesData();
	}
	

	private float getYPos(float value) {
		float x = value;
		float height = getHeight() - getPaddingTop() - getPaddingBottom();
		float maxValue = getMax(priceDataPoints);
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
	
	private float getXPos(float value) {
		float width = getWidth() - getPaddingLeft() - getPaddingRight();
		float maxValue = priceDataPoints.length - 1;
		// scale it to the view size
		value = (value / maxValue) * width;
		// offset it to adjust for padding
		value += getPaddingLeft();
		return value;
	}

	private float getMax(float[] array) {
		float max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
	
	private float getMin(float[] array) {
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
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
