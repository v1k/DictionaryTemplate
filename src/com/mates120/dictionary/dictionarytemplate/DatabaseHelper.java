package com.mates120.dictionary.dictionarytemplate;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper
{	
	public static final String TABLE_WORDS = "words_table";
	public static final String COL_WORDS_ID = "_id";
	public static final String COL_WORDS_SOURCE = "source";
	public static final String COL_WORDS_VALUE = "value";	
	private static final String DATABASE_NAME = "dict0";
	private static final int DATABASE_VERSION = 1;	
	
	public DatabaseHelper(Context context)
	{
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
}
