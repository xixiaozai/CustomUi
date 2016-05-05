package com.minisea.customui.ui;

import com.minisea.cookbook.R;
import com.minisea.customui.view.CombinationlStatusView;
import com.minisea.customui.view.MovingProgressBar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

/**
 *
 * @author haihai.zhang@stark-corp.com
 *
 **/
public class CombinationlStatusActivity extends Activity{
	private MovingProgressBar mProgressBar;
	private CombinationlStatusView combinationlStatusView;
	private static final int MSG_PROGRESS_UPDATE = 0x110; 
	private int time;
	private Handler mHandler = new Handler() {  
        public void handleMessage(android.os.Message msg) {  
        	time += 100;
        	if(time < 1000) {
        		Toast.makeText(CombinationlStatusActivity.this, "选中当前", Toast.LENGTH_SHORT).show();
        		
        		combinationlStatusView.show(getString(R.string.callback_status_view_type_checkbox));
        		
        	}else{
        		int progress = mProgressBar.getProgress(); 
        		if (progress >= 100) {  
        			Toast.makeText(CombinationlStatusActivity.this, "完成显示结果", Toast.LENGTH_SHORT).show();
               	 combinationlStatusView.show(getString(R.string.callback_status_view_type_checkedtext)); 
                }  else{
                	Toast.makeText(CombinationlStatusActivity.this, "显示选中行操作进度", Toast.LENGTH_SHORT).show();
                	combinationlStatusView.show(getString(R.string.callback_status_view_type_progressbar)); 
                     mProgressBar.setProgress(++progress); 
                }
        		
        	}
           
            mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);  
        };  
    };  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_combinational_status);
		mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
		combinationlStatusView = (CombinationlStatusView) findViewById(R.id.check);
		mProgressBar = (MovingProgressBar) combinationlStatusView.findViewById(R.id.progress_bar);
	}

	
	
}
