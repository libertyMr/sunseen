package com.sunseen.spcontrolsystem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sunseen.spcontrolsystem.utils.LogMsg;

public class DatabaseHelper extends SQLiteOpenHelper {
	private final static int VERSION = 2;
	private final static String DB_NAME = "control.db";

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	
	public DatabaseHelper(Context context, String name, int version){  
        this(context,name,null,version);  
    }
  
    public DatabaseHelper(Context context){  
        this(context,DB_NAME,VERSION);
    }
	@Override
	public void onCreate(SQLiteDatabase db) {
		LogMsg.printDebugMsg("DatabaseHelper onCreate");
		String sql = "create table userInfo("
				+ "user_name text," 	//用户名
				+ "pwd text,"
                + "user_id text PRIMARY KEY,"
                + "last_login text,"	//最近登入时间
                + "power int);";		//权限
		db.execSQL(sql);

		sql = "create table systemConfig("
				+ "name text primary key,"//属性名称
				+ "value text);";//属性值
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		LogMsg.printDebugMsg("DatabaseHelper onUpgrade");
	}

}
