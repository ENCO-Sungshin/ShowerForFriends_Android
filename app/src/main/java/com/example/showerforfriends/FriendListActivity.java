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

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<Friend> friendArrayList = new ArrayList<>();
    private FriendItemAdapter friendItemAdapter;
    private RecyclerView recyclerView;
    private Friend[] friendItem;
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
                .addHeader("Accept","application/hal+json")
                .addHeader("Content-Type","application/json;charset=UTF-8")
                .addPath("/info")
                .build();

        Amplify.API.get(options,
                response -> {
                    Log.i("MyAmplifyApp", "GET succeeded: " + response);

                    /*String res = response.getData().asString();

                    System.out.println("String : " + res);*/


                    //String array[] = res.split("},");
                    /*try {
                        JSONObject jsonObject = response.getData().asJSONObject();
                        JSONArray jsonArray = new JSONArray();
                        jsonObject.toJSONArray(jsonArray);
                        System.out.println(jsonArray.length());

                        for(int i=0; i<jsonArray.length(); i++)
                            System.out.println("i=" + i + " : " + jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    /*String jsonStr = response.getData().asString();
                    array = jsonStr.split("]");

                    for(int i=0; i<array.length; i++)
                        System.out.println(array[i]);

                    String data_str = array[0].substring(array[0].indexOf("[")+1);
                    System.out.println("data_str : " + data_str);
                    String array_2[] = data_str.split(",");
                    for(int i=0; i<array_2.length; i++) {
                        System.out.println(array_2[i]);
                    }*/

                  /*  name = new String[2];
                    for(int i=0; i<2 && i*7+6 < array_2.length; i++)
                    {
                        name[i] = array_2[i*7+6].substring(array_2[i*7+6].indexOf(":"), array_2[i*7+6].indexOf("}"));
                        System.out.println("name : " + name[i]);
                    }*/
                    /*JsonParser parser = new JsonParser();
                    Object object = parser.parse(jsonStr);
                    JsonObject jsonObject = (JsonObject) object;

                    System.out.println("jsonStr : " + jsonObject);

                    JsonElement element = jsonObject.get("user_name");
                    System.out.println("element : " + element);*/
                    /*JsonElement name_json = jsonObject.get("\"user_name\"");
                    name = name_json.getAsString();

                    System.out.println("name : " + name);*/

                    System.out.println("String : " + response.getData().asString());
                    res = response.getData().asString();


                    String counts_string = res.substring(res.indexOf("]") + 2);
                    String users_data = res.substring(0, res.indexOf("]"));
                    System.out.println("User data : " + users_data);

                    Integer count_index = counts_string.indexOf("\"Count\"");
                    String count_data = counts_string.substring(count_index, counts_string.substring(count_index).indexOf(",") + count_index);
                    Integer count_value = Integer.parseInt(count_data.substring(count_data.indexOf(":") + 1));
                    System.out.println("Count : " + count_value);
                    users_data = users_data.substring(10);

                    String userData[] = users_data.split(",");

                    friendItem = new Friend[count_value];

                    int count = 0;
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
                                name = userData[i].substring(1, userData[i].indexOf("}")-1);
                                System.out.println("name : " + name);
                                Friend item = new Friend(name, 0, R.drawable.person1);
                                friendArrayList.add(item);
                            }
                            /*else if (i == count * 7 + user_display_index) {
                                showData[index] = userData[i];
                            }*/
                        }
                        if(i % 7 == 6)
                        {
                            count++;
                            /*addGroupItem(name, 0, 1);*/
                            /*Friend item = new Friend(name, 0, 0);
                            friendArrayList.add(item);*/
                        }

                        System.out.println( userData[i] + " / ");
                        //recyclerView.setAdapter(friendItemAdapter);
                        friendItemAdapter = new FriendItemAdapter(context, friendArrayList);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                //System.out.println(friendArrayList.get(0).getFriend_name());
                                //friendItemAdapter = new FriendItemAdapter(getApplicationContext(), friendArrayList);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(friendItemAdapter);
                            }
                        });

                    }

                   /* System.out.println(friendArrayList.get(0).getFriend_name());*/
                },
                error -> Log.e("MyAmplifyApp", "GET failed: ", error));

        /*LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //System.out.println(friendArrayList.get(0).getFriend_name());
        //friendItemAdapter = new FriendItemAdapter(getApplicationContext(), friendArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(friendItemAdapter);*/

        /*String data_str = array[0].substring(array[0].indexOf("[")+1);
        System.out.println("data_str : " + data_str);
        String array_2[] = data_str.split(",");
        for(int i=0; i<array_2.length; i++) {
            System.out.println(array_2[i]);
        }

        for(int i=0; i<2 && i*7+6 < array_2.length; i++)
        {
            name[i] = array_2[i*7+6].substring(array_2[i*7+6].indexOf(":"), array_2[i*7+6].indexOf("}"));
            System.out.println("name : " + name[i]);
        }*/

        /*Friend friend[] = new Friend[2];
        for(int i=0; i<2; i++)
        {
            friendArrayList.add(new Friend(name[i], 0, 0));
        }*/


        // 액션바에 뒤로가기 버튼 추가하고 누르면 홈화면으로 돌아가기
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*addFriend = (Button) findViewById(R.id.addFriend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FriendAddActivity.class);
                startActivity(intent);
            }
        });*/

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