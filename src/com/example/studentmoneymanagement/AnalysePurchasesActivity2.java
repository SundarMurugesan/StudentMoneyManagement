package com.example.studentmoneymanagement;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewAnimator;

public class AnalysePurchasesActivity2 extends MainActivity{

	private ViewAnimator viewAnimator;
	private Animation slide_in_left;
	private Animation slide_out_right;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analyse_purchases_main2);

		viewAnimator = (ViewAnimator)findViewById(R.id.viewAnimator2);

		slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
		slide_out_right = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
		viewAnimator.setInAnimation(slide_in_left);
		viewAnimator.setOutAnimation(slide_out_right);
		

		viewAnimator.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				viewAnimator.showNext();
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
}
