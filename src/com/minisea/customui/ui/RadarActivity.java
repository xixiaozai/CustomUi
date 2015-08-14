package com.minisea.customui.ui;

import com.minisea.customui.R;
import com.minisea.customui.view.ImageRadarView;
import com.minisea.customui.view.RadarView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 *
 * @author haihai.zhang@stark-corp.com
 *
 **/
public class RadarActivity extends Activity{

	private ImageRadarView radarView;  
    private Button btn;  
    private Thread radarSweepThread;  
  
    private boolean startRadar = true;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_radar);  
  
        radarView = (ImageRadarView) findViewById(R.id.image_radar);  
        btn = (Button) findViewById(R.id.btn);  
        btn.setOnClickListener(new View.OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                if (startRadar) {  
                    btn.setText("end");  
                    radarView.setVisibility(View.VISIBLE);// 设置可见  
                    Animation radarAnimEnter = AnimationUtils.loadAnimation(  
                    		RadarActivity.this, R.anim.radar_anim_enter);// 初始化radarView进入动画  
                    radarView.startAnimation(radarAnimEnter);// 开始进入动画  
                    radarSweepThread = new Thread(new RadarSweep());// 雷达扫描线程  
                    radarSweepThread.start();  
                    startRadar = false;  
                } else {  
                    btn.setText("start");  
                    Animation radarAnimEnter = AnimationUtils.loadAnimation(  
                    		RadarActivity.this, R.anim.radar_anim_exit);// 初始化radarView退出动画  
                    radarView.startAnimation(radarAnimEnter);// 开始进入动画  
                    radarView.setVisibility(View.INVISIBLE);// 设置不可见  
                    radarSweepThread.interrupt();// 停止扫描更新  
                    startRadar = true;  
                }  
            }  
        });  
    }  
  
    /** 
     * @ClassName RadarSweep 
     * @Description 雷达扫描动画刷新线程类 
     */  
    private class RadarSweep implements Runnable {  
        int i = 1;  
  
        @Override  
        public void run() {  
            // TODO Auto-generated method stub  
  
            while (!Thread.currentThread().isInterrupted() && i == 1) {  
                try {  
                    radarView.postInvalidate();// 刷新radarView, 执行onDraw();  
                    Thread.sleep(10);// 暂停当前线程，更新UI线程  
                } catch (InterruptedException e) {  
                    i = 0;// 结束当前扫描线程标志符  
                    break;  
                }  
            }  
        }  
  
    }  


}
