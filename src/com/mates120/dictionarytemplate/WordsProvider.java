package com.mates120.dictionarytemplate;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class WordsProvider extends ContentProvider {
	
	//Helper constants and the matcher
	
	private static final String AUTHORITY = 
			"com.mates120.dictionarytemplate.data.WordsProvoder";
	public static final int WORDS = 100;
	public static final int WORD_ID = 110;
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
	    sURIMatcher.addURI(AUTHORITY, WORDS_BASE_PATH + "/#", WORD_ID);
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
	    case WORD_ID:
	        queryBuilder.appendWhere(DatabaseHelper.COL_WORDS_ID + "="
	                + uri.getLastPathSegment());
	        break;
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
}
