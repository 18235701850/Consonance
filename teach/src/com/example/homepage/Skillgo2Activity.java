package com.example.homepage;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import com.example.teacher.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Skillgo2Activity extends Activity {
	public int i;
	public String[] group={"一、导入技能","二、讲授技能","三、提问技能","四、板书技能","五、演示技能",
			"六、反馈和强化技能","七、教态语言变化技能","八、课堂组织技能","九、结束技能","十、教学评价技能"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_skillgo2);
		try{
		Bundle bundle = this.getIntent().getExtras();
		int c= bundle.getInt("c");
		i=c;
		
		readRaw();
		}
		catch(Exception e){
			Toast.makeText(Skillgo2Activity.this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	public void readRaw(){
		 ImageButton btn_back=(ImageButton)findViewById(R.id.btn_back);
	        btn_back.setOnClickListener(new OnClickListener(){
				@Override
	     	   public void onClick(View v){
					Intent intent=new Intent(Skillgo2Activity.this,HomepageActivity.class);
					startActivity(intent);
	     		  Skillgo2Activity.this.finish();
	     		 overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
	     	   }
	        });
        TextView tv=(TextView)findViewById(R.id.body);
        TextView tv2=(TextView)findViewById(R.id.body2);
        TextView tv3=(TextView)findViewById(R.id.body3);
        TextView tv1=(TextView)findViewById(R.id.title);
        
    	String ret = "";
    	String ret2 = "";
    	String ret3= "";
    	try {
    		for(int m=i;m<10;m++){
    		   tv1.setText(group[m]);
 		       InputStream is = getResources().openRawResource(R.raw.group00+m*3);
 		       InputStream is2 = getResources().openRawResource(R.raw.group00+m*3+1);
 		       InputStream is3 = getResources().openRawResource(R.raw.group00+m*3+2);
     	       int len = is.available();
     	       int len2= is2.available();
     	       int len3= is3.available();
     	       byte []buffer = new byte[len];
     	       byte []buffer2 = new byte[len2];
     	       byte []buffer3= new byte[len3];
     	       is.read(buffer);
     	       is2.read(buffer2);
     	       is3.read(buffer3);
     	       ret = EncodingUtils.getString(buffer, "utf-8");
     	       ret2 = EncodingUtils.getString(buffer2, "utf-8");
     	       ret3= EncodingUtils.getString(buffer3, "utf-8");
     	       is.close();
     	       is2.close();
     	       is3.close();
     	       break;
    		}
    	} 
    	catch (Exception e) {
    	   e.printStackTrace();
    	}
    	tv.setText(ret);
    	tv2.setText(ret2);
    	tv3.setText(ret3);
      }


}
