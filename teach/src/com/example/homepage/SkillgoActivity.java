package com.example.homepage;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import com.example.teacher.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class SkillgoActivity extends Activity {
	public int i;
	public int j;
	public String[] group={"һ�����뼼��","�������ڼ���","�������ʼ���","�ġ����鼼��","�塢��ʾ����",
			"����������ǿ������","�ߡ���̬���Ա仯����","�ˡ�������֯����","�š���������","ʮ����ѧ���ۼ���"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_skillgo);
		
		Bundle bundle = this.getIntent().getExtras();
		int a= bundle.getInt("a");
		int b= bundle.getInt("b");
	
		i=a;
		j=b;
		
		readRaw();
	}
	
	public void readRaw(){
        TextView tv=(TextView)findViewById(R.id.body);
        TextView tv1=(TextView)findViewById(R.id.title);
        ImageButton btn_back=(ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new OnClickListener(){
			@Override
     	   public void onClick(View v){
				Intent intent=new Intent(SkillgoActivity.this,HomepageActivity.class);
				startActivity(intent);
     		  SkillgoActivity.this.finish();
     		 overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
     	   }
        });
    	String ret = "";
    	try {
    		for(int m=i;m<10;m++){
    		   tv1.setText(group[m]);
 		       InputStream is = getResources().openRawResource(R.raw.group00+m*3+j);
     	       int len = is.available();
     	       byte []buffer = new byte[len];
     	       is.read(buffer);
     	       ret = EncodingUtils.getString(buffer, "utf-8");
     	       is.close();
     	       break;
    		}
    	} 
    	catch (Exception e) {
    	   e.printStackTrace();
    	}
    	
    	tv.setText(ret);
      }
}
