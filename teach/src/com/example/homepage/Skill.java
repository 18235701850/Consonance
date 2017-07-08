package com.example.homepage;

import java.util.ArrayList;
import java.util.List;

import com.example.teacher.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
 
public class Skill extends Activity{
    private ExpandableListView expandableListView;
    
    private List<String> group_list;
    private List<String> item_lt;
    private List<List<String>> item_list;
    
    private MyExpandableListViewAdapter adapter;
 
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_skill);
        
        group_list = new ArrayList<String>();
        group_list.add("一、导入技能");
        group_list.add("二、讲授技能");
        group_list.add("三、提问技能");
        group_list.add("四、板书技能");
        group_list.add("五、演示技能");
        group_list.add("六、反馈和强化技能");
        group_list.add("七、教态语言变化技能");
        group_list.add("八、课堂组织技能");
        group_list.add("九、结束技能");
        group_list.add("十、教学评价技能");
        
        item_lt = new ArrayList<String>();
        item_lt.add("1、概念");
        item_lt.add("2、目的");
        item_lt.add("3、类型");
        item_list = new ArrayList<List<String>>();
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        
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
            	Intent intent=new Intent(Skill.this,SkillgoActivity.class);
        		Bundle bundle=new Bundle();
        	    bundle.putInt("a",groupPosition);
        	    bundle.putInt("b",childPosition);
        	    intent.putExtras(bundle);
        		startActivity(intent); 
        		overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
        		 
            	return false;
            }
        });
 
        ImageButton btn_back=(ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new OnClickListener(){
			@Override
     	   public void onClick(View v){
     		  Skill.this.finish();
     		 overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
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
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
        {
            GroupHolder groupHolder = null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.skill_item, null);
                groupHolder = new GroupHolder();
                groupHolder.txt = (TextView)convertView.findViewById(R.id.title);
                groupHolder.btn = (Button)convertView.findViewById(R.id.btn);
                groupHolder.btn.setOnClickListener(new OnClickListener(){
               	   
               	   @Override
               	   public void onClick(View v){
               		Intent intent=new Intent(Skill.this,Skillgo2Activity.class);
            		Bundle bundle=new Bundle();
            	    bundle.putInt("c",groupPosition);
            	    intent.putExtras(bundle);
            		startActivity(intent); 
//            		overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);               	  
            		}
                  });   
                
                convertView.setTag(groupHolder);       
            }
            else
            {
                groupHolder = (GroupHolder)convertView.getTag();
            }
 
            groupHolder.txt.setText(group_list.get(groupPosition));
            return convertView;
        }
 
       
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
        {
            ItemHolder itemHolder = null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.skillitem, null);
                itemHolder = new ItemHolder();
                itemHolder.txt = (TextView)convertView.findViewById(R.id.txt);
                
                convertView.setTag(itemHolder);
            }
            else
            {
                itemHolder = (ItemHolder)convertView.getTag();
            }
            itemHolder.txt.setText(item_list.get(groupPosition).get(childPosition));
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
        public Button btn;
    }
 
    class ItemHolder
    {
        public TextView txt;
    }
 
}