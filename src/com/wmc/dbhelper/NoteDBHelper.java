package com.wmc.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDBHelper extends SQLiteOpenHelper {

	public NoteDBHelper(Context context) {
		super(context, "note.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table note (id integer primary key autoincrement, name varchar(20), time varchar(10), content varchar(100))");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
