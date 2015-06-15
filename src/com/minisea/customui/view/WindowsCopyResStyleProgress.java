package com.minisea.customui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

/**
 *
 * @author haihai.zhang@stark-corp.com
 *
 **/
public class WindowsCopyResStyleProgress extends ProgressBar{
	private Paint mPaint = new Paint();
	private int sw, sh;
	private int w, h;
	int l = -50, t = 5 , r = 0, b = 12;
	int lightW = 100;

	public WindowsCopyResStyleProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		w  = 0;
		h = 20;
		t = h;
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//获取控件宽度
		sw = getWidth();
		//绘制亮光
		LinearGradient lg=new LinearGradient(0, 0, 50,50,Color.argb(0, 255, 255, 255),Color.argb(80, 255, 255, 154),TileMode.MIRROR); 
		mPaint.setShader(lg);
		mPaint.setAntiAlias(true);  
		canvas.save();
		Log.i("CustomUI", "构造完成 宽度：" + sw + " 高度：" + sh);
		
		//根据当前进度设置亮光移动范围
		w = (int) ((float)getProgress() / 100f * sw);
		if(r <= w){
			l = l + 20;
			r = r + 20;
			if(getProgress() == 100) {
				l = -lightW;
				r = 0;
			}
		}else{
			l = -lightW;
			r = 0;
		}
		
		canvas.drawRect(new Rect(l, t, r, b), mPaint);
		canvas.restore();  
	}

}
