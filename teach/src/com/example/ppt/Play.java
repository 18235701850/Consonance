package com.example.ppt;
import com.example.teacher.R;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import android.app.Activity;
public class Play extends Activity {
private VideoView tt;
private ProgressBar progressBar;
private float width,heigh;
private boolean fullscreen=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.getWindow().setFormat(PixelFormat.TRANSLUCENT); 
		setContentView(R.layout.activity_vidoeplay);
		progressBar= (ProgressBar) findViewById(R.id.progressBar);
		tt=(VideoView) findViewById(R.id.video);
		MediaController mc = new MediaController(Play.this);
		tt.setMediaController(mc);
		Uri uri=(Uri.parse("http://120.24.80.209:8080/video/vi.MP4"));
		tt.setVideoURI(uri);
		Runnable runnable = new Runnable() {  
		    public void run() {  
		        if(!tt.isPlaying()){  
		        	progressBar.setVisibility(View.VISIBLE);  
		        } else{  
		        	progressBar.setVisibility(View.GONE);
		        	}  
		        
		}};  
		
		tt.setOnPreparedListener(new OnPreparedListener() {  
		    public void onPrepared(MediaPlayer mp) {   
		    	progressBar.setVisibility(View.GONE);//»º³åÍê³É¾ÍÒþ²Ø  
		    }  
		}); 
        tt.start();  
      
}
}
