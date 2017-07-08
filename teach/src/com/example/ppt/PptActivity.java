package com.example.ppt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;
import com.example.article.ArticleActivity;
import com.example.homepage.CollectActivity;
import com.example.homepage.FeedbackActivity;
import com.example.homepage.HomepageActivity;
import com.example.homepage.Main;
import com.example.homepage.NoticeActivity;
import com.example.homepage.SlidingLayout;
import com.example.homepage.Utils;
import com.example.teacher.R;
import com.example.upordown.ExDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PptActivity extends Activity {
	private TextView headline;
	private Button btnppt,btnvideo;
	private Button menuButton,btnSearch,add;
    Bitmap bitmap;
    private SharedPreferences sp;
	private ImageView ivDeleteText,image;
	private EditText etSearch;
	private SlidingLayout slidingLayout;
	private ListView vncListView;
	int result = 8;
	Dialog dialog;
	FileUtils futils;
	String fileUrl;
	String fileName;
	String subDir;
	String sdPath;
	private ArrayList<HashMap<String, Object>> Items;
	private lvButtonAdapter listItemAdapter;
	Handler myhandler = new Handler();
	ArrayList<String> nickname = new ArrayList<String>();
	ArrayList<String> time = new ArrayList<String>();
	ArrayList<Integer> pptstart = new ArrayList<Integer>();
	ArrayList<String> word = new ArrayList<String>();
	ArrayList<String> mListText = new ArrayList<String>();
	ArrayList<String> download = new ArrayList<String>();
	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_video);
	        btnppt=(Button)findViewById(R.id.ppt);
	        btnvideo=(Button)findViewById(R.id.video);
	        headline=(TextView)findViewById(R.id.headline);
	        btnppt.setBackgroundResource(R.drawable.ppt);
	        btnvideo.setBackgroundResource(R.drawable.videono);
	        headline.setText("课件");
	        futils= new FileUtils();
			subDir = "/test/";
			fileUrl = "http://120.24.80.209:8080/ppt/70.ppt";
			fileName= getFileNameFromUrl(fileUrl);
			sdPath = futils.getSDPATH() + subDir + fileName;
	        
	        vncListView = (ListView)findViewById(R.id.listView);
	        try{
	        set_vncListView_adapter();
	        slidinglayout();
	        ButtonClick();
	        }
			catch(Exception e){
				Toast.makeText(PptActivity.this, e.toString(), Toast.LENGTH_LONG).show();
			}
	    }

	 private void set_vncListView_adapter() {
		 Items=buildListForSimpleAdapter();
		 listItemAdapter = new lvButtonAdapter(
		            this,
		            Items,//数据源 
		            R.layout.pptitem,//ListItem的XML实现
		            //动态数组与ImageItem对应的子项 
		            new String[] {"nickname","time", "pptstart","word","download"},
		            //ImageItem的XML文件里面的一个ImageView,两个TextView ID 
		            new int[] {R.id.nickname,R.id.time,R.id.pptstart,R.id.word,R.id.download}
		        );
		        vncListView.setAdapter(listItemAdapter);
	}

	private ArrayList<HashMap<String, Object>> buildListForSimpleAdapter(){	
		 Items = new ArrayList<HashMap<String,Object>>();
         
         nickname.add("1111");
         time.add( "2017-01-01");
         pptstart.add(R.drawable.ppts);
         word.add("语文初中一年级\n创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT");
         download.add("");
         
         nickname.add("2222");
         time.add( "2017-01-02");
         pptstart.add(R.drawable.ppts);
         word.add("数学初中一年级\n创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT");
         download.add("");
         
         nickname.add("3333");
         time.add( "2017-01-03");
         pptstart.add(R.drawable.ppts);
         word.add("英语初中一年级\n创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT");
         download.add("");
         
         nickname.add("4444");
         time.add( "2017-01-04");
         pptstart.add(R.drawable.ppts);
         word.add("音乐初中一年级\n创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT创意PPT");
         download.add("");
         
         for(int i=0;i<4;i++){
         HashMap<String, Object> map = new HashMap<String, Object>();
         map.put("nickname",nickname.get(i));
         map.put("time", time.get(i)+i);
         map.put("pptstart",pptstart.get(i));
         map.put("word",word.get(i));
         map.put("download",download.get(i));
         Items.add(map);
         }
		return Items;
	 }
	    private void ButtonClick() {
	    	ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
             ivDeleteText.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					etSearch.setText("");
				}
			});	    	
	    	etSearch=(EditText)findViewById(R.id.etSearch);
			etSearch.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before,
						int count) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					if(s.length() == 0){
						ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
					}
					else {
						ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
					}
					myhandler.post(eChanged);
				}
			 });
			 btnSearch=(Button)findViewById(R.id.btnSearch);
			 btnSearch.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					//try{
					 //myhandler.post(eChanged);
					//}
					//catch(Exception e){
						//Toast.makeText(PptActivity.this, e.toString(), Toast.LENGTH_LONG).show();
					//}
					
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
	            	imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
					
				}
			 });
			 add = (Button)findViewById(R.id.add);
			 add.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.putExtra("explorer_title",getString(R.string.dialog_read_from_dir));
						intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");
						intent.setClass(PptActivity.this,ExDialog.class);
						startActivity(intent);
					}
				 });
			 LinearLayout btnhome=(LinearLayout)findViewById(R.id.btnhome);
			 btnhome.setOnClickListener(new OnClickListener(){
		     	   @Override
		     	   public void onClick(View v){
		     		   Intent intent=new Intent(PptActivity.this,HomepageActivity.class);
		     		   startActivity(intent);
		     		  PptActivity.this.finish();
		     		  overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
		     	   }
		        });
		        LinearLayout btnluntan=(LinearLayout)findViewById(R.id.btnluntan);
				 btnluntan.setOnClickListener(new OnClickListener(){
			     	   @Override
			     	   public void onClick(View v){
			     		   Intent intent=new Intent(PptActivity.this,ArticleActivity.class);
			     		   startActivity(intent);
			     		  PptActivity.this.finish();
			     		  overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
			     	   }
			        });
		        LinearLayout btnvideo=(LinearLayout)findViewById(R.id.btnvideo);
		        btnvideo.setOnClickListener(new OnClickListener(){
		     	   @Override
		     	   public void onClick(View v){
		     		   Intent intent=new Intent(PptActivity.this,VideoActivity.class);
		     		   startActivity(intent);
		     		  PptActivity.this.finish();
		     		  overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
		     	   }
		        });
		        LinearLayout btnexit=(LinearLayout)findViewById(R.id.exit);
		        btnexit.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Toast.makeText(PptActivity.this, "注销", Toast.LENGTH_SHORT).show();
					}
		        });
		        LinearLayout btn15=(LinearLayout)findViewById(R.id.button15);
		        btn15.setOnClickListener(new OnClickListener(){
		     	   
		     	   @Override
		     	   public void onClick(View v){
		     		   Intent intent=new Intent(PptActivity.this,Main.class);
		     		   startActivity(intent);
		     	   }
		        });
		        LinearLayout btn16=(LinearLayout)findViewById(R.id.button16);
		        btn16.setOnClickListener(new OnClickListener(){
		     	   
		     	   @Override
		     	   public void onClick(View v){
		     		   Intent intent=new Intent(PptActivity.this,CollectActivity.class);
		     		   startActivity(intent);
		     	   }
		        });
		        LinearLayout btn7=(LinearLayout)findViewById(R.id.button17);
		        btn7.setOnClickListener(new OnClickListener(){
		     	   
		     	   @Override
		     	   public void onClick(View v){
		     		   Intent intent=new Intent(PptActivity.this,NoticeActivity.class);
		     		   startActivity(intent);
		     	   }
		        });
		        LinearLayout btn8=(LinearLayout)findViewById(R.id.button18);
		        btn8.setOnClickListener(new OnClickListener(){
		     	   
		     	   @Override
		     	   public void onClick(View v){
		     		   Intent intent=new Intent(PptActivity.this,FeedbackActivity.class);
		     		   startActivity(intent);
		     	   }
		        });
	}

		private void slidinglayout() {
	    	slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
			menuButton = (Button) findViewById(R.id.menuButton);
			slidingLayout.setScrollEvent(vncListView);
			sp=getSharedPreferences("test",MODE_PRIVATE);
			 image=(ImageView)findViewById(R.id.img);
			 String img=sp.getString("image",null);
			 if(img!=null){
					byte[] bitmapArray;
					bitmapArray = Base64.decode(img, Base64.DEFAULT);
					bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
					bitmap=Utils.toRoundBit(bitmap);
					image.setImageBitmap(bitmap);
					Drawable d=new BitmapDrawable(bitmap);
					menuButton.setBackgroundDrawable(d);
				}
			menuButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (slidingLayout.isLeftLayoutVisible()) {
						slidingLayout.scrollToRightLayout();
					} else {
						slidingLayout.scrollToLeftLayout();
						slidingLayout.leftLayout.setVisibility(View.VISIBLE);
					}
				}
			});

		}
		Runnable eChanged = new Runnable() {
			
			@Override
			public void run() {
				String data = etSearch.getText().toString();
				Items.clear();//先要清空，不然会叠加
				try{
				getmDataSub(Items, data);//获取更新数
				
				listItemAdapter.notifyDataSetChanged();//更新
				}
				catch(Exception e){
					Toast.makeText(PptActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

				}
				}
		};
		private void getmDataSub(ArrayList<HashMap<String, Object>> DataSubs, String data)
		{
			int lengthbody =word.size();
			for(int i=0;i< lengthbody;i++){
				try{
					if(nickname.get(i).contains(data)||word.get(i).contains(data) ){
						HashMap<String, Object> mapa = new HashMap<String,Object>();
						mapa.put("nickname",nickname.get(i));
			            mapa.put("time", time.get(i)+i);
			            mapa.put("pptstart",pptstart.get(i));
			            mapa.put("word",word.get(i));
			            mapa.put("download",download.get(i));
			            DataSubs.add(mapa);
				        }
					}
			catch(Exception e){
				Toast.makeText(PptActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
			}
			}
		}
	    
	    public class lvButtonAdapter extends BaseAdapter {
	        private class buttonViewHolder {
	            TextView appuser;
	            TextView apptime;
	            TextView appword;
	            ImageButton buttonimg;
	            Button buttonload;
	        }
	        
	        private ArrayList<HashMap<String, Object>> mAppList;
	        private LayoutInflater mInflater;
	        private Context mContext;
	        private String[] keyString;
	        private int[] valueViewID;
	        private buttonViewHolder holder;
	        
	        public lvButtonAdapter(Context c, ArrayList<HashMap<String, Object>> appList, int resource,
	                String[] from, int[] to) {
	            mAppList = appList;
	            mContext = c;
	            mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            keyString = new String[from.length];
	            valueViewID = new int[to.length];
	            System.arraycopy(from, 0, keyString, 0, from.length);
	            System.arraycopy(to, 0, valueViewID, 0, to.length);
	        }
	        
	        @Override
	        public int getCount() {
	            return mAppList.size();
	        }

	        @Override
	        public Object getItem(int position) {
	            return mAppList.get(position);
	        }

	        @Override
	        public long getItemId(int position) {
	            return position;
	        }

	        
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	            if (convertView != null) {
	                holder = (buttonViewHolder) convertView.getTag();
	            } else {
	                convertView = mInflater.inflate(R.layout.pptitem, null);
	                holder = new buttonViewHolder();
	                holder.appuser = (TextView)convertView.findViewById(valueViewID[0]);
	                holder.apptime = (TextView)convertView.findViewById(valueViewID[1]);
	                holder.appword = (TextView)convertView.findViewById(valueViewID[3]);
	                holder.buttonimg = (ImageButton)convertView.findViewById(valueViewID[2]);
	                holder.buttonload = (Button)convertView.findViewById(valueViewID[4]);
	                convertView.setTag(holder);
	            }
	            
	            HashMap<String, Object> appInfo = mAppList.get(position);
	            if (appInfo != null) {
	                String names = (String) appInfo.get(keyString[1]);
	                String times = (String)appInfo.get(keyString[0]);
	                String words = (String)appInfo.get(keyString[3]);
	                int btnimg = (Integer)appInfo.get(keyString[2]);
	                String downloads = (String)appInfo.get(keyString[4]);
	                holder.appuser.setText(names);
	                holder.apptime.setText(times);
	                holder.appword.setText(words);
	                holder.buttonload.setText(downloads);
	                holder.buttonimg.setImageDrawable(holder.buttonimg.getResources().getDrawable(btnimg));
	                holder.buttonimg.setOnClickListener(new View.OnClickListener(){
	                	@SuppressLint("SdCardPath")
	        			@Override
	        			public void onClick(View v) 
	        			{
	        				if(futils.isFileExist(subDir + fileName))
	        				{
	        					Intent intent = OpenFiles.getPPTFileIntent(sdPath);  
	        	                startActivity(intent);  
	        				}
	        				else
	        				{
	        					Toast.makeText(PptActivity.this, "File not exists!,please DownLoad fist!请先下载", 1).show();
	        				}
	        			}
	               
	                });
	               holder.buttonload.setOnClickListener(new View.OnClickListener(){
	        			@Override
	        			public void onClick(View v) 
	        			{
	        				if(futils.isFileExist(subDir + fileName))
	        				{
	        					Intent intent = getPdfFileIntent(sdPath);  
	        	                startActivity(intent);
	        				}
	        				else
	        				{
	        					showLoad("  正在下载...");
	        					Thread t = new Thread(new Runnable()
	        					{
	        						@Override
	        						public void run() 
	        						{
	        							HttpDownloader httpDownLoader = new HttpDownloader();
	        							result = httpDownLoader.downfile(fileUrl, subDir, fileName);
	        							tt.run();
	        						}
	        					});
	        					t.start();
	        				}
	        			}
	        		});
	          }  
	            return convertView;
	        }

	    }
	    
	    @SuppressWarnings("null")
		public String getFileNameFromUrl(String fileUrl)
		{
			String fileName="";
			int index;
			if(fileUrl!=null || fileUrl.trim()!="")
			{
				index = fileUrl.lastIndexOf("/");
				fileName = fileUrl.substring(index+1, fileUrl.length());
			}
			return fileName;
		}
		
		@SuppressLint("HandlerLeak")
		Handler handler1 = new Handler()
		{
			public void handleMessage(Message msg)
			{
				disMisLoad();
				MessageShow();
			}
		};
		public void MessageShow()
		{
			 if(result==0)  
	        {  
	            Toast.makeText(PptActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();  
	        }  
	        else if(result==1) {  
	            Toast.makeText(PptActivity.this, "已有文件！", Toast.LENGTH_SHORT).show();  
	      }  
	        else if(result==-1){  
	            Toast.makeText(PptActivity.this, "下载失败！", Toast.LENGTH_SHORT).show();  
	        }   
			 Intent intent = getPdfFileIntent(sdPath);  
	         startActivity(intent); 
			 
		}
		TimerTask tt = new TimerTask()
		{
			@Override
			public void run(){
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what =1;
				handler1.sendMessageDelayed(msg, 2000);
			}
		};
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.ppt, menu);
			return true;
		}
		 public Intent getPdfFileIntent(String path)
		 {  
			 Intent intent = new Intent("android.intent.action.VIEW");     
		        intent.addCategory("android.intent.category.DEFAULT");     
		        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
		        Uri uri = Uri.fromFile(new File(path ));     
		        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");     
		        return intent;     
			    //return i;  
		}  
		 public void showLoad(String title)
			{
				dialog = new Dialog(this,R.style.AppTheme);
				//		dialog.setCancelable(false);
				dialog.setCanceledOnTouchOutside(false);
				dialog.setContentView(R.layout.load);
				LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
				View v = inflater.inflate(R.layout.load, null);
				TextView showLoadTitle = (TextView) v.findViewById(R.id.showLoadTitle);
				showLoadTitle.setText(title);
				dialog.setContentView(v);
				dialog.show();
			}
			public void disMisLoad(){
				if(dialog!=null){
					dialog.dismiss();
				}
			} 
}
