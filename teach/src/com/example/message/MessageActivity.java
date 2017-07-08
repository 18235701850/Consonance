package com.example.message;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.homepage.HomepageActivity;
import com.example.ppt.PptActivity;
import com.example.teacher.R;
import com.example.teacher.R.drawable;
import com.example.teacher.R.id;
import com.example.teacher.R.layout;
import com.example.teacher.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import android.support.v4.app.Fragment;  
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;  
import android.support.v4.app.FragmentPagerAdapter;  

public class MessageActivity extends FragmentActivity implements OnScrollListener {
	
	private ImageView ivDeleteText;
	private EditText etSearch;
//	ListView listView; 
	private Button btnSearch,searchgo,bt;
	private RelativeLayout searchlayout;
	private  SimpleAdapter mSimpleAdapter;
	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合
	private Bundle bundle;

	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点
	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号
	private ScheduledExecutorService scheduledExecutorService;
	private int MaxDateNum;

	private Handler handler1;
	private int lastVisibleIndex;
	private ProgressBar pg;
	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};
	
	Handler myhandler = new Handler();
	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	ArrayList<String> mListbody = new ArrayList<String>();
	ArrayList<String> mListtitle = new ArrayList<String>();
	ArrayList<String> mListtime = new ArrayList<String>();
	ArrayList<String> mListweb = new ArrayList<String>();

	private ViewPager mPager;  
    private ArrayList<Fragment> fragmentList;  
    private ImageView image;  
    private TextView view1, view2, view3;  
    private int currIndex;//当前页卡编号  
    private int bmpW;//横线图片宽度  
    private int offset;//图片移动的偏移量  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

//		listView=(ListView)findViewById(R.id.listView);
		 try{
		setListeners();		 
//		MaxDateNum = 9; // 设置最大数据条数
//        moreView = getLayoutInflater().inflate(R.layout.moredata, null);
//        bt = (Button) moreView.findViewById(R.id.bt_load);
//        pg = (ProgressBar) moreView.findViewById(R.id.pg);
//        handler1 = new Handler();

       
	    refreshListItems(); 
	    viewpagergo();

	    InitTextView();  
        InitImage();  
        InitViewPager(); 
        }
        catch(Exception e){
        	Toast.makeText(MessageActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
	        
	}
	private void viewpagergo() {
		imageResId = new int[] { R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4};
		titles = new String[imageResId.length];
		titles[0] = "专家入驻，为教师研修提供权威指导";
		titles[1] = "打造小组合作，提升教学品牌";
		titles[2] = "监利县初中语文“跟课文学写作”课题研讨会召开";
		titles[3] = "发展网教师研修社区云平台上线啦！";

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

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//

		viewPager = (ViewPager) findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}
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
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler2.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}
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
	private void refreshListItems() {
//		list = buildListForSimpleAdapter(); 
//        mSimpleAdapter=new SimpleAdapter(
//        		this,
//        		list,
//        		R.layout.messageitem,
//        		new String[]{"body","title","time","web"},
//        		new int[]{R.id.bodyView,R.id.titleView,R.id.timeView} ); 
//        listView.setAdapter(mSimpleAdapter);
//        listView.addFooterView(moreView);
//        listView.setOnScrollListener(this);
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
//				 myhandler.post(eChanged);
			}
		 });
//		 btnSearch=(Button)findViewById(R.id.btnSearch);
//		 btnSearch.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				try{
//				 myhandler.post(eChanged);
//				}
//				catch(Exception e){
//					Toast.makeText(MessageActivity.this, e.toString(), Toast.LENGTH_LONG);
//				}
//			}
//		 });
//		 searchgo=(Button)findViewById(R.id.searchgo);
//		 searchlayout=(RelativeLayout)findViewById(R.id.search);
//		 searchgo.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(searchlayout.getVisibility()==View.GONE){
//				 searchlayout.setVisibility(View.VISIBLE);
//				}
//				else{
//					searchlayout.setVisibility(View.GONE);
//				}
//
//			}
//		 });
		ImageButton btn_back=(ImageButton)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener(){
				@Override
	     	   public void onClick(View v){
					MessageActivity.this.finish();
	     	   }
	        });
			 ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
		    etSearch = (EditText) findViewById(R.id.etSearch);
		    ivDeleteText.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						etSearch.setText("");
						list.clear();
//						listView.setAdapter(null);
						refreshListItems(); 
					}
				});
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

	private void loadMoreDate() {
        int count = mSimpleAdapter.getCount();
        if (count + 3 < MaxDateNum) {
            // 每次加载5条
            for (int i = count; i < count + 3; i++) {
            	HashMap<String, Object> map=new HashMap<String,Object>();
            	map=new HashMap<String,Object>();
    			map.put("body","        "+mListbody.get(i));
    			map.put("title",mListtitle.get(i));
    			map.put("time", mListtime.get(i));
    			map.put("web", mListweb.get(i));
    			list.add(map);
            }
        } else {
            // 数据已经不足5条
            for (int i = count; i < MaxDateNum; i++) {
            	HashMap<String, Object> map=new HashMap<String,Object>();
            	map=new HashMap<String,Object>();
    			map.put("body","        "+mListbody.get(i));
    			map.put("title",mListtitle.get(i));
    			map.put("time", mListtime.get(i));
    			map.put("web", mListweb.get(i));
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
//        	listView.removeFooterView(moreView);
            Toast.makeText(this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 滑到底部后自动加载，判断listview已经停止滚动并且最后可视的条目等于adapter的条目
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == mSimpleAdapter.getCount()) {
//             当滑到底部时自动加载
             pg.setVisibility(View.VISIBLE);
             bt.setVisibility(View.GONE);
             handler1.postDelayed(new Runnable() {
            
             @Override
             public void run() {
             loadMoreDate();
             bt.setVisibility(View.VISIBLE);
             pg.setVisibility(View.GONE);
             mSimpleAdapter.notifyDataSetChanged();
             }
             }, 2000);
        }

    } 
    
	 /* 
     * 初始化标签名 
     */  
    public void InitTextView(){  
        view1 = (TextView)findViewById(R.id.tv_guid1);  
        view2 = (TextView)findViewById(R.id.tv_guid2);  
        view3 = (TextView)findViewById(R.id.tv_guid3);  
          
        view1.setOnClickListener(new txListener(0));  
        view2.setOnClickListener(new txListener(1));  
        view3.setOnClickListener(new txListener(2));  
    }  
      
      
    public class txListener implements View.OnClickListener{  
        private int index=0;  
          
        public txListener(int i) {  
            index =i;  
        }  
        @Override  
        public void onClick(View v) {  
            // TODO Auto-generated method stub  
            mPager.setCurrentItem(index);  
        }  
    }  
      
    /* 
     * 初始化图片的位移像素 
     */  
    public void InitImage(){  
        image = (ImageView)findViewById(R.id.cursor);  
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.main_two_select).getWidth();  
        DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        int screenW = dm.widthPixels;  
        offset = (screenW/3 - bmpW)/2;  
          
        //imgageview设置平移，使下划线平移到初始位置（平移一个offset）  
        Matrix matrix = new Matrix();  
        matrix.postTranslate(offset, 0);  
        image.setImageMatrix(matrix);  
    }  
      
    /* 
     * 初始化ViewPager 
     */  
    public void InitViewPager(){  
        mPager = (ViewPager)findViewById(R.id.viewpager);  
        fragmentList = new ArrayList<Fragment>();  
        Fragment oneFragment= new Messageinfrom();
        Fragment secondFragment = new Messagemessage();  
        Fragment thirdFragment = new Messagerule();
        fragmentList.add(oneFragment);  
        fragmentList.add(secondFragment);  
        fragmentList.add(thirdFragment);  
          
        //给ViewPager设置适配器  
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));  
        mPager.setCurrentItem(0);//设置当前显示标签页为第一页  
        mPager.setOffscreenPageLimit(3);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器  
    }  
  
      
    public class MyOnPageChangeListener implements OnPageChangeListener{  
        private int one = offset *2 +bmpW;//两个相邻页面的偏移量  
          
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
              
        }  
          
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
              
        }  
          
        @Override  
        public void onPageSelected(int arg0) {  
            // TODO Auto-generated method stub  
        	try{
            Animation animation = new TranslateAnimation(currIndex*one,arg0*one,0,0);//平移动画  
            currIndex = arg0;  
            animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态  
            animation.setDuration(200);//动画持续时间0.2秒  
            image.startAnimation(animation);//是用ImageView来显示动画的  
            int i = currIndex + 1;  
//            Toast.makeText(MessageActivity.this, "您选择了第"+i+"个页卡", Toast.LENGTH_SHORT).show();  
        	}
        	catch(Exception e){
        		Toast.makeText(MessageActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        	}
        	}  
    } 
    
}


