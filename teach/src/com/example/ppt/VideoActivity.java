package com.example.ppt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.app.Activity;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

public class VideoActivity extends Activity{
	private ListView listView;
	 Bitmap bitmap;
	private Button btnSearch,add;
	private ImageView ivDeleteText,image,ppt1;
	private EditText etSearch;
	private SlidingLayout slidingLayout;
	private SharedPreferences sp;
	private Button menuButton;
	private lvButtonAdapter mSimpleAdapter;
	Handler myhandler = new Handler();
	ArrayList<String> tvname = new ArrayList<String>();
	ArrayList<String> tvtime = new ArrayList<String>();
	ArrayList<String> tvtitle = new ArrayList<String>();
	private ArrayList<HashMap<String, Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
	    listView=(ListView)this.findViewById(R.id.listView);
	   
        try{
        set_vncListView_adapter();
        slidinglayout();
        ButtonClick();
        VideoPlay();
        }
        catch(Exception e){
        	Toast.makeText(VideoActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
	}

	 private void VideoPlay() {
		 new Thread(new Runnable() {  
	            @Override  
	            public void run() {  
	                String urlpath = "http://120.24.80.209:8080/vimage/vi.png";  
	                Bitmap bm = getInternetPicture(urlpath);  
	                Message msg = new Message();  
	                msg.obj = bm;  
	            }  
	        }).start();  
	}
	 public Bitmap getInternetPicture(String UrlPath) {  
	        Bitmap bm = null;  
	        String urlpath = UrlPath;  
	        // 2、获取Uri  
	        try {  
	            URL uri = new URL(urlpath);  
	  
	            // 3、获取连接对象、此时还没有建立连接  
	            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();  
	            // 4、初始化连接对象  
	            // 设置请求的方法，注意大写  
	            connection.setRequestMethod("GET");  
	            // 读取超时  
	            connection.setReadTimeout(5000);  
	            // 设置连接超时  
	            connection.setConnectTimeout(5000);  
	            // 5、建立连接  
	            connection.connect();  
	  
	            // 6、获取成功判断,获取响应码  
	            if (connection.getResponseCode() == 200) {  
	                // 7、拿到服务器返回的流，客户端请求的数据，就保存在流当中  
	                InputStream is = connection.getInputStream();  
	                // 8、从流中读取数据，构造一个图片对象GoogleAPI  
	                bm = BitmapFactory.decodeStream(is);  
	                // 9、把图片设置到UI主线程  
	                // ImageView中,获取网络资源是耗时操作需放在子线程中进行,通过创建消息发送消息给主线程刷新控件；  
	  
	                Log.i("", "网络请求成功");  
	  
	            } else {  
	                Log.v("tag", "网络请求失败");  
	                bm = null;  
	            }  
	        } catch (MalformedURLException e){  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return bm;  
	  
	    }  
	///Handler videohandler = new Handler() {  
	//       public void handleMessage(android.os.Message msg) {  
	      //  	ppt=(ImageView) findViewById(R.id.ppt1); 
	      //      ppt.setImageBitmap((Bitmap) msg.obj); 
	     //   };  
	 //  };
	private void ButtonClick() {
		 etSearch=(EditText)findViewById(R.id.etSearch);
		 etSearch.addTextChangedListener(new TextWatcher(){
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
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
				//	Toast.makeText(VideoActivity.this, e.toString(), Toast.LENGTH_LONG);
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
					intent.putExtra("explorer_title",
							getString(R.string.dialog_read_from_dir));
					intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");
					intent.setClass(VideoActivity.this,ExDialog.class );
					startActivity(intent);
				}
			 });
		 LinearLayout btnhome=(LinearLayout)findViewById(R.id.btnhome);
		 btnhome.setOnClickListener(new OnClickListener(){
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(VideoActivity.this,HomepageActivity.class);
	     		   startActivity(intent);
	     		  VideoActivity.this.finish();
	     		  overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
	     	   }
	        });
		 LinearLayout btnluntan=(LinearLayout)findViewById(R.id.btnluntan);
		 btnluntan.setOnClickListener(new OnClickListener(){
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(VideoActivity.this,ArticleActivity.class);
	     		   startActivity(intent);
	     		  VideoActivity.this.finish();
	     		  overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
	     	   }
	        });
		 LinearLayout btnppt=(LinearLayout)findViewById(R.id.btnppt);
	        btnppt.setOnClickListener(new OnClickListener(){
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(VideoActivity.this,PptActivity.class);
	     		   startActivity(intent);
	     		  VideoActivity.this.finish();
	     		  overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
	     	   }
	        });
	        LinearLayout btnexit=(LinearLayout)findViewById(R.id.exit);
	        btnexit.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Toast.makeText(VideoActivity.this, "注销", Toast.LENGTH_SHORT).show();
				}
	        });
	        LinearLayout btn15=(LinearLayout)findViewById(R.id.button15);
	        btn15.setOnClickListener(new OnClickListener(){
	     	   
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(VideoActivity.this,Main.class);
	     		   startActivity(intent);
	     	   }
	        });
	        LinearLayout btn16=(LinearLayout)findViewById(R.id.button16);
	        btn16.setOnClickListener(new OnClickListener(){
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(VideoActivity.this,CollectActivity.class);
	     		   startActivity(intent);
	     	   }
	        });
	        LinearLayout btn7=(LinearLayout)findViewById(R.id.button17);
	        btn7.setOnClickListener(new OnClickListener(){
	     	   
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(VideoActivity.this,NoticeActivity.class);
	     		   startActivity(intent);
	     	   }
	        });
	        LinearLayout btn8=(LinearLayout)findViewById(R.id.button18);
	        btn8.setOnClickListener(new OnClickListener(){
	     	   
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(VideoActivity.this,FeedbackActivity.class);
	     		   startActivity(intent);
	     	   }
	        });
	}
	 Runnable eChanged = new Runnable() {
			
			@Override
			public void run() {
				String data = etSearch.getText().toString();
				list.clear();//先要清空，不然会叠加
				try{
				getmDataSub(list, data);//获取更新数
				
				mSimpleAdapter.notifyDataSetChanged();//更新
				}
				catch(Exception e){
					Toast.makeText(VideoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

				}
				}
		};
		private void getmDataSub(ArrayList<HashMap<String, Object>> list2, String data)
		{
			int lengthbody =tvtitle.size();
			for(int i=0;i< lengthbody;i++){
				try{
					if(tvname.get(i).contains(data)||tvtitle.get(i).contains(data) ){
						HashMap<String, Object> item=new HashMap<String,Object>();
						item.put("ppt",R.drawable.vi);
						item.put("tvname",tvname.get(i));
						item.put("btn1",R.drawable.button);
						item.put("tvtime",tvtime.get(i));
						item.put("tvtitle",tvtitle.get(i));
				 		list.add(item);
				        }
					}
			catch(Exception e){
				Toast.makeText(VideoActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
			}
			}
		}
	private void set_vncListView_adapter() {
		// TODO Auto-generated method stub
		list=buildListForSimpleAdapter();
		 mSimpleAdapter=new lvButtonAdapter(
				 this,
				 list,
				 R.layout.videoitem,
	        	new String[]{"tvname","tvtime","ppt","tvtitle","btn1"},
	        	new int[]{R.id.tvname,R.id.tvtime,R.id.ppt1,R.id.tvtitle,R.id.btn1} ); 

	        listView.setAdapter(mSimpleAdapter);
	}

	private ArrayList<HashMap<String, Object>> buildListForSimpleAdapter(){	
		list=new ArrayList<HashMap<String, Object>>();
		tvname.add("111昵称");
		tvtime.add("2017/01/01");
		tvtitle.add("语文视频构思很有创意视频构思很有创意视频构思很有创意");
		
		tvname.add("222昵称");
		tvtime.add("2017/01/02");
		tvtitle.add("数学视频构思很有创意视频构思很有创意视频构思很有创意");
		
		tvname.add("333昵称");
		tvtime.add("2017/01/03");
		tvtitle.add("英语视频构思很有创意视频构思很有创意视频构思很有创意");

		tvname.add("444昵称");
		tvtime.add("2017/01/04");
		tvtitle.add("历史视频构思很有创意视频构思很有创意视频构思很有创意");
		
		for(int i=0;i<4;i++){
			HashMap<String, Object> map=new HashMap<String,Object>();
	 		map.put("tvname",tvname.get(i));
	 		map.put("tvtime",tvtime.get(i));
	 		map.put("ppt",R.drawable.vi);
	 		map.put("tvtitle",tvtitle.get(i));
	 		//map.put("btn1",R.drawable.button);
	 		list.add(map);
	         }
		return list;
	}

	private void slidinglayout() {
	    	slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
			menuButton = (Button) findViewById(R.id.menuButton);
			 image=(ImageView)findViewById(R.id.img);
			 sp=getSharedPreferences("test",MODE_PRIVATE);
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
			slidingLayout.setScrollEvent(listView);
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
			ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
	        etSearch = (EditText) findViewById(R.id.etSearch);
	        overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
	        ivDeleteText.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					etSearch.setText("");
				}
			});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.video, menu);
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
	public class lvButtonAdapter extends BaseAdapter {
        private class buttonViewHolder {
            TextView appuser;
            TextView apptime;
            TextView appword;
            ImageView img;
            ImageButton butStart;
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
                convertView = mInflater.inflate(R.layout.videoitem, null);
                holder = new buttonViewHolder();
                holder.appuser = (TextView)convertView.findViewById(valueViewID[0]);
                holder.apptime = (TextView)convertView.findViewById(valueViewID[1]);
                holder.appword = (TextView)convertView.findViewById(valueViewID[3]);
                holder.img = (ImageView)convertView.findViewById(valueViewID[2]);
                holder.butStart = (ImageButton)convertView.findViewById(valueViewID[4]);
                convertView.setTag(holder);
            }
            
            HashMap<String, Object> appInfo = mAppList.get(position);
            if (appInfo != null) {
                String names = (String) appInfo.get(keyString[0]);
                String times = (String)appInfo.get(keyString[1]);
                String words = (String)appInfo.get(keyString[3]);
                int img = (Integer)appInfo.get(keyString[2]);
                holder.appuser.setText(names);
                holder.apptime.setText(times);
                holder.appword.setText(words);
                holder.img.setImageDrawable(holder.img.getResources().getDrawable(img));
               
                holder.butStart.setOnClickListener(new View.OnClickListener(){
        			@Override
        			public void onClick(View v) 
        			{
        						Intent intent1=new Intent(VideoActivity.this,Play.class);
        					    startActivity(intent1);
        			}
        		});
          }  
            return convertView;
        }
    }
	
}
