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

public class FriendItemAdapter extends RecyclerView.Adapter<com.example.showerforfriends.FriendItemAdapter.ViewHolder> {

    ArrayList<Friend> items = new ArrayList<Friend>();
    Context context;

    public FriendItemAdapter(Context context, ArrayList<Friend> friendArrayList)
    {
        this.context = context;
        this.items = friendArrayList;
    }

    @NonNull
    @Override
    public FriendItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, null);
        return new FriendItemAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendItemAdapter.ViewHolder holder, int position) {
        ((FriendItemAdapter.ViewHolder)holder).onBind(items.get(position));
    }

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView friend_name, friend_usage;
        ImageView image_friend;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            // 화면에 내용 넣기
            friend_name = (TextView) itemView.findViewById(R.id.friend_name);
            friend_usage = (TextView) itemView.findViewById(R.id.friend_usage);
            image_friend = (ImageView) itemView.findViewById(R.id.image_friend);


        }

        public void onBind(Friend item) {
            friend_name.setText(item.getFriend_name());
            friend_usage.setText(item.getUse_time());
            image_friend.setImageResource(item.getFriend_img());
        }
    }
}
