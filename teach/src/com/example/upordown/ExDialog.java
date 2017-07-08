package com.example.upordown;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.teacher.R;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class ExDialog extends ListActivity implements OnUploadListener{
	private List<Map<String, Object>> mData;
	private String mDir = "/sdcard";
	private String filename=null;
	@Override
	protected void onCreate(Bundle savedInstanaceState) {
		super.onCreate(savedInstanaceState);
		Log.i("test", "ExDialog");
		String title = "open from dir";
		Intent intent=getIntent();
		Uri uri = intent.getData();
		mDir = uri.getPath();
		Log.i("test", "ExDialog mDir:"+mDir);
		setTitle(title);
		mData = getData();
		MyAdapter adapter = new MyAdapter(this);
		setListAdapter(adapter);
		//璁剧疆鏄剧ず鐨勫楂�
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay();
		LayoutParams p = getWindow().getAttributes();
		p.height = (int) (d.getHeight() * 0.8);
		p.width = (int) (d.getWidth() * 0.95);
		getWindow().setAttributes(p);
	
	}
	@SuppressLint("SdCardPath")
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		File f = new File(mDir);
		File[] files = f.listFiles();
		if (!mDir.equals("/sdcard")) {
			map = new HashMap<String, Object>();
			map.put("title", "Back to ../");
			map.put("info", f.getParent());
			map.put("img", R.drawable.ex_folder);
			list.add(map);
		}
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				map = new HashMap<String, Object>();
				map.put("title", files[i].getName());
				map.put("info", files[i].getPath());
				if (files[i].isDirectory())
					map.put("img", R.drawable.ex_folder);
				else
					map.put("img", R.drawable.ex_doc);
				list.add(map);
			}
		}
		return list;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.d("MyListView4-click", (String) mData.get(position).get("info"));
		if ((Integer) mData.get(position).get("img") == R.drawable.ex_folder){
			mDir = (String) mData.get(position).get("info");
			mData = getData();
			MyAdapter adapter = new MyAdapter(this);
			setListAdapter(adapter);
		} 
		else if((Integer) mData.get(position).get("img") == R.drawable.ex_doc){
			Log.v("aa00","杩涘叆瀛愮洰褰�");
			    String name=(String) mData.get(position).get("title");
			    Log.v("aa00",name);
	    		String[] s=name.split("\\.");
	    		filename=s[1];
	    		Log.v("aa11",filename.toString());
	    		if(filename.equals("png")||filename.equals("jpg")){
	    			String path=(String) mData.get(position).get("info");
	    			Log.v("aa33",path);
	    			String filepath=path.substring(0,path.lastIndexOf("/")+1);
	    			Log.v("aa44",filepath);
	    			uploads up=new uploads();
	    			up.upLoad(filepath,name,ExDialog.this);
	    			Toast.makeText(getApplicationContext(), "涓婁紶鍥剧墖瀹屾垚", Toast.LENGTH_SHORT).show();
	    		}
	    		else if(filename.equals("ppt")){
	    			String path=(String) mData.get(position).get("info");
	    			Log.v("aa33",path);
	    			String filepath=path.substring(0,path.lastIndexOf("/")+1);
	    			Log.v("aa44",filepath);
	    			uploadppt up=new uploadppt();
	    			up.upLoad(filepath,name,ExDialog.this);
	    			Toast.makeText(getApplicationContext(), "涓婁紶PPT瀹屾垚", Toast.LENGTH_SHORT).show();
	    		}
	    		else if(filename.equals("MP4")){
	    			String path=(String) mData.get(position).get("info");
	    			Log.v("aa33",path);
	    			String filepath=path.substring(0,path.lastIndexOf("/")+1);
	    			Log.v("aa44",filepath);
	    			uploadvideo up=new uploadvideo();
	    			up.upLoad(filepath,name,ExDialog.this);
	    			Toast.makeText(getApplicationContext(), "涓婁紶瑙嗛瀹屾垚", Toast.LENGTH_SHORT).show();
	    		}
	    		else{
	    			Toast.makeText(getApplicationContext(), "姝ょ被鍨嬫棤娉曚笂浼�", Toast.LENGTH_SHORT).show();
	    		}
	    	}
		else{
			Log.v("aa55","澶辫触");
			finishWithResult((String) mData.get(position).get("info"));
		}
	}
	public final class ViewHolder{
		public ImageView img;
		public TextView title;
		public TextView info;
	}
	public class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}
		public int getCount() {
			return mData.size();
		}
		public Object getItem(int arg0) {
			return null;
		}
		public long getItemId(int arg0) {
			return 0;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.listview, null);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.info = (TextView) convertView.findViewById(R.id.info);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.img.setBackgroundResource((Integer) mData.get(position).get(
					"img"));
			holder.title.setText((String) mData.get(position).get("title"));
			holder.info.setText((String) mData.get(position).get("info"));
			return convertView;
		}
	}

private void finishWithResult(String path) {
	Bundle conData = new Bundle();
	conData.putString("results", "Thanks Thanks");
	Intent intent = new Intent();
	intent.putExtras(conData);
	Uri startDir = Uri.fromFile(new File(path));
	Log.i("test", "ExDoalog startDir:"+startDir);
	intent.setDataAndType(startDir,
			"vnd.android.cursor.dir/lysesoft.andexplorer.file");
			//"application/pdf");
	setResult(RESULT_OK, intent);
	finish();
}
@Override
public void onUpload(double process) {
	
	
}
};

