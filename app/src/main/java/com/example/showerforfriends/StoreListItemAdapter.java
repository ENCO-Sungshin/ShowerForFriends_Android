package com.example.showerforfriends;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreListItemAdapter extends RecyclerView.Adapter<StoreListItemAdapter.ViewHolder> {

    ArrayList<StoreListItem> item = new ArrayList<StoreListItem>();
    Context context;

    public StoreListItemAdapter(Context context, ArrayList<StoreListItem> storeListItems)
    {
        this.context = context;
        this.item = storeListItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_list_item, null);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoreListItem storeListItem = item.get(position);

        holder.storeBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(storeListItem.getStoreBookmark() == true) {
                    storeListItem.setStoreBookmark(false);
                    holder.storeBookmark.setText("♡");
                }
                else {
                    storeListItem.setStoreBookmark(true);
                    holder.storeBookmark.setText("❤");
                }
            }
        });

        holder.storeURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(storeListItem.getStoreURL()));
                view.getContext().startActivity(mIntent);
            }
        });

        holder.storeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // map 연결하기
                Intent intent = new Intent(view.getContext(), ShowMapActivity.class);
                intent.putExtra("position1", storeListItem.getPosition1());
                intent.putExtra("position2", storeListItem.getPosition2());
                intent.putExtra("store_name", storeListItem.getStoreName());
                view.getContext().startActivity(intent);
            }
        });

        holder.storeName.setText(storeListItem.getStoreName());
        holder.storeIntro.setText(storeListItem.getStoreIntro());
        holder.storeLocation.setText(storeListItem.getStoreLocation());
        holder.storePicture.setImageResource(storeListItem.getStorePicture());
        //holder.storeURL.setText(storeListItem.getStoreURL());

        if(storeListItem.getStoreBookmark() == true)
            holder.storeBookmark.setText("❤");
        else
            holder.storeBookmark.setText("🤍");
    }

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public int getItemCount() {
        return item.size();
    }

    private OnItemClickListener mListener = null;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.mListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView storeName, storeIntro, storeLocation, storeBookmark;
        ImageView storePicture;
        Button storeURL, storeMap;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            // 화면에 내용 넣기
            storeName = (TextView) itemView.findViewById(R.id.storeName);
            storeIntro = (TextView) itemView.findViewById(R.id.storeIntro);
            storeLocation = (TextView) itemView.findViewById(R.id.storeLocation);
            storePicture = (ImageView) itemView.findViewById(R.id.storePicture);
            storeBookmark = (TextView) itemView.findViewById(R.id.storeBookmark);
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
    }
}
