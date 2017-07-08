package com.example.article;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import com.example.homepage.Utils;
import com.example.sql.insert;
import com.example.sql.query;
import com.example.sql.talk;
import com.example.teacher.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
public class ArticleitemActivity extends Activity
{	
	private ExpandableListView expandableListView;
	 Bitmap bitmap;
	 Bitmap bit;
	 private SharedPreferences sp;
    private List<String> group_list,group_list2;
    private List<Bitmap> group_list3;
    private EditText addDate;
    private List<List<String>> item_list,item_list2,item_list3;
    ImageView image;
    private int mYear,mMonth,mDay;
    Drawable d;
    private MyExpandableListViewAdapter adapter;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_articleitem);
        Resources res=ArticleitemActivity.this.getResources();
		bitmap=BitmapFactory.decodeResource(res, R.drawable.personal_image);
        Button btn = (Button)findViewById(R.id.btnsend);
        btn.setOnClickListener(new OnClickListener(){
      	   @Override
      	   public void onClick(View v){
      		 EditText text = (EditText)findViewById(R.id.sends);
         	 String content=text.getText().toString().trim();
         	 text.setText("");
         	
         	sp=getSharedPreferences("test",MODE_PRIVATE);
         	String name=sp.getString("name","");
       	   group_list.add(name);
           group_list2.add(content);
   		 String img=sp.getString("image",null);
   		 if(img!=null){
   				byte[] bitmapArray;
   				bitmapArray = Base64.decode(img, Base64.DEFAULT);
   				bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
   				bitmap=Utils.toRoundBit(bitmap);
   				Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
   				
   		 }
   		    
            group_list3.add(bitmap); 
             Calendar c = Calendar.getInstance();
             mYear = c.get(Calendar.YEAR);
             mMonth = c.get(Calendar.MONTH)+1;
             mDay = c.get(Calendar.DAY_OF_MONTH);
             String a=(mYear + "-" + (mMonth+1) + "-" + mDay);
      		 adapter.notifyDataSetChanged();
      		talk trade=new talk(0, content, a, name, img,ArticleitemActivity.this);
 	        trade.trade_add();
 	        Toast.makeText(ArticleitemActivity.this, "添加完成", 0).show();
      	    }
         });  
        
        final ImageButton imgbtn=(ImageButton)findViewById(R.id.imgbtn1);
        imgbtn.setOnClickListener(new OnClickListener(){
    			
    			@Override
    			public void onClick(View v) {
    				if(i==0){
    					imgbtn.setBackgroundResource(R.drawable.star2);
    					i=1;
    				}else {
    					imgbtn.setBackgroundResource(R.drawable.star);
    					i=0;
    				}
    			}
    		});
  
        query pack=new query(this);
		List<insert> List=pack.getAlltrade();
        group_list = new ArrayList<String>();
        group_list2 = new ArrayList<String>();
        group_list3 = new ArrayList<Bitmap>();
        for(insert con:List){
        	 group_list.add(con.getNickname());
        	 group_list2.add(con.getContent());
        	 String img=con.getImage();
        	 if(img!=null){
     			byte[] bitmapArray;
     			bitmapArray = Base64.decode(img, Base64.DEFAULT);
     			bit = BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
     			bit=Utils.toRoundBit(bit);
     		}
           group_list3.add(bit);
          
          
        
        }
                
        expandableListView = (ExpandableListView)findViewById(R.id.expendlist);
        expandableListView.addHeaderView(View.inflate(this, R.layout.artivle, null));
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
 
        adapter = new MyExpandableListViewAdapter(this);
        ImageButton btn_back=(ImageButton)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener(){
				@Override
	     	   public void onClick(View v){
					ArticleitemActivity.this.finish();
	     	   }
	        });
 
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
                convertView = LayoutInflater.from(context).inflate(R.layout.group, null);
                groupHolder = new GroupHolder();
                groupHolder.txtname = (TextView)convertView.findViewById(R.id.txtname);
                groupHolder.txtbody= (TextView)convertView.findViewById(R.id.txtbody);
                groupHolder.img = (ImageView)convertView.findViewById(R.id.img);
                
                groupHolder.mytime= (TextView)convertView.findViewById(R.id.texttime);
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH)+1;
                mDay = c.get(Calendar.DAY_OF_MONTH);
                groupHolder.mytime.setText(new StringBuilder().append(mYear).append("-")
                .append(mMonth).append("-")
                .append(mDay).append(" "));
               
                convertView.setTag(groupHolder);
                
            }
            else
            {
                groupHolder = (GroupHolder)convertView.getTag();
            }
            groupHolder.txtname.setText(group_list.get(groupPosition));
            groupHolder.txtbody.setText(group_list2.get(groupPosition));
            groupHolder.img.setImageBitmap(group_list3.get(groupPosition));
            final String body= groupHolder.txtbody.getText().toString();
            final String name= groupHolder.txtname.getText().toString();
           // ByteArrayOutputStream stream = new ByteArrayOutputStream();
			//bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
			//byte[] b = stream.toByteArray();
			// 将图片流以字符串形式存储下来
			 //final String tp = Base64.encodeToString(b,Base64.DEFAULT);
            groupHolder.btn = (Button)convertView.findViewById(R.id.Button1);
            groupHolder.btn.setOnClickListener(new OnClickListener(){
          	   
          	   @Override
          	   public void onClick(View v){
          		   Toast.makeText(ArticleitemActivity.this,body,Toast.LENGTH_LONG).show();
          		   Intent intent=new Intent(ArticleitemActivity.this,ChildlistActivity.class);
          		   Bundle bundle=new Bundle();
          		   bundle.putString("body", body);
          	   	   bundle.putString("name", name);
          	   	   bundle.putString("title","aaaaaaaaa");
          		//   bundle.putString("img", tp);
          		   intent.putExtras(bundle);
          		   overridePendingTransition(R.drawable.fadein,R.drawable.fadeout);
          		   startActivity(intent);
          	   }
             });  
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
        public TextView txtname;
        public TextView txtbody;
        public ImageView img;
        public Button btn;
        public TextView mytime;
    }
 
    class ItemHolder
    {
        public TextView txt;
        public TextView txt2;
        public TextView txt3;
    }
}