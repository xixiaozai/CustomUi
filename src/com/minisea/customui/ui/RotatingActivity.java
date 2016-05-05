package com.minisea.customui.ui;

import com.minisea.cookbook.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class RotatingActivity extends Activity {

	private ImageView roteView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_rotating);
		super.onCreate(savedInstanceState);
		roteView = (ImageView) findViewById(R.id.rote_icon);
		startRotate();
		
	}

	private void startRotate() {
		Animation rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotating_forever);
		roteView.setAnimation(rotateAnim);
		LinearInterpolator lir = new LinearInterpolator();
		rotateAnim.setInterpolator(lir);
		rotateAnim.start();
	}
}
