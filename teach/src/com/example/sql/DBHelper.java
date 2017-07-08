package com.example.sql;
//创建数据库和表
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper
{
  private static final int VERSION = 1;
  private static String DBNAME="message.db";
  public DBHelper(Context paramContext)
  {
    this(paramContext, DBNAME, VERSION);
  }
  public DBHelper(Context paramContext, String paramString, int paramInt)
  {
    this(paramContext, paramString, null, paramInt);
  }
  public DBHelper(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
  {
    super(paramContext, paramString, paramCursorFactory, paramInt);
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("create table Content(_id INTEGER PRIMARY KEY,nickname varchar(20),addTime TEXT,content varchar(50),image varchar(50))");
    paramSQLiteDatabase.execSQL("create table childContent(_id INTEGER PRIMARY KEY,father varchar(20),nickname varchar(20),addTime TEXT,content varchar(50),image varchar(50))");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
  }
}