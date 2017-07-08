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
	private List<ImageView> imageViews; // ������ͼƬ����

	private int[] imageResId; // ͼƬID
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
        View View = inflater.inflate(R.layout.message_inform, container, false);//���������ļ�  
        moreView1=null;  
        listView = (ListView)View.findViewById(R.id.listView);//����rootView�ҵ�button  
        try{
//        ���ð��������¼�  
          
		MaxDateNum = 9; // ���������������
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
			list.clear();//��Ҫ��գ���Ȼ�����
			try{
			getmDataSub(list, data);//��ȡ������
			
			mSimpleAdapter.notifyDataSetChanged();//����
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
		
		mListbody.add("Ϊ��ʵ�ƽ���Сѧ�׶�԰��ʦ���Ҽ���ѵ�ƻ������¼�ơ�����ƻ������ĸ�ʵʩ��������������ʦ��ѵ�������־�����2016�깤���������Ҫ��");
		mListtitle.add("�������칫�� �������칫����������2016����Сѧ�׶�԰��ʦ���Ҽ���ѵ�ƻ�ʵʩ������");
		
		mListbody.add("Ϊ�ٽ���Ϣ��������ý�ѧ�ںϴ��£��ƶ���Ϣ����ѧ��̬��Ӧ�ã�����2016�깤�����ţ�������������֯��չ��һʦһ�ſΡ�һ��һ��ʦ���������Ҫ�����ʶ��������Ҫ���壬");
		mListtitle.add("�������칫�����ڿ�չ2015-2016��ȡ�һʦһ�ſΡ�һ��һ��ʦ�����֪ͨ");
		
		mListbody.add("Ϊ�᳹��ʵ��������Ϣ��ʮ�귢չ�滮��2011-2020�꣩������֤������Ϣ����Ŀ��˳��ʵʩ��ʵ�ֿ�ѧ���淶����Ч�����ٽ�������Ϣ����������Э����չ���Ҳ��о��ƶ��ˡ�������Ϣ����Ŀ�������а취����");
		mListtitle.add("�������칫������ӡ����������Ϣ����Ŀ �������а취����֪ͨ");
		for(int i=39;i<42;i++){
			mListweb.add("http://www.zgjsfz.com/index/showNewDetail/100000008"+i);
		}
		
		mListbody.add("Ϊ�᳹����ʮ�˴���ڼӿ췢չ�ִ�ְҵ�������ش�����ʵ�����滮��Ҫ�͡�����Ժ���ڼ�ǿ��ʦ���齨����������������2012��41�ţ����񣬹�����ʦ���齨���׼��ϵ��");
		mListtitle.add("����������ӡ�����е�ְҵѧУ��ʦרҵ��׼�����У�����֪ͨ");
		
		mListbody.add("Ϊ�᳹����ʮ�˽�����ȫ�ᾫ����ʵ�����滮��Ҫ��������ʦ���齨���׼��ϵ��ȫ��������Сѧ��ʦ��Ϣ����Ӧ���������ٽ���Ϣ�����������ѧ����ں�");
		mListtitle.add("�������칫������ӡ������Сѧ��ʦ��Ϣ����Ӧ��������׼�����У�����֪ͨ");
		
		mListbody.add("Ϊ��ʵ�������г��ڽ����ĸ�ͷ�չ�滮��Ҫ��2010��2020�꣩��Ҫ�󣬽�һ�����ƽ�ʦ���齨���׼��ϵ���������������ʦרҵ�ɳ����ٽ���������ں���չ");
		mListtitle.add("����������ӡ�������������ʦרҵ��׼�����У�����֪ͨ");
		for(int i=78;i<81;i++){
			mListweb.add("http://www.zgjsfz.com/index/showNewDetail/100000008"+i);
		}
		
		mListbody.add("���գ��������䲼�����޶��ġ��׶�԰������̡������¼�ơ���̡���������������˾�����˾ͱ����޶����й���������˼���ר�á�");
		mListtitle.add("�޶����׶�԰������̡��������");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000758");
		
		mListbody.add("��ʦ�ǽ�����ҵ��չ�Ļ���������߽������������������������Ĺؼ��������롢����Ժ�����߶����ӽ�ʦ���齨�衣�ĸ￪���ر��ǵ���ʮ�������������������йز��Ų�ȡһϵ�����ߴ�ʩ");
		mListtitle.add("����Ժ���ڼ�ǿ��ʦ���齨������");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000722");
		
		mListbody.add("Ϊ�˽�һ���᳹������Ժ���ڻ��������ĸ��뷢չ�ľ�����������[2001]21�ţ��ͽ����������������γ̸ĸ��Ҫ�����У����ľ����ڻ��������γ̸ĸ��и��õط��ӽ�ѧ�о�����������");
		mListtitle.add("���ҽ��������ڸĽ��ͼ�ǿ��ѧ�о����������");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000723");
		
		mListbody.add("��ȫ��������Ϣ��������չ��Ϣϵͳ�������¼��ϵͳ����ʵʱ���ֽ�����Ϣ��������չ��ʵʩ��Ŀ������ṩ����֧�ֵ���Ҫץ�֡�ϵͳ�������������ڸ��صĹ�ͬŬ���£��ѻ�������˻������ݵĲɼ���");
		mListtitle.add("�������칫�����ڽ�һ�����á�ȫ��������Ϣ�� ������չ��Ϣϵͳ��ȫ��Ӧ�ù�����֪ͨ");
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
        	listView.removeFooterView(moreView1);
            Toast.makeText(Messageinfrom.this.getActivity(), "����ȫ��������ɣ�û�и������ݣ�", Toast.LENGTH_LONG).show();
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