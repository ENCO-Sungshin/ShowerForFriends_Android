package com.example.showerforfriends;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteTableLockedException;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.SQLInput;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreListFragment extends Fragment {

    //private static final String TAG = "StoreListFragment";
    private static final String TAG = "StoreDataList";
    ViewGroup viewGroup;
    Context context;
    public static SQLiteHelper sqLiteHelper;
    public static SQLiteDatabase storeDatabase;
    //private ArrayList storeArray;
    private ArrayList<Store> storeArrayList = new ArrayList<>();
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager layoutManager;
    TextView storeName, storeInfo, storeLocation, storeBookmark;
    ImageView storePicture;
    Button storeURL, storeMap;
    RecyclerView recyclerView;

    /*//Fragment storeFragment;//
    Button storeURI;
    private RecyclerView recyclerView;
    //private Store[] storeListItems = new Store[3];
    private StoreListItem[] storeListItems;
    private StoreListItemAdapter adapter;
    StoreDatabase storeDatabase = null; //
   // private StoreAdapter adapter;
    //public static StoreDatabase storeDatabase = null;
    private ArrayList<StoreListItem> storeListItemArrayList = new ArrayList<>();
    Context context;*/

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StoreListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreListFragment newInstance(String param1, String param2) {
        StoreListFragment fragment = new StoreListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_store_list, container, false);
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.storeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new EventViewDecoration(40));


        // 여기서 초기화하고 계속 새로운 거에 넣어주니까 원래 값을 기억못하는 게 아닐까?
        storeArrayList.clear(); // 초기화
        sqLiteHelper = new SQLiteHelper(context);
        storeDatabase = sqLiteHelper.getReadableDatabase();
        storeDatabase.beginTransaction();
        initialInsertStore();
        Cursor cursor = sqLiteHelper.loadSQLiteDBCursor();

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                addGroupItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5), cursor.getDouble(6));
                /*Log.i("Cursor Bookmark : ", cursor.getString(1) + " " + cursor.getString(7));*/
                cursor.moveToNext();
            }
            /*String storeURI[] = { "https://almang.modoo.at/", "https://dearearth.co.kr/", "https://www.thanksto.co.kr/main/index.php"};

            Store store_value[] = {new Store(0, "알맹상점", "껍데기는 가라 알맹이만 오라 리필 스테이션 '알맹상점'",
                    "서울시 마포구 월드컵로 49 2층", storeURI[0],37.55368, 126.91160, false),
                    new Store(1, "디어얼스", "일상에서 지구를 아끼고 사랑하는 편안한 라이프스타일 Dear.earth",
                    "서울시 서대문구 수색로 43 104호", storeURI[1], 37.56979, 126.91335, false),
                    new Store(2, "덕분愛", "지구를 향한 우리의 사랑과 노력 덕분에\n생명과 환경을 살리는 브랜드",
                            "서울시 서초구 서초대로 389 진흥상가 209호", storeURI[2], 37.49705, 127.02375, false) };*/

        /*// recyclerView
        storeListItems[0] = new Store(0, "알맹상점", "껍데기는 가라 알맹이만 오라 리필 스테이션 '알맹상점'",
                "서울시 마포구 월드컵로 49 2층", storeURI[0],37.55368, 126.91160, false);
        storeListItems[1] = new Store(1, "디어얼스", "일상에서 지구를 아끼고 사랑하는 편안한 라이프스타일 Dear.earth",
                "서울시 서대문구 수색로 43 104호", storeURI[1], 37.56979, 126.91335, false);
        storeListItems[2] = new Store(2, "덕분愛", "지구를 향한 우리의 사랑과 노력 덕분에\n생명과 환경을 살리는 브랜드",
                "서울시 서초구 서초대로 389 진흥상가 209호", storeURI[2], 37.49705, 127.02375, false);*/

            /*for(int i=0; i<store_value.length; i++)
                storeArrayList.add(store_value[i]);*/

        } catch (Exception e) { e.printStackTrace(); }
        finally {
            if(cursor != null) {
                cursor.close();
                storeDatabase.endTransaction();
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAdapter = new StoreListItemAdapter(getContext(), storeArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        //sqLiteHelper = new SQLiteHelper(getContext());

        //storeDatabase = sqLiteHelper.getWritableDatabase();
        // STORE DB CREATE AND OPEN
        //initialInsertStore();

        //CombiningCursorAdapter adapter = new CombiningCursorAdapter(getContext(), readData());
        //recyclerView.setAdapter(adapter);

        /*storeFragment = new StoreListSubFragment(); //
        //getSupportFragmentManager().beginTransaction().replace(R.id.storeList, storeFragment).commit;
        storeURI = (Button) viewGroup.findViewById(R.id.storeURI);
        //recyclerView = (RecyclerView) viewGroup.findViewById(R.id.storeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StoreListItemAdapter(context, storeListItemArrayList);
       // adapter = new StoreAdapter();
        recyclerView.addItemDecoration(new EventViewDecoration(40));
        storeListItems = new StoreListItem[3];
        *//*openDatabase();*//*
        //storeStoreList();
        //loadStoreList();

        String storeURI[] = { "https://almang.modoo.at/", "https://dearearth.co.kr/", "https://www.thanksto.co.kr/main/index.php"};

        // recyclerView
        storeListItems[0] = new StoreListItem("알맹상점", "껍데기는 가라 알맹이만 오라 리필 스테이션 '알맹상점'",
                "서울시 마포구 월드컵로 49 2층", storeURI[0], R.drawable.store1, false, 37.55368, 126.91160);
        storeListItems[1] = new StoreListItem("디어얼스", "일상에서 지구를 아끼고 사랑하는 편안한 라이프스타일 Dear.earth",
                "서울시 서대문구 수색로 43 104호", storeURI[1], R.drawable.store2, false, 37.56979, 126.91335);
        storeListItems[2] = new StoreListItem("덕분愛", "지구를 향한 우리의 사랑과 노력 덕분에\n생명과 환경을 살리는 브랜드",
                "서울시 서초구 서초대로 389 진흥상가 209호", storeURI[2], R.drawable.store1, false, 37.49705, 127.02375);

        for(int i=0; i<storeListItems.length; i++)
        {
            storeListItemArrayList.add(storeListItems[i]);
        }
        recyclerView.setAdapter(adapter);*/

        // Inflate the layout for this fragment
        return viewGroup;
    }

    public void addGroupItem(int store_id, String store_name, String store_info, String store_location, String store_uri, double store_pos1, double store_pos2){
        Store item = new Store(store_id, store_name, store_info, store_location, store_uri, store_pos1, store_pos2);
        storeArrayList.add(item);
    }

    private void initialInsertStore() {
        Cursor c = readData();
        int cursor_count = c.getCount();
        c.close();

        if(cursor_count != 0) return;

        ContentValues store_value = new ContentValues();


        String storeURI[] = { "https://almang.modoo.at/", "https://dearearth.co.kr/", "https://www.thanksto.co.kr/main/index.php"};

        /*// recyclerView
        storeListItems[0] = new Store(0, "알맹상점", "껍데기는 가라 알맹이만 오라 리필 스테이션 '알맹상점'",
                "서울시 마포구 월드컵로 49 2층", storeURI[0],37.55368, 126.91160, false);
        storeListItems[1] = new Store(1, "디어얼스", "일상에서 지구를 아끼고 사랑하는 편안한 라이프스타일 Dear.earth",
                "서울시 서대문구 수색로 43 104호", storeURI[1], 37.56979, 126.91335, false);
        storeListItems[2] = new Store(2, "덕분愛", "지구를 향한 우리의 사랑과 노력 덕분에\n생명과 환경을 살리는 브랜드",
                "서울시 서초구 서초대로 389 진흥상가 209호", storeURI[2], 37.49705, 127.02375, false);*/

        // 알맹상점
        store_value.put(SQLiteHelper.COLUMN_ID, 0);
        store_value.put(SQLiteHelper.COLUMN_NAME, "알맹상점");
        store_value.put(SQLiteHelper.COLUMN_INFO, "껍데기는 가라 알맹이만 오라 리필 스테이션 '알맹상점'");
        store_value.put(SQLiteHelper.COLUMN_LOCATION, "서울시 마포구 월드컵로 49 2층");
        store_value.put(SQLiteHelper.COLUMN_URI, storeURI[0]);
        store_value.put(SQLiteHelper.COLUMN_POS1, "37.55368");
        store_value.put(SQLiteHelper.COLUMN_POS2, "126.91160");
        /*store_value.put(SQLiteHelper.COLUMN_BOOKMARK, "false");*/
        storeDatabase.insert(SQLiteHelper.TABLE_NAME, null, store_value);

        // 디어얼스
        store_value.put(SQLiteHelper.COLUMN_ID, 1);
        store_value.put(SQLiteHelper.COLUMN_NAME, "디어얼스");
        store_value.put(SQLiteHelper.COLUMN_INFO, "일상에서 지구를 아끼고 사랑하는 편안한 라이프스타일 Dear.earth");
        store_value.put(SQLiteHelper.COLUMN_LOCATION, "서울시 서대문구 수색로 43 104호");
        store_value.put(SQLiteHelper.COLUMN_URI, storeURI[1]);
        store_value.put(SQLiteHelper.COLUMN_POS1, "37.56979");
        store_value.put(SQLiteHelper.COLUMN_POS2, "126.91335");
        /*store_value.put(SQLiteHelper.COLUMN_BOOKMARK, "false");*/
        storeDatabase.insert(SQLiteHelper.TABLE_NAME, null, store_value);

        // 덕분愛
        store_value.put(SQLiteHelper.COLUMN_ID, 2);
        store_value.put(SQLiteHelper.COLUMN_NAME, "덕분愛");
        store_value.put(SQLiteHelper.COLUMN_INFO, "지구를 향한 우리의 사랑과 노력 덕분에\n생명과 환경을 살리는 브랜드");
        store_value.put(SQLiteHelper.COLUMN_LOCATION, "서울시 서초구 서초대로 389 진흥상가 209호");
        store_value.put(SQLiteHelper.COLUMN_URI, storeURI[2]);
        store_value.put(SQLiteHelper.COLUMN_POS1, "37.49705");
        store_value.put(SQLiteHelper.COLUMN_POS2, "127.02375");
        /*store_value.put(SQLiteHelper.COLUMN_BOOKMARK, "false");*/
        storeDatabase.insert(SQLiteHelper.TABLE_NAME, null, store_value);
    }

    public Cursor readData() {
        String[] store_data = {
                SQLiteHelper.COLUMN_ID,
                SQLiteHelper.COLUMN_NAME,
                SQLiteHelper.COLUMN_INFO,
                SQLiteHelper.COLUMN_LOCATION,
                SQLiteHelper.COLUMN_URI,
                SQLiteHelper.COLUMN_POS1,
                SQLiteHelper.COLUMN_POS2/*,
                SQLiteHelper.COLUMN_BOOKMARK*/
        };
        String sorting = SQLiteHelper.COLUMN_ID + " desc";

        Cursor cursor = storeDatabase.query(SQLiteHelper.TABLE_NAME, store_data, null, null, null, null, sorting);

        return cursor;
    }


    /*public void openDatabase() {
        // open database
        if (storeDatabase != null) {
            storeDatabase.close();
            storeDatabase = null;
        }

        storeDatabase = StoreDatabase.getInstance(getContext());
        boolean isOpen = storeDatabase.open();
        if (isOpen) {
            Log.d(TAG, "Note database is open.");
        } else {
            Log.d(TAG, "Note database is not open.");
        }
    }

    public void onDestroy() {
        super.onDestroy();

        if (storeDatabase != null) {
            storeDatabase.close();
            storeDatabase = null;
        }
    }

    public void storeStoreList() {
        String storeURI[] = { "https://almang.modoo.at/", "https://dearearth.co.kr/", "https://www.thanksto.co.kr/main/index.php"};

        // recyclerView
        storeListItems[0] = new Store(0, "알맹상점", "껍데기는 가라 알맹이만 오라 리필 스테이션 '알맹상점'",
                "서울시 마포구 월드컵로 49 2층", storeURI[0],37.55368, 126.91160, false);
        storeListItems[1] = new Store(1, "디어얼스", "일상에서 지구를 아끼고 사랑하는 편안한 라이프스타일 Dear.earth",
                "서울시 서대문구 수색로 43 104호", storeURI[1], 37.56979, 126.91335, false);
        storeListItems[2] = new Store(2, "덕분愛", "지구를 향한 우리의 사랑과 노력 덕분에\n생명과 환경을 살리는 브랜드",
                "서울시 서초구 서초대로 389 진흥상가 209호", storeURI[2], 37.49705, 127.02375, false);

        for(int i=0; i<storeListItems.length; i++)
        {
            String saveSQL = "insert into " + StoreDatabase.TABLE_NOTE
                    + " (store_id) values ('" + storeListItems[i].getStore_id() + "'), "
                    + "(store_name) values ('" + storeListItems[i].getStore_name() + "'), "
                    + "(store_info) values ('" + storeListItems[i].getStore_info() + "'), "
                    + "(store_location) values ('" + storeListItems[i].getStore_loaciton() + "'), "
                    + "(store_uri) values ('" + storeListItems[i].getStore_uri() + "'), "
                    + "(store_pos1) values ('" + storeListItems[i].getStore_pos1() + "'), "
                    + "(store_pos2) values ('" + storeListItems[i].getStore_pos2() + "'), "
                    + "(store_bookmark) values ('" + storeListItems[i].getStore_bookmark() + "')";

            storeDatabase.execSQL(saveSQL);
        }
    }

    public int loadStoreList() {
        String loadSQL = "SELECT store_id, store_name, store_info, store_location, store_uri, store_pos1, store_pos2, store_bookmark FROM " + StoreDatabase.TABLE_NOTE + "order by store_id desc";
        int recordCount = -1;

        if(storeDatabase != null) {
            Cursor outCursor = storeDatabase.rawQuery(loadSQL);
            recordCount = outCursor.getCount();

            ArrayList<Store> items = new ArrayList<>();

            for(int i=0; i<recordCount; i++)
            {
                outCursor.moveToNext();
                int store_id = outCursor.getInt(0);
                String store_name = outCursor.getString(1);
                String store_info = outCursor.getString(2);
                String store_location = outCursor.getString(3);
                String store_uri = outCursor.getString(4);
                double store_pos1 = outCursor.getDouble(5);
                double store_pos2 = outCursor.getDouble(6);
                boolean store_bookmark = Boolean.getBoolean(outCursor.getString(7));
                items.add(new Store(store_id, store_name, store_info, store_location, store_uri, store_pos1, store_pos2, store_bookmark));
            }
            outCursor.close();

            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
        return recordCount;
    }*/
}