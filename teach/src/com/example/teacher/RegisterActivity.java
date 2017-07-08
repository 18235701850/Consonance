package com.example.teacher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button btn = (Button)findViewById(R.id.next);
        btn.setOnClickListener(new OnClickListener(){
      	   
      	   @Override
      	   public void onClick(View v){
      		EditText text = (EditText)findViewById(R.id.phonenumber);
         	String content=text.getText().toString().trim();
         	 
         	String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))"; 
            CharSequence inputStr = content; 
            Pattern pattern = Pattern.compile(expression);  
            Matcher matcher = pattern.matcher(inputStr); 
         	 
         	 if(matcher.matches()){
         		Intent intent=new Intent(RegisterActivity.this,Register2Activity.class);
        		Bundle bundle=new Bundle();
        	    bundle.putString("use",content);
        	    intent.putExtras(bundle);
        		startActivity(intent); 
        		overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);  
         	 }
         	 else{
         		Toast.makeText(RegisterActivity.this,"请输入正确的手机号", Toast.LENGTH_SHORT).show();
         		text.setText("");
         	 }
      	   }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
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
