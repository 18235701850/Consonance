package com.example.article;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.homepage.HomepageActivity;
import com.example.sql.article;
import com.example.sql.sql;
import com.example.teacher.LoginActivity;
import com.example.teacher.R;
import com.example.teacher.R.drawable;
import com.example.teacher.R.id;
import com.example.teacher.R.layout;
import com.example.teacher.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddarticleActivity extends Activity {
	private Button plus,ok;
	private EditText edittitle,tag1,tag2,tag3,editbody,editphonenumber;
	String title,body,phone,biao;
	String mess=null;
	private int a =0;
	 private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addarticle);
		oncreat();
		listener();
	}

	private void oncreat() {
		plus=(Button)findViewById(R.id.plus);
		ok=(Button)findViewById(R.id.ok);
		edittitle=(EditText)findViewById(R.id.edittitle);
		tag1=(EditText)findViewById(R.id.tag1);
		tag2=(EditText)findViewById(R.id.tag2);
		tag3=(EditText)findViewById(R.id.tag3);
		editbody=(EditText)findViewById(R.id.editbody);
		editphonenumber=(EditText)findViewById(R.id.editphonenumber);
	}

	private void listener() {
		plus.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(tag2.getVisibility()==View.GONE){
					tag2.setVisibility(View.VISIBLE);
				}
				else if(tag2.getVisibility()==View.VISIBLE & tag3.getVisibility()==View.GONE & a==0){
					tag3.setVisibility(View.VISIBLE);
					plus.setBackgroundResource(R.drawable.back2);
					a++;
				}
				else if(tag2.getVisibility()==View.VISIBLE & tag3.getVisibility()==View.VISIBLE)
					tag3.setVisibility(View.GONE);
				else {
					tag2.setVisibility(View.GONE);
					plus.setBackgroundResource(R.drawable.plus);
					a--;
				}
			}
		});
		ok.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
				//Date curDate = new Date(System.currentTimeMillis());//获取当前时间
				//String str = formatter.format(curDate);
				//Toast.makeText(AddarticleActivity.this, "发表成功,时间："+str, Toast.LENGTH_LONG).show();
				title=edittitle.getText().toString().trim();
				try {
	   			} catch (Exception e) {
	   				
	   				e.printStackTrace();
	   			}
				if (title==null||title.length()<=0) 
	   			{		
					edittitle.requestFocus();
					edittitle.setError("对不起，标题名不能为空");
	   				return;
	   			}
	   			else 
	   			{
	   				title=edittitle.getText().toString().trim();
	   			}
				biao=tag1.getText().toString().trim();
	   			if (biao==null||biao.length()<=0) 
	   			{		
	   				tag1.requestFocus();
	   				tag1.setError("对不起，标签不能为空");
	   				return;
	   			}
	   			else 
	   			{
	   				biao=tag1.getText().toString().trim();
	   			}
	   			body=editbody.getText().toString().trim();
	   			if (body==null||body.length()<=0) 
	   			{		
	   				editbody.requestFocus();
	   				editbody.setError("对不起，内容不能为空");
	   				return;
	   			}
	   			else 
	   			{
	   				body=editbody.getText().toString().trim();
	   			}
	   			phone=editphonenumber.getText().toString().trim();
	   			new Thread(new Runnable() {
	   				 final String  path="http://120.24.80.209:8080/web/articleby";
	   				public void run() {
	   					 article sq=new article();
	   					 sq.setTitle(title);
	   					 sq.setBiao(biao);
	   					 sq.setBody(body);
	   					 sq.setPhone(phone);
	   					mess=sq.articleby(title,biao,body,phone,path);				
	   					Message msg=new Message();
	   					msg.obj=mess;
	   					handler.sendMessage(msg);
	   				}
	   			}).start();

	   		}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.addarticle, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	  Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg){
				String string=(String) msg.obj;
					 if("1".equals(string.toString().trim())){
						 Toast.makeText(AddarticleActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
						 Intent intent=new Intent(AddarticleActivity.this,HomepageActivity.class);
					     startActivity(intent);
					 } else{
						 Toast.makeText(AddarticleActivity.this, "发表失败", Toast.LENGTH_SHORT).show();
					 }
			}	
		};
}
