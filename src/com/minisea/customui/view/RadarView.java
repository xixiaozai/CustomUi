package com.minisea.customui.view;

import java.util.Random;

import com.minisea.customui.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 
 * @author haihai.zhang@stark-corp.com
 * 
 **/
public class RadarView extends View {

	private int degree;
	private Paint circlePaint;
	private Paint linePaint;
	private Paint sweepPaint;
	private SweepGradient sweepGradient;
	private Paint foundPoitPaint;
	Random random;
	public RadarView(Context context) {
		super(context);
	}

	public RadarView(Context context, AttributeSet att) {
		super(context, att);
		initPaint();
	}

	/**
	 * @param
	 * @return void
	 * @Description //初始化定义的画笔
	 */
	private void initPaint() {
		Resources r = this.getResources();
		random =  new Random();
		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 圆形画笔，设置Paint为抗锯齿
		circlePaint.setARGB(255, 255, 255, 180);// 设置透明度和RGB颜色
		circlePaint.setStrokeWidth(3);// 轮廓宽度
		circlePaint.setStyle(Paint.Style.STROKE);

		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 线性画笔
		linePaint.setStrokeCap(Paint.Cap.ROUND);
		linePaint.setARGB(255, 50, 57, 74);
		linePaint.setStrokeWidth(2);

		sweepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 雷达Shader画笔
		sweepPaint.setStrokeCap(Paint.Cap.ROUND);
		sweepPaint.setStrokeWidth(4);
		sweepGradient = new SweepGradient(0, 0,
				r.getColor(R.color.sweep_start_color),
				r.getColor(R.color.sweep_end_color));
		sweepPaint.setShader(sweepGradient);
		
		foundPoitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		foundPoitPaint.setColor(Color.WHITE);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 计算控件中心位置
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int cx = width / 2;
		int cy = height / 2;
		
		// 设置半径
		int radius = (cx > cy) ? cy : cx;
		radius -= 10;
		canvas.save();

		// 设置扫描增量
		degree += 5;
		// 设置转动原点
		canvas.translate(cx, cy);
		// 旋转
		canvas.rotate(270 + degree);
		// 绘制扫描区域
		canvas.drawCircle(0, 0, radius, sweepPaint);

		// 恢复Canvasy坐标（0,0）
		canvas.restore();
		
		// 绘制三个嵌套同心圆形，适应circlePaint画笔
		canvas.drawCircle(cx, cy, radius, circlePaint);
		circlePaint.setAlpha(100);// 降低内部圆形的透明度
		circlePaint.setStrokeWidth(2);// 设置轮廓宽度
		canvas.drawCircle(cx, cy, radius * 2 / 3, circlePaint);
		canvas.drawCircle(cx, cy, radius / 3, circlePaint);

		canvas.drawLine(cx - radius , cy, cx + radius , cy, linePaint);
		canvas.drawLine(cx, cy - radius, cx, cy + radius, linePaint);
		
		
		canvas.save();//
		canvas.translate(10, radius + 10);
		
		
		int x = random.nextInt(radius);
		int y = random.nextInt(radius);
		float rD= random.nextFloat();
		if(rD <= 0.25){
			x = -x;
			y = -y;
		}
		
		if(rD <= 0.5 && rD> 0.25){
			y = -y;
		}
		
		if(rD <= 0.75 && rD> 0.5){
			x = -y;
			
		}

		canvas.drawCircle(cx + x, y, 5, foundPoitPaint);
		
		float interval = radius / 12f;// 刻度间距
		float minLength = interval / 2;
		float maxLength = interval;

		for (int i = 0; i < 24; i++) {
			float startX, stopX;
			startX = stopX = interval * i;
			if (i % 2 != 0) {
				canvas.drawLine(startX, -minLength, stopX, minLength, linePaint);
			} else {
				canvas.drawLine(startX, -maxLength, stopX, maxLength, linePaint);
			}
		}
		
		canvas.restore();
		canvas.translate(cx, 10);
		for (int i = 0; i < 24; i++) {
			float startY, stopY;
			startY = stopY = interval * i;
			if (i % 4 != 0) {
				if (i % 2 != 0) {
					canvas.drawLine(-minLength, startY, minLength, stopY,
							linePaint);
				} else {
					canvas.drawLine(-maxLength, startY, maxLength, stopY,
							linePaint);
				}
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int d = (width >= height) ? height : width; // 获取最短的边作为直径
		setMeasuredDimension(d, d); // 重写测量方法，保证获得的画布是正方形
	}

}
