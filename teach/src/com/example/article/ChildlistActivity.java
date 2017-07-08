package com.example.article;

import java.util.ArrayList;
import java.util.List;

import com.example.homepage.FeedbackActivity;
import com.example.homepage.Utils;
import com.example.teacher.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ChildlistActivity extends Activity
{
    private ExpandableListView expandableListView;
    
    private List<String> group_list,group_list2;
    private List<Integer> group_list3;
    private List<String> item_lt,item_lt2,item_lt3;
    private List<List<String>> item_list,item_list2,item_list3;
    private Intent intent;
    private MyExpandableListViewAdapter adapter;
    private TextView headline;
    private ImageView image;
 Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articleitem);
        headline=(TextView)findViewById(R.id.headline);
        headline.setText("回复");

        intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        String name=bundle.getString("name");
        String body=bundle.getString("body");
      //  String img=bundle.getString("img");
       // byte[] bitmapArray;
		//bitmapArray = Base64.decode(img, Base64.DEFAULT);
		//bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
		//bitmap=Utils.toRoundBit(bitmap);
		//image.setImageBitmap(bitmap);
        Button btn = (Button)findViewById(R.id.btnsend);
        btn.setOnClickListener(new OnClickListener(){
      	   @Override
      	   public void onClick(View v){
      		 EditText text = (EditText)findViewById(R.id.sends);
      		String content=text.getText().toString().trim();
      		 if(TextUtils.isEmpty(content)){
      			 return;
      		 }
      		 else{
         	 text.setText("");
             item_lt.add("冬天233333333");
             item_list.add(item_lt);
             item_lt2.add("春天");
             item_list2.add(item_lt2);  
             item_lt3.add(content); 
             item_list3.add(item_lt3);
      		 adapter.notifyDataSetChanged();
      		 }
      	    }
         });  
        
       
        group_list = new ArrayList<String>();
        group_list.add(name);
        group_list2 = new ArrayList<String>();
        group_list2.add(body);
        group_list3 = new ArrayList<Integer>();
        group_list3.add(R.drawable.personal_image);
        
        item_lt = new ArrayList<String>();
        item_lt.add("夏天");
        item_lt.add("秋天");
        item_lt.add("冬天");
        item_list = new ArrayList<List<String>>();
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        
        item_lt2= new ArrayList<String>();
        item_lt2.add("春天");
        item_lt2.add("春天");
        item_lt2.add("春天");
        item_list2 = new ArrayList<List<String>>();
        item_list2.add(item_lt2);
        item_list2.add(item_lt2);
        item_list2.add(item_lt2);
        item_list2.add(item_lt2);
        item_lt3 = new ArrayList<String>();
        item_lt3.add("你是班主任吗？你问问你周围的老师。让他们帮帮你");
        item_lt3.add("他们是有点欺软怕硬");
        item_lt3.add("办法嘛，我觉得要么凶一点，要么课精彩一点"); 
        item_list3 = new ArrayList<List<String>>();
        item_list3.add(item_lt3);
        item_list3.add(item_lt3);
        item_list3.add(item_lt3);
        item_list3.add(item_lt3);
        expandableListView = (ExpandableListView)findViewById(R.id.expendlist);
        expandableListView.setGroupIndicator(null);


        // 监听组点击
        expandableListView.setOnGroupClickListener(new OnGroupClickListener()
        {
            @SuppressLint("NewApi")
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {
                if (item_list.get(groupPosition).isEmpty())
                {
                    return true;
                }
                return false;
            }
        });
 
        // 监听每个分组里子控件的点击事件
        expandableListView.setOnChildClickListener(new OnChildClickListener()
        {
 
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
 
                return false;
            }
        });

        ImageButton btn_back=(ImageButton)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener(){
				@Override
	     	   public void onClick(View v){
					ChildlistActivity.this.finish();
	     	   }
	        });
        adapter = new MyExpandableListViewAdapter(this);
 
        expandableListView.setAdapter(adapter);
    }
    class MyExpandableListViewAdapter extends BaseExpandableListAdapter
    {
 
        private Context context;
 
        public MyExpandableListViewAdapter(Context context)
        {
            this.context = context;
        }
        @Override
        public int getGroupCount()
        {
            return group_list.size();
        }
        @Override
        public int getChildrenCount(int groupPosition)
        {
            return item_list.get(groupPosition).size();
        }
        @Override
        public Object getGroup(int groupPosition)
        {
            return group_list.get(groupPosition);
        }
        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return item_list.get(groupPosition).get(childPosition);
        }
        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }
        @Override
        public boolean hasStableIds()
        {
            return true;
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
        {
            GroupHolder groupHolder = null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.childlistitem, null);
                groupHolder = new GroupHolder();
                groupHolder.txt = (TextView)convertView.findViewById(R.id.txtname);
                groupHolder.txt2= (TextView)convertView.findViewById(R.id.txtbody);
                groupHolder.img = (ImageView)convertView.findViewById(R.id.img);
                
                convertView.setTag(groupHolder);             
            }
            else
            {
                groupHolder = (GroupHolder)convertView.getTag();
            }
            groupHolder.txt.setText(group_list.get(groupPosition));
            groupHolder.txt2.setText(group_list2.get(groupPosition));
            groupHolder.img.setBackgroundResource(group_list3.get(groupPosition));
            return convertView;
        }
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
        {
            ItemHolder itemHolder = null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.child, null);
                itemHolder = new ItemHolder();
                itemHolder.txt = (TextView)convertView.findViewById(R.id.txt);
                itemHolder.txt2= (TextView)convertView.findViewById(R.id.txt2);
                itemHolder.txt3= (TextView)convertView.findViewById(R.id.txt3);
                convertView.setTag(itemHolder);
            }
            else
            {
                itemHolder = (ItemHolder)convertView.getTag();
            }
            itemHolder.txt.setText(item_list.get(groupPosition).get(childPosition));
            itemHolder.txt2.setText(item_list2.get(groupPosition).get(childPosition));
            itemHolder.txt3.setText(item_list3.get(groupPosition).get(childPosition));
            return convertView;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }
 
    }
 
    class GroupHolder
    {
        public TextView txt;
        public TextView txt2;
        public ImageView img;
        public Button btn;
    }
 
    class ItemHolder
    {
        public TextView txt;
        public TextView txt2;
        public TextView txt3;
    }
 
}