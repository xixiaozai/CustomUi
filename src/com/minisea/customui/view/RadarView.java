package com.minisea.customui.view;

import java.util.Random;

import com.minisea.cookbook.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
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
	 * @Description //��ʼ������Ļ���
	 */
	private void initPaint() {
		Resources r = this.getResources();
		random =  new Random();
		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);// Բ�λ��ʣ�����PaintΪ�����
		circlePaint.setARGB(255, 255, 255, 180);// ����͸���Ⱥ�RGB��ɫ
		circlePaint.setStrokeWidth(3);// �������
		circlePaint.setStyle(Paint.Style.STROKE);

		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);// ���Ի���
		linePaint.setStrokeCap(Paint.Cap.ROUND);
		linePaint.setARGB(255, 50, 57, 74);
		linePaint.setStrokeWidth(2);

		sweepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// �״�Shader����
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
		// ����ؼ�����λ��
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int cx = width / 2;
		int cy = height / 2;
		
		// ���ð뾶
		int radius = (cx > cy) ? cy : cx;
		radius -= 10;
		canvas.save();

		// ����ɨ������
		degree += 5;
		// ����ת��ԭ��
		canvas.translate(cx, cy);
		// ��ת
		canvas.rotate(270 + degree);
		// ����ɨ������
		canvas.drawCircle(0, 0, radius, sweepPaint);

		// �ָ�Canvasy���꣨0,0��
		canvas.restore();
		
		// ��������Ƕ��ͬ��Բ�Σ���ӦcirclePaint����
		canvas.drawCircle(cx, cy, radius, circlePaint);
		circlePaint.setAlpha(100);// �����ڲ�Բ�ε�͸����
		circlePaint.setStrokeWidth(2);// �����������
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
		
		float interval = radius / 12f;// �̶ȼ��
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
		int d = (width >= height) ? height : width; // ��ȡ��̵ı���Ϊֱ��
		setMeasuredDimension(d, d); // ��д������������֤��õĻ�����������
	}

}
