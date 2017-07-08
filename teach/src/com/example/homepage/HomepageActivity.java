package com.example.homepage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import com.example.article.ArticleActivity;
import com.example.message.MessageActivity;
import com.example.message.MessageWebActivity;
import com.example.message.Messagemessage;
import com.example.ppt.PptActivity;
import com.example.ppt.VideoActivity;
import com.example.teacher.R;
import com.example.teacher.R.drawable;
import com.example.teacher.R.id;
import com.example.teacher.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
public class HomepageActivity extends Activity {
	
	private SlidingLayout slidingLayout;
	 Bitmap bitmap;
	private Button menuButton;

	private ListView contentListView;
	private SharedPreferences sp;

	ImageView image;
	private ArrayAdapter<String> contentListAdapter;
	
	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	ArrayList<Integer> mListImage = new ArrayList<Integer>();
	ArrayList<String> mListbody = new ArrayList<String>();
	ArrayList<String> mListtag = new ArrayList<String>();
	ArrayList<Integer> mListlook = new ArrayList<Integer>();
	ArrayList<String> mListweb = new ArrayList<String>();

	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.homepage);
			contentListView = (ListView) findViewById(R.id.contentlist);
			View header = View.inflate(this, R.layout.head, null);
			contentListView.addHeaderView(header);
			contentListView.addHeaderView(View.inflate(this, R.layout.float1, null));
			slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
			menuButton = (Button) findViewById(R.id.menuButton);
		contentListView = (ListView) findViewById(R.id.contentlist);
		addlistitem();
		 image=(ImageView)findViewById(R.id.imageView1);
		slidingLayout.setScrollEvent(contentListView);
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
//		contentListView.setOnScrollListener(new OnScrollListener() {
//
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//
////				if (slidingLayout.isLeftLayoutVisible()) {
////					slidingLayout.scrollToRightLayout();
////				} 
////				else {
////					slidingLayout.scrollToLeftLayout();
////					slidingLayout.leftLayout.setVisibility(View.VISIBLE);
////				}
//			}
//
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
//				if (firstVisibleItem >= 1) {
//					linear.setVisibility(View.VISIBLE);
//					
//				} else{
//					linear.setVisibility(View.GONE);
//					listener();
//				}
//			}
//		});
		RelativeLayout btn2=(RelativeLayout)findViewById(R.id.fmessage);
        btn2.setOnClickListener(new OnClickListener(){
     	   
     	   @Override
     	   public void onClick(View v){
     		   Intent intent=new Intent(HomepageActivity.this,MessageActivity.class);
     		   startActivity(intent);
     	   }
        });
        RelativeLayout btn_skill=(RelativeLayout)findViewById(R.id.fskill);
        btn_skill.setOnClickListener(new OnClickListener(){
     	   
     	   @Override
     	   public void onClick(View v){
     		   Intent intent=new Intent(HomepageActivity.this,Skill.class);
     		   startActivity(intent);
     	   }
        });
        LinearLayout btnluntan=(LinearLayout)findViewById(R.id.btnluntan);
        btnluntan.setOnClickListener(new OnClickListener(){
     	   
     	   @Override
     	   public void onClick(View v){
     		   Intent intent=new Intent(HomepageActivity.this,ArticleActivity.class);
     		   startActivity(intent);
     		  HomepageActivity.this.finish();
     		  overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
     	   }
        });
        LinearLayout btnppt=(LinearLayout)findViewById(R.id.btnppt);
        btnppt.setOnClickListener(new OnClickListener(){
     	   
     	   @Override
     	   public void onClick(View v){
     		   Intent intent=new Intent(HomepageActivity.this,PptActivity.class);
     		   startActivity(intent);
     		  HomepageActivity.this.finish();
     		  overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
     	   }
        });
        LinearLayout btnvideo=(LinearLayout)findViewById(R.id.btnvideo);
        btnvideo.setOnClickListener(new OnClickListener(){
     	   
     	   @Override
     	   public void onClick(View v){
     		   Intent intent=new Intent(HomepageActivity.this,VideoActivity.class);
     		   startActivity(intent);
     		  HomepageActivity.this.finish();
     		  overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
     	   }
        });
       
        LinearLayout btn15=(LinearLayout)findViewById(R.id.button15);
        btn15.setOnClickListener(new OnClickListener(){
     	   
     	   @Override
     	   public void onClick(View v){
     		   Intent intent=new Intent(HomepageActivity.this,Main.class);
     		   startActivity(intent);
     	   }
        });
        LinearLayout btn16=(LinearLayout)findViewById(R.id.button16);
        btn16.setOnClickListener(new OnClickListener(){
     	   
     	   @Override
     	   public void onClick(View v){
     		   Intent intent=new Intent(HomepageActivity.this,CollectActivity.class);
     		   startActivity(intent);
     	   }
        });
        LinearLayout btn7=(LinearLayout)findViewById(R.id.button17);
        btn7.setOnClickListener(new OnClickListener(){
     	   
     	   @Override
     	   public void onClick(View v){
     		   Intent intent=new Intent(HomepageActivity.this,NoticeActivity.class);
     		   startActivity(intent);
     	   }
        });
        LinearLayout btn8=(LinearLayout)findViewById(R.id.button18);
        btn8.setOnClickListener(new OnClickListener(){
     	   
     	   @Override
     	   public void onClick(View v){
     		   Intent intent=new Intent(HomepageActivity.this,FeedbackActivity.class);
     		   startActivity(intent);
     	   }
        });


		imageResId = new int[] { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };
		titles = new String[imageResId.length];
		titles[0] = "灵犀，实习教师启迪家园";
		titles[1] = "专业引领，启迪智慧人生";
		titles[2] = "同伴互助，伴随教师终身学习";
		titles[3] = "自我反思，成就教师专业发展";
		titles[4] = "课堂实践，展示教师教学风采";

		imageViews = new ArrayList<ImageView>();

		// 初始化图片资源
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}


		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.v_dot0));
		dots.add(findViewById(R.id.v_dot1));
		dots.add(findViewById(R.id.v_dot2));
		dots.add(findViewById(R.id.v_dot3));
		dots.add(findViewById(R.id.v_dot4));

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//

		viewPager = (ViewPager) findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());

	}


	protected void listener() {
		// TODO Auto-generated method stub
//		Button btn2=(Button)findViewById(R.id.message);
//        btn2.setOnClickListener(new OnClickListener(){
//     	   
//     	   @Override
//     	   public void onClick(View v){
//     		   Intent intent=new Intent(HomepageActivity.this,MessageActivity.class);
//     		   startActivity(intent);
//     	   }
//        });
//        Button btn_skill=(Button)findViewById(R.id.skill);
//        btn_skill.setOnClickListener(new OnClickListener(){
//     	   
//     	   @Override
//     	   public void onClick(View v){
//     		   Intent intent=new Intent(HomepageActivity.this,Skill.class);
//     		   startActivity(intent);
//     	   }
//        });
        LinearLayout btnexit=(LinearLayout)findViewById(R.id.exit);
        btnexit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(HomepageActivity.this, "注销", Toast.LENGTH_SHORT).show();
			}
        });
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
	private void addlistitem() {
		// TODO Auto-generated method stub
		list = buildListForSimpleAdapter(); 
        final SimpleAdapter mSimpleAdapter=new SimpleAdapter(this,list,R.layout.articleitem,
        		new String[]{"image","body","tag","look"},
        		new int[]{R.id.image,R.id.bodyView,R.id.tagView,R.id.lookView} ); 
        contentListView.setAdapter(mSimpleAdapter);
        contentListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try{
					Intent intentweb=new Intent(HomepageActivity.this,MessageWebActivity.class);
					Map<String,Object> map =(Map<String,Object>)mSimpleAdapter.getItem(position);				
					String web=map.get("web").toString();
//					Toast.makeText(HomepageActivity.this,web, Toast.LENGTH_SHORT).show();
				intentweb.putExtra("web",web);
				startActivity(intentweb);
				}
				catch(Exception e){
					Toast.makeText(HomepageActivity.this, e.toString(), Toast.LENGTH_LONG).show();
				}
			}
        });

	}
	private List<Map<String,Object>> buildListForSimpleAdapter(){	
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
		
		int look[]=new int[20];
		look[0]=1701;
		mListbody.add("“草根式”网站接地气 “个性化”服务受青睐");
		mListtag.add("来源：中国教师发展网");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000002340");
		mListImage.add(R.drawable.bigtree);
		
		map=new HashMap<String,Object>();
		look[1]=2745;
		mListbody.add("松滋国培简报第4期");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000002340");
		mListImage.add(null);

		mListImage.add(R.drawable.hot);
		look[2]=2807;
		mListbody.add("教育部关于大力推行中小学教师培训学分管理的指导意见");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000002053");
		mListImage.add(null);
		
		look[3]=2984;
		mListbody.add("松滋国培简报第3期");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000002000");
		mListImage.add(null);
		
		look[4]=4226;
		mListbody.add("“国培计划（2016）”--松滋市网络研修与校本研修整合培训用户体验报告");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000002088");
		mListImage.add(R.drawable.bigtree);
		
		look[5]=3939;
		mListbody.add("中国学生发展六大核心素养");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000001834");
		mListImage.add(R.drawable.hot);
		
		look[6]=128;
		mListbody.add("武汉市东西湖区启动教师远程培训");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000001327");
		mListImage.add(null);
		
		look[7]=511;
		mListbody.add("习近平为何说互联网在这方面大有作为？");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000887");
		mListImage.add(R.drawable.bigtree);
		
		look[8]=182;
		mListbody.add("教师资格条例");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000001327");
		mListImage.add(null);
		
		look[9]=193;
		mListbody.add("全国电化教育馆馆长会议召开");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000759-0");
		mListImage.add(null);
		for(int i=0;i<10;i++){
			mListtag.add("来源：中国教师发展网");
		}
		for(int i=0;i<10;i++){
			mListlook.add(look[i]);
		}
		for(int i=0;i<10;i++){
			 map=new HashMap<String,Object>();
			map.put("image",mListImage.get(i));
			map.put("body",mListbody.get(i));
			map.put("tag",mListtag.get(i));
			map.put("look", mListlook.get(i));
			map.put("web", mListweb.get(i));
			list.add(map);	
		}
		
		return list;
	}
	
	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合

	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点

	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引
	private ScheduledExecutorService scheduledExecutorService;

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};
	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	protected void onStop() {
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageResId.length;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}
}
