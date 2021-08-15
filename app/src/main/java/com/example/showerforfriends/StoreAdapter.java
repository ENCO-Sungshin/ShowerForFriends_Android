package com.example.showerforfriends;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    private static final String TAG = "StoreAdapter";

    ArrayList<Store> items = new ArrayList<> ();

    @NonNull
    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.ViewHolder holder, int position) {
        Store item = items.get(position);
        holder.setItem(item);
        holder.setLayout();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutStore;
        TextView storeID, storeName, storeInfo, storeLocation, storeBookmark;
        ImageView storePicture;
        Button storeURL, storeMap;

        public ViewHolder(View itemView) {
            super(itemView);

            layoutStore = itemView.findViewById(R.id.layoutStore);
            storeID = itemView.findViewById(R.id.storeID);
            storeName = itemView.findViewById(R.id.storeName);
            storeInfo = itemView.findViewById(R.id.storeInfo);
            storeLocation = itemView.findViewById(R.id.storeLocation);
            storeBookmark = itemView.findViewById(R.id.storeBookmark);

            storeURL = itemView.findViewById(R.id.storeURI);
            storeMap = itemView.findViewById(R.id.storeMap);

            storeURL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int _id = Integer.parseInt(storeID.getText().toString());
                    String url = selectURI(_id);
                    Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(mIntent);
                }

                Context context;
                private String selectURI(int id) {
                    String selectURISql = "SELECT store_id FROM " + StoreDatabase.TABLE_NOTE + "where store_id = '" + id + "'";
                    StoreDatabase database = StoreDatabase.getInstance(context);
                    Cursor cursor = database.rawQuery(selectURISql);
                    while(cursor.moveToNext()) {
                        String getURLWithID = cursor.getString(3);
                        return getURLWithID;
                    }
                    return null;
                }
            });
        }

        public void setItem(Store item){
            storeID.setText(item.getStore_id());
            storeName.setText(item.getStore_name());
            storeInfo.setText(item.getStore_info());
            storeLocation.setText(item.getStore_loaciton());
            storeBookmark.setText((item.getStore_bookmark()) == true ? "❤" : "찜하기");
        }

        //아이템들을 담은 LinearLayout을 보여주게하는 메서드
        public void setLayout(){
            layoutStore.setVisibility(View.VISIBLE);
        }
    }

    //배열에 있는 item들을 가리킴
    public void setItems(ArrayList<Store> items){
        this.items = items;
    }
}
