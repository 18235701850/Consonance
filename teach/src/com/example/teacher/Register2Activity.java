package com.example.teacher;

import com.example.sql.sql;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register2Activity extends Activity {
	private String mess=null;
	 public String use;
    int i=0;
    EditText texta,textb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register2);
		Bundle bundle = this.getIntent().getExtras();
		use= bundle.getString("use");
		texta = (EditText)findViewById(R.id.tv1);
  		textb = (EditText)findViewById(R.id.tv2);
        Button btn2= (Button)findViewById(R.id.button3);
        btn2.setOnClickListener(new OnClickListener(){
      	   
      	   @Override
      	   public void onClick(View v){
      		EditText text = (EditText)findViewById(R.id.tv1);
      		if(i==0){
      		    text.setInputType(0x90);
      		    i=1;
      		}
      		else{
      			text.setInputType(0x81);
      			i=0;
      		}
      	   }
       });
        
        
        Button btn3= (Button)findViewById(R.id.button4);
        btn3.setOnClickListener(new OnClickListener(){
      	   
      	   @Override
      	   public void onClick(View v){
      		 EditText text = (EditText)findViewById(R.id.tv2);
      		if(i==0){
      		    text.setInputType(0x90);
      		    i=1;
      		}
      		else{
      			text.setInputType(0x81);
      			i=0;
      		}
      	   }
       });   
        
        
        final Button btn4= (Button)findViewById(R.id.btnres);
        btn4.setOnClickListener(new OnClickListener(){
      	   
      	   @Override
      	   public void onClick(View v){
      		
      		
      		String expression ="^[A-Za-z0-9]{6,10}$";
      		
      		if(texta.getText().toString().matches(expression)&&textb.getText().toString().matches(expression)&&texta.getText().toString().matches(textb.getText().toString())){
      			new Thread(new Runnable(){
   				 final String  path="http://120.24.80.209:8080/web/loginservlet";
   				public void run() {
   					
   					String pwd=texta.getText().toString().trim();
   					 sql sq=new sql();
   					 sq.setUsername(use);
   					 sq.setPassword(pwd);
   					mess=sq.loginByPost(use,pwd,path);				
   					Message msg=new Message();
   					msg.obj=mess;
   					handler.sendMessage(msg);
   				}
   			}).start();
         	 }
         	 else{
         		Toast.makeText(Register2Activity.this,"输入的密码有误，请重新输入", Toast.LENGTH_SHORT).show();
         		texta.setText("");
         		textb.setText("");
      		}
      	   }
       });   
        
	}
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			String string=(String) msg.obj;
				 if("2".equals(string.toString().trim())){
					 Toast.makeText(Register2Activity.this, "用户注册成功", Toast.LENGTH_SHORT).show();
					 Intent intent=new Intent(Register2Activity.this,LoginActivity.class);
				     startActivity(intent);
				 } else if("1".equals(string.toString().trim())){
					 Toast.makeText(Register2Activity.this, "该用户已存在", Toast.LENGTH_SHORT).show();
				 }
				 else{
					 Toast.makeText(Register2Activity.this, "failed", Toast.LENGTH_SHORT).show();
				 }
		}	
	};
	
}
