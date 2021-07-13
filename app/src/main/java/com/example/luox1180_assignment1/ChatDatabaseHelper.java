package com.example.luox1180_assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    protected static final String ACTIVITY_NAME = "ChatDatabaseHelper";
    private static final String DATABASE_NAME = "items.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "KEY_ID";
    public static final String COLUMN_ITEM = "KEY_MESSAGE";

    ContentValues Cvalues = new ContentValues();
    private static final String DATABASE_CREATE= "create table "
            + TABLE_ITEMS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_ITEM
            + " text not null);";

    //private static final String DATA_INIT = "insert into "+TABLE_ITEMS + " ("+COLUMN_ITEM+") "+"VALUES ('TEST MESSAGE')";

    public ChatDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        //insertMsg("Test Message",db);
        Log.i(ACTIVITY_NAME, "Calling onCreate()");
    }

    public long insertMsg(String msg,SQLiteDatabase db){
        Cvalues.put("KEY_MESSAGE",msg);
        long insertId = db.insert(TABLE_ITEMS, "NullPlaceholder",Cvalues);
        return  insertId;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ChatDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade()");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ChatDatabaseHelper.class.getName(),
                "Downgrading database from version " +  newVersion + " to "
                        + oldVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);

    }
}
