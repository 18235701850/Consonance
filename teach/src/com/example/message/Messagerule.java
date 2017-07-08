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
  
public class Messagerule extends Fragment implements OnScrollListener{  
    ListView listView;  
	private EditText etSearch;
	private Button bt;
	private  SimpleAdapter mSimpleAdapter;
	private List<ImageView> imageViews; // 滑动的图片集合

	private int[] imageResId; // 图片ID
	private int MaxDateNum;
	 private View moreView3;
	private Handler handler1;
	private int lastVisibleIndex;
	private ProgressBar pg;

	
	Handler myhandler = new Handler();
	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	ArrayList<String> mListbody = new ArrayList<String>();
	ArrayList<String> mListtitle = new ArrayList<String>();
	ArrayList<String> mListtime = new ArrayList<String>();
	ArrayList<String> mListweb = new ArrayList<String>();
	public View View;

    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        View = inflater.inflate(R.layout.message_inform, container, false);//关联布局文件  
        listView = (ListView)View.findViewById(R.id.listView);//根据rootView找到button  
        try{
//        设置按键监听事件  
        
        setListeners();
          
		MaxDateNum = 9; // 设置最大数据条数
		 moreView3 = getActivity().getLayoutInflater().inflate(R.layout.moredata, null);
        bt = (Button) moreView3.findViewById(R.id.bt_load);
        pg = (ProgressBar) moreView3.findViewById(R.id.pg);
        handler1 = new Handler();
        refreshListItems(); 
        }
        catch(Exception e){
        	Toast.makeText(Messagerule.this.getActivity(), e.toString(), Toast.LENGTH_LONG).show();
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
        if(moreView3 !=null){
        	listView.addFooterView(moreView3);
            }
        else{
        	Toast.makeText(Messagerule.this.getActivity(),"33333333333", Toast.LENGTH_LONG).show();
        }
        listView.setAdapter(mSimpleAdapter);
        
        listView.setOnScrollListener(this);
	}

	private void setListeners() {
		// TODO Auto-generated method stub


			 listView=(ListView)View.findViewById(R.id.listView);
			 listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					try{
						Intent intentweb=new Intent(getActivity(),MessageWebActivity.class);
						Map<String,Object> map =(Map<String,Object>)mSimpleAdapter.getItem(position);				
						String web=map.get("web").toString();
//						Toast.makeText(getActivity(),web, Toast.LENGTH_SHORT).show();
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
				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

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
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
		}
		}
	}

	private List<Map<String,Object>> buildListForSimpleAdapter(){	
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map;
		mListbody.add("第一条 为规范民办非学历教育培训机构办学行为和教育审批机关的管理行为");
		mListtitle.add("武汉市中等及以下民办非学历教育培训机构设置和管理暂行办法");
		mListtime.add("2016-07-15");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000001275");
	
		mListbody.add("《幼儿园工作规程》已经2015年12月14日第48次部长办公会议审议通过，现予公布，自2016年3月1日起施行。　　");
		mListtitle.add("《幼儿园工作规程》中华人民共和国教育部令第39号");
		mListtime.add("2016-03-02");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000471");
		
		mListbody.add("为贯彻落实乡村教师支持计划（2015—2020年），推动各地变革乡村教师培训模式，提升乡村教师培训实效，在总结各地经验基础上，教育部研究制定了《送教下乡培训指南》《乡村教师网络研修与校本研修整合培训指南》");
		mListtitle.add("教育部办公厅关于印发乡村教师培训指南的通知");
		mListtime.add("2016-02-17 ");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000302");
		
		mListbody.add("为保证学校体育工作的正常开展，促进学生身心的健康成长，制定本条例。");
		mListtitle.add("学校体育工作条例");
		mListtime.add("2016-01-08");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000430");
		
		mListbody.add("为了提高教师素质，加强教师队伍建设，依据《中华人民共和国教师法》（以下简称教师法），制定本条例。");
		mListtitle.add("教师资格条例");
		mListtime.add("2016-01-08 ");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000429");
		
		mListbody.add("为了加强幼儿园的管理，促进幼儿教育事业的发展，制定本条例。");
		mListtitle.add("幼儿园管理条例");
		mListtime.add("2016-01-08");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000428");
		
		mListbody.add("为奖励取得教学成果的集体和个人，鼓励教育工作者从事教育教学研究，提高教学水平和教育质量，制定本条例。");
		mListtitle.add("教学成果奖励条例");
		mListtime.add("2016-01-08");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000427");
		
		mListbody.add("根据中华人民共和国义务教育法（以下简称义务教育法）第十七条的规定，制定本细则。");
		mListtitle.add("中华人民共和国义务教育法实施细则");
		mListtime.add("2016-01-08");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000426");
		
		mListbody.add("一、爱国守法。热爱祖国，热爱人民，拥护中国共产党领导，拥护社会主义。全面贯彻国家教育方针，自觉遵守教育法律法规，依法履行教师职责权利。不得有违背党和国家方针政策的言行。");
		mListtitle.add("中小学教师职业道德规范");
		mListtime.add("2016-01-08");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000425");
		
		mListbody.add("为了保障教师的合法权益，建设具有良好思想品德修养和业务素质的教师队伍，促进社会主义教育事业的发展，制定本法。 ");
		mListtitle.add("中华人民共和国教师法");
		mListtime.add("2016-01-08");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000424");
		
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
        	listView.removeFooterView(moreView3);
            Toast.makeText(getActivity(), "法规全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
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