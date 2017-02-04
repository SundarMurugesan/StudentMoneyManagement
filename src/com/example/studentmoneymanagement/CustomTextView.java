package com.example.studentmoneymanagement;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView{

	public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}
	
	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
		
	}
	
	public CustomTextView(Context context) {
		super(context);
		init(null);
	}
	
	private void init(AttributeSet attrs) {
		if (attrs!=null) {
			 TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
			// String fontName = a.getString(R.styleable.CustomTextView_fontName);
			 
				 Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
				 setTypeface(myTypeface);
			 
			 a.recycle();
		}
	}

	

}
