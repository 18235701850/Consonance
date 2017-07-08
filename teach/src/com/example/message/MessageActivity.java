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
	private ViewPager viewPager; // android-support-v4�еĻ������
	private List<ImageView> imageViews; // ������ͼƬ����
	private Bundle bundle;

	private String[] titles; // ͼƬ����
	private int[] imageResId; // ͼƬID
	private List<View> dots; // ͼƬ�������ĵ���Щ��
	private TextView tv_title;
	private int currentItem = 0; // ��ǰͼƬ��������
	private ScheduledExecutorService scheduledExecutorService;
	private int MaxDateNum;

	private Handler handler1;
	private int lastVisibleIndex;
	private ProgressBar pg;
	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// �л���ǰ��ʾ��ͼƬ
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
    private int currIndex;//��ǰҳ�����  
    private int bmpW;//����ͼƬ���  
    private int offset;//ͼƬ�ƶ���ƫ����  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

//		listView=(ListView)findViewById(R.id.listView);
		 try{
		setListeners();		 
//		MaxDateNum = 9; // ���������������
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
		titles[0] = "ר����פ��Ϊ��ʦ�����ṩȨ��ָ��";
		titles[1] = "����С�������������ѧƷ��";
		titles[2] = "�����س������ġ�������ѧд�����������ֻ��ٿ�";
		titles[3] = "��չ����ʦ����������ƽ̨��������";

		imageViews = new ArrayList<ImageView>();

		// ��ʼ��ͼƬ��Դ
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
		viewPager.setAdapter(new MyAdapter());// �������ViewPagerҳ���������
		// ����һ������������ViewPager�е�ҳ��ı�ʱ����
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}
	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// ��Activity��ʾ������ÿ�������л�һ��ͼƬ��ʾ
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
		super.onStart();
	}
	@Override
	protected void onStop() {
		// ��Activity���ɼ���ʱ��ֹͣ�л�
		scheduledExecutorService.shutdown();
		super.onStop();
	}
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler2.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
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
					ivDeleteText.setVisibility(View.GONE);//���ı���Ϊ��ʱ��������ʧ
				}
				else {
					ivDeleteText.setVisibility(View.VISIBLE);//���ı���Ϊ��ʱ�����ֲ��
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
            // ÿ�μ���5��
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
            // �����Ѿ�����5��
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
        // �������ɼ���Ŀ������
        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;

        // ���е���Ŀ�Ѿ������������ȣ����Ƴ��ײ���View
        if (totalItemCount == MaxDateNum + 1) {
//        	listView.removeFooterView(moreView);
            Toast.makeText(this, "����ȫ��������ɣ�û�и������ݣ�", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // �����ײ����Զ����أ��ж�listview�Ѿ�ֹͣ�������������ӵ���Ŀ����adapter����Ŀ
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == mSimpleAdapter.getCount()) {
//             �������ײ�ʱ�Զ�����
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
     * ��ʼ����ǩ�� 
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
     * ��ʼ��ͼƬ��λ������ 
     */  
    public void InitImage(){  
        image = (ImageView)findViewById(R.id.cursor);  
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.main_two_select).getWidth();  
        DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        int screenW = dm.widthPixels;  
        offset = (screenW/3 - bmpW)/2;  
          
        //imgageview����ƽ�ƣ�ʹ�»���ƽ�Ƶ���ʼλ�ã�ƽ��һ��offset��  
        Matrix matrix = new Matrix();  
        matrix.postTranslate(offset, 0);  
        image.setImageMatrix(matrix);  
    }  
      
    /* 
     * ��ʼ��ViewPager 
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
          
        //��ViewPager����������  
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));  
        mPager.setCurrentItem(0);//���õ�ǰ��ʾ��ǩҳΪ��һҳ  
        mPager.setOffscreenPageLimit(3);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//ҳ��仯ʱ�ļ�����  
    }  
  
      
    public class MyOnPageChangeListener implements OnPageChangeListener{  
        private int one = offset *2 +bmpW;//��������ҳ���ƫ����  
          
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
            Animation animation = new TranslateAnimation(currIndex*one,arg0*one,0,0);//ƽ�ƶ���  
            currIndex = arg0;  
            animation.setFillAfter(true);//������ֹʱͣ�������һ֡����Ȼ��ص�û��ִ��ǰ��״̬  
            animation.setDuration(200);//��������ʱ��0.2��  
            image.startAnimation(animation);//����ImageView����ʾ������  
            int i = currIndex + 1;  
//            Toast.makeText(MessageActivity.this, "��ѡ���˵�"+i+"��ҳ��", Toast.LENGTH_SHORT).show();  
        	}
        	catch(Exception e){
        		Toast.makeText(MessageActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        	}
        	}  
    } 
    
}


