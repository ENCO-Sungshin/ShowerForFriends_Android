package com.example.showerforfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button goToLogin, goToTimer, goToMypage, goToBookmark, goToFriendList, goToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToLogin = (Button) findViewById(R.id.goToLogin);
        goToTimer = (Button) findViewById(R.id.goToTimer);
        goToMypage = (Button) findViewById(R.id.goToMypage);
        goToBookmark = (Button) findViewById(R.id.goToBookmark);
        goToFriendList = (Button) findViewById(R.id.goToFriendList);
        goToHome = (Button) findViewById(R.id.goToHome);

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        goToTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                startActivity(intent);
            }
        });

        goToMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
            }
        });

        goToBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BookmarkActivity.class);
                startActivity(intent);
            }
        });

        goToFriendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FirendListActivity.class);
                startActivity(intent);
            }
        });

        goToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}