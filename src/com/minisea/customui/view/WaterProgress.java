package com.minisea.customui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.view.View;

/**
 * 水波纹动画<br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2015-2-2 上午10:01:05
 * <p>
 * 
 * @author linxf@c-platform.com
 * @version 1.0.0
 */
public class WaterProgress extends View {

	/**
	 * 绘图工具
	 */
	private Paint paint;

	private Paint mPaint = new Paint();

	private Paint bgPaint = new Paint();

	/**
	 * 路径
	 */
	private Path path = new Path();

	private PathEffect effect;

	/**
	 * 偏移量
	 */
	private double theta;

	/**
	 * (频率)控制速度
	 */
	private float delay;

	private Bitmap bitmap;

	private float angle;

	/**
	 * 屏幕宽度
	 */
	private int screenWidth;

	/**
	 * 屏幕高度
	 */
	private int screenHeight;

	/**
	 * 进度条背景宽度
	 */
	private int mWidth;

	/**
	 * 进度条背景高度
	 */
	private int mHeight;

	/**
	 * 进度条其实位置
	 */
	private int mStartX;

	/**
	 * 进度条背景的起始值(默认在800分辨率下)
	 */
	private float mStart = 6;

	/**
	 * 进度条背景的结束值(默认在800分辨率下)
	 */
	private float mEnd = 304;

	/**
	 * m间距
	 */
	private float mDistance;

	private int color;

	/**
	 * 已用文字提示
	 */
	// private String textRemain;

	/**
	 * 是否需要刷新
	 */
	private boolean isFresh = false;

	/**
	 * 进度条高度总高度
	 */
	private int progressHeight;

	/**
	 * 进度条高度
	 */
	private float height;

	public WaterProgress(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param bitmap
	 *            背景图片
	 * @param screenwidth
	 *            屏幕宽度
	 * @param screenHeight
	 *            屏幕高度
	 * @param mWidth
	 *            背景宽度
	 * @param mHeight
	 *            背景高度
	 * @param angle
	 *            加载进度
	 */
	public WaterProgress(Context context, Bitmap bitmap, int screenwidth, int screenHeight, int mWidth, int mHeight, float angle) {
		super(context);
		this.bitmap = bitmap;
		this.screenWidth = screenwidth;
		this.screenHeight = screenHeight;
		this.mHeight = mHeight;
		this.mWidth = mWidth;
		this.angle = angle;

		this.mStartX = (screenWidth - mWidth) / 2;
		mStart = (int) (mHeight * ((float) mStart / 314));
		mEnd = (int) (mHeight * ((float) mEnd / 314));
		this.mDistance = mEnd - mStart;
		this.progressHeight = (int) (mDistance * angle);
		this.height = mDistance;
		if (angle > 0.75) {
			color = Color.parseColor("#e82410");
		} else if (angle > 0.5) {
			color = Color.parseColor("#f39800");
		} else {
			color = Color.parseColor("#2eccf4");
		}

		effect = new CornerPathEffect(10);

		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(1);

		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeWidth(1);

		paint.setPathEffect(effect);
		paint.setColor(color);

		mPaint.setPathEffect(effect);
		mPaint.setColor(color);

		bgPaint = new Paint();

		path = new Path();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 创建、并初始化Path
		path.reset();
		if (height <= progressHeight) {
			path.moveTo(mStartX, (int) (mStart + mDistance * angle));
			height = (int) (mDistance * angle);
		} else {
			path.moveTo(mStartX, (int) (mStart + height));
			height--;
		}

		// 如果已用为0
		if (angle == 0) {
			isFresh = true;
			path.lineTo(mStartX + mWidth, mStart + height);
			path.lineTo(mStartX + mWidth, mEnd);
			path.lineTo(mStartX, mEnd);
			path.lineTo(mStartX, mStart + height);

			canvas.drawPath(path, paint);
			if (height <= progressHeight) {
				isFresh = false;
			}
		} else if (angle == 1) {
		} else {
			isFresh = true;
			for (int i = 1; i <= mWidth / 10; i++) {
				theta = 0.5 * i;
				path.lineTo(i * 10 + mStartX, mStart + height + (float) (Math.sin(theta + delay) * 10) + 10);
			}
			path.lineTo(mStartX + mWidth, mEnd);
			path.lineTo(mStartX, mEnd);
			path.lineTo(mStartX, mStart + height);
			canvas.drawPath(path, mPaint);
			// canvas.translate(0, 60);//向下平移
			delay += 0.05;
		}
		canvas.drawBitmap(bitmap, mStartX, 0, bgPaint);

		if (isFresh) {
			invalidate();
		}
	}

}
