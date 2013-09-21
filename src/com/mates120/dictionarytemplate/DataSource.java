package com.mates120.dictionarytemplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	
	private String[] allWordsColumns = {DatabaseHelper.COL_WORDS_ID, 
			DatabaseHelper.COL_WORDS_SOURCE, DatabaseHelper.COL_WORDS_VALUE};
	
	public DataSource(Context context){
		dbHelper = new DatabaseHelper(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public long insertWord(String wordSource, String value){
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COL_WORDS_SOURCE, wordSource);
		values.put(DatabaseHelper.COL_WORDS_VALUE, value);
		return database.insert(DatabaseHelper.TABLE_WORDS, null, values);
	}
	
	public void deleteWordById(long wordId){
		System.out.println("Word deleted with id: " + wordId);
		database.delete(DatabaseHelper.TABLE_WORDS, 
				DatabaseHelper.COL_WORDS_ID + " = " + wordId, null);
	}
	
	public Word getWord(String source){
		Word foundWord = null;
		Cursor cursor = database.query(DatabaseHelper.TABLE_WORDS, allWordsColumns, 
				DatabaseHelper.COL_WORDS_SOURCE + " = ?",
				new String[]{source}, null, null, null);
		cursor.moveToFirst();
		foundWord = cursorToWord(cursor);
		cursor.close();
		return foundWord;
	}
	
	private Word cursorToWord(Cursor cursor){
		Word word = null;
		if(cursor != null && cursor.getCount() > 0){
			word = new Word();
			word.setId(cursor.getLong(0));
			word.setSource(cursor.getString(1));
			word.setValue(cursor.getString(2));
		}
		return word;
	}
}
