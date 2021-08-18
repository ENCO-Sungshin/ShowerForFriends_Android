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

public class FriendItemAdapter extends RecyclerView.Adapter<FriendItemAdapter.ViewHolder> {

    ArrayList<Friend> item = new ArrayList<Friend>();
    Context context;

    public FriendItemAdapter(Context context, ArrayList<Friend> friendArrayList)
    {
        this.context = context;
        this.item = friendArrayList;
    }

    @NonNull
    @Override
    public FriendItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, null);
        return new FriendItemAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendItemAdapter.ViewHolder holder, int position) {
        /*((FriendItemAdapter.ViewHolder)holder).onBind(item.get(position));*/
        Friend friendItem = item.get(position);

        holder.friend_name.setText(friendItem.getFriend_name());
        holder.friend_usage.setText(new Calculator().getGrade(friendItem.getUse_time()));
        String s= (new Calculator()).getGrade(friendItem.getUse_time());
        switch(s){
            case "고래":
                holder.image_friend.setImageResource(R.drawable.whale);
                break;
            case "거북이":
                holder.image_friend.setImageResource(R.drawable.turtle);
                break;
            case "열대어":
                holder.image_friend.setImageResource(R.drawable.tropicalfish);
                break;
            case " 불가사리":
                holder.image_friend.setImageResource(R.drawable.starfish);
                break;
        }

    }

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public int getItemCount() {
        return item.size();
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
    }
}
