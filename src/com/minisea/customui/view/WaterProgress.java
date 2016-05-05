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
 * ˮ���ƶ���<br>
 * ����ϸ˵��.
 * <p>
 * Copyright: Copyright (c) 2015-2-2 ����10:01:05
 * <p>
 * 
 * @author linxf@c-platform.com
 * @version 1.0.0
 */
public class WaterProgress extends View {

	/**
	 * ��ͼ����
	 */
	private Paint paint;

	private Paint mPaint = new Paint();

	private Paint bgPaint = new Paint();

	/**
	 * ·��
	 */
	private Path path = new Path();

	private PathEffect effect;

	/**
	 * ƫ����
	 */
	private double theta;

	/**
	 * (Ƶ��)�����ٶ�
	 */
	private float delay;

	private Bitmap bitmap;

	private float angle;

	/**
	 * ��Ļ���
	 */
	private int screenWidth;

	/**
	 * ��Ļ�߶�
	 */
	private int screenHeight;

	/**
	 * �������������
	 */
	private int mWidth;

	/**
	 * �����������߶�
	 */
	private int mHeight;

	/**
	 * ��������ʵλ��
	 */
	private int mStartX;

	/**
	 * ��������������ʼֵ(Ĭ����800�ֱ�����)
	 */
	private float mStart = 6;

	/**
	 * �����������Ľ���ֵ(Ĭ����800�ֱ�����)
	 */
	private float mEnd = 304;

	/**
	 * m���
	 */
	private float mDistance;

	private int color;

	/**
	 * ����������ʾ
	 */
	// private String textRemain;

	/**
	 * �Ƿ���Ҫˢ��
	 */
	private boolean isFresh = false;

	/**
	 * �������߶��ܸ߶�
	 */
	private int progressHeight;

	/**
	 * �������߶�
	 */
	private float height;

	public WaterProgress(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param bitmap
	 *            ����ͼƬ
	 * @param screenwidth
	 *            ��Ļ���
	 * @param screenHeight
	 *            ��Ļ�߶�
	 * @param mWidth
	 *            �������
	 * @param mHeight
	 *            �����߶�
	 * @param angle
	 *            ���ؽ���
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
		// ����������ʼ��Path
		path.reset();
		if (height <= progressHeight) {
			path.moveTo(mStartX, (int) (mStart + mDistance * angle));
			height = (int) (mDistance * angle);
		} else {
			path.moveTo(mStartX, (int) (mStart + height));
			height--;
		}

		// �������Ϊ0
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
			// canvas.translate(0, 60);//����ƽ��
			delay += 0.05;
		}
		canvas.drawBitmap(bitmap, mStartX, 0, bgPaint);

		if (isFresh) {
			invalidate();
		}
	}

}
