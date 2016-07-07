package com.example.sysadmin.database_demo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleCursorAdapter mCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentValues cv = new ContentValues();
        Uri mnewUri;
        cv.put(ContentProviderContract.NAME,"Gautam");
        mnewUri = getContentResolver().insert(ContentProviderContract.CONTENT_URI,cv);

        listView = (ListView) findViewById(R.id.list_view);

        String[] mProjection = {
                ContentProviderContract._ID,
                ContentProviderContract.NAME
        };
        String mSelection = null;

        String[] mSelectionArgs = null;

        Cursor mCursor;
        mCursor = getContentResolver().query(ContentProviderContract.CONTENT_URI,
                mProjection,mSelection,mSelectionArgs,null);

        if(mCursor==null){
            Log.d("mm1cursor null", "onCreate: ");
        }else if(mCursor.getCount()<1){
            Log.d("mm1cursor empty","onCreate: ");
        }else{
            Log.d("mm1cursor good", "onCreate: ");
        }

        Log.d("Till exec querry", "onCreate: ");
        int i=0;
        while(mCursor.moveToNext()){
            Log.d("dataofcursor", mCursor.getString(1) + mCursor.getInt(0));
            ++i;
        }

        String[] mWordListColumns =
                {
                        ContentProviderContract.NAME
                };

// Defines a list of View IDs that will receive the Cursor columns for each row
        int[] mWordListItems = { R.id.tv};

// Creates a new SimpleCursorAdapter
        mCursorAdapter = new SimpleCursorAdapter(
                getApplicationContext(),               // The application's Context object
                R.layout.list_item,                  // A layout in XML for one row in the ListView
                mCursor,                               // The result from the query
                mWordListColumns,                      // A string array of column names in the cursor
                mWordListItems,                        // An integer array of view IDs in the row layout
                0);                                    // Flags (usually none are needed)
        Log.d("Till setting addapter", "onCreate: ");
// Sets the adapter for the ListView
        listView.setAdapter(mCursorAdapter);
    }
}
