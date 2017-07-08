package com.example.homepage;

import com.example.ppt.PptActivity;
import com.example.teacher.R;
import com.example.teacher.R.id;
import com.example.teacher.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;


public class float1 extends Activity {
	public static Button message;
	private Button btnhot,btnmsg,btngrades,btnskill;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.float1);
//		btnmsg = (Button) findViewById(R.id.message);
//		btnmsg.setOnClickListener(new OnClickListener(){
//		 	   
//		 	   @Override
//		 	   public void onClick(View v){
//		 		   Intent intent=new Intent(float1.this,MessageActivity.class);
//		 		   startActivity(intent);
//		 	   }
//		    });
//		btnskill = (Button) findViewById(R.id.skill);
//		btnskill.setOnClickListener(new OnClickListener(){
//		 	   
//		 	   @Override
//		 	   public void onClick(View v){
//		 		   Intent intent=new Intent(float1.this,MessageActivity.class);
//		 		   startActivity(intent);
//		 	   }
//		    });
		
	}
    

}
