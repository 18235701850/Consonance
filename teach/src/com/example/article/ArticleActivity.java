package com.example.article;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.homepage.CollectActivity;
import com.example.homepage.FeedbackActivity;
import com.example.homepage.HomepageActivity;
import com.example.homepage.Main;
import com.example.homepage.NoticeActivity;
import com.example.homepage.SlidingLayout;
import com.example.homepage.Utils;
import com.example.ppt.PptActivity;
import com.example.ppt.VideoActivity;
import com.example.teacher.R;
import com.example.teacher.R.drawable;
import com.example.teacher.R.id;
import com.example.teacher.R.layout;
import com.example.teacher.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;

public class ArticleActivity extends Activity implements OnScrollListener {
    private Button bt,menuButton,btnSearch,addarticle;
    private ProgressBar pg;
    private View moreView;
    private Handler handler;
    private int MaxDateNum;
    private int lastVisibleIndex;
    Bitmap bitmap;
    private SharedPreferences sp;
	private ImageView ivDeleteText,image;
	private EditText etSearch;
	ListView listView; 
	private  SimpleAdapter mSimpleAdapter;
	
	private SlidingLayout slidingLayout;
	
	Handler myhandler = new Handler();
	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	ArrayList<Integer> mListImage = new ArrayList<Integer>();
	ArrayList<String> mListbody = new ArrayList<String>();
	ArrayList<String> mListtag = new ArrayList<String>();
	ArrayList<Integer> mListlook = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		MaxDateNum = 22; 
		listView=(ListView)findViewById(R.id.listView);
		moreView = getLayoutInflater().inflate(R.layout.moredata, null);
		bt = (Button) moreView.findViewById(R.id.bt_load);
        pg = (ProgressBar) moreView.findViewById(R.id.pg);
        handler = new Handler();
		setListeners();		

	    refreshListItems(); 

		 listView.addFooterView(moreView);
		 listView.setOnScrollListener(this);
		 LinearLayout btn_back=(LinearLayout)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener(){
			@Override
     	   public void onClick(View v){
				Intent intent=new Intent(ArticleActivity.this,HomepageActivity.class);
				startActivity(intent);
     		  ArticleActivity.this.finish();
     		 overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
     	   }
        });
		 ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
	        etSearch = (EditText) findViewById(R.id.etSearch);
//	        overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
	        ivDeleteText.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					etSearch.setText("");
				}
			});
	        slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
			menuButton = (Button) findViewById(R.id.menuButton);
			 image=(ImageView)findViewById(R.id.imageView1);
		slidingLayout.setScrollEvent(listView);
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
	private void refreshListItems() {
		// TODO Auto-generated method stub
		list = buildListForSimpleAdapter(); 
        mSimpleAdapter=new SimpleAdapter(
        		this,
        		list,
        		R.layout.articleitem,
        		new String[]{"image","body","tag","look"},
        		new int[]{R.id.image,R.id.bodyView,R.id.tagView,R.id.lookView} ); 
        listView.setAdapter(mSimpleAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try{
				Map<String,Object> map =(Map<String,Object>)mSimpleAdapter.getItem(position);
				Toast.makeText(ArticleActivity.this,map.get("body").toString(), Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(ArticleActivity.this,ArticleitemActivity.class);
				startActivity(intent);
				}
				catch(Exception e){
					Toast.makeText(ArticleActivity.this, e.toString(), Toast.LENGTH_LONG).show();
				}
			}
        });
        bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);// 将进度条可见
                bt.setVisibility(View.GONE);// 按钮不可见

                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadMoreDate();// 加载更多数据
                        bt.setVisibility(View.VISIBLE);
                        pg.setVisibility(View.GONE);
                        mSimpleAdapter.notifyDataSetChanged();// 通知listView刷新数据
                    }

                }, 2000);
            }
        });
        addarticle=(Button)findViewById(R.id.add);
        addarticle.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View v) {
				Intent addarticle=new Intent(ArticleActivity.this,AddarticleActivity.class);
				startActivity(addarticle);
			}
        	
        });
	}

	 private void loadMoreDate() {
	        int count = mSimpleAdapter.getCount();
	        if (count + 5 < MaxDateNum) {
	            // 每次加载5条
	            for (int i = count; i < count + 5; i++) {
	            	Map<String,Object> map = new HashMap<String,Object>();
	                map.put("image",mListImage.get(1));
	                map.put("body", "新增第" + i + "行标题");
	                map.put("tag", "新增第" + i + "行内容");
	                map.put("look", mListlook.get(0));
	                list.add(map);
	            }
	        } else {
	            // 数据已经不足5条
	            for (int i = count; i < MaxDateNum; i++) {
	            	Map<String,Object> map = new HashMap<String,Object>();
	                map.put("image",mListImage.get(1));
	                map.put("body", "新增第" + i + "行标题");
	                map.put("tag", "新增第" + i + "行内容");
	                map.put("look", mListlook.get(0));
	                list.add(map);
	            }
	        }

	    }
	 @Override
	    public void onScroll(AbsListView view, int firstVisibleItem,
	            int visibleItemCount, int totalItemCount) {
	        // 计算最后可见条目的索引
	        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;

	        // 所有的条目已经和最大条数相等，则移除底部的View
	        if (totalItemCount == MaxDateNum + 1) {
	        	listView.removeFooterView(moreView);
	            Toast.makeText(this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
	        }

	    }

	    @Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {
	        // 滑到底部后自动加载，判断listview已经停止滚动并且最后可视的条目等于adapter的条目
	        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
	                && lastVisibleIndex == mSimpleAdapter.getCount()) {
	        }

	    }
	    
	private void setListeners() {
		// TODO Auto-generated method stub
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
				 myhandler.post(eChanged);
			}
		 });
		 btnSearch=(Button)findViewById(R.id.btnSearch);
		 btnSearch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
				 myhandler.post(eChanged);
				}
				catch(Exception e){
					Toast.makeText(ArticleActivity.this, e.toString(), Toast.LENGTH_LONG);
				}
			}
		 });
		 LinearLayout btnppt=(LinearLayout)findViewById(R.id.btnppt);
	        btnppt.setOnClickListener(new OnClickListener(){
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(ArticleActivity.this,PptActivity.class);
	     		   startActivity(intent);
	     		  ArticleActivity.this.finish();
	     		  overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
	     	   }
	        });
	        LinearLayout btnvideo=(LinearLayout)findViewById(R.id.btnvideo);
	        btnvideo.setOnClickListener(new OnClickListener(){
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(ArticleActivity.this,VideoActivity.class);
	     		   startActivity(intent);
	     		  ArticleActivity.this.finish();
	     		  overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
	     	   }
	        });
	        LinearLayout btnexit=(LinearLayout)findViewById(R.id.exit);
	        btnexit.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(ArticleActivity.this, "注销", Toast.LENGTH_SHORT).show();
				}
	        });
	        LinearLayout btn15=(LinearLayout)findViewById(R.id.button15);
	        btn15.setOnClickListener(new OnClickListener(){
	     	   
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(ArticleActivity.this,Main.class);
	     		   startActivity(intent);
	     	   }
	        });
	        LinearLayout btn16=(LinearLayout)findViewById(R.id.button16);
	        btn16.setOnClickListener(new OnClickListener(){
	     	   
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(ArticleActivity.this,CollectActivity.class);
	     		   startActivity(intent);
	     	   }
	        });
	        LinearLayout btn7=(LinearLayout)findViewById(R.id.button17);
	        btn7.setOnClickListener(new OnClickListener(){
	     	   
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(ArticleActivity.this,NoticeActivity.class);
	     		   startActivity(intent);
	     	   }
	        });
	        LinearLayout btn8=(LinearLayout)findViewById(R.id.button18);
	        btn8.setOnClickListener(new OnClickListener(){
	     	   
	     	   @Override
	     	   public void onClick(View v){
	     		   Intent intent=new Intent(ArticleActivity.this,FeedbackActivity.class);
	     		   startActivity(intent);
	     	   }
	        });
	}
Runnable eChanged = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String data = etSearch.getText().toString();
			list.clear();//先要清空，不然会叠加
			try{
			getmDataSub(list, data);//获取更新数
			
			mSimpleAdapter.notifyDataSetChanged();//更新
			}
			catch(Exception e){
				Toast.makeText(ArticleActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

			}
			}
	};

	private void getmDataSub(List<Map<String, Object>> DataSubs, String data)
	{
		int lengthbody =mListbody.size();

		for(int i=0;i< lengthbody;i++){
			try{
				if(mListbody.get(i).contains(data)||mListtag.get(i).contains(data) ){
					Map<String,Object> item1 = new HashMap<String,Object>();
					item1.put("image", mListImage.get(i));
					item1.put("body", mListbody.get(i));
			        item1.put("tag",mListtag.get(i));
			        item1.put("look", mListlook.get(i));
			        DataSubs.add(item1);
			        }
				}
		catch(Exception e){
			Toast.makeText(ArticleActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		}
	}

	private int doMax(int x, int y) {
		// TODO Auto-generated method stub
		 if (x >= y) {
			 return x;
	        } else {
	        	return y;
	        }
		
	}
	private List<Map<String,Object>> buildListForSimpleAdapter(){	
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
		mListImage.add(R.drawable.personal_image);
		mListbody.add("中学教师的发展前景如何11111");
		mListtag.add("湖南省  ");
		mListlook.add(256);
		map.put("image",mListImage.get(0));
		map.put("body",mListbody.get(0));
		map.put("tag",mListtag.get(0));
		map.put("look", mListlook.get(0));
		list.add(map);
		
		map=new HashMap<String,Object>();
		mListImage.add(R.drawable.personal_image);
		mListbody.add("学校十二五教师发展规划222222");
		mListtag.add("十二五   ");
		mListlook.add(342);
		map.put("image",mListImage.get(1));
		map.put("body",mListbody.get(1));
		map.put("tag",mListtag.get(1));
		map.put("look", mListlook.get(1));
		list.add(map);
		
		map=new HashMap<String,Object>();
		mListImage.add(R.drawable.bigtree);
		mListbody.add("中学教师的发展前景如何中前景如何中学教师的发展前景如何33333");
		mListtag.add("发展   ");
		mListlook.add(342);
		map.put("image",mListImage.get(2));
		map.put("body",mListbody.get(2));
		map.put("tag",mListtag.get(2));
		map.put("look", mListlook.get(2));
		list.add(map);
		
//		for(int i=0;i<3;i++){
//			map=new HashMap<String,Object>();
//			map.put("image",mListImage.get(1));
//			map.put("body",mListbody.get(1));
//			map.put("tag",mListtag.get(1)+mListtag.get(1));
//			map.put("look", mListlook.get(0));
//			list.add(map);	
//		}
		

	
		return list;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.artical, menu);
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
	
//	public class MyAdapter extends BaseAdapter 
//    {
//        private LayoutInflater mInflater = null;  
//        private MyAdapter(Context context)
//        {
//            this.mInflater = LayoutInflater.from(context);
//        }
//        public int getCount() 
//        {
//            return data.size();
//        }
//        public Object getItem(int position)
//        {
//            return position;
//        }
//        public long getItemId(int position)
//        {
//            return position;
//        }
//        public View getView(int position, View convertView, ViewGroup parent)
//        {
//            ViewHolder holder = null; 
//            if(convertView == null) 
//            {
//                holder = new ViewHolder();
//                convertView = mInflater.inflate(R.layout.list_item, null);  
//                holder.img = (ImageView)convertView.findViewById(R.id.img);  
//                holder.title = (TextView)convertView.findViewById(R.id.tv);  
//                holder.info = (TextView)convertView.findViewById(R.id.info); 
//                convertView.setTag(holder); 
//            }
//            else
//            {
//                holder = (ViewHolder)convertView.getTag();
//            }
//            holder.img.setBackgroundResource((Integer)data.get(position).get("img"));  
//            holder.title.setText((String)data.get(position).get("title"));  
//            holder.info.setText((String)data.get(position).get("info"));  
//            return convertView;  
//             
//        }
//         
//    }
// 
//     

}


