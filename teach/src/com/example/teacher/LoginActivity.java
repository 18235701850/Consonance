package com.example.teacher;

import com.example.homepage.HomepageActivity;
import com.example.sql.sql;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.MenuItem;
import android.view.View.OnClickListener;
public class LoginActivity extends Activity {
	private EditText used,password;
	private ImageView ivDeleteText,ivDeleteText1;
	private String use;
	private String pwd;
	private EditText edit1,edit2;
	ProgressDialog p;
	private String mess=null;
	private SharedPreferences sp=null;
	private String usevalue;
	private String pwdvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    	edit1=(EditText) findViewById(R.id.tvuser);
		edit2=(EditText) findViewById(R.id.tvpassword);
		ivDeleteText=(ImageView)findViewById(R.id.ivDeleteText);
	       ivDeleteText1=(ImageView)findViewById(R.id.ivDeleteText1);
	       used=(EditText)findViewById(R.id.tvuser);
	       password=(EditText)findViewById(R.id.tvpassword);
		sp = this.getSharedPreferences("userinfo", LoginActivity.MODE_PRIVATE); 
		try{ 
		if(sp.getBoolean("AUTO_ISCHECK", false)){
                usevalue = sp.getString("ACCOUNTVALUE",null);  
//                System.out.println(usevalue);  
                edit1.setText(usevalue);  
      			 Toast.makeText(LoginActivity.this, usevalue.toString(), Toast.LENGTH_LONG).show();
                pwdvalue = sp.getString("PASSWORDVALUE",null);  
//                System.out.println(pwdvalue);  
                Toast.makeText(LoginActivity.this, pwdvalue.toString(), Toast.LENGTH_LONG).show();
                edit2.setText(pwdvalue);  
                new Thread(new Runnable() {
 			      	 final String  path="http://120.24.80.209:8080/web/back";
 			    	public void run() {
 				    	 sql sq=new sql();
 				    	 sq.setUsername(usevalue);
 					     sq.setPassword(pwdvalue);
 				    	mess=sq.loginByGet(usevalue,pwdvalue,path);				
 					Message msg=new Message();
 					msg.obj=mess;
 					handler.sendMessage(msg);
 				}
  			}).start();
		}
		else{
			
		}
		 }catch (Exception e) {  
             Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
         }  
        Button btn1=(Button)findViewById(R.id.button1);
        btn1.setOnClickListener(new OnClickListener(){
     	   @Override
     	   public void onClick(View v){
     		   Intent intent=new Intent(LoginActivity.this,Regist1Activity.class);
     		   startActivity(intent);
//     		   LoginActivity.this.finish();
     	   }
        });
        Button btn2=(Button)findViewById(R.id.button2);
        btn2.setOnClickListener(new OnClickListener(){
     	   @Override
     	   public void onClick(View v){
     		   if(edit1.getText().toString().trim()!=null){
     		 Intent intent=new Intent(LoginActivity.this,Findpassword2Activity.class);
     		 Bundle bundle=new Bundle();
      	    bundle.putString("use",edit1.getText().toString().trim());
      	    intent.putExtras(bundle);
     		   startActivity(intent);
     		   }
     	   }
        });
       Button btn3=(Button)findViewById(R.id.button3);
       btn3.setOnClickListener(new OnClickListener(){
    	   
    	   @Override
    	   public void onClick(View v){
				 
    		   use=edit1.getText().toString().trim();
   			System.out.println(use);
//   			try {
//   			} catch (Exception e) {
//   				
//   				e.printStackTrace();
//   			}
   			if (use==null||use.length()<=0) 
   			{		
   				edit1.requestFocus();
   				edit1.setError("对不起，用户名不能为空");
   				return;
   			}
   			else 
   			{
   				use=edit1.getText().toString().trim();
   			}
   			pwd=edit2.getText().toString().trim();
   			if (pwd==null||pwd.length()<=0) 
   			{		
   				edit2.requestFocus();
   				edit2.setError("对不起，密码不能为空");
   				return;
   			}
   			else 
   			{
   				pwd=edit2.getText().toString().trim();
   			}
   			p.show();
   			new Thread(new Runnable() {
   				 final String  path="http://120.24.80.209:8080/web/back";
   				public void run() {
   					 sql sq=new sql();
   					 sq.setUsername(use);
   					 sq.setPassword(pwd);
   					mess=sq.loginByGet(use,pwd,path);				
   					Message msg=new Message();
   					msg.obj=mess;
   					handler.sendMessage(msg);
   				}
   			}).start();
   			try{		
					Editor editor = sp.edit();  
					editor.putString("ACCOUNTVALUE", use);  
					System.out.println(use);  
				 Toast.makeText(LoginActivity.this, use.toString(), Toast.LENGTH_LONG).show();
				    editor.putString("PASSWORDVALUE", pwd);  
				   System.out.println(pwd);
				 Toast.makeText(LoginActivity.this, pwd.toString(), Toast.LENGTH_LONG).show();
				editor.commit();      //写入数据
				sp.edit().putBoolean("AUTO_ISCHECK", true).commit(); 
			 }catch (Exception e) {  
            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } 

   		}
       });
        p=new ProgressDialog(LoginActivity.this);
		p.setTitle("登录中");
		p.setMessage("登录中，马上就好");
       
       ivDeleteText.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
				used.setText("");
			}
		});
       ivDeleteText1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				password.setText("");
			}
		});
       used.addTextChangedListener(new TextWatcher(){
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length() == 0){
					ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
				}
				else {
					ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
				}
			}
		 });
       password.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(s.length() == 0){
					ivDeleteText1.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
				}
				else {
					ivDeleteText1.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
				}
			}
		 });
    }
    Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			
			String string=(String) msg.obj;
			p.dismiss();
			//Toast.makeText(LoginActivity.this, string, 0).show();
				 if("1".equals(string.toString().trim())){
					 Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
					 Intent intent=new Intent(LoginActivity.this,HomepageActivity.class);
				     startActivity(intent);
				     LoginActivity.this.finish();
				 } else{
					 Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
				 }
		}	
	};
}