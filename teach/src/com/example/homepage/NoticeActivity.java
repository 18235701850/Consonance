package com.example.homepage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.teacher.R;
import com.example.teacher.R.drawable;
import com.example.teacher.R.id;
import com.example.teacher.R.layout;
import com.example.teacher.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NoticeActivity extends Activity {

	ListView listView; 
	
	private  SimpleAdapter mSimpleAdapter;
	
	Handler myhandler = new Handler();
	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	ArrayList<Integer> mListImage = new ArrayList<Integer>();
	ArrayList<String> mListbody = new ArrayList<String>();
	ArrayList<String> mListname = new ArrayList<String>();
	ArrayList<Integer> mListdate = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		try{
		viewcreate();
		setListeners();		
		refreshListItems(); 
		}
		catch(Exception e){
			Toast.makeText(NoticeActivity.this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void viewcreate() {
		// TODO Auto-generated method stub
		listView=(ListView)findViewById(R.id.listView);
	}
	private void setListeners() {
		// TODO Auto-generated method stub
		
	}

	private void refreshListItems() {
		// TODO Auto-generated method stub
		list = buildListForSimpleAdapter(); 
        mSimpleAdapter=new SimpleAdapter(
        		this,
        		list,
        		R.layout.collectionitem,
        		new String[]{"image","body","name","date"},
        		new int[]{R.id.image,R.id.bodyView,R.id.name,R.id.date} ); 
        listView.setAdapter(mSimpleAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try{
				Map<String,Object> map =(Map<String,Object>)mSimpleAdapter.getItem(position);
				Toast.makeText(NoticeActivity.this,map.get("body").toString(), Toast.LENGTH_SHORT).show();
				}
				catch(Exception e){
					Toast.makeText(NoticeActivity.this, e.toString(), Toast.LENGTH_LONG).show();
				}
			}
        });
    	ImageButton btn_back=(ImageButton)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener(){
				@Override
	     	   public void onClick(View v){
					NoticeActivity.this.finish();
	     	   }
	        });
	}

	private List<Map<String,Object>> buildListForSimpleAdapter(){	
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
		mListImage.add(R.drawable.personal_image);
		mListbody.add("中学教师的发展前景如何11111");
		mListname.add("娃哈哈dar");
		mListdate.add(25);
		map.put("image",mListImage.get(0));
		map.put("body",mListbody.get(0));
		map.put("name",mListname.get(0));
		map.put("date", mListdate.get(0)+"天前");
		list.add(map);
		
		map=new HashMap<String,Object>();
		mListImage.add(R.drawable.personal_image);
		mListbody.add("学校十二五教师发展规划222222");
		mListname.add("FJORGAS");
		mListdate.add(42);
		map.put("image",mListImage.get(1));
		map.put("body",mListbody.get(1));
		map.put("name",mListname.get(1));
		map.put("date", mListdate.get(1)+"天前");
		list.add(map);
		
		map=new HashMap<String,Object>();
		mListImage.add(R.drawable.bigtree);
		mListbody.add("中学教师的发展前景如何中前景如何中学教师的发展前景如何33333");
		mListname.add("发展   ");
		mListdate.add(42);
		map.put("image",mListImage.get(2));
		map.put("body",mListbody.get(2));
		map.put("name",mListname.get(2));
		map.put("date", mListdate.get(2)+"天前");
		list.add(map);
		

	
		return list;
	}
//		Button btn1=(Button)findViewById(R.id.button1);
//        btn1.setOnClickListener(new OnClickListener(){
//     	   
//     	   @Override
//     	   public void onClick(View v){
//     		   Intent intent=new Intent(CollectActivity.this,HomepageActivity.class);
//     		   startActivity(intent);
//     	   }
//        });


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mine3, menu);
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
