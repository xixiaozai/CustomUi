package com.minisea.customui.view;

import com.minisea.cookbook.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class MovingProgressBar extends View {

    private Paint mPaint;
    int progress = 0;
    int secondaryProgress = 0;
    private int progColor;
    private int secondProgDrawable;

    Bitmap bmp = null;
    Bitmap newbmp = null;
    Rect progRect = new Rect(0, 0, 0, 0);

    Handler handler = new Handler();

    public MovingProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public MovingProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MovingProgressBar(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        this.mPaint = new Paint();

        if (attrs != null) {
            TypedArray typeArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.MovingProgressBar, 0, 0);

            progColor = typeArray.getColor(R.styleable.MovingProgressBar_movingProgressColor, 0xFFFFFFFF);
            secondProgDrawable = typeArray.getResourceId(R.styleable.MovingProgressBar_secondProg, 0);
        }

    }

    public void setSecondaryProgress(int prog) {
        secondaryProgress = prog;
        if (secondaryProgress == 0) {
            handler.removeCallbacks(runSecondaryProgress);
        }
    }

    public void setProgress(int prog) {

        // ShuameLog.d("", "setProgress" + getId() + "setprog:" + prog);
        progress = prog;
        if (prog == 100) {
            invalidate();
        }
    }

    public void reDraw() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = this.getWidth();
        int height = this.getHeight();
        int progWidth = width * progress / 100;
        int secondProgWidth = width * secondaryProgress / 100;

        mPaint.setColor(progColor);

        progRect.set(0, 0, progWidth, height);
        canvas.drawRect(progRect, mPaint);

        if (secondaryProgress > 0 && progress != 100) {
            if (newbmp == null) {
                bmp = BitmapFactory.decodeResource(this.getResources(), secondProgDrawable);
                Matrix matrix = new Matrix();
                float scaleHeight = ((float) height / bmp.getHeight());
                matrix.postScale(scaleHeight, scaleHeight);
                newbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

                bmp.recycle();
                bmp = null;
            }

            canvas.drawBitmap(newbmp, secondProgWidth - newbmp.getWidth(), 0, mPaint);
        }
        handler.removeCallbacks(runSecondaryProgress);
        if (progress != 100) {
            handler.postDelayed(runSecondaryProgress, 10);
        }

        // get
        // Rect rect = new Rect();
        // this.mPaint.getTextBounds(this.str, 0, this.str.length(), rect);
        // int x = (getWidth() / 2) - rect.centerX();// 让现实的字体处于中心位置;;
        // int y = (getHeight() / 2) - rect.centerY();// 让显示的字体处于中心位置;;
        // canvas.drawText(this.str, x, y, this.mPaint);

    }

    public int getProgress(){
    	return progress;
    }
    Runnable runSecondaryProgress = new Runnable() {
        @Override
        public void run() {
            handler.removeCallbacks(this);

            if (progress != 0) {
                secondaryProgress = (secondaryProgress + 1 + progress) % progress;
            } else {
                secondaryProgress = 0;
            }

            // ShuameLog.d("", "runSecondaryProgress->" + secondaryProgress + "/" + progress);

            // setSecondaryProgress(secondaryProgress);
            invalidate();
            if (progress != 100 && secondaryProgress != 0) {
                handler.postDelayed(this, 20);
            }
        }
    };;

}
