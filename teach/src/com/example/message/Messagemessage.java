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
	private List<ImageView> imageViews; // ������ͼƬ����

	private int[] imageResId; // ͼƬID
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
        View View = inflater.inflate(R.layout.message_inform, container, false);//���������ļ�   
        listView = (ListView)View.findViewById(R.id.listView);//����rootView�ҵ�button  
        try{
//        ���ð��������¼�  
        
        setListeners();
          
		MaxDateNum = 9; // ���������������
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
			list.clear();//��Ҫ��գ���Ȼ�����
			try{
			getmDataSub(list, data);//��ȡ������
			
			mSimpleAdapter.notifyDataSetChanged();//����
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
		mListbody.add("����Ѷ������ ���Ϊ��������ʦ��ѵʵЧ����������ǰӡ�����ͽ�������ѵָ�ϡ�������ʦ��ѵָ�ϣ������ƶ��������ʦ��ѵģʽָ��Ҫ�����ؽ�����������Ҫ���ͽ�������ѵ��������ʦȫԱ��ѵ�滮���ƶ��ͽ�");
		mListtitle.add("���������ͽ�������ѵ������8�� ");
		mListtime.add("2016-05-11 18:30:40");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000783");
	
		mListbody.add("�������ļ���ʦ[2016]2�Ž��������ڼ�ǿʦ��������ʵ���������ʡ����������ֱϽ�н���������ί�����½�����������Ž����֣������йظߵ�ѧУ�����������ҹ���ʦ�����ĸ�����ƽ���ʦ��������ʵ�����ϼ�ǿ�����ǻ�������Ŀ�겻�����������ݲ����ḻ����ʽ��Ե�һ��ָ��������ǿ���������ۺ���֯������Ա���");
		mListtitle.add("���������ڼ�ǿʦ��������ʵ�������  ");
		mListtime.add("2016-05-11 18:30:04");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000784");
	
		mListbody.add("�ص����ƣ����³�̬�·������������������á�2016��4��19�գ�ϰ��ƽ�����簲ȫ����Ϣ��������̸����ָ");
		mListtitle.add("ϰ��ƽΪ��˵���������ⷽ�������Ϊ��   ");
		mListtime.add("2016-05-10 09:36:58");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000887");
		
		mListbody.add("����Ѷ������ �ںӣ���������������һ˾�ͽ���������Ϣ���Ľ����ںӱ�ʡ�������ٿ�ȫ����Сѧ��ѧ���������ƽ��ᡣ������ȷ��Ҫ��һ�����ѧ���������ˮƽ����ǿ");
		mListtitle.add("ȫ����Сѧ��ѧ���������ƽ����ٿ�");
		mListtime.add("2016-05-11 18:28:41");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000785");
		
		mListbody.add("��ʦ��Ӱ��ѧ�������ɳ��Ĺؼ��������߽����������ܶ����أ��Ǵٽ�������ƽ����Ҫ��֤����һ���ش�������ĺ���������");
		mListtitle.add("ר�����Ա���ҽ��������ʦ֧�ּƻ���2015��2020�꣩��");
		mListtime.add("2016-05-03 08:13:00");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000782");
		
		mListbody.add("Ϊ����������ũ���ʦ�����»��ƣ��������������˲ŵ�ũ��ѧУ�ӽ̣�������1�շ���֪ͨ����2016��ũ����������׶�ѧУ��ʦ�����λ�ƻ�ʵʩ�������в���");
		mListtitle.add("��������2016���ҹ�����ƸԼ7�����ظڽ�ʦ");
		mListtime.add("2016-04-05 11:49:24");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000582");
		
		mListbody.add("���գ�����Ժ�칫��ӡ��������ʦ֧�ּƻ���2015-2020�꣩�������¼�ơ��ƻ��������������йظ����˾��������ش��˼������ʡ�");
		mListtitle.add("�������йظ����˾�ʵʩ������ʦ֧�ּƻ���2015-2020�꣩���������");
		mListtime.add("2016-05-03 08:12:53");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000781");
		
		mListbody.add("����Ѷ������ ��ε��4��11����12�գ�2015��2016��ȡ�һʦһ�ſΡ�һ��һ��ʦ������Ҽ���ѵ��2016��ȫ���绯�����ݹݳ������ڽ���ʡ�Ͼ����ٿ�����������������ռԪ��ϯ���鲢������");
		mListtitle.add("ȫ���绯�����ݹݳ������ٿ�");
		mListtime.add("2016-04-29 08:34:57");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000759");
		
		mListbody.add("�������Ѿ����롮������ʱ����������������˳����Ϳ��ܱ���Զ˦�ں��档��");
		mListtitle.add("���ǿ���ٸ��������Ƽ���ҵ��������֧��");
		mListtime.add("2016-04-28 09:28:33");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000733");
		
		mListbody.add("���ǿ�����ڽ���������������̸���������ࡱ�ĸ�ʱ��ָ��Ҫ�����������󾫵Ĺ������񡱡����������״α�д�������������棬��Ϊ�ڶ�ý��Ĺ�ע�ȵ㡣��ʵ�������Ǿ��øĸ���Ҫ�����������񣬽�ʦ�����ĸ�ҲҪ��ʰ��������");
		mListtitle.add("��ʦ��������ʰ����������");
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
        	listView.removeFooterView(moreView2);
            Toast.makeText(Messagemessage.this.getActivity(), "��Ѷȫ��������ɣ�û�и������ݣ�", Toast.LENGTH_LONG).show();
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
}  