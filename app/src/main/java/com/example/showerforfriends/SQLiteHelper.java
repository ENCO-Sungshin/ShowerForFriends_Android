package com.example.showerforfriends;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "store.db";
    private static final int DATABASE_VERSION = 1;

    //String storeURI[] = { "https://almang.modoo.at/", "https://dearearth.co.kr/", "https://www.thanksto.co.kr/main/index.php"}; //

    public static final String TABLE_NAME = "storeTable";
    public static final String COLUMN_ID = "store_id";
    public static final String COLUMN_NAME = "store_name";
    public static final String COLUMN_INFO = "store_info";
    public static final String COLUMN_LOCATION = "store_location";
    public static final String COLUMN_URI = "store_uri";
    public static final String COLUMN_POS1 = "store_pos1";
    public static final String COLUMN_POS2 = "store_pos2";
    /*public static final String COLUMN_BOOKMARK = "store_bookmark";*/

    private static final String DATABASE_CREATE_TEAM =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, " + COLUMN_INFO + " TEXT, "
            + COLUMN_LOCATION + " TEXT, " + COLUMN_URI + " TEXT,"
            + COLUMN_POS1 + " DOUBLE, " + COLUMN_POS2 + " DOUBLE)";
            /*+ COLUMN_BOOKMARK + " BOOLEAN )"*/;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(DATABASE_CREATE_TEAM);

        /*Store store_value[] = {new Store(0, "알맹상점", "껍데기는 가라 알맹이만 오라 리필 스테이션 '알맹상점'",
                "서울시 마포구 월드컵로 49 2층", storeURI[0],37.55368, 126.91160, false),
                new Store(1, "디어얼스", "일상에서 지구를 아끼고 사랑하는 편안한 라이프스타일 Dear.earth",
                        "서울시 서대문구 수색로 43 104호", storeURI[1], 37.56979, 126.91335, false),
                new Store(2, "덕분愛", "지구를 향한 우리의 사랑과 노력 덕분에\n생명과 환경을 살리는 브랜드",
                        "서울시 서초구 서초대로 389 진흥상가 209호", storeURI[2], 37.49705, 127.02375, false) };*/

        String INSERT_SQL_1 = "INSERT INTO " + TABLE_NAME + " VALUES (" + 0 + ", '알맹상점', '껍데기는 가라 알맹이만 오라, 리필 스테이션 알맹상점', '서울시 마포구 월드컵로 49 2층', '" + "https://almang.modoo.at/" + "', 37.55368, 126.91160)"; //
        String INSERT_SQL_2 = "INSERT INTO " + TABLE_NAME + " VALUES (" + 1 + ", '디어얼스', '일상에서 지구를 아끼고 사랑하는 편안한 라이프스타일, Dear.earth', '서울시 서대문구 수색로 43 104호', '" + "https://dearearth.co.kr/" + "', 37.56979, 126.91335)"; //
        String INSERT_SQL_3 = "INSERT INTO " + TABLE_NAME + " VALUES (" + 2 + ", '덕분愛', '지구를 향한 우리의 사랑과 노력 덕분에 생명과 환경을 살리는 브랜드', '서울시 서초구 서초대로 389 진흥상가 209호', '" + "https://www.thanksto.co.kr/main/index.php" + "', 37.49705, 127.02375)"; //

        sqLiteDatabase.execSQL(INSERT_SQL_1); //
        sqLiteDatabase.execSQL(INSERT_SQL_2); //
        sqLiteDatabase.execSQL(INSERT_SQL_3); //

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor loadSQLiteDBCursor() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.beginTransaction();
        String selectSQL = "SELECT store_id, store_name, store_info, store_location, store_uri, store_pos1, store_pos2 FROM " + TABLE_NAME;
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(selectSQL, null);
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }

        return cursor;
    }
}
