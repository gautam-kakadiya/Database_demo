package com.example.sysadmin.database_demo;

import android.net.Uri;

/**
 * Created by sysadmin on 7/7/16.
 */
public class ContentProviderContract {
    public static final Uri CONTENT_URI = Uri.parse("content://com.example.sysadmin.database_demo.MyContentProvider/collegues");
    public static final String _ID = "_ID";
    public static final String NAME = "Name";
}
