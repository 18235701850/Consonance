package com.example.homepage;

import com.example.teacher.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class Main extends Activity {
	private SharedPreferences sp;
	private Button btn_edit;
	Bundle bundle;
	Intent intent;
	ImageView image;
	TextView nameview;
	TextView sexview;
	TextView ageview;
	TextView schoolview;
	TextView majorview;
	TextView gradesview;
	TextView subjectview;
	 Bitmap bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try{
		 sp=getSharedPreferences("test",MODE_PRIVATE);
		 image=(ImageView)findViewById(R.id.image);
		 nameview=(TextView)findViewById(R.id.nameview);
		 sexview=(TextView)findViewById(R.id.sexview);
		 ageview=(TextView)findViewById(R.id.ageview);
		 schoolview=(TextView)findViewById(R.id.schoolview);
		 majorview=(TextView)findViewById(R.id.majorview);
		 gradesview=(TextView)findViewById(R.id.gradesview);
		 subjectview=(TextView)findViewById(R.id.subjectview);
		String name=sp.getString("name","");
		String sex=sp.getString("sex", "");
		String age=sp.getString("age", "");
		String school=sp.getString("school", "");
		String major=sp.getString("major","");
		String grades=sp.getString("grades","");
		String subject=sp.getString("subject","");
		String img=sp.getString("image",null);
		if(img!=null){
			byte[] bitmapArray;
			bitmapArray = Base64.decode(img, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
			bitmap=Utils.toRoundBit(bitmap);
			image.setImageBitmap(bitmap);
		}
		nameview.setText(name);
		sexview.setText(sex);
		ageview.setText(age);
		schoolview.setText(school);
		majorview.setText(major);
		gradesview.setText(grades);
		subjectview.setText(subject);
		
		}
		catch(Exception e){
			Toast.makeText(Main.this, e.toString(), Toast.LENGTH_LONG).show();
		}
		btn_edit = (Button)findViewById(R.id.edit);
		btn_edit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Main.this,Main0.class);
				Bundle bundle=new Bundle();
				String name=nameview.getText().toString();
				String sex=sexview.getText().toString();
				String age=ageview.getText().toString();
				String school=schoolview.getText().toString();
				String major=majorview.getText().toString();
				String grades=gradesview.getText().toString();
				String subject=subjectview.getText().toString();
				bundle.putString("name", name);
				bundle.putString("sex", sex);
				bundle.putString("age", age);
				bundle.putString("school", school);
				bundle.putString("major", major);
				bundle.putString("grades", grades);
				bundle.putString("subject", subject);
				intent.putExtras(bundle);
				image.setDrawingCacheEnabled(true);
				Bitmap img = Bitmap.createBitmap(image.getDrawingCache());
				image.setDrawingCacheEnabled(false);
				intent.putExtra("image", img);
				startActivityForResult(intent,0);
			}
		});	
	}
	@Override
	protected void onActivityResult(int resquestCode,int resultCode,Intent date){
		switch(resultCode){
		case RESULT_OK:
			Bundle bundle=date.getExtras();
			Bitmap bitmap;
			bitmap=date.getParcelableExtra("image");
			ImageView image=(ImageView)findViewById(R.id.image);
			image.setImageBitmap(bitmap);//œ‘ æ‘⁄imageview¿Ô
			String name1=bundle.getString("name");
			TextView name=(TextView)findViewById(R.id.nameview);
			name.setText(name1);
			String sex1=bundle.getString("sex");
			TextView sex=(TextView)findViewById(R.id.sexview);
			sex.setText(sex1);
			String age1=bundle.getString("age");
			TextView age=(TextView)findViewById(R.id.ageview);
			age.setText(age1);
			String school1=bundle.getString("school");
			TextView school=(TextView)findViewById(R.id.schoolview);
			school.setText(school1);
			String major1=bundle.getString("major");
			TextView major=(TextView)findViewById(R.id.majorview);
			major.setText(major1);
			String grades1=bundle.getString("grades");
			TextView grades=(TextView)findViewById(R.id.gradesview);
			grades.setText(grades1);
			String subject1=bundle.getString("subject");
			TextView subject=(TextView)findViewById(R.id.subjectview);
			subject.setText(subject1);
			break;
		default:
			break;
		}
	}

	
}