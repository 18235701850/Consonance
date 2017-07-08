package com.example.sql;


import com.example.sql.DBHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public abstract class insert {
	private int id;
	private String content;
	private String time;
	private String nickname;
	private String fathername;
	private DBHelper dbhelper;
	private SQLiteDatabase db;
	private String tablename;
	private String image;
	
	public int getId() {
		return id;
	}
	public String getFathername() {
		return fathername;
	}
	public void setFathername(String fathername) {
		this.fathername = fathername;
	}
	public void settablename(String tablename)
	{
	    this.tablename = tablename;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public insert(int id, String content, String time, String nickname,
			 String image,Context context) {
	
		this.id = id;
		this.content = content;
		this.time = time;
		this.nickname = nickname;
		this.image = image;
		dbhelper=new DBHelper(context);
	}

	public void trade_add(){
	    db=dbhelper.getWritableDatabase();
	    try
	    {
	      ContentValues localContentValues = new ContentValues();//存储机制，存储基本数据类型
	      localContentValues.put("image", this.image);
	      localContentValues.put("addTime", this.time);
	      localContentValues.put("nickname", this.nickname);
	      localContentValues.put("content", this.content);
	      db.insert(tablename, null, localContentValues);//数据添加
	      db.close();
	      return;
	    }
	    catch (Exception localException)
	    {
	    	Log.v("id", "add consume error");
	    }
	}
	
}
