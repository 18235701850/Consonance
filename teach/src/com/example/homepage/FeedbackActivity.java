package com.example.homepage;

import com.example.teacher.R;
import com.example.teacher.R.id;
import com.example.teacher.R.layout;
import com.example.teacher.R.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class FeedbackActivity extends Activity {
	private TextView phone,system,edition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		creatview();
		getdata();
		listeen();
		Button btn1=(Button)findViewById(R.id.button1);
        btn1.setOnClickListener(new OnClickListener(){
     	   
     	   @Override
     	   public void onClick(View v){
     		   Intent intent=new Intent(FeedbackActivity.this,HomepageActivity.class);
     		   startActivity(intent);
     	   }
        });
	}

	private void listeen() {
		// TODO Auto-generated method stub
		ImageButton btn_back=(ImageButton)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener(){
				@Override
	     	   public void onClick(View v){
					FeedbackActivity.this.finish();
	     	   }
	        });
	}

	private void creatview() {
		// TODO Auto-generated method stub
		phone=(TextView)findViewById(R.id.phonetext);
		system=(TextView)findViewById(R.id.systemtext);
		edition=(TextView)findViewById(R.id.editiontext);
	}
	private void getdata() {
		// TODO Auto-generated method stub
		PackageManager packageManager=getPackageManager();
		TelephonyManager phoneMgr=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		PackageInfo Info;
		try {
			Info = packageManager.getPackageInfo(getPackageName(),0);
			edition.setText("版本："+Info.versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		phone.setText("设备："+Build.MANUFACTURER+" "+Build.MODEL);
		system.setText("系统："+Build.VERSION.RELEASE);
		
//		phone.setText("设备："+phoneMgr.getLine1Number());
	}

//	private String getAppVersionName(FeedbackActivity feedbackActivity) {
//		// TODO Auto-generated method stub
//		String versionName = "";  
//        try {  
//            PackageManager packageManager = cintext.getPackageManager();  
//            PackageInfo packageInfo = packageManager.getPackageInfo("cn.testgethandsetinfo", 0);  
//            versionName = packageInfo.versionName;  
//            if (TextUtils.isEmpty(versionName)) {  
//                return "";  
//            }  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
//        return versionName;  
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mine5, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
