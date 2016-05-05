package com.minisea.customui.ui;

import com.minisea.cookbook.R;
import com.minisea.customui.view.MovingProgressBar;
import com.minisea.customui.view.PointsProgressBar;
import com.minisea.customui.view.WindowsCopyResStyleProgress;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 *
 * @author haihai.zhang@stark-corp.com
 *
 **/
public class WindowsCopyResStyleProgressActivity extends Activity{

	private WindowsCopyResStyleProgress mProgressBar;  
    private static final int MSG_PROGRESS_UPDATE = 0x110;  
    private MovingProgressBar movingProgressBar;
    private PointsProgressBar pointsProgressBar;
    private Handler mHandler = new Handler() {  
        public void handleMessage(android.os.Message msg) {  
            int progress = mProgressBar.getProgress();  
            mProgressBar.setProgress(++progress); 
            movingProgressBar.setProgress(progress);
            if (progress >= 100) {  
                mHandler.removeMessages(MSG_PROGRESS_UPDATE);  
                  
            }  
            mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);  
        };  
    };  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_wcrs_progressbar);  
        mProgressBar = (WindowsCopyResStyleProgress) findViewById(R.id.progressbar);  
        movingProgressBar = (MovingProgressBar) findViewById(R.id.moving_progress_bar);
        pointsProgressBar = (PointsProgressBar) findViewById(R.id.points_p_b);
        pointsProgressBar.start();
        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);  
  
    }  
	
}
