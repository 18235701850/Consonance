package com.example.sql;

import java.util.ArrayList;
import java.util.List;

import com.example.sql.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class query {
	private DBHelper dbhelper;
	private SQLiteDatabase db;
	private Context context;
	public query(Context context){
		this.context=context;
		dbhelper=new DBHelper(context);
	}
	
	public List<insert> getAlltrade(){//查询操作
		List<insert> TradeList=new ArrayList<insert>();
		db=dbhelper.getReadableDatabase();
		Cursor cu=db.rawQuery("select * from Content", null);
		while(cu.moveToNext()){
			TradeList.add(new talk(cu.getInt(cu.getColumnIndex("_id")),cu.getString(cu.getColumnIndex("content")),
					cu.getString(cu.getColumnIndex("addTime")),cu.getString(cu.getColumnIndex("nickname")),cu.getString(cu.getColumnIndex("image")),context
					));	
		}
		return TradeList;
	}
	public List<insert> getAllchild(String fa){//查询操作
		List<insert> TradeList=new ArrayList<insert>();
		db=dbhelper.getReadableDatabase();
		Cursor cu=db.rawQuery("select * from childContent where father='"+fa+"'", null);
		while(cu.moveToNext()){
			TradeList.add(new talk(cu.getInt(cu.getColumnIndex("_id")),cu.getString(cu.getColumnIndex("content")),
					cu.getString(cu.getColumnIndex("addTime")),cu.getString(cu.getColumnIndex("nickname")),cu.getString(cu.getColumnIndex("image")),context
					));	
		}
		return TradeList;
	}
}
