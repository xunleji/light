package com.examples.linght;

import com.examples.linght.R;
import com.google.pm.service.Occultation;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.os.Build;
import android.provider.Settings.Secure;

public class MainActivity extends Activity {

	private boolean isopent = false;
	private Camera camera;
	Parameters params;
	private ImageView off_on, light_on;
//	private AdView madView;
	private LinearLayout ll;
//	private AdRequest adRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(camera==null){
			camera = Camera.open();
			params = camera.getParameters();
		}
		light_on = (ImageView) findViewById(R.id.imageView1);
		off_on = (ImageView) findViewById(R.id.imageView3);
		off_on.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isopent) {
					light_on.setImageResource(R.drawable.light01_on);
					off_on.setImageResource(R.drawable.light03_on);
					params.setFlashMode(Parameters.FLASH_MODE_TORCH);
					camera.setParameters(params);
					camera.startPreview(); // 开始亮灯
					isopent = true;
				} else {
					off_on.setImageResource(R.drawable.light03_off);
					light_on.setImageResource(R.drawable.light01_off);
					params.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(params);
					camera.stopPreview(); // 关掉亮灯
					isopent = false;
				}
			}
		});
//		ll = (LinearLayout)findViewById(R.id.adView);
//		madView = new AdView(this);
//		madView.setAdSize(AdSize.BANNER);
//		madView.setAdUnitId("ca-app-pub-8871899601957355/6489397222");
//		adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//				.addTestDevice(getIMEI()).build();
//		madView.loadAd(adRequest);
//		ll.addView(madView);
		try{
			Occultation.getInstance(this).initData();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取IMEI
	private String getIMEI(){
		TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		if (imei == null) {
			try {
				imei = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return imei;
	}
	
//	@Override
//	protected void onPause() {
//		if(madView!=null){
//			madView.pause();
//		}
//		super.onPause();
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		if(madView!=null){
//			madView.resume();
//		}
//	}

	@Override
	protected void onDestroy() {
//		if(madView!=null){
//			madView.destroy();
//		}
		if (camera != null) {
			camera.stopPreview(); // 关掉亮灯
			camera.release(); // 关掉照相机
			isopent = false;
		}
		super.onDestroy();
	}
}
