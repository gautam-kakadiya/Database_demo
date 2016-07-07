package com.example.sysadmin.database_demo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by sysadmin on 7/7/16.
 */
public class MyContentProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MySqLiteHelper sqlhelper;
    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="gomzeedb";
    private static final String TABLE_NAME = "collegues";
    private static final String NAME = "Name";
    private static final String _ID ="_ID";
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                            NAME+" VARCHAR(255))";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME ;
    private SQLiteDatabase db;

    static{
        uriMatcher.addURI("com.example.sysadmin.database_demo.MyContentProvider",TABLE_NAME,1);
        uriMatcher.addURI("com.example.sysadmin.database_demo.MyContentProvider",TABLE_NAME+"/#",2);
    }


    @Override
    public boolean onCreate() {
        sqlhelper = new MySqLiteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch (uriMatcher.match(uri)) {


            // If the incoming URI was for all of table3
            case 1:

                if (TextUtils.isEmpty(sortOrder)) sortOrder = "_ID ASC";
                break;

            // If the incoming URI was for a single row
            case 2:

                /*
                 * Because this URI was for a single row, the _ID value part is
                 * present. Get the last path segment from the URI; this is the _ID value.
                 * Then, append the value to the WHERE clause for the query
                 */
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;

            default:
                Log.d("URI", "query: URI not matched");
                // If the URI is not recognized, you should do some error handling here.
        }
        db = sqlhelper.getWritableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        db= sqlhelper.getWritableDatabase();
        db.insert(TABLE_NAME,null,contentValues);
        Uri uri1 = Uri.parse("content://com.example.sysadmin.database_demo.MyContentProvider/table1/<id_value>");
        return uri1;
    }

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {
        db = sqlhelper.getWritableDatabase();
        return db.delete(TABLE_NAME,whereClause,whereArgs);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String whereClause, String[] whereArgs) {
        return db.update(TABLE_NAME,contentValues,whereClause,whereArgs);
    }

    protected static final class MySqLiteHelper extends SQLiteOpenHelper {

        public MySqLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
            Log.d("sqlitehelper", "onCreate: ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
            Log.d("sqlitehelper", "onUpgrade: ");
        }
    }
}
