package com.minisea.customui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * @author haihai.zhang@stark-corp.com
 *
 **/
public class TestBottleView extends View{

	private Paint paint;
	private int c;
	private float i;
	private float k;
	private float l = -65.0F;
	private float m = 180.0F + 2.0F * Math.abs(this.l);
	private float n;
	private float o;
	
	 private RectF x;
	private RectF y;
	public TestBottleView(Context context) {
		super(context);
		paint = new Paint();
	}

	public TestBottleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint();
	}

	public TestBottleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.save();
	    paint.reset();
	    paint.setShader(null);
	    paint.setColor(-1);
	    paint.setAntiAlias(true);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setStrokeWidth(2.0F);
	    paint.setAlpha(255);
	    canvas.drawArc(this.y, this.l, this.m, false, paint);
	    canvas.restore();
	    canvas.save();
	    paint.reset();
	    paint.setShader(null);
	    paint.setColor(-1);
	    paint.setAntiAlias(true);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setStrokeWidth(2.0F);
	    paint.setAlpha(255);
	    float f1 = this.i - this.n;
	    canvas.drawLine(this.c + this.o, f1, this.c + this.o, f1 - this.k, paint);
	    canvas.drawLine(this.c - this.o, f1, this.c - this.o, f1 - this.k, paint);
	    canvas.restore();
	    canvas.save();
	    paint.reset();
	    paint.setShader(null);
	    paint.setColor(-1);
	    paint.setAntiAlias(true);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setStrokeWidth(2.0F);
	    paint.setAlpha(255);
	    canvas.drawArc(this.x, 180.0F, 180.0F, false, paint);
	    canvas.restore();
	}

	
}
