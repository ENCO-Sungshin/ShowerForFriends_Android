package com.example.showerforfriends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.core.Amplify;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<Friend> friendArrayList = new ArrayList<>();
    private ArrayList<String> friend_id = new ArrayList<>();
    private FriendItemAdapter friendItemAdapter;
    private RecyclerView recyclerView;
    private Friend friendItem;
    int count_val;
    Context context;
    String name;
    String array[];
    Integer user_id_index = 0;
    Integer user_name_index = 6;
    Integer user_display_index = 5;

    public String res;
    Friend f;
    //Button addFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //context = getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.friendList);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new EventViewDecoration(40));

        RestOptions options = RestOptions.builder()
                .addPath("/info")
                .build();

        Amplify.API.get(options,
                response -> {
                    Log.i("MyAmplifyApp", "GET succeeded: " + response);
                    //user 친구목록 받아오기
                    res = response.getData().asString();
                    JsonObject res_obj = JsonParser.parseString(res).getAsJsonObject();
                    JsonArray userfriends_arr = res_obj.getAsJsonArray("Items");
                    count_val = res_obj.get("Count").getAsInt();

                    String name = null;
                    for(int i = 0 ; i < count_val; i++){
                        Log.i("",userfriends_arr.get(i).getAsJsonObject().get("user_name").getAsString());
                        Log.i("",userfriends_arr.get(i).getAsJsonObject().get("user_id").getAsString());
                        name = userfriends_arr.get(i).getAsJsonObject().get("user_name").getAsString();
                        friend_id.add(userfriends_arr.get(i).getAsJsonObject().get("user_id").getAsString());
                        friendArrayList.add(new Friend(name,0,R.drawable.person1));
                    }

                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          for(int i = 0 ; i < count_val; i++) {
                                              String userinfo = "{" +
                                                      "\"user_id\":\"" + friend_id.get(i) + "\""
                                                      + "}";
                                              friendItem=friendArrayList.get(i);

                                              RestOptions options2 = RestOptions.builder()
                                                      .addPath("/usage")
                                                      .addBody(userinfo.getBytes())
                                                      .build();

                                              Amplify.API.post(options2,
                                                      response -> {
                                                          Log.i("MyAmplifyApp", "GET succeeded: " + response.getData().asString());
                                                          JsonObject res_obj2 = JsonParser.parseString(response.getData().asString()).getAsJsonObject();
                                                          JsonArray friend_usage_arr = res_obj2.getAsJsonArray("Items");
                                                          if (res_obj2.get("Count").getAsInt()==0){
                                                              friendItem.setUse_time(0);
                                                          }
                                                          else {
                                                              Log.i("", "" + friend_usage_arr.get(0).getAsJsonObject().get("totalAmount").getAsInt());
                                                              friendItem.setUse_time(friend_usage_arr.get(0).getAsJsonObject().get("totalAmount").getAsInt());
                                                           }
                                                      },
                                                      error -> Log.e("MyAmplifyApp", "GET failed: ", error)
                                              );
                                          }

                                          GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                          layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                          //System.out.println(friendArrayList.get(0).getFriend_name());
                                          friendItemAdapter = new FriendItemAdapter(getApplicationContext(), friendArrayList);
                                          recyclerView.setLayoutManager(layoutManager);
                                          recyclerView.setAdapter(friendItemAdapter);
                                      }
                                  }
                    );
                },
                error -> Log.e("MyAmplifyApp", "GET failed: ", error)
        );

        // 액션바에 뒤로가기 버튼 추가하고 누르면 홈화면으로 돌아가기
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    // 액션바에 뒤로가기 버튼 추가하고 누르면 홈화면으로 돌아가기
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}