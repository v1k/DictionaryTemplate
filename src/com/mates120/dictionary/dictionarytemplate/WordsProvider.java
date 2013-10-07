package com.mates120.dictionary.dictionarytemplate;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class WordsProvider extends ContentProvider {
	
	private final String dictionaryName = "Template Dictionary";
	//Helper constants and the matcher
	
	private static final String AUTHORITY = 
			"com.mates120.dictionary.dictionarytemplate.WordsProvider";
	public static final int WORDS = 100;
	public static final int CREATE_DICTIONARY = 110;
	private static final String WORDS_BASE_PATH = "words";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
	        + "/" + WORDS_BASE_PATH);
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/mt-word";
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
	        + "/mt-word";
	
	private static final UriMatcher sURIMatcher = new UriMatcher(
	        UriMatcher.NO_MATCH);
	static {
	    sURIMatcher.addURI(AUTHORITY, WORDS_BASE_PATH, WORDS);
	    sURIMatcher.addURI(AUTHORITY, WORDS_BASE_PATH + "/create", CREATE_DICTIONARY);
	}
	
	private DatabaseHelper dbHelper;

	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	    queryBuilder.setTables(DatabaseHelper.TABLE_WORDS);
	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case CREATE_DICTIONARY:
	    	MatrixCursor cursor = new MatrixCursor(new String[]{"dictionaryName"});
	    	cursor.addRow(new String[]{dictionaryName});
	    	createDatabaseFromAssets();
	    	return cursor;
	    case WORDS:
	        // no filter
	        break;
	    default:
	        throw new IllegalArgumentException("Unknown URI");
	    }
	    Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
	            projection, selection, selectionArgs, null, null, sortOrder);
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);
	    return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private void createDatabaseFromAssets(){
		dbHelper.getWritableDatabase();
		dbHelper.close();
	}
}
