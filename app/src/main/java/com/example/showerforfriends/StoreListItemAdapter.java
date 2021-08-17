package com.example.showerforfriends;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLData;
import java.util.ArrayList;

import static com.example.showerforfriends.SQLiteHelper.TABLE_NAME;

public class StoreListItemAdapter extends RecyclerView.Adapter<StoreListItemAdapter.ViewHolder> {

    ArrayList<Store> item = new ArrayList<Store>();
    Context context;
    /*SQLiteDatabase sqLiteDatabase;
    SQLiteHelper sqLiteHelper;
*/
    public StoreListItemAdapter(Context context, ArrayList<Store> storeListItems)
    {
        this.context = context;
        this.item = storeListItems;
        /*this.sqLiteDatabase = sqLiteDatabase;
        StoreListFragment.storeDatabase
        this.sqLiteHelper = new SQLiteHelper(this.context);*/
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_list_item, null);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((ViewHolder)holder).onBind(item.get(position));
        /*StoreListItemAdapter.ViewHolder viewHolder = (StoreListItemAdapter.ViewHolder) holder;
        Store storeListItem = item.get(position);

        holder.storeBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(storeListItem.getStore_bookmark() == true) {
                    storeListItem.setStore_bookmark(false);
                    holder.storeBookmark.setText("♡");
                }
                else {
                    storeListItem.setStore_bookmark(true);
                    holder.storeBookmark.setText("❤");
                }
            }
        });

        viewHolder.storeURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mListener.onItemClick(view, position);
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(storeListItem.getStore_uri()));
                view.getContext().startActivity(mIntent);
            }
        });

        viewHolder.storeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // map 연결하기
                Intent intent = new Intent(view.getContext(), ShowMapActivity.class);
                intent.putExtra("position1", storeListItem.getStore_pos1());
                intent.putExtra("position2", storeListItem.getStore_pos2());
                intent.putExtra("store_name", storeListItem.getStore_name());
                view.getContext().startActivity(intent);
            }
        });

        viewHolder.storeName.setText(storeListItem.getStore_name());
        viewHolder.storeIntro.setText(storeListItem.getStore_info());
        viewHolder.storeLocation.setText(storeListItem.getStore_loaciton());
        //viewHolder.storePicture.setImageResource(storeListItem.getStorePicture());
        //holder.storeURL.setText(storeListItem.getStoreURL());

        if(storeListItem.getStore_bookmark() == true)
            holder.storeBookmark.setText("❤");
        else
            holder.storeBookmark.setText("🤍");*/
    }

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public int getItemCount() {
        return item.size();
    }

    public interface OnItemClickListener
    {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mListener = null;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.mListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView storeID, storeName, storeIntro, storeLocation/*, storeBookmark*/;
        ImageView storePicture;
        Button storeURL, storeMap;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            // 화면에 내용 넣기
            storeID = (TextView) itemView.findViewById(R.id.storeID);
            storeName = (TextView) itemView.findViewById(R.id.storeName);
            storeIntro = (TextView) itemView.findViewById(R.id.storeInfo);
            storeLocation = (TextView) itemView.findViewById(R.id.storeLocation);
            storePicture = (ImageView) itemView.findViewById(R.id.storePicture);
           /* storeBookmark = (TextView) itemView.findViewById(R.id.storeBookmark);*/
            storeURL = (Button) itemView.findViewById(R.id.storeURI);
            storeMap = (Button) itemView.findViewById(R.id.storeMap);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION)
                    {
                        if(mListener != null)
                        {
                            mListener.onItemClick(view, position);
                        }
                    }
                }
            });
        }

        public void onBind(Store item) {
            storeID.setText(Integer.toString(item.getStore_id()));
            storeName.setText(item.getStore_name());
            storeIntro.setText(item.getStore_info());
            storeLocation.setText(item.getStore_location());
            storeURL.setText("사이트");
            storeMap.setText("지도");

            /*StoreListFragment.storeDatabase = StoreListFragment.sqLiteHelper.getReadableDatabase();
            StoreListFragment.storeDatabase.beginTransaction();
            StoreListFragment.storeDatabase.setTransactionSuccessful();
            storeBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(item.getStore_bookmark() == true) {
                        item.setStore_bookmark(false);
                        storeBookmark.setText("♡");
                        String UPDATE_SQL = "UPDATE " + TABLE_NAME + " SET store_bookmark = 'false' WHERE store_id = " + Integer.parseInt(storeID.getText().toString()); //
                        Log.i("UPDATE_SQL : ", UPDATE_SQL);
                        StoreListFragment.storeDatabase .execSQL(UPDATE_SQL); //
                    }
                    else {
                        item.setStore_bookmark(true);
                        storeBookmark.setText("❤");
                        String UPDATE_SQL = "UPDATE " + TABLE_NAME + " SET store_bookmark = 'true' WHERE store_id = " + Integer.parseInt(storeID.getText().toString()); //
                        Log.i("UPDATE_SQL : ", UPDATE_SQL);
                        StoreListFragment.storeDatabase .execSQL(UPDATE_SQL); //
                    }
                }
            });
            StoreListFragment.storeDatabase.endTransaction();*/

            storeURL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //mListener.onItemClick(view, position);
                    Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getStore_uri()));
                    view.getContext().startActivity(mIntent);
                }
            });

            storeMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // map 연결하기
                    Intent intent = new Intent(view.getContext(), ShowMapActivity.class);
                    intent.putExtra("position1", item.getStore_pos1());
                    intent.putExtra("position2", item.getStore_pos2());
                    intent.putExtra("store_name", item.getStore_name());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
