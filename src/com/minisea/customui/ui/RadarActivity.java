package com.minisea.customui.ui;

import com.minisea.cookbook.R;
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
                    radarView.setVisibility(View.VISIBLE);// ���ÿɼ�  
                    Animation radarAnimEnter = AnimationUtils.loadAnimation(  
                    		RadarActivity.this, R.anim.radar_anim_enter);// ��ʼ��radarView���붯��  
                    radarView.startAnimation(radarAnimEnter);// ��ʼ���붯��  
                    radarSweepThread = new Thread(new RadarSweep());// �״�ɨ���߳�  
                    radarSweepThread.start();  
                    startRadar = false;  
                } else {  
                    btn.setText("start");  
                    Animation radarAnimEnter = AnimationUtils.loadAnimation(  
                    		RadarActivity.this, R.anim.radar_anim_exit);// ��ʼ��radarView�˳�����  
                    radarView.startAnimation(radarAnimEnter);// ��ʼ���붯��  
                    radarView.setVisibility(View.INVISIBLE);// ���ò��ɼ�  
                    radarSweepThread.interrupt();// ֹͣɨ�����  
                    startRadar = true;  
                }  
            }  
        });  
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
                    radarView.postInvalidate();// ˢ��radarView, ִ��onDraw();  
                    Thread.sleep(10);// ��ͣ��ǰ�̣߳�����UI�߳�  
                } catch (InterruptedException e) {  
                    i = 0;// ������ǰɨ���̱߳�־��  
                    break;  
                }  
            }  
        }  
  
    }  


}
