package com.minisea.customui.view;

import com.minisea.cookbook.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.BoringLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * @author haihai.zhang@stark-corp.com
 * 
 **/
public class ImageRadarView extends View {

	private Thread radarSweepThread;
	private int degree;
	private Paint circlePaint;
	private Paint linePaint;
	private Paint sweepPaint;
	private SweepGradient sweepGradient;
	private Paint foundPoitPaint;

	private Bitmap radar_top_src;
	private Bitmap radar_border_src;
	private Bitmap radar_back_src;

	private int startColorID;
	private int endColorID;
	private int sweeepDegree;
	private int sweepRadiusMargin;
	private int circleExpandReverseSpeed;

	/**
	 * ����Բ����ɢ�ٶȣ���չ�ٶ���ָ��ֵ�෴
	 * 
	 * @param circleExpandReverseSpeed
	 */
	public void setCircleExpandReverseSpeed(int circleExpandReverseSpeed) {
		this.circleExpandReverseSpeed = circleExpandReverseSpeed;
	}

	/**
	 * ����ɨ��߾�
	 * 
	 * @param sweepRadiusMargin
	 */
	public void setSweepRadiusMargin(int sweepRadiusMargin) {
		this.sweepRadiusMargin = sweepRadiusMargin;
	}

	/**
	 * ����ɨ���ٶ�
	 * 
	 * @param sweeepDegree
	 */
	public void setSweeepDegree(int sweeepDegree) {
		this.sweeepDegree = sweeepDegree;
	}

	/**
	 * ���ñ���ͼƬ
	 * 
	 * @param back
	 */
	public void setBackground(Bitmap back) {
		this.radar_back_src = back;
	}

	/**
	 * ���ñ��̱�־
	 * 
	 * @param top
	 */
	public void setTopIcon(Bitmap top) {
		this.radar_top_src = top;
	}

	/**
	 * ���ñ��̱߿�ͼ
	 * 
	 * @param radar_border
	 */
	public void setRadarBorder(Bitmap radar_border) {
		this.radar_border_src = radar_border;
	}

	/**
	 * ����ɨ�轥����ɫ
	 * 
	 * @param startColorID
	 * @param endColorID
	 */
	public void setSweepGradientColor(int startColorID, int endColorID) {
		this.startColorID = startColorID;
		this.endColorID = endColorID;
	}

	public ImageRadarView(Context context) {
		super(context);
	}

	public ImageRadarView(Context context, AttributeSet att) {
		super(context, att);

		TypedArray typeArray = context.obtainStyledAttributes(att,
				R.styleable.image_radar_view);

		// ��ȡ����
		startColorID = typeArray.getColor(
				R.styleable.image_radar_view_radar_start_color_id,
				R.color.sweep_start_color);
		endColorID = typeArray.getColor(
				R.styleable.image_radar_view_radar_end_color_id,
				R.color.sweep_end_color);
		sweeepDegree = typeArray.getInt(
				R.styleable.image_radar_view_radar_sweeep_degree, 5);
		sweepRadiusMargin = typeArray.getInt(
				R.styleable.image_radar_view_radar_sweep_radius_margin, 15);
		circleExpandReverseSpeed = typeArray.getInt(
				R.styleable.image_radar_view_radar_circle_expand_reverse_speed,
				25);

		// ��ȡ��Դ
		Drawable backDrawable = typeArray
				.getDrawable(R.styleable.image_radar_view_radar_back_src);
		backDrawable = backDrawable == null ? getResources().getDrawable(
				R.drawable.radar_back) : backDrawable;
		radar_back_src = ((BitmapDrawable) backDrawable).getBitmap();

		Drawable borderDrawable = typeArray
				.getDrawable(R.styleable.image_radar_view_radar_border_src);
		borderDrawable = borderDrawable == null ? getResources().getDrawable(
				R.drawable.radar_border) : borderDrawable;
		radar_border_src = ((BitmapDrawable) borderDrawable).getBitmap();
		Log.i("CustomUI", "bitmap height:" + radar_border_src.getHeight() + ", width:" + radar_border_src.getWidth());
		Drawable topIcon = typeArray
				.getDrawable(R.styleable.image_radar_view_radar_top_icon_src);
		topIcon = topIcon == null ? getResources()
				.getDrawable(R.drawable.cloud) : topIcon;
		radar_top_src = ((BitmapDrawable) topIcon).getBitmap();
		
		typeArray.recycle();
		initPaint();
	}

	/**
	 * @param
	 * @return void
	 * @Description //��ʼ������Ļ���
	 */
	private void initPaint() {
		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);// Բ�λ��ʣ�����PaintΪ�����
		circlePaint.setARGB(255, 255, 255, 90);// ����͸���Ⱥ�RGB��ɫ
		circlePaint.setStrokeWidth(3);// �������
		circlePaint.setStyle(Paint.Style.STROKE);

		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);// ���Ի���
		linePaint.setStrokeCap(Paint.Cap.ROUND);
		linePaint.setARGB(255, 50, 57, 74);
		linePaint.setStrokeWidth(2);

		sweepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// �״�Shader����
		sweepPaint.setStrokeCap(Paint.Cap.ROUND);
		sweepPaint.setStrokeWidth(4);
		sweepGradient = new SweepGradient(0, 0, startColorID, endColorID);
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
		int radius = radar_back_src.getWidth() / 2;
		radius -= sweepRadiusMargin;
		canvas.save();

		// ����ɨ������
		degree += sweeepDegree;
		canvas.drawBitmap(radar_back_src, cx - radar_back_src.getWidth() / 2,
				cy - radar_back_src.getHeight() / 2, null);
		canvas.drawBitmap(radar_border_src, cx - radar_border_src.getWidth()
				/ 2, cy - radar_border_src.getHeight() / 2, null);
		// ����ת��ԭ��
		canvas.translate(cx, cy);
		// ��ת
		canvas.rotate(270 + degree);
		// ����ɨ������
		canvas.drawCircle(0, 0, radius, sweepPaint);

		// �ָ�Canvasy���꣨0,0��
		canvas.restore();

		canvas.drawCircle(
				cx,
				cy,
				radius
						* ((float) (degree / circleExpandReverseSpeed % circleExpandReverseSpeed) / (float) circleExpandReverseSpeed),
				sweepPaint);
		
		
		canvas.drawBitmap(radar_top_src, cx - radar_top_src.getWidth() / 2, cx
				- radar_top_src.getHeight() / 2, null);
		
		circlePaint.setAlpha(100);// �����ڲ�Բ�ε�͸����
		circlePaint.setStrokeWidth(2);// �����������

		canvas.save();//
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//����ͼƬ��С���ÿؼ���С
		int d = radar_border_src.getWidth() > radar_border_src.getHeight() ? radar_border_src.getWidth():  radar_border_src.getHeight();
		setMeasuredDimension(d, d); // ��д������������֤��õĻ�����������
	}

	public void startScan() {
		this.setVisibility(View.VISIBLE);// ���ÿɼ�
		Animation radarAnimEnter = AnimationUtils.loadAnimation(getContext(),
				R.anim.radar_anim_enter);// ��ʼ��radarView���붯��
		this.startAnimation(radarAnimEnter);// ��ʼ���붯��
		radarSweepThread = new Thread(new RadarSweep());// �״�ɨ���߳�
		radarSweepThread.start();
	}

	public void stopScan() {
		Animation radarAnimEnter = AnimationUtils.loadAnimation(getContext(),
				R.anim.radar_anim_exit);// ��ʼ��radarView�˳�����
		this.startAnimation(radarAnimEnter);// ��ʼ���붯��
		this.setVisibility(View.INVISIBLE);// ���ò��ɼ�
		radarSweepThread.interrupt();// ֹͣɨ�����
	}

	/**
	 * @ClassName RadarSweep
	 * @Description �״�ɨ�趯��ˢ���߳���
	 */
	private class RadarSweep implements Runnable {
		int i = 1;

		@Override
		public void run() {
			// TODO Auto-generated method stub

			while (!Thread.currentThread().isInterrupted() && i == 1) {
				try {
					ImageRadarView.this.postInvalidate();// ˢ��radarView,
															// ִ��onDraw();
					Thread.sleep(10);// ��ͣ��ǰ�̣߳�����UI�߳�
				} catch (InterruptedException e) {
					i = 0;// ������ǰɨ���̱߳�־��
					break;
				}
			}
		}

	}
}
