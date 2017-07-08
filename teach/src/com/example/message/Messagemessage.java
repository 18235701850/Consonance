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
  
public class Messagemessage extends Fragment implements OnScrollListener{  
    ListView listView;  
	private EditText etSearch;
	private Button bt;
	private  SimpleAdapter mSimpleAdapter;
	private List<ImageView> imageViews; // 滑动的图片集合

	private int[] imageResId; // 图片ID
	private int MaxDateNum;
	 private View moreView2;
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
        listView = (ListView)View.findViewById(R.id.listView);//根据rootView找到button  
        try{
//        设置按键监听事件  
        
        setListeners();
          
		MaxDateNum = 9; // 设置最大数据条数
		 moreView2 = getActivity().getLayoutInflater().inflate(R.layout.moredata, null);
        bt = (Button) moreView2.findViewById(R.id.bt_load);
        pg = (ProgressBar) moreView2.findViewById(R.id.pg);
        handler1 = new Handler();
        refreshListItems(); 
	   
        }
        catch(Exception e){
        	Toast.makeText(Messagemessage.this.getActivity(), e.toString(), Toast.LENGTH_LONG).show();
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
        if(moreView2 !=null){
        	listView.addFooterView(moreView2);
            }
            else{
            	Toast.makeText(Messagemessage.this.getActivity(),"222222222", Toast.LENGTH_LONG).show();
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
						Intent intentweb=new Intent(Messagemessage.this.getActivity(),MessageWebActivity.class);
						Map<String,Object> map =(Map<String,Object>)mSimpleAdapter.getItem(position);				
						String web=map.get("web").toString();
//						Toast.makeText(Messagemessage.this.getActivity(),web, Toast.LENGTH_SHORT).show();
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
				Toast.makeText(Messagemessage.this.getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

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
			Toast.makeText(Messagemessage.this.getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
		}
		}
	}

	private List<Map<String,Object>> buildListForSimpleAdapter(){	
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map;
		mListbody.add("本报讯（记者 李澈）为提升乡村教师培训实效，教育部日前印发《送教下乡培训指南》等乡村教师培训指南，力求推动变革乡村教师培训模式指南要求，区县教育行政部门要将送教下乡培训纳入乡村教师全员培训规划，制定送教");
		mListtitle.add("教育部：送教下乡培训不少于8天 ");
		mListtime.add("2016-05-11 18:30:40");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000783");
	
		mListbody.add("教育部文件教师[2016]2号教育部关于加强师范生教育实践的意见各省、自治区、直辖市教育厅（教委），新疆生产建设兵团教育局，部属有关高等学校：近年来，我国教师教育改革持续推进，师范生教育实践不断加强，但是还存在着目标不够清晰，内容不够丰富，形式相对单一，指导力量不强，管理评价和组织保障相对薄弱");
		mListtitle.add("教育部关于加强师范生教育实践的意见  ");
		mListtime.add("2016-05-11 18:30:04");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000784");
	
		mListbody.add("特的优势，在新常态下发挥着驱动和引领作用。2016年4月19日，习近平在网络安全和信息化工作座谈会上指");
		mListtitle.add("习近平为何说互联网在这方面大有作为？   ");
		mListtime.add("2016-05-10 09:36:58");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000887");
		
		mListbody.add("本报讯（记者 宗河）教育部基础教育一司和教育管理信息中心近日在河北省保定市召开全国中小学生学籍管理工作推进会。会议明确，要进一步提高学籍管理服务水平，增强");
		mListtitle.add("全国中小学生学籍管理工作推进会召开");
		mListtime.add("2016-05-11 18:28:41");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000785");
		
		mListbody.add("教师是影响学生健康成长的关键人物，是提高教育质量的能动因素，是促进教育公平的重要保证，是一切重大教育变革的核心力量。");
		mListtitle.add("专家组成员独家解读《乡村教师支持计划（2015—2020年）》");
		mListtime.add("2016-05-03 08:13:00");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000782");
		
		mListbody.add("为建立并完善农村教师补充新机制，吸引更多优秀人才到农村学校从教，教育部1日发出通知，对2016年农村义务教育阶段学校教师特设岗位计划实施工作进行部署");
		mListtitle.add("教育部：2016年我国将招聘约7万名特岗教师");
		mListtime.add("2016-04-05 11:49:24");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000582");
		
		mListbody.add("近日，国务院办公厅印发《乡村教师支持计划（2015-2020年）》（以下简称《计划》）。教育部有关负责人就相关问题回答了记者提问。");
		mListtitle.add("教育部有关负责人就实施《乡村教师支持计划（2015-2020年）》答记者问");
		mListtime.add("2016-05-03 08:12:53");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000781");
		
		mListbody.add("本报讯（记者 黄蔚）4月11日至12日，2015—2016年度“一师一优课、一课一名师”活动国家级培训暨2016年全国电化教育馆馆长会议在江苏省南京市召开。教育部副部长杜占元出席会议并讲话。");
		mListtitle.add("全国电化教育馆馆长会议召开");
		mListtime.add("2016-04-29 08:34:57");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000759");
		
		mListbody.add("“我们已经进入‘互联网时代’，跟不上这个浪潮，就可能被永远甩在后面。”");
		mListtitle.add("李克强力促给互联网科技企业更大政策支持");
		mListtime.add("2016-04-28 09:28:33");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000733");
		
		mListbody.add("李克强总理在今年政府工作报告谈及“供给侧”改革时，指出要“培育精益求精的工匠精神”。工匠精神首次被写入政府工作报告，成为众多媒体的关注热点。其实，不仅是经济改革需要培养工匠精神，教师教育改革也要重拾工匠精神。");
		mListtitle.add("教师教育：重拾“工匠精神”");
		mListtime.add("2016-04-29 08:25:55");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000755");
		
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
        	listView.removeFooterView(moreView2);
            Toast.makeText(Messagemessage.this.getActivity(), "资讯全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
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