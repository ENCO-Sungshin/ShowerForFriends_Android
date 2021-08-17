package com.example.showerforfriends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.core.Amplify;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FirendListActivity extends AppCompatActivity {

    private ArrayList<Friend> friendArrayList = new ArrayList<>();
    FriendItemAdapter recyclerViewAdapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    Context context;
    String name;

    //Button addFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firend_list);

        context = getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.friendList);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new EventViewDecoration(40));


        RestOptions options = RestOptions.builder()
                .addHeader("Accept","application/hal+json")
                .addHeader("Content-Type","application/json;charset=UTF-8")
                .addPath("/info")
                .build();

        Amplify.API.get(options,
                response -> {
                    Log.i("MyAmplifyApp", "GET succeeded: " + response);

                    System.out.println("String : " + response.getData().asString());
                    String res = response.getData().asString();
                    String counts_string = res.substring(res.indexOf("]") + 2);
                    String users_data = res.substring(0, res.indexOf("]"));
                    System.out.println("User data : " + users_data);

                    Integer count_index = counts_string.indexOf("\"Count\"");
                    String count_data = counts_string.substring(count_index, counts_string.substring(count_index).indexOf(",") + count_index);
                    Integer count_value = Integer.parseInt(count_data.substring(count_data.indexOf(":") + 1));
                    System.out.println("Count : " + count_value);
                    users_data = users_data.substring(10);
                    String userData[] = users_data.split(",");

                    int count = 0;
                    Integer user_id_index = 0;
                    Integer user_name_index = 6;
                    Integer user_display_index = 5;
                    Integer index = 0;
                    for(int i=0; i<userData.length; i++)
                    {
                        System.out.println( userData[i] + " / ");
                        userData[i] = userData[i].substring(userData[i].indexOf(":") + 1);
                        if(count < count_value) {
                            /*if (i == count * 7 + user_id_index) {
                               name = userData[i];
                            }
                            else*/ if (i == count * 7 + user_name_index) {
                                name = userData[i];
                                System.out.println("name : " + name);
                            }
                            /*else if (i == count * 7 + user_display_index) {
                                showData[index] = userData[i];
                            }*/
                        }
                        if(i % 7 == 6)
                        {
                            count++;
                            /*addGroupItem(name, 0, 1);*/
                            Friend item = new Friend(name, 0, 0);
                            friendArrayList.add(item);
                        }

                        System.out.println( userData[i] + " / ");
                    }

                },
                error -> Log.e("MyAmplifyApp", "GET failed: ", error));


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAdapter = new FriendItemAdapter(context, friendArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        /*addFriend = (Button) findViewById(R.id.addFriend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FriendAddActivity.class);
                startActivity(intent);
            }
        });*/

    }

    public void addGroupItem(String friend_name, Integer use_time, Integer friend_image){
        Friend item = new Friend(friend_name, use_time, friend_image);
        friendArrayList.add(item);
    }
}