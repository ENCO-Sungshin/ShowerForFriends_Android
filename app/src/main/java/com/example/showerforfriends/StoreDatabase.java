package com.example.showerforfriends;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StoreDatabase {
    private static final String TAG = "StoreDatabase";

    private static StoreDatabase database;
    public static String DATABASE_NAME = "store.db";
    public static String TABLE_NOTE = "STORE";
    public static int DATABASE_VERSION = 1;

    private Context context;
    private SQLiteDatabase store_db;
    private DataBaseHelper dbHelper;

    private StoreDatabase(Context context) { this.context = context; }

    public static StoreDatabase getInstance(Context context) {
        if(database == null) {
            database = new StoreDatabase(context);
        }
        return database;
    }

    // list에서 현재 위치 나타내는 커서 역할
    public Cursor rawQuery(String SQL) {
        Cursor c = null;
        try {
            c = store_db.rawQuery(SQL, null);
        } catch (Exception e) {
            Log.e(TAG, "Exception in rawQuery", e);
        }

        return c;
    }

    // sql문 실행
    public boolean execSQL(String SQL) {
        try {
            Log.d(TAG, "SQL : " + SQL);
            store_db.execSQL(SQL);
        } catch (Exception e) {
            Log.e(TAG, "Exception in execSQL", e);
            return false;
        }
        return true;
    }

    public class DataBaseHelper extends SQLiteOpenHelper
    {
        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // 초기화
            String DROP_SQL = "drop table if exists " + TABLE_NOTE;

            try {
                sqLiteDatabase.execSQL(DROP_SQL);
            } catch (Exception e) {
                Log.e(TAG, "Exception in DROP_SQL", e);
            }

            // 생성
            String CREATE_SQL = "create table " + TABLE_NOTE + "("
                    + " store_id integer NOT NULL PRIMARY KEY AUTOINCREMENT, store_name TEXT DEFAULT '',"
                    + " store_info TEXT DEFAULT '', store_uri TEXT DEFAULT '',"
                    + " store_pos1 double DEFAULT '', store_pos2 double DEFAULT '', "
                    + " TODO TEXT DEFAULT '')";

            try {
                sqLiteDatabase.execSQL(CREATE_SQL);
            } catch (Exception e) {
                Log.e(TAG, "Exception in CREATE_TABLE", e);
            }

            // set index in table
            String CREATE_INDEX_SQL = "create index " + TABLE_NOTE + "_INDEX ON " + TABLE_NOTE + "("
                    + "sotre_id" + ")";

            try {
                sqLiteDatabase.execSQL(CREATE_INDEX_SQL);
            } catch (Exception e) {
                Log.e(TAG, "Exception in CREATE_INDEX_SQL", e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    // open database
    public boolean open() {
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        store_db = dbHelper.getWritableDatabase();
        return true;
    }

    // close database
    public void close() {
        store_db.close();
        database = null;
    }
}


