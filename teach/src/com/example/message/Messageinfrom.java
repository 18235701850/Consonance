package com.example.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.teacher.R;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;  
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;  
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;  
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;  
import android.view.ViewGroup;  
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
  
public class Messageinfrom extends Fragment implements OnScrollListener{  
    ListView listView;  
	private EditText etSearch;
	private Button bt;
	private  SimpleAdapter mSimpleAdapter;
	private List<ImageView> imageViews; // 滑动的图片集合

	private int[] imageResId; // 图片ID
	private int MaxDateNum;
	 private View moreView1;
	private Handler handler1;
	private int lastVisibleIndex;
	private ProgressBar pg;

	
	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	ArrayList<String> mListbody = new ArrayList<String>();
	ArrayList<String> mListtitle = new ArrayList<String>();
	ArrayList<String> mListtime = new ArrayList<String>();
	ArrayList<String> mListweb = new ArrayList<String>();

    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        View View = inflater.inflate(R.layout.message_inform, container, false);//关联布局文件  
        moreView1=null;  
        listView = (ListView)View.findViewById(R.id.listView);//根据rootView找到button  
        try{
//        设置按键监听事件  
          
		MaxDateNum = 9; // 设置最大数据条数
		 moreView1 = getActivity().getLayoutInflater().inflate(R.layout.moredata, null);
        bt = (Button) moreView1.findViewById(R.id.bt_load);
        pg = (ProgressBar) moreView1.findViewById(R.id.pg);
        handler1 = new Handler();
        setListeners();
        refreshListItems(); 

	   
        }
        catch(Exception e){
        	Toast.makeText(Messageinfrom.this.getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }  
        return View;  
    }  
    private void refreshListItems() {
		list = buildListForSimpleAdapter(); 
        mSimpleAdapter=new SimpleAdapter(
        		getActivity(),
        		list,
        		R.layout.messageitem,
        		new String[]{"body","title","time","web"},
        		new int[]{R.id.bodyView,R.id.titleView,R.id.timeView} ); 
        if(moreView1 !=null){
        	listView.addFooterView(moreView1);
            }
            else{
            	Toast.makeText(Messageinfrom.this.getActivity(),"1111111111", Toast.LENGTH_LONG).show();
            }
        listView.setAdapter(mSimpleAdapter);
        
        listView.setOnScrollListener(this);
	}

	private void setListeners() {
		// TODO Auto-generated method stub

			 listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					try{
						Intent intentweb=new Intent(Messageinfrom.this.getActivity(),MessageWebActivity.class);
						Map<String,Object> map =(Map<String,Object>)mSimpleAdapter.getItem(position);				
						String web=map.get("web").toString();
//						Toast.makeText(Messageinfrom.this.getActivity(),web, Toast.LENGTH_SHORT).show();
					intentweb.putExtra("web",web);
					startActivity(intentweb);
					}
					catch(Exception e){
						Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
					}
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
				Toast.makeText(Messageinfrom.this.getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

			}
			}
	};

	private void getmDataSub(List<Map<String, Object>> DataSubs, String data)
	{
		int lengthbody =mListbody.size();

		for(int i=0;i< lengthbody;i++){
			try{
				if(mListbody.get(i).contains(data)||mListtitle.get(i).contains(data) ){
					Map<String,Object> item1 = new HashMap<String,Object>();
					item1.put("body",mListbody.get(i));
					item1.put("title",mListtitle.get(i));
					item1.put("time", mListtime.get(i));
					item1.put("web", mListweb.get(i));
			        DataSubs.add(item1);
			        }
				}
		catch(Exception e){
			Toast.makeText(Messageinfrom.this.getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
		}
		}
	}

	private List<Map<String,Object>> buildListForSimpleAdapter(){	
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map;
		
		mListbody.add("为扎实推进中小学幼儿园教师国家级培训计划（以下简称“国培计划”）改革实施工作，提升乡村教师培训质量，现就做好2016年工作提出如下要求。");
		mListtitle.add("教育部办公厅 财政部办公厅关于做好2016年中小学幼儿园教师国家级培训计划实施工作的");
		
		mListbody.add("为促进信息技术与课堂教学融合创新，推动信息化教学常态化应用，根据2016年工作安排，教育部继续组织开展“一师一优课、一课一名师”活动。各地要充分认识这项活动的重要意义，");
		mListtitle.add("教育部办公厅关于开展2015-2016年度“一师一优课、一课一名师”活动的通知");
		
		mListbody.add("为贯彻落实《教育信息化十年发展规划（2011-2020年）》，保证教育信息化项目的顺利实施，实现科学、规范、高效管理，促进教育信息化持续健康协调发展，我部研究制定了《教育信息化项目管理暂行办法》。");
		mListtitle.add("教育部办公厅关于印发《教育信息化项目 管理暂行办法》的通知");
		for(int i=39;i<42;i++){
			mListweb.add("http://www.zgjsfz.com/index/showNewDetail/100000008"+i);
		}
		
		mListbody.add("为贯彻党的十八大关于加快发展现代职业教育的重大部署，落实教育规划纲要和《国务院关于加强教师队伍建设的意见》（国发〔2012〕41号）精神，构建教师队伍建设标准体系，");
		mListtitle.add("教育部关于印发《中等职业学校教师专业标准（试行）》的通知");
		
		mListbody.add("为贯彻党的十八届三中全会精神，落实教育规划纲要，构建教师队伍建设标准体系，全面提升中小学教师信息技术应用能力，促进信息技术与教育教学深度融合");
		mListtitle.add("教育部办公厅关于印发《中小学教师信息技术应用能力标准（试行）》的通知");
		
		mListbody.add("为落实《国家中长期教育改革和发展规划纲要（2010—2020年）》要求，进一步完善教师队伍建设标准体系，引领特殊教育教师专业成长，促进特殊教育内涵发展");
		mListtitle.add("教育部关于印发《特殊教育教师专业标准（试行）》的通知");
		for(int i=78;i<81;i++){
			mListweb.add("http://www.zgjsfz.com/index/showNewDetail/100000008"+i);
		}
		
		mListbody.add("近日，教育部颁布了新修订的《幼儿园工作规程》（以下简称《规程》），基础教育二司负责人就本次修订的有关情况接受了记者专访。");
		mListtitle.add("修订《幼儿园工作规程》答记者问");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000758");
		
		mListbody.add("教师是教育事业发展的基础，是提高教育质量、办好人民满意教育的关键。党中央、国务院历来高度重视教师队伍建设。改革开放特别是党的十六大以来，各地区各有关部门采取一系列政策措施");
		mListtitle.add("国务院关于加强教师队伍建设的意见");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000722");
		
		mListbody.add("为了进一步贯彻《国务院关于基础教育改革与发展的决定》（国发[2001]21号）和教育部《基础教育课程改革纲要（试行）》的精神，在基础教育课程改革中更好地发挥教学研究工作的作用");
		mListtitle.add("国家教育部关于改进和加强教学研究工作的意见");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000723");
		
		mListbody.add("“全国教育信息化工作进展信息系统”（以下简称系统）是实时呈现教育信息化工作进展、实施项目管理和提供决策支持的重要抓手。系统自上线以来，在各地的共同努力下，已基本完成了基础数据的采集，");
		mListtitle.add("教育部办公厅关于进一步做好“全国教育信息化 工作进展信息系统”全面应用工作的通知");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000373");
		for(int i=0;i<3;i++){
			mListtime.add("2016-05-09");
		}
		for(int i=0;i<5;i++){
			mListtime.add("2016-04-29");
		}
		mListtime.add("2016-04-28");
		mListtime.add("2016-04-28");
//		mListtime.add("2016-04-26");
//		mListtime.add("2016-04-20");
		
		for(int i=0;i<6;i++){
			map=new HashMap<String,Object>();
			map.put("body","        "+mListbody.get(i));
			map.put("title",mListtitle.get(i));
			map.put("time", mListtime.get(i));
			map.put("web", mListweb.get(i));
			list.add(map);	
		}

		return list;
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
        	listView.removeFooterView(moreView1);
            Toast.makeText(Messageinfrom.this.getActivity(), "公告全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
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
}  