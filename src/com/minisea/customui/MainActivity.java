package com.minisea.customui;

import com.minisea.customui.R;
import com.minisea.customui.ui.RadarActivity;
import com.minisea.customui.ui.WindowsCopyResStyleProgressActivity;
import com.minisea.customui.view.RadarView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private Button radarBtn;  
    private Button combinationalStatusBtn;  
    private Button wcrsProgressbarBtn;
    private Button waterWaveProgressBtn;
    private Button imageRotateBtn;
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
  
        radarBtn = (Button) findViewById(R.id.radar_btn);  
        radarBtn.setOnClickListener(this);
        combinationalStatusBtn =  (Button) findViewById(R.id.combinaional_status_btn);
        combinationalStatusBtn.setOnClickListener(this);
        
        wcrsProgressbarBtn = (Button) findViewById(R.id.wcrs_progressbar_btn);
        wcrsProgressbarBtn.setOnClickListener(this);
        
        waterWaveProgressBtn = (Button) findViewById(R.id.water_wave_progress);
        waterWaveProgressBtn.setOnClickListener(this);
        
        imageRotateBtn = (Button) findViewById(R.id.rotating);
        imageRotateBtn.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.radar_btn: Intent intent = new Intent(MainActivity.this, RadarActivity.class);
    		MainActivity.this.startActivity(intent);
    		break;
		case R.id.combinaional_status_btn: Intent intent1 = new Intent(MainActivity.this, CombinationlStatusActivity.class);
			MainActivity.this.startActivity(intent1);
			break;
		case R.id.wcrs_progressbar_btn : Intent intent2 = new Intent(MainActivity.this, WindowsCopyResStyleProgressActivity.class);
			MainActivity.this.startActivity(intent2);
			break;
		case R.id.water_wave_progress:
			Intent intent3 = new Intent(MainActivity.this, WaterProgressActivity.class);
			MainActivity.this.startActivity(intent3);
			break;
		case R.id.rotating: 
			Intent intent4 = new Intent(MainActivity.this, RotatingActivity.class);
			MainActivity.this.startActivity(intent4);
			break;
		default : Toast.makeText(MainActivity.this, "没有选择任何效果", Toast.LENGTH_SHORT).show();
		}
		
	}  
    
    
  
}
