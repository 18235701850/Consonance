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
	private List<ImageView> imageViews; // ������ͼƬ����

	private int[] imageResId; // ͼƬID
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
        View = inflater.inflate(R.layout.message_inform, container, false);//���������ļ�  
        listView = (ListView)View.findViewById(R.id.listView);//����rootView�ҵ�button  
        try{
//        ���ð��������¼�  
        
        setListeners();
          
		MaxDateNum = 9; // ���������������
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
			list.clear();//��Ҫ��գ���Ȼ�����
			try{
			getmDataSub(list, data);//��ȡ������
			
			mSimpleAdapter.notifyDataSetChanged();//����
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
		mListbody.add("��һ�� Ϊ�淶����ѧ��������ѵ������ѧ��Ϊ�ͽ����������صĹ�����Ϊ");
		mListtitle.add("�人���еȼ���������ѧ��������ѵ�������ú͹������а취");
		mListtime.add("2016-07-15");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000001275");
	
		mListbody.add("���׶�԰������̡��Ѿ�2015��12��14�յ�48�β����칫��������ͨ�������蹫������2016��3��1����ʩ�С�����");
		mListtitle.add("���׶�԰������̡��л����񹲺͹����������39��");
		mListtime.add("2016-03-02");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000471");
		
		mListbody.add("Ϊ�᳹��ʵ����ʦ֧�ּƻ���2015��2020�꣩���ƶ����ر������ʦ��ѵģʽ����������ʦ��ѵʵЧ�����ܽ���ؾ�������ϣ��������о��ƶ��ˡ��ͽ�������ѵָ�ϡ�������ʦ����������У������������ѵָ�ϡ�");
		mListtitle.add("�������칫������ӡ������ʦ��ѵָ�ϵ�֪ͨ");
		mListtime.add("2016-02-17 ");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000302");
		
		mListbody.add("Ϊ��֤ѧУ����������������չ���ٽ�ѧ�����ĵĽ����ɳ����ƶ���������");
		mListtitle.add("ѧУ������������");
		mListtime.add("2016-01-08");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000430");
		
		mListbody.add("Ϊ����߽�ʦ���ʣ���ǿ��ʦ���齨�裬���ݡ��л����񹲺͹���ʦ���������¼�ƽ�ʦ�������ƶ���������");
		mListtitle.add("��ʦ�ʸ�����");
		mListtime.add("2016-01-08 ");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000429");
		
		mListbody.add("Ϊ�˼�ǿ�׶�԰�Ĺ����ٽ��׶�������ҵ�ķ�չ���ƶ���������");
		mListtitle.add("�׶�԰��������");
		mListtime.add("2016-01-08");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000428");
		
		mListbody.add("Ϊ����ȡ�ý�ѧ�ɹ��ļ���͸��ˣ��������������ߴ��½�����ѧ�о�����߽�ѧˮƽ�ͽ����������ƶ���������");
		mListtitle.add("��ѧ�ɹ���������");
		mListtime.add("2016-01-08");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000427");
		
		mListbody.add("�����л����񹲺͹���������������¼���������������ʮ�����Ĺ涨���ƶ���ϸ��");
		mListtitle.add("�л����񹲺͹����������ʵʩϸ��");
		mListtime.add("2016-01-08");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000426");
		
		mListbody.add("һ�������ط����Ȱ�������Ȱ�����ӵ���й��������쵼��ӵ��������塣ȫ��᳹���ҽ������룬�Ծ����ؽ������ɷ��棬�������н�ʦְ��Ȩ����������Υ�����͹��ҷ������ߵ����С�");
		mListtitle.add("��Сѧ��ʦְҵ���¹淶");
		mListtime.add("2016-01-08");
		mListweb.add("http://www.zgjsfz.com/index/showNewDetail/10000000425");
		
		mListbody.add("Ϊ�˱��Ͻ�ʦ�ĺϷ�Ȩ�棬�����������˼��Ʒ��������ҵ�����ʵĽ�ʦ���飬�ٽ�������������ҵ�ķ�չ���ƶ������� ");
		mListtitle.add("�л����񹲺͹���ʦ��");
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
        	listView.removeFooterView(moreView3);
            Toast.makeText(getActivity(), "����ȫ��������ɣ�û�и������ݣ�", Toast.LENGTH_LONG).show();
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